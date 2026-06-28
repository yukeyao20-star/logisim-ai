# TTL DIP wiring for logic_multi_sum_cout_abcin_xor_and_xor_or_ttl

## U1 - 7486 (XOR)

| Pin | Role | Gate | Component | End | Net |
| ---: | --- | --- | --- | ---: | --- |
| 1 | input1 | A | xor1 | 1 | net_a |
| 2 | input2 | A | xor1 | 2 | net_b |
| 3 | output | A | xor1 | 0 | net_i1 |
| 4 | input1 | B | xor2 | 1 | net_i1 |
| 5 | input2 | B | xor2 | 2 | net_cin |
| 6 | output | B | xor2 | 0 | net_i4 |
| 7 | GND |  |  | -1 | GND |
| 14 | VCC |  |  | -1 | VCC |

## U2 - 7408 (AND)

| Pin | Role | Gate | Component | End | Net |
| ---: | --- | --- | --- | ---: | --- |
| 1 | input1 | A | and3 | 1 | net_i1 |
| 2 | input2 | A | and3 | 2 | net_cin |
| 3 | output | A | and3 | 0 | net_i2 |
| 4 | input1 | B | and4 | 1 | net_a |
| 5 | input2 | B | and4 | 2 | net_b |
| 6 | output | B | and4 | 0 | net_i3 |
| 7 | GND |  |  | -1 | GND |
| 14 | VCC |  |  | -1 | VCC |

## U3 - 7432 (OR)

| Pin | Role | Gate | Component | End | Net |
| ---: | --- | --- | --- | ---: | --- |
| 1 | input1 | A | or5 | 1 | net_i2 |
| 2 | input2 | A | or5 | 2 | net_i3 |
| 3 | output | A | or5 | 0 | net_i5 |
| 7 | GND |  |  | -1 | GND |
| 14 | VCC |  |  | -1 | VCC |

