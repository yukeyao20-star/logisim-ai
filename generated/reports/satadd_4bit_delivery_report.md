# Generation delivery report for satadd_4bit

- Request kind: natural_language
- Request: 饱和加法器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\satadd_4bit.circ
- Summary: generated\reports\satadd_4bit_generation_summary.md
- Manifest: generated\reports\satadd_4bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 12
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
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

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,30) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,10) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,-100) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit satadd_4bit: (110,90) -> (4600,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit satadd_4bit: (2060,260) -> (5110,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S3 in circuit satadd_4bit: (2190,240) -> (5150,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit satadd_4bit: (2240,210) -> (5220,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit satadd_4bit: (2360,160) -> (5240,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S2 in circuit satadd_4bit: (2430,190) -> (5230,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S1 in circuit satadd_4bit: (560,140) -> (4630,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin S0 in circuit satadd_4bit: (740,110) -> (4740,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=satadd_4bit;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=satadd_4bit;x1=160;y1=30;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=satadd_4bit;x1=210;y1=10;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=satadd_4bit;x1=260;y1=-100;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=satadd_4bit;component=Pin;label=S0;anchorX=4280;anchorY=100;left=4266;top=86;right=4294;bottom=114;connectionPoints=1;x1=110;y1=90;x2=4600;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=satadd_4bit;component=Pin;label=S3;anchorX=4130;anchorY=250;left=4116;top=236;right=4144;bottom=264;connectionPoints=1;x1=2060;y1=260;x2=5110;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=satadd_4bit;component=Pin;label=S3;anchorX=4130;anchorY=250;left=4116;top=236;right=4144;bottom=264;connectionPoints=1;x1=2190;y1=240;x2=5150;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=satadd_4bit;component=Pin;label=S2;anchorX=4180;anchorY=200;left=4166;top=186;right=4194;bottom=214;connectionPoints=1;x1=2240;y1=210;x2=5220;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=satadd_4bit;component=Pin;label=S1;anchorX=4230;anchorY=150;left=4216;top=136;right=4244;bottom=164;connectionPoints=1;x1=2360;y1=160;x2=5240;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=satadd_4bit;component=Pin;label=S2;anchorX=4180;anchorY=200;left=4166;top=186;right=4194;bottom=214;connectionPoints=1;x1=2430;y1=190;x2=5230;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=satadd_4bit;component=Pin;label=S1;anchorX=4230;anchorY=150;left=4216;top=136;right=4244;bottom=164;connectionPoints=1;x1=560;y1=140;x2=4630;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=satadd_4bit;component=Pin;label=S0;anchorX=4280;anchorY=100;left=4266;top=86;right=4294;bottom=114;connectionPoints=1;x1=740;y1=110;x2=4740;y2=110)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\satadd_4bit.circ | true |
| manifest_json | generated\reports\satadd_4bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\satadd_4bit_circuit_manifest.md | true |
| delivery_json | generated\reports\satadd_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\satadd_4bit_delivery_report.md | true |
| summary_json | generated\reports\satadd_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\satadd_4bit_generation_summary.md | true |
| artifact | generated\reports\satadd_4bit_multichip.md | true |
