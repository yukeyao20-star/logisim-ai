package com.logisim_ai.ir;

import com.logisim_ai.layout.PortSide;

/** A visible input/output pin connected to a component endpoint. */
public record ExternalPort(
    String id,
    String label,
    int bitWidth,
    PortDirection direction,
    PortSide side,
    String targetComponentId,
    int targetEndIndex) {
  public ExternalPort {
    if (id == null || id.isBlank()) {
      throw new IllegalArgumentException("Port id must not be blank");
    }
    if (label == null || label.isBlank()) {
      throw new IllegalArgumentException("Port label must not be blank");
    }
    if (bitWidth < 1) {
      throw new IllegalArgumentException("Port bitWidth must be >= 1");
    }
    if (direction == null || side == null) {
      throw new IllegalArgumentException("Port direction and side must not be null");
    }
    if (targetComponentId == null || targetComponentId.isBlank()) {
      throw new IllegalArgumentException("Port targetComponentId must not be blank");
    }
    if (targetEndIndex < 0) {
      throw new IllegalArgumentException("Port targetEndIndex must be >= 0");
    }
  }
}
