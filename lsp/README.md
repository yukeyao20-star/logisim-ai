# LSP 语言服务器（Java + Kotlin/Gradle DSL）

本项目为 `logisim_ai` 提供**可真实启动**的语言服务器（Language Server Protocol，stdio）：

- **Java（jdtls / Eclipse JDT Language Server）**：`lsp\launch-jdtls.ps1`（或 `.bat`）
- **Kotlin / Gradle DSL（kotlin-language-server）**：`lsp\launch-kotlin-ls.ps1`（或 `.bat`）

两个服务器都用项目自带 JDK 21（`G:\logisim_ai\.jdks\jdk-21.0.11+10`）启动，因此本地不需要系统级 Java 安装。这是与 AGENTS.md「Java LSP (jdtls) + Kotlin DSL (kotlin-ls)」一节的对接。

## 一次性安装

**复用优先**：opencode 之前已自动下载过 jdtls（`%USERPROFILE%\.cache\opencode\bin\jdtls`），
`lsp\launch-jdtls.ps1` 会**优先复用**该副本，无需重复下载。只有该缓存不存在时才需要用
`setup-lsp.ps1` 安装到 `.tools\lsp`。

```powershell
# 仅当 opencode 缓存里没有 jdtls 时才需要（kotlin-ls 一般都要装）：
powershell -NoProfile -ExecutionPolicy Bypass -File lsp\setup-lsp.ps1
```

脚本会下载（仅当需要）：
- **jdtls** 最新 snapshot（约 50MB）：来自 `download.eclipse.org/jdtls/snapshots/`
- **kotlin-language-server 1.3.13**（约 90MB）：来自 GitHub `fwcd/kotlin-language-server` releases

解压到 `G:\logisim_ai\.tools\lsp\`（该目录已被 `.gitignore` 忽略，二进制不入库）。
重复运行可只安装缺失部分；加 `-Force` 强制重装。

> 网络要求：`setup-lsp.ps1` 需要联网。如果代理/内网无法直连，可手动把 jdtls 解压到
> `.tools\lsp\`，把 kotlin-language-server server 解压到 `.tools\lsp\kotlin-ls\server`。

## 启动（供任意 LSP 客户端调用）

LSP 客户端（opencode、VS Code、Neovim、Emacs、Helix…）只需 spawn 启动脚本，即通过 stdio 通信：

```powershell
# Java
powershell -NoProfile -ExecutionPolicy Bypass -File lsp\launch-jdtls.ps1

# Kotlin / Gradle DSL
powershell -NoProfile -ExecutionPolicy Bypass -File lsp\launch-kotlin-ls.ps1
```

## 冒烟测试（可复现证据）

脚本会启动服务器、发送 LSP `initialize` 请求、检查响应，再 `shutdown`/`exit`：

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File lsp\smoke-test-lsp.ps1
# 只看 Java：   ... -OnlyJava
# 只看 Kotlin： ... -OnlyKotlin
```

预期：`initialize response: {...}` 中带 `capabilities`，且 `RESULT: PASS`。

## opencode 集成

该项目 `opencode.json` 目前是 `{ "lsp": true }`（opencode 内置 jdtls 自动下载）。
若想让 opencode 直接使用本目录已装好、已验证的服务器，可在 `opencode.json` 增加显式 LSP 配置
（以下为 opencode 配置示例，按需启用；`commands` 指向本项目 launcher）：

```jsonc
{
  "lsp": {
    "java": {
      "name": "Java",
      "files": ["**/*.java"],
      "commands": ["G:\\logisim_ai\\lsp\\launch-jdtls.bat"]
    },
    "kotlin": {
      "name": "Kotlin",
      "files": ["**/*.kt", "**/*.kts", "**/*.gradle.kts", "build.gradle.kts", "settings.gradle.kts"],
      "commands": ["G:\\logisim_ai\\lsp\\launch-kotlin-ls.bat"]
    }
  }
}
```

> 注：opencode `"lsp": true` 会走其内置的 jdtls 自动下载；它解析 `JAVA_HOME`/PATH 上的 `java`
> 并要求 JDK 21+。当前放量的 JDK 已在 PATH 上（见 AGENTS.md）。显式 `lsp` 映射与内置模式二选一。

## 说明与约束

- **不用第三方构建依赖**：语言服务器仅用于**开发者/编辑器**，不进入 `build.gradle.kts` 依赖，
  不改变 `.circ` 编译产物。
- **二进制不入库**：`.tools/` 已加入 `.gitignore`；本目录（`lsp/`）只包含跟踪的脚本与文档。
- **jdtls classpath**：通过项目 `.classpath` / `.settings`（Eclipse JDT 元数据）与 Gradle
  classpath 容器解析，能识别 `logisim-evolution` fat jar 依赖（`third_party/.../all.jar`）。
```
