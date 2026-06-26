package com.logisim_ai.experiment;

import com.cburch.logisim.Main;
import com.cburch.logisim.circuit.Circuit;
import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.ComponentFactory;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.file.Loader;
import com.cburch.logisim.file.LogisimFile;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.wiring.Pin;
import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;
import java.io.File;
import java.nio.file.Files;

/**
 * 实验级复用 Helper：封装 Logisim-evolution API 的常用操作。
 *
 * <p>这不是正式架构，仅用于支撑 2-3 个实验电路的快速生成。
 * 正式架构等主 AI 审核后统一规划。
 */
public final class CircuitHelper {
  private CircuitHelper() {}

  // ========== 项目创建 ==========

  /** 初始化 headless 模式（必须在任何 Logisim API 调用前执行） */
  public static void initHeadless() {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");
  }

  /** 创建空项目上下文 */
  public static ProjectContext createProject() {
    Loader loader = new Loader(null);
    LogisimFile file = LogisimFile.createNew(loader, null);
    Circuit circuit = file.getMainCircuit();
    return new ProjectContext(loader, file, circuit);
  }

  // ========== 库注册 ==========

  /** 注册 Builtin 库（如 Gates, Wiring 等） */
  public static Library registerBuiltinLibrary(ProjectContext ctx, String libraryName) {
    var lib = ctx.loader().getBuiltin().getLibrary(libraryName);
    if (lib == null) {
      throw new IllegalStateException("Loader.getBuiltin() 中找不到 " + libraryName + " 库");
    }
    ctx.file().addLibrary(lib);
    return lib;
  }

  // ========== 工厂查找 ==========

  /** 在库中按名称查找 ComponentFactory */
  public static ComponentFactory findFactory(Library library, String name) {
    for (Tool tool : library.getTools()) {
      if (tool instanceof AddTool addTool) {
        if (name.equals(addTool.getFactory().getName())) {
          return addTool.getFactory();
        }
      }
    }
    StringBuilder available = new StringBuilder();
    for (Tool tool : library.getTools()) {
      if (tool instanceof AddTool addTool) {
        available.append("  - ").append(addTool.getFactory().getName()).append("\n");
      }
    }
    throw new IllegalStateException(
        "找不到 " + name + " factory。可用 factories:\n" + available);
  }

  // ========== 组件创建 ==========

  /** 创建 Gate 组件（使用默认属性） */
  public static Component createGate(ComponentFactory factory, int x, int y) {
    AttributeSet attrs = factory.createAttributeSet();
    return factory.createComponent(Location.create(x, y, true), attrs);
  }

  /** 创建输入 Pin */
  public static Component createInputPin(int x, int y, String label) {
    var attrs = Pin.FACTORY.createAttributeSet();
    attrs.setValue(StdAttr.LABEL, label);
    return Pin.FACTORY.createComponent(Location.create(x, y, true), attrs);
  }

  /** 创建输出 Pin */
  public static Component createOutputPin(int x, int y, String label) {
    var attrs = Pin.FACTORY.createAttributeSet();
    attrs.setValue(Pin.ATTR_TYPE, Pin.OUTPUT);
    attrs.setValue(StdAttr.LABEL, label);
    return Pin.FACTORY.createComponent(Location.create(x, y, true), attrs);
  }

  // ========== 端口查询 ==========

  /**
   * 获取 Gate 的输出端口位置。
   * Gate 端口顺序：端口0=输出, 端口1=输入0(上), 端口2=输入1(下), ...
   */
  public static Location getGateOutput(Component gate) {
    return gate.getEnd(0).getLocation();
  }

  /** 获取 Gate 的第 i 个输入端口位置（从1开始，1=输入0/上，2=输入1/下） */
  public static Location getGateInput(Component gate, int inputIndex) {
    return gate.getEnd(inputIndex).getLocation();
  }

  /** 获取 Pin 的端口位置 */
  public static Location getPinPort(Component pin) {
    return pin.getEnd(0).getLocation();
  }

  // ========== 电路修改 ==========

  /** 添加多个组件到电路 */
  public static void addComponents(Circuit circuit, Component... components) {
    CircuitMutation mutation = new CircuitMutation(circuit);
    for (Component comp : components) {
      mutation.add(comp);
    }
    mutation.execute();
  }

  /** 添加 Wire 到电路 */
  public static void addWire(Circuit circuit, Location from, Location to) {
    if (from.equals(to)) return;
    CircuitMutation mutation = new CircuitMutation(circuit);
    mutation.add(Wire.create(from, to));
    mutation.execute();
  }

  /** 批量添加 Wire（一次 mutation） */
  public static void addWires(Circuit circuit, Location... pairs) {
    if (pairs.length % 2 != 0) {
      throw new IllegalArgumentException("Wire 端点必须成对提供");
    }
    CircuitMutation mutation = new CircuitMutation(circuit);
    for (int i = 0; i < pairs.length; i += 2) {
      if (!pairs[i].equals(pairs[i + 1])) {
        mutation.add(Wire.create(pairs[i], pairs[i + 1]));
      }
    }
    mutation.execute();
  }

  /**
   * 使用 L 形折线连接两个端口。
   * 先水平走，再垂直走。返回添加的 Wire 数量。
   */
  public static int connectWithLShape(CircuitMutation mutation, Location from, Location to) {
    if (from.equals(to)) return 0;
    if (from.getY() == to.getY()) {
      mutation.add(Wire.create(from, to));
      return 1;
    }
    if (from.getX() == to.getX()) {
      mutation.add(Wire.create(from, to));
      return 1;
    }
    Location corner = Location.create(to.getX(), from.getY(), true);
    mutation.add(Wire.create(from, corner));
    mutation.add(Wire.create(corner, to));
    return 2;
  }

  // ========== 保存 ==========

  /** 保存 .circ 文件 */
  public static void saveCirc(ProjectContext ctx, File outputFile) {
    File parent = outputFile.getParentFile();
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }
    boolean ok = ctx.loader().save(ctx.file(), outputFile);
    if (!ok) {
      throw new IllegalStateException("Loader.save 返回 false，保存失败");
    }
    System.out.println("[OK] .circ 文件保存成功: " + outputFile.getName()
        + " (" + outputFile.length() + " bytes)");
  }

  /** 安全退出（停止 autosave 线程） */
  public static void safeExit(ProjectContext ctx) {
    if (ctx != null) {
      try {
        ctx.file().stopAutosaveThread(false);
        System.out.println("[DEBUG] stopAutosaveThread(false) 已调用");
      } catch (Exception e) {
        System.err.println("[WARN] stopAutosaveThread 失败: " + e.getMessage());
      }
    }
  }

  // ========== 校验 ==========

  /** 校验 .circ 文件包含指定组件名称 */
  public static void verifyContains(File file, String... componentNames) throws Exception {
    String content = Files.readString(file.toPath());
    for (String name : componentNames) {
      if (!content.contains("name=\"" + name + "\"")) {
        throw new IllegalStateException(
            "验证失败：.circ 文件不包含 " + name + " 组件");
      }
    }
  }

  /** 校验 .circ 文件包含 wire */
  public static void verifyHasWires(File file) throws Exception {
    String content = Files.readString(file.toPath());
    if (!content.contains("from=") || !content.contains("to=")) {
      throw new IllegalStateException("验证失败：.circ 文件不包含 wire");
    }
  }

  /** 校验 .circ 文件非空 */
  public static void verifyNonEmpty(File file) throws Exception {
    if (!file.exists()) {
      throw new IllegalStateException("验证失败：文件不存在 " + file.getAbsolutePath());
    }
    if (file.length() == 0) {
      throw new IllegalStateException("验证失败：文件为空 " + file.getAbsolutePath());
    }
  }

  /** 统计 .circ 文件中某组件名称出现次数 */
  public static long countComponent(File file, String componentName) throws Exception {
    String content = Files.readString(file.toPath());
    long count = 0;
    int idx = 0;
    String target = "name=\"" + componentName + "\"";
    while ((idx = content.indexOf(target, idx)) != -1) {
      count++;
      idx += target.length();
    }
    return count;
  }

  // ========== 数据类 ==========

  /** 项目上下文：持有 Loader、LogisimFile、主 Circuit */
  public record ProjectContext(Loader loader, LogisimFile file, Circuit circuit) {}
}
