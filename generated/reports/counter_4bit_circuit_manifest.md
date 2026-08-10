# Circuit artifact manifest for counter_4bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=8863 |
| round_trip_loadable | true | [OK] counter_4bit.circ round-trip loaded with main circuit counter_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] counter_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] counter_4bit.circ<br> - Small components are too close in circuit counter_4bit at (410,150) and (430,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (410,150) and (450,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (410,90) and (430,90): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (410,90) and (450,90): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (430,150) and (450,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (430,150) and (470,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (430,90) and (450,90): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (430,90) and (470,90): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (450,150) and (470,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (450,150) and (490,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (450,150) and (500,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (450,90) and (470,90): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (450,90) and (490,90): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (470,150) and (490,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (470,150) and (500,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (470,150) and (510,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (470,90) and (490,90): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (470,90) and (510,90): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (490,150) and (500,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (490,150) and (510,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (490,90) and (510,90): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (490,90) and (530,90): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (500,150) and (510,150): Tunnel and Tunnel<br> - Small components are too close in circuit counter_4bit at (510,90) and (530,90): Tunnel and Tunnel<br> - Wire crosses reserved area of Tunnel n4 in circuit counter_4bit: (470,150) -> (490,150)<br> - Wire crosses reserved area of Tunnel n2 in circuit counter_4bit: (490,150) -> (500,150)<br> - Wire crosses reserved area of Tunnel n12 in circuit counter_4bit: (500,150) -> (510,150)<br> - Wire crosses reserved area of Tunnel n4 in circuit counter_4bit: (510,150) -> (530,150) |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 9 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\counter_4bit.circ | true |
