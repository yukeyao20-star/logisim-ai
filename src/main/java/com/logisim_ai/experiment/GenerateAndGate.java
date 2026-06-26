package com.logisim_ai.experiment;

import com.cburch.logisim.Main;
import com.cburch.logisim.circuit.Circuit;
import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.ComponentFactory;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.file.Loader;
import com.cburch.logisim.file.LogisimFile;
import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;
import java.io.File;
import java.nio.file.Files;
public final class GenerateAndGate {
  private GenerateAndGate() {}

  public static void main(String[] args) {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");

    String experiment = (args.length > 0) ? args[0] : "0";

    switch (experiment) {
      case "1", "half-adder" -> {
        System.out.println("[dispatch] Running experiment 1: Half Adder");
        GenerateHalfAdder.main(new String[]{});
        System.exit(0);
        return;
      }
      case "2", "full-adder" -> {
        System.out.println("[dispatch] Running experiment 2: Full Adder");
        GenerateFullAdder.main(new String[]{});
        System.exit(0);
        return;
      }
      case "3", "two-bit-adder" -> {
        System.out.println("[dispatch] Running experiment 3: 2-bit Ripple-Carry Adder");
        GenerateTwoBitAdder.main(new String[]{});
        System.exit(0);
        return;
      }
      case "4", "74151" -> {
        System.out.println("[dispatch] Running experiment 4: 74151 Macro");
        GenerateTtl74151Macro.main(new String[]{});
        System.exit(0);
        return;
      }
      case "5", "jk" -> {
        System.out.println("[dispatch] Running experiment 5: JK Flip-Flop");
        GenerateJkFlipFlop.main(new String[]{});
        System.exit(0);
        return;
      }
      case "6", "74161" -> {
        System.out.println("[dispatch] Running experiment 6: native TTL 74161 demo");
        GenerateTtlCounters.main(new String[]{"74161"});
        System.exit(0);
        return;
      }
      case "7", "74160" -> {
        System.out.println("[dispatch] Running experiment 7: native TTL 74160 demo");
        GenerateTtlCounters.main(new String[]{"74160"});
        System.exit(0);
        return;
      }
      case "8", "nl" -> {
        // Natural language circuit generation
        String nlQuery = (args.length > 1) ? args[1] : "full adder";
        System.out.println("[dispatch] Running experiment 8: NL generation for '" + nlQuery + "'");
        runNlGeneration(nlQuery);
        System.exit(0);
        return;
      }
      case "9", "json" -> {
        // JSON-based circuit generation
        String jsonFile = (args.length > 1) ? args[1] : null;
        if (jsonFile == null) {
          System.err.println("[json] Usage: experiment 9 <json-file>");
          System.exit(1);
          return;
        }
        System.out.println("[dispatch] Running experiment 9: JSON generation from " + jsonFile);
        runJsonGeneration(jsonFile);
        System.exit(0);
        return;
      }
      case "0", "and-gate" -> {
        // continue to experiment 0
      }
      default -> {
        System.out.println("[dispatch] Unknown experiment: " + experiment + ", running experiment 0");
      }
    }

    System.out.println("=== Experiment 0: AND Gate ===");

    GenerationContext context = null;
    try {
      context = createEmptyProject();
      System.out.println("[OK] Empty project created, main circuit: " + context.circuit().getName());

      var gatesLibrary = context.loader().getBuiltin().getLibrary("Gates");
      if (gatesLibrary == null) {
        throw new IllegalStateException("Gates library not found");
      }
      LogisimProjectSetup.addLibrary(context.loader(), context.file(), "Gates");
      System.out.println("[OK] GatesLibrary registered, library count: " + context.file().getLibraries().size());

      var andFactory = findAndGateFactory(gatesLibrary);
      System.out.println("[OK] AND Gate factory found: " + andFactory.getName());

      var andGate = createAndGate(andFactory, Location.create(200, 100, true));
      System.out.println("[OK] AND gate component created at (200,100)");

      addComponent(context.circuit(), andGate);
      System.out.println("[OK] Component added, count: " + context.circuit().getNonWires().size());

      File outputFile = new File("generated/circ/and_gate.circ");
      saveCircuit(context.loader(), context.file(), outputFile);
      System.out.println("[OK] .circ file saved");

      verifyGeneratedFile(outputFile);
      System.out.println("[OK] Verification passed");

      System.out.println();
      System.out.println("=== Experiment 0 complete ===");

    } catch (Exception e) {
      System.err.println("=== Experiment 0 failed ===");
      System.err.println("Exception: " + e.getClass().getName() + ": " + e.getMessage());
      e.printStackTrace();
    } finally {
      if (context != null) {
        try {
          context.file().stopAutosaveThread(false);
        } catch (Exception e) {
          System.err.println("[WARN] stopAutosaveThread failed: " + e.getMessage());
        }
      }
    }
  }

  private static GenerationContext createEmptyProject() {
    Loader loader = new Loader(null);
    LogisimFile file = LogisimFile.createNew(loader, null);
    LogisimProjectSetup.configureGuiDefaults(loader, file);
    Circuit circuit = file.getMainCircuit();
    return new GenerationContext(loader, file, circuit);
  }

  private static ComponentFactory findAndGateFactory(Library gatesLibrary) {
    for (Tool tool : gatesLibrary.getTools()) {
      if (tool instanceof AddTool addTool) {
        if ("AND Gate".equals(addTool.getFactory().getName())) {
          return addTool.getFactory();
        }
      }
    }
    throw new IllegalStateException("AND Gate factory not found");
  }

  private static Component createAndGate(ComponentFactory andFactory, Location location) {
    AttributeSet attrs = andFactory.createAttributeSet();
    return andFactory.createComponent(location, attrs);
  }

  private static void addComponent(Circuit circuit, Component component) {
    CircuitMutation mutation = new CircuitMutation(circuit);
    mutation.add(component);
    mutation.execute();
  }

  private static void saveCircuit(Loader loader, LogisimFile file, File outputFile) {
    File parent = outputFile.getParentFile();
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }
    boolean ok = loader.save(file, outputFile);
    if (!ok) {
      throw new IllegalStateException("Loader.save returned false");
    }
    try {
      LogisimProjectSetup.patchV38Compat(outputFile);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to patch v3.8 compat", e);
    }
  }

  private static void verifyGeneratedFile(File outputFile) throws Exception {
    if (!outputFile.exists()) {
      throw new IllegalStateException("File does not exist: " + outputFile.getAbsolutePath());
    }
    if (outputFile.length() == 0) {
      throw new IllegalStateException("File is empty: " + outputFile.getAbsolutePath());
    }
    String content = Files.readString(outputFile.toPath());
    if (!content.contains("name=\"AND Gate\"")) {
      throw new IllegalStateException("File does not contain AND Gate component");
    }
  }

  /**
   * Run natural-language circuit generation.
   *
   * <p>Uses {@link com.logisim_ai.ai.NaturalLanguageCircuitPlanner} to convert
   * a natural language query into a {@link com.logisim_ai.ir.CircuitSpec},
   * then compiles it via {@link com.logisim_ai.compiler.LogisimCompiler}.
   */
  private static void runNlGeneration(String query) {
    try {
      var result = com.logisim_ai.ai.NaturalLanguageCircuitPlanner.plan(query);
      if (result == null) {
        System.err.println("[NL] No template matched for: '" + query + "'");
        System.err.println("[NL] Supported: " + com.logisim_ai.ai.NaturalLanguageCircuitPlanner.supportedCircuits());
        System.exit(1);
        return;
      }

      System.out.println("[NL] " + result.description());
      com.logisim_ai.ir.CircuitSpec spec = result.spec();

      // Validate
      com.logisim_ai.validation.CircuitSemanticValidator.requireValid(spec);
      System.out.println("[NL] Semantic validation passed");

      // Compile
      File outputFile = new File("generated/circ/" + spec.name() + ".circ");
      if (spec.isNetlist()) {
        com.logisim_ai.compiler.LogisimCompiler.compileNetlist(spec, outputFile);
      } else {
        com.logisim_ai.compiler.LogisimCompiler.compileSingleComponent(spec, outputFile);
      }
      System.out.println("[OK] " + spec.name() + ".circ generated via NL path");
    } catch (Exception e) {
      throw new RuntimeException("NL generation failed for: " + query, e);
    }
  }

  /**
   * Run JSON-based circuit generation.
   *
   * <p>Reads a JSON file describing a CircuitSpec, parses it via
   * {@link com.logisim_ai.ai.CircuitSpecJsonParser}, then compiles it.
   */
  private static void runJsonGeneration(String jsonFilePath) {
    try {
      String json = Files.readString(java.nio.file.Path.of(jsonFilePath));
      File outputFile = new File("generated/circ/circuit_from_json.circ");
      com.logisim_ai.ai.CircuitSpecJsonParser.parseAndCompile(json, outputFile);
      System.out.println("[OK] circuit_from_json.circ generated via JSON path");
    } catch (Exception e) {
      throw new RuntimeException("JSON generation failed for: " + jsonFilePath, e);
    }
  }

  private record GenerationContext(Loader loader, LogisimFile file, Circuit circuit) {}
}
