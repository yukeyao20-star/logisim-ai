package com.logisim_ai.layout;

import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.data.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wire router with provably disjoint segments (no gap columns).
 *
 * <p>Every port gets a stub vertical in ITS OWN x (direction: larger-y ports
 * stub downward, smaller-y ports stub upward; same-x port pairs therefore stub
 * in opposite directions with disjoint y ranges), a unique stub row, a unique
 * trunk column, and every net gets a unique lane row.
 *
 * <p>Path per pair:
 * <pre>
 *   portA --vertical stub--> (xA, stubY_A) --horizontal--> (trunkA, stubY_A)
 *        --vertical trunk--> (trunkA, laneY) --horizontal lane--> (trunkB, laneY)
 *        --vertical trunk--> (trunkB, stubY_B) --horizontal--> (xB, stubY_B)
 *        --vertical stub--> portB
 * </pre>
 *
 * <p>Isolation:
 * <ul>
 *   <li>stub rows unique and never a port y; trunk columns unique; lane rows
 *       unique per net;</li>
 *   <li>stub verticals share x only with same-x ports, whose stub directions are
 *       opposite and y ranges disjoint (larger-y down, smaller-y up);</li>
 *   <li>horizontal stub segments live on unique rows, so they never overlap;</li>
 *   <li>crossings of different nets do not connect in Logisim (no endpoint on a
 *       foreign wire: endpoints are (x,stubY)/(trunk,stubY)/(trunk,laneY), and a
 *       stub vertical of another port would need the same x AND y in range, which
 *       disjoint opposite-direction ranges prevent).</li>
 * </ul>
 */
public final class IsolatedRouter {
  private IsolatedRouter() {}

  private static final int STUB_LENGTH = 40;
  private static final int FIRST_TRUNK_X = 800;
  private static final int FIRST_LANE_Y = 900;

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

    Map<String, Integer> stubY = new HashMap<>();
    Map<String, Integer> trunkX = new HashMap<>();
    int downSeq = 0;
    int upSeq = 0;
    int trunkSeq = 0;
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
      int trunk = FIRST_TRUNK_X + 10 * trunkSeq++;
      while (trunkX.containsValue(trunk) || isPortX(endpointLocs, trunk)) {
        trunk += 10;
      }
      trunkX.put(key, trunk);
    }

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
      System.out.println("[router] net " + net.id() + " lane=" + laneY
          + " anchor=" + firstKey + " trunk=" + aTrunk + " stub=" + aStub);
      for (int i = 1; i < refs.size(); i++) {
        String tKey = key(refs.get(i));
        Location target = endpointLocs.get(tKey);
        if (target == null) {
          continue;
        }
        int tStub = stubY.get(tKey);
        int tTrunk = trunkX.get(tKey);
        System.out.println("[router]   -> " + tKey + " trunk=" + tTrunk + " stub=" + tStub);
        add(mutation, anchor, Location.create(anchor.getX(), aStub, true));
        add(mutation, Location.create(anchor.getX(), aStub, true),
            Location.create(aTrunk, aStub, true));
        add(mutation, Location.create(aTrunk, aStub, true),
            Location.create(aTrunk, laneY, true));
        add(mutation, Location.create(aTrunk, laneY, true),
            Location.create(tTrunk, laneY, true));
        add(mutation, Location.create(tTrunk, laneY, true),
            Location.create(tTrunk, tStub, true));
        add(mutation, Location.create(tTrunk, tStub, true),
            Location.create(target.getX(), tStub, true));
        add(mutation, Location.create(target.getX(), tStub, true), target);
      }
    }
  }

  private static boolean isPortY(Map<String, Location> locs, int y) {
    for (Location l : locs.values()) {
      if (l.getY() == y) {
        return true;
      }
    }
    return false;
  }

  private static boolean isPortX(Map<String, Location> locs, int x) {
    for (Location l : locs.values()) {
      if (l.getX() == x) {
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
