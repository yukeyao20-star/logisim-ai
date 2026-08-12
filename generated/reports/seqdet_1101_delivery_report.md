# Generation delivery report for seqdet_1101

- Request kind: natural_language
- Request: 1101序列检测器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\seqdet_1101.circ
- Summary: generated\reports\seqdet_1101_generation_summary.md
- Manifest: generated\reports\seqdet_1101_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 4
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 2
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

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,50) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,-330) -> (160,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin F in circuit seqdet_1101: (1280,110) -> (5380,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin F in circuit seqdet_1101: (2920,90) -> (5940,90)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=seqdet_1101;x1=110;y1=50;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=seqdet_1101;x1=160;y1=-330;x2=160;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=seqdet_1101;component=Pin;label=F;anchorX=4980;anchorY=100;left=4966;top=86;right=4994;bottom=114;connectionPoints=1;x1=1280;y1=110;x2=5380;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=seqdet_1101;component=Pin;label=F;anchorX=4980;anchorY=100;left=4966;top=86;right=4994;bottom=114;connectionPoints=1;x1=2920;y1=90;x2=5940;y2=90)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\seqdet_1101.circ | true |
| manifest_json | generated\reports\seqdet_1101_circuit_manifest.json | false |
| manifest_markdown | generated\reports\seqdet_1101_circuit_manifest.md | false |
| delivery_json | generated\reports\seqdet_1101_delivery_report.json | true |
| delivery_markdown | generated\reports\seqdet_1101_delivery_report.md | true |
| summary_json | generated\reports\seqdet_1101_generation_summary.json | true |
| summary_markdown | generated\reports\seqdet_1101_generation_summary.md | true |
| artifact | generated\reports\seqdet_1101_multichip.md | true |
