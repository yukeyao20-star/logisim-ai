# Generation delivery report for freq_meter

- Request kind: natural_language
- Request: 频率计
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\freq_meter.circ
- Summary: generated\reports\freq_meter_generation_summary.md
- Manifest: generated\reports\freq_meter_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 10
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 2
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 8
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,-690) -> (160,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin F0 in circuit freq_meter: (1050,90) -> (9280,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin F1 in circuit freq_meter: (1250,140) -> (9230,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin F2 in circuit freq_meter: (1520,210) -> (9180,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin F1 in circuit freq_meter: (1760,160) -> (9220,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin F3 in circuit freq_meter: (3080,240) -> (9540,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin WIN in circuit freq_meter: (3580,290) -> (9520,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin F0 in circuit freq_meter: (450,110) -> (9270,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin F2 in circuit freq_meter: (730,190) -> (9190,190)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=freq_meter;x1=110;y1=-90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=freq_meter;x1=160;y1=-690;x2=160;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=freq_meter;component=Pin;label=F0;anchorX=8880;anchorY=100;left=8866;top=86;right=8894;bottom=114;connectionPoints=1;x1=1050;y1=90;x2=9280;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=freq_meter;component=Pin;label=F1;anchorX=8830;anchorY=150;left=8816;top=136;right=8844;bottom=164;connectionPoints=1;x1=1250;y1=140;x2=9230;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=freq_meter;component=Pin;label=F2;anchorX=8780;anchorY=200;left=8766;top=186;right=8794;bottom=214;connectionPoints=1;x1=1520;y1=210;x2=9180;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=freq_meter;component=Pin;label=F1;anchorX=8830;anchorY=150;left=8816;top=136;right=8844;bottom=164;connectionPoints=1;x1=1760;y1=160;x2=9220;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=freq_meter;component=Pin;label=F3;anchorX=8730;anchorY=250;left=8716;top=236;right=8744;bottom=264;connectionPoints=1;x1=3080;y1=240;x2=9540;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=freq_meter;component=Pin;label=WIN;anchorX=8680;anchorY=300;left=8666;top=286;right=8694;bottom=314;connectionPoints=1;x1=3580;y1=290;x2=9520;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=freq_meter;component=Pin;label=F0;anchorX=8880;anchorY=100;left=8866;top=86;right=8894;bottom=114;connectionPoints=1;x1=450;y1=110;x2=9270;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=freq_meter;component=Pin;label=F2;anchorX=8780;anchorY=200;left=8766;top=186;right=8794;bottom=214;connectionPoints=1;x1=730;y1=190;x2=9190;y2=190)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\freq_meter.circ | true |
| manifest_json | generated\reports\freq_meter_circuit_manifest.json | true |
| manifest_markdown | generated\reports\freq_meter_circuit_manifest.md | true |
| delivery_json | generated\reports\freq_meter_delivery_report.json | true |
| delivery_markdown | generated\reports\freq_meter_delivery_report.md | true |
| summary_json | generated\reports\freq_meter_generation_summary.json | true |
| summary_markdown | generated\reports\freq_meter_generation_summary.md | true |
| artifact | generated\reports\freq_meter_multichip.md | true |
