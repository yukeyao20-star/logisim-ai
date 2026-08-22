# Circuit artifact manifest for counter_4bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=8728 |
| round_trip_loadable | true | [OK] counter_4bit.circ round-trip loaded with main circuit counter_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] counter_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] counter_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,80) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,190) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,210) -> (260,300)<br> - Wire crosses reserved area of Pin Q0 in circuit counter_4bit: (110,90) -> (1380,90)<br> - Wire crosses reserved area of Pin Q2 in circuit counter_4bit: (210,190) -> (1400,190)<br> - Wire crosses reserved area of Pin Q2 in circuit counter_4bit: (260,210) -> (1420,210)<br> - Wire crosses reserved area of Pin Q3 in circuit counter_4bit: (360,240) -> (1500,240)<br> - Wire crosses reserved area of Pin RCO in circuit counter_4bit: (460,290) -> (1580,290)<br> - Wire crosses reserved area of Pin Q0 in circuit counter_4bit: (630,110) -> (1450,110)<br> - Wire crosses reserved area of Pin Q1 in circuit counter_4bit: (650,140) -> (1410,140) |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 9 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\counter_4bit.circ | true |
