package com.data.data.hmly.service.wechat.entity.EntityData;

import java.util.Date;

/**
 * Created by dy on 2016/5/17.
 */
public class StatisticsQrcode {

    private Long id;
    private String account;
    private Long accountId;
    private String name;
    private String sceneStr;
    private Integer subCount;       // 已关注数量
    private Date subLastTime;       // 最新关注时间
    private Integer unsubCount;     // 已取消关注数量
    private Date unsubLastTime;       // 最新取消关注时间
    private Integer hadSubCount;    // 已关注过的数量

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnsubCount() {
        return unsubCount;
    }

    public void setUnsubCount(Integer unsubCount) {
        this.unsubCount = unsubCount;
    }

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

    public Integer getHadSubCount() {
        return hadSubCount;
    }

    public void setHadSubCount(Integer hadSubCount) {
        this.hadSubCount = hadSubCount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getSceneStr() {
        return sceneStr;
    }

    public void setSceneStr(String sceneStr) {
        this.sceneStr = sceneStr;
    }

    public Date getSubLastTime() {
        return subLastTime;
    }

    public void setSubLastTime(Date subLastTime) {
        this.subLastTime = subLastTime;
    }

    public Date getUnsubLastTime() {
        return unsubLastTime;
    }

    public void setUnsubLastTime(Date unsubLastTime) {
        this.unsubLastTime = unsubLastTime;
    }
}
