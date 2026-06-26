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

public final class GenerateTtl74151Macro {
  private static final String[] DATA = {"D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7"};

  private GenerateTtl74151Macro() {}

  public static void main(String[] args) {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");

    GenerationContext context = null;
    try {
      context = createEmptyProject();
      var gates = registerBuiltin(context, "Gates");
      var wiring = registerBuiltin(context, "Wiring");

      var notFactory = findFactory(gates, "NOT Gate");
      var andFactory = findFactory(gates, "AND Gate");
      var orFactory = findFactory(gates, "OR Gate");
      var tunnelFactory = findFactory(wiring, "Tunnel");

      CircuitMutation add = new CircuitMutation(context.circuit());

      for (int i = 0; i < DATA.length; i++) {
        addExternalInput(add, tunnelFactory, DATA[i], 70, 80 + i * 30, 130, 80 + i * 30);
      }
      addExternalInput(add, tunnelFactory, "S0", 70, 350, 130, 350);
      addExternalInput(add, tunnelFactory, "S1", 70, 380, 130, 380);
      addExternalInput(add, tunnelFactory, "S2", 70, 410, 130, 410);
      addExternalInput(add, tunnelFactory, "nG", 70, 450, 130, 450);
      addExternalOutput(add, tunnelFactory, "Y", 850, 260, 800, 260);
      addExternalOutput(add, tunnelFactory, "nW", 850, 340, 800, 340);

      addInverter(add, notFactory, tunnelFactory, "S0", "nS0", 210, 350);
      addInverter(add, notFactory, tunnelFactory, "S1", "nS1", 210, 380);
      addInverter(add, notFactory, tunnelFactory, "S2", "nS2", 210, 410);
      addInverter(add, notFactory, tunnelFactory, "nG", "EN", 210, 450);

      for (int i = 0; i < DATA.length; i++) {
        int y = 90 + i * 80;
        String s0 = (i & 1) == 0 ? "nS0" : "S0";
        String s1 = (i & 2) == 0 ? "nS1" : "S1";
        String s2 = (i & 4) == 0 ? "nS2" : "S2";
        addBinaryGate(add, andFactory, tunnelFactory, "EN", DATA[i], "P" + i + "_A", 420, y);
        addBinaryGate(add, andFactory, tunnelFactory, "P" + i + "_A", s0, "P" + i + "_B", 520, y);
        addBinaryGate(add, andFactory, tunnelFactory, "P" + i + "_B", s1, "P" + i + "_C", 620, y);
        addBinaryGate(add, andFactory, tunnelFactory, "P" + i + "_C", s2, "T" + i, 720, y);
      }

      addBinaryGate(add, orFactory, tunnelFactory, "T0", "T1", "O01", 860, 140);
      addBinaryGate(add, orFactory, tunnelFactory, "T2", "T3", "O23", 860, 300);
      addBinaryGate(add, orFactory, tunnelFactory, "T4", "T5", "O45", 860, 460);
      addBinaryGate(add, orFactory, tunnelFactory, "T6", "T7", "O67", 860, 620);
      addBinaryGate(add, orFactory, tunnelFactory, "O01", "O23", "O03", 980, 220);
      addBinaryGate(add, orFactory, tunnelFactory, "O45", "O67", "O47", 980, 540);
      addBinaryGate(add, orFactory, tunnelFactory, "O03", "O47", "Y", 1100, 380);

      addInverter(add, notFactory, tunnelFactory, "Y", "nW", 720, 340);

      add.execute();

      File macroFile = new File("generated/circ/ttl_74151_macro.circ");
      save(context, macroFile);
      verifyText(macroFile, "NOT Gate", "AND Gate", "OR Gate", "Pin", "Tunnel");
      roundTrip(macroFile, 100, 90);

      generateNative74151();
      System.out.println("[OK] ttl_74151_macro.circ and ttl_74151_native.circ generated");
    } catch (Exception e) {
      throw new RuntimeException("TTL 74151 macro generation failed", e);
    } finally {
      if (context != null) {
        context.file().stopAutosaveThread(false);
      }
    }
  }

  private static void addExternalInput(
      CircuitMutation add, ComponentFactory tunnelFactory, String label, int pinX, int pinY, int tunnelX, int tunnelY) {
    Component pin = inputPin(pinX, pinY, label);
    Component tunnel = tunnel(tunnelFactory, tunnelX, tunnelY, label, Direction.WEST);
    add.add(pin);
    add.add(tunnel);
    wire(add, port(pin), port(tunnel));
  }

  private static void addExternalOutput(
      CircuitMutation add, ComponentFactory tunnelFactory, String label, int pinX, int pinY, int tunnelX, int tunnelY) {
    Component pin = outputPin(pinX, pinY, label);
    Component tunnel = tunnel(tunnelFactory, tunnelX, tunnelY, label, Direction.EAST);
    add.add(pin);
    add.add(tunnel);
    wire(add, port(tunnel), port(pin));
  }

  private static void addInverter(
      CircuitMutation add,
      ComponentFactory notFactory,
      ComponentFactory tunnelFactory,
      String inLabel,
      String outLabel,
      int x,
      int y) {
    Component in = tunnel(tunnelFactory, x - 50, y, inLabel, Direction.EAST);
    Component not = gate(notFactory, x, y);
    Component out = tunnel(tunnelFactory, x + 70, y, outLabel, Direction.WEST);
    add.add(in);
    add.add(not);
    add.add(out);
    wire(add, port(in), input(not, 1));
    wire(add, output(not), port(out));
  }

  private static void addBinaryGate(
      CircuitMutation add,
      ComponentFactory gateFactory,
      ComponentFactory tunnelFactory,
      String inA,
      String inB,
      String outLabel,
      int x,
      int y) {
    Component gate = gate(gateFactory, x, y);
    Location inputA = input(gate, 1);
    Location inputB = input(gate, 2);
    Component a = tunnel(tunnelFactory, inputA.getX() - 40, inputA.getY(), inA, Direction.EAST);
    Component b = tunnel(tunnelFactory, inputB.getX() - 40, inputB.getY(), inB, Direction.EAST);
    Component out = tunnel(tunnelFactory, output(gate).getX() + 70, output(gate).getY(), outLabel, Direction.WEST);
    add.add(gate);
    add.add(a);
    add.add(b);
    add.add(out);
    wire(add, port(a), inputA);
    wire(add, port(b), inputB);
    wire(add, output(gate), port(out));
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

  private static void generateNative74151() throws Exception {
    GenerationContext context = createEmptyProject();
    try {
      Library ttl = registerBuiltin(context, "TTL");
      ComponentFactory factory = findFactory(ttl, "74151");
      Component chip = factory.createComponent(Location.create(420, 220, true),
          factory.createAttributeSet());

      CircuitMutation add = new CircuitMutation(context.circuit());
      add.add(chip);
      add.execute();

      File nativeFile = new File("generated/circ/ttl_74151_native.circ");
      save(context, nativeFile);
      roundTrip(nativeFile, 1, 0);
    } finally {
      context.file().stopAutosaveThread(false);
    }
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

  private static Location port(Component component) {
    return component.getEnd(0).getLocation();
  }

  private static Location output(Component gate) {
    return gate.getEnd(0).getLocation();
  }

  private static Location input(Component gate, int index) {
    return gate.getEnd(index).getLocation();
  }

  private static void wire(CircuitMutation mutation, Location from, Location to) {
    if (from.equals(to)) {
      return;
    }
    if (from.getX() != to.getX() && from.getY() != to.getY()) {
      throw new IllegalArgumentException("Only horizontal/vertical wires are allowed: " + from + " -> " + to);
    }
    mutation.add(Wire.create(from, to));
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
    if (content.contains("name=\"74151\"")) {
      throw new IllegalStateException("Macro file must not depend on a native 74151 component");
    }
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
