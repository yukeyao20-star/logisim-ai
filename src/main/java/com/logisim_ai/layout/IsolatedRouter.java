package com.logisim_ai.layout;

import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.data.Location;
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
 */
public final class IsolatedRouter {
  private IsolatedRouter() {}

  private static final int STUB_LENGTH = 40;
  private static final int ROUTING_MARGIN = 300;

  public record EndpointRef(String componentId, int endIndex) {}

  public record NetWire(String id, List<EndpointRef> endpoints) {}

  public static void wire(
      CircuitMutation mutation,
      List<NetWire> nets,
      Map<String, Location> endpointLocs) {
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
      while (stubY.containsValue(row) || isPortY(endpointLocs, row)
          || sameXStubRangeHits(endpointLocs, stubY, loc.getX(), row)) {
        row += down ? 10 : -10;
      }
      stubY.put(key, row);
    }

    // Stub segments (one per port).
    int stubCount = 0;
    for (String key : portKeys) {
      Location loc = endpointLocs.get(key);
      if (loc.getX() == 1180 || key.contains("n_gsh") || key.contains("z0")
          || key.contains("enc1#9")) {
        System.err.println("[stub-dbg] " + key + " loc=" + loc + " row=" + stubY.get(key));
      }
      add(mutation, loc, Location.create(loc.getX(), stubY.get(key), true));
      stubCount++;
    }
    System.err.println("[router] ports=" + portKeys.size() + " stubs=" + stubCount);

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
