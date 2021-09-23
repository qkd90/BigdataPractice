package com.gson;

import com.gson.bean.PayResult;
import com.gson.bean.QueryRefundResult;
import com.gson.bean.RefundResult;
import com.gson.bean.RefundResultDetail;
import com.gson.oauth.Pay;
import com.gson.util.HttpKit;
import com.gson.util.WeChatErroCode;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import com.zuipin.util.UUIDUtil;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by caiys on 2016/4/19.
 */
public class WeChatPay {
    // 统一下单
    private static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    // 查询订单
    private static final String ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    //统一退款
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    public static final String QUERYREFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";

    /**
     * 统一退款
     * @param paramMap  参数参考如下：
     *
     *   paramMap.put("appid", wechatAccount.getAppId());      // 公众账号ID
     *   paramMap.put("mch_id", wechatAccount.getMchId());     // 商户号
     *   paramMap.put("out_trade_no", orderNo);   // 商户订单号：32个字符内，唯一，重新支付用原订单号
     *   paramMap.put("out_refund_no", refundNo);   // 商户退款编号：32个字符内，唯一，重新退款用退款编号
     *   paramMap.put("total_fee", String.valueOf(totalFee)); // 订单总金额，单位为分
     *   paramMap.put("refund_fee", String.valueOf(refundFee)); // 订单退款金额，单位为分
     *   paramMap.put("op_user_id", wechatAccount.getOriginalId());
     *
     * @param paternerKey   MCH KEY
     * @return
     */
    public static RefundResult refundOrder(Map<String, String> paramMap, String paternerKey, String filePath, String wId) {
        String nonceStr = UUIDUtil.getUUID();
        paramMap.put("nonce_str", nonceStr);
        RefundResult refundResult = new RefundResult();
        try {
            String sign = Pay.sign(paramMap, paternerKey);
            paramMap.put("sign", sign);    // 签名
            String xmlData = toNoCDATAXml(paramMap);
            Map<String, Object> resultMap = HttpKit.refundHttpPost(REFUND_URL, filePath, wId, xmlData);
            String returnCode = (String) resultMap.get("return_code");          // 返回状态码
            String returnMsg = (String) resultMap.get("return_msg");
            if (!"SUCCESS".equals(returnCode)) {
                refundResult.setSuccess(false);
                refundResult.setMsg(returnMsg);
                return refundResult;
            }

            String resultCode = (String) resultMap.get("result_code");          // 业务结果
            if (!"SUCCESS".equals(resultCode)) {
                String errCode = (String) resultMap.get("err_code");            // 错误代码
                String errCodeDes = (String) resultMap.get("err_code_des");     // 错误描述
                refundResult.setErrCode(errCode);
                refundResult.setErrMsg(errCodeDes);
                refundResult.setSuccess(false);
                refundResult.setErrCode(returnMsg);
                return refundResult;
            }

            // 成功
            String outTradeNo = (String) resultMap.get("out_trade_no");
            String outRefundNo = (String) resultMap.get("out_refund_no");
            String refundId = (String) resultMap.get("refund_id");
            String refundFee = (String) resultMap.get("refund_fee");

            refundResult.setSuccess(true);
            refundResult.setMsg(returnMsg);
            refundResult.setOutRefundNo(outRefundNo);
            refundResult.setRefundId(refundId);
            refundResult.setOutTradeNo(outTradeNo);
            refundResult.setRefundFee(refundFee);
            Long time = new Date().getTime();
            time = time + 2 * 60 * 60 * 1000;
            Date expireTime = new Date(time);
            refundResult.setExpireTime(expireTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return refundResult;
    }

/*
    <xml>
    <appid>wx2421b1c4370ec43b</appid>
    <mch_id>10000100</mch_id>
    <nonce_str>0b9f35f484df17a732e537c37708d1d0</nonce_str>
    <out_refund_no></out_refund_no>
    <out_trade_no>1415757673</out_trade_no>
    <refund_id></refund_id>
    <transaction_id></transaction_id>
    <sign>66FFB727015F450D167EF38CCC549521</sign>
    </xml>*/


    /**
     * 退款查询
     * @param paramMap  参数参考如下：
     *
     *   paramMap.put("appid", wechatAccount.getAppId());      // 公众账号ID
     *   paramMap.put("mch_id", wechatAccount.getMchId());     // 商户号
     *   paramMap.put("out_trade_no", orderNo);   // 商户订单号：32个字符内，唯一，重新支付用原订单号
     *   paramMap.put("out_refund_no", refundNo);   // 商户退款编号：32个字符内，唯一，重新退款用退款编号
     *
     * @param paternerKey   MCH KEY
     * @return
     */

    public static QueryRefundResult queryRefundOrder(Map<String, String> paramMap, String paternerKey) {
        String nonceStr = UUIDUtil.getUUID();
        paramMap.put("nonce_str", nonceStr);
        QueryRefundResult queryRefundResult = new QueryRefundResult();
        try {
            String sign = Pay.sign(paramMap, paternerKey);
            paramMap.put("sign", sign);    // 签名
            String xmlData = toNoCDATAXml(paramMap);
            String xmlResult = HttpKit.postHttps(QUERYREFUND_URL, xmlData, HttpKit.CONTENT_TYPE_XML);
            Map<String, Object> resultMap = readStringXmlOutNull(xmlResult);
//            Map<String, Object> resultMap = HttpKit.refundHttpPost(refundUrl, filePath, wId, xmlData);

            String returnCode = (String) resultMap.get("return_code");          // 返回状态码
            String returnMsg = (String) resultMap.get("return_msg");
            if (!"SUCCESS".equals(returnCode)) {
                queryRefundResult.setReturnCode(false);
                queryRefundResult.setReturnMsg(returnMsg);
                return queryRefundResult;
            } else {
                queryRefundResult.setReturnCode(true);
            }
            String resultCode = (String) resultMap.get("result_code");          // 业务结果
            if (!"SUCCESS".equals(resultCode)) {
                String errCode = (String) resultMap.get("err_code");            // 错误代码
                String errCodeDes = (String) resultMap.get("err_code_des");     // 错误描述
                queryRefundResult.setErrCode(errCode);
                queryRefundResult.setErrCodeDesc(WeChatErroCode.decodeErrorCode(errCode));
                queryRefundResult.setResultCode(false);
                return queryRefundResult;
            } else {
                queryRefundResult.setResultCode(true);
            }
            String refundCount = (String) resultMap.get("refund_count");

            if (!StringUtils.isNotBlank(refundCount)) {
                queryRefundResult.setReturnCode(false);
                queryRefundResult.setReturnMsg("退款数量为0或为空");
                return queryRefundResult;
            }

            queryRefundResult.setRefundCount(Integer.parseInt(refundCount));

            List<RefundResultDetail> detailList = new ArrayList<RefundResultDetail>();
            for (int i = 0; i < Integer.parseInt(refundCount); i++) {
                RefundResultDetail detail = new RefundResultDetail();
                String outRefundNo = (String) resultMap.get("out_refund_no_" + i);
                String refundChannel = (String) resultMap.get("refund_channel_" + i);
                String refundFee = (String) resultMap.get("refund_fee_" + i);
                String refundStatus = (String) resultMap.get("refund_status_" + i);
                if (StringUtils.isNotBlank(outRefundNo)) {
                    detail.setRefundNo(outRefundNo);
                }
                if (StringUtils.isNotBlank(refundChannel)) {
                    detail.setRefundChannel(refundChannel);
                }
                if (StringUtils.isNotBlank(refundFee)) {
                    detail.setRefundFee(Integer.parseInt(refundFee));
                }
                if (StringUtils.isNotBlank(refundStatus)) {
                    detail.setStatus(refundStatus);
                }
                detailList.add(detail);
            }
            queryRefundResult.setRefundResultDetailList(detailList);
            // 成功
            String tradeNo = (String) resultMap.get("out_trade_no");
            String totalFee = (String) resultMap.get("total_fee");
            String refundFee = (String) resultMap.get("refund_fee");
/*
            private String tradeNo;
            private Boolean returnCode;
            private Integer totalFee;
            private Boolean resultCode;
            private String resultMsg;
            private String errCode;
            private String errCodeDesc;
            */

            queryRefundResult.setTradeNo(tradeNo);
            queryRefundResult.setReturnMsg(returnMsg);
            queryRefundResult.setTotalFee(Integer.parseInt(totalFee));
            queryRefundResult.setRefundFee(Integer.parseInt(refundFee));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryRefundResult;
    }


    /**
     * 统一下单
     * @param paramMap  参数参考如下：
     *
                paramMap.put("appid", "");      // 公众账号ID
                paramMap.put("mch_id", "");     // 商户号
                //paramMap.put("nonce_str", "");  // 随机字符串：32个字符内
                paramMap.put("body", "");       // 商品描述
    		    paramMap.put("out_trade_no", "");   // 商户订单号：32个字符内，唯一，重新支付用原订单号
                paramMap.put("total_fee", ""); // 订单总金额，单位为分
                //paramMap.put("spbill_create_ip", "110.84.38.253");  // APP和网页支付提交用户端ip
                paramMap.put("notify_url", "");  // 通知地址
                //paramMap.put("trade_type", "NATIVE");   // 交易类型
                paramMap.put("attach", ""); // 附加数据
     *
     * @param paternerKey   MCH KEY
     * @return
     */
    public static PayResult unifiedOrder(Map<String, String> paramMap, String paternerKey) {
        String nonceStr = UUIDUtil.getUUID();
        paramMap.put("nonce_str", nonceStr);
        paramMap.put("spbill_create_ip", "110.84.38.253");
        paramMap.put("trade_type", "NATIVE");
        PayResult payResult = new PayResult();
        try {
            String sign = Pay.sign(paramMap, paternerKey);
            paramMap.put("sign", sign);    // 签名
            String xmlData = toXml(paramMap);
            String xmlResult = HttpKit.postHttps(UNIFIEDORDER_URL, xmlData, HttpKit.CONTENT_TYPE_XML);
            Map<String, Object> resultMap = readStringXmlOutNull(xmlResult);
            String returnCode = (String) resultMap.get("return_code");  // 返回状态码
            String returnMsg = (String) resultMap.get("return_msg");
            if (!"SUCCESS".equals(returnCode)) {
                payResult.setSuccess(false);
                payResult.setMsg(returnMsg);
                return payResult;
            }
            // 校验结果返回签名
            if (!checkSign(convertMap(resultMap), paternerKey)) {
                payResult.setSuccess(false);
                payResult.setMsg("返回签名不正确");
                return payResult;
            }

            String resultCode = (String) resultMap.get("result_code");  // 业务结果
            String errCode = (String) resultMap.get("err_code");
            String errCodeDes = (String) resultMap.get("err_code_des");
            if (!"SUCCESS".equals(resultCode)) {
                payResult.setSuccess(false);
                payResult.setMsg(errCodeDes);
                payResult.setErrCode(errCode);
                payResult.setErrMsg(errCodeDes);
                return payResult;
            }
            // 成功
            String codeUrl = (String) resultMap.get("code_url");
            String prepayId = (String) resultMap.get("prepay_id");
            payResult.setSuccess(true);
            payResult.setMsg(returnMsg);
            payResult.setCodeUrl(codeUrl);
            payResult.setPrepayId(prepayId);
            Long time = new Date().getTime();
            time = time + 2 * 60 * 60 * 1000;
            Date expireTime = new Date(time);
            payResult.setExpireTime(expireTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payResult;
    }


    /**
     * 微信支付成功后回调处理
     * @param resultMap
     * @param paternerKey
     * @return
     */
    public static PayResult payBack(Map<String, String> resultMap, String paternerKey) {
        PayResult payResult = new PayResult();
        try {
            String returnCode = resultMap.get("return_code");  // 返回状态码
            String returnMsg = resultMap.get("return_msg");
            if (!"SUCCESS".equals(returnCode)) {
                payResult.setSuccess(false);
                payResult.setMsg(returnMsg);
                return payResult;
            }

            // 校验结果返回签名
            if (!checkSign(resultMap, paternerKey)) {
                payResult.setSuccess(false);
                payResult.setMsg("返回签名不正确");
                return payResult;
            }
            String resultCode = resultMap.get("result_code");  // 业务结果
            String errCode = resultMap.get("err_code");
            String errCodeDes = resultMap.get("err_code_des");
            if (!"SUCCESS".equals(resultCode)) {
                payResult.setSuccess(false);
                payResult.setMsg(errCodeDes);
                payResult.setErrCode(errCode);
                payResult.setErrMsg(errCodeDes);
                return payResult;
            }
            // 成功
            String attach = resultMap.get("attach");
            String timeEnd = resultMap.get("time_end");
            payResult.setSuccess(true);
            payResult.setMsg(returnMsg);
            payResult.setAttach(attach);
            payResult.setTimeEnd(DateUtils.getDate(timeEnd, "yyyyMMddHHmmss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payResult;
    }

    /**
     * 校验签名
     * @param resultMap
     * @param paternerKey
     * @return
     */
    public static boolean checkSign(Map<String, String> resultMap, String paternerKey) {
        try {
            String backSign = resultMap.get("sign");
            resultMap.remove("sign");   // 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名
            String sign = Pay.sign(resultMap, paternerKey);
            if (!sign.equals(backSign)) {
                return false;
            }
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通知反馈xml
     * @param returnCode SUCCESS/FAIL
     * @param returnMsg
     * @return 反馈微信的xml
     */
    public static String notifyBack(String returnCode, String returnMsg) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("return_code", returnCode);
        map.put("return_msg", returnMsg);
        return toXml(map);
    }

    /**
     * 转换map中string类型的键值对
     * @param map
     * @return
     */
    private static Map<String, String> convertMap(Map<String, Object> map) {
        Map<String, String> result = new HashMap<String, String>();
        Set<String> set = map.keySet();
        Iterator<String> its = set.iterator();
        while (its.hasNext()) {
            String it = its.next();
            Object val = map.get(it);
            if (val instanceof String) {
                result.put(it, (String) val);
            }
        }
        return result;
    }

    /**
     * 参数值用XML转义
     * @param paramMap
     * @return
     */
    public static String toXml(Map<String, String> paramMap) {
        StringBuilder xmlSb = new StringBuilder();
        xmlSb.append("<xml>");
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            xmlSb.append("<").append(entry.getKey()).append("><![CDATA[")
                    .append(entry.getValue()).append("]]>")
                    .append("</").append(entry.getKey()).append(">");
        }
        xmlSb.append("</xml>");

        return xmlSb.toString();
    }

    /**
     * 参数值用XML转义
     * @param paramMap
     * @return
     */
    public static String toNoCDATAXml(Map<String, String> paramMap) {
        StringBuilder xmlSb = new StringBuilder();
        xmlSb.append("<xml>");
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            xmlSb.append("<").append(entry.getKey()).append(">")
                    .append(entry.getValue())
                    .append("</").append(entry.getKey()).append(">");
        }
        xmlSb.append("</xml>");

        return xmlSb.toString();
    }

    private static Map<String, Object> readStringXmlOutNull(String xml) {
        try {
            Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            Iterator iter = rootElt.elementIterator(); // 获取根节点下的子节点head
            return toMap(iter);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Map<String, Object> toMap(Iterator iter) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 遍历head节点
        while (iter.hasNext()) {
            Element recordEle = (Element) iter.next();
            Iterator iterator = recordEle.elementIterator();
            if (iterator.hasNext()) {
                Map<String, Object> subMap = toMap(iterator);
                List attributes = recordEle.attributes();
                for (Object attribute1 : attributes) {
                    Attribute attribute = (Attribute) attribute1;
                    subMap.put(attribute.getName(), attribute.getValue());
                }
                packMap(map, recordEle.getName(), subMap);

            } else {
                List attributes = recordEle.attributes();
                if (attributes.isEmpty()) {
                    packMap(map, recordEle.getName(), recordEle.getData());
                    continue;
                }
                Map<String, Object> subMap = new HashMap<String, Object>();
                subMap.put("value", recordEle.getData());
                for (Object attribute1 : attributes) {
                    Attribute attribute = (Attribute) attribute1;
                    subMap.put(attribute.getName(), attribute.getValue());
                }
                packMap(map, recordEle.getName(), subMap);
            }
        }
        return map;
    }

    private static boolean packMap(Map<String, Object> map, String name, Object object) {
        if (!map.containsKey(name)) {
            map.put(name, object);
            return true;
        }
        if (map.get(name) instanceof List) {
            List list = (List) map.get(name);
            list.add(object);
        } else {
            List list = new ArrayList();
            list.add(map.get(name));
            list.add(object);
            map.put(name, list);
        }
        return false;
    }

}
