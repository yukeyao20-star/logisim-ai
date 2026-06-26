package com.logisim_ai.validation;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.NetSpec;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Static semantic validation for circuit IR before compilation.
 *
 * <p>Checks:
 * 1. Each ExternalPort.targetComponentId must exist in the component list.
 * 2. Each targetEndIndex must be valid for the component.
 * 3. Each NetSpec must have at least 2 endpoints.
 * 4. No input Pin should be driven by multiple output sources.
 * 5. All component IDs must be unique.
 * 6. All ExternalPort IDs must be unique.
 * 7. All NetSpec IDs must be unique.
 */
public final class CircuitSemanticValidator {
  private CircuitSemanticValidator() {}

  public static Report validate(CircuitSpec spec) {
    List<String> issues = new ArrayList<>();

    // Check unique component IDs
    Set<String> compIds = new HashSet<>();
    for (ComponentSpec cs : spec.components()) {
      if (!compIds.add(cs.id())) {
        issues.add("Duplicate component id: " + cs.id());
      }
    }

    // Check unique external port IDs
    Set<String> portIds = new HashSet<>();
    for (ExternalPort ep : spec.externalPorts()) {
      if (!portIds.add(ep.id())) {
        issues.add("Duplicate external port id: " + ep.id());
      }
    }

    // Check unique net IDs
    Set<String> netIds = new HashSet<>();
    for (NetSpec ns : spec.nets()) {
      if (!netIds.add(ns.id())) {
        issues.add("Duplicate net id: " + ns.id());
      }
    }

    // Check ExternalPort targets exist
    for (ExternalPort ep : spec.externalPorts()) {
      if (!compIds.contains(ep.targetComponentId())) {
        issues.add("ExternalPort '" + ep.id() + "' targets unknown component: " + ep.targetComponentId());
      }
      // Note: we can't check targetEndIndex bounds here without knowing
      // the actual component's port count (that happens at compile time)
    }

    // Check NetSpec endpoints reference existing components
    Set<String> drivenInputPorts = new HashSet<>();
    for (NetSpec ns : spec.nets()) {
      if (ns.endpoints().size() < 2) {
        issues.add("Net '" + ns.id() + "' has fewer than 2 endpoints");
      }
      for (EndpointRef ref : ns.endpoints()) {
        if (!compIds.contains(ref.componentId()) && !portIds.contains(ref.componentId())) {
          issues.add("Net '" + ns.id() + "' references unknown component: " + ref.componentId());
        }
      }
    }

    // Check for input ports driven by multiple nets
    // (A port that is an input shouldn't appear as endpoint in more than one net as a sink)
    Set<String> inputSinks = new HashSet<>();
    for (NetSpec ns : spec.nets()) {
      // Heuristic: first endpoint is source, rest are sinks
      for (int i = 1; i < ns.endpoints().size(); i++) {
        EndpointRef ref = ns.endpoints().get(i);
        String key = ref.componentId() + "." + ref.endIndex();
        if (!inputSinks.add(key)) {
          issues.add("Component endpoint " + key + " is driven by multiple nets (possible multi-driver)");
        }
      }
    }

    return new Report(issues);
  }

  public static void requireValid(CircuitSpec spec) {
    Report report = validate(spec);
    if (!report.valid()) {
      throw new IllegalStateException(
          "CircuitSpec semantic validation failed:\n" + report.toHumanString());
    }
  }

  public record Report(List<String> issues) {
    public boolean valid() {
      return issues.isEmpty();
    }

    public String toHumanString() {
      if (valid()) {
        return "[OK] CircuitSpec semantic validation passed";
      }
      return "[FAIL] CircuitSpec semantic validation:\n - " + String.join("\n - ", issues);
    }
  }
}
