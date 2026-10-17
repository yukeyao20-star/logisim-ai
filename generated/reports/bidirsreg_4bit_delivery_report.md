# Generation delivery report for bidirsreg_4bit

- Request kind: natural_language
- Request: 4 bit bidirectional shift register
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\bidirsreg_4bit.circ
- Summary: generated\reports\bidirsreg_4bit_generation_summary.md
- Manifest: generated\reports\bidirsreg_4bit_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 11
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 7
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,120) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,-180) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,40) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit bidirsreg_4bit: (110,110) -> (4420,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit bidirsreg_4bit: (1650,160) -> (4450,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit bidirsreg_4bit: (1700,90) -> (4520,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit bidirsreg_4bit: (1810,140) -> (4490,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q3 in circuit bidirsreg_4bit: (3020,240) -> (4940,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit bidirsreg_4bit: (3290,210) -> (5050,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit bidirsreg_4bit: (470,190) -> (4430,190)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bidirsreg_4bit;x1=110;y1=110;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bidirsreg_4bit;x1=160;y1=120;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bidirsreg_4bit;x1=210;y1=-180;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bidirsreg_4bit;x1=260;y1=40;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bidirsreg_4bit;component=Pin;label=Q0;anchorX=4120;anchorY=100;left=4106;top=86;right=4134;bottom=114;connectionPoints=1;x1=110;y1=110;x2=4420;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bidirsreg_4bit;component=Pin;label=Q1;anchorX=4070;anchorY=150;left=4056;top=136;right=4084;bottom=164;connectionPoints=1;x1=1650;y1=160;x2=4450;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bidirsreg_4bit;component=Pin;label=Q0;anchorX=4120;anchorY=100;left=4106;top=86;right=4134;bottom=114;connectionPoints=1;x1=1700;y1=90;x2=4520;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bidirsreg_4bit;component=Pin;label=Q1;anchorX=4070;anchorY=150;left=4056;top=136;right=4084;bottom=164;connectionPoints=1;x1=1810;y1=140;x2=4490;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bidirsreg_4bit;component=Pin;label=Q3;anchorX=3970;anchorY=250;left=3956;top=236;right=3984;bottom=264;connectionPoints=1;x1=3020;y1=240;x2=4940;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bidirsreg_4bit;component=Pin;label=Q2;anchorX=4020;anchorY=200;left=4006;top=186;right=4034;bottom=214;connectionPoints=1;x1=3290;y1=210;x2=5050;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bidirsreg_4bit;component=Pin;label=Q2;anchorX=4020;anchorY=200;left=4006;top=186;right=4034;bottom=214;connectionPoints=1;x1=470;y1=190;x2=4430;y2=190)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\bidirsreg_4bit.circ | true |
| manifest_json | generated\reports\bidirsreg_4bit_circuit_manifest.json | false |
| manifest_markdown | generated\reports\bidirsreg_4bit_circuit_manifest.md | false |
| delivery_json | generated\reports\bidirsreg_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\bidirsreg_4bit_delivery_report.md | true |
| summary_json | generated\reports\bidirsreg_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\bidirsreg_4bit_generation_summary.md | true |
| artifact | generated\reports\bidirsreg_4bit_multichip.md | true |
