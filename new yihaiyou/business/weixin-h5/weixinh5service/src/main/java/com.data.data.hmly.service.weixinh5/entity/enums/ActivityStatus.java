package com.data.data.hmly.service.weixinh5.entity.enums;

/**
 * Created by dy on 2016/2/16.
 */
public enum  ActivityStatus {

    DOWN("下架"), UP("上架"), TEMP("临时");

    private String description;

    ActivityStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
