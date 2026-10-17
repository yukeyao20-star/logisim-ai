# Generation delivery report for accumulator_4bit

- Request kind: json
- Request: generated/examples/accumulator_4bit_spec.json
- Intent: STRUCTURED_JSON
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: structured_circuit_spec
- Circuit: generated/circ/accumulator_4bit.circ
- Summary: generated/reports/accumulator_4bit_generation_summary.md
- Manifest: generated/reports/accumulator_4bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 31
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 18
- Layout wire risk count: 0
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 13
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Increase grid spacing, reassign crowded component rows, or rerun layout with wider component clearance. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: compare_source_json, open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (1010,170) and (1040,180): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (1070,130) and (1120,130): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (1120,130) and (1130,130): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (1330,170) and (1360,180): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (1390,130) and (1440,130): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (1440,130) and (1450,130): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (370,170) and (400,180): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (380,130) and (430,130): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (380,210) and (400,180): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (400,180) and (440,210): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (430,130) and (440,130): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (430,130) and (480,130): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (440,130) and (480,130): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (440,130) and (490,130): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (480,130) and (490,130): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (690,170) and (720,180): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (750,130) and (800,130): Tunnel and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit accumulator_4bit at (800,130) and (810,130): Tunnel and Tunnel
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n59 in circuit accumulator_4bit: (1010,170) -> (1030,170)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n30 in circuit accumulator_4bit: (1090,130) -> (1120,130)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n63 in circuit accumulator_4bit: (1330,170) -> (1350,170)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n42 in circuit accumulator_4bit: (1410,130) -> (1440,130)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n51 in circuit accumulator_4bit: (370,170) -> (390,170)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n58 in circuit accumulator_4bit: (390,130) -> (430,130)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n62 in circuit accumulator_4bit: (390,130) -> (430,130)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n2 in circuit accumulator_4bit: (440,130) -> (450,130)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n62 in circuit accumulator_4bit: (450,110) -> (450,130)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n62 in circuit accumulator_4bit: (450,130) -> (480,130)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n6 in circuit accumulator_4bit: (450,130) -> (480,130)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n55 in circuit accumulator_4bit: (690,170) -> (710,170)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Tunnel n18 in circuit accumulator_4bit: (770,130) -> (800,130)

## Layout Repair Suggestions

- [increase_spacing/OVERLAP] Increase grid spacing, reassign crowded component rows, or rerun layout with wider component clearance. (minClearanceX=80;minClearanceY=50)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n59;anchorX=1040;anchorY=180;left=1023;top=168;right=1057;bottom=192;connectionPoints=1;x1=1010;y1=170;x2=1030;y2=170)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n30;anchorX=1130;anchorY=130;left=1113;top=118;right=1147;bottom=142;connectionPoints=1;x1=1090;y1=130;x2=1120;y2=130)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n63;anchorX=1360;anchorY=180;left=1343;top=168;right=1377;bottom=192;connectionPoints=1;x1=1330;y1=170;x2=1350;y2=170)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n42;anchorX=1450;anchorY=130;left=1433;top=118;right=1467;bottom=142;connectionPoints=1;x1=1410;y1=130;x2=1440;y2=130)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n51;anchorX=400;anchorY=180;left=383;top=168;right=417;bottom=192;connectionPoints=1;x1=370;y1=170;x2=390;y2=170)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n58;anchorX=380;anchorY=130;left=363;top=118;right=397;bottom=142;connectionPoints=1;x1=390;y1=130;x2=430;y2=130)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n62;anchorX=440;anchorY=130;left=423;top=118;right=457;bottom=142;connectionPoints=1;x1=390;y1=130;x2=430;y2=130)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n2;anchorX=430;anchorY=130;left=417;top=118;right=443;bottom=142;connectionPoints=1;x1=440;y1=130;x2=450;y2=130)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n62;anchorX=440;anchorY=130;left=423;top=118;right=457;bottom=142;connectionPoints=1;x1=450;y1=110;x2=450;y2=130)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n62;anchorX=440;anchorY=130;left=423;top=118;right=457;bottom=142;connectionPoints=1;x1=450;y1=130;x2=480;y2=130)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n6;anchorX=490;anchorY=130;left=477;top=118;right=503;bottom=142;connectionPoints=1;x1=450;y1=130;x2=480;y2=130)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n55;anchorX=720;anchorY=180;left=703;top=168;right=737;bottom=192;connectionPoints=1;x1=690;y1=170;x2=710;y2=170)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=accumulator_4bit;component=Tunnel;label=n18;anchorX=810;anchorY=130;left=793;top=118;right=827;bottom=142;connectionPoints=1;x1=770;y1=130;x2=800;y2=130)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/accumulator_4bit.circ | true |
| manifest_json | generated/reports/accumulator_4bit_circuit_manifest.json | true |
| manifest_markdown | generated/reports/accumulator_4bit_circuit_manifest.md | true |
| delivery_json | generated/reports/accumulator_4bit_delivery_report.json | true |
| delivery_markdown | generated/reports/accumulator_4bit_delivery_report.md | true |
| summary_json | generated/reports/accumulator_4bit_generation_summary.json | true |
| summary_markdown | generated/reports/accumulator_4bit_generation_summary.md | true |
| artifact | generated/reports/accumulator_4bit_multichip.md | true |
| artifact | generated/reports/accumulator_4bit_official_simulation.json | true |
| artifact | generated/reports/accumulator_4bit_official_simulation.md | true |
