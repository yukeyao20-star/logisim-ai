# Circuit artifact manifest for bcd_counter

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=9728 |
| round_trip_loadable | true | [OK] bcd_counter.circ round-trip loaded with main circuit bcd_counter |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] bcd_counter.circ structure matches CircuitSpec |
| layout_clean | true | [OK] bcd_counter.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\bcd_counter.circ | true |
