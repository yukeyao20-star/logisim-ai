# Circuit artifact manifest for encoder_8to3

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=8857 |
| round_trip_loadable | true | [OK] encoder_8to3.circ round-trip loaded with main circuit encoder_8to3 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] encoder_8to3.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] encoder_8to3.circ<br> - Small components are too close in circuit encoder_8to3 at (410,150) and (430,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (410,150) and (450,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (410,90) and (430,90): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (410,90) and (450,90): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (430,150) and (450,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (430,150) and (470,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (430,90) and (450,90): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (430,90) and (470,90): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (450,150) and (470,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (450,150) and (490,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (450,150) and (500,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (450,90) and (470,90): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (450,90) and (490,90): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (470,150) and (490,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (470,150) and (500,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (470,150) and (510,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (470,90) and (490,90): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (470,90) and (510,90): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (490,150) and (500,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (490,150) and (510,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (490,90) and (510,90): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (490,90) and (530,90): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (500,150) and (510,150): Tunnel and Tunnel<br> - Small components are too close in circuit encoder_8to3 at (510,90) and (530,90): Tunnel and Tunnel<br> - Wire crosses reserved area of Tunnel n1 in circuit encoder_8to3: (470,150) -> (490,150)<br> - Wire crosses reserved area of Tunnel n7 in circuit encoder_8to3: (490,150) -> (500,150)<br> - Wire crosses reserved area of Tunnel n6 in circuit encoder_8to3: (500,150) -> (510,150)<br> - Wire crosses reserved area of Tunnel n1 in circuit encoder_8to3: (510,150) -> (530,150) |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 9 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\encoder_8to3.circ | true |
