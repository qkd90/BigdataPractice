package com.data.data.hmly.service.balance.entity.enums;

/**
 * Created by dy on 2016/3/8.
 */
public enum AccountStatus {
    submit("提交"), reject("拒绝"), normal("正常"), processing("处理中"), fail("失败");

    private String description;

    AccountStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
