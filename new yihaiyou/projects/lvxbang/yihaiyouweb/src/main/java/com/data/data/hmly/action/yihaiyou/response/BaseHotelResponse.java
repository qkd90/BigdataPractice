package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.hotel.entity.Hotel;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-04-21,0021.
 */
public class BaseHotelResponse {
    protected Long id;
    protected String name;
    protected String cover;
    protected Float score;
    protected String address;
    protected String introduction;

    public BaseHotelResponse() {
    }

    public BaseHotelResponse(Hotel hotel) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.cover = cover(hotel.getCover());
        this.score = hotel.getScore() / 20f;
        if (hotel.getExtend() != null) {
            this.introduction = hotel.getExtend().getDescription();
            this.address = hotel.getExtend().getAddress();
        }
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
        this.cover = cover(cover);
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
