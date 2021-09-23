package com.data.hmly.service.translation.flight.juhe.entity;

import java.util.List;

/**
 * 聚合接口：航班政策信息查询服务
 * Created by Sane on 15/12/23.
 */
public class FlightPolicy {

    private String error_code;
    private String reason;


    private List<ResultEntity> result;

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }


    public String getError_code() {
        return error_code;
    }

    public String getReason() {
        return reason;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity {
        private String adultAirportPrice;
        private String adultFuelPrice;

        private String airline;
        private String airlineName;
        private String babyAirportPrice;
        private String babyFuelPrice;
        private String childAirportPrice;
        private String childFuelPrice;


        private ExtInfoEntity extInfo;
        private String flightNum;
        private String fromCity;
        private String fromCityName;
        private String fromTerminal;
        private String landingPort;
        private String landingPortName;
        private String landingTime;
        private String planeType;
        private String planeTypeDesc;
        private String punctualityRate;
        private String shareFlag;
        private String shareFlightNum;
        private String stopDesc;
        private String stops;
        private String takeoffPort;
        private String takeoffPortName;
        private String takeoffTime;
        private String toCity;
        private String toCityName;
        private String toTerminal;
        private List<CabinInfosEntity> cabinInfos;

        public void setAdultAirportPrice(String adultAirportPrice) {
            this.adultAirportPrice = adultAirportPrice;
        }

        public void setAdultFuelPrice(String adultFuelPrice) {
            this.adultFuelPrice = adultFuelPrice;
        }

        /**
         * 航空公司二字码
         */
        public void setAirline(String airline) {
            this.airline = airline;
        }

        public void setAirlineName(String airlineName) {
            this.airlineName = airlineName;
        }

        public void setBabyAirportPrice(String babyAirportPrice) {
            this.babyAirportPrice = babyAirportPrice;
        }

        public void setBabyFuelPrice(String babyFuelPrice) {
            this.babyFuelPrice = babyFuelPrice;
        }

        public void setChildAirportPrice(String childAirportPrice) {
            this.childAirportPrice = childAirportPrice;
        }

        public void setChildFuelPrice(String childFuelPrice) {
            this.childFuelPrice = childFuelPrice;
        }

        public void setExtInfo(ExtInfoEntity extInfo) {
            this.extInfo = extInfo;
        }

        public void setFlightNum(String flightNum) {
            this.flightNum = flightNum;
        }

        public void setFromCity(String fromCity) {
            this.fromCity = fromCity;
        }

        public void setFromCityName(String fromCityName) {
            this.fromCityName = fromCityName;
        }

        public void setFromTerminal(String fromTerminal) {
            this.fromTerminal = fromTerminal;
        }

        public void setLandingPort(String landingPort) {
            this.landingPort = landingPort;
        }

        public void setLandingPortName(String landingPortName) {
            this.landingPortName = landingPortName;
        }

        public void setLandingTime(String landingTime) {
            this.landingTime = landingTime;
        }

        public void setPlaneType(String planeType) {
            this.planeType = planeType;
        }

        public void setPlaneTypeDesc(String planeTypeDesc) {
            this.planeTypeDesc = planeTypeDesc;
        }

        public void setPunctualityRate(
                String punctualityRate) {
            this.punctualityRate = punctualityRate;
        }

        public void setShareFlag(String shareFlag) {
            this.shareFlag = shareFlag;
        }

        public void setShareFlightNum(String shareFlightNum) {
            this.shareFlightNum = shareFlightNum;
        }

        public void setStopDesc(String stopDesc) {
            this.stopDesc = stopDesc;
        }

        public void setStops(String stops) {
            this.stops = stops;
        }

        public void setTakeoffPort(String takeoffPort) {
            this.takeoffPort = takeoffPort;
        }

        public void setTakeoffPortName(String takeoffPortName) {
            this.takeoffPortName = takeoffPortName;
        }

        public void setTakeoffTime(String takeoffTime) {
            this.takeoffTime = takeoffTime;
        }

        public void setToCity(String toCity) {
            this.toCity = toCity;
        }

        public void setToCityName(String toCityName) {
            this.toCityName = toCityName;
        }

        public void setToTerminal(String toTerminal) {
            this.toTerminal = toTerminal;
        }

        public void setCabinInfos(List<CabinInfosEntity> cabinInfos) {
            this.cabinInfos = cabinInfos;
        }

        /**
         * @return 成人机建税
         */
        public String getAdultAirportPrice() {
            return adultAirportPrice;
        }

        /**
         * @return 成人燃油税
         */
        public String getAdultFuelPrice() {
            return adultFuelPrice;
        }

        /**
         * 航空公司二字码
         *
         * @return 航空公司二字码
         */
        public String getAirline() {
            return airline;
        }

        /**
         * @return 航空公司名
         */
        public String getAirlineName() {
            return airlineName;
        }

        /**
         * @return 婴儿机建税
         */
        public String getBabyAirportPrice() {
            return babyAirportPrice;
        }

        /**
         * fefawef
         *
         * @return 婴儿燃油税
         */
        public String getBabyFuelPrice() {
            return babyFuelPrice;
        }

        /**
         * @return 儿童机建税
         */
        public String getChildAirportPrice() {
            return childAirportPrice;
        }

        /**
         * @return 儿童燃油税
         */
        public String getChildFuelPrice() {
            return childFuelPrice;
        }

        /**
         * @return 扩展信息，如餐食等
         */
        public ExtInfoEntity getExtInfo() {
            return extInfo;
        }

        /**
         * @return 航班号
         */
        public String getFlightNum() {
            return flightNum;
        }

        /**
         * @return 出发城市三字码
         */
        public String getFromCity() {
            return fromCity;
        }

        /**
         * @return 出发城市名称
         */
        public String getFromCityName() {
            return fromCityName;
        }

        /**
         * @return 出发航站楼
         */
        public String getFromTerminal() {
            return fromTerminal;
        }

        /**
         * @return 降落机场三字码
         */
        public String getLandingPort() {
            return landingPort;
        }

        /**
         * @return 降落机场名称
         */
        public String getLandingPortName() {
            return landingPortName;
        }

        /**
         * @return 降落时间
         */
        public String getLandingTime() {
            return landingTime;
        }

        /**
         * @return 机型
         */
        public String getPlaneType() {
            return planeType;
        }

        /**
         * @return 机型说明
         */
        public String getPlaneTypeDesc() {
            return planeTypeDesc;
        }

        /**
         * @return 准点率
         */
        public String getPunctualityRate() {
            return punctualityRate;
        }

        /**
         * @return 是否共享航班
         */
        public String getShareFlag() {
            return shareFlag;
        }

        /**
         * @return 共享航班号
         */
        public String getShareFlightNum() {
            return shareFlightNum;
        }

        /**
         * @return 中停信息
         */
        public String getStopDesc() {
            return stopDesc;
        }

        /**
         * @return 中停数
         */
        public String getStops() {
            return stops;
        }

        /**
         * @return 起飞机场三字码
         */
        public String getTakeoffPort() {
            return takeoffPort;
        }

        /**
         * @return 起飞机场名称
         */
        public String getTakeoffPortName() {
            return takeoffPortName;
        }

        /**
         * @return 起飞时间
         */
        public String getTakeoffTime() {
            return takeoffTime;
        }

        /**
         * @return 到达城市三字码
         */
        public String getToCity() {
            return toCity;
        }

        /**
         * @return 到达城市名称
         */
        public String getToCityName() {
            return toCityName;
        }

        /**
         * @return 到达航站楼
         */
        public String getToTerminal() {
            return toTerminal;
        }

        /**
         * @return 舱位
         */
        public List<CabinInfosEntity> getCabinInfos() {
            return cabinInfos;
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

        public static class CabinInfosEntity {
            private String airRemark;
            private String babyBackPolicy;
            private String babyChangePolicy;
            private String babySignPolicy;
            private String backPolicy;
            private String baseCabin;
            private String cabin;
            private String cabinGrade;
            private String changePolicy;
            private String childBackPolicy;
            private String childChangePolicy;
            private String childSignPolicy;
            private String discount;
            private String finallyPrice;
            private String initTicketPrice;
            private String patFlag;
            private String protocolTicketPrice;
            private String rebateAmount;
            private String seating;
            private String signPolicy;
            private String standardPrice;

            public void setAirRemark(String airRemark) {
                this.airRemark = airRemark;
            }

            public void setBabyBackPolicy(String babyBackPolicy) {
                this.babyBackPolicy = babyBackPolicy;
            }

            public void setBabyChangePolicy(String babyChangePolicy) {
                this.babyChangePolicy = babyChangePolicy;
            }

            public void setBabySignPolicy(String babySignPolicy) {
                this.babySignPolicy = babySignPolicy;
            }

            public void setBackPolicy(String backPolicy) {
                this.backPolicy = backPolicy;
            }

            public void setBaseCabin(String baseCabin) {
                this.baseCabin = baseCabin;
            }

            public void setCabin(String cabin) {
                this.cabin = cabin;
            }

            public void setCabinGrade(String cabinGrade) {
                this.cabinGrade = cabinGrade;
            }

            public void setChangePolicy(String changePolicy) {
                this.changePolicy = changePolicy;
            }

            public void setChildBackPolicy(String childBackPolicy) {
                this.childBackPolicy = childBackPolicy;
            }

            public void setChildChangePolicy(String childChangePolicy) {
                this.childChangePolicy = childChangePolicy;
            }

            public void setChildSignPolicy(String childSignPolicy) {
                this.childSignPolicy = childSignPolicy;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public void setFinallyPrice(String finallyPrice) {
                this.finallyPrice = finallyPrice;
            }

            public void setInitTicketPrice(String initTicketPrice) {
                this.initTicketPrice = initTicketPrice;
            }

            public void setPatFlag(String patFlag) {
                this.patFlag = patFlag;
            }

            public void setProtocolTicketPrice(String protocolTicketPrice) {
                this.protocolTicketPrice = protocolTicketPrice;
            }

            public void setRebateAmount(String rebateAmount) {
                this.rebateAmount = rebateAmount;
            }

            public void setSeating(String seating) {
                this.seating = seating;
            }

            public void setSignPolicy(String signPolicy) {
                this.signPolicy = signPolicy;
            }

            public void setStandardPrice(String standardPrice) {
                this.standardPrice = standardPrice;
            }

            /**
             * @return 备注
             */
            public String getAirRemark() {
                return airRemark;
            }

            /**
             * @return 婴儿票退票规定
             */
            public String getBabyBackPolicy() {
                return babyBackPolicy;
            }

            /**
             * @return 婴儿票改期规定
             */
            public String getBabyChangePolicy() {
                return babyChangePolicy;
            }

            /**
             * @return 婴儿票签转规定
             */
            public String getBabySignPolicy() {
                return babySignPolicy;
            }

            /**
             * @return 退票规定
             */
            public String getBackPolicy() {
                return backPolicy;
            }

            /**
             * @return 基础舱位 (Y表示经济 C表示商务 F表示头等)
             */
            public String getBaseCabin() {
                return baseCabin;
            }

            /**
             * @return 舱位
             */
            public String getCabin() {
                return cabin;
            }

            /**
             * @return 舱位等级
             */
            public String getCabinGrade() {
                return cabinGrade;
            }

            /**
             * @return 改期规定
             */
            public String getChangePolicy() {
                return changePolicy;
            }

            /**
             * @return 儿童票退票规定
             */
            public String getChildBackPolicy() {
                return childBackPolicy;
            }

            /**
             * @return 儿童票改期规定
             */
            public String getChildChangePolicy() {
                return childChangePolicy;
            }

            /**
             * @return 儿童票签转规定
             */
            public String getChildSignPolicy() {
                return childSignPolicy;
            }

            /**
             * @return 折扣
             */
            public String getDiscount() {
                return discount;
            }

            /**
             * @return 最终价
             */
            public String getFinallyPrice() {
                return finallyPrice;
            }

            /**
             * @return 原始价格
             */
            public String getInitTicketPrice() {
                return initTicketPrice;
            }

            /**
             * @return 批价标识(Y/N，是否需要验证价格)
             */
            public String getPatFlag() {
                return patFlag;
            }

            /**
             * @return 协议价
             */
            public String getProtocolTicketPrice() {
                return protocolTicketPrice;
            }

            /**
             * @return 座位数
             */
            public String getRebateAmount() {
                return rebateAmount;
            }

            /**
             * @return 签转规定
             */
            public String getSeating() {
                return seating;
            }

            /**
             * @return 标准价(整数。基础舱位价格，经济舱为Y舱全价)
             */
            public String getSignPolicy() {
                return signPolicy;
            }

            /**
             * @return
             */
            public String getStandardPrice() {
                return standardPrice;
            }
        }
    }
}
