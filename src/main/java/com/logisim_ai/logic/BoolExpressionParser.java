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
      if (Character.isLetter(ch)) {
        int start = pos;
        pos++;
        while (pos < input.length()) {
          char next = input.charAt(pos);
          if (!Character.isLetterOrDigit(next) && next != '_') {
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

  private record Token(String kind, String text) {}
}
