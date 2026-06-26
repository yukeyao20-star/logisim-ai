package com.logisim_ai.ir;

/** A Logisim component requested by the intermediate representation. */
public record ComponentSpec(
    String id,
    String libraryName,
    String factoryName,
    int bitWidth,
    String label) {
  public ComponentSpec {
    if (id == null || id.isBlank()) {
      throw new IllegalArgumentException("Component id must not be blank");
    }
    if (libraryName == null || libraryName.isBlank()) {
      throw new IllegalArgumentException("Component library must not be blank");
    }
    if (factoryName == null || factoryName.isBlank()) {
      throw new IllegalArgumentException("Component factory must not be blank");
    }
    if (bitWidth < 1) {
      throw new IllegalArgumentException("Component bitWidth must be >= 1");
    }
  }
}
