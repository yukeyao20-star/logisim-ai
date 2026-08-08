package com.logisim_ai.compiler;

import com.cburch.logisim.Main;
import com.cburch.logisim.circuit.Circuit;
import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.ComponentFactory;
import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.file.Loader;
import com.cburch.logisim.file.LogisimFile;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.proj.Project;
import com.cburch.logisim.std.wiring.Pin;
import com.cburch.logisim.std.wiring.Tunnel;
import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;
import com.logisim_ai.experiment.LogisimProjectSetup;
import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.AutoLayoutPolicy;
import com.logisim_ai.layout.ExternalPortSpec;
import com.logisim_ai.layout.FootprintPolicy;
import com.logisim_ai.layout.GridLayoutEngine;
import com.logisim_ai.layout.PlacedComponent;
import com.logisim_ai.layout.PlacedPort;
import com.logisim_ai.layout.PortSide;
import com.logisim_ai.layout.ReadableChipLayout;
import com.logisim_ai.validation.CircuitArtifactManifest;
import com.logisim_ai.validation.CircuitLayoutInspector;
import com.logisim_ai.validation.CircuitSemanticValidator;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compiles the project IR into a Logisim-evolution .circ file.
 *
 * <p>Two compilation modes:
 * <ul>
 *   <li>{@link #compileSingleComponent}: direct wiring (safe for single chips)</li>
 *   <li>{@link #compileNetlist}: Tunnel-based routing (safe for multi-component circuits)</li>
 * </ul>
 *
 * <p>In netlist mode, all connections are made via Tunnel components:
 * - Each NetSpec creates a set of Tunnels with the same label (= net.id())
 * - Each endpoint gets a Tunnel placed nearby, with a short local wire
 * - No long physical wires cross the canvas
 * - Different nets never share physical connection points
 */
public final class LogisimCompiler {
  private LogisimCompiler() {}

  /**
   * Compile a single-component circuit (original mode).
   * The component is placed centrally, and external ports surround it.
   * Direct orthogonal wiring is used (safe for single chip).
   */
  public static void compileSingleComponent(CircuitSpec spec, File outputFile) throws Exception {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");

    Loader loader = new Loader(null);
    LogisimFile file = LogisimFile.createNew(loader, null);
    LogisimProjectSetup.configureGuiDefaults(loader, file);
    Project project = new Project(file);
    Circuit circuit = new Circuit(spec.name(), file, project);
    file.addCircuit(circuit);
    file.setMainCircuit(circuit);

    try {
      ReadableChipLayout layout = new ReadableChipLayout(Location.create(460, 250, true));
      Map<String, Component> components = instantiateComponentsAt(loader, file, spec, layout.chipLocation());
      List<PlacedPort> placedPorts = layout.placePorts(spec.externalPorts().stream()
          .map(p -> new ExternalPortSpec(p.label(), BitWidth.create(p.bitWidth()), p.side()))
          .toList());

      CircuitMutation mutation = new CircuitMutation(circuit);
      components.values().forEach(mutation::add);
      for (int i = 0; i < spec.externalPorts().size(); i++) {
        ExternalPort external = spec.externalPorts().get(i);
        PlacedPort placed = placedPorts.get(i);
        Component pin = createPin(external, placed);
        mutation.add(pin);
        Component target = components.get(external.targetComponentId());
        if (target == null) {
          throw new IllegalStateException("Missing target component: " + external.targetComponentId());
        }
        Location pinPort = pin.getEnd(0).getLocation();
        Location targetPort = target.getEnd(external.targetEndIndex()).getLocation();
        if (external.direction() == PortDirection.INPUT) {
          connectOrthogonal(mutation, pinPort, targetPort, routeChannel(placed, i));
        } else {
          connectOrthogonal(mutation, targetPort, pinPort, routeChannel(placed, i));
        }
      }
      mutation.execute();

      saveAndVerify(loader, file, outputFile);
      CircuitArtifactManifest.writeReports(spec, outputFile);
    } finally {
      file.stopAutosaveThread(false);
    }
  }

  /**
   * Compile a multi-component netlist circuit using Tunnel routing.
   *
   * <p>Steps:
   * 1. Create project and main circuit.
   * 2. Compute logic component positions via GridLayoutEngine.
   * 3. Instantiate components at their computed positions.
   * 4. Compute port pin positions aligned to component endpoints.
   * 5. Create external port Pins (no auto-wiring).
   * 6. Wire all nets via Tunnels: each net.id() becomes a Tunnel label,
   *    each endpoint gets a nearby Tunnel with a short local wire.
   * 7. Runtime validation + save + verify.
   */
  public static void compileNetlist(CircuitSpec spec, File outputFile) throws Exception {
    compileNetlist(spec, outputFile, List.of());
  }

  public static void compileNetlistForTesting(
      CircuitSpec spec,
      File outputFile,
      List<FootprintPolicy.ReservedArea> extraReservedAreas) throws Exception {
    compileNetlist(spec, outputFile, extraReservedAreas);
  }

  static void compileNetlist(
      CircuitSpec spec,
      File outputFile,
      List<FootprintPolicy.ReservedArea> extraReservedAreas) throws Exception {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");

    Loader loader = new Loader(null);
    LogisimFile file = LogisimFile.createNew(loader, null);
    LogisimProjectSetup.configureGuiDefaults(loader, file);
    Project project = new Project(file);
    Circuit circuit = new Circuit(spec.name(), file, project);
    file.addCircuit(circuit);
    file.setMainCircuit(circuit);

    // Register Wiring library for Tunnels
    Library wiringLib = LogisimProjectSetup.addLibrary(loader, file, "Wiring");
    ComponentFactory tunnelFactory = findFactory(wiringLib, "Tunnel");

    try {
      // Phase 1: Compute logic component positions
      GridLayoutEngine.ComponentLayoutResult compLayout =
          GridLayoutEngine.layoutComponents(spec.components(), spec.layoutHints());

      // Phase 2: Instantiate components at their computed positions
      Map<String, Component> positioned = new HashMap<>();
      List<FootprintPolicy.ReservedArea> reservedAreas = new ArrayList<>(extraReservedAreas);
      for (ComponentSpec cs : spec.components()) {
        Library library = LogisimProjectSetup.addLibrary(loader, file, cs.libraryName());
        ComponentFactory factory = findFactory(library, cs.factoryName());
        var attrs = factory.createAttributeSet();
        setIfPresent(attrs, StdAttr.WIDTH, BitWidth.create(cs.bitWidth()));
        if (cs.label() != null && !cs.label().isBlank()) {
          setIfPresent(attrs, StdAttr.LABEL, cs.label());
        }
        Location loc = compLayout.components().stream()
            .filter(pc -> pc.id().equals(cs.id()))
            .map(PlacedComponent::location)
            .findFirst()
            .orElse(Location.create(200, 200, true));
        positioned.put(cs.id(), factory.createComponent(loc, attrs));
        addReservedArea(reservedAreas, cs.factoryName(), cs.label(), loc);
      }

      // Phase 3: Compute port pin positions aligned to component endpoints
      int outputPinX = compLayout.totalWidth() - 100;
      GridLayoutEngine.PortLayoutResult portLayout =
          GridLayoutEngine.layoutPorts(spec.externalPorts(), positioned, outputPinX);

      // Phase 4: Create circuit - components + pins (no auto-wiring)
      CircuitMutation mutation = new CircuitMutation(circuit);
      positioned.values().forEach(mutation::add);

      // Create external port Pins only - NO auto-wiring to targets
      Map<String, Component> pinMap = new HashMap<>();
      for (int i = 0; i < spec.externalPorts().size(); i++) {
        ExternalPort ep = spec.externalPorts().get(i);
        PlacedPort pp = portLayout.ports().get(i);
        Component pin = createPin(ep, pp);
        mutation.add(pin);
        pinMap.put(ep.id(), pin);
        addReservedArea(reservedAreas, "Pin", ep.label(), pp.pinLocation());
      }

      // Runtime validation
      CircuitSemanticValidator.requireValidRuntime(spec, positioned, pinMap);

      // Phase 5: Wire all nets using Tunnels
      int netLabelSeq = 1;
      for (NetSpec net : spec.nets()) {
        String label = "n" + netLabelSeq++;
        wireNetWithTunnels(
            mutation, net, positioned, pinMap, spec.externalPorts(), tunnelFactory, label, reservedAreas);
      }

      mutation.execute();

      // Save and verify
      saveAndVerify(loader, file, outputFile);
      CircuitArtifactManifest.writeReports(spec, outputFile);
    } finally {
      file.stopAutosaveThread(false);
    }
  }

  /**
   * Wire all endpoints in a net together using Tunnel components.
   *
   * <p>Strategy:
   * - Create Tunnels beside every endpoint, including external Pins
   * - Draw only short local wires from each endpoint into its own Tunnel
   * - All Tunnels with the same label are logically connected by Logisim
   * - No long boundary-to-internal wires cross unrelated input rows
   *
   * <p>Tunnel placement is delegated to {@link AutoLayoutPolicy}. Component
   * tunnels keep fan-out readable, while boundary Pins connect to their net by
   * a short local segment. This avoids accidental physical shorts at wire
   * crossings.
   */
  private static void wireNetWithTunnels(
      CircuitMutation mutation,
      NetSpec net,
      Map<String, Component> components,
      Map<String, Component> pinMap,
      List<ExternalPort> externalPorts,
      ComponentFactory tunnelFactory,
      String label,
      List<FootprintPolicy.ReservedArea> reservedAreas) {

    List<EndpointRef> refs = net.endpoints();
    if (refs.size() < 2) return;

    List<NetEndpoint> endpoints = new ArrayList<>();
    for (EndpointRef ref : refs) {
      Component comp = components.get(ref.componentId());
      boolean isPin = false;
      if (comp == null) {
        comp = pinMap.get(ref.componentId());
        isPin = true;
      }
      if (comp == null) {
        throw new IllegalStateException(
            "Net '" + net.id() + "' references unknown component: " + ref.componentId());
      }
      if (ref.endIndex() >= comp.getEnds().size()) {
        throw new IllegalStateException(
            "Net '" + net.id() + "' endIndex " + ref.endIndex()
                + " out of range for component " + ref.componentId()
                + " (has " + comp.getEnds().size() + " ends)");
      }

      Location endpointLoc = comp.getEnd(ref.endIndex()).getLocation();
      endpoints.add(new NetEndpoint(ref, isPin, endpointLoc));
    }

    for (NetEndpoint endpoint : endpoints) {
      AutoLayoutPolicy.TunnelPlacement placement = endpoint.isPin()
          ? AutoLayoutPolicy.tunnelForExternalPin(
              endpoint.location(),
              externalPortSide(endpoint.ref().componentId(), externalPorts))
          : AutoLayoutPolicy.tunnelForComponentEnd(endpoint.location(), endpoint.ref().endIndex());
      Component tunnel = createTunnel(tunnelFactory, label, placement.location(), placement.facing());
      mutation.add(tunnel);

      Location tunnelPort = tunnel.getEnd(0).getLocation();
      List<FootprintPolicy.ReservedArea> routeAreas = new ArrayList<>(reservedAreas);
      addReservedArea(routeAreas, "Tunnel", label, placement.location());
      connectShortLocal(mutation, endpoint.location(), tunnelPort, routeAreas);
      addReservedArea(reservedAreas, "Tunnel", label, placement.location());
    }
  }

  private static PortSide externalPortSide(String pinId, List<ExternalPort> externalPorts) {
    ExternalPort ep = findExternalPort(externalPorts, pinId);
    return ep == null ? PortSide.LEFT : ep.side();
  }

  private static Location nearestTunnelPort(Location pin, List<TunnelEndpoint> tunnels) {
    TunnelEndpoint best = null;
    int bestScore = Integer.MAX_VALUE;
    for (TunnelEndpoint tunnel : tunnels) {
      Location port = tunnel.portLocation();
      int sameRowPenalty = port.getY() == pin.getY() ? 0 : 10_000;
      int score = sameRowPenalty + Math.abs(port.getX() - pin.getX()) + Math.abs(port.getY() - pin.getY());
      if (score < bestScore) {
        best = tunnel;
        bestScore = score;
      }
    }
    return best.portLocation();
  }

  private static void wirePinOnlyNet(CircuitMutation mutation, List<NetEndpoint> endpoints) {
    Location anchor = endpoints.get(0).location();
    for (int i = 1; i < endpoints.size(); i++) {
      connectOrthogonal(mutation, anchor, endpoints.get(i).location(), anchor.getX() + i * 40);
    }
  }

  private static int routeChannelForPin(NetEndpoint endpoint, List<ExternalPort> externalPorts) {
    ExternalPort ep = findExternalPort(externalPorts, endpoint.ref().componentId());
    PortSide side = ep == null ? PortSide.LEFT : ep.side();
    return switch (side) {
      case LEFT -> AutoLayoutPolicy.snap(endpoint.location().getX() + 160);
      case RIGHT -> AutoLayoutPolicy.snap(endpoint.location().getX() - 160);
      case TOP, BOTTOM -> AutoLayoutPolicy.snap(endpoint.location().getX());
    };
  }

  private static void connectShortLocal(
      CircuitMutation mutation,
      Location endpointLoc,
      Location tunnelPort,
      List<FootprintPolicy.ReservedArea> reservedAreas) {
    for (AutoLayoutPolicy.WireSegment segment
        : AutoLayoutPolicy.planLocalTunnelWireAvoidingAreas(endpointLoc, tunnelPort, reservedAreas)) {
      mutation.add(Wire.create(segment.from(), segment.to()));
    }
  }

  private static void addReservedArea(
      List<FootprintPolicy.ReservedArea> reservedAreas,
      String componentName,
      String label,
      Location location) {
    FootprintPolicy.ReservedArea area =
        FootprintPolicy.reservedAreaForComponent(componentName, label == null ? "" : label, location);
    if (area != null) {
      reservedAreas.add(area);
    }
  }

  private static ExternalPort findExternalPort(List<ExternalPort> externalPorts, String pinId) {
    return externalPorts.stream()
        .filter(port -> port.id().equals(pinId))
        .findFirst()
        .orElse(null);
  }

  /** Create a Tunnel component. */
  private static Component createTunnel(
      ComponentFactory tunnelFactory, String label, Location loc, Direction facing) {
    var attrs = tunnelFactory.createAttributeSet();
    attrs.setValue(StdAttr.LABEL, label);
    attrs.setValue(StdAttr.FACING, facing);
    return tunnelFactory.createComponent(loc, attrs);
  }

  private record NetEndpoint(EndpointRef ref, boolean isPin, Location location) {}

  private record TunnelEndpoint(Location portLocation) {}

  // ---- Shared helpers ----

  private static Map<String, Component> instantiateComponentsAt(
      Loader loader, LogisimFile file, CircuitSpec spec, Location location) {
    Map<String, Component> components = new HashMap<>();
    for (ComponentSpec componentSpec : spec.components()) {
      Library library = LogisimProjectSetup.addLibrary(loader, file, componentSpec.libraryName());
      ComponentFactory factory = findFactory(library, componentSpec.factoryName());
      var attrs = factory.createAttributeSet();
      setIfPresent(attrs, StdAttr.WIDTH, BitWidth.create(componentSpec.bitWidth()));
      if (componentSpec.label() != null && !componentSpec.label().isBlank()) {
        setIfPresent(attrs, StdAttr.LABEL, componentSpec.label());
      }
      components.put(componentSpec.id(), factory.createComponent(location, attrs));
    }
    return components;
  }

  private static <T> void setIfPresent(
      com.cburch.logisim.data.AttributeSet attrs, Attribute<T> attribute, T value) {
    if (attrs.getAttribute(attribute.getName()) != null) {
      attrs.setValue(attribute, value);
    }
  }

  private static Component createPin(ExternalPort external, PlacedPort placed) {
    var attrs = Pin.FACTORY.createAttributeSet();
    if (external.direction() == PortDirection.OUTPUT) {
      attrs.setValue(Pin.ATTR_TYPE, Pin.OUTPUT);
    }
    attrs.setValue(StdAttr.LABEL, placed.label());
    attrs.setValue(StdAttr.FACING, placed.pinFacing());
    attrs.setValue(StdAttr.WIDTH, placed.width());
    return Pin.FACTORY.createComponent(placed.pinLocation(), attrs);
  }

  private static ComponentFactory findFactory(Library library, String name) {
    for (Tool tool : library.getTools()) {
      if (tool instanceof AddTool addTool && name.equals(addTool.getFactory().getName())) {
        return addTool.getFactory();
      }
    }
    throw new IllegalStateException("Missing factory " + name + " in " + library.getName());
  }

  private static int routeChannel(PlacedPort placed, int index) {
    return switch (placed.side()) {
      case LEFT -> 310 + index * 10;
      case RIGHT -> 620 + index * 10;
      case TOP, BOTTOM -> 380 + index * 10;
    };
  }

  static void connectOrthogonal(CircuitMutation mutation, Location from, Location to, int channel) {
    if (from.equals(to)) {
      return;
    }
    if (from.getX() == to.getX() || from.getY() == to.getY()) {
      mutation.add(Wire.create(from, to));
      return;
    }
    Location first = Location.create(channel, from.getY(), true);
    Location second = Location.create(channel, to.getY(), true);
    mutation.add(Wire.create(from, first));
    mutation.add(Wire.create(first, second));
    mutation.add(Wire.create(second, to));
  }
  private static void saveAndVerify(Loader loader, LogisimFile file, File outputFile) throws Exception {
    File parent = outputFile.getParentFile();
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }
    if (!loader.save(file, outputFile)) {
      throw new IllegalStateException("Loader.save returned false");
    }
    LogisimProjectSetup.patchGeneratedProjectXml(outputFile);
    roundTrip(outputFile);
    CircuitLayoutInspector.requireClean(outputFile);
  }

  private static void roundTrip(File file) throws Exception {
    Loader loader = new Loader(null);
    LogisimFile loaded = loader.openLogisimFile(file);
    try {
      if (loaded == null || loaded.getMainCircuit() == null) {
        throw new IllegalStateException("Round-trip load failed: " + file);
      }
    } finally {
      if (loaded != null) {
        loaded.stopAutosaveThread(false);
      }
    }
  }
}
