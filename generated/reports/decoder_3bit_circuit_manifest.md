# Circuit artifact manifest for decoder_3bit

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=19195 |
| round_trip_loadable | true | [OK] decoder_3bit.circ round-trip loaded with main circuit decoder_3bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] decoder_3bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] decoder_3bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,120) -> (160,200)<br> - Wire crosses reserved area of Pin Y0 in circuit decoder_3bit: (110,90) -> (4360,90)<br> - Wire crosses reserved area of Pin Y5 in circuit decoder_3bit: (1410,340) -> (4570,340)<br> - Wire crosses reserved area of Pin Y5 in circuit decoder_3bit: (1570,360) -> (4630,360)<br> - Wire crosses reserved area of Pin Y6 in circuit decoder_3bit: (2050,390) -> (4770,390)<br> - Wire crosses reserved area of Pin Y6 in circuit decoder_3bit: (2210,410) -> (4830,410)<br> - Wire crosses reserved area of Pin Y4 in circuit decoder_3bit: (2570,290) -> (4850,290)<br> - Wire crosses reserved area of Pin Y3 in circuit decoder_3bit: (2730,260) -> (4910,260)<br> - Wire crosses reserved area of Pin Y3 in circuit decoder_3bit: (2780,240) -> (4940,240)<br> - Wire crosses reserved area of Pin Y2 in circuit decoder_3bit: (2940,210) -> (4980,210)<br> - Wire crosses reserved area of Pin Y2 in circuit decoder_3bit: (470,190) -> (4350,190)<br> - Wire crosses reserved area of Pin Y1 in circuit decoder_3bit: (500,140) -> (4400,140)<br> - Wire crosses reserved area of Pin Y1 in circuit decoder_3bit: (790,160) -> (4390,160)<br> - Wire crosses reserved area of Pin Y0 in circuit decoder_3bit: (930,110) -> (4410,110)<br> - Wire crosses reserved area of Pin Y4 in circuit decoder_3bit: (930,310) -> (4430,310) |
| truth_table_evidence | true | [OK] exhaustive rows=8 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\decoder_3bit.circ | true |
| truth_table_markdown | generated\reports\decoder_3bit_circuit_truth_table.md | true |
| truth_table_json | generated\reports\decoder_3bit_circuit_truth_table.json | true |
