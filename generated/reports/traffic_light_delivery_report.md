# Generation delivery report for traffic_light

- Request kind: natural_language
- Request: 交通灯控制
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\traffic_light.circ
- Summary: generated\reports\traffic_light_generation_summary.md
- Manifest: generated\reports\traffic_light_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 8
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 2
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 6
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,90) -> (160,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin RED in circuit traffic_light: (110,110) -> (2260,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin RED in circuit traffic_light: (160,90) -> (2340,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin GREEN in circuit traffic_light: (490,210) -> (2270,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin GREEN in circuit traffic_light: (550,190) -> (2280,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin YELLOW in circuit traffic_light: (780,160) -> (2370,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin YELLOW in circuit traffic_light: (810,140) -> (2380,140)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=traffic_light;x1=110;y1=110;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=traffic_light;x1=160;y1=90;x2=160;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=traffic_light;component=Pin;label=RED;anchorX=1960;anchorY=100;left=1946;top=86;right=1974;bottom=114;connectionPoints=1;x1=110;y1=110;x2=2260;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=traffic_light;component=Pin;label=RED;anchorX=1960;anchorY=100;left=1946;top=86;right=1974;bottom=114;connectionPoints=1;x1=160;y1=90;x2=2340;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=traffic_light;component=Pin;label=GREEN;anchorX=1860;anchorY=200;left=1846;top=186;right=1874;bottom=214;connectionPoints=1;x1=490;y1=210;x2=2270;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=traffic_light;component=Pin;label=GREEN;anchorX=1860;anchorY=200;left=1846;top=186;right=1874;bottom=214;connectionPoints=1;x1=550;y1=190;x2=2280;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=traffic_light;component=Pin;label=YELLOW;anchorX=1910;anchorY=150;left=1896;top=136;right=1924;bottom=164;connectionPoints=1;x1=780;y1=160;x2=2370;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=traffic_light;component=Pin;label=YELLOW;anchorX=1910;anchorY=150;left=1896;top=136;right=1924;bottom=164;connectionPoints=1;x1=810;y1=140;x2=2380;y2=140)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\traffic_light.circ | true |
| manifest_json | generated\reports\traffic_light_circuit_manifest.json | false |
| manifest_markdown | generated\reports\traffic_light_circuit_manifest.md | false |
| delivery_json | generated\reports\traffic_light_delivery_report.json | true |
| delivery_markdown | generated\reports\traffic_light_delivery_report.md | true |
| summary_json | generated\reports\traffic_light_generation_summary.json | true |
| summary_markdown | generated\reports\traffic_light_generation_summary.md | true |
| artifact | generated\reports\traffic_light_multichip.md | true |
