package com.logisim_ai.experiment;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Renders a generated .circ file to an SVG image (component boxes, pin
 * labels, wires) so circuit layout can be inspected in a browser without
 * launching the Logisim GUI. Pure-stdlib XML string parsing; no Logisim
 * API needed — this is a visualization of the persisted artifact.
 *
 * <p>Run: {@code java -cp <cp> com.logisim_ai.experiment.CircSvg <in.circ> [out.svg]}
 */
public final class CircSvg {
  private CircSvg() {}

  private static final Pattern COMP = Pattern.compile(
      "<comp lib=\"\\d+\" loc=\"\\((\\d+),(\\d+)\\)\" name=\"([^\"]+)\">");
  private static final Pattern LABEL = Pattern.compile(
      "<a name=\"label\" val=\"([^\"]+)\"/>");
  private static final Pattern FACING = Pattern.compile(
      "<a name=\"facing\" val=\"([^\"]+)\"/>");
  private static final Pattern WIRE = Pattern.compile(
      "<wire from=\"\\((\\d+),(\\d+)\\)\" to=\"\\((\\d+),(\\d+)\\)\"/>");
  private static final Pattern CIRCUIT = Pattern.compile(
      "<circuit name=\"([^\"]+)\">(.*?)</circuit>", Pattern.DOTALL);

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.err.println("usage: CircSvg <in.circ> [out.svg]");
      System.exit(2);
    }
    Path in = Path.of(args[0]);
    Path out = Path.of(args.length > 1 ? args[1]
        : in.toString().replaceAll("\\.circ$", "") + ".svg");
    String xml = Files.readString(in);
    String svg = render(xml);
    Files.writeString(out, svg);
    System.out.println("[OK] " + out + " (" + svg.length() + " bytes)");
  }

  static String render(String xml) {
    String mainName = "main";
    Matcher cm = Pattern.compile("<main name=\"([^\"]+)\"").matcher(xml);
    if (cm.find()) {
      mainName = cm.group(1);
    }
    String body = null;
    Matcher cb = CIRCUIT.matcher(xml);
    while (cb.find()) {
      if (cb.group(1).equals(mainName)) {
        body = cb.group(2);
      }
    }
    if (body == null) {
      throw new IllegalArgumentException("main circuit body not found: " + mainName);
    }

    List<String[]> comps = new ArrayList<>();
    List<String> labels = new ArrayList<>();
    Matcher mc = COMP.matcher(body);
    while (mc.find()) {
      int x = Integer.parseInt(mc.group(1));
      int y = Integer.parseInt(mc.group(2));
      String name = mc.group(3);
      String block = body.substring(mc.start(), Math.min(body.length(), mc.end() + 300));
      String label = "";
      Matcher ml = LABEL.matcher(block);
      if (ml.find()) {
        label = ml.group(1);
      }
      comps.add(new String[]{String.valueOf(x), String.valueOf(y), name});
      labels.add(label);
    }
    List<int[]> wires = new ArrayList<>();
    Matcher mw = WIRE.matcher(body);
    while (mw.find()) {
      wires.add(new int[]{
          Integer.parseInt(mw.group(1)), Integer.parseInt(mw.group(2)),
          Integer.parseInt(mw.group(3)), Integer.parseInt(mw.group(4))});
    }

    int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = 0, maxY = 0;
    for (String[] c : comps) {
      minX = Math.min(minX, Integer.parseInt(c[0]));
      minY = Math.min(minY, Integer.parseInt(c[1]));
      maxX = Math.max(maxX, Integer.parseInt(c[0]) + 140);
      maxY = Math.max(maxY, Integer.parseInt(c[1]) + 80);
    }
    for (int[] w : wires) {
      minX = Math.min(minX, Math.min(w[0], w[2]));
      minY = Math.min(minY, Math.min(w[1], w[3]));
      maxX = Math.max(maxX, Math.max(w[0], w[2]) + 40);
      maxY = Math.max(maxY, Math.max(w[1], w[3]) + 40);
    }
    int pad = 30;
    StringBuilder sb = new StringBuilder();
    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"")
        .append(maxX - minX + 2 * pad).append("\" height=\"")
        .append(maxY - minY + 2 * pad).append("\" viewBox=\"")
        .append(minX - pad).append(" ").append(minY - pad).append(" ")
        .append(maxX - minX + 2 * pad).append(" ").append(maxY - minY + 2 * pad)
        .append("\">\n");
    sb.append("<rect x=\"").append(minX - pad).append("\" y=\"").append(minY - pad)
        .append("\" width=\"100%\" height=\"100%\" fill=\"white\"/>\n");
    for (int[] w : wires) {
      sb.append("<line x1=\"").append(w[0]).append("\" y1=\"").append(w[1])
          .append("\" x2=\"").append(w[2]).append("\" y2=\"").append(w[3])
          .append("\" stroke=\"#1a7f37\" stroke-width=\"2\"/>\n");
    }
    for (int i = 0; i < comps.size(); i++) {
      String[] c = comps.get(i);
      int x = Integer.parseInt(c[0]);
      int y = Integer.parseInt(c[1]);
      String name = c[2];
      String color = switch (name) {
        case "Pin", "Clock" -> "#8250df";
        case "Tunnel" -> "#9a6700";
        case "NOT Gate", "AND Gate", "OR Gate", "XOR Gate", "NAND Gate",
            "NOR Gate", "XNOR Gate" -> "#0969da";
        default -> "#cf222e";
      };
      int w = switch (name) {
        case "NOT Gate" -> 42;
        case "Tunnel" -> 40;
        case "Pin", "Clock" -> 30;
        case "AND Gate", "OR Gate", "XOR Gate", "NAND Gate", "NOR Gate",
            "XNOR Gate" -> 72;
        default -> 110;
      };
      int h = switch (name) {
        case "Pin", "Clock", "Tunnel" -> 24;
        default -> 48;
      };
      sb.append("<rect x=\"").append(x - w / 2).append("\" y=\"").append(y - h / 2)
          .append("\" width=\"").append(w).append("\" height=\"").append(h)
          .append("\" fill=\"").append(color).append("22\" stroke=\"")
          .append(color).append("\" stroke-width=\"1.5\" rx=\"4\"/>\n");
      String label = labels.get(i);
      String text = label.isEmpty() ? name : name + " " + label;
      sb.append("<text x=\"").append(x).append("\" y=\"").append(y + 4)
          .append("\" font-family=\"monospace\" font-size=\"11\" fill=\"#24292f\" text-anchor=\"middle\">")
          .append(text.replace("&", "&amp;").replace("<", "&lt;")).append("</text>\n");
    }
    sb.append("</svg>\n");
    return sb.toString();
  }
}
