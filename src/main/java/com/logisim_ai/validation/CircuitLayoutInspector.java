package com.logisim_ai.validation;

import com.cburch.logisim.data.Location;
import com.logisim_ai.layout.AutoLayoutPolicy;
import com.logisim_ai.layout.FootprintPolicy;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
  private static final int MAX_TUNNEL_LABEL_LENGTH = 4;

  private CircuitLayoutInspector() {}

  public static Report inspect(File circFile) throws java.io.IOException {
    String xml = Files.readString(circFile.toPath(), StandardCharsets.UTF_8);
    List<LayoutIssue> issues = new ArrayList<>();
    if (xml.contains("lib=\"toolbar\"")) {
      issues.add(new LayoutIssue(
          LayoutIssueType.UNRESOLVED_TOOL,
          LayoutIssueSeverity.ERROR,
          "Toolbar contains unresolved lib=\"toolbar\" entries."));
    }
    List<CircuitXmlEditor.CircuitBlock> circuits = CircuitXmlEditor.parseCircuitBlocks(xml);
    inspectComponents(circuits, issues);
    inspectWires(circuits, CircuitXmlEditor.mainCircuitName(xml), issues);
    inspectWireReservedAreaIntrusions(circuits, issues);
    inspectPinTunnelPositions(circuits, CircuitXmlEditor.mainCircuitName(xml), issues);
    inspectTunnelLabels(circuits, issues);
    return new Report(circFile.getName(), issues, true);
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

  private static void inspectComponents(
      List<CircuitXmlEditor.CircuitBlock> circuits, List<LayoutIssue> issues) {
    for (CircuitXmlEditor.CircuitBlock circuit : circuits) {
      List<CircuitXmlEditor.ParsedComponent> components =
          CircuitXmlEditor.parseComponentBlocks(circuit.body());
      for (int i = 0; i < components.size(); i++) {
        CircuitXmlEditor.ParsedComponent a = components.get(i);
        for (int j = i + 1; j < components.size(); j++) {
          CircuitXmlEditor.ParsedComponent b = components.get(j);
          if (a.x() == b.x() && a.y() == b.y()) {
            issues.add(new LayoutIssue(
                LayoutIssueType.OVERLAP,
                LayoutIssueSeverity.ERROR,
                "Components overlap exactly in circuit " + circuit.name()
                    + " at (" + a.x() + "," + a.y() + "): " + a.name() + " and " + b.name()));
          }
          if (isSmallVisualComponent(a) && isSmallVisualComponent(b)
              && Math.abs(a.x() - b.x()) < SMALL_COMPONENT_CLEARANCE_X
              && Math.abs(a.y() - b.y()) < SMALL_COMPONENT_CLEARANCE_Y) {
            issues.add(new LayoutIssue(
                LayoutIssueType.OVERLAP,
                LayoutIssueSeverity.WARNING,
                "Small components are too close in circuit " + circuit.name()
                    + " at (" + a.x() + "," + a.y() + ") and (" + b.x() + "," + b.y() + "): "
                    + a.name() + " and " + b.name()));
          }
        }
      }
    }
  }

  private static boolean isSmallVisualComponent(CircuitXmlEditor.ParsedComponent component) {
    return component.name().equals("Pin") || component.name().equals("Tunnel");
  }

  // ---- Wire checks ----

  private static void inspectWires(
      List<CircuitXmlEditor.CircuitBlock> circuits, String mainCircuit, List<LayoutIssue> issues) {
    for (CircuitXmlEditor.CircuitBlock circuit : circuits) {
      for (CircuitXmlEditor.ParsedWire wire : CircuitXmlEditor.parseWireBlocks(circuit.body())) {
        if (wire.isDiagonal()) {
          issues.add(new LayoutIssue(
              LayoutIssueType.WIRE_RISK,
              LayoutIssueSeverity.ERROR,
              "Wire is diagonal in circuit " + circuit.name() + ": ("
                  + wire.x1() + "," + wire.y1() + ") -> (" + wire.x2() + "," + wire.y2() + ")",
              wireDetail("diagonal", circuit.name(), wire)));
        }
        if (circuit.name().equals(mainCircuit)
            && AutoLayoutPolicy.isRiskyBoundaryVertical(
                Location.create(wire.x1(), wire.y1(), true),
                Location.create(wire.x2(), wire.y2(), true))) {
          issues.add(new LayoutIssue(
              LayoutIssueType.WIRE_RISK,
              LayoutIssueSeverity.ERROR,
              "Vertical wire in left boundary lane can short external inputs: ("
                  + wire.x1() + "," + wire.y1() + ") -> (" + wire.x2() + "," + wire.y2() + ")",
              wireDetail("boundary_vertical", circuit.name(), wire)));
        }
      }
    }
  }

  private static String wireDetail(String kind, String circuitName, CircuitXmlEditor.ParsedWire wire) {
    return "kind=" + kind
        + ";circuit=" + circuitName
        + ";x1=" + wire.x1()
        + ";y1=" + wire.y1()
        + ";x2=" + wire.x2()
        + ";y2=" + wire.y2();
  }

  private static void inspectWireReservedAreaIntrusions(
      List<CircuitXmlEditor.CircuitBlock> circuits,
      List<LayoutIssue> issues) {
    for (CircuitXmlEditor.CircuitBlock circuit : circuits) {
      List<FootprintPolicy.ReservedArea> areas = new ArrayList<>();
      for (CircuitXmlEditor.ParsedComponent component : CircuitXmlEditor.parseComponentBlocks(circuit.body())) {
        FootprintPolicy.ReservedArea area = FootprintPolicy.reservedAreaForComponent(
            component.name(),
            component.label(),
            Location.create(component.x(), component.y(), true));
        if (area != null) {
          areas.add(area);
        }
      }
      for (CircuitXmlEditor.ParsedWire wire : CircuitXmlEditor.parseWireBlocks(circuit.body())) {
        AutoLayoutPolicy.WireSegment segment = new AutoLayoutPolicy.WireSegment(
            Location.create(wire.x1(), wire.y1(), true),
            Location.create(wire.x2(), wire.y2(), true));
        for (FootprintPolicy.ReservedArea area : areas) {
          if (area.crossesInterior(segment)) {
            issues.add(new LayoutIssue(
                LayoutIssueType.RESERVED_AREA_INTRUSION,
                LayoutIssueSeverity.WARNING,
                "Wire crosses reserved area of " + area.kind() + " " + area.label()
                    + " in circuit " + circuit.name() + ": ("
                    + wire.x1() + "," + wire.y1() + ") -> (" + wire.x2() + "," + wire.y2() + ")",
                reservedAreaDetail(circuit.name(), wire, area)));
          }
        }
      }
    }
  }

  private static String reservedAreaDetail(
      String circuitName,
      CircuitXmlEditor.ParsedWire wire,
      FootprintPolicy.ReservedArea area) {
    return "kind=reserved_area_intrusion"
        + ";circuit=" + circuitName
        + ";component=" + area.kind()
        + ";label=" + area.label()
        + ";anchorX=" + area.anchorX()
        + ";anchorY=" + area.anchorY()
        + ";left=" + area.left()
        + ";top=" + area.top()
        + ";right=" + area.right()
        + ";bottom=" + area.bottom()
        + ";connectionPoints=" + area.connectionPoints().size()
        + ";x1=" + wire.x1()
        + ";y1=" + wire.y1()
        + ";x2=" + wire.x2()
        + ";y2=" + wire.y2();
  }

  // ---- Pin/Tunnel position and facing checks ----

  private static void inspectPinTunnelPositions(
      List<CircuitXmlEditor.CircuitBlock> circuits,
      String mainCircuit,
      List<LayoutIssue> issues) {
    String mainXml = circuits.stream()
        .filter(circuit -> circuit.name().equals(mainCircuit))
        .findFirst()
        .map(CircuitXmlEditor.CircuitBlock::body)
        .orElse("");
    List<CircuitXmlEditor.ParsedComponent> all = CircuitXmlEditor.parseComponentBlocks(mainXml);
    List<CircuitXmlEditor.ParsedComponent> pins = new ArrayList<>();
    List<CircuitXmlEditor.ParsedComponent> tunnels = new ArrayList<>();

    for (CircuitXmlEditor.ParsedComponent c : all) {
      if ("Pin".equals(c.name())) {
        pins.add(c);
      } else if ("Tunnel".equals(c.name())) {
        tunnels.add(c);
      }
    }

    for (CircuitXmlEditor.ParsedComponent pin : pins) {
      boolean isOutput = "output".equals(pin.type());
      String expectedFacing = isOutput ? "west" : "east";
      String actualFacing = pin.facing() != null ? pin.facing() : expectedFacing;
      if (!expectedFacing.equals(actualFacing)) {
        issues.add(new LayoutIssue(
            LayoutIssueType.PIN_FACING,
            LayoutIssueSeverity.WARNING,
            (isOutput ? "Output" : "Input") + " pin " + pin.label()
                + " facing should be " + expectedFacing + ", got: " + actualFacing));
      }

      for (CircuitXmlEditor.ParsedComponent tunnel : tunnels) {
        int dx = Math.abs(tunnel.x() - pin.x());
        if (tunnel.y() == pin.y() && dx > 0 && dx < NEAR_PIN_TUNNEL_DIST) {
          issues.add(new LayoutIssue(
              LayoutIssueType.PIN_TUNNEL_PROXIMITY,
              LayoutIssueSeverity.WARNING,
              "Tunnel " + tunnel.label() + " is too close to external pin " + pin.label()
                  + " on the same row: (" + tunnel.x() + "," + tunnel.y() + ") vs ("
                  + pin.x() + "," + pin.y() + ")"));
        }
      }
    }
  }

  private static void inspectTunnelLabels(
      List<CircuitXmlEditor.CircuitBlock> circuits, List<LayoutIssue> issues) {
    for (CircuitXmlEditor.CircuitBlock circuit : circuits) {
      for (CircuitXmlEditor.ParsedComponent component : CircuitXmlEditor.parseComponentBlocks(circuit.body())) {
        if (!"Tunnel".equals(component.name())) {
          continue;
        }
        if (component.label().length() > MAX_TUNNEL_LABEL_LENGTH) {
          issues.add(new LayoutIssue(
              LayoutIssueType.LABEL_RISK,
              LayoutIssueSeverity.INFO,
              "Tunnel label is too long and may overlap nearby components in circuit "
                  + circuit.name() + ": " + component.label() + " at ("
                  + component.x() + "," + component.y() + ")"));
        }
      }
    }
  }

  public enum LayoutIssueType {
    UNRESOLVED_TOOL,
    OVERLAP,
    WIRE_RISK,
    PIN_FACING,
    PIN_TUNNEL_PROXIMITY,
    LABEL_RISK,
    RESERVED_AREA_INTRUSION,
    INSPECTION_FAILURE,
    OTHER
  }

  public enum LayoutIssueSeverity {
    ERROR,
    WARNING,
    INFO
  }

  public record LayoutIssue(
      LayoutIssueType type,
      LayoutIssueSeverity severity,
      String message,
      String detail) {
    public LayoutIssue(LayoutIssueType type, LayoutIssueSeverity severity, String message) {
      this(type, severity, message, "");
    }
  }

  public record LayoutRepairPlan(
      String action,
      LayoutIssueType targetType,
      LayoutIssueSeverity severity,
      String suggestion,
      String parameters) {}

  public record Report(String fileName, List<String> issues, List<LayoutIssue> layoutIssues) {
    public Report(String fileName, List<String> issues) {
      this(fileName, issues, issues.stream()
          .map(issue -> new LayoutIssue(LayoutIssueType.OTHER, LayoutIssueSeverity.WARNING, issue))
          .toList());
    }

    public Report(String fileName, List<LayoutIssue> layoutIssues, boolean structured) {
      this(fileName, layoutIssues.stream().map(LayoutIssue::message).toList(), layoutIssues);
    }

    public boolean clean() {
      // Warnings (e.g. small components / Tunnels too close) do not affect
      // loadability or official-JAR simulation; only ERROR-level layout
      // problems (exact overlaps, unresolvable conflicts) fail the check.
      return layoutIssues.stream()
          .noneMatch(issue -> issue.severity() == LayoutIssueSeverity.ERROR);
    }

    public int issueCount() {
      return issues.size();
    }

    public List<String> repairSuggestions() {
      List<String> suggestions = new ArrayList<>();
      for (LayoutRepairPlan plan : repairPlans()) {
        if (!suggestions.contains(plan.suggestion())) {
          suggestions.add(plan.suggestion());
        }
      }
      return suggestions;
    }

    public List<LayoutRepairPlan> repairPlans() {
      List<LayoutRepairPlan> plans = new ArrayList<>();
      for (LayoutIssue issue : layoutIssues) {
        LayoutRepairPlan plan = repairPlan(issue);
        if (!containsPlan(plans, plan)) {
          plans.add(plan);
        }
      }
      return plans;
    }

    public int unresolvedToolCount() {
      return countIssues(LayoutIssueType.UNRESOLVED_TOOL);
    }

    public int overlapCount() {
      return countIssues(LayoutIssueType.OVERLAP);
    }

    public int wireRiskCount() {
      return countIssues(LayoutIssueType.WIRE_RISK);
    }

    public int pinFacingIssueCount() {
      return countIssues(LayoutIssueType.PIN_FACING);
    }

    public int pinTunnelProximityCount() {
      return countIssues(LayoutIssueType.PIN_TUNNEL_PROXIMITY);
    }

    public int labelRiskCount() {
      return countIssues(LayoutIssueType.LABEL_RISK);
    }

    public int reservedAreaIntrusionCount() {
      return countIssues(LayoutIssueType.RESERVED_AREA_INTRUSION);
    }

    public int inspectionFailureCount() {
      return countIssues(LayoutIssueType.INSPECTION_FAILURE);
    }

    public int otherIssueCount() {
      int categorized = unresolvedToolCount()
          + overlapCount()
          + wireRiskCount()
          + pinFacingIssueCount()
          + pinTunnelProximityCount()
          + labelRiskCount()
          + reservedAreaIntrusionCount()
          + inspectionFailureCount();
      return Math.max(0, issueCount() - categorized);
    }

    public int qualityScore() {
      int penalty = unresolvedToolCount() * 40
          + overlapCount() * 30
          + wireRiskCount() * 30
          + pinFacingIssueCount() * 20
          + pinTunnelProximityCount() * 20
          + labelRiskCount() * 10
          + reservedAreaIntrusionCount() * 20
          + inspectionFailureCount() * 50
          + otherIssueCount() * 20;
      return Math.max(0, 100 - penalty);
    }

    public String riskLevel() {
      if (clean()) {
        return "low";
      }
      if (issueCount() <= 2) {
        return "medium";
      }
      return "high";
    }

    private int countIssues(LayoutIssueType type) {
      int count = 0;
      for (LayoutIssue issue : layoutIssues) {
        if (issue.type() == type) {
          count++;
        }
      }
      return count;
    }

    private boolean containsPlan(List<LayoutRepairPlan> plans, LayoutRepairPlan candidate) {
      for (LayoutRepairPlan plan : plans) {
        if (plan.action().equals(candidate.action())
            && plan.targetType() == candidate.targetType()
            && plan.parameters().equals(candidate.parameters())) {
          return true;
        }
      }
      return false;
    }

    private LayoutRepairPlan repairPlan(LayoutIssue issue) {
      LayoutIssueType type = issue.type();
      LayoutIssueSeverity severity = issue.severity();
      return switch (type) {
        case UNRESOLVED_TOOL -> new LayoutRepairPlan(
            "regenerate_toolbar",
            type,
            severity,
            "Remove unresolved toolbar library entries and regenerate the project toolbar from known Logisim tools.",
            "knownToolsOnly=true");
        case OVERLAP -> new LayoutRepairPlan(
            "increase_spacing",
            type,
            severity,
            "Increase grid spacing, reassign crowded component rows, or rerun layout with wider component clearance.",
            "minClearanceX=80;minClearanceY=50");
        case WIRE_RISK -> new LayoutRepairPlan(
            "reroute_wires",
            type,
            severity,
            "Reroute risky wires through tunnels or move boundary wire lanes away from external input rows.",
            issue.detail().isEmpty() ? "preferTunnels=true;avoidBoundaryLane=true" : issue.detail());
        case PIN_FACING -> new LayoutRepairPlan(
            "fix_pin_facing",
            type,
            severity,
            "Correct external Pin facing attributes: inputs should face east and outputs should face west.",
            "inputFacing=east;outputFacing=west");
        case PIN_TUNNEL_PROXIMITY -> new LayoutRepairPlan(
            "increase_pin_tunnel_clearance",
            type,
            severity,
            "Move tunnels farther from external pins or increase the pin-to-tunnel clearance rule.",
            "minClearance=150");
        case LABEL_RISK -> new LayoutRepairPlan(
            "shorten_tunnel_labels",
            type,
            severity,
            "Shorten tunnel labels or increase label padding around tunnel components.",
            "maxLabelLength=4;increasePadding=true");
        case RESERVED_AREA_INTRUSION -> new LayoutRepairPlan(
            "avoid_reserved_areas",
            type,
            severity,
            "Move wires, tunnels, or labels away from reserved component visual areas.",
            issue.detail().isEmpty() ? "requiresReroute=true" : issue.detail());
        case INSPECTION_FAILURE -> new LayoutRepairPlan(
            "fix_layout_inspector",
            type,
            severity,
            "Fix the layout inspector failure first, then rerun static layout inspection.",
            "rerunInspector=true");
        case OTHER -> new LayoutRepairPlan(
            "inspect_uncategorized_layout_issue",
            type,
            severity,
            "Inspect the uncategorized layout issue and add a specific LayoutIssueType if it recurs.",
            "requiresHumanReview=true");
      };
    }

    public String toHumanString() {
      if (clean()) {
        return "[OK] " + fileName + " static layout checks passed";
      }
      return "[FAIL] " + fileName + "\n - " + String.join("\n - ", issues);
    }
  }
}
