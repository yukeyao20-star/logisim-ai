# Circuit artifact manifest for adder_4bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=23937 |
| round_trip_loadable | true | [OK] adder_4bit.circ round-trip loaded with main circuit adder_4bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] adder_4bit.circ structure matches CircuitSpec |
| layout_clean | true | [FAIL] adder_4bit.circ<br> - Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)<br> - Vertical wire in left boundary lane can short external inputs: (160,110) -> (160,200)<br> - Vertical wire in left boundary lane can short external inputs: (210,90) -> (210,250)<br> - Vertical wire in left boundary lane can short external inputs: (260,160) -> (260,300)<br> - Wire crosses reserved area of Pin S0 in circuit adder_4bit: (160,110) -> (4660,110)<br> - Wire crosses reserved area of Pin S2 in circuit adder_4bit: (1950,190) -> (5150,190)<br> - Wire crosses reserved area of Pin S0 in circuit adder_4bit: (210,90) -> (4700,90)<br> - Wire crosses reserved area of Pin S1 in circuit adder_4bit: (260,160) -> (4740,160)<br> - Wire crosses reserved area of Pin S2 in circuit adder_4bit: (360,210) -> (4820,210)<br> - Wire crosses reserved area of Pin Cout in circuit adder_4bit: (3620,290) -> (5500,290)<br> - Wire crosses reserved area of Pin S3 in circuit adder_4bit: (3810,240) -> (5530,240)<br> - Wire crosses reserved area of Pin S3 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of OR Gate FA0O1 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of OR Gate FA0O2 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of XOR Gate FA1X1 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of XOR Gate FA1X2 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of AND Gate FA1G1 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of AND Gate FA1G2 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of AND Gate FA1G3 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of XOR Gate FA0X1 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of XOR Gate FA0X2 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of AND Gate FA0G1 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of AND Gate FA0G2 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of AND Gate FA0G3 in circuit adder_4bit: (460,260) -> (4900,260)<br> - Wire crosses reserved area of Pin S1 in circuit adder_4bit: (690,140) -> (4640,140)<br> - Wire crosses reserved area of Pin Cout in circuit adder_4bit: (690,310) -> (4610,310) |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 9 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\adder_4bit.circ | true |
