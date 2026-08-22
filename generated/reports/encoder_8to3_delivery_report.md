# Generation delivery report for encoder_8to3

- Request kind: natural_language
- Request: priority encoder
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\encoder_8to3.circ
- Summary: generated\reports\encoder_8to3_generation_summary.md
- Manifest: generated\reports\encoder_8to3_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 9
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 5
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,130) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,170) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,210) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin A0 in circuit encoder_8to3: (110,90) -> (1380,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin A2 in circuit encoder_8to3: (260,210) -> (1440,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin nGS in circuit encoder_8to3: (310,240) -> (1460,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin nEO in circuit encoder_8to3: (360,290) -> (1480,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin nEO in circuit encoder_8to3: (410,310) -> (1500,310)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=encoder_8to3;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=encoder_8to3;x1=160;y1=130;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=encoder_8to3;x1=210;y1=170;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=encoder_8to3;x1=260;y1=210;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=A0;anchorX=1060;anchorY=100;left=1046;top=86;right=1074;bottom=114;connectionPoints=1;x1=110;y1=90;x2=1380;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=A2;anchorX=960;anchorY=200;left=946;top=186;right=974;bottom=214;connectionPoints=1;x1=260;y1=210;x2=1440;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=nGS;anchorX=910;anchorY=250;left=896;top=236;right=924;bottom=264;connectionPoints=1;x1=310;y1=240;x2=1460;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=nEO;anchorX=860;anchorY=300;left=846;top=286;right=874;bottom=314;connectionPoints=1;x1=360;y1=290;x2=1480;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=encoder_8to3;component=Pin;label=nEO;anchorX=860;anchorY=300;left=846;top=286;right=874;bottom=314;connectionPoints=1;x1=410;y1=310;x2=1500;y2=310)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\encoder_8to3.circ | true |
| manifest_json | generated\reports\encoder_8to3_circuit_manifest.json | true |
| manifest_markdown | generated\reports\encoder_8to3_circuit_manifest.md | true |
| delivery_json | generated\reports\encoder_8to3_delivery_report.json | true |
| delivery_markdown | generated\reports\encoder_8to3_delivery_report.md | true |
| summary_json | generated\reports\encoder_8to3_generation_summary.json | true |
| summary_markdown | generated\reports\encoder_8to3_generation_summary.md | true |
| artifact | generated\reports\encoder_8to3_multichip.md | true |
