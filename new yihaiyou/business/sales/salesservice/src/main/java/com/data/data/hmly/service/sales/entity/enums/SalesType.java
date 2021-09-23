package com.data.data.hmly.service.sales.entity.enums;

/**
 * Created by zzl on 2016/7/6.
 */
public enum SalesType {

    insurance("保险");

    private String description;

    SalesType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
