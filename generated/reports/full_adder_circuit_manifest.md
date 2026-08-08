# Circuit artifact manifest for full_adder

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=6224 |
| round_trip_loadable | true | [OK] full_adder.circ round-trip loaded with main circuit full_adder |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] full_adder.circ structure matches CircuitSpec |
| layout_clean | true | [OK] full_adder.circ static layout checks passed |
| truth_table_evidence | true | [OK] exhaustive rows=8 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\full_adder.circ | true |
| truth_table_markdown | generated\reports\full_adder_circuit_truth_table.md | true |
| truth_table_json | generated\reports\full_adder_circuit_truth_table.json | true |
