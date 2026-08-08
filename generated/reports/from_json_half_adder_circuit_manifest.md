# Circuit artifact manifest for half_adder

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=4277 |
| round_trip_loadable | true | [OK] from_json_half_adder.circ round-trip loaded with main circuit half_adder |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] from_json_half_adder.circ structure matches CircuitSpec |
| layout_clean | true | [OK] from_json_half_adder.circ static layout checks passed |
| truth_table_evidence | true | [OK] exhaustive rows=4 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\from_json_half_adder.circ | true |
| truth_table_markdown | generated\reports\from_json_half_adder_circuit_truth_table.md | true |
| truth_table_json | generated\reports\from_json_half_adder_circuit_truth_table.json | true |
