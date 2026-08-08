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
 * Net router that keeps every net electrically isolated by giving every port a
 * unique stub column placed in the gap between it and its neighbours on the same
 * row, plus a unique vertical channel and a unique lane per net.
 *
 * <p>Ports on the same y row are sorted by x and each gets a stub x midway to the
 * previous port, so the short horizontal segment from the port to its stub never
 * overlaps a neighbour's segment. Stubs on different rows never interact because
 * rows differ. Each net uses one lane y and each port one channel x, all unique.
 */
public final class IsolatedRouter {
  private IsolatedRouter() {}

  public record EndpointRef(String componentId, int endIndex) {}

  public record NetWire(String id, List<EndpointRef> endpoints) {}

  public static void wire(
      CircuitMutation mutation,
      List<NetWire> nets,
      Map<String, Location> endpointLocs) {
    // Collect unique ports.
    List<String> portKeys = new ArrayList<>();
    for (NetWire net : nets) {
      for (EndpointRef ref : net.endpoints()) {
        String key = ref.componentId() + "#" + ref.endIndex();
        if (!portKeys.contains(key) && endpointLocs.containsKey(key)) {
          portKeys.add(key);
        }
      }
    }
    // Per port: stubX (gap column on its row), stubRow (unique vertical row),
    // channelX (unique vertical trunk).
    Map<String, Integer> stubX = new HashMap<>();
    Map<String, Integer> stubRow = new HashMap<>();
    Map<String, Integer> channelX = new HashMap<>();
    int chanSeq = 0;
    for (String key : portKeys) {
      channelX.put(key, 700 + 10 * chanSeq++);
    }
    // stub rows: unique across all ports. Direction: inputs (larger y, chip
    // bottom) stub downward so their vertical never crosses the output row;
    // outputs (smaller y, chip top) stub upward. Uniqueness is enforced by
    // bumping until free (different y bases can otherwise collide).
    int downSeq = 0;
    int upSeq = 0;
    for (String key : portKeys) {
      Location loc = endpointLocs.get(key);
      int row;
      if (loc.getY() >= 250) {
        row = loc.getY() + 40 + 10 * downSeq++;
        while (stubRow.containsValue(row)) {
          row += 10;
        }
      } else {
        row = loc.getY() - 40 - 10 * upSeq++;
        while (stubRow.containsValue(row)) {
          row -= 10;
        }
      }
      stubRow.put(key, row);
    }
    // stub columns: group ports by y, sort by x, gap allocation. stubX must be
    // globally unique across ALL groups (not just within a row), otherwise two
    // ports on different rows can share a stub column and their vertical stubs
    // short together.
    Map<Integer, List<String>> byRow = new LinkedHashMap<>();
    for (String key : portKeys) {
      int y = endpointLocs.get(key).getY();
      byRow.computeIfAbsent(y, k -> new ArrayList<>()).add(key);
    }
    for (List<String> group : byRow.values()) {
      group.sort(Comparator.comparingInt(k -> endpointLocs.get(k).getX()));
      int prevX = Integer.MIN_VALUE;
      for (String key : group) {
        int portX = endpointLocs.get(key).getX();
        int x;
        if (prevX == Integer.MIN_VALUE) {
          x = portX - 40;
        } else {
          x = prevX + Math.max(20, (portX - prevX) / 2);
        }
        // Global uniqueness: never reuse a stub column already taken by another
        // row's port.
        while (stubX.containsValue(x) || isPortX(endpointLocs, x)) {
          x++;
        }
        stubX.put(key, x);
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
      int laneY = 560 + (laneSeq++) * 10;
      String firstKey = key(refs.get(0));
      Location anchor = endpointLocs.get(firstKey);
      if (anchor == null) {
        continue;
      }
      for (int i = 1; i < refs.size(); i++) {
        String tKey = key(refs.get(i));
        Location target = endpointLocs.get(tKey);
        if (target == null) {
          continue;
        }
        // Path per pair:
        //   anchor -> (stubX_a, anchor.y) [row-level gap]
        //   -> (stubX_a, stubRow_a) -> (channelX_a, stubRow_a) [unique trunk]
        //   -> (channelX_a, laneY) -> (channelX_t, laneY) [net lane]
        //   -> (channelX_t, stubRow_t) -> (stubX_t, stubRow_t)
        //   -> (stubX_t, target.y) -> target
        Location a1 = Location.create(stubX.get(firstKey), anchor.getY(), true);
        Location a2 = Location.create(stubX.get(firstKey), stubRow.get(firstKey), true);
        Location a3 = Location.create(channelX.get(firstKey), stubRow.get(firstKey), true);
        Location a4 = Location.create(channelX.get(firstKey), laneY, true);
        Location b4 = Location.create(channelX.get(tKey), laneY, true);
        Location b3 = Location.create(channelX.get(tKey), stubRow.get(tKey), true);
        Location b2 = Location.create(stubX.get(tKey), stubRow.get(tKey), true);
        Location b1 = Location.create(stubX.get(tKey), target.getY(), true);
        add(mutation, anchor, a1);
        add(mutation, a1, a2);
        add(mutation, a2, a3);
        add(mutation, a3, a4);
        add(mutation, a4, b4);
        add(mutation, b4, b3);
        add(mutation, b3, b2);
        add(mutation, b2, b1);
        add(mutation, b1, target);
      }
    }
  }

  private static void add(CircuitMutation mutation, Location a, Location b) {
    if (!a.equals(b)) {
      mutation.add(Wire.create(a, b));
    }
  }

  /** True when x equals any port's x (stub verticals must never run through a port). */
  private static boolean isPortX(Map<String, Location> endpointLocs, int x) {
    for (Location loc : endpointLocs.values()) {
      if (loc.getX() == x) {
        return true;
      }
    }
    return false;
  }

  private static String key(EndpointRef ref) {
    return ref.componentId() + "#" + ref.endIndex();
  }
}
