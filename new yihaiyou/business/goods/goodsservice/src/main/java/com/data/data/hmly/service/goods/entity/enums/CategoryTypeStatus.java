package com.data.data.hmly.service.goods.entity.enums;

/**
 * Created by zzl on 2016/4/19.
 */
public enum CategoryTypeStatus {
    SHOW("展示"), HIDE("隐藏"), DEL("删除");

    private String description;

    CategoryTypeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
