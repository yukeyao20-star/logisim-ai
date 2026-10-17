# Generation delivery report for comparator_4bit

- Request kind: natural_language
- Request: 4位比较器
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
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,70) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,120) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,10) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin GT in circuit comparator_4bit: (110,110) -> (5820,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin GT in circuit comparator_4bit: (1110,90) -> (5930,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin EQ in circuit comparator_4bit: (3370,160) -> (6350,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin LT in circuit comparator_4bit: (4220,210) -> (6340,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin LT in circuit comparator_4bit: (630,190) -> (5830,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin EQ in circuit comparator_4bit: (770,140) -> (5870,140)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_4bit;x1=110;y1=110;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_4bit;x1=160;y1=70;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_4bit;x1=210;y1=120;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_4bit;x1=260;y1=10;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Pin;label=GT;anchorX=5520;anchorY=100;left=5506;top=86;right=5534;bottom=114;connectionPoints=1;x1=110;y1=110;x2=5820;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Pin;label=GT;anchorX=5520;anchorY=100;left=5506;top=86;right=5534;bottom=114;connectionPoints=1;x1=1110;y1=90;x2=5930;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Pin;label=EQ;anchorX=5470;anchorY=150;left=5456;top=136;right=5484;bottom=164;connectionPoints=1;x1=3370;y1=160;x2=6350;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Pin;label=LT;anchorX=5420;anchorY=200;left=5406;top=186;right=5434;bottom=214;connectionPoints=1;x1=4220;y1=210;x2=6340;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Pin;label=LT;anchorX=5420;anchorY=200;left=5406;top=186;right=5434;bottom=214;connectionPoints=1;x1=630;y1=190;x2=5830;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_4bit;component=Pin;label=EQ;anchorX=5470;anchorY=150;left=5456;top=136;right=5484;bottom=164;connectionPoints=1;x1=770;y1=140;x2=5870;y2=140)

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
| artifact | generated\reports\comparator_4bit_multichip.md | true |
| artifact | generated\reports\comparator_4bit_official_simulation.json | true |
| artifact | generated\reports\comparator_4bit_official_simulation.md | true |
