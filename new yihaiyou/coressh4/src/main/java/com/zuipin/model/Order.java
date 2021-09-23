package com.zuipin.model;

public class Order implements java.io.Serializable {
    private static final long serialVersionUID = -1638765112014326561L;
    private String            orderNo;
    private String            cardPayFlag;
    private Double            svcCardDeductAmt = 0d;
    private String            recName;
    private String            address;
    private String            mobilePhone;
    private Long              regionId         = 0l;
    private String            mentionMobilePhone;
    private String            payMode;
    private String            deliverSendTime;
    private String            invoiceNeed;
    private String            invoiceType;
    private String            invoiceName;
    private String            remark;
    
    public String getOrderNo() {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    public Double getSvcCardDeductAmt() {
        return svcCardDeductAmt;
    }
    
    public void setSvcCardDeductAmt(Double svcCardDeductAmt) {
        this.svcCardDeductAmt = svcCardDeductAmt;
    }
    
    public String getCardPayFlag() {
        return cardPayFlag;
    }
    
    public void setCardPayFlag(String cardPayFlag) {
        this.cardPayFlag = cardPayFlag;
    }
    
    public String getRecName() {
        return recName;
    }
    
    public void setRecName(String recName) {
        this.recName = recName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getMobilePhone() {
        return mobilePhone;
    }
    
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    
    public Long getRegionId() {
        return regionId;
    }
    
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    
    public String getPayMode() {
        return payMode;
    }
    
    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }
    
    public String getMentionMobilePhone() {
        return mentionMobilePhone;
    }
    
    public void setMentionMobilePhone(String mentionMobilePhone) {
        this.mentionMobilePhone = mentionMobilePhone;
    }
    
    public String getDeliverSendTime() {
        return deliverSendTime;
    }
    
    public void setDeliverSendTime(String deliverSendTime) {
        this.deliverSendTime = deliverSendTime;
    }
    
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    public String getInvoiceNeed() {
        return invoiceNeed;
    }
    
    public void setInvoiceNeed(String invoiceNeed) {
        this.invoiceNeed = invoiceNeed;
    }
    
    public String getInvoiceType() {
        return invoiceType;
    }
    
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }
    
    public String getInvoiceName() {
        return invoiceName;
    }
    
    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
