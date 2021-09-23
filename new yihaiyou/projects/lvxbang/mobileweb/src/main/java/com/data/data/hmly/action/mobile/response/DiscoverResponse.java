package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.action.mobile.enums.DiscoverType;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class DiscoverResponse {
    private Long id;
    private String title;
    private String content;
    private String cover;
    private Integer browsingNum;
    private String createTime;
    private DiscoverType type;
    private Integer commend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getBrowsingNum() {
        return browsingNum;
    }

    public void setBrowsingNum(Integer browsingNum) {
        this.browsingNum = browsingNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public DiscoverType getType() {
        return type;
    }

    public void setType(DiscoverType type) {
        this.type = type;
    }

    public Integer getCommend() {
        return commend;
    }

    public void setCommend(Integer commend) {
        this.commend = commend;
    }
}
