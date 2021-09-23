package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.vo.PlanSolrEntity;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-04-19,0019.
 */
public class PlanSimpleResponse {
    private Long id;
    private String name;
    private Integer day;
    private String startDate;
    private String cover;

    public PlanSimpleResponse() {
    }

    public PlanSimpleResponse(Plan plan) {
        this.id = plan.getId();
        this.name = plan.getName();
        this.day = plan.getPlanDays();
        this.startDate = DateUtils.formatShortDate(plan.getStartTime());
        this.cover = cover(plan.getCoverPath());
    }

    public PlanSimpleResponse(PlanSolrEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.day = entity.getDays();
        this.startDate = DateUtils.formatShortDate(entity.getStartTime());
        this.cover = cover(entity.getCover());
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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
