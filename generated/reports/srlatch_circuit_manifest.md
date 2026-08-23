# Circuit artifact manifest for srlatch

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: truth_table_evidence - IllegalStateException: Output was not resolved: q

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=4361 |
| round_trip_loadable | true | [OK] srlatch.circ round-trip loaded with main circuit srlatch |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] srlatch.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] srlatch.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)<br> - Wire crosses reserved area of Pin Q in circuit srlatch: (110,90) -> (1360,90)<br> - Wire crosses reserved area of Pin nQ in circuit srlatch: (560,160) -> (1370,160)<br> - Wire crosses reserved area of Pin nQ in circuit srlatch: (620,140) -> (1410,140) |
| truth_table_evidence | false | IllegalStateException: Output was not resolved: q |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\srlatch.circ | true |
