#!/usr/bin/env python3
"""Builds a release zip of the logisim_ai circuit generator.

Layout (user unzips and runs bin/logisim-ai):
  logisim-ai-<ver>/
    bin/logisim-ai            (Linux launcher)
    bin/logisim-ai.bat        (Windows launcher)
    lib/classes/              (compiled Java classes)
    lib/logisim-evolution-*.jar
    examples/*.json           (spec samples for interface B)
    docs/接入指南.md, JSON_IR_SCHEMA_025.md, circuit_catalog.md
    README.txt                (quick start)

Usage: python3 scripts/build_release.py [--out dist]
Excludes: generated/ (regenerable), .git, temp, .jdks, build/, third_party sources.
"""
import argparse
import os
import shutil
import subprocess
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
VER = "0.73"  # round number

def sh(cmd, env=None):
    e = os.environ.copy()
    if env:
        e.update(env)
    subprocess.run(cmd, shell=True, check=True, cwd=ROOT, env=e)

def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--out", default=str(ROOT / "dist"))
    args = ap.parse_args()
    out = Path(args.out)
    out.mkdir(parents=True, exist_ok=True)

    stage = out / f"logisim-ai-{VER}"
    if stage.exists():
        shutil.rmtree(stage)
    stage.mkdir(parents=True)

    # 1. Compile (uses whatever JAVA_HOME is set; caller must provide JDK 21)
    print("[1/4] compiling...")
    java_home = os.environ.get("JAVA_HOME", "")
    gradle = ROOT / ".gradle-local/gradle-9.6.0/bin/gradle"
    sh(f'"{gradle}" -Dorg.gradle.java.home="{java_home}" compileJava --no-daemon')
    shutil.copytree(ROOT / "build/classes/java/main", stage / "lib/classes")

    # 2. Fat jar
    print("[2/4] copying logisim fat jar...")
    (stage / "lib").mkdir(parents=True, exist_ok=True)
    shutil.copy(ROOT / "third_party/logisim-evolution/build/libs/logisim-evolution-4.2.0dev-all.jar",
                stage / "lib/")

    # 3. Launchers
    print("[3/4] writing launchers...")
    bin_dir = stage / "bin"
    bin_dir.mkdir()
    launcher_sh = """#!/usr/bin/env bash
# logisim-ai circuit generator — NL (mode 8) / JSON IR (mode 9)
# Requires JDK 21+: set JAVA_HOME or put java on PATH.
DIR="$(cd "$(dirname "$0")/.." && pwd)"
CP="$DIR/lib/classes:$DIR/lib/logisim-evolution-4.2.0dev-all.jar"
if [ "$1" = "9" ]; then
  exec java -Djava.awt.headless=true -cp "$CP" com.logisim_ai.experiment.GenerateAndGate "$@"
fi
QUERY="${*:-帮助}"
exec java -Djava.awt.headless=true -cp "$CP" com.logisim_ai.experiment.GenerateAndGate 8 "$QUERY"
"""
    (bin_dir / "logisim-ai").write_text(launcher_sh)
    os.chmod(bin_dir / "logisim-ai", 0o755)
    (bin_dir / "logisim-ai.bat").write_text("""@echo off
REM logisim-ai circuit generator — NL (mode 8) / JSON IR (mode 9)
REM Requires JDK 21+: set JAVA_HOME first.
setlocal
set DIR=%~dp0..
set CP=%DIR%\\lib\\classes;%DIR%\\lib\\logisim-evolution-4.2.0dev-all.jar
if "%1"=="9" (
  java -Djava.awt.headless=true -cp "%CP%" com.logisim_ai.experiment.GenerateAndGate %*
) else (
  set QUERY=%*
  if "%QUERY%"=="" set QUERY=帮助
  java -Djava.awt.headless=true -cp "%CP%" com.logisim_ai.experiment.GenerateAndGate 8 %QUERY%
)
""")
    (stage / "README.txt").write_text("""logisim-ai — 自然语言生成 Logisim-evolution 电路

快速开始:
  Linux/macOS:  bin/logisim-ai "设计一个四位二进制加法器"
  Windows:      bin\\logisim-ai.bat "设计一个四位二进制加法器"
  JSON IR:      bin/logisim-ai 9 <spec.json> [out.circ]

要求: JDK 21+ (java 在 PATH 或 JAVA_HOME 已设置)
产物: 与运行目录同级的 generated/circ/*.circ + 验证报告
目录: docs/80_接入指南.md (两种接入方式) | circuit_catalog.md (能建什么)
验证: 每个 .circ 均经官方 Logisim 4.1.0 JAR 真值表/时钟向量验证
""")

    # 4. Docs + examples
    print("[4/4] docs + examples...")
    docs_dir = stage / "docs"
    docs_dir.mkdir()
    for name in ["80_接入指南.md", "JSON_IR_SCHEMA_025.md"]:
        shutil.copy(ROOT / "docs" / name, docs_dir / name)
    shutil.copy(ROOT / "AGENTS.md", docs_dir / "AGENTS.md")
    shutil.copy(ROOT / "generated/reports/circuit_catalog.md",
                docs_dir / "circuit_catalog.md")
    ex_dir = stage / "examples"
    ex_dir.mkdir()
    spec_dir = ROOT / "generated/examples"
    count = 0
    for f in sorted(spec_dir.glob("*_spec.json")):
        shutil.copy(f, ex_dir / f.name)
        count += 1

    # 5. Zip
    zip_path = out / f"logisim-ai-{VER}.zip"
    with zipfile.ZipFile(zip_path, "w", zipfile.ZIP_DEFLATED) as z:
        for root, dirs, files in os.walk(stage):
            for f in files:
                p = Path(root) / f
                z.write(p, p.relative_to(out))
    size_mb = zip_path.stat().st_size / 1024 / 1024
    print(f"[OK] {zip_path} ({size_mb:.1f} MB, {count} spec samples)")

if __name__ == "__main__":
    main()
