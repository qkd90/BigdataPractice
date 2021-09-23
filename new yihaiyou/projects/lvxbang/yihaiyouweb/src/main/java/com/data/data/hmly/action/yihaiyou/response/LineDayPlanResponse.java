package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.line.entity.Linedaysplan;

import java.util.List;

/**
 * Created by huangpeijie on 2016-07-26,0026.
 */
public class LineDayPlanResponse {
    private String timeNode;
    private String timeDesc;
    private List<LineDayPlanInfoResponse> planInfoList;
    private List<LineDayPlanImageResponse> planInfoImagesList;

    public LineDayPlanResponse() {
    }

    public LineDayPlanResponse(Linedaysplan linedaysplan) {
        this.timeNode = linedaysplan.getTimeNode();
        this.timeDesc = linedaysplan.getTimeDesc();
    }

    public String getTimeNode() {
        return timeNode;
    }

    public void setTimeNode(String timeNode) {
        this.timeNode = timeNode;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public List<LineDayPlanInfoResponse> getPlanInfoList() {
        return planInfoList;
    }

    public void setPlanInfoList(List<LineDayPlanInfoResponse> planInfoList) {
        this.planInfoList = planInfoList;
    }

    public List<LineDayPlanImageResponse> getPlanInfoImagesList() {
        return planInfoImagesList;
    }

    public void setPlanInfoImagesList(List<LineDayPlanImageResponse> planInfoImagesList) {
        this.planInfoImagesList = planInfoImagesList;
    }
}
