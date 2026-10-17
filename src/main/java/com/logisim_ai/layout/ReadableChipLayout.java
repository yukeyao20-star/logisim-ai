package com.logisim_ai.layout;

import com.cburch.logisim.data.Location;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared layout policy for deliverable top-level circuits.
 *
 * <p>The rule is intentionally conservative: user-facing pins stay far away from the
 * chip/subcircuit body, labels keep at least 40 px vertical spacing, and dense chip
 * internals should be hidden inside a native component or a subcircuit.
 */
public final class ReadableChipLayout {
  public static final int GRID = 10;
  public static final int MIN_PORT_SPACING = 50;
  public static final int SIDE_PIN_DISTANCE = 300;
  public static final int VERTICAL_PIN_DISTANCE = 170;

  private final Location chipLocation;

  public ReadableChipLayout(Location chipLocation) {
    this.chipLocation = chipLocation;
  }

  public Location chipLocation() {
    return chipLocation;
  }

  public List<PlacedPort> placePorts(List<ExternalPortSpec> specs) {
    List<PlacedPort> placed = new ArrayList<>();
    placeSide(specs, placed, PortSide.LEFT);
    placeSide(specs, placed, PortSide.RIGHT);
    placeSide(specs, placed, PortSide.TOP);
    placeSide(specs, placed, PortSide.BOTTOM);
    // Return in the caller's spec order: LogisimCompiler aligns this list
    // with spec.externalPorts() by index, and a side-grouped order scrambles
    // that pairing whenever one side hosts more than one port.
    List<PlacedPort> ordered = new ArrayList<>();
    for (ExternalPortSpec spec : specs) {
      boolean matched = false;
      for (PlacedPort p : placed) {
        if (!matched && p.label().equals(spec.label())) {
          ordered.add(p);
          matched = true;
        }
      }
      if (!matched) {
        throw new IllegalStateException("port not placed: " + spec.label());
      }
    }
    return List.copyOf(ordered);
  }

  private void placeSide(List<ExternalPortSpec> specs, List<PlacedPort> placed, PortSide side) {
    List<ExternalPortSpec> group = specs.stream().filter(p -> p.side() == side).toList();
    if (group.isEmpty()) {
      return;
    }

    int start = centeredStart(group.size());
    for (int i = 0; i < group.size(); i++) {
      ExternalPortSpec spec = group.get(i);
      int offset = start + i * MIN_PORT_SPACING;
      Location pin;
      Location anchor;
      com.cburch.logisim.data.Direction facing;
      switch (side) {
        case LEFT -> {
          pin = loc(chipLocation.getX() - SIDE_PIN_DISTANCE, chipLocation.getY() + offset);
          anchor = loc(chipLocation.getX() - 90, chipLocation.getY() + offset);
          facing = com.cburch.logisim.data.Direction.EAST;
        }
        case RIGHT -> {
          pin = loc(chipLocation.getX() + SIDE_PIN_DISTANCE, chipLocation.getY() + offset);
          anchor = loc(chipLocation.getX() + 90, chipLocation.getY() + offset);
          facing = com.cburch.logisim.data.Direction.WEST;
        }
        case TOP -> {
          pin = loc(chipLocation.getX() + offset, chipLocation.getY() - VERTICAL_PIN_DISTANCE);
          anchor = loc(chipLocation.getX() + offset, chipLocation.getY() - 80);
          facing = com.cburch.logisim.data.Direction.SOUTH;
        }
        case BOTTOM -> {
          pin = loc(chipLocation.getX() + offset, chipLocation.getY() + VERTICAL_PIN_DISTANCE);
          anchor = loc(chipLocation.getX() + offset, chipLocation.getY() + 80);
          facing = com.cburch.logisim.data.Direction.NORTH;
        }
        default -> throw new IllegalStateException("Unhandled side: " + side);
      }
      placed.add(new PlacedPort(spec.label(), spec.width(), side, facing, pin, anchor));
    }
  }

  private static int centeredStart(int count) {
    return -((count - 1) * MIN_PORT_SPACING) / 2;
  }

  private static Location loc(int x, int y) {
    return Location.create(snap(x), snap(y), true);
  }

  private static int snap(int value) {
    return Math.round(value / (float) GRID) * GRID;
  }
}
