package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.google.common.collect.Lists;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class ScenicResponse {
    private Long id;
    private String name;
    private String cover;
    private String introduction;
    private String address;
    private String openTime;
    private String adviceTime;
    private Float score;
    private Float price;
    private Double longitude;
    private Double latitude;
    private Integer type;
    private Long cityId;
    private List<ScenicSimpleResponse> child = Lists.newArrayList();

    public ScenicResponse() {
    }

    public ScenicResponse(ScenicInfo scenicInfo) {
        this.id = scenicInfo.getId();
        this.name = scenicInfo.getName();
        this.cover = cover(scenicInfo.getCover());
        this.score = scenicInfo.getScore().floatValue() / 20f;
        this.price = scenicInfo.getPrice();
        this.type = 1;
        this.cityId = Long.valueOf(scenicInfo.getCityCode());
        if (scenicInfo.getScenicOther() != null) {
            this.introduction = scenicInfo.getScenicOther().getDescription();
            this.address = scenicInfo.getScenicOther().getAddress();
            this.openTime = scenicInfo.getScenicOther().getOpenTime();
            this.adviceTime = scenicInfo.getScenicOther().getAdviceTimeDesc().replace("建议", "");
        }
        if (scenicInfo.getScenicGeoinfo() != null) {
            this.longitude = scenicInfo.getScenicGeoinfo().getBaiduLng();
            this.latitude = scenicInfo.getScenicGeoinfo().getBaiduLat();
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

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(String adviceTime) {
        this.adviceTime = adviceTime;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public List<ScenicSimpleResponse> getChild() {
        return child;
    }

    public void setChild(List<ScenicSimpleResponse> child) {
        this.child = child;
    }
}
