package com.taobao.core;

/**
 * Created by dy on 2016/6/1.
 */
public class AlipayErroCode {

    public static String decodeErrorCode(String erroCode) {
        String resultLog = "";
        switch (erroCode) {
            case "ILLEGAL_USER": resultLog = "ILLEGAL_USER：用户ID不正确"; break;
            case "BATCH_NUM_EXCEED_LIMIT": resultLog = "BATCH_NUM_EXCEED_LIMIT：总比数大于1000"; break;
            case "REFUND_DATE_ERROR": resultLog = "REFUND_DATE_ERROR：错误的退款时间"; break;
            case "BATCH_NUM_ERROR": resultLog = "BATCH_NUM_ERROR：传入的总笔数格式错误"; break;
            case "BATCH_NUM_NOT_EQUAL_TOTAL": resultLog = "BATCH_NUM_NOT_EQUAL_TOTAL：传入的退款条数不等于数据集解析出的退款条数"; break;
            case "SINGLE_DETAIL_DATA_EXCEED_LIMIT": resultLog = "SINGLE_DETAIL_DATA_EXCEED_LIMIT：单笔退款明细超出限制"; break;
            case "NOT_THIS_SELLER_TRADE": resultLog = "NOT_THIS_SELLER_TRADE：不是当前卖家的交易"; break;
            case "DUBL_TRADE_NO_IN_SAME_BATCH": resultLog = "DUBL_TRADE_NO_IN_SAME_BATCH：同一批退款中存在两条相同的退款记录"; break;
            case "DUPLICATE_BATCH_NO": resultLog = "DUPLICATE_BATCH_NO：重复的批次号"; break;
            case "TRADE_STATUS_ERROR": resultLog = "TRADE_STATUS_ERROR：交易状态不允许退款"; break;
            case "BATCH_NO_FORMAT_ERROR": resultLog = "BATCH_NO_FORMAT_ERROR：批次号格式错误"; break;
            case "SELLER_INFO_NOT_EXIST": resultLog = "SELLER_INFO_NOT_EXIST：卖家信息不存在"; break;
            case "PARTNER_NOT_SIGN_PROTOCOL": resultLog = "PARTNER_NOT_SIGN_PROTOCOL：平台商未签署协议"; break;
            case "NOT_THIS_PARTNERS_TRADE": resultLog = "NOT_THIS_PARTNERS_TRADE：退款明细非本合作伙伴的交易"; break;
            case "DETAIL_DATA_FORMAT_ERROR": resultLog = "DETAIL_DATA_FORMAT_ERROR：数据集参数格式错误"; break;
            case "PWD_REFUND_NOT_ALLOW_ROYALTY": resultLog = "PWD_REFUND_NOT_ALLOW_ROYALTY：有密接口不允许退分润"; break;
            case "NANHANG_REFUND_CHARGE_AMOUNT_ERROR": resultLog = "NANHANG_REFUND_CHARGE_AMOUNT_ERROR：退票面价金额不合法"; break;
            case "REFUND_AMOUNT_NOT_VALID": resultLog = "REFUND_AMOUNT_NOT_VALID：退款金额不合法"; break;
            case "TRADE_PRODUCT_TYPE_NOT_ALLOW_REFUND": resultLog = "TRADE_PRODUCT_TYPE_NOT_ALLOW_REFUND：交易类型不允许退交易"; break;
            case "RESULT_FACE_AMOUNT_NOT_VALID": resultLog = "RESULT_FACE_AMOUNT_NOT_VALID：退款票面价不能大于支付票面价"; break;
            case "REFUND_CHARGE_FEE_ERROR": resultLog = "REFUND_CHARGE_FEE_ERROR：退收费金额不合法"; break;
            case "REASON_REFUND_CHARGE_ERR": resultLog = "REASON_REFUND_CHARGE_ERR：退收费失败"; break;
            case "RESULT_AMOUNT_NOT_VALID": resultLog = "RESULT_AMOUNT_NOT_VALID：退收费金额错误"; break;
            case "RESULT_ACCOUNT_NO_NOT_VALID": resultLog = "RESULT_ACCOUNT_NO_NOT_VALID：账号无效"; break;
            case "REASON_TRADE_REFUND_FEE_ERR": resultLog = "REASON_TRADE_REFUND_FEE_ERR：退款金额错误"; break;
            case "REASON_HAS_REFUND_FEE_NOT_MATCH": resultLog = "REASON_HAS_REFUND_FEE_NOT_MATCH：已退款金额错误"; break;
            case "TXN_RESULT_ACCOUNT_STATUS_NOT_VALID": resultLog = "TXN_RESULT_ACCOUNT_STATUS_NOT_VALID：账户状态无效"; break;
            case "TXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH": resultLog = "TXN_RESULT_ACCOUNT_BALANCE_NOT_ENOUGH：账户余额不足"; break;
            case "REFUND_TRADE_FEE_ERROR": resultLog = "REFUND_TRADE_FEE_ERROR：交易金额不一致"; break;
            case "TRADE_HAS_CLOSED": resultLog = "TRADE_HAS_CLOSED：交易关闭"; break;
            case "REASON_REFUND_AMOUNT_LESS_THAN_COUPON_FEE": resultLog = "REASON_REFUND_AMOUNT_LESS_THAN_COUPON_FEE：红包无法部分退款"; break;
            default: resultLog = "退款成功"; break;
        }
        return resultLog;
    }

}
