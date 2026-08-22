# Circuit artifact manifest for barrel_shifter_4bit

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=38358 |
| round_trip_loadable | true | [OK] barrel_shifter_4bit.circ round-trip loaded with main circuit barrel_shifter_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] barrel_shifter_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] barrel_shifter_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-200) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-390) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,-580) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,260) -> (260,300)<br> - Wire crosses reserved area of Pin Y3 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA3 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB4 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA5 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB6 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA7 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB8 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of OR Gate MO9 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of OR Gate MO10 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of NOT Gate NS1 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of NOT Gate NS2 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SA1 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of AND Gate SB2 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)<br> - Wire crosses reserved area of Pin Y2 in circuit barrel_shifter_4bit: (2730,210) -> (9570,210)<br> - Wire crosses reserved area of Pin Y1 in circuit barrel_shifter_4bit: (2890,160) -> (9610,160)<br> - Wire crosses reserved area of Pin Y3 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of AND Gate SA3 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of AND Gate SB4 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of AND Gate SA5 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of AND Gate SB6 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of AND Gate SA7 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of AND Gate SB8 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of OR Gate MO9 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of OR Gate MO10 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of NOT Gate NS1 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of NOT Gate NS2 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of AND Gate SA1 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of AND Gate SB2 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)<br> - Wire crosses reserved area of Pin Y2 in circuit barrel_shifter_4bit: (470,190) -> (9070,190)<br> - Wire crosses reserved area of Pin Y0 in circuit barrel_shifter_4bit: (660,90) -> (9160,90)<br> - Wire crosses reserved area of Pin Y1 in circuit barrel_shifter_4bit: (770,140) -> (9110,140)<br> - Wire crosses reserved area of Pin Y0 in circuit barrel_shifter_4bit: (930,110) -> (9150,110) |
| truth_table_evidence | true | [OK] exhaustive rows=128 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\barrel_shifter_4bit.circ | true |
| truth_table_markdown | generated\reports\barrel_shifter_4bit_circuit_truth_table.md | true |
| truth_table_json | generated\reports\barrel_shifter_4bit_circuit_truth_table.json | true |
