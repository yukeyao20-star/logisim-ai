# Circuit artifact manifest for stopwatch

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=27724 |
| round_trip_loadable | true | [OK] stopwatch.circ round-trip loaded with main circuit stopwatch |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] stopwatch.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] stopwatch.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-360) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-10) -> (160,200)<br> - Wire crosses reserved area of Pin Q6 in circuit stopwatch: (1130,410) -> (6510,410)<br> - Wire crosses reserved area of Pin Q0 in circuit stopwatch: (2680,110) -> (6400,110)<br> - Wire crosses reserved area of Pin Q0 in circuit stopwatch: (2750,90) -> (6390,90)<br> - Wire crosses reserved area of Pin Q5 in circuit stopwatch: (3170,340) -> (6650,340)<br> - Wire crosses reserved area of Pin Q4 in circuit stopwatch: (3460,290) -> (6690,290)<br> - Wire crosses reserved area of Pin Q2 in circuit stopwatch: (3700,210) -> (6750,210)<br> - Wire crosses reserved area of Pin Q3 in circuit stopwatch: (3820,240) -> (6730,240)<br> - Wire crosses reserved area of Pin Q5 in circuit stopwatch: (4840,360) -> (7080,360)<br> - Wire crosses reserved area of Pin Q1 in circuit stopwatch: (5730,140) -> (6470,140)<br> - Wire crosses reserved area of Pin Q1 in circuit stopwatch: (5780,160) -> (6370,160)<br> - Wire crosses reserved area of Pin Q2 in circuit stopwatch: (5830,190) -> (6300,190)<br> - Wire crosses reserved area of Pin Q6 in circuit stopwatch: (870,390) -> (6500,390) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\stopwatch.circ | true |
