package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by zzl on 2016/4/15.
 */
public enum FeedBackStatus {

    OPEN("未回复"), REPLYED(" 已回复"), DEL("已删除"), CLOSED("已关闭");
    private String description;

    FeedBackStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
