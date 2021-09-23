package com.data.data.hmly.action.mobile.response;

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

    public AccountLogResponse() {
    }

    public AccountLogResponse(AccountLog accountLog) {
        this.status = accountLog.getStatus().getDescription();
        this.money = accountLog.getMoney();
        this.balance = accountLog.getBalance();
        this.description = accountLog.getDescription();
        this.createTime = DateUtils.formatDate(accountLog.getCreateTime());
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
}
