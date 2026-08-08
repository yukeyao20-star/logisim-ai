package com.logisim_ai.layout;

import com.cburch.logisim.data.Location;
import java.util.ArrayList;
import java.util.List;

/**
 * Conservative visual footprint model for generated Logisim components.
 *
 * <p>This is a layout quality guard, not a replacement for Logisim's renderer.
 * It gives validators and future routers a shared notion of component bodies
 * and legal connection points, so wires can be checked against areas they
 * should not visually cross.
 */
public final class FootprintPolicy {
  private FootprintPolicy() {}

  private static final int TWO_INPUT_GATE_WIDTH = 72;
  private static final int TWO_INPUT_GATE_HEIGHT = 58;
  private static final int NOT_GATE_WIDTH = 42;
  private static final int NOT_GATE_HEIGHT = 32;

  public record ReservedArea(
      String kind,
      String label,
      int anchorX,
      int anchorY,
      int left,
      int top,
      int right,
      int bottom,
      List<Location> connectionPoints) {
    public boolean touchesAnchor(Location point) {
      return point.getX() == anchorX && point.getY() == anchorY;
    }

    public boolean touchesConnectionPoint(Location point) {
      for (Location connectionPoint : connectionPoints) {
        if (connectionPoint.equals(point)) {
          return true;
        }
      }
      return false;
    }

    public ReservedArea withConnectionPoint(Location point) {
      List<Location> updated = new ArrayList<>(connectionPoints);
      if (!updated.contains(point)) {
        updated.add(point);
      }
      return new ReservedArea(kind, label, anchorX, anchorY, left, top, right, bottom, List.copyOf(updated));
    }

    public boolean crossesInterior(AutoLayoutPolicy.WireSegment segment) {
      if (segment.isDiagonal()
          || touchesConnectionPoint(segment.from())
          || touchesConnectionPoint(segment.to())) {
        return false;
      }
      int x1 = segment.from().getX();
      int y1 = segment.from().getY();
      int x2 = segment.to().getX();
      int y2 = segment.to().getY();
      if (y1 == y2) {
        return y1 > top
            && y1 < bottom
            && overlapLength(Math.min(x1, x2), Math.max(x1, x2), left, right) > 0;
      }
      if (x1 == x2) {
        return x1 > left
            && x1 < right
            && overlapLength(Math.min(y1, y2), Math.max(y1, y2), top, bottom) > 0;
      }
      return false;
    }
  }

  public static ReservedArea reservedAreaForComponent(
      String componentName, String label, Location anchor) {
    if ("Pin".equals(componentName)) {
      return reservedArea(componentName, label, anchor, 28, 28)
          .withConnectionPoint(anchor);
    }
    if ("Tunnel".equals(componentName)) {
      int labelWidth = Math.max(24, 10 + Math.max(1, label.length()) * 8);
      return reservedArea(componentName, label, anchor, labelWidth, 24)
          .withConnectionPoint(anchor);
    }
    if ("NOT Gate".equals(componentName)) {
      return reservedArea(componentName, label, anchor, NOT_GATE_WIDTH, NOT_GATE_HEIGHT)
          .withConnectionPoint(anchor)
          .withConnectionPoint(Location.create(anchor.getX() - 30, anchor.getY(), true));
    }
    if (isTwoInputGate(componentName)) {
      return reservedArea(componentName, label, anchor, TWO_INPUT_GATE_WIDTH, TWO_INPUT_GATE_HEIGHT)
          .withConnectionPoint(anchor)
          .withConnectionPoint(Location.create(anchor.getX() - 60, anchor.getY() - 20, true))
          .withConnectionPoint(Location.create(anchor.getX() - 60, anchor.getY() + 20, true));
    }
    return null;
  }

  public static ReservedArea reservedAreaForSmallComponent(
      String componentName, String label, Location anchor) {
    return reservedAreaForComponent(componentName, label, anchor);
  }

  private static ReservedArea reservedArea(
      String kind, String label, Location anchor, int width, int height) {
    int halfW = width / 2;
    int halfH = height / 2;
    return new ReservedArea(
        kind,
        label == null ? "" : label,
        anchor.getX(),
        anchor.getY(),
        anchor.getX() - halfW,
        anchor.getY() - halfH,
        anchor.getX() + halfW,
        anchor.getY() + halfH,
        List.of());
  }

  private static int overlapLength(int a1, int a2, int b1, int b2) {
    return Math.max(0, Math.min(a2, b2) - Math.max(a1, b1));
  }

  private static boolean isTwoInputGate(String componentName) {
    return "AND Gate".equals(componentName)
        || "OR Gate".equals(componentName)
        || "XOR Gate".equals(componentName)
        || "NAND Gate".equals(componentName)
        || "NOR Gate".equals(componentName);
  }
}
