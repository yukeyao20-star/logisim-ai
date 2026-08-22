# Generation delivery report for decoder_2bit

- Request kind: natural_language
- Request: 2 to 4 decoder
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated\circ\decoder_2bit.circ
- Summary: generated\reports\decoder_2bit_generation_summary.md
- Manifest: generated\reports\decoder_2bit_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 5
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 0
- Layout wire risk count: 1
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 0
- Layout label risk count: 0
- Layout reserved area intrusion count: 4
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. | Move wires, tunnels, or labels away from reserved component visual areas.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [ERROR/WIRE_RISK] Vertical wire in left boundary lane can short external inputs: (110,90) -> (110,150)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit decoder_2bit: (110,90) -> (2080,90)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y2 in circuit decoder_2bit: (470,190) -> (2070,190)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y1 in circuit decoder_2bit: (770,140) -> (2110,140)
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin Y0 in circuit decoder_2bit: (980,110) -> (2200,110)

## Layout Repair Suggestions

- [reroute_wires/WIRE_RISK] Reroute risky wires through tunnels or move boundary wire lanes away from external input rows. (kind=boundary_vertical;circuit=decoder_2bit;x1=110;y1=90;x2=110;y2=150)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_2bit;component=Pin;label=Y0;anchorX=1760;anchorY=100;left=1746;top=86;right=1774;bottom=114;connectionPoints=1;x1=110;y1=90;x2=2080;y2=90)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_2bit;component=Pin;label=Y2;anchorX=1660;anchorY=200;left=1646;top=186;right=1674;bottom=214;connectionPoints=1;x1=470;y1=190;x2=2070;y2=190)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_2bit;component=Pin;label=Y1;anchorX=1710;anchorY=150;left=1696;top=136;right=1724;bottom=164;connectionPoints=1;x1=770;y1=140;x2=2110;y2=140)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=decoder_2bit;component=Pin;label=Y0;anchorX=1760;anchorY=100;left=1746;top=86;right=1774;bottom=114;connectionPoints=1;x1=980;y1=110;x2=2200;y2=110)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated\circ\decoder_2bit.circ | true |
| manifest_json | generated\reports\decoder_2bit_circuit_manifest.json | true |
| manifest_markdown | generated\reports\decoder_2bit_circuit_manifest.md | true |
| truth_table_json | generated\reports\decoder_2bit_circuit_truth_table.json | true |
| truth_table_markdown | generated\reports\decoder_2bit_circuit_truth_table.md | true |
| delivery_json | generated\reports\decoder_2bit_delivery_report.json | true |
| delivery_markdown | generated\reports\decoder_2bit_delivery_report.md | true |
| summary_json | generated\reports\decoder_2bit_generation_summary.json | true |
| summary_markdown | generated\reports\decoder_2bit_generation_summary.md | true |
| artifact | generated\reports\decoder_2bit_multichip.md | true |
