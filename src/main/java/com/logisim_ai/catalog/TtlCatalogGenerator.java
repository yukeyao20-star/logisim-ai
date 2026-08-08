package com.logisim_ai.catalog;

import com.cburch.logisim.Main;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.ComponentFactory;
import com.cburch.logisim.comp.EndData;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.file.Loader;
import com.cburch.logisim.std.ttl.AbstractTtlGate;
import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Builds a runtime catalog for Logisim-evolution TTL factories and port indices. */
public final class TtlCatalogGenerator {
  private TtlCatalogGenerator() {}

  public static void main(String[] args) throws Exception {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");

    File jsonFile = args.length > 0
        ? new File(args[0])
        : new File("generated/catalog/ttl_catalog.json");
    File markdownFile = args.length > 1
        ? new File(args[1])
        : new File("docs/TTL_FACTORY_CATALOG_029.md");

    Catalog catalog = collect();
    writeJson(jsonFile, catalog);
    writeMarkdown(markdownFile, catalog);
    System.out.println("[OK] TTL catalog generated: " + jsonFile + " and " + markdownFile);
  }

  public static Catalog collect() {
    Loader loader = new Loader(null);
    Library ttl = loader.getBuiltin().getLibrary("TTL");
    if (ttl == null) {
      throw new IllegalStateException("Missing builtin TTL library");
    }

    List<Chip> chips = new ArrayList<>();
    for (Tool tool : ttl.getTools()) {
      if (!(tool instanceof AddTool addTool)) {
        continue;
      }
      ComponentFactory factory = addTool.getFactory();
      Component component = factory.createComponent(Location.create(0, 0, true), factory.createAttributeSet());
      chips.add(describeChip(factory, component));
    }
    chips.sort(Comparator.comparing(Chip::factoryName, TtlCatalogGenerator::compareChipNames));
    return new Catalog("TTL", chips);
  }

  private static Chip describeChip(ComponentFactory factory, Component component) {
    ReflectionData reflection = ReflectionData.from(factory);
    List<PinMapping> pinMappings = reflection.pinMappings(component);
    return new Chip(
        factory.getName(),
        factory.getClass().getName(),
        reflection.pinCount(),
        component.getEnds().size(),
        pinMappings);
  }

  private static int compareChipNames(String left, String right) {
    try {
      return Integer.compare(Integer.parseInt(left), Integer.parseInt(right));
    } catch (NumberFormatException ignored) {
      return left.compareTo(right);
    }
  }

  private static void writeJson(File file, Catalog catalog) throws Exception {
    File parent = file.getParentFile();
    if (parent != null) {
      Files.createDirectories(parent.toPath());
    }

    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"library\": \"").append(escape(catalog.library())).append("\",\n");
    out.append("  \"chips\": [\n");
    for (int i = 0; i < catalog.chips().size(); i++) {
      Chip chip = catalog.chips().get(i);
      out.append("    {\n");
      out.append("      \"factory\": \"").append(escape(chip.factoryName())).append("\",\n");
      out.append("      \"className\": \"").append(escape(chip.className())).append("\",\n");
      out.append("      \"datasheetPinCount\": ").append(chip.datasheetPinCount()).append(",\n");
      out.append("      \"logisimEndCount\": ").append(chip.logisimEndCount()).append(",\n");
      out.append("      \"ports\": [\n");
      for (int j = 0; j < chip.ports().size(); j++) {
        PinMapping pin = chip.ports().get(j);
        out.append("        {\"endIndex\": ").append(pin.endIndex())
            .append(", \"datasheetPin\": ").append(pin.datasheetPin())
            .append(", \"name\": \"").append(escape(pin.name())).append("\"")
            .append(", \"direction\": \"").append(pin.direction()).append("\"")
            .append(", \"width\": ").append(pin.width())
            .append(", \"x\": ").append(pin.x())
            .append(", \"y\": ").append(pin.y()).append("}");
        out.append(j + 1 == chip.ports().size() ? "\n" : ",\n");
      }
      out.append("      ]\n");
      out.append("    }");
      out.append(i + 1 == catalog.chips().size() ? "\n" : ",\n");
    }
    out.append("  ]\n");
    out.append("}\n");
    Files.writeString(file.toPath(), out.toString(), StandardCharsets.UTF_8);
  }

  private static void writeMarkdown(File file, Catalog catalog) throws Exception {
    File parent = file.getParentFile();
    if (parent != null) {
      Files.createDirectories(parent.toPath());
    }

    StringBuilder out = new StringBuilder();
    out.append("# TTL Factory Catalog 029\n\n");
    out.append("Generated: 2026-06-27 20:50:00 +08:00\n\n");
    out.append("This catalog is generated from the runtime Logisim-evolution TTL library. ");
    out.append("Use `factory` as JSON `components[].factory`; use `endIndex` in JSON `nets[].endpoints[].endIndex`. ");
    out.append("Do not use raw datasheet pin numbers as `endIndex`.\n\n");

    out.append("## Summary\n\n");
    out.append("| factory | Java class | datasheet pins | Logisim ends |\n");
    out.append("|---|---|---:|---:|\n");
    for (Chip chip : catalog.chips()) {
      out.append("| `").append(chip.factoryName()).append("` | `")
          .append(chip.className()).append("` | ")
          .append(chip.datasheetPinCount()).append(" | ")
          .append(chip.logisimEndCount()).append(" |\n");
    }

    out.append("\n## Focus Chips\n\n");
    for (String name : List.of("7400", "74138", "74151", "74160", "74161")) {
      Chip chip = catalog.find(name);
      if (chip == null) {
        out.append("### ").append(name).append("\n\n");
        out.append("Not found in current TTL library.\n\n");
        continue;
      }
      appendChip(out, chip);
    }

    out.append("## Full Port Catalog\n\n");
    for (Chip chip : catalog.chips()) {
      appendChip(out, chip);
    }

    Files.writeString(file.toPath(), out.toString(), StandardCharsets.UTF_8);
  }

  private static void appendChip(StringBuilder out, Chip chip) {
    out.append("### ").append(chip.factoryName()).append("\n\n");
    out.append("| endIndex | datasheet pin | name | direction | width | loc |\n");
    out.append("|---:|---:|---|---|---:|---|\n");
    for (PinMapping pin : chip.ports()) {
      out.append("| ").append(pin.endIndex())
          .append(" | ").append(pin.datasheetPin())
          .append(" | `").append(pin.name()).append("`")
          .append(" | ").append(pin.direction())
          .append(" | ").append(pin.width())
          .append(" | (").append(pin.x()).append(",").append(pin.y()).append(") |\n");
    }
    out.append("\n");
  }

  private static String escape(String value) {
    return value.replace("\\", "\\\\").replace("\"", "\\\"");
  }

  public record Catalog(String library, List<Chip> chips) {
    Chip find(String factoryName) {
      return chips.stream()
          .filter(chip -> chip.factoryName().equals(factoryName))
          .findFirst()
          .orElse(null);
    }
  }

  public record Chip(
      String factoryName,
      String className,
      int datasheetPinCount,
      int logisimEndCount,
      List<PinMapping> ports) {}

  public record PinMapping(
      int endIndex,
      int datasheetPin,
      String name,
      String direction,
      int width,
      int x,
      int y) {}

  private record ReflectionData(
      int pinCount,
      Set<Integer> outputPins,
      Set<Integer> inoutPins,
      Set<Integer> unusedPins,
      String[] portNames,
      Map<Integer, String> constantNames) {

    static ReflectionData from(ComponentFactory factory) {
      Class<?> type = factory.getClass();
      int pinCount = ((Number) getField(type, AbstractTtlGate.class, "pinNumber")).intValue();
      Set<Integer> outputPins = toIntSet(getField(type, AbstractTtlGate.class, "outputPorts"));
      Set<Integer> inoutPins = toIntSet(getField(type, AbstractTtlGate.class, "inoutPorts"));
      Set<Integer> unusedPins = toIntSet(getField(type, AbstractTtlGate.class, "unusedPins"));
      String[] portNames = (String[]) getField(type, AbstractTtlGate.class, "portNames");
      Map<Integer, String> constantNames = collectConstantNames(type);
      return new ReflectionData(pinCount, outputPins, inoutPins, unusedPins, portNames, constantNames);
    }

    List<PinMapping> pinMappings(Component component) {
      List<Integer> datasheetPins = includedDatasheetPins();
      List<PinMapping> result = new ArrayList<>();
      List<EndData> ends = component.getEnds();
      for (int i = 0; i < ends.size(); i++) {
        int datasheetPin = i < datasheetPins.size() ? datasheetPins.get(i) : -1;
        EndData end = ends.get(i);
        String name = portName(i, datasheetPin);
        result.add(new PinMapping(
            i,
            datasheetPin,
            name,
            direction(end),
            end.getWidth().getWidth(),
            end.getLocation().getX(),
            end.getLocation().getY()));
      }
      return result;
    }

    private List<Integer> includedDatasheetPins() {
      List<Integer> result = new ArrayList<>();
      int gndPin = pinCount / 2;
      int vccPin = pinCount;
      for (int pin = 1; pin <= pinCount; pin++) {
        if (unusedPins.contains(pin) || pin == gndPin || pin == vccPin) {
          continue;
        }
        result.add(pin);
      }
      return result;
    }

    private String portName(int endIndex, int datasheetPin) {
      if (portNames != null && endIndex < portNames.length && portNames[endIndex] != null) {
        return portNames[endIndex];
      }
      String constant = constantNames.get(datasheetPin);
      if (constant != null) {
        return constant;
      }
      return "pin" + datasheetPin;
    }
  }

  private static String direction(EndData end) {
    if (end.isInput() && end.isOutput()) {
      return "INOUT";
    }
    if (end.isOutput()) {
      return "OUTPUT";
    }
    return "INPUT";
  }

  @SuppressWarnings("unchecked")
  private static Set<Integer> toIntSet(Object value) {
    Set<Integer> result = new HashSet<>();
    if (value instanceof Set<?> set) {
      for (Object item : set) {
        if (item instanceof Number number) {
          result.add(number.intValue());
        }
      }
    }
    return result;
  }

  private static Object getField(Class<?> instanceClass, Class<?> declaringClass, String name) {
    try {
      Field field = declaringClass.getDeclaredField(name);
      field.setAccessible(true);
      return field.get(instanceClass.cast(instanceClass.getConstructor().newInstance()));
    } catch (ReflectiveOperationException e) {
      throw new IllegalStateException("Cannot read field " + name + " from " + instanceClass.getName(), e);
    }
  }

  private static Map<Integer, String> collectConstantNames(Class<?> type) {
    Map<Integer, String> result = new HashMap<>();
    for (Field field : type.getDeclaredFields()) {
      int modifiers = field.getModifiers();
      if (!Modifier.isStatic(modifiers) || !Modifier.isFinal(modifiers)) {
        continue;
      }
      Class<?> fieldType = field.getType();
      if (fieldType != byte.class && fieldType != int.class) {
        continue;
      }
      try {
        field.setAccessible(true);
        int value = ((Number) field.get(null)).intValue();
        if (value >= 1 && value <= 64) {
          result.putIfAbsent(value, field.getName());
        }
      } catch (ReflectiveOperationException ignored) {
        // Ignore constants that are not accessible in this runtime.
      }
    }
    return result;
  }
}
