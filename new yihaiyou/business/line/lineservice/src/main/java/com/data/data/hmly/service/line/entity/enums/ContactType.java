package com.data.data.hmly.service.line.entity.enums;

/**
 * Created by dy on 2016/6/21.
 */
public enum ContactType {

    sendHuman ("送站/机联系人"), receive("接站/机联系人"), tourGuide ("导游"), departurePlace ("出发地联系人"), localPlace("当地联系人"), others("其他");


    private String description;

    ContactType(String s) {
        this.description = s;
    }

    public String getDescription() {
        return this.description;
    }

}
