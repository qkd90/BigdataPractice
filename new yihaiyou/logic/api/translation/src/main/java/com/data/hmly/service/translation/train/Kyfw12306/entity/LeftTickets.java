package com.data.hmly.service.translation.train.Kyfw12306.entity;

import java.util.List;

/**
 * Created by Sane on 16/4/7.
 */
public class LeftTickets {

    private String validateMessagesShowId;
    private boolean status;
    private int httpstatus;
    /**
     * queryLeftNewDTO : {"train_no":"240000G1010B","station_train_code":"G101","start_station_telecode":"VNP","start_station_name":"北京南","end_station_telecode":"AOH","end_station_name":"上海虹桥","from_station_telecode":"VNP","from_station_name":"北京南","to_station_telecode":"AOH","to_station_name":"上海虹桥","start_time":"07:00","arrive_time":"12:37","day_difference":"0","train_class_name":"","lishi":"05:37","canWebBuy":"Y","lishiValue":"337","yp_info":"O055300036M0933000379174800014","control_train_day":"20301231","start_train_date":"20160409","seat_feature":"O3M393","yp_ex":"O0M090","train_seat_feature":"3","seat_types":"OM9","location_code":"P2","from_station_no":"01","to_station_no":"10","control_day":59,"sale_time":"1230","is_support_card":"1","controlled_train_flag":"0","controlled_train_message":"正常车次，不受控","gg_num":"--","gr_num":"--","qt_num":"--","rw_num":"--","rz_num":"--","tz_num":"--","wz_num":"--","yb_num":"--","yw_num":"--","yz_num":"--","ze_num":"有","zy_num":"有","swz_num":"14"}
     * secretStr : MjAxNi0wNC0wOSMwMCNHMTAxIzA1OjM3IzA3OjAwIzI0MDAwMEcxMDEwQiNWTlAjQU9IIzEyOjM3I%2BWMl%2BS6rOWNlyPkuIrmtbfombnmoaUjMDEjMTAjTzA1NTMwMDAzNk0wOTMzMDAwMzc5MTc0ODAwMDE0I1AyIzE0NjAwMjM3MjY5MDAjMTQ1NTA3ODYwMDAwMCNFMEU5Qzk0NDU5ODExQTY5M0JCNjg5Q0UzN0E5RkI2NjVEQTBEMTEzQUQ4NEZEREY4MjBGN0EyMA%3D%3D
     * buttonTextInfo : 预订
     */

    private List<DataEntity> data;
    private List<?> messages;

    public String getValidateMessagesShowId() {
        return validateMessagesShowId;
    }

    public void setValidateMessagesShowId(String validateMessagesShowId) {
        this.validateMessagesShowId = validateMessagesShowId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getHttpstatus() {
        return httpstatus;
    }

    public void setHttpstatus(int httpstatus) {
        this.httpstatus = httpstatus;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<?> getMessages() {
        return messages;
    }

    public void setMessages(List<?> messages) {
        this.messages = messages;
    }

    public static class DataEntity {
        /**
         * train_no : 240000G1010B
         * station_train_code : G101
         * start_station_telecode : VNP
         * start_station_name : 北京南
         * end_station_telecode : AOH
         * end_station_name : 上海虹桥
         * from_station_telecode : VNP
         * from_station_name : 北京南
         * to_station_telecode : AOH
         * to_station_name : 上海虹桥
         * start_time : 07:00
         * arrive_time : 12:37
         * day_difference : 0
         * train_class_name :
         * lishi : 05:37
         * canWebBuy : Y
         * lishiValue : 337
         * yp_info : O055300036M0933000379174800014
         * control_train_day : 20301231
         * start_train_date : 20160409
         * seat_feature : O3M393
         * yp_ex : O0M090
         * train_seat_feature : 3
         * seat_types : OM9
         * location_code : P2
         * from_station_no : 01
         * to_station_no : 10
         * control_day : 59
         * sale_time : 1230
         * is_support_card : 1
         * controlled_train_flag : 0
         * controlled_train_message : 正常车次，不受控
         * gg_num : --
         * gr_num : --
         * qt_num : --
         * rw_num : --
         * rz_num : --
         * tz_num : --
         * wz_num : --
         * yb_num : --
         * yw_num : --
         * yz_num : --
         * ze_num : 有
         * zy_num : 有
         * swz_num : 14
         */

        private QueryLeftNewDTOEntity queryLeftNewDTO;
        private String secretStr;
        private String buttonTextInfo;

        public QueryLeftNewDTOEntity getQueryLeftNewDTO() {
            return queryLeftNewDTO;
        }

        public void setQueryLeftNewDTO(QueryLeftNewDTOEntity queryLeftNewDTO) {
            this.queryLeftNewDTO = queryLeftNewDTO;
        }

        public String getSecretStr() {
            return secretStr;
        }

        public void setSecretStr(String secretStr) {
            this.secretStr = secretStr;
        }

        public String getButtonTextInfo() {
            return buttonTextInfo;
        }

        public void setButtonTextInfo(String buttonTextInfo) {
            this.buttonTextInfo = buttonTextInfo;
        }

        public static class QueryLeftNewDTOEntity {
            private String train_no;
            private String station_train_code;
            private String start_station_telecode;
            private String start_station_name;
            private String end_station_telecode;
            private String end_station_name;
            private String from_station_telecode;
            private String from_station_name;
            private String to_station_telecode;
            private String to_station_name;
            private String start_time;
            private String arrive_time;
            private String day_difference;
            private String train_class_name;
            private String lishi;
            private String canWebBuy;
            private String lishiValue;
            private String yp_info;
            private String control_train_day;
            private String start_train_date;
            private String seat_feature;
            private String yp_ex;
            private String train_seat_feature;
            private String seat_types;
            private String location_code;
            private String from_station_no;
            private String to_station_no;
            private int control_day;
            private String sale_time;
            private String is_support_card;
            private String controlled_train_flag;
            private String controlled_train_message;
            private String gg_num;
            private String gr_num;
            private String qt_num;
            private String rw_num;
            private String rz_num;
            private String tz_num;
            private String wz_num;
            private String yb_num;
            private String yw_num;
            private String yz_num;
            private String ze_num;
            private String zy_num;
            private String swz_num;

            public String getTrain_no() {
                return train_no;
            }

            public void setTrain_no(String train_no) {
                this.train_no = train_no;
            }

            public String getStation_train_code() {
                return station_train_code;
            }

            public void setStation_train_code(String station_train_code) {
                this.station_train_code = station_train_code;
            }

            public String getStart_station_telecode() {
                return start_station_telecode;
            }

            public void setStart_station_telecode(String start_station_telecode) {
                this.start_station_telecode = start_station_telecode;
            }

            public String getStart_station_name() {
                return start_station_name;
            }

            public void setStart_station_name(String start_station_name) {
                this.start_station_name = start_station_name;
            }

            public String getEnd_station_telecode() {
                return end_station_telecode;
            }

            public void setEnd_station_telecode(String end_station_telecode) {
                this.end_station_telecode = end_station_telecode;
            }

            public String getEnd_station_name() {
                return end_station_name;
            }

            public void setEnd_station_name(String end_station_name) {
                this.end_station_name = end_station_name;
            }

            public String getFrom_station_telecode() {
                return from_station_telecode;
            }

            public void setFrom_station_telecode(String from_station_telecode) {
                this.from_station_telecode = from_station_telecode;
            }

            public String getFrom_station_name() {
                return from_station_name;
            }

            public void setFrom_station_name(String from_station_name) {
                this.from_station_name = from_station_name;
            }

            public String getTo_station_telecode() {
                return to_station_telecode;
            }

            public void setTo_station_telecode(String to_station_telecode) {
                this.to_station_telecode = to_station_telecode;
            }

            public String getTo_station_name() {
                return to_station_name;
            }

            public void setTo_station_name(String to_station_name) {
                this.to_station_name = to_station_name;
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

            public String getDay_difference() {
                return day_difference;
            }

            public void setDay_difference(String day_difference) {
                this.day_difference = day_difference;
            }

            public String getTrain_class_name() {
                return train_class_name;
            }

            public void setTrain_class_name(String train_class_name) {
                this.train_class_name = train_class_name;
            }

            public String getLishi() {
                return lishi;
            }

            public void setLishi(String lishi) {
                this.lishi = lishi;
            }

            public String getCanWebBuy() {
                return canWebBuy;
            }

            public void setCanWebBuy(String canWebBuy) {
                this.canWebBuy = canWebBuy;
            }

            public String getLishiValue() {
                return lishiValue;
            }

            public void setLishiValue(String lishiValue) {
                this.lishiValue = lishiValue;
            }

            public String getYp_info() {
                return yp_info;
            }

            public void setYp_info(String yp_info) {
                this.yp_info = yp_info;
            }

            public String getControl_train_day() {
                return control_train_day;
            }

            public void setControl_train_day(String control_train_day) {
                this.control_train_day = control_train_day;
            }

            public String getStart_train_date() {
                return start_train_date;
            }

            public void setStart_train_date(String start_train_date) {
                this.start_train_date = start_train_date;
            }

            public String getSeat_feature() {
                return seat_feature;
            }

            public void setSeat_feature(String seat_feature) {
                this.seat_feature = seat_feature;
            }

            public String getYp_ex() {
                return yp_ex;
            }

            public void setYp_ex(String yp_ex) {
                this.yp_ex = yp_ex;
            }

            public String getTrain_seat_feature() {
                return train_seat_feature;
            }

            public void setTrain_seat_feature(String train_seat_feature) {
                this.train_seat_feature = train_seat_feature;
            }

            public String getSeat_types() {
                return seat_types;
            }

            public void setSeat_types(String seat_types) {
                this.seat_types = seat_types;
            }

            public String getLocation_code() {
                return location_code;
            }

            public void setLocation_code(String location_code) {
                this.location_code = location_code;
            }

            public String getFrom_station_no() {
                return from_station_no;
            }

            public void setFrom_station_no(String from_station_no) {
                this.from_station_no = from_station_no;
            }

            public String getTo_station_no() {
                return to_station_no;
            }

            public void setTo_station_no(String to_station_no) {
                this.to_station_no = to_station_no;
            }

            public int getControl_day() {
                return control_day;
            }

            public void setControl_day(int control_day) {
                this.control_day = control_day;
            }

            public String getSale_time() {
                return sale_time;
            }

            public void setSale_time(String sale_time) {
                this.sale_time = sale_time;
            }

            public String getIs_support_card() {
                return is_support_card;
            }

            public void setIs_support_card(String is_support_card) {
                this.is_support_card = is_support_card;
            }

            public String getControlled_train_flag() {
                return controlled_train_flag;
            }

            public void setControlled_train_flag(String controlled_train_flag) {
                this.controlled_train_flag = controlled_train_flag;
            }

            public String getControlled_train_message() {
                return controlled_train_message;
            }

            public void setControlled_train_message(String controlled_train_message) {
                this.controlled_train_message = controlled_train_message;
            }

            public String getGg_num() {
                return gg_num;
            }

            public void setGg_num(String gg_num) {
                this.gg_num = gg_num;
            }

            public String getGr_num() {
                return gr_num;
            }

            public void setGr_num(String gr_num) {
                this.gr_num = gr_num;
            }

            public String getQt_num() {
                return qt_num;
            }

            public void setQt_num(String qt_num) {
                this.qt_num = qt_num;
            }

            public String getRw_num() {
                return rw_num;
            }

            public void setRw_num(String rw_num) {
                this.rw_num = rw_num;
            }

            public String getRz_num() {
                return rz_num;
            }

            public void setRz_num(String rz_num) {
                this.rz_num = rz_num;
            }

            public String getTz_num() {
                return tz_num;
            }

            public void setTz_num(String tz_num) {
                this.tz_num = tz_num;
            }

            public String getWz_num() {
                return wz_num;
            }

            public void setWz_num(String wz_num) {
                this.wz_num = wz_num;
            }

            public String getYb_num() {
                return yb_num;
            }

            public void setYb_num(String yb_num) {
                this.yb_num = yb_num;
            }

            public String getYw_num() {
                return yw_num;
            }

            public void setYw_num(String yw_num) {
                this.yw_num = yw_num;
            }

            public String getYz_num() {
                return yz_num;
            }

            public void setYz_num(String yz_num) {
                this.yz_num = yz_num;
            }

            public String getZe_num() {
                return ze_num;
            }

            public void setZe_num(String ze_num) {
                this.ze_num = ze_num;
            }

            public String getZy_num() {
                return zy_num;
            }

            public void setZy_num(String zy_num) {
                this.zy_num = zy_num;
            }

            public String getSwz_num() {
                return swz_num;
            }

            public void setSwz_num(String swz_num) {
                this.swz_num = swz_num;
            }
        }
    }
}
