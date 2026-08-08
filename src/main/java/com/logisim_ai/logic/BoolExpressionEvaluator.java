package com.logisim_ai.logic;

import java.util.Map;

/** Evaluates a Boolean expression AST for a single input assignment. */
public final class BoolExpressionEvaluator {
  private BoolExpressionEvaluator() {}

  public static boolean evaluate(BoolExpr expression, Map<String, Boolean> inputs) {
    if (expression instanceof BoolExpr.Var var) {
      Boolean value = inputs.get(var.name());
      if (value == null) {
        value = inputs.get(var.name().toLowerCase(java.util.Locale.ROOT));
      }
      if (value == null) {
        throw new IllegalArgumentException("Missing input value for variable: " + var.name());
      }
      return value;
    }
    if (expression instanceof BoolExpr.Not not) {
      return !evaluate(not.child(), inputs);
    }
    if (expression instanceof BoolExpr.Binary binary) {
      boolean left = evaluate(binary.left(), inputs);
      boolean right = evaluate(binary.right(), inputs);
      return switch (binary.op()) {
        case "and" -> left && right;
        case "or" -> left || right;
        case "xor" -> left ^ right;
        case "nand" -> !(left && right);
        case "nor" -> !(left || right);
        default -> throw new IllegalArgumentException("Unsupported binary op: " + binary.op());
      };
    }
    throw new IllegalArgumentException("Unknown expression");
  }
}
