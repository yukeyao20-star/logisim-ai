package com.logisim_ai.validation;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ttl.TtlDipWiringReport;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Writes a delivery manifest for TTL DIP subcircuit artifacts and checks. */
public final class TtlDipArtifactManifest {
  private TtlDipArtifactManifest() {}

  public static Report writeReports(CircuitSpec spec, File circFile) throws java.io.IOException {
    Report report = inspect(spec, circFile);
    Path reportDir = Path.of("generated", "reports");
    Files.createDirectories(reportDir);
    String base = spec.name() + "_ttl_dip_subckt_manifest";
    Files.writeString(reportDir.resolve(base + ".md"), toMarkdown(report));
    Files.writeString(reportDir.resolve(base + ".json"), toJson(report));
    if (!report.clean()) {
      throw new IllegalStateException(report.toHumanString());
    }
    return report;
  }

  public static Report inspect(CircuitSpec spec, File circFile) {
    List<Item> items = expectedItems(spec, circFile);
    boolean artifactsComplete = items.stream().allMatch(item -> Files.exists(Path.of(item.path())));
    List<Check> checks = checks(spec, circFile, artifactsComplete);
    return inspect(spec.name(), items, checks);
  }

  public static Report inspect(String circuitName, List<Item> items, List<Check> checks) {
    List<String> issues = new ArrayList<>();
    for (Item item : items) {
      if (!Files.exists(Path.of(item.path()))) {
        issues.add("Missing artifact: " + item.path());
      }
    }
    for (Check check : checks) {
      if (!check.passed()) {
        issues.add("Check failed: " + check.name() + " - " + check.detail());
      }
    }
    return new Report(circuitName, List.copyOf(items), List.copyOf(checks), List.copyOf(issues));
  }

  private static List<Check> checks(CircuitSpec spec, File circFile, boolean artifactsComplete) {
    List<Check> checks = new ArrayList<>();
    checks.add(new Check(
        "artifacts_complete",
        artifactsComplete,
        artifactsComplete ? "all expected artifacts exist" : "one or more expected artifacts are missing"));
    checks.add(safeCheck("layout_clean", () ->
        CircuitLayoutInspector.inspect(circFile).toHumanString(), () ->
        CircuitLayoutInspector.inspect(circFile).clean()));
    checks.add(safeCheck("structure_clean", () ->
        TtlDipSubcircuitInspector.inspect(circFile).toHumanString(), () ->
        TtlDipSubcircuitInspector.inspect(circFile).clean()));
    checks.add(safeCheck("pinmap_consistent", () ->
        TtlDipPinMapInspector.inspect(spec, circFile).toHumanString(), () ->
        TtlDipPinMapInspector.inspect(spec, circFile).clean()));
    checks.add(safeCheck("behavior_evidence_clean", () ->
        TtlDipBehaviorInspector.inspect(spec, circFile).toHumanString(), () ->
        TtlDipBehaviorInspector.inspect(spec, circFile).clean()));
    return List.copyOf(checks);
  }

  private static Check safeCheck(String name, CheckedString detail, CheckedBoolean passed) {
    try {
      String detailText = detail.get();
      return new Check(name, passed.get(), detailText);
    } catch (Exception e) {
      return new Check(name, false, e.getClass().getSimpleName() + ": " + e.getMessage());
    }
  }

  private static List<Item> expectedItems(CircuitSpec spec, File circFile) {
    String name = spec.name();
    List<Item> items = new ArrayList<>();
    items.add(new Item("circuit", circFile.getPath()));
    items.add(report("aggregate_bom_markdown", name + "_ttl_bom.md"));
    items.add(report("aggregate_bom_json", name + "_ttl_bom.json"));
    items.add(report("dip_wiring_markdown", name + "_ttl_dip.md"));
    items.add(report("dip_wiring_json", name + "_ttl_dip.json"));
    items.add(report("dip_layout_markdown", name + "_ttl_dip_layout.md"));
    items.add(report("dip_layout_json", name + "_ttl_dip_layout.json"));
    items.add(report("pinmap_markdown", name + "_ttl_dip_subckt_pinmap.md"));
    items.add(report("pinmap_json", name + "_ttl_dip_subckt_pinmap.json"));
    items.add(report("behavior_markdown", name + "_ttl_dip_subckt_behavior.md"));
    items.add(report("behavior_json", name + "_ttl_dip_subckt_behavior.json"));

    Set<String> packageTypes = new LinkedHashSet<>();
    for (TtlDipWiringReport.Chip chip : TtlDipWiringReport.chips(spec)) {
      packageTypes.add(chip.packageType());
    }
    for (String packageType : packageTypes) {
      items.add(report(packageType + "_mapping_markdown", name + "_" + packageType + "_mapping.md"));
      items.add(report(packageType + "_mapping_json", name + "_" + packageType + "_mapping.json"));
      items.add(report(packageType + "_bom_markdown", name + "_" + packageType + "_bom.md"));
      items.add(report(packageType + "_bom_json", name + "_" + packageType + "_bom.json"));
    }
    return List.copyOf(items);
  }

  private static Item report(String kind, String fileName) {
    return new Item(kind, Path.of("generated", "reports", fileName).toString());
  }

  private static String toMarkdown(Report report) {
    StringBuilder out = new StringBuilder();
    out.append("# TTL DIP artifact manifest for ").append(report.circuitName()).append("\n\n");
    out.append("- Artifact count: ").append(report.items().size()).append("\n");
    out.append("- Check count: ").append(report.checks().size()).append("\n");
    out.append("- Complete: ").append(report.clean()).append("\n\n");
    if (!report.issues().isEmpty()) {
      out.append("## Issues\n\n");
      for (String issue : report.issues()) {
        out.append("- ").append(issue).append("\n");
      }
      out.append("\n");
    }
    out.append("## Checks\n\n");
    out.append("| Check | Passed | Detail |\n");
    out.append("| --- | --- | --- |\n");
    for (Check check : report.checks()) {
      out.append("| ")
          .append(check.name())
          .append(" | ")
          .append(check.passed())
          .append(" | ")
          .append(check.detail().replace("\n", "<br>"))
          .append(" |\n");
    }
    out.append("\n");
    out.append("## Artifacts\n\n");
    out.append("| Kind | Path | Exists |\n");
    out.append("| --- | --- | --- |\n");
    for (Item item : report.items()) {
      out.append("| ")
          .append(item.kind())
          .append(" | ")
          .append(item.path())
          .append(" | ")
          .append(Files.exists(Path.of(item.path())))
          .append(" |\n");
    }
    return out.toString();
  }

  private static String toJson(Report report) {
    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"circuitName\": \"").append(escape(report.circuitName())).append("\",\n");
    out.append("  \"complete\": ").append(report.clean()).append(",\n");
    out.append("  \"artifactCount\": ").append(report.items().size()).append(",\n");
    out.append("  \"checkCount\": ").append(report.checks().size()).append(",\n");
    out.append("  \"checks\": [\n");
    for (int i = 0; i < report.checks().size(); i++) {
      Check check = report.checks().get(i);
      out.append("    {\"name\": \"").append(escape(check.name()))
          .append("\", \"passed\": ").append(check.passed())
          .append(", \"detail\": \"").append(escape(check.detail()))
          .append("\"}");
      if (i + 1 < report.checks().size()) {
        out.append(",");
      }
      out.append("\n");
    }
    out.append("  ],\n");
    out.append("  \"artifacts\": [\n");
    for (int i = 0; i < report.items().size(); i++) {
      Item item = report.items().get(i);
      out.append("    {\"kind\": \"").append(escape(item.kind()))
          .append("\", \"path\": \"").append(escape(item.path()))
          .append("\", \"exists\": ").append(Files.exists(Path.of(item.path())))
          .append("}");
      if (i + 1 < report.items().size()) {
        out.append(",");
      }
      out.append("\n");
    }
    out.append("  ],\n");
    out.append("  \"issues\": [");
    for (int i = 0; i < report.issues().size(); i++) {
      out.append("\"").append(escape(report.issues().get(i))).append("\"");
      if (i + 1 < report.issues().size()) {
        out.append(", ");
      }
    }
    out.append("]\n");
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

  public record Item(String kind, String path) {}

  public record Check(String name, boolean passed, String detail) {}

  private interface CheckedString {
    String get() throws Exception;
  }

  private interface CheckedBoolean {
    boolean get() throws Exception;
  }

  public record Report(String circuitName, List<Item> items, List<Check> checks, List<String> issues) {
    public boolean clean() {
      return issues.isEmpty() && checks.stream().allMatch(Check::passed);
    }

    public String toHumanString() {
      if (clean()) {
        return "[OK] " + circuitName + " TTL DIP artifact manifest items=" + items.size();
      }
      return "[FAIL] " + circuitName + "\n - " + String.join("\n - ", issues);
    }
  }
}
