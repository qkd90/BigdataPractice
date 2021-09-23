package com.zuipin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CpsConstants {
    public static final String              COOKIE_CPS           = "cps";
    public static final String              COOKIE_QQ_CPS        = "qq_cps";
    public static final Integer             COOKIE_EXPIRY        = 3600 * 24 * 30;
    public static final String              COOKIE_DELIMITER     = "|";
    public static final String              REQUEST_DELIMITER    = "|";
    public static final String              QUERY_DELIMITER      = "||";
    public static final String              URL                  = "http://o.yiqifa.com/servlet/handleCpsIn";
    public static final String              TEST_URL             = "http://o.test.yiqifa.com/servlet/handleCpsIn";
    public static final String              ENCODING             = "gbk";
    public static final String              METHOD               = "GET";
    public static final String              PROMO_CODE           = "返现抵扣|优惠券";
    public static final Map<String, String> COMMISSION_CATEGORY1 = new HashMap<String, String>();
    public static final Map<String, String> COMMISSION_CATEGORY2 = new HashMap<String, String>();
    public static final Map<String, String> COMMISSION_CATEGORY3 = new HashMap<String, String>();
    public static final List<String>        COMMISSION_BLACKLIST = new ArrayList<String>();
    
    // qq登陆 接口
    public static final String              CID                  = "7008";
//    public static final String              WID                  = "438384";
    public static final String              QQMID                = "jxmall";
    public static final String              QQWID                = "435983";
    
    public static final String              CT                   = "qqlogin1";
    public static final String              TA                   = "1";
    public static final String              QQ_REQUEST_DELIMITER = ",";
    public static final String              DT                   = null;
    public static final String              OS                   = null;
    
    static {
        COMMISSION_CATEGORY1.put("2146", "2");
        COMMISSION_CATEGORY1.put("11084", "5");
        COMMISSION_CATEGORY1.put("1634", "4");
        COMMISSION_CATEGORY1.put("5317", "2");
        COMMISSION_CATEGORY1.put("5499", "2");
        COMMISSION_CATEGORY1.put("5390", "3");
        COMMISSION_CATEGORY1.put("1636", "4");
        COMMISSION_CATEGORY1.put("2148", "3");
        COMMISSION_CATEGORY1.put("5435", "8");
        COMMISSION_CATEGORY1.put("5624", "1");
        COMMISSION_CATEGORY1.put("9502", "2");
        
        COMMISSION_CATEGORY2.put("11212", "8");
        COMMISSION_CATEGORY2.put("11211", "4");
        COMMISSION_CATEGORY2.put("11131", "5");
        COMMISSION_CATEGORY2.put("11122", "5");
        COMMISSION_CATEGORY2.put("11160", "5");
        COMMISSION_CATEGORY2.put("1730", "5");
        COMMISSION_CATEGORY2.put("117", "5");
        COMMISSION_CATEGORY2.put("9740", "5");
        
        COMMISSION_CATEGORY3.put("1066", "2");
        COMMISSION_CATEGORY3.put("1854", "5");
        COMMISSION_CATEGORY3.put("1716", "5");
        COMMISSION_CATEGORY3.put("1668", "5");
        COMMISSION_CATEGORY3.put("2168", "5");
        COMMISSION_CATEGORY3.put("11121", "5");
        
        // COMMISSION_BLACKLIST.add("33576");
    }
}
