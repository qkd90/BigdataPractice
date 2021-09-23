package com.data.data.hmly.service.outOrder.entity.enums;

/**
 * Created by dy on 2016/2/24.
 */
public enum JszxOrderDetailStatus {

    UNUSED("未使用"), CANCEL("已取消"), REFUNDING("退款中"), USED("已使用"), CHECKIN("已入住"), CHECKOUT("已退房");

    private String description;

    JszxOrderDetailStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
