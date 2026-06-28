package com.logisim_ai.validation;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Structural checks for TTL DIP subcircuit-view .circ files. */
public final class TtlDipSubcircuitInspector {
  private static final Pattern MAIN =
      Pattern.compile("<main\\s+name=\"([^\"]+)\"\\s*/>");
  private static final Pattern CIRCUIT =
      Pattern.compile("<circuit\\s+name=\"([^\"]+)\"[^>]*>(.*?)</circuit>", Pattern.DOTALL);
  private static final Pattern COMPONENT =
      Pattern.compile("<comp\\b([^>]*?)(?:/>|>((?:(?!</comp>).)*?)</comp>)", Pattern.DOTALL);
  private static final Pattern WIRE =
      Pattern.compile("<wire\\s+from=\"\\((-?\\d+),(-?\\d+)\\)\"\\s+to=\"\\((-?\\d+),(-?\\d+)\\)\"\\s*/>");
  private static final Pattern ATTR_LOC = Pattern.compile("loc=\"\\((-?\\d+),(-?\\d+)\\)\"");
  private static final Pattern ATTR_NAME = Pattern.compile("name=\"([^\"]+)\"");
  private static final Pattern CHIP_NAME = Pattern.compile("U\\d+_\\d+");

  private TtlDipSubcircuitInspector() {}

  public static Report inspect(File circFile) throws java.io.IOException {
    String xml = Files.readString(circFile.toPath(), StandardCharsets.UTF_8);
    List<String> issues = new ArrayList<>();
    Map<String, String> circuits = circuits(xml);
    String mainName = mainName(xml);
    if (mainName.isBlank()) {
      issues.add("Missing <main> circuit declaration.");
      return new Report(circFile.getName(), issues);
    }
    String mainXml = circuits.get(mainName);
    if (mainXml == null) {
      issues.add("Main circuit is declared but missing: " + mainName);
      return new Report(circFile.getName(), issues);
    }

    List<ParsedComp> topChips = parseComponents(mainXml).stream()
        .filter(comp -> CHIP_NAME.matcher(comp.name()).matches())
        .toList();
    if (topChips.isEmpty()) {
      issues.add("Main circuit has no TTL DIP chip subcircuit instances.");
    }

    for (ParsedComp chip : topChips) {
      String childXml = circuits.get(chip.name());
      if (childXml == null) {
        issues.add("Top-level chip instance has no matching subcircuit: " + chip.name());
        continue;
      }
      long pinCount = parseComponents(childXml).stream()
          .filter(comp -> comp.name().equals("Pin"))
          .count();
      if (pinCount != 14) {
        issues.add("Chip subcircuit " + chip.name() + " should expose 14 DIP pins, got " + pinCount);
      }
      long logicGateCount = parseComponents(childXml).stream()
          .filter(comp -> comp.name().endsWith("Gate"))
          .count();
      if (logicGateCount < 1) {
        issues.add("Chip subcircuit " + chip.name() + " has no internal logic gate.");
      }
      if (!hasWireTouching(mainXml, chip.x(), chip.y())) {
        issues.add("Top-level chip instance is not wired at its anchor: "
            + chip.name() + " at (" + chip.x() + "," + chip.y() + ")");
      }
    }

    if (!mainXml.contains("label\" val=\"A\"")
        || !mainXml.contains("label\" val=\"B\"")
        || !mainXml.contains("label\" val=\"Cin\"")
        || !mainXml.contains("label\" val=\"SUM\"")
        || !mainXml.contains("label\" val=\"Cout\"")) {
      issues.add("Main circuit is missing expected full-adder external ports A/B/Cin/SUM/Cout.");
    }

    return new Report(circFile.getName(), issues);
  }

  public static void requireClean(File circFile) throws java.io.IOException {
    Report report = inspect(circFile);
    if (!report.clean()) {
      throw new IllegalStateException(report.toHumanString());
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      throw new IllegalArgumentException("Usage: TtlDipSubcircuitInspector <file.circ>...");
    }
    boolean failed = false;
    for (String arg : args) {
      Report report = inspect(new File(arg));
      System.out.println(report.toHumanString());
      failed |= !report.clean();
    }
    if (failed) {
      System.exit(2);
    }
  }

  private static String mainName(String xml) {
    Matcher matcher = MAIN.matcher(xml);
    return matcher.find() ? matcher.group(1) : "";
  }

  private static Map<String, String> circuits(String xml) {
    Map<String, String> result = new LinkedHashMap<>();
    Matcher matcher = CIRCUIT.matcher(xml);
    while (matcher.find()) {
      result.put(matcher.group(1), matcher.group(2));
    }
    return result;
  }

  private static List<ParsedComp> parseComponents(String xml) {
    List<ParsedComp> result = new ArrayList<>();
    Matcher matcher = COMPONENT.matcher(xml);
    while (matcher.find()) {
      String attrs = matcher.group(1);
      Matcher locM = ATTR_LOC.matcher(attrs);
      Matcher nameM = ATTR_NAME.matcher(attrs);
      if (!locM.find() || !nameM.find()) {
        continue;
      }
      result.add(new ParsedComp(
          Integer.parseInt(locM.group(1)),
          Integer.parseInt(locM.group(2)),
          nameM.group(1)));
    }
    return result;
  }

  private static boolean hasWireTouching(String circuitXml, int x, int y) {
    Matcher matcher = WIRE.matcher(circuitXml);
    while (matcher.find()) {
      int x1 = Integer.parseInt(matcher.group(1));
      int y1 = Integer.parseInt(matcher.group(2));
      int x2 = Integer.parseInt(matcher.group(3));
      int y2 = Integer.parseInt(matcher.group(4));
      if ((x1 == x && y1 == y) || (x2 == x && y2 == y)) {
        return true;
      }
    }
    return false;
  }

  private record ParsedComp(int x, int y, String name) {}

  public record Report(String fileName, List<String> issues) {
    public boolean clean() {
      return issues.isEmpty();
    }

    public String toHumanString() {
      if (clean()) {
        return "[OK] " + fileName + " TTL DIP subcircuit structure checks passed";
      }
      return "[FAIL] " + fileName + "\n - " + String.join("\n - ", issues);
    }
  }
}
