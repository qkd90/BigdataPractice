package com.data.data.hmly.enums;

/**
 * Created by vacuity on 15/12/14.
 */
public enum  Gender {
    male("男"), female("女"), secret("保密");

    String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
