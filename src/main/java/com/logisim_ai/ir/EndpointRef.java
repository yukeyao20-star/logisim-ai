package com.logisim_ai.ir;

/**
 * Reference to a specific endpoint on a component.
 *
 * <p>Used in {@link NetSpec} to declare which component ends are connected
 * together in a net.
 */
public record EndpointRef(
    String componentId,
    int endIndex) {
  public EndpointRef {
    if (componentId == null || componentId.isBlank()) {
      throw new IllegalArgumentException("componentId must not be blank");
    }
    if (endIndex < 0) {
      throw new IllegalArgumentException("endIndex must be >= 0");
    }
  }
}
