# Circuit artifact manifest for shift_right_4bit

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=38342 |
| round_trip_loadable | true | [OK] shift_right_4bit.circ round-trip loaded with main circuit shift_right_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] shift_right_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] shift_right_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,10) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,0) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,30) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,260) -> (260,300)<br> - Wire crosses reserved area of Pin Y3 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA3 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB4 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA5 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB6 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA7 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB8 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of OR Gate MO9 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of OR Gate MO10 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of NOT Gate NS1 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of NOT Gate NS2 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA1 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB2 in circuit shift_right_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of Pin Y3 in circuit shift_right_4bit: (2620,240) -> (9540,240)<br> - Wire crosses reserved area of Pin Y2 in circuit shift_right_4bit: (2780,210) -> (9600,210)<br> - Wire crosses reserved area of Pin Y1 in circuit shift_right_4bit: (3050,160) -> (9650,160)<br> - Wire crosses reserved area of Pin Y2 in circuit shift_right_4bit: (470,190) -> (9070,190)<br> - Wire crosses reserved area of Pin Y0 in circuit shift_right_4bit: (660,90) -> (9160,90)<br> - Wire crosses reserved area of Pin Y1 in circuit shift_right_4bit: (770,140) -> (9110,140)<br> - Wire crosses reserved area of Pin Y0 in circuit shift_right_4bit: (930,110) -> (9150,110) |
| truth_table_evidence | true | [OK] exhaustive rows=128 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\shift_right_4bit.circ | true |
| truth_table_markdown | generated\reports\shift_right_4bit_circuit_truth_table.md | true |
| truth_table_json | generated\reports\shift_right_4bit_circuit_truth_table.json | true |
