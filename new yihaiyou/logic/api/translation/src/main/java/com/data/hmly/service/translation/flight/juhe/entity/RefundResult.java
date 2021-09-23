package com.data.hmly.service.translation.flight.juhe.entity;

/**
 * Created by Sane on 16/3/17.
 */
public class RefundResult {

    /*
    {
      "error_code": "200",
      "reason": "请求成功",
      "result": {
              "refundNum":xxxx
       }
    }

    {
      "error_code": "215203",
      "reason": "其他异常",
      "result": "申请退票失败：当前订单状态不支持申请退票"
    }
     */

    private String error_code;
    private String reason;
    /**
     * refundNum : xxxx
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
        private String refundNum;

        public void setRefundNum(String refundNum) {
            this.refundNum = refundNum;
        }

        public String getRefundNum() {
            return refundNum;
        }
    }
}
