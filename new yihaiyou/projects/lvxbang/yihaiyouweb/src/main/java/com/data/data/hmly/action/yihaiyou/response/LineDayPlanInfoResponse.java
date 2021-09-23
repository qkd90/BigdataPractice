package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.line.entity.LineDaysPlanInfo;

/**
 * Created by huangpeijie on 2016-07-26,0026.
 */
public class LineDayPlanInfoResponse {
    private String littleTitle;
    private String titleDesc;

    public LineDayPlanInfoResponse() {
    }

    public LineDayPlanInfoResponse(LineDaysPlanInfo lineDaysPlanInfo) {
        this.littleTitle = lineDaysPlanInfo.getLittleTitle();
        this.titleDesc = lineDaysPlanInfo.getTitleDesc();
    }

    public String getLittleTitle() {
        return littleTitle;
    }

    public void setLittleTitle(String littleTitle) {
        this.littleTitle = littleTitle;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }
}
