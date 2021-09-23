package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.cruiseship.entity.CruiseShipExtend;

/**
 * Created by huangpeijie on 2016-09-19,0019.
 */
public class CruiseShipExtendResponse {
    private String lightPoint;
    private String quoteNoContainDesc;
    private String quoteContainDesc;
    private String orderKnow;
    private String howToOrder;
    private String signWay;
    private String payWay;
    private String introduction;

    public CruiseShipExtendResponse() {
    }

    public CruiseShipExtendResponse(CruiseShipExtend cruiseShipExtend) {
        this.lightPoint = cruiseShipExtend.getLightPoint();
        this.quoteNoContainDesc = cruiseShipExtend.getQuoteNoContainDesc();
        this.quoteContainDesc = cruiseShipExtend.getQuoteContainDesc();
        this.orderKnow = cruiseShipExtend.getOrderKnow();
        this.howToOrder = cruiseShipExtend.getHowToOrder();
        this.signWay = cruiseShipExtend.getSignWay();
        this.payWay = cruiseShipExtend.getPayWay();
        this.introduction = cruiseShipExtend.getIntroduction();
    }

    public String getLightPoint() {
        return lightPoint;
    }

    public void setLightPoint(String lightPoint) {
        this.lightPoint = lightPoint;
    }

    public String getQuoteNoContainDesc() {
        return quoteNoContainDesc;
    }

    public void setQuoteNoContainDesc(String quoteNoContainDesc) {
        this.quoteNoContainDesc = quoteNoContainDesc;
    }

    public String getQuoteContainDesc() {
        return quoteContainDesc;
    }

    public void setQuoteContainDesc(String quoteContainDesc) {
        this.quoteContainDesc = quoteContainDesc;
    }

    public String getOrderKnow() {
        return orderKnow;
    }

    public void setOrderKnow(String orderKnow) {
        this.orderKnow = orderKnow;
    }

    public String getHowToOrder() {
        return howToOrder;
    }

    public void setHowToOrder(String howToOrder) {
        this.howToOrder = howToOrder;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
