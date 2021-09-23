package com.data.data.hmly.enums;

/**
 * Created by huangpeijie on 2016-11-22,0022.
 */
public enum FerryIdType {
    ID_CARD("二代身份证"), REMNANT_SOLDIER("残军证"), OTHER("其他证件");

    private String description;

    FerryIdType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static FerryIdType getByDesc(String description) {
        for (FerryIdType ferryIdType : FerryIdType.values()) {
            if (ferryIdType.getDescription().equals(description)) {
                return ferryIdType;
            }
        }
        return null;
    }
}
