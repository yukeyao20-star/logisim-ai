package com.logisim_ai.experiment;

import com.cburch.logisim.Main;
import com.logisim_ai.ai.CircuitSpecJsonParser;
import com.logisim_ai.ir.CircuitSpec;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Batch-regenerate legacy .circ files from their persisted spec JSONs,
 * re-persisting each spec first (so schema upgrades such as the clock flag
 * stick), compiling via the same route the NL flow picks
 * ({@link GenerateAndGate#usesDirectCompiler}), then reporting layout-clean
 * counts. Used in rounds 62 (layout debt) and 68 (clock flag / square
 * layout); keep in the experiment package so future sessions retain it.
 *
 * <p>Run: {@code java -cp <classpath> com.logisim_ai.experiment.RegenSweep}
 */
public class RegenSweep {
  public static void main(String[] args) throws Exception {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");
    File dir = new File("generated/circ");
    File[] files = dir.listFiles((d, n) -> n.endsWith(".circ"));
    List<String> targets = new ArrayList<>();
    for (File f : files) {
      String stem = f.getName().replaceAll("\\.circ$", "");
      File spec = new File("generated/examples/" + stem + "_spec.json");
      if (spec.exists()) {
        targets.add(stem);
      }
    }
    int done = 0;
    int cleanAfter = 0;
    List<String> stillDirty = new ArrayList<>();
    List<String> failed = new ArrayList<>();
    for (String stem : targets) {
      try {
        String json = "generated/examples/" + stem + "_spec.json";
        String out = "generated/circ/" + stem + ".circ";
        CircuitSpec spec = CircuitSpecJsonParser.parse(
            java.nio.file.Files.readString(java.nio.file.Path.of(json)));
        // Re-persist so schema upgrades (e.g. the clock flag) stick.
        java.nio.file.Files.writeString(java.nio.file.Path.of(json),
            CircuitSpecJsonParser.toJson(spec));
        File outFile = new File(out);
        if (GenerateAndGate.usesDirectCompiler(spec)) {
          com.logisim_ai.compiler.DirectMultiChipCompiler.compile(spec, outFile);
        } else {
          com.logisim_ai.compiler.LogisimCompiler.compileNetlist(spec, outFile);
        }
        java.nio.file.Files.writeString(
            java.nio.file.Path.of("generated/reports/" + stem + "_regen_flag.txt"),
            "regenerated");
        new java.io.File("generated/reports/" + stem + "_regen_flag.txt").delete();
        done++;
        var report = com.logisim_ai.validation.CircuitLayoutInspector.inspect(new File(out));
        // Render an SVG companion so every circuit is viewable in a browser.
        try {
          String svg = CircSvg.render(
              java.nio.file.Files.readString(java.nio.file.Path.of(out)));
          java.nio.file.Files.writeString(
              java.nio.file.Path.of("generated/circ/" + stem + ".svg"), svg);
        } catch (Exception ignored) {
          // SVG is a convenience artifact; never fail the sweep on it.
        }
        if (report.issues().isEmpty()) {
          cleanAfter++;
        } else {
          stillDirty.add(stem + " (" + report.issues().size() + ")");
        }
      } catch (Exception e) {
        failed.add(stem + ": " + e.getClass().getSimpleName());
      }
    }
    System.out.println("[regen] done=" + done + " clean=" + cleanAfter
        + " stillDirty=" + stillDirty.size() + " failed=" + failed.size());
    stillDirty.stream().limit(15).forEach(s -> System.out.println("  dirty: " + s));
    failed.stream().limit(10).forEach(s -> System.out.println("  failed: " + s));
    System.exit(0);
  }
}
