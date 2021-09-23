package com.data.data.hmly.service.pay;

/**
 * Created by dy on 2016/5/23.
 */
public class AliRefundNotify {

    private String notify_time;
    private String notify_type;
    private String notify_id;
    private String sign_type;
    private String sign;
    private String batch_no;
    private String pay_user_id;
    private String pay_user_name;
    private String pay_account_no;
    private String success_details;
    private String fail_details;


    public String getNotify_time() {
        return notify_time;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getPay_user_id() {
        return pay_user_id;
    }

    public void setPay_user_id(String pay_user_id) {
        this.pay_user_id = pay_user_id;
    }

    public String getPay_user_name() {
        return pay_user_name;
    }

    public void setPay_user_name(String pay_user_name) {
        this.pay_user_name = pay_user_name;
    }

    public String getPay_account_no() {
        return pay_account_no;
    }

    public void setPay_account_no(String pay_account_no) {
        this.pay_account_no = pay_account_no;
    }

    public String getSuccess_details() {
        return success_details;
    }

    public void setSuccess_details(String success_details) {
        this.success_details = success_details;
    }

    public String getFail_details() {
        return fail_details;
    }

    public void setFail_details(String fail_details) {
        this.fail_details = fail_details;
    }
}
