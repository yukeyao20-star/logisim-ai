# Generation delivery report for barrel_shifter_2bit

- Request kind: natural_language
- Request: 2 bit barrel shifter
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\barrel_shifter_2bit.circ
- Summary: generated\reports\barrel_shifter_2bit_generation_summary.md
- Manifest: generated\reports\barrel_shifter_2bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 7
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 3
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 4
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-10) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,160) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,130) -> (210,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit barrel_shifter_2bit: (160,160) -> (2220,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit barrel_shifter_2bit: (610,140) -> (2250,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit barrel_shifter_2bit: (660,110) -> (2320,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit barrel_shifter_2bit: (930,90) -> (2330,90)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=barrel_shifter_2bit;x1=110;y1=-10;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=barrel_shifter_2bit;x1=160;y1=160;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=barrel_shifter_2bit;x1=210;y1=130;x2=210;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_2bit;component=Pin;label=Y1;anchorX=1870;anchorY=150;left=1856;top=136;right=1884;bottom=164;connectionPoints=1;x1=160;y1=160;x2=2220;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_2bit;component=Pin;label=Y1;anchorX=1870;anchorY=150;left=1856;top=136;right=1884;bottom=164;connectionPoints=1;x1=610;y1=140;x2=2250;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_2bit;component=Pin;label=Y0;anchorX=1920;anchorY=100;left=1906;top=86;right=1934;bottom=114;connectionPoints=1;x1=660;y1=110;x2=2320;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=barrel_shifter_2bit;component=Pin;label=Y0;anchorX=1920;anchorY=100;left=1906;top=86;right=1934;bottom=114;connectionPoints=1;x1=930;y1=90;x2=2330;y2=90)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\barrel_shifter_2bit.circ | true |
| manifest_json | generated\reports\barrel_shifter_2bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\barrel_shifter_2bit_circuit_manifest.md | true |
| truth_table_json | generated\reports\barrel_shifter_2bit_circuit_truth_table.json | true |
| truth_table_markdown | generated\reports\barrel_shifter_2bit_circuit_truth_table.md | true |
| delivery_json | generated\reports\barrel_shifter_2bit_delivery_report.json | true |
| delivery_markdown | generated\reports\barrel_shifter_2bit_delivery_report.md | true |
| summary_json | generated\reports\barrel_shifter_2bit_generation_summary.json | true |
| summary_markdown | generated\reports\barrel_shifter_2bit_generation_summary.md | true |
| artifact | generated\reports\barrel_shifter_2bit_multichip.md | true |
