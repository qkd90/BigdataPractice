package com.data.data.hmly.service.line.entity.enums;

/**
 * Created by zzl on 2016/6/6.
 */
public enum LineConfirmAndPayType {
    confirm("需要确认"), noconfirm("无需确认");

    private String description;

    LineConfirmAndPayType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
