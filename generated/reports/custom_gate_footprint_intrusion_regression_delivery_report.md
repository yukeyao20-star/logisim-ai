# Generation delivery report for summary_regression

- Request kind: natural_language
- Request: synthetic gate footprint intrusion regression
- Intent: GATE_LEVEL_CIRCUIT
- Circuit: generated/circ/custom_gate_footprint_intrusion_regression.circ
- Summary: generated/reports/custom_summary_regression_generation_summary.md
- Manifest: generated/reports/custom_summary_regression_circuit_manifest.md
- Complete: true
- Manifest check count: 3
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 1
- Layout score: 80
- Layout risk level: medium
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 0
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 1
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate  in circuit main: (370,120) -> (430,120)

## Layout Repair Suggestions

- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=main;component=AND Gate;label=;anchorX=400;anchorY=120;left=364;top=91;right=436;bottom=149;connectionPoints=3;x1=370;y1=120;x2=430;y2=120)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/custom_gate_footprint_intrusion_regression.circ | true |
| circuit | generated/circ/custom_summary_regression.circ | true |
| delivery_json | generated/reports/custom_gate_footprint_intrusion_regression_delivery_report.json | true |
| delivery_markdown | generated/reports/custom_gate_footprint_intrusion_regression_delivery_report.md | true |
| manifest_json | generated/reports/custom_summary_regression_circuit_manifest.json | true |
| manifest_markdown | generated/reports/custom_summary_regression_circuit_manifest.md | true |
| summary_json | generated/reports/custom_summary_regression_generation_summary.json | false |
| summary_markdown | generated/reports/custom_summary_regression_generation_summary.md | false |
