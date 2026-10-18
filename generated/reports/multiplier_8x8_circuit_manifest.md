# Circuit artifact manifest for multiplier_8x8

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=260960 |
| round_trip_loadable | true | [OK] multiplier_8x8.circ round-trip loaded with main circuit multiplier_8x8 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] multiplier_8x8.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] multiplier_8x8.circ<br> - Small components are too close in circuit multiplier_8x8 at (1400,700) and (1410,690): Pin and Tunnel<br> - Small components are too close in circuit multiplier_8x8 at (1540,600) and (1580,590): Pin and Tunnel<br> - Wire crosses reserved area of Pin P11 in circuit multiplier_8x8: (1460,590) -> (1460,670)<br> - Tunnel TQ26 is too close to external pin P11 on the same row: (1410,650) vs (1470,650)<br> - Tunnel TQ29 is too close to external pin P11 on the same row: (1530,650) vs (1470,650)<br> - Tunnel TQ26 is too close to external pin B3 on the same row: (450,650) vs (310,650) |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 17 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/multiplier_8x8.circ | true |
