# Generation delivery report for twoscomp_4bit

- Request kind: natural_language
- Request: 4 bit 2s complement
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\twoscomp_4bit.circ
- Summary: generated\reports\twoscomp_4bit_generation_summary.md
- Manifest: generated\reports\twoscomp_4bit_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 8
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 3
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

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,120) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,140) -> (210,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B0 in circuit twoscomp_4bit: (110,90) -> (2720,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B1 in circuit twoscomp_4bit: (210,140) -> (2760,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B2 in circuit twoscomp_4bit: (470,190) -> (2710,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B0 in circuit twoscomp_4bit: (660,110) -> (2800,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B1 in circuit twoscomp_4bit: (790,160) -> (2750,160)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=twoscomp_4bit;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=twoscomp_4bit;x1=160;y1=120;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=twoscomp_4bit;x1=210;y1=140;x2=210;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=twoscomp_4bit;component=Pin;label=B0;anchorX=2400;anchorY=100;left=2386;top=86;right=2414;bottom=114;connectionPoints=1;x1=110;y1=90;x2=2720;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=twoscomp_4bit;component=Pin;label=B1;anchorX=2350;anchorY=150;left=2336;top=136;right=2364;bottom=164;connectionPoints=1;x1=210;y1=140;x2=2760;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=twoscomp_4bit;component=Pin;label=B2;anchorX=2300;anchorY=200;left=2286;top=186;right=2314;bottom=214;connectionPoints=1;x1=470;y1=190;x2=2710;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=twoscomp_4bit;component=Pin;label=B0;anchorX=2400;anchorY=100;left=2386;top=86;right=2414;bottom=114;connectionPoints=1;x1=660;y1=110;x2=2800;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=twoscomp_4bit;component=Pin;label=B1;anchorX=2350;anchorY=150;left=2336;top=136;right=2364;bottom=164;connectionPoints=1;x1=790;y1=160;x2=2750;y2=160)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\twoscomp_4bit.circ | true |
| manifest_json | generated\reports\twoscomp_4bit_circuit_manifest.json | false |
| manifest_markdown | generated\reports\twoscomp_4bit_circuit_manifest.md | false |
| delivery_json | generated\reports\twoscomp_4bit_delivery_report.json | true |
| delivery_markdown | generated\reports\twoscomp_4bit_delivery_report.md | true |
| summary_json | generated\reports\twoscomp_4bit_generation_summary.json | true |
| summary_markdown | generated\reports\twoscomp_4bit_generation_summary.md | true |
| artifact | generated\reports\twoscomp_4bit_multichip.md | true |
