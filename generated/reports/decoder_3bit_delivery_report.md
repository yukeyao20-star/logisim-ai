# Generation delivery report for decoder_3bit

- Request kind: natural_language
- Request: 3 to 8 decoder
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\decoder_3bit.circ
- Summary: generated\reports\decoder_3bit_generation_summary.md
- Manifest: generated\reports\decoder_3bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 16
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 2
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 14
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 12

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,120) -> (160,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit decoder_3bit: (110,90) -> (4360,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y5 in circuit decoder_3bit: (1410,340) -> (4570,340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y5 in circuit decoder_3bit: (1570,360) -> (4630,360)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y6 in circuit decoder_3bit: (2050,390) -> (4770,390)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y6 in circuit decoder_3bit: (2210,410) -> (4830,410)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y4 in circuit decoder_3bit: (2570,290) -> (4850,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y3 in circuit decoder_3bit: (2730,260) -> (4910,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y3 in circuit decoder_3bit: (2780,240) -> (4940,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y2 in circuit decoder_3bit: (2940,210) -> (4980,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y2 in circuit decoder_3bit: (470,190) -> (4350,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit decoder_3bit: (500,140) -> (4400,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit decoder_3bit: (790,160) -> (4390,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit decoder_3bit: (930,110) -> (4410,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y4 in circuit decoder_3bit: (930,310) -> (4430,310)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=decoder_3bit;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=decoder_3bit;x1=160;y1=120;x2=160;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y0;anchorX=4040;anchorY=100;left=4026;top=86;right=4054;bottom=114;connectionPoints=1;x1=110;y1=90;x2=4360;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y5;anchorX=3790;anchorY=350;left=3776;top=336;right=3804;bottom=364;connectionPoints=1;x1=1410;y1=340;x2=4570;y2=340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y5;anchorX=3790;anchorY=350;left=3776;top=336;right=3804;bottom=364;connectionPoints=1;x1=1570;y1=360;x2=4630;y2=360)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y6;anchorX=3740;anchorY=400;left=3726;top=386;right=3754;bottom=414;connectionPoints=1;x1=2050;y1=390;x2=4770;y2=390)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y6;anchorX=3740;anchorY=400;left=3726;top=386;right=3754;bottom=414;connectionPoints=1;x1=2210;y1=410;x2=4830;y2=410)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y4;anchorX=3840;anchorY=300;left=3826;top=286;right=3854;bottom=314;connectionPoints=1;x1=2570;y1=290;x2=4850;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y3;anchorX=3890;anchorY=250;left=3876;top=236;right=3904;bottom=264;connectionPoints=1;x1=2730;y1=260;x2=4910;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y3;anchorX=3890;anchorY=250;left=3876;top=236;right=3904;bottom=264;connectionPoints=1;x1=2780;y1=240;x2=4940;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y2;anchorX=3940;anchorY=200;left=3926;top=186;right=3954;bottom=214;connectionPoints=1;x1=2940;y1=210;x2=4980;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y2;anchorX=3940;anchorY=200;left=3926;top=186;right=3954;bottom=214;connectionPoints=1;x1=470;y1=190;x2=4350;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y1;anchorX=3990;anchorY=150;left=3976;top=136;right=4004;bottom=164;connectionPoints=1;x1=500;y1=140;x2=4400;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y1;anchorX=3990;anchorY=150;left=3976;top=136;right=4004;bottom=164;connectionPoints=1;x1=790;y1=160;x2=4390;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y0;anchorX=4040;anchorY=100;left=4026;top=86;right=4054;bottom=114;connectionPoints=1;x1=930;y1=110;x2=4410;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=Pin;label=Y4;anchorX=3840;anchorY=300;left=3826;top=286;right=3854;bottom=314;connectionPoints=1;x1=930;y1=310;x2=4430;y2=310)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\decoder_3bit.circ | true |
| manifest_json | generated\reports\decoder_3bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\decoder_3bit_circuit_manifest.md | true |
| truth_table_json | generated\reports\decoder_3bit_circuit_truth_table.json | true |
| truth_table_markdown | generated\reports\decoder_3bit_circuit_truth_table.md | true |
| delivery_json | generated\reports\decoder_3bit_delivery_report.json | true |
| delivery_markdown | generated\reports\decoder_3bit_delivery_report.md | true |
| summary_json | generated\reports\decoder_3bit_generation_summary.json | true |
| summary_markdown | generated\reports\decoder_3bit_generation_summary.md | true |
| artifact | generated\reports\decoder_3bit_multichip.md | true |
| artifact | generated\reports\decoder_3bit_official_simulation.json | true |
| artifact | generated\reports\decoder_3bit_official_simulation.md | true |
