package com.data.data.hmly.action.yihaiyou.request;

import com.data.data.hmly.service.impression.entity.ImpressionGallery;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class Photo {
    private Long id;
    private String imgUrl;
    private Integer width;
    private Integer height;

    public Photo() {
    }

    public Photo(ImpressionGallery impressionGallery) {
        this.id = impressionGallery.getId();
        this.imgUrl = cover(impressionGallery.getImgUrl());
        this.width = impressionGallery.getWidth();
        this.height = impressionGallery.getHeight();
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = cover(imgUrl);
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
