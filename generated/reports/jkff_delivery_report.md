# Generation delivery report for jkff

- Request kind: natural_language
- Request: JK触发器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\jkff.circ
- Summary: generated\reports\jkff_generation_summary.md
- Manifest: generated\reports\jkff_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 7
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 3
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 4
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,160) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,40) -> (210,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q in circuit jkff: (110,90) -> (2020,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin nQ in circuit jkff: (160,160) -> (2000,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin nQ in circuit jkff: (870,140) -> (2030,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q in circuit jkff: (920,110) -> (2100,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=jkff;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=jkff;x1=160;y1=160;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=jkff;x1=210;y1=40;x2=210;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=jkff;component=Pin;label=Q;anchorX=1700;anchorY=100;left=1686;top=86;right=1714;bottom=114;connectionPoints=1;x1=110;y1=90;x2=2020;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=jkff;component=Pin;label=nQ;anchorX=1650;anchorY=150;left=1636;top=136;right=1664;bottom=164;connectionPoints=1;x1=160;y1=160;x2=2000;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=jkff;component=Pin;label=nQ;anchorX=1650;anchorY=150;left=1636;top=136;right=1664;bottom=164;connectionPoints=1;x1=870;y1=140;x2=2030;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=jkff;component=Pin;label=Q;anchorX=1700;anchorY=100;left=1686;top=86;right=1714;bottom=114;connectionPoints=1;x1=920;y1=110;x2=2100;y2=110)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\jkff.circ | true |
| manifest_json | generated\reports\jkff_circuit_manifest.json | false |
| manifest_markdown | generated\reports\jkff_circuit_manifest.md | false |
| delivery_json | generated\reports\jkff_delivery_report.json | true |
| delivery_markdown | generated\reports\jkff_delivery_report.md | true |
| summary_json | generated\reports\jkff_generation_summary.json | true |
| summary_markdown | generated\reports\jkff_generation_summary.md | true |
| artifact | generated\reports\jkff_multichip.md | true |
