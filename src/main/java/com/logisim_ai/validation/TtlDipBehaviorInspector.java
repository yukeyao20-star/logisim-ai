package com.logisim_ai.validation;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.PortDirection;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Writes truth-table evidence for the CircuitSpec behind a TTL DIP subcircuit view. */
public final class TtlDipBehaviorInspector {
  private TtlDipBehaviorInspector() {}

  public static Report writeReports(CircuitSpec spec, File circFile) throws java.io.IOException {
    Report report = inspect(spec, circFile);
    Path reportDir = Path.of("generated", "reports");
    Files.createDirectories(reportDir);
    String base = spec.name() + "_ttl_dip_subckt_behavior";
    Files.writeString(reportDir.resolve(base + ".md"), toMarkdown(report));
    Files.writeString(reportDir.resolve(base + ".json"), toJson(report));
    if (!report.clean()) {
      throw new IllegalStateException(report.toHumanString());
    }
    return report;
  }

  public static Report inspect(CircuitSpec spec, File circFile) throws java.io.IOException {
    TtlDipPinMapInspector.Report pinMapReport = TtlDipPinMapInspector.inspect(spec, circFile);
    List<ExternalPort> inputs = spec.externalPorts().stream()
        .filter(port -> port.direction() == PortDirection.INPUT)
        .toList();
    List<ExternalPort> outputs = spec.externalPorts().stream()
        .filter(port -> port.direction() == PortDirection.OUTPUT)
        .toList();

    List<Row> rows = new java.util.ArrayList<>();
    if (inputs.size() > 12) {
      return new Report(spec.name(), circFile.getPath(), false, List.of(),
          List.of("Too many inputs for exhaustive TTL DIP behavior evidence: " + inputs.size()));
    }
    for (int mask = 0; mask < (1 << inputs.size()); mask++) {
      Map<String, Boolean> inputValues = new LinkedHashMap<>();
      for (int i = 0; i < inputs.size(); i++) {
        int shift = inputs.size() - i - 1;
        inputValues.put(inputs.get(i).id(), ((mask >> shift) & 1) == 1);
      }
      Map<String, Boolean> outputValues = CircuitTruthTableEvaluator.evaluate(spec, inputValues);
      Map<String, Boolean> visibleOutputs = new LinkedHashMap<>();
      for (ExternalPort output : outputs) {
        visibleOutputs.put(output.id(), outputValues.get(output.id()));
      }
      rows.add(new Row(inputValues, visibleOutputs));
    }

    List<String> issues = new java.util.ArrayList<>();
    if (!pinMapReport.clean()) {
      issues.add("PinMap mismatch: " + pinMapReport.toHumanString());
    }
    if (rows.isEmpty()) {
      issues.add("No behavior rows generated.");
    }
    return new Report(spec.name(), circFile.getPath(), pinMapReport.clean(), rows, List.copyOf(issues));
  }

  private static String toMarkdown(Report report) {
    StringBuilder out = new StringBuilder();
    out.append("# TTL DIP behavior evidence for ").append(report.circuitName()).append("\n\n");
    out.append("- Circuit file: ").append(report.circuitFile()).append("\n");
    out.append("- PinMap consistent: ").append(report.pinMapConsistent()).append("\n");
    out.append("- Truth-table rows: ").append(report.rows().size()).append("\n\n");
    if (!report.issues().isEmpty()) {
      out.append("## Issues\n\n");
      for (String issue : report.issues()) {
        out.append("- ").append(issue).append("\n");
      }
      out.append("\n");
    }
    out.append("| Inputs | Outputs |\n");
    out.append("| --- | --- |\n");
    for (Row row : report.rows()) {
      out.append("| ")
          .append(formatValues(row.inputs()))
          .append(" | ")
          .append(formatValues(row.outputs()))
          .append(" |\n");
    }
    return out.toString();
  }

  private static String toJson(Report report) {
    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"circuitName\": \"").append(escape(report.circuitName())).append("\",\n");
    out.append("  \"circuitFile\": \"").append(escape(report.circuitFile())).append("\",\n");
    out.append("  \"pinMapConsistent\": ").append(report.pinMapConsistent()).append(",\n");
    out.append("  \"rowCount\": ").append(report.rows().size()).append(",\n");
    out.append("  \"rows\": [\n");
    for (int i = 0; i < report.rows().size(); i++) {
      Row row = report.rows().get(i);
      out.append("    {\"inputs\": ")
          .append(valuesJson(row.inputs()))
          .append(", \"outputs\": ")
          .append(valuesJson(row.outputs()))
          .append("}");
      if (i + 1 < report.rows().size()) {
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

  private static String formatValues(Map<String, Boolean> values) {
    return values.entrySet().stream()
        .map(entry -> entry.getKey() + "=" + (entry.getValue() ? "1" : "0"))
        .collect(java.util.stream.Collectors.joining(", "));
  }

  private static String valuesJson(Map<String, Boolean> values) {
    StringBuilder out = new StringBuilder();
    out.append("{");
    int index = 0;
    for (Map.Entry<String, Boolean> entry : values.entrySet()) {
      out.append("\"").append(escape(entry.getKey())).append("\": ").append(entry.getValue());
      if (++index < values.size()) {
        out.append(", ");
      }
    }
    out.append("}");
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

  public record Row(Map<String, Boolean> inputs, Map<String, Boolean> outputs) {}

  public record Report(
      String circuitName,
      String circuitFile,
      boolean pinMapConsistent,
      List<Row> rows,
      List<String> issues) {
    public boolean clean() {
      return issues.isEmpty();
    }

    public String toHumanString() {
      if (clean()) {
        return "[OK] " + circuitName + " TTL DIP behavior evidence rows=" + rows.size();
      }
      return "[FAIL] " + circuitName + "\n - " + String.join("\n - ", issues);
    }
  }
}
