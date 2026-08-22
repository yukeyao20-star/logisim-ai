# Circuit artifact manifest for tff

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] tff.circ
 - Component factory counts mismatch: expected {T Flip-Flop=1}, got {Clock=1, T Flip-Flop=1}
 - External pin labels mismatch: expected [CLK, Q, RST, T, nQ], got [Q, RST, T, nQ]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=4450 |
| round_trip_loadable | true | [OK] tff.circ round-trip loaded with main circuit tff |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] tff.circ<br> - Component factory counts mismatch: expected {T Flip-Flop=1}, got {Clock=1, T Flip-Flop=1}<br> - External pin labels mismatch: expected [CLK, Q, RST, T, nQ], got [Q, RST, T, nQ] |
| layout_clean | true | [FAIL] tff.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,130) -> (160,200)<br> - Wire crosses reserved area of Pin Q in circuit tff: (110,110) -> (1360,110) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\tff.circ | true |
