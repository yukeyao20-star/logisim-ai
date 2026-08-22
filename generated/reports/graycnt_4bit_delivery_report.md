# Generation delivery report for graycnt_4bit

- Request kind: natural_language
- Request: 4位格雷码计数器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\graycnt_4bit.circ
- Summary: generated\reports\graycnt_4bit_generation_summary.md
- Manifest: generated\reports\graycnt_4bit_circuit_manifest.md
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
- Artifact count: 9

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,40) -> (110,150)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G3 in circuit graycnt_4bit: (1270,390) -> (3400,390)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G0 in circuit graycnt_4bit: (1330,90) -> (3460,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q3 in circuit graycnt_4bit: (1730,440) -> (3760,440)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G3 in circuit graycnt_4bit: (1840,410) -> (3560,410)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q3 in circuit graycnt_4bit: (2200,460) -> (3770,460)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit graycnt_4bit: (2560,340) -> (3660,340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit graycnt_4bit: (2630,240) -> (3500,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G1 in circuit graycnt_4bit: (2680,210) -> (3490,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit graycnt_4bit: (490,160) -> (3350,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit graycnt_4bit: (490,360) -> (3370,360)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G1 in circuit graycnt_4bit: (520,190) -> (3420,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit graycnt_4bit: (780,140) -> (3430,140)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=graycnt_4bit;x1=110;y1=40;x2=110;y2=150)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=G3;anchorX=2680;anchorY=400;left=2666;top=386;right=2694;bottom=414;connectionPoints=1;x1=1270;y1=390;x2=3400;y2=390)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=G0;anchorX=2980;anchorY=100;left=2966;top=86;right=2994;bottom=114;connectionPoints=1;x1=1330;y1=90;x2=3460;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=Q3;anchorX=2630;anchorY=450;left=2616;top=436;right=2644;bottom=464;connectionPoints=1;x1=1730;y1=440;x2=3760;y2=440)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=G3;anchorX=2680;anchorY=400;left=2666;top=386;right=2694;bottom=414;connectionPoints=1;x1=1840;y1=410;x2=3560;y2=410)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=Q3;anchorX=2630;anchorY=450;left=2616;top=436;right=2644;bottom=464;connectionPoints=1;x1=2200;y1=460;x2=3770;y2=460)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=Q2;anchorX=2730;anchorY=350;left=2716;top=336;right=2744;bottom=364;connectionPoints=1;x1=2560;y1=340;x2=3660;y2=340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=Q1;anchorX=2830;anchorY=250;left=2816;top=236;right=2844;bottom=264;connectionPoints=1;x1=2630;y1=240;x2=3500;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=G1;anchorX=2880;anchorY=200;left=2866;top=186;right=2894;bottom=214;connectionPoints=1;x1=2680;y1=210;x2=3490;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=Q0;anchorX=2930;anchorY=150;left=2916;top=136;right=2944;bottom=164;connectionPoints=1;x1=490;y1=160;x2=3350;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=Q2;anchorX=2730;anchorY=350;left=2716;top=336;right=2744;bottom=364;connectionPoints=1;x1=490;y1=360;x2=3370;y2=360)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=G1;anchorX=2880;anchorY=200;left=2866;top=186;right=2894;bottom=214;connectionPoints=1;x1=520;y1=190;x2=3420;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=graycnt_4bit;component=Pin;label=Q0;anchorX=2930;anchorY=150;left=2916;top=136;right=2944;bottom=164;connectionPoints=1;x1=780;y1=140;x2=3430;y2=140)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\graycnt_4bit.circ | true |
| manifest_json | generated\reports\graycnt_4bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\graycnt_4bit_circuit_manifest.md | true |
| delivery_json | generated\reports\graycnt_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\graycnt_4bit_delivery_report.md | true |
| summary_json | generated\reports\graycnt_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\graycnt_4bit_generation_summary.md | true |
| artifact | generated\reports\graycnt_4bit_multichip.md | true |
| artifact | generated\reports\graycnt_4bit_test_multichip.md | true |
