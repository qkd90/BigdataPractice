package com.data.data.hmly.service.outOrder.entity.enums;

/**
 * Created by dy on 2016/3/3.
 */
public enum JszxOrderStatus {

    CANCELED("已取消"), WAITING("待确认"), UNPAY("待付款"), PAYED("已付款"), UNCANCEL("待取消");

    private String description;

    JszxOrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
