# Generation delivery report for excess3tobcd

- Request kind: natural_language
- Request: 余3码转8421
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\excess3tobcd.circ
- Summary: generated\reports\excess3tobcd_generation_summary.md
- Manifest: generated\reports\excess3tobcd_circuit_manifest.md
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

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,70) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,50) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,-40) -> (210,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D4 in circuit excess3tobcd: (470,190) -> (2390,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D2 in circuit excess3tobcd: (600,140) -> (2430,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D1 in circuit excess3tobcd: (770,110) -> (2490,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D1 in circuit excess3tobcd: (950,90) -> (2530,90)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=excess3tobcd;x1=110;y1=70;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=excess3tobcd;x1=160;y1=50;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=excess3tobcd;x1=210;y1=-40;x2=210;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3tobcd;component=Pin;label=D4;anchorX=1980;anchorY=200;left=1966;top=186;right=1994;bottom=214;connectionPoints=1;x1=470;y1=190;x2=2390;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3tobcd;component=Pin;label=D2;anchorX=2030;anchorY=150;left=2016;top=136;right=2044;bottom=164;connectionPoints=1;x1=600;y1=140;x2=2430;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3tobcd;component=Pin;label=D1;anchorX=2080;anchorY=100;left=2066;top=86;right=2094;bottom=114;connectionPoints=1;x1=770;y1=110;x2=2490;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3tobcd;component=Pin;label=D1;anchorX=2080;anchorY=100;left=2066;top=86;right=2094;bottom=114;connectionPoints=1;x1=950;y1=90;x2=2530;y2=90)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\excess3tobcd.circ | true |
| manifest_json | generated\reports\excess3tobcd_circuit_manifest.json | false |
| manifest_markdown | generated\reports\excess3tobcd_circuit_manifest.md | false |
| delivery_json | generated\reports\excess3tobcd_delivery_report.json | true |
| delivery_markdown | generated\reports\excess3tobcd_delivery_report.md | true |
| summary_json | generated\reports\excess3tobcd_generation_summary.json | true |
| summary_markdown | generated\reports\excess3tobcd_generation_summary.md | true |
| artifact | generated\reports\excess3tobcd_multichip.md | true |
