package com.data.data.hmly.service.hotel.entity.enums;


/**
 * Created by Sane on 16/4/15.
 */
public enum PriceStatus {
    UP("上架"), DOWN("下架"), DEL("删除"), GUARANTEE("担保"),
    UP_CHECKING("上架中"), REFUSE("拒绝");

    private String description;

    PriceStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
