# Circuit artifact manifest for barrel_shifter_2bit

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=8186 |
| round_trip_loadable | true | [OK] barrel_shifter_2bit.circ round-trip loaded with main circuit barrel_shifter_2bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] barrel_shifter_2bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] barrel_shifter_2bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-10) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,160) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,130) -> (210,250)<br> - Wire crosses reserved area of Pin Y1 in circuit barrel_shifter_2bit: (160,160) -> (2220,160)<br> - Wire crosses reserved area of Pin Y1 in circuit barrel_shifter_2bit: (610,140) -> (2250,140)<br> - Wire crosses reserved area of Pin Y0 in circuit barrel_shifter_2bit: (660,110) -> (2320,110)<br> - Wire crosses reserved area of Pin Y0 in circuit barrel_shifter_2bit: (930,90) -> (2330,90) |
| truth_table_evidence | true | [OK] exhaustive rows=16 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\barrel_shifter_2bit.circ | true |
| truth_table_markdown | generated\reports\barrel_shifter_2bit_circuit_truth_table.md | true |
| truth_table_json | generated\reports\barrel_shifter_2bit_circuit_truth_table.json | true |
