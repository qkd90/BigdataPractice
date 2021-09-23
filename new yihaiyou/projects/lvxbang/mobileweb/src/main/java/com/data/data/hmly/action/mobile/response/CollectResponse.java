package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.other.entity.OtherFavorite;

/**
 * Created by huangpeijie on 2016-04-20,0020.
 */
public class CollectResponse {
    private String title;
    private String content;
    private Long targetId;
    private ProductType targetType;
    private Integer browsingNum;
    private String createTime;

    public CollectResponse() {
    }

    public CollectResponse(OtherFavorite otherFavorite) {
        this.title = otherFavorite.getTitle();
        this.content = otherFavorite.getContent();
        this.targetId = otherFavorite.getFavoriteId();
        this.targetType = otherFavorite.getFavoriteType();
        this.createTime = DateUtils.formatShortDate(otherFavorite.getCreateTime());
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

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public ProductType getTargetType() {
        return targetType;
    }

    public void setTargetType(ProductType targetType) {
        this.targetType = targetType;
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
}
