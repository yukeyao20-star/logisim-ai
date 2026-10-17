# Circuit artifact manifest for seqdet_101

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] seqdet_101.circ
 - Component factory counts mismatch: expected {D Flip-Flop=2, NOT Gate=2, AND Gate=4, OR Gate=1}, got {Clock=1, NOT Gate=2, AND Gate=4, OR Gate=1, D Flip-Flop=2}
 - External pin labels mismatch: expected [CLK, F, RST, X], got [F, RST, X]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=9403 |
| round_trip_loadable | true | [OK] seqdet_101.circ round-trip loaded with main circuit seqdet_101 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] seqdet_101.circ<br> - Component factory counts mismatch: expected {D Flip-Flop=2, NOT Gate=2, AND Gate=4, OR Gate=1}, got {Clock=1, NOT Gate=2, AND Gate=4, OR Gate=1, D Flip-Flop=2}<br> - External pin labels mismatch: expected [CLK, F, RST, X], got [F, RST, X] |
| layout_clean | true | [FAIL] seqdet_101.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-50) -> (160,200)<br> - Wire crosses reserved area of Pin F in circuit seqdet_101: (110,110) -> (2740,110)<br> - Wire crosses reserved area of Pin F in circuit seqdet_101: (1450,90) -> (2850,90) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\seqdet_101.circ | true |
