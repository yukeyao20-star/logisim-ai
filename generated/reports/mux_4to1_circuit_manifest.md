# Circuit artifact manifest for mux_4to1

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=10780 |
| round_trip_loadable | true | [OK] mux_4to1.circ round-trip loaded with main circuit mux_4to1 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] mux_4to1.circ structure matches CircuitSpec |
| layout_clean | true | [OK] mux_4to1.circ static layout checks passed |
| truth_table_evidence | true | [OK] exhaustive rows=64 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mux_4to1.circ | true |
| truth_table_markdown | generated\reports\mux_4to1_circuit_truth_table.md | true |
| truth_table_json | generated\reports\mux_4to1_circuit_truth_table.json | true |
