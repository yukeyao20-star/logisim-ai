package com.logisim_ai.layout;

import com.cburch.logisim.comp.Component;
import com.cburch.logisim.comp.ComponentFactory;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Location;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.std.wiring.Pin;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.LayoutHint;
import com.logisim_ai.ir.PortDirection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * First-version grid layout engine for multi-component circuits.
 *
 * <p>Rules:
 * 1. Input Pins fixed at leftmost column (x=80).
 * 2. Output Pins fixed at rightmost column (x=80+totalColumns*columnSpacing).
 * 3. Logic components placed in middle columns by topological rank.
 * 4. Same-column components spaced at least 90 px vertically.
 * 5. No two components at the same location.
 * 6. Horizontal spacing between columns: at least 180 px.
 * 7. All wires must be orthogonal (enforced by caller).
 * 8. When wiring is complex, prefer Tunnels over long wires.
 */
public final class GridLayoutEngine {
  private GridLayoutEngine() {}

  public static final int INPUT_PIN_X = 80;
  public static final int COLUMN_SPACING = 200;
  public static final int ROW_SPACING = 100;
  public static final int VERTICAL_START = 100;
  public static final int PIN_TO_COMPONENT_GAP = 120;

  /** Result of the layout process. */
  public record LayoutResult(
      List<PlacedComponent> components,
      List<PlacedPort> ports,
      int totalWidth,
      int totalHeight) {}

  /**
   * Lay out all components and external ports based on layout hints.
   *
   * <p>If no hints are provided, components are arranged in a single
   * column by their order in the spec list.
   */
  public static LayoutResult layout(
      List<ComponentSpec> componentSpecs,
      List<ExternalPort> externalPorts,
      List<LayoutHint> hints,
      Map<String, Component> instantiatedComponents) {

    // Determine max rank from hints (or default to sequential)
    int maxRank = 0;
    Map<String, Integer> rankMap = new HashMap<>();
    Map<String, Integer> rowMap = new HashMap<>();

    if (hints != null && !hints.isEmpty()) {
      for (LayoutHint hint : hints) {
        rankMap.put(hint.componentId(), hint.rank());
        rowMap.put(hint.componentId(), hint.row());
        maxRank = Math.max(maxRank, hint.rank());
      }
    } else {
      // Default: assign sequential ranks
      for (int i = 0; i < componentSpecs.size(); i++) {
        rankMap.put(componentSpecs.get(i).id(), i);
        rowMap.put(componentSpecs.get(i).id(), 0);
        maxRank = Math.max(maxRank, i);
      }
    }

    // Place logic components in columns by rank
    Map<Integer, List<String>> byRank = new HashMap<>();
    for (ComponentSpec spec : componentSpecs) {
      int rank = rankMap.getOrDefault(spec.id(), 0);
      byRank.computeIfAbsent(rank, k -> new ArrayList<>()).add(spec.id());
    }

    List<PlacedComponent> placed = new ArrayList<>();
    for (int rank = 0; rank <= maxRank; rank++) {
      List<String> ids = byRank.getOrDefault(rank, List.of());
      int col = INPUT_PIN_X + PIN_TO_COMPONENT_GAP + rank * COLUMN_SPACING;
      for (int i = 0; i < ids.size(); i++) {
        String id = ids.get(i);
        int row = rowMap.getOrDefault(id, i);
        int y = VERTICAL_START + row * ROW_SPACING;
        Component comp = instantiatedComponents.get(id);
        if (comp != null) {
          placed.add(new PlacedComponent(id, comp, Location.create(col, y, true)));
        }
      }
    }

    // Place external ports using ReadableChipLayout for clean spacing
    int totalColumns = maxRank + 1;
    int outputPinX = INPUT_PIN_X + PIN_TO_COMPONENT_GAP + totalColumns * COLUMN_SPACING + PIN_TO_COMPONENT_GAP;

    // Build port specs and place them
    List<ExternalPortSpec> portSpecs = externalPorts.stream()
        .map(p -> new ExternalPortSpec(p.label(), BitWidth.create(p.bitWidth()), p.side()))
        .toList();

    ReadableChipLayout portLayout = new ReadableChipLayout(
        Location.create(INPUT_PIN_X + PIN_TO_COMPONENT_GAP + (totalColumns * COLUMN_SPACING) / 2, 250, true));
    List<PlacedPort> ports = portLayout.placePorts(portSpecs);

    // Override pin X positions for LEFT/RIGHT ports to align with grid
    List<PlacedPort> adjustedPorts = new ArrayList<>();
    for (int i = 0; i < ports.size(); i++) {
      PlacedPort p = ports.get(i);
      ExternalPort ep = externalPorts.get(i);
      PlacedPort adjusted;
      if (ep.side() == PortSide.LEFT) {
        // Input pins on left
        adjusted = new PlacedPort(p.label(), p.width(), p.side(), p.pinFacing(),
            Location.create(INPUT_PIN_X, p.pinLocation().getY(), true),
            Location.create(INPUT_PIN_X + 50, p.routeAnchor().getY(), true));
      } else if (ep.side() == PortSide.RIGHT) {
        // Output pins on right
        adjusted = new PlacedPort(p.label(), p.width(), p.side(), p.pinFacing(),
            Location.create(outputPinX, p.pinLocation().getY(), true),
            Location.create(outputPinX - 50, p.routeAnchor().getY(), true));
      } else {
        adjusted = p; // TOP/BOTTOM keep ReadableChipLayout placement
      }
      adjustedPorts.add(adjusted);
    }

    int totalWidth = outputPinX + 100;
    int maxY = ports.stream().mapToInt(p -> p.pinLocation().getY()).max().orElse(300);
    int totalHeight = maxY + 100;

    return new LayoutResult(placed, adjustedPorts, totalWidth, totalHeight);
  }
}
