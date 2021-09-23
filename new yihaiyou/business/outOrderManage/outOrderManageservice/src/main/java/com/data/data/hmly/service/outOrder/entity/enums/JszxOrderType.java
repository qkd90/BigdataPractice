package com.data.data.hmly.service.outOrder.entity.enums;

/**
 * Created by zzl on 2016/10/14.
 */
public enum  JszxOrderType {
    sailboat("帆船订单"), yacht("游艇订单"), scenic("门票订单");

    private String description;

    JszxOrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
