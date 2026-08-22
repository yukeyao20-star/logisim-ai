# Circuit artifact manifest for graycnt_4bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=14681 |
| round_trip_loadable | true | [OK] graycnt_4bit.circ round-trip loaded with main circuit graycnt_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] graycnt_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] graycnt_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,40) -> (110,150)<br> - Wire crosses reserved area of Pin G3 in circuit graycnt_4bit: (1270,390) -> (3400,390)<br> - Wire crosses reserved area of Pin G0 in circuit graycnt_4bit: (1330,90) -> (3460,90)<br> - Wire crosses reserved area of Pin Q3 in circuit graycnt_4bit: (1730,440) -> (3760,440)<br> - Wire crosses reserved area of Pin G3 in circuit graycnt_4bit: (1840,410) -> (3560,410)<br> - Wire crosses reserved area of Pin Q3 in circuit graycnt_4bit: (2200,460) -> (3770,460)<br> - Wire crosses reserved area of Pin Q2 in circuit graycnt_4bit: (2560,340) -> (3660,340)<br> - Wire crosses reserved area of Pin Q1 in circuit graycnt_4bit: (2630,240) -> (3500,240)<br> - Wire crosses reserved area of Pin G1 in circuit graycnt_4bit: (2680,210) -> (3490,210)<br> - Wire crosses reserved area of Pin Q0 in circuit graycnt_4bit: (490,160) -> (3350,160)<br> - Wire crosses reserved area of Pin Q2 in circuit graycnt_4bit: (490,360) -> (3370,360)<br> - Wire crosses reserved area of Pin G1 in circuit graycnt_4bit: (520,190) -> (3420,190)<br> - Wire crosses reserved area of Pin Q0 in circuit graycnt_4bit: (780,140) -> (3430,140) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\graycnt_4bit.circ | true |
