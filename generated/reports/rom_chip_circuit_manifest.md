# Circuit artifact manifest for rom_chip

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=2714 |
| round_trip_loadable | true | [OK] rom_chip.circ round-trip loaded with main circuit rom_chip |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] rom_chip.circ structure matches CircuitSpec |
| layout_clean | true | [OK] rom_chip.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/rom_chip.circ | true |
