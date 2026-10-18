# Generation delivery report for digital_clock

- Request kind: natural_language
- Request: 做一个数字时钟
- Intent: GATE_LEVEL_CIRCUIT
- Implementation level: GATE_LEVEL
- Target package: 
- Package primitive: false
- Routing primitive: primitive_gates
- Circuit: generated/circ/digital_clock.circ
- Summary: generated/reports/digital_clock_generation_summary.md
- Manifest: generated/reports/digital_clock_circuit_manifest.md
- Complete: true
- Manifest check count: 7
- Manifest issue count: 0
- Layout clean: false
- Layout issue count: 6
- Layout score: 0
- Layout risk level: high
- Layout unresolved tool count: 0
- Layout overlap count: 4
- Layout wire risk count: 0
- Layout pin facing issue count: 0
- Layout pin/tunnel proximity count: 2
- Layout label risk count: 0
- Layout reserved area intrusion count: 0
- Layout inspection failure count: 0
- Layout other issue count: 0
- Layout repair suggestions: Increase grid spacing, reassign crowded component rows, or rerun layout with wider component clearance. | Move tunnels farther from external pins or increase the pin-to-tunnel clearance rule.
- Layout auto repair applied: false
- Recommended actions: open_circuit, review_manifest, review_truth_table, fix_layout, regenerate_layout
- Artifact count: 10

## Layout Issues

- [WARNING/OVERLAP] Small components are too close in circuit digital_clock at (1190,1010) and (1220,1000): Tunnel and Pin
- [WARNING/OVERLAP] Small components are too close in circuit digital_clock at (1640,690) and (1640,700): Tunnel and Pin
- [WARNING/OVERLAP] Small components are too close in circuit digital_clock at (1710,650) and (1760,650): Pin and Tunnel
- [WARNING/OVERLAP] Small components are too close in circuit digital_clock at (1780,600) and (1810,590): Pin and Tunnel
- [WARNING/PIN_TUNNEL_PROXIMITY] Tunnel TQ1k is too close to external pin Q11 on the same row: (1640,650) vs (1710,650)
- [WARNING/PIN_TUNNEL_PROXIMITY] Tunnel TQ10 is too close to external pin Q11 on the same row: (1760,650) vs (1710,650)

## Layout Repair Suggestions

- [increase_spacing/OVERLAP] Increase grid spacing, reassign crowded component rows, or rerun layout with wider component clearance. (minClearanceX=80;minClearanceY=50)
- [increase_pin_tunnel_clearance/PIN_TUNNEL_PROXIMITY] Move tunnels farther from external pins or increase the pin-to-tunnel clearance rule. (minClearance=150)

## Artifacts

| Kind | Path | Exists |
| --- | --- | --- |
| circuit | generated/circ/digital_clock.circ | true |
| manifest_json | generated/reports/digital_clock_circuit_manifest.json | true |
| manifest_markdown | generated/reports/digital_clock_circuit_manifest.md | true |
| delivery_json | generated/reports/digital_clock_delivery_report.json | true |
| delivery_markdown | generated/reports/digital_clock_delivery_report.md | true |
| summary_json | generated/reports/digital_clock_generation_summary.json | true |
| summary_markdown | generated/reports/digital_clock_generation_summary.md | true |
| artifact | generated/reports/digital_clock_multichip.md | true |
| artifact | generated/reports/digital_clock_official_simulation.json | true |
| artifact | generated/reports/digital_clock_official_simulation.md | true |
