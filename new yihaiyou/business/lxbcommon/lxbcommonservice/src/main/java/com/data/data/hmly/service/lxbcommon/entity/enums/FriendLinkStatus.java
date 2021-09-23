package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by zzl on 2017/3/3.
 */
public enum FriendLinkStatus {
    SHOW("展示"), HIDE("隐藏"), DEL("已删除"), EXPIRED("已过期");
    private String description;

    FriendLinkStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
