# Circuit artifact manifest for decoder_3bit

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=21521 |
| round_trip_loadable | true | [OK] decoder_3bit.circ round-trip loaded with main circuit decoder_3bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] decoder_3bit.circ structure matches CircuitSpec |
| layout_clean | true | [OK] decoder_3bit.circ static layout checks passed |
| truth_table_evidence | true | [OK] exhaustive rows=8 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\decoder_3bit.circ | true |
| truth_table_markdown | generated\reports\decoder_3bit_circuit_truth_table.md | true |
| truth_table_json | generated\reports\decoder_3bit_circuit_truth_table.json | true |
