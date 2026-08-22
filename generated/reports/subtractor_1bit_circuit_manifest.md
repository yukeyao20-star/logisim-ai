# Circuit artifact manifest for subtractor_1bit

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=7722 |
| round_trip_loadable | true | [OK] subtractor_1bit.circ round-trip loaded with main circuit subtractor_1bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] subtractor_1bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] subtractor_1bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,70) -> (160,200)<br> - Wire crosses reserved area of Pin D0 in circuit subtractor_1bit: (600,110) -> (2300,110)<br> - Wire crosses reserved area of Pin D0 in circuit subtractor_1bit: (660,90) -> (2330,90)<br> - Wire crosses reserved area of Pin Bout in circuit subtractor_1bit: (930,140) -> (2270,140) |
| truth_table_evidence | true | [OK] exhaustive rows=8 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\subtractor_1bit.circ | true |
| truth_table_markdown | generated\reports\subtractor_1bit_circuit_truth_table.md | true |
| truth_table_json | generated\reports\subtractor_1bit_circuit_truth_table.json | true |
