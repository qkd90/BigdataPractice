package com.data.data.hmly.action.lvxbang.response;

import com.data.data.hmly.service.scenic.entity.ScenicInfo;

/**
 * @author Jonathan.Guo
 */
public class MiniScenicResponse {
    public Long id;
    public String name;
    public String cover;
    public int score;
    public Long cityId;
    public Integer adviceMinute;

    public MiniScenicResponse(ScenicInfo scenicInfo) {
        this.id = scenicInfo.getId();
        this.name = scenicInfo.getName();
        this.cover = scenicInfo.getCover();
        this.score = scenicInfo.getScore();
        this.cityId = scenicInfo.getCity().getId();
        this.adviceMinute = scenicInfo.getScenicOther().getAdviceTime();

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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Integer getAdviceMinute() {
        return adviceMinute;
    }

    public void setAdviceMinute(Integer adviceMinute) {
        this.adviceMinute = adviceMinute;
    }
}
