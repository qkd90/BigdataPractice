package com.hmlyinfo.app.soutu.pay.allin.controller;

import com.allinpay.ets.client.SecurityUtil;

/**
 * Created by guoshijie on 2015/7/22.
 */
public class BatchQueryRequest {

    private String postUrl;
    private String merchantId;
    private String beginDateTime;
    private String endDateTime;
    private String pageNo;
    private String signType;
    private String version;
    private String key;

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(String beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSign() {
        String signSrc = "version=" + version + "&merchantId=" + merchantId + "&beginDateTime=" + beginDateTime + "&endDateTime=" + endDateTime + "&pageNo=" + pageNo + "&signType=" + signType + "&key=" + key;
        String signMsg= SecurityUtil.MD5Encode(signSrc);
        return signMsg;
    }
}
