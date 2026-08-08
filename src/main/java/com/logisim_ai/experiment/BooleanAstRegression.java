package com.logisim_ai.experiment;

import com.logisim_ai.logic.BoolExpr;
import com.logisim_ai.logic.BoolExpressionEvaluator;
import com.logisim_ai.logic.BoolExpressionNandMapper;
import com.logisim_ai.logic.BoolExpressionNormalizer;
import com.logisim_ai.logic.BoolExpressionParser;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Regression checks for shared Boolean AST parsing and canonicalization. */
public final class BooleanAstRegression {
  private BooleanAstRegression() {}

  public static void main(String[] args) {
    requireSame("A and B", "B and A");
    requireSame("(C xor A) xor B", "A xor (B xor C)");
    requireSame("not not A", "A");
    requireSame("A nand B", "B nand A");
    requireDifferent("A nand (B nand C)", "(A nand B) nand C");
    requireNandEquivalent("not A", List.of("a"));
    requireNandEquivalent("A and B", List.of("a", "b"));
    requireNandEquivalent("A or B", List.of("a", "b"));
    requireNandEquivalent("A xor B", List.of("a", "b"));
    requireNandEquivalent("A nor B", List.of("a", "b"));
    requireNandEquivalent("(A xor B) and (C or not A)", List.of("a", "b", "c"));
    System.out.println("[OK] Boolean AST parse/normalization regression passed");
  }

  private static void requireSame(String left, String right) {
    BoolExpr normalizedLeft = BoolExpressionParser.parseNormalized(left);
    BoolExpr normalizedRight = BoolExpressionParser.parseNormalized(right);
    String leftKey = BoolExpressionNormalizer.structuralKey(normalizedLeft);
    String rightKey = BoolExpressionNormalizer.structuralKey(normalizedRight);
    if (!leftKey.equals(rightKey)) {
      throw new IllegalStateException("Expected equivalent expressions: "
          + left + " -> " + leftKey
          + ", " + right + " -> " + rightKey);
    }
  }

  private static void requireDifferent(String left, String right) {
    BoolExpr normalizedLeft = BoolExpressionParser.parseNormalized(left);
    BoolExpr normalizedRight = BoolExpressionParser.parseNormalized(right);
    String leftKey = BoolExpressionNormalizer.structuralKey(normalizedLeft);
    String rightKey = BoolExpressionNormalizer.structuralKey(normalizedRight);
    if (leftKey.equals(rightKey)) {
      throw new IllegalStateException("Expected different expressions: "
          + left + " and " + right + " both -> " + leftKey);
    }
  }

  private static void requireNandEquivalent(String expression, List<String> variables) {
    BoolExpr original = BoolExpressionParser.parseNormalized(expression);
    BoolExpr nandOnly = BoolExpressionNandMapper.toNandOnly(original);
    if (!BoolExpressionNandMapper.isNandOnly(nandOnly)) {
      throw new IllegalStateException("Expression was not mapped to NAND-only: " + expression);
    }

    for (int mask = 0; mask < (1 << variables.size()); mask++) {
      Map<String, Boolean> inputs = valuesFor(variables, mask);
      boolean expected = BoolExpressionEvaluator.evaluate(original, inputs);
      boolean actual = BoolExpressionEvaluator.evaluate(nandOnly, inputs);
      if (expected != actual) {
        throw new IllegalStateException("NAND mapping mismatch for " + expression
            + " inputs=" + inputs
            + " expected=" + expected
            + " actual=" + actual);
      }
    }
  }

  private static Map<String, Boolean> valuesFor(List<String> variables, int mask) {
    Map<String, Boolean> result = new LinkedHashMap<>();
    for (int i = 0; i < variables.size(); i++) {
      int shift = variables.size() - i - 1;
      result.put(variables.get(i), ((mask >> shift) & 1) == 1);
    }
    return result;
  }
}
