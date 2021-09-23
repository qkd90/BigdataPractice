package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.other.entity.OtherFavorite;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-04-20,0020.
 */
public class CollectResponse {
    private Long id;
    private String title;
    private String content;
    private Long targetId;
    private ProductType targetType;
    private Integer browsingNum;
    private String createTime;
    private Float price;
    private String startDate;
    private Integer score;
    private Integer satisfaction;
    private String cover;

    public CollectResponse() {
    }

    public CollectResponse(OtherFavorite otherFavorite) {
        this.id = otherFavorite.getId();
        this.title = otherFavorite.getTitle();
        this.content = otherFavorite.getContent();
        this.targetId = otherFavorite.getFavoriteId();
        this.targetType = otherFavorite.getFavoriteType();
        this.createTime = DateUtils.formatShortDate(otherFavorite.getCreateTime());
    }

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover(cover);
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        } else {
            if (cover.startsWith("http")) {
                return cover;
            } else {
                return QiniuUtil.URL + cover;
            }
        }
    }
}
