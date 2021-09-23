package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.plan.entity.PlanTrip;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-04-19,0019.
 */
public class PlanTripResponse {
    private Long tripId;
    private Long scenicId;
    private String scenicName;
    private String cover;
    private String introduction;
    private String address;
    private String adviceTime;
    private Float price;
    private Double longitude;
    private Double latitude;

    public PlanTripResponse() {
    }

    public PlanTripResponse(PlanTrip planTrip) {
        this.tripId = planTrip.getId();
        ScenicInfo scenicInfo = planTrip.getScenicInfo();
        if (scenicInfo != null) {
            this.scenicId = scenicInfo.getId();
            this.scenicName = scenicInfo.getName();
            this.cover = cover(scenicInfo.getCover());
            this.price = scenicInfo.getPrice();
            if (scenicInfo.getScenicOther() != null) {
                this.introduction = scenicInfo.getScenicOther().getDescription();
                this.address = scenicInfo.getScenicOther().getAddress();
                this.adviceTime = scenicInfo.getScenicOther().getAdviceTimeDesc().replace("建议", "");
            }
            if (scenicInfo.getScenicGeoinfo() != null) {
                this.longitude = scenicInfo.getScenicGeoinfo().getBaiduLng();
                this.latitude = scenicInfo.getScenicGeoinfo().getBaiduLat();
            }
        }
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return QiniuUtil.URL + "jingdian.png";
        } else {
            if (cover.startsWith("http")) {
                return cover;
            } else {
                return QiniuUtil.URL + cover;
            }
        }
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(String adviceTime) {
        this.adviceTime = adviceTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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
