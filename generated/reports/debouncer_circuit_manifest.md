# Circuit artifact manifest for debouncer

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=8790 |
| round_trip_loadable | true | [OK] debouncer.circ round-trip loaded with main circuit debouncer |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] debouncer.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] debouncer.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-10) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,90) -> (160,200)<br> - Wire crosses reserved area of Pin OUT in circuit debouncer: (160,90) -> (2600,90) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\debouncer.circ | true |
