package com.logisim_ai.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Parser for a compact Boolean expression language. */
public final class BoolExpressionParser {
  private final List<Token> tokens;
  private int index;

  public BoolExpressionParser(String expression) {
    this.tokens = tokenize(expression);
  }

  public BoolExpr parse() {
    BoolExpr result = parseOr();
    if (!peek("eof")) {
      throw new IllegalArgumentException("Unexpected token: " + tokens.get(index).text());
    }
    return result;
  }

  public static BoolExpr parseNormalized(String expression) {
    return BoolExpressionNormalizer.normalize(new BoolExpressionParser(expression).parse());
  }

  private BoolExpr parseOr() {
    BoolExpr left = parseXor();
    while (peek("or") || peek("nor")) {
      String op = tokens.get(index).kind();
      index++;
      left = new BoolExpr.Binary(op, left, parseXor());
    }
    return left;
  }

  private BoolExpr parseXor() {
    BoolExpr left = parseAnd();
    while (match("xor")) {
      left = new BoolExpr.Binary("xor", left, parseAnd());
    }
    return left;
  }

  private BoolExpr parseAnd() {
    BoolExpr left = parseUnary();
    while (peek("and") || peek("nand")) {
      String op = tokens.get(index).kind();
      index++;
      left = new BoolExpr.Binary(op, left, parseUnary());
    }
    return left;
  }

  private BoolExpr parseUnary() {
    if (match("not")) {
      return new BoolExpr.Not(parseUnary());
    }
    if (match("(")) {
      BoolExpr inner = parseOr();
      require(")");
      return inner;
    }
    Token token = tokens.get(index);
    if (match("var")) {
      return new BoolExpr.Var(token.text());
    }
    throw new IllegalArgumentException("Expected variable/expression");
  }

  private boolean match(String kind) {
    if (peek(kind)) {
      index++;
      return true;
    }
    return false;
  }

  private void require(String kind) {
    if (!match(kind)) {
      throw new IllegalArgumentException("Expected " + kind);
    }
  }

  private boolean peek(String kind) {
    return tokens.get(index).kind().equals(kind);
  }

  private static List<Token> tokenize(String input) {
    List<Token> result = new ArrayList<>();
    int pos = 0;
    while (pos < input.length()) {
      char ch = input.charAt(pos);
      if (Character.isWhitespace(ch)) {
        pos++;
        continue;
      }
      if (ch == '(' || ch == ')') {
        result.add(new Token(String.valueOf(ch), String.valueOf(ch)));
        pos++;
        continue;
      }
      if (ch == '!' || ch == '~') {
        result.add(new Token("not", "not"));
        pos++;
        continue;
      }
      if (ch == '&' || ch == '*') {
        result.add(new Token("and", "and"));
        pos++;
        continue;
      }
      if (ch == '|' || ch == '+') {
        result.add(new Token("or", "or"));
        pos++;
        continue;
      }
      if (ch == '^') {
        result.add(new Token("xor", "xor"));
        pos++;
        continue;
      }
      // Chinese Boolean operators. Handle multi-char words before single-char ones
      // so "与非" (nand) is not split into 与 + 非.
      ChineseOp chineseOp = chineseOperator(input, pos);
      if (chineseOp != null) {
        result.add(new Token(chineseOp.kind(), chineseOp.kind()));
        pos += chineseOp.length();
        continue;
      }
      if (Character.isLetter(ch)) {
        int start = pos;
        pos++;
        while (pos < input.length()) {
          char next = input.charAt(pos);
          // ASCII letters/digits/underscore are part of the identifier; Chinese
          // characters act as separators (they are operators like 与/或/非).
          if (!isAsciiIdentifierPart(next)) {
            break;
          }
          pos++;
        }
        String word = input.substring(start, pos);
        String lower = word.toLowerCase(Locale.ROOT);
        if (lower.equals("and")
            || lower.equals("or")
            || lower.equals("not")
            || lower.equals("xor")
            || lower.equals("nand")
            || lower.equals("nor")) {
          result.add(new Token(lower, lower));
        } else {
          result.add(new Token("var", word));
        }
        continue;
      }
      throw new IllegalArgumentException("Unsupported character: " + ch);
    }
    result.add(new Token("eof", ""));
    return result;
  }

  private static boolean isAsciiIdentifierPart(char c) {
    return (c >= 'a' && c <= 'z')
        || (c >= 'A' && c <= 'Z')
        || (c >= '0' && c <= '9')
        || c == '_';
  }

  /** Detect a Chinese Boolean operator at the given position, or return null. */
  private static ChineseOp chineseOperator(String input, int pos) {
    if (input.regionMatches(pos, "与非", 0, 2)) {
      return new ChineseOp("nand", 2);
    }
    if (input.regionMatches(pos, "或非", 0, 2)) {
      return new ChineseOp("nor", 2);
    }
    if (input.regionMatches(pos, "异或", 0, 2)) {
      return new ChineseOp("xor", 2);
    }
    if (pos < input.length() && input.charAt(pos) == '与') {
      return new ChineseOp("and", 1);
    }
    if (pos < input.length() && input.charAt(pos) == '或') {
      return new ChineseOp("or", 1);
    }
    if (pos < input.length() && input.charAt(pos) == '非') {
      return new ChineseOp("not", 1);
    }
    return null;
  }

  private record ChineseOp(String kind, int length) {}

  private record Token(String kind, String text) {}
}
