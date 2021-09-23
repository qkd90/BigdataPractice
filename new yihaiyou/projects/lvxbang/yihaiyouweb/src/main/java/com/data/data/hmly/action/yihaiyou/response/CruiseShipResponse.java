package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.vo.CruiseShipSolrEntity;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

import java.util.List;

/**
 * Created by huangpeijie on 2016-09-19,0019.
 */
public class CruiseShipResponse {
    private Long id;
    private String name;
    private String brand;
    private String productNo;
    private String startCity;
    private String arriveCity;
    private String coverImage;
    private Integer playDay;
    private String route;
    private Float price;
    private Integer satisfaction;
    private String startDate;
    private List<CruiseShipDayResponse> planDays;
    private CruiseShipExtendResponse extend;
    private CruiseShipDateResponse date;
    private List<CruiseShipRoomResponse> rooms;
    private List<CruiseShipRoomTypeResponse> roomTypes;
    private List<CruiseShipDeckResponse> decks;
    private List<String> pathList;

    public CruiseShipResponse() {
    }

    public CruiseShipResponse(CruiseShip cruiseShip) {
        this.id = cruiseShip.getId();
        this.name = cruiseShip.getName();
        this.productNo = cruiseShip.getProductNo();
        this.startCity = cruiseShip.getStartCity();
        this.coverImage = cover(cruiseShip.getCoverImage());
        this.playDay = cruiseShip.getPlayDay();
        this.arriveCity = cruiseShip.getArriveCity();
        if (cruiseShip.getCruiseShipExtend() != null) {
            this.extend = new CruiseShipExtendResponse(cruiseShip.getCruiseShipExtend());
        }
    }

    public CruiseShipResponse(CruiseShipSolrEntity cruiseShipSolrEntity) {
        this.id = cruiseShipSolrEntity.getId();
        this.name = cruiseShipSolrEntity.getName();
        this.startCity = cruiseShipSolrEntity.getStartCity();
        this.coverImage = cover(cruiseShipSolrEntity.getCover());
        this.playDay = cruiseShipSolrEntity.getLineDay().intValue();
        this.price = cruiseShipSolrEntity.getPrice();
        this.satisfaction = cruiseShipSolrEntity.getSatisfaction();
        this.startDate = cruiseShipSolrEntity.getStartDate();
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

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<CruiseShipDayResponse> getPlanDays() {
        return planDays;
    }

    public void setPlanDays(List<CruiseShipDayResponse> planDays) {
        this.planDays = planDays;
    }

    public CruiseShipExtendResponse getExtend() {
        return extend;
    }

    public void setExtend(CruiseShipExtendResponse extend) {
        this.extend = extend;
    }

    public CruiseShipDateResponse getDate() {
        return date;
    }

    public void setDate(CruiseShipDateResponse date) {
        this.date = date;
    }

    public List<CruiseShipRoomResponse> getRooms() {
        return rooms;
    }

    public void setRooms(List<CruiseShipRoomResponse> rooms) {
        this.rooms = rooms;
    }

    public Integer getPlayDay() {
        return playDay;
    }

    public void setPlayDay(Integer playDay) {
        this.playDay = playDay;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public List<CruiseShipDeckResponse> getDecks() {
        return decks;
    }

    public void setDecks(List<CruiseShipDeckResponse> decks) {
        this.decks = decks;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<CruiseShipRoomTypeResponse> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<CruiseShipRoomTypeResponse> roomTypes) {
        this.roomTypes = roomTypes;
    }

    public List<String> getPathList() {
        return pathList;
    }

    public void setPathList(List<String> pathList) {
        this.pathList = pathList;
    }

    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }
}
