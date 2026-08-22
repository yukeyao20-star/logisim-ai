# Generation delivery report for encoder_4to2

- Request kind: natural_language
- Request: 4 to 2 priority encoder
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\encoder_4to2.circ
- Summary: generated\reports\encoder_4to2_generation_summary.md
- Manifest: generated\reports\encoder_4to2_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 8
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 3
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 5
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,20) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,90) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,190) -> (210,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit encoder_4to2: (160,90) -> (1890,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G in circuit encoder_4to2: (210,190) -> (1840,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit encoder_4to2: (450,140) -> (1870,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin G in circuit encoder_4to2: (620,210) -> (1820,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit encoder_4to2: (830,160) -> (1880,160)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=encoder_4to2;x1=110;y1=20;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=encoder_4to2;x1=160;y1=90;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=encoder_4to2;x1=210;y1=190;x2=210;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_4to2;component=Pin;label=Y1;anchorX=1520;anchorY=100;left=1506;top=86;right=1534;bottom=114;connectionPoints=1;x1=160;y1=90;x2=1890;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_4to2;component=Pin;label=G;anchorX=1420;anchorY=200;left=1406;top=186;right=1434;bottom=214;connectionPoints=1;x1=210;y1=190;x2=1840;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_4to2;component=Pin;label=Y0;anchorX=1470;anchorY=150;left=1456;top=136;right=1484;bottom=164;connectionPoints=1;x1=450;y1=140;x2=1870;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_4to2;component=Pin;label=G;anchorX=1420;anchorY=200;left=1406;top=186;right=1434;bottom=214;connectionPoints=1;x1=620;y1=210;x2=1820;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_4to2;component=Pin;label=Y0;anchorX=1470;anchorY=150;left=1456;top=136;right=1484;bottom=164;connectionPoints=1;x1=830;y1=160;x2=1880;y2=160)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\encoder_4to2.circ | true |
| manifest_json | generated\reports\encoder_4to2_circuit_manifest.json | true |
| manifest_markdown | generated\reports\encoder_4to2_circuit_manifest.md | true |
| truth_table_json | generated\reports\encoder_4to2_circuit_truth_table.json | true |
| truth_table_markdown | generated\reports\encoder_4to2_circuit_truth_table.md | true |
| delivery_json | generated\reports\encoder_4to2_delivery_report.json | true |
| delivery_markdown | generated\reports\encoder_4to2_delivery_report.md | true |
| summary_json | generated\reports\encoder_4to2_generation_summary.json | true |
| summary_markdown | generated\reports\encoder_4to2_generation_summary.md | true |
| artifact | generated\reports\encoder_4to2_multichip.md | true |
