package com.data.data.hmly.enums;

/**
 * Created by huangpeijie on 2016-10-07,0007.
 */
public enum ShenzhouAccessTokenStatus {
    used("可用"), unused("不可用");

    private String description;

    ShenzhouAccessTokenStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
