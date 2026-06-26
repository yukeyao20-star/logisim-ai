package com.logisim_ai.experiment;

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
import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;
import java.io.File;
import java.nio.file.Files;

/** Generates readable counter examples for current Logisim-evolution 4.x. */
public final class GenerateTtlCounters {
  private GenerateTtlCounters() {}

  public static void main(String[] args) {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");

    String target = args.length == 0 ? "74161" : args[0];
    if ("74160".equals(target)) {
      generate74160Native();
    } else {
      generate74161Native();
    }
  }

  private static void generate74160Native() {
    GenerationContext context = null;
    try {
      context = createProject("ttl_74160_native");
      Library ttl = LogisimProjectSetup.addLibrary(context.loader(), context.file(), "TTL");
      ComponentFactory factory = findFactory(ttl, "74160");
      Component chip = factory.createComponent(Location.create(420, 220, true),
          factory.createAttributeSet());

      CircuitMutation add = new CircuitMutation(context.circuit());
      add.add(chip);
      add.execute();

      File output = new File("generated/circ/ttl_74160_native.circ");
      save(context, output);
      verifyContains(output, "74160");
      System.out.println("[OK] ttl_74160_native.circ generated");
    } catch (Exception e) {
      throw new RuntimeException("74160 native generation failed", e);
    } finally {
      stop(context);
    }
  }

  private static void generate74161Native() {
    GenerationContext context = null;
    try {
      context = createProject("ttl_74161_native");
      Library ttl = LogisimProjectSetup.addLibrary(context.loader(), context.file(), "TTL");
      ComponentFactory factory = findFactory(ttl, "74161");
      Component chip = factory.createComponent(Location.create(420, 220, true),
          factory.createAttributeSet());

      CircuitMutation add = new CircuitMutation(context.circuit());
      add.add(chip);
      add.execute();

      File output = new File("generated/circ/ttl_74161_native.circ");
      save(context, output);
      verifyContains(output, "74161");
      System.out.println("[OK] ttl_74161_native.circ generated");
    } catch (Exception e) {
      throw new RuntimeException("74161 generation failed", e);
    } finally {
      stop(context);
    }
  }

  private static void generate74160Macro() {
    GenerationContext context = null;
    try {
      context = createProject("ttl_74160_macro");
      Library memory = LogisimProjectSetup.addLibrary(context.loader(), context.file(), "Memory");
      ComponentFactory counterFactory = findFactory(memory, "Counter");

      Project project = new Project(context.file());
      Circuit core = new Circuit("74160_core", context.file(), project);
      context.file().addCircuit(core);

      var attrs = counterFactory.createAttributeSet();
      attrs.setValue(StdAttr.WIDTH, BitWidth.create(4));
      setAttr(attrs, "max", 9L);
      setAttr(attrs, "label", "CTR DIV10");
      Component counter = counterFactory.createComponent(Location.create(440, 240, true), attrs);

      CircuitMutation add = new CircuitMutation(core);
      add.add(counter);

      // Counter ports: OUT, IN, CK, CLR, LD, UD, EN, CARRY.
      Component pinD = inputPin(100, 350, "D[3:0]", BitWidth.create(4));
      Component pinClk = inputPin(100, 320, "CLK", BitWidth.ONE);
      Component pinClr = inputPin(100, 260, "CLR", BitWidth.ONE);
      Component pinLoad = inputPin(100, 270, "LOAD", BitWidth.ONE);
      Component pinUp = inputPin(100, 290, "UP=1", BitWidth.ONE);
      Component pinEn = inputPin(100, 310, "EN", BitWidth.ONE);
      Component pinQ = outputPin(760, 350, "Q[3:0]", BitWidth.create(4));
      Component pinTc = outputPin(760, 290, "TC", BitWidth.ONE);

      add.add(pinD); add.add(pinClk); add.add(pinClr); add.add(pinLoad);
      add.add(pinUp); add.add(pinEn); add.add(pinQ); add.add(pinTc);
      add.add(Wire.create(port(pinD), counter.getEnd(1).getLocation()));
      add.add(Wire.create(port(pinClk), counter.getEnd(2).getLocation()));
      add.add(Wire.create(port(pinClr), counter.getEnd(3).getLocation()));
      add.add(Wire.create(port(pinLoad), counter.getEnd(4).getLocation()));
      add.add(Wire.create(port(pinUp), counter.getEnd(5).getLocation()));
      add.add(Wire.create(port(pinEn), counter.getEnd(6).getLocation()));
      add.add(Wire.create(counter.getEnd(0).getLocation(), port(pinQ)));
      add.add(Wire.create(counter.getEnd(7).getLocation(), port(pinTc)));
      add.execute();

      Component wrapper = core.getSubcircuitFactory()
          .createComponent(Location.create(420, 240, true),
              core.getSubcircuitFactory().createAttributeSet());
      CircuitMutation mainAdd = new CircuitMutation(context.circuit());
      mainAdd.add(wrapper);
      for (int i = 0; i < wrapper.getEnds().size(); i++) {
        Location endpoint = wrapper.getEnd(i).getLocation();
        String label = label74160(i);
        boolean output = i >= 6;
        Component pin = output
            ? outputPin(760, endpoint.getY(), label, i == 6 ? BitWidth.create(4) : BitWidth.ONE)
            : inputPin(120, endpoint.getY(), label, i == 0 ? BitWidth.create(4) : BitWidth.ONE);
        mainAdd.add(pin);
        mainAdd.add(Wire.create(output ? endpoint : port(pin), output ? port(pin) : endpoint));
      }
      mainAdd.execute();

      File output = new File("generated/circ/ttl_74160_macro.circ");
      save(context, output);
      verifyContains(output, "Counter");
      System.out.println("[OK] ttl_74160_macro.circ generated");
    } catch (Exception e) {
      throw new RuntimeException("74160 macro generation failed", e);
    } finally {
      stop(context);
    }
  }

  private static GenerationContext createProject(String circuitName) {
    Loader loader = new Loader(null);
    LogisimFile file = LogisimFile.createNew(loader, null);
    LogisimProjectSetup.configureGuiDefaults(loader, file);
    Project project = new Project(file);
    Circuit circuit = new Circuit(circuitName, file, project);
    file.addCircuit(circuit);
    file.setMainCircuit(circuit);
    return new GenerationContext(loader, file, circuit);
  }

  private static ComponentFactory findFactory(Library library, String name) {
    for (Tool tool : library.getTools()) {
      if (tool instanceof AddTool addTool && name.equals(addTool.getFactory().getName())) {
        return addTool.getFactory();
      }
    }
    throw new IllegalStateException("Missing factory " + name + " in " + library.getName());
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static void setAttr(com.cburch.logisim.data.AttributeSet attrs, String name, Object value) {
    Attribute attr = attrs.getAttribute(name);
    if (attr == null) {
      throw new IllegalStateException("Missing attribute: " + name);
    }
    attrs.setValue(attr, value);
  }

  private static Component inputPin(int x, int y, String label, BitWidth width) {
    return inputPin(x, y, label, width, Direction.EAST);
  }

  private static Component inputPin(int x, int y, String label, BitWidth width, Direction facing) {
    var attrs = Pin.FACTORY.createAttributeSet();
    attrs.setValue(StdAttr.LABEL, label);
    attrs.setValue(StdAttr.FACING, facing);
    attrs.setValue(StdAttr.WIDTH, width);
    return Pin.FACTORY.createComponent(Location.create(x, y, true), attrs);
  }

  private static Component outputPin(int x, int y, String label, BitWidth width) {
    return outputPin(x, y, label, width, Direction.WEST);
  }

  private static Component outputPin(int x, int y, String label, BitWidth width, Direction facing) {
    var attrs = Pin.FACTORY.createAttributeSet();
    attrs.setValue(Pin.ATTR_TYPE, Pin.OUTPUT);
    attrs.setValue(StdAttr.LABEL, label);
    attrs.setValue(StdAttr.FACING, facing);
    attrs.setValue(StdAttr.WIDTH, width);
    return Pin.FACTORY.createComponent(Location.create(x, y, true), attrs);
  }

  private static Location port(Component c) {
    return c.getEnd(0).getLocation();
  }

  private static String label74160(int port) {
    return switch (port) {
      case 0 -> "D[3:0]";
      case 1 -> "CLK";
      case 2 -> "CLR";
      case 3 -> "LOAD";
      case 4 -> "UP";
      case 5 -> "EN";
      case 6 -> "Q[3:0]";
      case 7 -> "TC";
      default -> "P" + port;
    };
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
      throw new IllegalStateException("Missing token in generated circ: " + token);
    }
  }

  private static void stop(GenerationContext context) {
    if (context != null) {
      context.file().stopAutosaveThread(false);
    }
  }

  private static String label74161(int port) {
    return switch (port) {
      case 0 -> "nCLR";
      case 1 -> "CLK";
      case 2 -> "A";
      case 3 -> "B";
      case 4 -> "C";
      case 5 -> "D";
      case 6 -> "ENP";
      case 7 -> "nLOAD";
      case 8 -> "ENT";
      case 9 -> "QD";
      case 10 -> "QC";
      case 11 -> "QB";
      case 12 -> "QA";
      case 13 -> "RCO";
      default -> "P" + port;
    };
  }

  private record GenerationContext(Loader loader, LogisimFile file, Circuit circuit) {}
}
