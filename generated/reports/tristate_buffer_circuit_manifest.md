# Circuit artifact manifest for tristate_buffer

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=3475 |
| round_trip_loadable | true | [OK] tristate_buffer.circ round-trip loaded with main circuit tristate_buffer |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] tristate_buffer.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] tristate_buffer.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Wire crosses reserved area of Pin Y in circuit tristate_buffer: (110,110) -> (1360,110) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\tristate_buffer.circ | true |
