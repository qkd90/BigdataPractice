package com.data.hmly.service.translation.train.juhe.entity;

/**
 * Created by Sane on 16/1/8.
 */
public class RefundResult {


    /**
     * reason : 成功的返回
     * result : {"msg":"退票请求已接收，正在处理","refund_time":"2015-07-10 15:54:16","status":"6"}
     * error_code : 0
     */

    private String reason;
    /**
     * msg : 退票请求已接收，正在处理
     * refund_time : 2015-07-10 15:54:16
     * status : 6
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
        private String msg;
        private String refund_time;
        private String status;

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setRefund_time(String refund_time) {
            this.refund_time = refund_time;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public String getRefund_time() {
            return refund_time;
        }

        public String getStatus() {
            return status;
        }
    }
}