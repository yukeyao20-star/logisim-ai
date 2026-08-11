# Generation delivery report for bcd2bin_2digit

- Request kind: natural_language
- Request: bcd转二进制
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\bcd2bin_2digit.circ
- Summary: generated\reports\bcd2bin_2digit_generation_summary.md
- Manifest: generated\reports\bcd2bin_2digit_circuit_manifest.md
- Complete: unknown
- Manifest check count: unknown
- Manifest issue count: unknown
- Layout clean: false
- Layout issue count: 18
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 4
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 14
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: review_manifest, fix_manifest_issues, regenerate_circuit, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,70) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,140) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,120) -> (210,250)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (260,-50) -> (260,300)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B5 in circuit bcd2bin_2digit: (1090,360) -> (6010,360)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B6 in circuit bcd2bin_2digit: (1560,390) -> (6130,390)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B1 in circuit bcd2bin_2digit: (160,140) -> (5840,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B6 in circuit bcd2bin_2digit: (1730,410) -> (6170,410)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B4 in circuit bcd2bin_2digit: (2560,290) -> (6350,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B3 in circuit bcd2bin_2digit: (2720,260) -> (6390,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B3 in circuit bcd2bin_2digit: (2890,240) -> (6430,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B2 in circuit bcd2bin_2digit: (2940,210) -> (6500,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B2 in circuit bcd2bin_2digit: (3210,190) -> (6510,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B4 in circuit bcd2bin_2digit: (440,310) -> (5850,310)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B1 in circuit bcd2bin_2digit: (610,160) -> (5870,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B0 in circuit bcd2bin_2digit: (820,110) -> (5940,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B5 in circuit bcd2bin_2digit: (920,340) -> (5970,340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin B0 in circuit bcd2bin_2digit: (920,90) -> (5950,90)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd2bin_2digit;x1=110;y1=70;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd2bin_2digit;x1=160;y1=140;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd2bin_2digit;x1=210;y1=120;x2=210;y2=250)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd2bin_2digit;x1=260;y1=-50;x2=260;y2=300)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B5;anchorX=5270;anchorY=350;left=5256;top=336;right=5284;bottom=364;connectionPoints=1;x1=1090;y1=360;x2=6010;y2=360)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B6;anchorX=5220;anchorY=400;left=5206;top=386;right=5234;bottom=414;connectionPoints=1;x1=1560;y1=390;x2=6130;y2=390)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B1;anchorX=5470;anchorY=150;left=5456;top=136;right=5484;bottom=164;connectionPoints=1;x1=160;y1=140;x2=5840;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B6;anchorX=5220;anchorY=400;left=5206;top=386;right=5234;bottom=414;connectionPoints=1;x1=1730;y1=410;x2=6170;y2=410)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B4;anchorX=5320;anchorY=300;left=5306;top=286;right=5334;bottom=314;connectionPoints=1;x1=2560;y1=290;x2=6350;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B3;anchorX=5370;anchorY=250;left=5356;top=236;right=5384;bottom=264;connectionPoints=1;x1=2720;y1=260;x2=6390;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B3;anchorX=5370;anchorY=250;left=5356;top=236;right=5384;bottom=264;connectionPoints=1;x1=2890;y1=240;x2=6430;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B2;anchorX=5420;anchorY=200;left=5406;top=186;right=5434;bottom=214;connectionPoints=1;x1=2940;y1=210;x2=6500;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B2;anchorX=5420;anchorY=200;left=5406;top=186;right=5434;bottom=214;connectionPoints=1;x1=3210;y1=190;x2=6510;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B4;anchorX=5320;anchorY=300;left=5306;top=286;right=5334;bottom=314;connectionPoints=1;x1=440;y1=310;x2=5850;y2=310)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B1;anchorX=5470;anchorY=150;left=5456;top=136;right=5484;bottom=164;connectionPoints=1;x1=610;y1=160;x2=5870;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B0;anchorX=5520;anchorY=100;left=5506;top=86;right=5534;bottom=114;connectionPoints=1;x1=820;y1=110;x2=5940;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B5;anchorX=5270;anchorY=350;left=5256;top=336;right=5284;bottom=364;connectionPoints=1;x1=920;y1=340;x2=5970;y2=340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd2bin_2digit;component=Pin;label=B0;anchorX=5520;anchorY=100;left=5506;top=86;right=5534;bottom=114;connectionPoints=1;x1=920;y1=90;x2=5950;y2=90)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\bcd2bin_2digit.circ | true |
| manifest_json | generated\reports\bcd2bin_2digit_circuit_manifest.json | false |
| manifest_markdown | generated\reports\bcd2bin_2digit_circuit_manifest.md | false |
| delivery_json | generated\reports\bcd2bin_2digit_delivery_report.json | true |
| delivery_markdown | generated\reports\bcd2bin_2digit_delivery_report.md | true |
| summary_json | generated\reports\bcd2bin_2digit_generation_summary.json | true |
| summary_markdown | generated\reports\bcd2bin_2digit_generation_summary.md | true |
| artifact | generated\reports\bcd2bin_2digit_multichip.md | true |
