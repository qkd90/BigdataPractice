package com.data.data.hmly.service.lvxbang.request;

import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * @author Jonathan.Guo
 */
public class TripNode {
    public Long id;
    public String name;
    public String cover;
    public int score;
    public int ranking;
    public String advice;
    public String price;
    public int type;
    public Double lng;
    public Double lat;
    public String address;
    public String shortIntro;
    public Integer star;
    public Integer cityId;

    public TripNode() {
    }

    public TripNode(ScenicInfo scenicInfo) {
        this.id = scenicInfo.getId();
        this.name = scenicInfo.getName();
//        this.cover = cover(scenicInfo.getCover());
        if (StringUtils.isBlank(scenicInfo.getCover())) {
            this.cover = QiniuUtil.URL + "jingdian.png";
        } else {
            if (scenicInfo.getCover().startsWith("http")) {
                this.cover =  scenicInfo.getCover();
            } else {
                this.cover =  QiniuUtil.URL + scenicInfo.getCover();
            }
        }
        this.score = scenicInfo.getScore();
        this.ranking = scenicInfo.getRanking();
        if (scenicInfo.getScenicOther() != null) {
            this.advice = scenicInfo.getScenicOther().getAdviceTimeDesc();
            this.address = scenicInfo.getScenicOther().getAddress();
        }
        if (scenicInfo.getScenicGeoinfo() != null) {
            this.lng = scenicInfo.getScenicGeoinfo().getBaiduLng();
            this.lat = scenicInfo.getScenicGeoinfo().getBaiduLat();
        }
        this.star = scenicInfo.getStar();
        this.cityId = scenicInfo.getCityId();
    }

    public String cover(String cover) {
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getStar() {
        return star;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
