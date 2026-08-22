# Launches the Eclipse JDT Language Server (jdtls) for this project using the
# bundled JDK 21. The server speaks LSP over stdio (or --pipe/--socket).
#
# Usage:
#   # stdio (e.g. an editor/opencode that spawns it):
#   powershell -NoProfile -ExecutionPolicy Bypass -File lsp\launch-jdtls.ps1
#
# The workspace data dir and config area are derived from this repo so the
# server resolves the project's .classpath / Gradle classpath container.
param(
  [string]$JavaHome = "G:\logisim_ai\.jdks\jdk-21.0.11+10",
  [string]$DataDir  = "G:\logisim_ai\.tools\lsp\workspace\jdtls",
  [switch]$Clean
)

$ErrorActionPreference = "Stop"
$repo = "G:\logisim_ai"

# Reuse the jdtls already downloaded by opencode (the "previously configured"
# LSP) when present; fall back to the local .tools\lsp install.
$candidates = @(
  "$env:USERPROFILE\.cache\opencode\bin\jdtls",   # opencode auto-download
  (Join-Path $repo ".tools\lsp")                  # lsp\setup-lsp.ps1 install
)
$base = $candidates | Where-Object { Test-Path (Join-Path $_ "plugins") } | Select-Object -First 1
if (!$base) { throw "jdtls runtime not found (checked: $($candidates -join ', ')); run lsp\setup-lsp.ps1 first" }
Write-Host "[jdtls] using runtime at $base" | Out-Null

$java = Join-Path $JavaHome "bin\java.exe"
if (!(Test-Path $java)) { throw "java not found at $java" }

# Latest equinox launcher (platform-independent):
$launcher = Get-ChildItem (Join-Path $base "plugins") -Filter "org.eclipse.equinox.launcher_*.jar" |
    Sort-Object Name -Descending | Select-Object -First 1
if (!$launcher) { throw "equinox launcher jar not found under $base\plugins" }

# Windows shared config directory:
$configWin = Join-Path $base "config_win"
if (!(Test-Path $configWin)) { throw "config_win not found at $configWin" }

if ($Clean) {
  Remove-Item -Recurse -Force $DataDir -ErrorAction SilentlyContinue
}
New-Item -ItemType Directory -Force -Path $DataDir | Out-Null

# Base JVM args mirror jdtls.py; add --add-opens etc. Java 21 does not need the
# XML entity limits that Java 24+ require.
$jvmArgs = @(
  "-Declipse.application=org.eclipse.jdt.ls.core.id1",
  "-Dosgi.bundles.defaultStartLevel=4",
  "-Declipse.product=org.eclipse.jdt.ls.core.product",
  "-Dosgi.checkConfiguration=true",
  "-Dosgi.sharedConfiguration.area=$configWin",
  "-Dosgi.sharedConfiguration.area.readOnly=true",
  "-Dosgi.configuration.cascaded=true",
  "-Xms1G",
  "--add-modules=ALL-SYSTEM",
  "--add-opens", "java.base/java.util=ALL-UNNAMED",
  "--add-opens", "java.base/java.lang=ALL-UNNAMED"
)

& $java @jvmArgs "-jar" $launcher.FullName "-data" $DataDir @args

exit $LASTEXITCODE
