# Circuit artifact manifest for nine_complement

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=7446 |
| round_trip_loadable | true | [OK] nine_complement.circ round-trip loaded with main circuit nine_complement |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] nine_complement.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] nine_complement.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,70) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,90) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,20) -> (210,250)<br> - Wire crosses reserved area of Pin C1 in circuit nine_complement: (160,90) -> (1960,90)<br> - Wire crosses reserved area of Pin C4 in circuit nine_complement: (470,190) -> (1910,190) |
| truth_table_evidence | true | [OK] exhaustive rows=16 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\nine_complement.circ | true |
| truth_table_markdown | generated\reports\nine_complement_circuit_truth_table.md | true |
| truth_table_json | generated\reports\nine_complement_circuit_truth_table.json | true |
