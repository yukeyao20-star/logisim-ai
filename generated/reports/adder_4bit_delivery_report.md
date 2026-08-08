# Generation delivery report for adder_4bit

- Request kind: natural_language
- Request: 四位加法器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\adder_4bit.circ
- Summary: generated\reports\adder_4bit_generation_summary.md
- Manifest: generated\reports\adder_4bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 42
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 38
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,110) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,90) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,160) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G2 in circuit adder_4bit: (1000,50) -> (1000,710)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G2 in circuit adder_4bit: (1010,40) -> (1010,710)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G3 in circuit adder_4bit: (1120,220) -> (1120,740)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G3 in circuit adder_4bit: (1150,-10) -> (1150,740)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G3 in circuit adder_4bit: (1170,260) -> (1170,750)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O1 in circuit adder_4bit: (1270,-80) -> (1270,780)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O1 in circuit adder_4bit: (1280,-70) -> (1280,790)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O1 in circuit adder_4bit: (1310,-90) -> (1310,800)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O1 in circuit adder_4bit: (1330,-100) -> (1330,810)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O2 in circuit adder_4bit: (1430,-260) -> (1430,840)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O2 in circuit adder_4bit: (1440,-170) -> (1440,850)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O2 in circuit adder_4bit: (1450,-200) -> (1450,850)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O2 in circuit adder_4bit: (1470,-210) -> (1470,860)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O2 in circuit adder_4bit: (1490,-220) -> (1490,870)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X1 in circuit adder_4bit: (1590,-290) -> (1590,900)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X1 in circuit adder_4bit: (1600,-300) -> (1600,910)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X1 in circuit adder_4bit: (1610,-330) -> (1610,910)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X1 in circuit adder_4bit: (1630,-310) -> (1630,920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X1 in circuit adder_4bit: (1640,-320) -> (1640,920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X1 in circuit adder_4bit: (1650,-340) -> (1650,930)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X2 in circuit adder_4bit: (1750,-390) -> (1750,960)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X2 in circuit adder_4bit: (1760,-400) -> (1760,970)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X2 in circuit adder_4bit: (1770,-410) -> (1770,970)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X2 in circuit adder_4bit: (1790,-420) -> (1790,980)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X2 in circuit adder_4bit: (1800,-430) -> (1800,980)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X2 in circuit adder_4bit: (1810,-440) -> (1810,990)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA1G1 in circuit adder_4bit: (1910,-510) -> (1910,1030)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G3 in circuit adder_4bit: (460,260) -> (1170,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA0X1 in circuit adder_4bit: (460,260) -> (1170,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA0X2 in circuit adder_4bit: (460,260) -> (1170,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G1 in circuit adder_4bit: (460,260) -> (1170,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G2 in circuit adder_4bit: (460,260) -> (1170,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G1 in circuit adder_4bit: (800,60) -> (800,670)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G1 in circuit adder_4bit: (810,180) -> (810,670)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G1 in circuit adder_4bit: (830,170) -> (830,670)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G1 in circuit adder_4bit: (850,80) -> (850,680)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G2 in circuit adder_4bit: (960,70) -> (960,700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G2 in circuit adder_4bit: (990,160) -> (990,710)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=adder_4bit;x1=110;y1=80;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=adder_4bit;x1=160;y1=110;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=adder_4bit;x1=210;y1=90;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=adder_4bit;x1=260;y1=160;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G2;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=1000;y1=50;x2=1000;y2=710)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G2;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=1010;y1=40;x2=1010;y2=710)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G3;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=1120;y1=220;x2=1120;y2=740)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G3;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=1150;y1=-10;x2=1150;y2=740)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G3;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=1170;y1=260;x2=1170;y2=750)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=OR Gate;label=FA0O1;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=1270;y1=-80;x2=1270;y2=780)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=OR Gate;label=FA0O1;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=1280;y1=-70;x2=1280;y2=790)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=OR Gate;label=FA0O1;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=1310;y1=-90;x2=1310;y2=800)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=OR Gate;label=FA0O1;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=1330;y1=-100;x2=1330;y2=810)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=OR Gate;label=FA0O2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=1430;y1=-260;x2=1430;y2=840)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=OR Gate;label=FA0O2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=1440;y1=-170;x2=1440;y2=850)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=OR Gate;label=FA0O2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=1450;y1=-200;x2=1450;y2=850)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=OR Gate;label=FA0O2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=1470;y1=-210;x2=1470;y2=860)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=OR Gate;label=FA0O2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=1490;y1=-220;x2=1490;y2=870)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=1590;y1=-290;x2=1590;y2=900)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=1600;y1=-300;x2=1600;y2=910)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=1610;y1=-330;x2=1610;y2=910)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=1630;y1=-310;x2=1630;y2=920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=1640;y1=-320;x2=1640;y2=920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=1650;y1=-340;x2=1650;y2=930)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X2;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=1750;y1=-390;x2=1750;y2=960)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X2;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=1760;y1=-400;x2=1760;y2=970)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X2;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=1770;y1=-410;x2=1770;y2=970)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X2;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=1790;y1=-420;x2=1790;y2=980)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X2;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=1800;y1=-430;x2=1800;y2=980)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA1X2;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=1810;y1=-440;x2=1810;y2=990)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA1G1;anchorX=1940;anchorY=250;left=1904;top=221;right=1976;bottom=279;connectionPoints=3;x1=1910;y1=-510;x2=1910;y2=1030)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G3;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=460;y1=260;x2=1170;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA0X1;anchorX=500;anchorY=250;left=464;top=221;right=536;bottom=279;connectionPoints=3;x1=460;y1=260;x2=1170;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=XOR Gate;label=FA0X2;anchorX=660;anchorY=250;left=624;top=221;right=696;bottom=279;connectionPoints=3;x1=460;y1=260;x2=1170;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G1;anchorX=820;anchorY=250;left=784;top=221;right=856;bottom=279;connectionPoints=3;x1=460;y1=260;x2=1170;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G2;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=460;y1=260;x2=1170;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G1;anchorX=820;anchorY=250;left=784;top=221;right=856;bottom=279;connectionPoints=3;x1=800;y1=60;x2=800;y2=670)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G1;anchorX=820;anchorY=250;left=784;top=221;right=856;bottom=279;connectionPoints=3;x1=810;y1=180;x2=810;y2=670)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G1;anchorX=820;anchorY=250;left=784;top=221;right=856;bottom=279;connectionPoints=3;x1=830;y1=170;x2=830;y2=670)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G1;anchorX=820;anchorY=250;left=784;top=221;right=856;bottom=279;connectionPoints=3;x1=850;y1=80;x2=850;y2=680)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G2;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=960;y1=70;x2=960;y2=700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_4bit;component=AND Gate;label=FA0G2;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=990;y1=160;x2=990;y2=710)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\adder_4bit.circ | true |
| manifest_json | generated\reports\adder_4bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\adder_4bit_circuit_manifest.md | true |
| delivery_json | generated\reports\adder_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\adder_4bit_delivery_report.md | true |
| summary_json | generated\reports\adder_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\adder_4bit_generation_summary.md | true |
| artifact | generated\reports\adder_4bit_multichip.md | true |
