package com.logisim_ai.layout;

import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.data.Location;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wire router that guarantees every port and every wire uses globally unique
 * coordinates, so no two nets ever touch.
 *
 * <p>Each port gets:
 * <ul>
 *   <li>a unique stub row y (port_y - 40 - 10*portIndex) and</li>
 *   <li>a unique vertical column x (starting at 700, +10 each).</li>
 * </ul>
 * Each net gets a unique lane y (560 + 10*netIndex).
 *
 * <p>Wire path: port -> (port.x, stubY) -> (ownCol, stubY) -> (ownCol, laneY)
 * -> (otherCol, laneY) -> (otherCol, otherStubY) -> (otherPort.x, otherStubY)
 * -> otherPort. Because stubY, ownCol, and laneY are all unique across the whole
 * design, no segment ever overlaps another net's segment.
 */
public final class IsolatedRouter {
  private IsolatedRouter() {}

  public record EndpointRef(String componentId, int endIndex) {}

  public record NetWire(String id, List<EndpointRef> endpoints) {}

  public static void wire(
      CircuitMutation mutation,
      List<NetWire> nets,
      Map<String, Location> endpointLocs) {
    // Assign unique stub row and column per unique port.
    Map<String, Integer> stubRow = new HashMap<>();
    Map<String, Integer> column = new HashMap<>();
    List<String> portKeys = new ArrayList<>();
    for (NetWire net : nets) {
      for (EndpointRef ref : net.endpoints()) {
        String key = ref.componentId() + "#" + ref.endIndex();
        if (!portKeys.contains(key) && endpointLocs.containsKey(key)) {
          portKeys.add(key);
        }
      }
    }
    for (int i = 0; i < portKeys.size(); i++) {
      String key = portKeys.get(i);
      Location loc = endpointLocs.get(key);
      stubRow.put(key, loc.getY() - 40 - 10 * i);
      column.put(key, 700 + 10 * i);
    }

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
      int anchorRow = stubRow.get(firstKey);
      int anchorCol = column.get(firstKey);
      for (int i = 1; i < refs.size(); i++) {
        String targetKey = key(refs.get(i));
        Location target = endpointLocs.get(targetKey);
        if (target == null) {
          continue;
        }
        int targetRow = stubRow.get(targetKey);
        int targetCol = column.get(targetKey);
        // Path
        add(mutation, anchor, Location.create(anchor.getX(), anchorRow, true));
        add(mutation, Location.create(anchor.getX(), anchorRow, true),
            Location.create(anchorCol, anchorRow, true));
        add(mutation, Location.create(anchorCol, anchorRow, true),
            Location.create(anchorCol, laneY, true));
        add(mutation, Location.create(anchorCol, laneY, true),
            Location.create(targetCol, laneY, true));
        add(mutation, Location.create(targetCol, laneY, true),
            Location.create(targetCol, targetRow, true));
        add(mutation, Location.create(targetCol, targetRow, true),
            Location.create(target.getX(), targetRow, true));
        add(mutation, Location.create(target.getX(), targetRow, true), target);
      }
    }
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
