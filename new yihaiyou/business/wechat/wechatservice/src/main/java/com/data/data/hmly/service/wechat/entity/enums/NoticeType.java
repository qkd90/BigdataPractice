package com.data.data.hmly.service.wechat.entity.enums;

/**
 * Created by vacuity on 15/11/19.
 */
public enum NoticeType {
    deliver("发货通知"), order("订单通知"), pay("支付通知"), notice("旅游提示");
    private String description;

    NoticeType(String s) {
        this.description = s;
    }

    public String getDescription() {
        return this.description;
    }
}
