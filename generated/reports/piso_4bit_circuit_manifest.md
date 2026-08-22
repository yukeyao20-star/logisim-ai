# Circuit artifact manifest for piso_4bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=17698 |
| round_trip_loadable | true | [OK] piso_4bit.circ round-trip loaded with main circuit piso_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] piso_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] piso_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-250) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,-150) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,240) -> (260,300)<br> - Wire crosses reserved area of Pin SO in circuit piso_4bit: (1430,110) -> (3870,110)<br> - Wire crosses reserved area of Pin SO in circuit piso_4bit: (1480,90) -> (3900,90)<br> - Wire crosses reserved area of Pin Q3 in circuit piso_4bit: (2490,290) -> (4110,290)<br> - Wire crosses reserved area of Pin Q2 in circuit piso_4bit: (260,240) -> (3760,240)<br> - Wire crosses reserved area of AND Gate PISOA1 in circuit piso_4bit: (260,240) -> (3760,240)<br> - Wire crosses reserved area of AND Gate PISOB1 in circuit piso_4bit: (260,240) -> (3760,240)<br> - Wire crosses reserved area of OR Gate PISOM1 in circuit piso_4bit: (260,240) -> (3760,240)<br> - Wire crosses reserved area of AND Gate PISOA2 in circuit piso_4bit: (260,240) -> (3760,240)<br> - Wire crosses reserved area of AND Gate PISOB2 in circuit piso_4bit: (260,240) -> (3760,240)<br> - Wire crosses reserved area of OR Gate PISOM2 in circuit piso_4bit: (260,240) -> (3760,240)<br> - Wire crosses reserved area of NOT Gate PISOL in circuit piso_4bit: (260,240) -> (3760,240)<br> - Wire crosses reserved area of AND Gate PISOA0 in circuit piso_4bit: (260,240) -> (3760,240)<br> - Wire crosses reserved area of AND Gate PISOB0 in circuit piso_4bit: (260,240) -> (3760,240)<br> - Wire crosses reserved area of OR Gate PISOM0 in circuit piso_4bit: (260,240) -> (3760,240)<br> - Wire crosses reserved area of Pin Q1 in circuit piso_4bit: (360,210) -> (3950,210)<br> - Wire crosses reserved area of Pin Q1 in circuit piso_4bit: (620,190) -> (3780,190)<br> - Wire crosses reserved area of Pin Q0 in circuit piso_4bit: (860,160) -> (3820,160)<br> - Wire crosses reserved area of Pin Q0 in circuit piso_4bit: (970,140) -> (3830,140) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\piso_4bit.circ | true |
