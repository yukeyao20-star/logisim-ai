# TTL DIP wiring for logic_y_ab_xor_7400

## U1 - 7400 (NAND)

| Pin | Role | Gate | Component | End | Net |
| ---: | --- | --- | --- | ---: | --- |
| 1 | input1 | A | nand1 | 1 | net_a |
| 2 | input2 | A | nand1 | 2 | net_b |
| 3 | output | A | nand1 | 0 | net_i1 |
| 4 | input1 | B | nand2 | 1 | net_a |
| 5 | input2 | B | nand2 | 2 | net_i1 |
| 6 | output | B | nand2 | 0 | net_i2 |
| 7 | GND |  |  | -1 | GND |
| 8 | output | C | nand3 | 0 | net_i3 |
| 9 | input2 | C | nand3 | 2 | net_i1 |
| 10 | input1 | C | nand3 | 1 | net_b |
| 11 | output | D | nand4 | 0 | net_i4 |
| 12 | input2 | D | nand4 | 2 | net_i3 |
| 13 | input1 | D | nand4 | 1 | net_i2 |
| 14 | VCC |  |  | -1 | VCC |

