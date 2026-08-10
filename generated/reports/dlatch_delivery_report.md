# Generation delivery report for dlatch

- Request kind: natural_language
- Request: D锁存器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\dlatch.circ
- Summary: generated\reports\dlatch_generation_summary.md
- Manifest: generated\reports\dlatch_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 5
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 1
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 4
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q in circuit dlatch: (110,90) -> (1940,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin nQ in circuit dlatch: (600,160) -> (1950,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin nQ in circuit dlatch: (760,140) -> (1990,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q in circuit dlatch: (820,110) -> (2020,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=dlatch;x1=110;y1=90;x2=110;y2=150)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=dlatch;component=Pin;label=Q;anchorX=1600;anchorY=100;left=1586;top=86;right=1614;bottom=114;connectionPoints=1;x1=110;y1=90;x2=1940;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=dlatch;component=Pin;label=nQ;anchorX=1550;anchorY=150;left=1536;top=136;right=1564;bottom=164;connectionPoints=1;x1=600;y1=160;x2=1950;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=dlatch;component=Pin;label=nQ;anchorX=1550;anchorY=150;left=1536;top=136;right=1564;bottom=164;connectionPoints=1;x1=760;y1=140;x2=1990;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=dlatch;component=Pin;label=Q;anchorX=1600;anchorY=100;left=1586;top=86;right=1614;bottom=114;connectionPoints=1;x1=820;y1=110;x2=2020;y2=110)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\dlatch.circ | true |
| manifest_json | generated\reports\dlatch_circuit_manifest.json | false |
| manifest_markdown | generated\reports\dlatch_circuit_manifest.md | false |
| delivery_json | generated\reports\dlatch_delivery_report.json | true |
| delivery_markdown | generated\reports\dlatch_delivery_report.md | true |
| summary_json | generated\reports\dlatch_generation_summary.json | true |
| summary_markdown | generated\reports\dlatch_generation_summary.md | true |
| artifact | generated\reports\dlatch_multichip.md | true |
