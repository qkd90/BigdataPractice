package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by vacuity on 15/12/24.
 */
public enum  OrderTouristPeopleType {
    ADULT("成人"), CHILD("儿童"), BABY("婴儿"), STUDENT("学生");
    private String description;

    OrderTouristPeopleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
