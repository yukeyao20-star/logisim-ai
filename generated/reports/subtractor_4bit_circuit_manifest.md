# Circuit artifact manifest for subtractor_4bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=24383 |
| round_trip_loadable | true | [OK] subtractor_4bit.circ round-trip loaded with main circuit subtractor_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] subtractor_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] subtractor_4bit.circ<br> - Small components are too close in circuit subtractor_4bit at (1250,1140) and (1290,1120): Tunnel and Tunnel<br> - Small components are too close in circuit subtractor_4bit at (1250,1140) and (1290,1160): Tunnel and Tunnel<br> - Small components are too close in circuit subtractor_4bit at (320,760) and (330,780): Tunnel and Tunnel<br> - Small components are too close in circuit subtractor_4bit at (330,780) and (380,760): Tunnel and Tunnel<br> - Small components are too close in circuit subtractor_4bit at (610,1140) and (650,1120): Tunnel and Tunnel<br> - Small components are too close in circuit subtractor_4bit at (610,1140) and (650,1160): Tunnel and Tunnel<br> - Small components are too close in circuit subtractor_4bit at (930,1140) and (970,1120): Tunnel and Tunnel<br> - Small components are too close in circuit subtractor_4bit at (930,1140) and (970,1160): Tunnel and Tunnel |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 9 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\subtractor_4bit.circ | true |
