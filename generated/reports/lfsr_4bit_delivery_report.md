# Generation delivery report for lfsr_4bit

- Request kind: natural_language
- Request: 伪随机数发生器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\lfsr_4bit.circ
- Summary: generated\reports\lfsr_4bit_generation_summary.md
- Manifest: generated\reports\lfsr_4bit_circuit_manifest.md
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

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,80) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,20) -> (160,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit lfsr_4bit: (1070,190) -> (2480,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit lfsr_4bit: (1540,160) -> (2520,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit lfsr_4bit: (1650,140) -> (2510,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit lfsr_4bit: (550,110) -> (2560,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit lfsr_4bit: (750,90) -> (2570,90)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=lfsr_4bit;x1=110;y1=80;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=lfsr_4bit;x1=160;y1=20;x2=160;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=lfsr_4bit;component=Pin;label=Q2;anchorX=2060;anchorY=200;left=2046;top=186;right=2074;bottom=214;connectionPoints=1;x1=1070;y1=190;x2=2480;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=lfsr_4bit;component=Pin;label=Q1;anchorX=2110;anchorY=150;left=2096;top=136;right=2124;bottom=164;connectionPoints=1;x1=1540;y1=160;x2=2520;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=lfsr_4bit;component=Pin;label=Q1;anchorX=2110;anchorY=150;left=2096;top=136;right=2124;bottom=164;connectionPoints=1;x1=1650;y1=140;x2=2510;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=lfsr_4bit;component=Pin;label=Q0;anchorX=2160;anchorY=100;left=2146;top=86;right=2174;bottom=114;connectionPoints=1;x1=550;y1=110;x2=2560;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=lfsr_4bit;component=Pin;label=Q0;anchorX=2160;anchorY=100;left=2146;top=86;right=2174;bottom=114;connectionPoints=1;x1=750;y1=90;x2=2570;y2=90)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\lfsr_4bit.circ | true |
| manifest_json | generated\reports\lfsr_4bit_circuit_manifest.json | false |
| manifest_markdown | generated\reports\lfsr_4bit_circuit_manifest.md | false |
| delivery_json | generated\reports\lfsr_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\lfsr_4bit_delivery_report.md | true |
| summary_json | generated\reports\lfsr_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\lfsr_4bit_generation_summary.md | true |
| artifact | generated\reports\lfsr_4bit_multichip.md | true |
