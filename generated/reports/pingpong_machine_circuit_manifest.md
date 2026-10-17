# Circuit artifact manifest for pingpong_machine

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=112874 |
| round_trip_loadable | true | [OK] pingpong_machine.circ round-trip loaded with main circuit pingpong_machine |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] pingpong_machine.circ structure matches CircuitSpec |
| layout_clean | true | [OK] pingpong_machine.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/pingpong_machine.circ | true |
