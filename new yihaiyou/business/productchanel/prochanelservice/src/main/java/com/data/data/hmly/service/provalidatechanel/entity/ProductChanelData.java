package com.data.data.hmly.service.provalidatechanel.entity;

import com.data.data.hmly.service.provalidatechanel.entity.enums.ChanelType;

import java.util.Date;

/**
 * Created by dy on 2016/9/2.
 */
public class ProductChanelData {

    private Long id;
    private String name;
    private Long channelId;
    private ChanelType chanel;
    private Long userId;
    private String userName;
    private Date updateTime;

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

    public ChanelType getChanel() {
        return chanel;
    }

    public void setChanel(ChanelType chanel) {
        this.chanel = chanel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}
