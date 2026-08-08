package com.logisim_ai.layout;

import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.data.Location;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Routes wires through per-port vertical channels placed between adjacent ports.
 *
 * <p>For each unique component port we allocate a vertical column x between that
 * port and its neighbour, so every wire's vertical run lives in a gap that no
 * other port or wire occupies. Horizontal runs stay inside the port's own y row
 * between the channel and the port. This prevents the WireRepair OOM that dense
 * overlapping wires trigger, and keeps every net electrically isolated.
 */
public final class IsolatedRouter {
  private IsolatedRouter() {}

  public record EndpointRef(String componentId, int endIndex) {}

  public record NetWire(String id, List<EndpointRef> endpoints) {}

  /**
   * Wire all nets.
   *
   * @param endpointLocs keyed by "componentId#endIndex"
   */
  public static void wire(
      CircuitMutation mutation,
      List<NetWire> nets,
      Map<String, Location> endpointLocs) {
    // Collect unique ports, grouped by x so we can allocate channels in gaps.
    Map<String, Location> ports = new HashMap<>();
    for (NetWire net : nets) {
      for (EndpointRef ref : net.endpoints()) {
        String key = ref.componentId() + "#" + ref.endIndex();
        if (!ports.containsKey(key) && endpointLocs.containsKey(key)) {
          ports.put(key, endpointLocs.get(key));
        }
      }
    }
    List<Map.Entry<String, Location>> sorted = new ArrayList<>(ports.entrySet());
    sorted.sort(Comparator.comparingInt(e -> e.getValue().getX()));

    // Allocate a vertical channel x per port, in the gap before/after it.
    Map<String, Integer> channelForPort = new HashMap<>();
    for (int i = 0; i < sorted.size(); i++) {
      Map.Entry<String, Location> e = sorted.get(i);
      int portX = e.getValue().getX();
      // Channel sits just left of this port (or right of previous if ports share x).
      int channel = portX - 30;
      // Ensure uniqueness: if colliding with previous port, place after it.
      if (i > 0) {
        int prevX = sorted.get(i - 1).getValue().getX();
        int prevChannel = channelForPort.get(sorted.get(i - 1).getKey());
        if (channel <= prevX || channel <= prevChannel) {
          channel = prevChannel + 10;
        }
      }
      channelForPort.put(e.getKey(), channel);
    }

    int laneSeq = 0;
    for (NetWire net : nets) {
      List<EndpointRef> refs = net.endpoints();
      if (refs.size() < 2) {
        continue;
      }
      int laneY = 560 + (laneSeq++) * 10;
      EndpointRef first = refs.get(0);
      Location anchor = endpointLocs.get(key(first));
      if (anchor == null) {
        continue;
      }
      int anchorChannel = channelForPort.get(key(first));
      for (int i = 1; i < refs.size(); i++) {
        EndpointRef targetRef = refs.get(i);
        Location target = endpointLocs.get(key(targetRef));
        if (target == null) {
          continue;
        }
        int targetChannel = channelForPort.get(key(targetRef));
        // Route: anchor -> (anchorChannel, anchor.y) -> (anchorChannel, laneY)
        //        -> (targetChannel, laneY) -> (targetChannel, target.y) -> target
        add(mutation, anchor, Location.create(anchorChannel, anchor.getY(), true));
        add(mutation, Location.create(anchorChannel, anchor.getY(), true),
            Location.create(anchorChannel, laneY, true));
        add(mutation, Location.create(anchorChannel, laneY, true),
            Location.create(targetChannel, laneY, true));
        add(mutation, Location.create(targetChannel, laneY, true),
            Location.create(targetChannel, target.getY(), true));
        add(mutation, Location.create(targetChannel, target.getY(), true), target);
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
