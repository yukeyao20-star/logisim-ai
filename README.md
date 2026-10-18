# logisim-ai — 通用电路生成 AI

**说一句自然语言，得到一个可在 [Logisim-evolution](https://github.com/logisim-evolution/logisim-evolution) 中打开、仿真、编辑的数字电路。**

```
"设计一个四位二进制加法器"     → adder_4bit.circ（官方 JAR 真值表 6/6 通过）
"做一个数字时钟"               → digital_clock.circ（264/264 时钟向量）
"自动售货机控制器"             → vending_machine.circ（27/27）
"8位乘法器"                    → multiplier_8x8.circ（16/16）
"三人表决器"                   → logic_f_abc.circ（真值表 8/8）
```

## 快速开始（发布包，解压即用）

从 [Releases](../../releases) 下载 `logisim-ai-0.73.zip`（49 MB），解压。要求仅 **JDK 21+**（`java -version` 显示 21 或更高）。

### Windows

```bat
cd logisim-ai-0.73
bin\logisim-ai.bat "设计一个四位二进制加法器"
bin\logisim-ai.bat 9 myspec.json out.circ
```

- `cmd` 或 PowerShell 均可；中文输出乱码时先执行 `chcp 65001`。
- 未设置 `JAVA_HOME` 时，确保 JDK 21 的 `bin` 目录在 `PATH` 中。

### macOS / Linux

```bash
cd logisim-ai-0.73
chmod +x bin/logisim-ai            # 若解压后没有执行权限
./bin/logisim-ai "设计一个四位二进制加法器"
./bin/logisim-ai 9 myspec.json out.circ
```

- macOS 可用 `export JAVA_HOME=$(/usr/libexec/java_home -v 21)` 指定 JDK。
- Linux 可用 `export JAVA_HOME=/path/to/jdk-21`。

两个命令的产物都在 `generated/circ/`，含验证报告与 SVG 可视化。

## 两条统一接入接口

| 接口 | 适用 | 说明 |
|---|---|---|
| **A. 自然语言 CLI** | 本机 / 能访问仓库的 agent（Codex CLI 等 CLI agent 均可） | `experiment 8`；agent 读 `AGENTS.md` 自主工作 |
| **B. JSON IR 契约** | GPT 网页订阅等无法访问文件系统的 AI | AI 按 JSON IR 契约输出 JSON → 本机 `experiment 9` 编译（schema 见发布包 `docs/JSON_IR_SCHEMA_025.md`） |

接入说明与按 agent 类型的完整矩阵见发布包 `docs/80_接入指南.md`。

## 能力面

- **248 个电路**，类型 100% 分类：运算（加/减/乘/除/MAC/累加/平方）、计数器（mod-N ≤256/约翰逊/环形/LFSR/格雷码）、选择/译码、寄存/触发、码转换（BCD/余3/补码/原码）、显示（七段/十六进制）、**14 个 FSM 控制器**（售货机/电梯/步进电机/洗衣机/自动门/水位/烘干机/乒乓球/尾灯/24秒计时/通用状态机…）、TTL 芯片视图（74 系列）、RAM/ROM、门级 flash ADC、布尔表达式/NAND/TTL 映射/最小项综合
- **真实课设语料覆盖 86%**（163 条探测，不匹配项有显式能力边界声明 + 相近查询提示）
- **验证三支柱**：回归 249 OK（语义/连通性/时钟序列 62 电路/英文 147 条）+ 官方 JAR 真值表 142 电路全 PASS + 时钟向量逐行验证
- **布局**：96% 电路 0 警告，门级方形布局，FSM 面积优化 -94%，每电路配 SVG/PNG 可视化
- **AI 视觉验收**：渲染 → GLM 评估 5/5 全过（可读性/交叉有序/无缺陷）

## 从源码构建

```bash
# 要求 JDK 21
# Windows:
.gradle-local\gradle-9.6.0\bin\gradle.bat compileJava --no-daemon
# macOS / Linux:
export JAVA_HOME=/path/to/jdk-21
.gradle-local/gradle-9.6.0/bin/gradle -Dorg.gradle.java.home="$JAVA_HOME" compileJava --no-daemon
# 打包 release zip（三系统通用；Windows 上用 python，macOS/Linux 上用 python3）
python3 scripts/build_release.py
```

## 文档

| 文档 | 内容 |
|---|---|
| [AGENTS.md](AGENTS.md) | agent 操作手册（构建/命令/工具/验证协议） |
| [generated/reports/circuit_catalog.md](generated/reports/circuit_catalog.md) | 全库 248 电路目录（类型/spec/布局/证据/SVG 五列索引） |

接入指南、JSON IR 契约与开发历史随发布包提供（`docs/` 目录）。

## 架构一句话

`自然语言 / JSON → CircuitSpec (ir/) → 编译器（门级/方形布局/混合路由）→ .circ → 官方 JAR 验证 → 报告 + SVG`

Java 21 单体，唯一依赖为 Logisim-evolution fat jar；零 Logisim 源码修改。
