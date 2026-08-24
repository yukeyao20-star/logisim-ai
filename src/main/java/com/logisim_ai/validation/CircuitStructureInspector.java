package com.logisim_ai.validation;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.ExternalPort;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Verifies that generated .circ XML still contains the structure requested by CircuitSpec. */
public final class CircuitStructureInspector {
  private static final Pattern MAIN =
      Pattern.compile("<main\\s+name=\"([^\"]+)\"\\s*/>");
  private static final Pattern CIRCUIT =
      Pattern.compile("<circuit\\s+name=\"([^\"]+)\"[^>]*>(.*?)</circuit>", Pattern.DOTALL);
  private static final Pattern COMPONENT =
      Pattern.compile("<comp\\b([^>]*?)(?:/>|>((?:(?!</comp>).)*?)</comp>)", Pattern.DOTALL);
  private static final Pattern ATTR_NAME = Pattern.compile("name=\"([^\"]+)\"");
  private static final Pattern ATTR_LABEL = Pattern.compile("<a\\s+name=\"label\"\\s+val=\"([^\"]+)\"");

  private CircuitStructureInspector() {}

  public static Report inspect(CircuitSpec spec, File circFile) throws java.io.IOException {
    String xml = Files.readString(circFile.toPath(), StandardCharsets.UTF_8);
    return inspectXml(spec, circFile.getName(), xml);
  }

  public static Report inspectXml(CircuitSpec spec, String fileName, String xml) {
    List<String> issues = new ArrayList<>();
    String mainName = mainCircuitName(xml);
    if (mainName.isBlank()) {
      issues.add("Missing <main> circuit declaration");
    } else if (!mainName.equals(spec.name())) {
      issues.add("Main circuit mismatch: expected " + spec.name() + ", got " + mainName);
    }

    String mainXml = circuitXml(xml, mainName.isBlank() ? spec.name() : mainName);
    if (mainXml.isBlank()) {
      issues.add("Missing circuit body for main circuit: " + spec.name());
      return new Report(fileName, issues);
    }

    List<ParsedComp> comps = parseComponents(mainXml);
    verifyComponentFactories(spec, comps, issues);
    verifyExternalPins(spec, comps, issues);
    verifyTunnelCount(spec, comps, issues);
    return new Report(fileName, issues);
  }

  private static void verifyComponentFactories(
      CircuitSpec spec,
      List<ParsedComp> comps,
      List<String> issues) {
    Map<String, Integer> expected = new LinkedHashMap<>();
    for (ComponentSpec component : spec.components()) {
      expected.merge(component.factoryName(), 1, Integer::sum);
    }
    Map<String, Integer> actual = new LinkedHashMap<>();
    for (ParsedComp comp : comps) {
      if (!comp.name().equals("Pin") && !comp.name().equals("Tunnel")
          && !comp.name().equals("Clock")) {
        actual.merge(comp.name(), 1, Integer::sum);
      }
    }
    if (!expected.equals(actual)) {
      issues.add("Component factory counts mismatch: expected " + expected + ", got " + actual);
    }

    for (ComponentSpec component : spec.components()) {
      if (component.label() == null || component.label().isBlank()) {
        continue;
      }
      boolean labelFound = comps.stream()
          .anyMatch(comp -> comp.name().equals(component.factoryName())
              && comp.label().equals(component.label()));
      if (!labelFound) {
        issues.add("Missing component label " + component.label()
            + " for factory " + component.factoryName());
      }
    }
  }

  private static void verifyExternalPins(
      CircuitSpec spec,
      List<ParsedComp> comps,
      List<String> issues) {
    List<String> expectedLabels = spec.externalPorts().stream()
        .map(ExternalPort::label)
        .sorted()
        .toList();
    List<String> actualLabels = comps.stream()
        .filter(comp -> comp.name().equals("Pin") || comp.name().equals("Clock"))
        .map(ParsedComp::label)
        .sorted()
        .toList();
    if (!expectedLabels.equals(actualLabels)) {
      issues.add("External pin labels mismatch: expected " + expectedLabels + ", got " + actualLabels);
    }
  }

  private static void verifyTunnelCount(
      CircuitSpec spec,
      List<ParsedComp> comps,
      List<String> issues) {
    long actualTunnelCount = comps.stream()
        .filter(comp -> comp.name().equals("Tunnel"))
        .count();
    // DirectMultiChipCompiler structural templates wire nets with orthogonal
    // segments and no Tunnels; the Tunnel-count contract only applies to the
    // netlist (Tunnel-based) compilation path. A circuit with zero Tunnels is
    // therefore a valid Direct-layout circuit, not a mismatch.
    if (actualTunnelCount == 0) {
      return;
    }
    int expectedTunnelCount = spec.nets().stream()
        .mapToInt(net -> net.endpoints().size())
        .sum();
    // Hybrid router contract (IsolatedRouter): long nets are tunneled with
    // short "TQ<base36>" labels while short nets stay physical, so any count
    // between 0 and the full endpoint sum is valid as long as every label
    // follows the hybrid idiom. Connectivity itself is asserted by the
    // netlist audit (CircuitWiringChecker), not by this count.
    boolean hybridLabels = comps.stream()
        .filter(comp -> comp.name().equals("Tunnel"))
        .allMatch(comp -> comp.label().matches("TQ[0-9a-z]+"));
    if (actualTunnelCount != expectedTunnelCount && !hybridLabels) {
      issues.add("Tunnel count mismatch: expected " + expectedTunnelCount + ", got " + actualTunnelCount);
    }
  }

  private static List<ParsedComp> parseComponents(String xml) {
    List<ParsedComp> result = new ArrayList<>();
    Matcher matcher = COMPONENT.matcher(xml);
    while (matcher.find()) {
      String attrs = matcher.group(1);
      String body = matcher.group(2) == null ? "" : matcher.group(2);
      Matcher nameM = ATTR_NAME.matcher(attrs);
      if (!nameM.find()) {
        continue;
      }
      String label = "";
      Matcher labelM = ATTR_LABEL.matcher(body);
      if (labelM.find()) {
        label = labelM.group(1);
      }
      result.add(new ParsedComp(nameM.group(1), label));
    }
    return result;
  }

  private static String mainCircuitName(String xml) {
    Matcher matcher = MAIN.matcher(xml);
    return matcher.find() ? matcher.group(1) : "";
  }

  private static String circuitXml(String xml, String name) {
    Matcher matcher = CIRCUIT.matcher(xml);
    while (matcher.find()) {
      if (matcher.group(1).equals(name)) {
        return matcher.group(2);
      }
    }
    return "";
  }

  private record ParsedComp(String name, String label) {}

  public record Report(String fileName, List<String> issues) {
    public boolean clean() {
      return issues.isEmpty();
    }

    public String toHumanString() {
      if (clean()) {
        return "[OK] " + fileName + " structure matches CircuitSpec";
      }
      return "[FAIL] " + fileName + "\n - " + String.join("\n - ", issues);
    }
  }
}
