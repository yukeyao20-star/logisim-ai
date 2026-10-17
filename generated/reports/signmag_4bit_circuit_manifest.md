# Circuit artifact manifest for signmag_4bit

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=12890 |
| round_trip_loadable | true | [OK] signmag_4bit.circ round-trip loaded with main circuit signmag_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] signmag_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [OK] signmag_4bit.circ static layout checks passed |
| truth_table_evidence | true | [OK] exhaustive rows=16 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/signmag_4bit.circ | true |
| truth_table_markdown | generated/reports/signmag_4bit_circuit_truth_table.md | true |
| truth_table_json | generated/reports/signmag_4bit_circuit_truth_table.json | true |
