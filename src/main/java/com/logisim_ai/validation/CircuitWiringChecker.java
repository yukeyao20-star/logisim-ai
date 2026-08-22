package com.logisim_ai.validation;

import com.cburch.logisim.Main;
import com.cburch.logisim.circuit.Circuit;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.EndData;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.file.Loader;
import com.cburch.logisim.file.LogisimFile;
import com.cburch.logisim.proj.Project;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Circuit-integrity checker that loads a generated {@code .circ} with the real
 * Logisim-evolution engine and looks for the two structural causes of official
 * JAR "E" (error) outputs:
 *
 * <ul>
 *   <li><b>floating component inputs</b> — a gate/pin input end with no wire
 *       endpoint on it (drives the output to E);</li>
 *   <li><b>multi-net junction points</b> — a location where &ge;4 wire
 *       endpoints meet, i.e. two wires crossing (WireRepair turns a crossing
 *       into a junction = electrical short between two nets).</li>
 * </ul>
 *
 * <p>This is the static counterpart to the official JAR check: it can be run
 * on every generated file before the (much slower) subprocess simulation, and
 * it pinpointed the aligned-row router bug (stub-row merge horizontals landing
 * inside other pins' stub y-ranges) that produced E in adder_4bit.
 *
 * <p>Usage: {@code java -cp <main>;<jar> com.logisim_ai.validation.CircuitWiringChecker <file.circ>}
 */
public final class CircuitWiringChecker {
  private CircuitWiringChecker() {}

  public static void main(String[] args) throws Exception {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");
    if (args.length < 1) {
      System.err.println("usage: CircuitWiringChecker <file.circ>");
      System.exit(2);
    }
    Loader loader = new Loader(null);
    LogisimFile file = loader.openLogisimFile(new File(args[0]));
    Project project = new Project(file);
    Circuit circuit = file.getMainCircuit();
    project.setCurrentCircuit(circuit);
    check(circuit);
    System.exit(0);
  }

  /** Run integrity checks on a loaded circuit; returns true when clean. */
  public static boolean check(Circuit circuit) {
    Set<Location> wireEnds = new HashSet<>();
    Map<Location, Integer> endCount = new HashMap<>();
    // Connectivity model: every node is a Location where a wire endpoint or a
    // component end sits. Any two endpoints at the SAME Location are the same
    // electrical node (wires, pin-on-port direct connections, and pins that
    // share a location with a gate port all merge).
    Map<Location, List<Location>> adj = new HashMap<>();
    for (Wire w : circuit.getWires()) {
      wireEnds.add(w.getEnd0());
      wireEnds.add(w.getEnd1());
      endCount.merge(w.getEnd0(), 1, Integer::sum);
      endCount.merge(w.getEnd1(), 1, Integer::sum);
      adj.computeIfAbsent(w.getEnd0(), k -> new java.util.ArrayList<>()).add(w.getEnd1());
      adj.computeIfAbsent(w.getEnd1(), k -> new java.util.ArrayList<>()).add(w.getEnd0());
    }
    // Component ends at wire endpoints join the graph; component ends that
    // share a location with another component end (direct pin-on-port, no
    // wire) connect those components too.
    Map<Location, List<String[]>> endInfo = new HashMap<>();
    for (Component comp : circuit.getNonWires()) {
      String name = comp.getFactory().getName();
      for (EndData end : comp.getEnds()) {
        Location loc = end.getLocation();
        String kind = end.isOutput() ? "OUT" : "IN";
        endInfo.computeIfAbsent(loc, k -> new java.util.ArrayList<>())
            .add(new String[] {name, kind});
        if (adj.containsKey(loc)) {
          // wire node exists; no extra edges needed
        }
      }
    }
    // Location-equality edges: every endpoint at the same location connects to
    // every wire node at that location (via a virtual hub).
    boolean clean = true;

    // Multi-net junctions: >=4 wire endpoints at one location = crossing short.
    int junctions = 0;
    for (Map.Entry<Location, Integer> e : endCount.entrySet()) {
      if (e.getValue() >= 4) {
        System.out.println("[JUNCTION] " + e.getKey() + " endpoints=" + e.getValue());
        junctions++;
      }
    }
    if (junctions > 0) {
      clean = false;
    }

    // Floating component inputs: an input end at a location that is neither a
    // wire endpoint nor shared with any other component end (truly isolated).
    int floating = 0;
    Set<Location> sharedComponentLocs = new HashSet<>();
    for (Map.Entry<Location, List<String[]>> e : endInfo.entrySet()) {
      if (e.getValue().size() > 1) {
        sharedComponentLocs.add(e.getKey());
      }
    }
    for (Map.Entry<Location, List<String[]>> e : endInfo.entrySet()) {
      Location loc = e.getKey();
      for (String[] info : e.getValue()) {
        if (info[1].equals("IN") && !wireEnds.contains(loc)
            && !sharedComponentLocs.contains(loc)) {
          System.out.println("[FLOAT] " + info[0] + " @ " + loc);
          floating++;
        }
      }
    }
    if (floating > 0) {
      clean = false;
    }

    // Two-driver nets: a connected node group with >=2 OUTPUT ends. Node
    // grouping: BFS over wire adjacency, where every location with component
    // ends is a node and wire endpoints at the same location merge.
    int twoDriver = 0;
    // Build node -> wire-neighbor edges and node -> same-location component ends.
    Set<Location> nodes = new HashSet<>(adj.keySet());
    nodes.addAll(endInfo.keySet());
    Map<Location, List<String>> nodeOutputs = new HashMap<>();
    for (Map.Entry<Location, List<String[]>> e : endInfo.entrySet()) {
      for (String[] info : e.getValue()) {
        if (info[1].equals("OUT")) {
          nodeOutputs.computeIfAbsent(e.getKey(), k -> new java.util.ArrayList<>())
              .add(info[0] + "@" + e.getKey());
        }
      }
    }
    Set<Location> visited = new HashSet<>();
    for (Location start : nodes) {
      if (visited.contains(start)) {
        continue;
      }
      java.util.ArrayDeque<Location> queue = new java.util.ArrayDeque<>();
      queue.add(start);
      visited.add(start);
      java.util.List<String> outputs = new java.util.ArrayList<>(nodeOutputs.getOrDefault(start, List.of()));
      while (!queue.isEmpty()) {
        Location loc = queue.poll();
        for (Location n : adj.getOrDefault(loc, List.of())) {
          if (!visited.contains(n)) {
            visited.add(n);
            outputs.addAll(nodeOutputs.getOrDefault(n, List.of()));
            queue.add(n);
          }
        }
      }
      if (outputs.size() >= 2) {
        twoDriver++;
        System.out.println("[2-DRIVER] net outputs: " + String.join(" ; ", outputs));
      }
    }
    if (twoDriver > 0) {
      clean = false;
    }

    System.out.println("[wiring] clean=" + clean + " junctions=" + junctions
        + " floatingInputs=" + floating + " twoDriverNets=" + twoDriver
        + " wires=" + circuit.getWires().size()
        + " comps=" + circuit.getNonWires().size());
    return clean;
  }

  /**
   * Spec↔circuit connectivity audit: for each intended net in the spec, every
   * endpoint must land in ONE connected wire component, and no endpoint of a
   * DIFFERENT spec net may join it. This catches the router's silent fan-out
   * cross-connection defect (junction cascades merging nets) that the
   * junction/floating/two-driver heuristics all miss — the class that broke
   * the gray-code counter while every static check passed.
   *
   * <p>Endpoint resolution: spec component ids resolve to .circ components by
   * (factory name, label); external-port ids resolve to Pins/Clocks by label.
   * Components/nets that cannot be resolved are skipped (best effort).
   *
   * @return human-readable issues; empty when the audit is clean
   */
  public static List<String> auditAgainstSpec(
      com.logisim_ai.ir.CircuitSpec spec, Circuit circuit) {
    List<String> issues = new ArrayList<>();
    // .circ components by (factory, label)
    Map<String, Component> byLabel = new HashMap<>();
    Map<String, Integer> labelCount = new HashMap<>();
    for (Component c : circuit.getNonWires()) {
      String name = c.getFactory().getName();
      String label = c.getAttributeSet().getValue(com.cburch.logisim.instance.StdAttr.LABEL);
      if (label == null) {
        continue;
      }
      String key = name + "|" + label;
      labelCount.merge(key, 1, Integer::sum);
      byLabel.put(key, c);
    }
    // external port ids -> (factory name, label)
    Map<String, String[]> portKeys = new HashMap<>();
    for (com.logisim_ai.ir.ExternalPort p : spec.externalPorts()) {
      String factory = p.clock() ? "Clock" : "Pin";
      portKeys.put(p.id(), new String[] {factory, p.label()});
    }
    // component ids -> (factory, label); spec-side label counts. Logisim keeps
    // only ONE copy of a duplicated label across components (the second gate's
    // label is dropped on save), so a label that names 2+ spec components is
    // ambiguous even if the .circ has one — resolve neither.
    Map<String, String[]> compKeys = new HashMap<>();
    Map<String, Integer> specLabelCount = new HashMap<>();
    for (com.logisim_ai.ir.ComponentSpec cs : spec.components()) {
      if (cs.label() != null && !cs.label().isBlank()) {
        compKeys.put(cs.id(), new String[] {cs.factoryName(), cs.label()});
        specLabelCount.merge(cs.factoryName() + "|" + cs.label(), 1, Integer::sum);
      }
    }
    // wire adjacency
    Map<Location, List<Location>> adj = new HashMap<>();
    for (Wire w : circuit.getWires()) {
      adj.computeIfAbsent(w.getEnd0(), k -> new ArrayList<>()).add(w.getEnd1());
      adj.computeIfAbsent(w.getEnd1(), k -> new ArrayList<>()).add(w.getEnd0());
    }
    // intended net id -> endpoint locations; output-endpoint locations -> owner
    Map<String, List<Location>> netEndpoints = new HashMap<>();
    Map<Location, String> outputOwner = new HashMap<>();
    // An input pin's circuit-side end is a legitimate SHARED node: the pin's
    // driver net and every fan-out net all land in one wire component. Nets
    // that share a pin-end location form a "pin cluster"; a foreign driver is
    // only a real cross-connection when its net is OUTSIDE the cluster.
    Map<Location, Set<String>> pinClusters = new HashMap<>(); // pinLoc -> net ids
    for (com.logisim_ai.ir.NetSpec net : spec.nets()) {
      List<Location> locs = new ArrayList<>();
      for (com.logisim_ai.ir.EndpointRef ref : net.endpoints()) {
        String[] key = compKeys.get(ref.componentId());
        boolean isComponent = key != null;
        if (!isComponent) {
          key = portKeys.get(ref.componentId());
        }
        if (key == null) {
          continue;
        }
        String k = key[0] + "|" + key[1];
        if (isComponent && specLabelCount.getOrDefault(k, 0) != 1) {
          continue; // label names 0 or 2+ spec components: cannot resolve uniquely
        }
        if (labelCount.getOrDefault(k, 0) != 1) {
          continue; // .circ label missing or duplicated: cannot verify
        }
        Component comp = byLabel.get(k);
        if (comp == null || ref.endIndex() >= comp.getEnds().size()) {
          continue;
        }
        Location loc = comp.getEnd(ref.endIndex()).getLocation();
        locs.add(loc);
        // Any pin/clock end (input OR output) is a legitimate shared node:
        // its driver net and fan-out nets all land in one wire component.
        String cn = comp.getFactory().getName();
        if (cn.equals("Pin") || cn.equals("Clock")) {
          pinClusters.computeIfAbsent(loc, kk -> new HashSet<>()).add(net.id());
        }
        if (comp.getEnd(ref.endIndex()).isOutput()) {
          outputOwner.putIfAbsent(loc, net.id());
        }
      }
      if (!locs.isEmpty()) {
        netEndpoints.put(net.id(), locs);
      }
    }
    // union-find over pin-cluster memberships
    Map<String, String> parent = new HashMap<>();
    for (Set<String> members : pinClusters.values()) {
      String root = null;
      for (String m : members) {
        if (root == null) {
          root = m;
          parent.putIfAbsent(m, m);
        } else {
          parent.putIfAbsent(m, m);
          // union m -> root
          String r2 = find(parent, m);
          parent.put(r2, root);
        }
      }
    }
    // BFS each net from its first endpoint; check containment + foreign driver
    for (Map.Entry<String, List<Location>> e : netEndpoints.entrySet()) {
      String netId = e.getKey();
      List<Location> targets = e.getValue();
      Set<Location> visited = new HashSet<>();
      java.util.ArrayDeque<Location> queue = new java.util.ArrayDeque<>();
      queue.add(targets.get(0));
      visited.add(targets.get(0));
      while (!queue.isEmpty()) {
        Location cur = queue.poll();
        for (Location n : adj.getOrDefault(cur, List.of())) {
          if (!visited.add(n)) {
            continue;
          }
          queue.add(n);
        }
      }
      for (Location t : targets) {
        if (!visited.contains(t)) {
          issues.add("[SPEC-NET] " + netId + " endpoint " + t + " not connected to "
              + targets.get(0));
        }
      }
      for (Location v : visited) {
        String owner = outputOwner.get(v);
        if (owner != null && !targets.contains(v)
            && !sameCluster(parent, netId, owner)) {
          issues.add("[SPEC-NET] " + netId + " contains foreign driver of net " + owner
              + " at " + v + " (router cross-connection)");
        }
      }
    }
    return issues;
  }

  private static String find(Map<String, String> parent, String x) {
    String p = parent.get(x);
    if (p == null) {
      return x;
    }
    if (!p.equals(x)) {
      parent.put(x, find(parent, p));
    }
    return parent.get(x);
  }

  private static boolean sameCluster(Map<String, String> parent, String a, String b) {
    return find(parent, a).equals(find(parent, b));
  }
}
