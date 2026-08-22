# Generation delivery report for encoder_8to3

- Request kind: natural_language
- Request: 8 to 3 priority encoder
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\encoder_8to3.circ
- Summary: generated\reports\encoder_8to3_generation_summary.md
- Manifest: generated\reports\encoder_8to3_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 12
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 8
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 12

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-420) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,-140) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,20) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,-100) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y2 in circuit encoder_8to3: (1050,90) -> (4790,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G in circuit encoder_8to3: (2790,260) -> (5110,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G in circuit encoder_8to3: (2930,240) -> (5150,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit encoder_8to3: (500,210) -> (4700,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit encoder_8to3: (620,190) -> (4720,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit encoder_8to3: (690,160) -> (4730,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit encoder_8to3: (810,140) -> (4750,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y2 in circuit encoder_8to3: (980,110) -> (4780,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=encoder_8to3;x1=110;y1=-420;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=encoder_8to3;x1=160;y1=-140;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=encoder_8to3;x1=210;y1=20;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=encoder_8to3;x1=260;y1=-100;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=Y2;anchorX=4400;anchorY=100;left=4386;top=86;right=4414;bottom=114;connectionPoints=1;x1=1050;y1=90;x2=4790;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=G;anchorX=4250;anchorY=250;left=4236;top=236;right=4264;bottom=264;connectionPoints=1;x1=2790;y1=260;x2=5110;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=G;anchorX=4250;anchorY=250;left=4236;top=236;right=4264;bottom=264;connectionPoints=1;x1=2930;y1=240;x2=5150;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=Y0;anchorX=4300;anchorY=200;left=4286;top=186;right=4314;bottom=214;connectionPoints=1;x1=500;y1=210;x2=4700;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=Y0;anchorX=4300;anchorY=200;left=4286;top=186;right=4314;bottom=214;connectionPoints=1;x1=620;y1=190;x2=4720;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=Y1;anchorX=4350;anchorY=150;left=4336;top=136;right=4364;bottom=164;connectionPoints=1;x1=690;y1=160;x2=4730;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=Y1;anchorX=4350;anchorY=150;left=4336;top=136;right=4364;bottom=164;connectionPoints=1;x1=810;y1=140;x2=4750;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=Y2;anchorX=4400;anchorY=100;left=4386;top=86;right=4414;bottom=114;connectionPoints=1;x1=980;y1=110;x2=4780;y2=110)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\encoder_8to3.circ | true |
| manifest_json | generated\reports\encoder_8to3_circuit_manifest.json | true |
| manifest_markdown | generated\reports\encoder_8to3_circuit_manifest.md | true |
| truth_table_json | generated\reports\encoder_8to3_circuit_truth_table.json | true |
| truth_table_markdown | generated\reports\encoder_8to3_circuit_truth_table.md | true |
| delivery_json | generated\reports\encoder_8to3_delivery_report.json | true |
| delivery_markdown | generated\reports\encoder_8to3_delivery_report.md | true |
| summary_json | generated\reports\encoder_8to3_generation_summary.json | true |
| summary_markdown | generated\reports\encoder_8to3_generation_summary.md | true |
| artifact | generated\reports\encoder_8to3_multichip.md | true |
| artifact | generated\reports\encoder_8to3_official_simulation.json | true |
| artifact | generated\reports\encoder_8to3_official_simulation.md | true |
