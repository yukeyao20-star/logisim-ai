# Circuit artifact manifest for logic_y_abcdef

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=6500 |
| round_trip_loadable | true | [OK] logic_y_abcdef.circ round-trip loaded with main circuit logic_y_abcdef |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] logic_y_abcdef.circ structure matches CircuitSpec |
| layout_clean | true | [OK] logic_y_abcdef.circ static layout checks passed |
| truth_table_evidence | true | [OK] exhaustive rows=64 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/logic_y_abcdef.circ | true |
| truth_table_markdown | generated/reports/logic_y_abcdef_circuit_truth_table.md | true |
| truth_table_json | generated/reports/logic_y_abcdef_circuit_truth_table.json | true |
