# Circuit artifact manifest for stepper_motor

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=27256 |
| round_trip_loadable | true | [OK] stepper_motor.circ round-trip loaded with main circuit stepper_motor |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] stepper_motor.circ structure matches CircuitSpec |
| layout_clean | true | [OK] stepper_motor.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/stepper_motor.circ | true |
