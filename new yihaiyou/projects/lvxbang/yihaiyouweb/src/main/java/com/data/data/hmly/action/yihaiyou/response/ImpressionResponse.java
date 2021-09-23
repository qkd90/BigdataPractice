package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.action.yihaiyou.request.Photo;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.impression.entity.Impression;
import com.google.common.collect.Lists;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class ImpressionResponse {
    private Long id;
    private String placeName;
    private String content;
    private String cover;
    private Long userId;
    private String createTime;
    private Integer browsingNum;
    private List<Photo> gallery = Lists.newArrayList();

    public ImpressionResponse() {
    }

    public ImpressionResponse(Impression impression) {
        this.id = impression.getId();
        this.placeName = impression.getPlaceName();
        this.content = impression.getContent();
        this.cover = cover(impression.getCover());
        this.userId = impression.getUser().getId();
        this.createTime = DateUtils.formatShortDate(impression.getCreateTime());
        this.browsingNum = impression.getBrowsingNum();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getBrowsingNum() {
        return browsingNum;
    }

    public void setBrowsingNum(Integer browsingNum) {
        this.browsingNum = browsingNum;
    }

    public List<Photo> getGallery() {
        return gallery;
    }

    public void setGallery(List<Photo> gallery) {
        this.gallery = gallery;
    }
}
