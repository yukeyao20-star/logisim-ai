# Generation delivery report for decoder_3bit

- Request kind: natural_language
- Request: 三位译码器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\decoder_3bit.circ
- Summary: generated\reports\decoder_3bit_generation_summary.md
- Manifest: generated\reports\decoder_3bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 37
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 2
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 35
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,120) -> (160,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate DECN3 in circuit decoder_3bit: (1000,-70) -> (1000,610)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate DECN3 in circuit decoder_3bit: (1010,60) -> (1010,620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y3 in circuit decoder_3bit: (1020,50) -> (1020,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate DECN3 in circuit decoder_3bit: (1030,-120) -> (1030,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y3 in circuit decoder_3bit: (1040,30) -> (1040,620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate DECN3 in circuit decoder_3bit: (1040,30) -> (1040,620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y2 in circuit decoder_3bit: (1070,20) -> (1070,640)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y2 in circuit decoder_3bit: (1090,10) -> (1090,640)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit decoder_3bit: (1120,0) -> (1120,660)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit decoder_3bit: (1140,-80) -> (1140,660)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit decoder_3bit: (1170,90) -> (1170,680)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit decoder_3bit: (1170,90) -> (1300,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_1 in circuit decoder_3bit: (1250,-40) -> (1250,710)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_1 in circuit decoder_3bit: (1260,-90) -> (1260,710)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_1 in circuit decoder_3bit: (1270,60) -> (1270,720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_1 in circuit decoder_3bit: (1290,-50) -> (1290,720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_1 in circuit decoder_3bit: (1300,90) -> (1300,730)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_2 in circuit decoder_3bit: (1510,130) -> (1510,830)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_2 in circuit decoder_3bit: (1530,-190) -> (1530,840)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_2 in circuit decoder_3bit: (1550,-200) -> (1550,840)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_2 in circuit decoder_3bit: (1560,120) -> (1560,850)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_1 in circuit decoder_3bit: (1770,120) -> (1770,950)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_1 in circuit decoder_3bit: (1790,-310) -> (1790,960)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_1 in circuit decoder_3bit: (1810,-320) -> (1810,960)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit decoder_3bit: (820,90) -> (1170,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y7 in circuit decoder_3bit: (820,90) -> (820,550)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y7 in circuit decoder_3bit: (840,180) -> (840,550)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit decoder_3bit: (870,140) -> (1150,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y6 in circuit decoder_3bit: (870,140) -> (870,570)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit decoder_3bit: (890,110) -> (1230,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y6 in circuit decoder_3bit: (890,110) -> (890,570)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y4 in circuit decoder_3bit: (910,310) -> (1230,310)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y5 in circuit decoder_3bit: (920,80) -> (920,590)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y5 in circuit decoder_3bit: (940,70) -> (940,590)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y4 in circuit decoder_3bit: (970,40) -> (970,610)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=decoder_3bit;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=decoder_3bit;x1=160;y1=120;x2=160;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=NOT Gate;label=DECN3;anchorX=1020;anchorY=250;left=999;top=234;right=1041;bottom=266;connectionPoints=2;x1=1000;y1=-70;x2=1000;y2=610)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=NOT Gate;label=DECN3;anchorX=1020;anchorY=250;left=999;top=234;right=1041;bottom=266;connectionPoints=2;x1=1010;y1=60;x2=1010;y2=620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y3;anchorX=1030;anchorY=250;left=1016;top=236;right=1044;bottom=264;connectionPoints=1;x1=1020;y1=50;x2=1020;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=NOT Gate;label=DECN3;anchorX=1020;anchorY=250;left=999;top=234;right=1041;bottom=266;connectionPoints=2;x1=1030;y1=-120;x2=1030;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y3;anchorX=1030;anchorY=250;left=1016;top=236;right=1044;bottom=264;connectionPoints=1;x1=1040;y1=30;x2=1040;y2=620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=NOT Gate;label=DECN3;anchorX=1020;anchorY=250;left=999;top=234;right=1041;bottom=266;connectionPoints=2;x1=1040;y1=30;x2=1040;y2=620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y2;anchorX=1080;anchorY=200;left=1066;top=186;right=1094;bottom=214;connectionPoints=1;x1=1070;y1=20;x2=1070;y2=640)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y2;anchorX=1080;anchorY=200;left=1066;top=186;right=1094;bottom=214;connectionPoints=1;x1=1090;y1=10;x2=1090;y2=640)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y1;anchorX=1130;anchorY=150;left=1116;top=136;right=1144;bottom=164;connectionPoints=1;x1=1120;y1=0;x2=1120;y2=660)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y1;anchorX=1130;anchorY=150;left=1116;top=136;right=1144;bottom=164;connectionPoints=1;x1=1140;y1=-80;x2=1140;y2=660)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y0;anchorX=1180;anchorY=100;left=1166;top=86;right=1194;bottom=114;connectionPoints=1;x1=1170;y1=90;x2=1170;y2=680)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y0;anchorX=1180;anchorY=100;left=1166;top=86;right=1194;bottom=114;connectionPoints=1;x1=1170;y1=90;x2=1300;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_1;anchorX=1280;anchorY=250;left=1244;top=221;right=1316;bottom=279;connectionPoints=3;x1=1250;y1=-40;x2=1250;y2=710)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_1;anchorX=1280;anchorY=250;left=1244;top=221;right=1316;bottom=279;connectionPoints=3;x1=1260;y1=-90;x2=1260;y2=710)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_1;anchorX=1280;anchorY=250;left=1244;top=221;right=1316;bottom=279;connectionPoints=3;x1=1270;y1=60;x2=1270;y2=720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_1;anchorX=1280;anchorY=250;left=1244;top=221;right=1316;bottom=279;connectionPoints=3;x1=1290;y1=-50;x2=1290;y2=720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_1;anchorX=1280;anchorY=250;left=1244;top=221;right=1316;bottom=279;connectionPoints=3;x1=1300;y1=90;x2=1300;y2=730)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_2;anchorX=1540;anchorY=250;left=1504;top=221;right=1576;bottom=279;connectionPoints=3;x1=1510;y1=130;x2=1510;y2=830)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_2;anchorX=1540;anchorY=250;left=1504;top=221;right=1576;bottom=279;connectionPoints=3;x1=1530;y1=-190;x2=1530;y2=840)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_2;anchorX=1540;anchorY=250;left=1504;top=221;right=1576;bottom=279;connectionPoints=3;x1=1550;y1=-200;x2=1550;y2=840)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_2;anchorX=1540;anchorY=250;left=1504;top=221;right=1576;bottom=279;connectionPoints=3;x1=1560;y1=120;x2=1560;y2=850)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_1;anchorX=1800;anchorY=250;left=1764;top=221;right=1836;bottom=279;connectionPoints=3;x1=1770;y1=120;x2=1770;y2=950)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_1;anchorX=1800;anchorY=250;left=1764;top=221;right=1836;bottom=279;connectionPoints=3;x1=1790;y1=-310;x2=1790;y2=960)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_1;anchorX=1800;anchorY=250;left=1764;top=221;right=1836;bottom=279;connectionPoints=3;x1=1810;y1=-320;x2=1810;y2=960)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y0;anchorX=1180;anchorY=100;left=1166;top=86;right=1194;bottom=114;connectionPoints=1;x1=820;y1=90;x2=1170;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y7;anchorX=830;anchorY=450;left=816;top=436;right=844;bottom=464;connectionPoints=1;x1=820;y1=90;x2=820;y2=550)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y7;anchorX=830;anchorY=450;left=816;top=436;right=844;bottom=464;connectionPoints=1;x1=840;y1=180;x2=840;y2=550)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y1;anchorX=1130;anchorY=150;left=1116;top=136;right=1144;bottom=164;connectionPoints=1;x1=870;y1=140;x2=1150;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y6;anchorX=880;anchorY=400;left=866;top=386;right=894;bottom=414;connectionPoints=1;x1=870;y1=140;x2=870;y2=570)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y0;anchorX=1180;anchorY=100;left=1166;top=86;right=1194;bottom=114;connectionPoints=1;x1=890;y1=110;x2=1230;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y6;anchorX=880;anchorY=400;left=866;top=386;right=894;bottom=414;connectionPoints=1;x1=890;y1=110;x2=890;y2=570)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y4;anchorX=980;anchorY=300;left=966;top=286;right=994;bottom=314;connectionPoints=1;x1=910;y1=310;x2=1230;y2=310)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y5;anchorX=930;anchorY=350;left=916;top=336;right=944;bottom=364;connectionPoints=1;x1=920;y1=80;x2=920;y2=590)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y5;anchorX=930;anchorY=350;left=916;top=336;right=944;bottom=364;connectionPoints=1;x1=940;y1=70;x2=940;y2=590)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y4;anchorX=980;anchorY=300;left=966;top=286;right=994;bottom=314;connectionPoints=1;x1=970;y1=40;x2=970;y2=610)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\decoder_3bit.circ | true |
| manifest_json | generated\reports\decoder_3bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\decoder_3bit_circuit_manifest.md | true |
| truth_table_json | generated\reports\decoder_3bit_circuit_truth_table.json | true |
| truth_table_markdown | generated\reports\decoder_3bit_circuit_truth_table.md | true |
| delivery_json | generated\reports\decoder_3bit_delivery_report.json | true |
| delivery_markdown | generated\reports\decoder_3bit_delivery_report.md | true |
| summary_json | generated\reports\decoder_3bit_generation_summary.json | true |
| summary_markdown | generated\reports\decoder_3bit_generation_summary.md | true |
| artifact | generated\reports\decoder_3bit_multichip.md | true |
