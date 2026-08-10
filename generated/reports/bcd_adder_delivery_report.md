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
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit bcd_adder: (110,90) -> (10080,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit bcd_adder: (2560,290) -> (10550,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit bcd_adder: (2720,260) -> (10590,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit bcd_adder: (2890,240) -> (10630,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit bcd_adder: (2940,210) -> (10700,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit bcd_adder: (3100,160) -> (10720,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit bcd_adder: (3210,190) -> (10710,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit bcd_adder: (440,310) -> (10090,310)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit bcd_adder: (600,140) -> (10110,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB27 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO27 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG27 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX28 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX38 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA28 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB28 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO28 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG28 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX29 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX39 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA29 in circuit bcd_adder: (6280,1500) -> (11450,1500)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB27 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO27 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG27 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX28 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX38 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA28 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB28 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO28 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG28 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX29 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate BX39 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BA29 in circuit bcd_adder: (6450,1520) -> (11490,1520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate BB29 in circuit bcd_adder: (6810,1940) -> (11530,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BO29 in circuit bcd_adder: (6810,1940) -> (11530,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate BG29 in circuit bcd_adder: (6810,1940) -> (11530,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit bcd_adder: (820,110) -> (10220,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=160;y1=20;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=210;y1=10;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd_adder;x1=260;y1=-100;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S0;anchorX=9760;anchorY=100;left=9746;top=86;right=9774;bottom=114;connectionPoints=1;x1=110;y1=90;x2=10080;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=Cout;anchorX=9560;anchorY=300;left=9546;top=286;right=9574;bottom=314;connectionPoints=1;x1=2560;y1=290;x2=10550;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S3;anchorX=9610;anchorY=250;left=9596;top=236;right=9624;bottom=264;connectionPoints=1;x1=2720;y1=260;x2=10590;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S3;anchorX=9610;anchorY=250;left=9596;top=236;right=9624;bottom=264;connectionPoints=1;x1=2890;y1=240;x2=10630;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S2;anchorX=9660;anchorY=200;left=9646;top=186;right=9674;bottom=214;connectionPoints=1;x1=2940;y1=210;x2=10700;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S1;anchorX=9710;anchorY=150;left=9696;top=136;right=9724;bottom=164;connectionPoints=1;x1=3100;y1=160;x2=10720;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S2;anchorX=9660;anchorY=200;left=9646;top=186;right=9674;bottom=214;connectionPoints=1;x1=3210;y1=190;x2=10710;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=Cout;anchorX=9560;anchorY=300;left=9546;top=286;right=9574;bottom=314;connectionPoints=1;x1=440;y1=310;x2=10090;y2=310)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S1;anchorX=9710;anchorY=150;left=9696;top=136;right=9724;bottom=164;connectionPoints=1;x1=600;y1=140;x2=10110;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB27;anchorX=6860;anchorY=1510;left=6824;top=1481;right=6896;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO27;anchorX=7020;anchorY=1510;left=6984;top=1481;right=7056;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG27;anchorX=7180;anchorY=1510;left=7144;top=1481;right=7216;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX28;anchorX=7340;anchorY=1510;left=7304;top=1481;right=7376;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX38;anchorX=7500;anchorY=1510;left=7464;top=1481;right=7536;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA28;anchorX=7660;anchorY=1510;left=7624;top=1481;right=7696;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB28;anchorX=7820;anchorY=1510;left=7784;top=1481;right=7856;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO28;anchorX=7980;anchorY=1510;left=7944;top=1481;right=8016;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG28;anchorX=8140;anchorY=1510;left=8104;top=1481;right=8176;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX29;anchorX=8300;anchorY=1510;left=8264;top=1481;right=8336;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX39;anchorX=8460;anchorY=1510;left=8424;top=1481;right=8496;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA29;anchorX=8620;anchorY=1510;left=8584;top=1481;right=8656;bottom=1539;connectionPoints=3;x1=6280;y1=1500;x2=11450;y2=1500)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB27;anchorX=6860;anchorY=1510;left=6824;top=1481;right=6896;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO27;anchorX=7020;anchorY=1510;left=6984;top=1481;right=7056;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG27;anchorX=7180;anchorY=1510;left=7144;top=1481;right=7216;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX28;anchorX=7340;anchorY=1510;left=7304;top=1481;right=7376;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX38;anchorX=7500;anchorY=1510;left=7464;top=1481;right=7536;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA28;anchorX=7660;anchorY=1510;left=7624;top=1481;right=7696;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB28;anchorX=7820;anchorY=1510;left=7784;top=1481;right=7856;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO28;anchorX=7980;anchorY=1510;left=7944;top=1481;right=8016;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG28;anchorX=8140;anchorY=1510;left=8104;top=1481;right=8176;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX29;anchorX=8300;anchorY=1510;left=8264;top=1481;right=8336;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=XOR Gate;label=BX39;anchorX=8460;anchorY=1510;left=8424;top=1481;right=8496;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BA29;anchorX=8620;anchorY=1510;left=8584;top=1481;right=8656;bottom=1539;connectionPoints=3;x1=6450;y1=1520;x2=11490;y2=1520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=AND Gate;label=BB29;anchorX=8980;anchorY=1930;left=8944;top=1901;right=9016;bottom=1959;connectionPoints=3;x1=6810;y1=1940;x2=11530;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BO29;anchorX=9140;anchorY=1930;left=9104;top=1901;right=9176;bottom=1959;connectionPoints=3;x1=6810;y1=1940;x2=11530;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=OR Gate;label=BG29;anchorX=9300;anchorY=1930;left=9264;top=1901;right=9336;bottom=1959;connectionPoints=3;x1=6810;y1=1940;x2=11530;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd_adder;component=Pin;label=S0;anchorX=9760;anchorY=100;left=9746;top=86;right=9774;bottom=114;connectionPoints=1;x1=820;y1=110;x2=10220;y2=110)

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
