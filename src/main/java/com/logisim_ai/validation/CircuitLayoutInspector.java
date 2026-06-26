package com.logisim_ai.validation;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Static checks for generated .circ files before they are shown as deliverables. */
public final class CircuitLayoutInspector {
  private static final Pattern COMPONENT =
      Pattern.compile("<comp\\b[^>]*loc=\"\\((-?\\d+),(-?\\d+)\\)\"[^>]*name=\"([^\"]+)\"");
  private static final Pattern WIRE =
      Pattern.compile("<wire\\s+from=\"\\((-?\\d+),(-?\\d+)\\)\"\\s+to=\"\\((-?\\d+),(-?\\d+)\\)\"\\s*/>");

  private CircuitLayoutInspector() {}

  public static Report inspect(File circFile) throws java.io.IOException {
    String xml = Files.readString(circFile.toPath(), StandardCharsets.UTF_8);
    List<String> issues = new ArrayList<>();
    if (xml.contains("lib=\"toolbar\"")) {
      issues.add("Toolbar contains unresolved lib=\"toolbar\" entries.");
    }
    inspectComponents(xml, issues);
    inspectWires(xml, issues);
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
      throw new IllegalArgumentException("Usage: CircuitLayoutInspector <file.circ>...");
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

  private static void inspectComponents(String xml, List<String> issues) {
    Matcher matcher = COMPONENT.matcher(xml);
    List<ComponentLoc> components = new ArrayList<>();
    while (matcher.find()) {
      components.add(new ComponentLoc(
          Integer.parseInt(matcher.group(1)),
          Integer.parseInt(matcher.group(2)),
          matcher.group(3)));
    }
    for (int i = 0; i < components.size(); i++) {
      ComponentLoc a = components.get(i);
      for (int j = i + 1; j < components.size(); j++) {
        ComponentLoc b = components.get(j);
        if (a.x == b.x && a.y == b.y) {
          issues.add("Components overlap exactly at (" + a.x + "," + a.y + "): "
              + a.name + " and " + b.name);
        }
      }
    }
  }

  private static void inspectWires(String xml, List<String> issues) {
    Matcher matcher = WIRE.matcher(xml);
    while (matcher.find()) {
      int x1 = Integer.parseInt(matcher.group(1));
      int y1 = Integer.parseInt(matcher.group(2));
      int x2 = Integer.parseInt(matcher.group(3));
      int y2 = Integer.parseInt(matcher.group(4));
      if (x1 != x2 && y1 != y2) {
        issues.add("Wire is diagonal: (" + x1 + "," + y1 + ") -> (" + x2 + "," + y2 + ")");
      }
    }
  }

  private record ComponentLoc(int x, int y, String name) {}

  public record Report(String fileName, List<String> issues) {
    public boolean clean() {
      return issues.isEmpty();
    }

    public String toHumanString() {
      if (clean()) {
        return "[OK] " + fileName + " static layout checks passed";
      }
      return "[FAIL] " + fileName + "\n - " + String.join("\n - ", issues);
    }
  }
}
