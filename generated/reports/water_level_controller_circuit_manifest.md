# Circuit artifact manifest for water_level_controller

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=35506 |
| round_trip_loadable | true | [OK] water_level_controller.circ round-trip loaded with main circuit water_level_controller |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] water_level_controller.circ structure matches CircuitSpec |
| layout_clean | true | [OK] water_level_controller.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/water_level_controller.circ | true |
