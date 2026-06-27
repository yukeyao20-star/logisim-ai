package com.logisim_ai.layout;

import com.cburch.logisim.comp.Component;
import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.LayoutHint;
import com.logisim_ai.ir.PortDirection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Grid layout engine for multi-component circuits.
 *
 * <p>Two-phase layout:
 * <ol>
 *   <li>Phase 1 ({@link #layoutComponents}): place logic components by rank/column/row.</li>
 *   <li>Phase 2 ({@link #layoutPorts}): place external pins aligned to target component endpoints.</li>
 * </ol>
 *
 * <p>Rules:
 * 1. Input Pins fixed at leftmost column (x=80).
 * 2. Output Pins fixed at rightmost column.
 * 3. Logic components placed in middle columns by topological rank.
 * 4. Same-rank components sorted by row, spaced vertically.
 * 5. Input Pin y-coordinate aligns with target component input endpoint.
 * 6. Output Pin y-coordinate aligns with target component output endpoint.
 * 7. If multiple pins map to the same y, offset by MIN_PORT_SPACING.
 * 8. All coordinates snap to 10px grid.
 */
public final class GridLayoutEngine {
  private GridLayoutEngine() {}

  public static final int INPUT_PIN_X = AutoLayoutPolicy.INPUT_PIN_X;
  public static final int COLUMN_SPACING = AutoLayoutPolicy.COLUMN_SPACING;
  public static final int ROW_SPACING = AutoLayoutPolicy.ROW_SPACING;
  public static final int VERTICAL_START = AutoLayoutPolicy.VERTICAL_START;
  public static final int PIN_TO_COMPONENT_GAP = AutoLayoutPolicy.PIN_TO_COMPONENT_GAP;
  public static final int MIN_PORT_SPACING = AutoLayoutPolicy.MIN_PORT_SPACING;
  public static final int GRID = AutoLayoutPolicy.GRID;

  /** Result of Phase 1: logic component positions. */
  public record ComponentLayoutResult(
      List<PlacedComponent> components,
      int totalColumns,
      int totalWidth) {}

  /** Result of Phase 2: port pin positions. */
  public record PortLayoutResult(
      List<PlacedPort> ports,
      int totalHeight) {}

  /** Combined result (backward-compatible). */
  public record LayoutResult(
      List<PlacedComponent> components,
      List<PlacedPort> ports,
      int totalWidth,
      int totalHeight) {}

  // ---- Phase 1: Place logic components ----

  /**
   * Phase 1: Compute positions for logic components based on layout hints.
   *
   * <p>Components are arranged in columns by rank, with same-rank components
   * sorted by row value. If no hints are provided, components get sequential
   * ranks in a single row.
   *
   * @param componentSpecs the component specifications
   * @param hints layout hints (may be null or empty)
   * @return component positions and overall dimensions
   */
  public static ComponentLayoutResult layoutComponents(
      List<ComponentSpec> componentSpecs,
      List<LayoutHint> hints) {

    // Build rank and row maps from hints
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

    // Group by rank, then sort each group by row
    Map<Integer, List<String>> byRank = new HashMap<>();
    for (ComponentSpec spec : componentSpecs) {
      int rank = rankMap.getOrDefault(spec.id(), 0);
      byRank.computeIfAbsent(rank, k -> new ArrayList<>()).add(spec.id());
    }
    // Sort each rank group by row value
    for (List<String> ids : byRank.values()) {
      ids.sort(Comparator.comparingInt(id -> rowMap.getOrDefault(id, 0)));
    }

    // Place components at grid positions
    List<PlacedComponent> placed = new ArrayList<>();
    for (int rank = 0; rank <= maxRank; rank++) {
      List<String> ids = byRank.getOrDefault(rank, List.of());
      int col = INPUT_PIN_X + PIN_TO_COMPONENT_GAP + rank * COLUMN_SPACING;
      for (int i = 0; i < ids.size(); i++) {
        String id = ids.get(i);
        int row = rowMap.getOrDefault(id, i);
        int y = snap(VERTICAL_START + row * ROW_SPACING);
        // Use a placeholder component; will be replaced by real positioned component later
        placed.add(new PlacedComponent(id, null, Location.create(col, y, true)));
      }
    }

    int totalColumns = maxRank + 1;
    int outputPinX = INPUT_PIN_X + PIN_TO_COMPONENT_GAP + totalColumns * COLUMN_SPACING + PIN_TO_COMPONENT_GAP;
    int totalWidth = outputPinX + 100;

    return new ComponentLayoutResult(placed, totalColumns, totalWidth);
  }

  // ---- Phase 2: Place external ports aligned to endpoints ----

  /**
   * Phase 2: Compute positions for external port pins, aligned to their
   * target component endpoints.
   *
   * <p>For INPUT pins: y-coordinate aligns with the target component's
   * input endpoint (targetEndIndex).
   * For OUTPUT pins: y-coordinate aligns with the target component's
   * output endpoint (targetEndIndex, usually end 0).
   *
   * @param externalPorts the port specifications
   * @param positionedComponents map from component id to instantiated component
   * @param outputPinX x-coordinate for output pins
   * @return port positions and total canvas height
   */
  public static PortLayoutResult layoutPorts(
      List<ExternalPort> externalPorts,
      Map<String, Component> positionedComponents,
      int outputPinX) {

    List<PlacedPort> ports = new ArrayList<>();

    // Track used y-coordinates per side to avoid overlaps
    Map<PortSide, List<Integer>> usedY = new HashMap<>();
    usedY.put(PortSide.LEFT, new ArrayList<>());
    usedY.put(PortSide.RIGHT, new ArrayList<>());

    for (ExternalPort ep : externalPorts) {
      Component target = positionedComponents.get(ep.targetComponentId());
      if (target == null) {
        throw new IllegalStateException(
            "External port '" + ep.id() + "' targets unknown component: " + ep.targetComponentId());
      }

      // Get the absolute location of the target endpoint
      Location targetPortLoc = target.getEnd(ep.targetEndIndex()).getLocation();
      int targetY = targetPortLoc.getY();

      // Determine pin side and x position
      int pinX;
      Direction pinFacing;
      int routeAnchorX;
      if (ep.side() == PortSide.LEFT) {
        pinX = INPUT_PIN_X;
        pinFacing = Direction.EAST;
        routeAnchorX = INPUT_PIN_X + 50;
      } else if (ep.side() == PortSide.RIGHT) {
        pinX = outputPinX;
        pinFacing = Direction.WEST;
        routeAnchorX = outputPinX - 50;
      } else {
        // TOP/BOTTOM: fallback to simple layout
        pinX = (INPUT_PIN_X + outputPinX) / 2;
        pinFacing = ep.side() == PortSide.TOP ? Direction.SOUTH : Direction.NORTH;
        routeAnchorX = pinX;
      }

      // Resolve y-coordinate conflicts: if this y is already used, offset
      int resolvedY = resolveYConflict(targetY, usedY.getOrDefault(ep.side(), new ArrayList<>()));
      usedY.computeIfAbsent(ep.side(), k -> new ArrayList<>()).add(resolvedY);

      Location pinLocation = Location.create(pinX, resolvedY, true);
      Location routeAnchor = Location.create(routeAnchorX, resolvedY, true);

      ports.add(new PlacedPort(
          ep.label(),
          BitWidth.create(ep.bitWidth()),
          ep.side(),
          pinFacing,
          pinLocation,
          routeAnchor));
    }

    int maxY = ports.stream()
        .mapToInt(p -> p.pinLocation().getY())
        .max()
        .orElse(300);
    int totalHeight = maxY + 100;

    return new PortLayoutResult(ports, totalHeight);
  }

  // ---- Backward-compatible combined layout ----

  /**
   * Combined layout (backward-compatible with old API).
   *
   * <p>Uses the two-phase approach internally, but since the old API
   * receives pre-instantiated components (at origin), it falls back to
   * the simpler alignment strategy for ports.
   */
  public static LayoutResult layout(
      List<ComponentSpec> componentSpecs,
      List<ExternalPort> externalPorts,
      List<LayoutHint> hints,
      Map<String, Component> instantiatedComponents) {

    ComponentLayoutResult compResult = layoutComponents(componentSpecs, hints);

    // For the old API, components are instantiated at (0,0), so we can't
    // use their endpoint positions directly. Instead, we compute port positions
    // based on the planned component positions.
    // Map from componentId -> planned Location
    Map<String, Location> plannedLocations = new HashMap<>();
    for (PlacedComponent pc : compResult.components()) {
      plannedLocations.put(pc.id(), pc.location());
    }

    // Estimate endpoint y-coordinates based on planned locations
    // For a 2-input gate at (gx, gy): input end y = gy ± 20
    // This is an approximation; the precise positions require instantiated components.
    List<PlacedPort> ports = new ArrayList<>();
    Map<PortSide, List<Integer>> usedY = new HashMap<>();
    usedY.put(PortSide.LEFT, new ArrayList<>());
    usedY.put(PortSide.RIGHT, new ArrayList<>());

    int outputPinX = compResult.totalWidth() - 100;

    for (ExternalPort ep : externalPorts) {
      Location planned = plannedLocations.get(ep.targetComponentId());
      if (planned == null) {
        throw new IllegalStateException(
            "External port '" + ep.id() + "' targets unknown component: " + ep.targetComponentId());
      }

      // Estimate the target endpoint y based on planned location
      // For end 0 (output): y = planned.y
      // For end 1 (input 0): y = planned.y - 20
      // For end 2 (input 1): y = planned.y + 20
      int targetY = estimateEndpointY(planned, ep.targetEndIndex());

      int pinX;
      Direction pinFacing;
      int routeAnchorX;
      if (ep.side() == PortSide.LEFT) {
        pinX = INPUT_PIN_X;
        pinFacing = Direction.EAST;
        routeAnchorX = INPUT_PIN_X + 50;
      } else if (ep.side() == PortSide.RIGHT) {
        pinX = outputPinX;
        pinFacing = Direction.WEST;
        routeAnchorX = outputPinX - 50;
      } else {
        pinX = (INPUT_PIN_X + outputPinX) / 2;
        pinFacing = ep.side() == PortSide.TOP ? Direction.SOUTH : Direction.NORTH;
        routeAnchorX = pinX;
      }

      int resolvedY = resolveYConflict(targetY, usedY.getOrDefault(ep.side(), new ArrayList<>()));
      usedY.computeIfAbsent(ep.side(), k -> new ArrayList<>()).add(resolvedY);

      Location pinLocation = Location.create(pinX, resolvedY, true);
      Location routeAnchor = Location.create(routeAnchorX, resolvedY, true);

      ports.add(new PlacedPort(
          ep.label(),
          BitWidth.create(ep.bitWidth()),
          ep.side(),
          pinFacing,
          pinLocation,
          routeAnchor));
    }

    int maxY = ports.stream()
        .mapToInt(p -> p.pinLocation().getY())
        .max()
        .orElse(300);
    int totalHeight = maxY + 100;

    // Build placed components with real component references
    List<PlacedComponent> placedWithComps = new ArrayList<>();
    for (PlacedComponent pc : compResult.components()) {
      Component comp = instantiatedComponents.get(pc.id());
      placedWithComps.add(new PlacedComponent(pc.id(), comp, pc.location()));
    }

    return new LayoutResult(placedWithComps, ports, compResult.totalWidth(), totalHeight);
  }

  // ---- Helpers ----

  /**
   * Estimate the y-coordinate of a component endpoint based on its planned location.
   *
   * <p>This is an approximation based on typical 2-input gate layouts:
   * - End 0 (output): same y as component location
   * - End 1 (first input): y - 20
   * - End 2 (second input): y + 20
   * - End N (additional inputs): evenly distributed
   */
  static int estimateEndpointY(Location componentLocation, int endIndex) {
    if (endIndex == 0) {
      return componentLocation.getY();
    }
    // For inputs, approximate: index 1 → -20, index 2 → +20, etc.
    // This matches 2-input gate offsets for AND/OR (size=50)
    int inputIndex = endIndex - 1; // 0-based input index
    // Symmetric spread: -20, +20 for 2 inputs; -20, 0, +20 for 3 inputs, etc.
    int spread = 20;
    int offset;
    if (inputIndex == 0) {
      offset = -spread;
    } else if (inputIndex == 1) {
      offset = spread;
    } else {
      // For 3+ inputs, distribute evenly
      offset = (inputIndex - 1) * spread;
    }
    return snap(componentLocation.getY() + offset);
  }

  /**
   * Resolve y-coordinate conflict: if the target y is already occupied,
   * find the nearest available y that maintains MIN_PORT_SPACING.
   */
  static int resolveYConflict(int targetY, List<Integer> usedYs) {
    if (usedYs.isEmpty()) {
      return snap(targetY);
    }

    // Check if targetY conflicts with any used y
    for (int used : usedYs) {
      if (Math.abs(targetY - used) < MIN_PORT_SPACING) {
        // Find the nearest available y below or above
        // Try offsetting downward first (positive direction)
        int candidate = snap(used + MIN_PORT_SPACING);
        // Recursively check this candidate
        return resolveYConflict(candidate, usedYs);
      }
    }
    return snap(targetY);
  }

  private static int snap(int value) {
    return AutoLayoutPolicy.snap(value);
  }
}
