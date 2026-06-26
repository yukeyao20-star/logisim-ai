package com.logisim_ai.ir;

import com.logisim_ai.layout.PortSide;

/**
 * A layout hint for a component or port placement.
 *
 * <p>First version supports only rank (topological depth), column, row,
 * and side. This keeps the hint system minimal while giving the layout
 * engine enough information to produce readable schematics.
 */
public record LayoutHint(
    String componentId,
    int rank,
    int column,
    int row,
    PortSide side) {
  public LayoutHint {
    if (componentId == null || componentId.isBlank()) {
      throw new IllegalArgumentException("componentId must not be blank");
    }
    if (rank < 0) {
      throw new IllegalArgumentException("rank must be >= 0");
    }
  }
}
