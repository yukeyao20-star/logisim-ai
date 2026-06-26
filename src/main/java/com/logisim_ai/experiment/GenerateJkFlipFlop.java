package com.logisim_ai.experiment;

import com.cburch.logisim.Main;
import com.cburch.logisim.circuit.Circuit;
import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.ComponentFactory;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.file.Loader;
import com.cburch.logisim.file.LogisimFile;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.wiring.Pin;
import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;
import com.logisim_ai.layout.ExternalPortSpec;
import com.logisim_ai.layout.PortSide;
import com.logisim_ai.layout.ReadableChipLayout;
import com.logisim_ai.validation.CircuitLayoutInspector;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

/** Generates a readable JK flip-flop demo using the native Memory library component. */
public final class GenerateJkFlipFlop {
  private GenerateJkFlipFlop() {}

  public static void main(String[] args) {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");

    GenerationContext context = null;
    try {
      context = createProject();
      Library memory = LogisimProjectSetup.addLibrary(context.loader(), context.file(), "Memory");
      ComponentFactory jkFactory = findFactory(memory, "J-K Flip-Flop");

      var attrs = jkFactory.createAttributeSet();
      attrs.setValue(StdAttr.LABEL, "JK");
      ReadableChipLayout layout = new ReadableChipLayout(Location.create(460, 240, true));
      Component jk = jkFactory.createComponent(layout.chipLocation(), attrs);

      CircuitMutation add = new CircuitMutation(context.circuit());
      add.add(jk);

      // Native JK port order in current Logisim-evolution:
      // 0 J, 1 K, 2 CLK, 3 Q, 4 /Q, 5 CLR, 6 PRE.
      var ports = layout.placePorts(List.of(
          new ExternalPortSpec("J", BitWidth.ONE, PortSide.LEFT),
          new ExternalPortSpec("K", BitWidth.ONE, PortSide.LEFT),
          new ExternalPortSpec("CLK", BitWidth.ONE, PortSide.LEFT),
          new ExternalPortSpec("Q", BitWidth.ONE, PortSide.RIGHT),
          new ExternalPortSpec("/Q", BitWidth.ONE, PortSide.RIGHT),
          new ExternalPortSpec("PRE", BitWidth.ONE, PortSide.TOP),
          new ExternalPortSpec("CLR", BitWidth.ONE, PortSide.BOTTOM)));

      Component pinJ = inputPin(ports.get(0));
      Component pinK = inputPin(ports.get(1));
      Component pinClk = inputPin(ports.get(2));
      Component pinQ = outputPin(ports.get(3));
      Component pinQn = outputPin(ports.get(4));
      Component pinPre = inputPin(ports.get(5));
      Component pinClr = inputPin(ports.get(6));

      add.add(pinJ); add.add(pinK); add.add(pinClk);
      add.add(pinQ); add.add(pinQn); add.add(pinClr); add.add(pinPre);
      connectOrthogonal(add, port(pinJ), jk.getEnd(0).getLocation(), 300);
      connectOrthogonal(add, port(pinK), jk.getEnd(1).getLocation(), 320);
      connectOrthogonal(add, port(pinClk), jk.getEnd(2).getLocation(), 340);
      connectOrthogonal(add, jk.getEnd(3).getLocation(), port(pinQ), 620);
      connectOrthogonal(add, jk.getEnd(4).getLocation(), port(pinQn), 640);
      connectOrthogonal(add, port(pinClr), jk.getEnd(5).getLocation(), 540);
      connectOrthogonal(add, port(pinPre), jk.getEnd(6).getLocation(), 380);
      add.execute();

      File output = new File("generated/circ/jk_flipflop.circ");
      save(context, output);
      verifyContains(output, "J-K Flip-Flop");
      CircuitLayoutInspector.requireClean(output);
      System.out.println("[OK] jk_flipflop.circ generated with native J-K Flip-Flop");
    } catch (Exception e) {
      throw new RuntimeException("JK flip-flop generation failed", e);
    } finally {
      if (context != null) {
        context.file().stopAutosaveThread(false);
      }
    }
  }

  private static GenerationContext createProject() {
    Loader loader = new Loader(null);
    LogisimFile file = LogisimFile.createNew(loader, null);
    LogisimProjectSetup.configureGuiDefaults(loader, file);
    return new GenerationContext(loader, file, file.getMainCircuit());
  }

  private static ComponentFactory findFactory(Library library, String name) {
    for (Tool tool : library.getTools()) {
      if (tool instanceof AddTool addTool && name.equals(addTool.getFactory().getName())) {
        return addTool.getFactory();
      }
    }
    throw new IllegalStateException("Missing factory " + name + " in " + library.getName());
  }

  private static Component inputPin(com.logisim_ai.layout.PlacedPort port) {
    var attrs = Pin.FACTORY.createAttributeSet();
    attrs.setValue(StdAttr.LABEL, port.label());
    attrs.setValue(StdAttr.FACING, port.pinFacing());
    attrs.setValue(StdAttr.WIDTH, port.width());
    return Pin.FACTORY.createComponent(port.pinLocation(), attrs);
  }

  private static Component outputPin(com.logisim_ai.layout.PlacedPort port) {
    var attrs = Pin.FACTORY.createAttributeSet();
    attrs.setValue(Pin.ATTR_TYPE, Pin.OUTPUT);
    attrs.setValue(StdAttr.LABEL, port.label());
    attrs.setValue(StdAttr.FACING, port.pinFacing());
    attrs.setValue(StdAttr.WIDTH, port.width());
    return Pin.FACTORY.createComponent(port.pinLocation(), attrs);
  }

  private static Location port(Component c) {
    return c.getEnd(0).getLocation();
  }

  private static void connectOrthogonal(CircuitMutation add, Location from, Location to, int channel) {
    if (from.equals(to)) {
      return;
    }
    if (from.getX() == to.getX() || from.getY() == to.getY()) {
      add.add(Wire.create(from, to));
      return;
    }
    Location first = Location.create(channel, from.getY(), true);
    Location second = Location.create(channel, to.getY(), true);
    add.add(Wire.create(from, first));
    add.add(Wire.create(first, second));
    add.add(Wire.create(second, to));
  }

  private static void save(GenerationContext context, File outputFile) throws Exception {
    File parent = outputFile.getParentFile();
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }
    if (!context.loader().save(context.file(), outputFile)) {
      throw new IllegalStateException("Loader.save returned false");
    }
    LogisimProjectSetup.patchV38Compat(outputFile);
    roundTrip(outputFile);
  }

  private static void roundTrip(File file) throws Exception {
    Loader loader = new Loader(null);
    LogisimFile loaded = loader.openLogisimFile(file);
    try {
      if (loaded == null || loaded.getMainCircuit() == null) {
        throw new IllegalStateException("Round-trip failed: " + file);
      }
    } finally {
      if (loaded != null) {
        loaded.stopAutosaveThread(false);
      }
    }
  }

  private static void verifyContains(File file, String token) throws Exception {
    String content = Files.readString(file.toPath());
    if (!content.contains("name=\"" + token + "\"")) {
      throw new IllegalStateException("Missing component in circ: " + token);
    }
  }

  private record GenerationContext(Loader loader, LogisimFile file, Circuit circuit) {}
}
