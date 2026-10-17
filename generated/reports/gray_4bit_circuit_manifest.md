# Circuit artifact manifest for gray_4bit

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=6589 |
| round_trip_loadable | true | [OK] gray_4bit.circ round-trip loaded with main circuit gray_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] gray_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] gray_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,80) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,110) -> (210,250)<br> - Wire crosses reserved area of Pin G0 in circuit gray_4bit: (110,90) -> (1600,90)<br> - Wire crosses reserved area of Pin G0 in circuit gray_4bit: (210,110) -> (1720,110)<br> - Wire crosses reserved area of Pin G1 in circuit gray_4bit: (600,140) -> (1650,140) |
| truth_table_evidence | true | [OK] exhaustive rows=16 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\gray_4bit.circ | true |
| truth_table_markdown | generated\reports\gray_4bit_circuit_truth_table.md | true |
| truth_table_json | generated\reports\gray_4bit_circuit_truth_table.json | true |
