# Generation delivery report for piso_4bit

- Request kind: natural_language
- Request: 并行输入串行输出移位寄存器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\piso_4bit.circ
- Summary: generated\reports\piso_4bit_generation_summary.md
- Manifest: generated\reports\piso_4bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 22
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 18
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-110) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,-250) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,-150) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,240) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin SO in circuit piso_4bit: (1430,110) -> (3870,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin SO in circuit piso_4bit: (1480,90) -> (3900,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q3 in circuit piso_4bit: (2490,290) -> (4110,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit piso_4bit: (260,240) -> (3760,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate PISOA1 in circuit piso_4bit: (260,240) -> (3760,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate PISOB1 in circuit piso_4bit: (260,240) -> (3760,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate PISOM1 in circuit piso_4bit: (260,240) -> (3760,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate PISOA2 in circuit piso_4bit: (260,240) -> (3760,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate PISOB2 in circuit piso_4bit: (260,240) -> (3760,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate PISOM2 in circuit piso_4bit: (260,240) -> (3760,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate PISOL in circuit piso_4bit: (260,240) -> (3760,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate PISOA0 in circuit piso_4bit: (260,240) -> (3760,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate PISOB0 in circuit piso_4bit: (260,240) -> (3760,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate PISOM0 in circuit piso_4bit: (260,240) -> (3760,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit piso_4bit: (360,210) -> (3950,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit piso_4bit: (620,190) -> (3780,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit piso_4bit: (860,160) -> (3820,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit piso_4bit: (970,140) -> (3830,140)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=piso_4bit;x1=110;y1=-110;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=piso_4bit;x1=160;y1=-250;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=piso_4bit;x1=210;y1=-150;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=piso_4bit;x1=260;y1=240;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=Pin;label=SO;anchorX=3460;anchorY=100;left=3446;top=86;right=3474;bottom=114;connectionPoints=1;x1=1430;y1=110;x2=3870;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=Pin;label=SO;anchorX=3460;anchorY=100;left=3446;top=86;right=3474;bottom=114;connectionPoints=1;x1=1480;y1=90;x2=3900;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=Pin;label=Q3;anchorX=3260;anchorY=300;left=3246;top=286;right=3274;bottom=314;connectionPoints=1;x1=2490;y1=290;x2=4110;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=Pin;label=Q2;anchorX=3310;anchorY=250;left=3296;top=236;right=3324;bottom=264;connectionPoints=1;x1=260;y1=240;x2=3760;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=AND Gate;label=PISOA1;anchorX=1240;anchorY=250;left=1204;top=221;right=1276;bottom=279;connectionPoints=3;x1=260;y1=240;x2=3760;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=AND Gate;label=PISOB1;anchorX=1360;anchorY=250;left=1324;top=221;right=1396;bottom=279;connectionPoints=3;x1=260;y1=240;x2=3760;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=OR Gate;label=PISOM1;anchorX=1480;anchorY=250;left=1444;top=221;right=1516;bottom=279;connectionPoints=3;x1=260;y1=240;x2=3760;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=AND Gate;label=PISOA2;anchorX=1860;anchorY=250;left=1824;top=221;right=1896;bottom=279;connectionPoints=3;x1=260;y1=240;x2=3760;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=AND Gate;label=PISOB2;anchorX=1980;anchorY=250;left=1944;top=221;right=2016;bottom=279;connectionPoints=3;x1=260;y1=240;x2=3760;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=OR Gate;label=PISOM2;anchorX=2100;anchorY=250;left=2064;top=221;right=2136;bottom=279;connectionPoints=3;x1=260;y1=240;x2=3760;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=NOT Gate;label=PISOL;anchorX=500;anchorY=250;left=479;top=234;right=521;bottom=266;connectionPoints=2;x1=260;y1=240;x2=3760;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=AND Gate;label=PISOA0;anchorX=620;anchorY=250;left=584;top=221;right=656;bottom=279;connectionPoints=3;x1=260;y1=240;x2=3760;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=AND Gate;label=PISOB0;anchorX=740;anchorY=250;left=704;top=221;right=776;bottom=279;connectionPoints=3;x1=260;y1=240;x2=3760;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=OR Gate;label=PISOM0;anchorX=860;anchorY=250;left=824;top=221;right=896;bottom=279;connectionPoints=3;x1=260;y1=240;x2=3760;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=Pin;label=Q1;anchorX=3360;anchorY=200;left=3346;top=186;right=3374;bottom=214;connectionPoints=1;x1=360;y1=210;x2=3950;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=Pin;label=Q1;anchorX=3360;anchorY=200;left=3346;top=186;right=3374;bottom=214;connectionPoints=1;x1=620;y1=190;x2=3780;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=Pin;label=Q0;anchorX=3410;anchorY=150;left=3396;top=136;right=3424;bottom=164;connectionPoints=1;x1=860;y1=160;x2=3820;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=piso_4bit;component=Pin;label=Q0;anchorX=3410;anchorY=150;left=3396;top=136;right=3424;bottom=164;connectionPoints=1;x1=970;y1=140;x2=3830;y2=140)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\piso_4bit.circ | true |
| manifest_json | generated\reports\piso_4bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\piso_4bit_circuit_manifest.md | true |
| delivery_json | generated\reports\piso_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\piso_4bit_delivery_report.md | true |
| summary_json | generated\reports\piso_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\piso_4bit_generation_summary.md | true |
| artifact | generated\reports\piso_4bit_multichip.md | true |
