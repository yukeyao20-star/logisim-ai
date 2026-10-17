# Circuit artifact manifest for mux_4to1

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=12158 |
| round_trip_loadable | true | [OK] mux_4to1.circ round-trip loaded with main circuit mux_4to1 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] mux_4to1.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] mux_4to1.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-70) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-50) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,-40) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,220) -> (260,300)<br> - Wire crosses reserved area of Pin Y in circuit mux_4to1: (1300,110) -> (3540,110)<br> - Wire crosses reserved area of Pin Y in circuit mux_4to1: (1570,90) -> (3570,90) |
| truth_table_evidence | true | [OK] exhaustive rows=64 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mux_4to1.circ | true |
| truth_table_markdown | generated\reports\mux_4to1_circuit_truth_table.md | true |
| truth_table_json | generated\reports\mux_4to1_circuit_truth_table.json | true |
