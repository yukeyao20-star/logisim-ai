package com.logisim_ai.experiment;

import com.logisim_ai.compiler.LogisimCompiler;
import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.PortSide;
import java.io.File;
import java.util.List;

/** Generates a readable 2-bit adder through the first IR -> layout -> Logisim compiler path. */
public final class GenerateTwoBitAdder {
  private GenerateTwoBitAdder() {}

  public static void main(String[] args) {
    try {
      CircuitSpec spec = new CircuitSpec(
          "two_bit_ripple_adder",
          List.of(new ComponentSpec("adder", "Arithmetic", "Adder", 2, "ADD2")),
          List.of(
              // Native Adder port order: A, B, Sum, Cin, Cout.
              new ExternalPort("a", "A[1:0]", 2, PortDirection.INPUT, PortSide.LEFT, "adder", 0),
              new ExternalPort("b", "B[1:0]", 2, PortDirection.INPUT, PortSide.LEFT, "adder", 1),
              new ExternalPort("cin", "Cin", 1, PortDirection.INPUT, PortSide.LEFT, "adder", 3),
              new ExternalPort("sum", "S[1:0]", 2, PortDirection.OUTPUT, PortSide.RIGHT, "adder", 2),
              new ExternalPort("cout", "Cout", 1, PortDirection.OUTPUT, PortSide.RIGHT, "adder", 4)));

      LogisimCompiler.compileSingleComponent(spec, new File("generated/circ/two_bit_ripple_adder.circ"));
      System.out.println("[OK] two_bit_ripple_adder.circ generated through IR compiler");
    } catch (Exception e) {
      throw new RuntimeException("2-bit adder generation failed", e);
    }
  }
}
