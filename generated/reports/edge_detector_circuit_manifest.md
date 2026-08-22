# Circuit artifact manifest for edge_detector

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=6408 |
| round_trip_loadable | true | [OK] edge_detector.circ round-trip loaded with main circuit edge_detector |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] edge_detector.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] edge_detector.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,140) -> (160,200)<br> - Wire crosses reserved area of Pin FALL in circuit edge_detector: (160,140) -> (1860,140)<br> - Wire crosses reserved area of AND Gate A1 in circuit edge_detector: (520,240) -> (1870,240)<br> - Wire crosses reserved area of AND Gate A2 in circuit edge_detector: (520,240) -> (1870,240)<br> - Wire crosses reserved area of NOT Gate N1 in circuit edge_detector: (520,240) -> (1870,240)<br> - Wire crosses reserved area of NOT Gate N2 in circuit edge_detector: (520,240) -> (1870,240)<br> - Wire crosses reserved area of Pin RISE in circuit edge_detector: (550,110) -> (1940,110)<br> - Wire crosses reserved area of Pin RISE in circuit edge_detector: (730,90) -> (1950,90)<br> - Wire crosses reserved area of Pin FALL in circuit edge_detector: (850,160) -> (1900,160) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\edge_detector.circ | true |
