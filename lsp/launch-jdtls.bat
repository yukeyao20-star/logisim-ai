@echo off
rem Launches the Eclipse JDT Language Server (jdtls) for this project with the
rem bundled JDK 21 over stdio. Spawn this file from any LSP client
rem (opencode, VS Code, Neovim, Emacs).
@powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0launch-jdtls.ps1" %*
