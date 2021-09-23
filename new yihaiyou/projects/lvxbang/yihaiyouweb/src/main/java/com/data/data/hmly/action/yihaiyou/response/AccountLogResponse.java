package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.balance.entity.AccountLog;
import com.data.data.hmly.service.common.util.DateUtils;

/**
 * Created by huangpeijie on 2016-08-30,0030.
 */
public class AccountLogResponse {
    private String status;
    private Double money;
    private Double balance;
    private String description;
    private String createTime;
    private String createWeek;
    private String type;
    private String orderType;
    private String orderTypeDesc;

    public AccountLogResponse() {
    }

    public AccountLogResponse(AccountLog accountLog) {
        this.status = accountLog.getStatus().getDescription();
        this.money = accountLog.getMoney();
        this.balance = accountLog.getBalance();
        this.description = accountLog.getDescription();
        this.createTime = DateUtils.format(accountLog.getCreateTime(), "MM-dd");
        this.createWeek = DateUtils.getWeek(DateUtils.getWeek(accountLog.getCreateTime()) - 1);
        this.type = accountLog.getType().name();
        if (accountLog.getOrderType() != null) {
            this.orderType = accountLog.getOrderType().name();
            this.orderTypeDesc = accountLog.getOrderType().getDescription();
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeDesc() {
        return orderTypeDesc;
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
    }

    public String getCreateWeek() {
        return createWeek;
    }

    public void setCreateWeek(String createWeek) {
        this.createWeek = createWeek;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
