# Generation delivery report for decoder_3bit

- Request kind: natural_language
- Request: 三位译码器
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
- Layout issue count: 32
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 2
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 30
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,120) -> (160,200)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_1 in circuit decoder_3bit: (1000,30) -> (1000,620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_1 in circuit decoder_3bit: (1010,130) -> (1010,630)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_2 in circuit decoder_3bit: (1110,-10) -> (1110,670)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_2 in circuit decoder_3bit: (1120,90) -> (1120,680)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_2 in circuit decoder_3bit: (1150,-20) -> (1150,690)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_2 in circuit decoder_3bit: (1160,-30) -> (1160,690)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_2 in circuit decoder_3bit: (1170,50) -> (1170,700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_1 in circuit decoder_3bit: (1270,-100) -> (1270,740)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_1 in circuit decoder_3bit: (1280,50) -> (1280,750)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_1 in circuit decoder_3bit: (1310,-110) -> (1310,760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_1 in circuit decoder_3bit: (1320,-120) -> (1320,760)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_1 in circuit decoder_3bit: (1330,140) -> (1330,770)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_2 in circuit decoder_3bit: (1430,-170) -> (1430,810)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_2 in circuit decoder_3bit: (1440,60) -> (1440,820)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_2 in circuit decoder_3bit: (1450,-180) -> (1450,820)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_2 in circuit decoder_3bit: (1470,130) -> (1470,830)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC1_2 in circuit decoder_3bit: (1490,-190) -> (1490,840)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC2_1 in circuit decoder_3bit: (1600,-240) -> (1600,890)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC2_1 in circuit decoder_3bit: (1610,-250) -> (1610,890)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC2_1 in circuit decoder_3bit: (1630,120) -> (1630,900)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC2_1 in circuit decoder_3bit: (1650,-260) -> (1650,910)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC2_2 in circuit decoder_3bit: (1760,-310) -> (1760,960)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC2_2 in circuit decoder_3bit: (1770,-320) -> (1770,960)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate DECN3 in circuit decoder_3bit: (800,60) -> (800,540)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate DECN3 in circuit decoder_3bit: (810,190) -> (810,540)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate DECN3 in circuit decoder_3bit: (830,90) -> (830,550)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate DECN3 in circuit decoder_3bit: (840,180) -> (840,550)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_1 in circuit decoder_3bit: (960,40) -> (960,610)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_1 in circuit decoder_3bit: (970,-70) -> (970,610)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate DEC0_1 in circuit decoder_3bit: (990,60) -> (990,620)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=decoder_3bit;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=decoder_3bit;x1=160;y1=120;x2=160;y2=200)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_1;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=1000;y1=30;x2=1000;y2=620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_1;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=1010;y1=130;x2=1010;y2=630)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_2;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=1110;y1=-10;x2=1110;y2=670)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_2;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=1120;y1=90;x2=1120;y2=680)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_2;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=1150;y1=-20;x2=1150;y2=690)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_2;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=1160;y1=-30;x2=1160;y2=690)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_2;anchorX=1140;anchorY=250;left=1104;top=221;right=1176;bottom=279;connectionPoints=3;x1=1170;y1=50;x2=1170;y2=700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_1;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=1270;y1=-100;x2=1270;y2=740)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_1;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=1280;y1=50;x2=1280;y2=750)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_1;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=1310;y1=-110;x2=1310;y2=760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_1;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=1320;y1=-120;x2=1320;y2=760)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_1;anchorX=1300;anchorY=250;left=1264;top=221;right=1336;bottom=279;connectionPoints=3;x1=1330;y1=140;x2=1330;y2=770)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=1430;y1=-170;x2=1430;y2=810)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=1440;y1=60;x2=1440;y2=820)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=1450;y1=-180;x2=1450;y2=820)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=1470;y1=130;x2=1470;y2=830)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC1_2;anchorX=1460;anchorY=250;left=1424;top=221;right=1496;bottom=279;connectionPoints=3;x1=1490;y1=-190;x2=1490;y2=840)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC2_1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=1600;y1=-240;x2=1600;y2=890)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC2_1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=1610;y1=-250;x2=1610;y2=890)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC2_1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=1630;y1=120;x2=1630;y2=900)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC2_1;anchorX=1620;anchorY=250;left=1584;top=221;right=1656;bottom=279;connectionPoints=3;x1=1650;y1=-260;x2=1650;y2=910)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC2_2;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=1760;y1=-310;x2=1760;y2=960)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC2_2;anchorX=1780;anchorY=250;left=1744;top=221;right=1816;bottom=279;connectionPoints=3;x1=1770;y1=-320;x2=1770;y2=960)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=NOT Gate;label=DECN3;anchorX=820;anchorY=250;left=799;top=234;right=841;bottom=266;connectionPoints=2;x1=800;y1=60;x2=800;y2=540)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=NOT Gate;label=DECN3;anchorX=820;anchorY=250;left=799;top=234;right=841;bottom=266;connectionPoints=2;x1=810;y1=190;x2=810;y2=540)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=NOT Gate;label=DECN3;anchorX=820;anchorY=250;left=799;top=234;right=841;bottom=266;connectionPoints=2;x1=830;y1=90;x2=830;y2=550)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=NOT Gate;label=DECN3;anchorX=820;anchorY=250;left=799;top=234;right=841;bottom=266;connectionPoints=2;x1=840;y1=180;x2=840;y2=550)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_1;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=960;y1=40;x2=960;y2=610)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_1;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=970;y1=-70;x2=970;y2=610)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_3bit;component=AND Gate;label=DEC0_1;anchorX=980;anchorY=250;left=944;top=221;right=1016;bottom=279;connectionPoints=3;x1=990;y1=60;x2=990;y2=620)

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
