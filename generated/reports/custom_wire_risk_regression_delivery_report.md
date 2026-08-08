# Generation delivery report for summary_regression

- Request kind: natural_language
- Request: synthetic wire risk non-auto-repair regression
- Intent: GATE_LEVEL_CIRCUIT
- Circuit: generated\circ\custom_wire_risk_regression.circ
- Summary: generated\reports\custom_summary_regression_generation_summary.md
- Manifest: generated\reports\custom_summary_regression_circuit_manifest.md
- Complete: true
- Manifest check count: 3
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 2
- Layout score: 40
- Layout risk level: medium
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 2
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 0
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (100,100) -> (100,180)
- [ERROR/WIRE_RISK] Wire is diagonal in circuit main: (300,100) -> (340,140)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=main;x1=100;y1=100;x2=100;y2=180)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=diagonal;circuit=main;x1=300;y1=100;x2=340;y2=140)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\custom_summary_regression.circ | true |
| circuit | generated\circ\custom_wire_risk_regression.circ | true |
| manifest_json | generated\reports\custom_summary_regression_circuit_manifest.json | true |
| manifest_markdown | generated\reports\custom_summary_regression_circuit_manifest.md | true |
| summary_json | generated\reports\custom_summary_regression_generation_summary.json | false |
| summary_markdown | generated\reports\custom_summary_regression_generation_summary.md | false |
| delivery_json | generated\reports\custom_wire_risk_regression_delivery_report.json | true |
| delivery_markdown | generated\reports\custom_wire_risk_regression_delivery_report.md | true |
