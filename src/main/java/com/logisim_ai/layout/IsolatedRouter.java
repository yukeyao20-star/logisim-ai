package com.logisim_ai.layout;

import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.data.Location;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Wire router with DRC-grade segment isolation.
 *
 * <p>Isolation invariants enforced:
 * <ol>
 *   <li>stub rows are globally unique AND never equal any port y (so a stub
 *       horizontal never runs through another port's y);</li>
 *   <li>same-x ports (74138 top/bottom pairs) stub in opposite directions
 *       (larger-y downward, smaller-y upward), so their stub vertical ranges
 *       are disjoint;</li>
 *   <li>gap columns sit strictly between adjacent ports on the same row, so a
 *       stub horizontal only spans its own gap and never covers another port's
 *       x;</li>
 *   <li>trunk columns and lane rows are globally unique.</li>
 * </ol>
 */
public final class IsolatedRouter {
  private IsolatedRouter() {}

  private static final int STUB_LENGTH = 40;
  private static final int FIRST_TRUNK_X = 800;
  private static final int FIRST_LANE_Y = 560;

  public record EndpointRef(String componentId, int endIndex) {}

  public record NetWire(String id, List<EndpointRef> endpoints) {}

  public static void wire(
      CircuitMutation mutation,
      List<NetWire> nets,
      Map<String, Location> endpointLocs) {
    List<String> portKeys = new ArrayList<>();
    for (NetWire net : nets) {
      for (EndpointRef ref : net.endpoints()) {
        String key = ref.componentId() + "#" + ref.endIndex();
        if (!portKeys.contains(key) && endpointLocs.containsKey(key)) {
          portKeys.add(key);
        }
      }
    }

    // Trunk columns: globally unique, outside all port x (ports < 800).
    Map<String, Integer> trunkX = new HashMap<>();
    for (int i = 0; i < portKeys.size(); i++) {
      trunkX.put(portKeys.get(i), FIRST_TRUNK_X + 10 * i);
    }

    // Stub rows: downward for portY >= 250 (chip bottom / inputs), upward for
    // portY < 250 (chip top / outputs). Never equal to any port y.
    Map<String, Integer> stubY = new HashMap<>();
    int downSeq = 0;
    int upSeq = 0;
    for (String key : portKeys) {
      Location loc = endpointLocs.get(key);
      boolean down = loc.getY() >= 250;
      int row = down
          ? loc.getY() + STUB_LENGTH + 10 * downSeq++
          : loc.getY() - STUB_LENGTH - 10 * upSeq++;
      while (stubY.containsValue(row) || isPortY(endpointLocs, row)) {
        row += down ? 10 : -10;
      }
      stubY.put(key, row);
    }

    // Gap columns: within each y-row group, sort by x and place the gap midway
    // to the previous port (first port gets portX-40). Uniqueness across groups
    // is enforced by bumping.
    Map<String, Integer> gapX = new HashMap<>();
    Map<Integer, List<String>> byRow = new LinkedHashMap<>();
    for (String key : portKeys) {
      byRow.computeIfAbsent(endpointLocs.get(key).getY(), k -> new ArrayList<>()).add(key);
    }
    for (List<String> group : byRow.values()) {
      group.sort(Comparator.comparingInt(k -> endpointLocs.get(k).getX()));
      int prevX = Integer.MIN_VALUE;
      for (String key : group) {
        int portX = endpointLocs.get(key).getX();
        int x = (prevX == Integer.MIN_VALUE) ? portX - 40
            : prevX + Math.max(20, (portX - prevX) / 2);
        while (gapX.containsValue(x) || isPortX(endpointLocs, x)) {
          x++;
        }
        gapX.put(key, x);
        prevX = portX;
      }
    }

    // Wire each net.
    int laneSeq = 0;
    for (NetWire net : nets) {
      List<EndpointRef> refs = net.endpoints();
      if (refs.size() < 2) {
        continue;
      }
      int laneY = FIRST_LANE_Y + (laneSeq++) * 10;
      String firstKey = key(refs.get(0));
      Location anchor = endpointLocs.get(firstKey);
      if (anchor == null) {
        continue;
      }
      int aStub = stubY.get(firstKey);
      int aTrunk = trunkX.get(firstKey);
      int aGap = gapX.get(firstKey);
      for (int i = 1; i < refs.size(); i++) {
        String tKey = key(refs.get(i));
        Location target = endpointLocs.get(tKey);
        if (target == null) {
          continue;
        }
        int tStub = stubY.get(tKey);
        int tTrunk = trunkX.get(tKey);
        int tGap = gapX.get(tKey);
        // Path:
        //  port -> (gap, portY) [row gap] -> (gap, stubY) [stub]
        //  -> (trunk, stubY) [short] -> (trunk, laneY) [trunk]
        //  -> (tTrunk, laneY) [lane] -> (tTrunk, tStub) [trunk]
        //  -> (tGap, tStub) [short] -> (tGap, tY) [stub] -> port
        add(mutation, anchor, Location.create(aGap, anchor.getY(), true));
        add(mutation, Location.create(aGap, anchor.getY(), true),
            Location.create(aGap, aStub, true));
        add(mutation, Location.create(aGap, aStub, true),
            Location.create(aTrunk, aStub, true));
        add(mutation, Location.create(aTrunk, aStub, true),
            Location.create(aTrunk, laneY, true));
        add(mutation, Location.create(aTrunk, laneY, true),
            Location.create(tTrunk, laneY, true));
        add(mutation, Location.create(tTrunk, laneY, true),
            Location.create(tTrunk, tStub, true));
        add(mutation, Location.create(tTrunk, tStub, true),
            Location.create(tGap, tStub, true));
        add(mutation, Location.create(tGap, tStub, true),
            Location.create(tGap, target.getY(), true));
        add(mutation, Location.create(tGap, target.getY(), true), target);
      }
    }
  }

  private static boolean isPortX(Map<String, Location> locs, int x) {
    for (Location l : locs.values()) {
      if (l.getX() == x) {
        return true;
      }
    }
    return false;
  }

  private static boolean isPortY(Map<String, Location> locs, int y) {
    for (Location l : locs.values()) {
      if (l.getY() == y) {
        return true;
      }
    }
    return false;
  }

  private static void add(CircuitMutation mutation, Location a, Location b) {
    if (!a.equals(b)) {
      mutation.add(Wire.create(a, b));
    }
  }

  private static String key(EndpointRef ref) {
    return ref.componentId() + "#" + ref.endIndex();
  }
}
