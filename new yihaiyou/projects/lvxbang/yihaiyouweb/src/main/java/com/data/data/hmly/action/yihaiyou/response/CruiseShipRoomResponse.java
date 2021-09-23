package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoom;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-09-19,0019.
 */
public class CruiseShipRoomResponse {
    private String name;
    private String roomType;
    private String roomTypeDesc;
    private String deck;
    private String area;
    private String facilities;
    private Integer peopleNum;
    private Boolean forceFlag;
    private Float price;
    private Long roomId;
    private Long roomDateId;
    private String coverImage;
    private Boolean isOver;

    public CruiseShipRoomResponse() {
    }

    public CruiseShipRoomResponse(CruiseShipRoom cruiseShipRoom) {
        this.name = cruiseShipRoom.getName();
        this.roomType = cruiseShipRoom.getRoomType().name();
        this.roomTypeDesc = cruiseShipRoom.getRoomType().getDescription();
        this.deck = cruiseShipRoom.getDeck();
        this.area = cruiseShipRoom.getArea();
        this.facilities = cruiseShipRoom.getFacilities();
        this.peopleNum = cruiseShipRoom.getPeopleNum();
        this.forceFlag = cruiseShipRoom.getForceFlag();
        this.roomId = cruiseShipRoom.getId();
        this.coverImage = cover(cruiseShipRoom.getCoverImage());
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        }
        if (cover.startsWith("http")) {
            return cover;
        }
        return QiniuUtil.URL + cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomTypeDesc() {
        return roomTypeDesc;
    }

    public void setRoomTypeDesc(String roomTypeDesc) {
        this.roomTypeDesc = roomTypeDesc;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Boolean getForceFlag() {
        return forceFlag;
    }

    public void setForceFlag(Boolean forceFlag) {
        this.forceFlag = forceFlag;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getRoomDateId() {
        return roomDateId;
    }

    public void setRoomDateId(Long roomDateId) {
        this.roomDateId = roomDateId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Boolean getIsOver() {
        return isOver;
    }

    public void setIsOver(Boolean isOver) {
        this.isOver = isOver;
    }
}
