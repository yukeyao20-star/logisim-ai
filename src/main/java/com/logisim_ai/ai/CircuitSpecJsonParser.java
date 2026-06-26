package com.logisim_ai.ai;

import com.logisim_ai.compiler.LogisimCompiler;
import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.LayoutHint;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.PortSide;
import com.logisim_ai.validation.CircuitSemanticValidator;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses a JSON representation of a CircuitSpec and compiles it to a .circ file.
 *
 * <p>JSON format:
 * <pre>{@code
 * {
 *   "name": "my_circuit",
 *   "components": [
 *     {"id": "xor1", "library": "Gates", "factory": "XOR Gate", "bitWidth": 1, "label": ""}
 *   ],
 *   "externalPorts": [
 *     {"id": "a", "label": "A", "bitWidth": 1, "direction": "INPUT", "side": "LEFT",
 *      "targetComponentId": "xor1", "targetEndIndex": 1}
 *   ],
 *   "nets": [
 *     {"id": "net1", "endpoints": [
 *       {"componentId": "xor1", "endIndex": 0},
 *       {"componentId": "and1", "endIndex": 1}
 *     ]}
 *   ],
 *   "layoutHints": [
 *     {"componentId": "xor1", "rank": 0, "column": 0, "row": 0, "side": null}
 *   ]
 * }
 * }</pre>
 *
 * <p>This class provides a JSON→CircuitSpec bridge that an LLM can target.
 * It uses simple string parsing without external JSON libraries to avoid
 * adding dependencies. For production use, consider integrating Jackson or Gson.
 */
public final class CircuitSpecJsonParser {
  private CircuitSpecJsonParser() {}

  /**
   * Parse a JSON string into a CircuitSpec and compile it.
   *
   * @param json the JSON string representing a CircuitSpec
   * @param outputFile the output .circ file
   * @throws Exception on parse or compile errors
   */
  public static void parseAndCompile(String json, File outputFile) throws Exception {
    CircuitSpec spec = parse(json);
    CircuitSemanticValidator.requireValid(spec);
    if (spec.isNetlist()) {
      LogisimCompiler.compileNetlist(spec, outputFile);
    } else {
      LogisimCompiler.compileSingleComponent(spec, outputFile);
    }
  }

  /**
   * Parse a JSON string into a CircuitSpec object.
   *
   * <p>Uses a simple hand-rolled parser. Not suitable for adversarial input,
   * but sufficient for LLM-generated JSON that follows the schema.
   */
  public static CircuitSpec parse(String json) {
    String name = extractString(json, "name");

    List<ComponentSpec> components = parseComponents(json);
    List<ExternalPort> ports = parsePorts(json);
    List<NetSpec> nets = parseNets(json);
    List<LayoutHint> hints = parseHints(json);

    return new CircuitSpec(name, components, ports, nets, hints);
  }

  // ---- Component parsing ----

  private static List<ComponentSpec> parseComponents(String json) {
    String arrayStr = extractArray(json, "components");
    if (arrayStr == null) return List.of();

    List<ComponentSpec> result = new ArrayList<>();
    for (String item : splitArrayItems(arrayStr)) {
      String id = extractString(item, "id");
      String library = extractString(item, "library");
      String factory = extractString(item, "factory");
      int bitWidth = extractInt(item, "bitWidth", 1);
      String label = extractStringOrDefault(item, "label", "");
      result.add(new ComponentSpec(id, library, factory, bitWidth, label));
    }
    return result;
  }

  // ---- Port parsing ----

  private static List<ExternalPort> parsePorts(String json) {
    String arrayStr = extractArray(json, "externalPorts");
    if (arrayStr == null) return List.of();

    List<ExternalPort> result = new ArrayList<>();
    for (String item : splitArrayItems(arrayStr)) {
      String id = extractString(item, "id");
      String label = extractString(item, "label");
      int bitWidth = extractInt(item, "bitWidth", 1);
      PortDirection dir = parseDirection(extractString(item, "direction"));
      PortSide side = parseSide(extractString(item, "side"));
      String targetId = extractString(item, "targetComponentId");
      int targetEnd = extractInt(item, "targetEndIndex", 0);
      result.add(new ExternalPort(id, label, bitWidth, dir, side, targetId, targetEnd));
    }
    return result;
  }

  // ---- Net parsing ----

  private static List<NetSpec> parseNets(String json) {
    String arrayStr = extractArray(json, "nets");
    if (arrayStr == null) return List.of();

    List<NetSpec> result = new ArrayList<>();
    for (String item : splitArrayItems(arrayStr)) {
      String id = extractString(item, "id");
      List<EndpointRef> endpoints = parseEndpoints(item);
      result.add(new NetSpec(id, endpoints));
    }
    return result;
  }

  private static List<EndpointRef> parseEndpoints(String json) {
    String arrayStr = extractArray(json, "endpoints");
    if (arrayStr == null) return List.of();

    List<EndpointRef> result = new ArrayList<>();
    for (String item : splitArrayItems(arrayStr)) {
      String compId = extractString(item, "componentId");
      int endIndex = extractInt(item, "endIndex", 0);
      result.add(new EndpointRef(compId, endIndex));
    }
    return result;
  }

  // ---- Layout hint parsing ----

  private static List<LayoutHint> parseHints(String json) {
    String arrayStr = extractArray(json, "layoutHints");
    if (arrayStr == null) return List.of();

    List<LayoutHint> result = new ArrayList<>();
    for (String item : splitArrayItems(arrayStr)) {
      String compId = extractString(item, "componentId");
      int rank = extractInt(item, "rank", 0);
      int column = extractInt(item, "column", 0);
      int row = extractInt(item, "row", 0);
      String sideStr = extractStringOrNull(item, "side");
      PortSide side = sideStr != null ? parseSide(sideStr) : null;
      result.add(new LayoutHint(compId, rank, column, row, side));
    }
    return result;
  }

  // ---- Simple JSON extraction helpers ----

  private static String extractString(String json, String key) {
    String pattern = "\"" + key + "\"";
    int idx = json.indexOf(pattern);
    if (idx < 0) throw new IllegalArgumentException("Missing key: " + key);
    // Find the colon after the key
    int colon = json.indexOf(':', idx + pattern.length());
    if (colon < 0) throw new IllegalArgumentException("Malformed JSON at key: " + key);
    // Find the opening quote of the value
    int openQuote = json.indexOf('"', colon + 1);
    if (openQuote < 0) throw new IllegalArgumentException("Missing string value for key: " + key);
    int closeQuote = json.indexOf('"', openQuote + 1);
    if (closeQuote < 0) throw new IllegalArgumentException("Unclosed string for key: " + key);
    return json.substring(openQuote + 1, closeQuote);
  }

  private static String extractStringOrDefault(String json, String key, String defaultVal) {
    try {
      return extractString(json, key);
    } catch (IllegalArgumentException e) {
      return defaultVal;
    }
  }

  private static String extractStringOrNull(String json, String key) {
    try {
      return extractString(json, key);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  private static int extractInt(String json, String key, int defaultVal) {
    String pattern = "\"" + key + "\"";
    int idx = json.indexOf(pattern);
    if (idx < 0) return defaultVal;
    int colon = json.indexOf(':', idx + pattern.length());
    if (colon < 0) return defaultVal;
    // Skip whitespace
    int start = colon + 1;
    while (start < json.length() && Character.isWhitespace(json.charAt(start))) start++;
    if (start >= json.length()) return defaultVal;
    // Find end of number
    int end = start;
    while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) end++;
    if (end == start) return defaultVal;
    try {
      return Integer.parseInt(json.substring(start, end));
    } catch (NumberFormatException e) {
      return defaultVal;
    }
  }

  private static String extractArray(String json, String key) {
    String pattern = "\"" + key + "\"";
    int idx = json.indexOf(pattern);
    if (idx < 0) return null;
    int colon = json.indexOf(':', idx + pattern.length());
    if (colon < 0) return null;
    int openBracket = json.indexOf('[', colon + 1);
    if (openBracket < 0) return null;
    // Find matching close bracket (respecting nesting)
    int depth = 1;
    int i = openBracket + 1;
    while (i < json.length() && depth > 0) {
      char c = json.charAt(i);
      if (c == '[') depth++;
      else if (c == ']') depth--;
      else if (c == '"') {
        // Skip string content
        i++;
        while (i < json.length() && json.charAt(i) != '"') {
          if (json.charAt(i) == '\\') i++; // skip escaped char
          i++;
        }
      }
      i++;
    }
    return json.substring(openBracket + 1, i - 1);
  }

  private static List<String> splitArrayItems(String arrayContent) {
    List<String> items = new ArrayList<>();
    if (arrayContent == null || arrayContent.isBlank()) return items;

    int depth = 0;
    int start = -1;
    boolean inString = false;
    for (int i = 0; i < arrayContent.length(); i++) {
      char c = arrayContent.charAt(i);
      if (c == '"' && (i == 0 || arrayContent.charAt(i - 1) != '\\')) {
        inString = !inString;
      } else if (!inString) {
        if (c == '{') {
          if (depth == 0) start = i;
          depth++;
        } else if (c == '}') {
          depth--;
          if (depth == 0 && start >= 0) {
            items.add(arrayContent.substring(start, i + 1));
            start = -1;
          }
        }
      }
    }
    return items;
  }

  private static PortDirection parseDirection(String s) {
    if ("OUTPUT".equalsIgnoreCase(s)) return PortDirection.OUTPUT;
    return PortDirection.INPUT;
  }

  private static PortSide parseSide(String s) {
    if (s == null) return PortSide.LEFT;
    return switch (s.toUpperCase()) {
      case "RIGHT" -> PortSide.RIGHT;
      case "TOP" -> PortSide.TOP;
      case "BOTTOM" -> PortSide.BOTTOM;
      default -> PortSide.LEFT;
    };
  }
}
