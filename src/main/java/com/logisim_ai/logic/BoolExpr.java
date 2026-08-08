package com.logisim_ai.logic;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Shared Boolean expression AST used by synthesis, optimization, and technology mapping. */
public sealed interface BoolExpr permits BoolExpr.Var, BoolExpr.Not, BoolExpr.Binary {
  void collectVariables(Set<String> vars);

  default List<String> variables() {
    Set<String> vars = new LinkedHashSet<>();
    collectVariables(vars);
    return List.copyOf(vars);
  }

  record Var(String name) implements BoolExpr {
    @Override
    public void collectVariables(Set<String> vars) {
      vars.add(name);
    }
  }

  record Not(BoolExpr child) implements BoolExpr {
    @Override
    public void collectVariables(Set<String> vars) {
      child.collectVariables(vars);
    }
  }

  record Binary(String op, BoolExpr left, BoolExpr right) implements BoolExpr {
    @Override
    public void collectVariables(Set<String> vars) {
      left.collectVariables(vars);
      right.collectVariables(vars);
    }
  }
}
