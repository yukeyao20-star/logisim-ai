# Circuit artifact manifest for adder_8bit

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=44451 |
| round_trip_loadable | true | [OK] adder_8bit.circ round-trip loaded with main circuit adder_8bit |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] adder_8bit.circ structure matches CircuitSpec |
| layout_clean | true | [OK] adder_8bit.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 17 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\adder_8bit.circ | true |
