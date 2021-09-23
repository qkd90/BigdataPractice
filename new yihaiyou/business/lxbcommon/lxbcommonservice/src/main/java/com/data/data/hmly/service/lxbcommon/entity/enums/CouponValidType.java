package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by zzl on 2016/5/9.
 */
public enum CouponValidType {
    days("领取日后固定有效天数"), range("固定范围有效期"), forever("永久有效");

    private String description;

    CouponValidType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
