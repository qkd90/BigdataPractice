package com.data.data.hmly.service.weixinh5.entity.enums;

/**
 * Created by dy on 2016/2/16.
 */
public enum ActivityType {

    flashsale("限时抢购"),coupon("优惠券");

    private String description;

    ActivityType(String description) {
        this.description = description;
    }

    public String getDescription() {

        return description;
    }
}
