# Circuit artifact manifest for comparator_3bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=17007 |
| round_trip_loadable | true | [OK] comparator_3bit.circ round-trip loaded with main circuit comparator_3bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] comparator_3bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] comparator_3bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,70) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,120) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,10) -> (260,300)<br> - Wire crosses reserved area of Pin GT in circuit comparator_3bit: (110,110) -> (4500,110)<br> - Wire crosses reserved area of Pin GT in circuit comparator_3bit: (1110,90) -> (4610,90)<br> - Wire crosses reserved area of Pin LT in circuit comparator_3bit: (2620,210) -> (5040,210)<br> - Wire crosses reserved area of Pin EQ in circuit comparator_3bit: (3530,160) -> (5070,160)<br> - Wire crosses reserved area of Pin LT in circuit comparator_3bit: (630,190) -> (4510,190)<br> - Wire crosses reserved area of Pin EQ in circuit comparator_3bit: (770,140) -> (4550,140) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\comparator_3bit.circ | true |
