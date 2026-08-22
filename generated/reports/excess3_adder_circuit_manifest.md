# Circuit artifact manifest for excess3_adder

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=37344 |
| round_trip_loadable | true | [OK] excess3_adder.circ round-trip loaded with main circuit excess3_adder |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] excess3_adder.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] excess3_adder.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,50) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,20) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,-80) -> (260,300)<br> - Wire crosses reserved area of Pin Cout in circuit excess3_adder: (1940,290) -> (7560,290)<br> - Wire crosses reserved area of Pin S3 in circuit excess3_adder: (2000,240) -> (7640,240)<br> - Wire crosses reserved area of Pin S2 in circuit excess3_adder: (2240,210) -> (7670,210)<br> - Wire crosses reserved area of Pin S3 in circuit excess3_adder: (2310,260) -> (7630,260)<br> - Wire crosses reserved area of Pin S2 in circuit excess3_adder: (2430,190) -> (7680,190)<br> - Wire crosses reserved area of Pin Cout in circuit excess3_adder: (440,310) -> (7260,310)<br> - Wire crosses reserved area of Pin S1 in circuit excess3_adder: (500,160) -> (7280,160)<br> - Wire crosses reserved area of XOR Gate E3X1 in circuit excess3_adder: (510,-490) -> (510,550)<br> - Wire crosses reserved area of Pin S1 in circuit excess3_adder: (560,140) -> (7290,140)<br> - Wire crosses reserved area of OR Gate E3G28 in circuit excess3_adder: (6150,1920) -> (8490,1920)<br> - Wire crosses reserved area of Pin S0 in circuit excess3_adder: (860,110) -> (7330,110)<br> - Wire crosses reserved area of Pin S0 in circuit excess3_adder: (980,90) -> (7350,90) |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 10 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\excess3_adder.circ | true |
