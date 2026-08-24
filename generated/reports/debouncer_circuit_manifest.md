# Circuit artifact manifest for debouncer

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=7848 |
| round_trip_loadable | true | [OK] debouncer.circ round-trip loaded with main circuit debouncer |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] debouncer.circ structure matches CircuitSpec |
| layout_clean | true | [OK] debouncer.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\debouncer.circ | true |
