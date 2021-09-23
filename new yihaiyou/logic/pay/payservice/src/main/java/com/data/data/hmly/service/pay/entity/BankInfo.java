package com.data.data.hmly.service.pay.entity;

/**
 * Created by huangpeijie on 2017-03-03,0003.
 */
public class BankInfo {
    private String bankNo;
    private String holderName;
    private String bankName;
    private String province;
    private String city;

    public BankInfo() {
    }

    public BankInfo(String bankNo, String holderName, String bankName, String province, String city) {
        this.bankNo = bankNo;
        this.holderName = holderName;
        this.bankName = bankName;
        this.province = province;
        this.city = city;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
