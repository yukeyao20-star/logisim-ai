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
import java.nio.file.Path;
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
        String nlQuery = (args.length > 1)
            ? String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length))
            : "full adder";
        System.out.println("[dispatch] Running experiment 8: NL generation for '" + nlQuery + "'");
        runNlGeneration(nlQuery);
        System.exit(0);
        return;
      }
      case "9", "json" -> {
        // JSON-based circuit generation
        String jsonFile = (args.length > 1) ? args[1] : null;
        if (jsonFile == null) {
          System.err.println("[json] Usage: experiment 9 <json-file> [output-circ-file]");
          System.exit(1);
          return;
        }
        String outputFile = (args.length > 2) ? args[2] : "generated/circ/circuit_from_json.circ";
        System.out.println("[dispatch] Running experiment 9: JSON generation from " + jsonFile
            + " to " + outputFile);
        runJsonGeneration(jsonFile, outputFile);
        System.exit(0);
        return;
      }
      case "10", "catalog", "ttl-catalog" -> {
        System.out.println("[dispatch] Running experiment 10: TTL catalog generation");
        String jsonOutput = (args.length > 1) ? args[1] : "generated/catalog/ttl_catalog.json";
        String markdownOutput = (args.length > 2) ? args[2] : "docs/TTL_FACTORY_CATALOG_029.md";
        runTtlCatalogGeneration(jsonOutput, markdownOutput);
        System.exit(0);
        return;
      }
      case "11", "truth-table", "boolean-regression" -> {
        System.out.println("[dispatch] Running experiment 11: Boolean AST/truth-table regression");
        BooleanAstRegression.main(new String[]{});
        BooleanSynthesisRegression.main(new String[]{});
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
      var report = com.logisim_ai.ai.NaturalLanguageCircuitPlanner.planDetailed(query);
      var result = report.result();
      if (result == null) {
        System.err.println("[NL] Could not generate circuit for: '" + query + "'");
        System.err.println("[NL] Reason: " + report.diagnostic());
        if (!report.suggestions().isEmpty()) {
          System.err.println("[NL] Suggestions: " + report.suggestions());
        }
        System.exit(1);
        return;
      }

      System.out.println("[NL] " + result.description());
      if (result.sourceJson() != null) {
        System.out.println("[NL] Source JSON: " + result.sourceJson());
      }
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
      writeTtlReportsIfPresent(spec);
      System.out.println("[OK] " + spec.name() + ".circ generated via NL path");
    } catch (Exception e) {
      throw new RuntimeException("NL generation failed for: " + query, e);
    }
  }

  private static void writeTtlReportsIfPresent(com.logisim_ai.ir.CircuitSpec spec) throws Exception {
    for (com.logisim_ai.ttl.TtlPackageSpec packageSpec :
        com.logisim_ai.ttl.TtlMultiPackageReport.standardPackages()) {
      writeTtlReportIfPresent(spec, packageSpec);
    }
    writeMultiPackageTtlBomIfPresent(spec);
    writeTtlDipReportIfPresent(spec);
  }

  private static void writeMultiPackageTtlBomIfPresent(com.logisim_ai.ir.CircuitSpec spec) throws Exception {
    if (!com.logisim_ai.ttl.TtlMultiPackageReport.hasMultiplePackageFamilies(spec)) {
      return;
    }
    Path markdownReport = Path.of("generated/reports/" + spec.name() + "_ttl_bom.md");
    Path jsonReport = Path.of("generated/reports/" + spec.name() + "_ttl_bom.json");
    Files.createDirectories(markdownReport.getParent());
    Files.writeString(markdownReport, com.logisim_ai.ttl.TtlMultiPackageReport.toMarkdown(spec));
    Files.writeString(jsonReport, com.logisim_ai.ttl.TtlMultiPackageReport.toJson(spec));
    System.out.println("[OK] " + markdownReport + " generated");
    System.out.println("[OK] " + jsonReport + " generated");
  }

  private static void writeTtlDipReportIfPresent(com.logisim_ai.ir.CircuitSpec spec) throws Exception {
    if (com.logisim_ai.ttl.TtlDipWiringReport.chips(spec).isEmpty()) {
      return;
    }
    Path markdownReport = Path.of("generated/reports/" + spec.name() + "_ttl_dip.md");
    Path jsonReport = Path.of("generated/reports/" + spec.name() + "_ttl_dip.json");
    Files.createDirectories(markdownReport.getParent());
    Files.writeString(markdownReport, com.logisim_ai.ttl.TtlDipWiringReport.toMarkdown(spec));
    Files.writeString(jsonReport, com.logisim_ai.ttl.TtlDipWiringReport.toJson(spec));
    System.out.println("[OK] " + markdownReport + " generated");
    System.out.println("[OK] " + jsonReport + " generated");
  }

  private static void writeTtlReportIfPresent(
      com.logisim_ai.ir.CircuitSpec spec,
      com.logisim_ai.ttl.TtlPackageSpec packageSpec) throws Exception {
    var assignments = com.logisim_ai.ttl.TtlPackageMapper.assignments(spec, packageSpec);
    if (assignments.isEmpty()) {
      return;
    }
    com.logisim_ai.ttl.TtlPackageMapper.requireContiguousAssignments(spec, packageSpec);
    Path markdownReport = Path.of("generated/reports/" + spec.name()
        + "_" + packageSpec.packageType() + "_mapping.md");
    Path jsonReport = Path.of("generated/reports/" + spec.name()
        + "_" + packageSpec.packageType() + "_mapping.json");
    Path bomMarkdownReport = Path.of("generated/reports/" + spec.name()
        + "_" + packageSpec.packageType() + "_bom.md");
    Path bomJsonReport = Path.of("generated/reports/" + spec.name()
        + "_" + packageSpec.packageType() + "_bom.json");
    Files.createDirectories(markdownReport.getParent());
    Files.writeString(markdownReport, com.logisim_ai.ttl.TtlPackageMapper.toMarkdown(spec, packageSpec));
    Files.writeString(jsonReport, com.logisim_ai.ttl.TtlPackageMapper.toJson(spec, packageSpec));
    String bomMarkdown = com.logisim_ai.ttl.TtlPackageMapper.toBomMarkdown(spec, packageSpec);
    String bomJson = com.logisim_ai.ttl.TtlPackageMapper.toBomJson(spec, packageSpec);
    Files.writeString(bomMarkdownReport, bomMarkdown);
    Files.writeString(bomJsonReport, bomJson);
    if (packageSpec.packageType().equals("7400")) {
      Files.writeString(Path.of("generated/reports/" + spec.name() + "_bom.md"), bomMarkdown);
      Files.writeString(Path.of("generated/reports/" + spec.name() + "_bom.json"), bomJson);
    }
    System.out.println("[OK] " + markdownReport + " generated");
    System.out.println("[OK] " + jsonReport + " generated");
    System.out.println("[OK] " + bomMarkdownReport + " generated");
    System.out.println("[OK] " + bomJsonReport + " generated");
  }

  /**
   * Run JSON-based circuit generation.
   *
   * <p>Reads a JSON file describing a CircuitSpec, parses it via
   * {@link com.logisim_ai.ai.CircuitSpecJsonParser}, then compiles it.
   */
  private static void runJsonGeneration(String jsonFilePath, String outputFilePath) {
    try {
      String json = Files.readString(java.nio.file.Path.of(jsonFilePath));
      File outputFile = new File(outputFilePath);
      com.logisim_ai.ai.CircuitSpecJsonParser.parseAndCompile(json, outputFile);
      System.out.println("[OK] " + outputFile.getPath() + " generated via JSON path");
    } catch (Exception e) {
      throw new RuntimeException("JSON generation failed for: " + jsonFilePath, e);
    }
  }

  private static void runTtlCatalogGeneration(String jsonOutputPath, String markdownOutputPath) {
    try {
      com.logisim_ai.catalog.TtlCatalogGenerator.main(new String[] {jsonOutputPath, markdownOutputPath});
    } catch (Exception e) {
      throw new RuntimeException("TTL catalog generation failed", e);
    }
  }

  private record GenerationContext(Loader loader, LogisimFile file, Circuit circuit) {}
}
