# Generation delivery report for alu_1bit

- Request kind: natural_language
- Request: 1位ALU
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\alu_1bit.circ
- Summary: generated\reports\alu_1bit_generation_summary.md
- Manifest: generated\reports\alu_1bit_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 10
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 6
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,50) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,90) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,110) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,40) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit alu_1bit: (1090,160) -> (5180,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Cout in circuit alu_1bit: (1250,140) -> (5190,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin R0 in circuit alu_1bit: (160,90) -> (5240,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin R0 in circuit alu_1bit: (210,110) -> (5270,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Z in circuit alu_1bit: (2620,210) -> (5680,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Z in circuit alu_1bit: (2890,190) -> (5710,190)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=alu_1bit;x1=110;y1=50;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=alu_1bit;x1=160;y1=90;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=alu_1bit;x1=210;y1=110;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=alu_1bit;x1=260;y1=40;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_1bit;component=Pin;label=Cout;anchorX=4790;anchorY=150;left=4776;top=136;right=4804;bottom=164;connectionPoints=1;x1=1090;y1=160;x2=5180;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_1bit;component=Pin;label=Cout;anchorX=4790;anchorY=150;left=4776;top=136;right=4804;bottom=164;connectionPoints=1;x1=1250;y1=140;x2=5190;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_1bit;component=Pin;label=R0;anchorX=4840;anchorY=100;left=4826;top=86;right=4854;bottom=114;connectionPoints=1;x1=160;y1=90;x2=5240;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_1bit;component=Pin;label=R0;anchorX=4840;anchorY=100;left=4826;top=86;right=4854;bottom=114;connectionPoints=1;x1=210;y1=110;x2=5270;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_1bit;component=Pin;label=Z;anchorX=4740;anchorY=200;left=4726;top=186;right=4754;bottom=214;connectionPoints=1;x1=2620;y1=210;x2=5680;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=alu_1bit;component=Pin;label=Z;anchorX=4740;anchorY=200;left=4726;top=186;right=4754;bottom=214;connectionPoints=1;x1=2890;y1=190;x2=5710;y2=190)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\alu_1bit.circ | true |
| manifest_json | generated\reports\alu_1bit_circuit_manifest.json | false |
| manifest_markdown | generated\reports\alu_1bit_circuit_manifest.md | false |
| delivery_json | generated\reports\alu_1bit_delivery_report.json | true |
| delivery_markdown | generated\reports\alu_1bit_delivery_report.md | true |
| summary_json | generated\reports\alu_1bit_generation_summary.json | true |
| summary_markdown | generated\reports\alu_1bit_generation_summary.md | true |
| artifact | generated\reports\alu_1bit_multichip.md | true |
| artifact | generated\reports\alu_1bit_official_simulation.json | true |
| artifact | generated\reports\alu_1bit_official_simulation.md | true |
