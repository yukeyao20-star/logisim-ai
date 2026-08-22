# Generation delivery report for comparator_8bit

- Request kind: natural_language
- Request: 8 bit comparator
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\comparator_8bit.circ
- Summary: generated\reports\comparator_8bit_generation_summary.md
- Manifest: generated\reports\comparator_8bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 45
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 41
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,70) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,120) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,10) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin GT in circuit comparator_8bit: (110,110) -> (7940,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin LT in circuit comparator_8bit: (1930,210) -> (8410,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate CMPG4 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPE4 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPA5 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate CMPG5 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPE5 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPA6 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate CMPG6 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPE6 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPA7 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate CMPG7 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPE7 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPQ1 in circuit comparator_8bit: (3690,1500) -> (9490,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate CMPG4 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPE4 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPA5 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate CMPG5 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPE5 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPA6 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate CMPG6 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPE6 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPA7 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate CMPG7 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPE7 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMPQ1 in circuit comparator_8bit: (3810,1520) -> (9530,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin EQ in circuit comparator_8bit: (460,160) -> (8380,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMP1G in circuit comparator_8bit: (510,260) -> (8340,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate CMP2N in circuit comparator_8bit: (510,260) -> (8340,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMP2G in circuit comparator_8bit: (510,260) -> (8340,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate CMP3N in circuit comparator_8bit: (510,260) -> (8340,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMP3G in circuit comparator_8bit: (510,260) -> (8340,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate CMP0N in circuit comparator_8bit: (510,260) -> (8340,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMP0G in circuit comparator_8bit: (510,260) -> (8340,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate CMP1N in circuit comparator_8bit: (510,260) -> (8340,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin LT in circuit comparator_8bit: (590,190) -> (7950,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate CMP0N in circuit comparator_8bit: (610,-70) -> (610,650)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin EQ in circuit comparator_8bit: (690,140) -> (7990,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMP0G in circuit comparator_8bit: (710,-130) -> (710,750)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate CMP0G in circuit comparator_8bit: (760,-220) -> (760,800)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin GT in circuit comparator_8bit: (950,90) -> (8050,90)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_8bit;x1=110;y1=110;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_8bit;x1=160;y1=70;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_8bit;x1=210;y1=120;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_8bit;x1=260;y1=10;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=Pin;label=GT;anchorX=7640;anchorY=100;left=7626;top=86;right=7654;bottom=114;connectionPoints=1;x1=110;y1=110;x2=7940;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=Pin;label=LT;anchorX=7540;anchorY=200;left=7526;top=186;right=7554;bottom=214;connectionPoints=1;x1=1930;y1=210;x2=8410;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=OR Gate;label=CMPG4;anchorX=5000;anchorY=1510;left=4964;top=1481;right=5036;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPE4;anchorX=5120;anchorY=1510;left=5084;top=1481;right=5156;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPA5;anchorX=5240;anchorY=1510;left=5204;top=1481;right=5276;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=OR Gate;label=CMPG5;anchorX=5360;anchorY=1510;left=5324;top=1481;right=5396;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPE5;anchorX=5480;anchorY=1510;left=5444;top=1481;right=5516;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPA6;anchorX=5600;anchorY=1510;left=5564;top=1481;right=5636;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=OR Gate;label=CMPG6;anchorX=5720;anchorY=1510;left=5684;top=1481;right=5756;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPE6;anchorX=5840;anchorY=1510;left=5804;top=1481;right=5876;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPA7;anchorX=5960;anchorY=1510;left=5924;top=1481;right=5996;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=OR Gate;label=CMPG7;anchorX=6080;anchorY=1510;left=6044;top=1481;right=6116;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPE7;anchorX=6200;anchorY=1510;left=6164;top=1481;right=6236;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPQ1;anchorX=6320;anchorY=1510;left=6284;top=1481;right=6356;bottom=1539;connectionPoints=3;x1=3690;y1=1500;x2=9490;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=OR Gate;label=CMPG4;anchorX=5000;anchorY=1510;left=4964;top=1481;right=5036;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPE4;anchorX=5120;anchorY=1510;left=5084;top=1481;right=5156;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPA5;anchorX=5240;anchorY=1510;left=5204;top=1481;right=5276;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=OR Gate;label=CMPG5;anchorX=5360;anchorY=1510;left=5324;top=1481;right=5396;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPE5;anchorX=5480;anchorY=1510;left=5444;top=1481;right=5516;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPA6;anchorX=5600;anchorY=1510;left=5564;top=1481;right=5636;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=OR Gate;label=CMPG6;anchorX=5720;anchorY=1510;left=5684;top=1481;right=5756;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPE6;anchorX=5840;anchorY=1510;left=5804;top=1481;right=5876;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPA7;anchorX=5960;anchorY=1510;left=5924;top=1481;right=5996;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=OR Gate;label=CMPG7;anchorX=6080;anchorY=1510;left=6044;top=1481;right=6116;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPE7;anchorX=6200;anchorY=1510;left=6164;top=1481;right=6236;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMPQ1;anchorX=6320;anchorY=1510;left=6284;top=1481;right=6356;bottom=1539;connectionPoints=3;x1=3810;y1=1520;x2=9530;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=Pin;label=EQ;anchorX=7590;anchorY=150;left=7576;top=136;right=7604;bottom=164;connectionPoints=1;x1=460;y1=160;x2=8380;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMP1G;anchorX=1100;anchorY=250;left=1064;top=221;right=1136;bottom=279;connectionPoints=3;x1=510;y1=260;x2=8340;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=NOT Gate;label=CMP2N;anchorX=1340;anchorY=250;left=1319;top=234;right=1361;bottom=266;connectionPoints=2;x1=510;y1=260;x2=8340;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMP2G;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=510;y1=260;x2=8340;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=NOT Gate;label=CMP3N;anchorX=1700;anchorY=250;left=1679;top=234;right=1721;bottom=266;connectionPoints=2;x1=510;y1=260;x2=8340;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMP3G;anchorX=1820;anchorY=250;left=1784;top=221;right=1856;bottom=279;connectionPoints=3;x1=510;y1=260;x2=8340;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=NOT Gate;label=CMP0N;anchorX=620;anchorY=250;left=599;top=234;right=641;bottom=266;connectionPoints=2;x1=510;y1=260;x2=8340;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMP0G;anchorX=740;anchorY=250;left=704;top=221;right=776;bottom=279;connectionPoints=3;x1=510;y1=260;x2=8340;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=NOT Gate;label=CMP1N;anchorX=980;anchorY=250;left=959;top=234;right=1001;bottom=266;connectionPoints=2;x1=510;y1=260;x2=8340;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=Pin;label=LT;anchorX=7540;anchorY=200;left=7526;top=186;right=7554;bottom=214;connectionPoints=1;x1=590;y1=190;x2=7950;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=NOT Gate;label=CMP0N;anchorX=620;anchorY=250;left=599;top=234;right=641;bottom=266;connectionPoints=2;x1=610;y1=-70;x2=610;y2=650)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=Pin;label=EQ;anchorX=7590;anchorY=150;left=7576;top=136;right=7604;bottom=164;connectionPoints=1;x1=690;y1=140;x2=7990;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMP0G;anchorX=740;anchorY=250;left=704;top=221;right=776;bottom=279;connectionPoints=3;x1=710;y1=-130;x2=710;y2=750)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=AND Gate;label=CMP0G;anchorX=740;anchorY=250;left=704;top=221;right=776;bottom=279;connectionPoints=3;x1=760;y1=-220;x2=760;y2=800)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_8bit;component=Pin;label=GT;anchorX=7640;anchorY=100;left=7626;top=86;right=7654;bottom=114;connectionPoints=1;x1=950;y1=90;x2=8050;y2=90)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\comparator_8bit.circ | true |
| manifest_json | generated\reports\comparator_8bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\comparator_8bit_circuit_manifest.md | true |
| delivery_json | generated\reports\comparator_8bit_delivery_report.json | true |
| delivery_markdown | generated\reports\comparator_8bit_delivery_report.md | true |
| summary_json | generated\reports\comparator_8bit_generation_summary.json | true |
| summary_markdown | generated\reports\comparator_8bit_generation_summary.md | true |
| artifact | generated\reports\comparator_8bit_multichip.md | true |
