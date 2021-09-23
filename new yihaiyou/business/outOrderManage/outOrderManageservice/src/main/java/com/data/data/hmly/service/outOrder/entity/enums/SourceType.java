package com.data.data.hmly.service.outOrder.entity.enums;

/**
 * Created by dy on 2016/5/16.
 */
public enum SourceType {


    JSZX("集散中心"), LVXBANG("旅行帮"), HULISHAN("胡里山炮台");

    private String description;

    SourceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
