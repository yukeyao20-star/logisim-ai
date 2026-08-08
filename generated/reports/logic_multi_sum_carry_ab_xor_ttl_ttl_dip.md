# TTL DIP wiring for logic_multi_sum_carry_ab_xor_ttl

## U1 - 7486 (XOR)

| Pin | Role | Gate | Component | End | Net |
| ---: | --- | --- | --- | ---: | --- |
| 1 | input1 | A | xor1 | 1 | net_a |
| 2 | input2 | A | xor1 | 2 | net_b |
| 3 | output | A | xor1 | 0 | net_i1 |
| 7 | GND |  |  | -1 | GND |
| 14 | VCC |  |  | -1 | VCC |

## U2 - 7408 (AND)

| Pin | Role | Gate | Component | End | Net |
| ---: | --- | --- | --- | ---: | --- |
| 1 | input1 | A | and2 | 1 | net_a |
| 2 | input2 | A | and2 | 2 | net_b |
| 3 | output | A | and2 | 0 | net_i2 |
| 7 | GND |  |  | -1 | GND |
| 14 | VCC |  |  | -1 | VCC |

