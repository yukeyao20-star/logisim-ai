# Circuit artifact manifest for nand_xor3

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=7529 |
| round_trip_loadable | true | [OK] nand_xor3.circ round-trip loaded with main circuit nand_xor3 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] nand_xor3.circ structure matches CircuitSpec |
| layout_clean | true | [OK] nand_xor3.circ static layout checks passed |
| truth_table_evidence | true | [OK] exhaustive rows=8 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\nand_xor3.circ | true |
| truth_table_markdown | generated\reports\nand_xor3_circuit_truth_table.md | true |
| truth_table_json | generated\reports\nand_xor3_circuit_truth_table.json | true |
