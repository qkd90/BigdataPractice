package com.data.data.hmly.service.pay.util;

/**
 * Created by dy on 2016/6/1.
 */
public class WeChatErroCode {
    public static String decodeErrorCode(String erroCode) {
        String resultLog = "";
        switch (erroCode) {
            case "NOAUTH": resultLog = "没有授权请求此api"; break;
            case "AMOUNT_LIMIT": resultLog = "付款金额不能小于最低限额"; break;
            case "PARAM_ERROR": resultLog = "参数缺失，或参数格式出错，参数不合法等"; break;
            case "OPENID_ERROR": resultLog = "Openid格式错误或者不属于商家公众账号"; break;
            case "NOTENOUGH": resultLog = "帐号余额不足"; break;
            case "SYSTEMERROR": resultLog = "系统错误，请重试"; break;
            case "NAME_MISMATCH": resultLog = "请求参数里填写了需要检验姓名，但是输入了错误的姓名"; break;
            case "SIGN_ERROR": resultLog = "没有按照文档要求进行签名"; break;
            case "XML_ERROR": resultLog = "Post请求数据不是合法的xml格式内容"; break;
            case "FATAL_ERROR": resultLog = "两次请求商户单号一样，但是参数不一致"; break;
            case "CA_ERROR": resultLog = "请求没带证书或者带上了错误的证书"; break;
            case "REFUNDNOTEXIST": resultLog = "退款单号不存在"; break;
            default: resultLog = "操作成功"; break;
        }
        return resultLog;
    }

    public static boolean equalCode(String code) {
        boolean resultLog =false;
        switch (code) {
            case "NOAUTH": resultLog = true; break;
            case "AMOUNT_LIMIT": resultLog = true; break;
            case "PARAM_ERROR": resultLog = true; break;
            case "OPENID_ERROR": resultLog = true; break;
            case "NOTENOUGH": resultLog = true; break;
            case "SYSTEMERROR": resultLog = true; break;
            case "NAME_MISMATCH": resultLog = true; break;
            case "SIGN_ERROR": resultLog = true; break;
            case "XML_ERROR": resultLog = true; break;
            case "FATAL_ERROR": resultLog = true; break;
            case "CA_ERROR": resultLog = true; break;
            case "REFUNDNOTEXIST": resultLog = true; break;
            default: resultLog = false; break;
        }
        return resultLog;
    }

}
