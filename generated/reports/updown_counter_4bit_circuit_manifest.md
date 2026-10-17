# Circuit artifact manifest for updown_counter_4bit

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] updown_counter_4bit.circ
 - Component factory counts mismatch: expected {NOT Gate=4, T Flip-Flop=4, XOR Gate=1, AND Gate=8, OR Gate=2}, got {Clock=1, NOT Gate=4, XOR Gate=1, AND Gate=8, OR Gate=2, T Flip-Flop=4}
 - External pin labels mismatch: expected [CLK, EN, Q0, Q1, Q2, Q3, RST, UD], got [EN, Q0, Q1, Q2, Q3, RST, UD]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=18654 |
| round_trip_loadable | true | [OK] updown_counter_4bit.circ round-trip loaded with main circuit updown_counter_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] updown_counter_4bit.circ<br> - Component factory counts mismatch: expected {NOT Gate=4, T Flip-Flop=4, XOR Gate=1, AND Gate=8, OR Gate=2}, got {Clock=1, NOT Gate=4, XOR Gate=1, AND Gate=8, OR Gate=2, T Flip-Flop=4}<br> - External pin labels mismatch: expected [CLK, EN, Q0, Q1, Q2, Q3, RST, UD], got [EN, Q0, Q1, Q2, Q3, RST, UD] |
| layout_clean | true | [FAIL] updown_counter_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,80) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,-190) -> (210,250)<br> - Wire crosses reserved area of Pin Q0 in circuit updown_counter_4bit: (110,110) -> (4740,110)<br> - Wire crosses reserved area of Pin Q1 in circuit updown_counter_4bit: (1130,160) -> (4780,160)<br> - Wire crosses reserved area of Pin Q1 in circuit updown_counter_4bit: (1310,140) -> (4790,140)<br> - Wire crosses reserved area of Pin Q3 in circuit updown_counter_4bit: (3610,240) -> (5210,240)<br> - Wire crosses reserved area of Pin Q2 in circuit updown_counter_4bit: (3770,210) -> (5250,210)<br> - Wire crosses reserved area of Pin Q2 in circuit updown_counter_4bit: (470,190) -> (4750,190)<br> - Wire crosses reserved area of Pin Q0 in circuit updown_counter_4bit: (650,90) -> (4830,90) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\updown_counter_4bit.circ | true |
