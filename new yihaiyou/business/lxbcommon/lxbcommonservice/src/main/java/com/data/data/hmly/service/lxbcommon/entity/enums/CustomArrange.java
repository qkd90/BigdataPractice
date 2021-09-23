package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by caiys on 2016/6/15.
 * 定制需求-行程安排
 */
public enum CustomArrange {
    compact("紧凑"), moderate("适中"), loose("宽松"), unsure("不确定");

    private String description;

    CustomArrange(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
