package com.logisim_ai.layout;

import com.cburch.logisim.data.BitWidth;

/** Describes a user-visible external port before it is placed on the canvas. */
public record ExternalPortSpec(String label, BitWidth width, PortSide side) {
  public ExternalPortSpec {
    if (label == null || label.isBlank()) {
      throw new IllegalArgumentException("Port label must not be blank");
    }
    if (width == null) {
      throw new IllegalArgumentException("Port width must not be null");
    }
    if (side == null) {
      throw new IllegalArgumentException("Port side must not be null");
    }
  }
}
