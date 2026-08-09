# Circuit artifact manifest for multiplier_2x2

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: truth_table_evidence - IllegalStateException: Output was not resolved: p2

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=21577 |
| round_trip_loadable | true | [OK] multiplier_2x2.circ round-trip loaded with main circuit multiplier_2x2 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] multiplier_2x2.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] multiplier_2x2.circ<br> - Small components are too close in circuit multiplier_2x2 at (320,120) and (330,100): Tunnel and Tunnel<br> - Small components are too close in circuit multiplier_2x2 at (320,120) and (330,140): Tunnel and Tunnel<br> - Small components are too close in circuit multiplier_2x2 at (320,280) and (330,270): Tunnel and Tunnel<br> - Small components are too close in circuit multiplier_2x2 at (320,280) and (330,310): Tunnel and Tunnel<br> - Small components are too close in circuit multiplier_2x2 at (320,440) and (330,440): Tunnel and Tunnel<br> - Small components are too close in circuit multiplier_2x2 at (330,440) and (380,440): Tunnel and Tunnel<br> - Small components are too close in circuit multiplier_2x2 at (440,440) and (490,460): Tunnel and Tunnel<br> - Small components are too close in circuit multiplier_2x2 at (490,460) and (500,440): Tunnel and Tunnel<br> - Small components are too close in circuit multiplier_2x2 at (500,440) and (550,460): Tunnel and Tunnel<br> - Small components are too close in circuit multiplier_2x2 at (550,460) and (560,440): Tunnel and Tunnel<br> - Wire crosses reserved area of Tunnel n3 in circuit multiplier_2x2: (260,280) -> (320,280)<br> - Wire crosses reserved area of Tunnel n5 in circuit multiplier_2x2: (260,440) -> (320,440)<br> - Wire crosses reserved area of Tunnel n4 in circuit multiplier_2x2: (330,270) -> (350,270)<br> - Wire crosses reserved area of Tunnel n15 in circuit multiplier_2x2: (330,440) -> (350,440)<br> - Wire crosses reserved area of AND Gate MULP3 in circuit multiplier_2x2: (350,440) -> (380,440)<br> - Wire crosses reserved area of Tunnel n32 in circuit multiplier_2x2: (550,340) -> (550,460) |
| truth_table_evidence | false | IllegalStateException: Output was not resolved: p2 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\multiplier_2x2.circ | true |
