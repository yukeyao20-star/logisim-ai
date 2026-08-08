# logisim-evolution Ttl74148 patch

Restores the TTL 74x148 8-input priority encoder, which was missing from the
vendored logisim-evolution 4.2.0dev tree (the component exists upstream in
4.1.0-era builds but was absent from this checkout, so generated circuits
containing a 74148 component would not load).

## Files

### 1. NEW `src/main/java/com/cburch/logisim/std/ttl/Ttl74148.java`

Extends `AbstractTtlGate` (same proven mechanism as `Ttl74138`).

Key detail: the physical 74148 has GND on pin 9, while `AbstractTtlGate`'s
port layout assumes GND on pin 8 (the common 16-pin TTL arrangement). The pin
constants are therefore remapped so `updatePorts` produces the official port
layout:

- end0..7 = I0..I7 (active LOW inputs)
- end8 = nEI (active LOW enable input)
- end9 = nGS, end10 = nEO, end11 = A0, end12 = A1, end13 = A2
  (active LOW outputs)

Behavior (verified with the official JAR, `-w` test vectors):

- inputs active LOW: `bI[i] = port == FALSE`
- priority encoding: I7 highest
- A2A1A0 = complemented index when enabled and any input active, else 111
- nGS = 0 when enabled and any input active
- nEO = 0 when enabled and NO input active (cascade enable out)

### 2. EDIT `src/main/java/com/cburch/logisim/std/ttl/TtlLibrary.java`

Register the factory between `Ttl74139` and `Ttl74151`:

```java
new FactoryDescription(Ttl74148.class, S.getter("TTL74148"), "ttl.gif"),
```

## Rebuild

```powershell
cd third_party\logisim-evolution
.\gradlew.bat shadowJar --no-daemon -x test
```

## Verification

- Single 74148 (t148d): table mode shows correct A/nGS/nEO for idle, enabled,
  priority cases.
- `encoder_16to4.circ` (2x 74148 + 18 gates): 5/5 test vectors pass
  (all-inactive -> 0000/GS0; I0 -> 0000/GS1; I7 -> 0111/GS1;
  I15 -> 1111/GS1; EN=1 disabled -> 0000/GS0).
