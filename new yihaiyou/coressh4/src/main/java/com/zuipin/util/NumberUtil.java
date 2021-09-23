package com.zuipin.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class NumberUtil {

    /**
     * @param value
     * @param maxInteger
     *            整数位数
     * @param minInteger
     *            整数位数
     * @param maxFranc
     *            小数位数
     * @param minFranc
     *            小数位数
     * @return
     */
    public static String format(Float value, Integer maxInteger, Integer minInteger, Integer maxFranc, Integer minFranc) {
        if (value == null)
            return null;
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(maxFranc);

        percentFormat.setMaximumIntegerDigits(maxInteger);

        percentFormat.setMinimumFractionDigits(minFranc);

        percentFormat.setMinimumIntegerDigits(minInteger);

        return percentFormat.format(value);// 自动转换成百分比显示..
    }

    /**
     * 简易生成流水号：12位时间+3位随机数
     * @return
     */
    public static String getRunningNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        StringBuilder sb = new StringBuilder(format.format(new Date()));
        Random r = new Random();
        int i = r.nextInt(1000);
        String suffixNo = StringUtils.paddingLeft(String.valueOf(i), '0', 3);
        sb.append(suffixNo);
        return sb.toString();
    }

    public static String getRandomNo() {

        Random r = new Random();

        Integer no = 0;
        while (true) {
            no = r.nextInt(1000000000);
            if (no > 100000000) {
                break;
            }
        }
        return no.toString();
    }

    public static Integer getRandomScore() {
        Random r = new Random();
        Integer no = 0;
        while (true) {
            no = r.nextInt(100);
            if (no > 60) {
                break;
            }
        }
        return no;
    }

}
