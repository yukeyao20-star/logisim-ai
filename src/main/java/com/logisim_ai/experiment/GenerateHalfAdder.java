package com.logisim_ai.experiment;

import com.logisim_ai.compiler.LogisimCompiler;
import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.LayoutHint;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.PortSide;
import com.logisim_ai.validation.CircuitSemanticValidator;
import java.io.File;
import java.util.List;

/**
 * Experiment 1 (v2): Half Adder generated through the IR -> netlist compiler path.
 *
 * <p>Half Adder = XOR + AND gates.
 * Components: XOR1, AND1
 *   XOR1: A XOR B -> SUM
 *   AND1: A AND B -> CARRY
 *
 * <p>Replaces the old GenerateHalfAdder that used direct API calls with Tunnels.
 * Now uses CircuitSpec + NetSpec + compileNetlist for cleaner, declarative generation.
 */
public final class GenerateHalfAdder {
  private GenerateHalfAdder() {}

  public static void main(String[] args) {
    try {
      CircuitSpec spec = new CircuitSpec(
          "half_adder",
          // Components
          List.of(
              new ComponentSpec("xor1", "Gates", "XOR Gate", 1, ""),
              new ComponentSpec("and1", "Gates", "AND Gate", 1, "")),
          // External ports
          List.of(
              new ExternalPort("a", "A", 1, PortDirection.INPUT, PortSide.LEFT, "xor1", 1),
              new ExternalPort("b", "B", 1, PortDirection.INPUT, PortSide.LEFT, "xor1", 2),
              new ExternalPort("sum", "SUM", 1, PortDirection.OUTPUT, PortSide.RIGHT, "xor1", 0),
              new ExternalPort("carry", "CARRY", 1, PortDirection.OUTPUT, PortSide.RIGHT, "and1", 0)),
          // Nets (semantic IR: all connections including external ports)
          List.of(
              new NetSpec("A", List.of(
                  new EndpointRef("a", 0),
                  new EndpointRef("xor1", 1),
                  new EndpointRef("and1", 1))),
              new NetSpec("B", List.of(
                  new EndpointRef("b", 0),
                  new EndpointRef("xor1", 2),
                  new EndpointRef("and1", 2))),
              new NetSpec("SUM", List.of(
                  new EndpointRef("xor1", 0),
                  new EndpointRef("sum", 0))),
              new NetSpec("CARRY", List.of(
                  new EndpointRef("and1", 0),
                  new EndpointRef("carry", 0)))),
          // Layout hints
          List.of(
              new LayoutHint("xor1", 0, 0, 0, null),
              new LayoutHint("and1", 0, 0, 1, null)));

      CircuitSemanticValidator.requireValid(spec);
      LogisimCompiler.compileNetlist(spec, new File("generated/circ/half_adder.circ"));
      System.out.println("[OK] half_adder.circ generated through IR netlist compiler");
    } catch (Exception e) {
      throw new RuntimeException("Half adder generation failed", e);
    }
  }
}
