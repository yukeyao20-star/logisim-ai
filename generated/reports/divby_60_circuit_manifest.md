# Circuit artifact manifest for divby_60

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=30115 |
| round_trip_loadable | true | [OK] divby_60.circ round-trip loaded with main circuit divby_60 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] divby_60.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] divby_60.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-220) -> (110,150)<br> - Wire crosses reserved area of Pin Q1 in circuit divby_60: (1610,140) -> (6580,140)<br> - Wire crosses reserved area of Pin Q7 in circuit divby_60: (1610,510) -> (7430,510)<br> - Wire crosses reserved area of Pin TC1 in circuit divby_60: (1730,540) -> (7470,540)<br> - Wire crosses reserved area of Pin Q0 in circuit divby_60: (1730,90) -> (6670,90)<br> - Wire crosses reserved area of AND Gate S0M2 in circuit divby_60: (1850,660) -> (7560,660)<br> - Wire crosses reserved area of OR Gate S0O2 in circuit divby_60: (1850,660) -> (7560,660)<br> - Wire crosses reserved area of AND Gate S0R3 in circuit divby_60: (1850,660) -> (7560,660)<br> - Wire crosses reserved area of AND Gate S0M3 in circuit divby_60: (1850,660) -> (7560,660)<br> - Wire crosses reserved area of OR Gate S0O3 in circuit divby_60: (1850,660) -> (7560,660)<br> - Wire crosses reserved area of AND Gate S1A1 in circuit divby_60: (1850,660) -> (7560,660)<br> - Wire crosses reserved area of NOT Gate S1N2 in circuit divby_60: (1850,660) -> (7560,660)<br> - Wire crosses reserved area of AND Gate S1A2 in circuit divby_60: (1850,660) -> (7560,660)<br> - Wire crosses reserved area of Pin Q4 in circuit divby_60: (2210,340) -> (6730,340)<br> - Wire crosses reserved area of Pin TC0 in circuit divby_60: (3180,290) -> (7060,290)<br> - Wire crosses reserved area of Pin Q6 in circuit divby_60: (3210,460) -> (6790,460)<br> - Wire crosses reserved area of Pin Q5 in circuit divby_60: (3470,410) -> (6840,410)<br> - Wire crosses reserved area of Pin Q3 in circuit divby_60: (3700,240) -> (7080,240)<br> - Wire crosses reserved area of Pin Q2 in circuit divby_60: (3990,210) -> (7100,210)<br> - Wire crosses reserved area of Pin Q2 in circuit divby_60: (4690,190) -> (7630,190)<br> - Wire crosses reserved area of Pin TC in circuit divby_60: (4740,590) -> (7130,590)<br> - Wire crosses reserved area of Pin Q1 in circuit divby_60: (4980,160) -> (7660,160)<br> - Wire crosses reserved area of Pin Q0 in circuit divby_60: (6040,110) -> (6710,110)<br> - Wire crosses reserved area of Pin TC in circuit divby_60: (750,610) -> (7520,610) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\divby_60.circ | true |
