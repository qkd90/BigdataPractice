package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by zzl on 2016/5/12.
 */
public enum CouponUseConditionType {

    full("满减"), none("无限制");
    private String description;

    CouponUseConditionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
