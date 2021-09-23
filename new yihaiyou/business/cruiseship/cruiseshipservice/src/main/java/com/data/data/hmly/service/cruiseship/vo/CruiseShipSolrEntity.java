package com.data.data.hmly.service.cruiseship.vo;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.goods.entity.Category;
import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-09-20,0020.
 */
@SolrDocument(solrCoreName = "products")
public class CruiseShipSolrEntity extends SolrEntity<CruiseShip> implements Cloneable {
    @Field
    private Long id;
    @Field
    private Long productId;
    @Field
    private String name;
    @Field
    private final SolrType type = SolrType.cruise_ship;
    @Field
    private String typeid;
    @Field
    private Long lineDay;
    @Field
    private String cover;
    @Field
    private Float price;
    @Field
    private Long cityId;
    @Field
    private List<Long> labelIds;
    @Field
    private String startCity;
    @Field
    private Long startCityId;
    @Field
    private List<String> startDays;
    @Field
    private Integer satisfaction;
    @Field
    private List<String> brands = Lists.newArrayList();
    @Field
    private List<String> routes = Lists.newArrayList();
    @Field
    private Integer orderNum;
    @Field
    private String startDate;
    @Field
    private Date start;
    @Field
    private List<Long> categoryIds = Lists.newArrayList();

    public CruiseShipSolrEntity() {
    }

    public CruiseShipSolrEntity(CruiseShipDate cruiseShipDate) {
        this.id = cruiseShipDate.getId();
        this.typeid = this.type.name() + this.id;
        this.start = DateUtils.add(cruiseShipDate.getDate(), Calendar.HOUR, 8);
        this.startDate = DateUtils.formatShortDate(cruiseShipDate.getDate());

        CruiseShip cruiseShip = cruiseShipDate.getCruiseShip();
        this.productId = cruiseShip.getId();
        this.name = cruiseShip.getName();
        this.lineDay = cruiseShip.getPlayDay().longValue();
        this.cover = cruiseShip.getCoverImage();
        this.cityId = cruiseShip.getArriveCityId();
        this.startCity = cruiseShip.getStartCity();
        this.startCityId = cruiseShip.getStartCityId();
        this.satisfaction = cruiseShip.getSatisfaction();
        Category brand = cruiseShip.getBrand();
        if (brand != null) {
            this.brands.add(brand.getName());
            this.categoryIds.add(brand.getId());
            if (brand.getParentId() > 0) {
                this.categoryIds.add(brand.getParentId());
            }
        }
        Category route = cruiseShip.getRoute();
        if (route != null) {
            this.routes.add(route.getName());
            this.categoryIds.add(route.getId());
            if (route.getParentId() > 0) {
                this.categoryIds.add(route.getParentId());
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public SolrType getType() {
        return type;
    }

    @Override
    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
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

    public List<Long> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<Long> labelIds) {
        this.labelIds = labelIds;
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<String> getRoutes() {
        return routes;
    }

    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }

    @Override
    public CruiseShipSolrEntity clone() throws CloneNotSupportedException {
        return (CruiseShipSolrEntity) super.clone();
    }
}
