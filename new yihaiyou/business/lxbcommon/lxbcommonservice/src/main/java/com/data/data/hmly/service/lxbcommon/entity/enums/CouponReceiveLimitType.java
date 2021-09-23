package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by zzl on 2016/5/9.
 */
public enum CouponReceiveLimitType {

    code("仅可通过优惠券码兑换"), num("通过领取数量限制"), none("无领取限制");

    private String description;

    CouponReceiveLimitType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
