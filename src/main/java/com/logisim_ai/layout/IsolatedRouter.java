package com.logisim_ai.layout;

import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.wiring.Tunnel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Wire router with provably disjoint segments for multi-chip netlists, aware
 * that Logisim-evolution runs WireRepair (doMerges / doOverlaps / doSplits)
 * after every mutation: any wire endpoint that lands on a foreign wire is
 * split into a junction (short) and overlapping collinear wires are merged.
 *
 * <p>Layout per (net, port-role):
 * <pre>
 *   port --stub--> (portX, stubY) --trunk seg--> (trunkX, stubY)
 *        --vertical--> (trunkX, laneY) --lane seg--> (tTrunk, laneY)
 *        --vertical--> (tTrunk, tStubY) --trunk seg--> (tPortX, tStubY) --stub--> port
 * </pre>
 *
 * <p>Isolation invariants (verified against WireRepair semantics):
 * <ul>
 *   <li>stub rows: one per port, unique, never a port y; same-x ports stub in
 *       opposite directions with disjoint y ranges (no same-x stub overlap);</li>
 *   <li>trunks: unique per (port, net) usage, never a port x (fan-out ports get
 *       one trunk per net, so no two nets share a vertical endpoint);</li>
 *   <li>lane rows: unique per net, above all stub rows;</li>
 *   <li>every segment endpoint is either a port, a (port,net)-owned trunk, or the
 *       net's own lane; a foreign wire would need the same x and y in range to
 *       catch an endpoint, which the disjoint invariants prevent.</li>
 * </ul>
 *
 * <p>Hybrid routing: nets whose leftmost port sits farther than
 * {@link #PHYSICAL_REACH} from the trunk bank (all trunks live right of the
 * whole circuit, so their stub horizontal would cross every component
 * reserved area in the row) are connected with same-label Tunnels placed on
 * their port points instead of wires. This adds no wires, so the physical
 * invariants above are unaffected.
 */
public final class IsolatedRouter {
  private IsolatedRouter() {}

  private static final int STUB_LENGTH = 40;
  private static final int ROUTING_MARGIN = 300;
  /**
   * Physical reach of a stub-row horizontal. The trunk bank always sits right
   * of the whole circuit, so a net whose leftmost port is farther than this
   * from the bank would need a full-width horizontal that crosses every
   * component reserved area in its row. Such nets are connected with same-label
   * Tunnel components placed exactly on their port points instead — no wires,
   * so the WireRepair isolation invariants of the physically-routed nets are
   * untouched.
   */
  private static final int PHYSICAL_REACH = 2500;

  public record EndpointRef(String componentId, int endIndex) {}

  public record NetWire(String id, List<EndpointRef> endpoints) {}

  public static void wire(
      CircuitMutation mutation,
      List<NetWire> nets,
      Map<String, Location> endpointLocs) {
    wire(mutation, nets, endpointLocs, Map.of(), Set.of());
  }

  /**
   * Full routing entry. {@code tunnelOffsets} maps endpoint keys
   * ({@code componentId#endIndex}) to a vertical offset for their tunnel:
   * absent/0 = tunnel directly on the port point; negative = place the tunnel
   * that many pixels above the port and connect it with a short vertical wire
   * (used for ports that coincide with their component anchor, where an
   * on-port tunnel would trigger exact-overlap). {@code pinEndpointKeys}
   * lists external-pin endpoints: nets touching a pin are always tunneled —
   * pins share a column (inputs) or a diagonal band (outputs), and a physical
   * stub vertical at a pin's x crosses or merges with neighbouring pins' stub
   * verticals (WireRepair then shorts them).
   */
  public static void wire(
      CircuitMutation mutation,
      List<NetWire> nets,
      Map<String, Location> endpointLocs,
      Map<String, Integer> tunnelOffsets,
      Set<String> pinEndpointKeys) {
    // Fan-out roots: a driving port (e.g. a D-FF's Q) legitimately appears in
    // several spec nets (Q->gate1, Q->gate2, Q->output pin). The router's
    // contract is "one net per port" -- otherwise each net gives the same port
    // a different trunk column and Pass 2's merged stub-row horizontal shorts
    // them together. Any two nets sharing a port are the same electrical
    // signal, so union them into one NetWire first.
    nets = unionNetsBySharedPort(nets);

    // Compute routing zone from component bounding box
    int maxX = 0, maxY = 0;
    for (Location loc : endpointLocs.values()) {
      maxX = Math.max(maxX, loc.getX());
      maxY = Math.max(maxY, loc.getY());
    }
    int firstTrunkX = maxX + ROUTING_MARGIN;
    int firstLaneY = maxY + ROUTING_MARGIN;

    // Hybrid routing: nets whose leftmost port lies beyond PHYSICAL_REACH of
    // the trunk bank are tunneled (their stub horizontal would cross the whole
    // circuit); the rest keep the provably-isolated physical route.
    List<NetWire> physical = new ArrayList<>();
    List<NetWire> tunneled = new ArrayList<>();
    for (NetWire net : nets) {
      List<EndpointRef> refs = net.endpoints();
      if (refs.size() < 2) {
        physical.add(net);
        continue;
      }
      int minPortX = Integer.MAX_VALUE;
      boolean allKnown = true;
      boolean touchesPin = false;
      for (EndpointRef ref : refs) {
        String k = key(ref);
        Location loc = endpointLocs.get(k);
        if (loc == null) {
          allKnown = false;
          break;
        }
        minPortX = Math.min(minPortX, loc.getX());
        touchesPin |= pinEndpointKeys.contains(k);
      }
      if (allKnown && (touchesPin || firstTrunkX - minPortX > PHYSICAL_REACH)) {
        tunneled.add(net);
      } else {
        physical.add(net);
      }
    }
    nets = physical;

    List<String> portKeys = new ArrayList<>();
    for (NetWire net : nets) {
      for (EndpointRef ref : net.endpoints()) {
        String key = ref.componentId() + "#" + ref.endIndex();
        if (!portKeys.contains(key) && endpointLocs.containsKey(key)) {
          portKeys.add(key);
        }
      }
    }

    // One stub row per port (unique, direction-aware, disjoint for same-x ports).
    Map<String, Integer> stubY = new HashMap<>();
    int downSeq = 0;
    int upSeq = 0;
    for (String key : portKeys) {
      Location loc = endpointLocs.get(key);
      boolean down = prefersDown(endpointLocs, loc);
      int row = down
          ? loc.getY() + STUB_LENGTH + 10 * downSeq++
          : loc.getY() - STUB_LENGTH - 10 * upSeq++;
      while (stubY.containsValue(row) || isNearPortY(endpointLocs, row)
          || sameXStubRangeHits(endpointLocs, stubY, loc.getX(), row)) {
        row += down ? 10 : -10;
      }
      stubY.put(key, row);
    }

    // Tunnels for the long nets, now that the physical stub rows are known
    // (vertical tunnel risers must not terminate on a foreign stub row).
    emitTunnels(mutation, tunneled, endpointLocs, tunnelOffsets, stubY);

    // Stub segments (one per port).
    for (String key : portKeys) {
      Location loc = endpointLocs.get(key);
      add(mutation, loc, Location.create(loc.getX(), stubY.get(key), true));
    }

    // Lane rows start above every stub row so lanes never collide with stubs.
    int maxStub = 0;
    for (int row : stubY.values()) {
      maxStub = Math.max(maxStub, row);
    }
    int laneStartY = Math.max(firstLaneY, maxStub + 50);

    // Pass 1: assign one lane per net and one trunk per (net, port-role).
    int trunkSeq = 0;
    int laneSeq = 0;
    List<NetPlan> plans = new ArrayList<>();
    Map<String, int[]> portTrunkRange = new HashMap<>();
    Set<Integer> usedTrunks = new HashSet<>();
    for (NetWire net : nets) {
      List<EndpointRef> refs = net.endpoints();
      if (refs.size() < 2) {
        continue;
      }
      String firstKey = key(refs.get(0));
      Location anchor = endpointLocs.get(firstKey);
      if (anchor == null) {
        continue;
      }
      int laneY = laneStartY + (laneSeq++) * 10;
      int aTrunk = nextTrunk(endpointLocs, usedTrunks, trunkSeq++, firstTrunkX);
      includeTrunk(portTrunkRange, firstKey, aTrunk, anchor.getX());
      List<int[]> targets = new ArrayList<>();
      for (int i = 1; i < refs.size(); i++) {
        String tKey = key(refs.get(i));
        Location target = endpointLocs.get(tKey);
        if (target == null) {
          continue;
        }
        int tStub = stubY.get(tKey);
        int tTrunk = nextTrunk(endpointLocs, usedTrunks, trunkSeq++, firstTrunkX);
        includeTrunk(portTrunkRange, tKey, tTrunk, target.getX());
        targets.add(new int[] {tTrunk, tStub, target.getX()});
      }
      plans.add(new NetPlan(firstKey, stubY.get(firstKey), aTrunk, laneY, targets));
    }

    // Pass 2: one merged horizontal per port at its stub row, spanning the
    // whole trunk range (both sides of the port). Trunk columns are assigned in
    // net order, so some fan-out targets land left of the driving port; a
    // one-sided segment would leave them disconnected.
    for (Map.Entry<String, int[]> entry : portTrunkRange.entrySet()) {
      Location loc = endpointLocs.get(entry.getKey());
      int sy = stubY.get(entry.getKey());
      int[] range = entry.getValue();
      add(mutation, Location.create(range[0], sy, true),
          Location.create(range[1], sy, true));
    }

    // Pass 3: per-net verticals and lane segments.
    for (NetPlan plan : plans) {
      add(mutation, Location.create(plan.aTrunk, plan.aStub, true),
          Location.create(plan.aTrunk, plan.laneY, true));
      for (int[] t : plan.targets) {
        add(mutation, Location.create(plan.aTrunk, plan.laneY, true),
            Location.create(t[0], plan.laneY, true));
        add(mutation, Location.create(t[0], plan.laneY, true),
            Location.create(t[0], t[1], true));
      }
    }
  }

  /**
   * Connect the given nets with same-label Tunnels. Each endpoint normally
   * gets its tunnel exactly on its port point (zero wires, so the physical
   * nets' WireRepair isolation invariants are untouched); endpoints listed in
   * {@code tunnelOffsets} instead get the tunnel that many pixels above the
   * port, joined by a short vertical riser whose far end must not land on a
   * physical stub row (a wire endpoint on a foreign horizontal would be split
   * into a junction by WireRepair) — candidates walk upward until clear.
   *
   * <p>Labels are short circuit-local ids ("TQ0".."TQzz", base-36) to stay
   * under the layout inspector's tunnel-label-length threshold, and the
   * structure inspector recognises the prefix as the hybrid-router contract.
   */
  private static void emitTunnels(
      CircuitMutation mutation,
      List<NetWire> tunneled,
      Map<String, Location> endpointLocs,
      Map<String, Integer> tunnelOffsets,
      Map<String, Integer> stubY) {
    int seq = 0;
    Set<Location> usedTunnelPoints = new HashSet<>();
    for (NetWire net : tunneled) {
      String label = "TQ" + java.lang.Integer.toString(seq++, 36);
      for (EndpointRef ref : net.endpoints()) {
        Location loc = endpointLocs.get(key(ref));
        if (loc == null) {
          continue;
        }
        Integer offset = tunnelOffsets.get(key(ref));
        Location tunnelLoc = loc;
        if (offset != null && offset != 0) {
          int ty = loc.getY() + offset;
          // Walk clear of physical stub rows (a wire endpoint on a foreign
          // horizontal would be split into a junction), foreign port points,
          // and tunnels already placed — two tunnels on one point short
          // their nets.
          Location candidate = Location.create(loc.getX(), ty, true);
          while (stubY.containsValue(ty) || isPortY(endpointLocs, ty)
              || usedTunnelPoints.contains(candidate)) {
            ty -= 10;
            candidate = Location.create(loc.getX(), ty, true);
          }
          usedTunnelPoints.add(candidate);
          tunnelLoc = candidate;
          add(mutation, loc, tunnelLoc);
        } else {
          usedTunnelPoints.add(loc);
        }
        var attrs = Tunnel.FACTORY.createAttributeSet();
        attrs.setValue(StdAttr.LABEL, label);
        attrs.setValue(StdAttr.FACING, Direction.WEST);
        mutation.add(Tunnel.FACTORY.createComponent(tunnelLoc, attrs));
      }
    }
  }

  /**
   * Merge nets that share any port into a single electrical net. A driving
   * port (FF Q, gate output, input pin) appears in one spec net per fan-out
   * target; unioning restores the router's one-net-per-port contract so each
   * port gets exactly one trunk column. Ports appear in at most one net after
   * this, so Pass 2's merged stub-row horizontal can never short two nets.
   */
  private static List<NetWire> unionNetsBySharedPort(List<NetWire> nets) {
    int n = nets.size();
    if (n <= 1) {
      return nets;
    }
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = i;
    }
    Map<String, Integer> portToNet = new HashMap<>();
    for (int i = 0; i < n; i++) {
      for (EndpointRef ref : nets.get(i).endpoints()) {
        String key = key(ref);
        Integer prev = portToNet.putIfAbsent(key, i);
        if (prev != null) {
          union(parent, prev, i);
        }
      }
    }
    Map<Integer, List<NetWire>> groups = new HashMap<>();
    for (int i = 0; i < n; i++) {
      int root = find(parent, i);
      groups.computeIfAbsent(root, k -> new ArrayList<>()).add(nets.get(i));
    }
    List<NetWire> merged = new ArrayList<>();
    for (List<NetWire> group : groups.values()) {
      if (group.size() == 1) {
        merged.add(group.get(0));
        continue;
      }
      StringBuilder id = new StringBuilder();
      List<EndpointRef> refs = new ArrayList<>();
      Set<String> seen = new HashSet<>();
      for (NetWire net : group) {
        if (id.length() > 0) {
          id.append('+');
        }
        id.append(net.id());
        for (EndpointRef ref : net.endpoints()) {
          String k = key(ref);
          if (seen.add(k)) {
            refs.add(ref);
          }
        }
      }
      merged.add(new NetWire(id.toString(), refs));
    }
    return merged;
  }

  private static int find(int[] parent, int i) {
    while (parent[i] != i) {
      parent[i] = parent[parent[i]];
      i = parent[i];
    }
    return i;
  }

  private static void union(int[] parent, int a, int b) {
    int ra = find(parent, a);
    int rb = find(parent, b);
    if (ra != rb) {
      parent[ra] = rb;
    }
  }

  /** Grow a port's trunk range to cover a trunk column and the port itself. */
  private static void includeTrunk(      Map<String, int[]> portTrunkRange, String key, int trunk, int portX) {
    int[] range = portTrunkRange.get(key);
    if (range == null) {
      portTrunkRange.put(key, new int[] {Math.min(trunk, portX), Math.max(trunk, portX)});
    } else {
      range[0] = Math.min(range[0], Math.min(trunk, portX));
      range[1] = Math.max(range[1], Math.max(trunk, portX));
    }
  }

  /** Next free trunk column: starts from the sequenced base, skips port x and
   *  any trunk already handed out (duplicates would put one net's lane segment
   *  endpoint on another net's vertical, which WireRepair turns into a short). */
  private static int nextTrunk(
      Map<String, Location> locs, Set<Integer> usedTrunks, int seq, int firstTrunkX) {
    int trunk = firstTrunkX + 10 * seq;
    while (isPortX(locs, trunk) || usedTrunks.contains(trunk)) {
      trunk += 10;
    }
    usedTrunks.add(trunk);
    return trunk;
  }

  /**
   * Direction preference: point the stub toward the side with more room.
   * For a same-x port pair this yields "upper up, lower down" (disjoint stub
   * ranges); a crude "flip when a port is below" rule instead made a mid-port
   * stub pierce the port above it.
   */
  private static boolean prefersDown(Map<String, Location> locs, Location loc) {
    int upDist = Integer.MAX_VALUE;
    int downDist = Integer.MAX_VALUE;
    for (Location l : locs.values()) {
      if (l.getX() == loc.getX() && !l.equals(loc)) {
        if (l.getY() < loc.getY()) {
          upDist = Math.min(upDist, loc.getY() - l.getY());
        } else {
          downDist = Math.min(downDist, l.getY() - loc.getY());
        }
      }
    }
    if (upDist == Integer.MAX_VALUE) {
      return false;
    }
    if (downDist == Integer.MAX_VALUE) {
      return true;
    }
    return downDist >= upDist;
  }

  /** True if a stub row would fall inside the stub y-range of a same-x port. */
  private static boolean sameXStubRangeHits(
      Map<String, Location> locs, Map<String, Integer> stubY, int x, int row) {
    for (Map.Entry<String, Integer> e : stubY.entrySet()) {
      Location l = locs.get(e.getKey());
      if (l.getX() == x && l.getY() != row) {
        int lo = Math.min(l.getY(), e.getValue());
        int hi = Math.max(l.getY(), e.getValue());
        if (row > lo && row < hi) {
          return true;
        }
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

  /**
   * A stub row must clear not just foreign port y points but their whole
   * reserved boxes (pin boxes are 28px tall, gate boxes 40px) — a stub-row
   * horizontal grazing a box is a RESERVED_AREA_INTRUSION warning. A 24px
   * keep-out on each side covers both.
   */
  private static boolean isNearPortY(Map<String, Location> locs, int y) {
    for (Location l : locs.values()) {
      if (Math.abs(l.getY() - y) <= 24) {
        return true;
      }
    }
    return false;
  }

  private static boolean hasSameXBelow(Map<String, Location> locs, int x, int y) {
    for (Location l : locs.values()) {
      if (l.getX() == x && l.getY() > y) {
        return true;
      }
    }
    return false;
  }

  private static boolean hasSameXAbove(Map<String, Location> locs, int x, int y) {
    for (Location l : locs.values()) {
      if (l.getX() == x && l.getY() < y) {
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

  private record NetPlan(
      String anchorKey, int aStub, int aTrunk, int laneY, List<int[]> targets) {}
}
