# Circuit artifact manifest for lfsr_4bit

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] lfsr_4bit.circ
 - Component factory counts mismatch: expected {D Flip-Flop=4, XOR Gate=1, OR Gate=1}, got {Clock=1, XOR Gate=1, OR Gate=1, D Flip-Flop=4}
 - External pin labels mismatch: expected [CLK, Q0, Q1, Q2, Q3, RST, SEED], got [Q0, Q1, Q2, Q3, RST, SEED]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=10079 |
| round_trip_loadable | true | [OK] lfsr_4bit.circ round-trip loaded with main circuit lfsr_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] lfsr_4bit.circ<br> - Component factory counts mismatch: expected {D Flip-Flop=4, XOR Gate=1, OR Gate=1}, got {Clock=1, XOR Gate=1, OR Gate=1, D Flip-Flop=4}<br> - External pin labels mismatch: expected [CLK, Q0, Q1, Q2, Q3, RST, SEED], got [Q0, Q1, Q2, Q3, RST, SEED] |
| layout_clean | true | [FAIL] lfsr_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,20) -> (160,200)<br> - Wire crosses reserved area of Pin Q2 in circuit lfsr_4bit: (1070,190) -> (2480,190)<br> - Wire crosses reserved area of Pin Q1 in circuit lfsr_4bit: (1540,160) -> (2520,160)<br> - Wire crosses reserved area of Pin Q1 in circuit lfsr_4bit: (1650,140) -> (2510,140)<br> - Wire crosses reserved area of Pin Q0 in circuit lfsr_4bit: (550,110) -> (2560,110)<br> - Wire crosses reserved area of Pin Q0 in circuit lfsr_4bit: (750,90) -> (2570,90) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\lfsr_4bit.circ | true |
