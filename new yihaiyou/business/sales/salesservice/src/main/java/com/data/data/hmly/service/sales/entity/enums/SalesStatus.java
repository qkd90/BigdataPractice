package com.data.data.hmly.service.sales.entity.enums;

/**
 * Created by zzl on 2016/7/6.
 */
public enum SalesStatus {
    up("上架 "), down("下架");

    private String description;

    SalesStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
