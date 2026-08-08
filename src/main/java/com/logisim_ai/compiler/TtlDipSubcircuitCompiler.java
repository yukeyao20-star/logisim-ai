package com.logisim_ai.compiler;

import com.cburch.logisim.Main;
import com.cburch.logisim.circuit.Circuit;
import com.cburch.logisim.circuit.CircuitMutation;
import com.cburch.logisim.circuit.Wire;
import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.ComponentFactory;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.file.Loader;
import com.cburch.logisim.file.LogisimFile;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.proj.Project;
import com.cburch.logisim.std.base.Text;
import com.cburch.logisim.std.wiring.Pin;
import com.cburch.logisim.std.wiring.Tunnel;
import com.cburch.logisim.tools.AddTool;
import com.cburch.logisim.tools.Library;
import com.cburch.logisim.tools.Tool;
import com.logisim_ai.experiment.LogisimProjectSetup;
import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.ttl.TtlDipLayoutReport;
import com.logisim_ai.ttl.TtlDipWiringReport;
import com.logisim_ai.validation.CircuitLayoutInspector;
import com.logisim_ai.validation.TtlDipArtifactManifest;
import com.logisim_ai.validation.TtlDipBehaviorInspector;
import com.logisim_ai.validation.TtlDipPinMapInspector;
import com.logisim_ai.validation.TtlDipSubcircuitInspector;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Compiles TTL-labeled specs into a chip-package-oriented subcircuit view. */
public final class TtlDipSubcircuitCompiler {
  private static final int TOP_START_X = 620;
  private static final int TOP_START_Y = 220;
  private static final int TOP_CHIP_GAP_X = 420;
  private static final int TOP_PIN_GAP_Y = 60;

  private TtlDipSubcircuitCompiler() {}

  public static void compile(CircuitSpec spec, File outputFile) throws Exception {
    Main.headless = true;
    System.setProperty("java.awt.headless", "true");

    List<TtlDipWiringReport.Chip> chips = TtlDipWiringReport.chips(spec);
    if (chips.isEmpty()) {
      throw new IllegalArgumentException("Spec has no TTL DIP chip assignments: " + spec.name());
    }

    Loader loader = new Loader(null);
    LogisimFile file = LogisimFile.createNew(loader, null);
    LogisimProjectSetup.configureGuiDefaults(loader, file);
    Project project = new Project(file);
    Circuit top = new Circuit(spec.name() + "_dip_subckt", file, project);
    file.addCircuit(top);
    file.setMainCircuit(top);

    try {
      Map<String, Circuit> chipCircuits = new LinkedHashMap<>();
      for (TtlDipWiringReport.Chip chip : chips) {
        Circuit chipCircuit = createChipCircuit(loader, file, project, chip);
        file.addCircuit(chipCircuit);
        chipCircuits.put(chip.chipId(), chipCircuit);
      }

      List<PinEndBinding> pinMap = new ArrayList<>();
      createTopLevel(loader, file, top, spec, chips, chipCircuits, pinMap);
      writePinMapReports(spec, outputFile, pinMap);
      TtlDipPinMapInspector.requireConsistent(spec, outputFile);
      TtlDipBehaviorInspector.writeReports(spec, outputFile);
      saveAndVerify(loader, file, outputFile);
      TtlDipArtifactManifest.writeReports(spec, outputFile);
    } finally {
      try {
        if (project != null && project.getSimulator() != null) {
          project.getSimulator().shutDown();
        }
      } catch (Exception ignored) {
        // Best-effort.
      }
      file.stopAutosaveThread(false);
    }
  }

  private static Circuit createChipCircuit(
      Loader loader,
      LogisimFile file,
      Project project,
      TtlDipWiringReport.Chip chip) {
    Circuit circuit = new Circuit(chip.chipId() + "_" + chip.packageType(), file, project);
    CircuitMutation mutation = new CircuitMutation(circuit);
    Library gates = LogisimProjectSetup.addLibrary(loader, file, "Gates");
    ComponentFactory gateFactory = gateFactory(gates, chip.packageType());

    Map<Integer, Component> pinComponents = new HashMap<>();
    for (TtlDipWiringReport.Pin pin : completePins(chip)) {
      Component pinComponent = createDipPin(pin, chip.packageType());
      mutation.add(pinComponent);
      pinComponents.put(pin.pinNumber(), pinComponent);
    }

    for (Map.Entry<String, List<TtlDipWiringReport.Pin>> entry : pinsByGate(chip).entrySet()) {
      List<TtlDipWiringReport.Pin> gatePins = entry.getValue();
      if (gatePins.stream().noneMatch(pin -> pin.role().equals("output"))) {
        continue;
      }
      int gateIndex = gateIndex(entry.getKey());
      Component gate = createInternalGate(gateFactory, chip.gateKind(), gateIndex);
      mutation.add(gate);
      for (TtlDipWiringReport.Pin pin : gatePins) {
        Component pinComponent = pinComponents.get(pin.pinNumber());
        if (pinComponent == null) {
          continue;
        }
        int gateEnd = switch (pin.role()) {
          case "output" -> 0;
          case "input", "input1" -> 1;
          case "input2" -> 2;
          default -> -1;
        };
        if (gateEnd >= 0 && gateEnd < gate.getEnds().size()) {
          connectOrthogonal(mutation, pinComponent.getEnd(0).getLocation(), gate.getEnd(gateEnd).getLocation());
        }
      }
    }

    addText(mutation, chip.chipId() + " " + chip.packageType(), 230, 40);
    mutation.execute();
    circuit.getAppearance().recomputeDefaultAppearance();
    return circuit;
  }

  private static void createTopLevel(
      Loader loader,
      LogisimFile file,
      Circuit top,
      CircuitSpec spec,
      List<TtlDipWiringReport.Chip> chips,
      Map<String, Circuit> chipCircuits,
      List<PinEndBinding> pinMap) {
    CircuitMutation mutation = new CircuitMutation(top);
    Library wiring = LogisimProjectSetup.addLibrary(loader, file, "Wiring");
    ComponentFactory tunnelFactory = findFactory(wiring, "Tunnel");
    Map<String, String> netLabels = stableNetLabels(spec);

    int index = 0;
    for (TtlDipWiringReport.Chip chip : chips) {
      Circuit chipCircuit = chipCircuits.get(chip.chipId());
      var attrs = chipCircuit.getSubcircuitFactory().createAttributeSet();
      attrs.setValue(StdAttr.LABEL, chip.chipId() + " " + chip.packageType());
      Location loc = Location.create(TOP_START_X + index * TOP_CHIP_GAP_X, TOP_START_Y, true);
      Component chipComponent = chipCircuit.getSubcircuitFactory().createComponent(loc, attrs);
      mutation.add(chipComponent);
      addText(mutation, chip.chipId() + " " + chip.packageType(), loc.getX(), loc.getY() - 95);

      List<TtlDipLayoutReport.PinPlacement> topPins = chipPinsForTop(chip, index);
      Map<Integer, Integer> pinToEnd = physicalPinToEndIndexes(chipComponent, topPins);
      for (TtlDipLayoutReport.PinPlacement pin : topPins) {
        if (pin.netId().isBlank()) {
          continue;
        }
        String label = netLabels.getOrDefault(pin.netId(), pin.netId());
        Component tunnel = createTunnel(tunnelFactory, label, Location.create(pin.x(), pin.y(), true), tunnelFacing(pin));
        mutation.add(tunnel);
        Integer endIndex = pinToEnd.get(pin.pinNumber());
        if (endIndex != null) {
          Location endLocation = chipComponent.getEnd(endIndex).getLocation();
          pinMap.add(new PinEndBinding(
              chip.chipId(),
              chip.packageType(),
              pin.pinNumber(),
              pin.role(),
              pin.gateName(),
              pin.netId(),
              label,
              endIndex,
              endLocation.getX(),
              endLocation.getY(),
              tunnel.getEnd(0).getLocation().getX(),
              tunnel.getEnd(0).getLocation().getY()));
          connectOrthogonal(mutation, tunnel.getEnd(0).getLocation(), endLocation);
        }
        addText(mutation, pin.pinNumber() + ":" + pin.netId(), pin.x() + labelOffset(pin), pin.y() - 6);
      }
      index++;
    }

    addExternalPorts(mutation, tunnelFactory, spec, netLabels);
    mutation.execute();
  }

  private static List<TtlDipLayoutReport.PinPlacement> chipPinsForTop(
      TtlDipWiringReport.Chip chip,
      int chipIndex) {
    int chipLeft = TOP_START_X + chipIndex * TOP_CHIP_GAP_X - 90;
    int chipRight = TOP_START_X + chipIndex * TOP_CHIP_GAP_X + 90;
    int y0 = TOP_START_Y - 60;
    List<TtlDipLayoutReport.PinPlacement> pins = new ArrayList<>();
    for (TtlDipWiringReport.Pin pin : completePins(chip)) {
      String side = pin.pinNumber() <= 7 ? "LEFT" : "RIGHT";
      int x = side.equals("LEFT") ? chipLeft : chipRight;
      int y = y0 + sideIndex(pin.pinNumber()) * TOP_PIN_GAP_Y;
      pins.add(new TtlDipLayoutReport.PinPlacement(
          pin.pinNumber(), side, x, y, pin.role(), pin.gateName(), pin.netId(), chip.chipId() + "." + pin.pinNumber()));
    }
    pins.sort(Comparator.comparing(TtlDipLayoutReport.PinPlacement::side)
        .thenComparingInt(TtlDipLayoutReport.PinPlacement::y));
    return List.copyOf(pins);
  }

  private static void addExternalPorts(
      CircuitMutation mutation,
      ComponentFactory tunnelFactory,
      CircuitSpec spec,
      Map<String, String> netLabels) {
    int inputY = 90;
    int outputY = 90;
    for (ExternalPort port : spec.externalPorts()) {
      String netId = netForPort(spec, port.id());
      if (netId == null) {
        continue;
      }
      String label = netLabels.getOrDefault(netId, netId);
      if (port.direction() == PortDirection.INPUT) {
        Component pin = createTopPortPin(port, Location.create(70, inputY, true));
        Component tunnel = createTunnel(tunnelFactory, label, Location.create(240, inputY, true), Direction.WEST);
        mutation.add(pin);
        mutation.add(tunnel);
        mutation.add(Wire.create(pin.getEnd(0).getLocation(), tunnel.getEnd(0).getLocation()));
        inputY += 70;
      } else {
        Component tunnel = createTunnel(tunnelFactory, label, Location.create(1900, outputY, true), Direction.EAST);
        Component pin = createTopPortPin(port, Location.create(2070, outputY, true));
        mutation.add(tunnel);
        mutation.add(pin);
        mutation.add(Wire.create(tunnel.getEnd(0).getLocation(), pin.getEnd(0).getLocation()));
        outputY += 70;
      }
    }
  }

  private static Component createTopPortPin(ExternalPort port, Location location) {
    var attrs = Pin.FACTORY.createAttributeSet();
    attrs.setValue(StdAttr.LABEL, port.label());
    attrs.setValue(StdAttr.WIDTH, BitWidth.create(port.bitWidth()));
    if (port.direction() == PortDirection.OUTPUT) {
      attrs.setValue(Pin.ATTR_TYPE, Pin.OUTPUT);
      attrs.setValue(StdAttr.FACING, Direction.WEST);
    } else {
      attrs.setValue(StdAttr.FACING, Direction.EAST);
    }
    return Pin.FACTORY.createComponent(location, attrs);
  }

  private static Component createDipPin(TtlDipWiringReport.Pin pin, String packageType) {
    var attrs = Pin.FACTORY.createAttributeSet();
    attrs.setValue(StdAttr.LABEL, pinLabel(pin));
    attrs.setValue(StdAttr.WIDTH, BitWidth.ONE);
    if (pin.role().equals("output")) {
      attrs.setValue(Pin.ATTR_TYPE, Pin.OUTPUT);
      attrs.setValue(StdAttr.FACING, Direction.WEST);
    } else {
      attrs.setValue(StdAttr.FACING, Direction.EAST);
    }
    int x = pin.pinNumber() <= 7 ? 80 : 420;
    int y = 90 + sideIndex(pin.pinNumber()) * 40;
    if (pin.pinNumber() > 7) {
      attrs.setValue(StdAttr.FACING, Direction.WEST);
    }
    if (pin.role().equals("output") && pin.pinNumber() > 7) {
      attrs.setValue(StdAttr.FACING, Direction.EAST);
    }
    return Pin.FACTORY.createComponent(Location.create(x, y, true), attrs);
  }

  private static String pinLabel(TtlDipWiringReport.Pin pin) {
    String suffix = pin.netId().isBlank() ? pin.role() : pin.netId();
    return pin.pinNumber() + ":" + suffix;
  }

  private static Component createInternalGate(ComponentFactory gateFactory, String gateKind, int gateIndex) {
    var attrs = gateFactory.createAttributeSet();
    attrs.setValue(StdAttr.LABEL, gateKind + gateIndex);
    int x = 260;
    int y = 110 + gateIndex * 65;
    return gateFactory.createComponent(Location.create(x, y, true), attrs);
  }

  private static Component createTunnel(
      ComponentFactory tunnelFactory, String label, Location location, Direction facing) {
    var attrs = tunnelFactory.createAttributeSet();
    attrs.setValue(StdAttr.LABEL, label);
    attrs.setValue(StdAttr.FACING, facing);
    return tunnelFactory.createComponent(location, attrs);
  }

  private static void addText(CircuitMutation mutation, String text, int x, int y) {
    var attrs = Text.FACTORY.createAttributeSet();
    attrs.setValue(Text.ATTR_TEXT, text);
    mutation.add(Text.FACTORY.createComponent(Location.create(x, y, true), attrs));
  }

  private static List<TtlDipWiringReport.Pin> completePins(TtlDipWiringReport.Chip chip) {
    Map<Integer, TtlDipWiringReport.Pin> byNumber = new HashMap<>();
    for (TtlDipWiringReport.Pin pin : chip.pins()) {
      byNumber.put(pin.pinNumber(), pin);
    }
    List<TtlDipWiringReport.Pin> pins = new ArrayList<>();
    for (int pin = 1; pin <= 14; pin++) {
      pins.add(byNumber.getOrDefault(pin, new TtlDipWiringReport.Pin(pin, "unused", "", "", -1, "")));
    }
    return List.copyOf(pins);
  }

  private static Map<String, List<TtlDipWiringReport.Pin>> pinsByGate(TtlDipWiringReport.Chip chip) {
    Map<String, List<TtlDipWiringReport.Pin>> result = new LinkedHashMap<>();
    for (TtlDipWiringReport.Pin pin : chip.pins()) {
      if (pin.gateName().isBlank()) {
        continue;
      }
      result.computeIfAbsent(pin.gateName(), ignored -> new ArrayList<>()).add(pin);
    }
    return result;
  }

  private static Map<String, String> stableNetLabels(CircuitSpec spec) {
    Map<String, String> result = new LinkedHashMap<>();
    int seq = 1;
    for (NetSpec net : spec.nets()) {
      result.put(net.id(), "n" + seq++);
    }
    result.put("VCC", "VCC");
    result.put("GND", "GND");
    return result;
  }

  private static String netForPort(CircuitSpec spec, String portId) {
    return spec.nets().stream()
        .filter(net -> net.endpoints().stream().anyMatch(endpoint -> endpoint.componentId().equals(portId)))
        .map(NetSpec::id)
        .findFirst()
        .orElse(null);
  }

  private static ComponentFactory gateFactory(Library gates, String packageType) {
    return switch (packageType) {
      case "7400" -> findFactory(gates, "NAND Gate");
      case "7402" -> findFactory(gates, "NOR Gate");
      case "7404" -> findFactory(gates, "NOT Gate");
      case "7408" -> findFactory(gates, "AND Gate");
      case "7432" -> findFactory(gates, "OR Gate");
      case "7486" -> findFactory(gates, "XOR Gate");
      default -> throw new IllegalArgumentException("Unsupported DIP package type: " + packageType);
    };
  }

  private static ComponentFactory findFactory(Library library, String name) {
    for (Tool tool : library.getTools()) {
      if (tool instanceof AddTool addTool && name.equals(addTool.getFactory().getName())) {
        return addTool.getFactory();
      }
    }
    throw new IllegalStateException("Missing factory " + name + " in " + library.getName());
  }

  private static int sideIndex(int pinNumber) {
    if (pinNumber <= 7) {
      return pinNumber - 1;
    }
    return 14 - pinNumber;
  }

  private static int gateIndex(String gateName) {
    if (gateName.isBlank()) {
      return 0;
    }
    return Math.max(0, gateName.charAt(0) - 'A');
  }

  private static Direction tunnelFacing(TtlDipLayoutReport.PinPlacement pin) {
    return pin.side().equals("LEFT") ? Direction.EAST : Direction.WEST;
  }

  private static int labelOffset(TtlDipLayoutReport.PinPlacement pin) {
    return pin.side().equals("LEFT") ? -70 : 15;
  }

  private static void connectOrthogonal(CircuitMutation mutation, Location from, Location to) {
    if (from.equals(to)) {
      return;
    }
    if (from.getX() == to.getX() || from.getY() == to.getY()) {
      mutation.add(Wire.create(from, to));
      return;
    }
    Location corner = Location.create(to.getX(), from.getY(), true);
    mutation.add(Wire.create(from, corner));
    mutation.add(Wire.create(corner, to));
  }

  private static Map<Integer, Integer> physicalPinToEndIndexes(
      Component component,
      List<TtlDipLayoutReport.PinPlacement> pins) {
    Map<Integer, Integer> result = new LinkedHashMap<>();
    List<IndexedEnd> ends = new ArrayList<>();
    for (int i = 0; i < component.getEnds().size(); i++) {
      Location loc = component.getEnd(i).getLocation();
      ends.add(new IndexedEnd(i, loc.getX(), loc.getY()));
    }
    List<IndexedEnd> unusedEnds = new ArrayList<>(ends);
    for (TtlDipLayoutReport.PinPlacement pin : pins) {
      IndexedEnd best = null;
      int bestScore = Integer.MAX_VALUE;
      for (IndexedEnd end : unusedEnds) {
        int score = Math.abs(end.x() - pin.x()) + Math.abs(end.y() - pin.y());
        if (score < bestScore) {
          bestScore = score;
          best = end;
        }
      }
      if (best != null) {
        result.put(pin.pinNumber(), best.index());
        unusedEnds.remove(best);
      }
    }
    return result;
  }

  private static void saveAndVerify(Loader loader, LogisimFile file, File outputFile) throws Exception {
    File parent = outputFile.getParentFile();
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }
    if (!loader.save(file, outputFile)) {
      throw new IllegalStateException("Loader.save returned false");
    }
    LogisimProjectSetup.patchGeneratedProjectXml(outputFile);
    Loader verifyLoader = new Loader(null);
    LogisimFile loaded = verifyLoader.openLogisimFile(outputFile);
    try {
      if (loaded == null || loaded.getMainCircuit() == null) {
        throw new IllegalStateException("Round-trip load failed: " + outputFile);
      }
    } finally {
      if (loaded != null) {
        loaded.stopAutosaveThread(false);
      }
    }
    CircuitLayoutInspector.requireClean(outputFile);
    TtlDipSubcircuitInspector.requireClean(outputFile);
  }

  private static void writePinMapReports(
      CircuitSpec spec,
      File outputFile,
      List<PinEndBinding> pinMap) throws Exception {
    Path reportDir = Path.of("generated", "reports");
    Files.createDirectories(reportDir);
    Path markdown = reportDir.resolve(spec.name() + "_ttl_dip_subckt_pinmap.md");
    Path json = reportDir.resolve(spec.name() + "_ttl_dip_subckt_pinmap.json");
    Files.writeString(markdown, pinMapMarkdown(spec, outputFile, pinMap));
    Files.writeString(json, pinMapJson(spec, outputFile, pinMap));
  }

  private static String pinMapMarkdown(CircuitSpec spec, File outputFile, List<PinEndBinding> pinMap) {
    StringBuilder out = new StringBuilder();
    out.append("# TTL DIP subcircuit pin map for ").append(spec.name()).append("\n\n");
    out.append("- Circuit file: ").append(outputFile.getPath()).append("\n");
    out.append("- Bound pins: ").append(pinMap.size()).append("\n\n");
    out.append("| Chip | Package | Pin | Role | Gate | Net | Tunnel | End | End X | End Y | Tunnel X | Tunnel Y |\n");
    out.append("| --- | --- | ---: | --- | --- | --- | --- | ---: | ---: | ---: | ---: | ---: |\n");
    for (PinEndBinding binding : pinMap) {
      out.append("| ")
          .append(binding.chipId()).append(" | ")
          .append(binding.packageType()).append(" | ")
          .append(binding.pinNumber()).append(" | ")
          .append(binding.role()).append(" | ")
          .append(binding.gateName()).append(" | ")
          .append(binding.netId()).append(" | ")
          .append(binding.tunnelLabel()).append(" | ")
          .append(binding.endIndex()).append(" | ")
          .append(binding.endX()).append(" | ")
          .append(binding.endY()).append(" | ")
          .append(binding.tunnelX()).append(" | ")
          .append(binding.tunnelY()).append(" |\n");
    }
    return out.toString();
  }

  private static String pinMapJson(CircuitSpec spec, File outputFile, List<PinEndBinding> pinMap) {
    StringBuilder out = new StringBuilder();
    out.append("{\n");
    out.append("  \"circuitName\": \"").append(escape(spec.name())).append("\",\n");
    out.append("  \"circuitFile\": \"").append(escape(outputFile.getPath())).append("\",\n");
    out.append("  \"boundPinCount\": ").append(pinMap.size()).append(",\n");
    out.append("  \"bindings\": [\n");
    for (int i = 0; i < pinMap.size(); i++) {
      PinEndBinding binding = pinMap.get(i);
      out.append("    {\n");
      out.append("      \"chipId\": \"").append(escape(binding.chipId())).append("\",\n");
      out.append("      \"packageType\": \"").append(escape(binding.packageType())).append("\",\n");
      out.append("      \"pinNumber\": ").append(binding.pinNumber()).append(",\n");
      out.append("      \"role\": \"").append(escape(binding.role())).append("\",\n");
      out.append("      \"gateName\": \"").append(escape(binding.gateName())).append("\",\n");
      out.append("      \"netId\": \"").append(escape(binding.netId())).append("\",\n");
      out.append("      \"tunnelLabel\": \"").append(escape(binding.tunnelLabel())).append("\",\n");
      out.append("      \"endIndex\": ").append(binding.endIndex()).append(",\n");
      out.append("      \"endX\": ").append(binding.endX()).append(",\n");
      out.append("      \"endY\": ").append(binding.endY()).append(",\n");
      out.append("      \"tunnelX\": ").append(binding.tunnelX()).append(",\n");
      out.append("      \"tunnelY\": ").append(binding.tunnelY()).append("\n");
      out.append("    }");
      if (i + 1 < pinMap.size()) {
        out.append(",");
      }
      out.append("\n");
    }
    out.append("  ]\n");
    out.append("}\n");
    return out.toString();
  }

  private static String escape(String text) {
    return text
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t");
  }

  private record IndexedEnd(int index, int x, int y) {}

  private record PinEndBinding(
      String chipId,
      String packageType,
      int pinNumber,
      String role,
      String gateName,
      String netId,
      String tunnelLabel,
      int endIndex,
      int endX,
      int endY,
      int tunnelX,
      int tunnelY) {}
}
