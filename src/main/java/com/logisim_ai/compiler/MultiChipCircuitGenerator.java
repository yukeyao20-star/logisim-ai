package com.logisim_ai.compiler;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.LayoutHint;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.PortSide;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates multi-chip combinational circuits built from native TTL chips plus
 * primitive gates, using the netlist IR path (Tunnel routing). Examples:
 * 2x74138 -> 4-to-16 line decoder, 2x74148 -> 16-to-4 priority encoder.
 *
 * <p>These are the textbook "complex circuits" a user asks for with natural
 * language: "用2片74LS138构成4-16线译码器" etc. Each template builds a full
 * {@link CircuitSpec} that flows through the same compiler, manifest, and
 * official-JAR simulation evidence as every other generated circuit.
 */
public final class MultiChipCircuitGenerator {
  private MultiChipCircuitGenerator() {}

  public enum Template {
    DECODER_4TO16("decoder_4to16", "4-16 decoder"),
    ENCODER_16TO4("encoder_16to4", "16-4 priority encoder");

    private final String circuitName;
    private final String label;

    Template(String circuitName, String label) {
      this.circuitName = circuitName;
      this.label = label;
    }

    public String circuitName() {
      return circuitName;
    }

    public String label() {
      return label;
    }
  }

  /** Build the CircuitSpec for a known multi-chip template. */
  public static CircuitSpec build(Template template) {
    return switch (template) {
      case DECODER_4TO16 -> buildDecoder4to16();
      case ENCODER_16TO4 -> buildEncoder16to4();
    };
  }

  /** Compile a template to a .circ file. */
  public static void compile(Template template, File outputFile) throws Exception {
    CircuitSpec spec = build(template);
    DirectMultiChipCompiler.compile(spec, outputFile);
  }  /**
   * 4-to-16 line decoder from two 74138 (3-to-8) chips + one NOT gate.
   *
   * <p>Wiring:
   * - A0/A1/A2 feed both decoders' A/B/C select inputs.
   * - A3 selects the active chip: NOT(A3) enables dec1 (A3=0), A3 enables dec2.
   * - Both chips' nG2A/nG2B (active-LOW) are tied to 0 (ground) — expressed by
   *   leaving them unconnected is not allowed (floating), so we tie them to a
   *   dedicated low driver via a constant 0 input pin.
   * - Outputs: dec1 nY0..nY7 -> Y0..Y7; dec2 nY0..nY7 -> Y8..Y15 (active LOW
   *   output is standard for 74138).
   */
  private static CircuitSpec buildDecoder4to16() {
    List<ComponentSpec> components = new ArrayList<>();
    components.add(new ComponentSpec("dec1", "TTL", "74138", 1, "U1"));
    components.add(new ComponentSpec("dec2", "TTL", "74138", 1, "U2"));
    components.add(new ComponentSpec("not_sel", "Gates", "NOT Gate", 1, "U3A"));

    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();

    // Data inputs A0..A3
    addInput(ports, "a0", "A0");
    addInput(ports, "a1", "A1");
    addInput(ports, "a2", "A2");
    addInput(ports, "a3", "A3");
    // Active-low enable ground (tie nG2A/nG2B to 0). A dedicated input pin keeps
    // the circuit non-floating and makes the intent explicit in the schematic.
    addInput(ports, "gnd", "nG2");

    // A0..A2 fan out to both decoders
    nets.add(net("nA0", e("a0", 0), e("dec1", 0), e("dec2", 0)));
    nets.add(net("nA1", e("a1", 0), e("dec1", 1), e("dec2", 1)));
    nets.add(net("nA2", e("a2", 0), e("dec1", 2), e("dec2", 2)));

    // A3 selects: dec1 enabled when A3=0 (via NOT), dec2 when A3=1
    nets.add(net("nA3", e("a3", 0), e("not_sel", 1)));
    nets.add(net("nSel0", e("not_sel", 0), e("dec1", 5)));   // dec1 G1 = !A3
    nets.add(net("nSel1", e("a3", 0), e("dec2", 5)));        // dec2 G1 = A3

    // Ground nG2A (end 3) and nG2B (end 4) on both chips
    nets.add(net("nG21a", e("gnd", 0), e("dec1", 3), e("dec1", 4)));
    nets.add(net("nG22a", e("gnd", 0), e("dec2", 3), e("dec2", 4)));

    // Outputs: dec1 nY7..nY0 = end6..end13 => Y0..Y7 ; dec2 => Y8..Y15
    for (int i = 0; i < 8; i++) {
      int endIndex = 13 - i;
      String dec = (i < 8) ? "dec1" : "dec2";
      String outId = "y" + i;
      String label = "Y" + i;
      addOutput(ports, outId, label);
      nets.add(net("nY" + i, e(dec, endIndex), e(outId, 0)));
    }
    for (int i = 8; i < 16; i++) {
      int endIndex = 13 - (i - 8);
      String outId = "y" + i;
      String label = "Y" + i;
      addOutput(ports, outId, label);
      nets.add(net("nY" + i, e("dec2", endIndex), e(outId, 0)));
    }

    List<LayoutHint> hints = List.of(
        new LayoutHint("dec1", 0, 0, 0, null),
        new LayoutHint("dec2", 0, 1, 0, null),
        new LayoutHint("not_sel", 1, 0, 1, null));
    return new CircuitSpec("decoder_4to16", components, ports, nets, hints);
  }

  /**
   * 16-to-4 priority encoder from two 74148 (8-to-3) chips plus gates.
   *
   * <p>Runtime port map of Ttl74148 (logisim-evolution, VCC_GND off):
   * end0 = nEI, end1..8 = I0..I7 (active-low), end9 = nGS, end10 = nEO,
   * end11 = A0, end12 = A1, end13 = A2 (active-low outputs).
   *
   * <p>Cascade: high group (I8..I15) feeds enc1 (I15 wired to enc1.I7, the
   * highest priority), low group (I0..I7) feeds enc0. enc1.nEI is tied to EN
   * (user connects 0 to enable), enc0.nEI is daisy-chained from enc1.nEO so the
   * low group activates only when the high group has no valid input.
   *
   * <p>Outputs Y3Y2Y1Y0 are a positive-logic 0..15 value (Y3 is the MSB) and GS
   * is a positive-logic "any input" flag. Gates implement:
   * <pre>
   *   gsh  = not enc1.nGS              (high group active)
   *   gsl  = not enc0.nGS              (low group active)
   *   Y3   = gsh
   *   Y2..0 = (gsh and not enc1.A#) or (enc1.nGS and not enc0.A#)
   *   GS   = gsh or gsl
   * </pre>
   */
  private static CircuitSpec buildEncoder16to4() {
    List<ComponentSpec> components = new ArrayList<>();
    components.add(new ComponentSpec("enc0", "TTL", "74148", 1, "U1"));
    components.add(new ComponentSpec("enc1", "TTL", "74148", 1, "U2"));
    components.add(new ComponentSpec("n_gsh", "Gates", "NOT Gate", 1, "U3A"));
    components.add(new ComponentSpec("gsl", "Gates", "NOT Gate", 1, "U3B"));
    components.add(new ComponentSpec("nh2", "Gates", "NOT Gate", 1, "U4A"));
    components.add(new ComponentSpec("nh1", "Gates", "NOT Gate", 1, "U4B"));
    components.add(new ComponentSpec("nh0", "Gates", "NOT Gate", 1, "U4C"));
    components.add(new ComponentSpec("nl2", "Gates", "NOT Gate", 1, "U5A"));
    components.add(new ComponentSpec("nl1", "Gates", "NOT Gate", 1, "U5B"));
    components.add(new ComponentSpec("nl0", "Gates", "NOT Gate", 1, "U5C"));
    components.add(new ComponentSpec("t1_2", "Gates", "AND Gate", 1, "U6A"));
    components.add(new ComponentSpec("t1_1", "Gates", "AND Gate", 1, "U6B"));
    components.add(new ComponentSpec("t1_0", "Gates", "AND Gate", 1, "U6C"));
    components.add(new ComponentSpec("t2_2", "Gates", "AND Gate", 1, "U6D"));
    components.add(new ComponentSpec("t2_1", "Gates", "AND Gate", 1, "U7A"));
    components.add(new ComponentSpec("t2_0", "Gates", "AND Gate", 1, "U7B"));
    components.add(new ComponentSpec("z2", "Gates", "OR Gate", 1, "U8A"));
    components.add(new ComponentSpec("z1", "Gates", "OR Gate", 1, "U8B"));
    components.add(new ComponentSpec("z0", "Gates", "OR Gate", 1, "U8C"));
    components.add(new ComponentSpec("gso", "Gates", "OR Gate", 1, "U8D"));

    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();

    // 16 active-low inputs I0..I15 + global low-active enable EN
    for (int i = 0; i < 16; i++) {
      addInput(ports, "i" + i, "I" + i, "enc0");
    }
    addInput(ports, "en", "EN", "enc0");
    // outputs: Y0..Y3 (positive logic, Y3 = MSB), GS = any-valid
    addOutput(ports, "y0", "Y0", "enc0");
    addOutput(ports, "y1", "Y1", "enc0");
    addOutput(ports, "y2", "Y2", "enc0");
    addOutput(ports, "y3", "Y3", "enc0");
    addOutput(ports, "gs", "GS", "enc0");

    // Inputs: low group I0..I7 -> enc0 ends 0..7; high group I8..I15 -> enc1 ends 0..7
    for (int i = 0; i < 8; i++) {
      nets.add(net("ni_l" + i, e("i" + i, 0), e("enc0", i)));
      nets.add(net("ni_h" + i, e("i" + (8 + i), 0), e("enc1", i)));
    }
    // Enable chain: enc1.nEI(end8) from EN; enc0.nEI(end8) from enc1.nEO(end10)
    // (only when high group empty)
    nets.add(net("nen1", e("en", 0), e("enc1", 8)));
    nets.add(net("nen0", e("enc1", 10), e("enc0", 8)));

    // Group-active flags
    nets.add(net("nng1", e("enc1", 9), e("n_gsh", 1)));       // enc1.nGS -> NOT
    nets.add(net("nng0", e("enc0", 9), e("gsl", 1)));          // enc0.nGS -> NOT
    nets.add(net("ngsh", e("n_gsh", 0), e("y3", 0)));           // Y3 = gsh

    // gsh fans out to the three high-group AND inputs (port 2)
    nets.add(net("sg", e("n_gsh", 0), e("t1_2", 2), e("t1_1", 2), e("t1_0", 2)));
    // enc1.nGS (= not gsh) fans out to the three low-group AND inputs (port 2)
    nets.add(net("sng1", e("enc1", 9), e("t2_2", 2), e("t2_1", 2), e("t2_0", 2)));

    // Per-bit negative-to-positive + group mux:
    // enc1.A0..A2 are ends 11,12,13 ; enc0 same
    for (int bit = 0; bit < 3; bit++) {
      int endA = 11 + bit; // bit0 -> end11(A0), bit1 -> end12(A1), bit2 -> end13(A2)
      String nh = "nh" + bit;
      String nl = "nl" + bit;
      String t1 = "t1_" + bit;
      String t2 = "t2_" + bit;
      String z = "z" + bit;
      nets.add(net("nnh" + bit, e("enc1", endA), e(nh, 1)));
      nets.add(net("nnl" + bit, e("enc0", endA), e(nl, 1)));
      nets.add(net("snh" + bit, e(nh, 0), e(t1, 1)));   // not enc1.A# -> high AND input1
      nets.add(net("snl" + bit, e(nl, 0), e(t2, 1)));   // not enc0.A#  -> low AND input1
      // Separate nets per OR input: two gate outputs must not share a wire.
      nets.add(net("nz1_" + bit, e(t1, 0), e(z, 1)));
      nets.add(net("nz2_" + bit, e(t2, 0), e(z, 2)));
      nets.add(net("ny" + bit, e(z, 0), e("y" + bit, 0)));
    }
    // GS = gsh OR gsl (separate nets: two gate outputs must not share a wire)
    nets.add(net("ngs1", e("n_gsh", 0), e("gso", 1)));
    nets.add(net("ngs2", e("gsl", 0), e("gso", 2)));
    nets.add(net("ngso", e("gso", 0), e("gs", 0)));

    List<LayoutHint> hints = List.of(
        new LayoutHint("enc0", 0, 0, 0, null),
        new LayoutHint("enc1", 0, 1, 0, null),
        new LayoutHint("n_gsh", 1, 0, 0, null),
        new LayoutHint("gsl", 1, 0, 1, null),
        new LayoutHint("nh2", 1, 1, 0, null),
        new LayoutHint("nl2", 1, 1, 1, null),
        new LayoutHint("t1_2", 2, 0, 0, null),
        new LayoutHint("z2", 2, 1, 0, null));
    return new CircuitSpec("encoder_16to4", components, ports, nets, hints);
  }

  private static void addInput(List<ExternalPort> ports, String id, String label) {
    addInput(ports, id, label, "dec1");
  }

  private static void addInput(
      List<ExternalPort> ports, String id, String label, String target) {
    ports.add(new ExternalPort(id, label, 1, PortDirection.INPUT, PortSide.LEFT, target, 0));
  }

  private static void addOutput(List<ExternalPort> ports, String id, String label) {
    addOutput(ports, id, label, "dec1");
  }

  private static void addOutput(
      List<ExternalPort> ports, String id, String label, String target) {
    ports.add(new ExternalPort(id, label, 1, PortDirection.OUTPUT, PortSide.RIGHT, target, 0));
  }

  private static EndpointRef e(String componentId, int endIndex) {
    return new EndpointRef(componentId, endIndex);
  }

  private static NetSpec net(String id, EndpointRef... endpoints) {
    return new NetSpec(id, List.of(endpoints));
  }
}
