package com.logisim_ai.ir;

import java.util.Collections;
import java.util.List;

/**
 * Circuit IR consumed by the Logisim compiler.
 *
 * <p>Supports both single-component mode (externalPorts only) and
 * multi-component netlist mode (nets + layoutHints).
 */
public record CircuitSpec(
    String name,
    List<ComponentSpec> components,
    List<ExternalPort> externalPorts,
    List<NetSpec> nets,
    List<LayoutHint> layoutHints) {

  /** Full constructor with nets and layout hints. */
  public CircuitSpec {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Circuit name must not be blank");
    }
    components = List.copyOf(components);
    externalPorts = List.copyOf(externalPorts);
    nets = nets != null ? List.copyOf(nets) : List.of();
    layoutHints = layoutHints != null ? List.copyOf(layoutHints) : List.of();
    if (components.isEmpty()) {
      throw new IllegalArgumentException("Circuit must contain at least one component");
    }
  }

  /** Backward-compatible constructor for single-component mode. */
  public CircuitSpec(
      String name,
      List<ComponentSpec> components,
      List<ExternalPort> externalPorts) {
    this(name, components, externalPorts, null, null);
  }

  /** Whether this spec uses netlist mode (nets defined explicitly). */
  public boolean isNetlist() {
    return !nets.isEmpty();
  }
}
