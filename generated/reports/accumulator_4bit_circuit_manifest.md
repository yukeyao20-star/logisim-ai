# Circuit artifact manifest for accumulator_4bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=23972 |
| round_trip_loadable | true | [OK] accumulator_4bit.circ round-trip loaded with main circuit accumulator_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] accumulator_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] accumulator_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-520) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-300) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,210) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,70) -> (260,300)<br> - Wire crosses reserved area of AND Gate AA2 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of AND Gate AB2 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of OR Gate AO2 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of OR Gate AG2 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of XOR Gate AX3 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of XOR Gate AS3 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of AND Gate AA3 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of AND Gate AB3 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of OR Gate AO3 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of OR Gate AG3 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of XOR Gate AX4 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of XOR Gate AS4 in circuit accumulator_4bit: (1270,680) -> (6020,680)<br> - Wire crosses reserved area of Pin Q1 in circuit accumulator_4bit: (1540,160) -> (5170,160)<br> - Wire crosses reserved area of Pin Q1 in circuit accumulator_4bit: (1600,140) -> (5180,140)<br> - Wire crosses reserved area of Pin Q0 in circuit accumulator_4bit: (1900,110) -> (5220,110)<br> - Wire crosses reserved area of Pin Q0 in circuit accumulator_4bit: (2020,90) -> (5240,90)<br> - Wire crosses reserved area of Pin Q2 in circuit accumulator_4bit: (210,210) -> (5140,210)<br> - Wire crosses reserved area of Pin Q3 in circuit accumulator_4bit: (3100,240) -> (5480,240)<br> - Wire crosses reserved area of Pin Q2 in circuit accumulator_4bit: (3470,190) -> (5510,190) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\accumulator_4bit.circ | true |
