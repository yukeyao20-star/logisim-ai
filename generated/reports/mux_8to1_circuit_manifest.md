# Circuit artifact manifest for mux_8to1

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=28188 |
| round_trip_loadable | true | [OK] mux_8to1.circ round-trip loaded with main circuit mux_8to1 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] mux_8to1.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] mux_8to1.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-340) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-320) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,-310) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,-280) -> (260,300)<br> - Wire crosses reserved area of Pin Y in circuit mux_8to1: (1090,110) -> (7050,110)<br> - Wire crosses reserved area of Pin Y in circuit mux_8to1: (1250,90) -> (7090,90)<br> - Wire crosses reserved area of AND Gate MUX1S1 in circuit mux_8to1: (560,240) -> (7400,240)<br> - Wire crosses reserved area of AND Gate MUX1S2 in circuit mux_8to1: (560,240) -> (7400,240)<br> - Wire crosses reserved area of AND Gate MUX2D in circuit mux_8to1: (560,240) -> (7400,240)<br> - Wire crosses reserved area of AND Gate MUX2S1 in circuit mux_8to1: (560,240) -> (7400,240)<br> - Wire crosses reserved area of AND Gate MUX2S2 in circuit mux_8to1: (560,240) -> (7400,240)<br> - Wire crosses reserved area of AND Gate MUX3D in circuit mux_8to1: (560,240) -> (7400,240)<br> - Wire crosses reserved area of AND Gate MUX3S1 in circuit mux_8to1: (560,240) -> (7400,240)<br> - Wire crosses reserved area of AND Gate MUX3S2 in circuit mux_8to1: (560,240) -> (7400,240)<br> - Wire crosses reserved area of AND Gate MUX0S1 in circuit mux_8to1: (560,240) -> (7400,240)<br> - Wire crosses reserved area of AND Gate MUX0S2 in circuit mux_8to1: (560,240) -> (7400,240)<br> - Wire crosses reserved area of AND Gate MUX1D in circuit mux_8to1: (560,240) -> (7400,240) |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 11 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mux_8to1.circ | true |
