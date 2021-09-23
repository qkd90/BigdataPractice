package com.data.data.hmly.service.entity;

/**
 * Created by guoshijie on 2015/11/4.
 */
public enum ShareType {
    Timeline("朋友圈"), AppMessage("朋友"), QQ("QQ"), QZone("锁定"), Weibo("腾讯微博");

    private String description;

    ShareType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
