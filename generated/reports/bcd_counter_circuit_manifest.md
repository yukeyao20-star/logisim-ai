# Circuit artifact manifest for bcd_counter

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] bcd_counter.circ
 - Component factory counts mismatch: expected {T Flip-Flop=4, NOT Gate=1, AND Gate=5, OR Gate=1}, got {Clock=1, NOT Gate=1, AND Gate=5, OR Gate=1, T Flip-Flop=4}
 - External pin labels mismatch: expected [CLK, EN, Q0, Q1, Q2, Q3, RST], got [EN, Q0, Q1, Q2, Q3, RST]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=13388 |
| round_trip_loadable | true | [OK] bcd_counter.circ round-trip loaded with main circuit bcd_counter |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] bcd_counter.circ<br> - Component factory counts mismatch: expected {T Flip-Flop=4, NOT Gate=1, AND Gate=5, OR Gate=1}, got {Clock=1, NOT Gate=1, AND Gate=5, OR Gate=1, T Flip-Flop=4}<br> - External pin labels mismatch: expected [CLK, EN, Q0, Q1, Q2, Q3, RST], got [EN, Q0, Q1, Q2, Q3, RST] |
| layout_clean | true | [FAIL] bcd_counter.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-80) -> (160,200)<br> - Wire crosses reserved area of Pin Q0 in circuit bcd_counter: (110,110) -> (3260,110)<br> - Wire crosses reserved area of Pin Q2 in circuit bcd_counter: (1330,190) -> (3280,190)<br> - Wire crosses reserved area of Pin Q1 in circuit bcd_counter: (1650,140) -> (3310,140)<br> - Wire crosses reserved area of Pin Q2 in circuit bcd_counter: (490,210) -> (3270,210)<br> - Wire crosses reserved area of Pin Q0 in circuit bcd_counter: (750,90) -> (3350,90) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\bcd_counter.circ | true |
