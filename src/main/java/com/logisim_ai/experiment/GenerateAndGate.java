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
        String multiChipTemplate = findMultiChipTemplateByQuery(nlQuery);
        if (multiChipTemplate != null) {
          System.out.println("[dispatch] Detected multi-chip template request: " + multiChipTemplate);
          runMultiChipTemplateGeneration(multiChipTemplate);
        } else {
          String nativeChip = com.logisim_ai.ai.NaturalLanguageCircuitPlanner.findNativeChip(nlQuery);
          if (nativeChip != null) {
            System.out.println("[dispatch] Detected native TTL chip request: " + nativeChip);
            runTtlNativeChipGeneration(nativeChip);
          } else {
            runNlGeneration(nlQuery);
          }
        }
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
      case "12", "dip-subckt", "ttl-dip-subckt" -> {
        String nlQuery = (args.length > 1)
            ? String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length))
            : "SUM=A xor B xor Cin; Cout=(A and B) or (Cin and (A xor B)) use TTL chips";
        System.out.println("[dispatch] Running experiment 12: TTL DIP subcircuit view for '" + nlQuery + "'");
        runTtlDipSubcircuitGeneration(nlQuery);
        System.exit(0);
        return;
      }
      case "13", "simulate", "official-simulation" -> {
        System.out.println("[dispatch] Running experiment 13: official Logisim JAR simulation");
        runOfficialSimulation(args);
        System.exit(0);
        return;
      }
      case "14", "ttl-chip", "native-chip" -> {
        String factory = (args.length > 1) ? args[1] : "74160";
        System.out.println("[dispatch] Running experiment 14: native TTL chip '" + factory + "'");
        runTtlNativeChipGeneration(factory);
        System.exit(0);
        return;
      }
      case "15", "multichip", "template-circuit" -> {
        String templateName = (args.length > 1) ? args[1] : "decoder_4to16";
        System.out.println("[dispatch] Running experiment 15: multi-chip template '" + templateName + "'");
        runMultiChipTemplateGeneration(templateName);
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
   * True when a spec belongs to a structural template family, which routes
   * through {@link com.logisim_ai.compiler.DirectMultiChipCompiler} (no
   * Tunnels): reliable wiring for dense fan-out circuits, and every gate sits
   * in its own slot so the layout stays readable.
   */
  public static boolean usesDirectCompiler(com.logisim_ai.ir.CircuitSpec spec) {
    return spec.name().startsWith("adder_")
        || spec.name().startsWith("satadd_")
        || spec.name().startsWith("subtractor_")
        || spec.name().startsWith("comparator_")
        || spec.name().startsWith("mux_")
        || spec.name().startsWith("decoder_")
        || spec.name().startsWith("parity_")
        || spec.name().matches("encoder_(8to3|4to2)")
        || spec.name().startsWith("bcd7seg")
        || spec.name().startsWith("hex7seg")
        || spec.name().startsWith("squarer_")
        || spec.name().startsWith("accumulator_")
        || spec.name().startsWith("mac_")
        || spec.name().startsWith("multiplier_")
        || spec.name().startsWith("bcd_adder")
        || spec.name().startsWith("counter_")
        || spec.name().startsWith("dff")
        || spec.name().startsWith("jkff")
        || spec.name().startsWith("tff")
        || spec.name().startsWith("register_")
        || spec.name().startsWith("shiftreg_")
        || spec.name().startsWith("piso_")
        || spec.name().startsWith("srlatch")
        || spec.name().startsWith("divider_")
        || spec.name().startsWith("johnson_")
        || spec.name().startsWith("dlatch")
        || spec.name().startsWith("gray_")
        || spec.name().startsWith("graycnt_")
        || spec.name().startsWith("gray2bin_")
        || spec.name().startsWith("excess3")
        || spec.name().equals("excess3_adder")
        || spec.name().startsWith("twoscomp_")
        || spec.name().equals("traffic_light")
        || spec.name().equals("edge_detector")
        || spec.name().equals("debouncer")
        || spec.name().startsWith("oneshot_")
        || spec.name().startsWith("seqdet_")
        || spec.name().startsWith("bidirsreg_")
        || spec.name().equals("bcd_counter")
        || spec.name().startsWith("updown_counter_")
        || spec.name().startsWith("lfsr_")
        || spec.name().startsWith("demux_")
        || spec.name().matches("mod\\d+_counter")
        || spec.name().equals("digital_clock")
        || spec.name().equals("countdown_timer")
        || spec.name().equals("stopwatch")
        || spec.name().equals("freq_meter")
        || spec.name().startsWith("bcd2bin_")
        || spec.name().startsWith("bin2bcd")
        || spec.name().equals("tristate_buffer")
        || spec.name().startsWith("ring_")
        || spec.name().startsWith("nine_")
        || spec.name().startsWith("alu_")
        || spec.name().startsWith("bcd_subtractor")
        || spec.name().startsWith("barrel_shifter_")
        || spec.name().startsWith("rotate_shifter_")
        || spec.name().startsWith("shift_right_")
        || spec.name().startsWith("sar_");
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
      boolean structural = usesDirectCompiler(spec);
      if (structural) {
        // Structural templates route directly (no Tunnels): reliable wiring for
        // dense fan-out circuits like decoders, and every gate sits in its own
        // slot so the layout stays readable.
        com.logisim_ai.compiler.DirectMultiChipCompiler.compile(spec, outputFile);
      } else if (spec.isNetlist()) {
        com.logisim_ai.compiler.LogisimCompiler.compileNetlist(spec, outputFile);
      } else {
        com.logisim_ai.compiler.LogisimCompiler.compileSingleComponent(spec, outputFile);
      }
      persistSpecJson(spec);
      writeTtlReportsIfPresent(spec);
      System.out.println("[OK] " + spec.name() + ".circ generated via NL path");
      com.logisim_ai.validation.CircuitArtifactManifest.writeReports(spec, outputFile);
      printCircuitManifestPath(outputFile);
      Path summaryPath = com.logisim_ai.validation.GenerationSummary.writeCircuitSummary(
          "natural_language",
          query,
          result.description(),
          result.intent().name(),
          summaryProfile(result.implementationProfile()),
          result.sourceJson(),
          spec,
          outputFile);
      printSummaryPath(summaryPath);
      printDeliveryPath(outputFile);
    } catch (Exception e) {
      throw new RuntimeException("NL generation failed for: " + query, e);
    }
  }

  private static void persistSpecJson(com.logisim_ai.ir.CircuitSpec spec) throws Exception {
    Path specDir = Path.of("generated", "examples");
    Files.createDirectories(specDir);
    Path specPath = specDir.resolve(spec.name() + "_spec.json");
    Files.writeString(specPath, com.logisim_ai.ai.CircuitSpecJsonParser.toJson(spec));
    System.out.println("[OK] spec JSON: " + specPath);
  }

  private static void writeTtlReportsIfPresent(com.logisim_ai.ir.CircuitSpec spec) throws Exception {
    for (com.logisim_ai.ttl.TtlPackageSpec packageSpec :
        com.logisim_ai.ttl.TtlMultiPackageReport.standardPackages()) {
      writeTtlReportIfPresent(spec, packageSpec);
    }
    writeMultiPackageTtlBomIfPresent(spec);
    writeTtlDipReportIfPresent(spec);
    writeTtlDipLayoutReportIfPresent(spec);
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

  private static void writeTtlDipLayoutReportIfPresent(com.logisim_ai.ir.CircuitSpec spec) throws Exception {
    if (com.logisim_ai.ttl.TtlDipLayoutReport.blocks(spec).isEmpty()) {
      return;
    }
    Path markdownReport = Path.of("generated/reports/" + spec.name() + "_ttl_dip_layout.md");
    Path jsonReport = Path.of("generated/reports/" + spec.name() + "_ttl_dip_layout.json");
    Files.createDirectories(markdownReport.getParent());
    Files.writeString(markdownReport, com.logisim_ai.ttl.TtlDipLayoutReport.toMarkdown(spec));
    Files.writeString(jsonReport, com.logisim_ai.ttl.TtlDipLayoutReport.toJson(spec));
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
      com.logisim_ai.ir.CircuitSpec spec = com.logisim_ai.ai.CircuitSpecJsonParser.parse(json);
      com.logisim_ai.ai.GenerationRequest generationRequest =
          com.logisim_ai.ai.GenerationRequest.structuredJson(jsonFilePath);
      com.logisim_ai.validation.CircuitSemanticValidator.requireValid(spec);
      if (spec.isNetlist()) {
        com.logisim_ai.compiler.LogisimCompiler.compileNetlist(spec, outputFile);
      } else {
        com.logisim_ai.compiler.LogisimCompiler.compileSingleComponent(spec, outputFile);
      }
      System.out.println("[OK] " + outputFile.getPath() + " generated via JSON path");
      com.logisim_ai.validation.CircuitArtifactManifest.writeReports(spec, outputFile);
      printCircuitManifestPath(outputFile);
      Path summaryPath = com.logisim_ai.validation.GenerationSummary.writeCircuitSummary(
          "json",
          jsonFilePath,
          "Parsed CircuitSpec JSON and compiled it",
          "STRUCTURED_JSON",
          summaryProfile(generationRequest.implementationProfile()),
          Path.of(jsonFilePath),
          spec,
          outputFile);
      printSummaryPath(summaryPath);
      printDeliveryPath(outputFile);
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

  private static void runTtlNativeChipGeneration(String factory) {
    try {
      java.io.File outputFile = new java.io.File("generated/circ/ttl_" + factory + ".circ");
      com.logisim_ai.compiler.TtlNativeChipCompiler.compile(factory, outputFile);
      System.out.println("[OK] " + outputFile.getPath() + " generated as native TTL chip view");
      System.out.println("[OK] report: generated/reports/ttl_" + factory + "_ttl_chip.md");
      printSummaryPath(java.nio.file.Path.of("generated", "reports",
          "ttl_" + factory + "_ttl_chip.md"));
      // Additional evidence: run the official Logisim JAR truth table when possible.
      try {
        com.logisim_ai.ir.CircuitSpec spec = specForNativeChip(factory);
        com.logisim_ai.validation.OfficialSimulationVerifier.Report simReport =
            com.logisim_ai.validation.OfficialSimulationVerifier.verify(spec, outputFile);
        System.out.println(simReport.toHumanString());
      } catch (Exception simException) {
        System.out.println("[ttl-chip] official JAR simulation skipped for " + factory
            + ": " + simException.getMessage());
      }
    } catch (Exception e) {
      throw new RuntimeException("Native TTL chip generation failed for: " + factory, e);
    }
  }

  private static void runMultiChipTemplateGeneration(String templateName) {
    try {
      com.logisim_ai.compiler.MultiChipCircuitGenerator.Template template =
          findMultiChipTemplate(templateName);
      java.io.File outputFile = new java.io.File(
          "generated/circ/" + template.circuitName() + ".circ");
      com.logisim_ai.compiler.MultiChipCircuitGenerator.compile(template, outputFile);
      System.out.println("[OK] " + outputFile.getPath()
          + " generated as multi-chip template (" + template.label() + ")");
      printCircuitManifestPath(outputFile);
      printSummaryPath(com.logisim_ai.validation.GenerationSummary.markdownPath(outputFile));
      printDeliveryPath(outputFile);
    } catch (Exception e) {
      throw new RuntimeException("Multi-chip template generation failed for: " + templateName, e);
    }
  }

  private static String findMultiChipTemplateByQuery(String query) {
    if (query == null || query.isBlank()) {
      return null;
    }
    String q = query.toLowerCase(java.util.Locale.ROOT).replaceAll("\\s+", "");
    // 4-to-16 decoder: 2x74138 + 4-16 + decoder/译码
    if (q.contains("4-16") || q.contains("4-16线") || q.contains("416") || q.contains("4到16")
        || (q.contains("74138") && (q.contains("译码") || q.contains("decoder") || q.contains("16")))) {
      return com.logisim_ai.compiler.MultiChipCircuitGenerator.Template.DECODER_4TO16.circuitName();
    }
    // 16-to-4 priority encoder: 2x74148 + priority + encoder/编码
    if (q.contains("16-4") || q.contains("16-4线") || q.contains("164")
        || q.contains("16到4") || (q.contains("74148") && (q.contains("编码") || q.contains("encoder")))) {
      return com.logisim_ai.compiler.MultiChipCircuitGenerator.Template.ENCODER_16TO4.circuitName();
    }
    return null;
  }

  private static com.logisim_ai.compiler.MultiChipCircuitGenerator.Template findMultiChipTemplate(
      String name) {
    for (com.logisim_ai.compiler.MultiChipCircuitGenerator.Template template
        : com.logisim_ai.compiler.MultiChipCircuitGenerator.Template.values()) {
      if (template.name().equalsIgnoreCase(name)
          || template.circuitName().equalsIgnoreCase(name)
          || template.label().equalsIgnoreCase(name)
          || name.contains(template.label().toLowerCase().replace(" ", "-"))
          || name.contains(template.circuitName())) {
        return template;
      }
    }
    throw new IllegalArgumentException("Unknown multi-chip template: " + name
        + " (available: decoder_4to16, encoder_16to4)");
  }

  /** Minimal spec used for official JAR verification of a standalone native chip. */
  private static com.logisim_ai.ir.CircuitSpec specForNativeChip(String factory) throws Exception {
    java.nio.file.Path chipReport = java.nio.file.Path.of(
        "generated", "reports", "ttl_" + factory + "_ttl_chip.json");
    if (!java.nio.file.Files.exists(chipReport)) {
      throw new IllegalStateException("missing chip report " + chipReport);
    }
    String json = java.nio.file.Files.readString(chipReport);
    java.util.List<com.logisim_ai.ir.ExternalPort> ports = new java.util.ArrayList<>();
    java.util.List<com.logisim_ai.ir.NetSpec> nets = new java.util.ArrayList<>();
    java.util.regex.Matcher matcher = java.util.regex.Pattern
        .compile("\\{\"endIndex\": (\\d+), \"label\": \"([^\"]+)\", \"direction\": \"([A-Z]+)\", \"width\": (\\d+)\\}")
        .matcher(json);
    while (matcher.find()) {
      int endIndex = Integer.parseInt(matcher.group(1));
      String label = matcher.group(2);
      boolean output = "OUTPUT".equals(matcher.group(3));
      int width = Integer.parseInt(matcher.group(4));
      String id = "port" + endIndex;
      ports.add(new com.logisim_ai.ir.ExternalPort(id, label, width,
          output ? com.logisim_ai.ir.PortDirection.OUTPUT : com.logisim_ai.ir.PortDirection.INPUT,
          output ? com.logisim_ai.layout.PortSide.RIGHT : com.logisim_ai.layout.PortSide.LEFT,
          "chip1", endIndex));
      nets.add(new com.logisim_ai.ir.NetSpec("net" + endIndex, java.util.List.of(
          new com.logisim_ai.ir.EndpointRef(output ? "chip1" : id,
              output ? endIndex : 0),
          new com.logisim_ai.ir.EndpointRef(output ? id : "chip1",
              output ? 0 : endIndex))));
    }
    if (ports.isEmpty()) {
      throw new IllegalStateException("no ports parsed from " + chipReport);
    }
    return new com.logisim_ai.ir.CircuitSpec(
        "ttl_" + factory,
        java.util.List.of(new com.logisim_ai.ir.ComponentSpec(
            "chip1", "TTL", factory, 1, "")),
        ports,
        nets,
        java.util.List.of());
  }

  private static void runTtlDipSubcircuitGeneration(String query) {
    try {
      var report = com.logisim_ai.ai.NaturalLanguageCircuitPlanner.planDetailed(query);
      var result = report.result();
      if (result == null) {
        throw new IllegalArgumentException("Could not generate TTL DIP subcircuit view: "
            + report.diagnostic());
      }
      com.logisim_ai.ir.CircuitSpec spec = result.spec();
      com.logisim_ai.validation.CircuitSemanticValidator.requireValid(spec);
      File outputFile = new File("generated/circ/" + spec.name() + "_dip_subckt.circ");
      com.logisim_ai.compiler.TtlDipSubcircuitCompiler.compile(spec, outputFile);
      writeTtlReportsIfPresent(spec);
      System.out.println("[OK] " + outputFile.getPath() + " generated as TTL DIP subcircuit view");
      printTtlDipManifestPath(spec);
      Path summaryPath = com.logisim_ai.validation.GenerationSummary.writeTtlDipSummary(
          query,
          result.description(),
          result.intent().name(),
          result.sourceJson(),
          spec,
          outputFile);
      printSummaryPath(summaryPath);
      printDeliveryPath(outputFile);
    } catch (Exception e) {
      throw new RuntimeException("TTL DIP subcircuit generation failed for: " + query, e);
    }
  }

  private static void runOfficialSimulation(String[] args) {
    java.util.List<java.io.File> targets = new java.util.ArrayList<>();
    if (args.length > 1) {
      for (int i = 1; i < args.length; i++) {
        targets.add(new java.io.File(args[i]));
      }
    } else {
      java.io.File circDir = new java.io.File("generated/circ");
      java.io.File[] files = circDir.listFiles((dir, name) -> name.endsWith(".circ"));
      if (files == null) {
        throw new IllegalStateException("generated/circ directory not found");
      }
      targets.addAll(java.util.Arrays.asList(files));
    }

    int passed = 0;
    int skipped = 0;
    int failed = 0;
    for (java.io.File circFile : targets) {
      if (!circFile.exists()) {
        System.err.println("[simulate] missing file: " + circFile);
        failed++;
        continue;
      }
      var spec = loadSpecForSimulation(circFile);
      if (spec == null) {
        System.out.println("[simulate] [SKIP] " + circFile.getName()
            + " (no matching spec/examples JSON; run via experiment 8/9 to generate)");
        skipped++;
        continue;
      }
      com.logisim_ai.validation.OfficialSimulationVerifier.Report report =
          com.logisim_ai.validation.OfficialSimulationVerifier.verify(spec, circFile);
      try {
        writeOfficialSimulationReports(spec, circFile, report);
      } catch (Exception reportException) {
        System.err.println("[simulate] failed to write simulation report for "
            + circFile.getName() + ": " + reportException.getMessage());
      }
      System.out.println(report.toHumanString());
      if (report.passed()) {
        passed++;
      } else {
        failed++;
      }
    }
    System.out.println();
    System.out.println("[simulate] summary: passed=" + passed + " skipped=" + skipped + " failed=" + failed);
    if (failed > 0) {
      System.exit(1);
    }
  }

  private static com.logisim_ai.ir.CircuitSpec loadSpecForSimulation(java.io.File circFile) {
    String stem = circFile.getName().replaceAll("\\.circ$", "");
    // Try in priority order: <stem>_spec.json, <stem>.json, then a rewritten stem
    // that drops a from_json_ / logic_ prefix so the example spec matches.
    java.util.List<java.nio.file.Path> candidates = new java.util.ArrayList<>();
    candidates.add(java.nio.file.Path.of("generated/examples", stem + "_spec.json"));
    candidates.add(java.nio.file.Path.of("generated/examples", stem + ".json"));
    for (String prefix : java.util.List.of("from_json_", "logic_", "ttl_", "nand_")) {
      if (stem.startsWith(prefix)) {
        String bare = stem.substring(prefix.length());
        candidates.add(java.nio.file.Path.of("generated/examples", bare + "_spec.json"));
        candidates.add(java.nio.file.Path.of("generated/examples", bare + ".json"));
      }
    }
    for (java.nio.file.Path candidate : candidates) {
      if (java.nio.file.Files.exists(candidate)) {
        try {
          String json = java.nio.file.Files.readString(candidate);
          return com.logisim_ai.ai.CircuitSpecJsonParser.parse(json);
        } catch (Exception e) {
          System.err.println("[simulate] failed to parse " + candidate + ": " + e.getMessage());
          return null;
        }
      }
    }
    return null;
  }

  private static void writeOfficialSimulationReports(
      com.logisim_ai.ir.CircuitSpec spec,
      java.io.File circFile,
      com.logisim_ai.validation.OfficialSimulationVerifier.Report report) throws Exception {
    Path reportDir = Path.of("generated", "reports");
    Files.createDirectories(reportDir);
    String stem = circFile.getName().replaceAll("\\.circ$", "");
    Path mdPath = Path.of("generated", "reports", stem + "_official_simulation.md");
    Path jsonPath = Path.of("generated", "reports", stem + "_official_simulation.json");
    Files.writeString(mdPath, officialSimulationMarkdown(spec, circFile, report));
    Files.writeString(jsonPath, officialSimulationJson(spec, circFile, report));
    System.out.println("[simulate] report: " + mdPath);
  }

  private static String officialSimulationMarkdown(
      com.logisim_ai.ir.CircuitSpec spec,
      java.io.File circFile,
      com.logisim_ai.validation.OfficialSimulationVerifier.Report report) {
    StringBuilder out = new StringBuilder();
    out.append("# Official Logisim simulation for ").append(spec.name()).append("\n\n");
    out.append("- Circuit: ").append(circFile.getPath()).append("\n");
    out.append("- Status: ").append(report.passed() ? "PASS" : "FAIL").append("\n");
    out.append("- Detail: ").append(report.detail()).append("\n\n");
    out.append("## Truth table (official Logisim-evolution 4.1.0)\n\n");
    if (report.rows().isEmpty()) {
      out.append("_No rows returned._\n");
      return out.toString();
    }
    String[] header = report.header().isEmpty()
        ? report.rows().get(0)
        : report.header().toArray(new String[0]);
    out.append("| ").append(String.join(" | ", header)).append(" |\n");
    out.append("|").append(" --- |".repeat(header.length)).append("\n");
    for (String[] row : report.rows()) {
      out.append("| ").append(String.join(" | ", row)).append(" |\n");
    }
    return out.toString();
  }

  private static String officialSimulationJson(
      com.logisim_ai.ir.CircuitSpec spec,
      java.io.File circFile,
      com.logisim_ai.validation.OfficialSimulationVerifier.Report report) {
    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"circuitName\": \"").append(escapeJson(spec.name())).append("\",\n");
    out.append("  \"circuitPath\": \"").append(escapeJson(circFile.getPath())).append("\",\n");
    out.append("  \"status\": \"").append(report.passed() ? "PASS" : "FAIL").append("\",\n");
    out.append("  \"detail\": \"").append(escapeJson(report.detail())).append("\",\n");
    out.append("  \"rowCount\": ").append(report.rows().size()).append(",\n");
    out.append("  \"rows\": [\n");
    for (int r = 0; r < report.rows().size(); r++) {
      out.append("    [");
      String[] row = report.rows().get(r);
      for (int c = 0; c < row.length; c++) {
        out.append("\"").append(escapeJson(row[c])).append("\"");
        if (c + 1 < row.length) {
          out.append(", ");
        }
      }
      out.append("]");
      if (r + 1 < report.rows().size()) {
        out.append(",");
      }
      out.append("\n");
    }
    out.append("  ]\n");
    out.append("}\n");
    return out.toString();
  }

  private static String escapeJson(String text) {
    return text
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t");
  }

  private static void printCircuitManifestPath(File outputFile) {
    System.out.println("[OK] Manifest: "
        + com.logisim_ai.validation.CircuitArtifactManifest.markdownPath(outputFile));
  }

  private static void printTtlDipManifestPath(com.logisim_ai.ir.CircuitSpec spec) {
    System.out.println("[OK] Manifest: generated/reports/"
        + spec.name() + "_ttl_dip_subckt_manifest.md");
  }

  private static void printSummaryPath(Path summaryPath) {
    System.out.println("[OK] Summary: " + summaryPath);
  }

  private static void printDeliveryPath(File outputFile) {
    System.out.println("[OK] Delivery: "
        + com.logisim_ai.validation.GenerationDeliveryReport.markdownPath(outputFile));
  }

  private static com.logisim_ai.validation.GenerationSummary.ImplementationProfile summaryProfile(
      com.logisim_ai.ai.NaturalLanguageCircuitPlanner.ImplementationProfile profile) {
    return new com.logisim_ai.validation.GenerationSummary.ImplementationProfile(
        profile.level().name(),
        profile.targetPackage(),
        profile.packagePrimitive(),
        profile.routingPrimitive());
  }

  private record GenerationContext(Loader loader, LogisimFile file, Circuit circuit) {}
}
