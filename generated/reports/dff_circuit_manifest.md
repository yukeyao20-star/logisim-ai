# Circuit artifact manifest for dff

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] dff.circ
 - Component factory counts mismatch: expected {D Flip-Flop=1}, got {Clock=1, D Flip-Flop=1}
 - External pin labels mismatch: expected [CLK, D, Q, RST, nQ], got [D, Q, RST, nQ]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=4450 |
| round_trip_loadable | true | [OK] dff.circ round-trip loaded with main circuit dff |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] dff.circ<br> - Component factory counts mismatch: expected {D Flip-Flop=1}, got {Clock=1, D Flip-Flop=1}<br> - External pin labels mismatch: expected [CLK, D, Q, RST, nQ], got [D, Q, RST, nQ] |
| layout_clean | true | [FAIL] dff.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,130) -> (160,200)<br> - Wire crosses reserved area of Pin Q in circuit dff: (110,110) -> (1360,110) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\dff.circ | true |
