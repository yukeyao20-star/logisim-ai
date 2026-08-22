# Circuit artifact manifest for oneshot_4clk

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=13264 |
| round_trip_loadable | true | [OK] oneshot_4clk.circ round-trip loaded with main circuit oneshot_4clk |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] oneshot_4clk.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] oneshot_4clk.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-150) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,130) -> (210,250)<br> - Wire crosses reserved area of Pin PULSE in circuit oneshot_4clk: (110,90) -> (3540,90)<br> - Wire crosses reserved area of Pin PULSE in circuit oneshot_4clk: (1760,110) -> (3610,110) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\oneshot_4clk.circ | true |
