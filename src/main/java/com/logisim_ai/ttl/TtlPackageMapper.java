package com.logisim_ai.ttl;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Extracts and reports chip/gate assignments for repeated-gate TTL packages. */
public final class TtlPackageMapper {
  private static final Pattern LABEL = Pattern.compile("^U([1-9][0-9]*)([A-Z][A-Z0-9]*)$");

  private TtlPackageMapper() {}

  public static List<GateAssignment> assignments(CircuitSpec spec, TtlPackageSpec packageSpec) {
    List<GateAssignment> assignments = new ArrayList<>();
    for (ComponentSpec component : spec.components()) {
      if (!component.factoryName().equals(packageSpec.gateFactoryName())) {
        continue;
      }
      Matcher matcher = LABEL.matcher(component.label() == null ? "" : component.label());
      if (!matcher.matches()) {
        continue;
      }
      int chipNumber = Integer.parseInt(matcher.group(1));
      String gateName = matcher.group(2);
      int gateIndex = packageSpec.gateNames().indexOf(gateName);
      if (gateIndex < 0) {
        continue;
      }
      String chipId = "U" + chipNumber;
      assignments.add(new GateAssignment(
          component.id(),
          component.label(),
          chipId,
          gateName,
          chipNumber,
          gateIndex));
    }
    assignments.sort(Comparator
        .comparingInt(GateAssignment::chipNumber)
        .thenComparingInt(GateAssignment::gateIndex));
    return List.copyOf(assignments);
  }

  public static void requireContiguousAssignments(CircuitSpec spec, TtlPackageSpec packageSpec) {
    List<GateAssignment> assignments = assignments(spec, packageSpec);
    if (assignments.isEmpty()) {
      throw new IllegalStateException("No " + packageSpec.packageType()
          + " gate assignments found in " + spec.name());
    }

    long gateCount = spec.components().stream()
        .filter(component -> component.factoryName().equals(packageSpec.gateFactoryName()))
        .count();
    if (gateCount != assignments.size()) {
      throw new IllegalStateException("Some " + packageSpec.gateFactoryName()
          + " components lack " + packageSpec.packageType()
          + " assignments: gateCount=" + gateCount
          + " assignments=" + assignments.size());
    }

    for (int i = 0; i < assignments.size(); i++) {
      GateAssignment actual = assignments.get(i);
      int blockStart = (i / packageSpec.gatesPerChip()) * packageSpec.gatesPerChip();
      int expectedChip = assignments.get(blockStart).chipNumber();
      String expectedGate = packageSpec.gateNames().get(i % packageSpec.gatesPerChip());
      String expectedLabel = "U" + expectedChip + expectedGate;
      if (!actual.label().equals(expectedLabel)) {
        throw new IllegalStateException("Non-contiguous " + packageSpec.packageType()
            + " label at index " + i + ": expected=" + expectedLabel
            + " actual=" + actual.label());
      }
    }
  }

  public static String toMarkdown(CircuitSpec spec, TtlPackageSpec packageSpec) {
    List<GateAssignment> assignments = assignments(spec, packageSpec);
    StringBuilder out = new StringBuilder();
    out.append("# ").append(packageSpec.packageType())
        .append(" mapping for ").append(spec.name()).append("\n\n");
    out.append("| Component ID | ").append(packageSpec.packageType())
        .append(" Gate | Chip | Gate |\n");
    out.append("| --- | --- | --- | --- |\n");
    for (GateAssignment assignment : assignments) {
      out.append("| ")
          .append(assignment.componentId()).append(" | ")
          .append(assignment.label()).append(" | ")
          .append(assignment.chipId()).append(" | ")
          .append(assignment.gateName()).append(" |\n");
    }
    out.append("\n");
    out.append("Total ").append(packageSpec.gateKindLabel()).append(" gates: ")
        .append(assignments.size()).append("\n\n");
    out.append("Total ").append(packageSpec.packageType()).append(" chips: ")
        .append(chipCount(assignments)).append("\n");
    return out.toString();
  }

  public static String toJson(CircuitSpec spec, TtlPackageSpec packageSpec) {
    List<GateAssignment> assignments = assignments(spec, packageSpec);
    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"circuitName\": \"").append(escape(spec.name())).append("\",\n");
    out.append("  \"packageType\": \"").append(escape(packageSpec.packageType())).append("\",\n");
    out.append("  \"gateKind\": \"").append(escape(packageSpec.gateKindLabel())).append("\",\n");
    out.append("  \"gateCount\": ").append(assignments.size()).append(",\n");
    if (packageSpec.packageType().equals("7400")) {
      out.append("  \"nandGateCount\": ").append(assignments.size()).append(",\n");
    }
    out.append("  \"chipCount\": ").append(chipCount(assignments)).append(",\n");
    out.append("  \"assignments\": [\n");
    for (int i = 0; i < assignments.size(); i++) {
      GateAssignment assignment = assignments.get(i);
      out.append("    {\n");
      out.append("      \"componentId\": \"").append(escape(assignment.componentId())).append("\",\n");
      out.append("      \"label\": \"").append(escape(assignment.label())).append("\",\n");
      out.append("      \"chipId\": \"").append(escape(assignment.chipId())).append("\",\n");
      out.append("      \"gateName\": \"").append(escape(assignment.gateName())).append("\",\n");
      out.append("      \"chipNumber\": ").append(assignment.chipNumber()).append(",\n");
      out.append("      \"gateIndex\": ").append(assignment.gateIndex()).append("\n");
      out.append("    }");
      if (i + 1 < assignments.size()) {
        out.append(",");
      }
      out.append("\n");
    }
    out.append("  ]\n");
    out.append("}\n");
    return out.toString();
  }

  public static Bom bom(CircuitSpec spec, TtlPackageSpec packageSpec) {
    List<GateAssignment> assignments = assignments(spec, packageSpec);
    int chipCount = chipCount(assignments);
    int totalGateCapacity = chipCount * packageSpec.gatesPerChip();
    int usedGateCount = assignments.size();
    Set<String> usedLabels = new LinkedHashSet<>();
    for (GateAssignment assignment : assignments) {
      usedLabels.add(assignment.label());
    }
    List<String> unusedGateLabels = new ArrayList<>();
    for (int chip : usedChipNumbers(assignments)) {
      for (String gateName : packageSpec.gateNames()) {
        String label = "U" + chip + gateName;
        if (!usedLabels.contains(label)) {
          unusedGateLabels.add(label);
        }
      }
    }
    return new Bom(packageSpec.packageType(), chipCount, totalGateCapacity, usedGateCount,
        totalGateCapacity - usedGateCount, List.copyOf(unusedGateLabels));
  }

  public static String toBomMarkdown(CircuitSpec spec, TtlPackageSpec packageSpec) {
    Bom bom = bom(spec, packageSpec);
    StringBuilder out = new StringBuilder();
    out.append("# BOM for ").append(spec.name()).append("\n\n");
    out.append("| Package | Chips | Used ").append(packageSpec.gateKindLabel())
        .append(" gates | Total ").append(packageSpec.gateKindLabel())
        .append(" capacity | Unused ").append(packageSpec.gateKindLabel()).append(" gates |\n");
    out.append("| --- | ---: | ---: | ---: | ---: |\n");
    out.append("| ")
        .append(bom.packageType()).append(" | ")
        .append(bom.chipCount()).append(" | ")
        .append(bom.usedGateCount()).append(" | ")
        .append(bom.totalGateCapacity()).append(" | ")
        .append(bom.unusedGateCount()).append(" |\n\n");
    out.append("Summary: ")
        .append(bom.packageType())
        .append(" x ")
        .append(bom.chipCount())
        .append("\n");
    out.append("Unused gates: ")
        .append(bom.unusedGateLabels().isEmpty()
            ? "none"
            : String.join(", ", bom.unusedGateLabels()))
        .append("\n");
    return out.toString();
  }

  public static String toBomJson(CircuitSpec spec, TtlPackageSpec packageSpec) {
    Bom bom = bom(spec, packageSpec);
    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"circuitName\": \"").append(escape(spec.name())).append("\",\n");
    out.append("  \"items\": [\n");
    out.append("    {\n");
    out.append("      \"packageType\": \"").append(escape(bom.packageType())).append("\",\n");
    out.append("      \"gateKind\": \"").append(escape(packageSpec.gateKindLabel())).append("\",\n");
    out.append("      \"chipCount\": ").append(bom.chipCount()).append(",\n");
    out.append("      \"usedGateCount\": ").append(bom.usedGateCount()).append(",\n");
    out.append("      \"totalGateCapacity\": ").append(bom.totalGateCapacity()).append(",\n");
    out.append("      \"unusedGateCount\": ").append(bom.unusedGateCount()).append(",\n");
    out.append("      \"unusedGateLabels\": [");
    for (int i = 0; i < bom.unusedGateLabels().size(); i++) {
      if (i > 0) {
        out.append(", ");
      }
      out.append("\"").append(escape(bom.unusedGateLabels().get(i))).append("\"");
    }
    out.append("]\n");
    out.append("    }\n");
    out.append("  ]\n");
    out.append("}\n");
    return out.toString();
  }

  private static int chipCount(List<GateAssignment> assignments) {
    return usedChipNumbers(assignments).size();
  }

  private static List<Integer> usedChipNumbers(List<GateAssignment> assignments) {
    Set<Integer> chipNumbers = new LinkedHashSet<>();
    for (GateAssignment assignment : assignments) {
      chipNumbers.add(assignment.chipNumber());
    }
    return List.copyOf(chipNumbers);
  }

  private static String escape(String text) {
    StringBuilder escaped = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      switch (ch) {
        case '\\' -> escaped.append("\\\\");
        case '"' -> escaped.append("\\\"");
        case '\n' -> escaped.append("\\n");
        case '\r' -> escaped.append("\\r");
        case '\t' -> escaped.append("\\t");
        default -> escaped.append(ch);
      }
    }
    return escaped.toString();
  }

  public record GateAssignment(
      String componentId,
      String label,
      String chipId,
      String gateName,
      int chipNumber,
      int gateIndex) {}

  public record Bom(
      String packageType,
      int chipCount,
      int totalGateCapacity,
      int usedGateCount,
      int unusedGateCount,
      List<String> unusedGateLabels) {}
}
