package com.data.data.hmly.service.hotel.entity.enums;

/**
 * Created by zzl on 2016/11/22.
 */
public enum HotelRoomStatus {

    WAIT_CHECK("等待入住"), CHECK_IN("已入住"), AVAILABLE("空闲"), CLOSED("退款"), CHECKING("审核中");

    HotelRoomStatus(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
