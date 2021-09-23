package com.gson.bean;

import java.util.List;

/**
 * Created by dy on 2016/9/8.
 */
public class QueryRefundResult {

    private Integer refundCount;
    private String tradeNo;
    private Boolean returnCode;
    private Integer totalFee;
    private Integer refundFee;
    private Boolean resultCode;
    private String returnMsg;
    private String errCode;
    private String errCodeDesc;

    private List<RefundResultDetail> refundResultDetailList;

    public Integer getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(Integer refundCount) {
        this.refundCount = refundCount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Boolean getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Boolean returnCode) {
        this.returnCode = returnCode;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Boolean getResultCode() {
        return resultCode;
    }

    public void setResultCode(Boolean resultCode) {
        this.resultCode = resultCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public List<RefundResultDetail> getRefundResultDetailList() {
        return refundResultDetailList;
    }

    public void setRefundResultDetailList(List<RefundResultDetail> refundResultDetailList) {
        this.refundResultDetailList = refundResultDetailList;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDesc() {
        return errCodeDesc;
    }

    public void setErrCodeDesc(String errCodeDesc) {
        this.errCodeDesc = errCodeDesc;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }
}
