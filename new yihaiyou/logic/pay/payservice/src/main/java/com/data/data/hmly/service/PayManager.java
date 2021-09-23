package com.data.data.hmly.service;

import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.pay.PayService;
import com.data.data.hmly.service.pay.TaobaoPayService;
import com.data.data.hmly.service.pay.UlineAliPayService;
import com.data.data.hmly.service.pay.UlineWeixinPayService;
import com.data.data.hmly.service.pay.WeixinPayService;

public enum PayManager {
    taobao("taobao", new TaobaoPayService()), weixin(OrderPayType.WECHAT.name(), new WeixinPayService()),
    ULINEWECHAT(OrderPayType.ULINEWECHAT.name(), new UlineWeixinPayService()), ULINEALIPAY(OrderPayType.ULINEALIPAY.name(), new UlineAliPayService());

    private PayManager(String tag, PayService payService) {
        this.tag = tag;
        this.payService = payService;
    }

    private String tag;
    private PayService payService;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public PayService getPayService() {
        return payService;
    }

    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    public static PayManager findService(String tag) {
        for (PayManager payManager : values()) {
            if (payManager.getTag().equals(tag)) {
                return payManager;
            }
        }
        return null;
    }

}
