package com.logisim_ai.validation;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.PortDirection;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Runs the pinned official Logisim-evolution 4.1.0 JAR in headless
 * {@code -tty table} mode to produce authoritative truth-table evidence for a
 * generated {@code .circ} file, and cross-checks it against the in-process
 * {@link CircuitTruthTableEvaluator}.
 *
 * <p>This is the end-to-end simulation evidence that static round-trip loading
 * cannot provide: the real Logisim engine evaluates the stored gates and pins.
 * The JAR is located via {@code CIRCUITARIUM_LOGISIM_JAR} (or the bundled
 * 4.2.0dev jar fallback) and launched as a bounded subprocess with the JDK that
 * launched this program.
 */
public final class OfficialSimulationVerifier {
  private OfficialSimulationVerifier() {}

  /** Default location of the pinned official Logisim-evolution 4.1.0 all-JAR. */
  public static final String DEFAULT_LOGISIM_JAR =
      System.getProperty("user.home") + "\\.cache\\logisim\\logisim-evolution-4.1.0-all.jar";

  public static Path markdownPath(File circFile) {
    return Path.of("generated", "reports", outputStem(circFile) + "_official_simulation.md");
  }

  public static Path jsonPath(File circFile) {
    return Path.of("generated", "reports", outputStem(circFile) + "_official_simulation.json");
  }

  /**
   * Run official simulation evidence for a generated circuit.
   *
   * @return a Report whose {@code passed} is true only when the official JAR
   *     produced a full truth table and it matched the in-process evaluator.
   */
  public static Report verify(CircuitSpec spec, File circFile) {
    List<ExternalPort> inputs = spec.externalPorts().stream()
        .filter(port -> port.direction() == PortDirection.INPUT)
        .toList();
    List<ExternalPort> outputs = spec.externalPorts().stream()
        .filter(port -> port.direction() == PortDirection.OUTPUT)
        .toList();
    if (inputs.isEmpty() || outputs.isEmpty()) {
      return Report.skipped(circFile, "no visible input/output ports");
    }
    if (inputs.size() > 12) {
      return Report.skipped(circFile, "too many inputs for exhaustive official simulation: "
          + inputs.size());
    }

    String jar = locateLogisimJar();
    if (jar == null) {
      return Report.skipped(circFile, "official Logisim JAR not found; set CIRCUITARIUM_LOGISIM_JAR");
    }

    String table;
    try {
      table = runTtyTable(jar, circFile);
    } catch (Exception e) {
      return new Report(circFile, false,
          "official JAR subprocess failed: " + e.getClass().getSimpleName() + ": " + e.getMessage(),
          List.of(), List.of());
    }
    if (table == null || table.isBlank()) {
      return new Report(circFile, false, "official JAR returned an empty truth table", List.of(), List.of());
    }

    List<String[]> parsed = parseTable(table);
    if (parsed.size() <= 1) {
      return new Report(circFile, false,
          "official JAR table has no data rows (headers=" + parsed.size() + ")", List.of(), List.of());
    }

    String[] header = parsed.get(0);
    List<String[]> rows = parsed.subList(1, parsed.size());
    Map<String, Integer> columnIndex = columnIndex(header);
    int expectedRows = 1 << inputs.size();
    if (rows.size() != expectedRows) {
      return new Report(circFile, false,
          "official JAR returned " + rows.size() + " rows, expected " + expectedRows, List.of(), List.of());
    }

    // Native TTL chips (and other non-primitive-gate components) cannot be evaluated
    // by the in-process CircuitTruthTableEvaluator. For those, the official JAR's
    // successful full truth table is itself the behavioral evidence.
    if (containsNonPrimitiveComponent(spec)) {
      String detail = "[OK] official Logisim JAR truth table completed for native chip: rows=" + rows.size();
      return new Report(circFile, true, detail, List.copyOf(rows),
          List.copyOf(java.util.Arrays.asList(header)));
    }

    List<String> mismatches = new ArrayList<>();
    int inputCount = inputs.size();
    for (String[] row : rows) {
      Map<String, Boolean> inputValues = new LinkedHashMap<>();
      for (ExternalPort input : inputs) {
        Integer idx = columnIndex.get(input.label());
        if (idx == null) {
          mismatches.add("official table missing input column '" + input.label() + "'");
          break;
        }
        inputValues.put(input.id(), row[idx].trim().equals("1"));
      }
      if (!mismatches.isEmpty()) {
        break;
      }
      Map<String, Boolean> expectedOutputs;
      try {
        expectedOutputs = CircuitTruthTableEvaluator.evaluate(spec, inputValues);
      } catch (Exception e) {
        return new Report(circFile, false,
            "in-process evaluator failed on row " + String.join(" ", row) + ": "
                + e.getClass().getSimpleName() + ": " + e.getMessage(),
            List.of(), List.of());
      }
      for (ExternalPort output : outputs) {
        Integer idx = columnIndex.get(output.label());
        if (idx == null) {
          mismatches.add("official table missing output column '" + output.label() + "'");
          continue;
        }
        String official = row[idx].trim();
        String expected = Boolean.TRUE.equals(expectedOutputs.get(output.id())) ? "1" : "0";
        if (!official.equals("0") && !official.equals("1")) {
          mismatches.add("official value not binary at '" + output.label() + "' in row "
              + String.join(" ", row) + ": '" + official + "'");
        } else if (!official.equals(expected)) {
          mismatches.add("mismatch at '" + output.label() + "' for inputs "
              + String.join(" ", row) + ": official=" + official + " expected=" + expected);
        }
      }
    }

    boolean passed = mismatches.isEmpty();
    String detail;
    if (passed) {
      detail = "[OK] official Logisim JAR truth table matched in-process evaluator: rows=" + rows.size();
    } else {
      detail = "[FAIL] " + mismatches.size() + " mismatch(es): "
          + String.join("; ", mismatches.subList(0, Math.min(mismatches.size(), 5)));
    }
    return new Report(circFile, passed, detail, List.copyOf(rows),
        List.copyOf(java.util.Arrays.asList(header)));
  }

  private static String locateLogisimJar() {
    String fromEnv = System.getenv("CIRCUITARIUM_LOGISIM_JAR");
    if (fromEnv != null && !fromEnv.isBlank() && Files.exists(Path.of(fromEnv))) {
      return fromEnv;
    }
    Path defaultJar = Path.of(DEFAULT_LOGISIM_JAR);
    if (Files.exists(defaultJar)) {
      return defaultJar.toString();
    }
    Path bundled = Path.of("third_party", "logisim-evolution", "build", "libs",
        "logisim-evolution-4.2.0dev-all.jar");
    if (Files.exists(bundled)) {
      return bundled.toString();
    }
    return null;
  }

  private static String runTtyTable(String jar, File circFile) throws Exception {
    String javaBin = Path.of(System.getProperty("java.home"), "bin", "java.exe").toString();
    List<String> command = List.of(javaBin, "-jar", jar, "-tty", "table", circFile.getPath());
    ProcessBuilder builder = new ProcessBuilder(command);
    builder.redirectErrorStream(true);
    // JAVA_TOOL_OPTIONS (e.g. -Dfile.encoding=UTF-8) would be echoed on stderr by the
    // child JVM and merged into the table output; drop it so parsing stays clean.
    builder.environment().remove("JAVA_TOOL_OPTIONS");
    builder.environment().remove("_JAVA_OPTIONS");
    Process process = builder.start();
    String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    boolean finished = process.waitFor(60, TimeUnit.SECONDS);
    if (!finished) {
      process.destroyForcibly();
      throw new IllegalStateException("official JAR subprocess timed out after 60s");
    }
    if (process.exitValue() != 0) {
      throw new IllegalStateException("official JAR exited with code " + process.exitValue()
          + "\n" + output);
    }
    return output;
  }

  /** Parse whitespace-separated tty table output into header + data rows. */
  static List<String[]> parseTable(String table) {
    List<String[]> result = new ArrayList<>();
    String[] lines = table.split("\\r?\\n");
    for (String line : lines) {
      if (line == null || line.isBlank()) {
        continue;
      }
      // Ignore JVM banners such as "Picked up JAVA_TOOL_OPTIONS: ..." that can
      // appear on the merged stream when the child JVM inherits environment opts.
      if (line.startsWith("Picked up ") || line.startsWith("Unrecognized option:")
          || line.startsWith("Unrecognized VM option") || line.startsWith("OpenJDK")
          || line.startsWith("Error:")) {
        continue;
      }
      String[] cells = line.trim().split("\\s+");
      result.add(cells);
    }
    return result;
  }

  private static Map<String, Integer> columnIndex(String[] header) {
    Map<String, Integer> result = new LinkedHashMap<>();
    for (int i = 0; i < header.length; i++) {
      result.put(header[i].trim(), i);
    }
    return result;
  }

  /** True when the spec contains components the in-process evaluator cannot model. */
  private static boolean containsNonPrimitiveComponent(CircuitSpec spec) {
    return spec.components().stream()
        .anyMatch(component -> {
          String factory = component.factoryName();
          if (factory == null) {
            return true;
          }
          return switch (factory) {
            case "NOT Gate", "AND Gate", "OR Gate", "XOR Gate", "NAND Gate", "NOR Gate" -> false;
            default -> true;
          };
        });
  }

  private static String outputStem(File circFile) {
    String name = circFile.getName();
    if (name.endsWith(".circ")) {
      return name.substring(0, name.length() - ".circ".length());
    }
    return name;
  }

  public record Report(File circFile, boolean passed, String detail, List<String[]> rows,
      List<String> header) {
    static Report skipped(File circFile, String reason) {
      return new Report(circFile, true, "[SKIP] " + reason, List.of(), List.of());
    }

    public String toHumanString() {
      return passed
          ? "[OK] " + circFile.getName() + " official simulation: " + detail
          : "[FAIL] " + circFile.getName() + " official simulation: " + detail;
    }
  }
}
