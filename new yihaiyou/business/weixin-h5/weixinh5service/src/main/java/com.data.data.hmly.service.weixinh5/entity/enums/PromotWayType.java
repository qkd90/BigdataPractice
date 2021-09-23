package com.data.data.hmly.service.weixinh5.entity.enums;

/**
 * Created by dy on 2016/2/17.
 */
public enum PromotWayType {

    sellersend("卖家发放"),buyerget("买家领取");

    private String description;

    PromotWayType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
