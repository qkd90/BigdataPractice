package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by zzl on 2016/10/25.
 */
public enum OrderBillTarget {

    SYSTEM("平台"), SHENZHOU("神州专车"), FERRY("轮渡船票");

    private String description;

    OrderBillTarget(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
