# Multi-chip circuit debouncer

- Circuit: generated/circ/debouncer.circ
- Components: 8
- Nets: 20

## Components

| id | factory | label |
|--- | --- | --- |
| db0 | D Flip-Flop | U1 |
| db1 | D Flip-Flop | U2 |
| db2 | D Flip-Flop | U3 |
| db_ab | AND Gate | A1 |
| db_ac | AND Gate | A2 |
| db_bc | AND Gate | A3 |
| db_abac | OR Gate | O1 |
| db_out | OR Gate | O2 |
