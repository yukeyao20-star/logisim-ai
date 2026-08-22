# Generation delivery report for alu_2bit

- Request kind: natural_language
- Request: 2位算术逻辑单元
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\alu_2bit.circ
- Summary: generated\reports\alu_2bit_generation_summary.md
- Manifest: generated\reports\alu_2bit_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 14
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 10
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,50) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,-370) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,-380) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,190) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin R1 in circuit alu_2bit: (1090,160) -> (9780,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin R1 in circuit alu_2bit: (1250,140) -> (9790,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin R0 in circuit alu_2bit: (1890,90) -> (9860,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit alu_2bit: (260,190) -> (9840,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Z in circuit alu_2bit: (2730,260) -> (10230,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Z in circuit alu_2bit: (2780,240) -> (10260,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit alu_2bit: (310,210) -> (9870,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin R0 in circuit alu_2bit: (360,110) -> (9940,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate ALUCG in circuit alu_2bit: (7130,1920) -> (11030,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate ALUCG in circuit alu_2bit: (7450,1940) -> (11070,1940)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=alu_2bit;x1=110;y1=50;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=alu_2bit;x1=160;y1=-370;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=alu_2bit;x1=210;y1=-380;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=alu_2bit;x1=260;y1=190;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_2bit;component=Pin;label=R1;anchorX=9390;anchorY=150;left=9376;top=136;right=9404;bottom=164;connectionPoints=1;x1=1090;y1=160;x2=9780;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_2bit;component=Pin;label=R1;anchorX=9390;anchorY=150;left=9376;top=136;right=9404;bottom=164;connectionPoints=1;x1=1250;y1=140;x2=9790;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_2bit;component=Pin;label=R0;anchorX=9440;anchorY=100;left=9426;top=86;right=9454;bottom=114;connectionPoints=1;x1=1890;y1=90;x2=9860;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_2bit;component=Pin;label=Cout;anchorX=9340;anchorY=200;left=9326;top=186;right=9354;bottom=214;connectionPoints=1;x1=260;y1=190;x2=9840;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_2bit;component=Pin;label=Z;anchorX=9290;anchorY=250;left=9276;top=236;right=9304;bottom=264;connectionPoints=1;x1=2730;y1=260;x2=10230;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_2bit;component=Pin;label=Z;anchorX=9290;anchorY=250;left=9276;top=236;right=9304;bottom=264;connectionPoints=1;x1=2780;y1=240;x2=10260;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_2bit;component=Pin;label=Cout;anchorX=9340;anchorY=200;left=9326;top=186;right=9354;bottom=214;connectionPoints=1;x1=310;y1=210;x2=9870;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_2bit;component=Pin;label=R0;anchorX=9440;anchorY=100;left=9426;top=86;right=9454;bottom=114;connectionPoints=1;x1=360;y1=110;x2=9940;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_2bit;component=AND Gate;label=ALUCG;anchorX=8980;anchorY=1930;left=8944;top=1901;right=9016;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=11030;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_2bit;component=AND Gate;label=ALUCG;anchorX=8980;anchorY=1930;left=8944;top=1901;right=9016;bottom=1959;connectionPoints=3;x1=7450;y1=1940;x2=11070;y2=1940)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\alu_2bit.circ | true |
| manifest_json | generated\reports\alu_2bit_circuit_manifest.json | false |
| manifest_markdown | generated\reports\alu_2bit_circuit_manifest.md | false |
| delivery_json | generated\reports\alu_2bit_delivery_report.json | true |
| delivery_markdown | generated\reports\alu_2bit_delivery_report.md | true |
| summary_json | generated\reports\alu_2bit_generation_summary.json | true |
| summary_markdown | generated\reports\alu_2bit_generation_summary.md | true |
| artifact | generated\reports\alu_2bit_multichip.md | true |
| artifact | generated\reports\alu_2bit_official_simulation.json | true |
| artifact | generated\reports\alu_2bit_official_simulation.md | true |
