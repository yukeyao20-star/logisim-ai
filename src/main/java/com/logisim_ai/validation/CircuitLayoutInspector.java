package com.logisim_ai.validation;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Static checks for generated .circ files before they are shown as deliverables.
 *
 * <p>Checks:
 * 1. No unresolved toolbar lib entries.
 * 2. No overlapping components.
 * 3. No diagonal wires.
 * 4. Input Pin facing must be east (or default = east).
 * 5. Output Pin facing must be west.
 * 6. No Tunnel should be placed immediately beside an external Pin.
 */
public final class CircuitLayoutInspector {
  private static final int SMALL_COMPONENT_CLEARANCE_X = 60;
  private static final int SMALL_COMPONENT_CLEARANCE_Y = 40;

  private static final int NEAR_PIN_TUNNEL_DIST = 150;

  // Parse <comp ...> blocks
  private static final Pattern COMPONENT =
      Pattern.compile("<comp\\b([^>]*?)(?:/>|>((?:(?!</comp>).)*?)</comp>)", Pattern.DOTALL);

  // Parse <wire .../> blocks
  private static final Pattern WIRE =
      Pattern.compile("<wire\\s+from=\"\\((-?\\d+),(-?\\d+)\\)\"\\s+to=\"\\((-?\\d+),(-?\\d+)\\)\"\\s*/>");

  // Attribute patterns within a comp block
  private static final Pattern ATTR_LOC = Pattern.compile("loc=\"\\((-?\\d+),(-?\\d+)\\)\"");
  private static final Pattern ATTR_NAME = Pattern.compile("name=\"([^\"]+)\"");
  private static final Pattern ATTR_LABEL = Pattern.compile("<a\\s+name=\"label\"\\s+val=\"([^\"]+)\"");
  private static final Pattern ATTR_FACING = Pattern.compile("<a\\s+name=\"facing\"\\s+val=\"([^\"]+)\"");
  private static final Pattern ATTR_TYPE = Pattern.compile("<a\\s+name=\"type\"\\s+val=\"([^\"]+)\"");

  private CircuitLayoutInspector() {}

  public static Report inspect(File circFile) throws java.io.IOException {
    String xml = Files.readString(circFile.toPath(), StandardCharsets.UTF_8);
    List<String> issues = new ArrayList<>();
    if (xml.contains("lib=\"toolbar\"")) {
      issues.add("Toolbar contains unresolved lib=\"toolbar\" entries.");
    }
    inspectComponents(xml, issues);
    inspectWires(xml, issues);
    inspectPinTunnelPositions(xml, issues);
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

  // ---- Component overlap checks ----

  private static void inspectComponents(String xml, List<String> issues) {
    List<ParsedComp> components = parseComponents(xml);
    for (int i = 0; i < components.size(); i++) {
      ParsedComp a = components.get(i);
      for (int j = i + 1; j < components.size(); j++) {
        ParsedComp b = components.get(j);
        if (a.x == b.x && a.y == b.y) {
          issues.add("Components overlap exactly at (" + a.x + "," + a.y + "): "
              + a.name + " and " + b.name);
        }
        if (isSmallVisualComponent(a) && isSmallVisualComponent(b)
            && Math.abs(a.x - b.x) < SMALL_COMPONENT_CLEARANCE_X
            && Math.abs(a.y - b.y) < SMALL_COMPONENT_CLEARANCE_Y) {
          issues.add("Small components are too close at (" + a.x + "," + a.y + ") and ("
              + b.x + "," + b.y + "): " + a.name + " and " + b.name);
        }
      }
    }
  }

  private static boolean isSmallVisualComponent(ParsedComp component) {
    return component.name.equals("Pin") || component.name.equals("Tunnel");
  }

  // ---- Wire checks ----

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

  // ---- Pin/Tunnel position and facing checks ----

  private static void inspectPinTunnelPositions(String xml, List<String> issues) {
    List<ParsedComp> all = parseComponents(xml);
    List<ParsedComp> pins = new ArrayList<>();
    List<ParsedComp> tunnels = new ArrayList<>();

    for (ParsedComp c : all) {
      if ("Pin".equals(c.name)) {
        pins.add(c);
      } else if ("Tunnel".equals(c.name)) {
        tunnels.add(c);
      }
    }

    for (ParsedComp pin : pins) {
      boolean isOutput = "output".equals(pin.type);
      String expectedFacing = isOutput ? "west" : "east";
      String actualFacing = pin.facing != null ? pin.facing : expectedFacing;
      if (!expectedFacing.equals(actualFacing)) {
        issues.add((isOutput ? "Output" : "Input") + " pin " + pin.label
            + " facing should be " + expectedFacing + ", got: " + actualFacing);
      }

      for (ParsedComp tunnel : tunnels) {
        int dx = Math.abs(tunnel.x - pin.x);
        if (tunnel.y == pin.y && dx > 0 && dx < NEAR_PIN_TUNNEL_DIST) {
          issues.add("Tunnel " + tunnel.label + " is too close to external pin " + pin.label
              + " on the same row: (" + tunnel.x + "," + tunnel.y + ") vs ("
              + pin.x + "," + pin.y + ")");
        }
      }
    }
  }

  // ---- XML Parsing ----

  private static List<ParsedComp> parseComponents(String xml) {
    List<ParsedComp> result = new ArrayList<>();
    Matcher matcher = COMPONENT.matcher(xml);
    while (matcher.find()) {
      String attrs = matcher.group(1);
      String body = matcher.group(2) == null ? "" : matcher.group(2);

      // Parse location
      Matcher locM = ATTR_LOC.matcher(attrs);
      if (!locM.find()) continue;
      int x = Integer.parseInt(locM.group(1));
      int y = Integer.parseInt(locM.group(2));

      // Parse name
      Matcher nameM = ATTR_NAME.matcher(attrs);
      if (!nameM.find()) continue;
      String name = nameM.group(1);

      // Parse label from body
      String label = "";
      Matcher labelM = ATTR_LABEL.matcher(body);
      if (labelM.find()) {
        label = labelM.group(1);
      }

      // Parse facing from body (may be absent = default)
      String facing = null;
      Matcher facingM = ATTR_FACING.matcher(body);
      if (facingM.find()) {
        facing = facingM.group(1);
      }

      // Parse type from body (Pin only)
      String type = null;
      Matcher typeM = ATTR_TYPE.matcher(body);
      if (typeM.find()) {
        type = typeM.group(1);
      }

      result.add(new ParsedComp(x, y, name, label, facing, type));
    }
    return result;
  }

  /** Parsed component from .circ XML. */
  private record ParsedComp(int x, int y, String name, String label, String facing, String type) {}

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
