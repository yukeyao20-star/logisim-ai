# Generation delivery report for bin2bcd_4bit

- Request kind: natural_language
- Request: 二进制转BCD
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\bin2bcd_4bit.circ
- Summary: generated\reports\bin2bcd_4bit_generation_summary.md
- Manifest: generated\reports\bin2bcd_4bit_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 13
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 3
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 10
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,160) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,170) -> (210,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin T1 in circuit bin2bcd_4bit: (110,90) -> (3880,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin U0 in circuit bin2bcd_4bit: (160,160) -> (3860,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin U3 in circuit bin2bcd_4bit: (2570,290) -> (4350,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin U2 in circuit bin2bcd_4bit: (2730,260) -> (4410,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin U2 in circuit bin2bcd_4bit: (2890,240) -> (4450,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin U1 in circuit bin2bcd_4bit: (2940,190) -> (4500,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin U1 in circuit bin2bcd_4bit: (3050,210) -> (4490,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin U3 in circuit bin2bcd_4bit: (450,310) -> (3890,310)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin U0 in circuit bin2bcd_4bit: (610,140) -> (3910,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin T1 in circuit bin2bcd_4bit: (790,110) -> (3970,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bin2bcd_4bit;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bin2bcd_4bit;x1=160;y1=160;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bin2bcd_4bit;x1=210;y1=170;x2=210;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bin2bcd_4bit;component=Pin;label=T1;anchorX=3560;anchorY=100;left=3546;top=86;right=3574;bottom=114;connectionPoints=1;x1=110;y1=90;x2=3880;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bin2bcd_4bit;component=Pin;label=U0;anchorX=3510;anchorY=150;left=3496;top=136;right=3524;bottom=164;connectionPoints=1;x1=160;y1=160;x2=3860;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bin2bcd_4bit;component=Pin;label=U3;anchorX=3360;anchorY=300;left=3346;top=286;right=3374;bottom=314;connectionPoints=1;x1=2570;y1=290;x2=4350;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bin2bcd_4bit;component=Pin;label=U2;anchorX=3410;anchorY=250;left=3396;top=236;right=3424;bottom=264;connectionPoints=1;x1=2730;y1=260;x2=4410;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bin2bcd_4bit;component=Pin;label=U2;anchorX=3410;anchorY=250;left=3396;top=236;right=3424;bottom=264;connectionPoints=1;x1=2890;y1=240;x2=4450;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bin2bcd_4bit;component=Pin;label=U1;anchorX=3460;anchorY=200;left=3446;top=186;right=3474;bottom=214;connectionPoints=1;x1=2940;y1=190;x2=4500;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bin2bcd_4bit;component=Pin;label=U1;anchorX=3460;anchorY=200;left=3446;top=186;right=3474;bottom=214;connectionPoints=1;x1=3050;y1=210;x2=4490;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bin2bcd_4bit;component=Pin;label=U3;anchorX=3360;anchorY=300;left=3346;top=286;right=3374;bottom=314;connectionPoints=1;x1=450;y1=310;x2=3890;y2=310)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bin2bcd_4bit;component=Pin;label=U0;anchorX=3510;anchorY=150;left=3496;top=136;right=3524;bottom=164;connectionPoints=1;x1=610;y1=140;x2=3910;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bin2bcd_4bit;component=Pin;label=T1;anchorX=3560;anchorY=100;left=3546;top=86;right=3574;bottom=114;connectionPoints=1;x1=790;y1=110;x2=3970;y2=110)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\bin2bcd_4bit.circ | true |
| manifest_json | generated\reports\bin2bcd_4bit_circuit_manifest.json | false |
| manifest_markdown | generated\reports\bin2bcd_4bit_circuit_manifest.md | false |
| delivery_json | generated\reports\bin2bcd_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\bin2bcd_4bit_delivery_report.md | true |
| summary_json | generated\reports\bin2bcd_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\bin2bcd_4bit_generation_summary.md | true |
| artifact | generated\reports\bin2bcd_4bit_multichip.md | true |
