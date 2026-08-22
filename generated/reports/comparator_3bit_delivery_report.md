# Generation delivery report for comparator_3bit

- Request kind: natural_language
- Request: 3 bit comparator
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\comparator_3bit.circ
- Summary: generated\reports\comparator_3bit_generation_summary.md
- Manifest: generated\reports\comparator_3bit_circuit_manifest.md
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
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,70) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,120) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,10) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin GT in circuit comparator_3bit: (110,110) -> (4500,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin GT in circuit comparator_3bit: (1110,90) -> (4610,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin LT in circuit comparator_3bit: (2620,210) -> (5040,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin EQ in circuit comparator_3bit: (3530,160) -> (5070,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin LT in circuit comparator_3bit: (630,190) -> (4510,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin EQ in circuit comparator_3bit: (770,140) -> (4550,140)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_3bit;x1=110;y1=110;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_3bit;x1=160;y1=70;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_3bit;x1=210;y1=120;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=comparator_3bit;x1=260;y1=10;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_3bit;component=Pin;label=GT;anchorX=4200;anchorY=100;left=4186;top=86;right=4214;bottom=114;connectionPoints=1;x1=110;y1=110;x2=4500;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_3bit;component=Pin;label=GT;anchorX=4200;anchorY=100;left=4186;top=86;right=4214;bottom=114;connectionPoints=1;x1=1110;y1=90;x2=4610;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_3bit;component=Pin;label=LT;anchorX=4100;anchorY=200;left=4086;top=186;right=4114;bottom=214;connectionPoints=1;x1=2620;y1=210;x2=5040;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_3bit;component=Pin;label=EQ;anchorX=4150;anchorY=150;left=4136;top=136;right=4164;bottom=164;connectionPoints=1;x1=3530;y1=160;x2=5070;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_3bit;component=Pin;label=LT;anchorX=4100;anchorY=200;left=4086;top=186;right=4114;bottom=214;connectionPoints=1;x1=630;y1=190;x2=4510;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=comparator_3bit;component=Pin;label=EQ;anchorX=4150;anchorY=150;left=4136;top=136;right=4164;bottom=164;connectionPoints=1;x1=770;y1=140;x2=4550;y2=140)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\comparator_3bit.circ | true |
| manifest_json | generated\reports\comparator_3bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\comparator_3bit_circuit_manifest.md | true |
| delivery_json | generated\reports\comparator_3bit_delivery_report.json | true |
| delivery_markdown | generated\reports\comparator_3bit_delivery_report.md | true |
| summary_json | generated\reports\comparator_3bit_generation_summary.json | true |
| summary_markdown | generated\reports\comparator_3bit_generation_summary.md | true |
| artifact | generated\reports\comparator_3bit_multichip.md | true |
