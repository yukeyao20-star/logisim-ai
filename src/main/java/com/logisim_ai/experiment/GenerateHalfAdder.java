package com.logisim_ai.experiment;

import com.cburch.logisim.Main;
import com.cburch.logisim.circuit.Circuit;
import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.ComponentFactory;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.file.Loader;
import com.cburch.logisim.file.LogisimFile;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.wiring.Pin;
import com.cburch.logisim.std.wiring.Tunnel;
import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;
import java.io.File;
import java.nio.file.Files;

public final class GenerateHalfAdder {
  private GenerateHalfAdder() {}

  public static void main(String[] args) {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");

    GenerationContext context = null;
    try {
      context = createEmptyProject();
      var gates = registerBuiltin(context, "Gates");
      var wiring = registerBuiltin(context, "Wiring");

      var xorFactory = findFactory(gates, "XOR Gate");
      var andFactory = findFactory(gates, "AND Gate");
      var tunnelFactory = findFactory(wiring, "Tunnel");

      Component pinA = inputPin(50, 80, "A");
      Component pinB = inputPin(50, 140, "B");
      Component xor = gate(xorFactory, 220, 100);
      Component and = gate(andFactory, 230, 200);
      Component sum = outputPin(380, 100, "SUM");
      Component carry = outputPin(380, 200, "CARRY");

      Component aSource = tunnel(tunnelFactory, 110, 80, "A", Direction.WEST);
      Component bSource = tunnel(tunnelFactory, 110, 140, "B", Direction.WEST);
      Component aToXor = tunnel(tunnelFactory, 150, 80, "A", Direction.EAST);
      Component bToXor = tunnel(tunnelFactory, 150, 120, "B", Direction.EAST);
      Component aToAnd = tunnel(tunnelFactory, 160, 180, "A", Direction.EAST);
      Component bToAnd = tunnel(tunnelFactory, 160, 220, "B", Direction.EAST);

      addComponents(context.circuit(), pinA, pinB, xor, and, sum, carry,
          aSource, bSource, aToXor, bToXor, aToAnd, bToAnd);

      CircuitMutation wires = new CircuitMutation(context.circuit());
      wire(wires, port(pinA), port(aSource));
      wire(wires, port(pinB), port(bSource));
      wire(wires, port(aToXor), input(xor, 1));
      wire(wires, port(bToXor), input(xor, 2));
      wire(wires, port(aToAnd), input(and, 1));
      wire(wires, port(bToAnd), input(and, 2));
      wire(wires, output(xor), port(sum));
      wire(wires, output(and), port(carry));
      wires.execute();

      File outputFile = new File("generated/circ/half_adder.circ");
      save(context, outputFile);
      verifyText(outputFile, "XOR Gate", "AND Gate", "Pin", "Tunnel");
      roundTrip(outputFile, 12, 8);
      System.out.println("[OK] half_adder.circ generated");
    } catch (Exception e) {
      throw new RuntimeException("Half adder generation failed", e);
    } finally {
      if (context != null) {
        context.file().stopAutosaveThread(false);
      }
    }
  }

  private static GenerationContext createEmptyProject() {
    Loader loader = new Loader(null);
    LogisimFile file = LogisimFile.createNew(loader, null);
    LogisimProjectSetup.configureGuiDefaults(loader, file);
    return new GenerationContext(loader, file, file.getMainCircuit());
  }

  private static Library registerBuiltin(GenerationContext context, String name) {
    return LogisimProjectSetup.addLibrary(context.loader(), context.file(), name);
  }

  private static ComponentFactory findFactory(Library library, String name) {
    for (Tool tool : library.getTools()) {
      if (tool instanceof AddTool addTool && name.equals(addTool.getFactory().getName())) {
        return addTool.getFactory();
      }
    }
    throw new IllegalStateException("Missing factory: " + name);
  }

  private static Component inputPin(int x, int y, String label) {
    var attrs = Pin.FACTORY.createAttributeSet();
    attrs.setValue(StdAttr.LABEL, label);
    attrs.setValue(StdAttr.FACING, Direction.EAST);
    return Pin.FACTORY.createComponent(Location.create(x, y, true), attrs);
  }

  private static Component outputPin(int x, int y, String label) {
    var attrs = Pin.FACTORY.createAttributeSet();
    attrs.setValue(Pin.ATTR_TYPE, Pin.OUTPUT);
    attrs.setValue(StdAttr.LABEL, label);
    attrs.setValue(StdAttr.FACING, Direction.WEST);
    return Pin.FACTORY.createComponent(Location.create(x, y, true), attrs);
  }

  private static Component gate(ComponentFactory factory, int x, int y) {
    return factory.createComponent(Location.create(x, y, true), factory.createAttributeSet());
  }

  private static Component tunnel(ComponentFactory factory, int x, int y, String label, Direction facing) {
    var attrs = factory.createAttributeSet();
    attrs.setValue(StdAttr.LABEL, label);
    attrs.setValue(StdAttr.FACING, facing);
    return factory.createComponent(Location.create(x, y, true), attrs);
  }

  private static void addComponents(Circuit circuit, Component... components) {
    CircuitMutation mutation = new CircuitMutation(circuit);
    for (Component component : components) {
      mutation.add(component);
    }
    mutation.execute();
  }

  private static void wire(CircuitMutation mutation, Location from, Location to) {
    if (!from.equals(to)) {
      mutation.add(Wire.create(from, to));
    }
  }

  private static Location port(Component component) {
    return component.getEnd(0).getLocation();
  }

  private static Location output(Component gate) {
    return gate.getEnd(0).getLocation();
  }

  private static Location input(Component gate, int index) {
    return gate.getEnd(index).getLocation();
  }

  private static void save(GenerationContext context, File outputFile) {
    File parent = outputFile.getParentFile();
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }
    if (!context.loader().save(context.file(), outputFile)) {
      throw new IllegalStateException("Loader.save returned false");
    }
    try {
      LogisimProjectSetup.patchV38Compat(outputFile);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to patch v3.8 toolbar", e);
    }
  }

  private static void verifyText(File file, String... names) throws Exception {
    String content = Files.readString(file.toPath());
    for (String name : names) {
      if (!content.contains("name=\"" + name + "\"")) {
        throw new IllegalStateException("Missing component in circ: " + name);
      }
    }
  }

  private static void roundTrip(File file, int minComponents, int minWires) throws Exception {
    Loader loader = new Loader(null);
    LogisimFile loaded = loader.openLogisimFile(file);
    try {
      if (loaded == null || loaded.getMainCircuit() == null) {
        throw new IllegalStateException("Round-trip load failed");
      }
      if (loaded.getMainCircuit().getNonWires().size() < minComponents) {
        throw new IllegalStateException("Round-trip component count too small");
      }
      if (loaded.getMainCircuit().getWires().size() < minWires) {
        throw new IllegalStateException("Round-trip wire count too small");
      }
    } finally {
      if (loaded != null) {
        loaded.stopAutosaveThread(false);
      }
    }
  }

  private record GenerationContext(Loader loader, LogisimFile file, Circuit circuit) {}
}
