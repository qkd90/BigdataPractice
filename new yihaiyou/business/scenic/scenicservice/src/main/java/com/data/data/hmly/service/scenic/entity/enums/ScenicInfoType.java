package com.data.data.hmly.service.scenic.entity.enums;

/**
 * Created by zzl on 2016/9/20.
 */
public enum ScenicInfoType {
    scenic("景点"), sailboat("帆船"), yacht("游艇"), lvji("驴迹");

    private String description;

    ScenicInfoType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
