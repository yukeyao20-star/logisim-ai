package com.logisim_ai.ttl;

import com.logisim_ai.ir.CircuitSpec;
import java.util.ArrayList;
import java.util.List;

/** Builds aggregate reports for circuits that use more than one TTL package family. */
public final class TtlMultiPackageReport {
  private TtlMultiPackageReport() {}

  public static List<TtlPackageSpec> standardPackages() {
    return List.of(
        TtlPackageSpec.ttl7400(),
        TtlPackageSpec.ttl7402(),
        TtlPackageSpec.ttl7404(),
        TtlPackageSpec.ttl7408(),
        TtlPackageSpec.ttl7432(),
        TtlPackageSpec.ttl7486());
  }

  public static List<Item> items(CircuitSpec spec) {
    List<Item> items = new ArrayList<>();
    for (TtlPackageSpec packageSpec : standardPackages()) {
      if (TtlPackageMapper.assignments(spec, packageSpec).isEmpty()) {
        continue;
      }
      TtlPackageMapper.Bom bom = TtlPackageMapper.bom(spec, packageSpec);
      items.add(new Item(
          packageSpec.packageType(),
          packageSpec.gateKindLabel(),
          bom.chipCount(),
          bom.usedGateCount(),
          bom.totalGateCapacity(),
          bom.unusedGateCount(),
          bom.unusedGateLabels()));
    }
    return List.copyOf(items);
  }

  public static boolean hasMultiplePackageFamilies(CircuitSpec spec) {
    return items(spec).size() > 1;
  }

  public static String toMarkdown(CircuitSpec spec) {
    List<Item> items = items(spec);
    StringBuilder out = new StringBuilder();
    out.append("# TTL BOM for ").append(spec.name()).append("\n\n");
    out.append("| Package | Gate kind | Chips | Used gates | Total capacity | Unused gates |\n");
    out.append("| --- | --- | ---: | ---: | ---: | ---: |\n");
    int totalChips = 0;
    int totalUsedGates = 0;
    int totalCapacity = 0;
    int totalUnusedGates = 0;
    for (Item item : items) {
      totalChips += item.chipCount();
      totalUsedGates += item.usedGateCount();
      totalCapacity += item.totalGateCapacity();
      totalUnusedGates += item.unusedGateCount();
      out.append("| ")
          .append(item.packageType()).append(" | ")
          .append(item.gateKind()).append(" | ")
          .append(item.chipCount()).append(" | ")
          .append(item.usedGateCount()).append(" | ")
          .append(item.totalGateCapacity()).append(" | ")
          .append(item.unusedGateCount()).append(" |\n");
    }
    out.append("| **Total** |  | ")
        .append(totalChips).append(" | ")
        .append(totalUsedGates).append(" | ")
        .append(totalCapacity).append(" | ")
        .append(totalUnusedGates).append(" |\n\n");
    out.append("Summary: ");
    if (items.isEmpty()) {
      out.append("no TTL packages\n");
    } else {
      List<String> summaries = new ArrayList<>();
      for (Item item : items) {
        summaries.add(item.packageType() + " x " + item.chipCount());
      }
      out.append(String.join(", ", summaries)).append("\n");
    }
    out.append("\n");
    out.append("Unused gates by package:\n\n");
    for (Item item : items) {
      out.append("- ")
          .append(item.packageType())
          .append(": ")
          .append(item.unusedGateLabels().isEmpty()
              ? "none"
              : String.join(", ", item.unusedGateLabels()))
          .append("\n");
    }
    return out.toString();
  }

  public static String toJson(CircuitSpec spec) {
    List<Item> items = items(spec);
    int totalChips = 0;
    int totalUsedGates = 0;
    int totalCapacity = 0;
    int totalUnusedGates = 0;
    for (Item item : items) {
      totalChips += item.chipCount();
      totalUsedGates += item.usedGateCount();
      totalCapacity += item.totalGateCapacity();
      totalUnusedGates += item.unusedGateCount();
    }

    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"circuitName\": \"").append(escape(spec.name())).append("\",\n");
    out.append("  \"packageFamilyCount\": ").append(items.size()).append(",\n");
    out.append("  \"totalChipCount\": ").append(totalChips).append(",\n");
    out.append("  \"totalUsedGateCount\": ").append(totalUsedGates).append(",\n");
    out.append("  \"totalGateCapacity\": ").append(totalCapacity).append(",\n");
    out.append("  \"totalUnusedGateCount\": ").append(totalUnusedGates).append(",\n");
    out.append("  \"items\": [\n");
    for (int i = 0; i < items.size(); i++) {
      Item item = items.get(i);
      out.append("    {\n");
      out.append("      \"packageType\": \"").append(escape(item.packageType())).append("\",\n");
      out.append("      \"gateKind\": \"").append(escape(item.gateKind())).append("\",\n");
      out.append("      \"chipCount\": ").append(item.chipCount()).append(",\n");
      out.append("      \"usedGateCount\": ").append(item.usedGateCount()).append(",\n");
      out.append("      \"totalGateCapacity\": ").append(item.totalGateCapacity()).append(",\n");
      out.append("      \"unusedGateCount\": ").append(item.unusedGateCount()).append(",\n");
      out.append("      \"unusedGateLabels\": [");
      for (int j = 0; j < item.unusedGateLabels().size(); j++) {
        if (j > 0) {
          out.append(", ");
        }
        out.append("\"").append(escape(item.unusedGateLabels().get(j))).append("\"");
      }
      out.append("]\n");
      out.append("    }");
      if (i + 1 < items.size()) {
        out.append(",");
      }
      out.append("\n");
    }
    out.append("  ]\n");
    out.append("}\n");
    return out.toString();
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

  public record Item(
      String packageType,
      String gateKind,
      int chipCount,
      int usedGateCount,
      int totalGateCapacity,
      int unusedGateCount,
      List<String> unusedGateLabels) {}
}
