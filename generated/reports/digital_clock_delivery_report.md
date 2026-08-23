# Generation delivery report for digital_clock

- Request kind: natural_language
- Request: 数显钟
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\digital_clock.circ
- Summary: generated\reports\digital_clock_generation_summary.md
- Manifest: generated\reports\digital_clock_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 56
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 1
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 55
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-1350) -> (110,150)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q13 in circuit digital_clock: (10820,740) -> (17080,740)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q11 in circuit digital_clock: (11080,660) -> (17090,660)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q11 in circuit digital_clock: (11340,640) -> (17100,640)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q22 in circuit digital_clock: (11370,1010) -> (16500,1010)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q20 in circuit digital_clock: (11630,890) -> (16640,890)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q10 in circuit digital_clock: (11890,610) -> (17130,610)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q18 in circuit digital_clock: (1270,790) -> (16770,790)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit digital_clock: (13910,210) -> (15280,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q5 in circuit digital_clock: (14010,340) -> (15160,340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit digital_clock: (1540,190) -> (14880,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit digital_clock: (1850,140) -> (14910,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit digital_clock: (1970,90) -> (14970,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q12 in circuit digital_clock: (2210,710) -> (16590,710)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q6 in circuit digital_clock: (2330,410) -> (15420,410)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q19 in circuit digital_clock: (2510,860) -> (15640,860)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q3 in circuit digital_clock: (2630,240) -> (15240,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q4 in circuit digital_clock: (2680,290) -> (15210,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit digital_clock: (2870,160) -> (15340,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q19 in circuit digital_clock: (2870,840) -> (15440,840)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit digital_clock: (2920,110) -> (15450,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q20 in circuit digital_clock: (4010,910) -> (15830,910)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate D1NW in circuit digital_clock: (4130,1080) -> (16550,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate D1TE in circuit digital_clock: (4130,1080) -> (16550,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate D1T0 in circuit digital_clock: (4130,1080) -> (16550,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate D1M1 in circuit digital_clock: (4130,1080) -> (16550,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate D1R2 in circuit digital_clock: (4130,1080) -> (16550,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate D1M2 in circuit digital_clock: (4130,1080) -> (16550,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate D1O2 in circuit digital_clock: (4130,1080) -> (16550,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate D1EC in circuit digital_clock: (4130,1080) -> (16550,1080)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q13 in circuit digital_clock: (490,760) -> (16740,760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate D3T0 in circuit digital_clock: (7690,2340) -> (17550,2340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate D3M1 in circuit digital_clock: (7690,2340) -> (17550,2340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate D3R2 in circuit digital_clock: (7690,2340) -> (17550,2340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate D3M2 in circuit digital_clock: (7690,2340) -> (17550,2340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate D3O2 in circuit digital_clock: (7690,2340) -> (17550,2340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate D3EC in circuit digital_clock: (7690,2340) -> (17550,2340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate D3TE in circuit digital_clock: (7690,2340) -> (17550,2340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q5 in circuit digital_clock: (8540,360) -> (17050,360)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HA1 in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HA2 in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate HN3 in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HA3 in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HA4 in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HW in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate HNW in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HM1 in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate HO1 in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HR2 in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HM2 in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate HO2 in circuit digital_clock: (9610,2760) -> (17780,2760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HR3 in circuit digital_clock: (9910,3180) -> (17800,3180)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HM3 in circuit digital_clock: (9910,3180) -> (17800,3180)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HR4 in circuit digital_clock: (9910,3180) -> (17800,3180)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate HM4 in circuit digital_clock: (9910,3180) -> (17800,3180)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate HO4 in circuit digital_clock: (9910,3180) -> (17800,3180)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=digital_clock;x1=110;y1=-1350;x2=110;y2=150)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q13;anchorX=13910;anchorY=750;left=13896;top=736;right=13924;bottom=764;connectionPoints=1;x1=10820;y1=740;x2=17080;y2=740)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q11;anchorX=14010;anchorY=650;left=13996;top=636;right=14024;bottom=664;connectionPoints=1;x1=11080;y1=660;x2=17090;y2=660)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q11;anchorX=14010;anchorY=650;left=13996;top=636;right=14024;bottom=664;connectionPoints=1;x1=11340;y1=640;x2=17100;y2=640)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q22;anchorX=13660;anchorY=1000;left=13646;top=986;right=13674;bottom=1014;connectionPoints=1;x1=11370;y1=1010;x2=16500;y2=1010)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q20;anchorX=13760;anchorY=900;left=13746;top=886;right=13774;bottom=914;connectionPoints=1;x1=11630;y1=890;x2=16640;y2=890)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q10;anchorX=14060;anchorY=600;left=14046;top=586;right=14074;bottom=614;connectionPoints=1;x1=11890;y1=610;x2=17130;y2=610)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q18;anchorX=13860;anchorY=800;left=13846;top=786;right=13874;bottom=814;connectionPoints=1;x1=1270;y1=790;x2=16770;y2=790)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q2;anchorX=14460;anchorY=200;left=14446;top=186;right=14474;bottom=214;connectionPoints=1;x1=13910;y1=210;x2=15280;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q5;anchorX=14310;anchorY=350;left=14296;top=336;right=14324;bottom=364;connectionPoints=1;x1=14010;y1=340;x2=15160;y2=340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q2;anchorX=14460;anchorY=200;left=14446;top=186;right=14474;bottom=214;connectionPoints=1;x1=1540;y1=190;x2=14880;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q1;anchorX=14510;anchorY=150;left=14496;top=136;right=14524;bottom=164;connectionPoints=1;x1=1850;y1=140;x2=14910;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q0;anchorX=14560;anchorY=100;left=14546;top=86;right=14574;bottom=114;connectionPoints=1;x1=1970;y1=90;x2=14970;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q12;anchorX=13960;anchorY=700;left=13946;top=686;right=13974;bottom=714;connectionPoints=1;x1=2210;y1=710;x2=16590;y2=710)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q6;anchorX=14260;anchorY=400;left=14246;top=386;right=14274;bottom=414;connectionPoints=1;x1=2330;y1=410;x2=15420;y2=410)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q19;anchorX=13810;anchorY=850;left=13796;top=836;right=13824;bottom=864;connectionPoints=1;x1=2510;y1=860;x2=15640;y2=860)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q3;anchorX=14410;anchorY=250;left=14396;top=236;right=14424;bottom=264;connectionPoints=1;x1=2630;y1=240;x2=15240;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q4;anchorX=14360;anchorY=300;left=14346;top=286;right=14374;bottom=314;connectionPoints=1;x1=2680;y1=290;x2=15210;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q1;anchorX=14510;anchorY=150;left=14496;top=136;right=14524;bottom=164;connectionPoints=1;x1=2870;y1=160;x2=15340;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q19;anchorX=13810;anchorY=850;left=13796;top=836;right=13824;bottom=864;connectionPoints=1;x1=2870;y1=840;x2=15440;y2=840)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q0;anchorX=14560;anchorY=100;left=14546;top=86;right=14574;bottom=114;connectionPoints=1;x1=2920;y1=110;x2=15450;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q20;anchorX=13760;anchorY=900;left=13746;top=886;right=13774;bottom=914;connectionPoints=1;x1=4010;y1=910;x2=15830;y2=910)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=NOT Gate;label=D1NW;anchorX=4480;anchorY=1090;left=4459;top=1074;right=4501;bottom=1106;connectionPoints=2;x1=4130;y1=1080;x2=16550;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=D1TE;anchorX=4600;anchorY=1090;left=4564;top=1061;right=4636;bottom=1119;connectionPoints=3;x1=4130;y1=1080;x2=16550;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=OR Gate;label=D1T0;anchorX=4720;anchorY=1090;left=4684;top=1061;right=4756;bottom=1119;connectionPoints=3;x1=4130;y1=1080;x2=16550;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=D1M1;anchorX=4840;anchorY=1090;left=4804;top=1061;right=4876;bottom=1119;connectionPoints=3;x1=4130;y1=1080;x2=16550;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=D1R2;anchorX=4960;anchorY=1090;left=4924;top=1061;right=4996;bottom=1119;connectionPoints=3;x1=4130;y1=1080;x2=16550;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=D1M2;anchorX=5080;anchorY=1090;left=5044;top=1061;right=5116;bottom=1119;connectionPoints=3;x1=4130;y1=1080;x2=16550;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=OR Gate;label=D1O2;anchorX=5200;anchorY=1090;left=5164;top=1061;right=5236;bottom=1119;connectionPoints=3;x1=4130;y1=1080;x2=16550;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=D1EC;anchorX=5320;anchorY=1090;left=5284;top=1061;right=5356;bottom=1119;connectionPoints=3;x1=4130;y1=1080;x2=16550;y2=1080)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q13;anchorX=13910;anchorY=750;left=13896;top=736;right=13924;bottom=764;connectionPoints=1;x1=490;y1=760;x2=16740;y2=760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=OR Gate;label=D3T0;anchorX=10080;anchorY=2350;left=10044;top=2321;right=10116;bottom=2379;connectionPoints=3;x1=7690;y1=2340;x2=17550;y2=2340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=D3M1;anchorX=10200;anchorY=2350;left=10164;top=2321;right=10236;bottom=2379;connectionPoints=3;x1=7690;y1=2340;x2=17550;y2=2340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=D3R2;anchorX=10320;anchorY=2350;left=10284;top=2321;right=10356;bottom=2379;connectionPoints=3;x1=7690;y1=2340;x2=17550;y2=2340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=D3M2;anchorX=10440;anchorY=2350;left=10404;top=2321;right=10476;bottom=2379;connectionPoints=3;x1=7690;y1=2340;x2=17550;y2=2340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=OR Gate;label=D3O2;anchorX=10560;anchorY=2350;left=10524;top=2321;right=10596;bottom=2379;connectionPoints=3;x1=7690;y1=2340;x2=17550;y2=2340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=D3EC;anchorX=10680;anchorY=2350;left=10644;top=2321;right=10716;bottom=2379;connectionPoints=3;x1=7690;y1=2340;x2=17550;y2=2340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=D3TE;anchorX=9960;anchorY=2350;left=9924;top=2321;right=9996;bottom=2379;connectionPoints=3;x1=7690;y1=2340;x2=17550;y2=2340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=Pin;label=Q5;anchorX=14310;anchorY=350;left=14296;top=336;right=14324;bottom=364;connectionPoints=1;x1=8540;y1=360;x2=17050;y2=360)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HA1;anchorX=12160;anchorY=2770;left=12124;top=2741;right=12196;bottom=2799;connectionPoints=3;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HA2;anchorX=12280;anchorY=2770;left=12244;top=2741;right=12316;bottom=2799;connectionPoints=3;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=NOT Gate;label=HN3;anchorX=12400;anchorY=2770;left=12379;top=2754;right=12421;bottom=2786;connectionPoints=2;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HA3;anchorX=12520;anchorY=2770;left=12484;top=2741;right=12556;bottom=2799;connectionPoints=3;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HA4;anchorX=12640;anchorY=2770;left=12604;top=2741;right=12676;bottom=2799;connectionPoints=3;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HW;anchorX=12760;anchorY=2770;left=12724;top=2741;right=12796;bottom=2799;connectionPoints=3;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=NOT Gate;label=HNW;anchorX=12880;anchorY=2770;left=12859;top=2754;right=12901;bottom=2786;connectionPoints=2;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HM1;anchorX=13000;anchorY=2770;left=12964;top=2741;right=13036;bottom=2799;connectionPoints=3;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=OR Gate;label=HO1;anchorX=13120;anchorY=2770;left=13084;top=2741;right=13156;bottom=2799;connectionPoints=3;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HR2;anchorX=13240;anchorY=2770;left=13204;top=2741;right=13276;bottom=2799;connectionPoints=3;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HM2;anchorX=13360;anchorY=2770;left=13324;top=2741;right=13396;bottom=2799;connectionPoints=3;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=OR Gate;label=HO2;anchorX=13480;anchorY=2770;left=13444;top=2741;right=13516;bottom=2799;connectionPoints=3;x1=9610;y1=2760;x2=17780;y2=2760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HR3;anchorX=13660;anchorY=3190;left=13624;top=3161;right=13696;bottom=3219;connectionPoints=3;x1=9910;y1=3180;x2=17800;y2=3180)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HM3;anchorX=13780;anchorY=3190;left=13744;top=3161;right=13816;bottom=3219;connectionPoints=3;x1=9910;y1=3180;x2=17800;y2=3180)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HR4;anchorX=13900;anchorY=3190;left=13864;top=3161;right=13936;bottom=3219;connectionPoints=3;x1=9910;y1=3180;x2=17800;y2=3180)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=AND Gate;label=HM4;anchorX=14020;anchorY=3190;left=13984;top=3161;right=14056;bottom=3219;connectionPoints=3;x1=9910;y1=3180;x2=17800;y2=3180)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=digital_clock;component=OR Gate;label=HO4;anchorX=14140;anchorY=3190;left=14104;top=3161;right=14176;bottom=3219;connectionPoints=3;x1=9910;y1=3180;x2=17800;y2=3180)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\digital_clock.circ | true |
| manifest_json | generated\reports\digital_clock_circuit_manifest.json | true |
| manifest_markdown | generated\reports\digital_clock_circuit_manifest.md | true |
| delivery_json | generated\reports\digital_clock_delivery_report.json | true |
| delivery_markdown | generated\reports\digital_clock_delivery_report.md | true |
| summary_json | generated\reports\digital_clock_generation_summary.json | true |
| summary_markdown | generated\reports\digital_clock_generation_summary.md | true |
| artifact | generated\reports\digital_clock_multichip.md | true |
