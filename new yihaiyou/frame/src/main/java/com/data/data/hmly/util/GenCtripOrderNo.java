package com.data.data.hmly.util;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.Random;

/**
 * Created by caiys on 2016/11/12.
 */
public class GenCtripOrderNo {
    public static final Integer INIT_LIMIT_NO = 100000;    // 初始最大值限制，超过时订单位数会增加
    private static Integer initRandomNo = null;   // 初始随机数
    private static String curDateShort = null;  // 当前9位时间戳：13位时间戳去掉第1位和尾数3位
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
     * 规则说明：9位时间戳+1位校验码+5位唯一编号（位数可变）
     * 使用范围：满足32年（2049年后）内订单数在十万/秒以下（超过该量级位数会相应增加）的15位订单编号
     * @param machineNo 服务器机器编号，取自配置文件：propertiesManager.getString("MACHINE_NO", "");
     * @return
     */
    public static synchronized String generate(final String machineNo) {
        // 当前9位时间戳：13位时间戳去掉第1位和尾数3位
        Long date = new Date().getTime();
        String dateShort = String.valueOf(date).substring(1, 10);
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

}
