package com.logisim_ai.ai;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.LayoutHint;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.PortSide;
import com.logisim_ai.logic.BoolExpr;
import com.logisim_ai.logic.BoolExpressionNandMapper;
import com.logisim_ai.logic.BoolExpressionParser;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Conservative combinational synthesizer for simple Boolean functions.
 *
 * <p>This is the first deterministic layer below natural language planning:
 * the planner may recognize or extract a Boolean expression, then this class
 * emits CircuitSpec netlists that reuse the same compiler, layout, and
 * validation path as hand-written JSON examples.
 */
public final class BoolFunctionSynthesizer {
  private static final Pattern ASSIGNMENT =
      Pattern.compile("^\\s*([A-Za-z][A-Za-z0-9_]*)\\s*(?:\\([^)]*\\))?\\s*=\\s*(.+)$");
  private static final Pattern MINTERM =
      Pattern.compile("^\\s*([A-Za-z][A-Za-z0-9_]*)\\s*\\(([^)]*)\\)\\s*=\\s*sum\\s*\\(([^)]*)\\)\\s*$",
          Pattern.CASE_INSENSITIVE);

  private BoolFunctionSynthesizer() {}

  public enum SynthesisStrategy {
    DEFAULT,
    NAND_ONLY,
    TTL_7400,
    TTL_7486,
    TTL_7408,
    TTL_7432,
    TTL_7404,
    TTL_7402,
    MIXED_TTL
  }

  public static Optional<CircuitSpec> trySynthesize(String query) {
    return trySynthesize(query, SynthesisStrategy.DEFAULT);
  }

  public static Optional<CircuitSpec> trySynthesize(String query, SynthesisStrategy strategy) {
    if (query == null || query.isBlank()) {
      return Optional.empty();
    }

    String cleaned = extractCandidate(query, strategy);
    // Functional descriptions (e.g. "三人表决器") map to Boolean expressions
    // first, then flow through the normal synthesis path.
    Optional<String> functional = tryFunctionalMapping(cleaned);
    if (functional.isPresent()) {
      return trySynthesize(functional.get(), strategy);
    }
    Optional<CircuitSpec> minterm = tryMinterm(cleaned);
    if (minterm.isPresent() && strategy == SynthesisStrategy.DEFAULT) {
      return minterm;
    }
    Optional<CircuitSpec> multiOutput = tryMultiOutput(cleaned, strategy);
    if (multiOutput.isPresent()) {
      return multiOutput;
    }

    Matcher assignment = ASSIGNMENT.matcher(cleaned);
    if (!assignment.matches()) {
      return Optional.empty();
    }

    String output = assignment.group(1);
    String expression = assignment.group(2);
    try {
      BoolExpr ast = BoolExpressionParser.parseNormalized(expression);
      ast = applyStrategy(ast, strategy);
      String suffix = switch (strategy) {
        case NAND_ONLY -> appendSuffix(nameSuffix(expression), "nand_only");
        case TTL_7400 -> appendSuffix(nameSuffix(expression), "7400");
        case TTL_7486 -> appendSuffix(nameSuffixOr(expression, "xor"), "7486");
        case TTL_7408 -> appendSuffix(nameSuffixOr(expression, "and"), "7408");
        case TTL_7432 -> appendSuffix(nameSuffixOr(expression, "or"), "7432");
        case TTL_7404 -> appendSuffix(nameSuffixOr(expression, "not"), "7404");
        case TTL_7402 -> appendSuffix(nameSuffixOr(expression, "nor"), "7402");
        case MIXED_TTL -> appendSuffix(nameSuffixOr(expression, "logic"), "ttl");
        case DEFAULT -> nameSuffix(expression);
      };
      return validateStrategy(new Builder(output, ast.variables(), suffix, strategy).build(ast), strategy);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  private static Optional<CircuitSpec> tryMultiOutput(String text) {
    return tryMultiOutput(text, SynthesisStrategy.DEFAULT);
  }

  /**
   * Maps natural-language functional descriptions of textbook circuits to
   * equivalent Boolean expressions. The expression then flows through the
   * regular synthesis path (parse -> strategy -> CircuitSpec), so a
   * functional description needs no special compiler support.
   */
  private static Optional<String> tryFunctionalMapping(String text) {
    String t = text.toLowerCase(Locale.ROOT);
    if (containsAny(t,
        "三人表决", "多数表决", "表决器", "majority", "voting", "vote of")) {
      return Optional.of("F=(A and B) or (A and C) or (B and C)");
    }
    if (containsAny(t, "不一致", "意见不同", "disagreement", "disagree")) {
      return Optional.of("F=(A xor B) or (A xor C)");
    }
    if (containsAny(t, "偶校验", "奇偶校验", "校验器", "even parity", "parity")) {
      return Optional.of("F=A xor B xor C");
    }
    if (containsAny(t, "相等", "相同", "equal", "equals", "xnor", "同或")) {
      return Optional.of("F=not (A xor B)");
    }
    if (containsAny(t, "互斥", "只能一个", "exclusive", "mutually")) {
      return Optional.of("F=A xor B");
    }
    // 1-bit magnitude comparator (three outputs)
    if (containsAny(t, "谁大", "谁小", "哪个大", "compare two", "comparison", "比较两个")) {
      return Optional.of(
          "F_GT=A and not B; F_EQ=not (A xor B); F_LT=not A and B");
    }
    // 4-bit password/keypad lock: all four key bits must match the code
    if (containsAny(t, "密码", "开锁", "password", "keypad", "combination lock")) {
      return Optional.of(
          "F=not (A0 xor B0) and not (A1 xor B1) and not (A2 xor B2) and not (A3 xor B3)");
    }
    return Optional.empty();
  }

  private static boolean containsAny(String text, String... keywords) {
    for (String keyword : keywords) {
      if (text.contains(keyword)) {
        return true;
      }
    }
    return false;
  }

  private static Optional<CircuitSpec> tryMultiOutput(String text, SynthesisStrategy strategy) {
    List<String> statements = splitStatements(text);
    if (statements.size() < 2) {
      return Optional.empty();
    }

    Map<String, BoolExpr> outputs = new LinkedHashMap<>();
    Set<String> variables = new LinkedHashSet<>();
    List<String> suffixes = new ArrayList<>();
    try {
      for (String statement : statements) {
        Matcher assignment = ASSIGNMENT.matcher(statement);
        if (!assignment.matches()) {
          return Optional.empty();
        }
        String output = assignment.group(1);
        String expression = assignment.group(2);
        BoolExpr ast = BoolExpressionParser.parseNormalized(expression);
        ast = applyStrategy(ast, strategy);
        outputs.put(output, ast);
        variables.addAll(ast.variables());
        String suffix = nameSuffix(expression);
        if (!suffix.isBlank()) {
          suffixes.add(suffix);
        }
      }
      String nameSuffix = suffixes.isEmpty() ? "multi" : String.join("_", suffixes);
      if (strategy == SynthesisStrategy.NAND_ONLY) {
        nameSuffix = appendSuffix(nameSuffix, "nand_only");
      } else if (strategy == SynthesisStrategy.TTL_7400) {
        nameSuffix = appendSuffix(nameSuffix, "7400");
      } else if (strategy == SynthesisStrategy.TTL_7486) {
        nameSuffix = appendSuffix(nameSuffix.isBlank() ? "xor" : nameSuffix, "7486");
      } else if (strategy == SynthesisStrategy.TTL_7408) {
        nameSuffix = appendSuffix(nameSuffix.isBlank() ? "and" : nameSuffix, "7408");
      } else if (strategy == SynthesisStrategy.TTL_7432) {
        nameSuffix = appendSuffix(nameSuffix.isBlank() ? "or" : nameSuffix, "7432");
      } else if (strategy == SynthesisStrategy.TTL_7404) {
        nameSuffix = appendSuffix(nameSuffix.isBlank() ? "not" : nameSuffix, "7404");
      } else if (strategy == SynthesisStrategy.TTL_7402) {
        nameSuffix = appendSuffix(nameSuffix.isBlank() ? "nor" : nameSuffix, "7402");
      } else if (strategy == SynthesisStrategy.MIXED_TTL) {
        nameSuffix = appendSuffix(nameSuffix.isBlank() ? "logic" : nameSuffix, "ttl");
      }
      return validateStrategy(
          new Builder("multi", List.copyOf(variables), nameSuffix, strategy).build(outputs),
          strategy);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  private static Optional<CircuitSpec> tryMinterm(String text) {
    Matcher matcher = MINTERM.matcher(text);
    if (!matcher.matches()) {
      return Optional.empty();
    }

    String output = matcher.group(1);
    List<String> vars = splitVariables(matcher.group(2));
    List<Integer> minterms = splitMinterms(matcher.group(3));
    if (vars.isEmpty() || minterms.isEmpty()) {
      return Optional.empty();
    }

    for (int minterm : minterms) {
      if (minterm < 0 || minterm >= (1 << vars.size())) {
        return Optional.empty();
      }
    }
    return Optional.of(new Builder(output, vars).buildMinterms(minterms));
  }

  private static String extractCandidate(String query, SynthesisStrategy strategy) {
    String trimmed = query.trim();
    int colon = trimmed.indexOf(':');
    if (colon >= 0 && colon + 1 < trimmed.length()) {
      trimmed = trimmed.substring(colon + 1).trim();
    }
    trimmed = trimmed
        .replace("&&", " and ")
        .replace("||", " or ");
    trimmed = removeImplementationIntentPhrases(trimmed);
    if (strategy == SynthesisStrategy.NAND_ONLY
        || strategy == SynthesisStrategy.TTL_7400
        || strategy == SynthesisStrategy.TTL_7486
        || strategy == SynthesisStrategy.TTL_7408
        || strategy == SynthesisStrategy.TTL_7432
        || strategy == SynthesisStrategy.TTL_7404
        || strategy == SynthesisStrategy.TTL_7402
        || strategy == SynthesisStrategy.MIXED_TTL) {
      trimmed = trimmed
          .replaceAll("(?i)\\busing\\s+only\\s+nand\\s+gates?\\b", "")
          .replaceAll("(?i)\\buse\\s+only\\s+nand\\s+gates?\\b", "")
          .replaceAll("(?i)\\busing\\s+only\\s+nand\\b", "")
          .replaceAll("(?i)\\buse\\s+only\\s+nand\\b", "")
          .replaceAll("(?i)\\bonly\\s+nand\\s+gates?\\b", "")
          .replaceAll("(?i)\\bonly\\s+nand\\b", "")
          .replaceAll("(?i)\\bnand\\s+only\\b", "")
          .replaceAll("(?i)\\b只用\\s*nand\\s*门?\\b", "")
          .replaceAll("(?i)\\b仅用\\s*nand\\s*门?\\b", "")
          .replaceAll("(?i)\\busing\\s+7400\\b", "")
          .replaceAll("(?i)\\buse\\s+7400\\b", "")
          .replaceAll("(?i)\\bwith\\s+7400\\b", "")
          .replaceAll("(?i)用\\s*7400(?:芯片|门)??(?:\\s|$)", "")
          .replaceAll("(?i)\\b7400(?=\\s|$)", "")
          .replaceAll("(?i)\\busing\\s+7486\\b", "")
          .replaceAll("(?i)\\buse\\s+7486\\b", "")
          .replaceAll("(?i)\\bwith\\s+7486\\b", "")
          .replaceAll("(?i)用\\s*7486(?:芯片|门)??(?:\\s|$)", "")
          .replaceAll("(?i)\\b7486(?=\\s|$)", "")
          .replaceAll("(?i)\\busing\\s+7408\\b", "")
          .replaceAll("(?i)\\buse\\s+7408\\b", "")
          .replaceAll("(?i)\\bwith\\s+7408\\b", "")
          .replaceAll("(?i)用\\s*7408(?:芯片|门)??(?:\\s|$)", "")
          .replaceAll("(?i)\\b7408(?=\\s|$)", "")
          .replaceAll("(?i)\\busing\\s+7432\\b", "")
          .replaceAll("(?i)\\buse\\s+7432\\b", "")
          .replaceAll("(?i)\\bwith\\s+7432\\b", "")
          .replaceAll("(?i)用\\s*7432(?:芯片|门)??(?:\\s|$)", "")
          .replaceAll("(?i)\\b7432(?=\\s|$)", "")
          .replaceAll("(?i)\\busing\\s+7404\\b", "")
          .replaceAll("(?i)\\buse\\s+7404\\b", "")
          .replaceAll("(?i)\\bwith\\s+7404\\b", "")
          .replaceAll("(?i)用\\s*7404(?:芯片|门)??(?:\\s|$)", "")
          .replaceAll("(?i)\\b7404(?=\\s|$)", "")
          .replaceAll("(?i)\\busing\\s+7402\\b", "")
          .replaceAll("(?i)\\buse\\s+7402\\b", "")
          .replaceAll("(?i)\\bwith\\s+7402\\b", "")
          .replaceAll("(?i)用\\s*7402(?:芯片|门)??(?:\\s|$)", "")
          .replaceAll("(?i)\\b7402(?=\\s|$)", "")
          .trim();
    }
    return trimmed;
  }

  private static String removeImplementationIntentPhrases(String text) {
    return text
        .replaceAll("(?i)\\bimplement\\s+(?:a\\s+|the\\s+)?chip\\s+with\\s+gates?\\b", "")
        .replaceAll("(?i)\\bgate[- ]level\\s+chip\\b", "")
        .replaceAll("(?i)\\buse\\s+ttl\\s+chips?\\b", "")
        .replaceAll("(?i)\\busing\\s+ttl\\s+chips?\\b", "")
        .replaceAll("(?i)\\bwith\\s+ttl\\s+chips?\\b", "")
        .replaceAll("(?i)\\bchip[- ]level\\b", "")
        .replaceAll("(?i)\\buse\\s+chips?\\b", "")
        .replaceAll("(?i)\\busing\\s+chips?\\b", "")
        .replaceAll("用逻辑门实现\\s*74[0-9A-Za-z]*\\s*芯片?", "")
        .replaceAll("用门实现\\s*74[0-9A-Za-z]*\\s*芯片?", "")
        .replaceAll("用逻辑门实现芯片", "")
        .replaceAll("用门实现芯片", "")
        .replaceAll("门级实现芯片", "")
        .replaceAll("门级展开芯片", "")
        .replaceAll("芯片内部结构", "")
        .replaceAll("用\\s*ttl\\s*芯片", "")
        .replaceAll("(?i)\\b用\\s*ttl\\s*芯片?", "")
        .replaceAll("用ttl芯片", "")
        .replaceAll("用芯片实现", "")
        .replaceAll("芯片级实现", "")
        .trim();
  }

  private static List<String> splitStatements(String text) {
    String normalized = text.replace("\r\n", "\n").replace('\r', '\n');
    List<String> result = new ArrayList<>();
    for (String raw : normalized.split("[;\\n]+")) {
      String statement = raw.trim();
      if (!statement.isEmpty()) {
        result.add(statement);
      }
    }
    return result;
  }

  private static List<String> splitVariables(String text) {
    List<String> vars = new ArrayList<>();
    for (String raw : text.split(",")) {
      String var = raw.trim();
      if (!var.matches("[A-Za-z][A-Za-z0-9_]*")) {
        return List.of();
      }
      vars.add(var);
    }
    return vars;
  }

  private static List<Integer> splitMinterms(String text) {
    List<Integer> result = new ArrayList<>();
    for (String raw : text.split(",")) {
      String number = raw.trim();
      if (number.isEmpty()) {
        continue;
      }
      try {
        result.add(Integer.parseInt(number));
      } catch (NumberFormatException e) {
        return List.of();
      }
    }
    return result;
  }

  private static String nameSuffix(String expression) {
    String lower = expression.toLowerCase(Locale.ROOT);
    List<String> ops = new ArrayList<>();
    boolean hasSpecial = lower.matches(".*\\bxor\\b.*")
        || lower.contains("^")
        || lower.matches(".*\\bnand\\b.*")
        || lower.matches(".*\\bnor\\b.*");
    if (!hasSpecial) {
      return "";
    }
    if (lower.matches(".*\\bnot\\b.*") || lower.contains("!") || lower.contains("~")) {
      ops.add("not");
    }
    if (lower.matches(".*\\band\\b.*") || lower.contains("&") || lower.contains("*")) {
      ops.add("and");
    }
    if (lower.matches(".*\\bxor\\b.*") || lower.contains("^")) {
      ops.add("xor");
    }
    if (lower.matches(".*\\bor\\b.*") || lower.contains("|") || lower.contains("+")) {
      ops.add("or");
    }
    if (lower.matches(".*\\bnand\\b.*")) {
      ops.add("nand");
    }
    if (lower.matches(".*\\bnor\\b.*")) {
      ops.add("nor");
    }
    return String.join("_", ops);
  }

  private static BoolExpr applyStrategy(BoolExpr ast, SynthesisStrategy strategy) {
    if (strategy == SynthesisStrategy.NAND_ONLY || strategy == SynthesisStrategy.TTL_7400) {
      return BoolExpressionNandMapper.toNandOnly(ast);
    }
    return ast;
  }

  private static Optional<CircuitSpec> validateStrategy(CircuitSpec spec, SynthesisStrategy strategy) {
    if (strategy == SynthesisStrategy.MIXED_TTL) {
      boolean allSupportedAndLabeled = spec.components().stream()
          .allMatch(component -> mixedTtlPackageFor(component.factoryName()) != null
              && component.label() != null
              && !component.label().isBlank());
      return allSupportedAndLabeled ? Optional.of(spec) : Optional.empty();
    }
    String expectedFactory = requiredFactory(strategy);
    if (expectedFactory == null) {
      return Optional.of(spec);
    }
    boolean allComponentsMatch = spec.components().stream()
        .allMatch(component -> component.factoryName().equals(expectedFactory)
            && component.label() != null
            && !component.label().isBlank());
    return allComponentsMatch ? Optional.of(spec) : Optional.empty();
  }

  private static String requiredFactory(SynthesisStrategy strategy) {
    return switch (strategy) {
      case TTL_7400 -> "NAND Gate";
      case TTL_7486 -> "XOR Gate";
      case TTL_7408 -> "AND Gate";
      case TTL_7432 -> "OR Gate";
      case TTL_7404 -> "NOT Gate";
      case TTL_7402 -> "NOR Gate";
      case DEFAULT, NAND_ONLY, MIXED_TTL -> null;
    };
  }

  private static String mixedTtlPackageFor(String factory) {
    return switch (factory) {
      case "NAND Gate" -> "7400";
      case "NOR Gate" -> "7402";
      case "NOT Gate" -> "7404";
      case "AND Gate" -> "7408";
      case "OR Gate" -> "7432";
      case "XOR Gate" -> "7486";
      default -> null;
    };
  }

  private static String appendSuffix(String original, String suffix) {
    if (original == null || original.isBlank()) {
      return suffix;
    }
    return original + "_" + suffix;
  }

  private static String nameSuffixOr(String expression, String fallback) {
    String suffix = nameSuffix(expression);
    return suffix.isBlank() ? fallback : suffix;
  }

  private static final class Builder {
    private final String outputLabel;
    private final List<String> variables;
    private final String nameSuffix;
    private final SynthesisStrategy strategy;
    private final List<ComponentSpec> components = new ArrayList<>();
    private final List<NetSpec> nets = new ArrayList<>();
    private final List<LayoutHint> hints = new ArrayList<>();
    private final Map<String, List<EndpointRef>> variableSinks = new LinkedHashMap<>();
    private final Map<EndpointRef, List<EndpointRef>> drivenSinks = new LinkedHashMap<>();
    private final Map<BoolExpr, Signal> memoizedSignals = new LinkedHashMap<>();
    private int gateSeq;
    private int nandSeq;
    private int xorSeq;
    private int andSeq;
    private int orSeq;
    private int notSeq;
    private int norSeq;
    private int nextMixedChipNumber = 1;
    private final Map<String, List<Integer>> mixedChipNumbersByFactory = new LinkedHashMap<>();
    private int netSeq;

    Builder(String outputLabel, List<String> variables) {
      this(outputLabel, variables, "", SynthesisStrategy.DEFAULT);
    }

    Builder(String outputLabel, List<String> variables, String nameSuffix) {
      this(outputLabel, variables, nameSuffix, SynthesisStrategy.DEFAULT);
    }

    Builder(
        String outputLabel,
        List<String> variables,
        String nameSuffix,
        SynthesisStrategy strategy) {
      this.outputLabel = outputLabel;
      this.variables = variables;
      this.nameSuffix = nameSuffix == null ? "" : nameSuffix;
      this.strategy = strategy;
      for (String variable : variables) {
        variableSinks.put(variable, new ArrayList<>());
      }
    }

    CircuitSpec build(BoolExpr expression) {
      Signal signal = emit(expression);
      if (signal.isVariable()) {
        signal = emit(identityExpression(signal.variableName()));
      }
      return finish(signal);
    }

    CircuitSpec build(Map<String, BoolExpr> outputs) {
      Map<String, Signal> outputSignals = new LinkedHashMap<>();
      for (Map.Entry<String, BoolExpr> output : outputs.entrySet()) {
        Signal signal = emit(output.getValue());
        if (signal.isVariable()) {
          signal = emit(identityExpression(signal.variableName()));
        }
        outputSignals.put(output.getKey(), signal);
      }
      return finish(outputSignals);
    }

    CircuitSpec buildMinterms(List<Integer> minterms) {
      List<Signal> products = new ArrayList<>();
      for (int termIndex = 0; termIndex < minterms.size(); termIndex++) {
        int minterm = minterms.get(termIndex);
        int productRow = termIndex;
        Signal product = null;
        for (int bit = 0; bit < variables.size(); bit++) {
          String var = variables.get(bit);
          int shift = variables.size() - bit - 1;
          boolean one = ((minterm >> shift) & 1) == 1;
          Signal literal = one ? Signal.variable(var) : invertedVariable(var, productRow, bit + 1);
          if (product == null) {
            product = literal;
          } else {
            int depth = Math.max(product.depth(), literal.depth()) + 1;
            String id = addGate("AND Gate", "and", depth, productRow);
            connect(product, new EndpointRef(id, 1));
            connect(literal, new EndpointRef(id, 2));
            product = Signal.driver(new EndpointRef(id, 0), depth);
          }
        }
        products.add(product);
      }

      Signal result = products.get(0);
      for (int i = 1; i < products.size(); i++) {
        Signal next = products.get(i);
        int depth = Math.max(result.depth(), next.depth()) + 1;
        int row = i;
        String id = addGate("OR Gate", "or", depth, row);
        connect(result, new EndpointRef(id, 1));
        connect(next, new EndpointRef(id, 2));
        result = Signal.driver(new EndpointRef(id, 0), depth);
      }
      return finish(result);
    }

    private CircuitSpec finish(Signal signal) {
      return finish(Map.of(outputLabel, signal));
    }

    private BoolExpr identityExpression(String variableName) {
      BoolExpr variable = new BoolExpr.Var(variableName);
      if (strategy == SynthesisStrategy.NAND_ONLY || strategy == SynthesisStrategy.TTL_7400) {
        BoolExpr inverted = new BoolExpr.Binary("nand", variable, variable);
        return new BoolExpr.Binary("nand", inverted, inverted);
      }
      return new BoolExpr.Not(new BoolExpr.Not(variable));
    }

    private CircuitSpec finish(Map<String, Signal> outputSignals) {
      List<ExternalPort> ports = new ArrayList<>();
      for (String variable : variables) {
        List<EndpointRef> sinks = variableSinks.getOrDefault(variable, List.of());
        if (sinks.isEmpty()) {
          continue;
        }
        ports.add(new ExternalPort(
            portId(variable),
            variable,
            1,
            PortDirection.INPUT,
            PortSide.LEFT,
            sinks.get(0).componentId(),
            sinks.get(0).endIndex()));

        List<EndpointRef> endpoints = new ArrayList<>();
        endpoints.add(new EndpointRef(portId(variable), 0));
        endpoints.addAll(sinks);
        nets.add(new NetSpec("net_" + portId(variable), endpoints));
      }

      for (Map.Entry<String, Signal> output : outputSignals.entrySet()) {
        String outputId = portId(output.getKey());
        Signal signal = output.getValue();
        ports.add(new ExternalPort(
            outputId,
            output.getKey(),
            1,
            PortDirection.OUTPUT,
            PortSide.RIGHT,
            signal.driver().componentId(),
            signal.driver().endIndex()));
        drivenSinks.computeIfAbsent(signal.driver(), ignored -> new ArrayList<>())
            .add(new EndpointRef(outputId, 0));
      }

      for (Map.Entry<EndpointRef, List<EndpointRef>> driven : drivenSinks.entrySet()) {
        List<EndpointRef> endpoints = new ArrayList<>();
        endpoints.add(driven.getKey());
        endpoints.addAll(driven.getValue());
        nets.add(new NetSpec("net_i" + (++netSeq), endpoints));
      }

      return new CircuitSpec(
          circuitName(circuitBaseName(outputSignals), variables, nameSuffix),
          components,
          ports,
          nets,
          hints);
    }

    private String circuitBaseName(Map<String, Signal> outputSignals) {
      if (!outputLabel.equals("multi")) {
        return outputLabel;
      }
      return "multi_" + String.join("_", outputSignals.keySet());
    }

    private Signal emit(BoolExpr expression) {
      if (expression instanceof BoolExpr.Var var) {
        return Signal.variable(var.name());
      }
      Signal cached = memoizedSignals.get(expression);
      if (cached != null) {
        return cached;
      }
      Signal result;
      if (expression instanceof BoolExpr.Not not) {
        Signal child = emit(not.child());
        if (not.child() instanceof BoolExpr.Var var) {
          result = invertedVariable(
              var.name(),
              Math.max(0, variables.indexOf(var.name()) * 2),
              child.depth() + 1);
          memoizedSignals.put(expression, result);
          return result;
        }
        String id = addGate("NOT Gate", "not", child.depth() + 1, Math.max(0, gateSeq));
        connect(child, new EndpointRef(id, 1));
        result = Signal.driver(new EndpointRef(id, 0), child.depth() + 1);
        memoizedSignals.put(expression, result);
        return result;
      }
      if (expression instanceof BoolExpr.Binary binary) {
        Signal left = emit(binary.left());
        Signal right = emit(binary.right());
        int depth = Math.max(left.depth(), right.depth()) + 1;
        String factory = gateFactory(binary.op());
        String id = addGate(factory, binary.op(), depth, Math.max(0, gateSeq));
        connect(left, new EndpointRef(id, 1));
        connect(right, new EndpointRef(id, 2));
        result = Signal.driver(new EndpointRef(id, 0), depth);
        memoizedSignals.put(expression, result);
        return result;
      }
      throw new IllegalArgumentException("Unknown expression");
    }

    private static String gateFactory(String op) {
      return switch (op) {
        case "and" -> "AND Gate";
        case "or" -> "OR Gate";
        case "xor" -> "XOR Gate";
        case "nand" -> "NAND Gate";
        case "nor" -> "NOR Gate";
        default -> throw new IllegalArgumentException("Unsupported binary op: " + op);
      };
    }

    private Signal invertedVariable(String variable, int row, int depth) {
      String id = addGate("NOT Gate", "not_" + portId(variable), depth, row);
      connect(Signal.variable(variable), new EndpointRef(id, 1));
      return Signal.driver(new EndpointRef(id, 0), depth);
    }

    private String addGate(String factory, String prefix, int depth, int row) {
      String id = prefix + (++gateSeq);
      components.add(new ComponentSpec(id, "Gates", factory, 1, gateLabel(factory)));
      hints.add(new LayoutHint(id, depth, depth, row, null));
      return id;
    }

    private String gateLabel(String factory) {
      if (strategy == SynthesisStrategy.TTL_7400 && factory.equals("NAND Gate")) {
        int index = nandSeq++;
        int chip = index / 4 + 1;
        char gate = (char) ('A' + (index % 4));
        return "U" + chip + gate;
      }
      if (strategy == SynthesisStrategy.TTL_7486 && factory.equals("XOR Gate")) {
        int index = xorSeq++;
        int chip = index / 4 + 1;
        char gate = (char) ('A' + (index % 4));
        return "U" + chip + gate;
      }
      if (strategy == SynthesisStrategy.TTL_7408 && factory.equals("AND Gate")) {
        int index = andSeq++;
        int chip = index / 4 + 1;
        char gate = (char) ('A' + (index % 4));
        return "U" + chip + gate;
      }
      if (strategy == SynthesisStrategy.TTL_7432 && factory.equals("OR Gate")) {
        int index = orSeq++;
        int chip = index / 4 + 1;
        char gate = (char) ('A' + (index % 4));
        return "U" + chip + gate;
      }
      if (strategy == SynthesisStrategy.TTL_7404 && factory.equals("NOT Gate")) {
        int index = notSeq++;
        int chip = index / 6 + 1;
        char gate = (char) ('A' + (index % 6));
        return "U" + chip + gate;
      }
      if (strategy == SynthesisStrategy.TTL_7402 && factory.equals("NOR Gate")) {
        int index = norSeq++;
        int chip = index / 4 + 1;
        char gate = (char) ('A' + (index % 4));
        return "U" + chip + gate;
      }
      if (strategy == SynthesisStrategy.MIXED_TTL) {
        return mixedTtlLabel(factory);
      }
      return "";
    }

    private String mixedTtlLabel(String factory) {
      if (factory.equals("NAND Gate")) {
        int index = nandSeq++;
        return mixedTtlLabel(factory, index, 4);
      }
      if (factory.equals("NOR Gate")) {
        int index = norSeq++;
        return mixedTtlLabel(factory, index, 4);
      }
      if (factory.equals("NOT Gate")) {
        int index = notSeq++;
        return mixedTtlLabel(factory, index, 6);
      }
      if (factory.equals("AND Gate")) {
        int index = andSeq++;
        return mixedTtlLabel(factory, index, 4);
      }
      if (factory.equals("OR Gate")) {
        int index = orSeq++;
        return mixedTtlLabel(factory, index, 4);
      }
      if (factory.equals("XOR Gate")) {
        int index = xorSeq++;
        return mixedTtlLabel(factory, index, 4);
      }
      return "";
    }

    private String mixedTtlLabel(String factory, int index, int gatesPerChip) {
      int chipIndex = index / gatesPerChip;
      List<Integer> chipNumbers = mixedChipNumbersByFactory.computeIfAbsent(
          factory,
          ignored -> new ArrayList<>());
      while (chipNumbers.size() <= chipIndex) {
        chipNumbers.add(nextMixedChipNumber++);
      }
      char gate = (char) ('A' + (index % gatesPerChip));
      return "U" + chipNumbers.get(chipIndex) + gate;
    }

    private void connect(Signal signal, EndpointRef sink) {
      if (signal.isVariable()) {
        variableSinks.computeIfAbsent(signal.variableName(), ignored -> new ArrayList<>()).add(sink);
      } else {
        drivenSinks.computeIfAbsent(signal.driver(), ignored -> new ArrayList<>()).add(sink);
      }
    }

    private static String circuitName(String output, List<String> variables, String nameSuffix) {
      String suffix = variables.isEmpty() ? "" : "_" + String.join("", variables).toLowerCase(Locale.ROOT);
      String opSuffix = nameSuffix == null || nameSuffix.isBlank() ? "" : "_" + portId(nameSuffix);
      return "logic_" + portId(output) + suffix + opSuffix;
    }

    private static String portId(String label) {
      String id = label.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]+", "_");
      id = id.replaceAll("^_+|_+$", "");
      if (id.isBlank()) {
        return "out";
      }
      if (Character.isDigit(id.charAt(0))) {
        return "p_" + id;
      }
      return id;
    }
  }

  private record Signal(EndpointRef driver, String variableName, int depth) {
    static Signal driver(EndpointRef driver, int depth) {
      return new Signal(driver, null, depth);
    }

    static Signal variable(String name) {
      return new Signal(null, name, 0);
    }

    boolean isVariable() {
      return variableName != null;
    }
  }
}
