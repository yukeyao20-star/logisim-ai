# Circuit artifact manifest for decoder_2bit

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=7807 |
| round_trip_loadable | true | [OK] decoder_2bit.circ round-trip loaded with main circuit decoder_2bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] decoder_2bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] decoder_2bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)<br> - Wire crosses reserved area of Pin Y0 in circuit decoder_2bit: (110,90) -> (2080,90)<br> - Wire crosses reserved area of Pin Y2 in circuit decoder_2bit: (470,190) -> (2070,190)<br> - Wire crosses reserved area of Pin Y1 in circuit decoder_2bit: (770,140) -> (2110,140)<br> - Wire crosses reserved area of Pin Y0 in circuit decoder_2bit: (980,110) -> (2200,110) |
| truth_table_evidence | true | [OK] exhaustive rows=4 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\decoder_2bit.circ | true |
| truth_table_markdown | generated\reports\decoder_2bit_circuit_truth_table.md | true |
| truth_table_json | generated\reports\decoder_2bit_circuit_truth_table.json | true |
