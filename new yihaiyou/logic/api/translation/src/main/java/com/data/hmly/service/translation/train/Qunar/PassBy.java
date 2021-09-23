package com.data.hmly.service.translation.train.Qunar;

/**
 * Created by Sane on 16/1/19.
 */
public class PassBy {
    public PassBy(String num, String siteName, String leave, String arrive, String time, String day) {
        this.num = num;
        this.siteName = siteName;
        this.arrive = arrive;
        this.leave = leave;
        this.time = time;
        this.day = day;
    }

    private String num;
    private String siteName;
    private String arrive;
    private String leave;
    private String time;
    private String day;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
