package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by huangpeijie on 2016-08-31,0031.
 */
public enum CmbOrderStatus {
    settled("已结账"), unsettled("未结账"), cancel("撤销"), refund("退款"), wait("待支付");

    CmbOrderStatus(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
