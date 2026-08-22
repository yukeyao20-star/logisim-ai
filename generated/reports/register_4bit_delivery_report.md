# Generation delivery report for register_4bit

- Request kind: natural_language
- Request: 锁存器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\register_4bit.circ
- Summary: generated\reports\register_4bit_generation_summary.md
- Manifest: generated\reports\register_4bit_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 9
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 5
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,160) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,140) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,110) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit register_4bit: (1010,90) -> (2310,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit register_4bit: (160,160) -> (2140,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit register_4bit: (210,140) -> (2220,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit register_4bit: (260,110) -> (2300,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit register_4bit: (490,210) -> (2150,210)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=register_4bit;x1=110;y1=80;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=register_4bit;x1=160;y1=160;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=register_4bit;x1=210;y1=140;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=register_4bit;x1=260;y1=110;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=register_4bit;component=Pin;label=Q0;anchorX=1840;anchorY=100;left=1826;top=86;right=1854;bottom=114;connectionPoints=1;x1=1010;y1=90;x2=2310;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=register_4bit;component=Pin;label=Q1;anchorX=1790;anchorY=150;left=1776;top=136;right=1804;bottom=164;connectionPoints=1;x1=160;y1=160;x2=2140;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=register_4bit;component=Pin;label=Q1;anchorX=1790;anchorY=150;left=1776;top=136;right=1804;bottom=164;connectionPoints=1;x1=210;y1=140;x2=2220;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=register_4bit;component=Pin;label=Q0;anchorX=1840;anchorY=100;left=1826;top=86;right=1854;bottom=114;connectionPoints=1;x1=260;y1=110;x2=2300;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=register_4bit;component=Pin;label=Q2;anchorX=1740;anchorY=200;left=1726;top=186;right=1754;bottom=214;connectionPoints=1;x1=490;y1=210;x2=2150;y2=210)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\register_4bit.circ | true |
| manifest_json | generated\reports\register_4bit_circuit_manifest.json | false |
| manifest_markdown | generated\reports\register_4bit_circuit_manifest.md | false |
| delivery_json | generated\reports\register_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\register_4bit_delivery_report.md | true |
| summary_json | generated\reports\register_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\register_4bit_generation_summary.md | true |
| artifact | generated\reports\register_4bit_multichip.md | true |
