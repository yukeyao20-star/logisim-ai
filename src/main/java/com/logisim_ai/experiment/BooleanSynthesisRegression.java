package com.logisim_ai.experiment;

import com.cburch.logisim.data.Location;
import com.logisim_ai.ai.GenerationRequest;
import com.logisim_ai.ai.NaturalLanguageCircuitPlanner;
import com.logisim_ai.ai.NaturalLanguageCircuitPlanner.ImplementationIntent;
import com.logisim_ai.ai.NaturalLanguageCircuitPlanner.ImplementationLevel;
import com.logisim_ai.compiler.LogisimCompiler;
import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.LayoutHint;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.AutoLayoutPolicy;
import com.logisim_ai.layout.FootprintPolicy;
import com.logisim_ai.layout.PortSide;
import com.logisim_ai.ttl.Ttl7400Mapper;
import com.logisim_ai.ttl.TtlDipLayoutReport;
import com.logisim_ai.ttl.TtlDipWiringReport;
import com.logisim_ai.ttl.TtlMultiPackageReport;
import com.logisim_ai.ttl.TtlPackageMapper;
import com.logisim_ai.ttl.TtlPackageSpec;
import com.logisim_ai.validation.CircuitArtifactManifest;
import com.logisim_ai.validation.CircuitLayoutInspector;
import com.logisim_ai.validation.CircuitSemanticValidator;
import com.logisim_ai.validation.CircuitStructureInspector;
import com.logisim_ai.validation.CircuitTruthTableEvaluator;
import com.logisim_ai.validation.GenerationDeliveryReport;
import com.logisim_ai.validation.GenerationSummary;
import com.logisim_ai.validation.LayoutRepairExecutor;
import com.logisim_ai.validation.TtlDipArtifactManifest;
import com.logisim_ai.validation.TtlDipBehaviorInspector;
import com.logisim_ai.validation.TtlDipPinMapInspector;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/** Regression checks for NL/template Boolean circuits at the CircuitSpec level. */
public final class BooleanSynthesisRegression {
  private BooleanSynthesisRegression() {}

  public static void main(String[] args) {
    List<Case> cases = List.of(
        new Case(
            "F=A and not B",
            List.of("a", "b"),
            "f",
            values -> values.get("a") && !values.get("b")),
        new Case(
            "F(A,B,C)=sum(1,3,7)",
            List.of("a", "b", "c"),
            "f",
            values -> {
              int index = (values.get("a") ? 4 : 0)
                  + (values.get("b") ? 2 : 0)
                  + (values.get("c") ? 1 : 0);
              return index == 1 || index == 3 || index == 7;
            }),
        new Case(
            "Y=A xor B xor C",
            List.of("a", "b", "c"),
            "y",
            values -> values.get("a") ^ values.get("b") ^ values.get("c")),
        new Case(
            "Y=A nand B",
            List.of("a", "b"),
            "y",
            values -> !(values.get("a") && values.get("b"))),
        new Case(
            "Y=A nor B",
            List.of("a", "b"),
            "y",
            values -> !(values.get("a") || values.get("b"))),
        new Case(
            "Y=A xor B and C",
            List.of("a", "b", "c"),
            "y",
            values -> values.get("a") ^ (values.get("b") && values.get("c"))),
        new Case(
            "half-adder",
            List.of("a", "b"),
            "sum",
            values -> values.get("a") ^ values.get("b")),
        new Case(
            "full-adder",
            List.of("a", "b", "cin"),
            "sum",
            values -> values.get("a") ^ values.get("b") ^ values.get("cin")),
        new Case(
            "4-to-1-mux",
            List.of("d0", "d1", "d2", "d3", "s0", "s1"),
            "y",
            values -> {
              int select = (values.get("s1") ? 2 : 0) + (values.get("s0") ? 1 : 0);
              return switch (select) {
                case 0 -> values.get("d0");
                case 1 -> values.get("d1");
                case 2 -> values.get("d2");
                case 3 -> values.get("d3");
                default -> throw new IllegalStateException("Invalid select: " + select);
              };
            }),
        new Case(
            "xor3",
            List.of("a", "b", "c"),
            "y",
            values -> values.get("a") ^ values.get("b") ^ values.get("c")),
        new Case(
            "Y=A与B",
            List.of("a", "b"),
            "y",
            values -> values.get("a") && values.get("b")),
        new Case(
            "Y=A或非B",
            List.of("a", "b"),
            "y",
            values -> !(values.get("a") || values.get("b"))),
        new Case(
            "Y=非A",
            List.of("a"),
            "y",
            values -> !values.get("a")),
        new Case(
            "Y=A异或B",
            List.of("a", "b"),
            "y",
            values -> values.get("a") ^ values.get("b")),
        new Case(
            "Y=A与非B",
            List.of("a", "b"),
            "y",
            values -> !(values.get("a") && values.get("b"))),
        new Case(
            "三人表决器",
            List.of("a", "b", "c"),
            "f",
            values -> (values.get("a") && values.get("b"))
                || (values.get("a") && values.get("c"))
                || (values.get("b") && values.get("c"))),
        new Case(
            "两个开关串联",
            List.of("a", "b"),
            "f",
            values -> values.get("a") && values.get("b")),
        new Case(
            "两个开关并联",
            List.of("a", "b"),
            "f",
            values -> values.get("a") || values.get("b")),
        new Case(
            "三个开关控制一盏灯",
            List.of("a", "b", "c"),
            "f",
            values -> values.get("a") ^ values.get("b") ^ values.get("c")),
        new Case(
            "两个数不相等",
            List.of("a", "b"),
            "f",
            values -> values.get("a") ^ values.get("b")),
        new Case(
            "三输入与非门",
            List.of("a", "b", "c"),
            "f",
            values -> !(values.get("a") && values.get("b") && values.get("c"))),
        new Case(
            "取反",
            List.of("a"),
            "f",
            values -> !values.get("a")),
        new Case(
            "求两个数的最大值",
            List.of("a", "b"),
            "f",
            values -> values.get("a") || values.get("b")),
        new Case(
            "求两个数的最小值",
            List.of("a", "b"),
            "f",
            values -> values.get("a") && values.get("b")),
        new Case(
            "三个开关都闭合",
            List.of("a", "b", "c"),
            "f",
            values -> values.get("a") && values.get("b") && values.get("c")),
        new Case(
            "任何一个开关闭合",
            List.of("a", "b", "c"),
            "f",
            values -> values.get("a") || values.get("b") || values.get("c")),
        new Case(
            "至少两个开关闭合",
            List.of("a", "b", "c"),
            "f",
            values -> (values.get("a") && values.get("b"))
                || (values.get("a") && values.get("c"))
                || (values.get("b") && values.get("c"))),
        new Case(
            "恰好一个开关闭合",
            List.of("a", "b", "c"),
            "f",
            values -> (values.get("a") && !values.get("b") && !values.get("c"))
                || (!values.get("a") && values.get("b") && !values.get("c"))
                || (!values.get("a") && !values.get("b") && values.get("c"))),
        new Case(
            "求和",
            List.of("a", "b", "cin"),
            "sum",
            values -> values.get("a") ^ values.get("b") ^ values.get("cin")),
        new Case(
            "与逻辑",
            List.of("a", "b"),
            "f",
            values -> values.get("a") && values.get("b")),
        new Case(
            "或逻辑",
            List.of("a", "b"),
            "f",
            values -> values.get("a") || values.get("b")),
        new Case(
            "异或逻辑",
            List.of("a", "b"),
            "f",
            values -> values.get("a") ^ values.get("b")),
        new Case(
            "逻辑与",
            List.of("a", "b"),
            "f",
            values -> values.get("a") && values.get("b")),
        new Case(
            "逻辑或",
            List.of("a", "b"),
            "f",
            values -> values.get("a") || values.get("b")),
        new Case(
            "与非",
            List.of("a", "b"),
            "f",
            values -> !(values.get("a") && values.get("b"))),
        new Case(
            "或非",
            List.of("a", "b"),
            "f",
            values -> !(values.get("a") || values.get("b"))),
        new Case(
            "互斥",
            List.of("a", "b"),
            "f",
            values -> values.get("a") ^ values.get("b")),
        new Case(
            "同或",
            List.of("a", "b"),
            "f",
            values -> !(values.get("a") ^ values.get("b"))),
        new Case(
            "密码锁",
            List.of("a0", "a1", "a2", "a3", "b0", "b1", "b2", "b3"),
            "f",
            values -> values.get("a0").equals(values.get("b0"))
                && values.get("a1").equals(values.get("b1"))
                && values.get("a2").equals(values.get("b2"))
                && values.get("a3").equals(values.get("b3"))),
        new Case(
            "一致",
            List.of("a", "b"),
            "f",
            values -> !(values.get("a") ^ values.get("b"))),
        new Case(
            "相反",
            List.of("a", "b"),
            "f",
            values -> values.get("a") ^ values.get("b")),
        new Case(
            "零检测",
            List.of("a", "b", "c", "d"),
            "f",
            values -> !(values.get("a") || values.get("b") || values.get("c") || values.get("d"))),
        new Case(
            "非零检测",
            List.of("a", "b", "c", "d"),
            "f",
            values -> values.get("a") || values.get("b") || values.get("c") || values.get("d")),
        new Case(
            "至少一个开关闭合",
            List.of("a", "b", "c"),
            "f",
            values -> values.get("a") || values.get("b") || values.get("c")),
        new Case(
            "恰好两个开关闭合",
            List.of("a", "b", "c"),
            "f",
            values -> (values.get("a") && values.get("b") && !values.get("c"))
                || (values.get("a") && !values.get("b") && values.get("c"))
                || (!values.get("a") && values.get("b") && values.get("c"))),
        new Case(
            "两个开关都闭合",
            List.of("a", "b"),
            "f",
            values -> values.get("a") && values.get("b")),
        new Case(
            "两个开关都断开",
            List.of("a", "b"),
            "f",
            values -> !(values.get("a") || values.get("b"))),
        new Case(
            "三个开关同时闭合",
            List.of("a", "b", "c"),
            "f",
            values -> values.get("a") && values.get("b") && values.get("c")),
        new Case(
            "判断偶数",
            List.of("a"),
            "f",
            values -> !values.get("a")),
        new Case(
            "判断奇数",
            List.of("a"),
            "f",
            values -> values.get("a")),
        new Case(
            "两个都为真",
            List.of("a", "b"),
            "f",
            values -> values.get("a") && values.get("b")),
        new Case(
            "全部为真",
            List.of("a", "b", "c"),
            "f",
            values -> values.get("a") && values.get("b") && values.get("c")),
        new Case(
            "任一为真",
            List.of("a", "b", "c"),
            "f",
            values -> values.get("a") || values.get("b") || values.get("c")),
        new Case(
            "至少两个为真",
            List.of("a", "b", "c"),
            "f",
            values -> (values.get("a") && values.get("b"))
                || (values.get("a") && values.get("c"))
                || (values.get("b") && values.get("c"))),
        new Case(
            "恰好一个为真",
            List.of("a", "b", "c"),
            "f",
            values -> (values.get("a") && !values.get("b") && !values.get("c"))
                || (!values.get("a") && values.get("b") && !values.get("c"))
                || (!values.get("a") && !values.get("b") && values.get("c"))),
        new Case(
            "一个开一个关",
            List.of("a", "b"),
            "f",
            values -> values.get("a") ^ values.get("b")),
        new Case(
            "两个开关同时闭合",
            List.of("a", "b"),
            "f",
            values -> values.get("a") && values.get("b")),
        new Case(
            "两个输入选一个",
            List.of("d0", "d1", "s"),
            "f",
            values -> values.get("s") ? values.get("d1") : values.get("d0")),
        new Case(
            "全部为1",
            List.of("a", "b", "c"),
            "f",
            values -> values.get("a") && values.get("b") && values.get("c")),
        new Case(
            "任一为1",
            List.of("a", "b", "c"),
            "f",
            values -> values.get("a") || values.get("b") || values.get("c")),
        new Case(
            "全部为0",
            List.of("a", "b", "c"),
            "f",
            values -> !(values.get("a") || values.get("b") || values.get("c"))),
        new Case(
            "任一为0",
            List.of("a", "b", "c"),
            "f",
            values -> !(values.get("a") && values.get("b") && values.get("c"))),
        new Case(
            "两个都为1",
            List.of("a", "b"),
            "f",
            values -> values.get("a") && values.get("b")),
        new Case(
            "有信号",
            List.of("a"),
            "f",
            values -> values.get("a")),
        new Case(
            "无信号",
            List.of("a"),
            "f",
            values -> !values.get("a")),
        new Case(
            "两个信号都有效",
            List.of("a", "b"),
            "f",
            values -> values.get("a") && values.get("b")),
        new Case(
            "求差",
            List.of("a", "b"),
            "d",
            values -> values.get("a") ^ values.get("b")),
        new Case(
            "求积",
            List.of("a", "b"),
            "p",
            values -> values.get("a") && values.get("b")),
        new Case(
            "非运算",
            List.of("a"),
            "f",
            values -> !values.get("a")),
        new Case(
            "取非",
            List.of("a"),
            "f",
            values -> !values.get("a")),
        new Case(
            "两个地点控制灯",
            List.of("a", "b"),
            "f",
            values -> values.get("a") ^ values.get("b")),
        new Case(
            "两个信号不同",
            List.of("a", "b"),
            "f",
            values -> values.get("a") ^ values.get("b")),
        new Case(
            "三个开关至少2个",
            List.of("a", "b", "c"),
            "f",
            values -> (values.get("a") && values.get("b"))
                || (values.get("a") && values.get("c"))
                || (values.get("b") && values.get("c"))),
        new Case(
            "只要一个开关闭合灯亮",
            List.of("a", "b", "c"),
            "f",
            values -> (values.get("a") && !values.get("b") && !values.get("c"))
                || (!values.get("a") && values.get("b") && !values.get("c"))
                || (!values.get("a") && !values.get("b") && values.get("c"))),
        // 8-to-1 mux (structural): 2048 combinations, OR-chain + shared not-S fan-out.
        new Case(
            "8 to 1 mux",
            List.of("d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "s0", "s1", "s2"),
            "y",
            values -> {
              int select = (values.get("s0") ? 1 : 0)
                  + (values.get("s1") ? 2 : 0)
                  + (values.get("s2") ? 4 : 0);
              return switch (select) {
                case 0 -> values.get("d0");
                case 1 -> values.get("d1");
                case 2 -> values.get("d2");
                case 3 -> values.get("d3");
                case 4 -> values.get("d4");
                case 5 -> values.get("d5");
                case 6 -> values.get("d6");
                case 7 -> values.get("d7");
                default -> throw new IllegalStateException("Invalid select: " + select);
              };
            }),
        new Case(
            "开关量",
            List.of("a"),
            "f",
            values -> values.get("a")),
        // Conditional-sentence fallback (当/如果...则) — exercises the
        // extraction regex + normalizeConnectives + parse path.
        new Case(
            "当A与B时输出1",
            List.of("a", "b"),
            "f",
            values -> values.get("a") && values.get("b")),
        new Case(
            "如果A和B不同时输出1",
            List.of("a", "b"),
            "f",
            values -> values.get("a") ^ values.get("b")),
        new Case(
            "如果非A则报警",
            List.of("a"),
            "f",
            values -> !values.get("a")),
        // "A或者非B" must parse as A OR (NOT B), not the NOR token (guarded fix
        // in normalizeConnectives).
        new Case(
            "如果A或者非B则报警",
            List.of("a", "b"),
            "f",
            values -> values.get("a") || !values.get("b")),
        // Generic N-input gate builder — N>3 and non-NAND variants.
        new Case(
            "四输入异或",
            List.of("a", "b", "c", "d"),
            "f",
            values -> values.get("a") ^ values.get("b") ^ values.get("c") ^ values.get("d")),
        new Case(
            "三输入或非",
            List.of("a", "b", "c"),
            "f",
            values -> !(values.get("a") || values.get("b") || values.get("c"))),
        new Case(
            "四输入与非",
            List.of("a", "b", "c", "d"),
            "f",
            values -> !(values.get("a") && values.get("b") && values.get("c") && values.get("d"))),
        // 不一致: true unless all three inputs agree.
        new Case(
            "不一致",
            List.of("a", "b", "c"),
            "f",
            values -> (values.get("a") ^ values.get("b")) || (values.get("a") ^ values.get("c"))));

    for (Case testCase : cases) {
      runCase(testCase);
    }
    runMultiOutputHalfAdder();
    runMultiOutputFullAdder();
    runFunctionalMultiOutput();
    runEncoderPriorityRegression();
    runStructuralComparatorRegression();
    runAluTruthTable();
    runMagnitudeComparatorRegression();
    runPasswordLockRegression();
    runBcdSubtractorRegression();
    runBarrelShifterRegression();
    runRotateShifterRegression();
    runRightShiftRegression();
    runDividerRegression();
    runMultiplierRegression();
    runBcd1Regression();
    runParityRegression();
    runBcd2Regression();
    runBcdSub2Regression();
    runHex7SegRegression();
    runBcd7SegRegression();
    runSquarerRegression();
    runExcess3AdderRegression();
    runSaturatingAdderRegression();
    runEnglishTemplateQueriesRegression();
    runCommutativeReuse();
    runNandOnlySynthesis();
    runTtl7400Synthesis();
    runTtl7486Synthesis();
    runDirectGateTtlSynthesis();
    runTtlStrategyMismatchRejection();
    runMixedTtlPackageSynthesis();
    runMixedTtlFullAdderSynthesis();
    runTtlDipPinMapInspectorRegression();
    runTtlDipBehaviorInspectorRegression();
    runTtlDipArtifactManifestRegression();
    runCircuitArtifactManifestRegression();
    runCircuitStructureInspectorRegression();
    runGenerationSummaryRegression();
    runLayoutRepairExecutorRegression();
    runAutoLayoutPolicyRegression();
    runTtl7400FullAdderSynthesis();
    runGenericTtlPackageMapper();
    runNaturalLanguageImplementationIntent();
    runOfficialSimulationRegression();
    runNetlistConnectivityRegression();
    runClockSequenceRegression();
    runDirectedVectorRegression();
    System.out.println("[OK] Boolean synthesis/template truth-table regression passed");
  }

  /** Official JAR truth-table evidence for the shipped example circuits, when the JAR is present. */
  private static void runOfficialSimulationRegression() {
    List<String> circuitNames =
        List.of(
            // Example circuits (template keyword path)
            "half_adder", "full_adder", "mux_4to1", "nand_xor3",
            // Structural templates verified against the official Logisim JAR
            "adder_4bit", "comparator_4bit", "subtractor_4bit",
            "multiplier_2x2", "multiplier_4x4", "decoder_3bit",
            "mux_8to1", "parity_8bit", "bcd7seg", "nine_complement",
            "excess3_bcd", "excess3tobcd", "gray_4bit", "gray2bin_4bit",
            "twoscomp_4bit", "bin2bcd_4bit", "bcd2bin_2digit", "demux_4to1",
            "alu_4bit", "bcd_adder", "bcd_subtractor", "barrel_shifter_4bit",
            "rotate_shifter_4bit_left", "shift_right_4bit", "sar_4bit",
            "subtractor_1bit", "encoder_8to3", "decoder_2bit", "comparator_3bit",
            "parity_4bit", "hex7seg", "squarer_4bit", "excess3_adder", "satadd_4bit");
    java.io.File jarProbe = new java.io.File(
        System.getenv().getOrDefault("CIRCUITARIUM_LOGISIM_JAR",
            com.logisim_ai.validation.OfficialSimulationVerifier.DEFAULT_LOGISIM_JAR));
    if (!jarProbe.exists()) {
      System.out.println("[OK] official Logisim JAR simulation regression: SKIP (JAR not present)");
      return;
    }
    int checked = 0;
    for (String name : circuitNames) {
      java.io.File circ = new java.io.File("generated/circ/" + name + ".circ");
      java.io.File specJson = new java.io.File("generated/examples/" + name + "_spec.json");
      if (!circ.exists() || !specJson.exists()) {
        System.out.println("[OK] official Logisim JAR simulation regression: SKIP missing " + name);
        continue;
      }
      try {
        CircuitSpec spec = com.logisim_ai.ai.CircuitSpecJsonParser.parse(
            java.nio.file.Files.readString(specJson.toPath()));
        com.logisim_ai.validation.OfficialSimulationVerifier.Report report =
            com.logisim_ai.validation.OfficialSimulationVerifier.verify(spec, circ);
        if (!report.passed()) {
          throw new IllegalStateException("Official JAR simulation failed for " + name + ": "
              + report.detail());
        }
        System.out.println("[OK] " + name + " official Logisim JAR simulation passed: rows="
            + report.rows().size());
        checked++;
      } catch (Exception e) {
        throw new IllegalStateException("Official JAR simulation regression failed for " + name, e);
      }
    }
    System.out.println("[OK] official Logisim JAR simulation regression passed: circuits=" + checked);
  }

  /**
   * Spec↔circuit connectivity audit over every Direct-routed generated circuit:
   * each intended net's endpoints must be one connected wire component with no
   * foreign driver. This is the static defense against the router's silent
   * fan-out cross-connection defect (the gray-counter failure class) that the
   * junction/floating/two-driver heuristics all miss.
   */
  private static void runNetlistConnectivityRegression() {
    java.io.File circDir = new java.io.File("generated/circ");
    java.io.File[] files = circDir.listFiles((dir, name) -> name.endsWith(".circ"));
    if (files == null) {
      throw new IllegalStateException("generated/circ directory not found");
    }
    int checked = 0;
    int skipped = 0;
    for (java.io.File circ : files) {
      String stem = circ.getName().replaceAll("\\.circ$", "");
      java.io.File specJson = new java.io.File("generated/examples/" + stem + "_spec.json");
      if (!specJson.exists()) {
        skipped++;
        continue;
      }
      try {
        CircuitSpec spec = com.logisim_ai.ai.CircuitSpecJsonParser.parse(
            java.nio.file.Files.readString(specJson.toPath()));
        if (!GenerateAndGate.usesDirectCompiler(spec)) {
          skipped++;
          continue;
        }
        com.cburch.logisim.Main.headless = true;
        System.setProperty("java.awt.headless", "true");
        com.cburch.logisim.file.Loader loader = new com.cburch.logisim.file.Loader(null);
        com.cburch.logisim.file.LogisimFile file = loader.openLogisimFile(circ);
        com.cburch.logisim.proj.Project project = new com.cburch.logisim.proj.Project(file);
        com.cburch.logisim.circuit.Circuit circuit = file.getMainCircuit();
        project.setCurrentCircuit(circuit);
        java.util.List<String> issues;
        try {
          issues = com.logisim_ai.validation.CircuitWiringChecker.auditAgainstSpec(spec, circuit);
        } finally {
          try {
            if (project.getSimulator() != null) {
              project.getSimulator().shutDown();
            }
          } catch (Exception ignored) {
            // best effort
          }
          file.stopAutosaveThread(false);
        }
        if (!issues.isEmpty()) {
          throw new IllegalStateException("connectivity audit failed for " + stem + ": "
              + String.join(" | ", issues.subList(0, Math.min(issues.size(), 5))));
        }
        checked++;
      } catch (Exception e) {
        throw new IllegalStateException("netlist-connectivity regression failed for " + stem, e);
      }
    }
    System.out.println("[OK] netlist-connectivity regression passed: circuits=" + checked
        + " skipped=" + skipped);
  }

  /** Clock-pulse sequence evidence for sequential circuits (real Clock + <SET>/<SEQ> vectors). */
  private static void runClockSequenceRegression() {
    String[][] cases = {
        {"counter_4bit", "cnt4c.vec", "11", "0"},
        {"counter_8bit", "cnt8c.vec", "34", "0"},
        {"dff", "dff.vec", "7", "0"},
        {"jkff", "jkff.vec", "9", "0"},
        {"tff", "tff.vec", "9", "0"},
        {"register_4bit", "reg4.vec", "7", "0"},
        {"shiftreg_4bit", "shreg.vec", "11", "0"},
        {"piso_4bit", "piso.vec", "11", "0"},
        {"srlatch", "srl.vec", "4", "0"},
        {"divider_4stage", "div4.vec", "33", "0"},
        {"johnson_4stage", "john4.vec", "17", "0"},
        {"dlatch", "dlatch.vec", "5", "0"},
        {"gray_4bit", "gray4.vec", "16", "0"},
        {"gray2bin_4bit", "g2b4.vec", "16", "0"},
        {"graycnt_3bit", "graycnt3.vec", "17", "0"},
        {"graycnt_4bit", "graycnt4.vec", "32", "0"},
        {"excess3_bcd", "e3.vec", "16", "0"},
        {"twoscomp_4bit", "twosc.vec", "16", "0"},
        {"traffic_light", "traffic.vec", "17", "0"},
        {"seqdet_101", "seqdet.vec", "17", "0"},
        {"bidirsreg_4bit", "bidirsreg.vec", "11", "0"},
        {"bcd_counter", "bcdcnt.vec", "23", "0"},
        {"accumulator_4bit", "acc4.vec", "25", "0"},
        {"mac_4bit", "mac4.vec", "14", "0"},
        {"edge_detector", "edgedet.vec", "10", "0"},
        {"oneshot_4clk", "oneshot.vec", "36", "0"},
        {"debouncer", "debounce.vec", "18", "0"},
        {"excess3tobcd", "e3b.vec", "10", "0"},
        {"updown_counter_4bit", "updown.vec", "17", "0"},
        {"lfsr_4bit", "lfsr.vec", "33", "0"},
        {"demux_4to1", "demux.vec", "8", "0"},
        {"mod7_counter", "mod7.vec", "17", "0"},
        {"mod10_counter", "mod10.vec", "21", "0"},
        {"mod60_counter", "mod60.vec", "121", "0"},
        {"digital_clock", "digclock.vec", "264", "0"},
        {"bcd2bin_2digit", "b2b.vec", "12", "0"},
        {"bin2bcd_4bit", "binbcd.vec", "16", "0"},
        {"tristate_buffer", "tristate.vec", "2", "0"},
        {"ring_counter", "ring.vec", "11", "0"},
        {"nine_complement", "ninecomp.vec", "10", "0"},
        {"seqdet_1101", "seqdet1101.vec", "19", "0"},
        // Wide combinational circuits whose prepared vectors were previously
        // orphaned (present on disk but bound to no regression row).
        {"bcd_adder", "bcdadd.vec", "6", "0"},
        {"comparator_4bit", "cmp4.vec", "6", "0"},
        {"subtractor_4bit", "sub4.vec", "6", "0"},
        {"decoder_4to16", "dec416.vec", "7", "0"},
        {"mux_8to1", "mux8.vec", "5", "0"},
        {"parity_8bit", "par8.vec", "6", "0"},
        {"encoder_8to3", "enc83.vec", "11", "0"},
        {"encoder_4to2", "enc42.vec", "7", "0"},
    };
    java.io.File jarProbe = new java.io.File(
        System.getenv().getOrDefault("CIRCUITARIUM_LOGISIM_JAR",
            com.logisim_ai.validation.OfficialSimulationVerifier.DEFAULT_LOGISIM_JAR));
    if (!jarProbe.exists()
        && !new java.io.File("third_party", "logisim-evolution/build/libs/logisim-evolution-4.2.0dev-all.jar")
            .exists()) {
      System.out.println("[OK] clock-sequence regression: SKIP (JAR not present)");
      return;
    }
    int checked = 0;
    for (String[] c : cases) {
      String name = c[0];
      java.io.File circ = new java.io.File("generated/circ/" + name + ".circ");
      java.io.File vec = new java.io.File("generated/" + c[1]);
      if (!circ.exists() || !vec.exists()) {
        System.out.println("[OK] clock-sequence regression: SKIP missing " + name);
        continue;
      }
      try {
        com.logisim_ai.validation.TestVectorRunner.Result result =
            com.logisim_ai.validation.TestVectorRunner.runVectors(circ, name, vec);
        if (result.passCount() != Integer.parseInt(c[2])
            || result.failCount() != Integer.parseInt(c[3])) {
          throw new IllegalStateException("unexpected pass/fail counts for " + name + ": "
              + result.passCount() + "/" + result.failCount());
        }
        System.out.println("[OK] " + name + " clock sequence passed: " + result.passCount() + " rows");
        checked++;
      } catch (Exception e) {
        throw new IllegalStateException("clock-sequence regression failed for " + name, e);
      }
    }
    System.out.println("[OK] clock-sequence regression passed: circuits=" + checked);
  }

  /**
   * Directed test-vector evidence for wide combinational circuits that exceed
   * the official exhaustive-table input limit (12). These circuits get a set of
   * hand-picked directed vectors covering boundary cases (all-0, all-1,
   * carry/borrow propagation) instead of being skipped.
   */
  private static void runDirectedVectorRegression() {
    String[][] cases = {
        {"adder_8bit", "adder8.vec", "12", "0"},
        {"alu_8bit", "alu8.vec", "12", "0"},
        {"divider_4x4", "divider4.vec", "20", "0"},
        {"multiplier_8x8", "mul8.vec", "16", "0"},
        {"subtractor_8bit", "sub8.vec", "20", "0"},
        {"comparator_8bit", "cmp8.vec", "16", "0"},
        {"bcd_adder_2digit", "bcd2.vec", "16", "0"},
        {"bcd_subtractor_2digit", "bcdsub2.vec", "16", "0"},
    };
    java.io.File jarProbe = new java.io.File(
        System.getenv().getOrDefault("CIRCUITARIUM_LOGISIM_JAR",
            com.logisim_ai.validation.OfficialSimulationVerifier.DEFAULT_LOGISIM_JAR));
    if (!jarProbe.exists()
        && !new java.io.File("third_party", "logisim-evolution/build/libs/logisim-evolution-4.2.0dev-all.jar")
            .exists()) {
      System.out.println("[OK] directed-vector regression: SKIP (JAR not present)");
      return;
    }
    int checked = 0;
    for (String[] c : cases) {
      String name = c[0];
      java.io.File circ = new java.io.File("generated/circ/" + name + ".circ");
      java.io.File vec = new java.io.File("generated/" + c[1]);
      if (!circ.exists() || !vec.exists()) {
        System.out.println("[OK] directed-vector regression: SKIP missing " + name);
        continue;
      }
      try {
        com.logisim_ai.validation.TestVectorRunner.Result result =
            com.logisim_ai.validation.TestVectorRunner.runVectors(circ, name, vec);
        if (result.passCount() != Integer.parseInt(c[2])
            || result.failCount() != Integer.parseInt(c[3])) {
          throw new IllegalStateException("unexpected pass/fail counts for " + name + ": "
              + result.passCount() + "/" + result.failCount());
        }
        System.out.println("[OK] " + name + " directed vectors passed: " + result.passCount() + " rows");
        checked++;
      } catch (Exception e) {
        throw new IllegalStateException("directed-vector regression failed for " + name, e);
      }
    }
    System.out.println("[OK] directed-vector regression passed: circuits=" + checked);
  }

  private static void runCase(Case testCase) {
    NaturalLanguageCircuitPlanner.PlanResult result =
        NaturalLanguageCircuitPlanner.plan(testCase.query());
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + testCase.query());
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    int combinations = 1 << testCase.inputs().size();
    for (int mask = 0; mask < combinations; mask++) {
      Map<String, Boolean> inputs = valuesFor(testCase.inputs(), mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, inputs);
      Boolean actual = outputs.get(testCase.output());
      if (actual == null) {
        throw new IllegalStateException("Missing output " + testCase.output()
            + " for query: " + testCase.query());
      }
      boolean expected = testCase.expected().test(inputs);
      if (actual != expected) {
        throw new IllegalStateException("Truth-table mismatch for " + testCase.query()
            + " inputs=" + inputs + " expected=" + expected + " actual=" + actual);
      }
    }
    System.out.println("[OK] " + testCase.query() + " truth table passed");
  }

  /** Functional-phrase multi-output: 求差 (D/BOUT) and 求和 (SUM/CARRY). */
  private static void runFunctionalMultiOutput() {
    NaturalLanguageCircuitPlanner.PlanResult sub = NaturalLanguageCircuitPlanner.plan("求差");
    if (sub == null) {
      throw new IllegalStateException("Planner did not match query: 求差");
    }
    CircuitSpec subSpec = sub.spec();
    CircuitSemanticValidator.requireValid(subSpec);
    List<String> subInputs = List.of("a", "b");
    for (int mask = 0; mask < (1 << subInputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(subInputs, mask);
      Map<String, Boolean> out = CircuitTruthTableEvaluator.evaluate(subSpec, values);
      boolean a = values.get("a"), b = values.get("b");
      if (out.get("d") != (a ^ b) || out.get("bout") != (!a && b)) {
        throw new IllegalStateException("求差 mismatch outputs=" + out + " expected d=" + (a ^ b)
            + " bout=" + (!a && b));
      }
    }
    System.out.println("[OK] 求差 truth table passed");

    NaturalLanguageCircuitPlanner.PlanResult add = NaturalLanguageCircuitPlanner.plan("求和");
    if (add == null) {
      throw new IllegalStateException("Planner did not match query: 求和");
    }
    CircuitSpec addSpec = add.spec();
    CircuitSemanticValidator.requireValid(addSpec);
    List<String> addInputs = List.of("a", "b", "cin");
    for (int mask = 0; mask < (1 << addInputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(addInputs, mask);
      Map<String, Boolean> out = CircuitTruthTableEvaluator.evaluate(addSpec, values);
      boolean a = values.get("a"), b = values.get("b"), cin = values.get("cin");
      boolean sum = a ^ b ^ cin;
      boolean carry = (a && b) || (cin && (a ^ b));
      if (out.get("sum") != sum || out.get("carry") != carry) {
        throw new IllegalStateException("求和 mismatch outputs=" + out + " expected sum=" + sum
            + " carry=" + carry);
      }
    }
    System.out.println("[OK] 求和 truth table passed");
  }

  /** Exhaustive 4→2 and 8→3 priority-encoder truth tables (in-process).
   *  Verifies the highest-priority asserted input is encoded on Y0..Yk and that
   *  G flags any assertion, over every input combination. */
  private static void runEncoderPriorityRegression() {
    checkPriorityEncoder("4 to 2 priority encoder", List.of("i0", "i1", "i2", "i3"),
        "y0", "y1", "g");
    checkPriorityEncoder("8 to 3 priority encoder",
        List.of("i0", "i1", "i2", "i3", "i4", "i5", "i6", "i7"), "y0", "y1", "y2", "g");
  }

  private static void checkPriorityEncoder(String query, List<String> inputs,
      String... ysAndG) {
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    int n = inputs.size();
    int gIdx = ysAndG.length - 1;
    for (int mask = 0; mask < (1 << n); mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> out = CircuitTruthTableEvaluator.evaluate(spec, values);
      int hi = -1;
      for (int k = 0; k < n; k++) {
        if (values.get(inputs.get(k))) {
          hi = k;
        }
      }
      boolean g = hi >= 0;
      if (out.get(ysAndG[gIdx]) != g) {
        throw new IllegalStateException(query + " G mismatch inputs=" + values
            + " outputs=" + out + " expected G=" + g);
      }
      int enc = Math.max(hi, 0);
      for (int bit = 0; bit < gIdx; bit++) {
        boolean expected = ((enc >> bit) & 1) == 1;
        if (out.get(ysAndG[bit]) != expected) {
          throw new IllegalStateException(query + " " + ysAndG[bit] + " mismatch inputs=" + values
              + " outputs=" + out + " expected=" + expected);
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: " + (1 << n) + " combos");
  }

  private static void runMultiOutputHalfAdder() {
    String query = "SUM=A xor B; CARRY=A and B";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    List<String> inputs = List.of("a", "b");
    for (int mask = 0; mask < (1 << inputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean expectedSum = values.get("a") ^ values.get("b");
      boolean expectedCarry = values.get("a") && values.get("b");
      if (outputs.get("sum") != expectedSum || outputs.get("carry") != expectedCarry) {
        throw new IllegalStateException("Multi-output half-adder mismatch inputs=" + values
            + " outputs=" + outputs
            + " expected SUM=" + expectedSum
            + " CARRY=" + expectedCarry);
      }
    }
    System.out.println("[OK] " + query + " truth table passed");
  }

  private static void runMultiOutputFullAdder() {
    String query = "SUM=A xor B xor Cin; Cout=(A and B) or (Cin and (A xor B))";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    List<String> inputs = List.of("a", "b", "cin");
    for (int mask = 0; mask < (1 << inputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean a = values.get("a");
      boolean b = values.get("b");
      boolean cin = values.get("cin");
      boolean expectedSum = a ^ b ^ cin;
      boolean expectedCout = (a && b) || (cin && (a ^ b));
      if (outputs.get("sum") != expectedSum || outputs.get("cout") != expectedCout) {
        throw new IllegalStateException("Multi-output full-adder mismatch inputs=" + values
            + " outputs=" + outputs
            + " expected SUM=" + expectedSum
            + " Cout=" + expectedCout);
      }
    }
    System.out.println("[OK] " + query + " truth table passed");
  }

  /**
   * N-bit ALU exhaustive truth table (in-process): verifies all four functions
   * AND/OR/ADD/SUB across every A/B/Cin combination, plus the Cout and Z flags.
   */
  private static void runAluTruthTable() {
    int bits = 4;
    String query = bits + "位ALU";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    List<String> inputs = new java.util.ArrayList<>();
    for (int i = 0; i < bits; i++) {
      inputs.add("a" + i);
    }
    for (int i = 0; i < bits; i++) {
      inputs.add("b" + i);
    }
    inputs.add("s1");
    inputs.add("s0");
    inputs.add("cin");

    int combinations = 1 << inputs.size();
    for (int mask = 0; mask < combinations; mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean s1 = values.get("s1");
      boolean s0 = values.get("s0");
      boolean cin = values.get("cin");
      int a = 0;
      int b = 0;
      for (int i = 0; i < bits; i++) {
        if (Boolean.TRUE.equals(values.get("a" + i))) a |= (1 << i);
        if (Boolean.TRUE.equals(values.get("b" + i))) b |= (1 << i);
      }
      int expectedResult;
      boolean expectedCout;
      if (!s1 && !s0) { // AND
        expectedResult = a & b;
        expectedCout = false;
      } else if (!s1 && s0) { // OR
        expectedResult = a | b;
        expectedCout = false;
      } else if (s1 && !s0) { // ADD
        int sum = a + b + (cin ? 1 : 0);
        expectedResult = sum & ((1 << bits) - 1);
        expectedCout = (sum >> bits) != 0;
      } else { // SUB: a - b, borrow out (Cout = borrow)
        int diff = a - b;
        expectedResult = diff & ((1 << bits) - 1);
        expectedCout = diff < 0;
      }
      for (int i = 0; i < bits; i++) {
        boolean actualBit = Boolean.TRUE.equals(outputs.get("r" + i));
        boolean expectedBit = ((expectedResult >> i) & 1) == 1;
        if (actualBit != expectedBit) {
          throw new IllegalStateException("ALU R" + i + " mismatch mask=" + mask + " inputs=" + values
              + " expected=" + expectedResult + " actual=" + outputs);
        }
      }
      if (!Boolean.valueOf(expectedCout).equals(outputs.get("cout"))) {
        throw new IllegalStateException("ALU Cout mismatch mask=" + mask + " inputs=" + values
            + " expectedCout=" + expectedCout + " actual=" + outputs);
      }
      boolean expectedZ = expectedResult == 0;
      if (!Boolean.valueOf(expectedZ).equals(outputs.get("z"))) {
        throw new IllegalStateException("ALU Z mismatch mask=" + mask + " inputs=" + values
            + " expectedZ=" + expectedZ + " actual=" + outputs);
      }
    }
    System.out.println("[OK] " + query + " exhaustive truth table passed: combinations=" + combinations);
  }

  /** 1-bit magnitude comparator described functionally ("谁大"): GT/EQ/LT. */
  private static void runMagnitudeComparatorRegression() {
    String query = "比较两个数谁大";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    List<String> inputs = List.of("a", "b");
    for (int mask = 0; mask < 4; mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean a = values.get("a");
      boolean b = values.get("b");
      if (!Boolean.valueOf(a && !b).equals(outputs.get("f_gt"))) {
        throw new IllegalStateException("GT mismatch inputs=" + values + " outputs=" + outputs);
      }
      if (!Boolean.valueOf(a == b).equals(outputs.get("f_eq"))) {
        throw new IllegalStateException("EQ mismatch inputs=" + values + " outputs=" + outputs);
      }
      if (!Boolean.valueOf(!a && b).equals(outputs.get("f_lt"))) {
        throw new IllegalStateException("LT mismatch inputs=" + values + " outputs=" + outputs);
      }
    }
    System.out.println("[OK] " + query + " truth table passed (GT/EQ/LT)");
  }

  /** Exhaustive structural 4-bit magnitude comparator (in-process): every
   *  A×B pair (65536 combos) checks GT/EQ/LT against integer ordering. Requires
   *  XNOR evaluation, which the in-process evaluator now supports; previously
   *  comparator_4bit only had JAR-gated .vec evidence. */
  private static void runStructuralComparatorRegression() {
    String query = "4 bit comparator";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    List<String> inputs = List.of("a0", "a1", "a2", "a3", "b0", "b1", "b2", "b3");
    for (int a = 0; a < 16; a++) {
      for (int b = 0; b < 16; b++) {
        Map<String, Boolean> values = new java.util.HashMap<>();
        for (int i = 0; i < 4; i++) {
          values.put("a" + i, ((a >> i) & 1) == 1);
          values.put("b" + i, ((b >> i) & 1) == 1);
        }
        Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
        if (outputs.get("gt") != (a > b)) {
          throw new IllegalStateException(query + " GT mismatch A=" + a + " B=" + b
              + " outputs=" + outputs + " expected gt=" + (a > b));
        }
        if (outputs.get("eq") != (a == b)) {
          throw new IllegalStateException(query + " EQ mismatch A=" + a + " B=" + b);
        }
        if (outputs.get("lt") != (a < b)) {
          throw new IllegalStateException(query + " LT mismatch A=" + a + " B=" + b);
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 65536 combos (GT/EQ/LT)");
  }

  /** 4-bit password/keypad lock ("开锁/密码"): F=1 exactly when A==B on all 4 bits. */
  private static void runPasswordLockRegression() {
    String query = "密码锁开锁电路";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    List<String> inputs = List.of("a0", "a1", "a2", "a3", "b0", "b1", "b2", "b3");
    int unlocked = 0;
    for (int mask = 0; mask < 256; mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      int a = bitField(values, "a", 4);
      int b = bitField(values, "b", 4);
      boolean expected = a == b;
      if (expected) unlocked++;
      if (!Boolean.valueOf(expected).equals(outputs.get("f"))) {
        throw new IllegalStateException("Password lock mismatch inputs=" + values
            + " expected=" + expected + " outputs=" + outputs);
      }
    }
    System.out.println("[OK] " + query + " truth table passed: unlocked=" + unlocked + "/256");
  }

  /** 1-digit BCD subtractor ("BCD减法器"): D = A-B mod 10, Borrow = (A<B). */
  private static void runBcdSubtractorRegression() {
    String query = "BCD减法器";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    List<String> inputs = List.of("a0", "a1", "a2", "a3", "b0", "b1", "b2", "b3",
        "gnd", "one");
    // Exhaustive over all valid BCD inputs A,B in 0..9 (gnd/one are constants 0/1).
    for (int a = 0; a < 10; a++) {
      for (int b = 0; b < 10; b++) {
        Map<String, Boolean> values = new java.util.LinkedHashMap<>();
        for (int i = 0; i < 4; i++) {
          values.put("a" + i, ((a >> i) & 1) == 1);
          values.put("b" + i, ((b >> i) & 1) == 1);
        }
        values.put("gnd", false);
        values.put("one", true);
        Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
        int d = 0;
        for (int i = 0; i < 4; i++) {
          if (Boolean.TRUE.equals(outputs.get("d" + i))) d |= (1 << i);
        }
        int expectedD = ((a - b) % 10 + 10) % 10;
        boolean expectedBorrow = a < b;
        if (d != expectedD || !Boolean.valueOf(expectedBorrow).equals(outputs.get("borrow"))) {
          throw new IllegalStateException("BCD subtractor mismatch A=" + a + " B=" + b
              + " d=" + d + " (expected " + expectedD + ") borrow=" + outputs.get("borrow")
              + " (expected " + expectedBorrow + ")");
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 100 BCD pairs (mod-10 + borrow)");
  }

  /** N-bit barrel shifter ("桶形移位器"): Y = D logical-left-shifted by SH (0..N-1). */
  private static void runBarrelShifterRegression() {
    String query = "4位桶形移位器";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    List<String> inputs = List.of("d0", "d1", "d2", "d3", "sh0", "sh1", "gnd");
    int shBits = 2;
    for (int d = 0; d < 16; d++) {
      for (int sh = 0; sh < (1 << shBits); sh++) {
        Map<String, Boolean> values = new java.util.LinkedHashMap<>();
        for (int i = 0; i < 4; i++) {
          values.put("d" + i, ((d >> i) & 1) == 1);
        }
        for (int k = 0; k < shBits; k++) {
          values.put("sh" + k, ((sh >> k) & 1) == 1);
        }
        values.put("gnd", false);
        Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
        int expected = sh >= 4 ? 0 : (d << sh) & 0xF;
        int y = 0;
        for (int i = 0; i < 4; i++) {
          if (Boolean.TRUE.equals(outputs.get("y" + i))) y |= (1 << i);
        }
        if (y != expected) {
          throw new IllegalStateException("Barrel shifter mismatch D=" + d + " SH=" + sh
              + " y=" + y + " (expected " + expected + ")");
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 64 D×SH combos (logical left shift)");
  }

  /** N-bit rotate shifter ("循环移位器"): Y = rotate_left(D, SH) / rotate_right(D, SH). */
  private static void runRotateShifterRegression() {
    String[][] cases = {
        {"4位循环左移", "left"},
        {"4位循环右移", "right"},
        {"4 bit rotate left", "left"},
        {"4 bit rotate right", "right"},
    };
    for (String[] c : cases) {
      String query = c[0];
      boolean left = c[1].equals("left");
      NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
      if (result == null) {
        throw new IllegalStateException("Planner did not match query: " + query);
      }
      CircuitSpec spec = result.spec();
      CircuitSemanticValidator.requireValid(spec);
      if (!spec.name().contains(left ? "_left" : "_right")) {
        throw new IllegalStateException("Rotate direction mismatch for '" + query + "': spec="
            + spec.name() + " expected " + (left ? "left" : "right"));
      }
      List<String> inputs = List.of("d0", "d1", "d2", "d3", "sh0", "sh1");
      for (int d = 0; d < 16; d++) {
        for (int sh = 0; sh < 4; sh++) {
          Map<String, Boolean> values = new java.util.LinkedHashMap<>();
          for (int i = 0; i < 4; i++) {
            values.put("d" + i, ((d >> i) & 1) == 1);
          }
          for (int k = 0; k < 2; k++) {
            values.put("sh" + k, ((sh >> k) & 1) == 1);
          }
          Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
          int expected = left
              ? (((d << sh) | (d >>> (4 - sh))) & 0xF)
              : (((d >>> sh) | (d << (4 - sh))) & 0xF);
          int y = 0;
          for (int i = 0; i < 4; i++) {
            if (Boolean.TRUE.equals(outputs.get("y" + i))) y |= (1 << i);
          }
          if (y != expected) {
            throw new IllegalStateException("Rotate mismatch " + query + " D=" + d + " SH=" + sh
                + " y=" + y + " (expected " + expected + ")");
          }
        }
      }
      System.out.println("[OK] " + query + " truth table passed: 64 D×SH combos");
    }
  }

  /** Right-shift templates: LSR (zero fill) and SAR (sign fill), 4-bit each. */
  private static void runRightShiftRegression() {
    String[][] cases = {
        {"4位逻辑右移", "shift_right_4bit"},
        {"4 bit logical shift right", "shift_right_4bit"},
        {"4位算术右移", "sar_4bit"},
        {"4 bit arithmetic shift right", "sar_4bit"},
    };
    for (String[] c : cases) {
      String query = c[0];
      String expectName = c[1];
      NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
      if (result == null) {
        throw new IllegalStateException("Planner did not match query: " + query);
      }
      CircuitSpec spec = result.spec();
      CircuitSemanticValidator.requireValid(spec);
      if (!spec.name().equals(expectName)) {
        throw new IllegalStateException("Right-shift name mismatch for '" + query + "': spec="
            + spec.name() + " expected " + expectName);
      }
      boolean arithmetic = expectName.startsWith("sar_");
      for (int d = 0; d < 16; d++) {
        for (int sh = 0; sh < 4; sh++) {
          Map<String, Boolean> values = new java.util.LinkedHashMap<>();
          for (int i = 0; i < 4; i++) {
            values.put("d" + i, ((d >> i) & 1) == 1);
          }
          for (int k = 0; k < 2; k++) {
            values.put("sh" + k, ((sh >> k) & 1) == 1);
          }
          values.put("gnd", false); // LSR zero-fill source; SAR spec has no gnd port (ignored)
          Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
          int expected = arithmetic
              ? ((d << 28) >> (28 + sh)) & 0xF   // sign-extend bit 3, then shift
              : (d >>> sh) & 0xF;                // zero fill
          int y = 0;
          for (int i = 0; i < 4; i++) {
            if (Boolean.TRUE.equals(outputs.get("y" + i))) y |= (1 << i);
          }
          if (y != expected) {
            throw new IllegalStateException("Right-shift mismatch " + query + " D=" + d + " SH=" + sh
                + " y=" + y + " (expected " + expected + ")");
          }
        }
      }
      System.out.println("[OK] " + query + " truth table passed: 64 D×SH combos");
    }
  }

  /** 4-bit restoring divider ("除法器"): Q = A/B, R = A mod B, full in-process sweep. */
  private static void runDividerRegression() {
    String query = "4位除法器";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    if (!spec.name().equals("divider_4x4")) {
      throw new IllegalStateException("Divider name mismatch: " + spec.name());
    }
    for (int a = 0; a < 16; a++) {
      for (int b = 0; b < 16; b++) {
        Map<String, Boolean> values = new java.util.LinkedHashMap<>();
        for (int i = 0; i < 4; i++) {
          values.put("a" + i, ((a >> i) & 1) == 1);
          values.put("b" + i, ((b >> i) & 1) == 1);
        }
        values.put("gnd", false);
        Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
        int expectedQ = b == 0 ? 15 : a / b;
        int expectedR = b == 0 ? a : a % b;
        int q = 0;
        int r = 0;
        for (int i = 0; i < 4; i++) {
          if (Boolean.TRUE.equals(outputs.get("q" + i))) q |= (1 << i);
          if (Boolean.TRUE.equals(outputs.get("r" + i))) r |= (1 << i);
        }
        if (q != expectedQ || r != expectedR) {
          throw new IllegalStateException("Divider mismatch A=" + a + " B=" + b
              + " q=" + q + " (expected " + expectedQ + ") r=" + r + " (expected " + expectedR + ")");
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 256 A×B combos (B=0 → Q=15,R=A)");
  }

  /** 4x4 multiplier ("4 bit multiplier"): full in-process sweep, p == a*b for all 256. */
  private static void runMultiplierRegression() {
    String query = "4 bit multiplier";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    if (!spec.name().equals("multiplier_4x4")) {
      throw new IllegalStateException("Multiplier name mismatch: " + spec.name());
    }
    for (int a = 0; a < 16; a++) {
      for (int b = 0; b < 16; b++) {
        Map<String, Boolean> values = new java.util.LinkedHashMap<>();
        for (int i = 0; i < 4; i++) {
          values.put("a" + i, ((a >> i) & 1) == 1);
          values.put("b" + i, ((b >> i) & 1) == 1);
        }
        values.put("gnd", false);
        Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
        int expected = a * b;
        int p = 0;
        for (int i = 0; i < 8; i++) {
          if (Boolean.TRUE.equals(outputs.get("p" + i))) p |= (1 << i);
        }
        if (p != expected) {
          throw new IllegalStateException("Multiplier mismatch A=" + a + " B=" + b
              + " p=" + p + " (expected " + expected + ")");
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 256 A×B combos");
  }

  /** 1-digit BCD adder: exhaustive A,B in 0..9 and cin in {0,1}, z=false. */
  private static void runBcd1Regression() {
    String query = "BCD加法器";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    if (!spec.name().equals("bcd_adder")) {
      throw new IllegalStateException("bcd_adder name mismatch: " + spec.name());
    }
    for (int a = 0; a < 10; a++) {
      for (int b = 0; b < 10; b++) {
        for (int cin = 0; cin <= 1; cin++) {
          Map<String, Boolean> values = new java.util.LinkedHashMap<>();
          for (int i = 0; i < 4; i++) {
            values.put("a" + i, ((a >> i) & 1) == 1);
            values.put("b" + i, ((b >> i) & 1) == 1);
          }
          values.put("cin", cin == 1);
          values.put("z", false);
          Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
          int total = a + b + cin;
          int expectedS = total <= 9 ? total : total - 10;
          boolean expectedCout = total > 9;
          int s = 0;
          for (int i = 0; i < 4; i++) {
            if (Boolean.TRUE.equals(outputs.get("s" + i))) s |= (1 << i);
          }
          if (s != expectedS || !Boolean.valueOf(expectedCout).equals(outputs.get("cout"))) {
            throw new IllegalStateException("bcd_adder mismatch A=" + a + " B=" + b + " Cin=" + cin
                + " s=" + s + " (expected " + expectedS + ") cout=" + outputs.get("cout")
                + " (expected " + expectedCout + ")");
          }
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 200 A×B×Cin combos");
  }

  /** N-bit parity ("4 bit parity"): XOR-cascade accumulator, p = XOR of D0..D3. */
  private static void runParityRegression() {
    String query = "4 bit parity";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    if (!spec.name().equals("parity_4bit")) {
      throw new IllegalStateException("parity name mismatch: " + spec.name());
    }
    for (int mask = 0; mask < 16; mask++) {
      Map<String, Boolean> values = new java.util.LinkedHashMap<>();
      boolean expected = false;
      for (int i = 0; i < 4; i++) {
        boolean bit = ((mask >> i) & 1) == 1;
        values.put("d" + i, bit);
        expected ^= bit;
      }
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      if (!Boolean.valueOf(expected).equals(outputs.get("p"))) {
        throw new IllegalStateException("parity mismatch mask=" + mask + " outputs=" + outputs);
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 16 combos");
  }

  /** 2-digit BCD adder ("两位BCD加法器"): full in-process sweep of valid BCD digits. */
  private static void runBcd2Regression() {
    String query = "两位BCD加法器";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    if (!spec.name().equals("bcd_adder_2digit")) {
      throw new IllegalStateException("2-digit BCD adder name mismatch: " + spec.name());
    }
    int combos = 0;
    for (int al = 0; al < 10; al++) {
      for (int ah = 0; ah < 10; ah++) {
        for (int bl = 0; bl < 10; bl++) {
          for (int bh = 0; bh < 10; bh++) {
            for (int cin = 0; cin <= 1; cin++) {
              Map<String, Boolean> values = new java.util.LinkedHashMap<>();
              putNibble(values, "a", al);
              putNibble(values, "a", ah, 4);
              putNibble(values, "b", bl);
              putNibble(values, "b", bh, 4);
              values.put("cin", cin == 1);
              values.put("z", false);
              Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
              int total = (ah * 10 + al) + (bh * 10 + bl) + cin;
              int sl = total % 10;
              int sh = (total / 10) % 10;
              int cout = total / 100;
              int s = 0;
              for (int i = 0; i < 8; i++) {
                if (Boolean.TRUE.equals(outputs.get("s" + i))) s |= (1 << i);
              }
              int expectedBits = sl | (sh << 4);
              boolean expectedCout = cout == 1;
              if (s != expectedBits
                  || !Boolean.valueOf(expectedCout).equals(outputs.get("cout"))) {
                throw new IllegalStateException("2-digit BCD adder mismatch A=" + ah + al
                    + " B=" + bh + bl + " cin=" + cin + " s=" + s
                    + " (expected " + expectedBits + ") cout=" + outputs.get("cout")
                    + " (expected " + expectedCout + ")");
              }
              combos++;
            }
          }
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: " + combos + " combos");
  }

  /** 2-digit BCD subtractor ("两位BCD减法器"): full in-process sweep of valid BCD digits. */
  private static void runBcdSub2Regression() {
    String query = "两位BCD减法器";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    if (!spec.name().equals("bcd_subtractor_2digit")) {
      throw new IllegalStateException("2-digit BCD subtractor name mismatch: " + spec.name());
    }
    int combos = 0;
    for (int al = 0; al < 10; al++) {
      for (int ah = 0; ah < 10; ah++) {
        for (int bl = 0; bl < 10; bl++) {
          for (int bh = 0; bh < 10; bh++) {
            for (int bin = 0; bin <= 1; bin++) {
              Map<String, Boolean> values = new java.util.LinkedHashMap<>();
              putNibble(values, "a", al);
              putNibble(values, "a", ah, 4);
              putNibble(values, "b", bl);
              putNibble(values, "b", bh, 4);
              values.put("bin", bin == 1);
              values.put("gnd", false);
              Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
              int a = ah * 10 + al;
              int b = bh * 10 + bl;
              int diff = a - b - bin;
              boolean borrow = diff < 0;
              int d = ((diff % 100) + 100) % 100;
              int dLow = d % 10;
              int dHigh = d / 10;
              int got = 0;
              for (int i = 0; i < 8; i++) {
                if (Boolean.TRUE.equals(outputs.get("d" + i))) got |= (1 << i);
              }
              int expectedBits = dLow | (dHigh << 4);
              if (got != expectedBits
                  || !Boolean.valueOf(borrow).equals(outputs.get("borrow"))) {
                throw new IllegalStateException("2-digit BCD subtractor mismatch A=" + ah + al
                    + " B=" + bh + bl + " bin=" + bin + " d=" + got
                    + " (expected " + expectedBits + ") borrow=" + outputs.get("borrow")
                    + " (expected " + borrow + ")");
              }
              combos++;
            }
          }
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: " + combos + " combos");
  }

  /** Hex 7-segment display ("十六进制转七段"): all 16 values vs the hex segment table. */
  private static void runHex7SegRegression() {
    String query = "十六进制转七段";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    if (!spec.name().equals("hex7seg")) {
      throw new IllegalStateException("hex7seg name mismatch: " + spec.name());
    }
    int[][] segs = {
      {0, 2, 3, 5, 6, 7, 8, 9, 10, 12, 14, 15},
      {0, 1, 2, 3, 4, 7, 8, 9, 10, 13},
      {0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13},
      {0, 2, 3, 5, 6, 8, 9, 10, 11, 12, 13, 14},
      {0, 2, 6, 8, 10, 11, 12, 13, 14, 15},
      {0, 4, 5, 6, 8, 9, 10, 11, 12, 14, 15},
      {2, 3, 4, 5, 6, 8, 9, 10, 11, 13, 14, 15},
    };
    for (int v = 0; v < 16; v++) {
      Map<String, Boolean> values = new java.util.LinkedHashMap<>();
      for (int i = 0; i < 4; i++) {
        values.put("b" + i, ((v >> i) & 1) == 1);
      }
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      for (int s = 0; s < 7; s++) {
        boolean expected = false;
        for (int m : segs[s]) {
          if (m == v) {
            expected = true;
            break;
          }
        }
        Boolean actual = outputs.get("seg" + s);
        if (actual == null || actual != expected) {
          throw new IllegalStateException("hex7seg mismatch value=" + v + " seg=" + s
              + " expected=" + expected + " actual=" + actual);
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 16 values × 7 segments");
  }

  /** BCD 7-segment decoder: 16 values × 7 segments (values 10..15 all-off). */
  private static void runBcd7SegRegression() {
    String query = "bcd to 7 segment";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    if (!spec.name().equals("bcd7seg")) {
      throw new IllegalStateException("bcd7seg name mismatch: " + spec.name());
    }
    int[][] segs = {
      {0, 2, 3, 5, 6, 7, 8, 9},
      {0, 1, 2, 3, 4, 7, 8, 9},
      {0, 1, 3, 4, 5, 6, 7, 8, 9},
      {0, 2, 3, 5, 6, 8, 9},
      {0, 2, 6, 8},
      {0, 4, 5, 6, 8, 9},
      {2, 3, 4, 5, 6, 8, 9},
    };
    for (int v = 0; v < 16; v++) {
      Map<String, Boolean> values = new java.util.LinkedHashMap<>();
      for (int i = 0; i < 4; i++) {
        values.put("b" + i, ((v >> i) & 1) == 1);
      }
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      for (int s = 0; s < 7; s++) {
        boolean expected = false;
        for (int m : segs[s]) {
          if (m == v) {
            expected = true;
            break;
          }
        }
        Boolean actual = outputs.get("seg" + s);
        if (actual == null || actual != expected) {
          throw new IllegalStateException("bcd7seg mismatch value=" + v + " seg=" + s
              + " expected=" + expected + " actual=" + actual);
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 16 values × 7 segments");
  }

  /** 4-bit squarer ("4位平方器"): all 16 A values, P = A*A. */
  private static void runSquarerRegression() {
    String query = "4位平方器";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    if (!spec.name().equals("squarer_4bit")) {
      throw new IllegalStateException("squarer name mismatch: " + spec.name());
    }
    for (int a = 0; a < 16; a++) {
      Map<String, Boolean> values = new java.util.LinkedHashMap<>();
      for (int i = 0; i < 4; i++) {
        values.put("a" + i, ((a >> i) & 1) == 1);
      }
      values.put("gnd", false);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      int expected = a * a;
      int p = 0;
      for (int i = 0; i < 8; i++) {
        if (Boolean.TRUE.equals(outputs.get("p" + i))) p |= (1 << i);
      }
      if (p != expected) {
        throw new IllegalStateException("Squarer mismatch A=" + a + " p=" + p
            + " (expected " + expected + ")");
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 16 A values (P=A²)");
  }

  /** Excess-3 adder ("余3码加法器"): full sweep of valid excess-3 digit pairs. */
  private static void runExcess3AdderRegression() {
    String query = "余3码加法器";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    if (!spec.name().equals("excess3_adder")) {
      throw new IllegalStateException("excess3_adder name mismatch: " + spec.name()
          + " (query resolved to the converter?)");
    }
    for (int a = 3; a <= 12; a++) { // valid excess-3 digits (decimal 0..9)
      for (int b = 3; b <= 12; b++) {
        Map<String, Boolean> values = new java.util.LinkedHashMap<>();
        for (int i = 0; i < 4; i++) {
          values.put("a" + i, ((a >> i) & 1) == 1);
          values.put("b" + i, ((b >> i) & 1) == 1);
        }
        values.put("one", true);
        values.put("gnd", false);
        Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
        int da = a - 3;
        int db = b - 3;
        int ds = da + db;
        int expectedS = ds <= 9 ? ds + 3 : ds - 10 + 3;
        boolean expectedCout = ds > 9;
        int s = 0;
        for (int i = 0; i < 4; i++) {
          if (Boolean.TRUE.equals(outputs.get("s" + i))) s |= (1 << i);
        }
        if (s != expectedS || !Boolean.valueOf(expectedCout).equals(outputs.get("cout"))) {
          throw new IllegalStateException("Excess-3 adder mismatch A=" + a + " B=" + b
              + " s=" + s + " (expected " + expectedS + ") cout=" + outputs.get("cout")
              + " (expected " + expectedCout + ")");
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 100 excess-3 digit pairs");
  }

  /** Saturating adder ("饱和加法器"): S = min(A+B, 15), full 4-bit sweep. */
  private static void runSaturatingAdderRegression() {
    String query = "饱和加法器";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    if (!spec.name().equals("satadd_4bit")) {
      throw new IllegalStateException("saturating adder name mismatch: " + spec.name());
    }
    for (int a = 0; a < 16; a++) {
      for (int b = 0; b < 16; b++) {
        Map<String, Boolean> values = new java.util.LinkedHashMap<>();
        for (int i = 0; i < 4; i++) {
          values.put("a" + i, ((a >> i) & 1) == 1);
          values.put("b" + i, ((b >> i) & 1) == 1);
        }
        values.put("gnd", false);
        Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
        int expected = Math.min(a + b, 15);
        int s = 0;
        for (int i = 0; i < 4; i++) {
          if (Boolean.TRUE.equals(outputs.get("s" + i))) s |= (1 << i);
        }
        if (s != expected) {
          throw new IllegalStateException("Saturating adder mismatch A=" + a + " B=" + b
              + " s=" + s + " (expected " + expected + ")");
        }
      }
    }
    System.out.println("[OK] " + query + " truth table passed: 256 A×B combos (S=min(A+B,15))");
  }

  /** Write a BCD nibble value into a value map under prefix (+ optional bit offset). */
  private static void putNibble(Map<String, Boolean> values, String prefix, int v, int offset) {
    for (int i = 0; i < 4; i++) {
      values.put(prefix + (i + offset), ((v >> i) & 1) == 1);
    }
  }

  private static void putNibble(Map<String, Boolean> values, String prefix, int v) {
    putNibble(values, prefix, v, 0);
  }

  /** English structural-template queries must all resolve to a valid spec. */
  private static void runEnglishTemplateQueriesRegression() {
    String[] queries = {
        "half adder", "full adder", "4 bit adder", "8 bit adder",
        "3 bit comparator", "4 to 1 mux", "8 to 1 mux", "2 to 4 decoder",
        "1 bit subtractor", "4 bit subtractor", "8 to 3 priority encoder",
        "3 to 8 decoder", "4 bit multiplier", "2 bit multiplier", "8 bit multiplier",
        "priority encoder", "even parity checker", "odd parity",
        "7 segment display", "bcd to 7 segment", "d flip flop", "jk flip flop",
        "t flip flop", "d latch", "4 bit register", "shift register",
        "ring counter", "johnson counter", "mod 5 counter", "up down counter",
        "decade counter", "frequency divider", "lfsr", "pseudo random",
        "gray code counter", "3 bit gray counter", "4-bit gray code counter",
        "tri state buffer", "traffic light", "101 sequence detector", "alu",
        "arithmetic logic unit", "bcd adder", "bcd subtractor", "gray code",
        "excess 3", "two's complement", "9's complement", "demultiplexer",
        "1 to 4 demux", "barrel shifter", "4 bit barrel shifter",
        "8 bit subtractor", "8 bit comparator", "2 digit bcd adder",
        "2 digit bcd subtractor", "hex to 7 segment", "4 bit squarer", "squarer",
        "excess 3 adder", "accumulator", "saturating adder",
        "multiply accumulate", "4 bit multiply accumulate", "mac unit", "4 bit mac",
        "edge detector", "rising edge detector", "falling edge detector",
        "one shot", "monostable", "4 cycle pulse generator", "pulse generator",
        "debounce", "debouncer", "button debounce",
        "4 bit rotate left", "rotate right",
        "4 bit shift right", "logical shift right", "arithmetic shift right",
        "4 bit division circuit", "division circuit",
        // English functional descriptions (BoolFunctionSynthesizer mappings)
        "two numbers are different", "find the maximum of two numbers",
        "find the minimum of two numbers", "subtract two numbers",
        "multiply two numbers", "detect non-zero", "detect all zeros",
        "even number detector", "odd number detector",
        "staircase light switch", "two switches control one light",
        "compare which is bigger", "3 input nand", "3 to 1 voter",
        "exactly one of three", "at least two of three"
    };
    int checked = 0;
    for (String query : queries) {
      NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
      if (result == null) {
        throw new IllegalStateException("English template query did not match: " + query);
      }
      CircuitSpec spec = result.spec();
      CircuitSemanticValidator.requireValid(spec);
      checked++;
    }
    System.out.println("[OK] English template queries regression passed: queries=" + checked);
  }

  /** Extract an N-bit little-endian integer from a value map under a prefix. */
  private static int bitField(Map<String, Boolean> values, String prefix, int bits) {
    int v = 0;
    for (int i = 0; i < bits; i++) {
      if (Boolean.TRUE.equals(values.get(prefix + i))) v |= (1 << i);
    }
    return v;
  }

  private static void runCommutativeReuse() {
    String query = "X=A and B; Y=B and A; Z=(C xor A) xor B";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    long andCount = countFactory(spec, "AND Gate");
    long xorCount = countFactory(spec, "XOR Gate");
    if (andCount != 1 || xorCount != 2) {
      throw new IllegalStateException("Commutative normalization failed for " + query
          + " AND count=" + andCount
          + " XOR count=" + xorCount);
    }

    List<String> inputs = List.of("a", "b", "c");
    for (int mask = 0; mask < (1 << inputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean expectedAnd = values.get("a") && values.get("b");
      boolean expectedXor = values.get("a") ^ values.get("b") ^ values.get("c");
      if (outputs.get("x") != expectedAnd
          || outputs.get("y") != expectedAnd
          || outputs.get("z") != expectedXor) {
        throw new IllegalStateException("Commutative reuse mismatch inputs=" + values
            + " outputs=" + outputs
            + " expected X/Y=" + expectedAnd
            + " Z=" + expectedXor);
      }
    }
    System.out.println("[OK] " + query + " normalization and truth table passed");
  }

  private static long countFactory(CircuitSpec spec, String factoryName) {
    return spec.components().stream()
        .filter(component -> component.factoryName().equals(factoryName))
        .count();
  }

  private static void runNandOnlySynthesis() {
    String query = "Y=A xor B only NAND";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    long nonNandGates = spec.components().stream()
        .filter(component -> !component.factoryName().equals("NAND Gate"))
        .count();
    if (nonNandGates != 0) {
      throw new IllegalStateException("NAND-only query generated non-NAND gates: " + query);
    }

    List<String> inputs = List.of("a", "b");
    for (int mask = 0; mask < (1 << inputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean expected = values.get("a") ^ values.get("b");
      if (outputs.get("y") != expected) {
        throw new IllegalStateException("NAND-only XOR mismatch inputs=" + values
            + " outputs=" + outputs
            + " expected Y=" + expected);
      }
    }
    System.out.println("[OK] " + query + " NAND-only truth table passed");
  }

  private static void runTtl7400Synthesis() {
    String query = "Y=A xor B use 7400";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    List<String> labels = spec.components().stream()
        .filter(component -> component.factoryName().equals("NAND Gate"))
        .map(component -> component.label())
        .toList();
    if (!labels.equals(List.of("U1A", "U1B", "U1C", "U1D"))) {
      throw new IllegalStateException("7400 mapping labels mismatch: " + labels);
    }
    Ttl7400Mapper.requireContiguousAssignments(spec);

    long nonNandGates = spec.components().stream()
        .filter(component -> !component.factoryName().equals("NAND Gate"))
        .count();
    if (nonNandGates != 0) {
      throw new IllegalStateException("7400 query generated non-NAND gates: " + query);
    }

    List<String> inputs = List.of("a", "b");
    for (int mask = 0; mask < (1 << inputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean expected = values.get("a") ^ values.get("b");
      if (outputs.get("y") != expected) {
        throw new IllegalStateException("7400 XOR mismatch inputs=" + values
            + " outputs=" + outputs
            + " expected Y=" + expected);
      }
    }
    System.out.println("[OK] " + query + " 7400-labeled truth table passed");
  }

  private static void runTtl7486Synthesis() {
    String query = "Y=A xor B use 7486";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    if (result.intent() != ImplementationIntent.CHIP_LEVEL_CIRCUIT_IMPLEMENTATION) {
      throw new IllegalStateException("7486 query intent mismatch: " + result.intent());
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    List<String> labels = spec.components().stream()
        .filter(component -> component.factoryName().equals("XOR Gate"))
        .map(component -> component.label())
        .toList();
    if (!labels.equals(List.of("U1A"))) {
      throw new IllegalStateException("7486 mapping labels mismatch: " + labels);
    }
    TtlPackageMapper.requireContiguousAssignments(spec, TtlPackageSpec.ttl7486());

    long nonXorGates = spec.components().stream()
        .filter(component -> !component.factoryName().equals("XOR Gate"))
        .count();
    if (nonXorGates != 0) {
      throw new IllegalStateException("7486 query generated non-XOR gates: " + query);
    }

    TtlPackageMapper.Bom bom = TtlPackageMapper.bom(spec, TtlPackageSpec.ttl7486());
    if (bom.chipCount() != 1
        || bom.usedGateCount() != 1
        || bom.totalGateCapacity() != 4
        || bom.unusedGateCount() != 3
        || !bom.unusedGateLabels().equals(List.of("U1B", "U1C", "U1D"))) {
      throw new IllegalStateException("7486 BOM mismatch: " + bom);
    }

    List<String> inputs = List.of("a", "b");
    for (int mask = 0; mask < (1 << inputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean expected = values.get("a") ^ values.get("b");
      if (outputs.get("y") != expected) {
        throw new IllegalStateException("7486 XOR mismatch inputs=" + values
            + " outputs=" + outputs
            + " expected Y=" + expected);
      }
    }
    System.out.println("[OK] " + query + " 7486-labeled truth table passed");
  }

  private static void runDirectGateTtlSynthesis() {
    runSinglePackageCase(
        "Y=A and B use 7408",
        "AND Gate",
        TtlPackageSpec.ttl7408(),
        List.of("a", "b"),
        values -> values.get("a") && values.get("b"));
    runSinglePackageCase(
        "Y=A or B use 7432",
        "OR Gate",
        TtlPackageSpec.ttl7432(),
        List.of("a", "b"),
        values -> values.get("a") || values.get("b"));
    runSinglePackageCase(
        "Y=not A use 7404",
        "NOT Gate",
        TtlPackageSpec.ttl7404(),
        List.of("a"),
        values -> !values.get("a"));
    runSinglePackageCase(
        "Y=A nor B use 7402",
        "NOR Gate",
        TtlPackageSpec.ttl7402(),
        List.of("a", "b"),
        values -> !(values.get("a") || values.get("b")));
    System.out.println("[OK] direct gate TTL package synthesis regression passed");
  }

  private static void runSinglePackageCase(
      String query,
      String expectedFactory,
      TtlPackageSpec packageSpec,
      List<String> inputs,
      Predicate<Map<String, Boolean>> expectedOutput) {
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    if (result.intent() != ImplementationIntent.CHIP_LEVEL_CIRCUIT_IMPLEMENTATION) {
      throw new IllegalStateException("TTL direct gate intent mismatch query=" + query
          + " intent=" + result.intent());
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    List<String> labels = spec.components().stream()
        .filter(component -> component.factoryName().equals(expectedFactory))
        .map(component -> component.label())
        .toList();
    if (!labels.equals(List.of("U1A"))) {
      throw new IllegalStateException(packageSpec.packageType()
          + " mapping labels mismatch query=" + query + " labels=" + labels);
    }
    TtlPackageMapper.requireContiguousAssignments(spec, packageSpec);

    long unexpectedGateCount = spec.components().stream()
        .filter(component -> !component.factoryName().equals(expectedFactory))
        .count();
    if (unexpectedGateCount != 0) {
      throw new IllegalStateException(packageSpec.packageType()
          + " query generated unexpected gate types: " + query);
    }

    TtlPackageMapper.Bom bom = TtlPackageMapper.bom(spec, packageSpec);
    List<String> expectedUnused = packageSpec.gateNames().subList(1, packageSpec.gateNames().size()).stream()
        .map(gate -> "U1" + gate)
        .toList();
    if (bom.chipCount() != 1
        || bom.usedGateCount() != 1
        || bom.totalGateCapacity() != packageSpec.gatesPerChip()
        || bom.unusedGateCount() != packageSpec.gatesPerChip() - 1
        || !bom.unusedGateLabels().equals(expectedUnused)) {
      throw new IllegalStateException(packageSpec.packageType()
          + " BOM mismatch query=" + query + " bom=" + bom);
    }

    for (int mask = 0; mask < (1 << inputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean expected = expectedOutput.test(values);
      if (outputs.get("y") != expected) {
        throw new IllegalStateException(packageSpec.packageType()
            + " truth-table mismatch query=" + query
            + " inputs=" + values
            + " outputs=" + outputs
            + " expected Y=" + expected);
      }
    }
  }

  private static void runTtlStrategyMismatchRejection() {
    for (String query : List.of(
        "Y=A xor B use 7408",
        "Y=A and B use 7486",
        "Y=A or B use 7404",
        "Y=not A use 7432",
        "Y=A nand B use 7402")) {
      NaturalLanguageCircuitPlanner.PlanningReport report =
          NaturalLanguageCircuitPlanner.planDetailed(query);
      if (report.matched()) {
        throw new IllegalStateException("TTL strategy mismatch should not synthesize: " + query
            + " spec=" + report.result().spec().name());
      }
      if (report.diagnostic().isBlank() || report.suggestions().isEmpty()) {
        throw new IllegalStateException("TTL strategy mismatch lacks diagnostic: " + query
            + " report=" + report);
      }
    }
    System.out.println("[OK] TTL strategy mismatch rejection regression passed");
  }

  private static void runMixedTtlPackageSynthesis() {
    String query = "SUM=A xor B; CARRY=A and B use TTL chips";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match mixed TTL query: " + query);
    }
    if (result.intent() != ImplementationIntent.CHIP_LEVEL_CIRCUIT_IMPLEMENTATION) {
      throw new IllegalStateException("Mixed TTL query intent mismatch: " + result.intent());
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    long xorCount = countFactory(spec, "XOR Gate");
    long andCount = countFactory(spec, "AND Gate");
    if (xorCount != 1 || andCount != 1) {
      throw new IllegalStateException("Mixed TTL half-adder gate count mismatch: xor="
          + xorCount + " and=" + andCount);
    }
    TtlPackageMapper.requireContiguousAssignments(spec, TtlPackageSpec.ttl7486());
    TtlPackageMapper.requireContiguousAssignments(spec, TtlPackageSpec.ttl7408());
    List<TtlMultiPackageReport.Item> ttlItems = TtlMultiPackageReport.items(spec);
    if (ttlItems.size() != 2
        || !TtlMultiPackageReport.toJson(spec).contains("\"packageFamilyCount\": 2")
        || !TtlMultiPackageReport.toJson(spec).contains("\"totalChipCount\": 2")
        || !TtlMultiPackageReport.toJson(spec).contains("\"totalUsedGateCount\": 2")
        || !TtlMultiPackageReport.toMarkdown(spec).contains("7408 x 1")
        || !TtlMultiPackageReport.toMarkdown(spec).contains("7486 x 1")) {
      throw new IllegalStateException("Mixed TTL aggregate BOM mismatch: " + ttlItems);
    }

    var xorBom = TtlPackageMapper.bom(spec, TtlPackageSpec.ttl7486());
    var andBom = TtlPackageMapper.bom(spec, TtlPackageSpec.ttl7408());
    if (xorBom.chipCount() != 1
        || xorBom.usedGateCount() != 1
        || !xorBom.unusedGateLabels().equals(List.of("U1B", "U1C", "U1D"))) {
      throw new IllegalStateException("Mixed TTL 7486 BOM mismatch: " + xorBom);
    }
    if (andBom.chipCount() != 1
        || andBom.usedGateCount() != 1
        || !andBom.unusedGateLabels().equals(List.of("U2B", "U2C", "U2D"))) {
      throw new IllegalStateException("Mixed TTL 7408 BOM mismatch: " + andBom);
    }

    List<String> inputs = List.of("a", "b");
    for (int mask = 0; mask < (1 << inputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean expectedSum = values.get("a") ^ values.get("b");
      boolean expectedCarry = values.get("a") && values.get("b");
      if (outputs.get("sum") != expectedSum || outputs.get("carry") != expectedCarry) {
        throw new IllegalStateException("Mixed TTL half-adder mismatch inputs=" + values
            + " outputs=" + outputs
            + " expected SUM=" + expectedSum
            + " CARRY=" + expectedCarry);
      }
    }
    System.out.println("[OK] " + query + " mixed TTL truth table passed");
  }

  private static void runMixedTtlFullAdderSynthesis() {
    String query = "SUM=A xor B xor Cin; Cout=(A and B) or (Cin and (A xor B)) use TTL chips";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match mixed TTL full-adder query: " + query);
    }
    if (result.intent() != ImplementationIntent.CHIP_LEVEL_CIRCUIT_IMPLEMENTATION) {
      throw new IllegalStateException("Mixed TTL full-adder intent mismatch: " + result.intent());
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    if (countFactory(spec, "XOR Gate") != 2
        || countFactory(spec, "AND Gate") != 2
        || countFactory(spec, "OR Gate") != 1) {
      throw new IllegalStateException("Mixed TTL full-adder gate family count mismatch: "
          + spec.components());
    }
    TtlPackageMapper.requireContiguousAssignments(spec, TtlPackageSpec.ttl7486());
    TtlPackageMapper.requireContiguousAssignments(spec, TtlPackageSpec.ttl7408());
    TtlPackageMapper.requireContiguousAssignments(spec, TtlPackageSpec.ttl7432());

    String bomJson = TtlMultiPackageReport.toJson(spec);
    String bomMarkdown = TtlMultiPackageReport.toMarkdown(spec);
    if (!bomJson.contains("\"packageFamilyCount\": 3")
        || !bomJson.contains("\"totalChipCount\": 3")
        || !bomJson.contains("\"totalUsedGateCount\": 5")
        || !bomMarkdown.contains("7408 x 1")
        || !bomMarkdown.contains("7432 x 1")
        || !bomMarkdown.contains("7486 x 1")
        || !bomMarkdown.contains("U1C, U1D")
        || !bomMarkdown.contains("U2C, U2D")
        || !bomMarkdown.contains("U3B, U3C, U3D")) {
      throw new IllegalStateException("Mixed TTL full-adder aggregate BOM mismatch: "
          + bomJson + "\n" + bomMarkdown);
    }
    String dipJson = TtlDipWiringReport.toJson(spec);
    String dipMarkdown = TtlDipWiringReport.toMarkdown(spec);
    if (TtlDipWiringReport.chips(spec).size() != 3
        || !dipJson.contains("\"chipId\": \"U1\"")
        || !dipJson.contains("\"packageType\": \"7486\"")
        || !dipJson.contains("\"chipId\": \"U2\"")
        || !dipJson.contains("\"packageType\": \"7408\"")
        || !dipJson.contains("\"chipId\": \"U3\"")
        || !dipJson.contains("\"packageType\": \"7432\"")
        || !dipMarkdown.contains("| 14 | VCC |")
        || !dipMarkdown.contains("| 7 | GND |")
        || !dipMarkdown.contains("| 3 | output | A | xor1 | 0 |")) {
      throw new IllegalStateException("Mixed TTL full-adder DIP report mismatch: "
          + dipJson + "\n" + dipMarkdown);
    }
    List<TtlDipLayoutReport.ChipBlock> dipBlocks = TtlDipLayoutReport.blocks(spec);
    String dipLayoutJson = TtlDipLayoutReport.toJson(spec);
    String dipLayoutMarkdown = TtlDipLayoutReport.toMarkdown(spec);
    if (dipBlocks.size() != 3
        || dipBlocks.get(0).x() >= dipBlocks.get(1).x()
        || dipBlocks.get(1).x() >= dipBlocks.get(2).x()
        || !dipLayoutJson.contains("\"chipCount\": 3")
        || !dipLayoutJson.contains("\"packageType\": \"7486\"")
        || !dipLayoutJson.contains("\"pinNumber\": 14")
        || !dipLayoutJson.contains("\"side\": \"RIGHT\"")
        || !dipLayoutMarkdown.contains("U1 - 7486 at")
        || !dipLayoutMarkdown.contains("| 1 | LEFT |")
        || !dipLayoutMarkdown.contains("| 14 | RIGHT |")) {
      throw new IllegalStateException("Mixed TTL full-adder DIP layout mismatch: "
          + dipLayoutJson + "\n" + dipLayoutMarkdown);
    }

    List<String> inputs = List.of("a", "b", "cin");
    for (int mask = 0; mask < (1 << inputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean a = values.get("a");
      boolean b = values.get("b");
      boolean cin = values.get("cin");
      boolean expectedSum = a ^ b ^ cin;
      boolean expectedCout = (a && b) || (cin && (a ^ b));
      if (outputs.get("sum") != expectedSum || outputs.get("cout") != expectedCout) {
        throw new IllegalStateException("Mixed TTL full-adder mismatch inputs=" + values
            + " outputs=" + outputs
            + " expected SUM=" + expectedSum
            + " Cout=" + expectedCout);
      }
    }
    System.out.println("[OK] " + query + " mixed TTL full-adder truth table passed");
  }

  private static void runTtl7400FullAdderSynthesis() {
    String query = "SUM=A xor B xor Cin; Cout=(A and B) or (Cin and (A xor B)) use 7400";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);
    Ttl7400Mapper.requireContiguousAssignments(spec);

    long nonNandGates = spec.components().stream()
        .filter(component -> !component.factoryName().equals("NAND Gate"))
        .count();
    if (nonNandGates != 0) {
      throw new IllegalStateException("7400 full-adder generated non-NAND gates: " + query);
    }

    int assignmentCount = Ttl7400Mapper.assignments(spec).size();
    if (assignmentCount <= 4) {
      throw new IllegalStateException("Expected multi-chip 7400 mapping, assignments=" + assignmentCount);
    }
    String json = Ttl7400Mapper.toJson(spec);
    if (!json.contains("\"packageType\": \"7400\"")
        || !json.contains("\"nandGateCount\": " + assignmentCount)
        || !json.contains("\"chipCount\": 4")
        || !json.contains("\"label\": \"U4A\"")) {
      throw new IllegalStateException("7400 JSON mapping missing expected fields: " + json);
    }
    Ttl7400Mapper.Bom bom = Ttl7400Mapper.bom(spec);
    if (bom.chipCount() != 4
        || bom.usedGateCount() != 13
        || bom.totalGateCapacity() != 16
        || bom.unusedGateCount() != 3
        || !bom.unusedGateLabels().equals(List.of("U4B", "U4C", "U4D"))) {
      throw new IllegalStateException("7400 BOM mismatch: " + bom);
    }
    String bomJson = Ttl7400Mapper.toBomJson(spec);
    if (!bomJson.contains("\"chipCount\": 4")
        || !bomJson.contains("\"usedGateCount\": 13")
        || !bomJson.contains("\"unusedGateCount\": 3")
        || !bomJson.contains("\"unusedGateLabels\": [\"U4B\", \"U4C\", \"U4D\"]")) {
      throw new IllegalStateException("7400 BOM JSON missing expected fields: " + bomJson);
    }

    List<String> inputs = List.of("a", "b", "cin");
    for (int mask = 0; mask < (1 << inputs.size()); mask++) {
      Map<String, Boolean> values = valuesFor(inputs, mask);
      Map<String, Boolean> outputs = CircuitTruthTableEvaluator.evaluate(spec, values);
      boolean a = values.get("a");
      boolean b = values.get("b");
      boolean cin = values.get("cin");
      boolean expectedSum = a ^ b ^ cin;
      boolean expectedCout = (a && b) || (cin && (a ^ b));
      if (outputs.get("sum") != expectedSum || outputs.get("cout") != expectedCout) {
        throw new IllegalStateException("7400 full-adder mismatch inputs=" + values
            + " outputs=" + outputs
            + " expected SUM=" + expectedSum
            + " Cout=" + expectedCout);
      }
    }
    System.out.println("[OK] " + query + " multi-chip 7400 truth table passed");
  }

  private static void runTtlDipPinMapInspectorRegression() {
    String query = "SUM=A xor B xor Cin; Cout=(A and B) or (Cin and (A xor B)) use TTL chips";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    String validJson = syntheticPinMapJson(spec, false);
    TtlDipPinMapInspector.Report validReport =
        TtlDipPinMapInspector.inspectJson(spec, "synthetic-valid-pinmap.json", validJson);
    if (!validReport.clean()) {
      throw new IllegalStateException("Expected synthetic PinMap to pass: "
          + validReport.toHumanString());
    }

    String corruptedJson = syntheticPinMapJson(spec, true);
    TtlDipPinMapInspector.Report corruptedReport =
        TtlDipPinMapInspector.inspectJson(spec, "synthetic-corrupt-pinmap.json", corruptedJson);
    if (corruptedReport.clean()) {
      throw new IllegalStateException("Expected corrupted PinMap to fail");
    }
    String human = corruptedReport.toHumanString();
    if (!human.contains("netId mismatch")) {
      throw new IllegalStateException("Corrupted PinMap failed for the wrong reason: " + human);
    }

    System.out.println("[OK] TTL DIP PinMap inspector positive/negative regression passed");
  }

  private static String syntheticPinMapJson(CircuitSpec spec, boolean corruptFirstNet) {
    List<TtlDipWiringReport.Chip> chips = TtlDipWiringReport.chips(spec);
    int count = 0;
    for (TtlDipWiringReport.Chip chip : chips) {
      for (TtlDipWiringReport.Pin pin : chip.pins()) {
        if (!pin.netId().isBlank()) {
          count++;
        }
      }
    }

    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"boundPinCount\": ").append(count).append(",\n");
    out.append("  \"bindings\": [\n");
    int index = 0;
    boolean corrupted = false;
    for (TtlDipWiringReport.Chip chip : chips) {
      for (TtlDipWiringReport.Pin pin : chip.pins()) {
        if (pin.netId().isBlank()) {
          continue;
        }
        String netId = pin.netId();
        if (corruptFirstNet && !corrupted) {
          netId = netId + "_wrong";
          corrupted = true;
        }
        out.append("    {\n");
        out.append("      \"chipId\": \"").append(chip.chipId()).append("\",\n");
        out.append("      \"packageType\": \"").append(chip.packageType()).append("\",\n");
        out.append("      \"pinNumber\": ").append(pin.pinNumber()).append(",\n");
        out.append("      \"role\": \"").append(pin.role()).append("\",\n");
        out.append("      \"gateName\": \"").append(pin.gateName()).append("\",\n");
        out.append("      \"netId\": \"").append(netId).append("\",\n");
        out.append("      \"endIndex\": ").append(index).append("\n");
        out.append("    }");
        index++;
        if (index < count) {
          out.append(",");
        }
        out.append("\n");
      }
    }
    out.append("  ]\n");
    out.append("}\n");
    return out.toString();
  }

  private static void runTtlDipBehaviorInspectorRegression() {
    String query = "SUM=A xor B xor Cin; Cout=(A and B) or (Cin and (A xor B)) use TTL chips";
    NaturalLanguageCircuitPlanner.PlanResult result = NaturalLanguageCircuitPlanner.plan(query);
    if (result == null) {
      throw new IllegalStateException("Planner did not match query: " + query);
    }
    CircuitSpec spec = result.spec();
    CircuitSemanticValidator.requireValid(spec);

    TtlDipPinMapInspector.Report cleanPinMap =
        TtlDipPinMapInspector.inspectJson(spec, "synthetic-valid-pinmap.json", syntheticPinMapJson(spec, false));
    TtlDipBehaviorInspector.Report behavior =
        TtlDipBehaviorInspector.inspect(spec, "synthetic-full-adder-dip.circ", cleanPinMap);
    if (!behavior.clean() || behavior.rows().size() != 8 || !behavior.pinMapConsistent()) {
      throw new IllegalStateException("Expected clean full-adder DIP behavior report: "
          + behavior.toHumanString());
    }

    TtlDipPinMapInspector.Report badPinMap =
        new TtlDipPinMapInspector.Report("synthetic-bad-pinmap.json", List.of("forced mismatch"));
    TtlDipBehaviorInspector.Report badBehavior =
        TtlDipBehaviorInspector.inspect(spec, "synthetic-full-adder-dip.circ", badPinMap);
    if (badBehavior.clean() || badBehavior.pinMapConsistent()) {
      throw new IllegalStateException("Expected PinMap mismatch to fail behavior report");
    }
    if (!badBehavior.toHumanString().contains("PinMap mismatch")) {
      throw new IllegalStateException("Behavior PinMap mismatch failed for wrong reason: "
          + badBehavior.toHumanString());
    }

    CircuitSpec oversized = oversizedInputSpec();
    TtlDipBehaviorInspector.Report oversizedReport =
        TtlDipBehaviorInspector.inspect(oversized, "synthetic-oversized-dip.circ", cleanPinMap);
    if (oversizedReport.clean()
        || !oversizedReport.toHumanString().contains("Too many inputs")) {
      throw new IllegalStateException("Expected oversized behavior report to fail: "
          + oversizedReport.toHumanString());
    }

    System.out.println("[OK] TTL DIP behavior inspector positive/negative regression passed");
  }

  private static void runTtlDipArtifactManifestRegression() {
    TtlDipArtifactManifest.Report cleanReport = TtlDipArtifactManifest.inspect(
        "synthetic_manifest_clean",
        List.of(new TtlDipArtifactManifest.Item(
            "source_self",
            "src/main/java/com/logisim_ai/validation/TtlDipArtifactManifest.java")),
        List.of(new TtlDipArtifactManifest.Check("synthetic_check", true, "ok")));
    if (!cleanReport.clean()) {
      throw new IllegalStateException("Expected clean manifest report: "
          + cleanReport.toHumanString());
    }

    TtlDipArtifactManifest.Report missingArtifact = TtlDipArtifactManifest.inspect(
        "synthetic_manifest_missing_artifact",
        List.of(new TtlDipArtifactManifest.Item(
            "missing",
            "generated/reports/__definitely_missing_manifest_regression__.md")),
        List.of(new TtlDipArtifactManifest.Check("synthetic_check", true, "ok")));
    if (missingArtifact.clean()
        || !missingArtifact.toHumanString().contains("Missing artifact")) {
      throw new IllegalStateException("Expected missing artifact manifest to fail: "
          + missingArtifact.toHumanString());
    }

    TtlDipArtifactManifest.Report failedCheck = TtlDipArtifactManifest.inspect(
        "synthetic_manifest_failed_check",
        List.of(new TtlDipArtifactManifest.Item(
            "source_self",
            "src/main/java/com/logisim_ai/validation/TtlDipArtifactManifest.java")),
        List.of(new TtlDipArtifactManifest.Check("forced_check", false, "forced failure")));
    if (failedCheck.clean()
        || !failedCheck.toHumanString().contains("Check failed: forced_check")) {
      throw new IllegalStateException("Expected failed check manifest to fail: "
          + failedCheck.toHumanString());
    }

    System.out.println("[OK] TTL DIP artifact manifest positive/negative regression passed");
  }

  private static void runCircuitArtifactManifestRegression() {
    CircuitArtifactManifest.Report cleanReport = CircuitArtifactManifest.inspect(
        "synthetic_circuit_manifest_clean",
        List.of(new CircuitArtifactManifest.Item(
            "source_self",
            "src/main/java/com/logisim_ai/validation/CircuitArtifactManifest.java")),
        List.of(new CircuitArtifactManifest.Check("synthetic_check", true, "ok")));
    if (!cleanReport.clean()) {
      throw new IllegalStateException("Expected clean circuit manifest report: "
          + cleanReport.toHumanString());
    }

    CircuitArtifactManifest.Report missingArtifact = CircuitArtifactManifest.inspect(
        "synthetic_circuit_manifest_missing_artifact",
        List.of(new CircuitArtifactManifest.Item(
            "missing",
            "generated/circ/__definitely_missing_circuit_manifest_regression__.circ")),
        List.of(new CircuitArtifactManifest.Check("synthetic_check", true, "ok")));
    if (missingArtifact.clean()
        || !missingArtifact.toHumanString().contains("Missing artifact")) {
      throw new IllegalStateException("Expected missing circuit artifact manifest to fail: "
          + missingArtifact.toHumanString());
    }

    CircuitArtifactManifest.Report failedCheck = CircuitArtifactManifest.inspect(
        "synthetic_circuit_manifest_failed_check",
        List.of(new CircuitArtifactManifest.Item(
            "source_self",
            "src/main/java/com/logisim_ai/validation/CircuitArtifactManifest.java")),
        List.of(new CircuitArtifactManifest.Check("forced_check", false, "forced failure")));
    if (failedCheck.clean()
        || !failedCheck.toHumanString().contains("Check failed: forced_check")) {
      throw new IllegalStateException("Expected failed circuit manifest check to fail: "
          + failedCheck.toHumanString());
    }

    System.out.println("[OK] circuit artifact manifest positive/negative regression passed");
  }

  private static void runCircuitStructureInspectorRegression() {
    CircuitSpec spec = new CircuitSpec(
        "synthetic_and",
        List.of(new ComponentSpec("and1", "Gates", "AND Gate", 1, "U1A")),
        List.of(
            new ExternalPort("a", "A", 1, PortDirection.INPUT, PortSide.LEFT, "and1", 1),
            new ExternalPort("b", "B", 1, PortDirection.INPUT, PortSide.LEFT, "and1", 2),
            new ExternalPort("y", "Y", 1, PortDirection.OUTPUT, PortSide.RIGHT, "and1", 0)));

    String validXml = """
        <project>
          <main name="synthetic_and"/>
          <circuit name="synthetic_and">
            <comp lib="0" loc="(100,100)" name="Pin"><a name="label" val="A"/></comp>
            <comp lib="0" loc="(100,140)" name="Pin"><a name="label" val="B"/></comp>
            <comp lib="0" loc="(300,120)" name="Pin"><a name="label" val="Y"/><a name="type" val="output"/></comp>
            <comp lib="1" loc="(200,120)" name="AND Gate"><a name="label" val="U1A"/></comp>
          </circuit>
        </project>
        """;
    CircuitStructureInspector.Report valid =
        CircuitStructureInspector.inspectXml(spec, "synthetic-valid.circ", validXml);
    if (!valid.clean()) {
      throw new IllegalStateException("Expected structure inspector positive case to pass: "
          + valid.toHumanString());
    }

    String missingPinXml = """
        <project>
          <main name="synthetic_and"/>
          <circuit name="synthetic_and">
            <comp lib="0" loc="(100,100)" name="Pin"><a name="label" val="A"/></comp>
            <comp lib="0" loc="(300,120)" name="Pin"><a name="label" val="Y"/><a name="type" val="output"/></comp>
            <comp lib="1" loc="(200,120)" name="AND Gate"><a name="label" val="U1A"/></comp>
          </circuit>
        </project>
        """;
    CircuitStructureInspector.Report missingPin =
        CircuitStructureInspector.inspectXml(spec, "synthetic-missing-pin.circ", missingPinXml);
    if (missingPin.clean() || !missingPin.toHumanString().contains("External pin labels mismatch")) {
      throw new IllegalStateException("Expected missing pin structure report to fail: "
          + missingPin.toHumanString());
    }

    String wrongMainXml = validXml.replace("<main name=\"synthetic_and\"/>", "<main name=\"wrong\"/>");
    CircuitStructureInspector.Report wrongMain =
        CircuitStructureInspector.inspectXml(spec, "synthetic-wrong-main.circ", wrongMainXml);
    if (wrongMain.clean() || !wrongMain.toHumanString().contains("Main circuit mismatch")) {
      throw new IllegalStateException("Expected wrong main structure report to fail: "
          + wrongMain.toHumanString());
    }

    System.out.println("[OK] circuit structure inspector positive/negative regression passed");
  }

  private static void runGenerationSummaryRegression() {
    try {
      Path reportDir = Path.of("generated", "reports");
      Path circDir = Path.of("generated", "circ");
      Files.createDirectories(reportDir);
      Files.createDirectories(circDir);

      Path passManifest = reportDir.resolve("summary_regression_pass_manifest.json");
      Files.writeString(passManifest, """
          {
            "complete": true,
            "checkCount": 3,
            "issues": []
          }
          """);
      GenerationSummary.ManifestStatus passStatus =
          GenerationSummary.readManifestStatusForTesting(passManifest);
      if (!Boolean.TRUE.equals(passStatus.complete())
          || passStatus.checkCount().isEmpty()
          || passStatus.checkCount().getAsInt() != 3
          || passStatus.issueCount().isEmpty()
          || passStatus.issueCount().getAsInt() != 0) {
        throw new IllegalStateException("GenerationSummary pass manifest status mismatch: " + passStatus);
      }

      Path failManifest = reportDir.resolve("summary_regression_fail_manifest.json");
      Files.writeString(failManifest, """
          {
            "complete": false,
            "checkCount": 2,
            "issues": ["first", "second"]
          }
          """);
      GenerationSummary.ManifestStatus failStatus =
          GenerationSummary.readManifestStatusForTesting(failManifest);
      if (!Boolean.FALSE.equals(failStatus.complete())
          || failStatus.checkCount().isEmpty()
          || failStatus.checkCount().getAsInt() != 2
          || failStatus.issueCount().isEmpty()
          || failStatus.issueCount().getAsInt() != 2) {
        throw new IllegalStateException("GenerationSummary fail manifest status mismatch: " + failStatus);
      }

      GenerationSummary.ManifestStatus missingStatus =
          GenerationSummary.readManifestStatusForTesting(reportDir.resolve("__missing_summary_manifest__.json"));
      if (missingStatus.complete() != null
          || missingStatus.checkCount().isPresent()
          || missingStatus.issueCount().isPresent()) {
        throw new IllegalStateException("GenerationSummary missing manifest should be unknown: "
            + missingStatus);
      }

      if (!GenerationSummary.describeIntentForTesting("GATE_LEVEL_CIRCUIT")
          .contains("primitive Logisim gates")) {
        throw new IllegalStateException("Gate-level intent explanation missing expected wording");
      }
      if (!GenerationSummary.describeIntentForTesting("CHIP_LEVEL_CIRCUIT_IMPLEMENTATION")
          .contains("chip/package-level")) {
        throw new IllegalStateException("Chip-level intent explanation missing expected wording");
      }
      if (!GenerationSummary.describeIntentForTesting("GATE_LEVEL_CHIP_IMPLEMENTATION")
          .contains("internal gate-level implementation")) {
        throw new IllegalStateException("Gate-level chip intent explanation missing expected wording");
      }
      if (!GenerationSummary.describeIntentForTesting("STRUCTURED_JSON")
          .contains("structured CircuitSpec JSON")) {
        throw new IllegalStateException("Structured JSON intent explanation missing expected wording");
      }
      if (!GenerationSummary.recommendNextActionForTesting(
              "natural_language",
              "GATE_LEVEL_CIRCUIT",
              passStatus)
          .contains("Open the .circ in Logisim-evolution")) {
        throw new IllegalStateException("Gate-level recommended next action mismatch");
      }
      if (!GenerationSummary.recommendNextActionForTesting(
              "json",
              "STRUCTURED_JSON",
              passStatus)
          .contains("Compare the source JSON")) {
        throw new IllegalStateException("Structured JSON recommended next action mismatch");
      }
      if (!GenerationSummary.recommendNextActionForTesting(
              "natural_language",
              "CHIP_LEVEL_CIRCUIT_IMPLEMENTATION",
              passStatus)
          .contains("chip/package mapping")) {
        throw new IllegalStateException("Chip-level recommended next action mismatch");
      }
      if (!GenerationSummary.recommendNextActionForTesting(
              "natural_language_ttl_dip",
              "CHIP_LEVEL_CIRCUIT_IMPLEMENTATION",
              passStatus)
          .contains("TTL BOM")) {
        throw new IllegalStateException("TTL DIP recommended next action mismatch");
      }
      if (!GenerationSummary.recommendNextActionForTesting(
              "natural_language",
              "GATE_LEVEL_CIRCUIT",
              failStatus)
          .contains("Open the manifest first")) {
        throw new IllegalStateException("Failing manifest recommended next action mismatch");
      }
      if (!GenerationSummary.recommendedActionsForTesting(
              "natural_language",
              "GATE_LEVEL_CIRCUIT",
              passStatus)
          .equals(List.of("open_circuit", "review_manifest", "review_truth_table"))) {
        throw new IllegalStateException("Gate-level recommended action ids mismatch");
      }
      if (!GenerationSummary.recommendedActionsForTesting(
              "json",
              "STRUCTURED_JSON",
              passStatus)
          .equals(List.of("compare_source_json", "open_circuit", "review_manifest", "review_truth_table"))) {
        throw new IllegalStateException("Structured JSON recommended action ids mismatch");
      }
      if (!GenerationSummary.recommendedActionsForTesting(
              "natural_language_ttl_dip",
              "CHIP_LEVEL_CIRCUIT_IMPLEMENTATION",
              passStatus)
          .equals(List.of(
              "open_circuit",
              "review_manifest",
              "review_ttl_bom",
              "review_pinmap",
              "review_ttl_dip_layout",
              "review_behavior_report"))) {
        throw new IllegalStateException("TTL DIP recommended action ids mismatch");
      }
      if (!GenerationSummary.recommendedActionsForTesting(
              "natural_language",
              "GATE_LEVEL_CIRCUIT",
              failStatus)
          .equals(List.of("review_manifest", "fix_manifest_issues", "regenerate_circuit"))) {
        throw new IllegalStateException("Failing manifest recommended action ids mismatch");
      }

      CircuitSpec spec = new CircuitSpec(
          "summary_regression",
          List.of(new ComponentSpec("and1", "Gates", "AND Gate", 1, "")),
          List.of(
              new ExternalPort("a", "A", 1, PortDirection.INPUT, PortSide.LEFT, "and1", 1),
              new ExternalPort("b", "B", 1, PortDirection.INPUT, PortSide.LEFT, "and1", 2),
              new ExternalPort("y", "Y", 1, PortDirection.OUTPUT, PortSide.RIGHT, "and1", 0)));
      Path output = circDir.resolve("custom_summary_regression.circ");
      Path outputSpecific = reportDir.resolve("custom_summary_regression_circuit_manifest.md");
      Path outputSpecificJson = reportDir.resolve("custom_summary_regression_circuit_manifest.json");
      Path oldSpecReport = reportDir.resolve("summary_regression_old_report.md");
      Path summaryMd = reportDir.resolve("custom_summary_regression_generation_summary.md");
      Path summaryJson = reportDir.resolve("custom_summary_regression_generation_summary.json");
      Files.writeString(output, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="toolbar" name="Poke Tool"/>
            </toolbar>
            <circuit name="main"/>
          </project>
          """);
      Files.writeString(outputSpecific, "manifest");
      Files.writeString(outputSpecificJson, "{}");
      Files.writeString(oldSpecReport, "old report");

      List<Path> customArtifacts = GenerationSummary.collectArtifactsForTesting(
          spec,
          output.toFile(),
          outputSpecific,
          outputSpecificJson,
          summaryMd,
          summaryJson,
          false);
      if (customArtifacts.contains(oldSpecReport)) {
        throw new IllegalStateException("GenerationSummary leaked spec-prefixed reports for custom output: "
            + customArtifacts);
      }
      if (!customArtifacts.contains(output) || !customArtifacts.contains(outputSpecific)) {
        throw new IllegalStateException("GenerationSummary missed output-specific artifacts: "
            + customArtifacts);
      }

      List<Path> specArtifacts = GenerationSummary.collectArtifactsForTesting(
          spec,
          output.toFile(),
          outputSpecific,
          outputSpecificJson,
          summaryMd,
          summaryJson,
          true);
      if (!specArtifacts.contains(oldSpecReport)) {
        throw new IllegalStateException("GenerationSummary failed to include spec-prefixed reports: "
            + specArtifacts);
      }

      List<GenerationDeliveryReport.Artifact> deliveryArtifacts =
          GenerationDeliveryReport.artifactsForTesting(
              output.toFile(),
              summaryMd,
              summaryJson,
              outputSpecific,
              outputSpecificJson,
              reportDir.resolve("custom_summary_regression_delivery_report.md"),
              reportDir.resolve("custom_summary_regression_delivery_report.json"),
              customArtifacts);
      if (deliveryArtifacts.stream().noneMatch(artifact ->
              artifact.kind().equals("delivery_json")
                  && artifact.path().endsWith("custom_summary_regression_delivery_report.json"))
          || deliveryArtifacts.stream().noneMatch(artifact ->
              artifact.kind().equals("summary_markdown")
                  && artifact.path().endsWith("custom_summary_regression_generation_summary.md"))
          || deliveryArtifacts.stream().noneMatch(artifact ->
              artifact.kind().equals("manifest_json")
                  && artifact.path().endsWith("custom_summary_regression_circuit_manifest.json"))) {
        throw new IllegalStateException("Delivery report artifact index missing core artifacts: "
            + deliveryArtifacts);
      }
      if (deliveryArtifacts.stream()
          .filter(artifact -> artifact.kind().startsWith("delivery_"))
          .anyMatch(artifact -> !artifact.exists())) {
        throw new IllegalStateException("Delivery report self artifacts should be marked present: "
            + deliveryArtifacts);
      }
      Path deliveryMd = GenerationDeliveryReport.write(
          "natural_language",
          "synthetic layout quality regression",
          "GATE_LEVEL_CIRCUIT",
          new GenerationSummary.ImplementationProfile(
              "GATE_LEVEL",
              "",
              false,
              "primitive_gates"),
          spec,
          output.toFile(),
          summaryMd,
          summaryJson,
          outputSpecific,
          outputSpecificJson,
          passStatus,
          List.of("open_circuit", "review_manifest"),
          customArtifacts);
      String deliveryJson = Files.readString(reportDir.resolve("custom_summary_regression_delivery_report.json"));
      String deliveryMarkdown = Files.readString(deliveryMd);
      if (!deliveryJson.contains("\"layoutClean\": true")
          || !deliveryJson.contains("\"implementationProfile\"")
          || !deliveryJson.contains("\"level\": \"GATE_LEVEL\"")
          || !deliveryJson.contains("\"targetPackage\": \"\"")
          || !deliveryJson.contains("\"packagePrimitive\": false")
          || !deliveryJson.contains("\"routingPrimitive\": \"primitive_gates\"")
          || !deliveryMarkdown.contains("- Implementation level: GATE_LEVEL")
          || !deliveryMarkdown.contains("- Routing primitive: primitive_gates")
          || !deliveryJson.contains("\"layoutIssueCount\": 0")
          || !deliveryJson.contains("\"layoutScore\": 100")
          || !deliveryJson.contains("\"layoutRiskLevel\": \"low\"")
          || !deliveryJson.contains("\"layoutUnresolvedToolCount\": 0")
          || !deliveryJson.contains("\"layoutOverlapCount\": 0")
          || !deliveryJson.contains("\"layoutWireRiskCount\": 0")
          || !deliveryJson.contains("\"layoutPinFacingIssueCount\": 0")
          || !deliveryJson.contains("\"layoutPinTunnelProximityCount\": 0")
          || !deliveryJson.contains("\"layoutLabelRiskCount\": 0")
          || !deliveryJson.contains("\"layoutReservedAreaIntrusionCount\": 0")
          || !deliveryJson.contains("\"layoutInspectionFailureCount\": 0")
          || !deliveryJson.contains("\"layoutOtherIssueCount\": 0")
          || !deliveryJson.contains("\"layoutIssueDetails\": []")
          || !deliveryJson.contains("\"layoutRepairSuggestions\": []")
          || !deliveryJson.contains("\"layoutRepairPlans\": []")
          || !deliveryJson.contains("\"layoutAutoRepairApplied\": true")
          || !deliveryJson.contains("\"layoutAutoRepairResults\": [{\"action\": \"regenerate_toolbar\"")
          || !deliveryJson.contains("\"targetType\": \"UNRESOLVED_TOOL\"")
          || !deliveryJson.contains("\"applied\": true")
          || !deliveryJson.contains("\"detail\": \"toolbar regenerated\"")
          || deliveryJson.contains("\"fix_layout\"")
          || deliveryJson.contains("\"regenerate_layout\"")
          || deliveryJson.contains("Toolbar contains unresolved")) {
        throw new IllegalStateException("Delivery report did not reflect automatic toolbar repair: "
            + deliveryJson);
      }
      if (!deliveryMarkdown.contains("- Layout clean: true")
          || !deliveryMarkdown.contains("- Layout score: 100")
          || !deliveryMarkdown.contains("- Layout unresolved tool count: 0")
          || !deliveryMarkdown.contains("- Layout overlap count: 0")
          || !deliveryMarkdown.contains("- Layout reserved area intrusion count: 0")
          || !deliveryMarkdown.contains("- Layout auto repair applied: true")
          || !deliveryMarkdown.contains("## Layout Repairs Applied")
          || !deliveryMarkdown.contains("[applied/regenerate_toolbar/UNRESOLVED_TOOL] toolbar regenerated")
          || deliveryMarkdown.contains("[ERROR/UNRESOLVED_TOOL]")
          || deliveryMarkdown.contains("## Layout Repair Suggestions")
          || deliveryMarkdown.contains("fix_layout")
          || deliveryMarkdown.contains("## Layout Issues")) {
        throw new IllegalStateException("Delivery report markdown did not reflect automatic toolbar repair: "
            + deliveryMarkdown);
      }
      String repairedOutputXml = Files.readString(output);
      if (repairedOutputXml.contains("lib=\"toolbar\"")
          || !repairedOutputXml.contains("<tool lib=\"0\" name=\"Poke Tool\"/>")
          || !repairedOutputXml.contains("<tool lib=\"1\" name=\"Pin\">")) {
        throw new IllegalStateException("Delivery report did not repair toolbar XML: "
            + repairedOutputXml);
      }

      Path pinTunnelOutput = circDir.resolve("custom_pin_tunnel_regression.circ");
      Files.writeString(pinTunnelOutput, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <comp lib="1" loc="(100,100)" name="Pin">
                <a name="label" val="A"/>
              </comp>
              <comp lib="1" loc="(200,100)" name="Tunnel">
                <a name="label" val="A"/>
              </comp>
              <wire from="(100,100)" to="(200,100)"/>
            </circuit>
          </project>
          """);
      Path pinTunnelDeliveryMd = GenerationDeliveryReport.write(
          "natural_language",
          "synthetic pin tunnel clearance regression",
          "GATE_LEVEL_CIRCUIT",
          spec,
          pinTunnelOutput.toFile(),
          summaryMd,
          summaryJson,
          outputSpecific,
          outputSpecificJson,
          passStatus,
          List.of("open_circuit", "review_manifest"),
          customArtifacts);
      String pinTunnelDeliveryJson =
          Files.readString(reportDir.resolve("custom_pin_tunnel_regression_delivery_report.json"));
      if (!pinTunnelDeliveryJson.contains("\"layoutClean\": true")
          || !pinTunnelDeliveryJson.contains("\"layoutPinTunnelProximityCount\": 0")
          || !pinTunnelDeliveryJson.contains("\"layoutAutoRepairApplied\": true")
          || !pinTunnelDeliveryJson.contains("\"action\": \"increase_pin_tunnel_clearance\"")
          || !pinTunnelDeliveryJson.contains("\"targetType\": \"PIN_TUNNEL_PROXIMITY\"")
          || !pinTunnelDeliveryJson.contains("\"detail\": \"moved 1 tunnel(s)\"")
          || pinTunnelDeliveryJson.contains("\"fix_layout\"")
          || pinTunnelDeliveryJson.contains("Tunnel A is too close")) {
        throw new IllegalStateException("Delivery report did not reflect automatic pin/tunnel repair: "
            + pinTunnelDeliveryJson);
      }
      String pinTunnelDeliveryMarkdown = Files.readString(pinTunnelDeliveryMd);
      if (!pinTunnelDeliveryMarkdown.contains("- Layout auto repair applied: true")
          || !pinTunnelDeliveryMarkdown.contains(
              "[applied/increase_pin_tunnel_clearance/PIN_TUNNEL_PROXIMITY] moved 1 tunnel(s)")
          || pinTunnelDeliveryMarkdown.contains("## Layout Issues")) {
        throw new IllegalStateException("Delivery markdown did not reflect automatic pin/tunnel repair: "
            + pinTunnelDeliveryMarkdown);
      }
      String pinTunnelRepairedXml = Files.readString(pinTunnelOutput);
      if (!pinTunnelRepairedXml.contains("loc=\"(250,100)\"")
          || !pinTunnelRepairedXml.contains("<wire from=\"(100,100)\" to=\"(250,100)\"/>")) {
        throw new IllegalStateException("Delivery report did not repair pin/tunnel XML: "
            + pinTunnelRepairedXml);
      }

      Path pinFacingOutput = circDir.resolve("custom_pin_facing_regression.circ");
      Files.writeString(pinFacingOutput, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <comp lib="1" loc="(100,100)" name="Pin">
                <a name="appearance" val="classic"/>
                <a name="facing" val="west"/>
                <a name="label" val="A"/>
              </comp>
              <comp lib="1" loc="(300,100)" name="Pin">
                <a name="appearance" val="classic"/>
                <a name="facing" val="east"/>
                <a name="label" val="Y"/>
                <a name="type" val="output"/>
              </comp>
            </circuit>
          </project>
          """);
      Path pinFacingDeliveryMd = GenerationDeliveryReport.write(
          "natural_language",
          "synthetic pin facing regression",
          "GATE_LEVEL_CIRCUIT",
          spec,
          pinFacingOutput.toFile(),
          summaryMd,
          summaryJson,
          outputSpecific,
          outputSpecificJson,
          passStatus,
          List.of("open_circuit", "review_manifest"),
          customArtifacts);
      String pinFacingDeliveryJson =
          Files.readString(reportDir.resolve("custom_pin_facing_regression_delivery_report.json"));
      if (!pinFacingDeliveryJson.contains("\"layoutClean\": true")
          || !pinFacingDeliveryJson.contains("\"layoutPinFacingIssueCount\": 0")
          || !pinFacingDeliveryJson.contains("\"layoutAutoRepairApplied\": true")
          || !pinFacingDeliveryJson.contains("\"action\": \"fix_pin_facing\"")
          || !pinFacingDeliveryJson.contains("\"targetType\": \"PIN_FACING\"")
          || !pinFacingDeliveryJson.contains("\"detail\": \"fixed 2 pin facing attribute(s)\"")
          || pinFacingDeliveryJson.contains("\"fix_layout\"")
          || pinFacingDeliveryJson.contains("facing should be")) {
        throw new IllegalStateException("Delivery report did not reflect automatic pin facing repair: "
            + pinFacingDeliveryJson);
      }
      String pinFacingDeliveryMarkdown = Files.readString(pinFacingDeliveryMd);
      if (!pinFacingDeliveryMarkdown.contains("- Layout auto repair applied: true")
          || !pinFacingDeliveryMarkdown.contains(
              "[applied/fix_pin_facing/PIN_FACING] fixed 2 pin facing attribute(s)")
          || pinFacingDeliveryMarkdown.contains("## Layout Issues")) {
        throw new IllegalStateException("Delivery markdown did not reflect automatic pin facing repair: "
            + pinFacingDeliveryMarkdown);
      }
      String pinFacingRepairedXml = Files.readString(pinFacingOutput);
      if (!pinFacingRepairedXml.contains("<a name=\"facing\" val=\"east\"/>")
          || !pinFacingRepairedXml.contains("<a name=\"facing\" val=\"west\"/>")) {
        throw new IllegalStateException("Delivery report did not repair pin facing XML: "
            + pinFacingRepairedXml);
      }

      Path labelOutput = circDir.resolve("custom_tunnel_label_regression.circ");
      Files.writeString(labelOutput, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <comp lib="1" loc="(100,100)" name="Tunnel">
                <a name="label" val="net_sum"/>
              </comp>
              <comp lib="1" loc="(260,100)" name="Tunnel">
                <a name="label" val="net_sum"/>
              </comp>
              <wire from="(100,100)" to="(260,100)"/>
            </circuit>
          </project>
          """);
      Path labelDeliveryMd = GenerationDeliveryReport.write(
          "natural_language",
          "synthetic tunnel label regression",
          "GATE_LEVEL_CIRCUIT",
          spec,
          labelOutput.toFile(),
          summaryMd,
          summaryJson,
          outputSpecific,
          outputSpecificJson,
          passStatus,
          List.of("open_circuit", "review_manifest"),
          customArtifacts);
      String labelDeliveryJson =
          Files.readString(reportDir.resolve("custom_tunnel_label_regression_delivery_report.json"));
      if (!labelDeliveryJson.contains("\"layoutClean\": true")
          || !labelDeliveryJson.contains("\"layoutLabelRiskCount\": 0")
          || !labelDeliveryJson.contains("\"layoutAutoRepairApplied\": true")
          || !labelDeliveryJson.contains("\"action\": \"shorten_tunnel_labels\"")
          || !labelDeliveryJson.contains("\"targetType\": \"LABEL_RISK\"")
          || !labelDeliveryJson.contains("custom_tunnel_label_regression_tunnel_label_map.md")
          || !labelDeliveryJson.contains("\"kind\": \"tunnel_label_map_markdown\"")
          || labelDeliveryJson.contains("Tunnel label is too long")
          || labelDeliveryJson.contains("\"fix_layout\"")) {
        throw new IllegalStateException("Delivery report did not reflect automatic tunnel label repair: "
            + labelDeliveryJson);
      }
      String labelDeliveryMarkdown = Files.readString(labelDeliveryMd);
      String labelMapMarkdown =
          Files.readString(reportDir.resolve("custom_tunnel_label_regression_tunnel_label_map.md"));
      String labelRepairedXml = Files.readString(labelOutput);
      if (!labelDeliveryMarkdown.contains("- Layout auto repair applied: true")
          || !labelDeliveryMarkdown.contains(
              "[applied/shorten_tunnel_labels/LABEL_RISK] shortened 1 tunnel label group(s)")
          || !labelMapMarkdown.contains("| main | net_sum | SUM |")
          || labelRepairedXml.contains("net_sum")
          || !labelRepairedXml.contains("val=\"SUM\"")
          || labelDeliveryMarkdown.contains("## Layout Issues")) {
        throw new IllegalStateException("Delivery markdown did not reflect automatic tunnel label repair: "
            + labelDeliveryMarkdown + "\n" + labelMapMarkdown + "\n" + labelRepairedXml);
      }

      Path wireRiskOutput = circDir.resolve("custom_wire_risk_regression.circ");
      Files.writeString(wireRiskOutput, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <wire from="(100,100)" to="(100,180)"/>
              <wire from="(300,100)" to="(340,140)"/>
            </circuit>
          </project>
          """);
      CircuitLayoutInspector.Report wireRiskReport = CircuitLayoutInspector.inspect(wireRiskOutput.toFile());
      String wireRiskDetails = wireRiskReport.layoutIssues().toString();
      String wireRiskPlans = wireRiskReport.repairPlans().toString();
      if (wireRiskReport.clean()
          || wireRiskReport.wireRiskCount() != 2
          || !wireRiskDetails.contains("kind=boundary_vertical;circuit=main;x1=100;y1=100;x2=100;y2=180")
          || !wireRiskDetails.contains("kind=diagonal;circuit=main;x1=300;y1=100;x2=340;y2=140")
          || !wireRiskPlans.contains("kind=boundary_vertical;circuit=main;x1=100;y1=100;x2=100;y2=180")
          || !wireRiskPlans.contains("kind=diagonal;circuit=main;x1=300;y1=100;x2=340;y2=140")) {
        throw new IllegalStateException("Wire risk report did not expose structured wire details: "
            + wireRiskReport.toHumanString() + "\n" + wireRiskDetails + "\n" + wireRiskPlans);
      }

      Path wireRiskDeliveryMd = GenerationDeliveryReport.write(
          "natural_language",
          "synthetic wire risk non-auto-repair regression",
          "GATE_LEVEL_CIRCUIT",
          spec,
          wireRiskOutput.toFile(),
          summaryMd,
          summaryJson,
          outputSpecific,
          outputSpecificJson,
          passStatus,
          List.of("open_circuit", "review_manifest"),
          customArtifacts);
      String wireRiskDeliveryJson =
          Files.readString(reportDir.resolve("custom_wire_risk_regression_delivery_report.json"));
      String wireRiskDeliveryMarkdown = Files.readString(wireRiskDeliveryMd);
      String wireRiskXmlAfterDelivery = Files.readString(wireRiskOutput);
      if (!wireRiskDeliveryJson.contains("\"layoutClean\": false")
          || !wireRiskDeliveryJson.contains("\"layoutWireRiskCount\": 2")
          || !wireRiskDeliveryJson.contains("\"layoutReservedAreaIntrusionCount\": 0")
          || !wireRiskDeliveryJson.contains("\"layoutAutoRepairApplied\": false")
          || !wireRiskDeliveryJson.contains("\"layoutAutoRepairResults\": []")
          || !wireRiskDeliveryJson.contains("\"action\": \"reroute_wires\"")
          || !wireRiskDeliveryJson.contains("\"detail\": \"kind=boundary_vertical")
          || !wireRiskDeliveryJson.contains("\"detail\": \"kind=diagonal")
          || wireRiskDeliveryJson.contains("rerouted 1 boundary vertical wire")
          || !wireRiskDeliveryMarkdown.contains("## Layout Issues")
          || !wireRiskDeliveryMarkdown.contains("## Layout Repair Suggestions")
          || !wireRiskXmlAfterDelivery.contains("<wire from=\"(100,100)\" to=\"(100,180)\"/>")
          || wireRiskXmlAfterDelivery.contains("val=\"R01\"")) {
        throw new IllegalStateException("Delivery report should not auto-repair reroute_wires yet: "
            + wireRiskDeliveryJson + "\n" + wireRiskDeliveryMarkdown + "\n" + wireRiskXmlAfterDelivery);
      }

      Path reservedAreaOutput = circDir.resolve("custom_reserved_area_intrusion_regression.circ");
      Files.writeString(reservedAreaOutput, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <comp lib="1" loc="(150,100)" name="Tunnel">
                <a name="label" val="N1"/>
              </comp>
              <wire from="(120,100)" to="(180,100)"/>
            </circuit>
          </project>
          """);
      Path reservedAreaDeliveryMd = GenerationDeliveryReport.write(
          "natural_language",
          "synthetic reserved area intrusion regression",
          "GATE_LEVEL_CIRCUIT",
          spec,
          reservedAreaOutput.toFile(),
          summaryMd,
          summaryJson,
          outputSpecific,
          outputSpecificJson,
          passStatus,
          List.of("open_circuit", "review_manifest"),
          customArtifacts);
      String reservedAreaDeliveryJson =
          Files.readString(reportDir.resolve("custom_reserved_area_intrusion_regression_delivery_report.json"));
      String reservedAreaDeliveryMarkdown = Files.readString(reservedAreaDeliveryMd);
      if (!reservedAreaDeliveryJson.contains("\"layoutClean\": false")
          || !reservedAreaDeliveryJson.contains("\"layoutReservedAreaIntrusionCount\": 1")
          || !reservedAreaDeliveryJson.contains("\"layoutOtherIssueCount\": 0")
          || !reservedAreaDeliveryJson.contains("\"layoutAutoRepairApplied\": false")
          || !reservedAreaDeliveryJson.contains("\"action\": \"avoid_reserved_areas\"")
          || !reservedAreaDeliveryJson.contains("\"targetType\": \"RESERVED_AREA_INTRUSION\"")
          || !reservedAreaDeliveryJson.contains("kind=reserved_area_intrusion")
          || !reservedAreaDeliveryJson.contains("component=Tunnel")
          || !reservedAreaDeliveryJson.contains("label=N1")
          || !reservedAreaDeliveryMarkdown.contains("- Layout reserved area intrusion count: 1")
          || !reservedAreaDeliveryMarkdown.contains("## Layout Issues")
          || !reservedAreaDeliveryMarkdown.contains("## Layout Repair Suggestions")) {
        throw new IllegalStateException("Delivery report did not expose reserved-area intrusion: "
            + reservedAreaDeliveryJson + "\n" + reservedAreaDeliveryMarkdown);
      }

      Path gateFootprintOutput = circDir.resolve("custom_gate_footprint_intrusion_regression.circ");
      Files.writeString(gateFootprintOutput, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <comp lib="2" loc="(400,120)" name="AND Gate"/>
              <wire from="(370,120)" to="(430,120)"/>
              <wire from="(400,120)" to="(490,120)"/>
              <wire from="(340,100)" to="(360,100)"/>
              <wire from="(340,140)" to="(360,140)"/>
            </circuit>
          </project>
          """);
      Path gateFootprintDeliveryMd = GenerationDeliveryReport.write(
          "natural_language",
          "synthetic gate footprint intrusion regression",
          "GATE_LEVEL_CIRCUIT",
          spec,
          gateFootprintOutput.toFile(),
          summaryMd,
          summaryJson,
          outputSpecific,
          outputSpecificJson,
          passStatus,
          List.of("open_circuit", "review_manifest"),
          customArtifacts);
      String gateFootprintDeliveryJson =
          Files.readString(reportDir.resolve("custom_gate_footprint_intrusion_regression_delivery_report.json"));
      String gateFootprintDeliveryMarkdown = Files.readString(gateFootprintDeliveryMd);
      if (!gateFootprintDeliveryJson.contains("\"layoutClean\": false")
          || !gateFootprintDeliveryJson.contains("\"layoutReservedAreaIntrusionCount\": 1")
          || !gateFootprintDeliveryJson.contains("\"layoutAutoRepairApplied\": false")
          || !gateFootprintDeliveryJson.contains("\"layoutAutoRepairResults\": []")
          || !gateFootprintDeliveryJson.contains("\"action\": \"avoid_reserved_areas\"")
          || !gateFootprintDeliveryJson.contains("\"targetType\": \"RESERVED_AREA_INTRUSION\"")
          || !gateFootprintDeliveryJson.contains("component=AND Gate")
          || !gateFootprintDeliveryJson.contains("connectionPoints=3")
          || !gateFootprintDeliveryJson.contains("x1=370")
          || !gateFootprintDeliveryJson.contains("x2=430")
          || !gateFootprintDeliveryMarkdown.contains("- Layout reserved area intrusion count: 1")
          || !gateFootprintDeliveryMarkdown.contains("AND Gate")
          || !gateFootprintDeliveryMarkdown.contains("## Layout Issues")
          || !gateFootprintDeliveryMarkdown.contains("## Layout Repair Suggestions")) {
        throw new IllegalStateException("Delivery report did not expose gate footprint intrusion: "
            + gateFootprintDeliveryJson + "\n" + gateFootprintDeliveryMarkdown);
      }
    } catch (Exception e) {
      throw new IllegalStateException("GenerationSummary regression failed", e);
    }

    System.out.println("[OK] generation summary positive/negative regression passed");
  }

  private static void runLayoutRepairExecutorRegression() {
    try {
      Path circDir = Path.of("generated", "circ");
      Files.createDirectories(circDir);
      Path brokenToolbar = circDir.resolve("layout_repair_toolbar_regression.circ");
      Files.writeString(brokenToolbar, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="toolbar" name="Poke Tool"/>
            </toolbar>
            <circuit name="main"/>
          </project>
          """);
      CircuitLayoutInspector.Report before = CircuitLayoutInspector.inspect(brokenToolbar.toFile());
      if (before.clean()
          || before.unresolvedToolCount() != 1
          || before.repairPlans().stream().noneMatch(plan ->
              plan.action().equals("regenerate_toolbar")
                  && plan.targetType() == CircuitLayoutInspector.LayoutIssueType.UNRESOLVED_TOOL)) {
        throw new IllegalStateException("Expected toolbar repair plan before execution: "
            + before.toHumanString());
      }

      LayoutRepairExecutor.Report repair =
          LayoutRepairExecutor.execute(brokenToolbar.toFile(), before);
      if (!repair.anyApplied() || !repair.cleanAfterRepair()) {
        throw new IllegalStateException("Expected toolbar repair executor to clean file: "
            + repair);
      }
      String repairedXml = Files.readString(brokenToolbar);
      if (repairedXml.contains("lib=\"toolbar\"")
          || !repairedXml.contains("<tool lib=\"0\" name=\"Poke Tool\"/>")
          || !repairedXml.contains("<tool lib=\"1\" name=\"Pin\">")) {
        throw new IllegalStateException("Toolbar repair did not rewrite expected toolbar XML: "
            + repairedXml);
      }

      Path closeTunnel = circDir.resolve("layout_repair_pin_tunnel_regression.circ");
      Files.writeString(closeTunnel, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <comp lib="1" loc="(100,100)" name="Pin">
                <a name="label" val="A"/>
              </comp>
              <comp lib="1" loc="(200,100)" name="Tunnel">
                <a name="label" val="A"/>
              </comp>
              <wire from="(100,100)" to="(200,100)"/>
            </circuit>
          </project>
          """);
      CircuitLayoutInspector.Report closeTunnelBefore =
          CircuitLayoutInspector.inspect(closeTunnel.toFile());
      if (closeTunnelBefore.clean()
          || closeTunnelBefore.pinTunnelProximityCount() != 1
          || closeTunnelBefore.repairPlans().stream().noneMatch(plan ->
              plan.action().equals("increase_pin_tunnel_clearance")
                  && plan.targetType() == CircuitLayoutInspector.LayoutIssueType.PIN_TUNNEL_PROXIMITY)) {
        throw new IllegalStateException("Expected pin/tunnel clearance repair plan before execution: "
            + closeTunnelBefore.toHumanString());
      }

      LayoutRepairExecutor.Report pinTunnelRepair =
          LayoutRepairExecutor.execute(closeTunnel.toFile(), closeTunnelBefore);
      if (!pinTunnelRepair.anyApplied() || !pinTunnelRepair.cleanAfterRepair()) {
        throw new IllegalStateException("Expected pin/tunnel clearance repair to clean file: "
            + pinTunnelRepair);
      }
      String repairedPinTunnelXml = Files.readString(closeTunnel);
      if (!repairedPinTunnelXml.contains("loc=\"(250,100)\"")
          || !repairedPinTunnelXml.contains("<wire from=\"(100,100)\" to=\"(250,100)\"/>")) {
        throw new IllegalStateException("Pin/tunnel repair did not move tunnel and wire endpoint: "
            + repairedPinTunnelXml);
      }

      Path wrongFacing = circDir.resolve("layout_repair_pin_facing_regression.circ");
      Files.writeString(wrongFacing, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <comp lib="1" loc="(100,100)" name="Pin">
                <a name="appearance" val="classic"/>
                <a name="facing" val="west"/>
                <a name="label" val="A"/>
              </comp>
              <comp lib="1" loc="(300,100)" name="Pin">
                <a name="appearance" val="classic"/>
                <a name="facing" val="east"/>
                <a name="label" val="Y"/>
                <a name="type" val="output"/>
              </comp>
            </circuit>
          </project>
          """);
      CircuitLayoutInspector.Report wrongFacingBefore =
          CircuitLayoutInspector.inspect(wrongFacing.toFile());
      if (wrongFacingBefore.clean()
          || wrongFacingBefore.pinFacingIssueCount() != 2
          || wrongFacingBefore.repairPlans().stream().noneMatch(plan ->
              plan.action().equals("fix_pin_facing")
                  && plan.targetType() == CircuitLayoutInspector.LayoutIssueType.PIN_FACING)) {
        throw new IllegalStateException("Expected pin facing repair plan before execution: "
            + wrongFacingBefore.toHumanString());
      }

      LayoutRepairExecutor.Report pinFacingRepair =
          LayoutRepairExecutor.execute(wrongFacing.toFile(), wrongFacingBefore);
      if (!pinFacingRepair.anyApplied() || !pinFacingRepair.cleanAfterRepair()) {
        throw new IllegalStateException("Expected pin facing repair to clean file: "
            + pinFacingRepair);
      }
      String repairedPinFacingXml = Files.readString(wrongFacing);
      if (!repairedPinFacingXml.contains("<a name=\"facing\" val=\"east\"/>")
          || !repairedPinFacingXml.contains("<a name=\"facing\" val=\"west\"/>")) {
        throw new IllegalStateException("Pin facing repair did not correct facing attributes: "
            + repairedPinFacingXml);
      }

      Path longLabels = circDir.resolve("layout_repair_tunnel_label_regression.circ");
      Files.writeString(longLabels, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <comp lib="1" loc="(100,100)" name="Tunnel">
                <a name="label" val="net_ab_xor"/>
              </comp>
              <comp lib="1" loc="(300,100)" name="Tunnel">
                <a name="label" val="net_ab_xor"/>
              </comp>
              <comp lib="1" loc="(100,180)" name="Tunnel">
                <a name="label" val="net_sum"/>
              </comp>
              <wire from="(100,100)" to="(300,100)"/>
            </circuit>
          </project>
          """);
      CircuitLayoutInspector.Report longLabelsBefore =
          CircuitLayoutInspector.inspect(longLabels.toFile());
      if (longLabelsBefore.clean()
          || longLabelsBefore.labelRiskCount() != 3
          || longLabelsBefore.repairPlans().stream().noneMatch(plan ->
              plan.action().equals("shorten_tunnel_labels")
                  && plan.targetType() == CircuitLayoutInspector.LayoutIssueType.LABEL_RISK)) {
        throw new IllegalStateException("Expected tunnel label shortening repair plan before execution: "
            + longLabelsBefore.toHumanString());
      }

      LayoutRepairExecutor.Report labelRepair =
          LayoutRepairExecutor.execute(longLabels.toFile(), longLabelsBefore);
      if (!labelRepair.anyApplied() || !labelRepair.cleanAfterRepair()) {
        throw new IllegalStateException("Expected tunnel label repair to clean file: "
            + labelRepair);
      }
      String repairedLabelXml = Files.readString(longLabels);
      String labelMapMarkdown =
          Files.readString(Path.of("generated", "reports", "layout_repair_tunnel_label_regression_tunnel_label_map.md"));
      if (repairedLabelXml.contains("net_ab_xor")
          || repairedLabelXml.contains("net_sum")
          || !repairedLabelXml.contains("val=\"T01\"")
          || !repairedLabelXml.contains("val=\"SUM\"")
          || !labelMapMarkdown.contains("| main | net_ab_xor | T01 |")
          || !labelMapMarkdown.contains("| main | net_sum | SUM |")) {
        throw new IllegalStateException("Tunnel label repair did not shorten labels with mapping: "
            + repairedLabelXml + "\n" + labelMapMarkdown);
      }

      Path boundaryWire = circDir.resolve("layout_repair_boundary_wire_regression.circ");
      Files.writeString(boundaryWire, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <wire from="(100,100)" to="(100,180)"/>
            </circuit>
          </project>
          """);
      CircuitLayoutInspector.Report boundaryWireBefore =
          CircuitLayoutInspector.inspect(boundaryWire.toFile());
      if (boundaryWireBefore.clean()
          || boundaryWireBefore.wireRiskCount() != 1
          || boundaryWireBefore.repairPlans().stream().noneMatch(plan ->
              plan.action().equals("reroute_wires")
                  && plan.parameters().contains("kind=boundary_vertical"))) {
        throw new IllegalStateException("Expected boundary wire reroute plan before execution: "
            + boundaryWireBefore.toHumanString() + "\n" + boundaryWireBefore.repairPlans());
      }

      LayoutRepairExecutor.Report boundaryWireRepair =
          LayoutRepairExecutor.execute(boundaryWire.toFile(), boundaryWireBefore);
      if (!boundaryWireRepair.anyApplied() || !boundaryWireRepair.cleanAfterRepair()) {
        throw new IllegalStateException("Expected boundary wire reroute to clean file: "
            + boundaryWireRepair);
      }
      String repairedBoundaryWireXml = Files.readString(boundaryWire);
      if (repairedBoundaryWireXml.contains("<wire from=\"(100,100)\" to=\"(100,180)\"/>")
          || !repairedBoundaryWireXml.contains("loc=\"(100,100)\" name=\"Tunnel\"")
          || !repairedBoundaryWireXml.contains("loc=\"(100,180)\" name=\"Tunnel\"")
          || !repairedBoundaryWireXml.contains("val=\"R01\"")) {
        throw new IllegalStateException("Boundary wire reroute did not replace wire with tunnels: "
            + repairedBoundaryWireXml);
      }

      Path occupiedBoundaryWire = circDir.resolve("layout_repair_boundary_wire_occupied_regression.circ");
      Files.writeString(occupiedBoundaryWire, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <comp lib="1" loc="(100,100)" name="Pin">
                <a name="label" val="A"/>
              </comp>
              <wire from="(100,100)" to="(100,180)"/>
            </circuit>
          </project>
          """);
      CircuitLayoutInspector.Report occupiedBoundaryWireBefore =
          CircuitLayoutInspector.inspect(occupiedBoundaryWire.toFile());
      if (occupiedBoundaryWireBefore.clean()
          || occupiedBoundaryWireBefore.wireRiskCount() != 1
          || occupiedBoundaryWireBefore.repairPlans().stream().noneMatch(plan ->
              plan.action().equals("reroute_wires")
                  && plan.parameters().contains("kind=boundary_vertical"))) {
        throw new IllegalStateException("Expected occupied boundary wire reroute plan before execution: "
            + occupiedBoundaryWireBefore.toHumanString() + "\n" + occupiedBoundaryWireBefore.repairPlans());
      }

      LayoutRepairExecutor.Report occupiedBoundaryWireRepair =
          LayoutRepairExecutor.execute(occupiedBoundaryWire.toFile(), occupiedBoundaryWireBefore);
      String occupiedBoundaryWireXml = Files.readString(occupiedBoundaryWire);
      if (occupiedBoundaryWireRepair.anyApplied()
          || occupiedBoundaryWireRepair.cleanAfterRepair()
          || !occupiedBoundaryWireXml.contains("<wire from=\"(100,100)\" to=\"(100,180)\"/>")
          || occupiedBoundaryWireXml.contains("val=\"R01\"")) {
        throw new IllegalStateException("Boundary wire reroute should skip occupied endpoints: "
            + occupiedBoundaryWireRepair + "\n" + occupiedBoundaryWireXml);
      }
    } catch (Exception e) {
      throw new IllegalStateException("LayoutRepairExecutor regression failed", e);
    }

    System.out.println("[OK] layout repair executor toolbar regression passed");
  }

  private static CircuitSpec oversizedInputSpec() {
    List<ExternalPort> ports = new java.util.ArrayList<>();
    for (int i = 0; i < 13; i++) {
      ports.add(new ExternalPort(
          "i" + i,
          "I" + i,
          1,
          PortDirection.INPUT,
          PortSide.LEFT,
          "and1",
          1));
    }
    ports.add(new ExternalPort(
        "y",
        "Y",
        1,
        PortDirection.OUTPUT,
        PortSide.RIGHT,
        "and1",
        0));
    return new CircuitSpec(
        "oversized_input_behavior_regression",
        List.of(new ComponentSpec("and1", "Gates", "AND Gate", 1, "U1A")),
        ports);
  }

  private static void runAutoLayoutPolicyRegression() {
    try {
      Location from = Location.create(100, 100, true);
      Location to = Location.create(100, 180, true);
      List<AutoLayoutPolicy.WireSegment> segments = AutoLayoutPolicy.planLocalTunnelWire(from, to);
      if (segments.size() != 3) {
        throw new IllegalStateException("Expected boundary vertical local wire to use a dogleg: " + segments);
      }
      for (AutoLayoutPolicy.WireSegment segment : segments) {
        if (segment.isDiagonal() || segment.isRiskyBoundaryVertical()) {
          throw new IllegalStateException("Unsafe local wire segment planned: " + segment
              + " allSegments=" + segments);
        }
      }
      boolean routedOutsideBoundary = segments.stream()
          .anyMatch(segment -> segment.from().getX() > AutoLayoutPolicy.LEFT_BOUNDARY_WIRE_MAX_X
              || segment.to().getX() > AutoLayoutPolicy.LEFT_BOUNDARY_WIRE_MAX_X);
      if (!routedOutsideBoundary) {
        throw new IllegalStateException("Dogleg did not leave left boundary lane: " + segments);
      }

      Path circDir = Path.of("generated", "circ");
      Files.createDirectories(circDir);
      Path inspectorBoundaryPolicy = circDir.resolve("layout_policy_inspector_boundary_regression.circ");
      Files.writeString(inspectorBoundaryPolicy, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <wire from="(100,100)" to="(100,180)"/>
              <wire from="(100,220)" to="(100,250)"/>
            </circuit>
          </project>
          """);
      CircuitLayoutInspector.Report policyReport =
          CircuitLayoutInspector.inspect(inspectorBoundaryPolicy.toFile());
      if (policyReport.clean()
          || policyReport.wireRiskCount() != 1
          || policyReport.layoutIssues().stream().noneMatch(issue ->
              issue.detail().contains("kind=boundary_vertical")
                  && issue.detail().contains("x1=100")
                  && issue.detail().contains("y1=100")
                  && issue.detail().contains("y2=180"))) {
        throw new IllegalStateException("Inspector did not match AutoLayoutPolicy boundary rule: "
            + policyReport.toHumanString());
      }

      Path reservedAreaIntrusion = circDir.resolve("layout_reserved_area_intrusion_regression.circ");
      Files.writeString(reservedAreaIntrusion, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <comp lib="1" loc="(150,100)" name="Tunnel">
                <a name="label" val="N1"/>
              </comp>
              <wire from="(120,100)" to="(180,100)"/>
              <wire from="(150,100)" to="(230,100)"/>
            </circuit>
          </project>
          """);
      CircuitLayoutInspector.Report reservedAreaReport =
          CircuitLayoutInspector.inspect(reservedAreaIntrusion.toFile());
      if (reservedAreaReport.clean()
          || reservedAreaReport.reservedAreaIntrusionCount() != 1
          || reservedAreaReport.repairPlans().stream().noneMatch(plan ->
              plan.action().equals("avoid_reserved_areas")
                  && plan.parameters().contains("kind=reserved_area_intrusion")
                  && plan.parameters().contains("component=Tunnel")
                  && plan.parameters().contains("label=N1")
                  && plan.parameters().contains("connectionPoints=1"))) {
        throw new IllegalStateException("Expected one reserved-area intrusion repair plan: "
            + reservedAreaReport.toHumanString() + "\n" + reservedAreaReport.repairPlans());
      }

      FootprintPolicy.ReservedArea customArea =
          FootprintPolicy.reservedAreaForSmallComponent("Tunnel", "N2", Location.create(200, 100, true))
              .withConnectionPoint(Location.create(200, 90, true));
      if (customArea.crossesInterior(new AutoLayoutPolicy.WireSegment(
          Location.create(200, 90, true), Location.create(200, 130, true)))) {
        throw new IllegalStateException("ReservedArea should allow explicit connection point crossings: "
            + customArea);
      }
      if (!customArea.crossesInterior(new AutoLayoutPolicy.WireSegment(
          Location.create(190, 90, true), Location.create(190, 130, true)))) {
        throw new IllegalStateException("ReservedArea should reject non-connection interior crossings: "
            + customArea);
      }
      List<AutoLayoutPolicy.WireSegment> tunnelAvoidance =
          AutoLayoutPolicy.planLocalTunnelWireAvoidingAreas(
              Location.create(120, 100, true),
              Location.create(180, 100, true),
              List.of(FootprintPolicy.reservedAreaForSmallComponent(
                  "Tunnel", "N1", Location.create(150, 100, true))));
      if (tunnelAvoidance.size() <= 1 || routeCrossesReservedArea(tunnelAvoidance, List.of(
          FootprintPolicy.reservedAreaForSmallComponent("Tunnel", "N1", Location.create(150, 100, true))))) {
        throw new IllegalStateException("Expected local router to dogleg around tunnel reserved area: "
            + tunnelAvoidance);
      }

      FootprintPolicy.ReservedArea andGateArea =
          FootprintPolicy.reservedAreaForComponent("AND Gate", "", Location.create(400, 120, true));
      List<AutoLayoutPolicy.WireSegment> gateAvoidance =
          AutoLayoutPolicy.planLocalTunnelWireAvoidingAreas(
              Location.create(330, 120, true),
              Location.create(470, 120, true),
              List.of(andGateArea));
      if (gateAvoidance.size() <= 1 || routeCrossesReservedArea(gateAvoidance, List.of(andGateArea))) {
        throw new IllegalStateException("Expected local router to dogleg around gate reserved area: "
            + gateAvoidance);
      }

      Path gateFootprintIntrusion = circDir.resolve("layout_gate_footprint_intrusion_regression.circ");
      Files.writeString(gateFootprintIntrusion, """
          <project source="logisim-evolution">
            <lib desc="#Base" name="0"/>
            <lib desc="#Wiring" name="1"/>
            <lib desc="#Gates" name="2"/>
            <main name="main"/>
            <toolbar>
              <tool lib="0" name="Poke Tool"/>
            </toolbar>
            <circuit name="main">
              <comp lib="2" loc="(400,120)" name="AND Gate"/>
              <wire from="(370,120)" to="(430,120)"/>
              <wire from="(400,120)" to="(490,120)"/>
              <wire from="(340,100)" to="(360,100)"/>
              <wire from="(340,140)" to="(360,140)"/>
            </circuit>
          </project>
          """);
      CircuitLayoutInspector.Report gateFootprintReport =
          CircuitLayoutInspector.inspect(gateFootprintIntrusion.toFile());
      if (gateFootprintReport.clean()
          || gateFootprintReport.reservedAreaIntrusionCount() != 1
          || gateFootprintReport.repairPlans().stream().noneMatch(plan ->
              plan.action().equals("avoid_reserved_areas")
                  && plan.parameters().contains("component=AND Gate")
                  && plan.parameters().contains("connectionPoints=3")
                  && plan.parameters().contains("x1=370")
                  && plan.parameters().contains("x2=430"))) {
        throw new IllegalStateException("Expected one AND gate footprint intrusion: "
            + gateFootprintReport.toHumanString() + "\n" + gateFootprintReport.repairPlans());
      }

      CircuitSpec routerFixtureSpec = new CircuitSpec(
          "layout_reserved_area_router_fixture",
          List.of(new ComponentSpec("not1", "Gates", "NOT Gate", 1, "N1")),
          List.of(
              new ExternalPort("a", "A", 1, PortDirection.INPUT, PortSide.LEFT, "not1", 1),
              new ExternalPort("y", "Y", 1, PortDirection.OUTPUT, PortSide.RIGHT, "not1", 0)),
          List.of(
              new NetSpec("in", List.of(new EndpointRef("a", 0), new EndpointRef("not1", 1))),
              new NetSpec("out", List.of(new EndpointRef("not1", 0), new EndpointRef("y", 0)))),
          List.of(new LayoutHint("not1", 0, 0, 0, null)));
      Path routerFixture = circDir.resolve("layout_reserved_area_router_fixture.circ");
      LogisimCompiler.compileNetlistForTesting(
          routerFixtureSpec,
          routerFixture.toFile(),
          List.of(FootprintPolicy.reservedAreaForSmallComponent(
              "Tunnel", "BLOCK", Location.create(450, 120, true))));
      CircuitLayoutInspector.Report routerFixtureReport =
          CircuitLayoutInspector.inspect(routerFixture.toFile());
      String routerFixtureXml = Files.readString(routerFixture);
      boolean hasDoglegEvidence =
          routerFixtureXml.contains("(400,100)")
              || routerFixtureXml.contains("(490,100)")
              || routerFixtureXml.contains("(400,140)")
              || routerFixtureXml.contains("(490,140)");
      if (!routerFixtureReport.clean() || !hasDoglegEvidence) {
        throw new IllegalStateException("Expected compiler to dogleg around injected reserved area: "
            + routerFixtureReport.toHumanString() + "\n" + routerFixtureXml);
      }
    } catch (Exception e) {
      throw new IllegalStateException("AutoLayoutPolicy regression failed", e);
    }
    System.out.println("[OK] auto layout policy local-wire safety regression passed");
  }

  private static boolean routeCrossesReservedArea(
      List<AutoLayoutPolicy.WireSegment> segments,
      List<FootprintPolicy.ReservedArea> areas) {
    for (AutoLayoutPolicy.WireSegment segment : segments) {
      if (segment.isDiagonal() || segment.isRiskyBoundaryVertical()) {
        return true;
      }
      for (FootprintPolicy.ReservedArea area : areas) {
        if (area.crossesInterior(segment)) {
          return true;
        }
      }
    }
    return false;
  }

  private static void runGenericTtlPackageMapper() {
    CircuitSpec ttl7404Spec = new CircuitSpec(
        "ttl7404_mapper_regression",
        List.of(
            new ComponentSpec("not1", "Gates", "NOT Gate", 1, "U1A"),
            new ComponentSpec("not2", "Gates", "NOT Gate", 1, "U1B"),
            new ComponentSpec("not3", "Gates", "NOT Gate", 1, "U1C"),
            new ComponentSpec("not4", "Gates", "NOT Gate", 1, "U1D"),
            new ComponentSpec("not5", "Gates", "NOT Gate", 1, "U1E"),
            new ComponentSpec("not6", "Gates", "NOT Gate", 1, "U1F"),
            new ComponentSpec("not7", "Gates", "NOT Gate", 1, "U2A")),
        List.of());
    TtlPackageMapper.requireContiguousAssignments(ttl7404Spec, TtlPackageSpec.ttl7404());
    TtlPackageMapper.Bom ttl7404Bom =
        TtlPackageMapper.bom(ttl7404Spec, TtlPackageSpec.ttl7404());
    if (ttl7404Bom.chipCount() != 2
        || ttl7404Bom.usedGateCount() != 7
        || ttl7404Bom.totalGateCapacity() != 12
        || ttl7404Bom.unusedGateCount() != 5
        || !ttl7404Bom.unusedGateLabels().equals(List.of("U2B", "U2C", "U2D", "U2E", "U2F"))) {
      throw new IllegalStateException("7404 generic TTL BOM mismatch: " + ttl7404Bom);
    }
    String ttl7404Json = TtlPackageMapper.toJson(ttl7404Spec, TtlPackageSpec.ttl7404());
    if (!ttl7404Json.contains("\"packageType\": \"7404\"")
        || !ttl7404Json.contains("\"gateKind\": \"NOT\"")
        || !ttl7404Json.contains("\"gateCount\": 7")
        || !ttl7404Json.contains("\"chipCount\": 2")) {
      throw new IllegalStateException("7404 generic TTL JSON missing expected fields: " + ttl7404Json);
    }

    CircuitSpec ttl7486Spec = new CircuitSpec(
        "ttl7486_mapper_regression",
        List.of(
            new ComponentSpec("xor1", "Gates", "XOR Gate", 1, "U1A"),
            new ComponentSpec("xor2", "Gates", "XOR Gate", 1, "U1B"),
            new ComponentSpec("xor3", "Gates", "XOR Gate", 1, "U1C"),
            new ComponentSpec("xor4", "Gates", "XOR Gate", 1, "U1D"),
            new ComponentSpec("xor5", "Gates", "XOR Gate", 1, "U2A")),
        List.of());
    TtlPackageMapper.requireContiguousAssignments(ttl7486Spec, TtlPackageSpec.ttl7486());
    TtlPackageMapper.Bom ttl7486Bom =
        TtlPackageMapper.bom(ttl7486Spec, TtlPackageSpec.ttl7486());
    if (ttl7486Bom.chipCount() != 2
        || ttl7486Bom.usedGateCount() != 5
        || ttl7486Bom.totalGateCapacity() != 8
        || ttl7486Bom.unusedGateCount() != 3
        || !ttl7486Bom.unusedGateLabels().equals(List.of("U2B", "U2C", "U2D"))) {
      throw new IllegalStateException("7486 generic TTL BOM mismatch: " + ttl7486Bom);
    }

    CircuitSpec nonContiguousSpec = new CircuitSpec(
        "ttl7408_non_contiguous_regression",
        List.of(
            new ComponentSpec("and1", "Gates", "AND Gate", 1, "U1A"),
            new ComponentSpec("and2", "Gates", "AND Gate", 1, "U1C")),
        List.of());
    boolean rejected = false;
    try {
      TtlPackageMapper.requireContiguousAssignments(nonContiguousSpec, TtlPackageSpec.ttl7408());
    } catch (IllegalStateException expected) {
      rejected = true;
    }
    if (!rejected) {
      throw new IllegalStateException("Generic TTL mapper accepted non-contiguous 7408 labels");
    }

    CircuitSpec offsetChipSpec = new CircuitSpec(
        "ttl7408_offset_chip_regression",
        List.of(
            new ComponentSpec("and1", "Gates", "AND Gate", 1, "U3A"),
            new ComponentSpec("and2", "Gates", "AND Gate", 1, "U3B")),
        List.of());
    TtlPackageMapper.requireContiguousAssignments(offsetChipSpec, TtlPackageSpec.ttl7408());
    TtlPackageMapper.Bom offsetBom =
        TtlPackageMapper.bom(offsetChipSpec, TtlPackageSpec.ttl7408());
    if (offsetBom.chipCount() != 1
        || offsetBom.totalGateCapacity() != 4
        || offsetBom.usedGateCount() != 2
        || !offsetBom.unusedGateLabels().equals(List.of("U3C", "U3D"))) {
      throw new IllegalStateException("Offset chip TTL BOM mismatch: " + offsetBom);
    }

    for (TtlPackageSpec spec : List.of(
        TtlPackageSpec.ttl7400(),
        TtlPackageSpec.ttl7402(),
        TtlPackageSpec.ttl7404(),
        TtlPackageSpec.ttl7408(),
        TtlPackageSpec.ttl7432(),
        TtlPackageSpec.ttl7486())) {
      if (spec.gatesPerChip() < 1) {
        throw new IllegalStateException("Invalid TTL package spec: " + spec);
      }
    }
    System.out.println("[OK] generic TTL package mapper regression passed");
  }

  private static void runNaturalLanguageImplementationIntent() {
    assertIntent(
        "Y=A xor B",
        ImplementationIntent.GATE_LEVEL_CIRCUIT);
    assertIntent(
        "Y=A xor B only NAND",
        ImplementationIntent.GATE_LEVEL_CIRCUIT);
    assertIntent(
        "Y=A xor B use 7400",
        ImplementationIntent.CHIP_LEVEL_CIRCUIT_IMPLEMENTATION);
    assertIntent(
        "Y=A xor B use 7486",
        ImplementationIntent.CHIP_LEVEL_CIRCUIT_IMPLEMENTATION);
    assertIntent(
        "Y=A xor B 用芯片实现",
        ImplementationIntent.CHIP_LEVEL_CIRCUIT_IMPLEMENTATION);
    assertIntent(
        "用逻辑门实现 7400 芯片: Y=A nand B",
        ImplementationIntent.GATE_LEVEL_CHIP_IMPLEMENTATION);
    assertProfile(
        "Y=A xor B",
        ImplementationLevel.GATE_LEVEL,
        "",
        false,
        "primitive_gates");
    assertProfile(
        "Y=A xor B use 7400",
        ImplementationLevel.CHIP_LEVEL,
        "7400",
        true,
        "package_or_labeled_ttl_gates");
    assertProfile(
        "用逻辑门实现 7400 芯片: Y=A nand B",
        ImplementationLevel.GATE_LEVEL_CHIP,
        "7400",
        false,
        "chip_internal_gates");
    assertProfile(
        "SUM=A xor B; CARRY=A and B use TTL chips",
        ImplementationLevel.CHIP_LEVEL,
        "TTL",
        true,
        "package_or_labeled_ttl_gates");

    NaturalLanguageCircuitPlanner.PlanResult chipLevel =
        NaturalLanguageCircuitPlanner.plan("Y=A xor B use 7400");
    if (chipLevel == null
        || chipLevel.intent() != ImplementationIntent.CHIP_LEVEL_CIRCUIT_IMPLEMENTATION) {
      throw new IllegalStateException("7400 chip-level intent not preserved in PlanResult");
    }
    assertGenerationRequest(
        chipLevel.generationRequest(),
        GenerationRequest.RequestKind.NATURAL_LANGUAGE,
        GenerationRequest.RequestInputKind.BOOLEAN_EXPRESSION,
        GenerationRequest.DesiredArtifactView.PACKAGE_MAPPED_CIRCUIT,
        ImplementationLevel.CHIP_LEVEL,
        "7400",
        "package_or_labeled_ttl_gates",
        "Y=A xor B use 7400");
    if (!"7400".equals(chipLevel.implementationProfile().targetPackage())
        || chipLevel.implementationProfile().level() != ImplementationLevel.CHIP_LEVEL) {
      throw new IllegalStateException("7400 chip-level profile not preserved in PlanResult: "
          + chipLevel.implementationProfile());
    }

    NaturalLanguageCircuitPlanner.PlanResult gateLevelChip =
        NaturalLanguageCircuitPlanner.plan("用逻辑门实现 7400 芯片: Y=A nand B");
    if (gateLevelChip == null
        || gateLevelChip.intent() != ImplementationIntent.GATE_LEVEL_CHIP_IMPLEMENTATION) {
      throw new IllegalStateException("Gate-level chip intent not preserved in PlanResult");
    }
    assertGenerationRequest(
        gateLevelChip.generationRequest(),
        GenerationRequest.RequestKind.NATURAL_LANGUAGE,
        GenerationRequest.RequestInputKind.BOOLEAN_EXPRESSION,
        GenerationRequest.DesiredArtifactView.CHIP_INTERNAL_CIRCUIT,
        ImplementationLevel.GATE_LEVEL_CHIP,
        "7400",
        "chip_internal_gates",
        "用逻辑门实现 7400 芯片: Y=A nand B");
    if (!"7400".equals(gateLevelChip.implementationProfile().targetPackage())
        || gateLevelChip.implementationProfile().level() != ImplementationLevel.GATE_LEVEL_CHIP
        || gateLevelChip.implementationProfile().packagePrimitive()) {
      throw new IllegalStateException("Gate-level chip profile not preserved in PlanResult: "
          + gateLevelChip.implementationProfile());
    }
    long labeled7400NandCount = gateLevelChip.spec().components().stream()
        .filter(component -> component.factoryName().equals("NAND Gate"))
        .filter(component -> component.label() != null && component.label().matches("U[0-9]+[A-D]"))
        .count();
    if (labeled7400NandCount != 0) {
      throw new IllegalStateException("Gate-level chip implementation was mislabeled as 7400 package");
    }

    NaturalLanguageCircuitPlanner.PlanResult template =
        NaturalLanguageCircuitPlanner.plan("full adder");
    if (template == null) {
      throw new IllegalStateException("Template query did not produce a PlanResult");
    }
    assertGenerationRequest(
        template.generationRequest(),
        GenerationRequest.RequestKind.NATURAL_LANGUAGE,
        GenerationRequest.RequestInputKind.VERIFIED_TEMPLATE,
        GenerationRequest.DesiredArtifactView.EDITABLE_CIRCUIT,
        ImplementationLevel.GATE_LEVEL,
        "",
        "primitive_gates",
        "full adder");

    assertGenerationRequest(
        GenerationRequest.structuredJson("generated/examples/half_adder_spec.json"),
        GenerationRequest.RequestKind.STRUCTURED_JSON,
        GenerationRequest.RequestInputKind.STRUCTURED_CIRCUIT_SPEC,
        GenerationRequest.DesiredArtifactView.EDITABLE_CIRCUIT,
        ImplementationLevel.GATE_LEVEL,
        "",
        "structured_circuit_spec",
        "generated/examples/half_adder_spec.json");
    System.out.println("[OK] natural-language implementation intent regression passed");
  }

  private static void assertIntent(String query, ImplementationIntent expected) {
    ImplementationIntent actual = NaturalLanguageCircuitPlanner.inferIntentForTesting(query);
    if (actual != expected) {
      throw new IllegalStateException("Intent mismatch query=" + query
          + " expected=" + expected + " actual=" + actual);
    }
  }

  private static void assertProfile(
      String query,
      ImplementationLevel expectedLevel,
      String expectedTargetPackage,
      boolean expectedPackagePrimitive,
      String expectedRoutingPrimitive) {
    NaturalLanguageCircuitPlanner.ImplementationProfile actual =
        NaturalLanguageCircuitPlanner.inferImplementationProfileForTesting(query);
    if (actual.level() != expectedLevel
        || !actual.targetPackage().equals(expectedTargetPackage)
        || actual.packagePrimitive() != expectedPackagePrimitive
        || !actual.routingPrimitive().equals(expectedRoutingPrimitive)) {
      throw new IllegalStateException("Implementation profile mismatch query=" + query
          + " expectedLevel=" + expectedLevel
          + " expectedTargetPackage=" + expectedTargetPackage
          + " expectedPackagePrimitive=" + expectedPackagePrimitive
          + " expectedRoutingPrimitive=" + expectedRoutingPrimitive
          + " actual=" + actual);
    }
  }

  private static void assertGenerationRequest(
      GenerationRequest actual,
      GenerationRequest.RequestKind expectedRequestKind,
      GenerationRequest.RequestInputKind expectedInputKind,
      GenerationRequest.DesiredArtifactView expectedArtifactView,
      ImplementationLevel expectedLevel,
      String expectedTargetPackage,
      String expectedRoutingPrimitive,
      String expectedOriginalRequest) {
    if (actual.requestKind() != expectedRequestKind
        || actual.inputKind() != expectedInputKind
        || actual.desiredArtifactView() != expectedArtifactView
        || actual.implementationProfile().level() != expectedLevel
        || !actual.implementationProfile().targetPackage().equals(expectedTargetPackage)
        || !actual.implementationProfile().routingPrimitive().equals(expectedRoutingPrimitive)
        || !actual.originalRequest().equals(expectedOriginalRequest)
        || actual.normalizedRequest().isBlank()) {
      throw new IllegalStateException("GenerationRequest mismatch: " + actual
          + " expectedRequestKind=" + expectedRequestKind
          + " expectedInputKind=" + expectedInputKind
          + " expectedArtifactView=" + expectedArtifactView
          + " expectedLevel=" + expectedLevel
          + " expectedTargetPackage=" + expectedTargetPackage
          + " expectedRoutingPrimitive=" + expectedRoutingPrimitive
          + " expectedOriginalRequest=" + expectedOriginalRequest);
    }
  }

  private static Map<String, Boolean> valuesFor(List<String> inputs, int mask) {
    Map<String, Boolean> result = new LinkedHashMap<>();
    for (int i = 0; i < inputs.size(); i++) {
      int shift = inputs.size() - i - 1;
      result.put(inputs.get(i), ((mask >> shift) & 1) == 1);
    }
    return result;
  }

  private record Case(
      String query,
      List<String> inputs,
      String output,
      Predicate<Map<String, Boolean>> expected) {}
}
