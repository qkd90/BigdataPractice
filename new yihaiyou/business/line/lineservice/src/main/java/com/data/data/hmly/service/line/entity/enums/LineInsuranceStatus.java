package com.data.data.hmly.service.line.entity.enums;

/**
 * Created by zzl on 2016/7/7.
 */
public enum LineInsuranceStatus {

    up("上架"), down("下架");

    private String description;

    LineInsuranceStatus(String s) {
        this.description = s;
    }

    public String getDescription() {
        return this.description;
    }
}
