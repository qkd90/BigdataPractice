package com.data.hmly.service.translation.train.juhe.entity;

/**
 * Created by Sane on 16/1/8.
 */
public class Ticket {

    /**
     * ticket_no : E1190475871081234
     * passengername : 李四
     * passporttypeseid : 1
     * passportseno : 370827195104212345
     */

    private String ticket_no;
    private String passengername;
    private String passporttypeseid;
    private String passportseno;

    public void setTicket_no(String ticket_no) {
        this.ticket_no = ticket_no;
    }

    public void setPassengername(String passengername) {
        this.passengername = passengername;
    }

    public void setPassporttypeseid(String passporttypeseid) {
        this.passporttypeseid = passporttypeseid;
    }

    public void setPassportseno(String passportseno) {
        this.passportseno = passportseno;
    }

    public String getTicket_no() {
        return ticket_no;
    }

    public String getPassengername() {
        return passengername;
    }

    public String getPassporttypeseid() {
        return passporttypeseid;
    }

    public String getPassportseno() {
        return passportseno;
    }
}
