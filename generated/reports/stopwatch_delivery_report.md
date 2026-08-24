# Generation delivery report for stopwatch

- Request kind: natural_language
- Request: 秒表
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\stopwatch.circ
- Summary: generated\reports\stopwatch_generation_summary.md
- Manifest: generated\reports\stopwatch_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 14
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 2
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 12
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-360) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,-10) -> (160,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q6 in circuit stopwatch: (1130,410) -> (6510,410)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit stopwatch: (2680,110) -> (6400,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit stopwatch: (2750,90) -> (6390,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q5 in circuit stopwatch: (3170,340) -> (6650,340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q4 in circuit stopwatch: (3460,290) -> (6690,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit stopwatch: (3700,210) -> (6750,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q3 in circuit stopwatch: (3820,240) -> (6730,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q5 in circuit stopwatch: (4840,360) -> (7080,360)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit stopwatch: (5730,140) -> (6470,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit stopwatch: (5780,160) -> (6370,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit stopwatch: (5830,190) -> (6300,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q6 in circuit stopwatch: (870,390) -> (6500,390)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=stopwatch;x1=110;y1=-360;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=stopwatch;x1=160;y1=-10;x2=160;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q6;anchorX=5680;anchorY=400;left=5666;top=386;right=5694;bottom=414;connectionPoints=1;x1=1130;y1=410;x2=6510;y2=410)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q0;anchorX=5980;anchorY=100;left=5966;top=86;right=5994;bottom=114;connectionPoints=1;x1=2680;y1=110;x2=6400;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q0;anchorX=5980;anchorY=100;left=5966;top=86;right=5994;bottom=114;connectionPoints=1;x1=2750;y1=90;x2=6390;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q5;anchorX=5730;anchorY=350;left=5716;top=336;right=5744;bottom=364;connectionPoints=1;x1=3170;y1=340;x2=6650;y2=340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q4;anchorX=5780;anchorY=300;left=5766;top=286;right=5794;bottom=314;connectionPoints=1;x1=3460;y1=290;x2=6690;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q2;anchorX=5880;anchorY=200;left=5866;top=186;right=5894;bottom=214;connectionPoints=1;x1=3700;y1=210;x2=6750;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q3;anchorX=5830;anchorY=250;left=5816;top=236;right=5844;bottom=264;connectionPoints=1;x1=3820;y1=240;x2=6730;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q5;anchorX=5730;anchorY=350;left=5716;top=336;right=5744;bottom=364;connectionPoints=1;x1=4840;y1=360;x2=7080;y2=360)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q1;anchorX=5930;anchorY=150;left=5916;top=136;right=5944;bottom=164;connectionPoints=1;x1=5730;y1=140;x2=6470;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q1;anchorX=5930;anchorY=150;left=5916;top=136;right=5944;bottom=164;connectionPoints=1;x1=5780;y1=160;x2=6370;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q2;anchorX=5880;anchorY=200;left=5866;top=186;right=5894;bottom=214;connectionPoints=1;x1=5830;y1=190;x2=6300;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=stopwatch;component=Pin;label=Q6;anchorX=5680;anchorY=400;left=5666;top=386;right=5694;bottom=414;connectionPoints=1;x1=870;y1=390;x2=6500;y2=390)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\stopwatch.circ | true |
| manifest_json | generated\reports\stopwatch_circuit_manifest.json | true |
| manifest_markdown | generated\reports\stopwatch_circuit_manifest.md | true |
| delivery_json | generated\reports\stopwatch_delivery_report.json | true |
| delivery_markdown | generated\reports\stopwatch_delivery_report.md | true |
| summary_json | generated\reports\stopwatch_generation_summary.json | true |
| summary_markdown | generated\reports\stopwatch_generation_summary.md | true |
| artifact | generated\reports\stopwatch_multichip.md | true |
