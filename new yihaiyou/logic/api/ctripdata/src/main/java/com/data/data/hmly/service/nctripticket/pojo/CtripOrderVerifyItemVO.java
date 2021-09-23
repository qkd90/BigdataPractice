package com.data.data.hmly.service.nctripticket.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by caiys on 2016/2/18.
 */
public class CtripOrderVerifyItemVO {
    private String verifyKey;   // 验证字段名称，固定填”UID”
    private String verifyValue; // 验证字段对应值

    @JsonProperty("VerifyKey")
    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }

    @JsonProperty("VerifyValue")
    public String getVerifyValue() {
        return verifyValue;
    }

    public void setVerifyValue(String verifyValue) {
        this.verifyValue = verifyValue;
    }
}
