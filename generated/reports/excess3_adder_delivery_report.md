# Generation delivery report for excess3_adder

- Request kind: natural_language
- Request: 余3码加法器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\excess3_adder.circ
- Summary: generated\reports\excess3_adder_generation_summary.md
- Manifest: generated\reports\excess3_adder_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 16
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 12
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,50) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,20) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,-80) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit excess3_adder: (1940,290) -> (7560,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit excess3_adder: (2000,240) -> (7640,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit excess3_adder: (2240,210) -> (7670,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit excess3_adder: (2310,260) -> (7630,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit excess3_adder: (2430,190) -> (7680,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit excess3_adder: (440,310) -> (7260,310)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit excess3_adder: (500,160) -> (7280,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of XOR Gate E3X1 in circuit excess3_adder: (510,-490) -> (510,550)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit excess3_adder: (560,140) -> (7290,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate E3G28 in circuit excess3_adder: (6150,1920) -> (8490,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit excess3_adder: (860,110) -> (7330,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit excess3_adder: (980,90) -> (7350,90)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=excess3_adder;x1=110;y1=80;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=excess3_adder;x1=160;y1=50;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=excess3_adder;x1=210;y1=20;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=excess3_adder;x1=260;y1=-80;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=Pin;label=Cout;anchorX=6720;anchorY=300;left=6706;top=286;right=6734;bottom=314;connectionPoints=1;x1=1940;y1=290;x2=7560;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=Pin;label=S3;anchorX=6770;anchorY=250;left=6756;top=236;right=6784;bottom=264;connectionPoints=1;x1=2000;y1=240;x2=7640;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=Pin;label=S2;anchorX=6820;anchorY=200;left=6806;top=186;right=6834;bottom=214;connectionPoints=1;x1=2240;y1=210;x2=7670;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=Pin;label=S3;anchorX=6770;anchorY=250;left=6756;top=236;right=6784;bottom=264;connectionPoints=1;x1=2310;y1=260;x2=7630;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=Pin;label=S2;anchorX=6820;anchorY=200;left=6806;top=186;right=6834;bottom=214;connectionPoints=1;x1=2430;y1=190;x2=7680;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=Pin;label=Cout;anchorX=6720;anchorY=300;left=6706;top=286;right=6734;bottom=314;connectionPoints=1;x1=440;y1=310;x2=7260;y2=310)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=Pin;label=S1;anchorX=6870;anchorY=150;left=6856;top=136;right=6884;bottom=164;connectionPoints=1;x1=500;y1=160;x2=7280;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=XOR Gate;label=E3X1;anchorX=500;anchorY=250;left=464;top=221;right=536;bottom=279;connectionPoints=3;x1=510;y1=-490;x2=510;y2=550)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=Pin;label=S1;anchorX=6870;anchorY=150;left=6856;top=136;right=6884;bottom=164;connectionPoints=1;x1=560;y1=140;x2=7290;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=OR Gate;label=E3G28;anchorX=6500;anchorY=1930;left=6464;top=1901;right=6536;bottom=1959;connectionPoints=3;x1=6150;y1=1920;x2=8490;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=Pin;label=S0;anchorX=6920;anchorY=100;left=6906;top=86;right=6934;bottom=114;connectionPoints=1;x1=860;y1=110;x2=7330;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=excess3_adder;component=Pin;label=S0;anchorX=6920;anchorY=100;left=6906;top=86;right=6934;bottom=114;connectionPoints=1;x1=980;y1=90;x2=7350;y2=90)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\excess3_adder.circ | true |
| manifest_json | generated\reports\excess3_adder_circuit_manifest.json | true |
| manifest_markdown | generated\reports\excess3_adder_circuit_manifest.md | true |
| delivery_json | generated\reports\excess3_adder_delivery_report.json | true |
| delivery_markdown | generated\reports\excess3_adder_delivery_report.md | true |
| summary_json | generated\reports\excess3_adder_generation_summary.json | true |
| summary_markdown | generated\reports\excess3_adder_generation_summary.md | true |
| artifact | generated\reports\excess3_adder_multichip.md | true |
| artifact | generated\reports\excess3_adder_official_simulation.json | true |
| artifact | generated\reports\excess3_adder_official_simulation.md | true |
