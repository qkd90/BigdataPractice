package com.data.data.hmly.service.common.entity.enums;

/**
 * Created by zzl on 2016/12/19.
 */
public enum MsgTemplateType {
    SMS("短信模板"), APP_PUSH("应用推送消息模板");
    private String description;

    MsgTemplateType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
