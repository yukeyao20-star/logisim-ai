# Circuit artifact manifest for mod6_counter

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=15585 |
| round_trip_loadable | true | [OK] mod6_counter.circ round-trip loaded with main circuit mod6_counter |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] mod6_counter.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] mod6_counter.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,20) -> (110,150)<br> - Wire crosses reserved area of Pin Q0 in circuit mod6_counter: (1040,110) -> (3960,110)<br> - Wire crosses reserved area of Pin Q1 in circuit mod6_counter: (1070,160) -> (3840,160)<br> - Wire crosses reserved area of Pin Q2 in circuit mod6_counter: (1510,190) -> (3770,190)<br> - Wire crosses reserved area of Pin Q1 in circuit mod6_counter: (1730,140) -> (3830,140)<br> - Wire crosses reserved area of Pin TC in circuit mod6_counter: (2560,290) -> (4180,290)<br> - Wire crosses reserved area of Pin Q3 in circuit mod6_counter: (2870,240) -> (4200,240)<br> - Wire crosses reserved area of Pin Q2 in circuit mod6_counter: (2920,210) -> (4210,210)<br> - Wire crosses reserved area of Pin Q0 in circuit mod6_counter: (3410,90) -> (3790,90) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mod6_counter.circ | true |
