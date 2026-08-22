# Circuit artifact manifest for satadd_4bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=25719 |
| round_trip_loadable | true | [OK] satadd_4bit.circ round-trip loaded with main circuit satadd_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] satadd_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] satadd_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,30) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,10) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,-100) -> (260,300)<br> - Wire crosses reserved area of Pin S0 in circuit satadd_4bit: (110,90) -> (4600,90)<br> - Wire crosses reserved area of Pin S3 in circuit satadd_4bit: (2060,260) -> (5110,260)<br> - Wire crosses reserved area of Pin S3 in circuit satadd_4bit: (2190,240) -> (5150,240)<br> - Wire crosses reserved area of Pin S2 in circuit satadd_4bit: (2240,210) -> (5220,210)<br> - Wire crosses reserved area of Pin S1 in circuit satadd_4bit: (2360,160) -> (5240,160)<br> - Wire crosses reserved area of Pin S2 in circuit satadd_4bit: (2430,190) -> (5230,190)<br> - Wire crosses reserved area of Pin S1 in circuit satadd_4bit: (560,140) -> (4630,140)<br> - Wire crosses reserved area of Pin S0 in circuit satadd_4bit: (740,110) -> (4740,110) |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 9 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\satadd_4bit.circ | true |
