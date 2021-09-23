package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by zzl on 2016/5/9.
 */
public enum CouponStatus {
    open("领取中"), closed("已关闭"), expired("已过期");

    private String description;

    CouponStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
