# Generation delivery report for johnson_4stage

- Request kind: natural_language
- Request: 4 bit johnson counter
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\johnson_4stage.circ
- Summary: generated\reports\johnson_4stage_generation_summary.md
- Manifest: generated\reports\johnson_4stage_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 7
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 1
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 6
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,60) -> (110,150)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit johnson_4stage: (1170,90) -> (2490,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit johnson_4stage: (470,190) -> (2310,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit johnson_4stage: (680,210) -> (2370,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit johnson_4stage: (710,140) -> (2380,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit johnson_4stage: (940,160) -> (2450,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit johnson_4stage: (970,110) -> (2460,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=johnson_4stage;x1=110;y1=60;x2=110;y2=150)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=johnson_4stage;component=Pin;label=Q0;anchorX=2000;anchorY=100;left=1986;top=86;right=2014;bottom=114;connectionPoints=1;x1=1170;y1=90;x2=2490;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=johnson_4stage;component=Pin;label=Q2;anchorX=1900;anchorY=200;left=1886;top=186;right=1914;bottom=214;connectionPoints=1;x1=470;y1=190;x2=2310;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=johnson_4stage;component=Pin;label=Q2;anchorX=1900;anchorY=200;left=1886;top=186;right=1914;bottom=214;connectionPoints=1;x1=680;y1=210;x2=2370;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=johnson_4stage;component=Pin;label=Q1;anchorX=1950;anchorY=150;left=1936;top=136;right=1964;bottom=164;connectionPoints=1;x1=710;y1=140;x2=2380;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=johnson_4stage;component=Pin;label=Q1;anchorX=1950;anchorY=150;left=1936;top=136;right=1964;bottom=164;connectionPoints=1;x1=940;y1=160;x2=2450;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=johnson_4stage;component=Pin;label=Q0;anchorX=2000;anchorY=100;left=1986;top=86;right=2014;bottom=114;connectionPoints=1;x1=970;y1=110;x2=2460;y2=110)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\johnson_4stage.circ | true |
| manifest_json | generated\reports\johnson_4stage_circuit_manifest.json | false |
| manifest_markdown | generated\reports\johnson_4stage_circuit_manifest.md | false |
| delivery_json | generated\reports\johnson_4stage_delivery_report.json | true |
| delivery_markdown | generated\reports\johnson_4stage_delivery_report.md | true |
| summary_json | generated\reports\johnson_4stage_generation_summary.json | true |
| summary_markdown | generated\reports\johnson_4stage_generation_summary.md | true |
| artifact | generated\reports\johnson_4stage_multichip.md | true |
