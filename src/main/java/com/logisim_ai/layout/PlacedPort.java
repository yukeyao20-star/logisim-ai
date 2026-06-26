package com.logisim_ai.layout;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Location;

/** Final canvas placement for an external pin. */
public record PlacedPort(
    String label,
    BitWidth width,
    PortSide side,
    Direction pinFacing,
    Location pinLocation,
    Location routeAnchor) {}
