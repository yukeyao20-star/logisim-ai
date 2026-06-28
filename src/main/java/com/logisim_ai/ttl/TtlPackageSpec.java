package com.logisim_ai.ttl;

import java.util.List;

/** Describes a TTL package made from repeated same-kind gates. */
public record TtlPackageSpec(
    String packageType,
    String gateFactoryName,
    String gateKindLabel,
    List<String> gateNames) {

  public TtlPackageSpec {
    if (packageType == null || packageType.isBlank()) {
      throw new IllegalArgumentException("packageType must not be blank");
    }
    if (gateFactoryName == null || gateFactoryName.isBlank()) {
      throw new IllegalArgumentException("gateFactoryName must not be blank");
    }
    if (gateKindLabel == null || gateKindLabel.isBlank()) {
      throw new IllegalArgumentException("gateKindLabel must not be blank");
    }
    if (gateNames == null || gateNames.isEmpty()) {
      throw new IllegalArgumentException("gateNames must not be empty");
    }
    gateNames = List.copyOf(gateNames);
  }

  public int gatesPerChip() {
    return gateNames.size();
  }

  public static TtlPackageSpec ttl7400() {
    return new TtlPackageSpec("7400", "NAND Gate", "NAND", List.of("A", "B", "C", "D"));
  }

  public static TtlPackageSpec ttl7402() {
    return new TtlPackageSpec("7402", "NOR Gate", "NOR", List.of("A", "B", "C", "D"));
  }

  public static TtlPackageSpec ttl7404() {
    return new TtlPackageSpec("7404", "NOT Gate", "NOT",
        List.of("A", "B", "C", "D", "E", "F"));
  }

  public static TtlPackageSpec ttl7408() {
    return new TtlPackageSpec("7408", "AND Gate", "AND", List.of("A", "B", "C", "D"));
  }

  public static TtlPackageSpec ttl7432() {
    return new TtlPackageSpec("7432", "OR Gate", "OR", List.of("A", "B", "C", "D"));
  }

  public static TtlPackageSpec ttl7486() {
    return new TtlPackageSpec("7486", "XOR Gate", "XOR", List.of("A", "B", "C", "D"));
  }
}
