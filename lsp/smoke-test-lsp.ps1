# LSP stdio smoke test for the Java (jdtls) and Kotlin language servers.
# Starts the server with piped stdio, sends an LSP `initialize` request, then
# `shutdown`/`exit`, and reports whether the server responded with capabilities.
#
# Usage:
#   powershell -NoProfile -ExecutionPolicy Bypass -File lsp\smoke-test-lsp.ps1

param(
  [string]$JavaHome = "G:\logisim_ai\.jdks\jdk-21.0.11+10",
  [switch]$OnlyJava,
  [switch]$OnlyKotlin,
  [int]$TimeoutSec = 180
)

$ErrorActionPreference = "Stop"

function Write-Frame {
  param($stream, [string]$body)
  $payload = [System.Text.Encoding]::UTF8.GetBytes($body)
  $header = "Content-Length: $($payload.Length)`r`n`r`n"
  $hdr = [System.Text.Encoding]::ASCII.GetBytes($header)
  $stream.Write($hdr, 0, $hdr.Length)
  $stream.Write($payload, 0, $payload.Length)
  $stream.Flush()
}

# Reads exactly one LSP message body from the raw byte stream.
function Read-Frame {
  param($stream, [int]$timeoutMs)
  $deadline = [Environment]::TickCount + $timeoutMs
  $buf = New-Object System.Collections.Generic.List[byte]
  $crlf = [byte[]]@(13, 10)

  # 1) read headers until empty line
  $headerBytes = New-Object System.Collections.Generic.List[byte]
  $foundContentLength = $false
  $contentLength = 0
  while ([Environment]::TickCount -lt $deadline) {
    if ($stream.CanRead) {
      $b = $stream.ReadByte()
      if ($b -ge 0) {
        $headerBytes.Add([byte]$b)
        # detect end of a header line
        $n = $headerBytes.Count
        if ($n -ge 2 -and $headerBytes[$n-2] -eq 13 -and $headerBytes[$n-1] -eq 10) {
          $line = [System.Text.Encoding]::ASCII.GetString($headerBytes.ToArray())
          $headerBytes.Clear()
          if ($line.Trim().Length -eq 0) { break }   # blank line = end of headers
          if ($line -match '^Content-Length:\s*(\d+)') {
            $contentLength = [int]$Matches[1]
            $foundContentLength = $true
          }
        }
      } else {
        Start-Sleep -Milliseconds 100
      }
    } else {
      Start-Sleep -Milliseconds 100
    }
  }
  if (-not $foundContentLength) { return $null }

  # 2) read exactly contentLength bytes
  $payload = New-Object byte[] $contentLength
  $read = 0
  while ($read -lt $contentLength -and [Environment]::TickCount -lt $deadline) {
    $chunk = $stream.Read($payload, $read, $contentLength - $read)
    if ($chunk -gt 0) {
      $read += $chunk
    } else {
      Start-Sleep -Milliseconds 100
    }
  }
  if ($read -lt $contentLength) { return $null }
  return [System.Text.Encoding]::UTF8.GetString($payload, 0, $read)
}

function Test-LspServer {
  param([string]$name, [string]$processPath, [string[]]$processArgs,
        [string]$workspaceUri, [string]$logFile)
  Write-Host "=== Smoke test: $name ==="
  $p = New-Object System.Diagnostics.Process
  $p.StartInfo.FileName = $processPath
  $p.StartInfo.Arguments = ($processArgs -join ' ')
  $p.StartInfo.RedirectStandardInput = $true
  $p.StartInfo.RedirectStandardOutput = $true
  $p.StartInfo.RedirectStandardError = $true
  $p.StartInfo.UseShellExecute = $false
  $p.StartInfo.CreateNoWindow = $true
  $p.StartInfo.WorkingDirectory = "G:\logisim_ai"
  $p.StartInfo.Environment["JAVA_HOME"] = $JavaHome
  $p.StartInfo.Environment["PATH"] =
      (Join-Path $JavaHome "bin") + ";" + $p.StartInfo.Environment["PATH"]
  [void]$p.Start()

  # async stderr capture
  $errTask = $p.StandardError.ReadToEndAsync()

  Write-Host "  started pid=$($p.Id) (timeout ${TimeoutSec}s)"
  $initParams = @{
    processId = $p.Id
    rootUri   = $workspaceUri
    capabilities = @{}
    initializationOptions = @{}
    clientInfo = @{ name = "logisim-ai-lsp-smoke"; version = "0.0.1" }
  } | ConvertTo-Json -Depth 10 -Compress
  $initBody = "{`"jsonrpc`":`"2.0`",`"id`":1,`"method`":`"initialize`",`"params`":$initParams}"
  Write-Frame $p.StandardInput.BaseStream $initBody
  Write-Host "  sent initialize; waiting for response..."

  $resp = Read-Frame $p.StandardOutput.BaseStream ($TimeoutSec * 1000)
  if ($null -eq $resp) {
    Write-Host "  RESULT: NO RESPONSE (timeout ${TimeoutSec}s)"
    $p.Kill()
    $p.WaitForExit(5000) | Out-Null
    $err = $errTask.Result
    if ($err) { Write-Host "  stderr tail: $($err.Substring(0, [Math]::Min($err.Length, 1500)))" }
    return $false
  }
  Write-Host ("  initialize response: " + $resp.Substring(0, [Math]::Min($resp.Length, 200)))
  $ok = $resp -match '"capabilities"' -and $resp -match '"id"\s*:\s*1'
  Write-Host "  RESULT: $($(if ($ok) {'PASS'} else {'CHECK'}))"

  # shutdown then exit
  Write-Frame $p.StandardInput.BaseStream '{ "jsonrpc":"2.0","id":2,"method":"shutdown","params":null }'
  $shut = Read-Frame $p.StandardOutput.BaseStream 20000
  if ($null -ne $shut) { Write-Host "  shutdown response: $($shut.Substring(0, [Math]::Min($shut.Length, 120)))" }
  Write-Frame $p.StandardInput.BaseStream '{ "jsonrpc":"2.0","id":2,"method":"exit","params":null }'
  if (!$p.WaitForExit(20000)) { $p.Kill() }
  Write-Host "  server exited: $($p.HasExited)"
  return $ok
}

$workspace = "file:///G:/logisim_ai"
$logDir = "G:\logisim_ai\.tools\lsp\workspace"
New-Item -ItemType Directory -Force -Path $logDir | Out-Null

$results = @()

if (-not $OnlyKotlin) {
  # Reuse opencode-cached jdtls if present, else local install
  $jdtBase = "$env:USERPROFILE\.cache\opencode\bin\jdtls"
  if (!(Test-Path (Join-Path $jdtBase "plugins"))) { $jdtBase = "G:\logisim_ai\.tools\lsp" }
  $launcher = Get-ChildItem (Join-Path $jdtBase "plugins") -Filter "org.eclipse.equinox.launcher_*.jar" |
      Sort-Object Name -Descending | Select-Object -First 1
  $jArgs = @(
    "-Declipse.application=org.eclipse.jdt.ls.core.id1",
    "-Dosgi.bundles.defaultStartLevel=4",
    "-Declipse.product=org.eclipse.jdt.ls.core.product",
    "-Dosgi.checkConfiguration=true",
    "-Dosgi.sharedConfiguration.area=$(Join-Path $jdtBase 'config_win')",
    "-Dosgi.sharedConfiguration.area.readOnly=true",
    "-Dosgi.configuration.cascaded=true",
    "-Xms512M",
    "--add-modules=ALL-SYSTEM",
    "--add-opens", "java.base/java.util=ALL-UNNAMED",
    "--add-opens", "java.base/java.lang=ALL-UNNAMED",
    "-jar", $launcher.FullName,
    "-data", (Join-Path $logDir "jdtls-smoke")
  )
  $results += Test-LspServer "jdtls (Java)" (Join-Path $JavaHome "bin\java.exe") $jArgs $workspace `
      (Join-Path $logDir "jdtls-smoke-stderr.log")
}

if (-not $OnlyJava) {
  $kClasspath = "G:\logisim_ai\.tools\lsp\kotlin-ls\server\lib\*"
  $kArgs = @(
    "-DkotlinLanguageServer.version=1.3.13",
    "-classpath", $kClasspath,
    "org.javacs.kt.MainKt"
  )
  $results += Test-LspServer "kotlin-language-server (Kotlin/Gradle DSL)" `
      (Join-Path $JavaHome "bin\java.exe") $kArgs $workspace `
      (Join-Path $logDir "kotlin-smoke-stderr.log")
}

Write-Host
Write-Host ("Smoke summary: PASS=" + (($results | Where-Object { $_ }).Count) +
            " FAIL=" + (($results | Where-Object { -not $_ }).Count))
if ($results -contains $false) { exit 1 }
