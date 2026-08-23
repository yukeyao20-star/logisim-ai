# Circuit artifact manifest for countdown_timer

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=24900 |
| round_trip_loadable | true | [OK] countdown_timer.circ round-trip loaded with main circuit countdown_timer |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] countdown_timer.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] countdown_timer.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-140) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-220) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,120) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,60) -> (260,300)<br> - Wire crosses reserved area of Pin Q2 in circuit countdown_timer: (1100,190) -> (5400,190)<br> - Wire crosses reserved area of Pin Q1 in circuit countdown_timer: (1290,160) -> (5410,160)<br> - Wire crosses reserved area of Pin ZERO in circuit countdown_timer: (1290,310) -> (5430,310)<br> - Wire crosses reserved area of Pin Q1 in circuit countdown_timer: (1340,140) -> (5440,140)<br> - Wire crosses reserved area of Pin Q0 in circuit countdown_timer: (1460,110) -> (5480,110)<br> - Wire crosses reserved area of Pin Q0 in circuit countdown_timer: (1650,90) -> (5490,90)<br> - Wire crosses reserved area of Pin ZERO in circuit countdown_timer: (2210,290) -> (5730,290)<br> - Wire crosses reserved area of Pin Q3 in circuit countdown_timer: (3690,260) -> (5750,260)<br> - Wire crosses reserved area of Pin Q3 in circuit countdown_timer: (3740,240) -> (5780,240)<br> - Wire crosses reserved area of Pin Q2 in circuit countdown_timer: (860,210) -> (5380,210) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\countdown_timer.circ | true |
