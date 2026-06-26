package com.logisim_ai.ai;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.LayoutHint;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.PortSide;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Placeholder natural-language circuit planner.
 *
 * <p>First version: hardcoded CircuitSpec templates for common circuits.
 * When the engineering pipeline is stable, this will be replaced by
 * an LLM-backed planner that converts natural language to CircuitSpec.
 *
 * <p>Supported queries (case-insensitive, partial match):
 * <ul>
 *   <li>"half adder" → half_adder CircuitSpec</li>
 *   <li>"full adder" → full_adder CircuitSpec</li>
 *   <li>"2-bit adder" / "two bit adder" / "ripple adder" → two_bit_ripple_adder CircuitSpec</li>
 *   <li>"jk flip flop" → jk_flipflop CircuitSpec</li>
 *   <li>"74151" → 74151 mux CircuitSpec</li>
 *   <li>"74160" → 74160 counter CircuitSpec</li>
 * </ul>
 */
public final class NaturalLanguageCircuitPlanner {
  private NaturalLanguageCircuitPlanner() {}

  /** Result of a planning query. */
  public record PlanResult(CircuitSpec spec, String description) {}

  // Keyword → factory method mapping
  private static final Map<List<String>, java.util.function.Supplier<CircuitSpec>> TEMPLATES = Map.of(
      List.of("half adder", "halfadder", "半加器"), NaturalLanguageCircuitPlanner::halfAdder,
      List.of("full adder", "fulladder", "全加器"), NaturalLanguageCircuitPlanner::fullAdder,
      List.of("2-bit adder", "2 bit adder", "two bit adder", "two-bit adder", "ripple adder",
              "2位加法器", "行波进位"), NaturalLanguageCircuitPlanner::twoBitAdder,
      List.of("jk flip flop", "jk flipflop", "jk触发器"), NaturalLanguageCircuitPlanner::jkFlipFlop,
      List.of("74151"), NaturalLanguageCircuitPlanner::ttl74151,
      List.of("74160"), NaturalLanguageCircuitPlanner::ttl74160
  );

  /**
   * Parse a natural-language query and return a matching CircuitSpec.
   *
   * @param query the user's natural language input (e.g., "generate a full adder")
   * @return a PlanResult with the CircuitSpec and description, or null if no match
   */
  public static PlanResult plan(String query) {
    if (query == null || query.isBlank()) return null;

    String normalized = query.toLowerCase(Locale.ROOT).trim();

    for (var entry : TEMPLATES.entrySet()) {
      for (String keyword : entry.getKey()) {
        if (normalized.contains(keyword)) {
          CircuitSpec spec = entry.getValue().get();
          return new PlanResult(spec, "Matched template: " + keyword);
        }
      }
    }

    return null;
  }

  /**
   * List all supported circuit templates.
   */
  public static List<String> supportedCircuits() {
    return TEMPLATES.keySet().stream()
        .map(keys -> keys.get(0))
        .toList();
  }

  // ---- Template builders ----

  private static CircuitSpec halfAdder() {
    return new CircuitSpec(
        "half_adder",
        List.of(
            new ComponentSpec("xor1", "Gates", "XOR Gate", 1, ""),
            new ComponentSpec("and1", "Gates", "AND Gate", 1, "")),
        List.of(
            new ExternalPort("a", "A", 1, PortDirection.INPUT, PortSide.LEFT, "xor1", 1),
            new ExternalPort("b", "B", 1, PortDirection.INPUT, PortSide.LEFT, "xor1", 2),
            new ExternalPort("sum", "SUM", 1, PortDirection.OUTPUT, PortSide.RIGHT, "xor1", 0),
            new ExternalPort("carry", "CARRY", 1, PortDirection.OUTPUT, PortSide.RIGHT, "and1", 0)),
        List.of(
            new NetSpec("a_to_and1", List.of(
                new EndpointRef("xor1", 1),
                new EndpointRef("and1", 1))),
            new NetSpec("b_to_and1", List.of(
                new EndpointRef("xor1", 2),
                new EndpointRef("and1", 2)))),
        List.of(
            new LayoutHint("xor1", 0, 0, 0, null),
            new LayoutHint("and1", 0, 0, 1, null)));
  }

  private static CircuitSpec fullAdder() {
    return new CircuitSpec(
        "full_adder",
        List.of(
            new ComponentSpec("xor1", "Gates", "XOR Gate", 1, ""),
            new ComponentSpec("xor2", "Gates", "XOR Gate", 1, ""),
            new ComponentSpec("and1", "Gates", "AND Gate", 1, ""),
            new ComponentSpec("and2", "Gates", "AND Gate", 1, ""),
            new ComponentSpec("or1", "Gates", "OR Gate", 1, "")),
        List.of(
            new ExternalPort("a", "A", 1, PortDirection.INPUT, PortSide.LEFT, "xor1", 1),
            new ExternalPort("b", "B", 1, PortDirection.INPUT, PortSide.LEFT, "xor1", 2),
            new ExternalPort("cin", "Cin", 1, PortDirection.INPUT, PortSide.LEFT, "xor2", 2),
            new ExternalPort("sum", "SUM", 1, PortDirection.OUTPUT, PortSide.RIGHT, "xor2", 0),
            new ExternalPort("cout", "Cout", 1, PortDirection.OUTPUT, PortSide.RIGHT, "or1", 0)),
        List.of(
            new NetSpec("a_to_and1", List.of(
                new EndpointRef("xor1", 1),
                new EndpointRef("and1", 1))),
            new NetSpec("b_to_and1", List.of(
                new EndpointRef("xor1", 2),
                new EndpointRef("and1", 2))),
            new NetSpec("x1_to_xor2", List.of(
                new EndpointRef("xor1", 0),
                new EndpointRef("xor2", 1))),
            new NetSpec("x1_to_and2", List.of(
                new EndpointRef("xor1", 0),
                new EndpointRef("and2", 1))),
            new NetSpec("cin_to_and2", List.of(
                new EndpointRef("xor2", 2),
                new EndpointRef("and2", 2))),
            new NetSpec("ab_to_or1", List.of(
                new EndpointRef("and1", 0),
                new EndpointRef("or1", 1))),
            new NetSpec("xc_to_or1", List.of(
                new EndpointRef("and2", 0),
                new EndpointRef("or1", 2)))),
        List.of(
            new LayoutHint("xor1", 0, 0, 0, null),
            new LayoutHint("and1", 0, 0, 1, null),
            new LayoutHint("xor2", 1, 1, 0, null),
            new LayoutHint("and2", 1, 1, 1, null),
            new LayoutHint("or1", 2, 2, 0, null)));
  }

  private static CircuitSpec twoBitAdder() {
    // Uses native Arithmetic/Adder component for simplicity
    return new CircuitSpec(
        "two_bit_ripple_adder",
        List.of(
            new ComponentSpec("adder", "Arithmetic", "Adder", 2, "")),
        List.of(
            new ExternalPort("a", "A", 2, PortDirection.INPUT, PortSide.LEFT, "adder", 1),
            new ExternalPort("b", "B", 2, PortDirection.INPUT, PortSide.RIGHT, "adder", 2),
            new ExternalPort("sum", "SUM", 2, PortDirection.OUTPUT, PortSide.RIGHT, "adder", 0)),
        List.of(),
        List.of());
  }

  private static CircuitSpec jkFlipFlop() {
    return new CircuitSpec(
        "jk_flipflop",
        List.of(
            new ComponentSpec("jk", "Memory", "JK Flip-Flop", 1, "")),
        List.of(
            new ExternalPort("j", "J", 1, PortDirection.INPUT, PortSide.LEFT, "jk", 2),
            new ExternalPort("k", "K", 1, PortDirection.INPUT, PortSide.LEFT, "jk", 3),
            new ExternalPort("clk", "CLK", 1, PortDirection.INPUT, PortSide.BOTTOM, "jk", 0),
            new ExternalPort("q", "Q", 1, PortDirection.OUTPUT, PortSide.RIGHT, "jk", 4),
            new ExternalPort("qbar", "Q'", 1, PortDirection.OUTPUT, PortSide.RIGHT, "jk", 5)),
        List.of(),
        List.of());
  }

  private static CircuitSpec ttl74151() {
    // 8-to-1 multiplexer using native TTL component
    return new CircuitSpec(
        "ttl_74151_native",
        List.of(
            new ComponentSpec("mux", "TTL", "74151", 1, "")),
        List.of(
            new ExternalPort("i0", "I0", 1, PortDirection.INPUT, PortSide.LEFT, "mux", 1),
            new ExternalPort("i7", "I7", 1, PortDirection.INPUT, PortSide.LEFT, "mux", 8),
            new ExternalPort("s0", "S0", 1, PortDirection.INPUT, PortSide.BOTTOM, "mux", 9),
            new ExternalPort("y", "Y", 1, PortDirection.OUTPUT, PortSide.RIGHT, "mux", 0)),
        List.of(),
        List.of());
  }

  private static CircuitSpec ttl74160() {
    // Synchronous 4-bit counter using native TTL component
    return new CircuitSpec(
        "ttl_74160_native",
        List.of(
            new ComponentSpec("counter", "TTL", "74160", 1, "")),
        List.of(
            new ExternalPort("clk", "CLK", 1, PortDirection.INPUT, PortSide.BOTTOM, "counter", 0),
            new ExternalPort("load", "LOAD", 1, PortDirection.INPUT, PortSide.LEFT, "counter", 1),
            new ExternalPort("qa", "QA", 1, PortDirection.OUTPUT, PortSide.RIGHT, "counter", 2)),
        List.of(),
        List.of());
  }
}
