# Circuit artifact manifest for ram_chip

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=4048 |
| round_trip_loadable | true | [OK] ram_chip.circ round-trip loaded with main circuit ram_chip |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] ram_chip.circ structure matches CircuitSpec |
| layout_clean | true | [OK] ram_chip.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/ram_chip.circ | true |
