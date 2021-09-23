package com.data.data.hmly.service.ctripcommon.enums;

/**
 * Created by caiys on 2016/1/27.
 */
public enum TagTargetType {
    SCENIC("景点"), TICKET_RESCOURCE("门票资源");

    private String description;

    TagTargetType(String s) {
        this.description = s;
    }

    public String getDescription() {
        return this.description;
    }
}
