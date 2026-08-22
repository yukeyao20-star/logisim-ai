package com.logisim_ai.ai;

import com.logisim_ai.ir.CircuitSpec;
import com.logisim_ai.ir.ComponentSpec;
import com.logisim_ai.ir.EndpointRef;
import com.logisim_ai.ir.ExternalPort;
import com.logisim_ai.ir.LayoutHint;
import com.logisim_ai.ir.NetSpec;
import com.logisim_ai.ir.PortDirection;
import com.logisim_ai.layout.PortSide;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parameterized structural circuit templates (N-bit adder, N-bit comparator,
 * ...). Each template builds a plain {@link CircuitSpec} that flows through
 * the exact same compiler and official-JAR verification pipeline as every
 * other generated circuit, so "difficult" circuits are just specs.
 *
 * <p>Layout: every bit slice occupies one column (rank = slice index), gates
 * are stacked by role inside the column, and the carry chain runs across
 * columns. {@code GridLayoutEngine} aligns each column evenly.
 */
public final class StructuralCircuitTemplates {
  private StructuralCircuitTemplates() {}

  private static final Pattern ADDER_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:加法器|全加器|二进制加法器)");
  private static final Pattern ADDER_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:adder|ripple[- ]carry\\s+adder)", Pattern.CASE_INSENSITIVE);
  private static final Pattern COMPARATOR_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:比较器|大小比较器|数值比较器)");
  private static final Pattern COMPARATOR_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:comparator|magnitude\\s+comparator)", Pattern.CASE_INSENSITIVE);
  private static final Pattern MUX_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*选[1一]\\s*(?:多路选择器|多路复用器|选择器|多路开关)?");
  private static final Pattern MUX_LU =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:路|位|输入)\\s*(?:选择器|多路选择器|多路复用器)");
  private static final Pattern MUX_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?to\\s*-?1\\s*(?:mux|multiplexer)", Pattern.CASE_INSENSITIVE);
  private static final Pattern DECODER_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:译码器|解码器|二进制译码器)");
  private static final Pattern DECODER_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?to\\s*-?(\\d{1,3})\\s*(?:decoder|demultiplexer)", Pattern.CASE_INSENSITIVE);
  private static final Pattern DECODER_ZH_NN =
      Pattern.compile("(\\d{1,2})\\s*(?:[-－]|\\s)\\s*(\\d{1,3})\\s*线?\\s*(?:译码器|解码器|分配器)");
  private static final Pattern DECODER_ZH_LINE =
      Pattern.compile("(\\d{1,2})\\s*线\\s*(\\d{1,3})\\s*线\\s*(?:译码器|解码器)");
  private static final Pattern DEMUX_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*路\\s*(?:分配器|多路分配器|选择分配器)");
  private static final Pattern DEMUX_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?way\\s*(?:demultiplexer|demux|de[- ]?multiplexer)|1\\s*to\\s*(\\d{1,2})\\s*(?:demux|demultiplexer)|demultiplexer|demux", Pattern.CASE_INSENSITIVE);
  private static final Pattern COUNTER_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:二进制计数器|计数器|加法计数器|up计数器|向上计数器)");
  private static final Pattern COUNTER_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:binary\\s+)?counter", Pattern.CASE_INSENSITIVE);
  private static final Pattern BCDCNT_ZH_BARE =
      Pattern.compile("bcd计数器|十进制计数器|模10计数器|模十计数器|8421计数器");
  private static final Pattern BCDCNT_EN_BARE =
      Pattern.compile("bcd\\s*counter|decade\\s*counter|mod[- ]?10\\s*counter", Pattern.CASE_INSENSITIVE);
  private static final Pattern UDCNT_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:可逆计数器|加减计数器|加減计数器)");
  private static final Pattern UDCNT_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:up[- ]?down\\s*counter|bidirectional\\s*counter)", Pattern.CASE_INSENSITIVE);
  private static final Pattern UDCNT_ZH_BARE =
      Pattern.compile("可逆计数器|加减计数器|加減计数器");
  private static final Pattern UDCNT_EN_BARE =
      Pattern.compile("up[- ]?down\\s*counter|bidirectional\\s*counter", Pattern.CASE_INSENSITIVE);
  private static final Pattern MODCNT_ZH =
      Pattern.compile("模([0-9一二三四五六七八九十两]{1,2})计数器|([0-9一二三四五六七八九十两]{1,2})进制计数器");
  private static final Pattern MODCNT_EN =
      Pattern.compile("mod[- ]?(\\d{1,2})\\s*counter", Pattern.CASE_INSENSITIVE);
  private static final Pattern PARITY_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:奇偶校验器|奇偶校验|偶校验器|奇校验器)");
  private static final Pattern PARITY_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:parity|even\\s+parity)", Pattern.CASE_INSENSITIVE);
  private static final Pattern ENCODER8_ZH =
      Pattern.compile("(?:8[\\s\\-－]*3|83|八三|八选三|八输入三输出|8输入3输出)\\s*(?:优先编码器|编码器)");
  private static final Pattern ENCODER8_EN =
      Pattern.compile("8[- ]to[- ]3\\s*(?:priority\\s+)?encoder", Pattern.CASE_INSENSITIVE);
  private static final Pattern ENCODER4_ZH =
      Pattern.compile("(?<!8)(?:4[\\s\\-－]*[2二]|4线2线|四线二线|四输入两输出|4输入2输出|4位2位|四选二)\\s*(?:优先编码器|编码器)");
  private static final Pattern ENCODER4_EN =
      Pattern.compile("4[- ]to[- ]2\\s*(?:priority\\s+)?encoder", Pattern.CASE_INSENSITIVE);
  private static final Pattern BCD7_ZH =
      Pattern.compile("(?:bcd|二十进制|8421码|8421)\\s*(?:转|到|[-])?\\s*(?:7段|七段|七段数码管|七段显示|数码管)\\s*(?:译码器|解码器|驱动器)?");
  private static final Pattern BCD7_EN =
      Pattern.compile("(?:bcd|binary\\s+coded\\s+decimal)\\s*(?:to)?\\s*7[- ]seg(?:ment)?\\s*(?:decoder|display|driver)?|7[- ]segment\\s*(?:display|decoder)", Pattern.CASE_INSENSITIVE);
  private static final Pattern MUL_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*[xX×位]\\s*([0-9一二三四五六七八九十两]{1,3})?\\s*位?\\s*(?:乘法器|二进制乘法器)");
  private static final Pattern MUL_ZH2 =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:乘法器|二进制乘法器)");
  private static final Pattern MUL_EN =
      Pattern.compile("(\\d{1,2})\\s*[xX×]\\s*(\\d{1,2})\\s*(?:multiplier)|(\\d{1,2})\\s*[- ]?bit\\s*multiplier", Pattern.CASE_INSENSITIVE);
  private static final Pattern SUB_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:减法器|全减器|二进制减法器)");
  private static final Pattern SUB_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:subtractor|full\\s+subtractor)", Pattern.CASE_INSENSITIVE);
  // Saturating adder (饱和加法器): S = min(A+B, 2^N-1). Checked BEFORE ADDER_*
  // so "饱和加法器" does not fall into the plain adder template.
  private static final Pattern SATADD_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(饱和加法器|饱和加法|防溢出加法器|饱和运算器)");
  private static final Pattern SATADD_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:saturating\\s*adder|saturation\\s*adder|saturated\\s*adder|saturating\\s*addition)", Pattern.CASE_INSENSITIVE);
  private static final Pattern SATADD_ZH_BARE =
      Pattern.compile("饱和加法器|饱和加法|防溢出加法器|饱和运算器");
  private static final Pattern SATADD_EN_BARE =
      Pattern.compile("saturating\\s*adder|saturation\\s*adder|saturated\\s*adder|saturating\\s*addition", Pattern.CASE_INSENSITIVE);
  private static final Pattern BCDADD_ZH =
      Pattern.compile("(?:bcd|二十进制|8421码|8421)\\s*(?:加法器|加法)");
  private static final Pattern BCDADD_EN =
      Pattern.compile("(?:bcd|binary\\s+coded\\s+decimal)\\s*adder", Pattern.CASE_INSENSITIVE);
  // 2-digit BCD adder (两位BCD加法器): checked BEFORE BCDADD_* so "2位BCD加法器"
  // builds the 2-digit chain, not the 1-digit adder.
  private static final Pattern BCDADD2_ZH =
      Pattern.compile("(?:两位|二位|2位|二位数|两位数)\\s*(?:bcd|二十进制|8421码|8421)\\s*(?:加法器|加法)");
  private static final Pattern BCDADD2_EN =
      Pattern.compile("(?:2[- ]digit|two[- ]digit|double[- ]digit)\\s*(?:bcd|binary\\s+coded\\s+decimal)\\s*adder", Pattern.CASE_INSENSITIVE);
  private static final Pattern BCDSUB_ZH =
      Pattern.compile("(?:bcd|二十进制|8421码|8421)\\s*(?:减法器|减法)");
  private static final Pattern BCDSUB_EN =
      Pattern.compile("(?:bcd|binary\\s+coded\\s+decimal)\\s*subtract", Pattern.CASE_INSENSITIVE);
  // 2-digit BCD subtractor (两位BCD减法器): checked BEFORE BCDSUB_* so "2位BCD减法器"
  // builds the 2-digit chain, not the 1-digit subtractor.
  private static final Pattern BCDSUB2_ZH =
      Pattern.compile("(?:两位|二位|2位|二位数|两位数)\\s*(?:bcd|二十进制|8421码|8421)\\s*(?:减法器|减法)");
  private static final Pattern BCDSUB2_EN =
      Pattern.compile("(?:2[- ]digit|two[- ]digit|double[- ]digit)\\s*(?:bcd|binary\\s+coded\\s+decimal)\\s*subtract", Pattern.CASE_INSENSITIVE);
  // Hex 7-segment display (十六进制转七段).
  private static final Pattern HEX7_ZH =
      Pattern.compile("十六进制\\s*(?:转|到|至)?\\s*七段|十六进制\\s*显示|hex\\s*七段");
  private static final Pattern HEX7_EN =
      Pattern.compile("hex\\s*(?:to|->|-)?\\s*(?:7[- ]segment|seven[- ]segment|display)", Pattern.CASE_INSENSITIVE);
  // N-bit squarer (平方器).
  private static final Pattern SQUARE_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(平方器|平方电路|平方运算|平方)");
  private static final Pattern SQUARE_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:squarer|square\\s*circuit|square\\s*of)", Pattern.CASE_INSENSITIVE);
  private static final Pattern SQUARE_ZH_BARE =
      Pattern.compile("平方器|平方电路|平方运算");
  private static final Pattern SQUARE_EN_BARE =
      Pattern.compile("\\bsquarer\\b|square\\s*circuit", Pattern.CASE_INSENSITIVE);
  // N-bit accumulator (累加器): ACC <- ACC + A on each clock edge.
  private static final Pattern ACCUM_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(累加器|累加电路|累加器电路)");
  private static final Pattern ACCUM_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:accumulator|accumulating\\s*adder)", Pattern.CASE_INSENSITIVE);
  private static final Pattern ACCUM_ZH_BARE =
      Pattern.compile("累加器|累加电路|累加器电路");
  private static final Pattern ACCUM_EN_BARE =
      Pattern.compile("\\baccumulator\\b|accumulating\\s*adder", Pattern.CASE_INSENSITIVE);
  // N-bit multiply-accumulate (乘累加器 / MAC): ACC += A*B per clock edge.
  private static final Pattern MAC_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)?\\s*(?:乘累加器|乘加器|乘法累加器)|乘累加器|乘加器|乘法累加器");
  private static final Pattern MAC_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:multiply[- ]?accumulate|mac\\s*unit)|multiply[- ]?accumulate|\\bmac\\b", Pattern.CASE_INSENSITIVE);
  // Edge detector (边沿检测器 / edge detector): one-clock pulse on signal
  // transitions. Rising = IN ∧ ¬INprev, Falling = ¬IN ∧ INprev.
  private static final Pattern EDGE_ZH =
      Pattern.compile("边沿检测|上升沿检测|下降沿检测|沿检测器|边沿探测器");
  private static final Pattern EDGE_EN =
      Pattern.compile("edge\\s*detector|rising[- ]edge|falling[- ]edge", Pattern.CASE_INSENSITIVE);
  // One-shot pulse generator (单脉冲/单稳态): TRIG rising edge -> PULSE high
  // for N clock cycles (retriggerable). N from query or default 4.
  private static final Pattern ONESHOT_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:周期|拍|个时钟|clk)?\\s*(?:单脉冲|单稳态|脉冲发生器)|单脉冲|单稳态|脉冲发生器");
  private static final Pattern ONESHOT_EN =
      Pattern.compile("(\\d{1,2})\\s*(?:cycle|clk)?\\s*(?:one[- ]?shot|monostable|pulse\\s*generator)|one[- ]?shot|monostable|pulse\\s*generator", Pattern.CASE_INSENSITIVE);
  // Debouncer (消抖): 3-stage shift register + majority vote. Stable input
  // for 2+ cycles passes; single-cycle glitches are rejected.
  private static final Pattern DEBOUNCE_ZH =
      Pattern.compile("消抖|去抖|防抖|按键消抖|抖动消除");
  private static final Pattern DEBOUNCE_EN =
      Pattern.compile("debounce|de[- ]?bounc|glitch\\s*filter|noise\\s*filter", Pattern.CASE_INSENSITIVE);
  private static final Pattern DFF_ZH =
      Pattern.compile("(?:d|D)触发器|d\\s*触发器|d触发器|触发器");
  private static final Pattern DFF_EN =
      Pattern.compile("d\\s*(?:flip[ -]?flop|flipflop|ff)|d\\s*触发器", Pattern.CASE_INSENSITIVE);
  private static final Pattern REG_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:寄存器|锁存器)");
  private static final Pattern REG_ZH_BARE =
      Pattern.compile("(?:寄存器|锁存器)");
  private static final Pattern REG_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:register|latch)", Pattern.CASE_INSENSITIVE);
  private static final Pattern SHIFTREG_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:移位寄存器|移位寄存器|串行寄存器|shift寄存器)");
  private static final Pattern SHIFTREG_ZH_BARE =
      Pattern.compile("(?:移位寄存器|移位寄存器)");
  public static final Pattern PISO_ZH =
      Pattern.compile("(?:并入串出|并行装入串行输出|并行输入串行输出|并转串|并入串)\\s*(?:移位寄存器|寄存器)?");
  public static final Pattern PISO_EN =
      Pattern.compile("(?:parallel\\s+in\\s+serial\\s+out|piso)", Pattern.CASE_INSENSITIVE);
  private static final Pattern SHIFTREG_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:shift\\s*register|sipo|piso)|shift\\s*register", Pattern.CASE_INSENSITIVE);
  private static final Pattern SR_ZH =
      Pattern.compile("(?:sr|s-r|rs|r-s)\\s*(?:触发器|锁存器)|(?:sr|s-r|rs|r-s)\\s*latch", Pattern.CASE_INSENSITIVE);
  private static final Pattern BUZZER_ZH =
      Pattern.compile("抢答器|优先抢答|抢答电路|抢答");
  private static final Pattern DIVIDER_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:分频器|分频电路|频率分频器)");
  private static final Pattern DIVIDER_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:divider|frequency\\s+divider)", Pattern.CASE_INSENSITIVE);
  private static final Pattern DIVIDER_ZH_BARE =
      Pattern.compile("(?:分频器|分频电路|分频|频率分频)");
  private static final Pattern DIVIDER_EN_BARE =
      Pattern.compile("(?:divider|frequency\\s+divider)", Pattern.CASE_INSENSITIVE);
  private static final Pattern JOHNSON_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:约翰逊计数器|约翰逊环形计数器|扭环形计数器)");
  private static final Pattern JOHNSON_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:johnson\\s*counter|twisted\\s*ring\\s*counter)", Pattern.CASE_INSENSITIVE);
  private static final Pattern JOHNSON_ZH_BARE =
      Pattern.compile("(?:约翰逊计数器|约翰逊环形计数器|扭环形计数器)");
  private static final Pattern JOHNSON_EN_BARE =
      Pattern.compile("johnson\\s*counter|twisted\\s*ring", Pattern.CASE_INSENSITIVE);
  private static final Pattern DLATCH_ZH =
      Pattern.compile("(?:d|D)锁存器|透明锁存器|数据锁存|数据锁存器|电平锁存器");
  private static final Pattern DLATCH_EN =
      Pattern.compile("(?:d\\s*latch|transparent\\s*latch|level[- ]sensitive\\s*latch|enable\\s*latch)", Pattern.CASE_INSENSITIVE);
  private static final Pattern GRAY_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:格雷码|格雷码转换器|二进制转格雷码)");
  private static final Pattern GRAY_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:binary[- ]to[- ]gray|gray[- ]code|gray)\\s*(?:converter|encoder)?", Pattern.CASE_INSENSITIVE);
  private static final Pattern GRAY_ZH_BARE =
      Pattern.compile("格雷码|格雷码转换器|二进制转格雷");
  private static final Pattern GRAY_EN_BARE =
      Pattern.compile("binary[- ]to[- ]gray|gray[- ]code|gray\\s*(?:converter|encoder)?", Pattern.CASE_INSENSITIVE);
  private static final Pattern G2B_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:格雷码转二进制|格雷转二进制|二进制输出)");
  private static final Pattern G2B_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:gray[- ]to[- ]binary|gray\\s*cod[ed]?\\s*to\\s*binary)", Pattern.CASE_INSENSITIVE);
  private static final Pattern G2B_ZH_BARE =
      Pattern.compile("格雷码转二进制|格雷转二进制");
  private static final Pattern G2B_EN_BARE =
      Pattern.compile("gray[- ]to[- ]binary|gray\\s*cod[ed]?\\s*to\\s*binary", Pattern.CASE_INSENSITIVE);
  // Gray-code COUNTER (格雷码计数器 / gray counter): checked before the
  // binary→gray converter so "格雷码计数器" builds the sequential counter.
  private static final Pattern GRAYCNT_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)?\\s*格雷码计数器|格雷码计数器|格雷计数器");
  private static final Pattern GRAYCNT_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:gray[- ]?code[- ]?counter|gray\\s*counter)|gray[- ]?code[- ]?counter|gray\\s*counter", Pattern.CASE_INSENSITIVE);
  private static final Pattern B2B_ZH_BARE =
      Pattern.compile("bcd转二进制|8421码转二进制|bcd码转二进制|十进制转二进制");
  private static final Pattern B2B_EN_BARE =
      Pattern.compile("bcd[- ]to[- ]binary|8421[- ]to[- ]binary", Pattern.CASE_INSENSITIVE);
  private static final Pattern BINBCD_ZH_BARE =
      Pattern.compile("二进制转bcd|二进制转8421|二进制转二十进制|二进制转十进制|bin转bcd|二进制转bcd码");
  private static final Pattern BINBCD_EN_BARE =
      Pattern.compile("binary[- ]to[- ]bcd|bin[- ]to[- ]bcd|binary[- ]to[- ]8421", Pattern.CASE_INSENSITIVE);
  private static final Pattern TSB_ZH =
      Pattern.compile("三态缓冲器|三态输出缓冲|三态门|三态缓冲门");
  private static final Pattern TSB_EN =
      Pattern.compile("tri[- ]?state\\s*buffer|tristate\\s*buffer|tri[- ]?state\\s*gate", Pattern.CASE_INSENSITIVE);
  private static final Pattern E3_ZH =
      Pattern.compile("余三码|余3码|8421码转余3|8421转余3");
  private static final Pattern E3_EN =
      Pattern.compile("excess[- ]?3|bcd[- ]to[- ]excess", Pattern.CASE_INSENSITIVE);
  // Excess-3 ADDER (余3码加法器): checked BEFORE E3_ZH/E3_EN so "余3码加法器"
  // builds the adder, not the BCD→excess-3 converter.
  private static final Pattern E3ADD_ZH =
      Pattern.compile("余3码加法器|余三码加法器|余3加法器|余三加法器|excess3?\\s*加法器");
  private static final Pattern E3ADD_EN =
      Pattern.compile("excess[- ]?3\\s*(?:adder|addition)", Pattern.CASE_INSENSITIVE);
  private static final Pattern E3B_ZH =
      Pattern.compile("余3码转8421|余三码转8421|余3转8421|余三转8421");
  private static final Pattern E3B_EN =
      Pattern.compile("excess[- ]?3\\s*to\\s*bcd|excess[- ]?3\\s*to\\s*binary", Pattern.CASE_INSENSITIVE);
  private static final Pattern TWOS_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:补码器|补码|求补|二进制补码)");
  private static final Pattern TWOS_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:two.?s?\\s*complement|2s\\s*complement)", Pattern.CASE_INSENSITIVE);
  private static final Pattern TWOS_ZH_BARE =
      Pattern.compile("补码器|求补|二进制补码");
  private static final Pattern TWOS_EN_BARE =
      Pattern.compile("two.?s?\\s*complement|2s\\s*complement", Pattern.CASE_INSENSITIVE);
  private static final Pattern NC_ZH =
      Pattern.compile("9的补码|九的补码|9的补数|九的补数|9补码");
  private static final Pattern NC_EN =
      Pattern.compile("nine.?s?\\s*complement|9'?s?\\s*complement", Pattern.CASE_INSENSITIVE);
  private static final Pattern TRAFFIC_ZH =
      Pattern.compile("交通灯|红绿灯|交通信号|交通灯控制");
  private static final Pattern TRAFFIC_EN =
      Pattern.compile("traffic light|traffic signal|stop light", Pattern.CASE_INSENSITIVE);
  private static final Pattern SEQDET_ZH =
      Pattern.compile("序列检测器|101序列检测|101检测|串行检测器");
  private static final Pattern SEQDET_EN =
      Pattern.compile("sequence detector|101 detector|101\\s*sequence", Pattern.CASE_INSENSITIVE);
  private static final Pattern BSREG_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:双向移位寄存器|左右移位寄存器|双向移位)");
  private static final Pattern BSREG_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:bidirectional\\s*shift\\s*register|left[- ]right\\s*shift\\s*register)", Pattern.CASE_INSENSITIVE);
  private static final Pattern BSREG_ZH_BARE =
      Pattern.compile("双向移位寄存器|左右移位寄存器|双向移位");
  private static final Pattern BSREG_EN_BARE =
      Pattern.compile("bidirectional\\s*shift|left[- ]right\\s*shift", Pattern.CASE_INSENSITIVE);
  private static final Pattern LFSR_ZH =
      Pattern.compile("伪随机数发生器|伪随机序列|线性反馈移位寄存器|随机数发生器|m序列|线性反馈");
  private static final Pattern LFSR_EN =
      Pattern.compile("lfsr|linear\\s*feedback\\s*shift\\s*register|pseudo[ -]random", Pattern.CASE_INSENSITIVE);
  // N-bit ALU (arithmetic logic unit): A op B selected by S1S0.
  // 00=AND, 01=OR, 10=ADD, 11=SUB. Outputs R[N], Cout, Z (zero flag).
  private static final Pattern ALU_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:alu|算术逻辑单元|算术运算单元)");
  private static final Pattern ALU_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:alu|arithmetic\\s*logic\\s*unit)", Pattern.CASE_INSENSITIVE);
  private static final Pattern ALU_ZH_BARE =
      Pattern.compile("(?:alu|算术逻辑单元|算术运算单元|运算器)");
  private static final Pattern ALU_EN_BARE =
      Pattern.compile("\\balu\\b|arithmetic\\s*logic\\s*unit", Pattern.CASE_INSENSITIVE);
  // N-bit barrel shifter (桶形移位器): logical left shift by SH bits (0..N-1).
  private static final Pattern BARREL_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:桶形移位器|桶式移位器|桶移位器)");
  private static final Pattern BARREL_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:barrel\\s*shifter|barrel\\s*shift)", Pattern.CASE_INSENSITIVE);
  private static final Pattern BARREL_ZH_BARE =
      Pattern.compile("桶形移位器|桶式移位器|桶移位器");
  private static final Pattern BARREL_EN_BARE =
      Pattern.compile("barrel\\s*shifter|barrel\\s*shift", Pattern.CASE_INSENSITIVE);
  // N-bit rotate shifter (循环移位器): rotate left/right by SH bits (0..N-1).
  private static final Pattern ROTATE_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(循环移位器|循环移位|循环左移|左循环移位)");
  private static final Pattern ROTATE_RIGHT_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(循环右移|右循环移位)");
  private static final Pattern ROTATE_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(left|right)?\\s*(?:rotate|rotation|cyclical\\s*shift)\\s*(left|right)?", Pattern.CASE_INSENSITIVE);
  private static final Pattern ROTATE_ZH_BARE =
      Pattern.compile("循环移位器|循环移位|循环左移|左循环移位|循环右移|右循环移位");
  private static final Pattern ROTATE_EN_BARE =
      Pattern.compile("\\brotate\\b|\\brotation\\b|cyclical\\s*shift", Pattern.CASE_INSENSITIVE);
  // N-bit logical right shift (逻辑右移): zero fill. Also accepts bare "右移".
  private static final Pattern LSR_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(逻辑右移|逻辑右移位|右移)");
  private static final Pattern LSR_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:logical\\s*(?:right\\s*)?shift|shift\\s*right)", Pattern.CASE_INSENSITIVE);
  private static final Pattern LSR_ZH_BARE =
      Pattern.compile("逻辑右移|逻辑右移位");
  private static final Pattern LSR_EN_BARE =
      Pattern.compile("logical\\s*(?:right\\s*)?shift|shift\\s*right", Pattern.CASE_INSENSITIVE);
  // N-bit arithmetic right shift (算术右移): sign fill.
  private static final Pattern SAR_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(算术右移|算数右移|符号右移)");
  private static final Pattern SAR_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:arithmetic\\s*(?:right\\s*)?shift|arithmetic\\s*right\\s*shift)", Pattern.CASE_INSENSITIVE);
  private static final Pattern SAR_ZH_BARE =
      Pattern.compile("算术右移|算数右移|符号右移");
  private static final Pattern SAR_EN_BARE =
      Pattern.compile("arithmetic\\s*(?:right\\s*)?shift|arithmetic\\s*right\\s*shift", Pattern.CASE_INSENSITIVE);
  // N-bit unsigned restoring divider (除法器): Q = A / B, R = A mod B.
  // Distinct from 分频器/frequency divider (DIVIDER_* above).
  private static final Pattern DIVISION_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(除法器|除法电路|除法运算器|除法)");
  private static final Pattern DIVISION_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:division\\s*circuit|unsigned\\s*divider|restoring\\s*divider|division)", Pattern.CASE_INSENSITIVE);
  private static final Pattern DIVISION_ZH_BARE =
      Pattern.compile("除法器|除法电路|除法运算器");
  private static final Pattern DIVISION_EN_BARE =
      Pattern.compile("division\\s*circuit|unsigned\\s*divider|restoring\\s*divider", Pattern.CASE_INSENSITIVE);

  /** Human-readable names of the parameterized structural templates, for NL help. */
  public static List<String> supportedStructuralNames() {
    return List.of(
        "Adder 加法器 (N-bit)",
        "Saturating adder 饱和加法器 (N-bit, S=min(A+B,2^N-1))",
        "Subtractor 减法器 (N-bit)",
        "Multiplier 乘法器 (N×N)",
        "Comparator 比较器 (N-bit)",
        "MUX 多路选择器 (N-to-1)",
        "Decoder 译码器 (N-to-2^N)",
        "Priority encoder 优先编码器 (8-to-3)",
        "Priority encoder 优先编码器 (4-to-2)",
        "Parity 奇偶校验器 (N-bit)",
        "BCD 7-segment 七段显示",
        "Hex 7-segment 十六进制转七段 (0-9,A-F)",
        "Squarer 平方器 (N-bit, Y=A²)",
        "Accumulator 累加器 (N-bit, ACC←ACC+A)",
        "BCD adder 8421加法器",
        "BCD adder 2-digit 两位BCD加法器",
        "BCD subtractor 8421减法器",
        "BCD subtractor 2-digit 两位BCD减法器",
        "D flip-flop D触发器",
        "JK flip-flop JK触发器",
        "T flip-flop T触发器",
        "D latch 透明锁存器",
        "Register 寄存器 (N-bit)",
        "Shift register 移位寄存器 (N-bit)",
        "Bidirectional shift register 双向移位寄存器",
        "PISO shift register 并入串出移位寄存器 (LOAD/SHIFT, SO)",
        "Frequency divider 分频器 (N-stage)",
        "BCD counter 十进制计数器",
        "Johnson counter 约翰逊计数器",
        "Gray code 格雷码转换器 (binary→gray)",
        "Gray→binary 格雷码转二进制",
        "Excess-3 余三码 (BCD→excess-3)",
        "Excess-3 adder 余3码加法器 (两位余3码相加)",
        "Excess-3→BCD 余三码转8421",
        "Two's complement 补码器 (N-bit)",
        "Nine's complement 9的补码",
        "Traffic light 交通灯控制器",
        "101 sequence detector 101序列检测器",
        "1101 sequence detector 1101序列检测器",
        "LFSR 线性反馈移位寄存器/伪随机数",
        "Ring counter 环形计数器",
        "SR latch SR锁存器",
        "Tri-state buffer 三态缓冲器",
        "Demux 多路分配器 (1-to-N)",
        "Mod-N counter 模N计数器",
        "Up/down counter 可逆计数器",
        "BCD→binary 二十进制转二进制",
        "Binary→BCD 二进制转8421",
        "ALU 算术逻辑单元 (N-bit, AND/OR/ADD/SUB)",
        "Barrel shifter 桶形移位器 (N-bit, 逻辑左移)",
        "Rotate shifter 循环移位器 (N-bit, 左/右循环)",
        "Logical shift right 逻辑右移 (N-bit, 补零)",
        "Arithmetic shift right 算术右移 (N-bit, 符号扩展)",
        "Divider 除法器 (N×N, 恢复余数, Q=A/B R=A mod B)");
  }

  public static Optional<CircuitSpec> tryBuild(String query) {
    if (query == null || query.isBlank()) {
      return Optional.empty();
    }
    String t = query.toLowerCase(Locale.ROOT);
    if (BCD7_ZH.matcher(t).find() || BCD7_EN.matcher(t).find()) {
      return Optional.of(buildBcd7Seg());
    }
    // Bare 七段/数码管 without the bcd/8421 prefix (e.g. "七段译码器").
    if (t.contains("七段译码器") || t.contains("七段显示") || t.contains("数码管") || t.contains("七段数码管")) {
      return Optional.of(buildBcd7Seg());
    }
    // Hex 7-segment display (checked before the bare 七段 branch so "十六进制转七段"
    // builds the hex display, not the BCD one).
    if (HEX7_ZH.matcher(t).find() || HEX7_EN.matcher(t).find()) {
      return Optional.of(buildHex7Seg());
    }
    // N-bit squarer (平方器).
    Matcher sqzh = SQUARE_ZH.matcher(t);
    if (sqzh.find()) {
      Integer n = chineseToInt(sqzh.group(1));
      if (n != null && n >= 2 && n <= 4) {
        return Optional.of(buildSquarer(n));
      }
    }
    Matcher sqen = SQUARE_EN.matcher(t);
    if (sqen.find()) {
      int n = Integer.parseInt(sqen.group(1));
      if (n >= 2 && n <= 4) {
        return Optional.of(buildSquarer(n));
      }
    }
    if (SQUARE_ZH_BARE.matcher(t).find() || SQUARE_EN_BARE.matcher(t).find()) {
      return Optional.of(buildSquarer(4));
    }
    // Edge detector (边沿检测器) — sequential pulse on signal transitions.
    if (EDGE_ZH.matcher(t).find() || EDGE_EN.matcher(t).find()) {
      return Optional.of(buildEdgeDetector());
    }
    // One-shot pulse generator (单脉冲/单稳态). Guard "脉冲发生器" against the
    // frequency-divider and clock paths that also mention pulses.
    Matcher osZH = ONESHOT_ZH.matcher(t);
    Matcher osEN = ONESHOT_EN.matcher(t);
    if (osZH.find()) {
      String g = osZH.group(1);
      if (g != null) {
        Integer n = chineseToInt(g);
        if (n != null && n >= 1 && n <= 16) {
          return Optional.of(buildOneShot(n));
        }
      }
      return Optional.of(buildOneShot(4));
    }
    if (osEN.find()) {
      if (osEN.group(1) != null) {
        int n = Integer.parseInt(osEN.group(1));
        if (n >= 1 && n <= 16) {
          return Optional.of(buildOneShot(n));
        }
      }
      return Optional.of(buildOneShot(4));
    }
    // Debouncer (消抖/防抖).
    if (DEBOUNCE_ZH.matcher(t).find() || DEBOUNCE_EN.matcher(t).find()) {
      return Optional.of(buildDebouncer());
    }
    // N-bit multiply-accumulate (乘累加器 / MAC): ACC += A*B per clock edge.
    // Checked BEFORE the accumulator so "乘累加器" (which contains "累加器")
    // builds the MAC, not the plain accumulator.
    Matcher macZH = MAC_ZH.matcher(t);
    Matcher macEN = MAC_EN.matcher(t);
    if (macZH.find()) {
      Integer n = chineseToInt(macZH.group(1));
      if (n != null && n >= 2 && n <= 4) {
        return Optional.of(buildMac(n));
      }
      return Optional.of(buildMac(4));
    }
    if (macEN.find()) {
      if (macEN.group(1) != null) {
        int n = Integer.parseInt(macEN.group(1));
        if (n >= 2 && n <= 4) {
          return Optional.of(buildMac(n));
        }
      }
      return Optional.of(buildMac(4));
    }
    // N-bit accumulator (累加器).
    Matcher acczh = ACCUM_ZH.matcher(t);
    if (acczh.find()) {
      Integer n = chineseToInt(acczh.group(1));
      if (n != null && n >= 2 && n <= 8) {
        return Optional.of(buildAccumulator(n));
      }
    }
    Matcher accen = ACCUM_EN.matcher(t);
    if (accen.find()) {
      int n = Integer.parseInt(accen.group(1));
      if (n >= 2 && n <= 8) {
        return Optional.of(buildAccumulator(n));
      }
    }
    if (ACCUM_ZH_BARE.matcher(t).find() || ACCUM_EN_BARE.matcher(t).find()) {
      return Optional.of(buildAccumulator(4));
    }
    Matcher mul = MUL_ZH.matcher(t);
    Matcher mul2 = MUL_ZH2.matcher(t);
    Matcher mulEn = MUL_EN.matcher(t);
    if (mul.find()) {
      Integer a = chineseToInt(mul.group(1));
      Integer b = mul.group(2) != null ? chineseToInt(mul.group(2)) : a;
      if (a != null && b != null && a.equals(b)) {
        return Optional.of(buildMultiplier(a));
      }
    }
    if (mul2.find()) {
      Integer n = chineseToInt(mul2.group(1));
      if (n != null) {
        return Optional.of(buildMultiplier(n));
      }
    }
    if (mulEn.find()) {
      // "NxN multiplier" -> group(1),group(2); "N bit multiplier" -> group(3)
      String ga = mulEn.group(1);
      String gb = mulEn.group(2);
      String gbits = mulEn.group(3);
      if (ga != null && gb != null) {
        int a = Integer.parseInt(ga);
        int b = Integer.parseInt(gb);
        if (a == b) {
          return Optional.of(buildMultiplier(a));
        }
      } else if (gbits != null) {
        int n = Integer.parseInt(gbits);
        if (n >= 1 && n <= 8) {
          return Optional.of(buildMultiplier(n));
        }
      }
    }
    Matcher subzh = SUB_ZH.matcher(t);
    Matcher suben = SUB_EN.matcher(t);
    if (subzh.find()) {
      Integer n = chineseToInt(subzh.group(1));
      if (n != null) {
        return Optional.of(buildSubtractor(n));
      }
    }
    if (suben.find()) {
      return Optional.of(buildSubtractor(Integer.parseInt(suben.group(1))));
    }
    if (BCDADD2_ZH.matcher(t).find() || BCDADD2_EN.matcher(t).find()) {
      return Optional.of(buildBcdAdder2Digit());
    }
    if (BCDADD_ZH.matcher(t).find() || BCDADD_EN.matcher(t).find()) {
      return Optional.of(buildBcdAdder());
    }
    // BCD subtractor must precede the generic 减法器 match below so
    // "BCD减法器"/"8421减法" route here, not to the binary subtractor.
    if (BCDSUB2_ZH.matcher(t).find() || BCDSUB2_EN.matcher(t).find()) {
      return Optional.of(buildBcdSubtractor2Digit());
    }
    if (BCDSUB_ZH.matcher(t).find() || BCDSUB_EN.matcher(t).find()) {
      return Optional.of(buildBcdSubtractor());
    }
    // Saturating adder (checked before the plain adder so "饱和加法器" builds
    // the saturating variant, not adder_4bit).
    Matcher satzh = SATADD_ZH.matcher(t);
    if (satzh.find()) {
      Integer n = chineseToInt(satzh.group(1));
      if (n != null && n >= 2 && n <= 8) {
        return Optional.of(buildSaturatingAdder(n));
      }
    }
    Matcher saten = SATADD_EN.matcher(t);
    if (saten.find()) {
      int n = Integer.parseInt(saten.group(1));
      if (n >= 2 && n <= 8) {
        return Optional.of(buildSaturatingAdder(n));
      }
    }
    if (SATADD_ZH_BARE.matcher(t).find() || SATADD_EN_BARE.matcher(t).find()) {
      return Optional.of(buildSaturatingAdder(4));
    }
    Matcher zh = ADDER_ZH.matcher(t);
    Matcher en = ADDER_EN.matcher(t);
    if (zh.find()) {
      Integer bits = chineseToInt(zh.group(1));
      if (bits != null) {
        return Optional.of(buildAdder(bits));
      }
    }
    if (en.find()) {
      return Optional.of(buildAdder(Integer.parseInt(en.group(1))));
    }
    Matcher czh = COMPARATOR_ZH.matcher(t);
    Matcher cen = COMPARATOR_EN.matcher(t);
    if (czh.find()) {
      Integer bits = chineseToInt(czh.group(1));
      if (bits != null) {
        return Optional.of(buildComparator(bits));
      }
    }
    if (cen.find()) {
      return Optional.of(buildComparator(Integer.parseInt(cen.group(1))));
    }
    Matcher mzh = MUX_ZH.matcher(t);
    Matcher men = MUX_EN.matcher(t);
    if (mzh.find()) {
      Integer n = chineseToInt(mzh.group(1));
      if (n != null) {
        return Optional.of(buildMux(pow2Ceil(n)));
      }
    }
    if (men.find()) {
      return Optional.of(buildMux(pow2Ceil(Integer.parseInt(men.group(1)))));
    }
    Matcher mlu = MUX_LU.matcher(t);
    if (mlu.find()) {
      Integer n = chineseToInt(mlu.group(1));
      if (n != null && n >= 2) {
        return Optional.of(buildMux(pow2Ceil(n)));
      }
    }
    Matcher dzh = DECODER_ZH.matcher(t);
    Matcher den = DECODER_EN.matcher(t);
    if (dzh.find()) {
      Integer bits = chineseToInt(dzh.group(1));
      if (bits != null) {
        return Optional.of(buildDecoder(bits));
      }
    }
    if (den.find()) {
      return Optional.of(buildDecoder(Integer.parseInt(den.group(1))));
    }
    Matcher dnn = DECODER_ZH_NN.matcher(t);
    if (dnn.find()) {
      // "3-8译码器" -> 3 input bits -> 8 outputs
      int n = Integer.parseInt(dnn.group(2));
      int bits = 0;
      while ((1 << bits) < n) {
        bits++;
      }
      return Optional.of(buildDecoder(bits));
    }
    Matcher dline = DECODER_ZH_LINE.matcher(t);
    if (dline.find()) {
      // "3线8线译码器" -> 8 outputs -> log2 bits
      int n = Integer.parseInt(dline.group(2));
      int bits = 0;
      while ((1 << bits) < n) {
        bits++;
      }
      return Optional.of(buildDecoder(bits));
    }
    // 1-to-N demultiplexer "N路分配器" (has a DATA input, unlike a decoder).
    Matcher dmzh = DEMUX_ZH.matcher(t);
    Matcher dmen = DEMUX_EN.matcher(t);
    if (dmzh.find()) {
      Integer n = chineseToInt(dmzh.group(1));
      if (n != null) {
        return Optional.of(buildDemux(n));
      }
    }
    if (dmen.find()) {
      // "N way demux" -> group(1); "1 to N demux" -> group(2); bare demux -> 4
      String g1 = dmen.group(1);
      String g2 = dmen.group(2);
      int n;
      if (g1 != null) {
        n = Integer.parseInt(g1);
      } else if (g2 != null) {
        n = Integer.parseInt(g2);
      } else {
        n = 4;
      }
      if (n >= 2 && n <= 16) {
        return Optional.of(buildDemux(n));
      }
    }
    // BCD / decade counter (mod-10). Must precede the 74161 binary counter
    // and its bare "计数器" fallback below.
    if (BCDCNT_ZH_BARE.matcher(t).find() || BCDCNT_EN_BARE.matcher(t).find()) {
      return Optional.of(buildBcdCounter());
    }
    // Up/down (可逆) counter. Also must precede the bare 计数器 fallback.
    Matcher udzh = UDCNT_ZH.matcher(t);
    Matcher uden = UDCNT_EN.matcher(t);
    if (udzh.find()) {
      Integer bits = chineseToInt(udzh.group(1));
      if (bits != null) {
        return Optional.of(buildUpDownCounter(bits));
      }
    }
    if (uden.find()) {
      return Optional.of(buildUpDownCounter(Integer.parseInt(uden.group(1))));
    }
    if (UDCNT_ZH_BARE.matcher(t).find() || UDCNT_EN_BARE.matcher(t).find()) {
      return Optional.of(buildUpDownCounter(4));
    }
    // Generic mod-N counter. Exclude N=2/10 (binary counter / BCD counter handle
    // 二进制计数器 and 十进制计数器 before reaching here).
    Matcher modzh = MODCNT_ZH.matcher(t);
    Matcher moden = MODCNT_EN.matcher(t);
    if (modzh.find()) {
      Integer n = modzh.group(1) != null ? chineseToInt(modzh.group(1)) : chineseToInt(modzh.group(2));
      if (n != null && n != 2 && n != 10) {
        return Optional.of(buildModCounter(n));
      }
    }
    if (moden.find()) {
      int n = Integer.parseInt(moden.group(1));
      if (n != 2 && n != 10) {
        return Optional.of(buildModCounter(n));
      }
    }
    // Bare binary counter (二进制计数器): the mod-N guard rejects N=2 and isBare
    // treats 二进制's 二 as a width prefix, so catch it explicitly.
    if (t.contains("二进制计数器") || t.contains("binary counter")) {
      return Optional.of(buildCounter(4));
    }
    Matcher ctrzh = COUNTER_ZH.matcher(t);
    Matcher ctrEn = COUNTER_EN.matcher(t);
    if (ctrzh.find()) {
      Integer bits = chineseToInt(ctrzh.group(1));
      if (bits != null) {
        return Optional.of(buildCounter(bits));
      }
    }
    if (ctrEn.find()) {
      return Optional.of(buildCounter(Integer.parseInt(ctrEn.group(1))));
    }
    Matcher divzh = DIVIDER_ZH.matcher(t);
    Matcher diven = DIVIDER_EN.matcher(t);
    if (divzh.find()) {
      Integer bits = chineseToInt(divzh.group(1));
      if (bits != null) {
        return Optional.of(buildDivider(bits));
      }
    }
    if (diven.find()) {
      return Optional.of(buildDivider(Integer.parseInt(diven.group(1))));
    }
    if (DIVIDER_ZH_BARE.matcher(t).find() || DIVIDER_EN_BARE.matcher(t).find()) {
      return Optional.of(buildDivider(4));
    }
    Matcher johnzh = JOHNSON_ZH.matcher(t);
    Matcher johnen = JOHNSON_EN.matcher(t);
    if (johnzh.find()) {
      Integer bits = chineseToInt(johnzh.group(1));
      if (bits != null) {
        return Optional.of(buildJohnsonCounter(bits));
      }
    }
    if (johnen.find()) {
      return Optional.of(buildJohnsonCounter(Integer.parseInt(johnen.group(1))));
    }
    if (JOHNSON_ZH_BARE.matcher(t).find() || JOHNSON_EN_BARE.matcher(t).find()) {
      return Optional.of(buildJohnsonCounter(4));
    }
    // Ring counter (环形计数器): a true ring counter (single 1 circulating),
    // seeded via SEED (D-FF has no preset; all-zero would dead-lock).
    if (t.contains("环形计数器") || t.contains("ring counter")) {
      return Optional.of(buildRingCounter());
    }
    Matcher pzh = PARITY_ZH.matcher(t);
    Matcher pen = PARITY_EN.matcher(t);
    if (pzh.find()) {
      Integer bits = chineseToInt(pzh.group(1));
      if (bits != null) {
        return Optional.of(buildParity(bits));
      }
    }
    if (pen.find()) {
      return Optional.of(buildParity(Integer.parseInt(pen.group(1))));
    }
    if (ENCODER8_ZH.matcher(t).find() || ENCODER8_EN.matcher(t).find()) {
      return Optional.of(buildEncoder8to3());
    }
    if (ENCODER4_ZH.matcher(t).find() || ENCODER4_EN.matcher(t).find()) {
      return Optional.of(buildEncoder4to2());
    }
    // Bare "优先编码器" without an 8-3 spec defaults to the 8-to-3 encoder.
    if ((t.contains("优先") && t.contains("编码")) || t.contains("priority encoder")) {
      return Optional.of(buildEncoder8to3());
    }
    if (DFF_EN.matcher(t).find() || (t.contains("触发器") && !t.contains("jk") && !t.contains("t触发器") && !t.contains("t触发器"))) {
      return Optional.of(buildDFlipFlop());
    }
    if (t.contains("jk触发器") || t.contains("jk flip") || t.contains("jkff")) {
      return Optional.of(buildJKFlipFlop());
    }
    if (t.contains("t触发器") || t.contains("t flip") || t.contains("tff")) {
      return Optional.of(buildTFlipFlop());
    }
    if (DLATCH_ZH.matcher(t).find() || DLATCH_EN.matcher(t).find()) {
      return Optional.of(buildDLatch());
    }
    // Gray-code COUNTER (格雷码计数器) — checked BEFORE the converters so the
    // "计数器" wording builds the sequential counter, not the combinational
    // binary→gray converter.
    Matcher gcZH = GRAYCNT_ZH.matcher(t);
    Matcher gcEN = GRAYCNT_EN.matcher(t);
    if (gcZH.find()) {
      String g = gcZH.group(1);
      if (g != null) {
        Integer bits = chineseToInt(g);
        if (bits != null) {
          return Optional.of(buildGrayCounter(bits));
        }
      }
      return Optional.of(buildGrayCounter(4));
    }
    if (gcEN.find()) {
      if (gcEN.group(1) != null) {
        return Optional.of(buildGrayCounter(Integer.parseInt(gcEN.group(1))));
      }
      return Optional.of(buildGrayCounter(4));
    }
    // Gray → Binary reverse converter (格雷码转二进制 / gray to binary).
    Matcher g2bzh = G2B_ZH.matcher(t);
    Matcher g2ben = G2B_EN.matcher(t);
    if (g2bzh.find()) {
      Integer bits = chineseToInt(g2bzh.group(1));
      if (bits != null) {
        return Optional.of(buildGrayToBinary(bits));
      }
    }
    if (g2ben.find()) {
      return Optional.of(buildGrayToBinary(Integer.parseInt(g2ben.group(1))));
    }
    if (G2B_ZH_BARE.matcher(t).find() || G2B_EN_BARE.matcher(t).find()) {
      return Optional.of(buildGrayToBinary(4));
    }
    // BCD → binary (2-digit). Guard the reverse "二进制转BCD / to bcd".
    if (!t.contains("转bcd") && !t.contains("to bcd")) {
      if (B2B_ZH_BARE.matcher(t).find() || B2B_EN_BARE.matcher(t).find()) {
        return Optional.of(buildBcdToBinary());
      }
    }
    // Binary → BCD (4-bit; double-dabble beyond 4 bits not built). This is the
    // reverse of bcd2bin; the guard above (转bcd/to bcd) skips these queries.
    if (BINBCD_ZH_BARE.matcher(t).find() || BINBCD_EN_BARE.matcher(t).find()) {
      return Optional.of(buildBinToBcd4());
    }
    // Binary → Gray forward converter. Guard the inverse query text so a
    // "转二进制 / to binary" request routes to the reverse converter above.
    Matcher gzh = GRAY_ZH.matcher(t);
    Matcher gen = GRAY_EN.matcher(t);
    if (!t.contains("转二进制") && !t.contains("to binary")) {
      if (gzh.find()) {
        Integer bits = chineseToInt(gzh.group(1));
        if (bits != null) {
          return Optional.of(buildGrayCode(bits));
        }
      }
      if (gen.find()) {
        return Optional.of(buildGrayCode(Integer.parseInt(gen.group(1))));
      }
      if (GRAY_ZH_BARE.matcher(t).find() || GRAY_EN_BARE.matcher(t).find()) {
        return Optional.of(buildGrayCode(4));
      }
    }
    // Excess-3 ADDER (余3码加法器) — checked before the converter so the
    // "加法器" wording builds the adder, not the BCD→excess-3 converter.
    if (E3ADD_ZH.matcher(t).find() || E3ADD_EN.matcher(t).find()) {
      return Optional.of(buildExcess3Adder());
    }
    // BCD → excess-3 (余3码). Guard the inverse "余3码转8421 / to bcd".
    if (!t.contains("转8421") && !t.contains("to bcd")) {
      if (E3_ZH.matcher(t).find() || E3_EN.matcher(t).find()) {
        return Optional.of(buildExcess3());
      }
    }
    // Excess-3 → BCD (inverse converter; the forward block above skips it).
    if (E3B_ZH.matcher(t).find() || E3B_EN.matcher(t).find()) {
      return Optional.of(buildExcess3ToBcd());
    }
    Matcher twzh = TWOS_ZH.matcher(t);
    Matcher twen = TWOS_EN.matcher(t);
    if (twzh.find()) {
      Integer bits = chineseToInt(twzh.group(1));
      if (bits != null) {
        return Optional.of(buildTwosComplement(bits));
      }
    }
    if (twen.find()) {
      return Optional.of(buildTwosComplement(Integer.parseInt(twen.group(1))));
    }
    if (TWOS_ZH_BARE.matcher(t).find() || TWOS_EN_BARE.matcher(t).find()) {
      return Optional.of(buildTwosComplement(4));
    }
    // 9's complement of a BCD digit (BCD subtraction helper).
    if (NC_ZH.matcher(t).find() || NC_EN.matcher(t).find()) {
      return Optional.of(buildNineComplement());
    }
    if (TRAFFIC_ZH.matcher(t).find() || TRAFFIC_EN.matcher(t).find()) {
      return Optional.of(buildTrafficLight());
    }
    if (SEQDET_ZH.matcher(t).find() || SEQDET_EN.matcher(t).find()) {
      if (t.contains("1101")) {
        return Optional.of(buildSequenceDetector1101());
      }
      // Only 101 is the other implemented pattern; reject explicit different
      // sequences like "110序列检测器" (which would grab a detector via the
      // 序列检测器 substring).
      if (!t.contains("110") && !t.contains("1010")) {
        return Optional.of(buildSequenceDetector101());
      }
    }
    // Bidirectional shift register. Must precede SHIFTREG (双向移位寄存器
    // contains 移位寄存器, which the uni-directional pattern would grab).
    Matcher bsregzh = BSREG_ZH.matcher(t);
    Matcher bsregen = BSREG_EN.matcher(t);
    if (bsregzh.find()) {
      Integer bits = chineseToInt(bsregzh.group(1));
      if (bits != null) {
        return Optional.of(buildBidirectionalShiftRegister(bits));
      }
    }
    if (bsregen.find()) {
      return Optional.of(buildBidirectionalShiftRegister(Integer.parseInt(bsregen.group(1))));
    }
    if (BSREG_ZH_BARE.matcher(t).find() || BSREG_EN_BARE.matcher(t).find()) {
      return Optional.of(buildBidirectionalShiftRegister(4));
    }
    // LFSR (伪随机/m序列). Must precede SHIFTREG/REGISTER (线性反馈移位寄存器
    // contains 移位寄存器 / 寄存器 substrings).
    if (LFSR_ZH.matcher(t).find() || LFSR_EN.matcher(t).find()) {
      return Optional.of(buildLfsr4());
    }
    Matcher shrzh = SHIFTREG_ZH.matcher(t);
    Matcher shren = SHIFTREG_EN.matcher(t);
    if (shrzh.find()) {
      Integer bits = chineseToInt(shrzh.group(1));
      if (bits != null) {
        return Optional.of(buildShiftRegister(bits));
      }
    }
    if (shren.find()) {
      // "N bit shift register" -> group(1); bare "shift register" -> 4 bits
      if (shren.group(1) != null) {
        return Optional.of(buildShiftRegister(Integer.parseInt(shren.group(1))));
      }
      return Optional.of(buildShiftRegister(4));
    }
    if (PISO_ZH.matcher(t).find() || PISO_EN.matcher(t).find()) {
      return Optional.of(buildPisoShiftRegister(4));
    }
    if (SHIFTREG_ZH_BARE.matcher(t).find()) {
      return Optional.of(buildShiftRegister(4));
    }
    Matcher regzh = REG_ZH.matcher(t);
    Matcher regen = REG_EN.matcher(t);
    if (regzh.find()) {
      Integer bits = chineseToInt(regzh.group(1));
      if (bits != null) {
        return Optional.of(buildRegister(bits));
      }
    }
    if (regen.find()) {
      return Optional.of(buildRegister(Integer.parseInt(regen.group(1))));
    }
    // SR latch must precede the bare 锁存器 register fallback so "SR锁存器"
    // does not route to a register.
    if (BUZZER_ZH.matcher(t).find() || SR_ZH.matcher(t).find()) {
      return Optional.of(buildSRLatch());
    }
    if (REG_ZH_BARE.matcher(t).find() && !t.contains("分配器")) {
      return Optional.of(buildRegister(4));
    }
    // Bare descriptions without a width default to a sensible width so plain
    // "加法器 / 比较器 / 计数器 / 译码器" still build a circuit.
    if (isBare(t, "加法器", "二进制加法器")) {
      return Optional.of(buildAdder(4));
    }
    if (isBare(t, "比较器", "大小比较器", "数值比较器")) {
      return Optional.of(buildComparator(1));
    }
    if (isBare(t, "计数器", "数数电路", "二进制计数")) {
      return Optional.of(buildCounter(4));
    }
    if (isBare(t, "译码器", "解码器", "地址译码", "多路分配器", "分配器")) {
      return Optional.of(buildDecoder(2));
    }
    if (isBare(t, "乘法器", "二进制乘法器")) {
      return Optional.of(buildMultiplier(4));
    }
    if (isBare(t, "减法器", "全减器", "二进制减法器")) {
      return Optional.of(buildSubtractor(4));
    }
    if (isBare(t, "编码器", "二进制编码器")) {
      return Optional.of(buildEncoder8to3());
    }
    if (isBare(t, "奇偶校验器", "偶校验器", "奇校验器")) {
      return Optional.of(buildParity(4));
    }
    // Tri-state buffer (native TTL 74125 gate 1)
    if (TSB_ZH.matcher(t).find() || TSB_EN.matcher(t).find()) {
      return Optional.of(buildTriStateBuffer());
    }
    // N-bit ALU: 4 function select lines S1S0; 00=AND 01=OR 10=ADD 11=SUB.
    Matcher aluzh = ALU_ZH.matcher(t);
    Matcher aluen = ALU_EN.matcher(t);
    if (aluzh.find()) {
      Integer bits = chineseToInt(aluzh.group(1));
      if (bits != null) {
        return Optional.of(buildAlu(bits));
      }
    }
    if (aluen.find()) {
      return Optional.of(buildAlu(Integer.parseInt(aluen.group(1))));
    }
    if (ALU_ZH_BARE.matcher(t).find() || ALU_EN_BARE.matcher(t).find()) {
      return Optional.of(buildAlu(4));
    }
    // N-bit barrel shifter (logical left shift by SH). Bare queries default to 4.
    Matcher barrelzh = BARREL_ZH.matcher(t);
    Matcher barrelen = BARREL_EN.matcher(t);
    if (barrelzh.find()) {
      Integer n = chineseToInt(barrelzh.group(1));
      if (n != null && n >= 2 && n <= 8) {
        return Optional.of(buildBarrelShifter(n));
      }
    }
    if (barrelen.find()) {
      int n = Integer.parseInt(barrelen.group(1));
      if (n >= 2 && n <= 8) {
        return Optional.of(buildBarrelShifter(n));
      }
    }
    if (BARREL_ZH_BARE.matcher(t).find() || BARREL_EN_BARE.matcher(t).find()) {
      return Optional.of(buildBarrelShifter(4));
    }
    // N-bit rotate shifter (循环移位器): rotate by SH, wrap-around (no gnd needed).
    Matcher rotrightzh = ROTATE_RIGHT_ZH.matcher(t);
    if (rotrightzh.find()) {
      Integer n = chineseToInt(rotrightzh.group(1));
      if (n != null && n >= 2 && n <= 8) {
        return Optional.of(buildRotateShifter(n, false));
      }
    }
    Matcher rotzh = ROTATE_ZH.matcher(t);
    if (rotzh.find()) {
      Integer n = chineseToInt(rotzh.group(1));
      if (n != null && n >= 2 && n <= 8) {
        return Optional.of(buildRotateShifter(n, true));
      }
    }
    Matcher roten = ROTATE_EN.matcher(t);
    if (roten.find()) {
      int n = Integer.parseInt(roten.group(1));
      if (n >= 2 && n <= 8) {
        String dir = roten.group(2) != null ? roten.group(2) : roten.group(3);
        boolean left = dir == null || dir.equalsIgnoreCase("left");
        return Optional.of(buildRotateShifter(n, left));
      }
    }
    if (ROTATE_ZH_BARE.matcher(t).find() || ROTATE_EN_BARE.matcher(t).find()) {
      // Bare rotate defaults left unless the query says 右/right.
      boolean left = !t.contains("右") && !t.contains("right");
      return Optional.of(buildRotateShifter(4, left));
    }
    // N-bit arithmetic right shift (算术右移): sign fill (checked before LSR so
    // "算术右移" never falls through to the bare LSR "右移" branch).
    Matcher sarzh = SAR_ZH.matcher(t);
    if (sarzh.find()) {
      Integer n = chineseToInt(sarzh.group(1));
      if (n != null && n >= 2 && n <= 8) {
        return Optional.of(buildArithmeticShiftRight(n));
      }
    }
    Matcher saren = SAR_EN.matcher(t);
    if (saren.find()) {
      int n = Integer.parseInt(saren.group(1));
      if (n >= 2 && n <= 8) {
        return Optional.of(buildArithmeticShiftRight(n));
      }
    }
    if (SAR_ZH_BARE.matcher(t).find() || SAR_EN_BARE.matcher(t).find()) {
      return Optional.of(buildArithmeticShiftRight(4));
    }
    // N-bit logical right shift (逻辑右移): zero fill.
    Matcher lsrzh = LSR_ZH.matcher(t);
    if (lsrzh.find()) {
      Integer n = chineseToInt(lsrzh.group(1));
      if (n != null && n >= 2 && n <= 8) {
        return Optional.of(buildLogicalShiftRight(n));
      }
    }
    Matcher lsren = LSR_EN.matcher(t);
    if (lsren.find()) {
      int n = Integer.parseInt(lsren.group(1));
      if (n >= 2 && n <= 8) {
        return Optional.of(buildLogicalShiftRight(n));
      }
    }
    if (LSR_ZH_BARE.matcher(t).find() || LSR_EN_BARE.matcher(t).find()) {
      return Optional.of(buildLogicalShiftRight(4));
    }
    // N-bit unsigned restoring divider (除法器). Checked after 分频器/frequency
    // divider (DIVIDER_*) so those keep their sequential meaning.
    Matcher divizh = DIVISION_ZH.matcher(t);
    if (divizh.find()) {
      Integer n = chineseToInt(divizh.group(1));
      if (n != null && n >= 2 && n <= 4) {
        return Optional.of(buildArithmeticDivider(n));
      }
    }
    Matcher divien = DIVISION_EN.matcher(t);
    if (divien.find()) {
      int n = Integer.parseInt(divien.group(1));
      if (n >= 2 && n <= 4) {
        return Optional.of(buildArithmeticDivider(n));
      }
    }
    if (DIVISION_ZH_BARE.matcher(t).find() || DIVISION_EN_BARE.matcher(t).find()) {
      return Optional.of(buildArithmeticDivider(4));
    }
    return Optional.empty();
  }

  /** True when the query mentions one of the words but no width prefix (e.g. "计数器" not "4位计数器"). */
  private static boolean isBare(String t, String... words) {
    boolean anyWord = false;
    for (String w : words) {
      if (t.contains(w)) {
        anyWord = true;
        break;
      }
    }
    if (!anyWord) {
      return false;
    }
    // Reject if a digit / Chinese numeral appears before any word (that path is
    // handled by the width-specific patterns above).
    for (String w : words) {
      int idx = t.indexOf(w);
      if (idx > 0) {
        String prefix = t.substring(0, idx);
        if (prefix.matches(".*[0-9一二三四五六七八九十两].*")) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * N-bit even-parity generator/checker: Y = XOR of all inputs (2-input XOR
   * cascade), plus the parity of the input bits. Output P = even parity.
   */
  static CircuitSpec buildParity(int bits) {
    if (bits < 2 || bits > 16) {
      throw new IllegalArgumentException("Parity width must be 2..16, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < bits; i++) {
      addInput(ports, "d" + i, "D" + i, "par" + Math.max(1, i) + "_x");
    }
    addOutput(ports, "p", "P", "par" + (bits - 1) + "_x");

    String acc = "d0";
    for (int i = 1; i < bits; i++) {
      String g = "par" + i + "_x";
      components.add(new ComponentSpec(g, "Gates", "XOR Gate", 1, "PAR" + i));
      hints.add(new LayoutHint(g, 0, 0, i, null));
      nets.add(net("px" + i, e(acc, 0), e(g, 1)));
      nets.add(net("py" + i, e("d" + i, 0), e(g, 2)));
      acc = g;
    }
    nets.add(net("pout", e(acc, 0), e("p", 0)));

    return new CircuitSpec("parity_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * 8-to-3 priority encoder as pure logic gates (high-active). I7 has highest
   * priority; when I_k is the highest asserted input, Y2Y1Y0 = k in binary and
   * G is 1. All gates are native, so the in-process truth-table evaluator and
   * the official Logisim JAR both verify it (the previous 74148 chip template
   * exposed active-low nEI/nGS/nEO ports and could never be asserted).
   */
  static CircuitSpec buildEncoder8to3() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    // Column-0 left-associative G chain doubles as the anchor layer: each input
    // pin (except I6/I7 on the innermost gate) targets a gate on its own row,
    // so pin wires are short horizontal stubs instead of long boundary-lane
    // verticals. G = (((((((I6|I7)|I5)|I4)|I3)|I2)|I1)|I0).
    addInput(ports, "i6", "I6", "enc_g_o1");
    addInput(ports, "i7", "I7", "enc_g_o1");
    addInput(ports, "i5", "I5", "enc_g_o2");
    addInput(ports, "i4", "I4", "enc_g_o3");
    addInput(ports, "i3", "I3", "enc_g_o4");
    addInput(ports, "i2", "I2", "enc_g_o5");
    addInput(ports, "i1", "I1", "enc_g_o6");
    addInput(ports, "i0", "I0", "enc_g_o7");
    addOutput(ports, "y2", "Y2", "enc_y2_o3");
    addOutput(ports, "y1", "Y1", "enc_y1_o1");
    addOutput(ports, "y0", "Y0", "enc_y0c_o1");
    addOutput(ports, "g", "G", "enc_g_o7");

    String g = orCombine("enc_g", 0, 0, components, hints, nets,
        List.of("i6", "i7", "i5", "i4", "i3", "i2", "i1", "i0"));
    // Y2 = I4|I5|I6|I7 : whenever any top-half input is set, the highest
    // asserted bit lies in 4..7, so bit-2 needs no masking.
    String y2 = orCombine("enc_y2", 3, 0, components, hints, nets, List.of("i4", "i5", "i6", "i7"));
    // Y1 = (I6|I7)  OR  ( !(I4|I5) AND (I3|I2) ).
    String r67 = orCombine("enc_r67", 4, 0, components, hints, nets, List.of("i6", "i7"));
    String r45 = orCombine("enc_r45", 4, 1, components, hints, nets, List.of("i4", "i5"));
    String n45 = encNot("enc_n45", 4, 2, components, hints, nets, r45);
    String r23 = orCombine("enc_r23", 4, 3, components, hints, nets, List.of("i3", "i2"));
    String y1a = encAnd("enc_y1a", 4, 4, components, hints, nets, n45, r23);
    String y1 = orCombine("enc_y1", 4, 5, components, hints, nets, List.of(r67, y1a));
    // Y0 = I7 | (!I6&I5) | (!I4&!I5&!I6&I3) | (!I2&!I3&!I4&!I5&!I6&I1).
    String n6 = encNot("enc_n6", 5, 0, components, hints, nets, "i6");
    String t5 = encAnd("enc_t5", 5, 1, components, hints, nets, n6, "i5");
    String t3a = encAnd("enc_t3a", 5, 2, components, hints, nets, n45, n6);
    String t3b = encAnd("enc_t3b", 5, 3, components, hints, nets, t3a, "i3");
    String n2 = encNot("enc_n2", 5, 4, components, hints, nets, "i2");
    String n3 = encNot("enc_n3", 5, 5, components, hints, nets, "i3");
    String n23 = encAnd("enc_n23", 5, 6, components, hints, nets, n2, n3);
    String t1a = encAnd("enc_t1a", 5, 7, components, hints, nets, n23, n45);
    String t1b = encAnd("enc_t1b", 5, 8, components, hints, nets, t1a, n6);
    String t1c = encAnd("enc_t1c", 5, 9, components, hints, nets, t1b, "i1");
    String y0a = orCombine("enc_y0a", 5, 10, components, hints, nets, List.of("i7", t5));
    String y0b = orCombine("enc_y0b", 5, 11, components, hints, nets, List.of(y0a, t3b));
    String y0 = orCombine("enc_y0c", 5, 12, components, hints, nets, List.of(y0b, t1c));
    nets.add(net("enc_y2o", e(y2, 0), e("y2", 0)));
    nets.add(net("enc_y1o", e(y1, 0), e("y1", 0)));
    nets.add(net("enc_y0o", e(y0, 0), e("y0", 0)));
    nets.add(net("enc_go", e(g, 0), e("g", 0)));

    return new CircuitSpec("encoder_8to3", components, ports, nets, hints);
  }

  /** 4-to-2 priority encoder as logic gates. I3 has highest priority;
   *  Y1 = I3|I2, Y0 = I3 | (I1 & !I2), G = I0|I1|I2|I3. */
  static CircuitSpec buildEncoder4to2() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    // Column-0 G chain anchors every pin to a gate on its own row (I2/I3 pair
    // on the innermost gate). Y1 = I2|I3 reuses that innermost gate.
    addInput(ports, "i2", "I2", "enc4_g_o1");
    addInput(ports, "i3", "I3", "enc4_g_o1");
    addInput(ports, "i1", "I1", "enc4_g_o2");
    addInput(ports, "i0", "I0", "enc4_g_o3");
    addOutput(ports, "y1", "Y1", "enc4_g_o1");
    addOutput(ports, "y0", "Y0", "enc4_y0_o1");
    addOutput(ports, "g", "G", "enc4_g_o3");
    String g = orCombine("enc4_g", 0, 0, components, hints, nets,
        List.of("i2", "i3", "i1", "i0"));
    String n2 = encNot("enc4_n2", 1, 3, components, hints, nets, "i2");
    String p0 = encAnd("enc4_p0", 1, 4, components, hints, nets, "i1", n2);
    String y0 = orCombine("enc4_y0", 1, 5, components, hints, nets, List.of("i3", p0));
    nets.add(net("enc4_y1o", e("enc4_g_o1", 0), e("y1", 0)));
    nets.add(net("enc4_y0o", e(y0, 0), e("y0", 0)));
    nets.add(net("enc4_go", e(g, 0), e("g", 0)));

    return new CircuitSpec("encoder_4to2", components, ports, nets, hints);
  }

  /** Single NOT gate (input=end1, output=end0). Label = component id, so each
   *  gate carries a unique label (the direct compiler emits each label once). */
  private static String encNot(String id, int col, int row, List<ComponentSpec> components,
      List<LayoutHint> hints, List<NetSpec> nets, String in) {
    components.add(new ComponentSpec(id, "Gates", "NOT Gate", 1, id));
    hints.add(new LayoutHint(id, col, 0, row, null));
    nets.add(net(id + "_i", e(in, 0), e(id, 1)));
    return id;
  }

  /** Single 2-input AND gate. */
  private static String encAnd(String id, int col, int row, List<ComponentSpec> components,
      List<LayoutHint> hints, List<NetSpec> nets, String a, String b) {
    components.add(new ComponentSpec(id, "Gates", "AND Gate", 1, id));
    hints.add(new LayoutHint(id, col, 0, row, null));
    nets.add(net(id + "_a", e(a, 0), e(id, 1)));
    nets.add(net(id + "_b", e(b, 0), e(id, 2)));
    return id;
  }

  /** Chain of 2-input OR gates OR-ing `srcs` (size ≥ 2); gate k sits at column
   *  `col`, row `row + k - 1`, labelled by its unique id. Returns the trailing
   *  gate id, whose output (end 0) the caller nets to an output pin or a
   *  later-stage input. */
  private static String orCombine(String prefix, int col, int row, List<ComponentSpec> components,
      List<LayoutHint> hints, List<NetSpec> nets, List<String> srcs) {
    String acc = srcs.get(0);
    for (int k = 1; k < srcs.size(); k++) {
      String gate = prefix + "_o" + k;
      components.add(new ComponentSpec(gate, "Gates", "OR Gate", 1, gate));
      hints.add(new LayoutHint(gate, col, 0, row + k - 1, null));
      nets.add(net(prefix + "_a" + k, e(acc, 0), e(gate, 1)));
      nets.add(net(prefix + "_b" + k, e(srcs.get(k), 0), e(gate, 2)));
      acc = gate;
    }
    return acc;
  }

  /**
   * N-bit binary up-counter built from 74161 (4-bit) chips cascaded via TC to
   * ENT. Port map (verified catalog): end0=nCLR, end1=CLK, end2..5=D0..D3,
   * end6=ENP, end7=nLOAD, end8=ENT, end9=Q3, end10=Q2, end11=Q1, end12=Q0,
   * end13=TC.
   */
  static CircuitSpec buildCounter(int bits) {
    if (bits < 1 || bits > 16) {
      throw new IllegalArgumentException("Counter width must be 1..16, got " + bits);
    }
    int chips = (bits + 3) / 4;
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int c = 0; c < chips; c++) {
      components.add(new ComponentSpec("cnt" + c, "TTL", "74161", 1, "U" + (c + 1)));
      hints.add(new LayoutHint("cnt" + c, c, 0, 0, null));
    }
    addClockInput(ports, "clk", "CLK", "cnt0");
    addInput(ports, "enp", "ENP", "cnt0");
    addInput(ports, "ent", "ENT", "cnt0");
    addInput(ports, "nload", "nLOAD", "cnt0");
    addInput(ports, "nclr", "nCLR", "cnt0");
    for (int i = 0; i < bits; i++) {
      addInput(ports, "d" + i, "D" + i, "cnt" + (i / 4));
      addOutput(ports, "q" + i, "Q" + i, "cnt" + (i / 4));
    }
    addOutput(ports, "rc", "RCO", "cnt" + (chips - 1));

    for (int c = 0; c < chips; c++) {
      int base = c * 4;
      String g = "cnt" + c;
      nets.add(net("clk" + c, e("clk", 0), e(g, 1)));
      nets.add(net("enp" + c, e("enp", 0), e(g, 6)));
      nets.add(net("load" + c, e("nload", 0), e(g, 7)));
      nets.add(net("clr" + c, e("nclr", 0), e(g, 0)));
      if (c == 0) {
        nets.add(net("ent" + c, e("ent", 0), e(g, 8)));
      } else {
        // cascade: lower chip TC drives this chip's ENT
        nets.add(net("ent" + c, e("cnt" + (c - 1), 13), e(g, 8)));
      }
      for (int i = 0; i < 4; i++) {
        int bit = base + i;
        if (bit < bits) {
          nets.add(net("d" + bit, e("d" + bit, 0), e(g, 2 + i)));
          // Q3..Q0 are ends 9..12 (Q3 = end9 = MSB)
          nets.add(net("q" + bit, e(g, 12 - i), e("q" + bit, 0)));
        }
      }
    }
    nets.add(net("rc", e("cnt" + (chips - 1), 13), e("rc", 0)));

    return new CircuitSpec("counter_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-stage synchronous frequency divider built as a proper binary counter:
   * T_0 = external enable; T_i = AND(Q_0, ..., Q_{i-1}) so stage i toggles
   * only when every lower stage is high → Q_{n-1} toggles every 2^(n-1) clock
   * edges, a 2^n divider. Shared CLK and RST. Port maps: T-FF end0=T,
   * end1=CLK, end2=Q, end3=nQ, end4=reset; 2-input AND end0=out, end1/end2=in.
   */
  static CircuitSpec buildDivider(int stages) {
    if (stages < 1 || stages > 16) {
      throw new IllegalArgumentException("Divider stages must be 1..16, got " + stages);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "div0");
    addInput(ports, "t", "T", "div0");
    addInput(ports, "rst", "RST", "div0");
    for (int i = 0; i < stages; i++) {
      String g = "div" + i;
      components.add(new ComponentSpec(g, "Memory", "T Flip-Flop", 1, "U" + (i + 1)));
      hints.add(new LayoutHint(g, i, 0, 0, null));
      addOutput(ports, "q" + i, "Q" + i, g);
      if (i == 0) {
        nets.add(net("div_t" + i, e("t", 0), e(g, 0)));
      } else {
        // T_i = AND(Q_0, ..., Q_{i-1}) via a 2-input AND cascade.
        String accId = "div0";
        int accEnd = 2; // flip-flop Q output
        for (int j = 1; j < i; j++) {
          String gateId = "divta" + i + "_" + j;
          components.add(new ComponentSpec(gateId, "Gates", "AND Gate", 1, "TA" + i + "_" + j));
          hints.add(new LayoutHint(gateId, i, 0, j, null));
          nets.add(net("divta" + i + "_" + j + "x", e(accId, accEnd), e(gateId, 1)));
          nets.add(net("divta" + i + "_" + j + "y", e("div" + j, 2), e(gateId, 2)));
          accId = gateId;
          accEnd = 0; // gate output
        }
        nets.add(net("div_t" + i, e(accId, accEnd), e(g, 0)));
      }
      nets.add(net("div_clk" + i, e("clk", 0), e(g, 1)));
      nets.add(net("div_rst" + i, e("rst", 0), e(g, 4)));
      nets.add(net("div_q" + i, e(g, 2), e("q" + i, 0)));
    }
    return new CircuitSpec("divider_" + stages + "stage", components, ports, nets, hints);
  }

  /**
   * N-stage Johnson (twisted ring) counter: N D flip-flops in a shift chain
   * (D_i = Q_{i-1}) with the last stage's nQ fed back to D_0 (D_0 = NOT Q_{n-1}),
   * so a single 1 walks across and back → 2N distinct states. Shared CLK and
   * RST. Port maps: D-FF end0=D, end1=CLK, end2=Q, end3=nQ, end4=reset; NOT
   * end0=out, end1=in.
   */
  static CircuitSpec buildJohnsonCounter(int stages) {
    if (stages < 1 || stages > 32) {
      throw new IllegalArgumentException("Johnson counter stages must be 1..32, got " + stages);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "j0");
    addInput(ports, "rst", "RST", "j0");
    components.add(new ComponentSpec("jn", "Gates", "NOT Gate", 1, "JN"));
    hints.add(new LayoutHint("jn", 0, 0, 0, null));
    for (int i = 0; i < stages; i++) {
      String g = "j" + i;
      components.add(new ComponentSpec(g, "Memory", "D Flip-Flop", 1, "U" + (i + 1)));
      hints.add(new LayoutHint(g, i + 1, 0, 0, null));
      addOutput(ports, "q" + i, "Q" + i, g);
      if (i == 0) {
        nets.add(net("j_nq", e("j" + (stages - 1), 2), e("jn", 1)));
        nets.add(net("j_d" + i, e("jn", 0), e(g, 0)));
      } else {
        nets.add(net("j_d" + i, e("j" + (i - 1), 2), e(g, 0)));
      }
      nets.add(net("j_clk" + i, e("clk", 0), e(g, 1)));
      nets.add(net("j_rst" + i, e("rst", 0), e(g, 4)));
      nets.add(net("j_q" + i, e(g, 2), e("q" + i, 0)));
    }
    return new CircuitSpec("johnson_" + stages + "stage", components, ports, nets, hints);
  }

  /**
   * N-bit register: N parallel D flip-flops sharing one Clock and one RST.
   * Port map per flip-flop: end0=D, end1=CLK, end2=Q, end3=nQ, end4=reset.
   */
  static CircuitSpec buildRegister(int bits) {
    if (bits < 1 || bits > 32) {
      throw new IllegalArgumentException("Register width must be 1..32, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "reg0");
    addInput(ports, "rst", "RST", "reg0");
    for (int i = 0; i < bits; i++) {
      String g = "reg" + i;
      components.add(new ComponentSpec(g, "Memory", "D Flip-Flop", 1, "U" + (i + 1)));
      hints.add(new LayoutHint(g, i, 0, 0, null));
      addInput(ports, "d" + i, "D" + i, g);
      addOutput(ports, "q" + i, "Q" + i, g);
      nets.add(net("reg_d" + i, e("d" + i, 0), e(g, 0)));
      nets.add(net("reg_clk" + i, e("clk", 0), e(g, 1)));
      nets.add(net("reg_rst" + i, e("rst", 0), e(g, 4)));
      nets.add(net("reg_q" + i, e(g, 2), e("q" + i, 0)));
    }
    return new CircuitSpec("register_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-bit accumulator (累加器): ACC ← ACC + A on each rising clock edge.
   * N D flip-flops + an N-bit ripple adder; adder sum feeds the flip-flop D
   * inputs, flip-flop Q feeds the adder B side and the Q outputs. Shared CLK,
   * RST (async reset) and a GND pin for the constant-0 carry lanes.
   * Port map per flip-flop: end0=D, end1=CLK, end2=Q, end3=nQ, end4=reset.
   */
  static CircuitSpec buildAccumulator(int bits) {
    if (bits < 2 || bits > 8) {
      throw new IllegalArgumentException("Accumulator width must be 2..8, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    int seq = 0;

    addClockInput(ports, "clk", "CLK", "acc_ff0");
    addInput(ports, "rst", "RST", "acc_ff0");
    addInput(ports, "gnd", "GND", "acc_ff0");
    for (int i = 0; i < bits; i++) {
      addInput(ports, "a" + i, "A" + i, "acc_ff0");
      addOutput(ports, "q" + i, "Q" + i, "acc_ff0");
    }
    for (int i = 0; i < bits; i++) {
      components.add(new ComponentSpec("acc_ff" + i, "Memory", "D Flip-Flop", 1, "U" + (i + 1)));
      hints.add(new LayoutHint("acc_ff" + i, i, 0, 0, null));
    }

    // Ripple adder: sum[i] = a[i] + q[i] + carry; carry chain via fg.
    String[] sum = new String[bits];
    String c = "gnd";
    for (int i = 0; i < bits; i++) {
      String x = "acc_x" + seq;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "AX" + (seq + 1)));
      hints.add(new LayoutHint(x, 1, 0, i, null));
      String s = "acc_s" + seq;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "AS" + (seq + 1)));
      hints.add(new LayoutHint(s, 1, 0, i, null));
      String fa = "acc_fa" + seq;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "AA" + (seq + 1)));
      hints.add(new LayoutHint(fa, 1, 0, i, null));
      String fb = "acc_fb" + seq;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "AB" + (seq + 1)));
      hints.add(new LayoutHint(fb, 1, 0, i, null));
      String fo = "acc_fo" + seq;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "AO" + (seq + 1)));
      hints.add(new LayoutHint(fo, 1, 0, i, null));
      String fg = "acc_fg" + seq;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "AG" + (seq + 1)));
      hints.add(new LayoutHint(fg, 1, 0, i, null));
      seq++;
      nets.add(net("acc_xa" + seq, e("a" + i, 0), e(x, 1)));
      nets.add(net("acc_xb" + seq, e("acc_ff" + i, 2), e(x, 2)));
      nets.add(net("acc_sa" + seq, e(x, 0), e(s, 1)));
      nets.add(net("acc_sb" + seq, e(c, 0), e(s, 2)));
      sum[i] = s;
      nets.add(net("acc_faa" + seq, e("a" + i, 0), e(fa, 1)));
      nets.add(net("acc_fab" + seq, e("acc_ff" + i, 2), e(fa, 2)));
      nets.add(net("acc_fba" + seq, e(c, 0), e(fb, 1)));
      nets.add(net("acc_fbb" + seq, e(x, 0), e(fb, 2)));
      nets.add(net("acc_fo1" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net("acc_fo2" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net("acc_fg1" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net("acc_fg2" + seq, e("gnd", 0), e(fg, 2)));
      c = fg;
    }
    // Connect flip-flops: D = sum, CLK = clk, RST = rst, Q -> output pins.
    for (int i = 0; i < bits; i++) {
      nets.add(net("acc_d" + i, e(sum[i], 0), e("acc_ff" + i, 0)));
      nets.add(net("acc_clk" + i, e("clk", 0), e("acc_ff" + i, 1)));
      nets.add(net("acc_rst" + i, e("rst", 0), e("acc_ff" + i, 4)));
      nets.add(net("acc_q" + i, e("acc_ff" + i, 2), e("q" + i, 0)));
    }

    return new CircuitSpec("accumulator_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-bit multiply-accumulate (乘累加器 / MAC): ACC += A*B on every clock
   * edge. Product A*B is built as 2N-bit ripple adder over partial products;
   * the accumulation adder adds the product to the FF feedback. FF port map:
   * end0=D, end1=CLK, end2=Q, end3=nQ, end4=reset.
   */
  static CircuitSpec buildMac(int bits) {
    if (bits < 2 || bits > 4) {
      throw new IllegalArgumentException("MAC width must be 2..4, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    int seq = 0;
    int outBits = 2 * bits;

    addClockInput(ports, "clk", "CLK", "mac_ff0");
    addInput(ports, "rst", "RST", "mac_ff0");
    addInput(ports, "gnd", "GND", "mac_ff0");
    for (int j = 0; j < bits; j++) {
      addInput(ports, "a" + j, "A" + j, "mac_ff0");
    }
    for (int i = 0; i < bits; i++) {
      addInput(ports, "b" + i, "B" + i, "mac_ff0");
    }
    for (int k = 0; k < outBits; k++) {
      addOutput(ports, "q" + k, "Q" + k, "mac_ff" + k);
    }

    // Accumulator flip-flops.
    for (int k = 0; k < outBits; k++) {
      components.add(new ComponentSpec("mac_ff" + k, "Memory", "D Flip-Flop", 1, "U" + (k + 1)));
      hints.add(new LayoutHint("mac_ff" + k, 0, 0, k, null));
    }

    // Partial products pp[i][j] = A[j] AND B[i].
    String[][] pp = new String[bits][bits];
    for (int i = 0; i < bits; i++) {
      for (int j = 0; j < bits; j++) {
        String g = "mac_p" + seq;
        components.add(new ComponentSpec(g, "Gates", "AND Gate", 1, "MACP" + (seq + 1)));
        hints.add(new LayoutHint(g, 1, 0, i, null));
        nets.add(net("mac_pa" + seq, e("a" + j, 0), e(g, 1)));
        nets.add(net("mac_pb" + seq, e("b" + i, 0), e(g, 2)));
        pp[i][j] = g;
        seq++;
      }
    }

    // Product P = A*B: ripple-adder stages over shifted partial products.
    String[] prod = new String[outBits];
    String[] acc = new String[bits];
    for (int k = 0; k < bits; k++) {
      acc[k] = pp[0][k];
    }
    for (int i = 1; i < bits; i++) {
      int accBits = acc.length;
      int stageBits = accBits + 2;
      String[] nacc = new String[stageBits];
      String cin = null;
      for (int k = 0; k < stageBits; k++) {
        if (k < i) {
          nacc[k] = acc[k];
          continue;
        }
        String a = k < accBits ? acc[k] : "gnd";
        int pj = k - i;
        String b = (pj >= 0 && pj < bits) ? pp[i][pj] : "gnd";
        String cinSig = cin == null ? "gnd" : cin;
        String x = "mac_x" + seq;
        components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "MACX" + (seq + 1)));
        hints.add(new LayoutHint(x, 2 + i, 0, k, null));
        String s = "mac_s" + seq;
        components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "MACS" + (seq + 1)));
        hints.add(new LayoutHint(s, 2 + i, 0, k, null));
        String fa = "mac_f" + seq;
        components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "MACA" + (seq + 1)));
        hints.add(new LayoutHint(fa, 2 + i, 0, k, null));
        String fb = "mac_b" + seq;
        components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "MACB" + (seq + 1)));
        hints.add(new LayoutHint(fb, 2 + i, 0, k, null));
        String fo = "mac_o" + seq;
        components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "MACO" + (seq + 1)));
        hints.add(new LayoutHint(fo, 2 + i, 0, k, null));
        String fg = "mac_g" + seq;
        components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "MACG" + (seq + 1)));
        hints.add(new LayoutHint(fg, 2 + i, 0, k, null));
        seq++;
        nets.add(net("mac_xa" + seq, e(a, 0), e(x, 1)));
        nets.add(net("mac_xb" + seq, e(b, 0), e(x, 2)));
        nets.add(net("mac_sa" + seq, e(x, 0), e(s, 1)));
        nets.add(net("mac_sb" + seq, e(cinSig, 0), e(s, 2)));
        nacc[k] = s;
        nets.add(net("mac_fa" + seq, e(a, 0), e(fa, 1)));
        nets.add(net("mac_fb" + seq, e(b, 0), e(fa, 2)));
        nets.add(net("mac_ga" + seq, e(cinSig, 0), e(fb, 1)));
        nets.add(net("mac_gb" + seq, e(x, 0), e(fb, 2)));
        nets.add(net("mac_o1" + seq, e(fa, 0), e(fo, 1)));
        nets.add(net("mac_o2" + seq, e(fb, 0), e(fo, 2)));
        nets.add(net("mac_g1" + seq, e(fo, 0), e(fg, 1)));
        nets.add(net("mac_g2" + seq, e("gnd", 0), e(fg, 2)));
        cin = fg;
      }
      acc = nacc;
    }
    for (int k = 0; k < outBits; k++) {
      prod[k] = acc[k];
    }

    // Accumulation: sum[k] = prod[k] + Q[k] + carry (ripple); FF D = sum.
    String cin = null;
    String[] sum = new String[outBits];
    for (int k = 0; k < outBits; k++) {
      String a = prod[k];
      String b = "mac_ff" + k;
      String cinSig = cin == null ? "gnd" : cin;
      String x = "mac_y" + seq;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "MACY" + (seq + 1)));
      hints.add(new LayoutHint(x, 3 + bits, 0, k, null));
      String s = "mac_t" + seq;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "MACT" + (seq + 1)));
      hints.add(new LayoutHint(s, 3 + bits, 0, k, null));
      String fa = "mac_h" + seq;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "MACH" + (seq + 1)));
      hints.add(new LayoutHint(fa, 3 + bits, 0, k, null));
      String fb = "mac_i" + seq;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "MACI" + (seq + 1)));
      hints.add(new LayoutHint(fb, 3 + bits, 0, k, null));
      String fo = "mac_j" + seq;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "MACJ" + (seq + 1)));
      hints.add(new LayoutHint(fo, 3 + bits, 0, k, null));
      String fg = "mac_k" + seq;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "MACK" + (seq + 1)));
      hints.add(new LayoutHint(fg, 3 + bits, 0, k, null));
      seq++;
      nets.add(net("mac_ya" + seq, e(a, 0), e(x, 1)));
      nets.add(net("mac_yb" + seq, e(b, 2), e(x, 2)));
      nets.add(net("mac_ta" + seq, e(x, 0), e(s, 1)));
      nets.add(net("mac_tb" + seq, e(cinSig, 0), e(s, 2)));
      sum[k] = s;
      nets.add(net("mac_ha" + seq, e(a, 0), e(fa, 1)));
      nets.add(net("mac_hb" + seq, e(b, 2), e(fa, 2)));
      nets.add(net("mac_ia" + seq, e(cinSig, 0), e(fb, 1)));
      nets.add(net("mac_ib" + seq, e(x, 0), e(fb, 2)));
      nets.add(net("mac_j1" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net("mac_j2" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net("mac_k1" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net("mac_k2" + seq, e("gnd", 0), e(fg, 2)));
      cin = fg;
    }

    // Connect flip-flops: D = sum, CLK, RST, Q -> output pins.
    for (int k = 0; k < outBits; k++) {
      nets.add(net("mac_d" + k, e(sum[k], 0), e("mac_ff" + k, 0)));
      nets.add(net("mac_clk" + k, e("clk", 0), e("mac_ff" + k, 1)));
      nets.add(net("mac_rst" + k, e("rst", 0), e("mac_ff" + k, 4)));
      nets.add(net("mac_q" + k, e("mac_ff" + k, 2), e("q" + k, 0)));
    }

    return new CircuitSpec("mac_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * Edge detector (边沿检测器): a D-FF samples IN on each clock edge; the
   * combinational block emits a one-clock pulse when IN differs from the
   * previous sample. RISE = IN ∧ ¬Q (previous), FALL = ¬IN ∧ Q. Port maps:
   * D-FF end0=D, end1=CLK, end2=Q, end4=reset; NOT end0=out/end1=in; AND
   * end0=out/end1/end2=in.
   */
  static CircuitSpec buildEdgeDetector() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "ed_ff");
    addInput(ports, "in", "IN", "ed_ff");
    addInput(ports, "rst", "RST", "ed_ff");
    addOutput(ports, "rise", "RISE", "ed_rise");
    addOutput(ports, "fall", "FALL", "ed_fall");
    components.add(new ComponentSpec("ed_ff", "Memory", "D Flip-Flop", 1, "U1"));
    hints.add(new LayoutHint("ed_ff", 0, 0, 0, null));
    String notQ = "ed_nq";
    components.add(new ComponentSpec(notQ, "Gates", "NOT Gate", 1, "N1"));
    hints.add(new LayoutHint(notQ, 1, 0, 0, null));
    String notIn = "ed_ni";
    components.add(new ComponentSpec(notIn, "Gates", "NOT Gate", 1, "N2"));
    hints.add(new LayoutHint(notIn, 1, 0, 3, null));
    String rise = "ed_rise";
    components.add(new ComponentSpec(rise, "Gates", "AND Gate", 1, "A1"));
    hints.add(new LayoutHint(rise, 1, 0, 1, null));
    String fall = "ed_fall";
    components.add(new ComponentSpec(fall, "Gates", "AND Gate", 1, "A2"));
    hints.add(new LayoutHint(fall, 1, 0, 2, null));
    nets.add(net("ed_d", e("in", 0), e("ed_ff", 0)));
    nets.add(net("ed_clk", e("clk", 0), e("ed_ff", 1)));
    nets.add(net("ed_rst", e("rst", 0), e("ed_ff", 4)));
    nets.add(net("ed_nq_in", e("ed_ff", 2), e(notQ, 1)));
    nets.add(net("ed_ni_in", e("in", 0), e(notIn, 1)));
    nets.add(net("ed_rise_a", e("in", 0), e(rise, 1)));
    nets.add(net("ed_rise_b", e(notQ, 0), e(rise, 2)));
    nets.add(net("ed_fall_a", e("ed_ff", 2), e(fall, 1)));
    nets.add(net("ed_fall_b", e(notIn, 0), e(fall, 2)));
    nets.add(net("ed_rise_o", e(rise, 0), e("rise", 0)));
    nets.add(net("ed_fall_o", e(fall, 0), e("fall", 0)));
    return new CircuitSpec("edge_detector", components, ports, nets, hints);
  }

  /**
   * One-shot pulse generator (单脉冲/单稳态): a TRIG rising edge starts a
   * window counter; PULSE stays high until the counter reaches N (N clock
   * cycles wide). Retriggerable: a fresh TRIG edge while PULSE is high
   * restarts the window. D-FF end0=D, end1=CLK, end2=Q, end4=reset; XOR/AND
   * end0=out/end1..=in; NOT end0=out/end1=in.
   */
  static CircuitSpec buildOneShot(int cycles) {
    if (cycles < 1 || cycles > 16) {
      throw new IllegalArgumentException("One-shot width must be 1..16, got " + cycles);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    int seq = 0;
    addClockInput(ports, "clk", "CLK", "os_s");
    addInput(ports, "trig", "TRIG", "os_s");
    addInput(ports, "rst", "RST", "os_s");
    addInput(ports, "gnd", "GND", "os_s");
    addOutput(ports, "pulse", "PULSE", "os_run");

    // Sample TRIG into a D-FF; rising edge = TRIG & !Qprev.
    String s = "os_s";
    components.add(new ComponentSpec(s, "Memory", "D Flip-Flop", 1, "U1"));
    hints.add(new LayoutHint(s, 0, 0, 0, null));
    String nq = "os_nq";
    components.add(new ComponentSpec(nq, "Gates", "NOT Gate", 1, "N1"));
    hints.add(new LayoutHint(nq, 1, 0, 0, null));
    String rise = "os_rise";
    components.add(new ComponentSpec(rise, "Gates", "AND Gate", 1, "A1"));
    hints.add(new LayoutHint(rise, 1, 0, 1, null));
    nets.add(net("os_sd", e("trig", 0), e(s, 0)));
    nets.add(net("os_sclk", e("clk", 0), e(s, 1)));
    nets.add(net("os_srst", e("rst", 0), e(s, 4)));
    nets.add(net("os_nqi", e(s, 2), e(nq, 1)));
    nets.add(net("os_risea", e("trig", 0), e(rise, 1)));
    nets.add(net("os_riseb", e(nq, 0), e(rise, 2)));
    seq++;

    // Window counter: binary counter CNT 0..cycles-1 gated by RUN.
    // RUN D-FF: D = RISE | (RUN & !DONE); DONE = (CNT == cycles-1).
    String run = "os_run";
    components.add(new ComponentSpec(run, "Memory", "D Flip-Flop", 1, "U2"));
    hints.add(new LayoutHint(run, 0, 0, 1, null));
    // DONE = AND over counter bits that are 1 at cycles-1.
    int bits = 0;
    int v = cycles - 1;
    while (v > 0) {
      bits++;
      v >>= 1;
    }
    String[] cnt = new String[bits];
    for (int i = 0; i < bits; i++) {
      String ff = "os_c" + i;
      components.add(new ComponentSpec(ff, "Memory", "D Flip-Flop", 1, "UC" + (i + 1)));
      hints.add(new LayoutHint(ff, 0, 0, 2 + i, null));
      cnt[i] = ff;
      String x = "os_x" + seq;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "X" + (seq + 1)));
      hints.add(new LayoutHint(x, 2, 0, i, null));
      String en = "os_en" + seq;
      components.add(new ComponentSpec(en, "Gates", "AND Gate", 1, "EA" + (seq + 1)));
      hints.add(new LayoutHint(en, 2, 0, i + bits, null));
      // cnt_i' = RUN & (cnt_i XOR (AND of lower cnt bits))
      if (i == 0) {
        // toggle every cycle while RUN: xor with constant 1 via NOT(gnd)
        String one = "os_one" + seq;
        components.add(new ComponentSpec(one, "Gates", "NOT Gate", 1, "NO" + (seq + 1)));
        hints.add(new LayoutHint(one, 1, 0, i + 2 * bits + 1, null));
        nets.add(net("os_one_in" + seq, e("gnd", 0), e(one, 1)));
        nets.add(net("os_xa" + seq, e(ff, 2), e(x, 1)));
        nets.add(net("os_xb" + seq, e(one, 0), e(x, 2)));
      } else if (i == 1) {
        // lower product = cnt[0] (no AND gate needed for the first stage)
        nets.add(net("os_xa" + seq, e(ff, 2), e(x, 1)));
        nets.add(net("os_xb" + seq, e(cnt[0], 2), e(x, 2)));
      } else {
        String andLower = "os_l" + seq;
        components.add(new ComponentSpec(andLower, "Gates", "AND Gate", 1, "LA" + (seq + 1)));
        hints.add(new LayoutHint(andLower, 2, 0, i + 2 * bits, null));
        nets.add(net("os_la" + seq, e(cnt[i - 1], 2), e(andLower, 1)));
        nets.add(net("os_lb" + seq, e(cnt[i - 2], 2), e(andLower, 2)));
        nets.add(net("os_xa" + seq, e(ff, 2), e(x, 1)));
        nets.add(net("os_xb" + seq, e(andLower, 0), e(x, 2)));
      }
      nets.add(net("os_xo" + seq, e(x, 0), e(en, 1)));
      nets.add(net("os_ena" + seq, e(run, 2), e(en, 2)));
      nets.add(net("os_enb" + seq, e(en, 0), e(ff, 0)));
      nets.add(net("os_clk" + seq, e("clk", 0), e(ff, 1)));
      nets.add(net("os_rst" + seq, e("rst", 0), e(ff, 4)));
      seq++;
    }
    // DONE: AND of the counter bits equal to (cycles-1) — implement as a chain
    // over the required-bit mask. For simplicity require a full count to all-1
    // by adding an AND over all bits when cycles is a power of two minus one;
    // otherwise compare via a mask built from NOTs of zero-bits.
    String done = buildDoneMask(components, nets, hints, cnt, bits, cycles - 1, seq);
    seq++;
    // RUN' = RISE | (RUN & !DONE)
    String nd = "os_nd";
    components.add(new ComponentSpec(nd, "Gates", "NOT Gate", 1, "ND"));
    hints.add(new LayoutHint(nd, 3, 0, 0, null));
    String keep = "os_keep";
    components.add(new ComponentSpec(keep, "Gates", "AND Gate", 1, "KA"));
    hints.add(new LayoutHint(keep, 3, 0, 1, null));
    String rset = "os_rset";
    components.add(new ComponentSpec(rset, "Gates", "OR Gate", 1, "OR1"));
    hints.add(new LayoutHint(rset, 3, 0, 2, null));
    nets.add(net("os_ndi", e(done, 0), e(nd, 1)));
    nets.add(net("os_keepa", e(run, 2), e(keep, 1)));
    nets.add(net("os_keepb", e(nd, 0), e(keep, 2)));
    nets.add(net("os_rseta", e(rise, 0), e(rset, 1)));
    nets.add(net("os_rsetb", e(keep, 0), e(rset, 2)));
    nets.add(net("os_rund", e(rset, 0), e(run, 0)));
    nets.add(net("os_runclk", e("clk", 0), e(run, 1)));
    nets.add(net("os_runrst", e("rst", 0), e(run, 4)));
    nets.add(net("os_pulse", e(run, 2), e("pulse", 0)));
    return new CircuitSpec("oneshot_" + cycles + "clk", components, ports, nets, hints);
  }

  /** Build an AND-chain that is 1 when the counter equals mask (used as DONE). */
  private static String buildDoneMask(
      List<ComponentSpec> components, List<NetSpec> nets, List<LayoutHint> hints,
      String[] cnt, int bits, int mask, int seq) {
    if (bits == 0) {
      return "gnd";
    }
    List<String> terms = new ArrayList<>();
    for (int i = 0; i < bits; i++) {
      boolean want = ((mask >> i) & 1) == 1;
      if (want) {
        terms.add(cnt[i]);
      } else {
        String n = "os_dn" + seq + "_" + i;
        components.add(new ComponentSpec(n, "Gates", "NOT Gate", 1, "DN" + (seq + 1) + "_" + i));
        hints.add(new LayoutHint(n, 3, 0, 3 + i, null));
        nets.add(net("os_dn_in" + seq + "_" + i, e(cnt[i], 2), e(n, 1)));
        terms.add(n);
      }
    }
    if (terms.size() == 1) {
      return terms.get(0);
    }
    String acc = terms.get(0);
    for (int i = 1; i < terms.size(); i++) {
      String a = "os_dm" + seq + "_" + i;
      components.add(new ComponentSpec(a, "Gates", "AND Gate", 1, "DM" + (seq + 1) + "_" + i));
      hints.add(new LayoutHint(a, 3, 0, 4 + i, null));
      nets.add(net("os_dm_a" + seq + "_" + i, e(acc, 2), e(a, 1)));
      nets.add(net("os_dm_b" + seq + "_" + i, e(terms.get(i), 2), e(a, 2)));
      acc = a;
    }
    return acc;
  }

  /**
   * Debouncer (消抖): a 3-stage shift register samples IN each clock; a
   * majority-of-3 vote passes IN only when it has been stable for 2+
   * consecutive samples, rejecting single-cycle glitches. Port maps: D-FF
   * end0=D, end1=CLK, end2=Q, end4=reset; AND/OR end0=out/end1..=in.
   */
  static CircuitSpec buildDebouncer() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "db0");
    addInput(ports, "in", "IN", "db0");
    addInput(ports, "rst", "RST", "db0");
    addOutput(ports, "out", "OUT", "db_out");
    for (int i = 0; i < 3; i++) {
      components.add(new ComponentSpec("db" + i, "Memory", "D Flip-Flop", 1, "U" + (i + 1)));
      hints.add(new LayoutHint("db" + i, i, 0, 0, null));
      nets.add(net("db_clk" + i, e("clk", 0), e("db" + i, 1)));
      nets.add(net("db_rst" + i, e("rst", 0), e("db" + i, 4)));
    }
    nets.add(net("db_d0", e("in", 0), e("db0", 0)));
    nets.add(net("db_d1", e("db0", 2), e("db1", 0)));
    nets.add(net("db_d2", e("db1", 2), e("db2", 0)));
    // majority(d0,d1,d2) = (d0&d1)|(d0&d2)|(d1&d2)
    String ab = "db_ab";
    components.add(new ComponentSpec(ab, "Gates", "AND Gate", 1, "A1"));
    String ac = "db_ac";
    components.add(new ComponentSpec(ac, "Gates", "AND Gate", 1, "A2"));
    String bc = "db_bc";
    components.add(new ComponentSpec(bc, "Gates", "AND Gate", 1, "A3"));
    String abac = "db_abac";
    components.add(new ComponentSpec(abac, "Gates", "OR Gate", 1, "O1"));
    String out = "db_out";
    components.add(new ComponentSpec(out, "Gates", "OR Gate", 1, "O2"));
    hints.add(new LayoutHint(ab, 2, 0, 0, null));
    hints.add(new LayoutHint(ac, 2, 0, 1, null));
    hints.add(new LayoutHint(bc, 2, 0, 2, null));
    hints.add(new LayoutHint(abac, 3, 0, 0, null));
    hints.add(new LayoutHint(out, 3, 0, 1, null));
    nets.add(net("db_ab_a", e("db0", 2), e(ab, 1)));
    nets.add(net("db_ab_b", e("db1", 2), e(ab, 2)));
    nets.add(net("db_ac_a", e("db0", 2), e(ac, 1)));
    nets.add(net("db_ac_b", e("db2", 2), e(ac, 2)));
    nets.add(net("db_bc_a", e("db1", 2), e(bc, 1)));
    nets.add(net("db_bc_b", e("db2", 2), e(bc, 2)));
    nets.add(net("db_o1a", e(ab, 0), e(abac, 1)));
    nets.add(net("db_o1b", e(ac, 0), e(abac, 2)));
    nets.add(net("db_o2a", e(abac, 0), e(out, 1)));
    nets.add(net("db_o2b", e(bc, 0), e(out, 2)));
    nets.add(net("db_out", e(out, 0), e("out", 0)));
    return new CircuitSpec("debouncer", components, ports, nets, hints);
  }

  /**
   * N-bit serial-in shift register: N D flip-flops chained Q[i-1] -> D[i];
   * serial input DIN feeds D[0]. Shared CLK and RST. Port map per flip-flop:
   * end0=D, end1=CLK, end2=Q, end3=nQ, end4=reset.
   */
  static CircuitSpec buildShiftRegister(int bits) {
    if (bits < 1 || bits > 32) {
      throw new IllegalArgumentException("Shift register width must be 1..32, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "sreg0");
    addInput(ports, "din", "DIN", "sreg0");
    addInput(ports, "rst", "RST", "sreg0");
    for (int i = 0; i < bits; i++) {
      String g = "sreg" + i;
      components.add(new ComponentSpec(g, "Memory", "D Flip-Flop", 1, "U" + (i + 1)));
      hints.add(new LayoutHint(g, i, 0, 0, null));
      addOutput(ports, "q" + i, "Q" + i, g);
      if (i == 0) {
        nets.add(net("sr_d" + i, e("din", 0), e(g, 0)));
      } else {
        // chain: previous flip-flop's Q output (end2) drives this D input (end0)
        nets.add(net("sr_d" + i, e("sreg" + (i - 1), 2), e(g, 0)));
      }
      nets.add(net("sr_clk" + i, e("clk", 0), e(g, 1)));
      nets.add(net("sr_rst" + i, e("rst", 0), e(g, 4)));
      nets.add(net("sr_q" + i, e(g, 2), e("q" + i, 0)));
    }
    return new CircuitSpec("shiftreg_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-bit parallel-in serial-out shift register (并入串出). LOAD=1 on the clock
   * edge latches D0..D_{n-1}; LOAD=0 shifts right each edge (SO streams the LSB
   * out first), with the top stage fed from SI. Each stage's D is a 2:1 select
   * D_i = (LOAD·D_i) ∨ (¬LOAD·next_i), next_i = Q_{i+1}, next_{n-1} = SI.
   * D-FF port map: end0=D, end1=CLK, end2=Q, end4=reset (high-active).
   */
  static CircuitSpec buildPisoShiftRegister(int bits) {
    if (bits < 1 || bits > 16) {
      throw new IllegalArgumentException("PISO width must be 1..16, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "pso_ff0");
    addInput(ports, "load", "LOAD", "pso_nl");
    addInput(ports, "rst", "RST", "pso_ff0");
    addInput(ports, "si", "SI", "pso_a2_" + (bits - 1));
    for (int i = 0; i < bits; i++) {
      addInput(ports, "d" + i, "D" + i, "pso_a1_" + i);
    }
    addOutput(ports, "so", "SO", "pso_ff0");
    for (int i = 0; i < bits; i++) {
      addOutput(ports, "q" + i, "Q" + i, "pso_ff" + i);
    }
    String nl = "pso_nl";
    components.add(new ComponentSpec(nl, "Gates", "NOT Gate", 1, "PISOL"));
    hints.add(new LayoutHint(nl, 0, 0, 0, null));
    nets.add(net("pso_nl_in", e("load", 0), e(nl, 1)));
    for (int i = 0; i < bits; i++) {
      String a1 = "pso_a1_" + i;
      String a2 = "pso_a2_" + i;
      String o = "pso_o_" + i;
      String ff = "pso_ff" + i;
      components.add(new ComponentSpec(a1, "Gates", "AND Gate", 1, "PISOA" + i));
      components.add(new ComponentSpec(a2, "Gates", "AND Gate", 1, "PISOB" + i));
      components.add(new ComponentSpec(o, "Gates", "OR Gate", 1, "PISOM" + i));
      components.add(new ComponentSpec(ff, "Memory", "D Flip-Flop", 1, "PISO" + (i + 1)));
      hints.add(new LayoutHint(a1, i + 1, 0, 2 * i, null));
      hints.add(new LayoutHint(a2, i + 1, 0, 2 * i + 1, null));
      hints.add(new LayoutHint(o, i + 1, 0, 2 * i + 2, null));
      hints.add(new LayoutHint(ff, i + 1, 0, 2 * i + 3, null));
      nets.add(net("pso_a1" + i + "x", e("load", 0), e(a1, 1)));
      nets.add(net("pso_a1" + i + "y", e("d" + i, 0), e(a1, 2)));
      nets.add(net("pso_a2" + i + "x", e(nl, 0), e(a2, 1)));
      String nextSrc = (i == bits - 1) ? "si" : "pso_ff" + (i + 1);
      nets.add(net("pso_a2" + i + "y",
          e(nextSrc, i == bits - 1 ? 0 : 2), e(a2, 2)));
      nets.add(net("pso_o" + i + "x", e(a1, 0), e(o, 1)));
      nets.add(net("pso_o" + i + "y", e(a2, 0), e(o, 2)));
      nets.add(net("pso_d" + i, e(o, 0), e(ff, 0)));
      nets.add(net("pso_clk" + i, e("clk", 0), e(ff, 1)));
      nets.add(net("pso_rst" + i, e("rst", 0), e(ff, 4)));
      nets.add(net("pso_q" + i, e(ff, 2), e("q" + i, 0)));
    }
    nets.add(net("pso_so", e("pso_ff0", 2), e("so", 0)));
    return new CircuitSpec("piso_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-bit bidirectional shift register: DIR=0 shifts right (D0=SIN,
   * D_i=Q_{i-1}), DIR=1 shifts left (D_{n-1}=SINL, D_i=Q_{i+1}). Each stage's
   * D is a 2:1 mux D_i = (¬DIR·right_src) ∨ (DIR·left_src). Shared CLK/RST.
   * Port maps: D-FF end0=D, end1=CLK, end2=Q, end4=reset; NOT end0=out/end1=in;
   * AND/OR end0=out/end1/end2=in.
   */
  static CircuitSpec buildBidirectionalShiftRegister(int bits) {
    if (bits < 2 || bits > 32) {
      throw new IllegalArgumentException("Bidirectional shift register width must be 2..32, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "bs0");
    addInput(ports, "dir", "DIR", "bs0");
    addInput(ports, "sin", "SIN", "bs0");
    addInput(ports, "sinl", "SINL", "bs" + (bits - 1));
    addInput(ports, "rst", "RST", "bs0");
    components.add(new ComponentSpec("bsn", "Gates", "NOT Gate", 1, "N1"));
    hints.add(new LayoutHint("bsn", 0, 0, 0, null));
    for (int i = 0; i < bits; i++) {
      String g = "bs" + i;
      components.add(new ComponentSpec(g, "Memory", "D Flip-Flop", 1, "U" + (i + 1)));
      hints.add(new LayoutHint(g, i + 1, 0, 0, null));
      addOutput(ports, "q" + i, "Q" + i, g);
    }
    nets.add(net("bs_n_dir", e("dir", 0), e("bsn", 1)));
    for (int i = 0; i < bits; i++) {
      String g = "bs" + i;
      String ar = "ba" + i + "_r";
      String al = "ba" + i + "_l";
      String or = "bo" + i;
      components.add(new ComponentSpec(ar, "Gates", "AND Gate", 1, "AR" + (i + 1)));
      components.add(new ComponentSpec(al, "Gates", "AND Gate", 1, "AL" + (i + 1)));
      components.add(new ComponentSpec(or, "Gates", "OR Gate", 1, "OR" + (i + 1)));
      hints.add(new LayoutHint(ar, i + 1, 0, 1, null));
      hints.add(new LayoutHint(al, i + 1, 0, 2, null));
      hints.add(new LayoutHint(or, i + 1, 0, 3, null));
      String rightSrc = i == 0 ? "sin" : "bs" + (i - 1);
      int rightEnd = i == 0 ? 0 : 2;
      String leftSrc = i == bits - 1 ? "sinl" : "bs" + (i + 1);
      int leftEnd = i == bits - 1 ? 0 : 2;
      nets.add(net("bs_ar" + i + "_nd", e("bsn", 0), e(ar, 1)));
      nets.add(net("bs_ar" + i + "_src", e(rightSrc, rightEnd), e(ar, 2)));
      nets.add(net("bs_al" + i + "_d", e("dir", 0), e(al, 1)));
      nets.add(net("bs_al" + i + "_src", e(leftSrc, leftEnd), e(al, 2)));
      nets.add(net("bs_bo" + i + "_r", e(ar, 0), e(or, 1)));
      nets.add(net("bs_bo" + i + "_l", e(al, 0), e(or, 2)));
      nets.add(net("bs_d" + i, e(or, 0), e(g, 0)));
      nets.add(net("bs_clk" + i, e("clk", 0), e(g, 1)));
      nets.add(net("bs_rst" + i, e("rst", 0), e(g, 4)));
      nets.add(net("bs_q" + i, e(g, 2), e("q" + i, 0)));
    }
    return new CircuitSpec("bidirsreg_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * Synchronous BCD (mod-10) counter from T flip-flops. Equations (verified by
   * hand, no async-clear transient): T0=EN, T1=Q0·¬Q3, T2=Q0·Q1·¬Q3,
   * T3=Q0·Q1·Q2 + Q0·Q3 → counts 0..9 then wraps 9→0. Shared CLK/RST.
   * Port maps: T-FF end0=T, end1=CLK, end2=Q, end4=reset; NOT end0=out/end1=in;
   * AND/OR end0=out/end1/end2=in.
   */
  static CircuitSpec buildBcdCounter() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "bc0");
    addInput(ports, "en", "EN", "bc0");
    addInput(ports, "rst", "RST", "bc0");
    for (int i = 0; i < 4; i++) {
      addOutput(ports, "q" + i, "Q" + i, "bc" + i);
    }
    components.add(new ComponentSpec("bc0", "Memory", "T Flip-Flop", 1, "U1"));
    components.add(new ComponentSpec("bc1", "Memory", "T Flip-Flop", 1, "U2"));
    components.add(new ComponentSpec("bc2", "Memory", "T Flip-Flop", 1, "U3"));
    components.add(new ComponentSpec("bc3", "Memory", "T Flip-Flop", 1, "U4"));
    components.add(new ComponentSpec("bc_n3", "Gates", "NOT Gate", 1, "N1"));
    components.add(new ComponentSpec("bc_t1", "Gates", "AND Gate", 1, "A1"));
    components.add(new ComponentSpec("bc_a01", "Gates", "AND Gate", 1, "A2"));
    components.add(new ComponentSpec("bc_t2", "Gates", "AND Gate", 1, "A3"));
    components.add(new ComponentSpec("bc_t3b", "Gates", "AND Gate", 1, "A4"));
    components.add(new ComponentSpec("bc_t3c", "Gates", "AND Gate", 1, "A5"));
    components.add(new ComponentSpec("bc_t3", "Gates", "OR Gate", 1, "O1"));
    for (int i = 0; i < 4; i++) {
      hints.add(new LayoutHint("bc" + i, i, 0, 0, null));
    }
    hints.add(new LayoutHint("bc_n3", 0, 0, 1, null));
    hints.add(new LayoutHint("bc_t1", 0, 0, 2, null));
    hints.add(new LayoutHint("bc_a01", 1, 0, 1, null));
    hints.add(new LayoutHint("bc_t2", 1, 0, 2, null));
    hints.add(new LayoutHint("bc_t3b", 2, 0, 1, null));
    hints.add(new LayoutHint("bc_t3c", 2, 0, 2, null));
    hints.add(new LayoutHint("bc_t3", 3, 0, 1, null));
    // T0 = EN
    nets.add(net("bc_t0", e("en", 0), e("bc0", 0)));
    // ¬Q3
    nets.add(net("bc_n3_in", e("bc3", 2), e("bc_n3", 1)));
    // T1 = Q0·¬Q3
    nets.add(net("bc_t1_q0", e("bc0", 2), e("bc_t1", 1)));
    nets.add(net("bc_t1_n3", e("bc_n3", 0), e("bc_t1", 2)));
    nets.add(net("bc_t1_out", e("bc_t1", 0), e("bc1", 0)));
    // a01 = Q0·Q1
    nets.add(net("bc_a01_q0", e("bc0", 2), e("bc_a01", 1)));
    nets.add(net("bc_a01_q1", e("bc1", 2), e("bc_a01", 2)));
    // T2 = a01·¬Q3
    nets.add(net("bc_t2_a01", e("bc_a01", 0), e("bc_t2", 1)));
    nets.add(net("bc_t2_n3", e("bc_n3", 0), e("bc_t2", 2)));
    nets.add(net("bc_t2_out", e("bc_t2", 0), e("bc2", 0)));
    // T3 = (a01·Q2) + (Q0·Q3)
    nets.add(net("bc_t3b_a01", e("bc_a01", 0), e("bc_t3b", 1)));
    nets.add(net("bc_t3b_q2", e("bc2", 2), e("bc_t3b", 2)));
    nets.add(net("bc_t3c_q0", e("bc0", 2), e("bc_t3c", 1)));
    nets.add(net("bc_t3c_q3", e("bc3", 2), e("bc_t3c", 2)));
    nets.add(net("bc_t3_b", e("bc_t3b", 0), e("bc_t3", 1)));
    nets.add(net("bc_t3_c", e("bc_t3c", 0), e("bc_t3", 2)));
    nets.add(net("bc_t3_out", e("bc_t3", 0), e("bc3", 0)));
    // shared CLK and RST
    for (int i = 0; i < 4; i++) {
      nets.add(net("bc_clk" + i, e("clk", 0), e("bc" + i, 1)));
      nets.add(net("bc_rst" + i, e("rst", 0), e("bc" + i, 4)));
      nets.add(net("bc_q" + i, e("bc" + i, 2), e("q" + i, 0)));
    }
    return new CircuitSpec("bcd_counter", components, ports, nets, hints);
  }

  /**
   * N-bit synchronous up/down counter: UD=0 counts up, UD=1 counts down.
   * T0=EN; T1=Q0⊕UD; for i≥2, T_i = ¬UD·AND(Q0..Q_{i-1}) + UD·AND(¬Q0..¬Q_{i-1})
   * (a stage toggles when all lower bits match the direction's active level).
   * Shared CLK/RST. Port maps: T-FF end0=T, end1=CLK, end2=Q, end4=reset;
   * NOT end0=out/end1=in; XOR/AND/OR end0=out/end1/end2=in.
   */
  static CircuitSpec buildUpDownCounter(int bits) {
    if (bits < 2 || bits > 16) {
      throw new IllegalArgumentException("Up/down counter width must be 2..16, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "ud0");
    addInput(ports, "ud", "UD", "ud0");
    addInput(ports, "en", "EN", "ud0");
    addInput(ports, "rst", "RST", "ud0");
    for (int i = 0; i < bits; i++) {
      addOutput(ports, "q" + i, "Q" + i, "ud" + i);
    }
    components.add(new ComponentSpec("ud_nd", "Gates", "NOT Gate", 1, "N1"));
    hints.add(new LayoutHint("ud_nd", 0, 0, 0, null));
    nets.add(net("ud_nd_in", e("ud", 0), e("ud_nd", 1)));
    for (int i = 0; i < bits; i++) {
      components.add(new ComponentSpec("ud" + i, "Memory", "T Flip-Flop", 1, "U" + (i + 1)));
      hints.add(new LayoutHint("ud" + i, i, 0, 0, null));
      if (i < bits - 1) {
        String nq = "ud_nq" + i;
        components.add(new ComponentSpec(nq, "Gates", "NOT Gate", 1, "N" + (i + 2)));
        hints.add(new LayoutHint(nq, i, 0, 6, null));
        nets.add(net("ud_nq" + i + "_in", e("ud" + i, 2), e(nq, 1)));
      }
    }
    // T0 = EN
    nets.add(net("ud_t0", e("en", 0), e("ud0", 0)));
    // T1 = Q0 XOR UD
    components.add(new ComponentSpec("ud_t1", "Gates", "XOR Gate", 1, "X1"));
    hints.add(new LayoutHint("ud_t1", 1, 0, 1, null));
    nets.add(net("ud_t1_q0", e("ud0", 2), e("ud_t1", 1)));
    nets.add(net("ud_t1_ud", e("ud", 0), e("ud_t1", 2)));
    nets.add(net("ud_t1_out", e("ud_t1", 0), e("ud1", 0)));
    String upSrc = "ud0";
    int upEnd = 2;
    String dnSrc = "ud_nq0";
    int dnEnd = 0;
    for (int i = 2; i < bits; i++) {
      String aUp = "ud_a" + i + "u";
      String aDn = "ud_a" + i + "d";
      components.add(new ComponentSpec(aUp, "Gates", "AND Gate", 1, "AU" + i));
      components.add(new ComponentSpec(aDn, "Gates", "AND Gate", 1, "AD" + i));
      hints.add(new LayoutHint(aUp, i, 0, 1, null));
      hints.add(new LayoutHint(aDn, i, 0, 2, null));
      nets.add(net("ud_au" + i + "_a", e(upSrc, upEnd), e(aUp, 1)));
      nets.add(net("ud_au" + i + "_b", e("ud" + (i - 1), 2), e(aUp, 2)));
      nets.add(net("ud_ad" + i + "_a", e(dnSrc, dnEnd), e(aDn, 1)));
      nets.add(net("ud_ad" + i + "_b", e("ud_nq" + (i - 1), 0), e(aDn, 2)));
      upSrc = aUp;
      upEnd = 0;
      dnSrc = aDn;
      dnEnd = 0;
      String tU = "ud_t" + i + "u";
      String tD = "ud_t" + i + "d";
      String or = "ud_t" + i;
      components.add(new ComponentSpec(tU, "Gates", "AND Gate", 1, "TU" + i));
      components.add(new ComponentSpec(tD, "Gates", "AND Gate", 1, "TD" + i));
      components.add(new ComponentSpec(or, "Gates", "OR Gate", 1, "TO" + i));
      hints.add(new LayoutHint(tU, i, 0, 3, null));
      hints.add(new LayoutHint(tD, i, 0, 4, null));
      hints.add(new LayoutHint(or, i, 0, 5, null));
      nets.add(net("ud_tu" + i + "_nd", e("ud_nd", 0), e(tU, 1)));
      nets.add(net("ud_tu" + i + "_acc", e(upSrc, upEnd), e(tU, 2)));
      nets.add(net("ud_td" + i + "_ud", e("ud", 0), e(tD, 1)));
      nets.add(net("ud_td" + i + "_acc", e(dnSrc, dnEnd), e(tD, 2)));
      nets.add(net("ud_t" + i + "_u", e(tU, 0), e(or, 1)));
      nets.add(net("ud_t" + i + "_d", e(tD, 0), e(or, 2)));
      nets.add(net("ud_t" + i + "_out", e(or, 0), e("ud" + i, 0)));
    }
    for (int i = 0; i < bits; i++) {
      nets.add(net("ud_clk" + i, e("clk", 0), e("ud" + i, 1)));
      nets.add(net("ud_rst" + i, e("rst", 0), e("ud" + i, 4)));
      nets.add(net("ud_q" + i, e("ud" + i, 2), e("q" + i, 0)));
    }
    return new CircuitSpec("updown_counter_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * Generic N-bit... mod-N counter (模N计数器, N in 2..16): counts 0..N-1 then
   * wraps to 0. Synchronous T-FF: atN is the minterm detecting state N-1;
   * T_i = (normal_i·¬atN) ∨ (atN·bit_i(N-1)) where normal_i = AND(Q0..Q_{i-1})
   * (normal_0 = 1). Constant-1 (T_0 when bit0 of N-1 is 1) is synthesized as
   * ¬atN ∨ atN since the IR has no internal constant. Shared CLK/RST.
   * Port maps: T-FF end0=T, end1=CLK, end2=Q, end4=reset; NOT end0=out/end1=in;
   * AND/OR end0=out/end1/end2=in.
   */
  static CircuitSpec buildModCounter(int n) {
    if (n < 2 || n > 16) {
      throw new IllegalArgumentException("Mod-N counter N must be 2..16, got " + n);
    }
    int nMinus1 = n - 1;
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "mc0");
    addInput(ports, "rst", "RST", "mc0");
    for (int i = 0; i < 4; i++) {
      addOutput(ports, "q" + i, "Q" + i, "mc" + i);
      components.add(new ComponentSpec("mc" + i, "Memory", "T Flip-Flop", 1, "U" + (i + 1)));
      hints.add(new LayoutHint("mc" + i, i, 0, 0, null));
    }
    // atN = minterm detecting N-1 over Q3..Q0
    String atN = null;
    int atNEnd = 0;
    for (int j = 0; j < 4; j++) {
      boolean one = ((nMinus1 >> j) & 1) == 1;
      String lit;
      int litEnd;
      if (one) {
        lit = "mc" + j; // T-FF, Q output is end2
        litEnd = 2;
      } else {
        lit = "mcn" + j; // NOT gate output is end0
        litEnd = 0;
        components.add(new ComponentSpec(lit, "Gates", "NOT Gate", 1, "N" + (j + 1)));
        hints.add(new LayoutHint(lit, 4, 0, j, null));
        nets.add(net("mcn" + j + "_in", e("mc" + j, 2), e(lit, 1)));
      }
      if (atN == null) {
        atN = lit;
        atNEnd = litEnd;
      } else {
        String gate = "mc_at" + j;
        components.add(new ComponentSpec(gate, "Gates", "AND Gate", 1, "AT" + (j + 1)));
        hints.add(new LayoutHint(gate, 4, 0, 4 + j, null));
        nets.add(net("mc_at" + j + "_a", e(atN, atNEnd), e(gate, 1)));
        nets.add(net("mc_at" + j + "_b", e(lit, litEnd), e(gate, 2)));
        atN = gate;
        atNEnd = 0;
      }
    }
    String notAt = "mc_notat";
    components.add(new ComponentSpec(notAt, "Gates", "NOT Gate", 1, "NA"));
    hints.add(new LayoutHint(notAt, 4, 0, 8, null));
    nets.add(net("mc_notat_in", e(atN, 0), e(notAt, 1)));
    String normSrc = "mc0";
    int normEnd = 2;
    for (int i = 0; i < 4; i++) {
      boolean bitOne = ((nMinus1 >> i) & 1) == 1;
      if (i == 0) {
        if (bitOne) {
          String or = "mc_t0";
          components.add(new ComponentSpec(or, "Gates", "OR Gate", 1, "T0"));
          hints.add(new LayoutHint(or, 0, 0, 1, null));
          nets.add(net("mc_t0_a", e(notAt, 0), e(or, 1)));
          nets.add(net("mc_t0_b", e(atN, 0), e(or, 2)));
          nets.add(net("mc_t0_out", e(or, 0), e("mc0", 0)));
        } else {
          nets.add(net("mc_t0", e(notAt, 0), e("mc0", 0)));
        }
      } else {
        if (i >= 2) {
          String ng = "mc_norm" + i;
          components.add(new ComponentSpec(ng, "Gates", "AND Gate", 1, "NR" + i));
          hints.add(new LayoutHint(ng, i, 0, 1, null));
          nets.add(net("mc_norm" + i + "_a", e(normSrc, normEnd), e(ng, 1)));
          nets.add(net("mc_norm" + i + "_b", e("mc" + (i - 1), 2), e(ng, 2)));
          normSrc = ng;
          normEnd = 0;
        }
        String main = "mc_t" + i + "m";
        components.add(new ComponentSpec(main, "Gates", "AND Gate", 1, "TM" + i));
        hints.add(new LayoutHint(main, i, 0, 2, null));
        nets.add(net("mc_t" + i + "m_n", e(normSrc, normEnd), e(main, 1)));
        nets.add(net("mc_t" + i + "m_na", e(notAt, 0), e(main, 2)));
        if (bitOne) {
          String or = "mc_t" + i;
          components.add(new ComponentSpec(or, "Gates", "OR Gate", 1, "TO" + i));
          hints.add(new LayoutHint(or, i, 0, 3, null));
          nets.add(net("mc_t" + i + "_m", e(main, 0), e(or, 1)));
          nets.add(net("mc_t" + i + "_at", e(atN, 0), e(or, 2)));
          nets.add(net("mc_t" + i + "_out", e(or, 0), e("mc" + i, 0)));
        } else {
          nets.add(net("mc_t" + i, e(main, 0), e("mc" + i, 0)));
        }
      }
    }
    for (int i = 0; i < 4; i++) {
      nets.add(net("mc_clk" + i, e("clk", 0), e("mc" + i, 1)));
      nets.add(net("mc_rst" + i, e("rst", 0), e("mc" + i, 4)));
      nets.add(net("mc_q" + i, e("mc" + i, 2), e("q" + i, 0)));
    }
    return new CircuitSpec("mod" + n + "_counter", components, ports, nets, hints);
  }

  /**
   * 2-digit BCD → binary converter: B = 10·T + U where T (tens) and U (units)
   * are BCD digits. Two ripple adders: S = 8·T + 2·T, then B = S + U.
   * 8·T = {t3t2t1t0,000}, 2·T = {t3t2t1t0,0} so S1=t0, S2=t1, S3=t0+t2,
   * S4=t1+t3+cA, S5=t2+cB, S6=t3+cC. Then B0=U0, B1=t0+U1, B2=t1+U2+c1,
   * B3=S3+U3+c2, B4=S4+c3, B5=S5+c4, B6=S6+c5 (~23 gates). Verified T=5,U=9→59.
   * Combinational. 2-input gates end0=out, end1/end2=in.
   */
  static CircuitSpec buildBcdToBinary() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addInput(ports, "t0", "T0", "b2b_s3x");
    addInput(ports, "t1", "T1", "b2b_s4a");
    addInput(ports, "t2", "T2", "b2b_s3x");
    addInput(ports, "t3", "T3", "b2b_s4a");
    addInput(ports, "u0", "U0", "b2b_b1x");
    addInput(ports, "u1", "U1", "b2b_b1x");
    addInput(ports, "u2", "U2", "b2b_b2a");
    addInput(ports, "u3", "U3", "b2b_b3a");
    addOutput(ports, "b0", "B0", "b2b_b1x");
    addOutput(ports, "b1", "B1", "b2b_b1x");
    addOutput(ports, "b2", "B2", "b2b_b2b");
    addOutput(ports, "b3", "B3", "b2b_b3b");
    addOutput(ports, "b4", "B4", "b2b_b4x");
    addOutput(ports, "b5", "B5", "b2b_b5x");
    addOutput(ports, "b6", "B6", "b2b_b6x");
    // Add1: S = 8T + 2T. S1=t0, S2=t1 (wires). S3=HA(t0,t2), S4=FA(t1,t3,cA),
    // S5=HA(t2,cB), S6=HA(t3,cC).
    // S3 = t0⊕t2, cA = t0·t2
    components.add(new ComponentSpec("b2b_s3x", "Gates", "XOR Gate", 1, "S3X"));
    components.add(new ComponentSpec("b2b_s3a", "Gates", "AND Gate", 1, "S3A"));
    hints.add(new LayoutHint("b2b_s3x", 0, 0, 0, null));
    hints.add(new LayoutHint("b2b_s3a", 0, 0, 1, null));
    nets.add(net("b2b_s3x_t0", e("t0", 0), e("b2b_s3x", 1)));
    nets.add(net("b2b_s3x_t2", e("t2", 0), e("b2b_s3x", 2)));
    nets.add(net("b2b_s3a_t0", e("t0", 0), e("b2b_s3a", 1)));
    nets.add(net("b2b_s3a_t2", e("t2", 0), e("b2b_s3a", 2)));
    // S4 = t1⊕t3⊕cA, cB = majority(t1,t3,cA)
    components.add(new ComponentSpec("b2b_s4a", "Gates", "XOR Gate", 1, "S4A"));
    components.add(new ComponentSpec("b2b_s4b", "Gates", "XOR Gate", 1, "S4B"));
    components.add(new ComponentSpec("b2b_s4c", "Gates", "AND Gate", 1, "S4C"));
    components.add(new ComponentSpec("b2b_s4d", "Gates", "AND Gate", 1, "S4D"));
    components.add(new ComponentSpec("b2b_s4o", "Gates", "OR Gate", 1, "S4O"));
    hints.add(new LayoutHint("b2b_s4a", 1, 0, 0, null));
    hints.add(new LayoutHint("b2b_s4b", 1, 0, 1, null));
    hints.add(new LayoutHint("b2b_s4c", 1, 0, 2, null));
    hints.add(new LayoutHint("b2b_s4d", 1, 0, 3, null));
    hints.add(new LayoutHint("b2b_s4o", 1, 0, 4, null));
    nets.add(net("b2b_s4a_t1", e("t1", 0), e("b2b_s4a", 1)));
    nets.add(net("b2b_s4a_t3", e("t3", 0), e("b2b_s4a", 2)));
    nets.add(net("b2b_s4b_x", e("b2b_s4a", 0), e("b2b_s4b", 1)));
    nets.add(net("b2b_s4b_c", e("b2b_s3a", 0), e("b2b_s4b", 2)));
    nets.add(net("b2b_s4c_t1", e("t1", 0), e("b2b_s4c", 1)));
    nets.add(net("b2b_s4c_t3", e("t3", 0), e("b2b_s4c", 2)));
    nets.add(net("b2b_s4d_c", e("b2b_s3a", 0), e("b2b_s4d", 1)));
    nets.add(net("b2b_s4d_x", e("b2b_s4a", 0), e("b2b_s4d", 2)));
    nets.add(net("b2b_s4o_a", e("b2b_s4c", 0), e("b2b_s4o", 1)));
    nets.add(net("b2b_s4o_b", e("b2b_s4d", 0), e("b2b_s4o", 2)));
    // S5 = t2⊕cB, cC = t2·cB
    components.add(new ComponentSpec("b2b_s5x", "Gates", "XOR Gate", 1, "S5X"));
    components.add(new ComponentSpec("b2b_s5a", "Gates", "AND Gate", 1, "S5A"));
    hints.add(new LayoutHint("b2b_s5x", 2, 0, 0, null));
    hints.add(new LayoutHint("b2b_s5a", 2, 0, 1, null));
    nets.add(net("b2b_s5x_t2", e("t2", 0), e("b2b_s5x", 1)));
    nets.add(net("b2b_s5x_c", e("b2b_s4o", 0), e("b2b_s5x", 2)));
    nets.add(net("b2b_s5a_t2", e("t2", 0), e("b2b_s5a", 1)));
    nets.add(net("b2b_s5a_c", e("b2b_s4o", 0), e("b2b_s5a", 2)));
    // S6 = t3⊕cC
    components.add(new ComponentSpec("b2b_s6x", "Gates", "XOR Gate", 1, "S6X"));
    hints.add(new LayoutHint("b2b_s6x", 3, 0, 0, null));
    nets.add(net("b2b_s6x_t3", e("t3", 0), e("b2b_s6x", 1)));
    nets.add(net("b2b_s6x_c", e("b2b_s5a", 0), e("b2b_s6x", 2)));
    // Add2: B = S + U.
    // B0 = U0
    nets.add(net("b2b_b0", e("u0", 0), e("b0", 0)));
    // B1 = t0⊕U1, c1 = t0·U1
    components.add(new ComponentSpec("b2b_b1x", "Gates", "XOR Gate", 1, "B1X"));
    components.add(new ComponentSpec("b2b_b1a", "Gates", "AND Gate", 1, "B1A"));
    hints.add(new LayoutHint("b2b_b1x", 4, 0, 0, null));
    hints.add(new LayoutHint("b2b_b1a", 4, 0, 1, null));
    nets.add(net("b2b_b1x_t", e("t0", 0), e("b2b_b1x", 1)));
    nets.add(net("b2b_b1x_u", e("u1", 0), e("b2b_b1x", 2)));
    nets.add(net("b2b_b1a_t", e("t0", 0), e("b2b_b1a", 1)));
    nets.add(net("b2b_b1a_u", e("u1", 0), e("b2b_b1a", 2)));
    nets.add(net("b2b_b1", e("b2b_b1x", 0), e("b1", 0)));
    // B2 = t1⊕U2⊕c1, c2 = majority
    components.add(new ComponentSpec("b2b_b2a", "Gates", "XOR Gate", 1, "B2A"));
    components.add(new ComponentSpec("b2b_b2b", "Gates", "XOR Gate", 1, "B2B"));
    components.add(new ComponentSpec("b2b_b2c", "Gates", "AND Gate", 1, "B2C"));
    components.add(new ComponentSpec("b2b_b2d", "Gates", "AND Gate", 1, "B2D"));
    components.add(new ComponentSpec("b2b_b2o", "Gates", "OR Gate", 1, "B2O"));
    hints.add(new LayoutHint("b2b_b2a", 5, 0, 0, null));
    hints.add(new LayoutHint("b2b_b2b", 5, 0, 1, null));
    hints.add(new LayoutHint("b2b_b2c", 5, 0, 2, null));
    hints.add(new LayoutHint("b2b_b2d", 5, 0, 3, null));
    hints.add(new LayoutHint("b2b_b2o", 5, 0, 4, null));
    nets.add(net("b2b_b2a_t", e("t1", 0), e("b2b_b2a", 1)));
    nets.add(net("b2b_b2a_u", e("u2", 0), e("b2b_b2a", 2)));
    nets.add(net("b2b_b2b_x", e("b2b_b2a", 0), e("b2b_b2b", 1)));
    nets.add(net("b2b_b2b_c", e("b2b_b1a", 0), e("b2b_b2b", 2)));
    nets.add(net("b2b_b2c_t", e("t1", 0), e("b2b_b2c", 1)));
    nets.add(net("b2b_b2c_u", e("u2", 0), e("b2b_b2c", 2)));
    nets.add(net("b2b_b2d_c", e("b2b_b1a", 0), e("b2b_b2d", 1)));
    nets.add(net("b2b_b2d_x", e("b2b_b2a", 0), e("b2b_b2d", 2)));
    nets.add(net("b2b_b2o_a", e("b2b_b2c", 0), e("b2b_b2o", 1)));
    nets.add(net("b2b_b2o_b", e("b2b_b2d", 0), e("b2b_b2o", 2)));
    nets.add(net("b2b_b2", e("b2b_b2b", 0), e("b2", 0)));
    // B3 = S3⊕U3⊕c2, c3 = majority
    components.add(new ComponentSpec("b2b_b3a", "Gates", "XOR Gate", 1, "B3A"));
    components.add(new ComponentSpec("b2b_b3b", "Gates", "XOR Gate", 1, "B3B"));
    components.add(new ComponentSpec("b2b_b3c", "Gates", "AND Gate", 1, "B3C"));
    components.add(new ComponentSpec("b2b_b3d", "Gates", "AND Gate", 1, "B3D"));
    components.add(new ComponentSpec("b2b_b3o", "Gates", "OR Gate", 1, "B3O"));
    hints.add(new LayoutHint("b2b_b3a", 6, 0, 0, null));
    hints.add(new LayoutHint("b2b_b3b", 6, 0, 1, null));
    hints.add(new LayoutHint("b2b_b3c", 6, 0, 2, null));
    hints.add(new LayoutHint("b2b_b3d", 6, 0, 3, null));
    hints.add(new LayoutHint("b2b_b3o", 6, 0, 4, null));
    nets.add(net("b2b_b3a_s", e("b2b_s3x", 0), e("b2b_b3a", 1)));
    nets.add(net("b2b_b3a_u", e("u3", 0), e("b2b_b3a", 2)));
    nets.add(net("b2b_b3b_x", e("b2b_b3a", 0), e("b2b_b3b", 1)));
    nets.add(net("b2b_b3b_c", e("b2b_b2o", 0), e("b2b_b3b", 2)));
    nets.add(net("b2b_b3c_s", e("b2b_s3x", 0), e("b2b_b3c", 1)));
    nets.add(net("b2b_b3c_u", e("u3", 0), e("b2b_b3c", 2)));
    nets.add(net("b2b_b3d_c", e("b2b_b2o", 0), e("b2b_b3d", 1)));
    nets.add(net("b2b_b3d_x", e("b2b_b3a", 0), e("b2b_b3d", 2)));
    nets.add(net("b2b_b3o_a", e("b2b_b3c", 0), e("b2b_b3o", 1)));
    nets.add(net("b2b_b3o_b", e("b2b_b3d", 0), e("b2b_b3o", 2)));
    nets.add(net("b2b_b3", e("b2b_b3b", 0), e("b3", 0)));
    // B4 = S4⊕c3, c4 = S4·c3
    components.add(new ComponentSpec("b2b_b4x", "Gates", "XOR Gate", 1, "B4X"));
    components.add(new ComponentSpec("b2b_b4a", "Gates", "AND Gate", 1, "B4A"));
    hints.add(new LayoutHint("b2b_b4x", 7, 0, 0, null));
    hints.add(new LayoutHint("b2b_b4a", 7, 0, 1, null));
    nets.add(net("b2b_b4x_s", e("b2b_s4b", 0), e("b2b_b4x", 1)));
    nets.add(net("b2b_b4x_c", e("b2b_b3o", 0), e("b2b_b4x", 2)));
    nets.add(net("b2b_b4a_s", e("b2b_s4b", 0), e("b2b_b4a", 1)));
    nets.add(net("b2b_b4a_c", e("b2b_b3o", 0), e("b2b_b4a", 2)));
    nets.add(net("b2b_b4", e("b2b_b4x", 0), e("b4", 0)));
    // B5 = S5⊕c4, c5 = S5·c4
    components.add(new ComponentSpec("b2b_b5x", "Gates", "XOR Gate", 1, "B5X"));
    components.add(new ComponentSpec("b2b_b5a", "Gates", "AND Gate", 1, "B5A"));
    hints.add(new LayoutHint("b2b_b5x", 8, 0, 0, null));
    hints.add(new LayoutHint("b2b_b5a", 8, 0, 1, null));
    nets.add(net("b2b_b5x_s", e("b2b_s5x", 0), e("b2b_b5x", 1)));
    nets.add(net("b2b_b5x_c", e("b2b_b4a", 0), e("b2b_b5x", 2)));
    nets.add(net("b2b_b5a_s", e("b2b_s5x", 0), e("b2b_b5a", 1)));
    nets.add(net("b2b_b5a_c", e("b2b_b4a", 0), e("b2b_b5a", 2)));
    nets.add(net("b2b_b5", e("b2b_b5x", 0), e("b5", 0)));
    // B6 = S6⊕c5
    components.add(new ComponentSpec("b2b_b6x", "Gates", "XOR Gate", 1, "B6X"));
    hints.add(new LayoutHint("b2b_b6x", 9, 0, 0, null));
    nets.add(net("b2b_b6x_s", e("b2b_s6x", 0), e("b2b_b6x", 1)));
    nets.add(net("b2b_b6x_c", e("b2b_b5a", 0), e("b2b_b6x", 2)));
    nets.add(net("b2b_b6", e("b2b_b6x", 0), e("b6", 0)));
    return new CircuitSpec("bcd2bin_2digit", components, ports, nets, hints);
  }

  /**
   * 4-bit binary (0..15) → BCD: tens T1 (1 bit) and units U (4 bits).
   * T1 = b3·(b2∨b1) [B≥10]; units = B for B≤9 else B−10 (= B+6 mod 16).
   * (B+6): s0=b0, c1=b1, s1=¬b1, s2=¬b2⊕c1, c2=b2∨(c1·¬b2), s3=b3⊕c2.
   * U0=b0, U1=b1⊕T1, U2=(¬T1·b2)∨(T1·s2), U3=(¬T1·b3)∨(T1·s3). ~16 gates.
   * Verified: 13→13, 9→09, 14→14. Combinational. 2-input gates end0=out,
   * end1/end2=in; NOT end0=out, end1=in.
   */
  static CircuitSpec buildBinToBcd4() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addInput(ports, "b0", "B0", "bb_or");
    addInput(ports, "b1", "B1", "bb_or");
    addInput(ports, "b2", "B2", "bb_or");
    addInput(ports, "b3", "B3", "bb_and");
    addOutput(ports, "t", "T1", "bb_and");
    addOutput(ports, "u0", "U0", "bb_or");
    addOutput(ports, "u1", "U1", "bb_xu1");
    addOutput(ports, "u2", "U2", "bb_or2");
    addOutput(ports, "u3", "U3", "bb_or3");
    // T1 = b3·(b2∨b1)
    components.add(new ComponentSpec("bb_or", "Gates", "OR Gate", 1, "O1"));
    components.add(new ComponentSpec("bb_and", "Gates", "AND Gate", 1, "A1"));
    hints.add(new LayoutHint("bb_or", 0, 0, 0, null));
    hints.add(new LayoutHint("bb_and", 0, 0, 1, null));
    nets.add(net("bb_or_b2", e("b2", 0), e("bb_or", 1)));
    nets.add(net("bb_or_b1", e("b1", 0), e("bb_or", 2)));
    nets.add(net("bb_and_b3", e("b3", 0), e("bb_and", 1)));
    nets.add(net("bb_and_o", e("bb_or", 0), e("bb_and", 2)));
    nets.add(net("bb_t", e("bb_and", 0), e("t", 0)));
    // ¬T1
    components.add(new ComponentSpec("bb_nt", "Gates", "NOT Gate", 1, "N1"));
    hints.add(new LayoutHint("bb_nt", 1, 0, 0, null));
    nets.add(net("bb_nt_in", e("bb_and", 0), e("bb_nt", 1)));
    // U0 = B0
    nets.add(net("bb_u0", e("b0", 0), e("u0", 0)));
    // U1 = B1⊕T1
    components.add(new ComponentSpec("bb_xu1", "Gates", "XOR Gate", 1, "X1"));
    hints.add(new LayoutHint("bb_xu1", 1, 0, 1, null));
    nets.add(net("bb_xu1_b1", e("b1", 0), e("bb_xu1", 1)));
    nets.add(net("bb_xu1_t", e("bb_and", 0), e("bb_xu1", 2)));
    nets.add(net("bb_u1", e("bb_xu1", 0), e("u1", 0)));
    // (B+6): ¬B1 (s1), ¬B2, s2=¬B2⊕c1(B1), c2=B2∨(B1·¬B2), s3=B3⊕c2
    components.add(new ComponentSpec("bb_nb1", "Gates", "NOT Gate", 1, "N2"));
    components.add(new ComponentSpec("bb_nb2", "Gates", "NOT Gate", 1, "N3"));
    components.add(new ComponentSpec("bb_xs2", "Gates", "XOR Gate", 1, "X2"));
    components.add(new ComponentSpec("bb_ac2", "Gates", "AND Gate", 1, "A2"));
    components.add(new ComponentSpec("bb_oc2", "Gates", "OR Gate", 1, "O2"));
    components.add(new ComponentSpec("bb_xs3", "Gates", "XOR Gate", 1, "X3"));
    hints.add(new LayoutHint("bb_nb1", 2, 0, 0, null));
    hints.add(new LayoutHint("bb_nb2", 2, 0, 1, null));
    hints.add(new LayoutHint("bb_xs2", 2, 0, 2, null));
    hints.add(new LayoutHint("bb_ac2", 2, 0, 3, null));
    hints.add(new LayoutHint("bb_oc2", 2, 0, 4, null));
    hints.add(new LayoutHint("bb_xs3", 2, 0, 5, null));
    nets.add(net("bb_nb1_in", e("b1", 0), e("bb_nb1", 1)));
    nets.add(net("bb_nb2_in", e("b2", 0), e("bb_nb2", 1)));
    nets.add(net("bb_xs2_a", e("bb_nb2", 0), e("bb_xs2", 1)));
    nets.add(net("bb_xs2_c", e("b1", 0), e("bb_xs2", 2)));
    nets.add(net("bb_ac2_c", e("b1", 0), e("bb_ac2", 1)));
    nets.add(net("bb_ac2_n", e("bb_nb2", 0), e("bb_ac2", 2)));
    nets.add(net("bb_oc2_b2", e("b2", 0), e("bb_oc2", 1)));
    nets.add(net("bb_oc2_a", e("bb_ac2", 0), e("bb_oc2", 2)));
    nets.add(net("bb_xs3_b3", e("b3", 0), e("bb_xs3", 1)));
    nets.add(net("bb_xs3_c", e("bb_oc2", 0), e("bb_xs3", 2)));
    // U2 = (¬T1·B2) ∨ (T1·s2)
    components.add(new ComponentSpec("bb_u2a", "Gates", "AND Gate", 1, "A3"));
    components.add(new ComponentSpec("bb_u2b", "Gates", "AND Gate", 1, "A4"));
    components.add(new ComponentSpec("bb_or2", "Gates", "OR Gate", 1, "O3"));
    hints.add(new LayoutHint("bb_u2a", 3, 0, 0, null));
    hints.add(new LayoutHint("bb_u2b", 3, 0, 1, null));
    hints.add(new LayoutHint("bb_or2", 3, 0, 2, null));
    nets.add(net("bb_u2a_nt", e("bb_nt", 0), e("bb_u2a", 1)));
    nets.add(net("bb_u2a_b2", e("b2", 0), e("bb_u2a", 2)));
    nets.add(net("bb_u2b_t", e("bb_and", 0), e("bb_u2b", 1)));
    nets.add(net("bb_u2b_s2", e("bb_xs2", 0), e("bb_u2b", 2)));
    nets.add(net("bb_or2_a", e("bb_u2a", 0), e("bb_or2", 1)));
    nets.add(net("bb_or2_b", e("bb_u2b", 0), e("bb_or2", 2)));
    nets.add(net("bb_u2", e("bb_or2", 0), e("u2", 0)));
    // U3 = (¬T1·B3) ∨ (T1·s3)
    components.add(new ComponentSpec("bb_u3a", "Gates", "AND Gate", 1, "A5"));
    components.add(new ComponentSpec("bb_u3b", "Gates", "AND Gate", 1, "A6"));
    components.add(new ComponentSpec("bb_or3", "Gates", "OR Gate", 1, "O4"));
    hints.add(new LayoutHint("bb_u3a", 4, 0, 0, null));
    hints.add(new LayoutHint("bb_u3b", 4, 0, 1, null));
    hints.add(new LayoutHint("bb_or3", 4, 0, 2, null));
    nets.add(net("bb_u3a_nt", e("bb_nt", 0), e("bb_u3a", 1)));
    nets.add(net("bb_u3a_b3", e("b3", 0), e("bb_u3a", 2)));
    nets.add(net("bb_u3b_t", e("bb_and", 0), e("bb_u3b", 1)));
    nets.add(net("bb_u3b_s3", e("bb_xs3", 0), e("bb_u3b", 2)));
    nets.add(net("bb_or3_a", e("bb_u3a", 0), e("bb_or3", 1)));
    nets.add(net("bb_or3_b", e("bb_u3b", 0), e("bb_or3", 2)));
    nets.add(net("bb_u3", e("bb_or3", 0), e("u3", 0)));
    return new CircuitSpec("bin2bcd_4bit", components, ports, nets, hints);
  }

  /**
   * 9's complement of a BCD digit (9的补码, used in BCD subtraction): with
   * BCD bits (w,x,y,z)=(D8,D4,D2,D1), the borrow-subtraction of 9−D gives
   * r0=¬z, r1=y, r2=x⊕y, r3=¬(w⊕(x∨y)). Verified: 0→9, 5→4, 9→0. ~5 gates.
   * Combinational. 2-input gates end0=out, end1/end2=in; NOT end0=out, end1=in.
   */
  static CircuitSpec buildNineComplement() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addInput(ports, "z", "D1", "nc_n0");
    addInput(ports, "y", "D2", "nc_x2");
    addInput(ports, "x", "D4", "nc_x2");
    addInput(ports, "w", "D8", "nc_x3");
    addOutput(ports, "r0", "C1", "nc_n0");
    addOutput(ports, "r1", "C2", "nc_x2");
    addOutput(ports, "r2", "C4", "nc_x2");
    addOutput(ports, "r3", "C8", "nc_n3");
    components.add(new ComponentSpec("nc_n0", "Gates", "NOT Gate", 1, "N1"));
    components.add(new ComponentSpec("nc_x2", "Gates", "XOR Gate", 1, "X2"));
    components.add(new ComponentSpec("nc_o", "Gates", "OR Gate", 1, "O1"));
    components.add(new ComponentSpec("nc_x3", "Gates", "XOR Gate", 1, "X3"));
    components.add(new ComponentSpec("nc_n3", "Gates", "NOT Gate", 1, "N2"));
    hints.add(new LayoutHint("nc_n0", 0, 0, 0, null));
    hints.add(new LayoutHint("nc_x2", 1, 0, 0, null));
    hints.add(new LayoutHint("nc_o", 2, 0, 0, null));
    hints.add(new LayoutHint("nc_x3", 3, 0, 0, null));
    hints.add(new LayoutHint("nc_n3", 4, 0, 0, null));
    // r0 = ¬z
    nets.add(net("nc_n0_in", e("z", 0), e("nc_n0", 1)));
    nets.add(net("nc_r0", e("nc_n0", 0), e("r0", 0)));
    // r1 = y
    nets.add(net("nc_r1", e("y", 0), e("r1", 0)));
    // r2 = x⊕y
    nets.add(net("nc_x2_x", e("x", 0), e("nc_x2", 1)));
    nets.add(net("nc_x2_y", e("y", 0), e("nc_x2", 2)));
    nets.add(net("nc_r2", e("nc_x2", 0), e("r2", 0)));
    // b3 = x∨y; r3 = ¬(w⊕b3)
    nets.add(net("nc_o_x", e("x", 0), e("nc_o", 1)));
    nets.add(net("nc_o_y", e("y", 0), e("nc_o", 2)));
    nets.add(net("nc_x3_w", e("w", 0), e("nc_x3", 1)));
    nets.add(net("nc_x3_b", e("nc_o", 0), e("nc_x3", 2)));
    nets.add(net("nc_n3_in", e("nc_x3", 0), e("nc_n3", 1)));
    nets.add(net("nc_r3", e("nc_n3", 0), e("r3", 0)));
    return new CircuitSpec("nine_complement", components, ports, nets, hints);
  }

  /**
   * Tri-state buffer (三态缓冲器) using the native TTL 74125 quad buffer, gate 1:
   * A (end1) → Y (end2) when the active-low enable nG (end0) is 0; when nG=1 the
   * output is high-impedance (floating). Port map verified from Ttl74125 source:
   * gate1 enable=end0, data=end1, out=end2.
   */
  static CircuitSpec buildTriStateBuffer() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    components.add(new ComponentSpec("tsb", "TTL", "74125", 1, "U1"));
    hints.add(new LayoutHint("tsb", 0, 0, 0, null));
    addInput(ports, "a", "A", "tsb");
    addInput(ports, "ng", "nG", "tsb");
    addOutput(ports, "y", "Y", "tsb");
    nets.add(net("tsb_ng", e("ng", 0), e("tsb", 0)));
    nets.add(net("tsb_a", e("a", 0), e("tsb", 1)));
    nets.add(net("tsb_y", e("tsb", 2), e("y", 0)));
    return new CircuitSpec("tristate_buffer", components, ports, nets, hints);
  }

  /**
   * 4-bit maximal-length LFSR (linear feedback shift register, 伪随机数/m序列),
   * polynomial x⁴+x³+1 → feedback = Q3⊕Q2. Shift: Q0←D0, Q_{i+1}←Q_i, with
   * D0 = SEED ∨ feedback so an all-zero dead-lock can be broken (drive SEED=1
   * for one clock). Produces a 15-state pseudo-random sequence. Shared CLK/RST.
   * Port maps: D-FF end0=D, end1=CLK, end2=Q, end4=reset; XOR/OR end0=out,
   * end1/end2=in.
   */
  static CircuitSpec buildLfsr4() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "l0");
    addInput(ports, "seed", "SEED", "l0");
    addInput(ports, "rst", "RST", "l0");
    for (int i = 0; i < 4; i++) {
      addOutput(ports, "q" + i, "Q" + i, "l" + i);
    }
    components.add(new ComponentSpec("l0", "Memory", "D Flip-Flop", 1, "U1"));
    components.add(new ComponentSpec("l1", "Memory", "D Flip-Flop", 1, "U2"));
    components.add(new ComponentSpec("l2", "Memory", "D Flip-Flop", 1, "U3"));
    components.add(new ComponentSpec("l3", "Memory", "D Flip-Flop", 1, "U4"));
    components.add(new ComponentSpec("l_fx", "Gates", "XOR Gate", 1, "X1"));
    components.add(new ComponentSpec("l_or", "Gates", "OR Gate", 1, "O1"));
    hints.add(new LayoutHint("l0", 0, 0, 0, null));
    hints.add(new LayoutHint("l1", 1, 0, 0, null));
    hints.add(new LayoutHint("l2", 2, 0, 0, null));
    hints.add(new LayoutHint("l3", 3, 0, 0, null));
    hints.add(new LayoutHint("l_fx", 0, 0, 1, null));
    hints.add(new LayoutHint("l_or", 0, 0, 2, null));
    // feedback = Q3⊕Q2
    nets.add(net("l_fx_q3", e("l3", 2), e("l_fx", 1)));
    nets.add(net("l_fx_q2", e("l2", 2), e("l_fx", 2)));
    // D0 = SEED ∨ feedback
    nets.add(net("l_or_seed", e("seed", 0), e("l_or", 1)));
    nets.add(net("l_or_fx", e("l_fx", 0), e("l_or", 2)));
    nets.add(net("l_d0", e("l_or", 0), e("l0", 0)));
    // shift chain: D1=Q0, D2=Q1, D3=Q2
    nets.add(net("l_d1", e("l0", 2), e("l1", 0)));
    nets.add(net("l_d2", e("l1", 2), e("l2", 0)));
    nets.add(net("l_d3", e("l2", 2), e("l3", 0)));
    for (int i = 0; i < 4; i++) {
      nets.add(net("l_clk" + i, e("clk", 0), e("l" + i, 1)));
      nets.add(net("l_rst" + i, e("rst", 0), e("l" + i, 4)));
      nets.add(net("l_q" + i, e("l" + i, 2), e("q" + i, 0)));
    }
    return new CircuitSpec("lfsr_4bit", components, ports, nets, hints);
  }

  /**
   * 4-bit ring counter (环形计数器): a single 1 circulates around a D-FF
   * shift ring. D0 = SEED ∨ Q3, D_i = Q_{i-1}; drive SEED=1 for one clock to
   * seed a single 1 (otherwise the all-zero state dead-locks). Sequence:
   * 1000 → 0100 → 0010 → 0001 → 1000 (mod-4). Shared CLK/RST. Port maps:
   * D-FF end0=D, end1=CLK, end2=Q, end4=reset; OR end0=out, end1/end2=in.
   */
  static CircuitSpec buildRingCounter() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "rc0");
    addInput(ports, "seed", "SEED", "rc0");
    addInput(ports, "rst", "RST", "rc0");
    for (int i = 0; i < 4; i++) {
      addOutput(ports, "q" + i, "Q" + i, "rc" + i);
    }
    components.add(new ComponentSpec("rc0", "Memory", "D Flip-Flop", 1, "U1"));
    components.add(new ComponentSpec("rc1", "Memory", "D Flip-Flop", 1, "U2"));
    components.add(new ComponentSpec("rc2", "Memory", "D Flip-Flop", 1, "U3"));
    components.add(new ComponentSpec("rc3", "Memory", "D Flip-Flop", 1, "U4"));
    components.add(new ComponentSpec("rc_or", "Gates", "OR Gate", 1, "O1"));
    hints.add(new LayoutHint("rc0", 0, 0, 0, null));
    hints.add(new LayoutHint("rc1", 1, 0, 0, null));
    hints.add(new LayoutHint("rc2", 2, 0, 0, null));
    hints.add(new LayoutHint("rc3", 3, 0, 0, null));
    hints.add(new LayoutHint("rc_or", 0, 0, 1, null));
    // D0 = SEED ∨ Q3
    nets.add(net("rc_or_seed", e("seed", 0), e("rc_or", 1)));
    nets.add(net("rc_or_q3", e("rc3", 2), e("rc_or", 2)));
    nets.add(net("rc_d0", e("rc_or", 0), e("rc0", 0)));
    // shift ring: D1=Q0, D2=Q1, D3=Q2
    nets.add(net("rc_d1", e("rc0", 2), e("rc1", 0)));
    nets.add(net("rc_d2", e("rc1", 2), e("rc2", 0)));
    nets.add(net("rc_d3", e("rc2", 2), e("rc3", 0)));
    for (int i = 0; i < 4; i++) {
      nets.add(net("rc_clk" + i, e("clk", 0), e("rc" + i, 1)));
      nets.add(net("rc_rst" + i, e("rst", 0), e("rc" + i, 4)));
      nets.add(net("rc_q" + i, e("rc" + i, 2), e("q" + i, 0)));
    }
    return new CircuitSpec("ring_counter", components, ports, nets, hints);
  }

  /**
   * Gate-level S-R latch (two NANDs): nQ = NAND(S, Q), Q = NAND(R, nQ).
   * Async set/reset — the core of a "抢答器" (buzzer) where the first press
   * latches. Verified via direct S/R drive.
   */
  static CircuitSpec buildSRLatch() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    components.add(new ComponentSpec("sr_q", "Gates", "NAND Gate", 1, "U1"));
    hints.add(new LayoutHint("sr_q", 0, 0, 0, null));
    components.add(new ComponentSpec("sr_nq", "Gates", "NAND Gate", 1, "U2"));
    hints.add(new LayoutHint("sr_nq", 0, 0, 0, null));
    addInput(ports, "s", "S", "sr_nq");
    addInput(ports, "r", "R", "sr_q");
    addOutput(ports, "q", "Q", "sr_q");
    addOutput(ports, "nq", "nQ", "sr_nq");
    // Standard NAND S-R latch (active-low): Q = NAND(S, nQ); nQ = NAND(R, Q)
    nets.add(net("sr_q1", e("s", 0), e("sr_q", 1)));
    nets.add(net("sr_nq_fb", e("sr_nq", 0), e("sr_q", 2)));
    nets.add(net("sr_nq1", e("r", 0), e("sr_nq", 1)));
    nets.add(net("sr_q_fb", e("sr_q", 0), e("sr_nq", 2)));
    nets.add(net("sr_qout", e("sr_q", 0), e("q", 0)));
    nets.add(net("sr_nqout", e("sr_nq", 0), e("nq", 0)));
    return new CircuitSpec("srlatch", components, ports, nets, hints);
  }

  /**
   * Transparent (level-sensitive) D latch built from NAND gates: G1 = NAND(EN, D),
   * G2 = NAND(EN, NOT D), Q = NAND(G1, nQ), nQ = NAND(G2, Q). EN=1 → Q follows D;
   * EN=0 → cross-coupled NANDs hold state. All Gates library (portable, no Memory).
   * NAND: end0=out, end1/end2=in; NOT: end0=out, end1=in.
   */
  static CircuitSpec buildDLatch() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    components.add(new ComponentSpec("dl_nd", "Gates", "NOT Gate", 1, "DN"));
    components.add(new ComponentSpec("dl_g1", "Gates", "NAND Gate", 1, "U1"));
    components.add(new ComponentSpec("dl_g2", "Gates", "NAND Gate", 1, "U2"));
    components.add(new ComponentSpec("dl_q", "Gates", "NAND Gate", 1, "U3"));
    components.add(new ComponentSpec("dl_nq", "Gates", "NAND Gate", 1, "U4"));
    hints.add(new LayoutHint("dl_nd", 0, 0, 0, null));
    hints.add(new LayoutHint("dl_g1", 0, 0, 1, null));
    hints.add(new LayoutHint("dl_g2", 0, 0, 2, null));
    hints.add(new LayoutHint("dl_q", 0, 0, 3, null));
    hints.add(new LayoutHint("dl_nq", 0, 0, 4, null));
    addInput(ports, "d", "D", "dl_g1");
    addInput(ports, "en", "EN", "dl_g1");
    addOutput(ports, "q", "Q", "dl_q");
    addOutput(ports, "nq", "nQ", "dl_nq");
    nets.add(net("dl_nd_in", e("d", 0), e("dl_nd", 1)));
    nets.add(net("dl_g1_d", e("d", 0), e("dl_g1", 2)));
    nets.add(net("dl_g1_en", e("en", 0), e("dl_g1", 1)));
    nets.add(net("dl_nd_out", e("dl_nd", 0), e("dl_g2", 2)));
    nets.add(net("dl_g2_en", e("en", 0), e("dl_g2", 1)));
    nets.add(net("dl_g1_out", e("dl_g1", 0), e("dl_q", 1)));
    nets.add(net("dl_g2_out", e("dl_g2", 0), e("dl_nq", 1)));
    nets.add(net("dl_q_nq", e("dl_q", 0), e("dl_nq", 2)));
    nets.add(net("dl_nq_q", e("dl_nq", 0), e("dl_q", 2)));
    nets.add(net("dl_q_out", e("dl_q", 0), e("q", 0)));
    nets.add(net("dl_nq_out", e("dl_nq", 0), e("nq", 0)));
    return new CircuitSpec("dlatch", components, ports, nets, hints);
  }

  /**
   * N-bit binary → Gray code converter: G_{n-1} = B_{n-1} (MSB passes through),
   * G_i = B_i XOR B_{i+1} for i &lt; n-1. n-1 XOR gates, purely combinational.
   * XOR: end0=out, end1/end2=in.
   */
  static CircuitSpec buildGrayCode(int bits) {
    if (bits < 1 || bits > 32) {
      throw new IllegalArgumentException("Gray code width must be 1..32, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    String lastXor = bits >= 2 ? "gx" + (bits - 2) : "gx0";
    for (int i = 0; i < bits; i++) {
      addInput(ports, "b" + i, "B" + i, i == bits - 1 ? lastXor : "gx" + i);
      addOutput(ports, "g" + i, "G" + i, i == bits - 1 ? lastXor : "gx" + i);
    }
    for (int i = 0; i < bits - 1; i++) {
      String g = "gx" + i;
      components.add(new ComponentSpec(g, "Gates", "XOR Gate", 1, "U" + (i + 1)));
      hints.add(new LayoutHint(g, i, 0, 0, null));
      nets.add(net("gx" + i + "_a", e("b" + i, 0), e(g, 1)));
      nets.add(net("gx" + i + "_b", e("b" + (i + 1), 0), e(g, 2)));      nets.add(net("gx" + i + "_o", e(g, 0), e("g" + i, 0)));
    }
    nets.add(net("gx_msb", e("b" + (bits - 1), 0), e("g" + (bits - 1), 0)));
    return new CircuitSpec("gray_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-bit Gray → binary converter: B_{n-1} = G_{n-1} (MSB direct),
   * B_i = G_i XOR B_{i+1} (ripple cascade, MSB→LSB). n-1 XOR gates,
   * purely combinational. XOR: end0=out, end1/end2=in.
   */
  static CircuitSpec buildGrayToBinary(int bits) {
    if (bits < 1 || bits > 32) {
      throw new IllegalArgumentException("Gray-to-binary width must be 1..32, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    String lastXor = bits >= 2 ? "g2b" + (bits - 2) : "g2b0";
    for (int i = 0; i < bits; i++) {
      addInput(ports, "g" + i, "G" + i, i == bits - 1 ? lastXor : "g2b" + i);
      addOutput(ports, "b" + i, "B" + i, i == bits - 1 ? lastXor : "g2b" + i);
    }
    for (int i = bits - 2; i >= 0; i--) {
      String gate = "g2b" + i;
      components.add(new ComponentSpec(gate, "Gates", "XOR Gate", 1, "U" + (i + 1)));
      hints.add(new LayoutHint(gate, i, 0, 0, null));
      nets.add(net("g2b" + i + "_g", e("g" + i, 0), e(gate, 1)));
      String bSrc = (i == bits - 2) ? "g" + (bits - 1) : "g2b" + (i + 1);
      nets.add(net("g2b" + i + "_b", e(bSrc, 0), e(gate, 2)));
      nets.add(net("g2b" + i + "_o", e(gate, 0), e("b" + i, 0)));
    }
    nets.add(net("g2b_msb", e("g" + (bits - 1), 0), e("b" + (bits - 1), 0)));
    return new CircuitSpec("gray2bin_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-bit Gray-code counter (格雷码计数器): binary ripple counter whose outputs
   * are presented as Gray code. FF0 toggles every clock (D=¬Q0); FFi toggles
   * when all lower bits are 1 (D = Qi XOR carry, carry = AND chain). Gray
   * outputs G_i = Q_i XOR Q_{i+1} (MSB passes through). Port map per
   * flip-flop: end0=D, end1=CLK, end2=Q, end3=nQ, end4=reset. Carry chain uses
   * the Q outputs (endIndex 2), never the D inputs.
   */
  static CircuitSpec buildGrayCounter(int bits) {
    if (bits < 1 || bits > 32) {
      throw new IllegalArgumentException("Gray counter width must be 1..32, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    int seq = 0;

    addClockInput(ports, "clk", "CLK", "gc_ff0");
    addInput(ports, "rst", "RST", "gc_ff0");
    for (int i = 0; i < bits; i++) {
      addOutput(ports, "g" + i, "G" + i, "gc_ff" + i);
      addOutput(ports, "q" + i, "Q" + i, "gc_ff" + i);
      components.add(new ComponentSpec("gc_ff" + i, "Memory", "D Flip-Flop", 1, "U" + (i + 1)));
      hints.add(new LayoutHint("gc_ff" + i, i, 0, 0, null));
    }

    // Toggle chain: D0 = ¬Q0; Di = Qi XOR carry (carry = AND of all Qj, j<i).
    String notQ0 = "gc_n" + seq;
    components.add(new ComponentSpec(notQ0, "Gates", "NOT Gate", 1, "N1"));
    hints.add(new LayoutHint(notQ0, 1, 0, 0, null));
    seq++;
    nets.add(net("gc_n0i", e("gc_ff0", 2), e(notQ0, 1)));
    nets.add(net("gc_d0", e(notQ0, 0), e("gc_ff0", 0)));
    String carry = "gc_ff0";
    int carryOut = 2; // D-FF Q output is endIndex 2 (end0=D, 1=CLK, 2=Q)
    for (int i = 1; i < bits; i++) {
      String dx = "gc_dx" + seq;
      components.add(new ComponentSpec(dx, "Gates", "XOR Gate", 1, "X" + (seq + 1)));
      hints.add(new LayoutHint(dx, 2, 0, i, null));
      seq++;
      nets.add(net("gc_dxa" + seq, e("gc_ff" + i, 2), e(dx, 1)));
      nets.add(net("gc_dxb" + seq, e(carry, carryOut), e(dx, 2)));
      nets.add(net("gc_d" + i, e(dx, 0), e("gc_ff" + i, 0)));
      if (i < bits - 1) {
        String cg = "gc_cg" + seq;
        components.add(new ComponentSpec(cg, "Gates", "AND Gate", 1, "A" + (seq + 1)));
        hints.add(new LayoutHint(cg, 1, 0, i, null));
        seq++;
        nets.add(net("gc_cga" + seq, e(carry, carryOut), e(cg, 1)));
        nets.add(net("gc_cgb" + seq, e("gc_ff" + i, 2), e(cg, 2)));
        carry = cg;
        carryOut = 0; // AND gate output is endIndex 0
      }
    }

    // Gray outputs: G_i = Q_i XOR Q_{i+1}, G_msb = Q_msb.
    for (int i = 0; i < bits - 1; i++) {
      String gx = "gc_gx" + i;
      components.add(new ComponentSpec(gx, "Gates", "XOR Gate", 1, "UG" + (i + 1)));
      hints.add(new LayoutHint(gx, 3, 0, i, null));
      seq++;
      nets.add(net("gc_gxa" + seq, e("gc_ff" + i, 2), e(gx, 1)));
      nets.add(net("gc_gxb" + seq, e("gc_ff" + (i + 1), 2), e(gx, 2)));
      nets.add(net("gc_g" + i, e(gx, 0), e("g" + i, 0)));
    }
    nets.add(net("gc_gmsb", e("gc_ff" + (bits - 1), 2), e("g" + (bits - 1), 0)));

    // Shared CLK and RST.
    for (int i = 0; i < bits; i++) {
      nets.add(net("gc_clk" + i, e("clk", 0), e("gc_ff" + i, 1)));
      nets.add(net("gc_rst" + i, e("rst", 0), e("gc_ff" + i, 4)));
      nets.add(net("gc_q" + i, e("gc_ff" + i, 2), e("q" + i, 0)));
    }

    return new CircuitSpec("graycnt_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * 4-bit BCD → excess-3 (余3码) converter: E3 = BCD + 3. With BCD bits
   * (w,x,y,z) = (D8,D4,D2,D1): E1=¬z, E2=¬(y⊕z), E4=x⊕(y∨z), E8=w⊕(x∧(y∨z)).
   * Combinational; 3 XOR + 2 NOT + 1 OR + 1 AND. 2-input gates end0=out,
   * end1/end2=in; NOT end0=out, end1=in.
   */
  static CircuitSpec buildExcess3() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addInput(ports, "z", "D1", "e3_x1");
    addInput(ports, "y", "D2", "e3_x1");
    addInput(ports, "x", "D4", "e3_x4");
    addInput(ports, "w", "D8", "e3_x8");
    addOutput(ports, "e1", "E1", "e3_n1");
    addOutput(ports, "e2", "E2", "e3_n2");
    addOutput(ports, "e4", "E4", "e3_x4");
    addOutput(ports, "e8", "E8", "e3_x8");
    components.add(new ComponentSpec("e3_x1", "Gates", "XOR Gate", 1, "U1"));
    components.add(new ComponentSpec("e3_n2", "Gates", "NOT Gate", 1, "U2"));
    components.add(new ComponentSpec("e3_n1", "Gates", "NOT Gate", 1, "U3"));
    components.add(new ComponentSpec("e3_o", "Gates", "OR Gate", 1, "U4"));
    components.add(new ComponentSpec("e3_x4", "Gates", "XOR Gate", 1, "U5"));
    components.add(new ComponentSpec("e3_a", "Gates", "AND Gate", 1, "U6"));
    components.add(new ComponentSpec("e3_x8", "Gates", "XOR Gate", 1, "U7"));
    hints.add(new LayoutHint("e3_x1", 0, 0, 0, null));
    hints.add(new LayoutHint("e3_n2", 1, 0, 0, null));
    hints.add(new LayoutHint("e3_n1", 2, 0, 0, null));
    hints.add(new LayoutHint("e3_o", 3, 0, 0, null));
    hints.add(new LayoutHint("e3_x4", 4, 0, 0, null));
    hints.add(new LayoutHint("e3_a", 5, 0, 0, null));
    hints.add(new LayoutHint("e3_x8", 6, 0, 0, null));
    // E2 = ¬(y⊕z)
    nets.add(net("e3_x1_y", e("y", 0), e("e3_x1", 1)));
    nets.add(net("e3_x1_z", e("z", 0), e("e3_x1", 2)));
    nets.add(net("e3_n2_in", e("e3_x1", 0), e("e3_n2", 1)));
    nets.add(net("e3_e2", e("e3_n2", 0), e("e2", 0)));
    // E1 = ¬z
    nets.add(net("e3_n1_in", e("z", 0), e("e3_n1", 1)));
    nets.add(net("e3_e1", e("e3_n1", 0), e("e1", 0)));
    // OR1 = y∨z; E4 = x⊕OR1; carry3 = x∧OR1; E8 = w⊕carry3
    nets.add(net("e3_o_y", e("y", 0), e("e3_o", 1)));
    nets.add(net("e3_o_z", e("z", 0), e("e3_o", 2)));
    nets.add(net("e3_x4_x", e("x", 0), e("e3_x4", 1)));
    nets.add(net("e3_x4_o", e("e3_o", 0), e("e3_x4", 2)));
    nets.add(net("e3_e4", e("e3_x4", 0), e("e4", 0)));
    nets.add(net("e3_a_x", e("x", 0), e("e3_a", 1)));
    nets.add(net("e3_a_o", e("e3_o", 0), e("e3_a", 2)));
    nets.add(net("e3_x8_w", e("w", 0), e("e3_x8", 1)));
    nets.add(net("e3_x8_a", e("e3_a", 0), e("e3_x8", 2)));
    nets.add(net("e3_e8", e("e3_x8", 0), e("e8", 0)));
    return new CircuitSpec("excess3_bcd", components, ports, nets, hints);
  }

  /**
   * Excess-3 ADDER (余3码加法器): adds two excess-3 digits (3..12) with the
   * standard correction — S = A + B; if carry out (decimal sum ≥ 10): result
   * = S + 3, else result = S - 3 (i.e. +13 mod 16). Outputs S0..S3 (excess-3)
   * and Cout. All gates 2-input; "ONE"/"GND" pins supply the constant lanes.
   */
  static CircuitSpec buildExcess3Adder() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    int seq = 0;

    for (int i = 0; i < 4; i++) {
      addInput(ports, "a" + i, "A" + i, "e3a_x0");
      addInput(ports, "b" + i, "B" + i, "e3a_x0");
    }
    addInput(ports, "one", "ONE", "e3a_x0");
    addInput(ports, "gnd", "GND", "e3a_x0");
    for (int i = 0; i < 4; i++) {
      addOutput(ports, "s" + i, "S" + i, "e3a_x0");
    }
    addOutput(ports, "cout", "Cout", "e3a_x0");

    // Stage 1: T = A + B (ripple, carry-in 0); c4 = carry out.
    String[] t = new String[4];
    String c = "gnd";
    for (int i = 0; i < 4; i++) {
      String x = "e3a_x" + seq;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "E3X" + (seq + 1)));
      hints.add(new LayoutHint(x, 0, 0, i, null));
      String s = "e3a_s" + seq;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "E3S" + (seq + 1)));
      hints.add(new LayoutHint(s, 0, 0, i, null));
      String fa = "e3a_fa" + seq;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "E3A" + (seq + 1)));
      hints.add(new LayoutHint(fa, 0, 0, i, null));
      String fb = "e3a_fb" + seq;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "E3B" + (seq + 1)));
      hints.add(new LayoutHint(fb, 0, 0, i, null));
      String fo = "e3a_fo" + seq;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "E3O" + (seq + 1)));
      hints.add(new LayoutHint(fo, 0, 0, i, null));
      String fg = "e3a_fg" + seq;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "E3G" + (seq + 1)));
      hints.add(new LayoutHint(fg, 0, 0, i, null));
      nets.add(net("e3a_xa" + seq, e("a" + i, 0), e(x, 1)));
      nets.add(net("e3a_xb" + seq, e("b" + i, 0), e(x, 2)));
      nets.add(net("e3a_sa" + seq, e(x, 0), e(s, 1)));
      nets.add(net("e3a_sb" + seq, e(c, 0), e(s, 2)));
      t[i] = s;
      nets.add(net("e3a_faa" + seq, e("a" + i, 0), e(fa, 1)));
      nets.add(net("e3a_fab" + seq, e("b" + i, 0), e(fa, 2)));
      nets.add(net("e3a_fba" + seq, e(c, 0), e(fb, 1)));
      nets.add(net("e3a_fbb" + seq, e(x, 0), e(fb, 2)));
      nets.add(net("e3a_fo1" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net("e3a_fo2" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net("e3a_fg1" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net("e3a_fg2" + seq, e("gnd", 0), e(fg, 2)));
      c = fg;
      seq++;
    }
    String c4 = c;

    // Correction addend = (¬c4, ¬c4, c4, 1): +3 when carry, -3 (=+13) otherwise.
    // One NOT(c4) fans out to bits 2 and 3 (router unions fan-out nets).
    String nc4 = "e3a_nc4";
    components.add(new ComponentSpec(nc4, "Gates", "NOT Gate", 1, "E3N1"));
    hints.add(new LayoutHint(nc4, 1, 0, 0, null));
    nets.add(net("e3a_nc4_in", e(c4, 0), e(nc4, 1)));
    String[] addend = {"one", c4, nc4, nc4};

    // Stage 2: S = T + addend (ripple, carry-in 0).
    String[] sOut = new String[4];
    String c2 = "gnd";
    for (int i = 0; i < 4; i++) {
      String x = "e3a_x2_" + seq;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "E3X2" + (seq + 1)));
      hints.add(new LayoutHint(x, 2, 0, i, null));
      String s = "e3a_s2_" + seq;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "E3S2" + (seq + 1)));
      hints.add(new LayoutHint(s, 2, 0, i, null));
      String fa = "e3a_f2a" + seq;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "E3A2" + (seq + 1)));
      hints.add(new LayoutHint(fa, 2, 0, i, null));
      String fb = "e3a_f2b" + seq;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "E3B2" + (seq + 1)));
      hints.add(new LayoutHint(fb, 2, 0, i, null));
      String fo = "e3a_f2o" + seq;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "E3O2" + (seq + 1)));
      hints.add(new LayoutHint(fo, 2, 0, i, null));
      String fg = "e3a_f2g" + seq;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "E3G2" + (seq + 1)));
      hints.add(new LayoutHint(fg, 2, 0, i, null));
      nets.add(net("e3a_x2a" + seq, e(t[i], 0), e(x, 1)));
      nets.add(net("e3a_x2b" + seq, e(addend[i], 0), e(x, 2)));
      nets.add(net("e3a_s2a" + seq, e(x, 0), e(s, 1)));
      nets.add(net("e3a_s2b" + seq, e(c2, 0), e(s, 2)));
      sOut[i] = s;
      nets.add(net("e3a_f2aa" + seq, e(t[i], 0), e(fa, 1)));
      nets.add(net("e3a_f2ab" + seq, e(addend[i], 0), e(fa, 2)));
      nets.add(net("e3a_f2ba" + seq, e(c2, 0), e(fb, 1)));
      nets.add(net("e3a_f2bb" + seq, e(x, 0), e(fb, 2)));
      nets.add(net("e3a_f2o1" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net("e3a_f2o2" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net("e3a_f2g1" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net("e3a_f2g2" + seq, e("gnd", 0), e(fg, 2)));
      c2 = fg;
      seq++;
    }
    for (int i = 0; i < 4; i++) {
      nets.add(net("e3a_sout" + i, e(sOut[i], 0), e("s" + i, 0)));
    }
    nets.add(net("e3a_cout", e(c4, 0), e("cout", 0)));

    return new CircuitSpec("excess3_adder", components, ports, nets, hints);
  }

  /**
   * 4-bit excess-3 → BCD converter (inverse of {@link #buildExcess3()}):
   * BCD = E3 − 3. With E3 bits (w,x,y,z)=(E8,E4,E2,E1): b0=¬z, borrow1=¬z;
   * b1=y⊕z, borrow2=¬(y·z); b2=x⊕borrow2, borrow3=¬x·borrow2; b3=w⊕borrow3.
   * Combinational; 3 XOR + 3 NOT + 2 AND. 2-input gates end0=out, end1/end2=in;
   * NOT end0=out, end1=in.
   */
  static CircuitSpec buildExcess3ToBcd() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addInput(ports, "z", "E1", "e3b_x1");
    addInput(ports, "y", "E2", "e3b_x1");
    addInput(ports, "x", "E4", "e3b_x2");
    addInput(ports, "w", "E8", "e3b_x3");
    addOutput(ports, "b0", "D1", "e3b_n1");
    addOutput(ports, "b1", "D2", "e3b_x1");
    addOutput(ports, "b2", "D4", "e3b_x2");
    addOutput(ports, "b3", "D8", "e3b_x3");
    components.add(new ComponentSpec("e3b_n1", "Gates", "NOT Gate", 1, "N1"));
    components.add(new ComponentSpec("e3b_x1", "Gates", "XOR Gate", 1, "X1"));
    components.add(new ComponentSpec("e3b_a1", "Gates", "AND Gate", 1, "A1"));
    components.add(new ComponentSpec("e3b_n2", "Gates", "NOT Gate", 1, "N2"));
    components.add(new ComponentSpec("e3b_n3", "Gates", "NOT Gate", 1, "N3"));
    components.add(new ComponentSpec("e3b_x2", "Gates", "XOR Gate", 1, "X2"));
    components.add(new ComponentSpec("e3b_a2", "Gates", "AND Gate", 1, "A2"));
    components.add(new ComponentSpec("e3b_x3", "Gates", "XOR Gate", 1, "X3"));
    hints.add(new LayoutHint("e3b_n1", 0, 0, 0, null));
    hints.add(new LayoutHint("e3b_x1", 1, 0, 0, null));
    hints.add(new LayoutHint("e3b_a1", 2, 0, 0, null));
    hints.add(new LayoutHint("e3b_n2", 3, 0, 0, null));
    hints.add(new LayoutHint("e3b_n3", 4, 0, 0, null));
    hints.add(new LayoutHint("e3b_x2", 5, 0, 0, null));
    hints.add(new LayoutHint("e3b_a2", 6, 0, 0, null));
    hints.add(new LayoutHint("e3b_x3", 7, 0, 0, null));
    // b0 = ¬z, borrow1 = ¬z
    nets.add(net("e3b_n1_in", e("z", 0), e("e3b_n1", 1)));
    nets.add(net("e3b_b0", e("e3b_n1", 0), e("b0", 0)));
    // b1 = y⊕z, borrow2 = ¬(y·z)
    nets.add(net("e3b_x1_y", e("y", 0), e("e3b_x1", 1)));
    nets.add(net("e3b_x1_z", e("z", 0), e("e3b_x1", 2)));
    nets.add(net("e3b_b1", e("e3b_x1", 0), e("b1", 0)));
    nets.add(net("e3b_a1_y", e("y", 0), e("e3b_a1", 1)));
    nets.add(net("e3b_a1_z", e("z", 0), e("e3b_a1", 2)));
    nets.add(net("e3b_n2_in", e("e3b_a1", 0), e("e3b_n2", 1)));
    // b2 = x⊕borrow2, borrow3 = ¬x·borrow2
    nets.add(net("e3b_n3_in", e("x", 0), e("e3b_n3", 1)));
    nets.add(net("e3b_x2_x", e("x", 0), e("e3b_x2", 1)));
    nets.add(net("e3b_x2_br", e("e3b_n2", 0), e("e3b_x2", 2)));
    nets.add(net("e3b_b2", e("e3b_x2", 0), e("b2", 0)));
    nets.add(net("e3b_a2_nx", e("e3b_n3", 0), e("e3b_a2", 1)));
    nets.add(net("e3b_a2_br", e("e3b_n2", 0), e("e3b_a2", 2)));
    // b3 = w⊕borrow3
    nets.add(net("e3b_x3_w", e("w", 0), e("e3b_x3", 1)));
    nets.add(net("e3b_x3_br", e("e3b_a2", 0), e("e3b_x3", 2)));
    nets.add(net("e3b_b3", e("e3b_x3", 0), e("b3", 0)));
    return new CircuitSpec("excess3tobcd", components, ports, nets, hints);
  }

  /**
   * N-bit two's complement (补码) converter: B = NOT A + 1. b0 = A0;
   * a'i = NOT Ai; c0 = a'0; for i=1..n-1: b_i = a'i XOR c_{i-1} and
   * c_i = a'i AND c_{i-1} (ripple carry). Purely combinational.
   * 2-input gates end0=out, end1/end2=in; NOT end0=out, end1=in.
   */
  static CircuitSpec buildTwosComplement(int bits) {
    if (bits < 1 || bits > 32) {
      throw new IllegalArgumentException("Two's complement width must be 1..32, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    for (int i = 0; i < bits; i++) {
      addInput(ports, "a" + i, "A" + i, i == 0 ? "tc_n0" : "tc_x" + i);
      addOutput(ports, "b" + i, "B" + i, i == 0 ? "tc_n0" : "tc_x" + i);
    }
    for (int i = 0; i < bits; i++) {
      String n = "tc_n" + i;
      components.add(new ComponentSpec(n, "Gates", "NOT Gate", 1, "N" + (i + 1)));
      hints.add(new LayoutHint(n, i, 0, 0, null));
      nets.add(net("tc_not" + i, e("a" + i, 0), e(n, 1)));
    }
    nets.add(net("tc_b0", e("a0", 0), e("b0", 0)));
    String carry = "tc_n0"; // c0 = a'0 (NOT gate output)
    for (int i = 1; i < bits; i++) {
      String and = "tc_a" + i;
      String xr = "tc_x" + i;
      components.add(new ComponentSpec(and, "Gates", "AND Gate", 1, "CA" + i));
      components.add(new ComponentSpec(xr, "Gates", "XOR Gate", 1, "XB" + i));
      hints.add(new LayoutHint(and, i, 0, 1, null));
      hints.add(new LayoutHint(xr, i, 0, 2, null));
      nets.add(net("tc_ci" + i + "_a", e("tc_n" + i, 0), e(and, 1)));
      nets.add(net("tc_ci" + i + "_c", e(carry, 0), e(and, 2)));
      nets.add(net("tc_bi" + i + "_a", e("tc_n" + i, 0), e(xr, 1)));
      nets.add(net("tc_bi" + i + "_c", e(carry, 0), e(xr, 2)));
      nets.add(net("tc_b" + i, e(xr, 0), e("b" + i, 0)));
      carry = and;
    }
    return new CircuitSpec("twoscomp_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * Simplified traffic light controller: a 2-bit binary counter (T-FF chain,
   * T0=EN, T1=Q0) decodes to RED/YELLOW/GREEN. GREEN = ¬Q1 (2 states),
   * YELLOW = Q1∧¬Q0 (1 state), RED = Q1∧Q0 (1 state) → a 4-state cycle.
   * Drive EN=1 to count. Port maps: T-FF end0=T, end1=CLK, end2=Q, end4=reset;
   * NOT end0=out/end1=in; AND end0=out/end1/end2=in.
   */
  static CircuitSpec buildTrafficLight() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "tl0");
    addInput(ports, "en", "EN", "tl0");
    addInput(ports, "rst", "RST", "tl0");
    addOutput(ports, "red", "RED", "tl_red");
    addOutput(ports, "yellow", "YELLOW", "tl_yel");
    addOutput(ports, "green", "GREEN", "tl_grn");
    components.add(new ComponentSpec("tl0", "Memory", "T Flip-Flop", 1, "U1"));
    components.add(new ComponentSpec("tl1", "Memory", "T Flip-Flop", 1, "U2"));
    components.add(new ComponentSpec("tl_grn", "Gates", "NOT Gate", 1, "N1"));
    components.add(new ComponentSpec("tl_nq0", "Gates", "NOT Gate", 1, "N2"));
    components.add(new ComponentSpec("tl_red", "Gates", "AND Gate", 1, "R1"));
    components.add(new ComponentSpec("tl_yel", "Gates", "AND Gate", 1, "Y1"));
    hints.add(new LayoutHint("tl0", 0, 0, 0, null));
    hints.add(new LayoutHint("tl1", 1, 0, 0, null));
    hints.add(new LayoutHint("tl_grn", 2, 0, 0, null));
    hints.add(new LayoutHint("tl_nq0", 3, 0, 0, null));
    hints.add(new LayoutHint("tl_red", 4, 0, 0, null));
    hints.add(new LayoutHint("tl_yel", 5, 0, 0, null));
    // 2-bit binary counter: T0 = EN, T1 = Q0; shared CLK and RST
    nets.add(net("tl_en", e("en", 0), e("tl0", 0)));
    nets.add(net("tl_t1", e("tl0", 2), e("tl1", 0)));
    nets.add(net("tl_clk0", e("clk", 0), e("tl0", 1)));
    nets.add(net("tl_clk1", e("clk", 0), e("tl1", 1)));
    nets.add(net("tl_rst0", e("rst", 0), e("tl0", 4)));
    nets.add(net("tl_rst1", e("rst", 0), e("tl1", 4)));
    // GREEN = ¬Q1; ¬Q0; RED = Q1∧Q0; YELLOW = Q1∧¬Q0
    nets.add(net("tl_grn_in", e("tl1", 2), e("tl_grn", 1)));
    nets.add(net("tl_grn_out", e("tl_grn", 0), e("green", 0)));
    nets.add(net("tl_nq0_in", e("tl0", 2), e("tl_nq0", 1)));
    nets.add(net("tl_red_q1", e("tl1", 2), e("tl_red", 1)));
    nets.add(net("tl_red_q0", e("tl0", 2), e("tl_red", 2)));
    nets.add(net("tl_red_out", e("tl_red", 0), e("red", 0)));
    nets.add(net("tl_yel_q1", e("tl1", 2), e("tl_yel", 1)));
    nets.add(net("tl_yel_nq0", e("tl_nq0", 0), e("tl_yel", 2)));
    nets.add(net("tl_yel_out", e("tl_yel", 0), e("yellow", 0)));
    return new CircuitSpec("traffic_light", components, ports, nets, hints);
  }

  /**
   * Moore 101 sequence detector: 4 states S0=00 (idle), S1=01 (saw 1),
   * S2=10 (saw 10), S3=11 (saw 101 → F=1). D0 = X; D1 = Q0·¬X + Q1·¬Q0·X;
   * F = Q1·Q0. Shared CLK/RST. Port maps: D-FF end0=D, end1=CLK, end2=Q,
   * end4=reset; NOT end0=out/end1=in; 2-input AND/OR end0=out/end1/end2=in.
   */
  static CircuitSpec buildSequenceDetector101() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "sd0");
    addInput(ports, "x", "X", "sd0");
    addInput(ports, "rst", "RST", "sd0");
    addOutput(ports, "f", "F", "sd_af");
    components.add(new ComponentSpec("sd0", "Memory", "D Flip-Flop", 1, "U1"));
    components.add(new ComponentSpec("sd1", "Memory", "D Flip-Flop", 1, "U2"));
    components.add(new ComponentSpec("sd_nx", "Gates", "NOT Gate", 1, "N1"));
    components.add(new ComponentSpec("sd_nq0", "Gates", "NOT Gate", 1, "N2"));
    components.add(new ComponentSpec("sd_a1", "Gates", "AND Gate", 1, "A1"));
    components.add(new ComponentSpec("sd_a2a", "Gates", "AND Gate", 1, "A2"));
    components.add(new ComponentSpec("sd_a2", "Gates", "AND Gate", 1, "A3"));
    components.add(new ComponentSpec("sd_or", "Gates", "OR Gate", 1, "O1"));
    components.add(new ComponentSpec("sd_af", "Gates", "AND Gate", 1, "A4"));
    hints.add(new LayoutHint("sd0", 0, 0, 0, null));
    hints.add(new LayoutHint("sd1", 1, 0, 0, null));
    hints.add(new LayoutHint("sd_nx", 2, 0, 0, null));
    hints.add(new LayoutHint("sd_nq0", 3, 0, 0, null));
    hints.add(new LayoutHint("sd_a1", 4, 0, 0, null));
    hints.add(new LayoutHint("sd_a2a", 5, 0, 0, null));
    hints.add(new LayoutHint("sd_a2", 6, 0, 0, null));
    hints.add(new LayoutHint("sd_or", 7, 0, 0, null));
    hints.add(new LayoutHint("sd_af", 8, 0, 0, null));
    // D0 = X
    nets.add(net("sd_d0", e("x", 0), e("sd0", 0)));
    // ¬X, ¬Q0
    nets.add(net("sd_nx_in", e("x", 0), e("sd_nx", 1)));
    nets.add(net("sd_nq0_in", e("sd0", 2), e("sd_nq0", 1)));
    // A1 = Q0∧¬X
    nets.add(net("sd_a1_q0", e("sd0", 2), e("sd_a1", 1)));
    nets.add(net("sd_a1_nx", e("sd_nx", 0), e("sd_a1", 2)));
    // A2a = ¬Q0∧X
    nets.add(net("sd_a2a_nq0", e("sd_nq0", 0), e("sd_a2a", 1)));
    nets.add(net("sd_a2a_x", e("x", 0), e("sd_a2a", 2)));
    // A2 = Q1∧A2a
    nets.add(net("sd_a2_q1", e("sd1", 2), e("sd_a2", 1)));
    nets.add(net("sd_a2_a2a", e("sd_a2a", 0), e("sd_a2", 2)));
    // D1 = A1 ∨ A2
    nets.add(net("sd_or_a1", e("sd_a1", 0), e("sd_or", 1)));
    nets.add(net("sd_or_a2", e("sd_a2", 0), e("sd_or", 2)));
    nets.add(net("sd_d1", e("sd_or", 0), e("sd1", 0)));
    // F = Q1∧Q0
    nets.add(net("sd_af_q1", e("sd1", 2), e("sd_af", 1)));
    nets.add(net("sd_af_q0", e("sd0", 2), e("sd_af", 2)));
    nets.add(net("sd_af_out", e("sd_af", 0), e("f", 0)));
    // shared CLK and RST
    nets.add(net("sd_clk0", e("clk", 0), e("sd0", 1)));
    nets.add(net("sd_clk1", e("clk", 0), e("sd1", 1)));
    nets.add(net("sd_rst0", e("rst", 0), e("sd0", 4)));
    nets.add(net("sd_rst1", e("rst", 0), e("sd1", 4)));
    return new CircuitSpec("seqdet_101", components, ports, nets, hints);
  }

  /**
   * Moore 1101 sequence detector: 5 states S0=000 idle, S1=001 (1), S2=010 (11),
   * S3=011 (110), S4=100 (1101 → F=1). Next state (verified by trace):
   * Q0'=X∧¬Q1∧¬Q0; Q1'=¬Q2∧[(¬Q1∧Q0∧X)∨(Q1∧¬Q0)]; Q2'=¬Q2∧Q1∧Q0∧X;
   * F=Q2∧¬Q1∧¬Q0. 3 D-FF + 3 NOT + 10 AND + 1 OR. Shared CLK/RST. Port maps:
   * D-FF end0=D, end1=CLK, end2=Q, end4=reset; NOT end0=out/end1=in;
   * 2-input AND/OR end0=out/end1/end2=in.
   */
  static CircuitSpec buildSequenceDetector1101() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addClockInput(ports, "clk", "CLK", "s0");
    addInput(ports, "x", "X", "s0");
    addInput(ports, "rst", "RST", "s0");
    addOutput(ports, "f", "F", "s_a11");
    components.add(new ComponentSpec("s0", "Memory", "D Flip-Flop", 1, "U1"));
    components.add(new ComponentSpec("s1", "Memory", "D Flip-Flop", 1, "U2"));
    components.add(new ComponentSpec("s2", "Memory", "D Flip-Flop", 1, "U3"));
    components.add(new ComponentSpec("s_nq0", "Gates", "NOT Gate", 1, "N1"));
    components.add(new ComponentSpec("s_nq1", "Gates", "NOT Gate", 1, "N2"));
    components.add(new ComponentSpec("s_nq2", "Gates", "NOT Gate", 1, "N3"));
    components.add(new ComponentSpec("s_a3", "Gates", "AND Gate", 1, "A3"));
    components.add(new ComponentSpec("s_a4", "Gates", "AND Gate", 1, "A4"));
    components.add(new ComponentSpec("s_a5", "Gates", "AND Gate", 1, "A5"));
    components.add(new ComponentSpec("s_o1", "Gates", "OR Gate", 1, "O1"));
    components.add(new ComponentSpec("s_a6", "Gates", "AND Gate", 1, "A6"));
    components.add(new ComponentSpec("s_a7", "Gates", "AND Gate", 1, "A7"));
    components.add(new ComponentSpec("s_a8", "Gates", "AND Gate", 1, "A8"));
    components.add(new ComponentSpec("s_a9", "Gates", "AND Gate", 1, "A9"));
    components.add(new ComponentSpec("s_a10", "Gates", "AND Gate", 1, "A10"));
    components.add(new ComponentSpec("s_a11", "Gates", "AND Gate", 1, "A11"));
    components.add(new ComponentSpec("s_nx", "Gates", "NOT Gate", 1, "N4"));
    components.add(new ComponentSpec("s_b1", "Gates", "AND Gate", 1, "B1"));
    components.add(new ComponentSpec("s_b2", "Gates", "AND Gate", 1, "B2"));
    components.add(new ComponentSpec("s_b3", "Gates", "AND Gate", 1, "B3"));
    components.add(new ComponentSpec("s_b4", "Gates", "AND Gate", 1, "B4"));
    components.add(new ComponentSpec("s_b5", "Gates", "AND Gate", 1, "B5"));
    components.add(new ComponentSpec("s_bo", "Gates", "OR Gate", 1, "O2"));
    hints.add(new LayoutHint("s0", 0, 0, 0, null));
    hints.add(new LayoutHint("s1", 1, 0, 0, null));
    hints.add(new LayoutHint("s2", 2, 0, 0, null));
    hints.add(new LayoutHint("s_nq0", 3, 0, 0, null));
    hints.add(new LayoutHint("s_nq1", 3, 0, 1, null));
    hints.add(new LayoutHint("s_nq2", 3, 0, 2, null));
    hints.add(new LayoutHint("s_a3", 5, 0, 0, null));
    hints.add(new LayoutHint("s_a4", 5, 0, 1, null));
    hints.add(new LayoutHint("s_a5", 5, 0, 2, null));
    hints.add(new LayoutHint("s_o1", 5, 0, 3, null));
    hints.add(new LayoutHint("s_a6", 5, 0, 4, null));
    hints.add(new LayoutHint("s_a7", 6, 0, 0, null));
    hints.add(new LayoutHint("s_a8", 6, 0, 1, null));
    hints.add(new LayoutHint("s_a9", 6, 0, 2, null));
    hints.add(new LayoutHint("s_a10", 7, 0, 0, null));
    hints.add(new LayoutHint("s_a11", 7, 0, 1, null));
    hints.add(new LayoutHint("s_nx", 8, 0, 0, null));
    hints.add(new LayoutHint("s_b1", 8, 0, 1, null));
    hints.add(new LayoutHint("s_b2", 8, 0, 2, null));
    hints.add(new LayoutHint("s_b3", 8, 0, 3, null));
    hints.add(new LayoutHint("s_b4", 8, 0, 4, null));
    hints.add(new LayoutHint("s_b5", 8, 0, 5, null));
    hints.add(new LayoutHint("s_bo", 8, 0, 6, null));
    // ¬Q0, ¬Q1, ¬Q2
    nets.add(net("s_nq0_in", e("s0", 2), e("s_nq0", 1)));
    nets.add(net("s_nq1_in", e("s1", 2), e("s_nq1", 1)));
    nets.add(net("s_nq2_in", e("s2", 2), e("s_nq2", 1)));
    // Q0' = (¬Q1∧¬Q0∧X) ∨ (¬Q2∧Q1∧¬Q0∧¬X)
    nets.add(net("s_nx_in", e("x", 0), e("s_nx", 1)));
    nets.add(net("s_b1_nq1", e("s_nq1", 0), e("s_b1", 1)));
    nets.add(net("s_b1_nq0", e("s_nq0", 0), e("s_b1", 2)));
    nets.add(net("s_b2_b1", e("s_b1", 0), e("s_b2", 1)));
    nets.add(net("s_b2_x", e("x", 0), e("s_b2", 2)));
    nets.add(net("s_b3_nq2", e("s_nq2", 0), e("s_b3", 1)));
    nets.add(net("s_b3_q1", e("s1", 2), e("s_b3", 2)));
    nets.add(net("s_b4_b3", e("s_b3", 0), e("s_b4", 1)));
    nets.add(net("s_b4_nq0", e("s_nq0", 0), e("s_b4", 2)));
    nets.add(net("s_b5_b4", e("s_b4", 0), e("s_b5", 1)));
    nets.add(net("s_b5_nx", e("s_nx", 0), e("s_b5", 2)));
    nets.add(net("s_bo_b2", e("s_b2", 0), e("s_bo", 1)));
    nets.add(net("s_bo_b5", e("s_b5", 0), e("s_bo", 2)));
    nets.add(net("s_d0", e("s_bo", 0), e("s0", 0)));
    // Q1' = ¬Q2 ∧ [(¬Q1∧Q0∧X) ∨ (Q1∧¬Q0)]
    nets.add(net("s_a3_nq1", e("s_nq1", 0), e("s_a3", 1)));
    nets.add(net("s_a3_q0", e("s0", 2), e("s_a3", 2)));
    nets.add(net("s_a4_a3", e("s_a3", 0), e("s_a4", 1)));
    nets.add(net("s_a4_x", e("x", 0), e("s_a4", 2)));
    nets.add(net("s_a5_q1", e("s1", 2), e("s_a5", 1)));
    nets.add(net("s_a5_nq0", e("s_nq0", 0), e("s_a5", 2)));
    nets.add(net("s_o1_a4", e("s_a4", 0), e("s_o1", 1)));
    nets.add(net("s_o1_a5", e("s_a5", 0), e("s_o1", 2)));
    nets.add(net("s_a6_nq2", e("s_nq2", 0), e("s_a6", 1)));
    nets.add(net("s_a6_o1", e("s_o1", 0), e("s_a6", 2)));
    nets.add(net("s_d1", e("s_a6", 0), e("s1", 0)));
    // Q2' = ¬Q2 ∧ Q1 ∧ Q0 ∧ X
    nets.add(net("s_a7_q1", e("s1", 2), e("s_a7", 1)));
    nets.add(net("s_a7_q0", e("s0", 2), e("s_a7", 2)));
    nets.add(net("s_a8_a7", e("s_a7", 0), e("s_a8", 1)));
    nets.add(net("s_a8_x", e("x", 0), e("s_a8", 2)));
    nets.add(net("s_a9_nq2", e("s_nq2", 0), e("s_a9", 1)));
    nets.add(net("s_a9_a8", e("s_a8", 0), e("s_a9", 2)));
    nets.add(net("s_d2", e("s_a9", 0), e("s2", 0)));
    // F = Q2 ∧ ¬Q1 ∧ ¬Q0
    nets.add(net("s_a10_q2", e("s2", 2), e("s_a10", 1)));
    nets.add(net("s_a10_nq1", e("s_nq1", 0), e("s_a10", 2)));
    nets.add(net("s_a11_a10", e("s_a10", 0), e("s_a11", 1)));
    nets.add(net("s_a11_nq0", e("s_nq0", 0), e("s_a11", 2)));
    nets.add(net("s_f", e("s_a11", 0), e("f", 0)));
    // shared CLK and RST
    for (int i = 0; i < 3; i++) {
      nets.add(net("s_clk" + i, e("clk", 0), e("s" + i, 1)));
      nets.add(net("s_rst" + i, e("rst", 0), e("s" + i, 4)));
    }
    return new CircuitSpec("seqdet_1101", components, ports, nets, hints);
  }

  /**
   * D flip-flop built from the native Logisim "D Flip-Flop" component
   * (Memory library, edge-triggered). Port map (verified component): end0=D,
   * end1=CLK, end2=Q, end3=nQ, end4=reset (active high), end5=preset. The
   * real Clock component (via addClockInput) allows <SET>/<SEQ> vector
   * verification of the Q=D sampling on the clock edge.
   */
  static CircuitSpec buildDFlipFlop() {
    return buildFlipFlop("D Flip-Flop", "dff", 1);
  }

  /**
   * JK flip-flop as gate-level logic around a native D flip-flop:
   * D = (J and nQ) or (not K and Q). This avoids the native "J-K Flip-Flop"'s
   * three same-x inputs (J/K/CLK 20px apart) which the router's stub placement
   * shorts. Verified 9/9 via <SET>/<SEQ> clock sequence.
   */
  static CircuitSpec buildJKFlipFlop() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    components.add(new ComponentSpec("jk_dff", "Memory", "D Flip-Flop", 1, "U1"));
    hints.add(new LayoutHint("jk_dff", 0, 0, 0, null));
    components.add(new ComponentSpec("jk_nk", "Gates", "NOT Gate", 1, "U2"));
    hints.add(new LayoutHint("jk_nk", 1, 0, 0, null));
    components.add(new ComponentSpec("jk_jq", "Gates", "AND Gate", 1, "U3"));
    hints.add(new LayoutHint("jk_jq", 1, 0, 0, null));
    components.add(new ComponentSpec("jk_kq", "Gates", "AND Gate", 1, "U4"));
    hints.add(new LayoutHint("jk_kq", 1, 0, 0, null));
    components.add(new ComponentSpec("jk_or", "Gates", "OR Gate", 1, "U5"));
    hints.add(new LayoutHint("jk_or", 1, 0, 0, null));
    addClockInput(ports, "clk", "CLK", "jk_dff");
    addInput(ports, "j", "J", "jk_jq");
    addInput(ports, "k", "K", "jk_nk");
    addInput(ports, "rst", "RST", "jk_dff");
    addOutput(ports, "q", "Q", "jk_dff");
    addOutput(ports, "nq", "nQ", "jk_dff");
    nets.add(net("jk_nk1", e("k", 0), e("jk_nk", 1)));
    nets.add(net("jk_jq1", e("j", 0), e("jk_jq", 1)));
    nets.add(net("jk_jq2", e("jk_dff", 3), e("jk_jq", 2)));
    nets.add(net("jk_kq1", e("jk_nk", 0), e("jk_kq", 1)));
    nets.add(net("jk_kq2", e("jk_dff", 2), e("jk_kq", 2)));
    nets.add(net("jk_or1", e("jk_jq", 0), e("jk_or", 1)));
    nets.add(net("jk_or2", e("jk_kq", 0), e("jk_or", 2)));
    nets.add(net("jk_d", e("jk_or", 0), e("jk_dff", 0)));
    nets.add(net("jk_clk", e("clk", 0), e("jk_dff", 1)));
    nets.add(net("jk_rst", e("rst", 0), e("jk_dff", 4)));
    nets.add(net("jk_q", e("jk_dff", 2), e("q", 0)));
    nets.add(net("jk_nq", e("jk_dff", 3), e("nq", 0)));
    return new CircuitSpec("jkff", components, ports, nets, hints);
  }

  /** T flip-flop (native component): end0=T, end1=CLK, end2=Q, end3=nQ, end4=reset, end5=preset. */
  static CircuitSpec buildTFlipFlop() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    components.add(new ComponentSpec("t0", "Memory", "T Flip-Flop", 1, "U1"));
    hints.add(new LayoutHint("t0", 0, 0, 0, null));
    addClockInput(ports, "clk", "CLK", "t0");
    addInput(ports, "t", "T", "t0");
    addInput(ports, "rst", "RST", "t0");
    addOutput(ports, "q", "Q", "t0");
    addOutput(ports, "nq", "nQ", "t0");
    nets.add(net("t_in", e("t", 0), e("t0", 0)));
    nets.add(net("t_clk", e("clk", 0), e("t0", 1)));
    nets.add(net("t_rst", e("rst", 0), e("t0", 4)));
    nets.add(net("t_q", e("t0", 2), e("q", 0)));
    nets.add(net("t_nq", e("t0", 3), e("nq", 0)));
    return new CircuitSpec("tff", components, ports, nets, hints);
  }

  private static CircuitSpec buildFlipFlop(String factoryName, String specName, int numInputs) {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    components.add(new ComponentSpec("ff0", "Memory", factoryName, 1, "U1"));
    hints.add(new LayoutHint("ff0", 0, 0, 0, null));
    addClockInput(ports, "clk", "CLK", "ff0");
    addInput(ports, "d", "D", "ff0");
    addInput(ports, "rst", "RST", "ff0");
    addOutput(ports, "q", "Q", "ff0");
    addOutput(ports, "nq", "nQ", "ff0");
    nets.add(net("ff_d", e("d", 0), e("ff0", 0)));
    nets.add(net("ff_clk", e("clk", 0), e("ff0", 1)));
    nets.add(net("ff_rst", e("rst", 0), e("ff0", numInputs + 3)));
    nets.add(net("ff_q", e("ff0", numInputs + 1), e("q", 0)));
    nets.add(net("ff_nq", e("ff0", numInputs + 2), e("nq", 0)));
    return new CircuitSpec(specName, components, ports, nets, hints);
  }

  /**
   * N-to-1 multiplexer: select bus S0..S(M-1) (M = log2 N) decodes to one-hot
   * lines; Y = OR over i of (D_i and sel_i).
   */
  static CircuitSpec buildMux(int n) {
    if (n < 2 || n > 256 || (n & (n - 1)) != 0) {
      throw new IllegalArgumentException("MUX width must be a power of two in 2..256, got " + n);
    }
    int m = Integer.numberOfTrailingZeros(n);
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      addInput(ports, "d" + i, "D" + i, "mux" + i + "_da");
    }
    for (int j = 0; j < m; j++) {
      addInput(ports, "s" + j, "S" + j, "mux0_da");
    }
    addOutput(ports, "y", "Y", "mux_or" + (n - 2));

    for (int i = 0; i < n; i++) {
      String g = "mux" + i + "_";
      String chip = "MUX" + i;
      // data gate: D_i and sel_i
      components.add(new ComponentSpec(g + "da", "Gates", "AND Gate", 1, chip + "D"));
      hints.add(new LayoutHint(g + "da", 1, 0, i, null));
      // select decode as a 2-input AND cascade: sel_i = AND over literals
      String acc = null;
      for (int j = 0; j < m; j++) {
        boolean one = ((i >> j) & 1) == 1;
        String src = one ? "s" + j : "sel_not" + j;
        if (acc == null) {
          acc = src;
        } else {
          String gateId = g + "se" + j;
          components.add(new ComponentSpec(gateId, "Gates", "AND Gate", 1, chip + "S" + j));
          hints.add(new LayoutHint(gateId, 0, 0, i, null));
          nets.add(net("selx" + i + "_" + j, e(acc, 0), e(gateId, 1)));
          nets.add(net("sely" + i + "_" + j, e(src, 0), e(gateId, 2)));
          acc = gateId;
        }
      }
      if (acc != null) {
        nets.add(net("selo" + i, e(acc, 0), e(g + "da", 2)));
      }
    }
    for (int k = 0; k < n - 1; k++) {
      components.add(new ComponentSpec("mux_or" + k, "Gates", "OR Gate", 1, "MUXO" + (k + 1)));
      hints.add(new LayoutHint("mux_or" + k, 2 + k, 0, 0, null));
    }
    // not-S inverters shared per select bit (one per select line, fan-out to decoders)
    for (int j = 0; j < m; j++) {
      components.add(new ComponentSpec("sel_not" + j, "Gates", "NOT Gate", 1, "MUXN" + (j + 1)));
      hints.add(new LayoutHint("sel_not" + j, 0, 0, n + j, null));
      nets.add(net("sn" + j, e("s" + j, 0), e("sel_not" + j, 1)));
    }

    for (int i = 0; i < n; i++) {
      String g = "mux" + i + "_";
      nets.add(net("d" + i, e("d" + i, 0), e(g + "da", 1)));
      // OR chain: or0 = da0 | da1 ; or_k = or_{k-1} | da_{k+1}
      nets.add(net("do" + i, e(g + "da", 0),
          e(i == 0 ? "mux_or0" : "mux_or" + (i - 1), i == 0 ? 1 : 2)));
    }
    for (int k = 0; k < n - 1; k++) {
      nets.add(net("or" + k, e("mux_or" + k, 0),
          e(k + 1 == n - 1 ? "y" : "mux_or" + (k + 1), k + 1 == n - 1 ? 0 : 1)));
    }

    return new CircuitSpec("mux_" + n + "to1", components, ports, nets, hints);
  }

  /**
   * 1-to-N demultiplexer (多路分配器): a shared DATA input is routed to
   * exactly the output selected by the log2(N) select bits. Each Y_i =
   * DATA AND sel_i, where sel_i is the one-hot decode of the select. Same
   * decode cascade as {@link #buildDecoder}. Combinational. 2-input gates
   * end0=out, end1/end2=in; NOT end0=out, end1=in.
   */
  static CircuitSpec buildDemux(int n) {
    if (n < 2 || n > 256 || (n & (n - 1)) != 0) {
      throw new IllegalArgumentException("Demultiplexer width must be a power of two in 2..256, got " + n);
    }
    int m = Integer.numberOfTrailingZeros(n);
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    addInput(ports, "data", "DATA", "dm_g0");
    for (int j = 0; j < m; j++) {
      addInput(ports, "s" + j, "S" + j, "dm_n" + j);
      String not = "dm_n" + j;
      components.add(new ComponentSpec(not, "Gates", "NOT Gate", 1, "DN" + (j + 1)));
      hints.add(new LayoutHint(not, 0, 0, j, null));
      nets.add(net("dm_n" + j + "_in", e("s" + j, 0), e(not, 1)));
    }
    for (int i = 0; i < n; i++) {
      addOutput(ports, "y" + i, "Y" + i, "dm_g" + i);
      String acc = null;
      for (int j = 0; j < m; j++) {
        boolean one = ((i >> j) & 1) == 1;
        String src = one ? "s" + j : "dm_n" + j;
        if (acc == null) {
          acc = src;
        } else {
          String gateId = "dm_" + i + "_" + j;
          components.add(new ComponentSpec(gateId, "Gates", "AND Gate", 1, "D" + i + "_" + j));
          hints.add(new LayoutHint(gateId, 1, 0, i, null));
          nets.add(net("dmd" + i + "_" + j + "x", e(acc, 0), e(gateId, 1)));
          nets.add(net("dmd" + i + "_" + j + "y", e(src, 0), e(gateId, 2)));
          acc = gateId;
        }
      }
      String g = "dm_g" + i;
      components.add(new ComponentSpec(g, "Gates", "AND Gate", 1, "G" + (i + 1)));
      hints.add(new LayoutHint(g, 2, 0, i, null));
      nets.add(net("dm_g" + i + "_data", e("data", 0), e(g, 1)));
      nets.add(net("dm_g" + i + "_sel", e(acc, 0), e(g, 2)));
      nets.add(net("dm_y" + i, e(g, 0), e("y" + i, 0)));
    }
    return new CircuitSpec("demux_" + n + "to1", components, ports, nets, hints);
  }

  /**
   * N-bit binary decoder: 2^N one-hot outputs, each Y_i = AND of literals.
   */
  static CircuitSpec buildDecoder(int bits) {
    if (bits < 1 || bits > 8) {
      throw new IllegalArgumentException("Decoder bits must be 1..8, got " + bits);
    }
    int n = 1 << bits;
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int j = 0; j < bits; j++) {
      addInput(ports, "a" + j, "A" + j, bits == 1 ? "dec_not0" : "dec0_a1");
      components.add(new ComponentSpec("dec_not" + j, "Gates", "NOT Gate", 1, "DECN" + (j + 1)));
      hints.add(new LayoutHint("dec_not" + j, 0, 0, j, null));
      nets.add(net("an" + j, e("a" + j, 0), e("dec_not" + j, 1)));
    }
    if (bits == 1) {
      addOutput(ports, "y0", "Y0", "dec_not0");
      addOutput(ports, "y1", "Y1", "dec_not0");
      nets.add(net("y0a", e("a0", 0), e("y0", 0)));
      nets.add(net("y1a", e("dec_not0", 0), e("y1", 0)));
      return new CircuitSpec("decoder_1bit", components, ports, nets, hints);
    }
    for (int i = 0; i < n; i++) {
      addOutput(ports, "y" + i, "Y" + i, "dec" + i + "_a" + (bits - 1));
      // cascade of 2-input AND gates: dec_i = AND(AND(...AND(lit0, lit1), lit2), ...)
      String acc = null;
      for (int j = 0; j < bits; j++) {
        boolean one = ((i >> j) & 1) == 1;
        String src = one ? "a" + j : "dec_not" + j;
        if (acc == null) {
          acc = src;
        } else {
          String gateId = "dec" + i + "_a" + j;
          components.add(new ComponentSpec(gateId, "Gates", "AND Gate", 1, "DEC" + i + "_" + j));
          hints.add(new LayoutHint(gateId, 1, 0, i, null));
          nets.add(net("decd" + i + "_" + j + "x", e(acc, 0), e(gateId, 1)));
          nets.add(net("decd" + i + "_" + j + "y", e(src, 0), e(gateId, 2)));
          acc = gateId;
        }
      }
      nets.add(net("deco" + i, e(acc, 0), e("y" + i, 0)));
    }

    return new CircuitSpec("decoder_" + bits + "bit", components, ports, nets, hints);
  }

  private static Integer chineseToInt(String s) {
    if (s.matches("\\d+")) {
      return Integer.parseInt(s);
    }
    return switch (s) {
      case "一" -> 1;
      case "二", "两" -> 2;
      case "三" -> 3;
      case "四" -> 4;
      case "五" -> 5;
      case "六" -> 6;
      case "七" -> 7;
      case "八" -> 8;
      case "九" -> 9;
      case "十" -> 10;
      case "十一" -> 11;
      case "十二" -> 12;
      case "十三" -> 13;
      case "十四" -> 14;
      case "十五" -> 15;
      case "十六" -> 16;
      default -> null;
    };
  }

  /**
   * N-bit ripple-carry adder. Bit slice i uses 7 gates:
   * s = a xor b xor c ; c' = (a and b) or (a and c) or (b and c).
   * Inputs A0..A(N-1), B0..B(N-1), Cin; outputs S0..S(N-1), Cout.
   */
  static CircuitSpec buildAdder(int bits) {
    if (bits < 1 || bits > 16) {
      throw new IllegalArgumentException("Adder width must be 1..16, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < bits; i++) {
      String px = "a" + i;
      String py = "b" + i;
      String ps = "s" + i;
      addInput(ports, px, "A" + i, "fa" + i + "_xor1");
      addInput(ports, py, "B" + i, "fa" + i + "_xor1");
      addOutput(ports, ps, "S" + i, "fa" + i + "_xor2");
      nets.add(net("na" + i, e(px, 0), e("fa" + i + "_xor1", 1), e("fa" + i + "_and2", 1), e("fa" + i + "_and1", 2)));
      nets.add(net("nb" + i, e(py, 0), e("fa" + i + "_xor1", 2), e("fa" + i + "_and1", 1), e("fa" + i + "_and3", 2)));
    }
    addInput(ports, "cin", "Cin", "fa0_xor2");
    addOutput(ports, "cout", "Cout", "fa" + (bits - 1) + "_or2");

    for (int i = 0; i < bits; i++) {
      String g = "fa" + i + "_";
      String chip = "FA" + i;
      components.add(new ComponentSpec(g + "xor1", "Gates", "XOR Gate", 1, chip + "X1"));
      components.add(new ComponentSpec(g + "xor2", "Gates", "XOR Gate", 1, chip + "X2"));
      components.add(new ComponentSpec(g + "and1", "Gates", "AND Gate", 1, chip + "G1"));
      components.add(new ComponentSpec(g + "and2", "Gates", "AND Gate", 1, chip + "G2"));
      components.add(new ComponentSpec(g + "and3", "Gates", "AND Gate", 1, chip + "G3"));
      components.add(new ComponentSpec(g + "or1", "Gates", "OR Gate", 1, chip + "O1"));
      components.add(new ComponentSpec(g + "or2", "Gates", "OR Gate", 1, chip + "O2"));
      // one column per slice; gates stack by role (rows 0..6)
      for (int r = 0; r < 7; r++) {
        String[] roles = {"xor1", "xor2", "and1", "and2", "and3", "or1", "or2"};
        hints.add(new LayoutHint(g + roles[r], i, 0, r, null));
      }
    }

    String carryIn = "cin";
    for (int i = 0; i < bits; i++) {
      String g = "fa" + i + "_";
      // carry in -> xor2 input2 and and2 input2 (cin) / and3 input1 (carry)
      nets.add(net("ci" + i, e(carryIn, 0), e(g + "xor2", 2), e(g + "and2", 2), e(g + "and3", 1)));
      // a xor b -> xor2 input1
      nets.add(net("x" + i, e(g + "xor1", 0), e(g + "xor2", 1)));
      // sum = a xor b xor cin
      nets.add(net("s" + i, e(g + "xor2", 0), e("s" + i, 0)));
      // carry generate/propagate
      nets.add(net("ab" + i, e(g + "and1", 0), e(g + "or1", 1)));
      nets.add(net("ac" + i, e(g + "and2", 0), e(g + "or1", 2)));
      nets.add(net("bc" + i, e(g + "and3", 0), e(g + "or2", 2)));
      nets.add(net("c1" + i, e(g + "or1", 0), e(g + "or2", 1)));
      carryIn = g + "or2";
    }
    nets.add(net("cout", e(carryIn, 0), e("cout", 0)));

    return new CircuitSpec("adder_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-bit unsigned saturating adder (饱和加法器): S = min(A + B, 2^N - 1).
   * A ripple adder computes A+B with carry-out c; every output bit is
   * S_i = sum_i OR c, so an overflow saturates the result to all ones.
   * All gates 2-input; a GND pin supplies the constant-0 carry-in.
   */
  static CircuitSpec buildSaturatingAdder(int bits) {
    if (bits < 2 || bits > 8) {
      throw new IllegalArgumentException("Saturating adder width must be 2..8, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    int seq = 0;

    for (int i = 0; i < bits; i++) {
      addInput(ports, "a" + i, "A" + i, "sa_x0");
      addInput(ports, "b" + i, "B" + i, "sa_x0");
      addOutput(ports, "s" + i, "S" + i, "sa_x0");
    }
    addInput(ports, "gnd", "GND", "sa_x0");

    // Ripple adder A + B (carry-in 0), 6-gate full-adder + fg OR per bit.
    String[] sum = new String[bits];
    String c = "gnd";
    for (int i = 0; i < bits; i++) {
      String x = "sa_x" + seq;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "SAX" + (seq + 1)));
      hints.add(new LayoutHint(x, 0, 0, i, null));
      String s = "sa_s" + seq;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "SAS" + (seq + 1)));
      hints.add(new LayoutHint(s, 0, 0, i, null));
      String fa = "sa_fa" + seq;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "SAA" + (seq + 1)));
      hints.add(new LayoutHint(fa, 0, 0, i, null));
      String fb = "sa_fb" + seq;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "SAB" + (seq + 1)));
      hints.add(new LayoutHint(fb, 0, 0, i, null));
      String fo = "sa_fo" + seq;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "SAO" + (seq + 1)));
      hints.add(new LayoutHint(fo, 0, 0, i, null));
      String fg = "sa_fg" + seq;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "SAG" + (seq + 1)));
      hints.add(new LayoutHint(fg, 0, 0, i, null));
      seq++;
      nets.add(net("sa_xa" + seq, e("a" + i, 0), e(x, 1)));
      nets.add(net("sa_xb" + seq, e("b" + i, 0), e(x, 2)));
      nets.add(net("sa_sa" + seq, e(x, 0), e(s, 1)));
      nets.add(net("sa_sb" + seq, e(c, 0), e(s, 2)));
      sum[i] = s;
      nets.add(net("sa_faa" + seq, e("a" + i, 0), e(fa, 1)));
      nets.add(net("sa_fab" + seq, e("b" + i, 0), e(fa, 2)));
      nets.add(net("sa_fba" + seq, e(c, 0), e(fb, 1)));
      nets.add(net("sa_fbb" + seq, e(x, 0), e(fb, 2)));
      nets.add(net("sa_fo1" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net("sa_fo2" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net("sa_fg1" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net("sa_fg2" + seq, e("gnd", 0), e(fg, 2)));
      c = fg;
    }
    String carry = c;

    // Saturate: S_i = sum_i OR carry.
    for (int i = 0; i < bits; i++) {
      String o = "sa_o" + seq;
      components.add(new ComponentSpec(o, "Gates", "OR Gate", 1, "SAU" + (seq + 1)));
      hints.add(new LayoutHint(o, 1, 0, i, null));
      nets.add(net("sa_o1" + seq, e(sum[i], 0), e(o, 1)));
      nets.add(net("sa_o2" + seq, e(carry, 0), e(o, 2)));
      nets.add(net("sa_sout" + i, e(o, 0), e("s" + i, 0)));
      seq++;
    }

    return new CircuitSpec("satadd_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-bit magnitude comparator with outputs GT (A>B), EQ (A==B), LT (A<B).
   * Bit slice i: e_i = XNOR(A_i,B_i), g_i = A_i and not B_i.
   * GT ripple: gt = g(N-1) | e(N-1)&g(N-2) | ... ; EQ = AND of all e_i;
   * LT = not (GT or EQ).
   */
  static CircuitSpec buildComparator(int bits) {
    if (bits < 1 || bits > 16) {
      throw new IllegalArgumentException("Comparator width must be 1..16, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < bits; i++) {
      addInput(ports, "a" + i, "A" + i, "cmp" + i + "_xn");
      addInput(ports, "b" + i, "B" + i, "cmp" + i + "_xn");
    }
    addOutput(ports, "gt", "GT", "cmp" + (bits - 1) + "_ga");
    addOutput(ports, "eq", "EQ", bits == 1 ? "cmp0_xn" : "eqc_" + (bits - 1));
    addOutput(ports, "lt", "LT", "lt_not");

    for (int i = 0; i < bits; i++) {
      String g = "cmp" + i + "_";
      String chip = "CMP" + i;
      components.add(new ComponentSpec(g + "xn", "Gates", "XNOR Gate", 1, chip + "E"));
      components.add(new ComponentSpec(g + "nb", "Gates", "NOT Gate", 1, chip + "N"));
      components.add(new ComponentSpec(g + "ga", "Gates", "AND Gate", 1, chip + "G"));
      hints.add(new LayoutHint(g + "xn", i, 0, 0, null));
      hints.add(new LayoutHint(g + "nb", i, 0, 1, null));
      hints.add(new LayoutHint(g + "ga", i, 0, 2, null));
    }
    components.add(new ComponentSpec("lt_or", "Gates", "OR Gate", 1, "CMPO"));
    components.add(new ComponentSpec("lt_not", "Gates", "NOT Gate", 1, "CMPL"));
    hints.add(new LayoutHint("lt_or", bits, 0, 0, null));
    hints.add(new LayoutHint("lt_not", bits, 0, 1, null));

    for (int i = 0; i < bits; i++) {
      String g = "cmp" + i + "_";
      nets.add(net("nb" + i, e("b" + i, 0), e(g + "nb", 1)));
      nets.add(net("g" + i, e(g + "nb", 0), e(g + "ga", 2)));
      nets.add(net("ag" + i, e("a" + i, 0), e(g + "ga", 1)));
      nets.add(net("a" + i, e("a" + i, 0), e(g + "xn", 1)));
      nets.add(net("b" + i, e("b" + i, 0), e(g + "xn", 2)));
    }

    // GT ripple from MSB down:
    //   gt_acc = g(N-1); e_acc = e(N-1)
    //   gt_acc = gt_acc | (e_acc & g(i)); e_acc = e_acc & e(i)
    // so the highest differing bit decides, high bits are never dropped.
    for (int i = 0; i < bits - 1; i++) {
      components.add(new ComponentSpec("gta_" + i, "Gates", "AND Gate", 1, "CMPA" + (i + 1)));
      components.add(new ComponentSpec("gtc_" + i, "Gates", "OR Gate", 1, "CMPG" + (i + 1)));
      components.add(new ComponentSpec("gte_" + i, "Gates", "AND Gate", 1, "CMPE" + (i + 1)));
      hints.add(new LayoutHint("gta_" + i, bits + 1 + i, 0, 0, null));
      hints.add(new LayoutHint("gtc_" + i, bits + 1 + i, 0, 1, null));
      hints.add(new LayoutHint("gte_" + i, bits + 1 + i, 0, 2, null));
    }
    String gtAcc = "cmp" + (bits - 1) + "_ga";
    String eAcc = "cmp" + (bits - 1) + "_xn";
    for (int i = bits - 2; i >= 0; i--) {
      nets.add(net("gta" + i, e(eAcc, 0), e("gta_" + i, 1)));
      nets.add(net("gtb" + i, e("cmp" + i + "_ga", 0), e("gta_" + i, 2)));
      nets.add(net("gtc" + i, e(gtAcc, 0), e("gtc_" + i, 1)));
      nets.add(net("gtd" + i, e("gta_" + i, 0), e("gtc_" + i, 2)));
      nets.add(net("gte" + i, e(eAcc, 0), e("gte_" + i, 1)));
      nets.add(net("gtf" + i, e("cmp" + i + "_xn", 0), e("gte_" + i, 2)));
      gtAcc = "gtc_" + i;
      eAcc = "gte_" + i;
    }
    nets.add(net("gtout", e(gtAcc, 0), e("gt", 0)));

    // EQ = AND of all e_i (two-input chain, one AND per step)
    for (int i = 0; i < bits; i++) {
      components.add(new ComponentSpec("eqc_" + i, "Gates", "AND Gate", 1, "CMPQ" + (i + 1)));
      hints.add(new LayoutHint("eqc_" + i, bits + 1, 0, 2 + i, null));
    }
    String eqAcc = "cmp0_xn";
    for (int i = 1; i < bits; i++) {
      nets.add(net("eqa_" + i, e(eqAcc, 0), e("eqc_" + i, 1)));
      nets.add(net("eqb_" + i, e("cmp" + i + "_xn", 0), e("eqc_" + i, 2)));
      eqAcc = "eqc_" + i;
    }
    nets.add(net("eqout", e(eqAcc, 0), e("eq", 0)));

    // LT = not (GT or EQ); feed gate outputs directly, never route through an
    // output Pin (an output Pin as a net source is not a supported fan-out).
    nets.add(net("lta", e(gtAcc, 0), e("lt_or", 1)));
    nets.add(net("ltb", e(eqAcc, 0), e("lt_or", 2)));
    nets.add(net("ltc", e("lt_or", 0), e("lt_not", 1)));
    nets.add(net("ltout", e("lt_not", 0), e("lt", 0)));

    return new CircuitSpec("comparator_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * BCD (0-9) to 7-segment decoder. Segment truth tables (standard a-g):
   * a=0,2,3,5,6,7,8,9; b=0,1,2,3,4,7,8,9; c=0,1,3,4,5,6,7,8,9;
   * d=0,2,3,5,6,8,9; e=0,2,6,8; f=0,4,5,6,8,9; g=2,3,4,5,6,8,9.
   * Each segment = OR of minterm products (2-input AND cascades + OR chain).
   */
  static CircuitSpec buildBcd7Seg() {
    int[][] segs = {
      {0, 2, 3, 5, 6, 7, 8, 9},
      {0, 1, 2, 3, 4, 7, 8, 9},
      {0, 1, 3, 4, 5, 6, 7, 8, 9},
      {0, 2, 3, 5, 6, 8, 9},
      {0, 2, 6, 8},
      {0, 4, 5, 6, 8, 9},
      {2, 3, 4, 5, 6, 8, 9},
    };
    return emitSeg7Display("bcd7seg", "seg7", segs);
  }

  /**
   * Hex 7-segment display (十六进制转七段): shows 0-9 then A-F. Same OR-of-
   * minterms structure as {@link #buildBcd7Seg} with the hex segment table.
   */
  static CircuitSpec buildHex7Seg() {
    int[][] segs = {
      {0, 2, 3, 5, 6, 7, 8, 9, 10, 12, 14, 15},
      {0, 1, 2, 3, 4, 7, 8, 9, 10, 13},
      {0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13},
      {0, 2, 3, 5, 6, 8, 9, 10, 11, 12, 13, 14},
      {0, 2, 6, 8, 10, 11, 12, 13, 14, 15},
      {0, 4, 5, 6, 8, 9, 10, 11, 12, 14, 15},
      {2, 3, 4, 5, 6, 8, 9, 10, 11, 13, 14, 15},
    };
    return emitSeg7Display("hex7seg", "hex7", segs);
  }

  /** Shared 4-input → 7-segment display builder (OR of minterm products). */
  private static CircuitSpec emitSeg7Display(String specName, String prefix, int[][] segs) {
    String[] segNames = {"a", "b", "c", "d", "e", "f", "g"};

    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < 4; i++) {
      addInput(ports, "b" + i, "B" + i, prefix + "_not" + i);
      components.add(new ComponentSpec(prefix + "_not" + i, "Gates", "NOT Gate", 1, "B7N" + (i + 1)));
      hints.add(new LayoutHint(prefix + "_not" + i, 0, 0, i, null));
      nets.add(net(prefix + "_nb" + i, e("b" + i, 0), e(prefix + "_not" + i, 1)));
    }
    for (int s = 0; s < 7; s++) {
      addOutput(ports, "seg" + s, segNames[s], prefix + "_not0");
    }

    int seq = 0;
    for (int s = 0; s < 7; s++) {
      String orAcc = null;
      for (int m = 0; m < segs[s].length; m++) {
        int minterm = segs[s][m];
        String andAcc = null;
        for (int bit = 0; bit < 4; bit++) {
          boolean one = ((minterm >> bit) & 1) == 1;
          String src = one ? "b" + bit : prefix + "_not" + bit;
          if (andAcc == null) {
            andAcc = src;
          } else {
            String gate = prefix + "_p" + seq;
            components.add(new ComponentSpec(gate, "Gates", "AND Gate", 1, "B7P" + (seq + 1)));
            hints.add(new LayoutHint(gate, 1 + bit, 0, s, null));
            nets.add(net(prefix + "_pa" + seq, e(andAcc, 0), e(gate, 1)));
            nets.add(net(prefix + "_pb" + seq, e(src, 0), e(gate, 2)));
            andAcc = gate;
            seq++;
          }
        }
        if (orAcc == null) {
          orAcc = andAcc;
        } else {
          String gate = prefix + "_o" + seq;
          components.add(new ComponentSpec(gate, "Gates", "OR Gate", 1, "B7O" + (seq + 1)));
          hints.add(new LayoutHint(gate, 6, 0, s, null));
          nets.add(net(prefix + "_oa" + seq, e(orAcc, 0), e(gate, 1)));
          nets.add(net(prefix + "_ob" + seq, e(andAcc, 0), e(gate, 2)));
          orAcc = gate;
          seq++;
        }
      }
      nets.add(net(prefix + "_out" + s, e(orAcc, 0), e("seg" + s, 0)));
    }

    return new CircuitSpec(specName, components, ports, nets, hints);
  }

  /**
   * N-bit squarer (平方器): Y = A*A (2N-bit result). Partial products
   * pp[i][j] = A_i AND A_j (symmetric, both orders emitted) are accumulated
   * with the same carry-save ripple stages as the multiplier (each stage adds
   * pp[i] shifted by i, outBits = accBits + 2). All gates 2-input.
   */
  static CircuitSpec buildSquarer(int bits) {
    if (bits < 2 || bits > 4) {
      throw new IllegalArgumentException("Squarer width must be 2..4, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    int seq = 0;

    for (int i = 0; i < bits; i++) {
      addInput(ports, "a" + i, "A" + i, "sq_p0");
    }
    addInput(ports, "gnd", "GND", "sq_p0");
    for (int k = 0; k < 2 * bits; k++) {
      addOutput(ports, "p" + k, "P" + k, "sq_p0");
    }

    // partial products pp[i][j] = A_j AND A_i (symmetric, duplicated).
    String[][] pp = new String[bits][bits];
    for (int i = 0; i < bits; i++) {
      for (int j = 0; j < bits; j++) {
        String g = "sq_p" + seq;
        components.add(new ComponentSpec(g, "Gates", "AND Gate", 1, "SQP" + (seq + 1)));
        hints.add(new LayoutHint(g, 0, 0, i, null));
        nets.add(net("sq_pa" + seq, e("a" + j, 0), e(g, 1)));
        nets.add(net("sq_pb" + seq, e("a" + i, 0), e(g, 2)));
        pp[i][j] = g;
        seq++;
      }
    }

    // accumulate: acc = pp[0]; stage i adds (pp[i] << i).
    String[] acc = new String[bits];
    for (int k = 0; k < bits; k++) {
      acc[k] = pp[0][k];
    }
    for (int i = 1; i < bits; i++) {
      int accBits = acc.length;
      int outBits = accBits + 2;
      String[] nacc = new String[outBits];
      String cin = null;
      for (int k = 0; k < outBits; k++) {
        if (k < i) {
          nacc[k] = acc[k];
          continue;
        }
        String a = k < accBits ? acc[k] : "gnd";
        int pj = k - i;
        String b = (pj >= 0 && pj < bits) ? pp[i][pj] : "gnd";
        String cinSig = cin == null ? "gnd" : cin;
        String x = "sq_x" + seq;
        components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "SQX" + (seq + 1)));
        hints.add(new LayoutHint(x, 1 + i, 0, k, null));
        String s = "sq_s" + seq;
        components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "SQS" + (seq + 1)));
        hints.add(new LayoutHint(s, 1 + i, 0, k, null));
        String fa = "sq_f" + seq;
        components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "SQA" + (seq + 1)));
        hints.add(new LayoutHint(fa, 1 + i, 0, k, null));
        String fb = "sq_b" + seq;
        components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "SQB" + (seq + 1)));
        hints.add(new LayoutHint(fb, 1 + i, 0, k, null));
        String fo = "sq_o" + seq;
        components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "SQO" + (seq + 1)));
        hints.add(new LayoutHint(fo, 1 + i, 0, k, null));
        String fg = "sq_g" + seq;
        components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "SQG" + (seq + 1)));
        hints.add(new LayoutHint(fg, 1 + i, 0, k, null));
        seq++;
        nets.add(net("sq_xa" + seq, e(a, 0), e(x, 1)));
        nets.add(net("sq_xb" + seq, e(b, 0), e(x, 2)));
        nets.add(net("sq_sa" + seq, e(x, 0), e(s, 1)));
        nets.add(net("sq_sb" + seq, e(cinSig, 0), e(s, 2)));
        nacc[k] = s;
        nets.add(net("sq_fa" + seq, e(a, 0), e(fa, 1)));
        nets.add(net("sq_fb" + seq, e(b, 0), e(fa, 2)));
        nets.add(net("sq_ga" + seq, e(cinSig, 0), e(fb, 1)));
        nets.add(net("sq_gb" + seq, e(x, 0), e(fb, 2)));
        nets.add(net("sq_o1" + seq, e(fa, 0), e(fo, 1)));
        nets.add(net("sq_o2" + seq, e(fb, 0), e(fo, 2)));
        nets.add(net("sq_g1" + seq, e(fo, 0), e(fg, 1)));
        nets.add(net("sq_g2" + seq, e("gnd", 0), e(fg, 2)));
        cin = fg;
      }
      acc = new String[outBits];
      for (int k = 0; k < outBits; k++) {
        acc[k] = nacc[k];
      }
    }

    for (int k = 0; k < 2 * bits; k++) {
      nets.add(net("sq_out" + k, e(acc[k], 0), e("p" + k, 0)));
    }

    return new CircuitSpec("squarer_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N x N unsigned array multiplier (shift-add): partial products p[i][j] =
   * A[j] and B[i], accumulated by N-1 ripple-add stages with the i-th product
   * shifted left by i. Output width 2N. Missing operand bits and the first
   * carry-in tie to a shared GND input pin (constant 0).
   */
  static CircuitSpec buildMultiplier(int bits) {
    if (bits < 2 || bits > 8) {
      throw new IllegalArgumentException("Multiplier width must be 2..8, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    int seq = 0;

    for (int j = 0; j < bits; j++) {
      addInput(ports, "a" + j, "A" + j, "mul_p0");
    }
    for (int i = 0; i < bits; i++) {
      addInput(ports, "b" + i, "B" + i, "mul_p0");
    }
    addInput(ports, "gnd", "GND", "mul_p0");
    for (int k = 0; k < 2 * bits; k++) {
      addOutput(ports, "p" + k, "P" + k, "mul_p0");
    }

    // partial products p[i][j] = A[j] and B[i]
    String[][] pp = new String[bits][bits];
    for (int i = 0; i < bits; i++) {
      for (int j = 0; j < bits; j++) {
        String g = "mul_p" + seq;
        components.add(new ComponentSpec(g, "Gates", "AND Gate", 1, "MULP" + (seq + 1)));
        hints.add(new LayoutHint(g, 0, 0, i, null));
        nets.add(net("mul_pa" + seq, e("a" + j, 0), e(g, 1)));
        nets.add(net("mul_pb" + seq, e("b" + i, 0), e(g, 2)));
        pp[i][j] = g;
        seq++;
      }
    }

    // accumulate: acc = pp[0]; stage i adds (pp[i] << i)
    String[] acc = new String[bits];
    for (int k = 0; k < bits; k++) {
      acc[k] = pp[0][k];
    }
    for (int i = 1; i < bits; i++) {
      int accBits = acc.length;
      int outBits = accBits + 2;
      String[] nacc = new String[outBits];
      String cin = null;
      for (int k = 0; k < outBits; k++) {
        if (k < i) {
          nacc[k] = acc[k];
          continue;
        }
        String a = k < accBits ? acc[k] : "gnd";
        int pj = k - i;
        String b = (pj >= 0 && pj < bits) ? pp[i][pj] : "gnd";
        String cinSig = cin == null ? "gnd" : cin;
        // full adder
        String x = "mul_x" + seq;
        components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "MULX" + (seq + 1)));
        hints.add(new LayoutHint(x, 1 + i, 0, k, null));
        String s = "mul_s" + seq;
        components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "MULS" + (seq + 1)));
        hints.add(new LayoutHint(s, 1 + i, 0, k, null));
        String fa = "mul_f" + seq;
        components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "MULA" + (seq + 1)));
        hints.add(new LayoutHint(fa, 1 + i, 0, k, null));
        String fb = "mul_b" + seq;
        components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "MULB" + (seq + 1)));
        hints.add(new LayoutHint(fb, 1 + i, 0, k, null));
        String fo = "mul_o" + seq;
        components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "MULO" + (seq + 1)));
        hints.add(new LayoutHint(fo, 1 + i, 0, k, null));
        String fg = "mul_g" + seq;
        components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "MULG" + (seq + 1)));
        hints.add(new LayoutHint(fg, 1 + i, 0, k, null));
        seq++;
        // x = a xor b ; s = x xor cin
        nets.add(net("mul_xa" + seq, e(a, 0), e(x, 1)));
        nets.add(net("mul_xb" + seq, e(b, 0), e(x, 2)));
        nets.add(net("mul_sa" + seq, e(x, 0), e(s, 1)));
        nets.add(net("mul_sb" + seq, e(cinSig, 0), e(s, 2)));
        nacc[k] = s;
        // carry = (a and b) or (cin and x)
        nets.add(net("mul_fa" + seq, e(a, 0), e(fa, 1)));
        nets.add(net("mul_fb" + seq, e(b, 0), e(fa, 2)));
        nets.add(net("mul_ga" + seq, e(cinSig, 0), e(fb, 1)));
        nets.add(net("mul_gb" + seq, e(x, 0), e(fb, 2)));
        nets.add(net("mul_o1" + seq, e(fa, 0), e(fo, 1)));
        nets.add(net("mul_o2" + seq, e(fb, 0), e(fo, 2)));
        // fg = fo or gnd: carry-out is fully captured by fo (fa|fb =
        // (a∧b)|(cin∧x)); the second input is a constant-0 so the OR gate has
        // >=2 inputs for the in-process evaluator.
        nets.add(net("mul_g1" + seq, e(fo, 0), e(fg, 1)));
        nets.add(net("mul_g2" + seq, e("gnd", 0), e(fg, 2)));
        cin = fg;
      }
      acc = new String[outBits];
      for (int k = 0; k < outBits; k++) {
        acc[k] = nacc[k];
      }
    }

    for (int k = 0; k < 2 * bits; k++) {
      nets.add(net("mul_out" + k, e(acc[k], 0), e("p" + k, 0)));
    }

    return new CircuitSpec("multiplier_" + bits + "x" + bits, components, ports, nets, hints);
  }
  private static String srcOr(String id, String fallback) {
    return id == null ? fallback : id;
  }

  /**
   * N-bit ripple-borrow subtractor: D = A - B - Bin. Bit slice:
   * x = A xor B ; D = x xor Bin ;
   * Bout = (not A and (B or Bin)) or (B and Bin).
   * Inputs A0..A(N-1), B0..B(N-1), Bin; outputs D0..D(N-1), Bout.
   */
  static CircuitSpec buildSubtractor(int bits) {
    if (bits < 1 || bits > 16) {
      throw new IllegalArgumentException("Subtractor width must be 1..16, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < bits; i++) {
      addInput(ports, "a" + i, "A" + i, "sub" + i + "_x");
      addInput(ports, "b" + i, "B" + i, "sub" + i + "_x");
      addOutput(ports, "d" + i, "D" + i, "sub" + i + "_d");
    }
    addInput(ports, "bin", "Bin", "sub0_d");
    addOutput(ports, "bout", "Bout", "sub" + (bits - 1) + "_bo");

    for (int i = 0; i < bits; i++) {
      String g = "sub" + i + "_";
      String chip = "SUB" + i;
      components.add(new ComponentSpec(g + "x", "Gates", "XOR Gate", 1, chip + "X"));
      components.add(new ComponentSpec(g + "d", "Gates", "XOR Gate", 1, chip + "D"));
      components.add(new ComponentSpec(g + "na", "Gates", "NOT Gate", 1, chip + "N"));
      components.add(new ComponentSpec(g + "bo", "Gates", "OR Gate", 1, chip + "O"));
      components.add(new ComponentSpec(g + "b1", "Gates", "AND Gate", 1, chip + "A"));
      components.add(new ComponentSpec(g + "b2", "Gates", "AND Gate", 1, chip + "B"));
      components.add(new ComponentSpec(g + "b3", "Gates", "OR Gate", 1, chip + "C"));
      hints.add(new LayoutHint(g + "x", i, 0, 0, null));
      hints.add(new LayoutHint(g + "d", i, 0, 1, null));
      hints.add(new LayoutHint(g + "na", i, 0, 2, null));
      hints.add(new LayoutHint(g + "bo", i, 0, 3, null));
      hints.add(new LayoutHint(g + "b1", i, 0, 4, null));
      hints.add(new LayoutHint(g + "b2", i, 0, 5, null));
      hints.add(new LayoutHint(g + "b3", i, 0, 6, null));
      nets.add(net("ax" + i, e("a" + i, 0), e(g + "x", 1), e(g + "na", 1)));
      nets.add(net("bx" + i, e("b" + i, 0), e(g + "x", 2), e(g + "bo", 1), e(g + "b2", 1)));
    }

    String borrowIn = "bin";
    for (int i = 0; i < bits; i++) {
      String g = "sub" + i + "_";
      // D = x xor Bin ; x = a xor b
      nets.add(net("dx" + i, e(g + "x", 0), e(g + "d", 1)));
      nets.add(net("di" + i, e(borrowIn, 0), e(g + "d", 2)));
      nets.add(net("do" + i, e(g + "d", 0), e("d" + i, 0)));
      // Bout = (not a and (b or bin)) or (b and bin)
      nets.add(net("bob" + i, e(borrowIn, 0), e(g + "bo", 2)));
      nets.add(net("bo1" + i, e(g + "na", 0), e(g + "b1", 1)));
      nets.add(net("bo2" + i, e(g + "bo", 0), e(g + "b1", 2)));
      nets.add(net("bo4" + i, e(borrowIn, 0), e(g + "b2", 2)));
      nets.add(net("bo5" + i, e(g + "b1", 0), e(g + "b3", 1)));
      nets.add(net("bo6" + i, e(g + "b2", 0), e(g + "b3", 2)));
      borrowIn = g + "b3";
    }
    nets.add(net("bout", e(borrowIn, 0), e("bout", 0)));

    return new CircuitSpec("subtractor_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-bit ALU with a 2-bit function select S1S0:
   * 00 → R = A and B; 01 → R = A or B; 10 → R = A + B (ripple carry);
   * 11 → R = A - B (A plus two's complement of B, borrow folded into Cout).
   * Outputs R0..R(N-1), Cout, and Z (zero flag: R == 0).
   * Every gate is 2-input so the DirectMultiChipCompiler netlist stays valid.
   */
  static CircuitSpec buildAlu(int bits) {
    if (bits < 1 || bits > 8) {
      throw new IllegalArgumentException("ALU width must be 1..8, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < bits; i++) {
      addInput(ports, "a" + i, "A" + i, "alu" + i + "_an");
      addInput(ports, "b" + i, "B" + i, "alu" + i + "_an");
      addOutput(ports, "r" + i, "R" + i, "alu" + i + "_mo2");
    }
    addInput(ports, "s1", "S1", "alu0_an");
    addInput(ports, "s0", "S0", "alu0_an");
    addInput(ports, "cin", "Cin", "alu0_x1");
    addOutput(ports, "cout", "Cout", "alu" + (bits - 1) + "_o2");
    addOutput(ports, "z", "Z", bits == 1 ? "alu0_zo" : "alu_znot");

    int seq = 0;
    for (int i = 0; i < bits; i++) {
      String g = "alu" + i + "_";
      String chip = "ALU" + i;

      // Logic block: AND + OR.
      components.add(new ComponentSpec(g + "an", "Gates", "AND Gate", 1, chip + "A"));
      components.add(new ComponentSpec(g + "or", "Gates", "OR Gate", 1, chip + "O"));
      hints.add(new LayoutHint(g + "an", 0, 0, 0, null));
      hints.add(new LayoutHint(g + "or", 0, 0, 1, null));

      // Adder block (XOR pair + generate/propagate ORs): sum = A xor B xor Cin.
      components.add(new ComponentSpec(g + "x1", "Gates", "XOR Gate", 1, chip + "X1"));
      components.add(new ComponentSpec(g + "x2", "Gates", "XOR Gate", 1, chip + "X2"));
      components.add(new ComponentSpec(g + "g1", "Gates", "AND Gate", 1, chip + "G1"));
      components.add(new ComponentSpec(g + "g2", "Gates", "AND Gate", 1, chip + "G2"));
      components.add(new ComponentSpec(g + "g3", "Gates", "AND Gate", 1, chip + "G3"));
      components.add(new ComponentSpec(g + "o1", "Gates", "OR Gate", 1, chip + "C1"));
      components.add(new ComponentSpec(g + "o2", "Gates", "OR Gate", 1, chip + "C2"));
      hints.add(new LayoutHint(g + "x1", 1, 0, 0, null));
      hints.add(new LayoutHint(g + "x2", 1, 0, 1, null));
      hints.add(new LayoutHint(g + "g1", 1, 0, 2, null));
      hints.add(new LayoutHint(g + "g2", 1, 0, 3, null));
      hints.add(new LayoutHint(g + "g3", 1, 0, 4, null));
      hints.add(new LayoutHint(g + "o1", 1, 0, 5, null));
      hints.add(new LayoutHint(g + "o2", 1, 0, 6, null));

      // SUB support: S_sub = S1 and S0 (11 = SUB). B' = B xor S_sub, so B is
      // inverted only in SUB mode; the adder's first carry-in is S_sub too
      // (A - B = A + ~B + 1). ADD (10) keeps B and carries from Cin.
      components.add(new ComponentSpec(g + "ssub", "Gates", "AND Gate", 1, chip + "SS"));
      components.add(new ComponentSpec(g + "bs", "Gates", "XOR Gate", 1, chip + "BX"));
      hints.add(new LayoutHint(g + "ssub", 2, 0, 0, null));
      hints.add(new LayoutHint(g + "bs", 2, 0, 1, null));

      // Output mux: R = (and,notS1,notS0)|(or,notS1,S0)|(arith,S1).
      // ADD and SUB share one arithmetic lane (B' and carry already encode the
      // mode via S_sub), so only three lanes are needed. Every gate stays
      // 2-input (multi-input gate size is not auto-set by the compiler, and a
      // 3rd input would dangle in the official JAR): each select AND is two
      // stages, e.g. m1 = (an and notS1) and notS0.
      components.add(new ComponentSpec(g + "ns1", "Gates", "NOT Gate", 1, chip + "N1"));
      components.add(new ComponentSpec(g + "ns0", "Gates", "NOT Gate", 1, chip + "N2"));
      components.add(new ComponentSpec(g + "m1a", "Gates", "AND Gate", 1, chip + "M1A"));
      components.add(new ComponentSpec(g + "m2a", "Gates", "AND Gate", 1, chip + "M2A"));
      components.add(new ComponentSpec(g + "m1", "Gates", "AND Gate", 1, chip + "M1"));
      components.add(new ComponentSpec(g + "m2", "Gates", "AND Gate", 1, chip + "M2"));
      components.add(new ComponentSpec(g + "m3", "Gates", "AND Gate", 1, chip + "M3"));
      components.add(new ComponentSpec(g + "mo1", "Gates", "OR Gate", 1, chip + "MO1"));
      components.add(new ComponentSpec(g + "mo2", "Gates", "OR Gate", 1, chip + "MO2"));
      hints.add(new LayoutHint(g + "ns1", 3, 0, 0, null));
      hints.add(new LayoutHint(g + "ns0", 3, 0, 1, null));
      hints.add(new LayoutHint(g + "m1a", 4, 0, 0, null));
      hints.add(new LayoutHint(g + "m2a", 4, 0, 1, null));
      hints.add(new LayoutHint(g + "m1", 4, 0, 2, null));
      hints.add(new LayoutHint(g + "m2", 4, 0, 3, null));
      hints.add(new LayoutHint(g + "m3", 4, 0, 4, null));
      hints.add(new LayoutHint(g + "mo1", 5, 0, 0, null));
      hints.add(new LayoutHint(g + "mo2", 5, 0, 1, null));

      // Inputs fan out: A_i, B_i, S1, S0 shared nets.
      // Carry-in: first slice uses S_sub (SUB forces 1, ADD/LOGIC pass Cin via
      // the select below); later slices take the previous carry-out.
      String carryIn = (i == 0)
          ? "cin"
          : "alu" + (i - 1) + "_o2";
      nets.add(net("alu_in_a" + seq, e("a" + i, 0), e(g + "an", 1), e(g + "or", 2), e(g + "x1", 1), e(g + "g1", 1), e(g + "g2", 1)));
      nets.add(net("alu_in_b" + seq, e("b" + i, 0), e(g + "an", 2), e(g + "or", 1), e(g + "bs", 2)));
      seq++;
      nets.add(net("alu_s1" + seq, e("s1", 0), e(g + "ns1", 1), e(g + "ssub", 1)));
      nets.add(net("alu_s0" + seq, e("s0", 0), e(g + "ns0", 1), e(g + "ssub", 2)));
      seq++;

      // AND / OR results feed the mux lanes 1 and 2 via the two-stage selects
      // below (m1a = an and notS1; m2a = or and notS1).
      seq++;

      // Adder: sum_i = A xor B' xor carry, where B' = B xor S_sub.
      // SUB (S_sub=1): B' = not B, first carry = 1 -> A - B = A + not B + 1.
      // ADD (S_sub=0): B' = B, first carry = Cin.
      if (i == 0) {
        // carry0 = (S_sub) ? S_sub : Cin   -- SUB forces 1, else pass Cin.
        components.add(new ComponentSpec(g + "cs", "Gates", "AND Gate", 1, chip + "CS"));
        components.add(new ComponentSpec(g + "csn", "Gates", "NOT Gate", 1, chip + "CSN"));
        components.add(new ComponentSpec(g + "cso", "Gates", "OR Gate", 1, chip + "CSO"));
        hints.add(new LayoutHint(g + "csn", 8, 0, 0, null));
        hints.add(new LayoutHint(g + "cs", 8, 0, 1, null));
        hints.add(new LayoutHint(g + "cso", 8, 0, 2, null));
        // not S_sub
        nets.add(net("alu_csn", e(g + "ssub", 0), e(g + "csn", 1)));
        // (not S_sub and Cin)
        nets.add(net("alu_csp", e(g + "csn", 0), e(g + "cs", 1)));
        nets.add(net("alu_csc", e("cin", 0), e(g + "cs", 2)));
        // (not S_sub and Cin) or S_sub
        nets.add(net("alu_cso1", e(g + "cs", 0), e(g + "cso", 1)));
        nets.add(net("alu_cso2", e(g + "ssub", 0), e(g + "cso", 2)));
        carryIn = g + "cso";
      }
      nets.add(net("alu_x1o" + seq, e(g + "x1", 0), e(g + "x2", 1), e(g + "g3", 1)));
      nets.add(net("alu_ci" + seq, e(carryIn, 0), e(g + "x2", 2), e(g + "g2", 2), e(g + "g3", 2)));
      nets.add(net("alu_ab" + seq, e(g + "g1", 0), e(g + "o1", 1)));
      nets.add(net("alu_ac" + seq, e(g + "g2", 0), e(g + "o1", 2)));
      nets.add(net("alu_bc" + seq, e(g + "g3", 0), e(g + "o2", 2)));
      nets.add(net("alu_c1" + seq, e(g + "o1", 0), e(g + "o2", 1)));
      seq++;

      // SUB and ADD share the same x2 result (B' and carry already reflect the
      // mode via S_sub), so the single arithmetic lane m3 takes x2.
      // B' = B xor S_sub feeds x1 input2 and the generate term g1.
      nets.add(net("alu_bs_in" + seq, e(g + "ssub", 0), e(g + "bs", 1)));
      nets.add(net("alu_bs_x" + seq, e(g + "bs", 0), e(g + "x1", 2), e(g + "g1", 2)));
      nets.add(net("alu_sum" + seq, e(g + "x2", 0), e(g + "m3", 1)));
      seq++;

      // Mux selects (two-stage AND, all 2-input).
      // m1a = AND lane pre-select: an and notS1;  m1 = m1a and notS0.
      // m2a = OR lane pre-select:  or and notS1;  m2 = m2a and S0.
      // m3  = arithmetic lane: x2 and S1 (ADD and SUB share it).
      nets.add(net("alu_m1a" + seq, e(g + "an", 0), e(g + "m1a", 1)));
      nets.add(net("alu_m1as" + seq, e(g + "ns1", 0), e(g + "m1a", 2)));
      nets.add(net("alu_m1" + seq, e(g + "m1a", 0), e(g + "m1", 1)));
      nets.add(net("alu_m1ss" + seq, e(g + "ns0", 0), e(g + "m1", 2)));
      nets.add(net("alu_m2a" + seq, e(g + "or", 0), e(g + "m2a", 1)));
      nets.add(net("alu_m2as" + seq, e(g + "ns1", 0), e(g + "m2a", 2)));
      nets.add(net("alu_m2" + seq, e(g + "m2a", 0), e(g + "m2", 1)));
      nets.add(net("alu_m2ss" + seq, e("s0", 0), e(g + "m2", 2)));
      nets.add(net("alu_m3s" + seq, e("s1", 0), e(g + "m3", 2)));
      seq++;

      // OR-tree: R_i = m1 | m2 | m3.
      nets.add(net("alu_mo1" + seq, e(g + "m1", 0), e(g + "mo1", 1)));
      nets.add(net("alu_mo2" + seq, e(g + "m2", 0), e(g + "mo1", 2)));
      nets.add(net("alu_mo3" + seq, e(g + "mo1", 0), e(g + "mo2", 1)));
      nets.add(net("alu_mo4" + seq, e(g + "m3", 0), e(g + "mo2", 2)));
      nets.add(net("alu_r" + seq, e(g + "mo2", 0), e("r" + i, 0)));
      seq++;

      // Zero flag: Z = not (R0 or R1 or ... or R(N-1)).
      // For bits>=2, slice 0's OR takes R0|R1 and later slices OR in the rest;
      // for bits==1, Z = not R0 directly (no constant needed, so the official
      // exhaustive truth table stays consistent).
      if (i == 0 && bits >= 2) {
        components.add(new ComponentSpec(g + "zo", "Gates", "OR Gate", 1, chip + "ZO"));
        hints.add(new LayoutHint(g + "zo", 6, 0, 0, null));
        nets.add(net("alu_z0" + seq, e("r0", 0), e(g + "zo", 1)));
        nets.add(net("alu_z1" + seq, e("r1", 0), e(g + "zo", 2)));
      } else if (i == 0) {
        // bits == 1: Z = not R0
        components.add(new ComponentSpec(g + "zo", "Gates", "NOT Gate", 1, chip + "ZO"));
        hints.add(new LayoutHint(g + "zo", 6, 0, 0, null));
        nets.add(net("alu_z0" + seq, e("r0", 0), e(g + "zo", 1)));
      } else if (i == 1 && bits >= 2) {
        // slices 2..N-1 accumulate; slice 1 is where the chain continues.
        components.add(new ComponentSpec(g + "zo", "Gates", "OR Gate", 1, chip + "ZO"));
        hints.add(new LayoutHint(g + "zo", 6, 0, 0, null));
        nets.add(net("alu_zr" + seq, e("r" + i, 0), e(g + "zo", 1)));
        nets.add(net("alu_zz" + seq, e("alu0_zo", 0), e(g + "zo", 2)));
      } else if (i >= 2) {
        components.add(new ComponentSpec(g + "zo", "Gates", "OR Gate", 1, chip + "ZO"));
        hints.add(new LayoutHint(g + "zo", 6, 0, 0, null));
        nets.add(net("alu_zr" + seq, e("r" + i, 0), e(g + "zo", 1)));
        nets.add(net("alu_zz" + seq, e("alu" + (i - 1) + "_zo", 0), e(g + "zo", 2)));
      }
      seq++;
    }
    // Z = not(OR of all R). For bits==1 the slice-0 NOT gate already inverts
    // R0, so its output drives Z directly. For bits>=2 the last slice's OR
    // holds the full OR and a shared NOT inverts it.
    if (bits == 1) {
      nets.add(net("alu_zout", e("alu0_zo", 0), e("z", 0)));
    } else {
      String zOr = "alu" + (bits - 1) + "_zo";
      components.add(new ComponentSpec("alu_znot", "Gates", "NOT Gate", 1, "ALUZ"));
      hints.add(new LayoutHint("alu_znot", 7, 0, 0, null));
      nets.add(net("alu_znot", e(zOr, 0), e("alu_znot", 1)));
      nets.add(net("alu_zout", e("alu_znot", 0), e("z", 0)));
    }

    // Cout: adder carry-out of the last slice. In SUB mode (S1 and S0) the
    // two's-complement carry-out is the inverted borrow, so the raw flag is
    // o2 xor (S1 and S0); AND/OR modes (S1=0) force Cout to 0, so the result
    // is gated by S1. Cout = (o2 xor subFlag) and S1.
    components.add(new ComponentSpec("alu_subf", "Gates", "AND Gate", 1, "ALUF"));
    components.add(new ComponentSpec("alu_cx", "Gates", "XOR Gate", 1, "ALUCX"));
    components.add(new ComponentSpec("alu_cg", "Gates", "AND Gate", 1, "ALUCG"));
    hints.add(new LayoutHint("alu_subf", 8, 0, 3, null));
    hints.add(new LayoutHint("alu_cx", 8, 0, 4, null));
    hints.add(new LayoutHint("alu_cg", 8, 0, 5, null));
    nets.add(net("alu_subf1", e("s1", 0), e("alu_subf", 1)));
    nets.add(net("alu_subf2", e("s0", 0), e("alu_subf", 2)));
    nets.add(net("alu_cx1", e("alu" + (bits - 1) + "_o2", 0), e("alu_cx", 1)));
    nets.add(net("alu_cx2", e("alu_subf", 0), e("alu_cx", 2)));
    nets.add(net("alu_cg1", e("alu_cx", 0), e("alu_cg", 1)));
    nets.add(net("alu_cg2", e("s1", 0), e("alu_cg", 2)));
    nets.add(net("alu_cout", e("alu_cg", 0), e("cout", 0)));

    return new CircuitSpec("alu_" + bits + "bit", components, ports, nets, hints);
  }

  /**
   * N-bit barrel shifter (桶形移位器): logical left shift of the input word by
   * SH bits, SH in 0..N-1 (SH width = ceil(log2 N)). Output y[i] selects
   * D[i-shift] over a mux tree; out-of-range sources (i-shift < 0) read a
   * constant-0 gnd. All gates 2-input, so the official JAR truth table is
   * fully deterministic.
   */
  static CircuitSpec buildBarrelShifter(int bits) {
    return buildShiftCircuit("barrel_shifter_" + bits + "bit", bits, true, false, "gnd");
  }

  /**
   * N-bit rotate shifter (循环移位器): rotate the input word left (or right) by
   * SH bits. Output y[i] selects D[(i ∓ shift) mod N]; every source wraps, so
   * no constant-0 gnd input is needed. SH values ≥ N behave as SH mod N.
   */
  static CircuitSpec buildRotateShifter(int bits, boolean left) {
    return buildShiftCircuit(
        "rotate_shifter_" + bits + "bit" + (left ? "_left" : "_right"),
        bits, left, true, null);
  }

  /** N-bit logical right shift (逻辑右移): zero fill, SH in 0..N-1. */
  static CircuitSpec buildLogicalShiftRight(int bits) {
    return buildShiftCircuit("shift_right_" + bits + "bit", bits, false, false, "gnd");
  }

  /** N-bit arithmetic right shift (算术右移): sign fill (D[N-1]), SH in 0..N-1. */
  static CircuitSpec buildArithmeticShiftRight(int bits) {
    return buildShiftCircuit("sar_" + bits + "bit", bits, false, false, "d" + (bits - 1));
  }

  /**
   * Shared mux-tree builder for shift/rotate circuits. Every gate is 2-input.
   * Out-of-range sources (raw outside 0..N-1): with {@code rotate=true} the
   * index wraps modulo N; otherwise the source reads {@code fillId} (e.g. the
   * external "gnd" pin for zero fill, or "d{N-1}" for arithmetic sign fill).
   */
  private static CircuitSpec buildShiftCircuit(
      String name, int bits, boolean left, boolean rotate, String fillId) {
    if (bits < 2 || bits > 8) {
      throw new IllegalArgumentException("Shift/rotate width must be 2..8, got " + bits);
    }
    int shBits = 0;
    while ((1 << shBits) < bits) {
      shBits++;
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < bits; i++) {
      addInput(ports, "d" + i, "D" + i, "bs_ns0");
      addOutput(ports, "y" + i, "Y" + i, "bs_ns0");
    }
    for (int k = 0; k < shBits; k++) {
      addInput(ports, "sh" + k, "SH" + k, "bs_ns0");
    }
    if ("gnd".equals(fillId)) {
      addInput(ports, "gnd", "GND", "bs_ns0");
    }

    int seq = 0;
    // Shared per-bit select signals: sh[k] and not sh[k].
    String[][] shSel = new String[shBits][2]; // [k][0]=sh, [k][1]=not sh
    for (int k = 0; k < shBits; k++) {
      String n = "bs_ns" + k;
      components.add(new ComponentSpec(n, "Gates", "NOT Gate", 1, "NS" + (k + 1)));
      hints.add(new LayoutHint(n, 0, 0, k, null));
      nets.add(net("bs_ns_in" + k, e("sh" + k, 0), e(n, 1)));
      shSel[k][0] = "sh" + k;
      shSel[k][1] = n;
    }

    for (int i = 0; i < bits; i++) {
      // mux tree: y[i] = OR over all shifts of (src(s) AND select(s)).
      // select(s) = AND of sh[k] (bit 1) or not sh[k] (bit 0) for each k.
      int shifts = 1 << shBits;
      String[] laneAnd = new String[shifts];
      for (int s = 0; s < shifts; s++) {
        int raw = left ? (i - s) : (i + s);
        String srcSig;
        if (rotate) {
          int wrapped = ((raw % bits) + bits) % bits;
          srcSig = "d" + wrapped;
        } else {
          srcSig = (raw >= 0 && raw < bits) ? ("d" + raw) : fillId;
        }
        // build select AND chain over shBits bits (2-input gates)
        String acc = null;
        String accSig = null;
        for (int k = 0; k < shBits; k++) {
          String selSig = ((s >> k) & 1) == 1 ? shSel[k][0] : shSel[k][1];
          if (acc == null) {
            acc = "bs_" + i + "_sa" + seq + "_" + k;
            components.add(new ComponentSpec(acc, "Gates", "AND Gate", 1, "SA" + (seq + 1)));
            hints.add(new LayoutHint(acc, 1, 0, k, null));
            nets.add(net("bs_sa1" + seq, e(srcSig, 0), e(acc, 1)));
            nets.add(net("bs_sa2" + seq, e(selSig, 0), e(acc, 2)));
            accSig = acc;
          } else {
            String nxt = "bs_" + i + "_sb" + seq + "_" + k;
            components.add(new ComponentSpec(nxt, "Gates", "AND Gate", 1, "SB" + (seq + 1)));
            hints.add(new LayoutHint(nxt, 2, 0, k, null));
            nets.add(net("bs_sb1" + seq, e(accSig, 0), e(nxt, 1)));
            nets.add(net("bs_sb2" + seq, e(selSig, 0), e(nxt, 2)));
            accSig = nxt;
          }
          seq++;
        }
        laneAnd[s] = accSig;
      }
      // OR-tree over all lanes (2-input ORs).
      String orAcc = null;
      for (int s = 0; s < shifts; s++) {
        if (orAcc == null) {
          orAcc = laneAnd[s];
        } else {
          String o = "bs_" + i + "_mo" + seq;
          components.add(new ComponentSpec(o, "Gates", "OR Gate", 1, "MO" + (seq + 1)));
          hints.add(new LayoutHint(o, 3, 0, seq % 8, null));
          nets.add(net("bs_mo1" + seq, e(orAcc, 0), e(o, 1)));
          nets.add(net("bs_mo2" + seq, e(laneAnd[s], 0), e(o, 2)));
          orAcc = o;
          seq++;
        }
      }
      nets.add(net("bs_y" + i, e(orAcc, 0), e("y" + i, 0)));
    }

    return new CircuitSpec(name, components, ports, nets, hints);
  }

  /**
   * N-bit unsigned restoring divider (除法器): Q = A / B, R = A mod B.
   * Shift-subtract unrolled over N stages; stage for dividend bit i computes
   * candidate = {R<<1 | A_i} (N+1 bits), subtracts B through a ripple borrow
   * chain, then muxes the N-bit remainder (quotient bit q_i = NOT borrow).
   * Every gate is 2-input; a shared GND input pin supplies the initial zero
   * fill and bit N of B (B < 2^N, so B_N = 0). With B = 0 the circuit yields
   * Q = 2^N-1, R = A (all-subtract degenerate case).
   */
  static CircuitSpec buildArithmeticDivider(int bits) {
    if (bits < 2 || bits > 4) {
      throw new IllegalArgumentException("Divider width must be 2..4, got " + bits);
    }
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < bits; i++) {
      addInput(ports, "a" + i, "A" + i, "dv0_x");
      addInput(ports, "b" + i, "B" + i, "dv0_x");
      addOutput(ports, "q" + i, "Q" + i, "dv0_x");
      addOutput(ports, "r" + i, "R" + i, "dv0_x");
    }
    addInput(ports, "gnd", "GND", "dv0_x");

    int seq = 0;
    String[] rPrev = null;
    for (int stage = 0; stage < bits; stage++) {
      int i = bits - 1 - stage; // dividend bit processed by this stage
      // candidate = {rPrev, A_i} as N+1 bits (bit0 = A_i, bit j>=1 = rPrev[j-1]).
      String[] cand = new String[bits + 1];
      for (int j = 0; j <= bits; j++) {
        if (j == 0) {
          cand[j] = "a" + i;
        } else if (stage == 0) {
          cand[j] = "gnd";
        } else {
          cand[j] = rPrev[j - 1];
        }
      }
      // Ripple subtract cand - B over N+1 bits; B_N = 0 via the gnd pin.
      String[] diff = new String[bits + 1];
      String bin = "gnd";
      for (int k = 0; k <= bits; k++) {
        String g = "dv" + seq + "_";
        String chip = "DV" + seq;
        components.add(new ComponentSpec(g + "x", "Gates", "XOR Gate", 1, chip + "X"));
        components.add(new ComponentSpec(g + "d", "Gates", "XOR Gate", 1, chip + "D"));
        components.add(new ComponentSpec(g + "na", "Gates", "NOT Gate", 1, chip + "N"));
        components.add(new ComponentSpec(g + "bo", "Gates", "OR Gate", 1, chip + "O"));
        components.add(new ComponentSpec(g + "b1", "Gates", "AND Gate", 1, chip + "A"));
        components.add(new ComponentSpec(g + "b2", "Gates", "AND Gate", 1, chip + "B"));
        components.add(new ComponentSpec(g + "b3", "Gates", "OR Gate", 1, chip + "C"));
        hints.add(new LayoutHint(g + "x", stage, 0, k, null));
        hints.add(new LayoutHint(g + "d", stage, 0, k, null));
        hints.add(new LayoutHint(g + "na", stage, 0, k, null));
        hints.add(new LayoutHint(g + "bo", stage, 0, k, null));
        hints.add(new LayoutHint(g + "b1", stage, 0, k, null));
        hints.add(new LayoutHint(g + "b2", stage, 0, k, null));
        hints.add(new LayoutHint(g + "b3", stage, 0, k, null));
        String bSig = k < bits ? ("b" + k) : "gnd";
        nets.add(net("dv_xa" + seq, e(cand[k], 0), e(g + "x", 1), e(g + "na", 1)));
        nets.add(net("dv_xb" + seq, e(bSig, 0), e(g + "x", 2), e(g + "bo", 1), e(g + "b2", 1)));
        nets.add(net("dv_dx" + seq, e(g + "x", 0), e(g + "d", 1)));
        nets.add(net("dv_di" + seq, e(bin, 0), e(g + "d", 2)));
        diff[k] = g + "d";
        nets.add(net("dv_bo2" + seq, e(bin, 0), e(g + "bo", 2)));
        nets.add(net("dv_b1a" + seq, e(g + "na", 0), e(g + "b1", 1)));
        nets.add(net("dv_b1b" + seq, e(g + "bo", 0), e(g + "b1", 2)));
        nets.add(net("dv_b2b" + seq, e(bin, 0), e(g + "b2", 2)));
        nets.add(net("dv_b3a" + seq, e(g + "b1", 0), e(g + "b3", 1)));
        nets.add(net("dv_b3b" + seq, e(g + "b2", 0), e(g + "b3", 2)));
        bin = g + "b3";
        seq++;
      }
      // q_i = NOT borrow (borrow=0 means candidate >= B, subtract).
      String qNot = "dv" + seq + "_q";
      components.add(new ComponentSpec(qNot, "Gates", "NOT Gate", 1, "DVQ" + (seq + 1)));
      hints.add(new LayoutHint(qNot, stage, 0, bits, null));
      nets.add(net("dv_qin" + seq, e(bin, 0), e(qNot, 1)));
      nets.add(net("dv_qout" + seq, e(qNot, 0), e("q" + i, 0)));
      // Mux result bits 0..N-1: rNext[j] = (diff[j] & q) | (cand[j] & borrow).
      String[] rNext = new String[bits];
      for (int j = 0; j < bits; j++) {
        String g = "dv" + seq + "_m";
        String chip = "DVM" + seq;
        components.add(new ComponentSpec(g + "1", "Gates", "AND Gate", 1, chip + "1"));
        components.add(new ComponentSpec(g + "2", "Gates", "AND Gate", 1, chip + "2"));
        components.add(new ComponentSpec(g + "o", "Gates", "OR Gate", 1, chip + "O"));
        hints.add(new LayoutHint(g + "1", stage, 0, j, null));
        hints.add(new LayoutHint(g + "2", stage, 0, j, null));
        hints.add(new LayoutHint(g + "o", stage, 0, j, null));
        nets.add(net("dv_m1a" + seq, e(diff[j], 0), e(g + "1", 1)));
        nets.add(net("dv_m1b" + seq, e(qNot, 0), e(g + "1", 2)));
        nets.add(net("dv_m2a" + seq, e(cand[j], 0), e(g + "2", 1)));
        nets.add(net("dv_m2b" + seq, e(bin, 0), e(g + "2", 2)));
        nets.add(net("dv_mo1" + seq, e(g + "1", 0), e(g + "o", 1)));
        nets.add(net("dv_mo2" + seq, e(g + "2", 0), e(g + "o", 2)));
        rNext[j] = g + "o";
        seq++;
      }
      rPrev = rNext;
    }
    for (int j = 0; j < bits; j++) {
      nets.add(net("dv_rout" + j, e(rPrev[j], 0), e("r" + j, 0)));
    }

    return new CircuitSpec("divider_" + bits + "x" + bits, components, ports, nets, hints);
  }

  /**
   * BCD adder: 4-bit binary add A+B+Cin, then add 6 when the sum exceeds 9 or
   * carries out (correction). Outputs S0..S3 (BCD) and Cout. A shared GND
   * input pin ("Z", constant 0) supplies the correction-off lanes.
   */
  static CircuitSpec buildBcdAdder() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < 4; i++) {
      addInput(ports, "a" + i, "A" + i, "bcdadd_x0");
      addInput(ports, "b" + i, "B" + i, "bcdadd_x0");
    }
    addInput(ports, "cin", "Cin", "bcdadd_x0");
    addInput(ports, "z", "Z", "bcdadd_x0");
    addOutput(ports, "s0", "S0", "bcdadd_x0");
    addOutput(ports, "s1", "S1", "bcdadd_x0");
    addOutput(ports, "s2", "S2", "bcdadd_x0");
    addOutput(ports, "s3", "S3", "bcdadd_x0");
    addOutput(ports, "cout", "Cout", "bcdadd_x0");

    int[] seqRef = {0};
    String[] aSig = new String[4];
    String[] bSig = new String[4];
    for (int i = 0; i < 4; i++) {
      aSig[i] = "a" + i;
      bSig[i] = "b" + i;
    }
    String[] out = emitBcdDigit(components, nets, hints, seqRef, "bcdadd", aSig, bSig, "cin", "z");
    for (int i = 0; i < 4; i++) {
      nets.add(net("bcdadd_sout" + i, e(out[i], 0), e("s" + i, 0)));
    }
    nets.add(net("bcdadd_cout", e(out[4], 0), e("cout", 0)));

    return new CircuitSpec("bcd_adder", components, ports, nets, hints);
  }

  /**
   * 2-digit BCD adder: A1A0 + B1B0 + Cin → S1S0 (BCD) + Cout. Two
   * {@link #emitBcdDigit} digits chained; digit 1's carry-in is digit 0's
   * carry-out. Outputs S0..S7 (S0..S3 low digit, S4..S7 high digit) and Cout.
   * A shared GND input pin ("Z") supplies the correction-off lanes.
   */
  static CircuitSpec buildBcdAdder2Digit() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < 8; i++) {
      addInput(ports, "a" + i, "A" + i, "bcd0_x0");
      addInput(ports, "b" + i, "B" + i, "bcd0_x0");
    }
    addInput(ports, "cin", "Cin", "bcd0_x0");
    addInput(ports, "z", "Z", "bcd0_x0");
    for (int i = 0; i < 8; i++) {
      addOutput(ports, "s" + i, "S" + i, "bcd0_x0");
    }
    addOutput(ports, "cout", "Cout", "bcd0_x0");

    int[] seqRef = {0};
    String[] aLow = new String[4];
    String[] bLow = new String[4];
    String[] aHigh = new String[4];
    String[] bHigh = new String[4];
    for (int i = 0; i < 4; i++) {
      aLow[i] = "a" + i;
      bLow[i] = "b" + i;
      aHigh[i] = "a" + (4 + i);
      bHigh[i] = "b" + (4 + i);
    }
    String[] low = emitBcdDigit(components, nets, hints, seqRef, "bcd0", aLow, bLow, "cin", "z");
    String[] high = emitBcdDigit(components, nets, hints, seqRef, "bcd1", aHigh, bHigh, low[4], "z");
    for (int i = 0; i < 4; i++) {
      nets.add(net("bcd0_sout" + i, e(low[i], 0), e("s" + i, 0)));
      nets.add(net("bcd1_sout" + i, e(high[i], 0), e("s" + (4 + i), 0)));
    }
    nets.add(net("bcd2_cout", e(high[4], 0), e("cout", 0)));

    return new CircuitSpec("bcd_adder_2digit", components, ports, nets, hints);
  }

  /**
   * Emits one 4-bit BCD adder digit into the shared component/net/hint lists.
   *
   * @param aSig/bSig/cinSig/zSig signal names (external port ids or internal
   *     gate outputs) for the digit's operands, carry-in and constant-0 lane
   * @return {S0, S1, S2, S3, Cout} gate-output signal names
   */
  private static String[] emitBcdDigit(
      List<ComponentSpec> components, List<NetSpec> nets, List<LayoutHint> hints,
      int[] seqRef, String prefix, String[] aSig, String[] bSig, String cinSig, String zSig) {
    int seq = seqRef[0];

    // stage 0: A + B + Cin -> S (4 bits) + c4
    String[] sum0 = new String[4];
    String c = cinSig;
    for (int i = 0; i < 4; i++) {
      String x = prefix + "_x" + seq;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "BXA" + (seq + 1)));
      hints.add(new LayoutHint(x, 0, 0, i, null));
      String s = prefix + "_s" + seq;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "BXS" + (seq + 1)));
      hints.add(new LayoutHint(s, 0, 0, i, null));
      String fa = prefix + "_a" + seq;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "BAA" + (seq + 1)));
      hints.add(new LayoutHint(fa, 0, 0, i, null));
      String fb = prefix + "_b" + seq;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "BAB" + (seq + 1)));
      hints.add(new LayoutHint(fb, 0, 0, i, null));
      String fo = prefix + "_o" + seq;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "BAO" + (seq + 1)));
      hints.add(new LayoutHint(fo, 0, 0, i, null));
      String fg = prefix + "_g" + seq;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "BAG" + (seq + 1)));
      hints.add(new LayoutHint(fg, 0, 0, i, null));
      seq++;
      nets.add(net(prefix + "_xa" + seq, e(aSig[i], 0), e(x, 1)));
      nets.add(net(prefix + "_xb" + seq, e(bSig[i], 0), e(x, 2)));
      nets.add(net(prefix + "_sa" + seq, e(x, 0), e(s, 1)));
      nets.add(net(prefix + "_sb" + seq, e(c, 0), e(s, 2)));
      nets.add(net(prefix + "_fa" + seq, e(aSig[i], 0), e(fa, 1)));
      nets.add(net(prefix + "_fb" + seq, e(bSig[i], 0), e(fa, 2)));
      nets.add(net(prefix + "_ga" + seq, e(c, 0), e(fb, 1)));
      nets.add(net(prefix + "_gb" + seq, e(x, 0), e(fb, 2)));
      nets.add(net(prefix + "_o1" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net(prefix + "_o2" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net(prefix + "_g1" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net(prefix + "_g2" + seq, e(zSig, 0), e(fg, 2)));
      sum0[i] = s;
      c = fg;
    }
    String c4 = c;

    // correction detect: adj = c4 or (S3 and (S2 or S1))
    String o1 = prefix + "_adj" + seq;
    components.add(new ComponentSpec(o1, "Gates", "OR Gate", 1, "BAJ" + (seq + 1)));
    hints.add(new LayoutHint(o1, 1, 0, 0, null));
    String a1 = prefix + "_adj2" + seq;
    components.add(new ComponentSpec(a1, "Gates", "AND Gate", 1, "BAK" + (seq + 1)));
    hints.add(new LayoutHint(a1, 1, 0, 1, null));
    String g1 = prefix + "_adj3" + seq;
    components.add(new ComponentSpec(g1, "Gates", "OR Gate", 1, "BAL" + (seq + 1)));
    hints.add(new LayoutHint(g1, 1, 0, 2, null));
    seq++;
    nets.add(net(prefix + "_s2s1" + seq, e(sum0[2], 0), e(g1, 1)));
    nets.add(net(prefix + "_s1s" + seq, e(sum0[1], 0), e(g1, 2)));
    nets.add(net(prefix + "_adj_a" + seq, e(g1, 0), e(a1, 1)));
    nets.add(net(prefix + "_adj_s3" + seq, e(sum0[3], 0), e(a1, 2)));
    nets.add(net(prefix + "_adj_or1" + seq, e(c4, 0), e(o1, 1)));
    nets.add(net(prefix + "_adj_or2" + seq, e(a1, 0), e(o1, 2)));
    String adj = o1;

    // stage 1: sum0 + (adj ? 6 : 0); bit0 adds 0, bits1..3 add adj
    String[] sumOut = new String[4];
    String cIn2 = zSig;
    for (int i = 0; i < 4; i++) {
      String bS = (i == 0 || i == 3) ? zSig : adj;
      String x = prefix + "_x2_" + seq;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "BX2" + (seq + 1)));
      hints.add(new LayoutHint(x, 2, 0, i, null));
      String s = prefix + "_s2_" + seq;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "BX3" + (seq + 1)));
      hints.add(new LayoutHint(s, 2, 0, i, null));
      String fa = prefix + "_a2_" + seq;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "BA2" + (seq + 1)));
      hints.add(new LayoutHint(fa, 2, 0, i, null));
      String fb = prefix + "_b2_" + seq;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "BB2" + (seq + 1)));
      hints.add(new LayoutHint(fb, 2, 0, i, null));
      String fo = prefix + "_o2_" + seq;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "BO2" + (seq + 1)));
      hints.add(new LayoutHint(fo, 2, 0, i, null));
      String fg = prefix + "_g2_" + seq;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "BG2" + (seq + 1)));
      hints.add(new LayoutHint(fg, 2, 0, i, null));
      seq++;
      nets.add(net(prefix + "_x2a" + seq, e(sum0[i], 0), e(x, 1)));
      nets.add(net(prefix + "_x2b" + seq, e(bS, 0), e(x, 2)));
      nets.add(net(prefix + "_s2a" + seq, e(x, 0), e(s, 1)));
      nets.add(net(prefix + "_s2b" + seq, e(cIn2, 0), e(s, 2)));
      nets.add(net(prefix + "_f2a" + seq, e(sum0[i], 0), e(fa, 1)));
      nets.add(net(prefix + "_f2b" + seq, e(bS, 0), e(fa, 2)));
      nets.add(net(prefix + "_g2a" + seq, e(cIn2, 0), e(fb, 1)));
      nets.add(net(prefix + "_g2b" + seq, e(x, 0), e(fb, 2)));
      nets.add(net(prefix + "_o21" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net(prefix + "_o22" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net(prefix + "_g21" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net(prefix + "_g22" + seq, e(zSig, 0), e(fg, 2)));
      sumOut[i] = s;
      cIn2 = fg;
    }
    seqRef[0] = seq;
    return new String[] {sumOut[0], sumOut[1], sumOut[2], sumOut[3], adj};
  }

  /**
   * 1-digit BCD subtractor: D = A - B (mod 10). Algorithm:
   *   Stage 1: T = A + 9's-complement(B) via a BCD adder (Cin=0);
   *   Stage 2: D = T + 1 via a BCD adder (Cin=1)  → A + 10 - B.
   *   Borrow = NOT(Stage2 carry-out): carry 1 means the sum reached 10 (A>=B)
   *   and the correction yields A-B; no carry means A<B and D = A-B+10 (the
   *   mod-10 difference), borrow 1.
   * Outputs D0..D3 (BCD), Borrow. All gates 2-input.
   */
  static CircuitSpec buildBcdSubtractor() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    int seq = 0;

    for (int i = 0; i < 4; i++) {
      addInput(ports, "a" + i, "A" + i, "bs_n0");
      addInput(ports, "b" + i, "B" + i, "bs_n0");
    }
    for (int i = 0; i < 4; i++) {
      addOutput(ports, "d" + i, "D" + i, "bs_s4_" + i);
    }
    addOutput(ports, "borrow", "Borrow", "bs_nb");

    // 9's complement of B (b9_0=¬b0, b9_1=b1, b9_2=b2⊕b1, b9_3=¬(b3⊕(b2∨b1))).
    components.add(new ComponentSpec("bs_n0", "Gates", "NOT Gate", 1, "N1"));
    components.add(new ComponentSpec("bs_x2", "Gates", "XOR Gate", 1, "X2"));
    components.add(new ComponentSpec("bs_o", "Gates", "OR Gate", 1, "O1"));
    components.add(new ComponentSpec("bs_x3", "Gates", "XOR Gate", 1, "X3"));
    components.add(new ComponentSpec("bs_n3", "Gates", "NOT Gate", 1, "N2"));
    hints.add(new LayoutHint("bs_n0", 0, 0, 0, null));
    hints.add(new LayoutHint("bs_x2", 0, 0, 1, null));
    hints.add(new LayoutHint("bs_o", 0, 0, 2, null));
    hints.add(new LayoutHint("bs_x3", 0, 0, 3, null));
    hints.add(new LayoutHint("bs_n3", 0, 0, 4, null));
    nets.add(net("bs_n0_in", e("b0", 0), e("bs_n0", 1)));
    nets.add(net("bs_x2_x", e("b2", 0), e("bs_x2", 1)));
    nets.add(net("bs_x2_y", e("b1", 0), e("bs_x2", 2)));
    nets.add(net("bs_o_x", e("b2", 0), e("bs_o", 1)));
    nets.add(net("bs_o_y", e("b1", 0), e("bs_o", 2)));
    nets.add(net("bs_x3_w", e("b3", 0), e("bs_x3", 1)));
    nets.add(net("bs_x3_b", e("bs_o", 0), e("bs_x3", 2)));
    nets.add(net("bs_n3_in", e("bs_x3", 0), e("bs_n3", 1)));
    String[] b9 = {"bs_n0", "b1", "bs_x2", "bs_n3"};

    // Stage 1: plain 4-bit ripple add T = A + B9 (no BCD correction yet — the
    // +6 correction happens once on the final A+B9+1 sum so the >=10 decision
    // for borrow stays correct). Outputs t0..t3, tCarry.
    String[] t = new String[4];
    // constant-0 pin for unused carry lanes and the correction-off lanes
    addInput(ports, "gnd", "GND", "bs_x1_0");
    String c = "gnd";
    for (int i = 0; i < 4; i++) {
      String x = "bs_x1_" + i;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "X1" + (i + 1)));
      hints.add(new LayoutHint(x, 1, 0, i, null));
      String s = "bs_s1_" + i;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "S1" + (i + 1)));
      hints.add(new LayoutHint(s, 1, 0, i, null));
      String fa = "bs_f1_" + i;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "A1" + (i + 1)));
      hints.add(new LayoutHint(fa, 1, 0, i, null));
      String fb = "bs_b1_" + i;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "B1" + (i + 1)));
      hints.add(new LayoutHint(fb, 1, 0, i, null));
      String fo = "bs_o1_" + i;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "C1" + (i + 1)));
      hints.add(new LayoutHint(fo, 1, 0, i, null));
      String fg = "bs_g1_" + i;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "G1" + (i + 1)));
      hints.add(new LayoutHint(fg, 1, 0, i, null));
      String cinSig = c;
      nets.add(net("bs_x1a" + seq, e("a" + i, 0), e(x, 1)));
      nets.add(net("bs_x1b" + seq, e(b9[i], 0), e(x, 2)));
      nets.add(net("bs_s1a" + seq, e(x, 0), e(s, 1)));
      nets.add(net("bs_s1b" + seq, e(cinSig, 0), e(s, 2)));
      t[i] = s;
      nets.add(net("bs_f1a" + seq, e("a" + i, 0), e(fa, 1)));
      nets.add(net("bs_f1b" + seq, e(b9[i], 0), e(fa, 2)));
      nets.add(net("bs_b1a" + seq, e(cinSig, 0), e(fb, 1)));
      nets.add(net("bs_b1b" + seq, e(x, 0), e(fb, 2)));
      nets.add(net("bs_o11" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net("bs_o12" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net("bs_g11" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net("bs_g12" + seq, e("gnd", 0), e(fg, 2)));
      c = fg;
      seq++;
    }
    String tCarry = c;

    // BCD addition D = A + B9 + 1 (bit0's bSig=ONE implements the +1; the
    // carry chain starts at 0), then a single +6 correction when the sum
    // exceeds 9 (adj = carry or (d3 & (d2|d1))).
    String[] d = new String[4];
    addInput(ports, "one", "ONE", "bs_x3_0");
    String c3 = "gnd";
    for (int i = 0; i < 4; i++) {
      String x = "bs_x3_" + i;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "X3" + (i + 1)));
      hints.add(new LayoutHint(x, 4, 0, i, null));
      String s = "bs_s3_" + i;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "S3" + (i + 1)));
      hints.add(new LayoutHint(s, 4, 0, i, null));
      String fa = "bs_f3_" + i;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "A3" + (i + 1)));
      hints.add(new LayoutHint(fa, 4, 0, i, null));
      String fb = "bs_b3_" + i;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "B3" + (i + 1)));
      hints.add(new LayoutHint(fb, 4, 0, i, null));
      String fo = "bs_o3_" + i;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "C3" + (i + 1)));
      hints.add(new LayoutHint(fo, 4, 0, i, null));
      String fg = "bs_g3_" + i;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "G3" + (i + 1)));
      hints.add(new LayoutHint(fg, 4, 0, i, null));
      String bSig = (i == 0) ? "one" : "gnd";
      nets.add(net("bs_x3a" + seq, e(t[i], 0), e(x, 1)));
      nets.add(net("bs_x3b" + seq, e(bSig, 0), e(x, 2)));
      nets.add(net("bs_s3a" + seq, e(x, 0), e(s, 1)));
      nets.add(net("bs_s3b" + seq, e(c3, 0), e(s, 2)));
      d[i] = s;
      nets.add(net("bs_f3a" + seq, e(t[i], 0), e(fa, 1)));
      nets.add(net("bs_f3b" + seq, e(bSig, 0), e(fa, 2)));
      nets.add(net("bs_b3a" + seq, e(c3, 0), e(fb, 1)));
      nets.add(net("bs_b3b" + seq, e(x, 0), e(fb, 2)));
      nets.add(net("bs_o31" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net("bs_o32" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net("bs_g31" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net("bs_g32" + seq, e("gnd", 0), e(fg, 2)));
      c3 = fg;
      seq++;
    }
    String dCarry = c3;

    // correction detect: adj = dCarry or (d3 and (d2 or d1))
    String go = "bs_adj2_o" + seq;
    components.add(new ComponentSpec(go, "Gates", "OR Gate", 1, "B2J" + (seq + 1)));
    hints.add(new LayoutHint(go, 5, 0, 0, null));
    String ga = "bs_adj2_a" + seq;
    components.add(new ComponentSpec(ga, "Gates", "AND Gate", 1, "B2K" + (seq + 1)));
    hints.add(new LayoutHint(ga, 5, 0, 1, null));
    String gg = "bs_adj2_g" + seq;
    components.add(new ComponentSpec(gg, "Gates", "OR Gate", 1, "B2L" + (seq + 1)));
    hints.add(new LayoutHint(gg, 5, 0, 2, null));
    seq++;
    nets.add(net("bs_adj2_g1", e(d[2], 0), e(gg, 1)));
    nets.add(net("bs_adj2_g2", e(d[1], 0), e(gg, 2)));
    nets.add(net("bs_adj2_a1", e(gg, 0), e(ga, 1)));
    nets.add(net("bs_adj2_a2", e(d[3], 0), e(ga, 2)));
    // adj = (tCarry or dCarry) or (d3 and (d2 or d1)).
    // tCarry (A+B9 >= 16) also means D = A+10-B >= 10, so it must feed adj.
    String tc = "bs_adj2_tc" + seq;
    components.add(new ComponentSpec(tc, "Gates", "OR Gate", 1, "B2T" + (seq + 1)));
    hints.add(new LayoutHint(tc, 5, 0, 3, null));
    seq++;
    nets.add(net("bs_adj2_tc1", e(tCarry, 0), e(tc, 1)));
    nets.add(net("bs_adj2_tc2", e(dCarry, 0), e(tc, 2)));
    nets.add(net("bs_adj2_o1", e(tc, 0), e(go, 1)));
    nets.add(net("bs_adj2_o2", e(ga, 0), e(go, 2)));
    String adj2 = go;
    String[] dFinal = new String[4];
    String c4 = "gnd";
    for (int i = 0; i < 4; i++) {
      String bSig = (i == 0 || i == 3) ? "gnd" : adj2;
      String x = "bs_x4_" + i;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "X4" + (i + 1)));
      hints.add(new LayoutHint(x, 5, 0, i, null));
      String s = "bs_s4_" + i;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "S4" + (i + 1)));
      hints.add(new LayoutHint(s, 5, 0, i, null));
      String fa = "bs_f4_" + i;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "A4" + (i + 1)));
      hints.add(new LayoutHint(fa, 5, 0, i, null));
      String fb = "bs_b4_" + i;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "B4" + (i + 1)));
      hints.add(new LayoutHint(fb, 5, 0, i, null));
      String fo = "bs_o4_" + i;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "C4" + (i + 1)));
      hints.add(new LayoutHint(fo, 5, 0, i, null));
      String fg = "bs_g4_" + i;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "G4" + (i + 1)));
      hints.add(new LayoutHint(fg, 5, 0, i, null));
      nets.add(net("bs_x4a" + seq, e(d[i], 0), e(x, 1)));
      nets.add(net("bs_x4b" + seq, e(bSig, 0), e(x, 2)));
      nets.add(net("bs_s4a" + seq, e(x, 0), e(s, 1)));
      nets.add(net("bs_s4b" + seq, e(c4, 0), e(s, 2)));
      dFinal[i] = s;
      nets.add(net("bs_f4a" + seq, e(d[i], 0), e(fa, 1)));
      nets.add(net("bs_f4b" + seq, e(bSig, 0), e(fa, 2)));
      nets.add(net("bs_b4a" + seq, e(c4, 0), e(fb, 1)));
      nets.add(net("bs_b4b" + seq, e(x, 0), e(fb, 2)));
      nets.add(net("bs_o41" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net("bs_o42" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net("bs_g41" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net("bs_g42" + seq, e("gnd", 0), e(fg, 2)));
      c4 = fg;
      seq++;
    }

    // outputs: D_i from dFinal, Borrow = NOT(adj2) (A<B detection)
    components.add(new ComponentSpec("bs_nb", "Gates", "NOT Gate", 1, "NB"));
    hints.add(new LayoutHint("bs_nb", 6, 0, 0, null));
    nets.add(net("bs_nb_in", e(adj2, 0), e("bs_nb", 1)));
    for (int i = 0; i < 4; i++) {
      nets.add(net("bs_dout" + i, e(dFinal[i], 0), e("d" + i, 0)));
    }
    nets.add(net("bs_borrow", e("bs_nb", 0), e("borrow", 0)));

    return new CircuitSpec("bcd_subtractor", components, ports, nets, hints);
  }

  /**
   * 2-digit BCD subtractor: A1A0 - B1B0 - Bin → D1D0 (BCD) + Borrow. Two
   * {@link #emitBcdSubDigit} digits chained; digit 0's borrow-out feeds digit
   * 1's borrow-in. Result D = (A - B - Bin) mod 100, Borrow = (A < B + Bin).
   * A shared GND input pin supplies the constant-0 lanes.
   */
  static CircuitSpec buildBcdSubtractor2Digit() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < 8; i++) {
      addInput(ports, "a" + i, "A" + i, "bcds0_n0");
      addInput(ports, "b" + i, "B" + i, "bcds0_n0");
    }
    addInput(ports, "bin", "Bin", "bcds0_n0");
    addInput(ports, "gnd", "GND", "bcds0_n0");
    for (int i = 0; i < 8; i++) {
      addOutput(ports, "d" + i, "D" + i, "bcds0_n0");
    }
    addOutput(ports, "borrow", "Borrow", "bcds0_n0");

    int[] seqRef = {0};
    String[] aLow = new String[4];
    String[] bLow = new String[4];
    String[] aHigh = new String[4];
    String[] bHigh = new String[4];
    for (int i = 0; i < 4; i++) {
      aLow[i] = "a" + i;
      bLow[i] = "b" + i;
      aHigh[i] = "a" + (4 + i);
      bHigh[i] = "b" + (4 + i);
    }
    String[] low = emitBcdSubDigit(components, nets, hints, seqRef, "bcds0", aLow, bLow, "bin", "gnd");
    String[] high = emitBcdSubDigit(components, nets, hints, seqRef, "bcds1", aHigh, bHigh, low[4], "gnd");
    for (int i = 0; i < 4; i++) {
      nets.add(net("bcds0_dout" + i, e(low[i], 0), e("d" + i, 0)));
      nets.add(net("bcds1_dout" + i, e(high[i], 0), e("d" + (4 + i), 0)));
    }
    nets.add(net("bcds2_borrow", e(high[4], 0), e("borrow", 0)));

    return new CircuitSpec("bcd_subtractor_2digit", components, ports, nets, hints);
  }

  /**
   * Emits one BCD subtractor digit (D = A - B - Bin mod 10, borrow-out =
   * A < B + Bin) into the shared lists. Algorithm per digit: T = A + 9's(B);
   * S = T + (1 - Bin) (bit0 addend = NOT Bin); adj = (tCarry|dCarry) |
   * (S3&(S2|S1)); D = S + 6·adj mod 16; borrow = NOT adj.
   *
   * @return {D0, D1, D2, D3, borrowOut} gate-output signal names
   */
  private static String[] emitBcdSubDigit(
      List<ComponentSpec> components, List<NetSpec> nets, List<LayoutHint> hints,
      int[] seqRef, String prefix, String[] aSig, String[] bSig, String binSig, String gndSig) {
    int seq = seqRef[0];

    // 9's complement of B: b9_0=¬b0, b9_1=b1, b9_2=b2⊕b1, b9_3=¬(b3⊕(b2∨b1)).
    String n0 = prefix + "_n0";
    String x2 = prefix + "_x2";
    String o = prefix + "_o";
    String x3 = prefix + "_x3";
    String n3 = prefix + "_n3";
    components.add(new ComponentSpec(n0, "Gates", "NOT Gate", 1, "N1"));
    components.add(new ComponentSpec(x2, "Gates", "XOR Gate", 1, "X2"));
    components.add(new ComponentSpec(o, "Gates", "OR Gate", 1, "O1"));
    components.add(new ComponentSpec(x3, "Gates", "XOR Gate", 1, "X3"));
    components.add(new ComponentSpec(n3, "Gates", "NOT Gate", 1, "N2"));
    hints.add(new LayoutHint(n0, 0, 0, 0, null));
    hints.add(new LayoutHint(x2, 0, 0, 1, null));
    hints.add(new LayoutHint(o, 0, 0, 2, null));
    hints.add(new LayoutHint(x3, 0, 0, 3, null));
    hints.add(new LayoutHint(n3, 0, 0, 4, null));
    nets.add(net(prefix + "_n0_in", e(bSig[0], 0), e(n0, 1)));
    nets.add(net(prefix + "_x2_x", e(bSig[2], 0), e(x2, 1)));
    nets.add(net(prefix + "_x2_y", e(bSig[1], 0), e(x2, 2)));
    nets.add(net(prefix + "_o_x", e(bSig[2], 0), e(o, 1)));
    nets.add(net(prefix + "_o_y", e(bSig[1], 0), e(o, 2)));
    nets.add(net(prefix + "_x3_w", e(bSig[3], 0), e(x3, 1)));
    nets.add(net(prefix + "_x3_b", e(o, 0), e(x3, 2)));
    nets.add(net(prefix + "_n3_in", e(x3, 0), e(n3, 1)));
    String[] b9 = {n0, bSig[1], x2, n3};

    // Stage 1: T = A + b9 (plain ripple, no correction yet).
    String[] t = new String[4];
    String c = gndSig;
    for (int i = 0; i < 4; i++) {
      String x = prefix + "_x1_" + i;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "X1" + (i + 1)));
      hints.add(new LayoutHint(x, 1, 0, i, null));
      String s = prefix + "_s1_" + i;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "S1" + (i + 1)));
      hints.add(new LayoutHint(s, 1, 0, i, null));
      String fa = prefix + "_f1_" + i;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "A1" + (i + 1)));
      hints.add(new LayoutHint(fa, 1, 0, i, null));
      String fb = prefix + "_b1_" + i;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "B1" + (i + 1)));
      hints.add(new LayoutHint(fb, 1, 0, i, null));
      String fo = prefix + "_o1_" + i;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "C1" + (i + 1)));
      hints.add(new LayoutHint(fo, 1, 0, i, null));
      String fg = prefix + "_g1_" + i;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "G1" + (i + 1)));
      hints.add(new LayoutHint(fg, 1, 0, i, null));
      nets.add(net(prefix + "_x1a" + seq, e(aSig[i], 0), e(x, 1)));
      nets.add(net(prefix + "_x1b" + seq, e(b9[i], 0), e(x, 2)));
      nets.add(net(prefix + "_s1a" + seq, e(x, 0), e(s, 1)));
      nets.add(net(prefix + "_s1b" + seq, e(c, 0), e(s, 2)));
      t[i] = s;
      nets.add(net(prefix + "_f1a" + seq, e(aSig[i], 0), e(fa, 1)));
      nets.add(net(prefix + "_f1b" + seq, e(b9[i], 0), e(fa, 2)));
      nets.add(net(prefix + "_b1a" + seq, e(c, 0), e(fb, 1)));
      nets.add(net(prefix + "_b1b" + seq, e(x, 0), e(fb, 2)));
      nets.add(net(prefix + "_o11" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net(prefix + "_o12" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net(prefix + "_g11" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net(prefix + "_g12" + seq, e(gndSig, 0), e(fg, 2)));
      c = fg;
      seq++;
    }
    String tCarry = c;

    // NOT Bin: bit0's stage-3 addend = (1 - Bin).
    String nbin = prefix + "_nbin";
    components.add(new ComponentSpec(nbin, "Gates", "NOT Gate", 1, "NBIN"));
    hints.add(new LayoutHint(nbin, 2, 0, 0, null));
    nets.add(net(prefix + "_nbin_in", e(binSig, 0), e(nbin, 1)));

    // Stage 3: S = T + (1 - Bin); carry chain from 0.
    String[] d = new String[4];
    String c3 = gndSig;
    for (int i = 0; i < 4; i++) {
      String x = prefix + "_x3_" + i;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "X3" + (i + 1)));
      hints.add(new LayoutHint(x, 4, 0, i, null));
      String s = prefix + "_s3_" + i;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "S3" + (i + 1)));
      hints.add(new LayoutHint(s, 4, 0, i, null));
      String fa = prefix + "_f3_" + i;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "A3" + (i + 1)));
      hints.add(new LayoutHint(fa, 4, 0, i, null));
      String fb = prefix + "_b3_" + i;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "B3" + (i + 1)));
      hints.add(new LayoutHint(fb, 4, 0, i, null));
      String fo = prefix + "_o3_" + i;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "C3" + (i + 1)));
      hints.add(new LayoutHint(fo, 4, 0, i, null));
      String fg = prefix + "_g3_" + i;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "G3" + (i + 1)));
      hints.add(new LayoutHint(fg, 4, 0, i, null));
      String bS = (i == 0) ? nbin : gndSig;
      nets.add(net(prefix + "_x3a" + seq, e(t[i], 0), e(x, 1)));
      nets.add(net(prefix + "_x3b" + seq, e(bS, 0), e(x, 2)));
      nets.add(net(prefix + "_s3a" + seq, e(x, 0), e(s, 1)));
      nets.add(net(prefix + "_s3b" + seq, e(c3, 0), e(s, 2)));
      d[i] = s;
      nets.add(net(prefix + "_f3a" + seq, e(t[i], 0), e(fa, 1)));
      nets.add(net(prefix + "_f3b" + seq, e(bS, 0), e(fa, 2)));
      nets.add(net(prefix + "_b3a" + seq, e(c3, 0), e(fb, 1)));
      nets.add(net(prefix + "_b3b" + seq, e(x, 0), e(fb, 2)));
      nets.add(net(prefix + "_o31" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net(prefix + "_o32" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net(prefix + "_g31" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net(prefix + "_g32" + seq, e(gndSig, 0), e(fg, 2)));
      c3 = fg;
      seq++;
    }
    String dCarry = c3;

    // adj = (tCarry | dCarry) | (d3 & (d2 | d1))
    String go = prefix + "_adj2_o" + seq;
    components.add(new ComponentSpec(go, "Gates", "OR Gate", 1, "B2J" + (seq + 1)));
    hints.add(new LayoutHint(go, 5, 0, 0, null));
    String ga = prefix + "_adj2_a" + seq;
    components.add(new ComponentSpec(ga, "Gates", "AND Gate", 1, "B2K" + (seq + 1)));
    hints.add(new LayoutHint(ga, 5, 0, 1, null));
    String gg = prefix + "_adj2_g" + seq;
    components.add(new ComponentSpec(gg, "Gates", "OR Gate", 1, "B2L" + (seq + 1)));
    hints.add(new LayoutHint(gg, 5, 0, 2, null));
    String tc = prefix + "_adj2_tc" + seq;
    components.add(new ComponentSpec(tc, "Gates", "OR Gate", 1, "B2T" + (seq + 1)));
    hints.add(new LayoutHint(tc, 5, 0, 3, null));
    seq++;
    nets.add(net(prefix + "_adj2_g1", e(d[2], 0), e(gg, 1)));
    nets.add(net(prefix + "_adj2_g2", e(d[1], 0), e(gg, 2)));
    nets.add(net(prefix + "_adj2_a1", e(gg, 0), e(ga, 1)));
    nets.add(net(prefix + "_adj2_a2", e(d[3], 0), e(ga, 2)));
    nets.add(net(prefix + "_adj2_tc1", e(tCarry, 0), e(tc, 1)));
    nets.add(net(prefix + "_adj2_tc2", e(dCarry, 0), e(tc, 2)));
    nets.add(net(prefix + "_adj2_o1", e(tc, 0), e(go, 1)));
    nets.add(net(prefix + "_adj2_o2", e(ga, 0), e(go, 2)));
    String adj2 = go;

    // Stage 4: D = d + adj2*6 mod 16 (bits1..3 add adj2).
    String[] dFinal = new String[4];
    String c4 = gndSig;
    for (int i = 0; i < 4; i++) {
      String x = prefix + "_x4_" + i;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "X4" + (i + 1)));
      hints.add(new LayoutHint(x, 5, 0, i, null));
      String s = prefix + "_s4_" + i;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "S4" + (i + 1)));
      hints.add(new LayoutHint(s, 5, 0, i, null));
      String fa = prefix + "_f4_" + i;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "A4" + (i + 1)));
      hints.add(new LayoutHint(fa, 5, 0, i, null));
      String fb = prefix + "_b4_" + i;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "B4" + (i + 1)));
      hints.add(new LayoutHint(fb, 5, 0, i, null));
      String fo = prefix + "_o4_" + i;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "C4" + (i + 1)));
      hints.add(new LayoutHint(fo, 5, 0, i, null));
      String fg = prefix + "_g4_" + i;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "G4" + (i + 1)));
      hints.add(new LayoutHint(fg, 5, 0, i, null));
      String bS = (i == 0 || i == 3) ? gndSig : adj2;
      nets.add(net(prefix + "_x4a" + seq, e(d[i], 0), e(x, 1)));
      nets.add(net(prefix + "_x4b" + seq, e(bS, 0), e(x, 2)));
      nets.add(net(prefix + "_s4a" + seq, e(x, 0), e(s, 1)));
      nets.add(net(prefix + "_s4b" + seq, e(c4, 0), e(s, 2)));
      dFinal[i] = s;
      nets.add(net(prefix + "_f4a" + seq, e(d[i], 0), e(fa, 1)));
      nets.add(net(prefix + "_f4b" + seq, e(bS, 0), e(fa, 2)));
      nets.add(net(prefix + "_b4a" + seq, e(c4, 0), e(fb, 1)));
      nets.add(net(prefix + "_b4b" + seq, e(x, 0), e(fb, 2)));
      nets.add(net(prefix + "_o41" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net(prefix + "_o42" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net(prefix + "_g41" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net(prefix + "_g42" + seq, e(gndSig, 0), e(fg, 2)));
      c4 = fg;
      seq++;
    }

    // borrow out = NOT adj2
    String nb = prefix + "_nb";
    components.add(new ComponentSpec(nb, "Gates", "NOT Gate", 1, "NB"));
    hints.add(new LayoutHint(nb, 6, 0, 0, null));
    nets.add(net(prefix + "_nb_in", e(adj2, 0), e(nb, 1)));
    seqRef[0] = seq;
    return new String[] {dFinal[0], dFinal[1], dFinal[2], dFinal[3], nb};
  }
  private static void addInput(
      List<ExternalPort> ports, String id, String label, String target) {
    ports.add(new ExternalPort(id, label, 1, PortDirection.INPUT, PortSide.LEFT, target, 0));
  }

  private static void addClockInput(
      List<ExternalPort> ports, String id, String label, String target) {
    ports.add(new ExternalPort(id, label, 1, PortDirection.INPUT, PortSide.LEFT, target, 0, true));
  }

  private static void addOutput(
      List<ExternalPort> ports, String id, String label, String target) {
    ports.add(new ExternalPort(id, label, 1, PortDirection.OUTPUT, PortSide.RIGHT, target, 0));
  }

  private static EndpointRef e(String componentId, int endIndex) {
    return new EndpointRef(componentId, endIndex);
  }

  /** Smallest power of two ≥ n (for MUX widths; 3 → 4, 5 → 8). */
  private static int pow2Ceil(int n) {
    int r = 1;
    while (r < n) {
      r <<= 1;
    }
    return r;
  }

  private static NetSpec net(String id, EndpointRef... endpoints) {
    return new NetSpec(id, List.of(endpoints));
  }
}

