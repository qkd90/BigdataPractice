package com.zuipin.util;

public class GiftBoxConstants {
    
    // 送礼场合变量
    public final static Long     BIRTHDAY_OCCASION   = 1L;
    public final static Long     APPRECIATE_OCCASION = 2L;
    public final static Long     SHOWLOVE_OCCASION   = 3L;
    public final static Long     MARRY_OCCASION      = 4L;
    public final static Long     BEARING_OCCASION    = 5L;
    public final static Long     BUSINESS_OCCASION   = 6L;
    public final static Long     VISIT_OCCASION      = 7L;
    public final static Long     SETUP_OCCASION      = 8L;
    
    // 送礼对象变量
    public final static Long     COLLEAGUE_OBJECT    = 1L;
    public final static Long     SPOUSE_OBJECT       = 2L;
    public final static Long     CHILD_OBJECT        = 3L;
    public final static Long     FRIEND_OBJECT       = 4L;
    public final static Long     CLASSMATE_OBJECT    = 5L;
    public final static Long     ELDER_OBJECT        = 6L;
    public final static Long     PARTNER_OBJECT      = 7L;
    
    // 排序
    public final static String   SALECOUNT_DESC      = "1";
    public final static String   SALECOUNT_ASC       = "2";
    public final static String   SALEPRICE_DESC      = "3";
    public final static String   SALEPRICE_ASC       = "4";
    
    // 价格区间
    public final static String   SALEPRICE_100       = "1";
    public final static String   SALEPRICE_100_200   = "2";
    public final static String   SALEPRICE_200_300   = "3";
    public final static String   SALEPRICE_300_500   = "4";
    public final static String   SALEPRICE_500       = "5";
    
    public final static String   NONE                = "";
    public final static String[] SORTS               = {NONE, SALECOUNT_DESC, SALECOUNT_ASC, SALEPRICE_DESC, SALEPRICE_ASC };
    public final static String[] PRICES              = {NONE, SALEPRICE_100, SALEPRICE_100_200, SALEPRICE_200_300, SALEPRICE_300_500, SALEPRICE_500 };
}
