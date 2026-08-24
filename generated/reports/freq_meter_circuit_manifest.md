# Circuit artifact manifest for freq_meter

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=28003 |
| round_trip_loadable | true | [OK] freq_meter.circ round-trip loaded with main circuit freq_meter |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] freq_meter.circ structure matches CircuitSpec |
| layout_clean | true | [OK] freq_meter.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\freq_meter.circ | true |
