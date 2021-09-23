package com.data.hmly.service.translation.train.juhe.entity;

/**
 * Created by Sane on 15/12/23.
 */
public class PaymentResult {

    /**
     * error_code : 200
     * reason : 请求成功
     * result : 付款成功
     */

    private String error_code;
    private String reason;
    private String result;

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError_code() {
        return error_code;
    }

    public String getReason() {
        return reason;
    }

    public String getResult() {
        return result;
    }
}
