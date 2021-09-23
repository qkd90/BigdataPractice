package com.data.data.hmly.service.entity;

/**
 * Created by guoshijie on 2015/11/4.
 */
public enum UserStatus {
    activity("活跃"), lock("锁定"), del("删除"), blacklist("黑名单");

    private String description;

    UserStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
