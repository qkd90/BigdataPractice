package com.data.hmly.service.translation.flight.juhe.entity;

/**
 * Created by Sane on 15/12/23.
 */
public class RefundCheckResult {

    /**
     * error_code : 200
     * reason : 请求成功
     * result : {"refundCheckResult":{"fee":"97.0","flag":"Y","message":""}}
     */

    private String error_code;
    private String reason;
    /**
     * refundCheckResult : {"fee":"97.0","flag":"Y","message":""}
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
        /**
         * fee : 97.0
         * flag : Y
         * message :
         */

        private RefundCheckResultEntity refundCheckResult;

        public void setRefundCheckResult(RefundCheckResultEntity refundCheckResult) {
            this.refundCheckResult = refundCheckResult;
        }

        public RefundCheckResultEntity getRefundCheckResult() {
            return refundCheckResult;
        }

        public static class RefundCheckResultEntity {
            private String fee;
            private String flag;
            private String message;

            public void setFee(String fee) {
                this.fee = fee;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getFee() {
                return fee;
            }

            public String getFlag() {
                return flag;
            }

            public String getMessage() {
                return message;
            }
        }
    }
}
