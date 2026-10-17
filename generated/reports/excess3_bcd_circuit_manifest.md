# Circuit artifact manifest for excess3_bcd

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=8856 |
| round_trip_loadable | true | [OK] excess3_bcd.circ round-trip loaded with main circuit excess3_bcd |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] excess3_bcd.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] excess3_bcd.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,30) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,0) -> (210,250)<br> - Wire crosses reserved area of Pin E1 in circuit excess3_bcd: (110,110) -> (2220,110)<br> - Wire crosses reserved area of Pin E2 in circuit excess3_bcd: (630,160) -> (2270,160)<br> - Wire crosses reserved area of Pin E2 in circuit excess3_bcd: (660,140) -> (2280,140)<br> - Wire crosses reserved area of Pin E1 in circuit excess3_bcd: (930,90) -> (2350,90) |
| truth_table_evidence | true | [OK] exhaustive rows=16 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\excess3_bcd.circ | true |
| truth_table_markdown | generated\reports\excess3_bcd_circuit_truth_table.md | true |
| truth_table_json | generated\reports\excess3_bcd_circuit_truth_table.json | true |
