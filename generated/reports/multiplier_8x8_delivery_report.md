# Generation delivery report for multiplier_8x8

- Request kind: natural_language
- Request: 8位乘法器
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated/circ/multiplier_8x8.circ
- Summary: generated/reports/multiplier_8x8_generation_summary.md
- Manifest: generated/reports/multiplier_8x8_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 6
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 2
- Layout wire risk count: 0
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 3
- Layout label risk count: 0
- Layout reserved area intrusion count: 1
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Increase grid spacing, reassign crowded component rows, or rerun layout with wider component clearance. | Move wires, tunnels, or labels away from reserved component visual areas. | Move tunnels farther from external pins or increase the pin-to-tunnel clearance rule.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [WARNING/OVERLAP] Small components are too close in circuit multiplier_8x8 at (1400,700) and (1410,690): Pin and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit multiplier_8x8 at (1540,600) and (1580,590): Pin and Tunnel
- [WARNING/RESERVED_AREA_INTRUSION] Wire crosses reserved area of Pin P11 in circuit multiplier_8x8: (1460,590) -> (1460,670)
- [WARNING/PIN_TUNNEL_PROXIMITY] Tunnel TQ26 is too close to external pin P11 on the same row: (1410,650) vs (1470,650)
- [WARNING/PIN_TUNNEL_PROXIMITY] Tunnel TQ29 is too close to external pin P11 on the same row: (1530,650) vs (1470,650)
- [WARNING/PIN_TUNNEL_PROXIMITY] Tunnel TQ26 is too close to external pin B3 on the same row: (450,650) vs (310,650)

## Layout Repair Suggestions

- [increase_spacing/OVERLAP] Increase grid spacing, reassign crowded component rows, or rerun layout with wider component clearance. (minClearanceX=80;minClearanceY=50)
- [avoid_reserved_areas/RESERVED_AREA_INTRUSION] Move wires, tunnels, or labels away from reserved component visual areas. (kind=reserved_area_intrusion;circuit=multiplier_8x8;component=Pin;label=P11;anchorX=1470;anchorY=650;left=1456;top=636;right=1484;bottom=664;connectionPoints=1;x1=1460;y1=590;x2=1460;y2=670)
- [increase_pin_tunnel_clearance/PIN_TUNNEL_PROXIMITY] Move tunnels farther from external pins or increase the pin-to-tunnel clearance rule. (minClearance=150)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/multiplier_8x8.circ | true |
| manifest_json | generated/reports/multiplier_8x8_circuit_manifest.json | true |
| manifest_markdown | generated/reports/multiplier_8x8_circuit_manifest.md | true |
| delivery_json | generated/reports/multiplier_8x8_delivery_report.json | true |
| delivery_markdown | generated/reports/multiplier_8x8_delivery_report.md | true |
| summary_json | generated/reports/multiplier_8x8_generation_summary.json | true |
| summary_markdown | generated/reports/multiplier_8x8_generation_summary.md | true |
| artifact | generated/reports/multiplier_8x8_multichip.md | true |
| artifact | generated/reports/multiplier_8x8_official_simulation.json | true |
| artifact | generated/reports/multiplier_8x8_official_simulation.md | true |
