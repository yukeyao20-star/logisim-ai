@echo off
rem Launches kotlin-language-server for this project with the bundled JDK 21
rem over stdio. Spawn this file from any LSP client (opencode, VS Code, ...).
@powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0launch-kotlin-ls.ps1" %*
