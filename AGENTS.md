# AGENTS.md

## Project

Java 21 + Gradle project that generates **Logisim-evolution `.circ` files** from natural-language queries or a JSON IR. Pipeline: `NL / JSON → CircuitSpec (ir/) → compiler → .circ` plus validation reports. Run experiments via a single dispatcher entry point.

All docs and round summaries are in **Chinese** (UTF-8). Code identifiers, commands, and `.circ`/report file names stay in English. PowerShell consoles often garble Chinese filenames/content — use the Read tool, not `Get-Content`, to read docs.

## Build & run

There is **no `gradlew` wrapper**. Use the bundled Gradle + a JDK 21.

**Windows** (repo at `G:\logisim_ai`):

```powershell
$env:JAVA_HOME='G:\logisim_ai\.jdks\jdk-21.0.11+10'
$env:Path="$env:JAVA_HOME\bin;" + $env:Path
# Compile: .gradle-local\gradle-9.6.0\bin\gradle.bat compileJava --no-daemon
java -cp "build\classes\java\main;third_party\logisim-evolution\build\libs\logisim-evolution-4.2.0dev-all.jar" com.logisim_ai.experiment.GenerateAndGate <N> [args]
```

**Ubuntu (dual-boot, same repo mounted at /media/yuke/新加卷/logisim_ai)**:

```bash
export JAVA_HOME=~/.jdks/jdk-21.0.12.1+1   # Linux Temurin 21 (Windows .jdks are unusable here)
export PATH="$JAVA_HOME/bin:$PATH"
# Compile (override the Windows java.home pinned in gradle.properties):
.gradle-local/gradle-9.6.0/bin/gradle -Dorg.gradle.java.home="$JAVA_HOME" compileJava --no-daemon
# Classpath separator is ':' on Linux:
java -cp "build/classes/java/main:third_party/logisim-evolution/build/libs/logisim-evolution-4.2.0dev-all.jar" \
  -Djava.awt.headless=true com.logisim_ai.experiment.GenerateAndGate <N> [args]
# Official JAR cached at ~/.cache/logisim/logisim-evolution-4.1.0-all.jar
# (TestVectorRunner/OfficialSimulationVerifier are os.name-aware since round 59.)
```

- `gradle.properties` pins JAVA_HOME and sets `org.gradle.daemon=false` — do not change.
- `gradle.bat run --args="N"` also works (application.mainClass = `GenerateAndGate`), but is slow.

## Dispatcher (`experiment/GenerateAndGate.java`)

| N | Mode |
|---|------|
| 0 | AND gate (default) |
| 1/2/3/4 | half-adder / full-adder / 2-bit adder / 74151 macro |
| 5/6/7 | JK flip-flop / 74161 / 74160 |
| 8 | natural-language generation: `... GenerateAndGate 8 "full adder"` |
| 9 | JSON IR generation: `... GenerateAndGate 9 <spec.json> [out.circ]` |
| 10 | TTL catalog generation |
| 11 | **Boolean AST + synthesis + truth-table regression (the main test suite)** |
| 12 | TTL DIP subcircuit view from NL query |
| 13 | **Official Logisim JAR simulation** — runs real `logisim-evolution-4.1.0-all.jar` truth tables on generated `.circ` files |
| 14 | **Native TTL chip view** — `... GenerateAndGate 14 74160` or NL `生成 74160` places any of the 62 catalog chips with per-port pins |

## Testing / verification

There is **no JUnit**. The regression suite is `GenerateAndGate 11` (`BooleanAstRegression` + `BooleanSynthesisRegression`) — always run it after changes:

```powershell
java -cp "build\classes\java\main;third_party\logisim-evolution\build\libs\logisim-evolution-4.2.0dev-all.jar" com.logisim_ai.experiment.GenerateAndGate 11
```

It should end with `[OK] Boolean synthesis/template truth-table regression passed` and many other `[OK]` lines. Static layout verification on a generated file:

```powershell
java -cp "build\classes\java\main;third_party\logisim-evolution\build\libs\logisim-evolution-4.2.0dev-all.jar" com.logisim_ai.validation.CircuitLayoutInspector generated/circ/<name>.circ
```

Reports (generation summary, delivery report, manifests, truth tables, TTL BOM/PinMap) are written to `generated/reports/` and are git-tracked.

**Official Logisim JAR simulation** (`GenerateAndGate 13`): runs real `logisim-evolution-4.1.0-all.jar` `-tty table` truth tables on every generated `.circ` and cross-checks against `CircuitTruthTableEvaluator`, writing `*_official_simulation.{md,json}`. Requires `CIRCUITARIUM_LOGISIM_JAR` (cached at `C:\Users\yaole\.cache\logisim\logisim-evolution-4.1.0-all.jar`); circuits without a persisted spec JSON are skipped. Regression 11 asserts official simulation for the 4 example circuits when the JAR is present. NL generation persists specs to `generated/examples/<name>_spec.json` so they are verifiable too.

## JSON IR contract

- **`docs/JSON_IR_SCHEMA_025.md` is the authoritative schema.** Read it before touching IR/compiler code.
- JSON is **semantic, not coordinates**: describes components + externalPorts + nets + optional layoutHints. Layout and wiring are computed by the compiler.
- Ports are implicit via `library` + `factory` lookup. **Only 2-input gates are a stable convention for JSON compilation** — do not assume the compiler auto-sets multi-input gate `size`; cascade 2-input gates instead.
- Example specs: `generated/examples/*.json` (half_adder, full_adder, mux_4to1, nand_xor3).

## Logisim API gotchas (high mistake rate — read before writing wiring code)

- **Gate ports: port 0 = OUTPUT, ports 1+ = inputs.** Intuition says the opposite. NOT Gate has only 2 ports (out, in).
- **Pins**: an *input* pin's port 0 is the *output* side (signal flows into the circuit); an *output* pin must have `FACING=WEST` and port 0 is its *input*.
- **Wires must be strictly horizontal or vertical** (`from.y==to.y` or `from.x==to.x`). Diagonal wire endpoints trigger `WireRepair.doOverlaps()` which can OOM.
- **Different signals' vertical branches must use distinct x columns** — same x = electrical short.
- Headless setup before any Logisim API call: `Main.headless = true; System.setProperty("java.awt.headless","true");`
- **Call `file.stopAutosaveThread(false)` before exiting** or the JVM hangs.
- After `loader.save(...)`, call `LogisimProjectSetup.patchV38Compat(outputFile)` so the file opens cleanly in Logisim v3.8 GUI.
- Key classes: `com.cburch.logisim.instance.StdAttr`, `com.cburch.logisim.std.wiring.Pin`, `com.cburch.logisim.std.gates.GateAttributes`, `com.cburch.logisim.data.Direction`, `com.cburch.logisim.circuit.Wire/CircuitMutation`, `com.cburch.logisim.file.Loader/LogisimFile`.

## Hard boundaries

- **Do not modify `build.gradle.kts`** (except nothing — even run args go through the dispatcher now).
- **Do not modify `third_party/` source.** It is gitignored; intentional Logisim-evolution changes live as patches under `patches/`.
- **No new external dependencies.** The only dependency is the logisim-evolution fat jar (`third_party/logisim-evolution/build/libs/logisim-evolution-4.2.0dev-all.jar`). Use JDK + that jar only.
- Java source must stay UTF-8 (enforced by `options.encoding = "UTF-8"`).

## Source layout (`src/main/java/com/logisim_ai/`)

- `experiment/` — dispatcher + per-circuit generators + regression suites (entry points)
- `ai/` — `NaturalLanguageCircuitPlanner` (templates + Boolean synthesis), `CircuitSpecJsonParser`, `GenerationRequest`, `BoolFunctionSynthesizer`
- `ir/` — IR model: `CircuitSpec`, `ComponentSpec`, `NetSpec`, `ExternalPort`, `LayoutHint`, `PortDirection`
- `compiler/` — `LogisimCompiler` (netlist + single-component paths), `TtlDipSubcircuitCompiler`
- `layout/` — `GridLayoutEngine`, `AutoLayoutPolicy`, `FootprintPolicy`, routers, placed-model records
- `logic/` — Boolean expression parser / normalizer / NAND mapper
- `ttl/` — package mapper, DIP wiring/layout reports, multi-package BOM
- `validation/` — semantic validator, layout inspector, truth-table evaluator, XML editor + `LayoutRepairExecutor` (post-edit fixes), manifests, `GenerationSummary`, `GenerationDeliveryReport`
- `catalog/` — `TtlCatalogGenerator` (experiment 10)

## Docs workflow (multi-AI collaboration)

- Drafts go in `temp/discussion_drafts/`; promote to `docs/` only when final.
- Every round updates `docs/07_轮次进展与待决问题.md`; new decisions update `docs/05_决策日志.md`.
- Per-round handoffs: `docs/HANDOFF_NNN_*.md` (latest is HANDOFF_110). **Trust the newest HANDOFF + JSON_IR_SCHEMA_025 over `docs/PROJECT_MEMORY.md`, which is stale (describes experiments 0–7 only).**
- See `.codex/user_instructions.md` for the full collaboration rules (language, review loop, decision responsibility).

## Environment quirks

- `Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8` appears on stderr in PowerShell and is rendered as a `RemoteException`-style error — **exit code 0 means success; this is not a failure.**
- Ignore `.fuse_hidden*` and `XX*` files under `src/` — FUSE editor temp artifacts, not source.
- Run all commands from repo root `G:\logisim_ai` (JavaExec workingDir is forced to it; paths like `generated/...` are repo-relative).

## Installed tools (configured once, reused everywhere)

Tool configs live in the **global** files below, not in this repo. This project only documents how to use them — do not reinstall or reconfigure.

### Circuitarium MCP (circuitarium-mcp)

- **Where configured**: `~/.config/opencode/opencode.json` (global `mcp.circuitarium-mcp`), `npx circuitarium-mcp@0.3.1`.
- **Scope**: analyzes `.circ` files under `G:\logisim_ai\generated` (static: `logisim_list_projects`, `logisim_analyze_design`, `logisim_export_netlist`) and — with the bundled JAR — runs **real Logisim truth tables and test vectors** (`logisim_truth_table`, `logisim_run_test_vector`, `logisim_component_stats`). This is the end-to-end simulation evidence the project's own `CircuitTruthTableEvaluator` cannot provide.
- **Runtime**: official `logisim-evolution-4.1.0-all.jar` cached at `C:\Users\yaole\.cache\logisim\`, launched with the bundled JDK 21. Env: `CIRCUITARIUM_MCP_ROOT=G:\logisim_ai\generated`, `CIRCUITARIUM_LOGISIM_JAR`, `CIRCUITARIUM_JAVA`.
- **Usage rule**: after generating a `.circ` via experiment 8/9/12, call `logisim_truth_table`/`logisim_run_test_vector` to confirm behavior, not just static layout checks.

### Vision MCP (zai-vision-mcp)

- **Where configured**: `~/.config/opencode/opencode.json` (global `mcp.zai-vision-mcp`), backed by Zhipu's official `@z_ai/mcp-server`.
- **Model**: free `glm-4.1v-thinking-flash` (`Z_AI_VISION_MODEL`; the intended `glm-4.6v-flash` is frequently HTTP-429 rate-limited, so it is NOT the default).
- **Auth**: `Z_AI_API_KEY` env var (user-level `setx`), plus `Z_AI_MODE=ZHIPU`.
- **Usage rule**: whenever a task needs to observe a visual result — `.circ` in the Logisim GUI, a screenshot, chart, generated image, or rendered output — call the MCP vision tool (e.g. `image_analysis`, `extract_text_from_screenshot`, `diagnose_error_screenshot`) with the image path/URL. Treat MCP output as visual evidence, then continue reasoning with the main model. If MCP is unavailable (e.g. Claude Desktop / Codex), fall back to the Qwen CLI helper `%USERPROFILE%\.claude\tools\vision.js` (`DASHSCOPE_API_KEY`).
- **To switch model back to 4.6v-flash** once it stops rate-limiting: edit `Z_AI_VISION_MODEL` in the global opencode.json.

### Java LSP (jdtls) + Kotlin DSL (kotlin-ls)

- **Where configured**: `G:\logisim_ai\opencode.json` (project `lsp: true`) + user PATH entry for the bundled JDK.
- opencode's built-in `jdtls` resolves `java` from PATH and requires JDK 21+. The bundled JDK bin (`G:\logisim_ai\.jdks\jdk-21.0.11+10\bin`) is already on the user PATH, so jdtls auto-downloads the Eclipse JDT language server on first `.java` open; `kotlin-ls` covers `.gradle.kts`/`.kts`.
