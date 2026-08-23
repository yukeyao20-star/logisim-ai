# Circuit artifact manifest for freq_meter

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=39418 |
| round_trip_loadable | true | [OK] freq_meter.circ round-trip loaded with main circuit freq_meter |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] freq_meter.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] freq_meter.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,-90) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,-690) -> (160,200)<br> - Wire crosses reserved area of Pin F0 in circuit freq_meter: (1050,90) -> (9280,90)<br> - Wire crosses reserved area of Pin F1 in circuit freq_meter: (1250,140) -> (9230,140)<br> - Wire crosses reserved area of Pin F2 in circuit freq_meter: (1520,210) -> (9180,210)<br> - Wire crosses reserved area of Pin F1 in circuit freq_meter: (1760,160) -> (9220,160)<br> - Wire crosses reserved area of Pin F3 in circuit freq_meter: (3080,240) -> (9540,240)<br> - Wire crosses reserved area of Pin WIN in circuit freq_meter: (3580,290) -> (9520,290)<br> - Wire crosses reserved area of Pin F0 in circuit freq_meter: (450,110) -> (9270,110)<br> - Wire crosses reserved area of Pin F2 in circuit freq_meter: (730,190) -> (9190,190) |
| truth_table_evidence | true | [SKIP] native TTL chip present; in-process truth table only covers primitive gates |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\freq_meter.circ | true |
