# Circuit artifact manifest for register_4bit

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] register_4bit.circ
 - Component factory counts mismatch: expected {D Flip-Flop=4}, got {Clock=1, D Flip-Flop=4}
 - External pin labels mismatch: expected [CLK, D0, D1, D2, D3, Q0, Q1, Q2, Q3, RST], got [D0, D1, D2, D3, Q0, Q1, Q2, Q3, RST]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=9204 |
| round_trip_loadable | true | [OK] register_4bit.circ round-trip loaded with main circuit register_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] register_4bit.circ<br> - Component factory counts mismatch: expected {D Flip-Flop=4}, got {Clock=1, D Flip-Flop=4}<br> - External pin labels mismatch: expected [CLK, D0, D1, D2, D3, Q0, Q1, Q2, Q3, RST], got [D0, D1, D2, D3, Q0, Q1, Q2, Q3, RST] |
| layout_clean | true | [FAIL] register_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,160) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,140) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,110) -> (260,300)<br> - Wire crosses reserved area of Pin Q0 in circuit register_4bit: (1010,90) -> (2310,90)<br> - Wire crosses reserved area of Pin Q1 in circuit register_4bit: (160,160) -> (2140,160)<br> - Wire crosses reserved area of Pin Q1 in circuit register_4bit: (210,140) -> (2220,140)<br> - Wire crosses reserved area of Pin Q0 in circuit register_4bit: (260,110) -> (2300,110)<br> - Wire crosses reserved area of Pin Q2 in circuit register_4bit: (490,210) -> (2150,210) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\register_4bit.circ | true |
