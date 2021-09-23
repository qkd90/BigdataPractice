package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by zzl on 2016/5/9.
 */
public enum CouponDiscountType {

    discount("打折型优惠券"), money("金额抵扣型优惠券");

    private String description;

    CouponDiscountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
