package com.logisim_ai.ai;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.LayoutHint;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.PortSide;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parameterized structural circuit templates (N-bit adder, N-bit comparator,
 * ...). Each template builds a plain {@link CircuitSpec} that flows through
 * the exact same compiler and official-JAR verification pipeline as every
 * other generated circuit, so "difficult" circuits are just specs.
 *
 * <p>Layout: every bit slice occupies one column (rank = slice index), gates
 * are stacked by role inside the column, and the carry chain runs across
 * columns. {@code GridLayoutEngine} aligns each column evenly.
 */
public final class StructuralCircuitTemplates {
  private StructuralCircuitTemplates() {}

  private static final Pattern ADDER_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*位\\s*(?:加法器|全加器|二进制加法器)");
  private static final Pattern ADDER_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:adder|ripple[- ]carry\\s+adder)", Pattern.CASE_INSENSITIVE);
  private static final Pattern COMPARATOR_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*位\\s*(?:比较器|大小比较器|数值比较器)");
  private static final Pattern COMPARATOR_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:comparator|magnitude\\s+comparator)", Pattern.CASE_INSENSITIVE);
  private static final Pattern MUX_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*选1\\s*(?:多路选择器|多路复用器|选择器|多路开关)?");
  private static final Pattern MUX_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?to\\s*-?1\\s*(?:mux|multiplexer)", Pattern.CASE_INSENSITIVE);
  private static final Pattern DECODER_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*位\\s*(?:译码器|解码器|二进制译码器)");
  private static final Pattern DECODER_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?to\\s*-?(\\d{1,3})\\s*(?:decoder|demultiplexer)", Pattern.CASE_INSENSITIVE);
  private static final Pattern COUNTER_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*位\\s*(?:二进制计数器|计数器)");
  private static final Pattern COUNTER_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:binary\\s+)?counter", Pattern.CASE_INSENSITIVE);
  private static final Pattern PARITY_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*位\\s*(?:奇偶校验器|奇偶校验|偶校验器|奇校验器)");
  private static final Pattern PARITY_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:parity|even\\s+parity)", Pattern.CASE_INSENSITIVE);
  private static final Pattern ENCODER8_ZH =
      Pattern.compile("(?:8[\\s\\-－]*3|83|八三|八选三|八输入三输出|8输入3输出)\\s*(?:优先编码器|编码器)");
  private static final Pattern ENCODER8_EN =
      Pattern.compile("8[- ]to[- ]3\\s*(?:priority\\s+)?encoder", Pattern.CASE_INSENSITIVE);
  private static final Pattern BCD7_ZH =
      Pattern.compile("(?:bcd|二十进制|8421码|8421)\\s*(?:转|到|[-])?\\s*(?:7段|七段|七段数码管|七段显示|数码管)\\s*(?:译码器|解码器|驱动器)?");
  private static final Pattern BCD7_EN =
      Pattern.compile("(?:bcd|binary\\s+coded\\s+decimal)\\s*(?:to)?\\s*7[- ]seg(?:ment)?\\s*(?:decoder|display|driver)", Pattern.CASE_INSENSITIVE);
  private static final Pattern MUL_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*[xX×位]\\s*([0-9一二三四五六七八九十两]{1,3})?\\s*位?\\s*(?:乘法器|二进制乘法器)");
  private static final Pattern MUL_ZH2 =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*位\\s*(?:乘法器|二进制乘法器)");
  private static final Pattern MUL_EN =
      Pattern.compile("(\\d{1,2})\\s*[xX×]\\s*(\\d{1,2})\\s*(?:multiplier)", Pattern.CASE_INSENSITIVE);
  private static final Pattern SUB_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*位\\s*(?:减法器|全减器|二进制减法器)");
  private static final Pattern SUB_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:subtractor|full\\s+subtractor)", Pattern.CASE_INSENSITIVE);

  public static Optional<CircuitSpec> tryBuild(String query) {
    if (query == null || query.isBlank()) {
      return Optional.empty();
    }
    String t = query.toLowerCase(Locale.ROOT);
    if (BCD7_ZH.matcher(t).find() || BCD7_EN.matcher(t).find()) {
      return Optional.of(buildBcd7Seg());
    }
    Matcher mul = MUL_ZH.matcher(t);
    Matcher mul2 = MUL_ZH2.matcher(t);
    Matcher mulEn = MUL_EN.matcher(t);
    if (mul.find()) {
      Integer a = chineseToInt(mul.group(1));
      Integer b = mul.group(2) != null ? chineseToInt(mul.group(2)) : a;
      if (a != null && b != null && a.equals(b)) {
        return Optional.of(buildMultiplier(a));
      }
    }
    if (mul2.find()) {
      Integer n = chineseToInt(mul2.group(1));
      if (n != null) {
        return Optional.of(buildMultiplier(n));
      }
    }
    if (mulEn.find()) {
      int a = Integer.parseInt(mulEn.group(1));
      int b = Integer.parseInt(mulEn.group(2));
      if (a == b) {
        return Optional.of(buildMultiplier(a));
      }
    }
    Matcher subzh = SUB_ZH.matcher(t);
    Matcher suben = SUB_EN.matcher(t);
    if (subzh.find()) {
      Integer n = chineseToInt(subzh.group(1));
      if (n != null) {
        return Optional.of(buildSubtractor(n));
      }
    }
    if (suben.find()) {
      return Optional.of(buildSubtractor(Integer.parseInt(suben.group(1))));
    }
    Matcher zh = ADDER_ZH.matcher(t);
    Matcher en = ADDER_EN.matcher(t);
    if (zh.find()) {
      Integer bits = chineseToInt(zh.group(1));
      if (bits != null) {
        return Optional.of(buildAdder(bits));
      }
    }
    if (en.find()) {
      return Optional.of(buildAdder(Integer.parseInt(en.group(1))));
    }
    Matcher czh = COMPARATOR_ZH.matcher(t);
    Matcher cen = COMPARATOR_EN.matcher(t);
    if (czh.find()) {
      Integer bits = chineseToInt(czh.group(1));
      if (bits != null) {
        return Optional.of(buildComparator(bits));
      }
    }
    if (cen.find()) {
      return Optional.of(buildComparator(Integer.parseInt(cen.group(1))));
    }
    Matcher mzh = MUX_ZH.matcher(t);
    Matcher men = MUX_EN.matcher(t);
    if (mzh.find()) {
      Integer n = chineseToInt(mzh.group(1));
      if (n != null) {
        return Optional.of(buildMux(n));
      }
    }
    if (men.find()) {
      return Optional.of(buildMux(Integer.parseInt(men.group(1))));
    }
    Matcher dzh = DECODER_ZH.matcher(t);
    Matcher den = DECODER_EN.matcher(t);
    if (dzh.find()) {
      Integer bits = chineseToInt(dzh.group(1));
      if (bits != null) {
        return Optional.of(buildDecoder(bits));
      }
    }
    if (den.find()) {
      return Optional.of(buildDecoder(Integer.parseInt(den.group(1))));
    }
    Matcher ctrzh = COUNTER_ZH.matcher(t);
    Matcher ctrEn = COUNTER_EN.matcher(t);
    if (ctrzh.find()) {
      Integer bits = chineseToInt(ctrzh.group(1));
      if (bits != null) {
        return Optional.of(buildCounter(bits));
      }
    }
    if (ctrEn.find()) {
      return Optional.of(buildCounter(Integer.parseInt(ctrEn.group(1))));
    }
    Matcher pzh = PARITY_ZH.matcher(t);
    Matcher pen = PARITY_EN.matcher(t);
    if (pzh.find()) {
      Integer bits = chineseToInt(pzh.group(1));
      if (bits != null) {
        return Optional.of(buildParity(bits));
      }
    }
    if (pen.find()) {
      return Optional.of(buildParity(Integer.parseInt(pen.group(1))));
    }
    if (ENCODER8_ZH.matcher(t).find() || ENCODER8_EN.matcher(t).find()) {
      return Optional.of(buildEncoder8to3());
    }
    return Optional.empty();
  }

  /**
   * N-bit even-parity generator/checker: Y = XOR of all inputs (2-input XOR
   * cascade), plus the parity of the input bits. Output P = even parity.
   */
  static CircuitSpec buildParity(int bits) {
    if (bits < 2 || bits > 16) {
      throw new IllegalArgumentException("Parity width must be 2..16, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < bits; i++) {
      addInput(ports, "d" + i, "D" + i, "par" + Math.max(1, i) + "_x");
    }
    addOutput(ports, "p", "P", "par" + (bits - 1) + "_x");

    String acc = "d0";
    for (int i = 1; i < bits; i++) {
      String g = "par" + i + "_x";
      components.add(new ComponentSpec(g, "Gates", "XOR Gate", 1, "PAR" + i));
      hints.add(new LayoutHint(g, 0, 0, i, null));
      nets.add(net("px" + i, e(acc, 0), e(g, 1)));
      nets.add(net("py" + i, e("d" + i, 0), e(g, 2)));
      acc = g;
    }
    nets.add(net("pout", e(acc, 0), e("p", 0)));

    return new CircuitSpec("parity_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * 8-to-3 priority encoder: two 74148? No - single 74148 already is 8-to-3;
   * this template exposes it with proper input/output pins (ends 0..7 = I0..I7,
   * end8 = nEI, end9 = nGS, end10 = nEO, end11..13 = A0..A2).
   */
  static CircuitSpec buildEncoder8to3() {
    List<ComponentSpec> components = List.of(new ComponentSpec("enc", "TTL", "74148", 1, "U1"));
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = List.of(new LayoutHint("enc", 0, 0, 0, null));

    for (int i = 0; i < 8; i++) {
      addInput(ports, "i" + i, "I" + i, "enc");
      nets.add(net("i" + i, e("i" + i, 0), e("enc", i)));
    }
    addInput(ports, "nei", "nEI", "enc");
    nets.add(net("nei", e("nei", 0), e("enc", 8)));
    addOutput(ports, "a0", "A0", "enc");
    addOutput(ports, "a1", "A1", "enc");
    addOutput(ports, "a2", "A2", "enc");
    addOutput(ports, "ngs", "nGS", "enc");
    addOutput(ports, "neo", "nEO", "enc");
    nets.add(net("a0", e("enc", 11), e("a0", 0)));
    nets.add(net("a1", e("enc", 12), e("a1", 0)));
    nets.add(net("a2", e("enc", 13), e("a2", 0)));
    nets.add(net("ngs", e("enc", 9), e("ngs", 0)));
    nets.add(net("neo", e("enc", 10), e("neo", 0)));

    return new CircuitSpec("encoder_8to3", components, ports, nets, hints);
  }

  /**
   * N-bit binary up-counter built from 74161 (4-bit) chips cascaded via TC to
   * ENT. Port map (verified catalog): end0=nCLR, end1=CLK, end2..5=D0..D3,
   * end6=ENP, end7=nLOAD, end8=ENT, end9=Q3, end10=Q2, end11=Q1, end12=Q0,
   * end13=TC.
   */
  static CircuitSpec buildCounter(int bits) {
    if (bits < 1 || bits > 16) {
      throw new IllegalArgumentException("Counter width must be 1..16, got " + bits);
    }
    int chips = (bits + 3) / 4;
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int c = 0; c < chips; c++) {
      components.add(new ComponentSpec("cnt" + c, "TTL", "74161", 1, "U" + (c + 1)));
      hints.add(new LayoutHint("cnt" + c, c, 0, 0, null));
    }
    addInput(ports, "clk", "CLK", "cnt0");
    addInput(ports, "enp", "ENP", "cnt0");
    addInput(ports, "ent", "ENT", "cnt0");
    addInput(ports, "nload", "nLOAD", "cnt0");
    addInput(ports, "nclr", "nCLR", "cnt0");
    for (int i = 0; i < bits; i++) {
      addInput(ports, "d" + i, "D" + i, "cnt" + (i / 4));
      addOutput(ports, "q" + i, "Q" + i, "cnt" + (i / 4));
    }
    addOutput(ports, "rc", "RCO", "cnt" + (chips - 1));

    for (int c = 0; c < chips; c++) {
      int base = c * 4;
      String g = "cnt" + c;
      nets.add(net("clk" + c, e("clk", 0), e(g, 1)));
      nets.add(net("enp" + c, e("enp", 0), e(g, 6)));
      nets.add(net("load" + c, e("nload", 0), e(g, 7)));
      nets.add(net("clr" + c, e("nclr", 0), e(g, 0)));
      if (c == 0) {
        nets.add(net("ent" + c, e("ent", 0), e(g, 8)));
      } else {
        // cascade: lower chip TC drives this chip's ENT
        nets.add(net("ent" + c, e("cnt" + (c - 1), 13), e(g, 8)));
      }
      for (int i = 0; i < 4; i++) {
        int bit = base + i;
        if (bit < bits) {
          nets.add(net("d" + bit, e("d" + bit, 0), e(g, 2 + i)));
          // Q3..Q0 are ends 9..12 (Q3 = end9 = MSB)
          nets.add(net("q" + bit, e(g, 12 - i), e("q" + bit, 0)));
        }
      }
    }
    nets.add(net("rc", e("cnt" + (chips - 1), 13), e("rc", 0)));

    return new CircuitSpec("counter_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-to-1 multiplexer: select bus S0..S(M-1) (M = log2 N) decodes to one-hot
   * lines; Y = OR over i of (D_i and sel_i).
   */
  static CircuitSpec buildMux(int n) {
    if (n < 2 || n > 256 || (n & (n - 1)) != 0) {
      throw new IllegalArgumentException("MUX width must be a power of two in 2..256, got " + n);
    }
    int m = Integer.numberOfTrailingZeros(n);
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      addInput(ports, "d" + i, "D" + i, "mux" + i + "_da");
    }
    for (int j = 0; j < m; j++) {
      addInput(ports, "s" + j, "S" + j, "mux0_da");
    }
    addOutput(ports, "y", "Y", "mux_or" + (n - 2));

    for (int i = 0; i < n; i++) {
      String g = "mux" + i + "_";
      String chip = "MUX" + i;
      // data gate: D_i and sel_i
      components.add(new ComponentSpec(g + "da", "Gates", "AND Gate", 1, chip + "D"));
      hints.add(new LayoutHint(g + "da", 1, 0, i, null));
      // select decode as a 2-input AND cascade: sel_i = AND over literals
      String acc = null;
      for (int j = 0; j < m; j++) {
        boolean one = ((i >> j) & 1) == 1;
        String src = one ? "s" + j : "sel_not" + j;
        if (acc == null) {
          acc = src;
        } else {
          String gateId = g + "se" + j;
          components.add(new ComponentSpec(gateId, "Gates", "AND Gate", 1, chip + "S" + j));
          hints.add(new LayoutHint(gateId, 0, 0, i, null));
          nets.add(net("selx" + i + "_" + j, e(acc, 0), e(gateId, 1)));
          nets.add(net("sely" + i + "_" + j, e(src, 0), e(gateId, 2)));
          acc = gateId;
        }
      }
      if (acc != null) {
        nets.add(net("selo" + i, e(acc, 0), e(g + "da", 2)));
      }
    }
    for (int k = 0; k < n - 1; k++) {
      components.add(new ComponentSpec("mux_or" + k, "Gates", "OR Gate", 1, "MUXO" + (k + 1)));
      hints.add(new LayoutHint("mux_or" + k, 2 + k, 0, 0, null));
    }
    // not-S inverters shared per select bit (one per select line, fan-out to decoders)
    for (int j = 0; j < m; j++) {
      components.add(new ComponentSpec("sel_not" + j, "Gates", "NOT Gate", 1, "MUXN" + (j + 1)));
      hints.add(new LayoutHint("sel_not" + j, 0, 0, n + j, null));
      nets.add(net("sn" + j, e("s" + j, 0), e("sel_not" + j, 1)));
    }

    for (int i = 0; i < n; i++) {
      String g = "mux" + i + "_";
      nets.add(net("d" + i, e("d" + i, 0), e(g + "da", 1)));
      // OR chain: or0 = da0 | da1 ; or_k = or_{k-1} | da_{k+1}
      nets.add(net("do" + i, e(g + "da", 0),
          e(i == 0 ? "mux_or0" : "mux_or" + (i - 1), i == 0 ? 1 : 2)));
    }
    for (int k = 0; k < n - 1; k++) {
      nets.add(net("or" + k, e("mux_or" + k, 0),
          e(k + 1 == n - 1 ? "y" : "mux_or" + (k + 1), k + 1 == n - 1 ? 0 : 1)));
    }

    return new CircuitSpec("mux_" + n + "to1", components, ports, nets, hints);
  }

  /**
   * N-bit binary decoder: 2^N one-hot outputs, each Y_i = AND of literals.
   */
  static CircuitSpec buildDecoder(int bits) {
    if (bits < 1 || bits > 8) {
      throw new IllegalArgumentException("Decoder bits must be 1..8, got " + bits);
    }
    int n = 1 << bits;
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int j = 0; j < bits; j++) {
      addInput(ports, "a" + j, "A" + j, bits == 1 ? "dec_not0" : "dec0_a1");
      components.add(new ComponentSpec("dec_not" + j, "Gates", "NOT Gate", 1, "DECN" + (j + 1)));
      hints.add(new LayoutHint("dec_not" + j, 0, 0, j, null));
      nets.add(net("an" + j, e("a" + j, 0), e("dec_not" + j, 1)));
    }
    if (bits == 1) {
      addOutput(ports, "y0", "Y0", "dec_not0");
      addOutput(ports, "y1", "Y1", "dec_not0");
      nets.add(net("y0a", e("a0", 0), e("y0", 0)));
      nets.add(net("y1a", e("dec_not0", 0), e("y1", 0)));
      return new CircuitSpec("decoder_1bit", components, ports, nets, hints);
    }
    for (int i = 0; i < n; i++) {
      addOutput(ports, "y" + i, "Y" + i, "dec" + i + "_a" + (bits - 1));
      // cascade of 2-input AND gates: dec_i = AND(AND(...AND(lit0, lit1), lit2), ...)
      String acc = null;
      for (int j = 0; j < bits; j++) {
        boolean one = ((i >> j) & 1) == 1;
        String src = one ? "a" + j : "dec_not" + j;
        if (acc == null) {
          acc = src;
        } else {
          String gateId = "dec" + i + "_a" + j;
          components.add(new ComponentSpec(gateId, "Gates", "AND Gate", 1, "DEC" + i + "_" + j));
          hints.add(new LayoutHint(gateId, 1, 0, i, null));
          nets.add(net("decd" + i + "_" + j + "x", e(acc, 0), e(gateId, 1)));
          nets.add(net("decd" + i + "_" + j + "y", e(src, 0), e(gateId, 2)));
          acc = gateId;
        }
      }
      nets.add(net("deco" + i, e(acc, 0), e("y" + i, 0)));
    }

    return new CircuitSpec("decoder_" + bits + "bit", components, ports, nets, hints);
  }

  private static Integer chineseToInt(String s) {
    if (s.matches("\\d+")) {
      return Integer.parseInt(s);
    }
    return switch (s) {
      case "一" -> 1;
      case "二", "两" -> 2;
      case "三" -> 3;
      case "四" -> 4;
      case "五" -> 5;
      case "六" -> 6;
      case "七" -> 7;
      case "八" -> 8;
      case "九" -> 9;
      case "十" -> 10;
      case "十一" -> 11;
      case "十二" -> 12;
      case "十三" -> 13;
      case "十四" -> 14;
      case "十五" -> 15;
      case "十六" -> 16;
      default -> null;
    };
  }

  /**
   * N-bit ripple-carry adder. Bit slice i uses 7 gates:
   * s = a xor b xor c ; c' = (a and b) or (a and c) or (b and c).
   * Inputs A0..A(N-1), B0..B(N-1), Cin; outputs S0..S(N-1), Cout.
   */
  static CircuitSpec buildAdder(int bits) {
    if (bits < 1 || bits > 16) {
      throw new IllegalArgumentException("Adder width must be 1..16, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < bits; i++) {
      String px = "a" + i;
      String py = "b" + i;
      String ps = "s" + i;
      addInput(ports, px, "A" + i, "fa" + i + "_xor1");
      addInput(ports, py, "B" + i, "fa" + i + "_xor1");
      addOutput(ports, ps, "S" + i, "fa" + i + "_xor2");
      nets.add(net("na" + i, e(px, 0), e("fa" + i + "_xor1", 1), e("fa" + i + "_and2", 1), e("fa" + i + "_and1", 2)));
      nets.add(net("nb" + i, e(py, 0), e("fa" + i + "_xor1", 2), e("fa" + i + "_and1", 1), e("fa" + i + "_and3", 2)));
    }
    addInput(ports, "cin", "Cin", "fa0_xor2");
    addOutput(ports, "cout", "Cout", "fa" + (bits - 1) + "_or2");

    for (int i = 0; i < bits; i++) {
      String g = "fa" + i + "_";
      String chip = "FA" + i;
      components.add(new ComponentSpec(g + "xor1", "Gates", "XOR Gate", 1, chip + "X1"));
      components.add(new ComponentSpec(g + "xor2", "Gates", "XOR Gate", 1, chip + "X2"));
      components.add(new ComponentSpec(g + "and1", "Gates", "AND Gate", 1, chip + "G1"));
      components.add(new ComponentSpec(g + "and2", "Gates", "AND Gate", 1, chip + "G2"));
      components.add(new ComponentSpec(g + "and3", "Gates", "AND Gate", 1, chip + "G3"));
      components.add(new ComponentSpec(g + "or1", "Gates", "OR Gate", 1, chip + "O1"));
      components.add(new ComponentSpec(g + "or2", "Gates", "OR Gate", 1, chip + "O2"));
      // one column per slice; gates stack by role (rows 0..6)
      for (int r = 0; r < 7; r++) {
        String[] roles = {"xor1", "xor2", "and1", "and2", "and3", "or1", "or2"};
        hints.add(new LayoutHint(g + roles[r], i, 0, r, null));
      }
    }

    String carryIn = "cin";
    for (int i = 0; i < bits; i++) {
      String g = "fa" + i + "_";
      // carry in -> xor2 input2 and and2 input2 (cin) / and3 input1 (carry)
      nets.add(net("ci" + i, e(carryIn, 0), e(g + "xor2", 2), e(g + "and2", 2), e(g + "and3", 1)));
      // a xor b -> xor2 input1
      nets.add(net("x" + i, e(g + "xor1", 0), e(g + "xor2", 1)));
      // sum = a xor b xor cin
      nets.add(net("s" + i, e(g + "xor2", 0), e("s" + i, 0)));
      // carry generate/propagate
      nets.add(net("ab" + i, e(g + "and1", 0), e(g + "or1", 1)));
      nets.add(net("ac" + i, e(g + "and2", 0), e(g + "or1", 2)));
      nets.add(net("bc" + i, e(g + "and3", 0), e(g + "or2", 2)));
      nets.add(net("c1" + i, e(g + "or1", 0), e(g + "or2", 1)));
      carryIn = g + "or2";
    }
    nets.add(net("cout", e(carryIn, 0), e("cout", 0)));

    return new CircuitSpec("adder_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-bit magnitude comparator with outputs GT (A>B), EQ (A==B), LT (A<B).
   * Bit slice i: e_i = XNOR(A_i,B_i), g_i = A_i and not B_i.
   * GT ripple: gt = g(N-1) | e(N-1)&g(N-2) | ... ; EQ = AND of all e_i;
   * LT = not (GT or EQ).
   */
  static CircuitSpec buildComparator(int bits) {
    if (bits < 1 || bits > 16) {
      throw new IllegalArgumentException("Comparator width must be 1..16, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < bits; i++) {
      addInput(ports, "a" + i, "A" + i, "cmp" + i + "_xn");
      addInput(ports, "b" + i, "B" + i, "cmp" + i + "_xn");
    }
    addOutput(ports, "gt", "GT", "cmp" + (bits - 1) + "_ga");
    addOutput(ports, "eq", "EQ", bits == 1 ? "cmp0_xn" : "eqc_" + (bits - 1));
    addOutput(ports, "lt", "LT", "lt_not");

    for (int i = 0; i < bits; i++) {
      String g = "cmp" + i + "_";
      String chip = "CMP" + i;
      components.add(new ComponentSpec(g + "xn", "Gates", "XNOR Gate", 1, chip + "E"));
      components.add(new ComponentSpec(g + "nb", "Gates", "NOT Gate", 1, chip + "N"));
      components.add(new ComponentSpec(g + "ga", "Gates", "AND Gate", 1, chip + "G"));
      hints.add(new LayoutHint(g + "xn", i, 0, 0, null));
      hints.add(new LayoutHint(g + "nb", i, 0, 1, null));
      hints.add(new LayoutHint(g + "ga", i, 0, 2, null));
    }
    components.add(new ComponentSpec("lt_or", "Gates", "OR Gate", 1, "CMPO"));
    components.add(new ComponentSpec("lt_not", "Gates", "NOT Gate", 1, "CMPL"));
    hints.add(new LayoutHint("lt_or", bits, 0, 0, null));
    hints.add(new LayoutHint("lt_not", bits, 0, 1, null));

    for (int i = 0; i < bits; i++) {
      String g = "cmp" + i + "_";
      nets.add(net("nb" + i, e("b" + i, 0), e(g + "nb", 1)));
      nets.add(net("g" + i, e(g + "nb", 0), e(g + "ga", 2)));
      nets.add(net("ag" + i, e("a" + i, 0), e(g + "ga", 1)));
      nets.add(net("a" + i, e("a" + i, 0), e(g + "xn", 1)));
      nets.add(net("b" + i, e("b" + i, 0), e(g + "xn", 2)));
    }

    // GT ripple from MSB down:
    //   gt_acc = g(N-1); e_acc = e(N-1)
    //   gt_acc = gt_acc | (e_acc & g(i)); e_acc = e_acc & e(i)
    // so the highest differing bit decides, high bits are never dropped.
    for (int i = 0; i < bits - 1; i++) {
      components.add(new ComponentSpec("gta_" + i, "Gates", "AND Gate", 1, "CMPA" + (i + 1)));
      components.add(new ComponentSpec("gtc_" + i, "Gates", "OR Gate", 1, "CMPG" + (i + 1)));
      components.add(new ComponentSpec("gte_" + i, "Gates", "AND Gate", 1, "CMPE" + (i + 1)));
      hints.add(new LayoutHint("gta_" + i, bits + 1 + i, 0, 0, null));
      hints.add(new LayoutHint("gtc_" + i, bits + 1 + i, 0, 1, null));
      hints.add(new LayoutHint("gte_" + i, bits + 1 + i, 0, 2, null));
    }
    String gtAcc = "cmp" + (bits - 1) + "_ga";
    String eAcc = "cmp" + (bits - 1) + "_xn";
    for (int i = bits - 2; i >= 0; i--) {
      nets.add(net("gta" + i, e(eAcc, 0), e("gta_" + i, 1)));
      nets.add(net("gtb" + i, e("cmp" + i + "_ga", 0), e("gta_" + i, 2)));
      nets.add(net("gtc" + i, e(gtAcc, 0), e("gtc_" + i, 1)));
      nets.add(net("gtd" + i, e("gta_" + i, 0), e("gtc_" + i, 2)));
      nets.add(net("gte" + i, e(eAcc, 0), e("gte_" + i, 1)));
      nets.add(net("gtf" + i, e("cmp" + i + "_xn", 0), e("gte_" + i, 2)));
      gtAcc = "gtc_" + i;
      eAcc = "gte_" + i;
    }
    nets.add(net("gtout", e(gtAcc, 0), e("gt", 0)));

    // EQ = AND of all e_i (two-input chain, one AND per step)
    for (int i = 0; i < bits; i++) {
      components.add(new ComponentSpec("eqc_" + i, "Gates", "AND Gate", 1, "CMPQ" + (i + 1)));
      hints.add(new LayoutHint("eqc_" + i, bits + 1, 0, 2 + i, null));
    }
    String eqAcc = "cmp0_xn";
    for (int i = 1; i < bits; i++) {
      nets.add(net("eqa_" + i, e(eqAcc, 0), e("eqc_" + i, 1)));
      nets.add(net("eqb_" + i, e("cmp" + i + "_xn", 0), e("eqc_" + i, 2)));
      eqAcc = "eqc_" + i;
    }
    nets.add(net("eqout", e(eqAcc, 0), e("eq", 0)));

    // LT = not (GT or EQ); feed gate outputs directly, never route through an
    // output Pin (an output Pin as a net source is not a supported fan-out).
    nets.add(net("lta", e(gtAcc, 0), e("lt_or", 1)));
    nets.add(net("ltb", e(eqAcc, 0), e("lt_or", 2)));
    nets.add(net("ltc", e("lt_or", 0), e("lt_not", 1)));
    nets.add(net("ltout", e("lt_not", 0), e("lt", 0)));

    return new CircuitSpec("comparator_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * BCD (0-9) to 7-segment decoder. Segment truth tables (standard a-g):
   * a=0,2,3,5,6,7,8,9; b=0,1,2,3,4,7,8,9; c=0,1,3,4,5,6,7,8,9;
   * d=0,2,3,5,6,8,9; e=0,2,6,8; f=0,4,5,6,8,9; g=2,3,4,5,6,8,9.
   * Each segment = OR of minterm products (2-input AND cascades + OR chain).
   */
  static CircuitSpec buildBcd7Seg() {
    int[][] segs = {
      {0, 2, 3, 5, 6, 7, 8, 9},
      {0, 1, 2, 3, 4, 7, 8, 9},
      {0, 1, 3, 4, 5, 6, 7, 8, 9},
      {0, 2, 3, 5, 6, 8, 9},
      {0, 2, 6, 8},
      {0, 4, 5, 6, 8, 9},
      {2, 3, 4, 5, 6, 8, 9},
    };
    String[] segNames = {"a", "b", "c", "d", "e", "f", "g"};

    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < 4; i++) {
      addInput(ports, "b" + i, "B" + i, "seg7_not" + i);
      components.add(new ComponentSpec("seg7_not" + i, "Gates", "NOT Gate", 1, "B7N" + (i + 1)));
      hints.add(new LayoutHint("seg7_not" + i, 0, 0, i, null));
      nets.add(net("seg7_nb" + i, e("b" + i, 0), e("seg7_not" + i, 1)));
    }
    for (int s = 0; s < 7; s++) {
      addOutput(ports, "seg" + s, segNames[s], "seg7_not0");
    }

    int seq = 0;
    for (int s = 0; s < 7; s++) {
      String orAcc = null;
      for (int m = 0; m < segs[s].length; m++) {
        int minterm = segs[s][m];
        String andAcc = null;
        for (int bit = 0; bit < 4; bit++) {
          boolean one = ((minterm >> bit) & 1) == 1;
          String src = one ? "b" + bit : "seg7_not" + bit;
          if (andAcc == null) {
            andAcc = src;
          } else {
            String gate = "seg7_p" + seq;
            components.add(new ComponentSpec(gate, "Gates", "AND Gate", 1, "B7P" + (seq + 1)));
            hints.add(new LayoutHint(gate, 1 + bit, 0, s, null));
            nets.add(net("seg7_pa" + seq, e(andAcc, 0), e(gate, 1)));
            nets.add(net("seg7_pb" + seq, e(src, 0), e(gate, 2)));
            andAcc = gate;
            seq++;
          }
        }
        if (orAcc == null) {
          orAcc = andAcc;
        } else {
          String gate = "seg7_o" + seq;
          components.add(new ComponentSpec(gate, "Gates", "OR Gate", 1, "B7O" + (seq + 1)));
          hints.add(new LayoutHint(gate, 6, 0, s, null));
          nets.add(net("seg7_oa" + seq, e(orAcc, 0), e(gate, 1)));
          nets.add(net("seg7_ob" + seq, e(andAcc, 0), e(gate, 2)));
          orAcc = gate;
          seq++;
        }
      }
      nets.add(net("seg7_out" + s, e(orAcc, 0), e("seg" + s, 0)));
    }

    return new CircuitSpec("bcd7seg", components, ports, nets, hints);
  }

  /**
   * N x N unsigned array multiplier (shift-add): partial products p[i][j] =
   * A[j] and B[i], accumulated by N-1 ripple-add stages with the i-th product
   * shifted left by i. Output width 2N. Missing operand bits and the first
   * carry-in tie to a shared GND input pin (constant 0).
   */
  static CircuitSpec buildMultiplier(int bits) {
    if (bits < 2 || bits > 8) {
      throw new IllegalArgumentException("Multiplier width must be 2..8, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    int seq = 0;

    for (int j = 0; j < bits; j++) {
      addInput(ports, "a" + j, "A" + j, "mul_p0");
    }
    for (int i = 0; i < bits; i++) {
      addInput(ports, "b" + i, "B" + i, "mul_p0");
    }
    addInput(ports, "gnd", "GND", "mul_p0");
    for (int k = 0; k < 2 * bits; k++) {
      addOutput(ports, "p" + k, "P" + k, "mul_p0");
    }

    // partial products p[i][j] = A[j] and B[i]
    String[][] pp = new String[bits][bits];
    for (int i = 0; i < bits; i++) {
      for (int j = 0; j < bits; j++) {
        String g = "mul_p" + seq;
        components.add(new ComponentSpec(g, "Gates", "AND Gate", 1, "MULP" + (seq + 1)));
        hints.add(new LayoutHint(g, 0, 0, i, null));
        nets.add(net("mul_pa" + seq, e("a" + j, 0), e(g, 1)));
        nets.add(net("mul_pb" + seq, e("b" + i, 0), e(g, 2)));
        pp[i][j] = g;
        seq++;
      }
    }

    // accumulate: acc = pp[0]; stage i adds (pp[i] << i)
    String[] acc = new String[bits];
    for (int k = 0; k < bits; k++) {
      acc[k] = pp[0][k];
    }
    for (int i = 1; i < bits; i++) {
      int accBits = acc.length;
      int outBits = accBits + 2;
      String[] nacc = new String[outBits];
      String cin = null;
      for (int k = 0; k < outBits; k++) {
        if (k < i) {
          nacc[k] = acc[k];
          continue;
        }
        String a = k < accBits ? acc[k] : "gnd";
        int pj = k - i;
        String b = (pj >= 0 && pj < bits) ? pp[i][pj] : "gnd";
        String cinSig = cin == null ? "gnd" : cin;
        // full adder
        String x = "mul_x" + seq;
        components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "MULX" + (seq + 1)));
        hints.add(new LayoutHint(x, 1 + i, 0, k, null));
        String s = "mul_s" + seq;
        components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "MULS" + (seq + 1)));
        hints.add(new LayoutHint(s, 1 + i, 0, k, null));
        String fa = "mul_f" + seq;
        components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "MULA" + (seq + 1)));
        hints.add(new LayoutHint(fa, 1 + i, 0, k, null));
        String fb = "mul_b" + seq;
        components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "MULB" + (seq + 1)));
        hints.add(new LayoutHint(fb, 1 + i, 0, k, null));
        String fo = "mul_o" + seq;
        components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "MULO" + (seq + 1)));
        hints.add(new LayoutHint(fo, 1 + i, 0, k, null));
        String fg = "mul_g" + seq;
        components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "MULG" + (seq + 1)));
        hints.add(new LayoutHint(fg, 1 + i, 0, k, null));
        seq++;
        // x = a xor b ; s = x xor cin
        nets.add(net("mul_xa" + seq, e(a, 0), e(x, 1)));
        nets.add(net("mul_xb" + seq, e(b, 0), e(x, 2)));
        nets.add(net("mul_sa" + seq, e(x, 0), e(s, 1)));
        nets.add(net("mul_sb" + seq, e(cinSig, 0), e(s, 2)));
        nacc[k] = s;
        // carry = (a and b) or (cin and x)
        nets.add(net("mul_fa" + seq, e(a, 0), e(fa, 1)));
        nets.add(net("mul_fb" + seq, e(b, 0), e(fa, 2)));
        nets.add(net("mul_ga" + seq, e(cinSig, 0), e(fb, 1)));
        nets.add(net("mul_gb" + seq, e(x, 0), e(fb, 2)));
        nets.add(net("mul_o1" + seq, e(fa, 0), e(fo, 1)));
        nets.add(net("mul_o2" + seq, e(fb, 0), e(fo, 2)));
        nets.add(net("mul_g1" + seq, e(fo, 0), e(fg, 1)));
        cin = fg;
      }
      acc = new String[outBits];
      for (int k = 0; k < outBits; k++) {
        acc[k] = nacc[k];
      }
    }

    for (int k = 0; k < 2 * bits; k++) {
      nets.add(net("mul_out" + k, e(acc[k], 0), e("p" + k, 0)));
    }

    return new CircuitSpec("multiplier_" + bits + "x" + bits, components, ports, nets, hints);
  }
  private static String srcOr(String id, String fallback) {
    return id == null ? fallback : id;
  }

  /**
   * N-bit ripple-borrow subtractor: D = A - B - Bin. Bit slice:
   * x = A xor B ; D = x xor Bin ;
   * Bout = (not A and (B or Bin)) or (B and Bin).
   * Inputs A0..A(N-1), B0..B(N-1), Bin; outputs D0..D(N-1), Bout.
   */
  static CircuitSpec buildSubtractor(int bits) {
    if (bits < 1 || bits > 16) {
      throw new IllegalArgumentException("Subtractor width must be 1..16, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < bits; i++) {
      addInput(ports, "a" + i, "A" + i, "sub" + i + "_x");
      addInput(ports, "b" + i, "B" + i, "sub" + i + "_x");
      addOutput(ports, "d" + i, "D" + i, "sub" + i + "_d");
    }
    addInput(ports, "bin", "Bin", "sub0_d");
    addOutput(ports, "bout", "Bout", "sub" + (bits - 1) + "_bo");

    for (int i = 0; i < bits; i++) {
      String g = "sub" + i + "_";
      String chip = "SUB" + i;
      components.add(new ComponentSpec(g + "x", "Gates", "XOR Gate", 1, chip + "X"));
      components.add(new ComponentSpec(g + "d", "Gates", "XOR Gate", 1, chip + "D"));
      components.add(new ComponentSpec(g + "na", "Gates", "NOT Gate", 1, chip + "N"));
      components.add(new ComponentSpec(g + "bo", "Gates", "OR Gate", 1, chip + "O"));
      components.add(new ComponentSpec(g + "b1", "Gates", "AND Gate", 1, chip + "A"));
      components.add(new ComponentSpec(g + "b2", "Gates", "AND Gate", 1, chip + "B"));
      components.add(new ComponentSpec(g + "b3", "Gates", "OR Gate", 1, chip + "C"));
      hints.add(new LayoutHint(g + "x", i, 0, 0, null));
      hints.add(new LayoutHint(g + "d", i, 0, 1, null));
      hints.add(new LayoutHint(g + "na", i, 0, 2, null));
      hints.add(new LayoutHint(g + "bo", i, 0, 3, null));
      hints.add(new LayoutHint(g + "b1", i, 0, 4, null));
      hints.add(new LayoutHint(g + "b2", i, 0, 5, null));
      hints.add(new LayoutHint(g + "b3", i, 0, 6, null));
      nets.add(net("ax" + i, e("a" + i, 0), e(g + "x", 1), e(g + "na", 1)));
      nets.add(net("bx" + i, e("b" + i, 0), e(g + "x", 2), e(g + "bo", 1), e(g + "b2", 1)));
    }

    String borrowIn = "bin";
    for (int i = 0; i < bits; i++) {
      String g = "sub" + i + "_";
      // D = x xor Bin ; x = a xor b
      nets.add(net("dx" + i, e(g + "x", 0), e(g + "d", 1)));
      nets.add(net("di" + i, e(borrowIn, 0), e(g + "d", 2)));
      nets.add(net("do" + i, e(g + "d", 0), e("d" + i, 0)));
      // Bout = (not a and (b or bin)) or (b and bin)
      nets.add(net("bob" + i, e(borrowIn, 0), e(g + "bo", 2)));
      nets.add(net("bo1" + i, e(g + "na", 0), e(g + "b1", 1)));
      nets.add(net("bo2" + i, e(g + "bo", 0), e(g + "b1", 2)));
      nets.add(net("bo4" + i, e(borrowIn, 0), e(g + "b2", 2)));
      nets.add(net("bo5" + i, e(g + "b1", 0), e(g + "b3", 1)));
      nets.add(net("bo6" + i, e(g + "b2", 0), e(g + "b3", 2)));
      borrowIn = g + "b3";
    }
    nets.add(net("bout", e(borrowIn, 0), e("bout", 0)));

    return new CircuitSpec("subtractor_" + bits + "bit", components, ports, nets, hints);
  }

  private static void addInput(
      List<ExternalPort> ports, String id, String label, String target) {
    ports.add(new ExternalPort(id, label, 1, PortDirection.INPUT, PortSide.LEFT, target, 0));
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
