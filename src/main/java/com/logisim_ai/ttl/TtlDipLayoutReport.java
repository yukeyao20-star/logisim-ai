package com.logisim_ai.ttl;

import com.logisim_ai.ir.CircuitSpec;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Places TTL DIP packages as readable chip blocks with physical pin coordinates. */
public final class TtlDipLayoutReport {
  private static final int CHIP_WIDTH = 220;
  private static final int CHIP_HEIGHT = 360;
  private static final int START_X = 160;
  private static final int START_Y = 120;
  private static final int CHIP_GAP_X = 300;
  private static final int PIN_STEP_Y = 40;
  private static final int PIN_START_Y_OFFSET = 60;

  private TtlDipLayoutReport() {}

  public static List<ChipBlock> blocks(CircuitSpec spec) {
    List<TtlDipWiringReport.Chip> chips = TtlDipWiringReport.chips(spec);
    List<ChipBlock> blocks = new ArrayList<>();
    for (int i = 0; i < chips.size(); i++) {
      TtlDipWiringReport.Chip chip = chips.get(i);
      int x = START_X + i * CHIP_GAP_X;
      int y = START_Y;
      blocks.add(new ChipBlock(
          chip.chipId(),
          chip.chipNumber(),
          chip.packageType(),
          chip.gateKind(),
          x,
          y,
          CHIP_WIDTH,
          CHIP_HEIGHT,
          pinPlacements(chip, x, y)));
    }
    return List.copyOf(blocks);
  }

  public static String toMarkdown(CircuitSpec spec) {
    List<ChipBlock> blocks = blocks(spec);
    StringBuilder out = new StringBuilder();
    out.append("# TTL DIP chip block layout for ").append(spec.name()).append("\n\n");
    if (blocks.isEmpty()) {
      out.append("No TTL DIP assignments found.\n");
      return out.toString();
    }
    out.append("- Canvas width: ").append(canvasWidth(blocks)).append("\n");
    out.append("- Canvas height: ").append(canvasHeight(blocks)).append("\n\n");
    for (ChipBlock block : blocks) {
      out.append("## ")
          .append(block.chipId())
          .append(" - ")
          .append(block.packageType())
          .append(" at (")
          .append(block.x())
          .append(", ")
          .append(block.y())
          .append(")\n\n");
      out.append("| Pin | Side | X | Y | Role | Gate | Net | Label |\n");
      out.append("| ---: | --- | ---: | ---: | --- | --- | --- | --- |\n");
      for (PinPlacement pin : block.pins()) {
        out.append("| ")
            .append(pin.pinNumber()).append(" | ")
            .append(pin.side()).append(" | ")
            .append(pin.x()).append(" | ")
            .append(pin.y()).append(" | ")
            .append(pin.role()).append(" | ")
            .append(pin.gateName()).append(" | ")
            .append(pin.netId()).append(" | ")
            .append(pin.label()).append(" |\n");
      }
      out.append("\n");
    }
    return out.toString();
  }

  public static String toJson(CircuitSpec spec) {
    List<ChipBlock> blocks = blocks(spec);
    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"circuitName\": \"").append(escape(spec.name())).append("\",\n");
    out.append("  \"chipCount\": ").append(blocks.size()).append(",\n");
    out.append("  \"canvasWidth\": ").append(canvasWidth(blocks)).append(",\n");
    out.append("  \"canvasHeight\": ").append(canvasHeight(blocks)).append(",\n");
    out.append("  \"chips\": [\n");
    for (int i = 0; i < blocks.size(); i++) {
      ChipBlock block = blocks.get(i);
      out.append("    {\n");
      out.append("      \"chipId\": \"").append(escape(block.chipId())).append("\",\n");
      out.append("      \"chipNumber\": ").append(block.chipNumber()).append(",\n");
      out.append("      \"packageType\": \"").append(escape(block.packageType())).append("\",\n");
      out.append("      \"gateKind\": \"").append(escape(block.gateKind())).append("\",\n");
      out.append("      \"x\": ").append(block.x()).append(",\n");
      out.append("      \"y\": ").append(block.y()).append(",\n");
      out.append("      \"width\": ").append(block.width()).append(",\n");
      out.append("      \"height\": ").append(block.height()).append(",\n");
      out.append("      \"pins\": [\n");
      for (int j = 0; j < block.pins().size(); j++) {
        PinPlacement pin = block.pins().get(j);
        out.append("        {\n");
        out.append("          \"pinNumber\": ").append(pin.pinNumber()).append(",\n");
        out.append("          \"side\": \"").append(pin.side()).append("\",\n");
        out.append("          \"x\": ").append(pin.x()).append(",\n");
        out.append("          \"y\": ").append(pin.y()).append(",\n");
        out.append("          \"role\": \"").append(escape(pin.role())).append("\",\n");
        out.append("          \"gateName\": \"").append(escape(pin.gateName())).append("\",\n");
        out.append("          \"netId\": \"").append(escape(pin.netId())).append("\",\n");
        out.append("          \"label\": \"").append(escape(pin.label())).append("\"\n");
        out.append("        }");
        if (j + 1 < block.pins().size()) {
          out.append(",");
        }
        out.append("\n");
      }
      out.append("      ]\n");
      out.append("    }");
      if (i + 1 < blocks.size()) {
        out.append(",");
      }
      out.append("\n");
    }
    out.append("  ]\n");
    out.append("}\n");
    return out.toString();
  }

  private static List<PinPlacement> pinPlacements(TtlDipWiringReport.Chip chip, int x, int y) {
    Map<Integer, TtlDipWiringReport.Pin> pinsByNumber = new LinkedHashMap<>();
    for (TtlDipWiringReport.Pin pin : chip.pins()) {
      pinsByNumber.put(pin.pinNumber(), pin);
    }
    List<PinPlacement> placements = new ArrayList<>();
    for (int pinNumber = 1; pinNumber <= 14; pinNumber++) {
      TtlDipWiringReport.Pin pin = pinsByNumber.get(pinNumber);
      String side = pinNumber <= 7 ? "LEFT" : "RIGHT";
      int pinX = side.equals("LEFT") ? x : x + CHIP_WIDTH;
      int pinY = y + PIN_START_Y_OFFSET + sideIndex(pinNumber) * PIN_STEP_Y;
      placements.add(new PinPlacement(
          pinNumber,
          side,
          pinX,
          pinY,
          pin == null ? "unused" : pin.role(),
          pin == null ? "" : pin.gateName(),
          pin == null ? "" : pin.netId(),
          chip.chipId() + "." + pinNumber));
    }
    placements.sort(Comparator
        .comparing(PinPlacement::side)
        .thenComparingInt(PinPlacement::y));
    return List.copyOf(placements);
  }

  private static int sideIndex(int pinNumber) {
    if (pinNumber <= 7) {
      return pinNumber - 1;
    }
    return 14 - pinNumber;
  }

  private static int canvasWidth(List<ChipBlock> blocks) {
    if (blocks.isEmpty()) {
      return 0;
    }
    ChipBlock last = blocks.get(blocks.size() - 1);
    return last.x() + last.width() + START_X;
  }

  private static int canvasHeight(List<ChipBlock> blocks) {
    if (blocks.isEmpty()) {
      return 0;
    }
    return START_Y + CHIP_HEIGHT + START_Y;
  }

  private static String escape(String text) {
    StringBuilder escaped = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      switch (ch) {
        case '\\' -> escaped.append("\\\\");
        case '"' -> escaped.append("\\\"");
        case '\n' -> escaped.append("\\n");
        case '\r' -> escaped.append("\\r");
        case '\t' -> escaped.append("\\t");
        default -> escaped.append(ch);
      }
    }
    return escaped.toString();
  }

  public record ChipBlock(
      String chipId,
      int chipNumber,
      String packageType,
      String gateKind,
      int x,
      int y,
      int width,
      int height,
      List<PinPlacement> pins) {}

  public record PinPlacement(
      int pinNumber,
      String side,
      int x,
      int y,
      String role,
      String gateName,
      String netId,
      String label) {}
}
