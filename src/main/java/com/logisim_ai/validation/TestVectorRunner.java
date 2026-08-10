package com.logisim_ai.validation;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Runs the bundled Logisim-evolution JAR in {@code -w} test-vector mode for
 * sequential circuits. Unlike {@code -tty table} (exhaustive combinational
 * truth table), {@code -w} supports {@code <SET>/<SEQ>} sequences and real
 * Clock components, so counters and other edge-triggered circuits can be
 * verified with clock pulse sequences.
 *
 * <p>The JAR is located via {@code CIRCUITARIUM_LOGISIM_JAR} (or the bundled
 * 4.2.0dev jar fallback) and launched as a bounded subprocess.
 */
public final class TestVectorRunner {
  private TestVectorRunner() {}

  private static final Pattern PASS_FAIL =
      Pattern.compile("Passed:\\s*(\\d+),\\s*Failed:\\s*(\\d+)");

  public record Result(boolean passed, int passCount, int failCount, String output) {}

  /**
   * Run a test vector file against a circuit in the pinned JAR.
   *
   * @param circuitName name of the (main) circuit in the .circ file
   * @return pass/fail counts parsed from the JAR output
   */
  public static Result runVectors(File circFile, String circuitName, File vecFile) throws Exception {
    String jar = locateLogisimJar();
    if (jar == null) {
      throw new IllegalStateException(
          "official Logisim JAR not found; set CIRCUITARIUM_LOGISIM_JAR");
    }
    String javaBin = Path.of(System.getProperty("java.home"), "bin", "java.exe").toString();
    List<String> command = List.of(
        javaBin, "-jar", jar, "-w", circuitName, vecFile.getPath(), circFile.getPath());
    ProcessBuilder builder = new ProcessBuilder(command);
    builder.redirectErrorStream(true);
    builder.environment().remove("JAVA_TOOL_OPTIONS");
    builder.environment().remove("_JAVA_OPTIONS");
    Process process = builder.start();
    String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    boolean finished = process.waitFor(120, TimeUnit.SECONDS);
    if (!finished) {
      process.destroyForcibly();
      throw new IllegalStateException("official JAR vector subprocess timed out after 120s");
    }
    if (process.exitValue() != 0) {
      throw new IllegalStateException(
          "official JAR exited with code " + process.exitValue() + "\n" + output);
    }
    Matcher m = PASS_FAIL.matcher(output);
    if (!m.find()) {
      throw new IllegalStateException(
          "official JAR vector output missing Passed/Failed summary:\n" + output);
    }
    int pass = Integer.parseInt(m.group(1));
    int fail = Integer.parseInt(m.group(2));
    return new Result(fail == 0, pass, fail, output);
  }

  private static String locateLogisimJar() {
    String fromEnv = System.getenv("CIRCUITARIUM_LOGISIM_JAR");
    if (fromEnv != null && !fromEnv.isBlank() && Files.exists(Path.of(fromEnv))) {
      return fromEnv;
    }
    Path defaultJar = Path.of(OfficialSimulationVerifier.DEFAULT_LOGISIM_JAR);
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
}
