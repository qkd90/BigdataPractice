package com.data.data.hmly.action.yhypc.vo;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.cruiseship.vo.CruiseShipSolrEntity;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/1/10.
 */
public class CruiseShipResponse {
    private Long id;
    private String name;
    private Long lineDay;
    private String cover;
    private Long productId;
    private Float price;
    private Long cityId;
    private List<Long> labelIds;
    private String startCity;
    private Long startCityId;
    private List<String> startDays;
    private Integer satisfaction;
    private List<String> brands = Lists.newArrayList();
    private String routes;
    private Integer orderNum;
    private String startDate;
    private List<Long> categoryIds = Lists.newArrayList();

    public CruiseShipResponse () {}
    public CruiseShipResponse (CruiseShipSolrEntity cruiseShipSolrEntity) {
        this.id = cruiseShipSolrEntity.getId();
        this.name = cruiseShipSolrEntity.getName();
        this.lineDay = cruiseShipSolrEntity.getLineDay();
        this.cover = cruiseShipSolrEntity.getCover();
        this.price = cruiseShipSolrEntity.getPrice();
        this.startDate = cruiseShipSolrEntity.getStartDate();
        this.productId = cruiseShipSolrEntity.getProductId();
        if (!cruiseShipSolrEntity.getRoutes().isEmpty()) {
            for (String route : cruiseShipSolrEntity.getRoutes()) {
                this.routes = route + " ";
            }
        }
        if (StringUtils.isNotBlank(cruiseShipSolrEntity.getStartCity())) {
            this.startCity = cruiseShipSolrEntity.getStartCity()
                    .substring(cruiseShipSolrEntity.getStartCity().lastIndexOf("/") + 1,
                            cruiseShipSolrEntity.getStartCity().length());
        }
        this.satisfaction = cruiseShipSolrEntity.getSatisfaction();
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

    public Long getLineDay() {
        return lineDay;
    }

    public void setLineDay(Long lineDay) {
        this.lineDay = lineDay;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public List<Long> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<Long> labelIds) {
        this.labelIds = labelIds;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public Long getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(Long startCityId) {
        this.startCityId = startCityId;
    }

    public List<String> getStartDays() {
        return startDays;
    }

    public void setStartDays(List<String> startDays) {
        this.startDays = startDays;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
