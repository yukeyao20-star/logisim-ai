# Circuit artifact manifest for ring_counter

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] ring_counter.circ
 - Component factory counts mismatch: expected {D Flip-Flop=4, OR Gate=1}, got {Clock=1, OR Gate=1, D Flip-Flop=4}
 - External pin labels mismatch: expected [CLK, Q0, Q1, Q2, Q3, RST, SEED], got [Q0, Q1, Q2, Q3, RST, SEED]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=9381 |
| round_trip_loadable | true | [OK] ring_counter.circ round-trip loaded with main circuit ring_counter |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] ring_counter.circ<br> - Component factory counts mismatch: expected {D Flip-Flop=4, OR Gate=1}, got {Clock=1, OR Gate=1, D Flip-Flop=4}<br> - External pin labels mismatch: expected [CLK, Q0, Q1, Q2, Q3, RST, SEED], got [Q0, Q1, Q2, Q3, RST, SEED] |
| layout_clean | true | [FAIL] ring_counter.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,40) -> (160,200)<br> - Wire crosses reserved area of Pin Q0 in circuit ring_counter: (1010,90) -> (2390,90)<br> - Wire crosses reserved area of Pin Q0 in circuit ring_counter: (110,110) -> (2300,110)<br> - Wire crosses reserved area of Pin Q2 in circuit ring_counter: (1330,190) -> (2320,190)<br> - Wire crosses reserved area of Pin Q1 in circuit ring_counter: (490,160) -> (2350,160)<br> - Wire crosses reserved area of Pin Q1 in circuit ring_counter: (550,140) -> (2360,140) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\ring_counter.circ | true |
