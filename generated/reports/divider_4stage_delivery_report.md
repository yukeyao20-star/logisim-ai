# Generation delivery report for divider_4stage

- Request kind: natural_language
- Request: 分频器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\divider_4stage.circ
- Summary: generated\reports\divider_4stage_generation_summary.md
- Manifest: generated\reports\divider_4stage_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 7
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 2
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 5
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,110) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,130) -> (160,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit divider_4stage: (110,110) -> (2620,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit divider_4stage: (1280,90) -> (2820,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit divider_4stage: (490,210) -> (2630,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit divider_4stage: (750,140) -> (2710,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit divider_4stage: (780,190) -> (2750,190)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=divider_4stage;x1=110;y1=110;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=divider_4stage;x1=160;y1=130;x2=160;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divider_4stage;component=Pin;label=Q0;anchorX=2320;anchorY=100;left=2306;top=86;right=2334;bottom=114;connectionPoints=1;x1=110;y1=110;x2=2620;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divider_4stage;component=Pin;label=Q0;anchorX=2320;anchorY=100;left=2306;top=86;right=2334;bottom=114;connectionPoints=1;x1=1280;y1=90;x2=2820;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divider_4stage;component=Pin;label=Q2;anchorX=2220;anchorY=200;left=2206;top=186;right=2234;bottom=214;connectionPoints=1;x1=490;y1=210;x2=2630;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divider_4stage;component=Pin;label=Q1;anchorX=2270;anchorY=150;left=2256;top=136;right=2284;bottom=164;connectionPoints=1;x1=750;y1=140;x2=2710;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divider_4stage;component=Pin;label=Q2;anchorX=2220;anchorY=200;left=2206;top=186;right=2234;bottom=214;connectionPoints=1;x1=780;y1=190;x2=2750;y2=190)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\divider_4stage.circ | true |
| manifest_json | generated\reports\divider_4stage_circuit_manifest.json | false |
| manifest_markdown | generated\reports\divider_4stage_circuit_manifest.md | false |
| delivery_json | generated\reports\divider_4stage_delivery_report.json | true |
| delivery_markdown | generated\reports\divider_4stage_delivery_report.md | true |
| summary_json | generated\reports\divider_4stage_generation_summary.json | true |
| summary_markdown | generated\reports\divider_4stage_generation_summary.md | true |
| artifact | generated\reports\divider_4stage_multichip.md | true |
