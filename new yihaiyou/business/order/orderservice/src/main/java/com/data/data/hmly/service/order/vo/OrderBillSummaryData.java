package com.data.data.hmly.service.order.vo;

import com.data.data.hmly.service.order.entity.enums.OrderBillType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by zzl on 2016/10/28.
 */
public class OrderBillSummaryData {
    private Date billDate;
    private OrderBillType billType;
    private BigInteger companyUnitId;
    private Double alreadyBillPrice;
    private Double notBillPrice;

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public OrderBillType getBillType() {
        return billType;
    }

    public void setBillType(OrderBillType billType) {
        this.billType = billType;
    }

    public BigInteger getCompanyUnitId() {
        return companyUnitId;
    }

    public void setCompanyUnitId(BigInteger companyUnitId) {
        this.companyUnitId = companyUnitId;
    }

    public Double getAlreadyBillPrice() {
        return alreadyBillPrice;
    }

    public void setAlreadyBillPrice(Double alreadyBillPrice) {
        this.alreadyBillPrice = alreadyBillPrice;
    }

    public Double getNotBillPrice() {
        return notBillPrice;
    }

    public void setNotBillPrice(Double notBillPrice) {
        this.notBillPrice = notBillPrice;
    }
}