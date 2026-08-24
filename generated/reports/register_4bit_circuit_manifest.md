# Circuit artifact manifest for register_4bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=6706 |
| round_trip_loadable | true | [OK] register_4bit.circ round-trip loaded with main circuit register_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] register_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [OK] register_4bit.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\register_4bit.circ | true |
