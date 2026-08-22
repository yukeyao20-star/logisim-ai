# Generation delivery report for mux_4to1

- Request kind: natural_language
- Request: 4 to 1 mux
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\mux_4to1.circ
- Summary: generated\reports\mux_4to1_generation_summary.md
- Manifest: generated\reports\mux_4to1_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 6
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 2
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 12

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-70) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,-50) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,-40) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,220) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y in circuit mux_4to1: (1300,110) -> (3540,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y in circuit mux_4to1: (1570,90) -> (3570,90)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=mux_4to1;x1=110;y1=-70;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=mux_4to1;x1=160;y1=-50;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=mux_4to1;x1=210;y1=-40;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=mux_4to1;x1=260;y1=220;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_4to1;component=Pin;label=Y;anchorX=3080;anchorY=100;left=3066;top=86;right=3094;bottom=114;connectionPoints=1;x1=1300;y1=110;x2=3540;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_4to1;component=Pin;label=Y;anchorX=3080;anchorY=100;left=3066;top=86;right=3094;bottom=114;connectionPoints=1;x1=1570;y1=90;x2=3570;y2=90)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mux_4to1.circ | true |
| manifest_json | generated\reports\mux_4to1_circuit_manifest.json | true |
| manifest_markdown | generated\reports\mux_4to1_circuit_manifest.md | true |
| truth_table_json | generated\reports\mux_4to1_circuit_truth_table.json | true |
| truth_table_markdown | generated\reports\mux_4to1_circuit_truth_table.md | true |
| delivery_json | generated\reports\mux_4to1_delivery_report.json | true |
| delivery_markdown | generated\reports\mux_4to1_delivery_report.md | true |
| summary_json | generated\reports\mux_4to1_generation_summary.json | true |
| summary_markdown | generated\reports\mux_4to1_generation_summary.md | true |
| artifact | generated\reports\mux_4to1_multichip.md | true |
| artifact | generated\reports\mux_4to1_official_simulation.json | true |
| artifact | generated\reports\mux_4to1_official_simulation.md | true |
