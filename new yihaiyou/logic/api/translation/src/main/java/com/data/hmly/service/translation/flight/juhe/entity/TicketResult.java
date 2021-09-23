package com.data.hmly.service.translation.flight.juhe.entity;

import java.util.List;

/**
 * Created by Sane on 15/12/23.
 */
public class TicketResult {

    /**
     * error_code : 错误号
     * reason : 原因
     * result : {"billNum":"订单号","billStatus":"订单状态 ","ticketList":[{"extInfo":[{"key":"orderNum","value":"101512220702_1"}],"idNumber":"id号","idType":"id类型","passengerName":"名字","passengerMobile":"电话","ticketNum":"票号","ticketStatus":"票状态","finallyPrice":"最价家","airportPrice":"机场建设费 ","fuelPrice":"燃油费","tripInsuCount":"","refundNum":"","refundFee":"","changeNum":"","changeFee":"","changeDetail":"","sum":"总价","updatetime":"更新时间","memo":""}]}
     */

    private String error_code;
    private String reason;
    /**
     * billNum : 订单号
     * billStatus : 订单状态
     * ticketList : [{"extInfo":[{"key":"orderNum","value":"101512220702_1"}],"idNumber":"id号","idType":"id类型","passengerName":"名字","passengerMobile":"电话","ticketNum":"票号","ticketStatus":"票状态","finallyPrice":"最价家","airportPrice":"机场建设费 ","fuelPrice":"燃油费","tripInsuCount":"","refundNum":"","refundFee":"","changeNum":"","changeFee":"","changeDetail":"","sum":"总价","updatetime":"更新时间","memo":""}]
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
        private String billNum;
        private String billStatus;
        /**
         * extInfo : [{"key":"orderNum","value":"101512220702_1"}]
         * idNumber : id号
         * idType : id类型
         * passengerName : 名字
         * passengerMobile : 电话
         * ticketNum : 票号
         * ticketStatus : 票状态
         * finallyPrice : 最价家
         * airportPrice : 机场建设费
         * fuelPrice : 燃油费
         * tripInsuCount :
         * refundNum :
         * refundFee :
         * changeNum :
         * changeFee :
         * changeDetail :
         * sum : 总价
         * updatetime : 更新时间
         * memo :
         */

        private List<TicketListEntity> ticketList;

        public void setBillNum(String billNum) {
            this.billNum = billNum;
        }

        public void setBillStatus(String billStatus) {
            this.billStatus = billStatus;
        }

        public void setTicketList(List<TicketListEntity> ticketList) {
            this.ticketList = ticketList;
        }

        public String getBillNum() {
            return billNum;
        }

        public String getBillStatus() {
            return billStatus;
        }

        public List<TicketListEntity> getTicketList() {
            return ticketList;
        }

        public static class TicketListEntity {
            private String idNumber;
            private String idType;
            private String passengerName;
            private String passengerMobile;
            private String ticketNum;
            private String ticketStatus;
            private String finallyPrice;
            private String airportPrice;
            private String fuelPrice;
            private String tripInsuCount;
            private String refundNum;
            private String refundFee;
            private String changeNum;
            private String changeFee;
            private String changeDetail;
            private String sum;
            private String updatetime;
            private String memo;
            private List<ExtInfoEntity> extInfo;

            public void setIdNumber(String idNumber) {
                this.idNumber = idNumber;
            }

            public void setIdType(String idType) {
                this.idType = idType;
            }

            public void setPassengerName(String passengerName) {
                this.passengerName = passengerName;
            }

            public void setPassengerMobile(String passengerMobile) {
                this.passengerMobile = passengerMobile;
            }

            public void setTicketNum(String ticketNum) {
                this.ticketNum = ticketNum;
            }

            public void setTicketStatus(String ticketStatus) {
                this.ticketStatus = ticketStatus;
            }

            public void setFinallyPrice(String finallyPrice) {
                this.finallyPrice = finallyPrice;
            }

            public void setAirportPrice(String airportPrice) {
                this.airportPrice = airportPrice;
            }

            public void setFuelPrice(String fuelPrice) {
                this.fuelPrice = fuelPrice;
            }

            public void setTripInsuCount(String tripInsuCount) {
                this.tripInsuCount = tripInsuCount;
            }

            public void setRefundNum(String refundNum) {
                this.refundNum = refundNum;
            }

            public void setRefundFee(String refundFee) {
                this.refundFee = refundFee;
            }

            public void setChangeNum(String changeNum) {
                this.changeNum = changeNum;
            }

            public void setChangeFee(String changeFee) {
                this.changeFee = changeFee;
            }

            public void setChangeDetail(String changeDetail) {
                this.changeDetail = changeDetail;
            }

            public void setSum(String sum) {
                this.sum = sum;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public void setExtInfo(List<ExtInfoEntity> extInfo) {
                this.extInfo = extInfo;
            }

            /**
             *
             * @return id号
             */
            public String getIdNumber() {
                return idNumber;
            }

            /**
             * @return id类型
             */
            public String getIdType() {
                return idType;
            }

            /**
             * @return 联系人姓名
             */
            public String getPassengerName() {
                return passengerName;
            }

            /**
             * @return 联系人手机
             */
            public String getPassengerMobile() {
                return passengerMobile;
            }

            /**
             * @return 票号
             */
            public String getTicketNum() {
                return ticketNum;
            }

            /**
             * @return 票单状态
             * SUCC_CREATE：预定成功，FAIL_CREATE：预定失败，SUCC_CANCEL：取消订单成功，
             * FAIL_CANCEL：取消订单失败，ISSUE_ING：正在出票，SUCC_ISSUE：出票成功，FAIL_ISSUE：出票失败，
             * REFUND_ING：正在退票，SUCC_REFUND：退票成功，FAIL_REFUND：退票失败，CHANGE_ING：正在改期，
             * SUCC_CHANGE：改期成功，FAIL_CHANGE：改期失败
             */
            public String getTicketStatus() {
                return ticketStatus;
            }

            /**
             * @return 最终价格
             */
            public String getFinallyPrice() {
                return finallyPrice;
            }

            /**
             * @return 机场建设费
             */
            public String getAirportPrice() {
                return airportPrice;
            }

            /**
             * @return 燃油税
             */
            public String getFuelPrice() {
                return fuelPrice;
            }

            /**
             * @return
             */
            public String getTripInsuCount() {
                return tripInsuCount;
            }

            /**
             * @return 退票单号
             */
            public String getRefundNum() {
                return refundNum;
            }

            /**
             * @return 退票手续费
             */
            public String getRefundFee() {
                return refundFee;
            }

            /**
             * @return 改签单号
             */
            public String getChangeNum() {
                return changeNum;
            }

            /**
             * @return 改签手续费
             */
            public String getChangeFee() {
                return changeFee;
            }

            /**
             * @return 改签详情
             */
            public String getChangeDetail() {
                return changeDetail;
            }

            /**
             * @return 订单总额
             */
            public String getSum() {
                return sum;
            }

            /**
             * @return 更新时间
             */
            public String getUpdatetime() {
                return updatetime;
            }

            /**
             * @return 备注
             */
            public String getMemo() {
                return memo;
            }

            /**
             * @return
             */
            public List<ExtInfoEntity> getExtInfo() {
                return extInfo;
            }

            public static class ExtInfoEntity {
                private String key;
                private String value;

                public void setKey(String key) {
                    this.key = key;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getKey() {
                    return key;
                }

                public String getValue() {
                    return value;
                }
            }
        }
    }
}
