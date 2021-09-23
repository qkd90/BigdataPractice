package com.data.data.hmly.service.plan.vo;


import java.math.BigInteger;

/**
 * Created by ZZL on 2015/11/20.
 * Update on 2016/02/03.
 */
public class RecommendPlanCountInfo {

    private BigInteger cityId;
    private String fullPath;
    private BigInteger totalCount;
    private BigInteger onSaleCount;
    private BigInteger offSaleCount;
    private BigInteger draftCount;

    public BigInteger getCityId() {
        return cityId;
    }

    public void setCityId(BigInteger cityId) {
        this.cityId = cityId;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public BigInteger getOnSaleCount() {
        return onSaleCount;
    }

    public void setOnSaleCount(BigInteger onSaleCount) {
        this.onSaleCount = onSaleCount;
    }

    public BigInteger getOffSaleCount() {
        return offSaleCount;
    }

    public void setOffSaleCount(BigInteger offSaleCount) {
        this.offSaleCount = offSaleCount;
    }

    public BigInteger getDraftCount() {
        return draftCount;
    }

    public void setDraftCount(BigInteger draftCount) {
        this.draftCount = draftCount;
    }
}
