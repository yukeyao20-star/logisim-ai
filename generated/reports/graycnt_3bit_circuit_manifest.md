# Circuit artifact manifest for graycnt_3bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=10968 |
| round_trip_loadable | true | [OK] graycnt_3bit.circ round-trip loaded with main circuit graycnt_3bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] graycnt_3bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] graycnt_3bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-30) -> (110,150)<br> - Wire crosses reserved area of Pin G1 in circuit graycnt_3bit: (1280,210) -> (2600,210)<br> - Wire crosses reserved area of Pin Q0 in circuit graycnt_3bit: (1470,140) -> (2650,140)<br> - Wire crosses reserved area of Pin Q2 in circuit graycnt_3bit: (1700,340) -> (2730,340)<br> - Wire crosses reserved area of Pin G1 in circuit graycnt_3bit: (490,190) -> (2610,190)<br> - Wire crosses reserved area of Pin G0 in circuit graycnt_3bit: (750,110) -> (2690,110)<br> - Wire crosses reserved area of Pin G0 in circuit graycnt_3bit: (810,90) -> (2700,90) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\graycnt_3bit.circ | true |
