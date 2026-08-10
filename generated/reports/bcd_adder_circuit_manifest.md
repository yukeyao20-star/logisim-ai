# Circuit artifact manifest for bcd_adder

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=47908 |
| round_trip_loadable | true | [OK] bcd_adder.circ round-trip loaded with main circuit bcd_adder |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] bcd_adder.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] bcd_adder.circ<br> - Small components are too close in circuit bcd_adder at (1110,120) and (1130,120): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (1130,120) and (1170,120): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (1170,120) and (1190,120): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (320,100) and (320,120): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (320,120) and (320,140): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (320,270) and (320,280): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (320,280) and (320,310): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (320,440) and (330,440): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (320,600) and (330,610): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (320,680) and (330,650): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (320,760) and (330,780): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (320,840) and (330,820): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (330,820) and (380,840): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (920,840) and (970,820): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (930,120) and (960,100): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (930,120) and (960,140): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (960,100) and (990,120): Tunnel and Tunnel<br> - Small components are too close in circuit bcd_adder at (960,140) and (990,120): Tunnel and Tunnel<br> - Wire crosses reserved area of Tunnel n92 in circuit bcd_adder: (1040,70) -> (1040,120)<br> - Wire crosses reserved area of XOR Gate BX26 in circuit bcd_adder: (1050,120) -> (1110,120)<br> - Wire crosses reserved area of Tunnel n107 in circuit bcd_adder: (1190,70) -> (1190,120)<br> - Wire crosses reserved area of Tunnel n3 in circuit bcd_adder: (260,280) -> (320,280)<br> - Wire crosses reserved area of Tunnel n5 in circuit bcd_adder: (260,440) -> (320,440)<br> - Wire crosses reserved area of Tunnel n7 in circuit bcd_adder: (260,600) -> (320,600)<br> - Wire crosses reserved area of Tunnel n17 in circuit bcd_adder: (320,270) -> (340,270)<br> - Wire crosses reserved area of Tunnel n29 in circuit bcd_adder: (330,440) -> (350,440)<br> - Wire crosses reserved area of Tunnel n41 in circuit bcd_adder: (330,610) -> (350,610)<br> - Wire crosses reserved area of Tunnel n48 in circuit bcd_adder: (400,850) -> (550,850)<br> - Wire crosses reserved area of Tunnel n56 in circuit bcd_adder: (400,850) -> (550,850)<br> - Wire crosses reserved area of Tunnel n58 in circuit bcd_adder: (400,850) -> (550,850)<br> - Wire crosses reserved area of Tunnel n58 in circuit bcd_adder: (550,850) -> (550,970)<br> - Wire crosses reserved area of Tunnel n92 in circuit bcd_adder: (990,120) -> (1040,120) |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 10 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\bcd_adder.circ | true |
