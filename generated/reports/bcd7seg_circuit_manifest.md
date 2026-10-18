# Circuit artifact manifest for bcd7seg

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=91757 |
| round_trip_loadable | true | [OK] bcd7seg.circ round-trip loaded with main circuit bcd7seg |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] bcd7seg.circ structure matches CircuitSpec |
| layout_clean | true | [OK] bcd7seg.circ static layout checks passed |
| truth_table_evidence | true | [OK] exhaustive rows=16 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/bcd7seg.circ | true |
| truth_table_markdown | generated/reports/bcd7seg_circuit_truth_table.md | true |
| truth_table_json | generated/reports/bcd7seg_circuit_truth_table.json | true |
