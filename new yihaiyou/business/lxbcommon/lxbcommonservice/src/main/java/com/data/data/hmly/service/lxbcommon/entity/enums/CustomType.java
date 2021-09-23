package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by caiys on 2016/6/15.
 * 定制需求-定制类型
 */
public enum CustomType {
    company("公司"), home("家庭"), personal("个人");

    private String description;

    CustomType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
