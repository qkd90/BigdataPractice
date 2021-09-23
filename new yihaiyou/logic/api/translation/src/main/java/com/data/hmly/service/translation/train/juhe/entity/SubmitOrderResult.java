package com.data.hmly.service.translation.train.juhe.entity;

/**
 * Created by Sane on 16/1/8.
 */
public class SubmitOrderResult {

    /**
     * reason : 成功的返回
     * result : {"orderid":"1452242394305"}
     * error_code : 0
     */

    private String reason;
    /**
     * orderid : 1452242394305
     */

    private ResultEntity result;
    private int error_code;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public ResultEntity getResult() {
        return result;
    }

    public int getError_code() {
        return error_code;
    }

    public static class ResultEntity {
        private String orderid;

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getOrderid() {
            return orderid;
        }
    }
}
