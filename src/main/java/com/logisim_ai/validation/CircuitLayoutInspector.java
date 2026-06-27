package com.logisim_ai.validation;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Static checks for generated .circ files before they are shown as deliverables.
 *
 * <p>Checks:
 * 1. No unresolved toolbar lib entries.
 * 2. No overlapping components.
 * 3. No diagonal wires.
 * 4. Input Pin must be left of its external Tunnel.
 * 5. Output Pin must be right of its external Tunnel.
 * 6. Pin-to-Tunnel distance must be 40–180 px.
 * 7. Input Pin facing must be east (or default = east).
 * 8. Output Pin facing must be west.
 * 9. External Tunnel facing must face the Pin.
 */
public final class CircuitLayoutInspector {
  private static final int SMALL_COMPONENT_CLEARANCE_X = 60;
  private static final int SMALL_COMPONENT_CLEARANCE_Y = 40;

  private static final int MIN_PIN_TUNNEL_DIST = 40;
  private static final int MAX_PIN_TUNNEL_DIST = 180;

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

    // Separate Pins and Tunnels by label
    Map<String, List<ParsedComp>> pinsByLabel = new HashMap<>();
    Map<String, List<ParsedComp>> tunnelsByLabel = new HashMap<>();

    for (ParsedComp c : all) {
      if ("Pin".equals(c.name)) {
        pinsByLabel.computeIfAbsent(c.label, k -> new ArrayList<>()).add(c);
      } else if ("Tunnel".equals(c.name)) {
        tunnelsByLabel.computeIfAbsent(c.label, k -> new ArrayList<>()).add(c);
      }
    }

    // Check each Pin against its matching Tunnel(s)
    for (Map.Entry<String, List<ParsedComp>> entry : pinsByLabel.entrySet()) {
      String label = entry.getKey();
      List<ParsedComp> pins = entry.getValue();
      List<ParsedComp> tunnels = tunnelsByLabel.getOrDefault(label, List.of());

      for (ParsedComp pin : pins) {
        if (tunnels.isEmpty()) continue;

        // Find the closest Tunnel on the same y (or closest y if none match)
        ParsedComp closestTunnel = null;
        int bestDist = Integer.MAX_VALUE;
        for (ParsedComp t : tunnels) {
          int dist = Math.abs(t.x - pin.x);
          // Prefer same-y tunnels
          if (t.y == pin.y && (closestTunnel == null || closestTunnel.y != pin.y)) {
            closestTunnel = t;
            bestDist = dist;
          } else if (t.y == pin.y && dist < bestDist) {
            closestTunnel = t;
            bestDist = dist;
          } else if (closestTunnel == null || (closestTunnel.y != pin.y && dist < bestDist)) {
            closestTunnel = t;
            bestDist = dist;
          }
        }

        if (closestTunnel == null) continue;

        boolean isOutput = "output".equals(pin.type);
        int dist = Math.abs(closestTunnel.x - pin.x);

        // A1/A2: Input Pin must be left of its Tunnel
        if (!isOutput) {
          if (pin.x >= closestTunnel.x) {
            issues.add("Input pin " + label + " at (" + pin.x + "," + pin.y
                + ") should be left of its external tunnel at (" + closestTunnel.x + "," + closestTunnel.y + ")");
          }
          // Distance check
          if (dist < MIN_PIN_TUNNEL_DIST) {
            issues.add("Pin " + label + " to tunnel distance is too small: " + dist);
          } else if (dist > MAX_PIN_TUNNEL_DIST) {
            issues.add("Pin " + label + " to tunnel distance is too large: " + dist);
          }
          // Facing check: input Pin should face east (or default = east)
          String pinFacing = pin.facing != null ? pin.facing : "east";
          if (!"east".equals(pinFacing)) {
            issues.add("Input pin " + label + " facing should be east, got: " + pinFacing);
          }
          // Tunnel should face the Pin (west)
          String tunnelFacing = closestTunnel.facing != null ? closestTunnel.facing : "west";
          if (!"west".equals(tunnelFacing)) {
            issues.add("External tunnel for " + label + " facing should be west, got: " + tunnelFacing);
          }
        }

        // A3: Output Pin must be right of its Tunnel
        if (isOutput) {
          if (pin.x <= closestTunnel.x) {
            issues.add("Output pin " + label + " at (" + pin.x + "," + pin.y
                + ") should be right of its external tunnel at (" + closestTunnel.x + "," + closestTunnel.y + ")");
          }
          // Distance check
          if (dist < MIN_PIN_TUNNEL_DIST) {
            issues.add("Pin " + label + " to tunnel distance is too small: " + dist);
          } else if (dist > MAX_PIN_TUNNEL_DIST) {
            issues.add("Pin " + label + " to tunnel distance is too large: " + dist);
          }
          // Facing check: output Pin should face west
          String pinFacing = pin.facing != null ? pin.facing : "west";
          if (!"west".equals(pinFacing)) {
            issues.add("Output pin " + label + " facing should be west, got: " + pinFacing);
          }
          // Tunnel should face the Pin (east)
          String tunnelFacing = closestTunnel.facing != null ? closestTunnel.facing : "east";
          if (!"east".equals(tunnelFacing)) {
            issues.add("External tunnel for " + label + " facing should be east, got: " + tunnelFacing);
          }
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
