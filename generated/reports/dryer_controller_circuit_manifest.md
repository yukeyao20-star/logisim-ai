# Circuit artifact manifest for dryer_controller

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=35794 |
| round_trip_loadable | true | [OK] dryer_controller.circ round-trip loaded with main circuit dryer_controller |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] dryer_controller.circ structure matches CircuitSpec |
| layout_clean | true | [OK] dryer_controller.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/dryer_controller.circ | true |
