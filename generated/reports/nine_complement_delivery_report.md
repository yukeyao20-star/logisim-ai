# Generation delivery report for nine_complement

- Request kind: natural_language
- Request: 9的补码
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\nine_complement.circ
- Summary: generated\reports\nine_complement_generation_summary.md
- Manifest: generated\reports\nine_complement_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 5
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 3
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 2
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,70) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,90) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,20) -> (210,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin C1 in circuit nine_complement: (160,90) -> (1960,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin C4 in circuit nine_complement: (470,190) -> (1910,190)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=nine_complement;x1=110;y1=70;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=nine_complement;x1=160;y1=90;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=nine_complement;x1=210;y1=20;x2=210;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=nine_complement;component=Pin;label=C1;anchorX=1600;anchorY=100;left=1586;top=86;right=1614;bottom=114;connectionPoints=1;x1=160;y1=90;x2=1960;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=nine_complement;component=Pin;label=C4;anchorX=1500;anchorY=200;left=1486;top=186;right=1514;bottom=214;connectionPoints=1;x1=470;y1=190;x2=1910;y2=190)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\nine_complement.circ | true |
| manifest_json | generated\reports\nine_complement_circuit_manifest.json | false |
| manifest_markdown | generated\reports\nine_complement_circuit_manifest.md | false |
| delivery_json | generated\reports\nine_complement_delivery_report.json | true |
| delivery_markdown | generated\reports\nine_complement_delivery_report.md | true |
| summary_json | generated\reports\nine_complement_generation_summary.json | true |
| summary_markdown | generated\reports\nine_complement_generation_summary.md | true |
| artifact | generated\reports\nine_complement_multichip.md | true |
