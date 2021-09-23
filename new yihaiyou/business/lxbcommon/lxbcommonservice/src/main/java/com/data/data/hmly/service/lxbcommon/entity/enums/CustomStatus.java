package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by caiys on 2016/6/15.
 * 定制需求-状态
 */
public enum CustomStatus {
    handling("处理中"), handled("已处理"), cancel("已取消");

    private String description;

    CustomStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
