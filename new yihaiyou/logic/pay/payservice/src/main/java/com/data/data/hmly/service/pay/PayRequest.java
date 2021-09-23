package com.data.data.hmly.service.pay;

import javax.persistence.Entity;

public class PayRequest {

    private String prePay;

    public String getPrePay() {
        return prePay;
    }

    public void setPrePay(String prePay) {
        this.prePay = prePay;
    }
}
