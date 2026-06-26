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
import java.util.ArrayList;
import java.util.List;

/**
 * Experiment 4 (v2): TTL 74151 8-to-1 Multiplexer macro generated through IR netlist compiler.
 *
 * <p>74151 logic:
 * - 8 data inputs D0-D7, 3 select lines S0-S2, enable nG (active low)
 * - Output Y = EN & (D0&nS2&nS1&nS0 | D1&nS2&nS1&S0 | ... | D7&S2&S1&S0)
 * - Output nW = NOT Y
 *
 * <p>Each minterm uses a chain of 4 two-input AND gates:
 *   and_i_a: Di AND EN
 *   and_i_b: result AND (s0 or nS0)
 *   and_i_c: result AND (s1 or nS1)
 *   and_i_d: result AND (s2 or nS2)  → Ti (minterm)
 *
 * <p>OR tree uses 7 two-input OR gates: 8→4→2→1
 *
 * <p>Replaces the old GenerateTtl74151Macro that used direct API + Tunnels.
 */
public final class GenerateTtl74151Macro {
  private static final String[] DATA = {"D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7"};

  private GenerateTtl74151Macro() {}

  public static void main(String[] args) {
    try {
      List<ComponentSpec> components = new ArrayList<>();
      List<ExternalPort> ports = new ArrayList<>();
      List<NetSpec> nets = new ArrayList<>();
      List<LayoutHint> hints = new ArrayList<>();

      build74151(components, ports, nets, hints);

      CircuitSpec spec = new CircuitSpec(
          "ttl_74151_macro",
          components, ports, nets, hints);

      CircuitSemanticValidator.requireValid(spec);
      LogisimCompiler.compileNetlist(spec, new File("generated/circ/ttl_74151_macro.circ"));
      System.out.println("[OK] ttl_74151_macro.circ generated through IR netlist compiler");

      // Also generate native 74151
      generateNative74151();
    } catch (Exception e) {
      throw new RuntimeException("74151 macro generation failed", e);
    }
  }

  private static void build74151(
      List<ComponentSpec> components,
      List<ExternalPort> ports,
      List<NetSpec> nets,
      List<LayoutHint> hints) {

    // ---- Inverters for select lines and enable ----
    components.add(new ComponentSpec("not_s0", "Gates", "NOT Gate", 1, ""));
    components.add(new ComponentSpec("not_s1", "Gates", "NOT Gate", 1, ""));
    components.add(new ComponentSpec("not_s2", "Gates", "NOT Gate", 1, ""));
    components.add(new ComponentSpec("not_ng", "Gates", "NOT Gate", 1, ""));

    // ---- 2-input AND gate chains for minterms ----
    // and_i_a: Di & EN, and_i_b: & s0/nS0, and_i_c: & s1/nS1, and_i_d: & s2/nS2
    // 2-input AND: end 0=output, end 1=input0, end 2=input1
    for (int i = 0; i < 8; i++) {
      components.add(new ComponentSpec("and" + i + "_a", "Gates", "AND Gate", 1, ""));
      components.add(new ComponentSpec("and" + i + "_b", "Gates", "AND Gate", 1, ""));
      components.add(new ComponentSpec("and" + i + "_c", "Gates", "AND Gate", 1, ""));
      components.add(new ComponentSpec("and" + i + "_d", "Gates", "AND Gate", 1, ""));
    }

    // ---- OR tree: 8 inputs → 1 output (7 two-input OR gates) ----
    components.add(new ComponentSpec("or01", "Gates", "OR Gate", 1, ""));
    components.add(new ComponentSpec("or23", "Gates", "OR Gate", 1, ""));
    components.add(new ComponentSpec("or45", "Gates", "OR Gate", 1, ""));
    components.add(new ComponentSpec("or67", "Gates", "OR Gate", 1, ""));
    components.add(new ComponentSpec("or03", "Gates", "OR Gate", 1, ""));
    components.add(new ComponentSpec("or47", "Gates", "OR Gate", 1, ""));
    components.add(new ComponentSpec("or_y", "Gates", "OR Gate", 1, ""));

    // ---- Output inverter ----
    components.add(new ComponentSpec("not_y", "Gates", "NOT Gate", 1, ""));

    // ---- External ports ----
    // Data inputs D0-D7 on left, target and_i_a input1
    for (int i = 0; i < 8; i++) {
      ports.add(new ExternalPort(
          "d" + i, DATA[i], 1, PortDirection.INPUT, PortSide.LEFT,
          "and" + i + "_a", 1));
    }
    // Select inputs S0-S2 on left
    ports.add(new ExternalPort("s0", "S0", 1, PortDirection.INPUT, PortSide.LEFT, "not_s0", 1));
    ports.add(new ExternalPort("s1", "S1", 1, PortDirection.INPUT, PortSide.LEFT, "not_s1", 1));
    ports.add(new ExternalPort("s2", "S2", 1, PortDirection.INPUT, PortSide.LEFT, "not_s2", 1));
    // Enable nG on left
    ports.add(new ExternalPort("ng", "nG", 1, PortDirection.INPUT, PortSide.LEFT, "not_ng", 1));
    // Outputs Y and nW on right
    ports.add(new ExternalPort("y", "Y", 1, PortDirection.OUTPUT, PortSide.RIGHT, "or_y", 0));
    ports.add(new ExternalPort("nw", "nW", 1, PortDirection.OUTPUT, PortSide.RIGHT, "not_y", 0));

    // ---- Nets ----

    // EN fanout: not_ng output → all and_i_a input2
    List<EndpointRef> enFans = new ArrayList<>();
    enFans.add(new EndpointRef("not_ng", 0));
    for (int i = 0; i < 8; i++) {
      enFans.add(new EndpointRef("and" + i + "_a", 2));
    }
    nets.add(new NetSpec("en_fanout", enFans));

    // S0 fanout: to not_s0 input, and to and_i_b input1 (for odd minterms)
    List<EndpointRef> s0Fans = new ArrayList<>();
    s0Fans.add(new EndpointRef("not_s0", 1));
    for (int i = 0; i < 8; i++) {
      if ((i & 1) != 0) {
        s0Fans.add(new EndpointRef("and" + i + "_b", 1));
      }
    }
    nets.add(new NetSpec("s0_fanout", s0Fans));

    // nS0 fanout: from not_s0 output to and_i_b input1 (for even minterms)
    List<EndpointRef> ns0Fans = new ArrayList<>();
    ns0Fans.add(new EndpointRef("not_s0", 0));
    for (int i = 0; i < 8; i++) {
      if ((i & 1) == 0) {
        ns0Fans.add(new EndpointRef("and" + i + "_b", 1));
      }
    }
    nets.add(new NetSpec("ns0_fanout", ns0Fans));

    // S1 fanout: to not_s1 input, and to and_i_c input1 (for minterms with bit1=1)
    List<EndpointRef> s1Fans = new ArrayList<>();
    s1Fans.add(new EndpointRef("not_s1", 1));
    for (int i = 0; i < 8; i++) {
      if ((i & 2) != 0) {
        s1Fans.add(new EndpointRef("and" + i + "_c", 1));
      }
    }
    nets.add(new NetSpec("s1_fanout", s1Fans));

    // nS1 fanout: from not_s1 output to and_i_c input1
    List<EndpointRef> ns1Fans = new ArrayList<>();
    ns1Fans.add(new EndpointRef("not_s1", 0));
    for (int i = 0; i < 8; i++) {
      if ((i & 2) == 0) {
        ns1Fans.add(new EndpointRef("and" + i + "_c", 1));
      }
    }
    nets.add(new NetSpec("ns1_fanout", ns1Fans));

    // S2 fanout: to not_s2 input, and to and_i_d input1
    List<EndpointRef> s2Fans = new ArrayList<>();
    s2Fans.add(new EndpointRef("not_s2", 1));
    for (int i = 0; i < 8; i++) {
      if ((i & 4) != 0) {
        s2Fans.add(new EndpointRef("and" + i + "_d", 1));
      }
    }
    nets.add(new NetSpec("s2_fanout", s2Fans));

    // nS2 fanout: from not_s2 output to and_i_d input1
    List<EndpointRef> ns2Fans = new ArrayList<>();
    ns2Fans.add(new EndpointRef("not_s2", 0));
    for (int i = 0; i < 8; i++) {
      if ((i & 4) == 0) {
        ns2Fans.add(new EndpointRef("and" + i + "_d", 1));
      }
    }
    nets.add(new NetSpec("ns2_fanout", ns2Fans));

    // AND chain internal nets for each minterm
    for (int i = 0; i < 8; i++) {
      // and_i_a output → and_i_b input2
      nets.add(new NetSpec("a" + i + "_to_b", List.of(
          new EndpointRef("and" + i + "_a", 0),
          new EndpointRef("and" + i + "_b", 2))));
      // and_i_b output → and_i_c input2
      nets.add(new NetSpec("b" + i + "_to_c", List.of(
          new EndpointRef("and" + i + "_b", 0),
          new EndpointRef("and" + i + "_c", 2))));
      // and_i_c output → and_i_d input2
      nets.add(new NetSpec("c" + i + "_to_d", List.of(
          new EndpointRef("and" + i + "_c", 0),
          new EndpointRef("and" + i + "_d", 2))));
    }

    // AND outputs (minterms) to OR tree level 1
    nets.add(new NetSpec("t0_to_or01", List.of(new EndpointRef("and0_d", 0), new EndpointRef("or01", 1))));
    nets.add(new NetSpec("t1_to_or01", List.of(new EndpointRef("and1_d", 0), new EndpointRef("or01", 2))));
    nets.add(new NetSpec("t2_to_or23", List.of(new EndpointRef("and2_d", 0), new EndpointRef("or23", 1))));
    nets.add(new NetSpec("t3_to_or23", List.of(new EndpointRef("and3_d", 0), new EndpointRef("or23", 2))));
    nets.add(new NetSpec("t4_to_or45", List.of(new EndpointRef("and4_d", 0), new EndpointRef("or45", 1))));
    nets.add(new NetSpec("t5_to_or45", List.of(new EndpointRef("and5_d", 0), new EndpointRef("or45", 2))));
    nets.add(new NetSpec("t6_to_or67", List.of(new EndpointRef("and6_d", 0), new EndpointRef("or67", 1))));
    nets.add(new NetSpec("t7_to_or67", List.of(new EndpointRef("and7_d", 0), new EndpointRef("or67", 2))));

    // OR tree level 2
    nets.add(new NetSpec("or01_to_03", List.of(new EndpointRef("or01", 0), new EndpointRef("or03", 1))));
    nets.add(new NetSpec("or23_to_03", List.of(new EndpointRef("or23", 0), new EndpointRef("or03", 2))));
    nets.add(new NetSpec("or45_to_47", List.of(new EndpointRef("or45", 0), new EndpointRef("or47", 1))));
    nets.add(new NetSpec("or67_to_47", List.of(new EndpointRef("or67", 0), new EndpointRef("or47", 2))));

    // OR tree level 3
    nets.add(new NetSpec("or03_to_y", List.of(new EndpointRef("or03", 0), new EndpointRef("or_y", 1))));
    nets.add(new NetSpec("or47_to_y", List.of(new EndpointRef("or47", 0), new EndpointRef("or_y", 2))));

    // Y to nW inverter
    nets.add(new NetSpec("y_to_nw", List.of(new EndpointRef("or_y", 0), new EndpointRef("not_y", 1))));

    // ---- Layout hints ----
    // Rank 0: NOT gates
    hints.add(new LayoutHint("not_s0", 0, 0, 0, null));
    hints.add(new LayoutHint("not_s1", 0, 0, 1, null));
    hints.add(new LayoutHint("not_s2", 0, 0, 2, null));
    hints.add(new LayoutHint("not_ng", 0, 0, 3, null));

    // Ranks 1-4: AND gate chains for each minterm
    for (int i = 0; i < 8; i++) {
      hints.add(new LayoutHint("and" + i + "_a", 1, 1, i, null));
      hints.add(new LayoutHint("and" + i + "_b", 2, 2, i, null));
      hints.add(new LayoutHint("and" + i + "_c", 3, 3, i, null));
      hints.add(new LayoutHint("and" + i + "_d", 4, 4, i, null));
    }

    // Rank 5: OR tree level 1
    hints.add(new LayoutHint("or01", 5, 5, 0, null));
    hints.add(new LayoutHint("or23", 5, 5, 1, null));
    hints.add(new LayoutHint("or45", 5, 5, 2, null));
    hints.add(new LayoutHint("or67", 5, 5, 3, null));

    // Rank 6: OR tree level 2
    hints.add(new LayoutHint("or03", 6, 6, 0, null));
    hints.add(new LayoutHint("or47", 6, 6, 1, null));

    // Rank 7: OR tree level 3 + output inverter
    hints.add(new LayoutHint("or_y", 7, 7, 0, null));
    hints.add(new LayoutHint("not_y", 7, 7, 1, null));
  }

  private static void generateNative74151() throws Exception {
    CircuitSpec spec = new CircuitSpec(
        "ttl_74151_native",
        List.of(new ComponentSpec("mux", "TTL", "74151", 1, "")),
        List.of(
            new ExternalPort("d0", "D0", 1, PortDirection.INPUT, PortSide.LEFT, "mux", 1),
            new ExternalPort("y", "Y", 1, PortDirection.OUTPUT, PortSide.RIGHT, "mux", 0)));

    CircuitSemanticValidator.requireValid(spec);
    LogisimCompiler.compileSingleComponent(spec, new File("generated/circ/ttl_74151_native.circ"));
    System.out.println("[OK] ttl_74151_native.circ generated through IR compiler");
  }
}
