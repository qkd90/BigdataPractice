package com.data.data.hmly.action.yihaiyou.response;

import java.util.List;

/**
 * Created by dy on 2017/2/17.
 */
public class CruiseShipRoomTypeResponse {
    private String roomType;
    private String roomTypeDesc;
    private Float price;
    private List<CruiseShipRoomResponse> roomList;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<CruiseShipRoomResponse> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<CruiseShipRoomResponse> roomList) {
        this.roomList = roomList;
    }

    public String getRoomTypeDesc() {
        return roomTypeDesc;
    }

    public void setRoomTypeDesc(String roomTypeDesc) {
        this.roomTypeDesc = roomTypeDesc;
    }
}
