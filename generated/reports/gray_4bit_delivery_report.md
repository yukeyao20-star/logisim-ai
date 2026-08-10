# Generation delivery report for gray_4bit

- Request kind: natural_language
- Request: 4 bit gray code
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\gray_4bit.circ
- Summary: generated\reports\gray_4bit_generation_summary.md
- Manifest: generated\reports\gray_4bit_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 6
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 3
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 3
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,80) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,110) -> (210,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G0 in circuit gray_4bit: (110,90) -> (1600,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G0 in circuit gray_4bit: (210,110) -> (1720,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G1 in circuit gray_4bit: (600,140) -> (1650,140)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=gray_4bit;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=gray_4bit;x1=160;y1=80;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=gray_4bit;x1=210;y1=110;x2=210;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=gray_4bit;component=Pin;label=G0;anchorX=1280;anchorY=100;left=1266;top=86;right=1294;bottom=114;connectionPoints=1;x1=110;y1=90;x2=1600;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=gray_4bit;component=Pin;label=G0;anchorX=1280;anchorY=100;left=1266;top=86;right=1294;bottom=114;connectionPoints=1;x1=210;y1=110;x2=1720;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=gray_4bit;component=Pin;label=G1;anchorX=1230;anchorY=150;left=1216;top=136;right=1244;bottom=164;connectionPoints=1;x1=600;y1=140;x2=1650;y2=140)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\gray_4bit.circ | true |
| manifest_json | generated\reports\gray_4bit_circuit_manifest.json | false |
| manifest_markdown | generated\reports\gray_4bit_circuit_manifest.md | false |
| delivery_json | generated\reports\gray_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\gray_4bit_delivery_report.md | true |
| summary_json | generated\reports\gray_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\gray_4bit_generation_summary.md | true |
| artifact | generated\reports\gray_4bit_multichip.md | true |
