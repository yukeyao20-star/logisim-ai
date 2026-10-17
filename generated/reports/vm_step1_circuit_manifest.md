# Circuit artifact manifest for vending_machine

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=50496 |
| round_trip_loadable | true | [OK] vm_step1.circ round-trip loaded with main circuit vending_machine |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] vm_step1.circ structure matches CircuitSpec |
| layout_clean | true | [OK] vm_step1.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | temp/vm_step1.circ | true |
