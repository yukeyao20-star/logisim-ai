# Circuit artifact manifest for traffic_light

- Artifact count: 1
- Check count: 7
- Complete: false

## Issues

- Check failed: structure_matches_spec - [FAIL] traffic_light.circ
 - Component factory counts mismatch: expected {T Flip-Flop=2, NOT Gate=2, AND Gate=2}, got {Clock=1, NOT Gate=2, AND Gate=2, T Flip-Flop=2}
 - External pin labels mismatch: expected [CLK, EN, GREEN, RED, RST, YELLOW], got [EN, GREEN, RED, RST, YELLOW]

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=8266 |
| round_trip_loadable | true | [OK] traffic_light.circ round-trip loaded with main circuit traffic_light |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | false | [FAIL] traffic_light.circ<br> - Component factory counts mismatch: expected {T Flip-Flop=2, NOT Gate=2, AND Gate=2}, got {Clock=1, NOT Gate=2, AND Gate=2, T Flip-Flop=2}<br> - External pin labels mismatch: expected [CLK, EN, GREEN, RED, RST, YELLOW], got [EN, GREEN, RED, RST, YELLOW] |
| layout_clean | true | [FAIL] traffic_light.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,90) -> (160,200)<br> - Wire crosses reserved area of Pin RED in circuit traffic_light: (110,110) -> (2260,110)<br> - Wire crosses reserved area of Pin RED in circuit traffic_light: (160,90) -> (2340,90)<br> - Wire crosses reserved area of Pin GREEN in circuit traffic_light: (490,210) -> (2270,210)<br> - Wire crosses reserved area of Pin GREEN in circuit traffic_light: (550,190) -> (2280,190)<br> - Wire crosses reserved area of Pin YELLOW in circuit traffic_light: (780,160) -> (2370,160)<br> - Wire crosses reserved area of Pin YELLOW in circuit traffic_light: (810,140) -> (2380,140) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\traffic_light.circ | true |
