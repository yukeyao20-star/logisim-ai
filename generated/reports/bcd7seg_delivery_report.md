# Generation delivery report for bcd7seg

- Request kind: natural_language
- Request: 7 segment display
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\bcd7seg.circ
- Summary: generated\reports\bcd7seg_generation_summary.md
- Manifest: generated\reports\bcd7seg_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 89
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 3
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 86
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 12

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (160,120) -> (160,200)
- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (210,140) -> (210,250)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin e in circuit bcd7seg: (1090,310) -> (35290,310)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin a in circuit bcd7seg: (110,90) -> (35200,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O93 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P94 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P95 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P96 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O97 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P98 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P99 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P100 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P101 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P102 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P103 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O104 in circuit bcd7seg: (14450,3600) -> (38370,3600)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O93 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P94 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P95 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P96 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O97 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P98 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P99 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P100 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P101 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P102 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P103 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O104 in circuit bcd7seg: (14610,3620) -> (38410,3620)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin f in circuit bcd7seg: (1570,340) -> (35410,340)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin f in circuit bcd7seg: (1730,360) -> (35450,360)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin b in circuit bcd7seg: (210,140) -> (35240,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P153 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O154 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P155 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P156 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P157 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O158 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P159 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P160 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P161 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O162 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P163 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P164 in circuit bcd7seg: (21970,5700) -> (40070,5700)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin g in circuit bcd7seg: (2210,390) -> (35570,390)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P153 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O154 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P155 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P156 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P157 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O158 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P159 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P160 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P161 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O162 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P163 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P164 in circuit bcd7seg: (22130,5720) -> (40110,5720)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin e in circuit bcd7seg: (2570,290) -> (35590,290)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin d in circuit bcd7seg: (2730,260) -> (35630,260)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin d in circuit bcd7seg: (2890,240) -> (35670,240)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin c in circuit bcd7seg: (3050,210) -> (35710,210)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin c in circuit bcd7seg: (470,190) -> (35190,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin a in circuit bcd7seg: (500,110) -> (35260,110)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin g in circuit bcd7seg: (5540,410) -> (36320,410)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P52 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P53 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O54 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P55 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P56 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P45 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O46 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P47 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P48 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P49 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O50 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P51 in circuit bcd7seg: (7130,1920) -> (36670,1920)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P52 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P53 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O54 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P55 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P56 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P45 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O46 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P47 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P48 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P49 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of OR Gate B7O50 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of AND Gate B7P51 in circuit bcd7seg: (7290,1940) -> (36710,1940)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin b in circuit bcd7seg: (790,160) -> (35230,160)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd7seg;x1=110;y1=90;x2=110;y2=150)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd7seg;x1=160;y1=120;x2=160;y2=200)
- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=bcd7seg;x1=210;y1=140;x2=210;y2=250)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=e;anchorX=34680;anchorY=300;left=34666;top=286;right=34694;bottom=314;connectionPoints=1;x1=1090;y1=310;x2=35290;y2=310)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=a;anchorX=34880;anchorY=100;left=34866;top=86;right=34894;bottom=114;connectionPoints=1;x1=110;y1=90;x2=35200;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O93;anchorX=17460;anchorY=3610;left=17424;top=3581;right=17496;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P94;anchorX=17620;anchorY=3610;left=17584;top=3581;right=17656;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P95;anchorX=17780;anchorY=3610;left=17744;top=3581;right=17816;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P96;anchorX=17940;anchorY=3610;left=17904;top=3581;right=17976;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O97;anchorX=18100;anchorY=3610;left=18064;top=3581;right=18136;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P98;anchorX=18260;anchorY=3610;left=18224;top=3581;right=18296;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P99;anchorX=18420;anchorY=3610;left=18384;top=3581;right=18456;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P100;anchorX=18580;anchorY=3610;left=18544;top=3581;right=18616;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P101;anchorX=18740;anchorY=3610;left=18704;top=3581;right=18776;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P102;anchorX=18900;anchorY=3610;left=18864;top=3581;right=18936;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P103;anchorX=19060;anchorY=3610;left=19024;top=3581;right=19096;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O104;anchorX=19220;anchorY=3610;left=19184;top=3581;right=19256;bottom=3639;connectionPoints=3;x1=14450;y1=3600;x2=38370;y2=3600)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O93;anchorX=17460;anchorY=3610;left=17424;top=3581;right=17496;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P94;anchorX=17620;anchorY=3610;left=17584;top=3581;right=17656;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P95;anchorX=17780;anchorY=3610;left=17744;top=3581;right=17816;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P96;anchorX=17940;anchorY=3610;left=17904;top=3581;right=17976;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O97;anchorX=18100;anchorY=3610;left=18064;top=3581;right=18136;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P98;anchorX=18260;anchorY=3610;left=18224;top=3581;right=18296;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P99;anchorX=18420;anchorY=3610;left=18384;top=3581;right=18456;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P100;anchorX=18580;anchorY=3610;left=18544;top=3581;right=18616;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P101;anchorX=18740;anchorY=3610;left=18704;top=3581;right=18776;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P102;anchorX=18900;anchorY=3610;left=18864;top=3581;right=18936;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P103;anchorX=19060;anchorY=3610;left=19024;top=3581;right=19096;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O104;anchorX=19220;anchorY=3610;left=19184;top=3581;right=19256;bottom=3639;connectionPoints=3;x1=14610;y1=3620;x2=38410;y2=3620)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=f;anchorX=34630;anchorY=350;left=34616;top=336;right=34644;bottom=364;connectionPoints=1;x1=1570;y1=340;x2=35410;y2=340)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=f;anchorX=34630;anchorY=350;left=34616;top=336;right=34644;bottom=364;connectionPoints=1;x1=1730;y1=360;x2=35450;y2=360)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=b;anchorX=34830;anchorY=150;left=34816;top=136;right=34844;bottom=164;connectionPoints=1;x1=210;y1=140;x2=35240;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P153;anchorX=28060;anchorY=5710;left=28024;top=5681;right=28096;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O154;anchorX=28220;anchorY=5710;left=28184;top=5681;right=28256;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P155;anchorX=28380;anchorY=5710;left=28344;top=5681;right=28416;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P156;anchorX=28540;anchorY=5710;left=28504;top=5681;right=28576;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P157;anchorX=28700;anchorY=5710;left=28664;top=5681;right=28736;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O158;anchorX=28860;anchorY=5710;left=28824;top=5681;right=28896;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P159;anchorX=29020;anchorY=5710;left=28984;top=5681;right=29056;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P160;anchorX=29180;anchorY=5710;left=29144;top=5681;right=29216;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P161;anchorX=29340;anchorY=5710;left=29304;top=5681;right=29376;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O162;anchorX=29500;anchorY=5710;left=29464;top=5681;right=29536;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P163;anchorX=29660;anchorY=5710;left=29624;top=5681;right=29696;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P164;anchorX=29820;anchorY=5710;left=29784;top=5681;right=29856;bottom=5739;connectionPoints=3;x1=21970;y1=5700;x2=40070;y2=5700)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=g;anchorX=34580;anchorY=400;left=34566;top=386;right=34594;bottom=414;connectionPoints=1;x1=2210;y1=390;x2=35570;y2=390)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P153;anchorX=28060;anchorY=5710;left=28024;top=5681;right=28096;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O154;anchorX=28220;anchorY=5710;left=28184;top=5681;right=28256;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P155;anchorX=28380;anchorY=5710;left=28344;top=5681;right=28416;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P156;anchorX=28540;anchorY=5710;left=28504;top=5681;right=28576;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P157;anchorX=28700;anchorY=5710;left=28664;top=5681;right=28736;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O158;anchorX=28860;anchorY=5710;left=28824;top=5681;right=28896;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P159;anchorX=29020;anchorY=5710;left=28984;top=5681;right=29056;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P160;anchorX=29180;anchorY=5710;left=29144;top=5681;right=29216;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P161;anchorX=29340;anchorY=5710;left=29304;top=5681;right=29376;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O162;anchorX=29500;anchorY=5710;left=29464;top=5681;right=29536;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P163;anchorX=29660;anchorY=5710;left=29624;top=5681;right=29696;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P164;anchorX=29820;anchorY=5710;left=29784;top=5681;right=29856;bottom=5739;connectionPoints=3;x1=22130;y1=5720;x2=40110;y2=5720)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=e;anchorX=34680;anchorY=300;left=34666;top=286;right=34694;bottom=314;connectionPoints=1;x1=2570;y1=290;x2=35590;y2=290)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=d;anchorX=34730;anchorY=250;left=34716;top=236;right=34744;bottom=264;connectionPoints=1;x1=2730;y1=260;x2=35630;y2=260)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=d;anchorX=34730;anchorY=250;left=34716;top=236;right=34744;bottom=264;connectionPoints=1;x1=2890;y1=240;x2=35670;y2=240)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=c;anchorX=34780;anchorY=200;left=34766;top=186;right=34794;bottom=214;connectionPoints=1;x1=3050;y1=210;x2=35710;y2=210)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=c;anchorX=34780;anchorY=200;left=34766;top=186;right=34794;bottom=214;connectionPoints=1;x1=470;y1=190;x2=35190;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=a;anchorX=34880;anchorY=100;left=34866;top=86;right=34894;bottom=114;connectionPoints=1;x1=500;y1=110;x2=35260;y2=110)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=g;anchorX=34580;anchorY=400;left=34566;top=386;right=34594;bottom=414;connectionPoints=1;x1=5540;y1=410;x2=36320;y2=410)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P52;anchorX=10100;anchorY=1930;left=10064;top=1901;right=10136;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P53;anchorX=10260;anchorY=1930;left=10224;top=1901;right=10296;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O54;anchorX=10420;anchorY=1930;left=10384;top=1901;right=10456;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P55;anchorX=10580;anchorY=1930;left=10544;top=1901;right=10616;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P56;anchorX=10740;anchorY=1930;left=10704;top=1901;right=10776;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P45;anchorX=8980;anchorY=1930;left=8944;top=1901;right=9016;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O46;anchorX=9140;anchorY=1930;left=9104;top=1901;right=9176;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P47;anchorX=9300;anchorY=1930;left=9264;top=1901;right=9336;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P48;anchorX=9460;anchorY=1930;left=9424;top=1901;right=9496;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P49;anchorX=9620;anchorY=1930;left=9584;top=1901;right=9656;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O50;anchorX=9780;anchorY=1930;left=9744;top=1901;right=9816;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P51;anchorX=9940;anchorY=1930;left=9904;top=1901;right=9976;bottom=1959;connectionPoints=3;x1=7130;y1=1920;x2=36670;y2=1920)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P52;anchorX=10100;anchorY=1930;left=10064;top=1901;right=10136;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P53;anchorX=10260;anchorY=1930;left=10224;top=1901;right=10296;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O54;anchorX=10420;anchorY=1930;left=10384;top=1901;right=10456;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P55;anchorX=10580;anchorY=1930;left=10544;top=1901;right=10616;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P56;anchorX=10740;anchorY=1930;left=10704;top=1901;right=10776;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P45;anchorX=8980;anchorY=1930;left=8944;top=1901;right=9016;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O46;anchorX=9140;anchorY=1930;left=9104;top=1901;right=9176;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P47;anchorX=9300;anchorY=1930;left=9264;top=1901;right=9336;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P48;anchorX=9460;anchorY=1930;left=9424;top=1901;right=9496;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P49;anchorX=9620;anchorY=1930;left=9584;top=1901;right=9656;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=OR Gate;label=B7O50;anchorX=9780;anchorY=1930;left=9744;top=1901;right=9816;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=AND Gate;label=B7P51;anchorX=9940;anchorY=1930;left=9904;top=1901;right=9976;bottom=1959;connectionPoints=3;x1=7290;y1=1940;x2=36710;y2=1940)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=bcd7seg;component=Pin;label=b;anchorX=34830;anchorY=150;left=34816;top=136;right=34844;bottom=164;connectionPoints=1;x1=790;y1=160;x2=35230;y2=160)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\bcd7seg.circ | true |
| manifest_json | generated\reports\bcd7seg_circuit_manifest.json | true |
| manifest_markdown | generated\reports\bcd7seg_circuit_manifest.md | true |
| truth_table_json | generated\reports\bcd7seg_circuit_truth_table.json | true |
| truth_table_markdown | generated\reports\bcd7seg_circuit_truth_table.md | true |
| delivery_json | generated\reports\bcd7seg_delivery_report.json | true |
| delivery_markdown | generated\reports\bcd7seg_delivery_report.md | true |
| summary_json | generated\reports\bcd7seg_generation_summary.json | true |
| summary_markdown | generated\reports\bcd7seg_generation_summary.md | true |
| artifact | generated\reports\bcd7seg_multichip.md | true |
| artifact | generated\reports\bcd7seg_official_simulation.json | true |
| artifact | generated\reports\bcd7seg_official_simulation.md | true |
