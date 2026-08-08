package com.logisim_ai.validation;

import com.logisim_ai.experiment.LogisimProjectSetup;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Executes low-risk layout repair plans against generated .circ artifacts. */
public final class LayoutRepairExecutor {
  private static final int PIN_TUNNEL_CLEARANCE = 150;
  private static final int SEARCH_STEP = 10;
  private static final int MAX_TUNNEL_LABEL_LENGTH = 4;
  private static final Pattern ATTR_FACING =
      Pattern.compile("<a\\s+name=\"facing\"\\s+val=\"([^\"]+)\"\\s*/>");

  private LayoutRepairExecutor() {}

  public static Report execute(File circFile, CircuitLayoutInspector.Report layoutReport)
      throws java.io.IOException {
    List<Result> results = new ArrayList<>();
    for (CircuitLayoutInspector.LayoutRepairPlan plan : layoutReport.repairPlans()) {
      results.add(executePlan(circFile, plan));
    }
    CircuitLayoutInspector.Report after = CircuitLayoutInspector.inspect(circFile);
    return new Report(circFile.getPath(), results, after);
  }

  private static Result executePlan(File circFile, CircuitLayoutInspector.LayoutRepairPlan plan)
      throws java.io.IOException {
    if ("regenerate_toolbar".equals(plan.action())) {
      LogisimProjectSetup.patchGeneratedProjectXml(circFile);
      return new Result(plan.action(), plan.targetType().name(), true, "toolbar regenerated");
    }
    if ("increase_pin_tunnel_clearance".equals(plan.action())) {
      int moved = increasePinTunnelClearance(circFile);
      return new Result(
          plan.action(),
          plan.targetType().name(),
          moved > 0,
          moved > 0 ? "moved " + moved + " tunnel(s)" : "no safe tunnel move found");
    }
    if ("fix_pin_facing".equals(plan.action())) {
      int fixed = fixPinFacing(circFile);
      return new Result(
          plan.action(),
          plan.targetType().name(),
          fixed > 0,
          fixed > 0 ? "fixed " + fixed + " pin facing attribute(s)" : "no pin facing change needed");
    }
    if ("shorten_tunnel_labels".equals(plan.action())) {
      LabelShorteningReport report = shortenTunnelLabels(circFile);
      return new Result(
          plan.action(),
          plan.targetType().name(),
          report.changedGroups() > 0,
          report.changedGroups() > 0
              ? "shortened " + report.changedGroups() + " tunnel label group(s); map="
                  + report.markdownPath()
              : "no long tunnel labels found");
    }
    if ("reroute_wires".equals(plan.action())) {
      int rerouted = rerouteBoundaryVerticalWire(circFile, plan.parameters());
      return new Result(
          plan.action(),
          plan.targetType().name(),
          rerouted > 0,
          rerouted > 0
              ? "rerouted " + rerouted + " boundary vertical wire(s)"
              : "no safe boundary vertical wire reroute found");
    }
    return new Result(plan.action(), plan.targetType().name(), false, "repair action not implemented yet");
  }

  private static int rerouteBoundaryVerticalWire(File circFile, String parameters) throws java.io.IOException {
    Map<String, String> detail = parseDetail(parameters);
    if (!"boundary_vertical".equals(detail.get("kind"))) {
      return 0;
    }
    int x1 = parseIntDetail(detail, "x1", Integer.MIN_VALUE);
    int y1 = parseIntDetail(detail, "y1", Integer.MIN_VALUE);
    int x2 = parseIntDetail(detail, "x2", Integer.MIN_VALUE);
    int y2 = parseIntDetail(detail, "y2", Integer.MIN_VALUE);
    if (x1 == Integer.MIN_VALUE || y1 == Integer.MIN_VALUE
        || x2 == Integer.MIN_VALUE || y2 == Integer.MIN_VALUE || x1 != x2 || y1 == y2) {
      return 0;
    }
    BodyRewrite rewrite = rewriteMainCircuitBody(circFile, body -> {
      BoundaryWireReroute reroute = rerouteBoundaryVerticalWireInBody(body, x1, y1, x2, y2);
      return new CircuitXmlEditor.BodyRepair(reroute.body(), reroute.changeCount());
    });
    return rewrite.changeCount();
  }

  private static BoundaryWireReroute rerouteBoundaryVerticalWireInBody(
      String circuitBody, int x1, int y1, int x2, int y2) {
    for (CircuitXmlEditor.ParsedComponent component : CircuitXmlEditor.parseComponentBlocks(circuitBody)) {
      if ((component.x() == x1 && component.y() == y1) || (component.x() == x2 && component.y() == y2)) {
        return new BoundaryWireReroute(circuitBody, 0);
      }
    }
    CircuitXmlEditor.ParsedWire target = null;
    for (CircuitXmlEditor.ParsedWire wire : CircuitXmlEditor.parseWireBlocks(circuitBody)) {
      boolean sameDirection = wire.x1() == x1 && wire.y1() == y1 && wire.x2() == x2 && wire.y2() == y2;
      boolean reverseDirection = wire.x1() == x2 && wire.y1() == y2 && wire.x2() == x1 && wire.y2() == y1;
      if (sameDirection || reverseDirection) {
        target = wire;
        break;
      }
    }
    if (target == null) {
      return new BoundaryWireReroute(circuitBody, 0);
    }
    String label = nextRerouteTunnelLabel(circuitBody);
    String tunnelA = tunnelXml(x1, y1, label);
    String tunnelB = tunnelXml(x2, y2, label);
    String repaired = circuitBody.substring(0, target.start())
        + tunnelA
        + tunnelB
        + circuitBody.substring(target.end());
    return new BoundaryWireReroute(repaired, 1);
  }

  private static String tunnelXml(int x, int y, String label) {
    return "    <comp lib=\"1\" loc=\"(" + x + "," + y + ")\" name=\"Tunnel\">\n"
        + "      <a name=\"label\" val=\"" + label + "\"/>\n"
        + "    </comp>\n";
  }

  private static String nextRerouteTunnelLabel(String circuitBody) {
    Set<String> labels = new HashSet<>();
    for (CircuitXmlEditor.ParsedComponent component : CircuitXmlEditor.parseComponentBlocks(circuitBody)) {
      if ("Tunnel".equals(component.name())) {
        labels.add(component.label());
      }
    }
    for (int i = 1; i < 100; i++) {
      String label = String.format("R%02d", i);
      if (!labels.contains(label)) {
        return label;
      }
    }
    return "R99";
  }

  private static Map<String, String> parseDetail(String parameters) {
    Map<String, String> result = new HashMap<>();
    if (parameters == null || parameters.isBlank()) {
      return result;
    }
    for (String part : parameters.split(";")) {
      int eq = part.indexOf('=');
      if (eq <= 0 || eq + 1 >= part.length()) {
        continue;
      }
      result.put(part.substring(0, eq), part.substring(eq + 1));
    }
    return result;
  }

  private static int parseIntDetail(Map<String, String> detail, String key, int fallback) {
    try {
      return Integer.parseInt(detail.getOrDefault(key, ""));
    } catch (NumberFormatException e) {
      return fallback;
    }
  }

  private static LabelShorteningReport shortenTunnelLabels(File circFile) throws java.io.IOException {
    String xml = Files.readString(circFile.toPath(), StandardCharsets.UTF_8);
    List<LabelMapping> mappings = new ArrayList<>();
    String repaired = CircuitXmlEditor.rewriteCircuitBodies(xml, (circuitName, body) -> {
      TunnelLabelPlan plan = planTunnelLabelShortening(circuitName, body);
      mappings.addAll(plan.mappings());
      return new CircuitXmlEditor.BodyRepair(applyTunnelLabelShortening(body, plan), plan.mappings().size());
    });
    if (mappings.isEmpty()) {
      return new LabelShorteningReport(0, "");
    }
    Files.writeString(circFile.toPath(), repaired, StandardCharsets.UTF_8);
    Path mdPath = tunnelLabelMapMarkdownPath(circFile);
    Path jsonPath = tunnelLabelMapJsonPath(circFile);
    Files.createDirectories(mdPath.getParent());
    Files.writeString(mdPath, tunnelLabelMapMarkdown(circFile, mappings), StandardCharsets.UTF_8);
    Files.writeString(jsonPath, tunnelLabelMapJson(circFile, mappings), StandardCharsets.UTF_8);
    return new LabelShorteningReport(mappings.size(), mdPath.toString());
  }

  private static int fixPinFacing(File circFile) throws java.io.IOException {
    BodyRewrite rewrite = rewriteMainCircuitBody(circFile, body -> {
      PinFacingRepair repaired = fixPinFacingInBody(body);
      return new CircuitXmlEditor.BodyRepair(repaired.body(), repaired.fixedCount());
    });
    return rewrite.changeCount();
  }

  private static PinFacingRepair fixPinFacingInBody(String circuitBody) {
    String repaired = circuitBody;
    int fixed = 0;
    List<ComponentEdit> edits = new ArrayList<>();
    for (CircuitXmlEditor.ParsedComponent component : CircuitXmlEditor.parseComponentBlocks(circuitBody)) {
      if (!"Pin".equals(component.name())) {
        continue;
      }
      String expectedFacing = "output".equals(component.type()) ? "west" : "east";
      String componentXml = component.xml();
      Matcher facingM = ATTR_FACING.matcher(componentXml);
      String updated;
      if (facingM.find()) {
        if (expectedFacing.equals(facingM.group(1))) {
          continue;
        }
        updated = facingM.replaceFirst("<a name=\"facing\" val=\"" + expectedFacing + "\"/>");
      } else if (componentXml.endsWith("/>")) {
        updated = componentXml.substring(0, componentXml.length() - 2)
            + ">\n      <a name=\"facing\" val=\"" + expectedFacing + "\"/>\n    </comp>";
      } else {
        updated = componentXml.replaceFirst(
            "\n\\s*</comp>$",
            "\n      <a name=\"facing\" val=\"" + expectedFacing + "\"/>\n    </comp>");
      }
      edits.add(new ComponentEdit(component.start(), component.end(), updated));
      fixed++;
    }

    edits.sort(Comparator.comparingInt(ComponentEdit::start).reversed());
    for (ComponentEdit edit : edits) {
      repaired = repaired.substring(0, edit.start()) + edit.replacement() + repaired.substring(edit.end());
    }
    return new PinFacingRepair(repaired, fixed);
  }

  private static int increasePinTunnelClearance(File circFile) throws java.io.IOException {
    BodyRewrite rewrite = rewriteMainCircuitBody(circFile, body -> {
      RepairPlan plan = planTunnelMoves(body);
      if (plan.movesByStart().isEmpty()) {
        return new CircuitXmlEditor.BodyRepair(body, 0);
      }
      return new CircuitXmlEditor.BodyRepair(applyTunnelMoves(body, plan), plan.movesByStart().size());
    });
    return rewrite.changeCount();
  }

  private static BodyRewrite rewriteMainCircuitBody(File circFile, MainBodyRepairer repairer)
      throws java.io.IOException {
    String xml = Files.readString(circFile.toPath(), StandardCharsets.UTF_8);
    final int[] changes = {0};
    String repairedXml = CircuitXmlEditor.rewriteMainCircuitBody(xml, body -> {
      CircuitXmlEditor.BodyRepair repair = repairer.repair(body);
      changes[0] = repair.changeCount();
      return repair;
    });
    if (changes[0] > 0) {
      Files.writeString(circFile.toPath(), repairedXml, StandardCharsets.UTF_8);
    }
    return new BodyRewrite(changes[0]);
  }

  private static TunnelLabelPlan planTunnelLabelShortening(String circuitName, String circuitBody) {
    List<CircuitXmlEditor.ParsedComponent> components = CircuitXmlEditor.parseComponentBlocks(circuitBody);
    Set<String> reserved = new HashSet<>();
    for (CircuitXmlEditor.ParsedComponent component : components) {
      if ("Tunnel".equals(component.name()) && !component.label().isEmpty()
          && component.label().length() <= MAX_TUNNEL_LABEL_LENGTH) {
        reserved.add(component.label());
      }
    }

    Map<String, String> byOriginalLabel = new LinkedHashMap<>();
    List<LabelMapping> mappings = new ArrayList<>();
    int index = 1;
    for (CircuitXmlEditor.ParsedComponent component : components) {
      if (!"Tunnel".equals(component.name()) || component.label().length() <= MAX_TUNNEL_LABEL_LENGTH) {
        continue;
      }
      String shortLabel = byOriginalLabel.get(component.label());
      if (shortLabel == null) {
        shortLabel = chooseShortTunnelLabel(component.label(), reserved, index);
        while (reserved.contains(shortLabel)) {
          index++;
          shortLabel = generatedTunnelLabel(index);
        }
        index++;
        byOriginalLabel.put(component.label(), shortLabel);
        reserved.add(shortLabel);
        mappings.add(new LabelMapping(circuitName, component.label(), shortLabel));
      }
    }
    return new TunnelLabelPlan(byOriginalLabel, mappings);
  }

  private static String chooseShortTunnelLabel(String label, Set<String> reserved, int index) {
    String candidate = semanticShortLabel(label);
    if (!candidate.isEmpty() && candidate.length() <= MAX_TUNNEL_LABEL_LENGTH && !reserved.contains(candidate)) {
      return candidate;
    }
    return generatedTunnelLabel(index);
  }

  private static String semanticShortLabel(String label) {
    String normalized = label;
    if (normalized.startsWith("net_")) {
      normalized = normalized.substring("net_".length());
    }
    if (normalized.isEmpty()) {
      return "";
    }
    if (normalized.contains("sum")) {
      return "SUM";
    }
    if (normalized.contains("cout") || normalized.contains("carry")) {
      return "Cout";
    }
    if (normalized.contains("cin")) {
      return "Cin";
    }
    if (normalized.matches("[a-zA-Z][a-zA-Z0-9]{0,3}")) {
      return normalized.length() == 1 ? normalized.toUpperCase() : capitalize(normalized);
    }
    return "";
  }

  private static String capitalize(String text) {
    if (text.isEmpty()) {
      return text;
    }
    return text.substring(0, 1).toUpperCase() + text.substring(1);
  }

  private static String generatedTunnelLabel(int index) {
    return index < 100 ? String.format("T%02d", index) : "T" + index;
  }

  private static String applyTunnelLabelShortening(String circuitBody, TunnelLabelPlan plan) {
    if (plan.labelsByOriginal().isEmpty()) {
      return circuitBody;
    }
    String repaired = circuitBody;
    List<ComponentEdit> edits = new ArrayList<>();
    for (CircuitXmlEditor.ParsedComponent component : CircuitXmlEditor.parseComponentBlocks(circuitBody)) {
      if (!"Tunnel".equals(component.name())) {
        continue;
      }
      String shortLabel = plan.labelsByOriginal().get(component.label());
      if (shortLabel == null) {
        continue;
      }
      String updated = component.xml().replaceFirst(
          "(<a\\s+name=\"label\"\\s+val=\")" + Pattern.quote(component.label()) + "\"",
          "$1" + Matcher.quoteReplacement(shortLabel) + "\"");
      edits.add(new ComponentEdit(component.start(), component.end(), updated));
    }
    edits.sort(Comparator.comparingInt(ComponentEdit::start).reversed());
    for (ComponentEdit edit : edits) {
      repaired = repaired.substring(0, edit.start()) + edit.replacement() + repaired.substring(edit.end());
    }
    return repaired;
  }

  private static Path tunnelLabelMapMarkdownPath(File circFile) {
    return Path.of("generated", "reports", outputStem(circFile) + "_tunnel_label_map.md");
  }

  private static Path tunnelLabelMapJsonPath(File circFile) {
    return Path.of("generated", "reports", outputStem(circFile) + "_tunnel_label_map.json");
  }

  private static String outputStem(File outputFile) {
    String name = outputFile.getName();
    if (name.endsWith(".circ")) {
      return name.substring(0, name.length() - ".circ".length());
    }
    return name;
  }

  private static String tunnelLabelMapMarkdown(File circFile, List<LabelMapping> mappings) {
    StringBuilder out = new StringBuilder();
    out.append("# Tunnel label map for ").append(circFile.getPath()).append("\n\n");
    out.append("| Circuit | Original label | Short label |\n");
    out.append("| --- | --- | --- |\n");
    for (LabelMapping mapping : mappings) {
      out.append("| ").append(mapping.circuitName())
          .append(" | ").append(mapping.originalLabel())
          .append(" | ").append(mapping.shortLabel())
          .append(" |\n");
    }
    return out.toString();
  }

  private static String tunnelLabelMapJson(File circFile, List<LabelMapping> mappings) {
    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"circuitPath\": \"").append(escape(circFile.getPath())).append("\",\n");
    out.append("  \"mappings\": [\n");
    for (int i = 0; i < mappings.size(); i++) {
      LabelMapping mapping = mappings.get(i);
      out.append("    {\"circuit\": \"").append(escape(mapping.circuitName()))
          .append("\", \"originalLabel\": \"").append(escape(mapping.originalLabel()))
          .append("\", \"shortLabel\": \"").append(escape(mapping.shortLabel())).append("\"}");
      if (i + 1 < mappings.size()) {
        out.append(",");
      }
      out.append("\n");
    }
    out.append("  ]\n");
    out.append("}\n");
    return out.toString();
  }

  private static String escape(String text) {
    return text
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t");
  }

  private static RepairPlan planTunnelMoves(String circuitBody) {
    List<CircuitXmlEditor.ParsedComponent> components = CircuitXmlEditor.parseComponentBlocks(circuitBody);
    List<CircuitXmlEditor.ParsedComponent> pins = components.stream()
        .filter(component -> component.name().equals("Pin"))
        .toList();
    List<CircuitXmlEditor.ParsedComponent> tunnels = components.stream()
        .filter(component -> component.name().equals("Tunnel"))
        .toList();
    Map<String, CircuitXmlEditor.ParsedComponent> occupied = new HashMap<>();
    for (CircuitXmlEditor.ParsedComponent component : components) {
      occupied.put(locKey(component.x(), component.y()), component);
    }

    Map<Integer, Move> movesByStart = new HashMap<>();
    for (CircuitXmlEditor.ParsedComponent tunnel : tunnels) {
      for (CircuitXmlEditor.ParsedComponent pin : pins) {
        int dx = Math.abs(tunnel.x() - pin.x());
        if (tunnel.y() != pin.y() || dx == 0 || dx >= PIN_TUNNEL_CLEARANCE) {
          continue;
        }
        if (!hasOnlyHorizontalAttachments(circuitBody, tunnel.x(), tunnel.y())) {
          continue;
        }
        int direction = tunnel.x() >= pin.x() ? 1 : -1;
        int newX = findFreeX(pin.x() + direction * PIN_TUNNEL_CLEARANCE, tunnel.y(), direction, occupied);
        if (newX < 0 && direction < 0) {
          direction = 1;
          newX = findFreeX(pin.x() + direction * PIN_TUNNEL_CLEARANCE, tunnel.y(), direction, occupied);
        }
        if (newX >= 0 && newX != tunnel.x()) {
          Move move = new Move(tunnel.x(), tunnel.y(), newX, tunnel.y());
          movesByStart.put(tunnel.start(), move);
          occupied.remove(locKey(tunnel.x(), tunnel.y()));
          occupied.put(locKey(newX, tunnel.y()), tunnel);
          break;
        }
      }
    }
    return new RepairPlan(movesByStart);
  }

  private static int findFreeX(
      int preferredX, int y, int direction, Map<String, CircuitXmlEditor.ParsedComponent> occupied) {
    int x = preferredX;
    for (int i = 0; i < 40; i++) {
      if (x >= 20 && !occupied.containsKey(locKey(x, y))) {
        return x;
      }
      x += direction * SEARCH_STEP;
    }
    return -1;
  }

  private static boolean hasOnlyHorizontalAttachments(String circuitBody, int x, int y) {
    for (CircuitXmlEditor.ParsedWire wire : CircuitXmlEditor.parseWireBlocks(circuitBody)) {
      if (wire.touchesVertically(x, y)) {
        return false;
      }
    }
    return true;
  }

  private static String applyTunnelMoves(String circuitBody, RepairPlan plan) {
    String repaired = circuitBody;
    List<Map.Entry<Integer, Move>> entries = new ArrayList<>(plan.movesByStart().entrySet());
    entries.sort(Map.Entry.comparingByKey(Comparator.reverseOrder()));
    for (Map.Entry<Integer, Move> entry : entries) {
      Move move = entry.getValue();
      for (CircuitXmlEditor.ParsedComponent component : CircuitXmlEditor.parseComponentBlocks(repaired)) {
        if (component.start() != entry.getKey()) {
          continue;
        }
        String updated = component.xml().replaceFirst(
            "loc=\"\\(" + move.oldX() + "," + move.oldY() + "\\)\"",
            "loc=\"(" + move.newX() + "," + move.newY() + ")\"");
        repaired = repaired.substring(0, component.start()) + updated + repaired.substring(component.end());
        break;
      }
    }
    for (Move move : plan.movesByStart().values()) {
      String oldLoc = "(" + move.oldX() + "," + move.oldY() + ")";
      String newLoc = "(" + move.newX() + "," + move.newY() + ")";
      repaired = repaired.replace("from=\"" + oldLoc + "\"", "from=\"" + newLoc + "\"")
          .replace("to=\"" + oldLoc + "\"", "to=\"" + newLoc + "\"");
    }
    return repaired;
  }

  private static String locKey(int x, int y) {
    return x + "," + y;
  }

  public record Result(String action, String targetType, boolean applied, String detail) {}

  public record Report(String circuitPath, List<Result> results, CircuitLayoutInspector.Report afterRepair) {
    public boolean anyApplied() {
      return results.stream().anyMatch(Result::applied);
    }

    public boolean cleanAfterRepair() {
      return afterRepair.clean();
    }
  }

  private record Move(int oldX, int oldY, int newX, int newY) {}

  private record RepairPlan(Map<Integer, Move> movesByStart) {}

  private record TunnelLabelPlan(Map<String, String> labelsByOriginal, List<LabelMapping> mappings) {}

  private record LabelMapping(String circuitName, String originalLabel, String shortLabel) {}

  private record LabelShorteningReport(int changedGroups, String markdownPath) {}

  private record ComponentEdit(int start, int end, String replacement) {}

  private record PinFacingRepair(String body, int fixedCount) {}

  private record BoundaryWireReroute(String body, int changeCount) {}

  private record BodyRewrite(int changeCount) {}

  @FunctionalInterface
  private interface MainBodyRepairer {
    CircuitXmlEditor.BodyRepair repair(String body);
  }
}
