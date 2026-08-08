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

  public static Optional<CircuitSpec> tryBuild(String query) {
    if (query == null || query.isBlank()) {
      return Optional.empty();
    }
    String t = query.toLowerCase(Locale.ROOT);
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
    return Optional.empty();
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
