package com.logisim_ai.experiment;

import com.logisim_ai.compiler.LogisimCompiler;
import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.PortSide;
import com.logisim_ai.validation.CircuitSemanticValidator;
import java.io.File;
import java.util.List;

/**
 * Experiment 5 (v2): JK Flip-Flop generated through the IR compiler path.
 *
 * <p>Uses the native Memory library J-K Flip-Flop component.
 * Port order in Logisim-evolution:
 *   0 J, 1 K, 2 CLK, 3 Q, 4 /Q, 5 CLR, 6 PRE
 *
 * <p>Replaces the old GenerateJkFlipFlop that used direct API calls.
 * Now uses CircuitSpec + compileSingleComponent for declarative generation.
 */
public final class GenerateJkFlipFlop {
  private GenerateJkFlipFlop() {}

  public static void main(String[] args) {
    try {
      CircuitSpec spec = new CircuitSpec(
          "jk_flipflop",
          // Components
          List.of(
              new ComponentSpec("jk", "Memory", "J-K Flip-Flop", 1, "JK")),
          // External ports (single-component mode: no nets needed)
          List.of(
              new ExternalPort("j", "J", 1, PortDirection.INPUT, PortSide.LEFT, "jk", 0),
              new ExternalPort("k", "K", 1, PortDirection.INPUT, PortSide.LEFT, "jk", 1),
              new ExternalPort("clk", "CLK", 1, PortDirection.INPUT, PortSide.LEFT, "jk", 2),
              new ExternalPort("q", "Q", 1, PortDirection.OUTPUT, PortSide.RIGHT, "jk", 3),
              new ExternalPort("qbar", "/Q", 1, PortDirection.OUTPUT, PortSide.RIGHT, "jk", 4),
              new ExternalPort("clr", "CLR", 1, PortDirection.INPUT, PortSide.BOTTOM, "jk", 5),
              new ExternalPort("pre", "PRE", 1, PortDirection.INPUT, PortSide.TOP, "jk", 6)));

      CircuitSemanticValidator.requireValid(spec);
      LogisimCompiler.compileSingleComponent(spec, new File("generated/circ/jk_flipflop.circ"));
      System.out.println("[OK] jk_flipflop.circ generated through IR compiler");
    } catch (Exception e) {
      throw new RuntimeException("JK flip-flop generation failed", e);
    }
  }
}
