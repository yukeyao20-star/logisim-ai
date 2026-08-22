# Circuit artifact manifest for demux_4to1

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=10833 |
| round_trip_loadable | true | [OK] demux_4to1.circ round-trip loaded with main circuit demux_4to1 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] demux_4to1.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] demux_4to1.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,140) -> (160,200)<br> - Wire crosses reserved area of Pin Y0 in circuit demux_4to1: (110,110) -> (2700,110)<br> - Wire crosses reserved area of Pin Y1 in circuit demux_4to1: (160,140) -> (2720,140)<br> - Wire crosses reserved area of Pin Y2 in circuit demux_4to1: (470,190) -> (2710,190)<br> - Wire crosses reserved area of Pin Y0 in circuit demux_4to1: (930,90) -> (2790,90) |
| truth_table_evidence | true | [OK] exhaustive rows=8 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\demux_4to1.circ | true |
| truth_table_markdown | generated\reports\demux_4to1_circuit_truth_table.md | true |
| truth_table_json | generated\reports\demux_4to1_circuit_truth_table.json | true |
