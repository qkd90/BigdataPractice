package com.data.hmly.service.translation.flight.juhe.entity;

/**
 * Created by Sane on 15/12/23.
 */
public class CheckPriceResult {

    /**
     * error_code : 200
     * reason : 请求成功
     * result : {"resultFlag":"变价","resultPrice":"240.0"}
     */

    private String error_code;
    private String reason;
    /**
     * resultFlag : 变价
     * resultPrice : 240.0
     */

    private ResultEntity result;

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public String getError_code() {
        return error_code;
    }

    public String getReason() {
        return reason;
    }

    public ResultEntity getResult() {
        return result;
    }

    public static class ResultEntity {
        private String resultFlag;
        private String resultPrice;

        public void setResultFlag(String resultFlag) {
            this.resultFlag = resultFlag;
        }

        public void setResultPrice(String resultPrice) {
            this.resultPrice = resultPrice;
        }

        public String getResultFlag() {
            return resultFlag;
        }

        public String getResultPrice() {
            return resultPrice;
        }
    }
}
