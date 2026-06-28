package com.logisim_ai.experiment;

import com.logisim_ai.ai.NaturalLanguageCircuitPlanner;
import com.logisim_ai.ai.NaturalLanguageCircuitPlanner.ImplementationIntent;
import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ttl.Ttl7400Mapper;
import com.logisim_ai.ttl.TtlMultiPackageReport;
import com.logisim_ai.ttl.TtlPackageMapper;
import com.logisim_ai.ttl.TtlPackageSpec;
import com.logisim_ai.validation.CircuitSemanticValidator;
import com.logisim_ai.validation.CircuitTruthTableEvaluator;
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
            values -> values.get("a") ^ values.get("b") ^ values.get("c")));

    for (Case testCase : cases) {
      runCase(testCase);
    }
    runMultiOutputHalfAdder();
    runMultiOutputFullAdder();
    runCommutativeReuse();
    runNandOnlySynthesis();
    runTtl7400Synthesis();
    runTtl7486Synthesis();
    runDirectGateTtlSynthesis();
    runTtlStrategyMismatchRejection();
    runMixedTtlPackageSynthesis();
    runMixedTtlFullAdderSynthesis();
    runTtl7400FullAdderSynthesis();
    runGenericTtlPackageMapper();
    runNaturalLanguageImplementationIntent();
    System.out.println("[OK] Boolean synthesis/template truth-table regression passed");
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

    NaturalLanguageCircuitPlanner.PlanResult chipLevel =
        NaturalLanguageCircuitPlanner.plan("Y=A xor B use 7400");
    if (chipLevel == null
        || chipLevel.intent() != ImplementationIntent.CHIP_LEVEL_CIRCUIT_IMPLEMENTATION) {
      throw new IllegalStateException("7400 chip-level intent not preserved in PlanResult");
    }

    NaturalLanguageCircuitPlanner.PlanResult gateLevelChip =
        NaturalLanguageCircuitPlanner.plan("用逻辑门实现 7400 芯片: Y=A nand B");
    if (gateLevelChip == null
        || gateLevelChip.intent() != ImplementationIntent.GATE_LEVEL_CHIP_IMPLEMENTATION) {
      throw new IllegalStateException("Gate-level chip intent not preserved in PlanResult");
    }
    long labeled7400NandCount = gateLevelChip.spec().components().stream()
        .filter(component -> component.factoryName().equals("NAND Gate"))
        .filter(component -> component.label() != null && component.label().matches("U[0-9]+[A-D]"))
        .count();
    if (labeled7400NandCount != 0) {
      throw new IllegalStateException("Gate-level chip implementation was mislabeled as 7400 package");
    }
    System.out.println("[OK] natural-language implementation intent regression passed");
  }

  private static void assertIntent(String query, ImplementationIntent expected) {
    ImplementationIntent actual = NaturalLanguageCircuitPlanner.inferIntentForTesting(query);
    if (actual != expected) {
      throw new IllegalStateException("Intent mismatch query=" + query
          + " expected=" + expected + " actual=" + actual);
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
