package com.logisim_ai.ai;

import com.logisim_ai.ir.CircuitSpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

/**
 * Minimal natural-language planner backed by verified JSON IR examples and a
 * conservative Boolean function synthesizer.
 *
 * <p>Long-term LLM planning should still target the same CircuitSpec contract:
 * templates and synthesized circuits both flow through semantic validation,
 * Logisim compilation, round-trip loading, and layout inspection.
 */
public final class NaturalLanguageCircuitPlanner {
  private NaturalLanguageCircuitPlanner() {}

  public enum ImplementationIntent {
    GATE_LEVEL_CIRCUIT,
    GATE_LEVEL_CHIP_IMPLEMENTATION,
    CHIP_LEVEL_CIRCUIT_IMPLEMENTATION
  }

  public enum ImplementationLevel {
    GATE_LEVEL,
    CHIP_LEVEL,
    GATE_LEVEL_CHIP
  }

  public record ImplementationProfile(
      ImplementationLevel level,
      String targetPackage,
      boolean packagePrimitive,
      String routingPrimitive) {}

  public record PlanResult(
      CircuitSpec spec,
      String description,
      Path sourceJson,
      ImplementationIntent intent,
      ImplementationProfile implementationProfile,
      GenerationRequest generationRequest) {
    public PlanResult(CircuitSpec spec, String description, Path sourceJson) {
      this(spec, description, sourceJson, ImplementationIntent.GATE_LEVEL_CIRCUIT);
    }

    public PlanResult(
        CircuitSpec spec,
        String description,
        Path sourceJson,
        ImplementationIntent intent) {
      this(spec, description, sourceJson, intent, implementationProfileForIntent(intent, ""));
    }

    public PlanResult(
        CircuitSpec spec,
        String description,
        Path sourceJson,
        ImplementationIntent intent,
        ImplementationProfile implementationProfile) {
      this(spec, description, sourceJson, intent, implementationProfile,
          GenerationRequest.legacyNaturalLanguage(intent, implementationProfile));
    }
  }

  public record PlanningReport(
      PlanResult result,
      String diagnostic,
      List<String> suggestions) {
    public boolean matched() {
      return result != null;
    }
  }

  private record Template(String sourceJson, List<String> keywords) {}

  private static final List<Template> TEMPLATES = List.of(
      new Template(
          "generated/examples/half_adder_spec.json",
          List.of("half adder", "half-adder", "halfadder")),
      new Template(
          "generated/examples/full_adder_spec.json",
          List.of("full adder", "full-adder", "fulladder")),
      new Template(
          "generated/examples/mux_4to1_spec.json",
          List.of("mux 4 to 1", "4 to 1 mux", "4-to-1 mux", "4:1 mux")),
      new Template(
          "generated/examples/nand_xor3_spec.json",
          List.of("three input xor", "3 input xor", "3-input xor", "xor3", "nand xor"))
  );

  public static PlanResult plan(String query) {
    return planDetailed(query).result();
  }

  public static PlanningReport planDetailed(String query) {
    if (query == null || query.isBlank()) {
      return new PlanningReport(
          null,
          "Empty request. Please describe a circuit or Boolean expression.",
          supportedCircuits());
    }

    String normalized = normalize(query);
    ImplementationIntent intent = inferIntent(normalized);
    ImplementationProfile profile = inferImplementationProfile(normalized, intent);
    boolean use7400 = requests7400(normalized)
        && intent != ImplementationIntent.GATE_LEVEL_CHIP_IMPLEMENTATION;
    boolean use7486 = requests7486(normalized)
        && intent != ImplementationIntent.GATE_LEVEL_CHIP_IMPLEMENTATION;
    boolean use7408 = requests7408(normalized)
        && intent != ImplementationIntent.GATE_LEVEL_CHIP_IMPLEMENTATION;
    boolean use7432 = requests7432(normalized)
        && intent != ImplementationIntent.GATE_LEVEL_CHIP_IMPLEMENTATION;
    boolean use7404 = requests7404(normalized)
        && intent != ImplementationIntent.GATE_LEVEL_CHIP_IMPLEMENTATION;
    boolean use7402 = requests7402(normalized)
        && intent != ImplementationIntent.GATE_LEVEL_CHIP_IMPLEMENTATION;
    boolean useMixedTtl = requestsGenericTtlChips(normalized)
        && intent != ImplementationIntent.GATE_LEVEL_CHIP_IMPLEMENTATION
        && !use7400
        && !use7486
        && !use7408
        && !use7432
        && !use7404
        && !use7402;
    boolean nandOnly = requestsNandOnly(normalized);
    if (!nandOnly && !use7400 && !use7486 && !use7408 && !use7432 && !use7404 && !use7402
        && !useMixedTtl) {
      for (Template template : TEMPLATES) {
        for (String keyword : template.keywords()) {
          if (normalized.contains(normalize(keyword))) {
            return matched(loadTemplate(template, keyword, query, normalized, intent, profile));
          }
        }
      }
    }

    BoolFunctionSynthesizer.SynthesisStrategy strategy = use7400
        ? BoolFunctionSynthesizer.SynthesisStrategy.TTL_7400
        : use7486
        ? BoolFunctionSynthesizer.SynthesisStrategy.TTL_7486
        : use7408
        ? BoolFunctionSynthesizer.SynthesisStrategy.TTL_7408
        : use7432
        ? BoolFunctionSynthesizer.SynthesisStrategy.TTL_7432
        : use7404
        ? BoolFunctionSynthesizer.SynthesisStrategy.TTL_7404
        : use7402
        ? BoolFunctionSynthesizer.SynthesisStrategy.TTL_7402
        : useMixedTtl
        ? BoolFunctionSynthesizer.SynthesisStrategy.MIXED_TTL
        : nandOnly
        ? BoolFunctionSynthesizer.SynthesisStrategy.NAND_ONLY
        : BoolFunctionSynthesizer.SynthesisStrategy.DEFAULT;
    var synthesized = BoolFunctionSynthesizer.trySynthesize(
        query,
        strategy);
    if (synthesized.isPresent()) {
      return matched(new PlanResult(
          synthesized.get(),
          use7400
              ? "Synthesized Boolean function into 7400-labeled NAND CircuitSpec"
              : use7486
              ? "Synthesized Boolean function into 7486-labeled XOR CircuitSpec"
              : use7408
              ? "Synthesized Boolean function into 7408-labeled AND CircuitSpec"
              : use7432
              ? "Synthesized Boolean function into 7432-labeled OR CircuitSpec"
              : use7404
              ? "Synthesized Boolean function into 7404-labeled NOT CircuitSpec"
              : use7402
              ? "Synthesized Boolean function into 7402-labeled NOR CircuitSpec"
              : useMixedTtl
              ? "Synthesized Boolean function into mixed TTL-labeled CircuitSpec"
              : nandOnly
              ? "Synthesized Boolean function into NAND-only CircuitSpec"
              : "Synthesized Boolean function into CircuitSpec",
          null,
          intent,
          profile,
          GenerationRequest.naturalLanguage(
              query,
              normalized,
              GenerationRequest.RequestInputKind.BOOLEAN_EXPRESSION,
              intent,
              profile)));
    }
    if (isDirectTtlStrategy(strategy)) {
      return new PlanningReport(
          null,
          directTtlMismatchDiagnostic(strategy),
          directTtlMismatchSuggestions(strategy));
    }
    return new PlanningReport(
        null,
        "No supported template or Boolean expression matched this request.",
        supportedCircuits());
  }

  public static List<String> supportedCircuits() {
    List<String> templateNames = TEMPLATES.stream()
        .map(template -> template.keywords().get(0))
        .toList();
    return java.util.stream.Stream.concat(
            templateNames.stream(),
            java.util.stream.Stream.of(
                "Boolean expressions: F=A and not B",
                "Boolean expressions: Y=A xor B nand C",
                "NAND-only expressions: Y=A xor B only NAND",
                "7400-mapped expressions: Y=A xor B use 7400",
                "7486-mapped expressions: Y=A xor B use 7486",
                "7408-mapped expressions: Y=A and B use 7408",
                "7432-mapped expressions: Y=A or B use 7432",
                "7404-mapped expressions: Y=not A use 7404",
                "7402-mapped expressions: Y=A nor B use 7402",
                "Mixed TTL expressions: SUM=A xor B; CARRY=A and B use TTL chips",
                "Minterms: F(A,B,C)=sum(1,3,7)"))
        .toList();
  }

  private static PlanResult loadTemplate(
      Template template,
      String keyword,
      String originalRequest,
      String normalizedRequest,
      ImplementationIntent intent,
      ImplementationProfile implementationProfile) {
    try {
      Path path = Path.of(template.sourceJson());
      String json = Files.readString(path, StandardCharsets.UTF_8);
      CircuitSpec spec = CircuitSpecJsonParser.parse(json);
      return new PlanResult(
          spec,
          "Matched verified JSON template: " + keyword,
          path,
          intent,
          implementationProfile,
          GenerationRequest.naturalLanguage(
              originalRequest,
              normalizedRequest,
              GenerationRequest.RequestInputKind.VERIFIED_TEMPLATE,
              intent,
              implementationProfile));
    } catch (Exception e) {
      throw new IllegalStateException("Failed to load NL template: " + template.sourceJson(), e);
    }
  }

  private static PlanningReport matched(PlanResult result) {
    return new PlanningReport(result, "", List.of());
  }

  private static boolean isDirectTtlStrategy(BoolFunctionSynthesizer.SynthesisStrategy strategy) {
    return strategy == BoolFunctionSynthesizer.SynthesisStrategy.TTL_7400
        || strategy == BoolFunctionSynthesizer.SynthesisStrategy.TTL_7402
        || strategy == BoolFunctionSynthesizer.SynthesisStrategy.TTL_7404
        || strategy == BoolFunctionSynthesizer.SynthesisStrategy.TTL_7408
        || strategy == BoolFunctionSynthesizer.SynthesisStrategy.TTL_7432
        || strategy == BoolFunctionSynthesizer.SynthesisStrategy.TTL_7486;
  }

  private static String directTtlMismatchDiagnostic(
      BoolFunctionSynthesizer.SynthesisStrategy strategy) {
    return switch (strategy) {
      case TTL_7400 -> "7400 direct chip strategy only accepts expressions that can be mapped to labeled NAND gates.";
      case TTL_7402 -> "7402 direct chip strategy only accepts expressions that can be mapped to labeled NOR gates.";
      case TTL_7404 -> "7404 direct chip strategy only accepts expressions that can be mapped to labeled NOT gates.";
      case TTL_7408 -> "7408 direct chip strategy only accepts expressions that can be mapped to labeled AND gates.";
      case TTL_7432 -> "7432 direct chip strategy only accepts expressions that can be mapped to labeled OR gates.";
      case TTL_7486 -> "7486 direct chip strategy only accepts expressions that can be mapped to labeled XOR gates.";
      default -> "The requested TTL chip strategy cannot represent the expression directly.";
    };
  }

  private static List<String> directTtlMismatchSuggestions(
      BoolFunctionSynthesizer.SynthesisStrategy strategy) {
    return switch (strategy) {
      case TTL_7400 -> List.of(
          "Use 'only NAND' or 'use 7400' for NAND-realizable package output.",
          "Use a mixed TTL chips request when the expression needs other gate families.");
      case TTL_7402 -> List.of(
          "Use 'Y=A nor B use 7402' for direct 7402 mapping.",
          "Use mixed TTL chips if the expression needs non-NOR gates.");
      case TTL_7404 -> List.of(
          "Use 'Y=not A use 7404' for direct 7404 mapping.",
          "Use mixed TTL chips if the expression needs non-inverter gates.");
      case TTL_7408 -> List.of(
          "Use 'Y=A and B use 7408' for direct 7408 mapping.",
          "Use 7486 for XOR, 7432 for OR, 7404 for NOT, or a mixed TTL chips request.");
      case TTL_7432 -> List.of(
          "Use 'Y=A or B use 7432' for direct 7432 mapping.",
          "Use mixed TTL chips if the expression needs non-OR gates.");
      case TTL_7486 -> List.of(
          "Use 'Y=A xor B use 7486' for direct 7486 mapping.",
          "Use mixed TTL chips if the expression needs AND/OR/NOT/NAND/NOR gates too.");
      default -> supportedCircuits();
    };
  }

  public static ImplementationIntent inferIntentForTesting(String query) {
    return inferIntent(normalize(query));
  }

  /**
   * Detect a "generate &lt;chip&gt;" native-TTL request (e.g. "生成 74160",
   * "generate 74138", "画一个 74151"). Returns the chip factory name (e.g. "74160")
   * or {@code null} when the request is not a native-chip request.
   */
  public static String findNativeChip(String query) {
    if (query == null || query.isBlank()) {
      return null;
    }
    String normalized = normalize(query);
    // Gate-package requests (use 7400 etc.) are handled by the Boolean/TTL mapper path,
    // not by standalone native-chip placement.
    if (requests7400(normalized) || requests7402(normalized) || requests7404(normalized)
        || requests7408(normalized) || requests7432(normalized) || requests7486(normalized)
        || requestsNandOnly(normalized)) {
      return null;
    }
    java.util.regex.Matcher matcher = java.util.regex.Pattern
        .compile("(?:74\\d{2,3})")
        .matcher(normalized);
    if (matcher.find()) {
      String chip = matcher.group();
      if (isNativeTtlChip(chip)) {
        return chip;
      }
    }
    return null;
  }

  /** Chip families available as standalone native components in the TTL library. */
  private static boolean isNativeTtlChip(String chip) {
    return List.of(
        "7400", "7402", "7404", "7408", "7410", "7411", "7413", "7414", "7420", "7427",
        "7430", "7432", "7442", "7447", "7474", "7485", "7486", "74138", "74139",
        "74151", "74153", "74157", "74158", "74160", "74161", "74163", "74164",
        "74165", "74166", "74175", "74181", "74182", "74192", "74193", "74194",
        "74240", "74244", "74245", "74266", "74273", "74283", "74299", "74377",
        "74381", "74541", "74670")
        .contains(chip);
  }

  public static ImplementationProfile inferImplementationProfileForTesting(String query) {
    String normalized = normalize(query);
    return inferImplementationProfile(normalized, inferIntent(normalized));
  }

  private static ImplementationIntent inferIntent(String normalized) {
    if (requestsGateLevelChipImplementation(normalized)) {
      return ImplementationIntent.GATE_LEVEL_CHIP_IMPLEMENTATION;
    }
    if (requestsChipLevelCircuitImplementation(normalized)) {
      return ImplementationIntent.CHIP_LEVEL_CIRCUIT_IMPLEMENTATION;
    }
    return ImplementationIntent.GATE_LEVEL_CIRCUIT;
  }

  private static ImplementationProfile inferImplementationProfile(
      String normalized,
      ImplementationIntent intent) {
    return implementationProfileForIntent(intent, targetPackage(normalized));
  }

  private static ImplementationProfile implementationProfileForIntent(
      ImplementationIntent intent,
      String targetPackage) {
    return switch (intent) {
      case GATE_LEVEL_CIRCUIT -> new ImplementationProfile(
          ImplementationLevel.GATE_LEVEL,
          "",
          false,
          "primitive_gates");
      case CHIP_LEVEL_CIRCUIT_IMPLEMENTATION -> new ImplementationProfile(
          ImplementationLevel.CHIP_LEVEL,
          targetPackage,
          true,
          "package_or_labeled_ttl_gates");
      case GATE_LEVEL_CHIP_IMPLEMENTATION -> new ImplementationProfile(
          ImplementationLevel.GATE_LEVEL_CHIP,
          targetPackage,
          false,
          "chip_internal_gates");
    };
  }

  private static String targetPackage(String normalized) {
    for (String pkg : List.of("7400", "7402", "7404", "7408", "7432", "7486")) {
      if (normalized.matches(".*\\b" + pkg + "\\b.*")) {
        return pkg;
      }
    }
    if (requestsGenericTtlChips(normalized)) {
      return "TTL";
    }
    return "";
  }

  private static String normalize(String text) {
    return text.toLowerCase(Locale.ROOT)
        .replace('_', ' ')
        .replace('-', ' ')
        .replaceAll("\\s+", " ")
        .trim();
  }

  private static boolean requestsNandOnly(String normalized) {
    return normalized.contains("only nand")
        || normalized.contains("nand only")
        || normalized.contains("use only nand")
        || normalized.contains("using only nand")
        || normalized.contains("只用 nand")
        || normalized.contains("仅用 nand");
  }

  private static boolean requests7400(String normalized) {
    return normalized.contains("use 7400")
        || normalized.contains("using 7400")
        || normalized.contains("with 7400")
        || normalized.contains("用 7400")
        || normalized.matches(".*\\b7400\\b.*");
  }

  private static boolean requests7486(String normalized) {
    return normalized.contains("use 7486")
        || normalized.contains("using 7486")
        || normalized.contains("with 7486")
        || normalized.contains("用 7486")
        || normalized.matches(".*\\b7486\\b.*");
  }

  private static boolean requests7408(String normalized) {
    return normalized.contains("use 7408")
        || normalized.contains("using 7408")
        || normalized.contains("with 7408")
        || normalized.contains("用 7408")
        || normalized.matches(".*\\b7408\\b.*");
  }

  private static boolean requests7432(String normalized) {
    return normalized.contains("use 7432")
        || normalized.contains("using 7432")
        || normalized.contains("with 7432")
        || normalized.contains("用 7432")
        || normalized.matches(".*\\b7432\\b.*");
  }

  private static boolean requests7404(String normalized) {
    return normalized.contains("use 7404")
        || normalized.contains("using 7404")
        || normalized.contains("with 7404")
        || normalized.contains("用 7404")
        || normalized.matches(".*\\b7404\\b.*");
  }

  private static boolean requests7402(String normalized) {
    return normalized.contains("use 7402")
        || normalized.contains("using 7402")
        || normalized.contains("with 7402")
        || normalized.contains("用 7402")
        || normalized.matches(".*\\b7402\\b.*");
  }

  private static boolean requestsGenericTtlChips(String normalized) {
    return normalized.contains("use ttl chips")
        || normalized.contains("using ttl chips")
        || normalized.contains("with ttl chips")
        || normalized.contains("use ttl chip")
        || normalized.contains("using ttl chip")
        || normalized.contains("用 ttl 芯片")
        || normalized.contains("用ttl芯片")
        || normalized.contains("用芯片实现")
        || normalized.contains("芯片级实现")
        || normalized.contains("chip level")
        || normalized.contains("chip-level");
  }

  private static boolean requestsGateLevelChipImplementation(String normalized) {
    return normalized.contains("用门实现芯片")
        || normalized.contains("用逻辑门实现芯片")
        || normalized.contains("用门实现 74")
        || normalized.contains("用逻辑门实现 74")
        || normalized.contains("门级实现芯片")
        || normalized.contains("门级展开芯片")
        || normalized.contains("芯片内部结构")
        || normalized.contains("implement chip with gates")
        || normalized.contains("implement a chip with gates")
        || normalized.contains("gate level chip")
        || normalized.contains("gate-level chip");
  }

  private static boolean requestsChipLevelCircuitImplementation(String normalized) {
    return requests7400(normalized)
        || requests7486(normalized)
        || requests7408(normalized)
        || requests7432(normalized)
        || requests7404(normalized)
        || requests7402(normalized)
        || requestsGenericTtlChips(normalized);
  }
}
