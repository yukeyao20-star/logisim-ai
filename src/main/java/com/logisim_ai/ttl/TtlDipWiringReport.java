package com.logisim_ai.ttl;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.NetSpec;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Reports logical net connections as real 14-pin DIP TTL package pins. */
public final class TtlDipWiringReport {
  private TtlDipWiringReport() {}

  public static List<Chip> chips(CircuitSpec spec) {
    Map<EndpointRef, String> endpointNets = endpointNets(spec);
    Map<String, ChipBuilder> chips = new LinkedHashMap<>();

    for (TtlPackageSpec packageSpec : TtlMultiPackageReport.standardPackages()) {
      for (TtlPackageMapper.GateAssignment assignment :
          TtlPackageMapper.assignments(spec, packageSpec)) {
        String chipKey = assignment.chipId();
        ChipBuilder chip = chips.computeIfAbsent(
            chipKey,
            ignored -> new ChipBuilder(
                assignment.chipId(),
                assignment.chipNumber(),
                packageSpec.packageType(),
                packageSpec.gateKindLabel()));
        for (PinBinding binding : pinBindings(packageSpec.packageType(), assignment.gateName())) {
          String netId = endpointNets.get(new EndpointRef(
              assignment.componentId(),
              binding.componentEndIndex()));
          chip.addPin(new Pin(
              binding.pinNumber(),
              binding.role(),
              assignment.gateName(),
              assignment.componentId(),
              binding.componentEndIndex(),
              netId == null ? "" : netId));
        }
      }
    }

    List<Chip> result = new ArrayList<>();
    for (ChipBuilder chip : chips.values()) {
      chip.addPowerPins();
      result.add(chip.build());
    }
    result.sort(Comparator.comparingInt(Chip::chipNumber));
    return List.copyOf(result);
  }

  public static String toMarkdown(CircuitSpec spec) {
    StringBuilder out = new StringBuilder();
    out.append("# TTL DIP wiring for ").append(spec.name()).append("\n\n");
    List<Chip> chips = chips(spec);
    if (chips.isEmpty()) {
      out.append("No TTL DIP assignments found.\n");
      return out.toString();
    }
    for (Chip chip : chips) {
      out.append("## ")
          .append(chip.chipId())
          .append(" - ")
          .append(chip.packageType())
          .append(" (")
          .append(chip.gateKind())
          .append(")\n\n");
      out.append("| Pin | Role | Gate | Component | End | Net |\n");
      out.append("| ---: | --- | --- | --- | ---: | --- |\n");
      for (Pin pin : chip.pins()) {
        out.append("| ")
            .append(pin.pinNumber()).append(" | ")
            .append(pin.role()).append(" | ")
            .append(pin.gateName()).append(" | ")
            .append(pin.componentId()).append(" | ")
            .append(pin.componentEndIndex()).append(" | ")
            .append(pin.netId()).append(" |\n");
      }
      out.append("\n");
    }
    return out.toString();
  }

  public static String toJson(CircuitSpec spec) {
    List<Chip> chips = chips(spec);
    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"circuitName\": \"").append(escape(spec.name())).append("\",\n");
    out.append("  \"chipCount\": ").append(chips.size()).append(",\n");
    out.append("  \"chips\": [\n");
    for (int i = 0; i < chips.size(); i++) {
      Chip chip = chips.get(i);
      out.append("    {\n");
      out.append("      \"chipId\": \"").append(escape(chip.chipId())).append("\",\n");
      out.append("      \"chipNumber\": ").append(chip.chipNumber()).append(",\n");
      out.append("      \"packageType\": \"").append(escape(chip.packageType())).append("\",\n");
      out.append("      \"gateKind\": \"").append(escape(chip.gateKind())).append("\",\n");
      out.append("      \"pins\": [\n");
      for (int j = 0; j < chip.pins().size(); j++) {
        Pin pin = chip.pins().get(j);
        out.append("        {\n");
        out.append("          \"pinNumber\": ").append(pin.pinNumber()).append(",\n");
        out.append("          \"role\": \"").append(escape(pin.role())).append("\",\n");
        out.append("          \"gateName\": \"").append(escape(pin.gateName())).append("\",\n");
        out.append("          \"componentId\": \"").append(escape(pin.componentId())).append("\",\n");
        out.append("          \"componentEndIndex\": ").append(pin.componentEndIndex()).append(",\n");
        out.append("          \"netId\": \"").append(escape(pin.netId())).append("\"\n");
        out.append("        }");
        if (j + 1 < chip.pins().size()) {
          out.append(",");
        }
        out.append("\n");
      }
      out.append("      ]\n");
      out.append("    }");
      if (i + 1 < chips.size()) {
        out.append(",");
      }
      out.append("\n");
    }
    out.append("  ]\n");
    out.append("}\n");
    return out.toString();
  }

  private static Map<EndpointRef, String> endpointNets(CircuitSpec spec) {
    Map<EndpointRef, String> result = new LinkedHashMap<>();
    for (NetSpec net : spec.nets()) {
      for (EndpointRef endpoint : net.endpoints()) {
        result.put(endpoint, net.id());
      }
    }
    return result;
  }

  private static List<PinBinding> pinBindings(String packageType, String gateName) {
    return switch (packageType) {
      case "7402" -> norPinBindings(gateName);
      case "7404" -> inverterPinBindings(gateName);
      case "7400", "7408", "7432", "7486" -> quadTwoInputPinBindings(gateName);
      default -> List.of();
    };
  }

  private static List<PinBinding> quadTwoInputPinBindings(String gateName) {
    return switch (gateName) {
      case "A" -> List.of(
          new PinBinding(1, "input1", 1),
          new PinBinding(2, "input2", 2),
          new PinBinding(3, "output", 0));
      case "B" -> List.of(
          new PinBinding(4, "input1", 1),
          new PinBinding(5, "input2", 2),
          new PinBinding(6, "output", 0));
      case "C" -> List.of(
          new PinBinding(10, "input1", 1),
          new PinBinding(9, "input2", 2),
          new PinBinding(8, "output", 0));
      case "D" -> List.of(
          new PinBinding(13, "input1", 1),
          new PinBinding(12, "input2", 2),
          new PinBinding(11, "output", 0));
      default -> List.of();
    };
  }

  private static List<PinBinding> norPinBindings(String gateName) {
    return switch (gateName) {
      case "A" -> List.of(
          new PinBinding(2, "input1", 1),
          new PinBinding(3, "input2", 2),
          new PinBinding(1, "output", 0));
      case "B" -> List.of(
          new PinBinding(5, "input1", 1),
          new PinBinding(6, "input2", 2),
          new PinBinding(4, "output", 0));
      case "C" -> List.of(
          new PinBinding(8, "input1", 1),
          new PinBinding(9, "input2", 2),
          new PinBinding(10, "output", 0));
      case "D" -> List.of(
          new PinBinding(11, "input1", 1),
          new PinBinding(12, "input2", 2),
          new PinBinding(13, "output", 0));
      default -> List.of();
    };
  }

  private static List<PinBinding> inverterPinBindings(String gateName) {
    return switch (gateName) {
      case "A" -> List.of(
          new PinBinding(1, "input", 1),
          new PinBinding(2, "output", 0));
      case "B" -> List.of(
          new PinBinding(3, "input", 1),
          new PinBinding(4, "output", 0));
      case "C" -> List.of(
          new PinBinding(5, "input", 1),
          new PinBinding(6, "output", 0));
      case "D" -> List.of(
          new PinBinding(9, "input", 1),
          new PinBinding(8, "output", 0));
      case "E" -> List.of(
          new PinBinding(11, "input", 1),
          new PinBinding(10, "output", 0));
      case "F" -> List.of(
          new PinBinding(13, "input", 1),
          new PinBinding(12, "output", 0));
      default -> List.of();
    };
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

  private record PinBinding(int pinNumber, String role, int componentEndIndex) {}

  private static final class ChipBuilder {
    private final String chipId;
    private final int chipNumber;
    private final String packageType;
    private final String gateKind;
    private final List<Pin> pins = new ArrayList<>();

    ChipBuilder(String chipId, int chipNumber, String packageType, String gateKind) {
      this.chipId = chipId;
      this.chipNumber = chipNumber;
      this.packageType = packageType;
      this.gateKind = gateKind;
    }

    void addPin(Pin pin) {
      pins.add(pin);
    }

    void addPowerPins() {
      pins.add(new Pin(7, "GND", "", "", -1, "GND"));
      pins.add(new Pin(14, "VCC", "", "", -1, "VCC"));
    }

    Chip build() {
      List<Pin> sortedPins = new ArrayList<>(pins);
      sortedPins.sort(Comparator.comparingInt(Pin::pinNumber));
      return new Chip(chipId, chipNumber, packageType, gateKind, List.copyOf(sortedPins));
    }
  }

  public record Chip(
      String chipId,
      int chipNumber,
      String packageType,
      String gateKind,
      List<Pin> pins) {}

  public record Pin(
      int pinNumber,
      String role,
      String gateName,
      String componentId,
      int componentEndIndex,
      String netId) {}
}
