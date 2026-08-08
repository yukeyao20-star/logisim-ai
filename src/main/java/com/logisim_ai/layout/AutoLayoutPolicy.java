package com.logisim_ai.layout;

import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;
import java.util.ArrayList;
import java.util.List;

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

  public static final int INPUT_PIN_X = 80;
  public static final int PIN_TO_COMPONENT_GAP = 320;
  public static final int COLUMN_SPACING = 320;
  public static final int ROW_SPACING = 170;
  public static final int MIN_PORT_SPACING = 80;
  public static final int VERTICAL_START = 120;
  public static final int LEFT_BOUNDARY_WIRE_MIN_X = 80;
  public static final int LEFT_BOUNDARY_WIRE_MAX_X = 300;
  public static final int LEFT_BOUNDARY_VERTICAL_WIRE_MIN_LENGTH = 40;
  public static final int SAFE_DOGLEG_CLEARANCE = 80;
  public static final int RESERVED_AREA_DOGLEG_CLEARANCE = 20;

  private static final int COMPONENT_INPUT_TUNNEL_OFFSET = 20;
  private static final int COMPONENT_OUTPUT_TUNNEL_OFFSET = 90;
  private static final int EXTERNAL_PIN_TUNNEL_OFFSET = 180;

  public record TunnelPlacement(Location location, Direction facing) {}

  public record WireSegment(Location from, Location to) {
    public boolean isDiagonal() {
      return from.getX() != to.getX() && from.getY() != to.getY();
    }

    public boolean isRiskyBoundaryVertical() {
      return AutoLayoutPolicy.isRiskyBoundaryVertical(from, to);
    }
  }

  public static TunnelPlacement tunnelForExternalPin(Location endpoint, PortSide side) {
    return switch (side) {
      case LEFT -> new TunnelPlacement(
          Location.create(snap(endpoint.getX() + EXTERNAL_PIN_TUNNEL_OFFSET), snap(endpoint.getY()), true),
          Direction.WEST);
      case RIGHT -> new TunnelPlacement(
          Location.create(snap(endpoint.getX() - EXTERNAL_PIN_TUNNEL_OFFSET), snap(endpoint.getY()), true),
          Direction.EAST);
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

  /**
   * Plans short local wiring between an endpoint and its nearby Tunnel.
   *
   * <p>This is intentionally conservative: every returned segment is
   * orthogonal, and a would-be long vertical segment in the left boundary lane
   * is routed through a dogleg outside that lane. The compiler should use this
   * instead of hand-building local wires so all generators inherit the same
   * safety rule.
   */
  public static List<WireSegment> planLocalTunnelWire(Location from, Location to) {
    if (from.equals(to)) {
      return List.of();
    }

    List<WireSegment> direct = List.of(new WireSegment(from, to));
    if (from.getX() == to.getX() || from.getY() == to.getY()) {
      if (!direct.get(0).isRiskyBoundaryVertical()) {
        return direct;
      }
      int doglegX = safeDoglegX(from.getX());
      return compactSegments(List.of(
          new WireSegment(from, Location.create(doglegX, from.getY(), true)),
          new WireSegment(Location.create(doglegX, from.getY(), true), Location.create(doglegX, to.getY(), true)),
          new WireSegment(Location.create(doglegX, to.getY(), true), to)));
    }

    Location corner = Location.create(snap(from.getX()), snap(to.getY()), true);
    List<WireSegment> planned = compactSegments(List.of(
        new WireSegment(from, corner),
        new WireSegment(corner, to)));
    if (containsRiskyBoundaryVertical(planned)) {
      int doglegX = safeDoglegX(from.getX());
      planned = compactSegments(List.of(
          new WireSegment(from, Location.create(doglegX, from.getY(), true)),
          new WireSegment(Location.create(doglegX, from.getY(), true), Location.create(doglegX, to.getY(), true)),
          new WireSegment(Location.create(doglegX, to.getY(), true), to)));
    }
    return planned;
  }

  /**
   * Plans local wiring while avoiding known component visual footprints.
   *
   * <p>This is a conservative first router hook. It keeps the existing direct/L
   * behavior when safe, then tries simple doglegs outside reserved areas. More
   * complex global routing should build on this policy instead of bypassing it.
   */
  public static List<WireSegment> planLocalTunnelWireAvoidingAreas(
      Location from,
      Location to,
      List<FootprintPolicy.ReservedArea> reservedAreas) {
    List<WireSegment> baseline = planLocalTunnelWire(from, to);
    if (isSafeRoute(baseline, reservedAreas)) {
      return baseline;
    }

    List<Integer> doglegYs = doglegYCandidates(from, to, reservedAreas);
    for (int doglegY : doglegYs) {
      List<WireSegment> candidate = compactSegments(List.of(
          new WireSegment(from, Location.create(from.getX(), doglegY, true)),
          new WireSegment(Location.create(from.getX(), doglegY, true), Location.create(to.getX(), doglegY, true)),
          new WireSegment(Location.create(to.getX(), doglegY, true), to)));
      if (isSafeRoute(candidate, reservedAreas)) {
        return candidate;
      }
    }

    List<Integer> doglegXs = doglegXCandidates(from, to, reservedAreas);
    for (int doglegX : doglegXs) {
      List<WireSegment> candidate = compactSegments(List.of(
          new WireSegment(from, Location.create(doglegX, from.getY(), true)),
          new WireSegment(Location.create(doglegX, from.getY(), true), Location.create(doglegX, to.getY(), true)),
          new WireSegment(Location.create(doglegX, to.getY(), true), to)));
      if (isSafeRoute(candidate, reservedAreas)) {
        return candidate;
      }
    }

    return baseline;
  }

  public static boolean isRiskyBoundaryVertical(Location from, Location to) {
    if (from.getX() != to.getX()) {
      return false;
    }
    int x = from.getX();
    return x >= LEFT_BOUNDARY_WIRE_MIN_X
        && x <= LEFT_BOUNDARY_WIRE_MAX_X
        && Math.abs(from.getY() - to.getY()) >= LEFT_BOUNDARY_VERTICAL_WIRE_MIN_LENGTH;
  }

  public static int snap(int value) {
    return Math.round(value / (float) GRID) * GRID;
  }

  private static int safeDoglegX(int x) {
    if (x >= LEFT_BOUNDARY_WIRE_MIN_X && x <= LEFT_BOUNDARY_WIRE_MAX_X) {
      return snap(LEFT_BOUNDARY_WIRE_MAX_X + SAFE_DOGLEG_CLEARANCE);
    }
    return snap(x);
  }

  private static boolean containsRiskyBoundaryVertical(List<WireSegment> segments) {
    for (WireSegment segment : segments) {
      if (segment.isRiskyBoundaryVertical()) {
        return true;
      }
    }
    return false;
  }

  private static boolean isSafeRoute(
      List<WireSegment> segments,
      List<FootprintPolicy.ReservedArea> reservedAreas) {
    for (WireSegment segment : segments) {
      if (segment.isDiagonal() || segment.isRiskyBoundaryVertical()) {
        return false;
      }
      for (FootprintPolicy.ReservedArea area : reservedAreas) {
        if (area.crossesInterior(segment)) {
          return false;
        }
      }
    }
    return true;
  }

  private static List<Integer> doglegYCandidates(
      Location from,
      Location to,
      List<FootprintPolicy.ReservedArea> reservedAreas) {
    List<Integer> candidates = new ArrayList<>();
    int clearance = RESERVED_AREA_DOGLEG_CLEARANCE;
    candidates.add(snap(Math.min(from.getY(), to.getY()) - clearance));
    candidates.add(snap(Math.max(from.getY(), to.getY()) + clearance));
    for (FootprintPolicy.ReservedArea area : reservedAreas) {
      candidates.add(snap(area.top() - clearance));
      candidates.add(snap(area.bottom() + clearance));
    }
    return distinctCandidates(candidates);
  }

  private static List<Integer> doglegXCandidates(
      Location from,
      Location to,
      List<FootprintPolicy.ReservedArea> reservedAreas) {
    List<Integer> candidates = new ArrayList<>();
    int clearance = RESERVED_AREA_DOGLEG_CLEARANCE;
    candidates.add(safeDoglegX(snap(Math.min(from.getX(), to.getX()) - clearance)));
    candidates.add(safeDoglegX(snap(Math.max(from.getX(), to.getX()) + clearance)));
    for (FootprintPolicy.ReservedArea area : reservedAreas) {
      candidates.add(safeDoglegX(snap(area.left() - clearance)));
      candidates.add(safeDoglegX(snap(area.right() + clearance)));
    }
    return distinctCandidates(candidates);
  }

  private static List<Integer> distinctCandidates(List<Integer> values) {
    List<Integer> distinct = new ArrayList<>();
    for (int value : values) {
      if (!distinct.contains(value)) {
        distinct.add(value);
      }
    }
    return distinct;
  }

  private static List<WireSegment> compactSegments(List<WireSegment> segments) {
    List<WireSegment> compacted = new ArrayList<>();
    for (WireSegment segment : segments) {
      if (!segment.from().equals(segment.to())) {
        compacted.add(segment);
      }
    }
    return compacted;
  }
}
