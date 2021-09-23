package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.sales.entity.Insurance;

/**
 * Created by huangpeijie on 2016-07-20,0020.
 */
public class InsuranceResponse {
    private Long id;
    private String name;
    private Float price;
    private String category;
    private String description;
    private String notice;

    public InsuranceResponse() {
    }

    public InsuranceResponse(Insurance insurance) {
        this.id = insurance.getId();
        this.name = insurance.getName();
        this.price = insurance.getPrice();
        this.category = insurance.getCategory().getName();
        this.description = insurance.getDescription();
        this.notice = insurance.getNotice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
