package com.logisim_ai.validation;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ttl.TtlDipWiringReport;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Checks generated TTL DIP pin-map reports against the logical CircuitSpec. */
public final class TtlDipPinMapInspector {
  private static final Pattern BOUND_PIN_COUNT =
      Pattern.compile("\"boundPinCount\"\\s*:\\s*(\\d+)");
  private static final Pattern BINDING_OBJECT =
      Pattern.compile("\\{\\s*\"chipId\"\\s*:\\s*\"[^\"]+\".*?\\}", Pattern.DOTALL);

  private TtlDipPinMapInspector() {}

  public static Report inspect(CircuitSpec spec, File circFile) throws java.io.IOException {
    Path pinMapPath = pinMapPath(circFile);
    List<String> issues = new ArrayList<>();
    if (!Files.exists(pinMapPath)) {
      issues.add("Missing TTL DIP pin map report: " + pinMapPath);
      return new Report(pinMapPath.toString(), issues);
    }

    String json = Files.readString(pinMapPath, StandardCharsets.UTF_8);
    List<ExpectedPin> expectedPins = expectedPins(spec);
    Map<String, Binding> bindings = parseBindings(json, issues);

    int expectedCount = expectedPins.size();
    int actualCount = boundPinCount(json);
    if (actualCount != expectedCount) {
      issues.add("boundPinCount mismatch expected=" + expectedCount + " actual=" + actualCount);
    }
    if (bindings.size() != expectedCount) {
      issues.add("binding object count mismatch expected=" + expectedCount + " actual=" + bindings.size());
    }

    for (ExpectedPin expected : expectedPins) {
      Binding binding = bindings.get(expected.key());
      if (binding == null) {
        issues.add("Missing binding for " + expected.key());
        continue;
      }
      requireEquals(issues, expected.key(), "packageType", expected.packageType(), binding.packageType());
      requireEquals(issues, expected.key(), "role", expected.role(), binding.role());
      requireEquals(issues, expected.key(), "gateName", expected.gateName(), binding.gateName());
      requireEquals(issues, expected.key(), "netId", expected.netId(), binding.netId());
      if (binding.endIndex() < 0) {
        issues.add(expected.key() + " has invalid endIndex: " + binding.endIndex());
      }
    }

    return new Report(pinMapPath.toString(), issues);
  }

  public static void requireConsistent(CircuitSpec spec, File circFile) throws java.io.IOException {
    Report report = inspect(spec, circFile);
    if (!report.clean()) {
      throw new IllegalStateException(report.toHumanString());
    }
  }

  private static List<ExpectedPin> expectedPins(CircuitSpec spec) {
    List<ExpectedPin> result = new ArrayList<>();
    for (TtlDipWiringReport.Chip chip : TtlDipWiringReport.chips(spec)) {
      for (TtlDipWiringReport.Pin pin : chip.pins()) {
        if (pin.netId().isBlank()) {
          continue;
        }
        result.add(new ExpectedPin(
            chip.chipId(),
            chip.packageType(),
            pin.pinNumber(),
            pin.role(),
            pin.gateName(),
            pin.netId()));
      }
    }
    return List.copyOf(result);
  }

  private static Map<String, Binding> parseBindings(String json, List<String> issues) {
    Map<String, Binding> bindings = new LinkedHashMap<>();
    Matcher matcher = BINDING_OBJECT.matcher(json);
    while (matcher.find()) {
      String object = matcher.group();
      Binding binding = new Binding(
          stringField(object, "chipId"),
          stringField(object, "packageType"),
          intField(object, "pinNumber"),
          stringField(object, "role"),
          stringField(object, "gateName"),
          stringField(object, "netId"),
          intField(object, "endIndex"));
      if (binding.chipId().isBlank() || binding.pinNumber() < 0) {
        issues.add("Malformed binding object: " + object.replace("\n", " "));
        continue;
      }
      Binding previous = bindings.put(binding.key(), binding);
      if (previous != null) {
        issues.add("Duplicate binding for " + binding.key());
      }
    }
    return bindings;
  }

  private static int boundPinCount(String json) {
    Matcher matcher = BOUND_PIN_COUNT.matcher(json);
    return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
  }

  private static String stringField(String object, String field) {
    Pattern pattern = Pattern.compile("\"" + Pattern.quote(field) + "\"\\s*:\\s*\"([^\"]*)\"");
    Matcher matcher = pattern.matcher(object);
    return matcher.find() ? unescape(matcher.group(1)) : "";
  }

  private static int intField(String object, String field) {
    Pattern pattern = Pattern.compile("\"" + Pattern.quote(field) + "\"\\s*:\\s*(-?\\d+)");
    Matcher matcher = pattern.matcher(object);
    return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
  }

  private static String unescape(String text) {
    return text
        .replace("\\\"", "\"")
        .replace("\\\\", "\\");
  }

  private static void requireEquals(
      List<String> issues,
      String key,
      String field,
      String expected,
      String actual) {
    if (!expected.equals(actual)) {
      issues.add(key + " " + field + " mismatch expected=" + expected + " actual=" + actual);
    }
  }

  private static Path pinMapPath(File circFile) {
    String name = circFile.getName();
    String suffix = "_dip_subckt.circ";
    if (name.endsWith(suffix)) {
      String specName = name.substring(0, name.length() - suffix.length());
      return Path.of("generated", "reports", specName + "_ttl_dip_subckt_pinmap.json");
    }
    return circFile.toPath().resolveSibling(name.replace(".circ", "_pinmap.json"));
  }

  private record ExpectedPin(
      String chipId,
      String packageType,
      int pinNumber,
      String role,
      String gateName,
      String netId) {
    String key() {
      return chipId + "." + pinNumber;
    }
  }

  private record Binding(
      String chipId,
      String packageType,
      int pinNumber,
      String role,
      String gateName,
      String netId,
      int endIndex) {
    String key() {
      return chipId + "." + pinNumber;
    }
  }

  public record Report(String fileName, List<String> issues) {
    public boolean clean() {
      return issues.isEmpty();
    }

    public String toHumanString() {
      if (clean()) {
        return "[OK] " + fileName + " TTL DIP pin map matches CircuitSpec";
      }
      return "[FAIL] " + fileName + "\n - " + String.join("\n - ", issues);
    }
  }
}
