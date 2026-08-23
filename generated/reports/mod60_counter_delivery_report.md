# Generation delivery report for mod60_counter

- Request kind: natural_language
- Request: 六十分频
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\mod60_counter.circ
- Summary: generated\reports\mod60_counter_generation_summary.md
- Manifest: generated\reports\mod60_counter_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 13
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 1
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

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-510) -> (110,150)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit mod60_counter: (2060,190) -> (5330,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit mod60_counter: (2250,160) -> (5340,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit mod60_counter: (2300,140) -> (5370,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit mod60_counter: (2490,110) -> (5400,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit mod60_counter: (2540,90) -> (5410,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin TC in circuit mod60_counter: (3390,410) -> (5520,410)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin TC in circuit mod60_counter: (3510,390) -> (5530,390)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q5 in circuit mod60_counter: (3560,340) -> (5580,340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q5 in circuit mod60_counter: (3800,360) -> (5560,360)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q3 in circuit mod60_counter: (3870,240) -> (5600,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit mod60_counter: (4040,210) -> (5610,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q4 in circuit mod60_counter: (4750,290) -> (5320,290)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=mod60_counter;x1=110;y1=-510;x2=110;y2=150)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=Q2;anchorX=4900;anchorY=200;left=4886;top=186;right=4914;bottom=214;connectionPoints=1;x1=2060;y1=190;x2=5330;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=Q1;anchorX=4950;anchorY=150;left=4936;top=136;right=4964;bottom=164;connectionPoints=1;x1=2250;y1=160;x2=5340;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=Q1;anchorX=4950;anchorY=150;left=4936;top=136;right=4964;bottom=164;connectionPoints=1;x1=2300;y1=140;x2=5370;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=Q0;anchorX=5000;anchorY=100;left=4986;top=86;right=5014;bottom=114;connectionPoints=1;x1=2490;y1=110;x2=5400;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=Q0;anchorX=5000;anchorY=100;left=4986;top=86;right=5014;bottom=114;connectionPoints=1;x1=2540;y1=90;x2=5410;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=TC;anchorX=4700;anchorY=400;left=4686;top=386;right=4714;bottom=414;connectionPoints=1;x1=3390;y1=410;x2=5520;y2=410)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=TC;anchorX=4700;anchorY=400;left=4686;top=386;right=4714;bottom=414;connectionPoints=1;x1=3510;y1=390;x2=5530;y2=390)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=Q5;anchorX=4750;anchorY=350;left=4736;top=336;right=4764;bottom=364;connectionPoints=1;x1=3560;y1=340;x2=5580;y2=340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=Q5;anchorX=4750;anchorY=350;left=4736;top=336;right=4764;bottom=364;connectionPoints=1;x1=3800;y1=360;x2=5560;y2=360)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=Q3;anchorX=4850;anchorY=250;left=4836;top=236;right=4864;bottom=264;connectionPoints=1;x1=3870;y1=240;x2=5600;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=Q2;anchorX=4900;anchorY=200;left=4886;top=186;right=4914;bottom=214;connectionPoints=1;x1=4040;y1=210;x2=5610;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mod60_counter;component=Pin;label=Q4;anchorX=4800;anchorY=300;left=4786;top=286;right=4814;bottom=314;connectionPoints=1;x1=4750;y1=290;x2=5320;y2=290)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mod60_counter.circ | true |
| manifest_json | generated\reports\mod60_counter_circuit_manifest.json | true |
| manifest_markdown | generated\reports\mod60_counter_circuit_manifest.md | true |
| delivery_json | generated\reports\mod60_counter_delivery_report.json | true |
| delivery_markdown | generated\reports\mod60_counter_delivery_report.md | true |
| summary_json | generated\reports\mod60_counter_generation_summary.json | true |
| summary_markdown | generated\reports\mod60_counter_generation_summary.md | true |
| artifact | generated\reports\mod60_counter_multichip.md | true |
