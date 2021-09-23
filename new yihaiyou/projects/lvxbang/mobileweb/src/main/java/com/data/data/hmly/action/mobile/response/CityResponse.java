package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.entity.TbArea;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class CityResponse {
    private Long id;
    private String name;
    private String cover;

    public CityResponse() {
    }

    public CityResponse(TbArea area) {
        this.id = area.getId();
        this.name = area.getName();
        if (area.getTbAreaExtend() != null) {
            this.cover = cover(area.getTbAreaExtend().getCover());
        }
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        } else {
            if (cover.startsWith("http")) {
                return cover;
            } else {
                return "http://7u2inn.com2.z0.glb.qiniucdn.com/" + cover;
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
}
