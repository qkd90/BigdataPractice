package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by zzl on 2016/5/25.
 */
public enum OrderLogLevel {

    debug("调试级"), info("信息级"), warn("警告级"), error("错误级");

    private String description;

    OrderLogLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
