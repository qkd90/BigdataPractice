package com.data.data.hmly.service.outOrder.entity.enums;

/**
 * Created by dy on 2016/3/3.
 */
public enum JszxOrderDetailPriceType {

    adult("成人"),student("学生"),child("儿童"),oldman("老人"),taopiao("套票"),other("其他"),team("团体");

    private String description;

    JszxOrderDetailPriceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}
