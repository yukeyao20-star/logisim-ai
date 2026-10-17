# Circuit artifact manifest for subtractor_4bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=23967 |
| round_trip_loadable | true | [OK] subtractor_4bit.circ round-trip loaded with main circuit subtractor_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] subtractor_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] subtractor_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,90) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,70) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,40) -> (260,300)<br> - Wire crosses reserved area of Pin D0 in circuit subtractor_4bit: (160,90) -> (6050,90)<br> - Wire crosses reserved area of Pin D0 in circuit subtractor_4bit: (1910,110) -> (6070,110)<br> - Wire crosses reserved area of Pin D1 in circuit subtractor_4bit: (310,160) -> (6150,160)<br> - Wire crosses reserved area of Pin Bout in circuit subtractor_4bit: (440,310) -> (6020,310)<br> - Wire crosses reserved area of Pin Bout in circuit subtractor_4bit: (4900,290) -> (6940,290)<br> - Wire crosses reserved area of Pin D3 in circuit subtractor_4bit: (5060,260) -> (6960,260)<br> - Wire crosses reserved area of Pin D3 in circuit subtractor_4bit: (5220,240) -> (6980,240)<br> - Wire crosses reserved area of Pin D2 in circuit subtractor_4bit: (790,190) -> (6000,190)<br> - Wire crosses reserved area of Pin D1 in circuit subtractor_4bit: (930,140) -> (6030,140) |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 9 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\subtractor_4bit.circ | true |
