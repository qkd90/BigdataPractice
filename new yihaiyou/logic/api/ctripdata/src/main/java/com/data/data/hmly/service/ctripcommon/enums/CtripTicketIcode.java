package com.data.data.hmly.service.ctripcommon.enums;

/**
 * 携程门票icode
 * Created by caiys on 2016/1/26.
 */
public enum CtripTicketIcode {
    //SCENICSPOT_ID("3e756f999d0a419eac8ed257e9a4cf5b", "门票景点查询"),
    //SCENICSPOT_INFO("07310f189af1477aadcf8ca45f8171a4", "门票景点详情"),
    //TICKET_PRICE_CALENDAR("8a813070d06f4482be39ea1f349624c5", "门票价格日历"),
    SCENICSPOT_ID("GET_SCENIC_LIST", "景点详情"),//获取景点ID
    TICKET_ID("GET_SCENIC_TICKET_LIST", "门票详情"),//获取门票ID
    SCENICSPOT_INFO("GET_SCENIC_WITH_TICKET_LIST", "门票景点详情"),
    TICKET_PRICE_CALENDAR("GET_PRICE_AND_INVT", "门票价格日历"),
    ORDER_BOOKING_CHECK("eedc35f1ca1b4751b477496ac00b6f21", "门票可订性检查"),
    CREATE_ORDER("PLACE_ORDER", "门票创建订单"),
    ORDER_CANCEL_CHECK("9ace498c96b8430bbaa7fd45a4ac4c8d", "门票可退检查"),
    ORDER_CANCEL("REFUND_ORDER", "门票退单"),
    ORDER_INFO("GET_ORDER_DETAIL", "门票订单基本信息查询");

    private String icode;
    private String description;

    CtripTicketIcode(String icode, String description) {
        this.icode = icode;
        this.description = description;
    }

    public String getIcode() {
        return icode;
    }

    public String getDescription() {
        return this.description;
    }
}
