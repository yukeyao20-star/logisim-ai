package com.logisim_ai.experiment;

import com.cburch.logisim.file.Loader;
import com.cburch.logisim.file.LogisimFile;
import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogisimProjectSetup {
  private LogisimProjectSetup() {}

  private static final String[] BUILTIN_LIBS = {
    "Base", "Wiring", "Gates", "Plexers", "Arithmetic", "FPArithmetic",
    "Memory", "I/O", "TTL", "HDL-IP", "TCL", "BFH-Praktika",
    "Input/Output-Extra", "Soc"
  };

  public static void configureGuiDefaults(Loader loader, LogisimFile file) {
    for (String name : BUILTIN_LIBS) {
      addLibrary(loader, file, name);
    }

    var toolbar = file.getOptions().getToolbarData();
    if (toolbar.size() > 0) {
      return;
    }

    Library base = loader.getBuiltin().getLibrary("Base");
    Library wiring = loader.getBuiltin().getLibrary("Wiring");
    Library gates = loader.getBuiltin().getLibrary("Gates");

    addToolbarTool(toolbar, base, "Poke Tool");
    addToolbarTool(toolbar, base, "Edit Tool");
    addToolbarTool(toolbar, base, "Text Tool");
    toolbar.addSeparator();
    addToolbarTool(toolbar, wiring, "Pin");
    addToolbarTool(toolbar, wiring, "Tunnel");
    toolbar.addSeparator();
    addToolbarTool(toolbar, gates, "NOT Gate");
    addToolbarTool(toolbar, gates, "AND Gate");
    addToolbarTool(toolbar, gates, "OR Gate");
    addToolbarTool(toolbar, gates, "XOR Gate");
  }

  public static Library addLibrary(Loader loader, LogisimFile file, String name) {
    Library library = loader.getBuiltin().getLibrary(name);
    if (library == null) {
      throw new IllegalStateException("Missing builtin library: " + name);
    }
    if (!file.getLibraries().contains(library)) {
      file.addLibrary(library);
    }
    return library;
  }

  private static void addToolbarTool(
      com.cburch.logisim.file.ToolbarData toolbar, Library library, String name) {
    Tool tool = findTool(library, name);
    toolbar.addTool(tool.cloneTool());
  }

  public static Tool findTool(Library library, String name) {
    for (Tool tool : library.getTools()) {
      if (name.equals(tool.getName())) {
        return tool;
      }
      if (tool instanceof AddTool addTool && name.equals(addTool.getFactory().getName())) {
        return tool;
      }
    }
    throw new IllegalStateException("Missing tool: " + name);
  }

  /**
   * Post-process .circ XML to:
   * 1) Ensure all 14 builtin libraries appear (XmlWriter only serializes referenced libs)
   * 2) Patch toolbar with explicit lib= indices for v3.8 compatibility
   */
  static void patchV38Compat(File circFile) throws java.io.IOException {
    patchGeneratedProjectXml(circFile);
  }

  /**
   * Post-process .circ XML to keep generated files easy to open, edit, and verify:
   * 1) Ensure all builtin libraries appear (XmlWriter only serializes referenced libs)
   * 2) Patch toolbar with explicit library indices
   * 3) Rewrite the <project source="..."> marker to 4.1.0 so the generated file can be
   *    loaded by the pinned official Logisim-evolution 4.1.0 JAR (truth tables / test
   *    vectors) as well as newer GUI builds. The XML dialect for the simple gate/TTL
   *    constructs this project emits is stable across 4.1.0 and 4.2.0dev.
   */
  public static void patchGeneratedProjectXml(File circFile) throws java.io.IOException {
    String xml = Files.readString(circFile.toPath(), StandardCharsets.UTF_8);

    // Step 0: Normalize the source version marker so runtime verification tools that pin
    // Logisim-evolution 4.1.0 accept the file.
    xml = xml.replaceFirst(
        "source=\"4\\.2\\.0dev\"",
        "source=\"4.1.0\"");

    // Step 1: Find existing <lib> declarations and their name indices
    Map<String, Integer> existingLibs = new LinkedHashMap<>();
    int maxIndex = -1;
    Pattern libPattern = Pattern.compile("<lib\\s+desc=\"([^\"]+)\"\\s+name=\"(\\d+)\"");
    Matcher m = libPattern.matcher(xml);
    while (m.find()) {
      existingLibs.put(m.group(1), Integer.parseInt(m.group(2)));
      if (Integer.parseInt(m.group(2)) > maxIndex) {
        maxIndex = Integer.parseInt(m.group(2));
      }
    }

    // Step 2: Add missing lib declarations
    StringBuilder insertLibs = new StringBuilder();
    Map<String, Integer> allLibs = new LinkedHashMap<>(existingLibs);
    for (String libName : BUILTIN_LIBS) {
      String desc = "#" + libName;
      if (!existingLibs.containsKey(desc)) {
        maxIndex++;
        allLibs.put(desc, maxIndex);
        insertLibs.append("  <lib desc=\"").append(desc)
            .append("\" name=\"").append(maxIndex).append("\"/>\n");
      }
    }

    // Insert after the last <lib> tag
    if (insertLibs.length() > 0) {
      Pattern libTagPattern = Pattern.compile("<lib[^>]*/>|<lib[^>]*>.*?</lib>");
      Matcher lm = libTagPattern.matcher(xml);
      int lastLibEnd = -1;
      while (lm.find()) {
        lastLibEnd = lm.end();
      }
      if (lastLibEnd > 0) {
        xml = xml.substring(0, lastLibEnd) + "\n" + insertLibs + xml.substring(lastLibEnd);
      }
    }

    // Step 3: Patch toolbar with correct lib indices
    int baseIdx = allLibs.getOrDefault("#Base", 0);
    int wiringIdx = allLibs.getOrDefault("#Wiring", 1);
    int gatesIdx = allLibs.getOrDefault("#Gates", 2);

    StringBuilder tb = new StringBuilder();
    tb.append("  <toolbar>\n");
    tb.append("    <tool lib=\"").append(baseIdx).append("\" name=\"Poke Tool\"/>\n");
    tb.append("    <tool lib=\"").append(baseIdx).append("\" name=\"Edit Tool\"/>\n");
    tb.append("    <tool lib=\"").append(baseIdx).append("\" name=\"Text Tool\"/>\n");
    tb.append("    <sep/>\n");
    tb.append("    <tool lib=\"").append(wiringIdx).append("\" name=\"Pin\">\n");
    tb.append("      <a name=\"appearance\" val=\"classic\"/>\n");
    tb.append("    </tool>\n");
    tb.append("    <tool lib=\"").append(wiringIdx).append("\" name=\"Tunnel\"/>\n");
    tb.append("    <sep/>\n");
    tb.append("    <tool lib=\"").append(gatesIdx).append("\" name=\"NOT Gate\"/>\n");
    tb.append("    <tool lib=\"").append(gatesIdx).append("\" name=\"AND Gate\"/>\n");
    tb.append("    <tool lib=\"").append(gatesIdx).append("\" name=\"OR Gate\"/>\n");
    tb.append("    <tool lib=\"").append(gatesIdx).append("\" name=\"XOR Gate\"/>\n");
    tb.append("  </toolbar>");

    xml = xml.replaceFirst("(?s)  <toolbar>.*?</toolbar>|  <toolbar/>",
        Matcher.quoteReplacement(tb.toString()));

    Files.writeString(circFile.toPath(), xml, StandardCharsets.UTF_8);
  }
}
