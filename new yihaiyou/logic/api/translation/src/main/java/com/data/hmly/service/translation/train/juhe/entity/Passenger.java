package com.data.hmly.service.translation.train.juhe.entity;

/**
 * Created by Sane on 16/1/8.
 */
public class Passenger {

    public Passenger(String passengersename, String passportseno, String price, String zwcode, String zwname) {
        this.passengersename = passengersename;
        this.piaotype = "1";
        this.piaotypename = "成人票";
        this.passporttypeseid = "1";
        this.passporttypeseidname = "二代身份证";
        this.passportseno = passportseno;
        this.price = price;
        this.zwcode = zwcode;
        this.zwname = zwname;
    }

    /**
     * passengerid : 1123
     * passengersename : 张三
     * piaotype : 1
     * piaotypename : 成人票
     * passporttypeseid : 1
     * passporttypeseidname : 二代身份证
     * passportseno : 420205199207231234
     * price : 763.5
     * zwcode : M
     * zwname : 一等座
     * insurance : {"name":"张三","mobile":"13888888888","gender":"M","birth":"1987-05-04","city":"苏州","idcard":"420205199207231234"}
     */

    private int passengerid;
    private String passengersename;
    private String piaotype;
    private String piaotypename;
    private String passporttypeseid;
    private String passporttypeseidname;
    private String passportseno;
    private String price;
    private String zwcode;
    private String zwname;
    /**
     * name : 张三
     * mobile : 13888888888
     * gender : M
     * birth : 1987-05-04
     * city : 苏州
     * idcard : 420205199207231234
     */

    private InsuranceEntity insurance;

    public void setPassengerid(int passengerid) {
        this.passengerid = passengerid;
    }

    public void setPassengersename(String passengersename) {
        this.passengersename = passengersename;
    }

    public void setPiaotype(String piaotype) {
        this.piaotype = piaotype;
    }

    public void setPiaotypename(String piaotypename) {
        this.piaotypename = piaotypename;
    }

    public void setPassporttypeseid(String passporttypeseid) {
        this.passporttypeseid = passporttypeseid;
    }

    public void setPassporttypeseidname(String passporttypeseidname) {
        this.passporttypeseidname = passporttypeseidname;
    }

    public void setPassportseno(String passportseno) {
        this.passportseno = passportseno;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setZwcode(String zwcode) {
        this.zwcode = zwcode;
    }

    public void setZwname(String zwname) {
        this.zwname = zwname;
    }

    public void setInsurance(InsuranceEntity insurance) {
        this.insurance = insurance;
    }

    public int getPassengerid() {
        return passengerid;
    }

    public String getPassengersename() {
        return passengersename;
    }

    public String getPiaotype() {
        return piaotype;
    }

    public String getPiaotypename() {
        return piaotypename;
    }

    public String getPassporttypeseid() {
        return passporttypeseid;
    }

    public String getPassporttypeseidname() {
        return passporttypeseidname;
    }

    public String getPassportseno() {
        return passportseno;
    }

    public String getPrice() {
        return price;
    }

    public String getZwcode() {
        return zwcode;
    }

    public String getZwname() {
        return zwname;
    }

    public InsuranceEntity getInsurance() {
        return insurance;
    }

    public static class InsuranceEntity {
        private String name;
        private String mobile;
        private String gender;
        private String birth;
        private String city;
        private String idcard;

        public void setName(String name) {
            this.name = name;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getName() {
            return name;
        }

        public String getMobile() {
            return mobile;
        }

        public String getGender() {
            return gender;
        }

        public String getBirth() {
            return birth;
        }

        public String getCity() {
            return city;
        }

        public String getIdcard() {
            return idcard;
        }
    }
}
