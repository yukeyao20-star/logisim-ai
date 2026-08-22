# Circuit artifact manifest for encoder_4to2

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=7601 |
| round_trip_loadable | true | [OK] encoder_4to2.circ round-trip loaded with main circuit encoder_4to2 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] encoder_4to2.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] encoder_4to2.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,20) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,90) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,190) -> (210,250)<br> - Wire crosses reserved area of Pin Y1 in circuit encoder_4to2: (160,90) -> (1890,90)<br> - Wire crosses reserved area of Pin G in circuit encoder_4to2: (210,190) -> (1840,190)<br> - Wire crosses reserved area of Pin Y0 in circuit encoder_4to2: (450,140) -> (1870,140)<br> - Wire crosses reserved area of Pin G in circuit encoder_4to2: (620,210) -> (1820,210)<br> - Wire crosses reserved area of Pin Y0 in circuit encoder_4to2: (830,160) -> (1880,160) |
| truth_table_evidence | true | [OK] exhaustive rows=16 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\encoder_4to2.circ | true |
| truth_table_markdown | generated\reports\encoder_4to2_circuit_truth_table.md | true |
| truth_table_json | generated\reports\encoder_4to2_circuit_truth_table.json | true |
