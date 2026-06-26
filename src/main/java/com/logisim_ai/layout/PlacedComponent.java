package com.logisim_ai.layout;

import com.cburch.logisim.comp.Component;
import com.cburch.logisim.data.Location;

/**
 * A component that has been assigned a position by the layout engine.
 */
public record PlacedComponent(
    String id,
    Component component,
    Location location) {}
