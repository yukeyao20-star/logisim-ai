# Generation delivery report for excess3_bcd

- Request kind: natural_language
- Request: 余三码转换器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\excess3_bcd.circ
- Summary: generated\reports\excess3_bcd_generation_summary.md
- Manifest: generated\reports\excess3_bcd_circuit_manifest.md
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

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,30) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,0) -> (210,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin E1 in circuit excess3_bcd: (110,110) -> (2220,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin E2 in circuit excess3_bcd: (630,160) -> (2270,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin E2 in circuit excess3_bcd: (660,140) -> (2280,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin E1 in circuit excess3_bcd: (930,90) -> (2350,90)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=excess3_bcd;x1=110;y1=110;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=excess3_bcd;x1=160;y1=30;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=excess3_bcd;x1=210;y1=0;x2=210;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_bcd;component=Pin;label=E1;anchorX=1920;anchorY=100;left=1906;top=86;right=1934;bottom=114;connectionPoints=1;x1=110;y1=110;x2=2220;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_bcd;component=Pin;label=E2;anchorX=1870;anchorY=150;left=1856;top=136;right=1884;bottom=164;connectionPoints=1;x1=630;y1=160;x2=2270;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_bcd;component=Pin;label=E2;anchorX=1870;anchorY=150;left=1856;top=136;right=1884;bottom=164;connectionPoints=1;x1=660;y1=140;x2=2280;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_bcd;component=Pin;label=E1;anchorX=1920;anchorY=100;left=1906;top=86;right=1934;bottom=114;connectionPoints=1;x1=930;y1=90;x2=2350;y2=90)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\excess3_bcd.circ | true |
| manifest_json | generated\reports\excess3_bcd_circuit_manifest.json | false |
| manifest_markdown | generated\reports\excess3_bcd_circuit_manifest.md | false |
| delivery_json | generated\reports\excess3_bcd_delivery_report.json | true |
| delivery_markdown | generated\reports\excess3_bcd_delivery_report.md | true |
| summary_json | generated\reports\excess3_bcd_generation_summary.json | true |
| summary_markdown | generated\reports\excess3_bcd_generation_summary.md | true |
| artifact | generated\reports\excess3_bcd_multichip.md | true |
