/**
 * AI provider abstractions for natural-language circuit generation.
 *
 * <p>Currently contains a hardcoded template planner ({@link com.logisim_ai.ai.NaturalLanguageCircuitPlanner}).
 * When the engineering pipeline is stable, this will be replaced by an LLM-backed
 * planner that converts natural language to {@link com.logisim_ai.ir.CircuitSpec}.
 *
 * <p>No provider-specific dependency should leak into the compiler or validation packages.
 */
package com.logisim_ai.ai;
