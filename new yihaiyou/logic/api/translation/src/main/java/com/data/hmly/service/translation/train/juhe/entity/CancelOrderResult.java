package com.data.hmly.service.translation.train.juhe.entity;

/**
 * Created by Sane on 16/1/8.
 */
public class CancelOrderResult {


    /**
     * reason : 成功的返回
     * result : {"orderid":"1440040885864","user_orderid":"","msg":"订单被取消","status":"1","cancel_time":"2015-08-20 11:22:36"}
     * error_code : 0
     */

    private String reason;
    /**
     * orderid : 1440040885864
     * user_orderid :
     * msg : 订单被取消
     * status : 1
     * cancel_time : 2015-08-20 11:22:36
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
        private String user_orderid;
        private String msg;
        private String status;
        private String cancel_time;

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public void setUser_orderid(String user_orderid) {
            this.user_orderid = user_orderid;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setCancel_time(String cancel_time) {
            this.cancel_time = cancel_time;
        }

        public String getOrderid() {
            return orderid;
        }

        public String getUser_orderid() {
            return user_orderid;
        }

        public String getMsg() {
            return msg;
        }

        public String getStatus() {
            return status;
        }

        public String getCancel_time() {
            return cancel_time;
        }
    }
}
