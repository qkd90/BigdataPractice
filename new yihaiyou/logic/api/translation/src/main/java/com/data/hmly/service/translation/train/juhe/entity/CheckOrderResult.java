package com.data.hmly.service.translation.train.juhe.entity;

import java.util.List;

/**
 * Created by Sane on 16/3/25.
 */
public class CheckOrderResult {

    /**
     * reason : 成功的返回
     * result : {"orderid":"1441696917857","user_orderid":"","msg":"有乘客退票成功，相关款项已退还至您的账户","orderamount":"8.50","status":"7","passengers":[{"passengerid":"1","passengersename":"杨颖","piaotype":"1","piaotypename":"成人票","passporttypeseid":"1","passporttypeseidname":"二代身份证","passportseno":"370827199109123212","price":"8.5","zwcode":"O","zwname":"二等座","reason":0,"ticket_no":"EC07095328101001B","cxin":"01车厢,01B座","returntickets":{"returnsuccess":true,"returnmoney":"6.5","returntime":"2015-09-08 15:28:10","returnfailid":"","returnfailmsg":"","returntype":"1"},"refundTimeline":[{"time":"2016-01-28 20:51:55","msg":"线上申请退票"},{"time":"2016-01-28 20:54:13","msg":"线上退票成功","detail":{"returnsuccess":true,"returnmoney":"39.5","returnfailid":"","returnfailmsg":"","returntype":"1","ticket_no":"E886597309101005D","passengername":"小黄","passporttypeseid":"1","passportseno":"321088789512071236"}}]}],"checi":"G7213","ordernumber":"EC07095328","submit_time":"2015-09-08 15:21:58","deal_time":"2015-09-08 15:22:18","cancel_time":null,"pay_time":"2015-09-08 15:23:46","finished_time":"2015-09-08 15:24:02","refund_time":"2015-09-08 15:24:44","juhe_refund_time":"2015-09-08 15:26:28","train_date":"2015-09-13","from_station_name":"上海西","from_station_code":"SXH","to_station_name":"上海","to_station_code":"SHH","refund_money":"6.50"}
     * error_code : 0
     */

    private String reason;
    /**
     * orderid : 1441696917857
     * user_orderid :
     * msg : 有乘客退票成功，相关款项已退还至您的账户
     * orderamount : 8.50
     * status : 7
     * passengers : [{"passengerid":"1","passengersename":"杨颖","piaotype":"1","piaotypename":"成人票","passporttypeseid":"1","passporttypeseidname":"二代身份证","passportseno":"370827199109123212","price":"8.5","zwcode":"O","zwname":"二等座","reason":0,"ticket_no":"EC07095328101001B","cxin":"01车厢,01B座","returntickets":{"returnsuccess":true,"returnmoney":"6.5","returntime":"2015-09-08 15:28:10","returnfailid":"","returnfailmsg":"","returntype":"1"},"refundTimeline":[{"time":"2016-01-28 20:51:55","msg":"线上申请退票"},{"time":"2016-01-28 20:54:13","msg":"线上退票成功","detail":{"returnsuccess":true,"returnmoney":"39.5","returnfailid":"","returnfailmsg":"","returntype":"1","ticket_no":"E886597309101005D","passengername":"小黄","passporttypeseid":"1","passportseno":"321088789512071236"}}]}]
     * checi : G7213
     * ordernumber : EC07095328
     * submit_time : 2015-09-08 15:21:58
     * deal_time : 2015-09-08 15:22:18
     * cancel_time : null
     * pay_time : 2015-09-08 15:23:46
     * finished_time : 2015-09-08 15:24:02
     * refund_time : 2015-09-08 15:24:44
     * start_time : 2015-09-08 15:24:44
     * arrive_time : 2015-09-08 15:24:44
     * run_time : 00:20
     * juhe_refund_time : 2015-09-08 15:26:28
     * train_date : 2015-09-13
     * from_station_name : 上海西
     * from_station_code : SXH
     * to_station_name : 上海
     * to_station_code : SHH
     * refund_money : 6.50
     */

    private ResultEntity result;
    private int error_code;

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

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultEntity {
        private String orderid;
        private String user_orderid;
        private String msg;
        private String orderamount;
        private String status;
        private String checi;
        private String ordernumber;
        private String submit_time;
        private String deal_time;
        private String cancel_time;
        private String pay_time;
        private String finished_time;
        private String refund_time;
        private String juhe_refund_time;
        private String start_time;
        private String arrive_time;
        private String run_time;
        private String train_date;
        private String from_station_name;
        private String from_station_code;
        private String to_station_name;
        private String to_station_code;
        private String refund_money;
        /**
         * passengerid : 1
         * passengersename : 杨颖
         * piaotype : 1
         * piaotypename : 成人票
         * passporttypeseid : 1
         * passporttypeseidname : 二代身份证
         * passportseno : 370827199109123212
         * price : 8.5
         * zwcode : O
         * zwname : 二等座
         * reason : 0
         * ticket_no : EC07095328101001B
         * cxin : 01车厢,01B座
         * returntickets : {"returnsuccess":true,"returnmoney":"6.5","returntime":"2015-09-08 15:28:10","returnfailid":"","returnfailmsg":"","returntype":"1"}
         * refundTimeline : [{"time":"2016-01-28 20:51:55","msg":"线上申请退票"},{"time":"2016-01-28 20:54:13","msg":"线上退票成功","detail":{"returnsuccess":true,"returnmoney":"39.5","returnfailid":"","returnfailmsg":"","returntype":"1","ticket_no":"E886597309101005D","passengername":"小黄","passporttypeseid":"1","passportseno":"321088789512071236"}}]
         */

        private List<PassengersEntity> passengers;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getUser_orderid() {
            return user_orderid;
        }

        public void setUser_orderid(String user_orderid) {
            this.user_orderid = user_orderid;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getOrderamount() {
            return orderamount;
        }

        public void setOrderamount(String orderamount) {
            this.orderamount = orderamount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCheci() {
            return checi;
        }

        public void setCheci(String checi) {
            this.checi = checi;
        }

        public String getOrdernumber() {
            return ordernumber;
        }

        public void setOrdernumber(String ordernumber) {
            this.ordernumber = ordernumber;
        }

        public String getSubmit_time() {
            return submit_time;
        }

        public void setSubmit_time(String submit_time) {
            this.submit_time = submit_time;
        }

        public String getDeal_time() {
            return deal_time;
        }

        public void setDeal_time(String deal_time) {
            this.deal_time = deal_time;
        }

        public String getCancel_time() {
            return cancel_time;
        }

        public void setCancel_time(String cancel_time) {
            this.cancel_time = cancel_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getFinished_time() {
            return finished_time;
        }

        public void setFinished_time(String finished_time) {
            this.finished_time = finished_time;
        }

        public String getRefund_time() {
            return refund_time;
        }

        public void setRefund_time(String refund_time) {
            this.refund_time = refund_time;
        }

        public String getJuhe_refund_time() {
            return juhe_refund_time;
        }

        public void setJuhe_refund_time(String juhe_refund_time) {
            this.juhe_refund_time = juhe_refund_time;
        }

        public String getTrain_date() {
            return train_date;
        }

        public void setTrain_date(String train_date) {
            this.train_date = train_date;
        }

        public String getFrom_station_name() {
            return from_station_name;
        }

        public void setFrom_station_name(String from_station_name) {
            this.from_station_name = from_station_name;
        }

        public String getFrom_station_code() {
            return from_station_code;
        }

        public void setFrom_station_code(String from_station_code) {
            this.from_station_code = from_station_code;
        }

        public String getTo_station_name() {
            return to_station_name;
        }

        public void setTo_station_name(String to_station_name) {
            this.to_station_name = to_station_name;
        }

        public String getTo_station_code() {
            return to_station_code;
        }

        public void setTo_station_code(String to_station_code) {
            this.to_station_code = to_station_code;
        }

        public String getRefund_money() {
            return refund_money;
        }

        public void setRefund_money(String refund_money) {
            this.refund_money = refund_money;
        }

        public List<PassengersEntity> getPassengers() {
            return passengers;
        }

        public void setPassengers(List<PassengersEntity> passengers) {
            this.passengers = passengers;
        }

        public static class PassengersEntity {
            private String passengerid;
            private String passengersename;
            private String piaotype;
            private String piaotypename;
            private String passporttypeseid;
            private String passporttypeseidname;
            private String passportseno;
            private String price;
            private String zwcode;
            private String zwname;
            private int reason;
            private String ticket_no;
            private String cxin;
            /**
             * returnsuccess : true
             * returnmoney : 6.5
             * returntime : 2015-09-08 15:28:10
             * returnfailid :
             * returnfailmsg :
             * returntype : 1
             */

            private ReturnticketsEntity returntickets;
            /**
             * time : 2016-01-28 20:51:55
             * msg : 线上申请退票
             */

            private List<RefundTimelineEntity> refundTimeline;

            public String getPassengerid() {
                return passengerid;
            }

            public void setPassengerid(String passengerid) {
                this.passengerid = passengerid;
            }

            public String getPassengersename() {
                return passengersename;
            }

            public void setPassengersename(String passengersename) {
                this.passengersename = passengersename;
            }

            public String getPiaotype() {
                return piaotype;
            }

            public void setPiaotype(String piaotype) {
                this.piaotype = piaotype;
            }

            public String getPiaotypename() {
                return piaotypename;
            }

            public void setPiaotypename(String piaotypename) {
                this.piaotypename = piaotypename;
            }

            public String getPassporttypeseid() {
                return passporttypeseid;
            }

            public void setPassporttypeseid(String passporttypeseid) {
                this.passporttypeseid = passporttypeseid;
            }

            public String getPassporttypeseidname() {
                return passporttypeseidname;
            }

            public void setPassporttypeseidname(String passporttypeseidname) {
                this.passporttypeseidname = passporttypeseidname;
            }

            public String getPassportseno() {
                return passportseno;
            }

            public void setPassportseno(String passportseno) {
                this.passportseno = passportseno;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getZwcode() {
                return zwcode;
            }

            public void setZwcode(String zwcode) {
                this.zwcode = zwcode;
            }

            public String getZwname() {
                return zwname;
            }

            public void setZwname(String zwname) {
                this.zwname = zwname;
            }

            public int getReason() {
                return reason;
            }

            public void setReason(int reason) {
                this.reason = reason;
            }

            public String getTicket_no() {
                return ticket_no;
            }

            public void setTicket_no(String ticket_no) {
                this.ticket_no = ticket_no;
            }

            public String getCxin() {
                return cxin;
            }

            public void setCxin(String cxin) {
                this.cxin = cxin;
            }

            public ReturnticketsEntity getReturntickets() {
                return returntickets;
            }

            public void setReturntickets(ReturnticketsEntity returntickets) {
                this.returntickets = returntickets;
            }

            public List<RefundTimelineEntity> getRefundTimeline() {
                return refundTimeline;
            }

            public void setRefundTimeline(List<RefundTimelineEntity> refundTimeline) {
                this.refundTimeline = refundTimeline;
            }

            public static class ReturnticketsEntity {
                private boolean returnsuccess;
                private String returnmoney;
                private String returntime;
                private String returnfailid;
                private String returnfailmsg;
                private String returntype;

                public boolean isReturnsuccess() {
                    return returnsuccess;
                }

                public void setReturnsuccess(boolean returnsuccess) {
                    this.returnsuccess = returnsuccess;
                }

                public String getReturnmoney() {
                    return returnmoney;
                }

                public void setReturnmoney(String returnmoney) {
                    this.returnmoney = returnmoney;
                }

                public String getReturntime() {
                    return returntime;
                }

                public void setReturntime(String returntime) {
                    this.returntime = returntime;
                }

                public String getReturnfailid() {
                    return returnfailid;
                }

                public void setReturnfailid(String returnfailid) {
                    this.returnfailid = returnfailid;
                }

                public String getReturnfailmsg() {
                    return returnfailmsg;
                }

                public void setReturnfailmsg(String returnfailmsg) {
                    this.returnfailmsg = returnfailmsg;
                }

                public String getReturntype() {
                    return returntype;
                }

                public void setReturntype(String returntype) {
                    this.returntype = returntype;
                }
            }

            public static class RefundTimelineEntity {
                private String time;
                private String msg;

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getMsg() {
                    return msg;
                }

                public void setMsg(String msg) {
                    this.msg = msg;
                }
            }
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getArrive_time() {
            return arrive_time;
        }

        public void setArrive_time(String arrive_time) {
            this.arrive_time = arrive_time;
        }

        public String getRun_time() {
            return run_time;
        }

        public void setRun_time(String run_time) {
            this.run_time = run_time;
        }
    }
}
