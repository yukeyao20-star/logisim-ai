# Generation delivery report for subtractor_1bit

- Request kind: natural_language
- Request: 1 bit subtractor
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\subtractor_1bit.circ
- Summary: generated\reports\subtractor_1bit_generation_summary.md
- Manifest: generated\reports\subtractor_1bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 5
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 2
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 3
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 12

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,70) -> (160,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D0 in circuit subtractor_1bit: (600,110) -> (2300,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin D0 in circuit subtractor_1bit: (660,90) -> (2330,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Bout in circuit subtractor_1bit: (930,140) -> (2270,140)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=subtractor_1bit;x1=110;y1=80;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=subtractor_1bit;x1=160;y1=70;x2=160;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_1bit;component=Pin;label=D0;anchorX=1920;anchorY=100;left=1906;top=86;right=1934;bottom=114;connectionPoints=1;x1=600;y1=110;x2=2300;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_1bit;component=Pin;label=D0;anchorX=1920;anchorY=100;left=1906;top=86;right=1934;bottom=114;connectionPoints=1;x1=660;y1=90;x2=2330;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=subtractor_1bit;component=Pin;label=Bout;anchorX=1870;anchorY=150;left=1856;top=136;right=1884;bottom=164;connectionPoints=1;x1=930;y1=140;x2=2270;y2=140)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\subtractor_1bit.circ | true |
| manifest_json | generated\reports\subtractor_1bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\subtractor_1bit_circuit_manifest.md | true |
| truth_table_json | generated\reports\subtractor_1bit_circuit_truth_table.json | true |
| truth_table_markdown | generated\reports\subtractor_1bit_circuit_truth_table.md | true |
| delivery_json | generated\reports\subtractor_1bit_delivery_report.json | true |
| delivery_markdown | generated\reports\subtractor_1bit_delivery_report.md | true |
| summary_json | generated\reports\subtractor_1bit_generation_summary.json | true |
| summary_markdown | generated\reports\subtractor_1bit_generation_summary.md | true |
| artifact | generated\reports\subtractor_1bit_multichip.md | true |
| artifact | generated\reports\subtractor_1bit_official_simulation.json | true |
| artifact | generated\reports\subtractor_1bit_official_simulation.md | true |
