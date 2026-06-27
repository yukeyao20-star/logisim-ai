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
 * Experiment 2 (v3): Full Adder generated through the IR -> netlist compiler path.
 *
 * <p>Full Adder = two half adders + OR gate for carry.
 * Components: XOR1, XOR2, AND1, AND2, OR1
 *   XOR1: A XOR B -> X1
 *   XOR2: X1 XOR Cin -> SUM
 *   AND1: A AND B -> AB
 *   AND2: X1 AND Cin -> XC
 *   OR1: AB OR XC -> Cout
 *
 * <p>All connections are defined as semantic nets (including external ports).
 * The compiler uses Tunnel routing to avoid long wires crossing gate endpoints.
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
          // External ports (Pin creation only, no auto-wiring)
          List.of(
              new ExternalPort("a", "A", 1, PortDirection.INPUT, PortSide.LEFT, "xor1", 1),
              new ExternalPort("b", "B", 1, PortDirection.INPUT, PortSide.LEFT, "xor1", 2),
              new ExternalPort("cin", "Cin", 1, PortDirection.INPUT, PortSide.LEFT, "xor2", 2),
              new ExternalPort("sum", "SUM", 1, PortDirection.OUTPUT, PortSide.RIGHT, "xor2", 0),
              new ExternalPort("cout", "Cout", 1, PortDirection.OUTPUT, PortSide.RIGHT, "or1", 0)),
          // Nets (semantic IR: all connections including external ports)
          List.of(
              // A: input pin -> xor1 input0 -> and1 input0 (fanout)
              new NetSpec("A", List.of(
                  new EndpointRef("a", 0),
                  new EndpointRef("xor1", 1),
                  new EndpointRef("and1", 1))),
              // B: input pin -> xor1 input1 -> and1 input1 (fanout)
              new NetSpec("B", List.of(
                  new EndpointRef("b", 0),
                  new EndpointRef("xor1", 2),
                  new EndpointRef("and1", 2))),
              // Cin: input pin -> xor2 input1 -> and2 input1 (fanout)
              new NetSpec("Cin", List.of(
                  new EndpointRef("cin", 0),
                  new EndpointRef("xor2", 2),
                  new EndpointRef("and2", 2))),
              // X1: xor1 output -> xor2 input0 -> and2 input0 (fanout)
              new NetSpec("X1", List.of(
                  new EndpointRef("xor1", 0),
                  new EndpointRef("xor2", 1),
                  new EndpointRef("and2", 1))),
              // AB: and1 output -> or1 input0
              new NetSpec("AB", List.of(
                  new EndpointRef("and1", 0),
                  new EndpointRef("or1", 1))),
              // XC: and2 output -> or1 input1
              new NetSpec("XC", List.of(
                  new EndpointRef("and2", 0),
                  new EndpointRef("or1", 2))),
              // SUM: xor2 output -> output pin
              new NetSpec("SUM", List.of(
                  new EndpointRef("xor2", 0),
                  new EndpointRef("sum", 0))),
              // Cout: or1 output -> output pin
              new NetSpec("Cout", List.of(
                  new EndpointRef("or1", 0),
                  new EndpointRef("cout", 0)))),
          // Layout hints
          List.of(
              new LayoutHint("xor1", 0, 0, 0, null),
              new LayoutHint("and1", 0, 0, 1, null),
              new LayoutHint("xor2", 1, 1, 0, null),
              new LayoutHint("and2", 1, 1, 1, null),
              new LayoutHint("or1", 2, 2, 0, null)));

      CircuitSemanticValidator.requireValid(spec);
      LogisimCompiler.compileNetlist(spec, new File("generated/circ/full_adder.circ"));
      System.out.println("[OK] full_adder.circ generated through IR netlist compiler (Tunnel routing)");
    } catch (Exception e) {
      throw new RuntimeException("Full adder generation failed", e);
    }
  }
}
