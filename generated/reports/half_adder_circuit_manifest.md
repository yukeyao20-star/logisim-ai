# Circuit artifact manifest for half_adder

- Artifact count: 1
- Check count: 5
- Complete: true

## Checks

| Check | Passed | Detail |
| --- | --- | --- |
| circuit_exists | true | circuit file exists |
| circuit_non_empty | true | bytes=4280 |
| round_trip_loadable | true | [OK] from_json_half_adder.circ round-trip loaded with main circuit half_adder |
| semantic_valid | true | [OK] CircuitSpec semantic validation passed |
| layout_clean | true | [OK] from_json_half_adder.circ static layout checks passed |

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\from_json_half_adder.circ | true |
