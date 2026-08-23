# Generation delivery report for divby_60

- Request kind: natural_language
- Request: 六十分频
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\divby_60.circ
- Summary: generated\reports\divby_60_generation_summary.md
- Manifest: generated\reports\divby_60_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 24
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 1
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 23
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 8

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,-220) -> (110,150)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit divby_60: (1610,140) -> (6580,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q7 in circuit divby_60: (1610,510) -> (7430,510)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin TC1 in circuit divby_60: (1730,540) -> (7470,540)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit divby_60: (1730,90) -> (6670,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate S0M2 in circuit divby_60: (1850,660) -> (7560,660)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate S0O2 in circuit divby_60: (1850,660) -> (7560,660)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate S0R3 in circuit divby_60: (1850,660) -> (7560,660)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate S0M3 in circuit divby_60: (1850,660) -> (7560,660)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate S0O3 in circuit divby_60: (1850,660) -> (7560,660)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate S1A1 in circuit divby_60: (1850,660) -> (7560,660)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of NOT Gate S1N2 in circuit divby_60: (1850,660) -> (7560,660)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate S1A2 in circuit divby_60: (1850,660) -> (7560,660)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q4 in circuit divby_60: (2210,340) -> (6730,340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin TC0 in circuit divby_60: (3180,290) -> (7060,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q6 in circuit divby_60: (3210,460) -> (6790,460)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q5 in circuit divby_60: (3470,410) -> (6840,410)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q3 in circuit divby_60: (3700,240) -> (7080,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit divby_60: (3990,210) -> (7100,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q2 in circuit divby_60: (4690,190) -> (7630,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin TC in circuit divby_60: (4740,590) -> (7130,590)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q1 in circuit divby_60: (4980,160) -> (7660,160)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Q0 in circuit divby_60: (6040,110) -> (6710,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin TC in circuit divby_60: (750,610) -> (7520,610)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=divby_60;x1=110;y1=-220;x2=110;y2=150)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=Q1;anchorX=6190;anchorY=150;left=6176;top=136;right=6204;bottom=164;connectionPoints=1;x1=1610;y1=140;x2=6580;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=Q7;anchorX=5840;anchorY=500;left=5826;top=486;right=5854;bottom=514;connectionPoints=1;x1=1610;y1=510;x2=7430;y2=510)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=TC1;anchorX=5790;anchorY=550;left=5776;top=536;right=5804;bottom=564;connectionPoints=1;x1=1730;y1=540;x2=7470;y2=540)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=Q0;anchorX=6240;anchorY=100;left=6226;top=86;right=6254;bottom=114;connectionPoints=1;x1=1730;y1=90;x2=6670;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=AND Gate;label=S0M2;anchorX=2560;anchorY=670;left=2524;top=641;right=2596;bottom=699;connectionPoints=3;x1=1850;y1=660;x2=7560;y2=660)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=OR Gate;label=S0O2;anchorX=2680;anchorY=670;left=2644;top=641;right=2716;bottom=699;connectionPoints=3;x1=1850;y1=660;x2=7560;y2=660)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=AND Gate;label=S0R3;anchorX=2800;anchorY=670;left=2764;top=641;right=2836;bottom=699;connectionPoints=3;x1=1850;y1=660;x2=7560;y2=660)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=AND Gate;label=S0M3;anchorX=2920;anchorY=670;left=2884;top=641;right=2956;bottom=699;connectionPoints=3;x1=1850;y1=660;x2=7560;y2=660)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=OR Gate;label=S0O3;anchorX=3040;anchorY=670;left=3004;top=641;right=3076;bottom=699;connectionPoints=3;x1=1850;y1=660;x2=7560;y2=660)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=AND Gate;label=S1A1;anchorX=4200;anchorY=670;left=4164;top=641;right=4236;bottom=699;connectionPoints=3;x1=1850;y1=660;x2=7560;y2=660)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=NOT Gate;label=S1N2;anchorX=4320;anchorY=670;left=4299;top=654;right=4341;bottom=686;connectionPoints=2;x1=1850;y1=660;x2=7560;y2=660)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=AND Gate;label=S1A2;anchorX=4440;anchorY=670;left=4404;top=641;right=4476;bottom=699;connectionPoints=3;x1=1850;y1=660;x2=7560;y2=660)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=Q4;anchorX=5990;anchorY=350;left=5976;top=336;right=6004;bottom=364;connectionPoints=1;x1=2210;y1=340;x2=6730;y2=340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=TC0;anchorX=6040;anchorY=300;left=6026;top=286;right=6054;bottom=314;connectionPoints=1;x1=3180;y1=290;x2=7060;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=Q6;anchorX=5890;anchorY=450;left=5876;top=436;right=5904;bottom=464;connectionPoints=1;x1=3210;y1=460;x2=6790;y2=460)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=Q5;anchorX=5940;anchorY=400;left=5926;top=386;right=5954;bottom=414;connectionPoints=1;x1=3470;y1=410;x2=6840;y2=410)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=Q3;anchorX=6090;anchorY=250;left=6076;top=236;right=6104;bottom=264;connectionPoints=1;x1=3700;y1=240;x2=7080;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=Q2;anchorX=6140;anchorY=200;left=6126;top=186;right=6154;bottom=214;connectionPoints=1;x1=3990;y1=210;x2=7100;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=Q2;anchorX=6140;anchorY=200;left=6126;top=186;right=6154;bottom=214;connectionPoints=1;x1=4690;y1=190;x2=7630;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=TC;anchorX=5740;anchorY=600;left=5726;top=586;right=5754;bottom=614;connectionPoints=1;x1=4740;y1=590;x2=7130;y2=590)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=Q1;anchorX=6190;anchorY=150;left=6176;top=136;right=6204;bottom=164;connectionPoints=1;x1=4980;y1=160;x2=7660;y2=160)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=Q0;anchorX=6240;anchorY=100;left=6226;top=86;right=6254;bottom=114;connectionPoints=1;x1=6040;y1=110;x2=6710;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=divby_60;component=Pin;label=TC;anchorX=5740;anchorY=600;left=5726;top=586;right=5754;bottom=614;connectionPoints=1;x1=750;y1=610;x2=7520;y2=610)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\divby_60.circ | true |
| manifest_json | generated\reports\divby_60_circuit_manifest.json | true |
| manifest_markdown | generated\reports\divby_60_circuit_manifest.md | true |
| delivery_json | generated\reports\divby_60_delivery_report.json | true |
| delivery_markdown | generated\reports\divby_60_delivery_report.md | true |
| summary_json | generated\reports\divby_60_generation_summary.json | true |
| summary_markdown | generated\reports\divby_60_generation_summary.md | true |
| artifact | generated\reports\divby_60_multichip.md | true |
