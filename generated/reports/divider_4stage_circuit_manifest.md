# Circuit artifact manifest for divider_4stage

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] divider_4stage.circ
 - Component factory counts mismatch: expected {T Flip-Flop=4, AND Gate=3}, got {Clock=1, AND Gate=3, T Flip-Flop=4}
 - External pin labels mismatch: expected [CLK, Q0, Q1, Q2, Q3, RST, T], got [Q0, Q1, Q2, Q3, RST, T]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=10808 |
| round_trip_loadable | true | [OK] divider_4stage.circ round-trip loaded with main circuit divider_4stage |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] divider_4stage.circ<br> - Component factory counts mismatch: expected {T Flip-Flop=4, AND Gate=3}, got {Clock=1, AND Gate=3, T Flip-Flop=4}<br> - External pin labels mismatch: expected [CLK, Q0, Q1, Q2, Q3, RST, T], got [Q0, Q1, Q2, Q3, RST, T] |
| layout_clean | true | [FAIL] divider_4stage.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,130) -> (160,200)<br> - Wire crosses reserved area of Pin Q0 in circuit divider_4stage: (110,110) -> (2620,110)<br> - Wire crosses reserved area of Pin Q0 in circuit divider_4stage: (1280,90) -> (2820,90)<br> - Wire crosses reserved area of Pin Q2 in circuit divider_4stage: (490,210) -> (2630,210)<br> - Wire crosses reserved area of Pin Q1 in circuit divider_4stage: (750,140) -> (2710,140)<br> - Wire crosses reserved area of Pin Q2 in circuit divider_4stage: (780,190) -> (2750,190) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\divider_4stage.circ | true |
