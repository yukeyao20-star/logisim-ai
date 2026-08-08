package com.logisim_ai.ai;

import com.logisim_ai.ai.NaturalLanguageCircuitPlanner.ImplementationIntent;
import com.logisim_ai.ai.NaturalLanguageCircuitPlanner.ImplementationLevel;
import com.logisim_ai.ai.NaturalLanguageCircuitPlanner.ImplementationProfile;

/**
 * Machine-readable planning contract shared by natural-language, structured
 * JSON, and future missing-chip generation paths.
 */
public record GenerationRequest(
    RequestKind requestKind,
    String originalRequest,
    String normalizedRequest,
    RequestInputKind inputKind,
    DesiredArtifactView desiredArtifactView,
    ImplementationIntent intent,
    ImplementationProfile implementationProfile) {

  public GenerationRequest {
    if (requestKind == null) {
      throw new IllegalArgumentException("requestKind must not be null");
    }
    originalRequest = originalRequest == null ? "" : originalRequest;
    normalizedRequest = normalizedRequest == null ? "" : normalizedRequest;
    if (inputKind == null) {
      throw new IllegalArgumentException("inputKind must not be null");
    }
    if (desiredArtifactView == null) {
      throw new IllegalArgumentException("desiredArtifactView must not be null");
    }
    if (intent == null) {
      throw new IllegalArgumentException("intent must not be null");
    }
    if (implementationProfile == null) {
      throw new IllegalArgumentException("implementationProfile must not be null");
    }
  }

  public enum RequestKind {
    NATURAL_LANGUAGE,
    STRUCTURED_JSON,
    MISSING_CHIP
  }

  public enum RequestInputKind {
    VERIFIED_TEMPLATE,
    BOOLEAN_EXPRESSION,
    STRUCTURED_CIRCUIT_SPEC,
    CHIP_TARGET
  }

  public enum DesiredArtifactView {
    EDITABLE_CIRCUIT,
    PACKAGE_MAPPED_CIRCUIT,
    CHIP_INTERNAL_CIRCUIT,
    TTL_DIP_SUBCIRCUIT
  }

  public static GenerationRequest naturalLanguage(
      String originalRequest,
      String normalizedRequest,
      RequestInputKind inputKind,
      ImplementationIntent intent,
      ImplementationProfile implementationProfile) {
    return new GenerationRequest(
        RequestKind.NATURAL_LANGUAGE,
        originalRequest,
        normalizedRequest,
        inputKind,
        desiredArtifactViewFor(implementationProfile),
        intent,
        implementationProfile);
  }

  public static GenerationRequest legacyNaturalLanguage(
      ImplementationIntent intent,
      ImplementationProfile implementationProfile) {
    return naturalLanguage("", "", RequestInputKind.BOOLEAN_EXPRESSION, intent, implementationProfile);
  }

  public static GenerationRequest structuredJson(String sourcePath) {
    ImplementationProfile profile = new ImplementationProfile(
        ImplementationLevel.GATE_LEVEL,
        "",
        false,
        "structured_circuit_spec");
    return new GenerationRequest(
        RequestKind.STRUCTURED_JSON,
        sourcePath,
        normalizePath(sourcePath),
        RequestInputKind.STRUCTURED_CIRCUIT_SPEC,
        DesiredArtifactView.EDITABLE_CIRCUIT,
        ImplementationIntent.GATE_LEVEL_CIRCUIT,
        profile);
  }

  private static DesiredArtifactView desiredArtifactViewFor(
      ImplementationProfile implementationProfile) {
    return switch (implementationProfile.level()) {
      case GATE_LEVEL -> DesiredArtifactView.EDITABLE_CIRCUIT;
      case CHIP_LEVEL -> DesiredArtifactView.PACKAGE_MAPPED_CIRCUIT;
      case GATE_LEVEL_CHIP -> DesiredArtifactView.CHIP_INTERNAL_CIRCUIT;
    };
  }

  private static String normalizePath(String sourcePath) {
    return sourcePath == null ? "" : sourcePath.replace('\\', '/').trim();
  }
}
