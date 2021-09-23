package com.data.data.hmly.service.balance.entity.enums;

/**
 * Created by dy on 2016/3/8.
 */
public enum AccountType {

    consume("消费"), recharge("充值"), refund("退款"), in("入账"), out("出账"), outlinerc("充值"), outlinewd("提现"), withdraw("提现"), running("流水");

    private String description;

    AccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
