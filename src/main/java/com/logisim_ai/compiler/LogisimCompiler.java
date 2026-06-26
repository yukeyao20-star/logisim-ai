package com.logisim_ai.compiler;

import com.cburch.logisim.Main;
import com.cburch.logisim.circuit.Circuit;
import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.ComponentFactory;
import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.file.Loader;
import com.cburch.logisim.file.LogisimFile;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.proj.Project;
import com.cburch.logisim.std.wiring.Pin;
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
import com.logisim_ai.layout.ExternalPortSpec;
import com.logisim_ai.layout.GridLayoutEngine;
import com.logisim_ai.layout.PlacedComponent;
import com.logisim_ai.layout.PlacedPort;
import com.logisim_ai.layout.ReadableChipLayout;
import com.logisim_ai.validation.CircuitLayoutInspector;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Compiles the project IR into a Logisim-evolution .circ file. */
public final class LogisimCompiler {
  private LogisimCompiler() {}

  /**
   * Compile a single-component circuit (original mode).
   * The component is placed centrally, and external ports surround it.
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
    } finally {
      file.stopAutosaveThread(false);
    }
  }

  /**
   * Compile a multi-component netlist circuit.
   *
   * <p>Steps:
   * 1. Create project and main circuit.
   * 2. Instantiate all components from ComponentSpecs.
   * 3. Run grid layout to assign positions.
   * 4. Create input/output Pins from ExternalPorts.
   * 5. Generate wires from NetSpecs (connecting component endpoints).
   * 6. Save .circ, post-process XML, round-trip check, layout check.
   */
  public static void compileNetlist(CircuitSpec spec, File outputFile) throws Exception {
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
      // Step 2: Instantiate components (at origin initially; layout will position them)
      Map<String, Component> components = instantiateComponentsAt(loader, file, spec, Location.create(0, 0, true));

      // Step 3: Run grid layout
      GridLayoutEngine.LayoutResult layout = GridLayoutEngine.layout(
          spec.components(),
          spec.externalPorts(),
          spec.layoutHints(),
          components);

      // Re-instantiate components at their layout positions
      Map<String, Component> positioned = new HashMap<>();
      for (ComponentSpec cs : spec.components()) {
        Library library = LogisimProjectSetup.addLibrary(loader, file, cs.libraryName());
        ComponentFactory factory = findFactory(library, cs.factoryName());
        var attrs = factory.createAttributeSet();
        setIfPresent(attrs, StdAttr.WIDTH, BitWidth.create(cs.bitWidth()));
        if (cs.label() != null && !cs.label().isBlank()) {
          setIfPresent(attrs, StdAttr.LABEL, cs.label());
        }
        // Find the placed position for this component
        Location loc = layout.components().stream()
            .filter(pc -> pc.id().equals(cs.id()))
            .map(PlacedComponent::location)
            .findFirst()
            .orElse(Location.create(200, 200, true));
        positioned.put(cs.id(), factory.createComponent(loc, attrs));
      }

      // Step 4: Create external port pins
      CircuitMutation mutation = new CircuitMutation(circuit);
      positioned.values().forEach(mutation::add);

      // Map from componentId -> created pin (for net wiring)
      Map<String, Component> pinMap = new HashMap<>();
      for (int i = 0; i < spec.externalPorts().size(); i++) {
        ExternalPort ep = spec.externalPorts().get(i);
        PlacedPort pp = layout.ports().get(i);
        Component pin = createPin(ep, pp);
        mutation.add(pin);
        pinMap.put(ep.id(), pin);

        Component target = positioned.get(ep.targetComponentId());
        if (target == null) {
          throw new IllegalStateException("External port '" + ep.id()
              + "' targets unknown component: " + ep.targetComponentId());
        }
        if (ep.targetEndIndex() >= target.getEnds().size()) {
          throw new IllegalStateException("External port '" + ep.id()
              + "' target endIndex " + ep.targetEndIndex()
              + " out of range for component " + ep.targetComponentId());
        }
        Location pinPort = pin.getEnd(0).getLocation();
        Location targetPort = target.getEnd(ep.targetEndIndex()).getLocation();
        if (ep.direction() == PortDirection.INPUT) {
          connectOrthogonal(mutation, pinPort, targetPort, routeChannel(pp, i));
        } else {
          connectOrthogonal(mutation, targetPort, pinPort, routeChannel(pp, i));
        }
      }

      // Step 5: Generate wires from NetSpecs
      for (NetSpec net : spec.nets()) {
        wireNet(mutation, net, positioned, pinMap);
      }

      mutation.execute();

      // Step 6: Save and verify
      saveAndVerify(loader, file, outputFile);
    } finally {
      file.stopAutosaveThread(false);
    }
  }

  /** Wire all endpoints in a net together using a simple bus approach. */
  private static void wireNet(
      CircuitMutation mutation,
      NetSpec net,
      Map<String, Component> components,
      Map<String, Component> pinMap) {

    List<EndpointRef> refs = net.endpoints();
    if (refs.size() < 2) return;

    // Get all port locations for this net
    java.util.List<Location> portLocs = new java.util.ArrayList<>();
    for (EndpointRef ref : refs) {
      Component comp = components.get(ref.componentId());
      if (comp == null) {
        comp = pinMap.get(ref.componentId());
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
      portLocs.add(comp.getEnd(ref.endIndex()).getLocation());
    }

    // Simple wiring: connect first endpoint to each subsequent one using orthogonal wires
    // Use a channel column between the leftmost and rightmost x coordinates
    Location first = portLocs.get(0);
    for (int i = 1; i < portLocs.size(); i++) {
      Location other = portLocs.get(i);
      // Channel is between the two points
      int channel = (first.getX() + other.getX()) / 2;
      // Snap to grid of 10
      channel = (channel / 10) * 10;
      connectOrthogonal(mutation, first, other, channel);
    }
  }

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
