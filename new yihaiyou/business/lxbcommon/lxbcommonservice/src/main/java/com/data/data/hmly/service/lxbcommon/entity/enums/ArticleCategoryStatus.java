package com.data.data.hmly.service.lxbcommon.entity.enums;

/**
 * Created by zzl on 2016/4/13.
 */
public enum ArticleCategoryStatus {

    UP("上架"), DOWN("下架"), DEL("删除");

    private String description;

    ArticleCategoryStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
