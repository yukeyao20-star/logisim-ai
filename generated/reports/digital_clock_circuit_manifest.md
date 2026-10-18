# Circuit artifact manifest for digital_clock

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=47731 |
| round_trip_loadable | true | [OK] digital_clock.circ round-trip loaded with main circuit digital_clock |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] digital_clock.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] digital_clock.circ<br> - Small components are too close in circuit digital_clock at (1190,1010) and (1220,1000): Tunnel and Pin<br> - Small components are too close in circuit digital_clock at (1640,690) and (1640,700): Tunnel and Pin<br> - Small components are too close in circuit digital_clock at (1710,650) and (1760,650): Pin and Tunnel<br> - Small components are too close in circuit digital_clock at (1780,600) and (1810,590): Pin and Tunnel<br> - Tunnel TQ1k is too close to external pin Q11 on the same row: (1640,650) vs (1710,650)<br> - Tunnel TQ10 is too close to external pin Q11 on the same row: (1760,650) vs (1710,650) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/digital_clock.circ | true |
