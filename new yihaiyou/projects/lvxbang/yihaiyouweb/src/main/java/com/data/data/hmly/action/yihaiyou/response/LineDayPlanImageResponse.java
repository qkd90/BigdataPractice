package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.line.entity.LineImages;

/**
 * Created by huangpeijie on 2016-07-26,0026.
 */
public class LineDayPlanImageResponse {
    private String imageUrl;
    private String imageDesc;

    public LineDayPlanImageResponse() {
    }

    public LineDayPlanImageResponse(LineImages lineImages) {
        this.imageUrl = lineImages.getImageUrl();
        this.imageDesc = lineImages.getImageDesc();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }
}
