package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.line.entity.Lineexplain;

/**
 * Created by huangpeijie on 2016-07-22,0022.
 */
public class LineExplainResponse {
    private String orderContext;
    private String tripNotice;
    private String specialLimit;
    private String signWay;
    private String payWay;
    private String breachTip;

    public LineExplainResponse() {
    }

    public LineExplainResponse(Lineexplain lineexplain) {
        this.orderContext = lineexplain.getOrderContext();
        this.tripNotice = lineexplain.getTripNotice();
        this.specialLimit = lineexplain.getSpecialLimit();
        this.signWay = lineexplain.getSignWay();
        this.payWay = lineexplain.getPayWay();
        this.breachTip = lineexplain.getBreachTip();
    }

    public String getOrderContext() {
        return orderContext;
    }

    public void setOrderContext(String orderContext) {
        this.orderContext = orderContext;
    }

    public String getTripNotice() {
        return tripNotice;
    }

    public void setTripNotice(String tripNotice) {
        this.tripNotice = tripNotice;
    }

    public String getSpecialLimit() {
        return specialLimit;
    }

    public void setSpecialLimit(String specialLimit) {
        this.specialLimit = specialLimit;
    }

    public String getSignWay() {
        return signWay;
    }

    public void setSignWay(String signWay) {
        this.signWay = signWay;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getBreachTip() {
        return breachTip;
    }

    public void setBreachTip(String breachTip) {
        this.breachTip = breachTip;
    }
}
