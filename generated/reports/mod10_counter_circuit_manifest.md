# Circuit artifact manifest for mod10_counter

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=15588 |
| round_trip_loadable | true | [OK] mod10_counter.circ round-trip loaded with main circuit mod10_counter |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] mod10_counter.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] mod10_counter.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,0) -> (110,150)<br> - Wire crosses reserved area of Pin Q1 in circuit mod10_counter: (1070,160) -> (3840,160)<br> - Wire crosses reserved area of Pin Q2 in circuit mod10_counter: (1510,190) -> (3770,190)<br> - Wire crosses reserved area of Pin Q0 in circuit mod10_counter: (1780,110) -> (3930,110)<br> - Wire crosses reserved area of Pin Q1 in circuit mod10_counter: (1850,140) -> (3830,140)<br> - Wire crosses reserved area of Pin TC in circuit mod10_counter: (2800,290) -> (4140,290)<br> - Wire crosses reserved area of Pin Q3 in circuit mod10_counter: (2920,240) -> (4210,240)<br> - Wire crosses reserved area of Pin Q2 in circuit mod10_counter: (2990,210) -> (4220,210)<br> - Wire crosses reserved area of Pin Q0 in circuit mod10_counter: (3410,90) -> (3790,90) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mod10_counter.circ | true |
