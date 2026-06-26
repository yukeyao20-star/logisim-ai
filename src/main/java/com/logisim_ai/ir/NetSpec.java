package com.logisim_ai.ir;

import java.util.List;

/**
 * A net (wire) connecting multiple component endpoints together.
 *
 * <p>Each net represents a single logical signal. At least 2 endpoints must
 * be connected: one driving the signal and one or more receiving it.
 */
public record NetSpec(
    String id,
    List<EndpointRef> endpoints) {
  public NetSpec {
    if (id == null || id.isBlank()) {
      throw new IllegalArgumentException("Net id must not be blank");
    }
    endpoints = List.copyOf(endpoints);
    if (endpoints.size() < 2) {
      throw new IllegalArgumentException(
          "Net must have at least 2 endpoints: " + id);
    }
  }
}
