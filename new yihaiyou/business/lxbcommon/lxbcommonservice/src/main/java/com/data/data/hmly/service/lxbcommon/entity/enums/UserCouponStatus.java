package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by zzl on 2016/5/9.
 */
public enum UserCouponStatus {
    used("已使用"), unused("未使用"), expired("已过期"), unavailable("不可用"), del("已删除");

    private String description;

    UserCouponStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
