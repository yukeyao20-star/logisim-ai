# Circuit artifact manifest for jkff

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] jkff.circ
 - Component factory counts mismatch: expected {D Flip-Flop=1, NOT Gate=1, AND Gate=2, OR Gate=1}, got {Clock=1, AND Gate=2, OR Gate=1, NOT Gate=1, D Flip-Flop=1}
 - External pin labels mismatch: expected [CLK, J, K, Q, RST, nQ], got [J, K, Q, RST, nQ]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=7166 |
| round_trip_loadable | true | [OK] jkff.circ round-trip loaded with main circuit jkff |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] jkff.circ<br> - Component factory counts mismatch: expected {D Flip-Flop=1, NOT Gate=1, AND Gate=2, OR Gate=1}, got {Clock=1, AND Gate=2, OR Gate=1, NOT Gate=1, D Flip-Flop=1}<br> - External pin labels mismatch: expected [CLK, J, K, Q, RST, nQ], got [J, K, Q, RST, nQ] |
| layout_clean | true | [FAIL] jkff.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,160) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,40) -> (210,250)<br> - Wire crosses reserved area of Pin Q in circuit jkff: (110,90) -> (2020,90)<br> - Wire crosses reserved area of Pin nQ in circuit jkff: (160,160) -> (2000,160)<br> - Wire crosses reserved area of Pin nQ in circuit jkff: (870,140) -> (2030,140)<br> - Wire crosses reserved area of Pin Q in circuit jkff: (920,110) -> (2100,110) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\jkff.circ | true |
