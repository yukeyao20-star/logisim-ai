package com.logisim_ai.ai;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.LayoutHint;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.PortSide;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic Moore FSM synthesizer: state transition table → gate-level
 * {@link CircuitSpec}. This is the "fisherman's net" behind the per-circuit
 * templates: any natural-language request that resolves to a state machine
 * (vending machine, elevator, stepper motor, access control, ...) only needs
 * to supply its transition table, and this class performs state assignment,
 * next-state equations (D flip-flop inputs), and output equations
 * automatically.
 *
 * <p>Synthesis flow:
 * <ol>
 *   <li>States are numbered 0..S-1 and encoded in binary over
 *       ceil(log2 S) D flip-flops (the natural assignment; transition tables
 *       are authored so the reset state is 0).</li>
 *   <li>Every transition (fromState, inputVector, toState) becomes one
 *       product term: AND over the fromState literals (Q or NOT Q per bit)
 *       and the inputVector literals (I or NOT I per input). Each flip-flop's
 *       D input is the OR of the terms whose toState has that bit set. The
 *       tables must cover every (state, input vector) pair the environment
 *       can present — a missing pair leaves that D unconnected.</li>
 *   <li>Outputs (Moore) are OR over the fromState minterm chains of the
 *       states listed in each {@link Output}.</li>
 * </ol>
 *
 * <p>Component contract (verified against the Memory library):
 * D flip-flop end0=D, end1=CLK, end2=Q, end3=nQ, end4=reset (active high);
 * 2-input gates end0=out, end1/end2=in; NOT end0=out, end1=in. Shared CLK
 * and RST nets feed every flip-flop.
 *
 * <p>Fan-out discipline: a state literal drives many AND gates, so its net
 * lists one EndpointRef per sink (the idiom all existing templates use for
 * clock/reset distribution). Every AND/OR input endpoint is a distinct sink
 * keyed by (componentId, endIndex), satisfying the validator's multi-driver
 * rule — each sink appears on exactly one net.
 */
public final class FsmSynthesizer {
  private FsmSynthesizer() {}

  /** One transition row: from state, on input vector, to state. */
  public record Transition(int fromState, Map<String, Boolean> inputs, int toState) {}

  /** Moore output assignment: the output is 1 while the FSM sits in these states. */
  public record Output(String name, List<Integer> oneStates) {}

  /** FSM definition consumed by {@link #synthesize}. */
  public record Definition(
      String circuitName,
      List<String> inputNames,
      List<Output> outputs,
      List<Transition> transitions,
      int stateCount) {}

  /**
   * Synthesize a gate-level Moore FSM from a transition table.
   *
   * @param definition the FSM: transitions must cover every (state, input
   *     vector) pair the environment can present
   * @return the compiled CircuitSpec, ready for DirectMultiChipCompiler
   */
  public static CircuitSpec synthesize(Definition definition) {
    int states = definition.stateCount();
    if (states < 2 || states > 16) {
      throw new IllegalArgumentException("FSM state count must be 2..16, got " + states);
    }
    int bits = 1;
    while ((1 << bits) < states) {
      bits++;
    }
    for (Transition t : definition.transitions()) {
      if (t.fromState() < 0 || t.fromState() >= states
          || t.toState() < 0 || t.toState() >= states) {
        throw new IllegalArgumentException(
            "Transition references state outside 0.." + (states - 1) + ": " + t);
      }
      if (t.inputs().size() != definition.inputNames().size()) {
        throw new IllegalArgumentException(
            "Transition input vector size mismatch: " + t.inputs());
      }
    }
    // Reachability guard: every (state, input vector) pair the environment can
    // present must have a transition row. A missing row leaves that D input
    // with no term for the covered bits — the defect only surfaced at
    // compile/simulation time before this check.
    int inputVectors = 1 << definition.inputNames().size();
    java.util.Set<String> covered = new java.util.HashSet<>();
    for (Transition t : definition.transitions()) {
      int mask = 0;
      for (int j = 0; j < definition.inputNames().size(); j++) {
        Boolean v = t.inputs().get(definition.inputNames().get(j));
        if (v == null) {
          throw new IllegalArgumentException(
              "Transition missing input '" + definition.inputNames().get(j) + "': " + t);
        }
        if (v) {
          mask |= 1 << j;
        }
      }
      covered.add(t.fromState() + ":" + mask);
    }
    List<String> missing = new ArrayList<>();
    for (int s = 0; s < states; s++) {
      for (int v = 0; v < inputVectors; v++) {
        if (!covered.contains(s + ":" + v)) {
          missing.add("state " + s + " input " + java.util.Arrays.toString(
              vectorNames(v, definition.inputNames())));
        }
      }
    }
    if (!missing.isEmpty()) {
      throw new IllegalArgumentException(
          "FSM transition table incomplete: " + missing.size() + " missing (state, input) "
              + "rows: " + String.join("; ", missing.subList(0, Math.min(missing.size(), 5))));
    }

    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    // Short component ids: fq = flip-flop, fn = NOT, fa = AND, fo = OR.
    String fp = "fq";
    String np = "fn";
    String ap = "fa";
    String op = "fo";
    // Output ports are emitted in the Moore-output block below; each output
    // with >1 minterm gets its own final OR gate named op<seq>.

    // ---- Ports: CLK, RST, inputs, outputs ----
    addClockInput(ports, "clk", "CLK", fp + "0");
    addInput(ports, "rst", "RST", fp + "0");
    for (String in : definition.inputNames()) {
      addInput(ports, in, in.toUpperCase(), fp + "0");
    }
    // Output ports are emitted in the Moore-output block below (their target
    // is either the single minterm's component or the outOr OR gate).

    // ---- State flip-flops with shared CLK/RST ----
    for (int i = 0; i < bits; i++) {
      components.add(new ComponentSpec(fp + i, "Memory", "D Flip-Flop", 1, "U" + (i + 1)));
      hints.add(new LayoutHint(fp + i, i, 0, 0, null));
      nets.add(net("fsm_clk" + i, e("clk", 0), e(fp + i, 1)));
      nets.add(net("fsm_rst" + i, e("rst", 0), e(fp + i, 4)));
    }

    // ---- Literal pool: Q_i, NOT Q_i, I_j, NOT I_j ----
    Map<String, SignalSource> literal = new LinkedHashMap<>();
    for (int i = 0; i < bits; i++) {
      literal.put("q" + i, new SignalSource(fp + i, 2));
      String notId = np + i;
      components.add(new ComponentSpec(notId, "Gates", "NOT Gate", 1, "N" + (i + 1)));
      hints.add(new LayoutHint(notId, bits, 0, i, null));
      nets.add(net("fsm_nq" + i, e(fp + i, 2), e(notId, 1)));
      literal.put("nq" + i, new SignalSource(notId, 0));
    }
    for (String in : definition.inputNames()) {
      literal.put(in, new SignalSource(in, 0));
      String notId = np + "_" + in;
      components.add(new ComponentSpec(notId, "Gates", "NOT Gate", 1, "NI" + in));
      hints.add(new LayoutHint(notId, bits, 0, bits, null));
      nets.add(net("fsm_ni_" + in, e(in, 0), e(notId, 1)));
      literal.put("n" + in, new SignalSource(notId, 0));
    }

    // ---- Group transitions: fromState -> (inputVector -> toState) ----
    Map<Integer, Map<Map<String, Boolean>, Integer>> nextMap = new LinkedHashMap<>();
    for (Transition t : definition.transitions()) {
      Map<Map<String, Boolean>, Integer> perInput =
          nextMap.computeIfAbsent(t.fromState(), k -> new LinkedHashMap<>());
      Integer clash = perInput.put(t.inputs(), t.toState());
      if (clash != null && clash != t.toState()) {
        throw new IllegalArgumentException(
            "Conflicting transitions from state " + t.fromState() + " on " + t.inputs());
      }
    }

    int gateSeq = 0;

    // ---- Next-state terms: for each (from, inputs) build the product term,
    // then OR the terms of each flip-flop's D input. ----
    // termOuts keyed by toState bit -> list of driven sources for D_i.
    Map<Integer, List<SignalSource>> dTerms = new LinkedHashMap<>();
    // Moore output minterms: output name -> list of state-chain sources.
    Map<String, List<SignalSource>> stateMinterms = new LinkedHashMap<>();

    for (Map.Entry<Integer, Map<Map<String, Boolean>, Integer>> fromEntry
        : nextMap.entrySet()) {
      int fromState = fromEntry.getKey();
      List<SignalSource> stateLits = stateLiterals(fromState, bits, literal);

      // State minterm chain: AND over all state literals (for outputs).
      String stateChainId = null;
      if (stateLits.size() > 1) {
        stateChainId = "fm" + fromState;
        String acc = null;
        int accEnd = 0;
        for (int k = 0; k < stateLits.size(); k++) {
          if (acc == null) {
            acc = stateLits.get(0).componentId();
            accEnd = stateLits.get(0).endIndex();
            continue;
          }
          String gate = ap + gateSeq;
          components.add(new ComponentSpec(gate, "Gates", "AND Gate", 1, "M" + gateSeq));
          hints.add(new LayoutHint(gate, bits + 1, 0, k - 1, null));
          nets.add(net("fsm_ma" + gateSeq, new EndpointRef(acc, k == 1 ? accEnd : 0),
              e(gate, 1)));
          nets.add(net("fsm_mb" + gateSeq, stateLits.get(k).toRef(), e(gate, 2)));
          acc = gate;
          gateSeq++;
        }
        stateChainId = acc;
      }
      // Record the minterm driver for outputs covering this state.
      SignalSource minterm = stateChainId != null
          ? new SignalSource(stateChainId, 0)
          : stateLits.get(0);
      for (Output out : definition.outputs()) {
        if (out.oneStates().contains(fromState)) {
          stateMinterms.computeIfAbsent(out.name(), k -> new ArrayList<>()).add(minterm);
        }
      }

      for (Map.Entry<Map<String, Boolean>, Integer> toEntry
          : fromEntry.getValue().entrySet()) {
        int toState = toEntry.getValue();
        List<SignalSource> termInputs = new ArrayList<>(stateLits);
        for (Map.Entry<String, Boolean> ie : toEntry.getKey().entrySet()) {
          String key = Boolean.TRUE.equals(ie.getValue()) ? ie.getKey() : "n" + ie.getKey();
          termInputs.add(literal.get(key));
        }

        String termId;
        if (termInputs.size() == 1) {
          termId = termInputs.get(0).componentId();
        } else {
          String acc = termInputs.get(0).componentId();
          int accEnd = termInputs.get(0).endIndex();
          for (int k = 1; k < termInputs.size(); k++) {
            String gate = ap + gateSeq;
            components.add(new ComponentSpec(gate, "Gates", "AND Gate", 1, "A" + gateSeq));
            hints.add(new LayoutHint(gate, bits + 2, 0, gateSeq % 8, null));
            nets.add(net("fsm_ta" + gateSeq, new EndpointRef(acc, k == 1 ? accEnd : 0),
                e(gate, 1)));
            nets.add(net("fsm_tb" + gateSeq, termInputs.get(k).toRef(), e(gate, 2)));
            acc = gate;
            gateSeq++;
          }
          termId = acc;
        }
        dTerms.computeIfAbsent(toState, k -> new ArrayList<>())
            .add(new SignalSource(termId, 0));
      }
    }

    // ---- D inputs: OR over the terms of every target state with bit i set ----
    // Group per bit first: D_i drives one flip-flop, so all terms of all
    // states with bit i set share a single net (multi-driver otherwise).
    List<List<SignalSource>> perBit = new ArrayList<>();
    for (int i = 0; i < bits; i++) {
      perBit.add(new ArrayList<>());
    }
    for (Map.Entry<Integer, List<SignalSource>> entry : dTerms.entrySet()) {
      int toState = entry.getKey();
      for (int i = 0; i < bits; i++) {
        if (((toState >> i) & 1) == 1) {
          perBit.get(i).addAll(entry.getValue());
        }
      }
    }
    for (int i = 0; i < bits; i++) {
      List<SignalSource> terms = perBit.get(i);
      if (terms.isEmpty()) {
        continue;
      }
      String dNet = "fsm_d" + i;
      if (terms.size() == 1) {
        nets.add(net(dNet, terms.get(0).toRef(), e(fp + i, 0)));
      } else {
        String acc = terms.get(0).componentId();
        for (int k = 1; k < terms.size(); k++) {
          String gate = op + gateSeq;
          components.add(new ComponentSpec(gate, "Gates", "OR Gate", 1, "O" + gateSeq));
          hints.add(new LayoutHint(gate, bits + 3, 0, i * 4 + k, null));
          nets.add(net("fsm_oa" + gateSeq, new EndpointRef(acc, 0), e(gate, 1)));
          nets.add(net("fsm_ob" + gateSeq, terms.get(k).toRef(), e(gate, 2)));
          acc = gate;
          gateSeq++;
        }
        nets.add(net(dNet, new EndpointRef(acc, 0), e(fp + i, 0)));
      }
    }

    // ---- Moore outputs: OR over the state minterms ----
    for (Output out : definition.outputs()) {
      List<SignalSource> minterms = stateMinterms.get(out.name());
      if (minterms == null || minterms.isEmpty()) {
        throw new IllegalArgumentException(
            "Output '" + out.name() + "' has no states assigned");
      }
      String portId = "out_" + out.name();
      if (minterms.size() == 1) {
        // Direct drive: the port target is the minterm's own component; the
        // net runs driver-end → port endpoint (template idiom, e.g. tl_grn).
        addOutput(ports, portId, out.name().toUpperCase(), minterms.get(0).componentId());
        nets.add(net("fsm_out_" + out.name(), minterms.get(0).toRef(), e(portId, 0)));
        continue;
      }
      // OR-cascade: the final OR gate drives the port; intermediate ORs chain
      // below it.
      String outOrId = op + gateSeq;
      addOutput(ports, portId, out.name().toUpperCase(), outOrId);
      String acc = minterms.get(0).componentId();
      for (int k = 1; k < minterms.size(); k++) {
        boolean last = k == minterms.size() - 1;
        String gate = last ? outOrId : op + gateSeq;
        if (!last) {
          gateSeq++;
        }
        components.add(new ComponentSpec(gate, "Gates", "OR Gate", 1, "O" + gate));
        hints.add(new LayoutHint(gate, bits + 4, 0, k, null));
        nets.add(net("fsm_outa_" + out.name() + k, new EndpointRef(acc, 0), e(gate, 1)));
        nets.add(net("fsm_outb_" + out.name() + k, minterms.get(k).toRef(), e(gate, 2)));
        acc = gate;
      }
      nets.add(net("fsm_out_" + out.name(), new EndpointRef(outOrId, 0), e(portId, 0)));
      // Account for the final gate's id consumption.
      gateSeq++;
    }

    return new CircuitSpec(definition.circuitName(), components, ports, nets, hints);
  }

  private static List<SignalSource> stateLiterals(
      int state, int bits, Map<String, SignalSource> literal) {
    List<SignalSource> result = new ArrayList<>();
    for (int i = 0; i < bits; i++) {
      boolean one = ((state >> i) & 1) == 1;
      result.add(literal.get(one ? "q" + i : "nq" + i));
    }
    return result;
  }

  /** Input vector bitmask → readable names, e.g. mask=0b10 over [a,b] → [a=0, b=1] style. */
  private static String[] vectorNames(int mask, List<String> inputNames) {
    String[] names = new String[inputNames.size()];
    for (int j = 0; j < inputNames.size(); j++) {
      names[j] = inputNames.get(j) + "=" + ((mask >> j) & 1);
    }
    return names;
  }

  private static void addClockInput(
      List<ExternalPort> ports, String id, String label, String target) {
    ports.add(new ExternalPort(id, label, 1, PortDirection.INPUT, PortSide.LEFT, target, 0, true));
  }

  private static void addInput(
      List<ExternalPort> ports, String id, String label, String target) {
    ports.add(new ExternalPort(id, label, 1, PortDirection.INPUT, PortSide.LEFT, target, 0));
  }

  private static void addOutput(
      List<ExternalPort> ports, String id, String label, String target) {
    ports.add(new ExternalPort(id, label, 1, PortDirection.OUTPUT, PortSide.RIGHT, target, 0));
  }

  private static EndpointRef e(String componentId, int endIndex) {
    return new EndpointRef(componentId, endIndex);
  }

  private static NetSpec net(String id, EndpointRef... endpoints) {
    return new NetSpec(id, List.of(endpoints));
  }

  /** A driven signal: component id + the end index that carries the value. */
  private record SignalSource(String componentId, int endIndex) {
    EndpointRef toRef() {
      return new EndpointRef(componentId, endIndex);
    }
  }
}
