# Generation delivery report for edge_detector

- Request kind: natural_language
- Request: 边沿检测器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\edge_detector.circ
- Summary: generated\reports\edge_detector_generation_summary.md
- Manifest: generated\reports\edge_detector_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 9
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 2
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 7
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,40) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,140) -> (160,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin FALL in circuit edge_detector: (160,140) -> (1740,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate A2 in circuit edge_detector: (520,240) -> (1750,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate N1 in circuit edge_detector: (520,240) -> (1750,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate A1 in circuit edge_detector: (520,240) -> (1750,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin FALL in circuit edge_detector: (730,160) -> (1790,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin RISE in circuit edge_detector: (830,90) -> (1830,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin RISE in circuit edge_detector: (880,110) -> (1850,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=edge_detector;x1=110;y1=40;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=edge_detector;x1=160;y1=140;x2=160;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=edge_detector;component=Pin;label=FALL;anchorX=1370;anchorY=150;left=1356;top=136;right=1384;bottom=164;connectionPoints=1;x1=160;y1=140;x2=1740;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=edge_detector;component=AND Gate;label=A2;anchorX=1000;anchorY=250;left=964;top=221;right=1036;bottom=279;connectionPoints=3;x1=520;y1=240;x2=1750;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=edge_detector;component=NOT Gate;label=N1;anchorX=760;anchorY=250;left=739;top=234;right=781;bottom=266;connectionPoints=2;x1=520;y1=240;x2=1750;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=edge_detector;component=AND Gate;label=A1;anchorX=880;anchorY=250;left=844;top=221;right=916;bottom=279;connectionPoints=3;x1=520;y1=240;x2=1750;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=edge_detector;component=Pin;label=FALL;anchorX=1370;anchorY=150;left=1356;top=136;right=1384;bottom=164;connectionPoints=1;x1=730;y1=160;x2=1790;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=edge_detector;component=Pin;label=RISE;anchorX=1420;anchorY=100;left=1406;top=86;right=1434;bottom=114;connectionPoints=1;x1=830;y1=90;x2=1830;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=edge_detector;component=Pin;label=RISE;anchorX=1420;anchorY=100;left=1406;top=86;right=1434;bottom=114;connectionPoints=1;x1=880;y1=110;x2=1850;y2=110)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\edge_detector.circ | true |
| manifest_json | generated\reports\edge_detector_circuit_manifest.json | true |
| manifest_markdown | generated\reports\edge_detector_circuit_manifest.md | true |
| delivery_json | generated\reports\edge_detector_delivery_report.json | true |
| delivery_markdown | generated\reports\edge_detector_delivery_report.md | true |
| summary_json | generated\reports\edge_detector_generation_summary.json | true |
| summary_markdown | generated\reports\edge_detector_generation_summary.md | true |
| artifact | generated\reports\edge_detector_multichip.md | true |
