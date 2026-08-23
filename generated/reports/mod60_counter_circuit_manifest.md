# Circuit artifact manifest for mod60_counter

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=23509 |
| round_trip_loadable | true | [OK] mod60_counter.circ round-trip loaded with main circuit mod60_counter |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] mod60_counter.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] mod60_counter.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-510) -> (110,150)<br> - Wire crosses reserved area of Pin Q2 in circuit mod60_counter: (2060,190) -> (5330,190)<br> - Wire crosses reserved area of Pin Q1 in circuit mod60_counter: (2250,160) -> (5340,160)<br> - Wire crosses reserved area of Pin Q1 in circuit mod60_counter: (2300,140) -> (5370,140)<br> - Wire crosses reserved area of Pin Q0 in circuit mod60_counter: (2490,110) -> (5400,110)<br> - Wire crosses reserved area of Pin Q0 in circuit mod60_counter: (2540,90) -> (5410,90)<br> - Wire crosses reserved area of Pin TC in circuit mod60_counter: (3390,410) -> (5520,410)<br> - Wire crosses reserved area of Pin TC in circuit mod60_counter: (3510,390) -> (5530,390)<br> - Wire crosses reserved area of Pin Q5 in circuit mod60_counter: (3560,340) -> (5580,340)<br> - Wire crosses reserved area of Pin Q5 in circuit mod60_counter: (3800,360) -> (5560,360)<br> - Wire crosses reserved area of Pin Q3 in circuit mod60_counter: (3870,240) -> (5600,240)<br> - Wire crosses reserved area of Pin Q2 in circuit mod60_counter: (4040,210) -> (5610,210)<br> - Wire crosses reserved area of Pin Q4 in circuit mod60_counter: (4750,290) -> (5320,290) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mod60_counter.circ | true |
