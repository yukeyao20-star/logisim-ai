# Generation delivery report for subtractor_4bit

- Request kind: natural_language
- Request: 4 bit subtractor
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\subtractor_4bit.circ
- Summary: generated\reports\subtractor_4bit_generation_summary.md
- Manifest: generated\reports\subtractor_4bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 13
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 9
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,90) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,70) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,40) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D0 in circuit subtractor_4bit: (160,90) -> (6050,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D0 in circuit subtractor_4bit: (1910,110) -> (6070,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D1 in circuit subtractor_4bit: (310,160) -> (6150,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Bout in circuit subtractor_4bit: (440,310) -> (6020,310)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Bout in circuit subtractor_4bit: (4900,290) -> (6940,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D3 in circuit subtractor_4bit: (5060,260) -> (6960,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D3 in circuit subtractor_4bit: (5220,240) -> (6980,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D2 in circuit subtractor_4bit: (790,190) -> (6000,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D1 in circuit subtractor_4bit: (930,140) -> (6030,140)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=subtractor_4bit;x1=110;y1=80;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=subtractor_4bit;x1=160;y1=90;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=subtractor_4bit;x1=210;y1=70;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=subtractor_4bit;x1=260;y1=40;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_4bit;component=Pin;label=D0;anchorX=5680;anchorY=100;left=5666;top=86;right=5694;bottom=114;connectionPoints=1;x1=160;y1=90;x2=6050;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_4bit;component=Pin;label=D0;anchorX=5680;anchorY=100;left=5666;top=86;right=5694;bottom=114;connectionPoints=1;x1=1910;y1=110;x2=6070;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_4bit;component=Pin;label=D1;anchorX=5630;anchorY=150;left=5616;top=136;right=5644;bottom=164;connectionPoints=1;x1=310;y1=160;x2=6150;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_4bit;component=Pin;label=Bout;anchorX=5480;anchorY=300;left=5466;top=286;right=5494;bottom=314;connectionPoints=1;x1=440;y1=310;x2=6020;y2=310)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_4bit;component=Pin;label=Bout;anchorX=5480;anchorY=300;left=5466;top=286;right=5494;bottom=314;connectionPoints=1;x1=4900;y1=290;x2=6940;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_4bit;component=Pin;label=D3;anchorX=5530;anchorY=250;left=5516;top=236;right=5544;bottom=264;connectionPoints=1;x1=5060;y1=260;x2=6960;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_4bit;component=Pin;label=D3;anchorX=5530;anchorY=250;left=5516;top=236;right=5544;bottom=264;connectionPoints=1;x1=5220;y1=240;x2=6980;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_4bit;component=Pin;label=D2;anchorX=5580;anchorY=200;left=5566;top=186;right=5594;bottom=214;connectionPoints=1;x1=790;y1=190;x2=6000;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_4bit;component=Pin;label=D1;anchorX=5630;anchorY=150;left=5616;top=136;right=5644;bottom=164;connectionPoints=1;x1=930;y1=140;x2=6030;y2=140)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\subtractor_4bit.circ | true |
| manifest_json | generated\reports\subtractor_4bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\subtractor_4bit_circuit_manifest.md | true |
| delivery_json | generated\reports\subtractor_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\subtractor_4bit_delivery_report.md | true |
| summary_json | generated\reports\subtractor_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\subtractor_4bit_generation_summary.md | true |
| artifact | generated\reports\subtractor_4bit_multichip.md | true |
| artifact | generated\reports\subtractor_4bit_official_simulation.json | true |
| artifact | generated\reports\subtractor_4bit_official_simulation.md | true |
