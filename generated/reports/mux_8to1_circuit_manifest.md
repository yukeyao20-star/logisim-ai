# Circuit artifact manifest for mux_8to1

- Artifact count: 1
- Check count: 7
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=29779 |
| round_trip_loadable | true | [OK] mux_8to1.circ round-trip loaded with main circuit mux_8to1 |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| structure_matches_spec | true | [OK] mux_8to1.circ structure matches CircuitSpec |
| layout_clean | true | [OK] mux_8to1.circ static layout checks passed |
| truth_table_evidence | true | [SKIP] too many inputs for exhaustive evidence: 11 |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mux_8to1.circ | true |
