package com.hmlyinfo.app.soutu.pay.allin.controller;

/**
 * Created by guoshijie on 2015/7/28.
 */
public class PostView {
    String serverUrl;
    String key;
    String version;
    String language;
    String inputCharset;
    String merchantId;
    /**
     * 用于支付成功后浏览器的跳转
     */
    String pickupUrl;
    /**
     * 用于通知后台支付成功的信息
     */
    String receiveUrl;
    int payType;
    int signType;
    String orderNo;
    long orderAmount;
    String orderDatetime;
    String orderCurrency;
    String orderExpireDatetime;
    String payerTelephone;
    String payerEmail;
    String payerName;
    String payerIDCard;
    String pid;
    String productName;
    String productId;
    String productNum;
    String productPrice;
    String productDesc;
    String ext1;
    String ext2;
    String extTL;
    String issuerId;
    String pan;
    String tradeNature;
    String sign;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPickupUrl() {
        return pickupUrl;
    }

    public void setPickupUrl(String pickupUrl) {
        this.pickupUrl = pickupUrl;
    }

    public String getReceiveUrl() {
        return receiveUrl;
    }

    public void setReceiveUrl(String receiveUrl) {
        this.receiveUrl = receiveUrl;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getSignType() {
        return signType;
    }

    public void setSignType(int signType) {
        this.signType = signType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    public String getOrderExpireDatetime() {
        return orderExpireDatetime;
    }

    public void setOrderExpireDatetime(String orderExpireDatetime) {
        this.orderExpireDatetime = orderExpireDatetime;
    }

    public String getPayerTelephone() {
        return payerTelephone;
    }

    public void setPayerTelephone(String payerTelephone) {
        this.payerTelephone = payerTelephone;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerIDCard() {
        return payerIDCard;
    }

    public void setPayerIDCard(String payerIDCard) {
        this.payerIDCard = payerIDCard;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExtTL() {
        return extTL;
    }

    public void setExtTL(String extTL) {
        this.extTL = extTL;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getTradeNature() {
        return tradeNature;
    }

    public void setTradeNature(String tradeNature) {
        this.tradeNature = tradeNature;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
