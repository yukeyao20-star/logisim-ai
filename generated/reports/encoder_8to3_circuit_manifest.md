# Circuit artifact manifest for encoder_8to3

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=23203 |
| round_trip_loadable | true | [OK] encoder_8to3.circ round-trip loaded with main circuit encoder_8to3 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] encoder_8to3.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] encoder_8to3.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-420) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-140) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,20) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,-100) -> (260,300)<br> - Wire crosses reserved area of Pin Y2 in circuit encoder_8to3: (1050,90) -> (4790,90)<br> - Wire crosses reserved area of Pin G in circuit encoder_8to3: (2790,260) -> (5110,260)<br> - Wire crosses reserved area of Pin G in circuit encoder_8to3: (2930,240) -> (5150,240)<br> - Wire crosses reserved area of Pin Y0 in circuit encoder_8to3: (500,210) -> (4700,210)<br> - Wire crosses reserved area of Pin Y0 in circuit encoder_8to3: (620,190) -> (4720,190)<br> - Wire crosses reserved area of Pin Y1 in circuit encoder_8to3: (690,160) -> (4730,160)<br> - Wire crosses reserved area of Pin Y1 in circuit encoder_8to3: (810,140) -> (4750,140)<br> - Wire crosses reserved area of Pin Y2 in circuit encoder_8to3: (980,110) -> (4780,110) |
| truth_table_evidence | true | [OK] exhaustive rows=256 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\encoder_8to3.circ | true |
| truth_table_markdown | generated\reports\encoder_8to3_circuit_truth_table.md | true |
| truth_table_json | generated\reports\encoder_8to3_circuit_truth_table.json | true |
