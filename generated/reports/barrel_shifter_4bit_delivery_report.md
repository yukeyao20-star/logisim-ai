# Generation delivery report for barrel_shifter_4bit

- Request kind: natural_language
- Request: 4位桶形移位器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\barrel_shifter_4bit.circ
- Summary: generated\reports\barrel_shifter_4bit_generation_summary.md
- Manifest: generated\reports\barrel_shifter_4bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 36
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 32
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 12

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-200) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,-390) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,-580) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,260) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y3 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SA3 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SB4 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SA5 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SB6 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SA7 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SB8 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate MO9 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate MO10 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate NS1 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate NS2 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SA1 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SB2 in circuit barrel_shifter_4bit: (260,260) -> (9060,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y2 in circuit barrel_shifter_4bit: (2730,210) -> (9570,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit barrel_shifter_4bit: (2890,160) -> (9610,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y3 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SA3 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SB4 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SA5 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SB6 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SA7 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SB8 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate MO9 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate MO10 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate NS1 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate NS2 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SA1 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate SB2 in circuit barrel_shifter_4bit: (360,240) -> (9180,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y2 in circuit barrel_shifter_4bit: (470,190) -> (9070,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit barrel_shifter_4bit: (660,90) -> (9160,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit barrel_shifter_4bit: (770,140) -> (9110,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit barrel_shifter_4bit: (930,110) -> (9150,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=barrel_shifter_4bit;x1=110;y1=-200;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=barrel_shifter_4bit;x1=160;y1=-390;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=barrel_shifter_4bit;x1=210;y1=-580;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=barrel_shifter_4bit;x1=260;y1=260;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=Pin;label=Y3;anchorX=8610;anchorY=250;left=8596;top=236;right=8624;bottom=264;connectionPoints=1;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SA3;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SB4;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SA5;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SB6;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SA7;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SB8;anchorX=1940;anchorY=250;left=1904;top=221;right=1976;bottom=279;connectionPoints=3;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=OR Gate;label=MO9;anchorX=2100;anchorY=250;left=2064;top=221;right=2136;bottom=279;connectionPoints=3;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=OR Gate;label=MO10;anchorX=2260;anchorY=250;left=2224;top=221;right=2296;bottom=279;connectionPoints=3;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=NOT Gate;label=NS1;anchorX=500;anchorY=250;left=479;top=234;right=521;bottom=266;connectionPoints=2;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=NOT Gate;label=NS2;anchorX=660;anchorY=250;left=639;top=234;right=681;bottom=266;connectionPoints=2;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SA1;anchorX=820;anchorY=250;left=784;top=221;right=856;bottom=279;connectionPoints=3;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SB2;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=260;y1=260;x2=9060;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=Pin;label=Y2;anchorX=8660;anchorY=200;left=8646;top=186;right=8674;bottom=214;connectionPoints=1;x1=2730;y1=210;x2=9570;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=Pin;label=Y1;anchorX=8710;anchorY=150;left=8696;top=136;right=8724;bottom=164;connectionPoints=1;x1=2890;y1=160;x2=9610;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=Pin;label=Y3;anchorX=8610;anchorY=250;left=8596;top=236;right=8624;bottom=264;connectionPoints=1;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SA3;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SB4;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SA5;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SB6;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SA7;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SB8;anchorX=1940;anchorY=250;left=1904;top=221;right=1976;bottom=279;connectionPoints=3;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=OR Gate;label=MO9;anchorX=2100;anchorY=250;left=2064;top=221;right=2136;bottom=279;connectionPoints=3;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=OR Gate;label=MO10;anchorX=2260;anchorY=250;left=2224;top=221;right=2296;bottom=279;connectionPoints=3;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=NOT Gate;label=NS1;anchorX=500;anchorY=250;left=479;top=234;right=521;bottom=266;connectionPoints=2;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=NOT Gate;label=NS2;anchorX=660;anchorY=250;left=639;top=234;right=681;bottom=266;connectionPoints=2;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SA1;anchorX=820;anchorY=250;left=784;top=221;right=856;bottom=279;connectionPoints=3;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=AND Gate;label=SB2;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=360;y1=240;x2=9180;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=Pin;label=Y2;anchorX=8660;anchorY=200;left=8646;top=186;right=8674;bottom=214;connectionPoints=1;x1=470;y1=190;x2=9070;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=Pin;label=Y0;anchorX=8760;anchorY=100;left=8746;top=86;right=8774;bottom=114;connectionPoints=1;x1=660;y1=90;x2=9160;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=Pin;label=Y1;anchorX=8710;anchorY=150;left=8696;top=136;right=8724;bottom=164;connectionPoints=1;x1=770;y1=140;x2=9110;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_4bit;component=Pin;label=Y0;anchorX=8760;anchorY=100;left=8746;top=86;right=8774;bottom=114;connectionPoints=1;x1=930;y1=110;x2=9150;y2=110)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\barrel_shifter_4bit.circ | true |
| manifest_json | generated\reports\barrel_shifter_4bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\barrel_shifter_4bit_circuit_manifest.md | true |
| truth_table_json | generated\reports\barrel_shifter_4bit_circuit_truth_table.json | true |
| truth_table_markdown | generated\reports\barrel_shifter_4bit_circuit_truth_table.md | true |
| delivery_json | generated\reports\barrel_shifter_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\barrel_shifter_4bit_delivery_report.md | true |
| summary_json | generated\reports\barrel_shifter_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\barrel_shifter_4bit_generation_summary.md | true |
| artifact | generated\reports\barrel_shifter_4bit_multichip.md | true |
| artifact | generated\reports\barrel_shifter_4bit_official_simulation.json | true |
| artifact | generated\reports\barrel_shifter_4bit_official_simulation.md | true |
