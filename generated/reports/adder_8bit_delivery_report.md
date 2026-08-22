# Generation delivery report for adder_8bit

- Request kind: natural_language
- Request: 8 bit adder
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\adder_8bit.circ
- Summary: generated\reports\adder_8bit_generation_summary.md
- Manifest: generated\reports\adder_8bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 69
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 65
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,110) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,90) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,160) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S5 in circuit adder_8bit: (1560,360) -> (10990,360)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit adder_8bit: (160,110) -> (10940,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S5 in circuit adder_8bit: (1890,340) -> (10970,340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit adder_8bit: (210,90) -> (10980,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit adder_8bit: (260,160) -> (11020,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit adder_8bit: (2880,490) -> (11030,490)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA3G2 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA3G3 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA3O1 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA3O2 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA4X1 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA4X2 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA4G1 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA4G2 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA4G3 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA4O1 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA4O2 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA5X1 in circuit adder_8bit: (3370,1080) -> (11840,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit adder_8bit: (360,210) -> (11100,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA3G2 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA3G3 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA3O1 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA3O2 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA4X1 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA4X2 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA4G1 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA4G2 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA4G3 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA4O1 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA4O2 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA5X1 in circuit adder_8bit: (3690,1100) -> (11930,1100)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S7 in circuit adder_8bit: (4000,440) -> (11110,440)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G3 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O1 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O2 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X1 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X2 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA1G1 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA1G2 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA1G3 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA0X1 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA0X2 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G1 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G2 in circuit adder_8bit: (460,260) -> (11180,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit adder_8bit: (4850,190) -> (12010,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S4 in circuit adder_8bit: (560,290) -> (11260,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G3 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O1 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate FA0O2 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X1 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA1X2 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA1G1 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA1G2 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA1G3 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate FA0X2 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G1 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate FA0G2 in circuit adder_8bit: (610,240) -> (11300,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S6 in circuit adder_8bit: (710,390) -> (11380,390)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S6 in circuit adder_8bit: (760,410) -> (11420,410)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit adder_8bit: (770,140) -> (10920,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S4 in circuit adder_8bit: (770,310) -> (10890,310)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S7 in circuit adder_8bit: (860,460) -> (11500,460)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit adder_8bit: (9460,510) -> (12700,510)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=adder_8bit;x1=110;y1=80;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=adder_8bit;x1=160;y1=110;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=adder_8bit;x1=210;y1=90;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=adder_8bit;x1=260;y1=160;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S5;anchorX=10310;anchorY=350;left=10296;top=336;right=10324;bottom=364;connectionPoints=1;x1=1560;y1=360;x2=10990;y2=360)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S0;anchorX=10560;anchorY=100;left=10546;top=86;right=10574;bottom=114;connectionPoints=1;x1=160;y1=110;x2=10940;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S5;anchorX=10310;anchorY=350;left=10296;top=336;right=10324;bottom=364;connectionPoints=1;x1=1890;y1=340;x2=10970;y2=340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S0;anchorX=10560;anchorY=100;left=10546;top=86;right=10574;bottom=114;connectionPoints=1;x1=210;y1=90;x2=10980;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S1;anchorX=10510;anchorY=150;left=10496;top=136;right=10524;bottom=164;connectionPoints=1;x1=260;y1=160;x2=11020;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=Cout;anchorX=10160;anchorY=500;left=10146;top=486;right=10174;bottom=514;connectionPoints=1;x1=2880;y1=490;x2=11030;y2=490)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA3G2;anchorX=4740;anchorY=1090;left=4704;top=1061;right=4776;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA3G3;anchorX=4900;anchorY=1090;left=4864;top=1061;right=4936;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA3O1;anchorX=5060;anchorY=1090;left=5024;top=1061;right=5096;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA3O2;anchorX=5220;anchorY=1090;left=5184;top=1061;right=5256;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA4X1;anchorX=5380;anchorY=1090;left=5344;top=1061;right=5416;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA4X2;anchorX=5540;anchorY=1090;left=5504;top=1061;right=5576;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA4G1;anchorX=5700;anchorY=1090;left=5664;top=1061;right=5736;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA4G2;anchorX=5860;anchorY=1090;left=5824;top=1061;right=5896;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA4G3;anchorX=6020;anchorY=1090;left=5984;top=1061;right=6056;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA4O1;anchorX=6180;anchorY=1090;left=6144;top=1061;right=6216;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA4O2;anchorX=6340;anchorY=1090;left=6304;top=1061;right=6376;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA5X1;anchorX=6500;anchorY=1090;left=6464;top=1061;right=6536;bottom=1119;connectionPoints=3;x1=3370;y1=1080;x2=11840;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S2;anchorX=10460;anchorY=200;left=10446;top=186;right=10474;bottom=214;connectionPoints=1;x1=360;y1=210;x2=11100;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA3G2;anchorX=4740;anchorY=1090;left=4704;top=1061;right=4776;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA3G3;anchorX=4900;anchorY=1090;left=4864;top=1061;right=4936;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA3O1;anchorX=5060;anchorY=1090;left=5024;top=1061;right=5096;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA3O2;anchorX=5220;anchorY=1090;left=5184;top=1061;right=5256;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA4X1;anchorX=5380;anchorY=1090;left=5344;top=1061;right=5416;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA4X2;anchorX=5540;anchorY=1090;left=5504;top=1061;right=5576;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA4G1;anchorX=5700;anchorY=1090;left=5664;top=1061;right=5736;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA4G2;anchorX=5860;anchorY=1090;left=5824;top=1061;right=5896;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA4G3;anchorX=6020;anchorY=1090;left=5984;top=1061;right=6056;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA4O1;anchorX=6180;anchorY=1090;left=6144;top=1061;right=6216;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA4O2;anchorX=6340;anchorY=1090;left=6304;top=1061;right=6376;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA5X1;anchorX=6500;anchorY=1090;left=6464;top=1061;right=6536;bottom=1119;connectionPoints=3;x1=3690;y1=1100;x2=11930;y2=1100)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S7;anchorX=10210;anchorY=450;left=10196;top=436;right=10224;bottom=464;connectionPoints=1;x1=4000;y1=440;x2=11110;y2=440)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S3;anchorX=10410;anchorY=250;left=10396;top=236;right=10424;bottom=264;connectionPoints=1;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA0G3;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA0O1;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA0O2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA1X1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA1X2;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA1G1;anchorX=1940;anchorY=250;left=1904;top=221;right=1976;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA1G2;anchorX=2100;anchorY=250;left=2064;top=221;right=2136;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA1G3;anchorX=2260;anchorY=250;left=2224;top=221;right=2296;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA0X1;anchorX=500;anchorY=250;left=464;top=221;right=536;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA0X2;anchorX=660;anchorY=250;left=624;top=221;right=696;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA0G1;anchorX=820;anchorY=250;left=784;top=221;right=856;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA0G2;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=460;y1=260;x2=11180;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S2;anchorX=10460;anchorY=200;left=10446;top=186;right=10474;bottom=214;connectionPoints=1;x1=4850;y1=190;x2=12010;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S4;anchorX=10360;anchorY=300;left=10346;top=286;right=10374;bottom=314;connectionPoints=1;x1=560;y1=290;x2=11260;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S3;anchorX=10410;anchorY=250;left=10396;top=236;right=10424;bottom=264;connectionPoints=1;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA0G3;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA0O1;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=OR Gate;label=FA0O2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA1X1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA1X2;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA1G1;anchorX=1940;anchorY=250;left=1904;top=221;right=1976;bottom=279;connectionPoints=3;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA1G2;anchorX=2100;anchorY=250;left=2064;top=221;right=2136;bottom=279;connectionPoints=3;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA1G3;anchorX=2260;anchorY=250;left=2224;top=221;right=2296;bottom=279;connectionPoints=3;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=XOR Gate;label=FA0X2;anchorX=660;anchorY=250;left=624;top=221;right=696;bottom=279;connectionPoints=3;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA0G1;anchorX=820;anchorY=250;left=784;top=221;right=856;bottom=279;connectionPoints=3;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=AND Gate;label=FA0G2;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=610;y1=240;x2=11300;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S6;anchorX=10260;anchorY=400;left=10246;top=386;right=10274;bottom=414;connectionPoints=1;x1=710;y1=390;x2=11380;y2=390)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S6;anchorX=10260;anchorY=400;left=10246;top=386;right=10274;bottom=414;connectionPoints=1;x1=760;y1=410;x2=11420;y2=410)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S1;anchorX=10510;anchorY=150;left=10496;top=136;right=10524;bottom=164;connectionPoints=1;x1=770;y1=140;x2=10920;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S4;anchorX=10360;anchorY=300;left=10346;top=286;right=10374;bottom=314;connectionPoints=1;x1=770;y1=310;x2=10890;y2=310)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=S7;anchorX=10210;anchorY=450;left=10196;top=436;right=10224;bottom=464;connectionPoints=1;x1=860;y1=460;x2=11500;y2=460)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=adder_8bit;component=Pin;label=Cout;anchorX=10160;anchorY=500;left=10146;top=486;right=10174;bottom=514;connectionPoints=1;x1=9460;y1=510;x2=12700;y2=510)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\adder_8bit.circ | true |
| manifest_json | generated\reports\adder_8bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\adder_8bit_circuit_manifest.md | true |
| delivery_json | generated\reports\adder_8bit_delivery_report.json | true |
| delivery_markdown | generated\reports\adder_8bit_delivery_report.md | true |
| summary_json | generated\reports\adder_8bit_generation_summary.json | true |
| summary_markdown | generated\reports\adder_8bit_generation_summary.md | true |
| artifact | generated\reports\adder_8bit_multichip.md | true |
| artifact | generated\reports\adder_8bit_official_simulation.json | true |
| artifact | generated\reports\adder_8bit_official_simulation.md | true |
