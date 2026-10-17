# Circuit artifact manifest for accumulator_4bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=29363 |
| round_trip_loadable | true | [OK] accumulator_4bit.circ round-trip loaded with main circuit accumulator_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] accumulator_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] accumulator_4bit.circ<br> - Small components are too close in circuit accumulator_4bit at (1010,170) and (1040,180): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (1070,130) and (1120,130): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (1120,130) and (1130,130): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (1330,170) and (1360,180): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (1390,130) and (1440,130): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (1440,130) and (1450,130): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (370,170) and (400,180): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (380,130) and (430,130): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (380,210) and (400,180): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (400,180) and (440,210): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (430,130) and (440,130): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (430,130) and (480,130): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (440,130) and (480,130): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (440,130) and (490,130): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (480,130) and (490,130): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (690,170) and (720,180): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (750,130) and (800,130): Tunnel and Tunnel<br> - Small components are too close in circuit accumulator_4bit at (800,130) and (810,130): Tunnel and Tunnel<br> - Wire crosses reserved area of Tunnel n59 in circuit accumulator_4bit: (1010,170) -> (1030,170)<br> - Wire crosses reserved area of Tunnel n30 in circuit accumulator_4bit: (1090,130) -> (1120,130)<br> - Wire crosses reserved area of Tunnel n63 in circuit accumulator_4bit: (1330,170) -> (1350,170)<br> - Wire crosses reserved area of Tunnel n42 in circuit accumulator_4bit: (1410,130) -> (1440,130)<br> - Wire crosses reserved area of Tunnel n51 in circuit accumulator_4bit: (370,170) -> (390,170)<br> - Wire crosses reserved area of Tunnel n58 in circuit accumulator_4bit: (390,130) -> (430,130)<br> - Wire crosses reserved area of Tunnel n62 in circuit accumulator_4bit: (390,130) -> (430,130)<br> - Wire crosses reserved area of Tunnel n2 in circuit accumulator_4bit: (440,130) -> (450,130)<br> - Wire crosses reserved area of Tunnel n62 in circuit accumulator_4bit: (450,110) -> (450,130)<br> - Wire crosses reserved area of Tunnel n62 in circuit accumulator_4bit: (450,130) -> (480,130)<br> - Wire crosses reserved area of Tunnel n6 in circuit accumulator_4bit: (450,130) -> (480,130)<br> - Wire crosses reserved area of Tunnel n55 in circuit accumulator_4bit: (690,170) -> (710,170)<br> - Wire crosses reserved area of Tunnel n18 in circuit accumulator_4bit: (770,130) -> (800,130) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/accumulator_4bit.circ | true |
