package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by guoshijie on 2015/10/29.
 */
public enum OrderTouristIdType {
	IDCARD("二代身份证"), STUDENTCARD("学生证"), PASSPORT("护照"), OTHER("其他 "), HKMP("港澳通行证"),
    TWP("台湾通行证");

    private String description;

    OrderTouristIdType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
