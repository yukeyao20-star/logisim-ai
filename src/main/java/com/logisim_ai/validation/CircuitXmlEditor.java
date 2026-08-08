package com.logisim_ai.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Package-private helpers for reading and rewriting Logisim .circ XML fragments. */
final class CircuitXmlEditor {
  private static final Pattern MAIN = Pattern.compile("<main\\s+name=\"([^\"]+)\"\\s*/>");
  private static final Pattern CIRCUIT =
      Pattern.compile("<circuit\\s+name=\"([^\"]+)\"[^>]*>(.*?)</circuit>", Pattern.DOTALL);
  private static final Pattern COMPONENT =
      Pattern.compile("<comp\\b([^>]*?)(?:/>|>((?:(?!</comp>).)*?)</comp>)", Pattern.DOTALL);
  private static final Pattern WIRE =
      Pattern.compile("<wire\\s+from=\"\\((-?\\d+),(-?\\d+)\\)\"\\s+to=\"\\((-?\\d+),(-?\\d+)\\)\"\\s*/>");
  private static final Pattern ATTR_LOC = Pattern.compile("loc=\"\\((-?\\d+),(-?\\d+)\\)\"");
  private static final Pattern ATTR_NAME = Pattern.compile("name=\"([^\"]+)\"");
  private static final Pattern ATTR_LABEL = Pattern.compile("<a\\s+name=\"label\"\\s+val=\"([^\"]+)\"");
  private static final Pattern ATTR_FACING = Pattern.compile("<a\\s+name=\"facing\"\\s+val=\"([^\"]+)\"");
  private static final Pattern ATTR_TYPE = Pattern.compile("<a\\s+name=\"type\"\\s+val=\"([^\"]+)\"");

  private CircuitXmlEditor() {}

  static String mainCircuitName(String xml) {
    Matcher matcher = MAIN.matcher(xml);
    return matcher.find() ? matcher.group(1) : "";
  }

  static List<CircuitBlock> parseCircuitBlocks(String xml) {
    List<CircuitBlock> result = new ArrayList<>();
    Matcher matcher = CIRCUIT.matcher(xml);
    while (matcher.find()) {
      result.add(new CircuitBlock(matcher.start(), matcher.end(), matcher.start(2), matcher.end(2),
          matcher.group(1), matcher.group(2)));
    }
    return result;
  }

  static List<ParsedComponent> parseComponentBlocks(String xml) {
    List<ParsedComponent> result = new ArrayList<>();
    Matcher matcher = COMPONENT.matcher(xml);
    while (matcher.find()) {
      String attrs = matcher.group(1);
      String body = matcher.group(2) == null ? "" : matcher.group(2);
      Matcher locM = ATTR_LOC.matcher(attrs);
      Matcher nameM = ATTR_NAME.matcher(attrs);
      if (!locM.find() || !nameM.find()) {
        continue;
      }
      String label = "";
      Matcher labelM = ATTR_LABEL.matcher(body);
      if (labelM.find()) {
        label = labelM.group(1);
      }
      String facing = null;
      Matcher facingM = ATTR_FACING.matcher(body);
      if (facingM.find()) {
        facing = facingM.group(1);
      }
      String type = null;
      Matcher typeM = ATTR_TYPE.matcher(body);
      if (typeM.find()) {
        type = typeM.group(1);
      }
      result.add(new ParsedComponent(
          matcher.start(),
          matcher.end(),
          matcher.group(),
          attrs,
          body,
          Integer.parseInt(locM.group(1)),
          Integer.parseInt(locM.group(2)),
          nameM.group(1),
          label,
          facing,
          type));
    }
    return result;
  }

  static List<ParsedWire> parseWireBlocks(String xml) {
    List<ParsedWire> result = new ArrayList<>();
    Matcher matcher = WIRE.matcher(xml);
    while (matcher.find()) {
      result.add(new ParsedWire(
          matcher.start(),
          matcher.end(),
          matcher.group(),
          Integer.parseInt(matcher.group(1)),
          Integer.parseInt(matcher.group(2)),
          Integer.parseInt(matcher.group(3)),
          Integer.parseInt(matcher.group(4))));
    }
    return result;
  }

  static String rewriteCircuitBodies(String xml, CircuitBodyRepairer repairer) {
    StringBuilder out = new StringBuilder();
    int last = 0;
    for (CircuitBlock circuit : parseCircuitBlocks(xml)) {
      BodyRepair repair = repairer.repair(circuit.name(), circuit.body());
      out.append(xml, last, circuit.bodyStart());
      out.append(repair.body());
      last = circuit.bodyEnd();
    }
    out.append(xml.substring(last));
    return out.toString();
  }

  static String rewriteMainCircuitBody(String xml, BodyRepairer repairer) {
    String mainName = mainCircuitName(xml);
    if (mainName.isEmpty()) {
      return xml;
    }
    for (CircuitBlock circuit : parseCircuitBlocks(xml)) {
      if (!mainName.equals(circuit.name())) {
        continue;
      }
      BodyRepair repair = repairer.repair(circuit.body());
      if (repair.changeCount() == 0) {
        return xml;
      }
      return xml.substring(0, circuit.bodyStart()) + repair.body() + xml.substring(circuit.bodyEnd());
    }
    return xml;
  }

  record CircuitBlock(int start, int end, int bodyStart, int bodyEnd, String name, String body) {}

  record ParsedComponent(
      int start,
      int end,
      String xml,
      String attrs,
      String body,
      int x,
      int y,
      String name,
      String label,
      String facing,
      String type) {}

  record ParsedWire(int start, int end, String xml, int x1, int y1, int x2, int y2) {
    boolean isDiagonal() {
      return x1 != x2 && y1 != y2;
    }

    boolean isVertical() {
      return x1 == x2;
    }

    boolean touches(int x, int y) {
      return (x1 == x && y1 == y) || (x2 == x && y2 == y);
    }

    boolean touchesVertically(int x, int y) {
      return (x1 == x && y1 == y && y2 != y) || (x2 == x && y2 == y && y1 != y);
    }
  }

  record BodyRepair(String body, int changeCount) {}

  @FunctionalInterface
  interface BodyRepairer {
    BodyRepair repair(String body);
  }

  @FunctionalInterface
  interface CircuitBodyRepairer {
    BodyRepair repair(String circuitName, String body);
  }
}
