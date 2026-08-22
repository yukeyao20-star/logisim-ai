# Circuit artifact manifest for multiplier_2x2

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=20522 |
| round_trip_loadable | true | [OK] multiplier_2x2.circ round-trip loaded with main circuit multiplier_2x2 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] multiplier_2x2.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] multiplier_2x2.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,140) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,110) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,10) -> (260,300)<br> - Wire crosses reserved area of Pin P1 in circuit multiplier_2x2: (160,140) -> (4840,140)<br> - Wire crosses reserved area of Pin P0 in circuit multiplier_2x2: (210,110) -> (4920,110)<br> - Wire crosses reserved area of Pin P3 in circuit multiplier_2x2: (2620,260) -> (5380,260)<br> - Wire crosses reserved area of Pin P3 in circuit multiplier_2x2: (2890,240) -> (5390,240)<br> - Wire crosses reserved area of Pin P2 in circuit multiplier_2x2: (2940,210) -> (5420,210)<br> - Wire crosses reserved area of Pin P2 in circuit multiplier_2x2: (3050,190) -> (5430,190)<br> - Wire crosses reserved area of Pin P1 in circuit multiplier_2x2: (3260,160) -> (5500,160)<br> - Wire crosses reserved area of Pin P0 in circuit multiplier_2x2: (930,90) -> (4950,90) |
| truth_table_evidence | true | [OK] exhaustive rows=32 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\multiplier_2x2.circ | true |
| truth_table_markdown | generated\reports\multiplier_2x2_circuit_truth_table.md | true |
| truth_table_json | generated\reports\multiplier_2x2_circuit_truth_table.json | true |
