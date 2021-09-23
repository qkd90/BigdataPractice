package com.data.data.hmly.service.entity;

/**
 * Created by dy on 2016/12/26.
 */
public enum CertificateType {
    passport("护照"), identity_card("身份证");
    private String description;
    CertificateType(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
