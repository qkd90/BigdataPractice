package com.data.data.hmly.service.common.entity.enums;

/**
 * Created by zzl on 2016/11/23.
 */
public enum ShowStatus {

    SHOW("显示"), HIDE_FOR_CHECK("审核隐藏");

    ShowStatus(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
