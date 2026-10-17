# Generation delivery report for mod7_counter

- Request kind: natural_language
- Request: 模7计数器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\mod7_counter.circ
- Summary: generated\reports\mod7_counter_generation_summary.md
- Manifest: generated\reports\mod7_counter_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 8
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 1
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 7
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-240) -> (110,150)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit mod7_counter: (1330,110) -> (4520,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit mod7_counter: (1510,190) -> (4430,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit mod7_counter: (1650,160) -> (4450,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit mod7_counter: (1700,140) -> (4480,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit mod7_counter: (1990,90) -> (4530,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q3 in circuit mod7_counter: (3450,240) -> (4870,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit mod7_counter: (3500,210) -> (4900,210)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=mod7_counter;x1=110;y1=-240;x2=110;y2=150)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod7_counter;component=Pin;label=Q0;anchorX=4120;anchorY=100;left=4106;top=86;right=4134;bottom=114;connectionPoints=1;x1=1330;y1=110;x2=4520;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod7_counter;component=Pin;label=Q2;anchorX=4020;anchorY=200;left=4006;top=186;right=4034;bottom=214;connectionPoints=1;x1=1510;y1=190;x2=4430;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod7_counter;component=Pin;label=Q1;anchorX=4070;anchorY=150;left=4056;top=136;right=4084;bottom=164;connectionPoints=1;x1=1650;y1=160;x2=4450;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod7_counter;component=Pin;label=Q1;anchorX=4070;anchorY=150;left=4056;top=136;right=4084;bottom=164;connectionPoints=1;x1=1700;y1=140;x2=4480;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod7_counter;component=Pin;label=Q0;anchorX=4120;anchorY=100;left=4106;top=86;right=4134;bottom=114;connectionPoints=1;x1=1990;y1=90;x2=4530;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod7_counter;component=Pin;label=Q3;anchorX=3970;anchorY=250;left=3956;top=236;right=3984;bottom=264;connectionPoints=1;x1=3450;y1=240;x2=4870;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod7_counter;component=Pin;label=Q2;anchorX=4020;anchorY=200;left=4006;top=186;right=4034;bottom=214;connectionPoints=1;x1=3500;y1=210;x2=4900;y2=210)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mod7_counter.circ | true |
| manifest_json | generated\reports\mod7_counter_circuit_manifest.json | false |
| manifest_markdown | generated\reports\mod7_counter_circuit_manifest.md | false |
| delivery_json | generated\reports\mod7_counter_delivery_report.json | true |
| delivery_markdown | generated\reports\mod7_counter_delivery_report.md | true |
| summary_json | generated\reports\mod7_counter_generation_summary.json | true |
| summary_markdown | generated\reports\mod7_counter_generation_summary.md | true |
| artifact | generated\reports\mod7_counter_multichip.md | true |
