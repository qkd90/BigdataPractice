package com.data.data.hmly.action.mobile.request;

import com.data.data.hmly.service.impression.entity.PlaceType;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class PlaceSearchRequest {
    private String keyword;
    private PlaceType type;
    private Double longitude;
    private Double latitude;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public PlaceType getType() {
        return type;
    }

    public void setType(PlaceType type) {
        this.type = type;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
