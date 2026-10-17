# Generation delivery report for bcd_adder

- Request kind: natural_language
- Request: bcd adder
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\bcd_adder.circ
- Summary: generated\reports\bcd_adder_generation_summary.md
- Manifest: generated\reports\bcd_adder_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 41
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 37
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 15

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,20) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,10) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,-100) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit bcd_adder: (110,90) -> (7480,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit bcd_adder: (1940,290) -> (7950,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit bcd_adder: (2060,260) -> (7990,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit bcd_adder: (2190,240) -> (8030,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit bcd_adder: (2240,210) -> (8100,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit bcd_adder: (2360,160) -> (8120,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit bcd_adder: (2430,190) -> (8110,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit bcd_adder: (440,310) -> (7490,310)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB27 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO27 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG27 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX28 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX38 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA28 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB28 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO28 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG28 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX29 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX39 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA29 in circuit bcd_adder: (4640,1500) -> (8850,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB27 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO27 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG27 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX28 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX38 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA28 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB28 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO28 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG28 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX29 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX39 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA29 in circuit bcd_adder: (4770,1520) -> (8890,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB29 in circuit bcd_adder: (4950,1940) -> (8930,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO29 in circuit bcd_adder: (4950,1940) -> (8930,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG29 in circuit bcd_adder: (4950,1940) -> (8930,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit bcd_adder: (560,140) -> (7510,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit bcd_adder: (740,110) -> (7620,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=160;y1=20;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=210;y1=10;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=260;y1=-100;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S0;anchorX=7160;anchorY=100;left=7146;top=86;right=7174;bottom=114;connectionPoints=1;x1=110;y1=90;x2=7480;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=Cout;anchorX=6960;anchorY=300;left=6946;top=286;right=6974;bottom=314;connectionPoints=1;x1=1940;y1=290;x2=7950;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S3;anchorX=7010;anchorY=250;left=6996;top=236;right=7024;bottom=264;connectionPoints=1;x1=2060;y1=260;x2=7990;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S3;anchorX=7010;anchorY=250;left=6996;top=236;right=7024;bottom=264;connectionPoints=1;x1=2190;y1=240;x2=8030;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S2;anchorX=7060;anchorY=200;left=7046;top=186;right=7074;bottom=214;connectionPoints=1;x1=2240;y1=210;x2=8100;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S1;anchorX=7110;anchorY=150;left=7096;top=136;right=7124;bottom=164;connectionPoints=1;x1=2360;y1=160;x2=8120;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S2;anchorX=7060;anchorY=200;left=7046;top=186;right=7074;bottom=214;connectionPoints=1;x1=2430;y1=190;x2=8110;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=Cout;anchorX=6960;anchorY=300;left=6946;top=286;right=6974;bottom=314;connectionPoints=1;x1=440;y1=310;x2=7490;y2=310)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB27;anchorX=5000;anchorY=1510;left=4964;top=1481;right=5036;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO27;anchorX=5120;anchorY=1510;left=5084;top=1481;right=5156;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG27;anchorX=5240;anchorY=1510;left=5204;top=1481;right=5276;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX28;anchorX=5360;anchorY=1510;left=5324;top=1481;right=5396;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX38;anchorX=5480;anchorY=1510;left=5444;top=1481;right=5516;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA28;anchorX=5600;anchorY=1510;left=5564;top=1481;right=5636;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB28;anchorX=5720;anchorY=1510;left=5684;top=1481;right=5756;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO28;anchorX=5840;anchorY=1510;left=5804;top=1481;right=5876;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG28;anchorX=5960;anchorY=1510;left=5924;top=1481;right=5996;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX29;anchorX=6080;anchorY=1510;left=6044;top=1481;right=6116;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX39;anchorX=6200;anchorY=1510;left=6164;top=1481;right=6236;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA29;anchorX=6320;anchorY=1510;left=6284;top=1481;right=6356;bottom=1539;connectionPoints=3;x1=4640;y1=1500;x2=8850;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB27;anchorX=5000;anchorY=1510;left=4964;top=1481;right=5036;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO27;anchorX=5120;anchorY=1510;left=5084;top=1481;right=5156;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG27;anchorX=5240;anchorY=1510;left=5204;top=1481;right=5276;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX28;anchorX=5360;anchorY=1510;left=5324;top=1481;right=5396;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX38;anchorX=5480;anchorY=1510;left=5444;top=1481;right=5516;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA28;anchorX=5600;anchorY=1510;left=5564;top=1481;right=5636;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB28;anchorX=5720;anchorY=1510;left=5684;top=1481;right=5756;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO28;anchorX=5840;anchorY=1510;left=5804;top=1481;right=5876;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG28;anchorX=5960;anchorY=1510;left=5924;top=1481;right=5996;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX29;anchorX=6080;anchorY=1510;left=6044;top=1481;right=6116;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX39;anchorX=6200;anchorY=1510;left=6164;top=1481;right=6236;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA29;anchorX=6320;anchorY=1510;left=6284;top=1481;right=6356;bottom=1539;connectionPoints=3;x1=4770;y1=1520;x2=8890;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB29;anchorX=6500;anchorY=1930;left=6464;top=1901;right=6536;bottom=1959;connectionPoints=3;x1=4950;y1=1940;x2=8930;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO29;anchorX=6620;anchorY=1930;left=6584;top=1901;right=6656;bottom=1959;connectionPoints=3;x1=4950;y1=1940;x2=8930;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG29;anchorX=6740;anchorY=1930;left=6704;top=1901;right=6776;bottom=1959;connectionPoints=3;x1=4950;y1=1940;x2=8930;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S1;anchorX=7110;anchorY=150;left=7096;top=136;right=7124;bottom=164;connectionPoints=1;x1=560;y1=140;x2=7510;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S0;anchorX=7160;anchorY=100;left=7146;top=86;right=7174;bottom=114;connectionPoints=1;x1=740;y1=110;x2=7620;y2=110)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\bcd_adder.circ | true |
| manifest_json | generated\reports\bcd_adder_2digit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\bcd_adder_2digit_circuit_manifest.md | true |
| summary_json | generated\reports\bcd_adder_2digit_generation_summary.json | true |
| summary_markdown | generated\reports\bcd_adder_2digit_generation_summary.md | true |
| artifact | generated\reports\bcd_adder_2digit_multichip.md | true |
| manifest_json | generated\reports\bcd_adder_circuit_manifest.json | true |
| manifest_markdown | generated\reports\bcd_adder_circuit_manifest.md | true |
| delivery_json | generated\reports\bcd_adder_delivery_report.json | true |
| delivery_markdown | generated\reports\bcd_adder_delivery_report.md | true |
| summary_json | generated\reports\bcd_adder_generation_summary.json | true |
| summary_markdown | generated\reports\bcd_adder_generation_summary.md | true |
| artifact | generated\reports\bcd_adder_multichip.md | true |
| artifact | generated\reports\bcd_adder_official_simulation.json | true |
| artifact | generated\reports\bcd_adder_official_simulation.md | true |
