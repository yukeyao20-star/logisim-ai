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
 * Experiment 2 (v2): Full Adder generated through the IR -> netlist compiler path.
 *
 * <p>Full Adder = two half adders + OR gate for carry.
 * Components: XOR1, XOR2, AND1, AND2, OR1
 *   XOR1: A XOR B -> X1
 *   XOR2: X1 XOR Cin -> SUM
 *   AND1: A AND B -> AB
 *   AND2: X1 AND Cin -> XC
 *   OR1: AB OR XC -> Cout
 */
public final class GenerateFullAdder {
  private GenerateFullAdder() {}

  public static void main(String[] args) {
    try {
      CircuitSpec spec = new CircuitSpec(
          "full_adder",
          // Components
          List.of(
              new ComponentSpec("xor1", "Gates", "XOR Gate", 1, ""),
              new ComponentSpec("xor2", "Gates", "XOR Gate", 1, ""),
              new ComponentSpec("and1", "Gates", "AND Gate", 1, ""),
              new ComponentSpec("and2", "Gates", "AND Gate", 1, ""),
              new ComponentSpec("or1", "Gates", "OR Gate", 1, "")),
          // External ports
          List.of(
              new ExternalPort("a", "A", 1, PortDirection.INPUT, PortSide.LEFT, "xor1", 1),
              new ExternalPort("b", "B", 1, PortDirection.INPUT, PortSide.LEFT, "xor1", 2),
              new ExternalPort("cin", "Cin", 1, PortDirection.INPUT, PortSide.LEFT, "xor2", 2),
              new ExternalPort("sum", "SUM", 1, PortDirection.OUTPUT, PortSide.RIGHT, "xor2", 0),
              new ExternalPort("cout", "Cout", 1, PortDirection.OUTPUT, PortSide.RIGHT, "or1", 0)),
          // Nets (wires between components)
          List.of(
              // A -> AND1 input0 (fanout from xor1 input0)
              new NetSpec("a_to_and1", List.of(
                  new EndpointRef("xor1", 1),   // source: A input of XOR1
                  new EndpointRef("and1", 1))), // sink: input0 of AND1
              // B -> AND1 input1 (fanout from xor1 input1)
              new NetSpec("b_to_and1", List.of(
                  new EndpointRef("xor1", 2),   // source: B input of XOR1
                  new EndpointRef("and1", 2))), // sink: input1 of AND1
              // XOR1 output -> XOR2 input0
              new NetSpec("x1_to_xor2", List.of(
                  new EndpointRef("xor1", 0),   // source: XOR1 output
                  new EndpointRef("xor2", 1))), // sink: XOR2 input0
              // XOR1 output -> AND2 input0 (fanout)
              new NetSpec("x1_to_and2", List.of(
                  new EndpointRef("xor1", 0),   // source: XOR1 output
                  new EndpointRef("and2", 1))), // sink: AND2 input0
              // Cin -> AND2 input1 (fanout from xor2 input1)
              new NetSpec("cin_to_and2", List.of(
                  new EndpointRef("xor2", 2),   // source: Cin input of XOR2
                  new EndpointRef("and2", 2))), // sink: AND2 input1
              // AND1 output -> OR1 input0
              new NetSpec("ab_to_or1", List.of(
                  new EndpointRef("and1", 0),   // source: AND1 output
                  new EndpointRef("or1", 1))), // sink: OR1 input0
              // AND2 output -> OR1 input1
              new NetSpec("xc_to_or1", List.of(
                  new EndpointRef("and2", 0),   // source: AND2 output
                  new EndpointRef("or1", 2)))), // sink: OR1 input1
          // Layout hints: rank determines column
          List.of(
              new LayoutHint("xor1", 0, 0, 0, null),
              new LayoutHint("and1", 0, 0, 1, null),
              new LayoutHint("xor2", 1, 1, 0, null),
              new LayoutHint("and2", 1, 1, 1, null),
              new LayoutHint("or1", 2, 2, 0, null)));

      CircuitSemanticValidator.requireValid(spec);
      LogisimCompiler.compileNetlist(spec, new File("generated/circ/full_adder.circ"));
      System.out.println("[OK] full_adder.circ generated through IR netlist compiler");
    } catch (Exception e) {
      throw new RuntimeException("Full adder generation failed", e);
    }
  }
}
