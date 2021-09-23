package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by huangpeijie on 2016-09-08,0008.
 */
public enum ShenzhouOrderPaymentStatus {
    unpaid("未支付"), paying("支付处理中"), paymentFailure("支付失败"), partPayment("部分支付"), paid("已支付");

    private String description;

    ShenzhouOrderPaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
