# Generation delivery report for mux_8to1

- Request kind: natural_language
- Request: 8 to 1 mux
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\mux_8to1.circ
- Summary: generated\reports\mux_8to1_generation_summary.md
- Manifest: generated\reports\mux_8to1_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 17
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 13
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-340) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,-320) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,-310) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,-280) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y in circuit mux_8to1: (1090,110) -> (7050,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y in circuit mux_8to1: (1250,90) -> (7090,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate MUX1S1 in circuit mux_8to1: (560,240) -> (7400,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate MUX1S2 in circuit mux_8to1: (560,240) -> (7400,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate MUX2D in circuit mux_8to1: (560,240) -> (7400,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate MUX2S1 in circuit mux_8to1: (560,240) -> (7400,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate MUX2S2 in circuit mux_8to1: (560,240) -> (7400,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate MUX3D in circuit mux_8to1: (560,240) -> (7400,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate MUX3S1 in circuit mux_8to1: (560,240) -> (7400,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate MUX3S2 in circuit mux_8to1: (560,240) -> (7400,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate MUX0S1 in circuit mux_8to1: (560,240) -> (7400,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate MUX0S2 in circuit mux_8to1: (560,240) -> (7400,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate MUX1D in circuit mux_8to1: (560,240) -> (7400,240)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=mux_8to1;x1=110;y1=-340;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=mux_8to1;x1=160;y1=-320;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=mux_8to1;x1=210;y1=-310;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=mux_8to1;x1=260;y1=-280;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=Pin;label=Y;anchorX=6640;anchorY=100;left=6626;top=86;right=6654;bottom=114;connectionPoints=1;x1=1090;y1=110;x2=7050;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=Pin;label=Y;anchorX=6640;anchorY=100;left=6626;top=86;right=6654;bottom=114;connectionPoints=1;x1=1250;y1=90;x2=7090;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=AND Gate;label=MUX1S1;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=560;y1=240;x2=7400;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=AND Gate;label=MUX1S2;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=560;y1=240;x2=7400;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=AND Gate;label=MUX2D;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=560;y1=240;x2=7400;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=AND Gate;label=MUX2S1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=560;y1=240;x2=7400;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=AND Gate;label=MUX2S2;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=560;y1=240;x2=7400;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=AND Gate;label=MUX3D;anchorX=1940;anchorY=250;left=1904;top=221;right=1976;bottom=279;connectionPoints=3;x1=560;y1=240;x2=7400;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=AND Gate;label=MUX3S1;anchorX=2100;anchorY=250;left=2064;top=221;right=2136;bottom=279;connectionPoints=3;x1=560;y1=240;x2=7400;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=AND Gate;label=MUX3S2;anchorX=2260;anchorY=250;left=2224;top=221;right=2296;bottom=279;connectionPoints=3;x1=560;y1=240;x2=7400;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=AND Gate;label=MUX0S1;anchorX=660;anchorY=250;left=624;top=221;right=696;bottom=279;connectionPoints=3;x1=560;y1=240;x2=7400;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=AND Gate;label=MUX0S2;anchorX=820;anchorY=250;left=784;top=221;right=856;bottom=279;connectionPoints=3;x1=560;y1=240;x2=7400;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=mux_8to1;component=AND Gate;label=MUX1D;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=560;y1=240;x2=7400;y2=240)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\mux_8to1.circ | true |
| manifest_json | generated\reports\mux_8to1_circuit_manifest.json | true |
| manifest_markdown | generated\reports\mux_8to1_circuit_manifest.md | true |
| delivery_json | generated\reports\mux_8to1_delivery_report.json | true |
| delivery_markdown | generated\reports\mux_8to1_delivery_report.md | true |
| summary_json | generated\reports\mux_8to1_generation_summary.json | true |
| summary_markdown | generated\reports\mux_8to1_generation_summary.md | true |
| artifact | generated\reports\mux_8to1_multichip.md | true |
| artifact | generated\reports\mux_8to1_official_simulation.json | true |
| artifact | generated\reports\mux_8to1_official_simulation.md | true |
