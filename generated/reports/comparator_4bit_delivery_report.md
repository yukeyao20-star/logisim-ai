# Generation delivery report for comparator_4bit

- Request kind: natural_language
- Request: 四位比较器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\comparator_4bit.circ
- Summary: generated\reports\comparator_4bit_generation_summary.md
- Manifest: generated\reports\comparator_4bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 40
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 22
- Layout wire risk count: 0
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 18
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Increase grid spacing, reassign crowded component rows, or rerun layout with wider component clearance. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 7

## Layout Issues

- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (1130,120) and (1140,120): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (1450,120) and (1460,120): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (1450,120) and (1470,120): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (1460,120) and (1470,120): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (2090,1140) and (2100,1140): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (2090,290) and (2100,290): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (2410,460) and (2420,460): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (260,120) and (270,120): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (260,120) and (310,100): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (260,120) and (310,140): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (260,200) and (270,200): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (260,280) and (270,280): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (260,360) and (270,360): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (260,440) and (270,440): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (260,520) and (270,520): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (260,600) and (270,600): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (260,680) and (270,680): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (270,120) and (310,100): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (270,120) and (310,140): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (2730,460) and (2740,460): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (490,120) and (500,120): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit comparator_4bit at (810,120) and (820,120): Tunnel and Tunnel
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n43 in circuit comparator_4bit: (1040,120) -> (1130,120)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n25 in circuit comparator_4bit: (1360,120) -> (1450,120)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n45 in circuit comparator_4bit: (1450,120) -> (1460,120)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n21 in circuit comparator_4bit: (1460,120) -> (1470,120)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n48 in circuit comparator_4bit: (2000,1140) -> (2090,1140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n47 in circuit comparator_4bit: (2000,290) -> (2090,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n37 in circuit comparator_4bit: (2320,460) -> (2410,460)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n31 in circuit comparator_4bit: (2640,460) -> (2730,460)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n40 in circuit comparator_4bit: (400,120) -> (490,120)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n41 in circuit comparator_4bit: (720,120) -> (810,120)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n4 in circuit comparator_4bit: (80,120) -> (260,120)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n5 in circuit comparator_4bit: (80,200) -> (260,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n9 in circuit comparator_4bit: (80,280) -> (260,280)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n10 in circuit comparator_4bit: (80,360) -> (260,360)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n14 in circuit comparator_4bit: (80,440) -> (260,440)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n15 in circuit comparator_4bit: (80,520) -> (260,520)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n19 in circuit comparator_4bit: (80,600) -> (260,600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n20 in circuit comparator_4bit: (80,680) -> (260,680)

## Layout Repair Suggestions

- [increase_spacing/OVERLAP] Increase grid spacing, reassign crowded component rows, or rerun layout with wider component clearance. (minClearanceX=80;minClearanceY=50)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n43;anchorX=1140;anchorY=120;left=1123;top=108;right=1157;bottom=132;connectionPoints=1;x1=1040;y1=120;x2=1130;y2=120)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n25;anchorX=1460;anchorY=120;left=1443;top=108;right=1477;bottom=132;connectionPoints=1;x1=1360;y1=120;x2=1450;y2=120)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n45;anchorX=1470;anchorY=120;left=1453;top=108;right=1487;bottom=132;connectionPoints=1;x1=1450;y1=120;x2=1460;y2=120)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n21;anchorX=1450;anchorY=120;left=1433;top=108;right=1467;bottom=132;connectionPoints=1;x1=1460;y1=120;x2=1470;y2=120)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n48;anchorX=2100;anchorY=1140;left=2083;top=1128;right=2117;bottom=1152;connectionPoints=1;x1=2000;y1=1140;x2=2090;y2=1140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n47;anchorX=2100;anchorY=290;left=2083;top=278;right=2117;bottom=302;connectionPoints=1;x1=2000;y1=290;x2=2090;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n37;anchorX=2420;anchorY=460;left=2403;top=448;right=2437;bottom=472;connectionPoints=1;x1=2320;y1=460;x2=2410;y2=460)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n31;anchorX=2740;anchorY=460;left=2723;top=448;right=2757;bottom=472;connectionPoints=1;x1=2640;y1=460;x2=2730;y2=460)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n40;anchorX=500;anchorY=120;left=483;top=108;right=517;bottom=132;connectionPoints=1;x1=400;y1=120;x2=490;y2=120)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n41;anchorX=820;anchorY=120;left=803;top=108;right=837;bottom=132;connectionPoints=1;x1=720;y1=120;x2=810;y2=120)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n4;anchorX=270;anchorY=120;left=257;top=108;right=283;bottom=132;connectionPoints=1;x1=80;y1=120;x2=260;y2=120)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n5;anchorX=270;anchorY=200;left=257;top=188;right=283;bottom=212;connectionPoints=1;x1=80;y1=200;x2=260;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n9;anchorX=270;anchorY=280;left=257;top=268;right=283;bottom=292;connectionPoints=1;x1=80;y1=280;x2=260;y2=280)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n10;anchorX=270;anchorY=360;left=253;top=348;right=287;bottom=372;connectionPoints=1;x1=80;y1=360;x2=260;y2=360)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n14;anchorX=270;anchorY=440;left=253;top=428;right=287;bottom=452;connectionPoints=1;x1=80;y1=440;x2=260;y2=440)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n15;anchorX=270;anchorY=520;left=253;top=508;right=287;bottom=532;connectionPoints=1;x1=80;y1=520;x2=260;y2=520)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n19;anchorX=270;anchorY=600;left=253;top=588;right=287;bottom=612;connectionPoints=1;x1=80;y1=600;x2=260;y2=600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Tunnel;label=n20;anchorX=270;anchorY=680;left=253;top=668;right=287;bottom=692;connectionPoints=1;x1=80;y1=680;x2=260;y2=680)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\comparator_4bit.circ | true |
| manifest_json | generated\reports\comparator_4bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\comparator_4bit_circuit_manifest.md | true |
| delivery_json | generated\reports\comparator_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\comparator_4bit_delivery_report.md | true |
| summary_json | generated\reports\comparator_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\comparator_4bit_generation_summary.md | true |
