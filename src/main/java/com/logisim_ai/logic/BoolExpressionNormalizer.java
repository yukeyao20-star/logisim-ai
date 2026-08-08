package com.logisim_ai.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Canonicalizes Boolean expressions so equivalent subexpressions can be shared. */
public final class BoolExpressionNormalizer {
  private BoolExpressionNormalizer() {}

  public static BoolExpr normalize(BoolExpr expression) {
    if (expression instanceof BoolExpr.Var) {
      return expression;
    }
    if (expression instanceof BoolExpr.Not not) {
      BoolExpr child = normalize(not.child());
      if (child instanceof BoolExpr.Not nested) {
        return normalize(nested.child());
      }
      return new BoolExpr.Not(child);
    }
    if (expression instanceof BoolExpr.Binary binary) {
      String op = binary.op();
      if (isAssociativeCommutative(op)) {
        List<BoolExpr> terms = new ArrayList<>();
        collectNormalizedTerms(op, binary, terms);
        terms.sort((left, right) -> structuralKey(left).compareTo(structuralKey(right)));
        return rebuildBinary(op, terms);
      }
      BoolExpr left = normalize(binary.left());
      BoolExpr right = normalize(binary.right());
      if (isCommutative(op) && structuralKey(left).compareTo(structuralKey(right)) > 0) {
        return new BoolExpr.Binary(op, right, left);
      }
      return new BoolExpr.Binary(op, left, right);
    }
    throw new IllegalArgumentException("Unknown expression");
  }

  public static String structuralKey(BoolExpr expression) {
    if (expression instanceof BoolExpr.Var var) {
      return "v:" + var.name().toLowerCase(Locale.ROOT);
    }
    if (expression instanceof BoolExpr.Not not) {
      return "n:" + structuralKey(not.child());
    }
    if (expression instanceof BoolExpr.Binary binary) {
      return "b:" + binary.op() + "("
          + structuralKey(binary.left()) + ","
          + structuralKey(binary.right()) + ")";
    }
    throw new IllegalArgumentException("Unknown expression");
  }

  private static void collectNormalizedTerms(String op, BoolExpr expression, List<BoolExpr> terms) {
    if (expression instanceof BoolExpr.Binary binary
        && binary.op().equals(op)
        && isAssociativeCommutative(op)) {
      collectNormalizedTerms(op, binary.left(), terms);
      collectNormalizedTerms(op, binary.right(), terms);
      return;
    }
    terms.add(normalize(expression));
  }

  private static BoolExpr rebuildBinary(String op, List<BoolExpr> terms) {
    if (terms.isEmpty()) {
      throw new IllegalArgumentException("No terms for " + op);
    }
    BoolExpr result = terms.get(0);
    for (int i = 1; i < terms.size(); i++) {
      result = new BoolExpr.Binary(op, result, terms.get(i));
    }
    return result;
  }

  private static boolean isAssociativeCommutative(String op) {
    return op.equals("and") || op.equals("or") || op.equals("xor");
  }

  private static boolean isCommutative(String op) {
    return isAssociativeCommutative(op) || op.equals("nand") || op.equals("nor");
  }
}
