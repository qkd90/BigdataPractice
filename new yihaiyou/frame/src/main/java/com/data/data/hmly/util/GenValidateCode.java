package com.data.data.hmly.util;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.Random;

/**
 * Created by caiys on 2016/11/12.
 */
public class GenValidateCode {
    public static final Integer INIT_LIMIT_NO = 1000;    // 初始最大值限制，超过时位数会增加
    private static Integer initRandomNo = null;   // 初始随机数
    private static String curDateShort = null;  // 当前9位时间戳：13位时间戳去掉第2位和尾数3位
    private static Integer curRandomNo = null;   // 当前随机数

    /**
     * 测试函数
     * @param args
     * @throws InterruptedException
     */
//    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 20; i++) {
//            System.out.println(generate("1"));
//            if (i == 10) {
//                Thread.sleep(1000L);
//            }
//        }
//    }

    /**
     * 规则说明：8位时间戳+1位校验码+3位唯一编号（位数可变）
     * 使用范围：满足三个月内码数在千/秒以下（超过该量级位数会相应增加）的12位验证码
     * @param machineNo 服务器机器编号，取自配置文件：propertiesManager.getString("MACHINE_NO", "");
     * @return
     */
    public static synchronized String generate(final String machineNo) {
        // 当前8位时间戳：13位时间戳去掉第2位和尾数3位
        Long date = new Date().getTime();
        String dateShort = String.valueOf(date).substring(2, 10);
        if (!dateShort.equals(curDateShort)) {  // 如果两个时间不等，重新设置时间戳和随机数
            curDateShort = dateShort;
            Random random = new Random();
            initRandomNo = random.nextInt(INIT_LIMIT_NO);
            curRandomNo = initRandomNo;
        } else {    // 否则随机数加1，如果随机数已不能满足当前订单数，则位数会增加
            if (curRandomNo.intValue() >= INIT_LIMIT_NO.intValue()) {
                curRandomNo = curRandomNo + 1;
            } else {
                curRandomNo = (curRandomNo + 1) % INIT_LIMIT_NO;
                if (curRandomNo.intValue() == initRandomNo.intValue()) {  // 随机数已不能满足当前订单数
                    curRandomNo = INIT_LIMIT_NO;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(curDateShort).append(StringUtils.defaultIfEmpty(machineNo, ""));
        // 位数不够补0
        int limitNum = String.valueOf(INIT_LIMIT_NO).length() - 1;
        int curNum = String.valueOf(curRandomNo).length();
        while (curNum < limitNum) {
            sb.append("0");
            curNum++;
        }
        sb.append(curRandomNo);
        return sb.toString();
    }

    /**
     * 短信验证码生成
     * 创建指定数量的随机字符串
     * @param allNumberFlag 是否全部是数字
     * @param length
     * @return
     */
    public static String createSMSCode(boolean allNumberFlag, int length) {
        String retStr = "";
        String strTable = allNumberFlag ? "0123456789" : "0123456789ABCDEFGHIJKMNPQRSTUVWXYZ";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }
}
