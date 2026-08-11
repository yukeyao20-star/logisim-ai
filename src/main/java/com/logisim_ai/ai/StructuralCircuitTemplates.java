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
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:路|位)\\s*(?:选择器|多路选择器|多路复用器)");
  private static final Pattern MUX_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?to\\s*-?1\\s*(?:mux|multiplexer)", Pattern.CASE_INSENSITIVE);
  private static final Pattern DECODER_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:译码器|解码器|二进制译码器)");
  private static final Pattern DECODER_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?to\\s*-?(\\d{1,3})\\s*(?:decoder|demultiplexer)", Pattern.CASE_INSENSITIVE);
  private static final Pattern DECODER_ZH_NN =
      Pattern.compile("(\\d{1,2})\\s*(?:[-－ ])\\s*(\\d{1,3})\\s*(?:译码器|解码器|分配器)");
  private static final Pattern DECODER_ZH_LINE =
      Pattern.compile("(\\d{1,2})\\s*线\\s*(\\d{1,3})\\s*线\\s*(?:译码器|解码器)");
  private static final Pattern DEMUX_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*路\\s*(?:分配器|多路分配器|选择分配器)");
  private static final Pattern DEMUX_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?way\\s*(?:demultiplexer|demux|de[- ]?multiplexer)", Pattern.CASE_INSENSITIVE);
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
  private static final Pattern BCD7_ZH =
      Pattern.compile("(?:bcd|二十进制|8421码|8421)\\s*(?:转|到|[-])?\\s*(?:7段|七段|七段数码管|七段显示|数码管)\\s*(?:译码器|解码器|驱动器)?");
  private static final Pattern BCD7_EN =
      Pattern.compile("(?:bcd|binary\\s+coded\\s+decimal)\\s*(?:to)?\\s*7[- ]seg(?:ment)?\\s*(?:decoder|display|driver)", Pattern.CASE_INSENSITIVE);
  private static final Pattern MUL_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*[xX×位]\\s*([0-9一二三四五六七八九十两]{1,3})?\\s*位?\\s*(?:乘法器|二进制乘法器)");
  private static final Pattern MUL_ZH2 =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:乘法器|二进制乘法器)");
  private static final Pattern MUL_EN =
      Pattern.compile("(\\d{1,2})\\s*[xX×]\\s*(\\d{1,2})\\s*(?:multiplier)", Pattern.CASE_INSENSITIVE);
  private static final Pattern SUB_ZH =
      Pattern.compile("([0-9一二三四五六七八九十两]{1,3})\\s*(?:bit|位)\\s*(?:减法器|全减器|二进制减法器)");
  private static final Pattern SUB_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:subtractor|full\\s+subtractor)", Pattern.CASE_INSENSITIVE);
  private static final Pattern BCDADD_ZH =
      Pattern.compile("(?:bcd|二十进制|8421码|8421)\\s*(?:加法器|加法)");
  private static final Pattern BCDADD_EN =
      Pattern.compile("(?:bcd|binary\\s+coded\\s+decimal)\\s*adder", Pattern.CASE_INSENSITIVE);
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
  private static final Pattern SHIFTREG_EN =
      Pattern.compile("(\\d{1,2})\\s*[- ]?bit\\s*(?:shift\\s*register|sipo|piso)", Pattern.CASE_INSENSITIVE);
  private static final Pattern SR_ZH =
      Pattern.compile("(?:sr|s-r|rs|r-s)\\s*(?:触发器|锁存器)|(?:sr|s-r|rs|r-s)\\s*latch", Pattern.CASE_INSENSITIVE);
  private static final Pattern BUZZER_ZH =
      Pattern.compile("抢答器|优先抢答|抢答电路");
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

  /** Human-readable names of the parameterized structural templates, for NL help. */
  public static List<String> supportedStructuralNames() {
    return List.of(
        "Adder 加法器 (N-bit)",
        "Subtractor 减法器 (N-bit)",
        "Multiplier 乘法器 (N×N)",
        "Comparator 比较器 (N-bit)",
        "MUX 多路选择器 (N-to-1)",
        "Decoder 译码器 (N-to-2^N)",
        "Priority encoder 优先编码器 (8-to-3)",
        "Parity 奇偶校验器 (N-bit)",
        "BCD 7-segment 七段显示",
        "BCD adder 8421加法器",
        "D flip-flop D触发器",
        "JK flip-flop JK触发器",
        "T flip-flop T触发器",
        "D latch 透明锁存器",
        "Register 寄存器 (N-bit)",
        "Shift register 移位寄存器 (N-bit)",
        "Bidirectional shift register 双向移位寄存器",
        "Frequency divider 分频器 (N-stage)",
        "BCD counter 十进制计数器",
        "Johnson counter 约翰逊计数器",
        "Gray code 格雷码转换器 (binary→gray)",
        "Gray→binary 格雷码转二进制",
        "Excess-3 余三码 (BCD→excess-3)",
        "Two's complement 补码器 (N-bit)",
        "Traffic light 交通灯控制器",
        "101 sequence detector 序列检测器");
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
      int a = Integer.parseInt(mulEn.group(1));
      int b = Integer.parseInt(mulEn.group(2));
      if (a == b) {
        return Optional.of(buildMultiplier(a));
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
    if (BCDADD_ZH.matcher(t).find() || BCDADD_EN.matcher(t).find()) {
      return Optional.of(buildBcdAdder());
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
        return Optional.of(buildMux(n));
      }
    }
    if (men.find()) {
      return Optional.of(buildMux(Integer.parseInt(men.group(1))));
    }
    Matcher mlu = MUX_LU.matcher(t);
    if (mlu.find()) {
      Integer n = chineseToInt(mlu.group(1));
      if (n != null && n >= 2) {
        return Optional.of(buildMux(n));
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
      return Optional.of(buildDemux(Integer.parseInt(dmen.group(1))));
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
    if (TRAFFIC_ZH.matcher(t).find() || TRAFFIC_EN.matcher(t).find()) {
      return Optional.of(buildTrafficLight());
    }
    if (SEQDET_ZH.matcher(t).find() || SEQDET_EN.matcher(t).find()) {
      // Only the 101 pattern is implemented; reject explicit different
      // sequences like "1101序列检测器" (which would otherwise grab the 101
      // detector via the 序列检测器 substring).
      if (!t.contains("1101") && !t.contains("110") && !t.contains("1010")) {
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
      return Optional.of(buildShiftRegister(Integer.parseInt(shren.group(1))));
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
   * 8-to-3 priority encoder: two 74148? No - single 74148 already is 8-to-3;
   * this template exposes it with proper input/output pins (ends 0..7 = I0..I7,
   * end8 = nEI, end9 = nGS, end10 = nEO, end11..13 = A0..A2).
   */
  static CircuitSpec buildEncoder8to3() {
    List<ComponentSpec> components = List.of(new ComponentSpec("enc", "TTL", "74148", 1, "U1"));
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = List.of(new LayoutHint("enc", 0, 0, 0, null));

    for (int i = 0; i < 8; i++) {
      addInput(ports, "i" + i, "I" + i, "enc");
      nets.add(net("i" + i, e("i" + i, 0), e("enc", i)));
    }
    addInput(ports, "nei", "nEI", "enc");
    nets.add(net("nei", e("nei", 0), e("enc", 8)));
    addOutput(ports, "a0", "A0", "enc");
    addOutput(ports, "a1", "A1", "enc");
    addOutput(ports, "a2", "A2", "enc");
    addOutput(ports, "ngs", "nGS", "enc");
    addOutput(ports, "neo", "nEO", "enc");
    nets.add(net("a0", e("enc", 11), e("a0", 0)));
    nets.add(net("a1", e("enc", 12), e("a1", 0)));
    nets.add(net("a2", e("enc", 13), e("a2", 0)));
    nets.add(net("ngs", e("enc", 9), e("ngs", 0)));
    nets.add(net("neo", e("enc", 10), e("neo", 0)));

    return new CircuitSpec("encoder_8to3", components, ports, nets, hints);
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
      nets.add(net("gx" + i + "_b", e("b" + (i + 1), 0), e(g, 2)));
      nets.add(net("gx" + i + "_o", e(g, 0), e("g" + i, 0)));
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
    String[] segNames = {"a", "b", "c", "d", "e", "f", "g"};

    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();

    for (int i = 0; i < 4; i++) {
      addInput(ports, "b" + i, "B" + i, "seg7_not" + i);
      components.add(new ComponentSpec("seg7_not" + i, "Gates", "NOT Gate", 1, "B7N" + (i + 1)));
      hints.add(new LayoutHint("seg7_not" + i, 0, 0, i, null));
      nets.add(net("seg7_nb" + i, e("b" + i, 0), e("seg7_not" + i, 1)));
    }
    for (int s = 0; s < 7; s++) {
      addOutput(ports, "seg" + s, segNames[s], "seg7_not0");
    }

    int seq = 0;
    for (int s = 0; s < 7; s++) {
      String orAcc = null;
      for (int m = 0; m < segs[s].length; m++) {
        int minterm = segs[s][m];
        String andAcc = null;
        for (int bit = 0; bit < 4; bit++) {
          boolean one = ((minterm >> bit) & 1) == 1;
          String src = one ? "b" + bit : "seg7_not" + bit;
          if (andAcc == null) {
            andAcc = src;
          } else {
            String gate = "seg7_p" + seq;
            components.add(new ComponentSpec(gate, "Gates", "AND Gate", 1, "B7P" + (seq + 1)));
            hints.add(new LayoutHint(gate, 1 + bit, 0, s, null));
            nets.add(net("seg7_pa" + seq, e(andAcc, 0), e(gate, 1)));
            nets.add(net("seg7_pb" + seq, e(src, 0), e(gate, 2)));
            andAcc = gate;
            seq++;
          }
        }
        if (orAcc == null) {
          orAcc = andAcc;
        } else {
          String gate = "seg7_o" + seq;
          components.add(new ComponentSpec(gate, "Gates", "OR Gate", 1, "B7O" + (seq + 1)));
          hints.add(new LayoutHint(gate, 6, 0, s, null));
          nets.add(net("seg7_oa" + seq, e(orAcc, 0), e(gate, 1)));
          nets.add(net("seg7_ob" + seq, e(andAcc, 0), e(gate, 2)));
          orAcc = gate;
          seq++;
        }
      }
      nets.add(net("seg7_out" + s, e(orAcc, 0), e("seg" + s, 0)));
    }

    return new CircuitSpec("bcd7seg", components, ports, nets, hints);
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
        nets.add(net("mul_g1" + seq, e(fo, 0), e(fg, 1)));
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
   * BCD adder: 4-bit binary add A+B+Cin, then add 6 when the sum exceeds 9 or
   * carries out (correction). Outputs S0..S3 (BCD) and Cout. A shared GND
   * input pin ("Z", constant 0) supplies the correction-off lanes.
   */
  static CircuitSpec buildBcdAdder() {
    List<ComponentSpec> components = new ArrayList<>();
    List<ExternalPort> ports = new ArrayList<>();
    List<NetSpec> nets = new ArrayList<>();
    List<LayoutHint> hints = new ArrayList<>();
    int seq = 0;

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

    // stage 0: A + B + Cin -> S (4 bits) + c4
    String[] sum0 = new String[4];
    String c = "cin";
    for (int i = 0; i < 4; i++) {
      String x = "bcdadd_x" + seq;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "BXA" + (seq + 1)));
      hints.add(new LayoutHint(x, 0, 0, i, null));
      String s = "bcdadd_s" + seq;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "BXS" + (seq + 1)));
      hints.add(new LayoutHint(s, 0, 0, i, null));
      String fa = "bcdadd_a" + seq;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "BAA" + (seq + 1)));
      hints.add(new LayoutHint(fa, 0, 0, i, null));
      String fb = "bcdadd_b" + seq;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "BAB" + (seq + 1)));
      hints.add(new LayoutHint(fb, 0, 0, i, null));
      String fo = "bcdadd_o" + seq;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "BAO" + (seq + 1)));
      hints.add(new LayoutHint(fo, 0, 0, i, null));
      String fg = "bcdadd_g" + seq;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "BAG" + (seq + 1)));
      hints.add(new LayoutHint(fg, 0, 0, i, null));
      seq++;
      nets.add(net("bcdadd_xa" + seq, e("a" + i, 0), e(x, 1)));
      nets.add(net("bcdadd_xb" + seq, e("b" + i, 0), e(x, 2)));
      nets.add(net("bcdadd_sa" + seq, e(x, 0), e(s, 1)));
      nets.add(net("bcdadd_sb" + seq, e(c, 0), e(s, 2)));
      nets.add(net("bcdadd_fa" + seq, e("a" + i, 0), e(fa, 1)));
      nets.add(net("bcdadd_fb" + seq, e("b" + i, 0), e(fa, 2)));
      nets.add(net("bcdadd_ga" + seq, e(c, 0), e(fb, 1)));
      nets.add(net("bcdadd_gb" + seq, e(x, 0), e(fb, 2)));
      nets.add(net("bcdadd_o1" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net("bcdadd_o2" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net("bcdadd_g1" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net("bcdadd_g2" + seq, e("z", 0), e(fg, 2)));
      sum0[i] = s;
      c = fg;
    }
    String c4 = c;

    // correction detect: adj = c4 or (S3 and (S2 or S1))
    String o1 = "bcdadd_adj" + seq;
    components.add(new ComponentSpec(o1, "Gates", "OR Gate", 1, "BAJ" + (seq + 1)));
    hints.add(new LayoutHint(o1, 1, 0, 0, null));
    String a1 = "bcdadd_adj2" + seq;
    components.add(new ComponentSpec(a1, "Gates", "AND Gate", 1, "BAK" + (seq + 1)));
    hints.add(new LayoutHint(a1, 1, 0, 1, null));
    String g1 = "bcdadd_adj3" + seq;
    components.add(new ComponentSpec(g1, "Gates", "OR Gate", 1, "BAL" + (seq + 1)));
    hints.add(new LayoutHint(g1, 1, 0, 2, null));
    seq++;
    nets.add(net("bcdadd_s2s1" + seq, e(sum0[2], 0), e(g1, 1)));
    nets.add(net("bcdadd_s1s" + seq, e(sum0[1], 0), e(g1, 2)));
    nets.add(net("bcdadd_adj_a" + seq, e(g1, 0), e(a1, 1)));
    nets.add(net("bcdadd_adj_s3" + seq, e(sum0[3], 0), e(a1, 2)));
    nets.add(net("bcdadd_adj_or1" + seq, e(c4, 0), e(o1, 1)));
    nets.add(net("bcdadd_adj_or2" + seq, e(a1, 0), e(o1, 2)));
    String adj = o1;

    // stage 1: sum0 + (adj ? 6 : 0); bit0 adds 0, bits1..3 add adj
    String[] sumOut = new String[4];
    String cIn2 = "z";
    for (int i = 0; i < 4; i++) {
      String bSig = (i == 0 || i == 3) ? "z" : adj;
      String x = "bcdadd_x2_" + seq;
      components.add(new ComponentSpec(x, "Gates", "XOR Gate", 1, "BX2" + (seq + 1)));
      hints.add(new LayoutHint(x, 2, 0, i, null));
      String s = "bcdadd_s2_" + seq;
      components.add(new ComponentSpec(s, "Gates", "XOR Gate", 1, "BX3" + (seq + 1)));
      hints.add(new LayoutHint(s, 2, 0, i, null));
      String fa = "bcdadd_a2_" + seq;
      components.add(new ComponentSpec(fa, "Gates", "AND Gate", 1, "BA2" + (seq + 1)));
      hints.add(new LayoutHint(fa, 2, 0, i, null));
      String fb = "bcdadd_b2_" + seq;
      components.add(new ComponentSpec(fb, "Gates", "AND Gate", 1, "BB2" + (seq + 1)));
      hints.add(new LayoutHint(fb, 2, 0, i, null));
      String fo = "bcdadd_o2_" + seq;
      components.add(new ComponentSpec(fo, "Gates", "OR Gate", 1, "BO2" + (seq + 1)));
      hints.add(new LayoutHint(fo, 2, 0, i, null));
      String fg = "bcdadd_g2_" + seq;
      components.add(new ComponentSpec(fg, "Gates", "OR Gate", 1, "BG2" + (seq + 1)));
      hints.add(new LayoutHint(fg, 2, 0, i, null));
      seq++;
      nets.add(net("bcdadd_x2a" + seq, e(sum0[i], 0), e(x, 1)));
      nets.add(net("bcdadd_x2b" + seq, e(bSig, 0), e(x, 2)));
      nets.add(net("bcdadd_s2a" + seq, e(x, 0), e(s, 1)));
      nets.add(net("bcdadd_s2b" + seq, e(cIn2, 0), e(s, 2)));
      nets.add(net("bcdadd_f2a" + seq, e(sum0[i], 0), e(fa, 1)));
      nets.add(net("bcdadd_f2b" + seq, e(bSig, 0), e(fa, 2)));
      nets.add(net("bcdadd_g2a" + seq, e(cIn2, 0), e(fb, 1)));
      nets.add(net("bcdadd_g2b" + seq, e(x, 0), e(fb, 2)));
      nets.add(net("bcdadd_o21" + seq, e(fa, 0), e(fo, 1)));
      nets.add(net("bcdadd_o22" + seq, e(fb, 0), e(fo, 2)));
      nets.add(net("bcdadd_g21" + seq, e(fo, 0), e(fg, 1)));
      nets.add(net("bcdadd_g22" + seq, e("z", 0), e(fg, 2)));
      sumOut[i] = s;
      cIn2 = fg;
    }
    nets.add(net("bcdadd_sout0", e(sumOut[0], 0), e("s0", 0)));
    nets.add(net("bcdadd_sout1", e(sumOut[1], 0), e("s1", 0)));
    nets.add(net("bcdadd_sout2", e(sumOut[2], 0), e("s2", 0)));
    nets.add(net("bcdadd_sout3", e(sumOut[3], 0), e("s3", 0)));
    nets.add(net("bcdadd_cout", e(adj, 0), e("cout", 0)));

    return new CircuitSpec("bcd_adder", components, ports, nets, hints);
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

  private static NetSpec net(String id, EndpointRef... endpoints) {
    return new NetSpec(id, List.of(endpoints));
  }
}

