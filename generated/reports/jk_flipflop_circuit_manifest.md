# Circuit artifact manifest for jk_flipflop

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=3992 |
| round_trip_loadable | true | [OK] jk_flipflop.circ round-trip loaded with main circuit jk_flipflop |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] jk_flipflop.circ structure matches CircuitSpec |
| layout_clean | true | [OK] jk_flipflop.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/jk_flipflop.circ | true |
