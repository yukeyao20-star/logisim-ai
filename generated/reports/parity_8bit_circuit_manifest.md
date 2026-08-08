# Circuit artifact manifest for parity_8bit

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=8683 |
| round_trip_loadable | true | [OK] parity_8bit.circ round-trip loaded with main circuit parity_8bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] parity_8bit.circ structure matches CircuitSpec |
| layout_clean | true | [OK] parity_8bit.circ static layout checks passed |
| truth_table_evidence | true | [OK] exhaustive rows=256 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\parity_8bit.circ | true |
| truth_table_markdown | generated\reports\parity_8bit_circuit_truth_table.md | true |
| truth_table_json | generated\reports\parity_8bit_circuit_truth_table.json | true |
