# Circuit artifact manifest for johnson_4stage

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] johnson_4stage.circ
 - Component factory counts mismatch: expected {NOT Gate=1, D Flip-Flop=4}, got {Clock=1, NOT Gate=1, D Flip-Flop=4}
 - External pin labels mismatch: expected [CLK, Q0, Q1, Q2, Q3, RST], got [Q0, Q1, Q2, Q3, RST]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=8906 |
| round_trip_loadable | true | [OK] johnson_4stage.circ round-trip loaded with main circuit johnson_4stage |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] johnson_4stage.circ<br> - Component factory counts mismatch: expected {NOT Gate=1, D Flip-Flop=4}, got {Clock=1, NOT Gate=1, D Flip-Flop=4}<br> - External pin labels mismatch: expected [CLK, Q0, Q1, Q2, Q3, RST], got [Q0, Q1, Q2, Q3, RST] |
| layout_clean | true | [FAIL] johnson_4stage.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,60) -> (110,150)<br> - Wire crosses reserved area of Pin Q0 in circuit johnson_4stage: (1170,90) -> (2490,90)<br> - Wire crosses reserved area of Pin Q2 in circuit johnson_4stage: (470,190) -> (2310,190)<br> - Wire crosses reserved area of Pin Q2 in circuit johnson_4stage: (680,210) -> (2370,210)<br> - Wire crosses reserved area of Pin Q1 in circuit johnson_4stage: (710,140) -> (2380,140)<br> - Wire crosses reserved area of Pin Q1 in circuit johnson_4stage: (940,160) -> (2450,160)<br> - Wire crosses reserved area of Pin Q0 in circuit johnson_4stage: (970,110) -> (2460,110) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\johnson_4stage.circ | true |
