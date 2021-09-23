package com.data.hmly.service.translation.flight.juhe.entity;

import java.util.List;

/**
 * Created by Sane on 16/3/28.
 */
public class CancelCheckResult {

    /**
     * error_code : 200
     * reason : 请求成功
     * result : {"billNum":"101505190016","billStatus":"5000","orderInfos":[{"airportPrice":"50","backPolicy":"航班规定离站时间前2小时（含）前：收取舱位对应公布运价10％的退票费；航班规定离站时间前2小时之内及航班起飞后：收取舱位对应公布运价20％的退票费（以客票上的航班起飞时间为准）","cabin":"Y","changeAmount":"0","changePolicy":"航班规定离站时间前2小时（含）前:免收改期费；航班规定离站时间前2小时之内及航班起飞后：按公布运价10%收取改期费。如客票航班或日期变更与舱位变更同时进行,则需同时收取变更费和升舱补差费（以客票上的航班起飞时间为准）","finallyPrice":"970","flightDate":"2015-06-17","flightNum":"CZ6890","fuelPrice":"60","idNumber":"321282198912244014","idType":"0","issueDate":{"nil":"","xsi":""},"landingPort":"CSX","orderDate":"2015-05-19 15:42:33","orderNum":"101505190016_1","orderStatus":"50301","orderType":"O","passengerMobile":"xxx","passengerName":"曹键","passengerType":"ADULT","refundAmount":"0","signPolicy":"允许签转","takeoffPort":"SZX","ticketNum":"524-645632135465","ticketStatus":"_10_OPEN_FOR_USE"},{"airportPrice":"0","backPolicy":"航班规定离站时间前2小时（含）前：收取舱位对应公布运价10％的退票费；航班规定离站时间前2小时之内及航班起飞后：收取舱位对应公布运价20％的退票费（以客票上的航班起飞时间为准）","cabin":"Y","changeAmount":"0","changePolicy":"航班规定离站时间前2小时（含）前:免收改期费；航班规定离站时间前2小时之内及航班起飞后：按公布运价10%收取改期费。如客票航班或日期变更与舱位变更同时进行,则需同时收取变更费和升舱补差费（以客票上的航班起飞时间为准）","finallyPrice":"0","flightDate":"2015-06-20","flightNum":"CZ6890","fuelPrice":"0","idNumber":"xxxx","idType":"0","issueDate":{"nil":"1","xsi":"http://www.w3.org/2001/XMLSchema-instance"},"landingPort":"CSX","orderDate":"2015-05-20 10:29:10","orderNum":"101505190016_1-G1","orderStatus":"10000","orderType":"G","passengerMobile":"xxx","passengerName":"曹键","passengerType":"ADULT","refundAmount":"0","signPolicy":"允许签转","takeoffPort":"SZX","ticketNum":"524-645632135465","ticketStatus":"_10_OPEN_FOR_USE"}]}
     */

    private String error_code;
    private String reason;
    /**
     * billNum : 101505190016
     * billStatus : 5000
     * orderInfos : [{"airportPrice":"50","backPolicy":"航班规定离站时间前2小时（含）前：收取舱位对应公布运价10％的退票费；航班规定离站时间前2小时之内及航班起飞后：收取舱位对应公布运价20％的退票费（以客票上的航班起飞时间为准）","cabin":"Y","changeAmount":"0","changePolicy":"航班规定离站时间前2小时（含）前:免收改期费；航班规定离站时间前2小时之内及航班起飞后：按公布运价10%收取改期费。如客票航班或日期变更与舱位变更同时进行,则需同时收取变更费和升舱补差费（以客票上的航班起飞时间为准）","finallyPrice":"970","flightDate":"2015-06-17","flightNum":"CZ6890","fuelPrice":"60","idNumber":"321282198912244014","idType":"0","issueDate":{"nil":"","xsi":""},"landingPort":"CSX","orderDate":"2015-05-19 15:42:33","orderNum":"101505190016_1","orderStatus":"50301","orderType":"O","passengerMobile":"xxx","passengerName":"曹键","passengerType":"ADULT","refundAmount":"0","signPolicy":"允许签转","takeoffPort":"SZX","ticketNum":"524-645632135465","ticketStatus":"_10_OPEN_FOR_USE"},{"airportPrice":"0","backPolicy":"航班规定离站时间前2小时（含）前：收取舱位对应公布运价10％的退票费；航班规定离站时间前2小时之内及航班起飞后：收取舱位对应公布运价20％的退票费（以客票上的航班起飞时间为准）","cabin":"Y","changeAmount":"0","changePolicy":"航班规定离站时间前2小时（含）前:免收改期费；航班规定离站时间前2小时之内及航班起飞后：按公布运价10%收取改期费。如客票航班或日期变更与舱位变更同时进行,则需同时收取变更费和升舱补差费（以客票上的航班起飞时间为准）","finallyPrice":"0","flightDate":"2015-06-20","flightNum":"CZ6890","fuelPrice":"0","idNumber":"xxxx","idType":"0","issueDate":{"nil":"1","xsi":"http://www.w3.org/2001/XMLSchema-instance"},"landingPort":"CSX","orderDate":"2015-05-20 10:29:10","orderNum":"101505190016_1-G1","orderStatus":"10000","orderType":"G","passengerMobile":"xxx","passengerName":"曹键","passengerType":"ADULT","refundAmount":"0","signPolicy":"允许签转","takeoffPort":"SZX","ticketNum":"524-645632135465","ticketStatus":"_10_OPEN_FOR_USE"}]
     */

    private ResultEntity result;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public static class ResultEntity {
        private String billNum;
        private String billStatus;
        /**
         * airportPrice : 50
         * backPolicy : 航班规定离站时间前2小时（含）前：收取舱位对应公布运价10％的退票费；航班规定离站时间前2小时之内及航班起飞后：收取舱位对应公布运价20％的退票费（以客票上的航班起飞时间为准）
         * cabin : Y
         * changeAmount : 0
         * changePolicy : 航班规定离站时间前2小时（含）前:免收改期费；航班规定离站时间前2小时之内及航班起飞后：按公布运价10%收取改期费。如客票航班或日期变更与舱位变更同时进行,则需同时收取变更费和升舱补差费（以客票上的航班起飞时间为准）
         * finallyPrice : 970
         * flightDate : 2015-06-17
         * flightNum : CZ6890
         * fuelPrice : 60
         * idNumber : 321282198912244014
         * idType : 0
         * issueDate : {"nil":"","xsi":""}
         * landingPort : CSX
         * orderDate : 2015-05-19 15:42:33
         * orderNum : 101505190016_1
         * orderStatus : 50301
         * orderType : O
         * passengerMobile : xxx
         * passengerName : 曹键
         * passengerType : ADULT
         * refundAmount : 0
         * signPolicy : 允许签转
         * takeoffPort : SZX
         * ticketNum : 524-645632135465
         * ticketStatus : _10_OPEN_FOR_USE
         */

        private List<OrderInfosEntity> orderInfos;

        public String getBillNum() {
            return billNum;
        }

        public void setBillNum(String billNum) {
            this.billNum = billNum;
        }

        public String getBillStatus() {
            return billStatus;
        }

        public void setBillStatus(String billStatus) {
            this.billStatus = billStatus;
        }

        public List<OrderInfosEntity> getOrderInfos() {
            return orderInfos;
        }

        public void setOrderInfos(List<OrderInfosEntity> orderInfos) {
            this.orderInfos = orderInfos;
        }

        public static class OrderInfosEntity {
            private String airportPrice;
            private String backPolicy;
            private String cabin;
            private String changeAmount;
            private String changePolicy;
            private String finallyPrice;
            private String flightDate;
            private String flightNum;
            private String fuelPrice;
            private String idNumber;
            private String idType;
            /**
             * nil :
             * xsi :
             */

            private IssueDateEntity issueDate;
            private String landingPort;
            private String orderDate;
            private String orderNum;
            private String orderStatus;
            private String orderType;
            private String passengerMobile;
            private String passengerName;
            private String passengerType;
            private String refundAmount;
            private String signPolicy;
            private String takeoffPort;
            private String ticketNum;
            private String ticketStatus;

            public String getAirportPrice() {
                return airportPrice;
            }

            public void setAirportPrice(String airportPrice) {
                this.airportPrice = airportPrice;
            }

            public String getBackPolicy() {
                return backPolicy;
            }

            public void setBackPolicy(String backPolicy) {
                this.backPolicy = backPolicy;
            }

            public String getCabin() {
                return cabin;
            }

            public void setCabin(String cabin) {
                this.cabin = cabin;
            }

            public String getChangeAmount() {
                return changeAmount;
            }

            public void setChangeAmount(String changeAmount) {
                this.changeAmount = changeAmount;
            }

            public String getChangePolicy() {
                return changePolicy;
            }

            public void setChangePolicy(String changePolicy) {
                this.changePolicy = changePolicy;
            }

            public String getFinallyPrice() {
                return finallyPrice;
            }

            public void setFinallyPrice(String finallyPrice) {
                this.finallyPrice = finallyPrice;
            }

            public String getFlightDate() {
                return flightDate;
            }

            public void setFlightDate(String flightDate) {
                this.flightDate = flightDate;
            }

            public String getFlightNum() {
                return flightNum;
            }

            public void setFlightNum(String flightNum) {
                this.flightNum = flightNum;
            }

            public String getFuelPrice() {
                return fuelPrice;
            }

            public void setFuelPrice(String fuelPrice) {
                this.fuelPrice = fuelPrice;
            }

            public String getIdNumber() {
                return idNumber;
            }

            public void setIdNumber(String idNumber) {
                this.idNumber = idNumber;
            }

            public String getIdType() {
                return idType;
            }

            public void setIdType(String idType) {
                this.idType = idType;
            }

            public IssueDateEntity getIssueDate() {
                return issueDate;
            }

            public void setIssueDate(IssueDateEntity issueDate) {
                this.issueDate = issueDate;
            }

            public String getLandingPort() {
                return landingPort;
            }

            public void setLandingPort(String landingPort) {
                this.landingPort = landingPort;
            }

            public String getOrderDate() {
                return orderDate;
            }

            public void setOrderDate(String orderDate) {
                this.orderDate = orderDate;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderType() {
                return orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public String getPassengerMobile() {
                return passengerMobile;
            }

            public void setPassengerMobile(String passengerMobile) {
                this.passengerMobile = passengerMobile;
            }

            public String getPassengerName() {
                return passengerName;
            }

            public void setPassengerName(String passengerName) {
                this.passengerName = passengerName;
            }

            public String getPassengerType() {
                return passengerType;
            }

            public void setPassengerType(String passengerType) {
                this.passengerType = passengerType;
            }

            public String getRefundAmount() {
                return refundAmount;
            }

            public void setRefundAmount(String refundAmount) {
                this.refundAmount = refundAmount;
            }

            public String getSignPolicy() {
                return signPolicy;
            }

            public void setSignPolicy(String signPolicy) {
                this.signPolicy = signPolicy;
            }

            public String getTakeoffPort() {
                return takeoffPort;
            }

            public void setTakeoffPort(String takeoffPort) {
                this.takeoffPort = takeoffPort;
            }

            public String getTicketNum() {
                return ticketNum;
            }

            public void setTicketNum(String ticketNum) {
                this.ticketNum = ticketNum;
            }

            public String getTicketStatus() {
                return ticketStatus;
            }

            public void setTicketStatus(String ticketStatus) {
                this.ticketStatus = ticketStatus;
            }

            public static class IssueDateEntity {
                private String nil;
                private String xsi;

                public String getNil() {
                    return nil;
                }

                public void setNil(String nil) {
                    this.nil = nil;
                }

                public String getXsi() {
                    return xsi;
                }

                public void setXsi(String xsi) {
                    this.xsi = xsi;
                }
            }
        }
    }
}
