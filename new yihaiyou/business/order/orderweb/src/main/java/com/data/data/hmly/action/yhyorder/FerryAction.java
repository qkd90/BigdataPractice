package com.data.data.hmly.action.yhyorder;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2016/12/20.
 */
public class FerryAction extends FrameBaseAction {

    private Map<String, Object> map = new HashMap<String, Object>();
    private String ferryStartDate;
    private String ferryEndDate;


    // /yhyorder/ferry/getFerryBillManage.jhtml
    public Result getFerryBillManage() {
        ferryStartDate = "2016-12-01 00:00:00";
        ferryStartDate = "2016-12-20 00:00:00";
        map = FerryUtil.getOrderCollect(DateUtils.toFullDate(ferryStartDate), DateUtils.toFullDate(ferryEndDate));
        return jsonResult(map);
    }

    public String getFerryStartDate() {
        return ferryStartDate;
    }

    public void setFerryStartDate(String ferryStartDate) {
        this.ferryStartDate = ferryStartDate;
    }

    public String getFerryEndDate() {
        return ferryEndDate;
    }

    public void setFerryEndDate(String ferryEndDate) {
        this.ferryEndDate = ferryEndDate;
    }
}
