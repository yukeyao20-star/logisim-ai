# One-time setup: downloads and installs the Java (jdtls) and Kotlin
# (kotlin-language-server) Language Servers into .tools\lsp for this project.
# Both run on the bundled JDK 21 (G:\logisim_ai\.jdks\jdk-21.0.11+10).
#
# Reuse note: opencode already auto-downloads jdtls into
#   %USERPROFILE%\.cache\opencode\bin\jdtls
# and lsp\launch-jdtls.ps1 prefers that copy. This script only installs jdtls
# into .tools\lsp when that cache is absent (or -Force is used).
#
# Usage:
#   powershell -NoProfile -ExecutionPolicy Bypass -File lsp\setup-lsp.ps1
#
# Requires network access. Idempotent: already-installed servers are refreshed
# only when -Force is passed.

param([switch]$Force)

$ErrorActionPreference = "Stop"
$ProgressPreference = "SilentlyContinue"
[Net.ServicePointManager]::SecurityProtocol =
    [Net.SecurityProtocolType]::Tls12 -bor [Net.SecurityProtocolType]::Tls13

$repo = "G:\logisim_ai"
$base = Join-Path $repo ".tools\lsp"
New-Item -ItemType Directory -Force -Path $base | Out-Null

# ---- kotlin-language-server (GitHub releases) ----
$kVersion = "1.3.13"
$kZip = Join-Path $base "kotlin-ls.zip"
$kDir = Join-Path $base "kotlin-ls"
if ($Force -or !(Test-Path (Join-Path $kDir "server\lib"))) {
  Write-Host "Downloading kotlin-language-server $kVersion ..."
  Invoke-WebRequest -Uri "https://github.com/fwcd/kotlin-language-server/releases/download/$kVersion/server.zip" `
      -OutFile $kZip -TimeoutSec 300
  Remove-Item -Recurse -Force $kDir -ErrorAction SilentlyContinue
  Expand-Archive -Path $kZip -DestinationPath $kDir -Force
  Remove-Item $kZip -ErrorAction SilentlyContinue
  Write-Host "  installed to $kDir"
} else {
  Write-Host "kotlin-language-server already present (use -Force to reinstall)"
}

# ---- Eclipse JDT LS (jdtls) ----
$jTar = Join-Path $base "jdtls.tar.gz"
if ($Force -or !(Test-Path (Join-Path $base "bin\jdtls")) -or !(Test-Path (Join-Path $base "plugins"))) {
  Write-Host "Downloading jdtls (latest snapshot) ..."
  Invoke-WebRequest -Uri "https://download.eclipse.org/jdtls/snapshots/jdt-language-server-latest.tar.gz" `
      -OutFile $jTar -TimeoutSec 600
  # jdtls tarball extracts directly into $base (bin/, config_*/, plugins/, features/).
  tar -xzf $jTar -C $base
  Remove-Item $jTar -ErrorAction SilentlyContinue
  Write-Host "  installed to $base"
} else {
  Write-Host "jdtls already present (use -Force to reinstall)"
}

Write-Host
Write-Host "Setup complete. Launch servers with:"
Write-Host "  lsp\launch-jdtls.ps1    (Java 21, LSP over stdio)"
Write-Host "  lsp\launch-kotlin-ls.ps1 (Kotlin/Gradle DSL, LSP over stdio)"
