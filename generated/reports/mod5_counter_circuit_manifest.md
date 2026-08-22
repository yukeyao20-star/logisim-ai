# Circuit artifact manifest for mod5_counter

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] mod5_counter.circ
 - Component factory counts mismatch: expected {T Flip-Flop=4, NOT Gate=4, AND Gate=8, OR Gate=1}, got {Clock=1, NOT Gate=4, AND Gate=8, OR Gate=1, T Flip-Flop=4}
 - External pin labels mismatch: expected [CLK, Q0, Q1, Q2, Q3, RST], got [Q0, Q1, Q2, Q3, RST]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=16799 |
| round_trip_loadable | true | [OK] mod5_counter.circ round-trip loaded with main circuit mod5_counter |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] mod5_counter.circ<br> - Component factory counts mismatch: expected {T Flip-Flop=4, NOT Gate=4, AND Gate=8, OR Gate=1}, got {Clock=1, NOT Gate=4, AND Gate=8, OR Gate=1, T Flip-Flop=4}<br> - External pin labels mismatch: expected [CLK, Q0, Q1, Q2, Q3, RST], got [Q0, Q1, Q2, Q3, RST] |
| layout_clean | true | [FAIL] mod5_counter.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-240) -> (110,150)<br> - Wire crosses reserved area of Pin Q0 in circuit mod5_counter: (1070,90) -> (4520,90)<br> - Wire crosses reserved area of Pin Q2 in circuit mod5_counter: (1510,190) -> (4430,190)<br> - Wire crosses reserved area of Pin Q1 in circuit mod5_counter: (1540,160) -> (4460,160)<br> - Wire crosses reserved area of Pin Q1 in circuit mod5_counter: (1810,140) -> (4470,140)<br> - Wire crosses reserved area of Pin Q0 in circuit mod5_counter: (1970,110) -> (4510,110)<br> - Wire crosses reserved area of Pin Q3 in circuit mod5_counter: (3500,240) -> (4880,240)<br> - Wire crosses reserved area of Pin Q2 in circuit mod5_counter: (3610,210) -> (4890,210) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mod5_counter.circ | true |
