# Circuit artifact manifest for rotate_shifter_4bit_left

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=38152 |
| round_trip_loadable | true | [OK] rotate_shifter_4bit_left.circ round-trip loaded with main circuit rotate_shifter_4bit_left |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] rotate_shifter_4bit_left.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] rotate_shifter_4bit_left.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-70) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,10) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,80) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,260) -> (260,300)<br> - Wire crosses reserved area of Pin Y3 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA3 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB4 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA5 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB6 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA7 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB8 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of OR Gate MO9 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of OR Gate MO10 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of NOT Gate NS1 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of NOT Gate NS2 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA1 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB2 in circuit rotate_shifter_4bit_left: (260,260) -> (9060,260)<br> - Wire crosses reserved area of Pin Y3 in circuit rotate_shifter_4bit_left: (2620,240) -> (9540,240)<br> - Wire crosses reserved area of Pin Y2 in circuit rotate_shifter_4bit_left: (2780,210) -> (9600,210)<br> - Wire crosses reserved area of Pin Y1 in circuit rotate_shifter_4bit_left: (3050,160) -> (9650,160)<br> - Wire crosses reserved area of Pin Y2 in circuit rotate_shifter_4bit_left: (470,190) -> (9070,190)<br> - Wire crosses reserved area of Pin Y0 in circuit rotate_shifter_4bit_left: (660,90) -> (9160,90)<br> - Wire crosses reserved area of Pin Y1 in circuit rotate_shifter_4bit_left: (770,140) -> (9110,140)<br> - Wire crosses reserved area of Pin Y0 in circuit rotate_shifter_4bit_left: (930,110) -> (9150,110) |
| truth_table_evidence | true | [OK] exhaustive rows=64 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\rotate_shifter_4bit_left.circ | true |
| truth_table_markdown | generated\reports\rotate_shifter_4bit_left_circuit_truth_table.md | true |
| truth_table_json | generated\reports\rotate_shifter_4bit_left_circuit_truth_table.json | true |
