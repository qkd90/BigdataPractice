package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class ScenicSimpleResponse {
    private Long id;
    private String name;
    private String cover;
    private Float score;
    private Float price;
    private Long cityId;
    private String openTime;
    private Integer productScore;
    private Integer ranking;
    private Integer star;
    private Integer adviceMinute;


    public ScenicSimpleResponse() {
    }

    public ScenicSimpleResponse(ScenicSolrEntity scenicSolrEntity) {
        this.id = scenicSolrEntity.getId();
        this.name = scenicSolrEntity.getName();
        this.cover = cover(scenicSolrEntity.getCover());
        this.score = scenicSolrEntity.getProductScore() == null ? 0f : scenicSolrEntity.getProductScore().floatValue() / 20f;
        this.price = scenicSolrEntity.getPrice();
        this.cityId = scenicSolrEntity.getCityId();
        this.openTime = scenicSolrEntity.getOpenTime();
        this.productScore = scenicSolrEntity.getProductScore();
        this.ranking = scenicSolrEntity.getRanking();
        if (StringUtils.isBlank(scenicSolrEntity.getLevel())) {
            this.star = 0;
        } else {
            this.star = scenicSolrEntity.getLevel().length();
        }
        this.adviceMinute = scenicSolrEntity.getAdviceMinute();
    }

    public ScenicSimpleResponse(ScenicInfo scenicInfo) {
        this.id = scenicInfo.getId();
        this.name = scenicInfo.getName();
        this.cover = cover(scenicInfo.getCover());
        this.score = scenicInfo.getScore().floatValue() / 20f;
        this.price = scenicInfo.getPrice();
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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Integer getProductScore() {
        return productScore;
    }

    public void setProductScore(Integer productScore) {
        this.productScore = productScore;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getAdviceMinute() {
        return adviceMinute;
    }

    public void setAdviceMinute(Integer adviceMinute) {
        this.adviceMinute = adviceMinute;
    }
}
