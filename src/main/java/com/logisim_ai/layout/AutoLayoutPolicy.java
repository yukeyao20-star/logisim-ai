package com.logisim_ai.layout;

import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;

/**
 * Central policy for generated-circuit layout.
 *
 * <p>Natural-language and JSON generation should feed semantic IR into the
 * compiler. This policy owns the readable default spacing and tunnel lanes so
 * individual generators do not hand-place wires.
 */
public final class AutoLayoutPolicy {
  private AutoLayoutPolicy() {}

  public static final int GRID = 10;

  public static final int INPUT_PIN_X = 140;
  public static final int PIN_TO_COMPONENT_GAP = 240;
  public static final int COLUMN_SPACING = 320;
  public static final int ROW_SPACING = 170;
  public static final int MIN_PORT_SPACING = 80;
  public static final int VERTICAL_START = 120;

  private static final int COMPONENT_INPUT_TUNNEL_OFFSET = 80;
  private static final int COMPONENT_OUTPUT_TUNNEL_OFFSET = 90;
  private static final int EXTERNAL_PIN_TUNNEL_OFFSET = 90;

  public record TunnelPlacement(Location location, Direction facing) {}

  public static TunnelPlacement tunnelForExternalPin(Location endpoint, PortSide side) {
    return switch (side) {
      case LEFT -> new TunnelPlacement(
          Location.create(snap(endpoint.getX() - EXTERNAL_PIN_TUNNEL_OFFSET), snap(endpoint.getY()), true),
          Direction.EAST);
      case RIGHT -> new TunnelPlacement(
          Location.create(snap(endpoint.getX() + EXTERNAL_PIN_TUNNEL_OFFSET), snap(endpoint.getY()), true),
          Direction.WEST);
      case TOP -> new TunnelPlacement(
          Location.create(snap(endpoint.getX()), snap(endpoint.getY() - EXTERNAL_PIN_TUNNEL_OFFSET), true),
          Direction.SOUTH);
      case BOTTOM -> new TunnelPlacement(
          Location.create(snap(endpoint.getX()), snap(endpoint.getY() + EXTERNAL_PIN_TUNNEL_OFFSET), true),
          Direction.NORTH);
    };
  }

  public static TunnelPlacement tunnelForComponentEnd(Location endpoint, int endIndex) {
    if (endIndex == 0) {
      return new TunnelPlacement(
          Location.create(snap(endpoint.getX() + COMPONENT_OUTPUT_TUNNEL_OFFSET), snap(endpoint.getY()), true),
          Direction.WEST);
    }
    return new TunnelPlacement(
        Location.create(snap(endpoint.getX() - COMPONENT_INPUT_TUNNEL_OFFSET), snap(endpoint.getY()), true),
        Direction.EAST);
  }

  public static int snap(int value) {
    return Math.round(value / (float) GRID) * GRID;
  }
}
