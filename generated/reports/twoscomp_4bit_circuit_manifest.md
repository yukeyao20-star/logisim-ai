# Circuit artifact manifest for twoscomp_4bit

- Artifact count: 3
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=10401 |
| round_trip_loadable | true | [OK] twoscomp_4bit.circ round-trip loaded with main circuit twoscomp_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] twoscomp_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] twoscomp_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,120) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,140) -> (210,250)<br> - Wire crosses reserved area of Pin B0 in circuit twoscomp_4bit: (110,90) -> (2720,90)<br> - Wire crosses reserved area of Pin B1 in circuit twoscomp_4bit: (210,140) -> (2760,140)<br> - Wire crosses reserved area of Pin B2 in circuit twoscomp_4bit: (470,190) -> (2710,190)<br> - Wire crosses reserved area of Pin B0 in circuit twoscomp_4bit: (660,110) -> (2800,110)<br> - Wire crosses reserved area of Pin B1 in circuit twoscomp_4bit: (790,160) -> (2750,160) |
| truth_table_evidence | true | [OK] exhaustive rows=16 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\twoscomp_4bit.circ | true |
| truth_table_markdown | generated\reports\twoscomp_4bit_circuit_truth_table.md | true |
| truth_table_json | generated\reports\twoscomp_4bit_circuit_truth_table.json | true |
