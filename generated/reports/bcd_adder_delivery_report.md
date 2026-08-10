# Generation delivery report for bcd_adder

- Request kind: natural_language
- Request: BCD加法器
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
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,20) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,10) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,-100) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB29 in circuit bcd_adder: (10650,1940) -> (16650,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO29 in circuit bcd_adder: (10650,1940) -> (16650,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG29 in circuit bcd_adder: (10650,1940) -> (16650,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit bcd_adder: (110,90) -> (15200,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit bcd_adder: (3840,290) -> (15670,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit bcd_adder: (4000,260) -> (15710,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit bcd_adder: (4170,240) -> (15750,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit bcd_adder: (4220,210) -> (15820,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit bcd_adder: (4380,160) -> (15840,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit bcd_adder: (440,310) -> (15210,310)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit bcd_adder: (4490,190) -> (15830,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit bcd_adder: (600,140) -> (15230,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit bcd_adder: (820,110) -> (15340,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB27 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO27 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG27 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX28 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX38 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA28 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB28 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO28 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG28 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX29 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX39 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA29 in circuit bcd_adder: (8840,1500) -> (16570,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB27 in circuit bcd_adder: (9010,1520) -> (16610,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO27 in circuit bcd_adder: (9010,1520) -> (16610,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG27 in circuit bcd_adder: (9010,1520) -> (16610,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX28 in circuit bcd_adder: (9010,1520) -> (16610,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX38 in circuit bcd_adder: (9010,1520) -> (16610,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA28 in circuit bcd_adder: (9010,1520) -> (16610,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB28 in circuit bcd_adder: (9010,1520) -> (16610,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO28 in circuit bcd_adder: (9010,1520) -> (16610,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG28 in circuit bcd_adder: (9010,1520) -> (16610,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX29 in circuit bcd_adder: (9010,1520) -> (16610,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX39 in circuit bcd_adder: (9010,1520) -> (16610,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA29 in circuit bcd_adder: (9010,1520) -> (16610,1520)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=160;y1=20;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=210;y1=10;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=260;y1=-100;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB29;anchorX=14100;anchorY=1930;left=14064;top=1901;right=14136;bottom=1959;connectionPoints=3;x1=10650;y1=1940;x2=16650;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO29;anchorX=14260;anchorY=1930;left=14224;top=1901;right=14296;bottom=1959;connectionPoints=3;x1=10650;y1=1940;x2=16650;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG29;anchorX=14420;anchorY=1930;left=14384;top=1901;right=14456;bottom=1959;connectionPoints=3;x1=10650;y1=1940;x2=16650;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S0;anchorX=14880;anchorY=100;left=14866;top=86;right=14894;bottom=114;connectionPoints=1;x1=110;y1=90;x2=15200;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=Cout;anchorX=14680;anchorY=300;left=14666;top=286;right=14694;bottom=314;connectionPoints=1;x1=3840;y1=290;x2=15670;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S3;anchorX=14730;anchorY=250;left=14716;top=236;right=14744;bottom=264;connectionPoints=1;x1=4000;y1=260;x2=15710;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S3;anchorX=14730;anchorY=250;left=14716;top=236;right=14744;bottom=264;connectionPoints=1;x1=4170;y1=240;x2=15750;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S2;anchorX=14780;anchorY=200;left=14766;top=186;right=14794;bottom=214;connectionPoints=1;x1=4220;y1=210;x2=15820;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S1;anchorX=14830;anchorY=150;left=14816;top=136;right=14844;bottom=164;connectionPoints=1;x1=4380;y1=160;x2=15840;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=Cout;anchorX=14680;anchorY=300;left=14666;top=286;right=14694;bottom=314;connectionPoints=1;x1=440;y1=310;x2=15210;y2=310)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S2;anchorX=14780;anchorY=200;left=14766;top=186;right=14794;bottom=214;connectionPoints=1;x1=4490;y1=190;x2=15830;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S1;anchorX=14830;anchorY=150;left=14816;top=136;right=14844;bottom=164;connectionPoints=1;x1=600;y1=140;x2=15230;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S0;anchorX=14880;anchorY=100;left=14866;top=86;right=14894;bottom=114;connectionPoints=1;x1=820;y1=110;x2=15340;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB27;anchorX=10700;anchorY=1510;left=10664;top=1481;right=10736;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO27;anchorX=10860;anchorY=1510;left=10824;top=1481;right=10896;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG27;anchorX=11020;anchorY=1510;left=10984;top=1481;right=11056;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX28;anchorX=11180;anchorY=1510;left=11144;top=1481;right=11216;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX38;anchorX=11340;anchorY=1510;left=11304;top=1481;right=11376;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA28;anchorX=11500;anchorY=1510;left=11464;top=1481;right=11536;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB28;anchorX=11660;anchorY=1510;left=11624;top=1481;right=11696;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO28;anchorX=11820;anchorY=1510;left=11784;top=1481;right=11856;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG28;anchorX=11980;anchorY=1510;left=11944;top=1481;right=12016;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX29;anchorX=12140;anchorY=1510;left=12104;top=1481;right=12176;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX39;anchorX=12300;anchorY=1510;left=12264;top=1481;right=12336;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA29;anchorX=12460;anchorY=1510;left=12424;top=1481;right=12496;bottom=1539;connectionPoints=3;x1=8840;y1=1500;x2=16570;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB27;anchorX=10700;anchorY=1510;left=10664;top=1481;right=10736;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO27;anchorX=10860;anchorY=1510;left=10824;top=1481;right=10896;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG27;anchorX=11020;anchorY=1510;left=10984;top=1481;right=11056;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX28;anchorX=11180;anchorY=1510;left=11144;top=1481;right=11216;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX38;anchorX=11340;anchorY=1510;left=11304;top=1481;right=11376;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA28;anchorX=11500;anchorY=1510;left=11464;top=1481;right=11536;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB28;anchorX=11660;anchorY=1510;left=11624;top=1481;right=11696;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO28;anchorX=11820;anchorY=1510;left=11784;top=1481;right=11856;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG28;anchorX=11980;anchorY=1510;left=11944;top=1481;right=12016;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX29;anchorX=12140;anchorY=1510;left=12104;top=1481;right=12176;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX39;anchorX=12300;anchorY=1510;left=12264;top=1481;right=12336;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA29;anchorX=12460;anchorY=1510;left=12424;top=1481;right=12496;bottom=1539;connectionPoints=3;x1=9010;y1=1520;x2=16610;y2=1520)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\bcd_adder.circ | true |
| manifest_json | generated\reports\bcd_adder_circuit_manifest.json | true |
| manifest_markdown | generated\reports\bcd_adder_circuit_manifest.md | true |
| delivery_json | generated\reports\bcd_adder_delivery_report.json | true |
| delivery_markdown | generated\reports\bcd_adder_delivery_report.md | true |
| summary_json | generated\reports\bcd_adder_generation_summary.json | true |
| summary_markdown | generated\reports\bcd_adder_generation_summary.md | true |
| artifact | generated\reports\bcd_adder_multichip.md | true |
