# Circuit artifact manifest for dlatch

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: truth_table_evidence - IllegalStateException: Output was not resolved: q

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=6371 |
| round_trip_loadable | true | [OK] dlatch.circ round-trip loaded with main circuit dlatch |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] dlatch.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] dlatch.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)<br> - Wire crosses reserved area of Pin Q in circuit dlatch: (110,90) -> (1940,90)<br> - Wire crosses reserved area of Pin nQ in circuit dlatch: (600,160) -> (1950,160)<br> - Wire crosses reserved area of Pin nQ in circuit dlatch: (760,140) -> (1990,140)<br> - Wire crosses reserved area of Pin Q in circuit dlatch: (820,110) -> (2020,110) |
| truth_table_evidence | false | IllegalStateException: Output was not resolved: q |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\dlatch.circ | true |
