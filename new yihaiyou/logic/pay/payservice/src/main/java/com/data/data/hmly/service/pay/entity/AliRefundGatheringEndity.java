package com.data.data.hmly.service.pay.entity;

/**
 * Created by dy on 2016/5/23.
 */
public class AliRefundGatheringEndity {

//    2011011201037066^5.00^协商退款
//    交易退款数据集的格式为：原付款支付宝交易号^退款总金额^退款理由；

    private String tradeNo;                   //支付宝交易时生成的支付宝交易号
    private Float refundFee;                //退款总金额
    private String refundDesc;             //退款理由

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Float getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Float refundFee) {
        this.refundFee = refundFee;
    }

    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
    }
}
