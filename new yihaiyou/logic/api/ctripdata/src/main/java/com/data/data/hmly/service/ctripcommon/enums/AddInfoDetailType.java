package com.data.data.hmly.service.ctripcommon.enums;

/**
 * Created by caiys on 2016/2/1.
 */
public enum AddInfoDetailType {
    PRODUCT("产品"), TICKET_RESCOURCE("门票资源");

    private String description;

    AddInfoDetailType(String s) {
        this.description = s;
    }

    public String getDescription() {
        return this.description;
    }
}
