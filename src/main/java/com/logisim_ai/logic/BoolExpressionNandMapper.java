package com.logisim_ai.logic;

/** Maps supported Boolean expressions to an equivalent expression containing only NAND. */
public final class BoolExpressionNandMapper {
  private BoolExpressionNandMapper() {}

  public static BoolExpr toNandOnly(BoolExpr expression) {
    if (expression instanceof BoolExpr.Var) {
      return expression;
    }
    if (expression instanceof BoolExpr.Not not) {
      BoolExpr child = toNandOnly(not.child());
      return nand(child, child);
    }
    if (expression instanceof BoolExpr.Binary binary) {
      BoolExpr left = toNandOnly(binary.left());
      BoolExpr right = toNandOnly(binary.right());
      return switch (binary.op()) {
        case "nand" -> nand(left, right);
        case "and" -> {
          BoolExpr n = nand(left, right);
          yield nand(n, n);
        }
        case "or" -> nand(nand(left, left), nand(right, right));
        case "nor" -> {
          BoolExpr or = nand(nand(left, left), nand(right, right));
          yield nand(or, or);
        }
        case "xor" -> {
          BoolExpr shared = nand(left, right);
          BoolExpr leftTerm = nand(left, shared);
          BoolExpr rightTerm = nand(right, shared);
          yield nand(leftTerm, rightTerm);
        }
        default -> throw new IllegalArgumentException("Unsupported binary op: " + binary.op());
      };
    }
    throw new IllegalArgumentException("Unknown expression");
  }

  public static boolean isNandOnly(BoolExpr expression) {
    if (expression instanceof BoolExpr.Var) {
      return true;
    }
    if (expression instanceof BoolExpr.Not) {
      return false;
    }
    if (expression instanceof BoolExpr.Binary binary) {
      return binary.op().equals("nand")
          && isNandOnly(binary.left())
          && isNandOnly(binary.right());
    }
    throw new IllegalArgumentException("Unknown expression");
  }

  private static BoolExpr nand(BoolExpr left, BoolExpr right) {
    return new BoolExpr.Binary("nand", left, right);
  }
}
