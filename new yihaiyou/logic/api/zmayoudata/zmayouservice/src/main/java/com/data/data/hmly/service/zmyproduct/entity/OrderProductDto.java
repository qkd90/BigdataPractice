package com.data.data.hmly.service.zmyproduct.entity;

/**
 * Created by dy on 2016/5/9.
 */
public class OrderProductDto {

    private String productId;
    private String productType;
    private String amount;
    private String visitorName;
    private String visitorIdNo;
    private String visitorMobile;
    private String ticketState;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorIdNo() {
        return visitorIdNo;
    }

    public void setVisitorIdNo(String visitorIdNo) {
        this.visitorIdNo = visitorIdNo;
    }

    public String getVisitorMobile() {
        return visitorMobile;
    }

    public void setVisitorMobile(String visitorMobile) {
        this.visitorMobile = visitorMobile;
    }

    public String getTicketState() {
        return ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState;
    }
}
