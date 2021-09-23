package com.data.spider.service.baidu;

import com.data.spider.util.MD5Util;

/**
 * Created by Sane on 15/9/17.
 * <p>
 * 获取百度旅游Android客户端验证字符串
 * （由key＋时间戳生成，小写）
 * （时间戳应是最近时间，太久会报实效）
 */

public class BaiduLVCodeService {
    static String key = "lv_app_key_Z%C!Z#KUkAtZsZ[Qog^a[UTXqiee1;I:";

    public static String getLvCode(long timeStamp) {
        return MD5Util.MD5(key + timeStamp).toLowerCase();
    }

    public static void main(String[] args) {
        int timeStamp = 1447328724;
        System.out.println(key + timeStamp);
        String result = new BaiduLVCodeService().getLvCode(timeStamp);
        System.err.println(result);
    }
}
