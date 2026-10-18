# Circuit artifact manifest for elevator_controller

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=35444 |
| round_trip_loadable | true | [OK] elevator_controller.circ round-trip loaded with main circuit elevator_controller |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] elevator_controller.circ structure matches CircuitSpec |
| layout_clean | true | [OK] elevator_controller.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/elevator_controller.circ | true |
