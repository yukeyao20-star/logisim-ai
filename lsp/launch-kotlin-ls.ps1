# Launches kotlin-language-server for this project using the bundled JDK 21.
# Serves Kotlin/Gradle DSL sources (.kt, .kts). Standard LSP over stdio.
#
# Usage:
#   powershell -NoProfile -ExecutionPolicy Bypass -File lsp\launch-kotlin-ls.ps1
param(
  [string]$JavaHome = "G:\logisim_ai\.jdks\jdk-21.0.11+10"
)
$ErrorActionPreference = "Stop"
$repo = "G:\logisim_ai"
$serverDir = Join-Path $repo ".tools\lsp\kotlin-ls\server"
$libDir = Join-Path $serverDir "lib"
$java = Join-Path $JavaHome "bin\java.exe"

if (!(Test-Path $java)) { throw "java not found at $java" }
if (!(Test-Path $libDir)) { throw "kotlin-language-server lib not found at $libDir (run lsp\setup-lsp.ps1 first)" }

# Wildcard classpath picks up every jar; main class per the official launcher.
$classpath = "$libDir\*"
& $java "-DkotlinLanguageServer.version=1.3.13" -classpath $classpath "org.javacs.kt.MainKt" @args

exit $LASTEXITCODE
