# Generation delivery report for tristate_buffer

- Request kind: natural_language
- Request: 三态缓冲器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\tristate_buffer.circ
- Summary: generated\reports\tristate_buffer_generation_summary.md
- Manifest: generated\reports\tristate_buffer_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 2
- Layout score: 50
- Layout risk level: medium
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 1
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 1
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y in circuit tristate_buffer: (110,110) -> (1360,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=tristate_buffer;x1=110;y1=110;x2=110;y2=150)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=tristate_buffer;component=Pin;label=Y;anchorX=1060;anchorY=100;left=1046;top=86;right=1074;bottom=114;connectionPoints=1;x1=110;y1=110;x2=1360;y2=110)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\tristate_buffer.circ | true |
| manifest_json | generated\reports\tristate_buffer_circuit_manifest.json | false |
| manifest_markdown | generated\reports\tristate_buffer_circuit_manifest.md | false |
| delivery_json | generated\reports\tristate_buffer_delivery_report.json | true |
| delivery_markdown | generated\reports\tristate_buffer_delivery_report.md | true |
| summary_json | generated\reports\tristate_buffer_generation_summary.json | true |
| summary_markdown | generated\reports\tristate_buffer_generation_summary.md | true |
| artifact | generated\reports\tristate_buffer_multichip.md | true |
