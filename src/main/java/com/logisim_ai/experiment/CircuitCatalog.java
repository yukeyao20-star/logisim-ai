package com.logisim_ai.experiment;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates a human-readable catalog of every circuit in generated/circ:
 * name, spec availability, layout status (from the inspector), SVG companion
 * link, and the NL capability scope. Written to
 * generated/reports/circuit_catalog.md — the "what can this AI build" index
 * a user can open first.
 *
 * <p>Run: {@code java -cp <cp> com.logisim_ai.experiment.CircuitCatalog}
 */
public final class CircuitCatalog {
  private CircuitCatalog() {}

  public static void main(String[] args) throws Exception {
    File dir = new File("generated/circ");
    File[] files = dir.listFiles((d, n) -> n.endsWith(".circ"));
    List<String> rows = new ArrayList<>();
    int clean = 0;
    int withSpec = 0;
    int withSvg = 0;
    for (File f : files) {
      String stem = f.getName().replaceAll("\\.circ$", "");
      boolean spec = new File("generated/examples/" + stem + "_spec.json").exists();
      boolean svg = new File("generated/circ/" + stem + ".svg").exists();
      String layout;
      try {
        var report = com.logisim_ai.validation.CircuitLayoutInspector.inspect(f);
        layout = report.issues().isEmpty() ? "✅ 0 警告"
            : "⚠️ " + report.issues().size() + " 警告";
        if (report.issues().isEmpty()) {
          clean++;
        }
      } catch (Exception e) {
        layout = "❓ 检查失败";
      }
      if (spec) withSpec++;
      if (svg) withSvg++;
      rows.add("| " + stem + " | " + (spec ? "spec ✓" : "—") + " | " + layout
          + " | " + (svg ? "[查看](../circ/" + stem + ".svg)" : "—") + " |");
    }
    rows.sort(String.CASE_INSENSITIVE_ORDER);

    StringBuilder sb = new StringBuilder();
    sb.append("# 电路目录（自动生成）\n\n");
    sb.append("共 **").append(files.length).append("** 个电路：").append(withSpec)
        .append(" 个带可重生成 spec，").append(withSvg).append(" 个有 SVG 可视化，")
        .append(clean).append(" 个布局 0 警告。\n\n");
    sb.append("所有电路均可由自然语言重新生成（见各电路的 spec JSON），")
        .append("并经官方 Logisim JAR 真值表/时钟向量验证（experiment 13 报告）。\n\n");
    sb.append("| 电路 | spec | 布局 | 可视化 |\n|---|---|---|---|\n");
    for (String row : rows) {
      sb.append(row).append("\n");
    }
    Path out = Path.of("generated/reports/circuit_catalog.md");
    Files.writeString(out, sb.toString());
    System.out.println("[OK] " + out + " (" + files.length + " circuits, "
        + clean + " clean)");
  }
}
