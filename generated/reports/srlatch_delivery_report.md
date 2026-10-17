# Generation delivery report for srlatch

- Request kind: natural_language
- Request: 抢答器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\srlatch.circ
- Summary: generated\reports\srlatch_generation_summary.md
- Manifest: generated\reports\srlatch_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 3
- Layout score: 30
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 1
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

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin nQ in circuit srlatch: (500,160) -> (1480,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin nQ in circuit srlatch: (600,140) -> (1470,140)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=srlatch;x1=110;y1=80;x2=110;y2=150)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=srlatch;component=Pin;label=nQ;anchorX=1070;anchorY=150;left=1056;top=136;right=1084;bottom=164;connectionPoints=1;x1=500;y1=160;x2=1480;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=srlatch;component=Pin;label=nQ;anchorX=1070;anchorY=150;left=1056;top=136;right=1084;bottom=164;connectionPoints=1;x1=600;y1=140;x2=1470;y2=140)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\srlatch.circ | true |
| manifest_json | generated\reports\srlatch_circuit_manifest.json | false |
| manifest_markdown | generated\reports\srlatch_circuit_manifest.md | false |
| delivery_json | generated\reports\srlatch_delivery_report.json | true |
| delivery_markdown | generated\reports\srlatch_delivery_report.md | true |
| summary_json | generated\reports\srlatch_generation_summary.json | true |
| summary_markdown | generated\reports\srlatch_generation_summary.md | true |
| artifact | generated\reports\srlatch_multichip.md | true |
