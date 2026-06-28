package com.logisim_ai.ttl;

import com.logisim_ai.ir.CircuitSpec;
import java.util.List;

/** Compatibility facade for 7400 NAND package reports. */
public final class Ttl7400Mapper {
  private static final TtlPackageSpec SPEC = TtlPackageSpec.ttl7400();

  private Ttl7400Mapper() {}

  public static List<GateAssignment> assignments(CircuitSpec spec) {
    return TtlPackageMapper.assignments(spec, SPEC).stream()
        .map(Ttl7400Mapper::assignmentFromGeneric)
        .toList();
  }

  public static void requireContiguousAssignments(CircuitSpec spec) {
    TtlPackageMapper.requireContiguousAssignments(spec, SPEC);
  }

  public static String toMarkdown(CircuitSpec spec) {
    return TtlPackageMapper.toMarkdown(spec, SPEC);
  }

  public static String toJson(CircuitSpec spec) {
    return TtlPackageMapper.toJson(spec, SPEC);
  }

  public static Bom bom(CircuitSpec spec) {
    return bomFromGeneric(TtlPackageMapper.bom(spec, SPEC));
  }

  public static String toBomMarkdown(CircuitSpec spec) {
    return TtlPackageMapper.toBomMarkdown(spec, SPEC);
  }

  public static String toBomJson(CircuitSpec spec) {
    return TtlPackageMapper.toBomJson(spec, SPEC);
  }

  private static GateAssignment assignmentFromGeneric(TtlPackageMapper.GateAssignment assignment) {
    return new GateAssignment(
        assignment.componentId(),
        assignment.label(),
        assignment.chipId(),
        assignment.gateName(),
        assignment.chipNumber(),
        assignment.gateIndex());
  }

  private static Bom bomFromGeneric(TtlPackageMapper.Bom bom) {
    return new Bom(
        bom.packageType(),
        bom.chipCount(),
        bom.totalGateCapacity(),
        bom.usedGateCount(),
        bom.unusedGateCount(),
        bom.unusedGateLabels());
  }

  public record GateAssignment(
      String componentId,
      String label,
      String chipId,
      String gateName,
      int chipNumber,
      int gateIndex) {}

  public record Bom(
      String packageType,
      int chipCount,
      int totalGateCapacity,
      int usedGateCount,
      int unusedGateCount,
      List<String> unusedGateLabels) {}
}
