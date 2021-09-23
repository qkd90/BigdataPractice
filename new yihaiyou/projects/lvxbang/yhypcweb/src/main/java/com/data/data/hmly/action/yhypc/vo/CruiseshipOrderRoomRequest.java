package com.data.data.hmly.action.yhypc.vo;

/**
 * Created by dy on 2017/1/13.
 */
public class CruiseshipOrderRoomRequest {

    private Long id;
    private Long dateId;
    private Long cruiseshipId;
    private String roomName;
    private Integer adultNum;
    private Integer childNum;
    private Integer roomNum;
    private Float price;
    private Float totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public Long getCruiseshipId() {
        return cruiseshipId;
    }

    public void setCruiseshipId(Long cruiseshipId) {
        this.cruiseshipId = cruiseshipId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getAdultNum() {
        return adultNum;
    }

    public void setAdultNum(Integer adultNum) {
        this.adultNum = adultNum;
    }

    public Integer getChildNum() {
        return childNum;
    }

    public void setChildNum(Integer childNum) {
        this.childNum = childNum;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
