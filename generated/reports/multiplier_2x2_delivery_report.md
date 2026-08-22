# Generation delivery report for multiplier_2x2

- Request kind: natural_language
- Request: 2 bit multiplier
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\multiplier_2x2.circ
- Summary: generated\reports\multiplier_2x2_generation_summary.md
- Manifest: generated\reports\multiplier_2x2_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 12
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 8
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 12

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,140) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,110) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,10) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin P1 in circuit multiplier_2x2: (160,140) -> (4840,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin P0 in circuit multiplier_2x2: (210,110) -> (4920,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin P3 in circuit multiplier_2x2: (2620,260) -> (5380,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin P3 in circuit multiplier_2x2: (2890,240) -> (5390,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin P2 in circuit multiplier_2x2: (2940,210) -> (5420,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin P2 in circuit multiplier_2x2: (3050,190) -> (5430,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin P1 in circuit multiplier_2x2: (3260,160) -> (5500,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin P0 in circuit multiplier_2x2: (930,90) -> (4950,90)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=multiplier_2x2;x1=110;y1=80;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=multiplier_2x2;x1=160;y1=140;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=multiplier_2x2;x1=210;y1=110;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=multiplier_2x2;x1=260;y1=10;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=multiplier_2x2;component=Pin;label=P1;anchorX=4470;anchorY=150;left=4456;top=136;right=4484;bottom=164;connectionPoints=1;x1=160;y1=140;x2=4840;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=multiplier_2x2;component=Pin;label=P0;anchorX=4520;anchorY=100;left=4506;top=86;right=4534;bottom=114;connectionPoints=1;x1=210;y1=110;x2=4920;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=multiplier_2x2;component=Pin;label=P3;anchorX=4370;anchorY=250;left=4356;top=236;right=4384;bottom=264;connectionPoints=1;x1=2620;y1=260;x2=5380;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=multiplier_2x2;component=Pin;label=P3;anchorX=4370;anchorY=250;left=4356;top=236;right=4384;bottom=264;connectionPoints=1;x1=2890;y1=240;x2=5390;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=multiplier_2x2;component=Pin;label=P2;anchorX=4420;anchorY=200;left=4406;top=186;right=4434;bottom=214;connectionPoints=1;x1=2940;y1=210;x2=5420;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=multiplier_2x2;component=Pin;label=P2;anchorX=4420;anchorY=200;left=4406;top=186;right=4434;bottom=214;connectionPoints=1;x1=3050;y1=190;x2=5430;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=multiplier_2x2;component=Pin;label=P1;anchorX=4470;anchorY=150;left=4456;top=136;right=4484;bottom=164;connectionPoints=1;x1=3260;y1=160;x2=5500;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=multiplier_2x2;component=Pin;label=P0;anchorX=4520;anchorY=100;left=4506;top=86;right=4534;bottom=114;connectionPoints=1;x1=930;y1=90;x2=4950;y2=90)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\multiplier_2x2.circ | true |
| manifest_json | generated\reports\multiplier_2x2_circuit_manifest.json | true |
| manifest_markdown | generated\reports\multiplier_2x2_circuit_manifest.md | true |
| truth_table_json | generated\reports\multiplier_2x2_circuit_truth_table.json | true |
| truth_table_markdown | generated\reports\multiplier_2x2_circuit_truth_table.md | true |
| delivery_json | generated\reports\multiplier_2x2_delivery_report.json | true |
| delivery_markdown | generated\reports\multiplier_2x2_delivery_report.md | true |
| summary_json | generated\reports\multiplier_2x2_generation_summary.json | true |
| summary_markdown | generated\reports\multiplier_2x2_generation_summary.md | true |
| artifact | generated\reports\multiplier_2x2_multichip.md | true |
| artifact | generated\reports\multiplier_2x2_official_simulation.json | true |
| artifact | generated\reports\multiplier_2x2_official_simulation.md | true |
