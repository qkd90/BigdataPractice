package com.data.data.hmly.enums;

/**
 * 网建短信状态码
 * Created by caiys on 2016/1/26.
 */
public enum SmsStatusCode {
    ERROR_UNKONW_ACCOUNT("-1", "没有该用户账户"),
    ERROR_KEY_WRONG("-2", "接口密钥不正确"),
    ERROR_MD5KEY_WRONG("-21", "MD5接口密钥加密不正确"),
    ERROR_SMS_LACK("-3", "短信数量不足"),
    ERROR_ACCOUNT_LIMIT("-11", "该用户被禁用"),
    ERROR_ILLEGAL_CHAR("-14", "短信内容出现非法字符"),
    ERROR_PHONE_WRONG("-4", "手机号格式不正确"),
    ERROR_PHONE_EMPTY("-41", "手机号码为空"),
    ERROR_CONTENT_EMPTY("-42", "短信内容为空"),
    ERROR_SIGNATURE_WRONG("-51", "短信签名格式不正确"),
    ERROR_IP_LIMIT("-6", "IP限制"),
    SUCCESS("1", "短信发送数量1"),
    ERROR_SERVER_EXCEPTION("-0", "服务器异常");

    private String code;
    private String description;

    SmsStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return this.description;
    }
}
