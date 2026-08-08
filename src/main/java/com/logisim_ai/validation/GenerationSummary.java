package com.logisim_ai.validation;

import com.logisim_ai.ir.CircuitSpec;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;

/** Writes a compact index for a generated circuit and its verification artifacts. */
public final class GenerationSummary {
  private GenerationSummary() {}

  public record ImplementationProfile(
      String level,
      String targetPackage,
      boolean packagePrimitive,
      String routingPrimitive) {}

  public static Path writeCircuitSummary(
      String requestKind,
      String request,
      String description,
      String intent,
      Path sourceJson,
      CircuitSpec spec,
      File outputFile) throws java.io.IOException {
    return writeSummary(
        requestKind,
        request,
        description,
        intent,
        sourceJson,
        spec,
        outputFile,
        CircuitArtifactManifest.markdownPath(outputFile),
        CircuitArtifactManifest.jsonPath(outputFile),
        outputStem(outputFile).equals(spec.name()),
        null);
  }

  public static Path writeCircuitSummary(
      String requestKind,
      String request,
      String description,
      String intent,
      ImplementationProfile implementationProfile,
      Path sourceJson,
      CircuitSpec spec,
      File outputFile) throws java.io.IOException {
    return writeSummary(
        requestKind,
        request,
        description,
        intent,
        sourceJson,
        spec,
        outputFile,
        CircuitArtifactManifest.markdownPath(outputFile),
        CircuitArtifactManifest.jsonPath(outputFile),
        outputStem(outputFile).equals(spec.name()),
        implementationProfile);
  }

  public static Path writeTtlDipSummary(
      String request,
      String description,
      String intent,
      Path sourceJson,
      CircuitSpec spec,
      File outputFile) throws java.io.IOException {
    Path manifestMd = Path.of("generated", "reports", spec.name() + "_ttl_dip_subckt_manifest.md");
    Path manifestJson = Path.of("generated", "reports", spec.name() + "_ttl_dip_subckt_manifest.json");
    return writeSummary(
        "natural_language_ttl_dip",
        request,
        description,
        intent,
        sourceJson,
        spec,
        outputFile,
        manifestMd,
        manifestJson,
        true,
        null);
  }

  public static Path markdownPath(File outputFile) {
    return Path.of("generated", "reports", outputStem(outputFile) + "_generation_summary.md");
  }

  public static Path jsonPath(File outputFile) {
    return Path.of("generated", "reports", outputStem(outputFile) + "_generation_summary.json");
  }

  public static ManifestStatus readManifestStatusForTesting(Path manifestJson) {
    return readManifestStatus(manifestJson);
  }

  public static List<Path> collectArtifactsForTesting(
      CircuitSpec spec,
      File outputFile,
      Path manifestMd,
      Path manifestJson,
      Path summaryMd,
      Path summaryJson,
      boolean includeSpecPrefixedReports) throws java.io.IOException {
    return artifactsFor(
        spec, outputFile, manifestMd, manifestJson, summaryMd, summaryJson, includeSpecPrefixedReports);
  }

  public static String describeIntentForTesting(String intent) {
    return describeIntent(intent);
  }

  public static String recommendNextActionForTesting(
      String requestKind,
      String intent,
      ManifestStatus manifestStatus) {
    return recommendNextAction(requestKind, intent, manifestStatus);
  }

  public static List<String> recommendedActionsForTesting(
      String requestKind,
      String intent,
      ManifestStatus manifestStatus) {
    return recommendedActions(requestKind, intent, manifestStatus);
  }

  private static Path writeSummary(
      String requestKind,
      String request,
      String description,
      String intent,
      Path sourceJson,
      CircuitSpec spec,
      File outputFile,
      Path manifestMd,
      Path manifestJson,
      boolean includeSpecPrefixedReports) throws java.io.IOException {
    return writeSummary(
        requestKind,
        request,
        description,
        intent,
        sourceJson,
        spec,
        outputFile,
        manifestMd,
        manifestJson,
        includeSpecPrefixedReports,
        null);
  }

  private static Path writeSummary(
      String requestKind,
      String request,
      String description,
      String intent,
      Path sourceJson,
      CircuitSpec spec,
      File outputFile,
      Path manifestMd,
      Path manifestJson,
      boolean includeSpecPrefixedReports,
      ImplementationProfile implementationProfile) throws java.io.IOException {
    Path reportDir = Path.of("generated", "reports");
    Files.createDirectories(reportDir);
    Path summaryMd = markdownPath(outputFile);
    Path summaryJson = jsonPath(outputFile);
    List<Path> artifacts = artifactsFor(
        spec, outputFile, manifestMd, manifestJson, summaryMd, summaryJson, includeSpecPrefixedReports);
    ManifestStatus manifestStatus = readManifestStatus(manifestJson);
    Files.writeString(summaryMd, toMarkdown(
        requestKind, request, description, intent, implementationProfile, sourceJson, spec, outputFile,
        manifestMd, manifestStatus, artifacts));
    Files.writeString(summaryJson, toJson(
        requestKind, request, description, intent, implementationProfile, sourceJson, spec, outputFile,
        manifestMd, manifestStatus, artifacts));
    GenerationDeliveryReport.write(
        requestKind,
        request,
        intent,
        implementationProfile,
        spec,
        outputFile,
        summaryMd,
        summaryJson,
        manifestMd,
        manifestJson,
        manifestStatus,
        recommendedActions(requestKind, intent, manifestStatus),
        artifacts);
    return summaryMd;
  }

  private static List<Path> artifactsFor(
      CircuitSpec spec,
      File outputFile,
      Path manifestMd,
      Path manifestJson,
      Path summaryMd,
      Path summaryJson,
      boolean includeSpecPrefixedReports) throws java.io.IOException {
    List<Path> artifacts = new ArrayList<>();
    addIfExists(artifacts, outputFile.toPath());
    addIfExists(artifacts, manifestMd);
    addIfExists(artifacts, manifestJson);
    addIfExists(artifacts, Path.of("generated", "reports", outputStem(outputFile) + "_circuit_truth_table.md"));
    addIfExists(artifacts, Path.of("generated", "reports", outputStem(outputFile) + "_circuit_truth_table.json"));
    Path reportDir = Path.of("generated", "reports");
    if (includeSpecPrefixedReports && Files.isDirectory(reportDir)) {
      try (var stream = Files.list(reportDir)) {
        stream
            .filter(path -> path.getFileName().toString().startsWith(spec.name() + "_"))
            .filter(path -> !path.getFileName().toString().contains("_delivery_report."))
            .filter(path -> !path.equals(summaryMd) && !path.equals(summaryJson))
            .forEach(path -> addIfExists(artifacts, path));
      }
    }
    addIfExists(artifacts, summaryMd);
    addIfExists(artifacts, summaryJson);
    return artifacts.stream()
        .distinct()
        .sorted(Comparator.comparing(Path::toString))
        .toList();
  }

  private static void addIfExists(List<Path> paths, Path path) {
    if (path != null && Files.exists(path)) {
      paths.add(path);
    }
  }

  private static String toMarkdown(
      String requestKind,
      String request,
      String description,
      String intent,
      ImplementationProfile implementationProfile,
      Path sourceJson,
      CircuitSpec spec,
      File outputFile,
      Path manifestMd,
      ManifestStatus manifestStatus,
      List<Path> artifacts) {
    StringBuilder out = new StringBuilder();
    out.append("# Generation summary for ").append(spec.name()).append("\n\n");
    out.append("- Request kind: ").append(requestKind).append("\n");
    out.append("- Request: ").append(request).append("\n");
    out.append("- Description: ").append(description).append("\n");
    out.append("- Intent: ").append(intent).append("\n");
    out.append("- Intent explanation: ").append(describeIntent(intent)).append("\n");
    if (implementationProfile != null) {
      out.append("- Implementation level: ").append(implementationProfile.level()).append("\n");
      out.append("- Target package: ").append(implementationProfile.targetPackage()).append("\n");
      out.append("- Package primitive: ").append(implementationProfile.packagePrimitive()).append("\n");
      out.append("- Routing primitive: ").append(implementationProfile.routingPrimitive()).append("\n");
    }
    out.append("- Recommended next action: ")
        .append(recommendNextAction(requestKind, intent, manifestStatus))
        .append("\n");
    out.append("- Recommended action ids: ")
        .append(String.join(", ", recommendedActions(requestKind, intent, manifestStatus)))
        .append("\n");
    out.append("- Source JSON: ").append(sourceJson == null ? "" : sourceJson).append("\n");
    out.append("- Circuit: ").append(outputFile.getPath()).append("\n");
    out.append("- Manifest: ").append(manifestMd).append("\n");
    out.append("- Manifest complete: ").append(manifestStatus.completeText()).append("\n");
    out.append("- Manifest check count: ").append(manifestStatus.checkCountText()).append("\n");
    out.append("- Manifest issue count: ").append(manifestStatus.issueCountText()).append("\n");
    out.append("- Artifact count: ").append(artifacts.size()).append("\n\n");
    out.append("## Artifacts\n\n");
    out.append("| Path | Exists |\n");
    out.append("| --- | --- |\n");
    for (Path artifact : artifacts) {
      out.append("| ").append(artifact).append(" | ").append(Files.exists(artifact)).append(" |\n");
    }
    return out.toString();
  }

  private static String toJson(
      String requestKind,
      String request,
      String description,
      String intent,
      ImplementationProfile implementationProfile,
      Path sourceJson,
      CircuitSpec spec,
      File outputFile,
      Path manifestMd,
      ManifestStatus manifestStatus,
      List<Path> artifacts) {
    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"requestKind\": \"").append(escape(requestKind)).append("\",\n");
    out.append("  \"request\": \"").append(escape(request)).append("\",\n");
    out.append("  \"description\": \"").append(escape(description)).append("\",\n");
    out.append("  \"intent\": \"").append(escape(intent)).append("\",\n");
    out.append("  \"intentExplanation\": \"").append(escape(describeIntent(intent))).append("\",\n");
    if (implementationProfile != null) {
      out.append("  \"implementationProfile\": {\n");
      out.append("    \"level\": \"").append(escape(implementationProfile.level())).append("\",\n");
      out.append("    \"targetPackage\": \"").append(escape(implementationProfile.targetPackage())).append("\",\n");
      out.append("    \"packagePrimitive\": ").append(implementationProfile.packagePrimitive()).append(",\n");
      out.append("    \"routingPrimitive\": \"").append(escape(implementationProfile.routingPrimitive())).append("\"\n");
      out.append("  },\n");
    }
    out.append("  \"recommendedNextAction\": \"")
        .append(escape(recommendNextAction(requestKind, intent, manifestStatus)))
        .append("\",\n");
    out.append("  \"recommendedActions\": [");
    List<String> actions = recommendedActions(requestKind, intent, manifestStatus);
    for (int i = 0; i < actions.size(); i++) {
      out.append("\"").append(escape(actions.get(i))).append("\"");
      if (i + 1 < actions.size()) {
        out.append(", ");
      }
    }
    out.append("],\n");
    out.append("  \"sourceJson\": \"").append(sourceJson == null ? "" : escape(sourceJson.toString())).append("\",\n");
    out.append("  \"circuitName\": \"").append(escape(spec.name())).append("\",\n");
    out.append("  \"circuitPath\": \"").append(escape(outputFile.getPath())).append("\",\n");
    out.append("  \"manifestPath\": \"").append(escape(manifestMd.toString())).append("\",\n");
    out.append("  \"manifestComplete\": ").append(manifestStatus.completeJson()).append(",\n");
    out.append("  \"manifestCheckCount\": ").append(manifestStatus.checkCountJson()).append(",\n");
    out.append("  \"manifestIssueCount\": ").append(manifestStatus.issueCountJson()).append(",\n");
    out.append("  \"artifactCount\": ").append(artifacts.size()).append(",\n");
    out.append("  \"artifacts\": [\n");
    for (int i = 0; i < artifacts.size(); i++) {
      Path artifact = artifacts.get(i);
      out.append("    {\"path\": \"").append(escape(artifact.toString()))
          .append("\", \"exists\": ").append(Files.exists(artifact)).append("}");
      if (i + 1 < artifacts.size()) {
        out.append(",");
      }
      out.append("\n");
    }
    out.append("  ]\n");
    out.append("}\n");
    return out.toString();
  }

  private static ManifestStatus readManifestStatus(Path manifestJson) {
    if (manifestJson == null || !Files.exists(manifestJson)) {
      return ManifestStatus.unknown();
    }
    try {
      String json = Files.readString(manifestJson);
      Boolean complete = extractBoolean(json, "complete");
      OptionalInt checkCount = extractInt(json, "checkCount");
      OptionalInt issueCount = extractIssueCount(json);
      return new ManifestStatus(complete, checkCount, issueCount);
    } catch (Exception e) {
      return ManifestStatus.unknown();
    }
  }

  private static String describeIntent(String intent) {
    if ("GATE_LEVEL_CIRCUIT".equals(intent)) {
      return "Draw the requested circuit directly with primitive Logisim gates and components.";
    }
    if ("CHIP_LEVEL_CIRCUIT_IMPLEMENTATION".equals(intent)) {
      return "Draw the requested circuit using chip/package-level parts or labeled TTL package gates where possible.";
    }
    if ("GATE_LEVEL_CHIP_IMPLEMENTATION".equals(intent)) {
      return "Draw the internal gate-level implementation of a chip, without treating the chip package itself as the primitive.";
    }
    if ("STRUCTURED_JSON".equals(intent)) {
      return "Compile the provided structured CircuitSpec JSON exactly through validation, layout, and manifest checks.";
    }
    if ("natural_language_ttl_dip".equals(intent)) {
      return "Generate a readable TTL DIP subcircuit/package view with pin, layout, and behavior artifacts.";
    }
    if (intent == null || intent.isBlank()) {
      return "No implementation intent was provided.";
    }
    return "Implementation intent was provided by the caller and preserved in the generated artifact summary.";
  }

  private static String recommendNextAction(
      String requestKind,
      String intent,
      ManifestStatus manifestStatus) {
    if (!Boolean.TRUE.equals(manifestStatus.complete())) {
      return "Open the manifest first and fix its issues before trusting the circuit artifact.";
    }
    if (isTtlDipRequest(requestKind)) {
      return "Open the .circ for a visual editability check, then review the TTL BOM, pin map, DIP layout, and behavior reports.";
    }
    if ("CHIP_LEVEL_CIRCUIT_IMPLEMENTATION".equals(intent)) {
      return "Open the .circ for visual inspection, then review chip/package mapping and BOM reports when present.";
    }
    if ("STRUCTURED_JSON".equals(intent)) {
      return "Compare the source JSON with the generated .circ, then review the manifest and truth-table artifacts.";
    }
    return "Open the .circ in Logisim-evolution for visual inspection, then review manifest and truth-table artifacts.";
  }

  private static List<String> recommendedActions(
      String requestKind,
      String intent,
      ManifestStatus manifestStatus) {
    if (!Boolean.TRUE.equals(manifestStatus.complete())) {
      return List.of("review_manifest", "fix_manifest_issues", "regenerate_circuit");
    }
    if (isTtlDipRequest(requestKind)) {
      return List.of(
          "open_circuit",
          "review_manifest",
          "review_ttl_bom",
          "review_pinmap",
          "review_ttl_dip_layout",
          "review_behavior_report");
    }
    if ("CHIP_LEVEL_CIRCUIT_IMPLEMENTATION".equals(intent)) {
      return List.of("open_circuit", "review_manifest", "review_chip_mapping", "review_ttl_bom");
    }
    if ("STRUCTURED_JSON".equals(intent)) {
      return List.of("compare_source_json", "open_circuit", "review_manifest", "review_truth_table");
    }
    return List.of("open_circuit", "review_manifest", "review_truth_table");
  }

  private static boolean isTtlDipRequest(String requestKind) {
    return "natural_language_ttl_dip".equals(requestKind)
        || (requestKind != null && requestKind.contains("ttl_dip"));
  }

  private static Boolean extractBoolean(String json, String key) {
    String marker = "\"" + key + "\"";
    int start = json.indexOf(marker);
    if (start < 0) {
      return null;
    }
    int colon = json.indexOf(':', start + marker.length());
    if (colon < 0) {
      return null;
    }
    int valueStart = skipWhitespace(json, colon + 1);
    if (json.startsWith("true", valueStart)) {
      return Boolean.TRUE;
    }
    if (json.startsWith("false", valueStart)) {
      return Boolean.FALSE;
    }
    return null;
  }

  private static OptionalInt extractInt(String json, String key) {
    String marker = "\"" + key + "\"";
    int start = json.indexOf(marker);
    if (start < 0) {
      return OptionalInt.empty();
    }
    int colon = json.indexOf(':', start + marker.length());
    if (colon < 0) {
      return OptionalInt.empty();
    }
    int valueStart = skipWhitespace(json, colon + 1);
    int valueEnd = valueStart;
    while (valueEnd < json.length() && Character.isDigit(json.charAt(valueEnd))) {
      valueEnd++;
    }
    if (valueEnd == valueStart) {
      return OptionalInt.empty();
    }
    return OptionalInt.of(Integer.parseInt(json.substring(valueStart, valueEnd)));
  }

  private static OptionalInt extractIssueCount(String json) {
    String marker = "\"issues\"";
    int start = json.indexOf(marker);
    if (start < 0) {
      return OptionalInt.empty();
    }
    int arrayStart = json.indexOf('[', start + marker.length());
    if (arrayStart < 0) {
      return OptionalInt.empty();
    }
    int arrayEnd = json.indexOf(']', arrayStart + 1);
    if (arrayEnd < 0) {
      return OptionalInt.empty();
    }
    String body = json.substring(arrayStart + 1, arrayEnd).trim();
    if (body.isEmpty()) {
      return OptionalInt.of(0);
    }
    int count = 0;
    boolean inString = false;
    for (int i = 0; i < body.length(); i++) {
      char c = body.charAt(i);
      if (c == '"' && (i == 0 || body.charAt(i - 1) != '\\')) {
        inString = !inString;
        if (inString) {
          count++;
        }
      }
    }
    return OptionalInt.of(count);
  }

  private static int skipWhitespace(String text, int index) {
    int current = index;
    while (current < text.length() && Character.isWhitespace(text.charAt(current))) {
      current++;
    }
    return current;
  }

  private static String outputStem(File outputFile) {
    String name = outputFile.getName();
    if (name.endsWith(".circ")) {
      return name.substring(0, name.length() - ".circ".length());
    }
    return name;
  }

  private static String escape(String text) {
    return text
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t");
  }

  public record ManifestStatus(Boolean complete, OptionalInt checkCount, OptionalInt issueCount) {
    static ManifestStatus unknown() {
      return new ManifestStatus(null, OptionalInt.empty(), OptionalInt.empty());
    }

    String completeText() {
      return complete == null ? "unknown" : complete.toString();
    }

    String checkCountText() {
      return checkCount.isPresent() ? Integer.toString(checkCount.getAsInt()) : "unknown";
    }

    String issueCountText() {
      return issueCount.isPresent() ? Integer.toString(issueCount.getAsInt()) : "unknown";
    }

    String completeJson() {
      return complete == null ? "null" : complete.toString();
    }

    String checkCountJson() {
      return checkCount.isPresent() ? Integer.toString(checkCount.getAsInt()) : "null";
    }

    String issueCountJson() {
      return issueCount.isPresent() ? Integer.toString(issueCount.getAsInt()) : "null";
    }
  }
}
