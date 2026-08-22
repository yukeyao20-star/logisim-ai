# Circuit artifact manifest for shiftreg_4bit

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] shiftreg_4bit.circ
 - Component factory counts mismatch: expected {D Flip-Flop=4}, got {Clock=1, D Flip-Flop=4}
 - External pin labels mismatch: expected [CLK, DIN, Q0, Q1, Q2, Q3, RST], got [DIN, Q0, Q1, Q2, Q3, RST]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=8665 |
| round_trip_loadable | true | [OK] shiftreg_4bit.circ round-trip loaded with main circuit shiftreg_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] shiftreg_4bit.circ<br> - Component factory counts mismatch: expected {D Flip-Flop=4}, got {Clock=1, D Flip-Flop=4}<br> - External pin labels mismatch: expected [CLK, DIN, Q0, Q1, Q2, Q3, RST], got [DIN, Q0, Q1, Q2, Q3, RST] |
| layout_clean | true | [FAIL] shiftreg_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,130) -> (160,200)<br> - Wire crosses reserved area of Pin Q0 in circuit shiftreg_4bit: (1010,90) -> (2310,90)<br> - Wire crosses reserved area of Pin Q0 in circuit shiftreg_4bit: (110,110) -> (2140,110)<br> - Wire crosses reserved area of Pin Q2 in circuit shiftreg_4bit: (490,210) -> (2150,210)<br> - Wire crosses reserved area of Pin Q1 in circuit shiftreg_4bit: (750,140) -> (2230,140)<br> - Wire crosses reserved area of Pin Q2 in circuit shiftreg_4bit: (780,190) -> (2270,190) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\shiftreg_4bit.circ | true |
