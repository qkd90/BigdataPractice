package com.zuipin.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zzl on 2016/5/10.
 */
public class CouponCodeUtils {

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    private static final Map<Character, Integer> DIGIT_MAP = new HashMap<Character, Integer>();

    static {
        for (int i = 0; i < DIGITS.length; i++) {
            DIGIT_MAP.put(DIGITS[i], i);
        }
    }

    /**
     * 支持的最大进制数
     */
    private static final int MAX_RADIX = DIGITS.length;

    /**
     * 支持的最小进制数
     */
    private static final int MIN_RADIX = 2;

    /**
     * 将长整型数值转换为指定的进制数（最大支持62进制，字母数字已经用尽）
     *
     * @param i
     * @param radix
     * @return
     */
    private static String toString(long i, int radix) {
        if (radix < MIN_RADIX || radix > MAX_RADIX)
            radix = 10;
        if (radix == 10)
            return Long.toString(i);

        final int size = 65;
        int charPos = 64;

        char[] buf = new char[size];
        boolean negative = i < 0;

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = DIGITS[(int) (-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = DIGITS[(int) (-i)];

        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, size - charPos);
    }

    static NumberFormatException forInputString(String s) {
        return new NumberFormatException("For input string: \"" + s + "\"");
    }

    /**
     * 将字符串转换为长整型数字
     *
     * @param s     数字字符串
     * @param radix 进制数
     * @return
     */
    private static long toNumber(String s, int radix) {
        if (s == null) {
            throw new NumberFormatException("null");
        }

        if (radix < MIN_RADIX) {
            throw new NumberFormatException("radix " + radix
                    + " less than Numbers.MIN_RADIX");
        }
        if (radix > MAX_RADIX) {
            throw new NumberFormatException("radix " + radix
                    + " greater than Numbers.MAX_RADIX");
        }

        long result = 0;
        boolean negative = false;
        int i = 0;
        int len = s.length();
        long limit = -Long.MAX_VALUE;
        long multmin;
        Integer digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < '0') {
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                } else if (firstChar != '+')
                    throw forInputString(s);

                if (len == 1) {
                    throw forInputString(s);
                }
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                digit = DIGIT_MAP.get(s.charAt(i++));
                if (digit == null) {
                    throw forInputString(s);
                }
                if (digit < 0) {
                    throw forInputString(s);
                }
                if (result < multmin) {
                    throw forInputString(s);
                }
                result *= radix;
                if (result < limit + digit) {
                    throw forInputString(s);
                }
                result -= digit;
            }
        } else {
            throw forInputString(s);
        }
        return negative ? result : -result;
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return CouponCodeUtils.toString(hi | (val & (hi - 1)), CouponCodeUtils.MAX_RADIX)
                .substring(1);
    }

    /**
     * 以62进制（字母加数字）生成19位UUID，最短的UUID
     * 优惠券码获取
     *
     * @return
     */
    public static String getCouponCode() {
        UUID uuid = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
        sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
        sb.append(digits(uuid.getMostSignificantBits(), 4));
        sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
        sb.append(digits(uuid.getLeastSignificantBits(), 12));
        sb.insert(5, "-");
        sb.insert(11, "-");
        sb.insert(17, "-");
        return sb.toString();
    }

    public static String getCodeWithoutSplit() {
        UUID uuid = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
        sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
        sb.append(digits(uuid.getMostSignificantBits(), 4));
        sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
        sb.append(digits(uuid.getLeastSignificantBits(), 12));
        return sb.toString();
    }

    /**
     * 优惠券码获取
     * 备用方法
     * @param id
     * @return
     */
    @Deprecated
    public static String getCouponCode2(Long id) {
        Integer rr = (int) (Math.random() * (System.currentTimeMillis() - 1000) + 100000);
        Integer r0 = (int) (Math.random() * (System.currentTimeMillis() - rr) + rr);
        Long stamp = System.currentTimeMillis() + System.nanoTime() + r0;
        stamp += id;
        String stampCode = Long.toString(stamp);
        Integer r1 = (int) (Math.random() * (65 - 49) + 49);
        Integer r2 = (int) (Math.random() * (33 - 17) + 17);
        char[] chars = stampCode.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i <= 5 && i % 2 == 0) {
                chars[i] += r1;
            } else if (i <= 5 && i % 2 != 0) {
                chars[i] += r2;
            } else if (i > 5 && i <= 9 && i % 2 != 0) {
                chars[i] += r1;
            } else if (i > 5 && i <= 9 && i % 2 == 0) {
                chars[i] += r2;
            }
        }
        String newStamp = new String(chars);
        StringBuilder sb = new StringBuilder(newStamp);
        sb.insert(5, "-");
        sb.insert(11, "-");
        sb.append(id);
        return sb.toString();
    }
}
