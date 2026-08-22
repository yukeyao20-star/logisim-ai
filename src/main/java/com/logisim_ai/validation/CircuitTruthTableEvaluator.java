package com.logisim_ai.validation;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Small Boolean evaluator for CircuitSpec netlists made from basic Gates.
 *
 * <p>This validates logic before opening Logisim: it treats every NetSpec's
 * first endpoint as the driver, propagates values through nets, evaluates basic
 * gates, and returns visible output port values.
 */
public final class CircuitTruthTableEvaluator {
  private CircuitTruthTableEvaluator() {}

  public static Map<String, Boolean> evaluate(CircuitSpec spec, Map<String, Boolean> inputs) {
    Map<String, ComponentSpec> components = new HashMap<>();
    for (ComponentSpec component : spec.components()) {
      components.put(component.id(), component);
    }

    Map<String, ExternalPort> ports = new LinkedHashMap<>();
    for (ExternalPort port : spec.externalPorts()) {
      ports.put(port.id(), port);
    }

    Map<EndpointRef, Boolean> values = new HashMap<>();
    for (ExternalPort port : spec.externalPorts()) {
      if (port.direction() != PortDirection.INPUT) {
        continue;
      }
      Boolean value = inputs.get(port.id());
      if (value == null) {
        value = inputs.get(port.label());
      }
      if (value == null) {
        throw new IllegalArgumentException("Missing input value for port: " + port.id());
      }
      values.put(new EndpointRef(port.id(), 0), value);
    }

    int maxIterations = Math.max(8, spec.components().size() * 4 + spec.nets().size() * 2);
    for (int iteration = 0; iteration < maxIterations; iteration++) {
      boolean changed = false;
      for (NetSpec net : spec.nets()) {
        Optional<Boolean> driven = firstKnown(values, net.endpoints());
        if (driven.isEmpty()) {
          continue;
        }
        for (EndpointRef endpoint : net.endpoints()) {
          changed |= putIfChanged(values, endpoint, driven.get());
        }
      }

      for (ComponentSpec component : spec.components()) {
        Optional<Boolean> output = evaluateComponent(component, values);
        if (output.isPresent()) {
          changed |= putIfChanged(values, new EndpointRef(component.id(), 0), output.get());
        }
      }

      if (!changed) {
        break;
      }
    }

    Map<String, Boolean> outputs = new LinkedHashMap<>();
    for (ExternalPort port : spec.externalPorts()) {
      if (port.direction() != PortDirection.OUTPUT) {
        continue;
      }
      Boolean value = values.get(new EndpointRef(port.id(), 0));
      if (value == null) {
        throw new IllegalStateException("Output was not resolved: " + port.id());
      }
      outputs.put(port.id(), value);
    }
    return outputs;
  }

  private static Optional<Boolean> firstKnown(Map<EndpointRef, Boolean> values, List<EndpointRef> endpoints) {
    for (EndpointRef endpoint : endpoints) {
      Boolean value = values.get(endpoint);
      if (value != null) {
        return Optional.of(value);
      }
    }
    return Optional.empty();
  }

  private static boolean putIfChanged(Map<EndpointRef, Boolean> values, EndpointRef endpoint, boolean value) {
    Boolean old = values.putIfAbsent(endpoint, value);
    if (old == null) {
      return true;
    }
    if (old != value) {
      throw new IllegalStateException(
          "Conflicting values at " + endpoint.componentId() + "." + endpoint.endIndex());
    }
    return false;
  }

  private static Optional<Boolean> evaluateComponent(
      ComponentSpec component,
      Map<EndpointRef, Boolean> values) {
    String factory = component.factoryName();
    if (factory.equals("NOT Gate")) {
      Boolean input = values.get(new EndpointRef(component.id(), 1));
      return input == null ? Optional.empty() : Optional.of(!input);
    }

    if (!isSupportedMultiInputGate(factory)) {
      throw new IllegalArgumentException("Unsupported gate for truth-table evaluation: " + factory);
    }

    List<Boolean> inputs = new ArrayList<>();
    for (int end = 1; end <= 8; end++) {
      Boolean value = values.get(new EndpointRef(component.id(), end));
      if (value != null) {
        inputs.add(value);
      }
    }
    if (inputs.size() < 2) {
      return Optional.empty();
    }

    boolean output = switch (factory) {
      case "AND Gate" -> inputs.stream().allMatch(Boolean::booleanValue);
      case "OR Gate" -> inputs.stream().anyMatch(Boolean::booleanValue);
      case "XOR Gate" -> inputs.stream().filter(Boolean::booleanValue).count() % 2 == 1;
      case "XNOR Gate" -> inputs.stream().filter(Boolean::booleanValue).count() % 2 == 0;
      case "NAND Gate" -> !inputs.stream().allMatch(Boolean::booleanValue);
      case "NOR Gate" -> !inputs.stream().anyMatch(Boolean::booleanValue);
      default -> throw new IllegalArgumentException("Unsupported gate: " + factory);
    };
    return Optional.of(output);
  }

  private static boolean isSupportedMultiInputGate(String factory) {
    return factory.equals("AND Gate")
        || factory.equals("OR Gate")
        || factory.equals("XOR Gate")
        || factory.equals("XNOR Gate")
        || factory.equals("NAND Gate")
        || factory.equals("NOR Gate");
  }
}
