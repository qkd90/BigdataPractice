package com.data.data.hmly.service.lxbcommon.entity.vo;

/**
 * Created by caiys on 2016/6/20.
 */
public class CustomRequireDestinationVo {
    private Long id;            // 标识
    private Long cityId;
    private String cityName;
    private Integer level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
