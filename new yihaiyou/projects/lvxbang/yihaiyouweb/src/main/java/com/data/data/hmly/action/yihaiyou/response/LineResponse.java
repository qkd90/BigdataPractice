package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.line.entity.Line;

import java.util.List;

/**
 * Created by huangpeijie on 2016-07-22,0022.
 */
public class LineResponse {
    private String name;
    private String appendTitle;
    private String cover;
    private String productNo;
    private String productAttr;
    private Float price;
    private Long arriveCityId;
    private String startCity;
    private String lineLightPoint;
    private List<LineTypePriceResponse> linetypepriceList;

    public LineResponse() {
    }

    public LineResponse(Line line) {
        this.name = line.getName();
        this.appendTitle = line.getAppendTitle();
        this.productNo = line.getProductNo();
        this.productAttr = line.getProductAttr().getDesc();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppendTitle() {
        return appendTitle;
    }

    public void setAppendTitle(String appendTitle) {
        this.appendTitle = appendTitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getLineLightPoint() {
        return lineLightPoint;
    }

    public void setLineLightPoint(String lineLightPoint) {
        this.lineLightPoint = lineLightPoint;
    }

    public List<LineTypePriceResponse> getLinetypepriceList() {
        return linetypepriceList;
    }

    public void setLinetypepriceList(List<LineTypePriceResponse> linetypepriceList) {
        this.linetypepriceList = linetypepriceList;
    }

    public Long getArriveCityId() {
        return arriveCityId;
    }

    public void setArriveCityId(Long arriveCityId) {
        this.arriveCityId = arriveCityId;
    }
}
