package com.logisim_ai.validation;

import com.logisim_ai.ir.CircuitSpec;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Writes a machine-readable delivery index for generated circuit artifacts. */
public final class GenerationDeliveryReport {
  private GenerationDeliveryReport() {}

  public static Path markdownPath(File outputFile) {
    return Path.of("generated", "reports", outputStem(outputFile) + "_delivery_report.md");
  }

  public static Path jsonPath(File outputFile) {
    return Path.of("generated", "reports", outputStem(outputFile) + "_delivery_report.json");
  }

  public static Path write(
      String requestKind,
      String request,
      String intent,
      CircuitSpec spec,
      File outputFile,
      Path summaryMd,
      Path summaryJson,
      Path manifestMd,
      Path manifestJson,
      GenerationSummary.ManifestStatus manifestStatus,
      List<String> recommendedActions,
      List<Path> summaryArtifacts) throws java.io.IOException {
    return write(
        requestKind,
        request,
        intent,
        null,
        spec,
        outputFile,
        summaryMd,
        summaryJson,
        manifestMd,
        manifestJson,
        manifestStatus,
        recommendedActions,
        summaryArtifacts);
  }

  public static Path write(
      String requestKind,
      String request,
      String intent,
      GenerationSummary.ImplementationProfile implementationProfile,
      CircuitSpec spec,
      File outputFile,
      Path summaryMd,
      Path summaryJson,
      Path manifestMd,
      Path manifestJson,
      GenerationSummary.ManifestStatus manifestStatus,
      List<String> recommendedActions,
      List<Path> summaryArtifacts) throws java.io.IOException {
    Path reportDir = Path.of("generated", "reports");
    Files.createDirectories(reportDir);
    Path deliveryMd = markdownPath(outputFile);
    Path deliveryJson = jsonPath(outputFile);
    LayoutInspectionResult layoutInspection = inspectLayout(outputFile);
    CircuitLayoutInspector.Report layoutReport = layoutInspection.layoutReport();
    List<String> deliveryActions = recommendedActionsWithLayout(recommendedActions, layoutReport);
    List<Artifact> artifacts = artifacts(
        outputFile, summaryMd, summaryJson, manifestMd, manifestJson, deliveryMd, deliveryJson,
        summaryArtifactsWithLayoutRepairs(outputFile, summaryArtifacts));
    Files.writeString(deliveryMd, toMarkdown(
        requestKind, request, intent, implementationProfile, spec, outputFile, summaryMd, manifestMd,
        manifestStatus, deliveryActions, artifacts, layoutInspection));
    Files.writeString(deliveryJson, toJson(
        requestKind, request, intent, implementationProfile, spec, outputFile, summaryMd, summaryJson,
        manifestMd, manifestJson, manifestStatus, deliveryActions, artifacts, layoutInspection));
    return deliveryMd;
  }

  private static List<Path> summaryArtifactsWithLayoutRepairs(File outputFile, List<Path> summaryArtifacts) {
    List<Path> artifacts = new ArrayList<>();
    if (summaryArtifacts != null) {
      artifacts.addAll(summaryArtifacts);
    }
    Path tunnelLabelMapMd = Path.of("generated", "reports", outputStem(outputFile) + "_tunnel_label_map.md");
    Path tunnelLabelMapJson = Path.of("generated", "reports", outputStem(outputFile) + "_tunnel_label_map.json");
    if (Files.exists(tunnelLabelMapMd)) {
      artifacts.add(tunnelLabelMapMd);
    }
    if (Files.exists(tunnelLabelMapJson)) {
      artifacts.add(tunnelLabelMapJson);
    }
    return artifacts;
  }

  public static List<Artifact> artifactsForTesting(
      File outputFile,
      Path summaryMd,
      Path summaryJson,
      Path manifestMd,
      Path manifestJson,
      Path deliveryMd,
      Path deliveryJson,
      List<Path> summaryArtifacts) {
    return artifacts(outputFile, summaryMd, summaryJson, manifestMd, manifestJson,
        deliveryMd, deliveryJson, summaryArtifacts);
  }

  private static List<Artifact> artifacts(
      File outputFile,
      Path summaryMd,
      Path summaryJson,
      Path manifestMd,
      Path manifestJson,
      Path deliveryMd,
      Path deliveryJson,
      List<Path> summaryArtifacts) {
    List<Artifact> artifacts = new ArrayList<>();
    add(artifacts, "circuit", outputFile.toPath());
    add(artifacts, "summary_markdown", summaryMd);
    add(artifacts, "summary_json", summaryJson);
    add(artifacts, "manifest_markdown", manifestMd);
    add(artifacts, "manifest_json", manifestJson);
    if (summaryArtifacts != null) {
      for (Path artifact : summaryArtifacts) {
        add(artifacts, inferKind(artifact), artifact);
      }
    }
    addGenerated(artifacts, "delivery_markdown", deliveryMd);
    addGenerated(artifacts, "delivery_json", deliveryJson);
    return dedupeByPath(artifacts).stream()
        .sorted(Comparator.comparing(Artifact::path))
        .toList();
  }

  private static List<Artifact> dedupeByPath(List<Artifact> artifacts) {
    Map<String, Artifact> byPath = new LinkedHashMap<>();
    for (Artifact artifact : artifacts) {
      Artifact existing = byPath.get(artifact.path());
      if (existing == null || isMoreSpecific(artifact.kind(), existing.kind())) {
        byPath.put(artifact.path(), artifact);
      }
    }
    return new ArrayList<>(byPath.values());
  }

  private static boolean isMoreSpecific(String candidate, String existing) {
    return "artifact".equals(existing) && !"artifact".equals(candidate);
  }

  private static void add(List<Artifact> artifacts, String kind, Path path) {
    if (path != null) {
      artifacts.add(new Artifact(kind, path.toString(), Files.exists(path)));
    }
  }

  private static void addGenerated(List<Artifact> artifacts, String kind, Path path) {
    if (path != null) {
      artifacts.add(new Artifact(kind, path.toString(), true));
    }
  }

  private static String inferKind(Path path) {
    String name = path.getFileName().toString();
    if (name.endsWith("_circuit_truth_table.md")) {
      return "truth_table_markdown";
    }
    if (name.endsWith("_circuit_truth_table.json")) {
      return "truth_table_json";
    }
    if (name.contains("_ttl_bom.")) {
      return "ttl_bom";
    }
    if (name.contains("_ttl_dip.")) {
      return "ttl_dip_wiring";
    }
    if (name.contains("_ttl_dip_layout.")) {
      return "ttl_dip_layout";
    }
    if (name.contains("_ttl_dip_subckt_pinmap.")) {
      return "pinmap";
    }
    if (name.contains("_ttl_dip_subckt_behavior.")) {
      return "behavior_report";
    }
    if (name.contains("_mapping.")) {
      return "chip_mapping";
    }
    if (name.endsWith("_tunnel_label_map.md")) {
      return "tunnel_label_map_markdown";
    }
    if (name.endsWith("_tunnel_label_map.json")) {
      return "tunnel_label_map_json";
    }
    if (name.contains("_bom.")) {
      return "chip_bom";
    }
    if (name.endsWith("_generation_summary.md")) {
      return "summary_markdown";
    }
    if (name.endsWith("_generation_summary.json")) {
      return "summary_json";
    }
    if (name.endsWith("_manifest.md")) {
      return "manifest_markdown";
    }
    if (name.endsWith("_manifest.json")) {
      return "manifest_json";
    }
    if (name.endsWith(".circ")) {
      return "circuit";
    }
    return "artifact";
  }

  private static String toMarkdown(
      String requestKind,
      String request,
      String intent,
      GenerationSummary.ImplementationProfile implementationProfile,
      CircuitSpec spec,
      File outputFile,
      Path summaryMd,
      Path manifestMd,
      GenerationSummary.ManifestStatus manifestStatus,
      List<String> recommendedActions,
      List<Artifact> artifacts,
      LayoutInspectionResult layoutInspection) {
    CircuitLayoutInspector.Report layoutReport = layoutInspection.layoutReport();
    StringBuilder out = new StringBuilder();
    out.append("# Generation delivery report for ").append(spec.name()).append("\n\n");
    out.append("- Request kind: ").append(requestKind).append("\n");
    out.append("- Request: ").append(request).append("\n");
    out.append("- Intent: ").append(intent).append("\n");
    if (implementationProfile != null) {
      out.append("- Implementation level: ").append(implementationProfile.level()).append("\n");
      out.append("- Target package: ").append(implementationProfile.targetPackage()).append("\n");
      out.append("- Package primitive: ").append(implementationProfile.packagePrimitive()).append("\n");
      out.append("- Routing primitive: ").append(implementationProfile.routingPrimitive()).append("\n");
    }
    out.append("- Circuit: ").append(outputFile.getPath()).append("\n");
    out.append("- Summary: ").append(summaryMd).append("\n");
    out.append("- Manifest: ").append(manifestMd).append("\n");
    out.append("- Complete: ").append(manifestStatus.completeText()).append("\n");
    out.append("- Manifest check count: ").append(manifestStatus.checkCountText()).append("\n");
    out.append("- Manifest issue count: ").append(manifestStatus.issueCountText()).append("\n");
    out.append("- Layout clean: ").append(layoutReport.clean()).append("\n");
    out.append("- Layout issue count: ").append(layoutReport.issueCount()).append("\n");
    out.append("- Layout score: ").append(layoutReport.qualityScore()).append("\n");
    out.append("- Layout risk level: ").append(layoutReport.riskLevel()).append("\n");
    out.append("- Layout unresolved tool count: ").append(layoutReport.unresolvedToolCount()).append("\n");
    out.append("- Layout overlap count: ").append(layoutReport.overlapCount()).append("\n");
    out.append("- Layout wire risk count: ").append(layoutReport.wireRiskCount()).append("\n");
    out.append("- Layout pin facing issue count: ").append(layoutReport.pinFacingIssueCount()).append("\n");
    out.append("- Layout pin/tunnel proximity count: ").append(layoutReport.pinTunnelProximityCount()).append("\n");
    out.append("- Layout label risk count: ").append(layoutReport.labelRiskCount()).append("\n");
    out.append("- Layout reserved area intrusion count: ")
        .append(layoutReport.reservedAreaIntrusionCount()).append("\n");
    out.append("- Layout inspection failure count: ").append(layoutReport.inspectionFailureCount()).append("\n");
    out.append("- Layout other issue count: ").append(layoutReport.otherIssueCount()).append("\n");
    out.append("- Layout repair suggestions: ").append(String.join(" | ", layoutReport.repairSuggestions())).append("\n");
    out.append("- Layout auto repair applied: ").append(layoutInspection.autoRepairApplied()).append("\n");
    out.append("- Recommended actions: ").append(String.join(", ", recommendedActions)).append("\n");
    out.append("- Artifact count: ").append(artifacts.size()).append("\n\n");
    if (layoutInspection.autoRepairApplied()) {
      out.append("## Layout Repairs Applied\n\n");
      for (LayoutRepairExecutor.Result result : layoutInspection.repairReport().results()) {
        out.append("- [")
            .append(result.applied() ? "applied" : "skipped")
            .append("/")
            .append(result.action())
            .append("/")
            .append(result.targetType())
            .append("] ")
            .append(result.detail())
            .append("\n");
      }
      out.append("\n");
    }
    if (!layoutReport.clean()) {
      out.append("## Layout Issues\n\n");
      for (CircuitLayoutInspector.LayoutIssue issue : layoutReport.layoutIssues()) {
        out.append("- [")
            .append(issue.severity())
            .append("/")
            .append(issue.type())
            .append("] ")
            .append(issue.message())
            .append("\n");
      }
      out.append("\n");
    }
    if (!layoutReport.repairSuggestions().isEmpty()) {
      out.append("## Layout Repair Suggestions\n\n");
      for (CircuitLayoutInspector.LayoutRepairPlan plan : layoutReport.repairPlans()) {
        out.append("- [")
            .append(plan.action())
            .append("/")
            .append(plan.targetType())
            .append("] ")
            .append(plan.suggestion())
            .append(" (")
            .append(plan.parameters())
            .append(")\n");
      }
      out.append("\n");
    }
    out.append("## Artifacts\n\n");
    out.append("| Kind | Path | Exists |\n");
    out.append("| --- | --- | --- |\n");
    for (Artifact artifact : artifacts) {
      out.append("| ")
          .append(artifact.kind())
          .append(" | ")
          .append(artifact.path())
          .append(" | ")
          .append(artifact.exists())
          .append(" |\n");
    }
    return out.toString();
  }

  private static String toJson(
      String requestKind,
      String request,
      String intent,
      GenerationSummary.ImplementationProfile implementationProfile,
      CircuitSpec spec,
      File outputFile,
      Path summaryMd,
      Path summaryJson,
      Path manifestMd,
      Path manifestJson,
      GenerationSummary.ManifestStatus manifestStatus,
      List<String> recommendedActions,
      List<Artifact> artifacts,
      LayoutInspectionResult layoutInspection) {
    CircuitLayoutInspector.Report layoutReport = layoutInspection.layoutReport();
    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"requestKind\": \"").append(escape(requestKind)).append("\",\n");
    out.append("  \"request\": \"").append(escape(request)).append("\",\n");
    out.append("  \"intent\": \"").append(escape(intent)).append("\",\n");
    if (implementationProfile != null) {
      out.append("  \"implementationProfile\": {\n");
      out.append("    \"level\": \"").append(escape(implementationProfile.level())).append("\",\n");
      out.append("    \"targetPackage\": \"").append(escape(implementationProfile.targetPackage())).append("\",\n");
      out.append("    \"packagePrimitive\": ").append(implementationProfile.packagePrimitive()).append(",\n");
      out.append("    \"routingPrimitive\": \"").append(escape(implementationProfile.routingPrimitive())).append("\"\n");
      out.append("  },\n");
    }
    out.append("  \"circuitName\": \"").append(escape(spec.name())).append("\",\n");
    out.append("  \"circuitPath\": \"").append(escape(outputFile.getPath())).append("\",\n");
    out.append("  \"summaryPath\": \"").append(escape(summaryMd.toString())).append("\",\n");
    out.append("  \"summaryJsonPath\": \"").append(escape(summaryJson.toString())).append("\",\n");
    out.append("  \"manifestPath\": \"").append(escape(manifestMd.toString())).append("\",\n");
    out.append("  \"manifestJsonPath\": \"").append(escape(manifestJson.toString())).append("\",\n");
    out.append("  \"complete\": ").append(manifestStatus.completeJson()).append(",\n");
    out.append("  \"manifestCheckCount\": ").append(manifestStatus.checkCountJson()).append(",\n");
    out.append("  \"manifestIssueCount\": ").append(manifestStatus.issueCountJson()).append(",\n");
    out.append("  \"layoutClean\": ").append(layoutReport.clean()).append(",\n");
    out.append("  \"layoutIssueCount\": ").append(layoutReport.issueCount()).append(",\n");
    out.append("  \"layoutScore\": ").append(layoutReport.qualityScore()).append(",\n");
    out.append("  \"layoutRiskLevel\": \"").append(escape(layoutReport.riskLevel())).append("\",\n");
    out.append("  \"layoutUnresolvedToolCount\": ").append(layoutReport.unresolvedToolCount()).append(",\n");
    out.append("  \"layoutOverlapCount\": ").append(layoutReport.overlapCount()).append(",\n");
    out.append("  \"layoutWireRiskCount\": ").append(layoutReport.wireRiskCount()).append(",\n");
    out.append("  \"layoutPinFacingIssueCount\": ").append(layoutReport.pinFacingIssueCount()).append(",\n");
    out.append("  \"layoutPinTunnelProximityCount\": ")
        .append(layoutReport.pinTunnelProximityCount()).append(",\n");
    out.append("  \"layoutLabelRiskCount\": ").append(layoutReport.labelRiskCount()).append(",\n");
    out.append("  \"layoutReservedAreaIntrusionCount\": ")
        .append(layoutReport.reservedAreaIntrusionCount()).append(",\n");
    out.append("  \"layoutInspectionFailureCount\": ").append(layoutReport.inspectionFailureCount()).append(",\n");
    out.append("  \"layoutOtherIssueCount\": ").append(layoutReport.otherIssueCount()).append(",\n");
    out.append("  \"layoutIssues\": [");
    for (int i = 0; i < layoutReport.issues().size(); i++) {
      out.append("\"").append(escape(layoutReport.issues().get(i))).append("\"");
      if (i + 1 < layoutReport.issues().size()) {
        out.append(", ");
      }
    }
    out.append("],\n");
    out.append("  \"layoutIssueDetails\": [");
    for (int i = 0; i < layoutReport.layoutIssues().size(); i++) {
      CircuitLayoutInspector.LayoutIssue issue = layoutReport.layoutIssues().get(i);
      out.append("{\"type\": \"").append(escape(issue.type().name()))
          .append("\", \"severity\": \"").append(escape(issue.severity().name()))
          .append("\", \"message\": \"").append(escape(issue.message()))
          .append("\", \"detail\": \"").append(escape(issue.detail())).append("\"}");
      if (i + 1 < layoutReport.layoutIssues().size()) {
        out.append(", ");
      }
    }
    out.append("],\n");
    out.append("  \"layoutRepairSuggestions\": [");
    List<String> repairSuggestions = layoutReport.repairSuggestions();
    for (int i = 0; i < repairSuggestions.size(); i++) {
      out.append("\"").append(escape(repairSuggestions.get(i))).append("\"");
      if (i + 1 < repairSuggestions.size()) {
        out.append(", ");
      }
    }
    out.append("],\n");
    out.append("  \"layoutRepairPlans\": [");
    List<CircuitLayoutInspector.LayoutRepairPlan> repairPlans = layoutReport.repairPlans();
    for (int i = 0; i < repairPlans.size(); i++) {
      CircuitLayoutInspector.LayoutRepairPlan plan = repairPlans.get(i);
      out.append("{\"action\": \"").append(escape(plan.action()))
          .append("\", \"targetType\": \"").append(escape(plan.targetType().name()))
          .append("\", \"severity\": \"").append(escape(plan.severity().name()))
          .append("\", \"suggestion\": \"").append(escape(plan.suggestion()))
          .append("\", \"parameters\": \"").append(escape(plan.parameters())).append("\"}");
      if (i + 1 < repairPlans.size()) {
        out.append(", ");
      }
    }
    out.append("],\n");
    out.append("  \"layoutAutoRepairApplied\": ").append(layoutInspection.autoRepairApplied()).append(",\n");
    out.append("  \"layoutAutoRepairResults\": [");
    List<LayoutRepairExecutor.Result> repairResults = layoutInspection.repairResults();
    for (int i = 0; i < repairResults.size(); i++) {
      LayoutRepairExecutor.Result result = repairResults.get(i);
      out.append("{\"action\": \"").append(escape(result.action()))
          .append("\", \"targetType\": \"").append(escape(result.targetType()))
          .append("\", \"applied\": ").append(result.applied())
          .append(", \"detail\": \"").append(escape(result.detail())).append("\"}");
      if (i + 1 < repairResults.size()) {
        out.append(", ");
      }
    }
    out.append("],\n");
    out.append("  \"recommendedActions\": [");
    for (int i = 0; i < recommendedActions.size(); i++) {
      out.append("\"").append(escape(recommendedActions.get(i))).append("\"");
      if (i + 1 < recommendedActions.size()) {
        out.append(", ");
      }
    }
    out.append("],\n");
    out.append("  \"artifactCount\": ").append(artifacts.size()).append(",\n");
    out.append("  \"artifacts\": [\n");
    for (int i = 0; i < artifacts.size(); i++) {
      Artifact artifact = artifacts.get(i);
      out.append("    {\"kind\": \"").append(escape(artifact.kind()))
          .append("\", \"path\": \"").append(escape(artifact.path()))
          .append("\", \"exists\": ").append(artifact.exists()).append("}");
      if (i + 1 < artifacts.size()) {
        out.append(",");
      }
      out.append("\n");
    }
    out.append("  ]\n");
    out.append("}\n");
    return out.toString();
  }

  private static String outputStem(File outputFile) {
    String name = outputFile.getName();
    if (name.endsWith(".circ")) {
      return name.substring(0, name.length() - ".circ".length());
    }
    return name;
  }

  private static LayoutInspectionResult inspectLayout(File outputFile) {
    try {
      CircuitLayoutInspector.Report report = CircuitLayoutInspector.inspect(outputFile);
      if (hasOnlySafeAutoRepairPlans(report)) {
        LayoutRepairExecutor.Report repairReport = LayoutRepairExecutor.execute(outputFile, report);
        return new LayoutInspectionResult(repairReport.afterRepair(), repairReport);
      }
      return new LayoutInspectionResult(report, null);
    } catch (Exception e) {
      return new LayoutInspectionResult(new CircuitLayoutInspector.Report(
          outputFile.getName(),
          List.of(new CircuitLayoutInspector.LayoutIssue(
              CircuitLayoutInspector.LayoutIssueType.INSPECTION_FAILURE,
              CircuitLayoutInspector.LayoutIssueSeverity.ERROR,
              "Layout inspection failed: " + e.getMessage())),
          true), null);
    }
  }

  private static boolean hasOnlySafeAutoRepairPlans(CircuitLayoutInspector.Report report) {
    if (report.clean() || report.repairPlans().isEmpty()) {
      return false;
    }
    return report.repairPlans().stream()
        .allMatch(plan -> isSafeAutoRepairAction(plan.action()));
  }

  private static boolean isSafeAutoRepairAction(String action) {
    return "regenerate_toolbar".equals(action)
        || "increase_pin_tunnel_clearance".equals(action)
        || "fix_pin_facing".equals(action)
        || "shorten_tunnel_labels".equals(action);
  }

  private static List<String> recommendedActionsWithLayout(
      List<String> recommendedActions,
      CircuitLayoutInspector.Report layoutReport) {
    List<String> actions = new ArrayList<>(recommendedActions);
    if (!layoutReport.clean()) {
      addAction(actions, "fix_layout");
      addAction(actions, "regenerate_layout");
    }
    return actions;
  }

  private static void addAction(List<String> actions, String action) {
    if (!actions.contains(action)) {
      actions.add(action);
    }
  }

  private static String escape(String text) {
    return text
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t");
  }

  public record Artifact(String kind, String path, boolean exists) {}

  private record LayoutInspectionResult(
      CircuitLayoutInspector.Report layoutReport,
      LayoutRepairExecutor.Report repairReport) {
    boolean autoRepairApplied() {
      return repairReport != null && repairReport.anyApplied();
    }

    List<LayoutRepairExecutor.Result> repairResults() {
      return repairReport == null ? List.of() : repairReport.results();
    }
  }
}
