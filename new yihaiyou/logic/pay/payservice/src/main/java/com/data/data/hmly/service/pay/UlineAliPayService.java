package com.data.data.hmly.service.pay;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.pay.config.UlineCfg;
import com.data.data.hmly.service.pay.entity.PayLog;
import com.data.data.hmly.service.pay.entity.enums.PayAction;
import com.data.data.hmly.service.pay.entity.enums.PayTongdao;
import com.data.data.hmly.service.pay.util.UUIDUtil;
import com.data.data.hmly.service.pay.util.httpclient.HttpClientUtils;
import com.data.data.hmly.service.pay.util.httpclient.HttpUtil;
import com.google.common.collect.Maps;
import com.gson.bean.RefundResult;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by huangpeijie on 2017-02-17,0017.
 */
public class UlineAliPayService extends PayService {
    private static final Logger logger = Logger.getLogger(UlineAliPayService.class);

    private PayLogService payLogService = SpringContextHolder.getBean("payLogService");
    private PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
    private String apiUrl = propertiesManager.getString("ULINE_API_URL");
    private String mchId = propertiesManager.getString("ULINE_MCH_ID");
    private static final Random random = new Random();

    @Override
    public PayRequest doMakePayRequest(Order order) {
        String prepayId = prePay(order);
        PayRequest payRequest = new PayRequest();
        payRequest.setPrePay(prepayId);
        return payRequest;
    }

    @Override
    public PayRequest doMakePayRequest(FerryOrder order) {
        String prepayId = prePay(order);
        PayRequest payRequest = new PayRequest();
        payRequest.setPrePay(prepayId);
        return payRequest;
    }

    @Override
    public PayBackObject doMakeBackObject(Map<String, String> params, Order order) {
        String sign = params.get("sign");
        params.remove("sign");
        boolean result = doDealNotify(params, sign, order);
        Map resMap = new HashMap();
        if (result) {
            resMap.put("return_code", "SUCCESS");
        } else {
            resMap.put("return_code", "FAIL");
        }
        PayBackObject payBackObject = new PayBackObject();
        payBackObject.setResult(resMap);
        return payBackObject;
    }

    @Override
    public PayBackObject doMakeBackObject(Map<String, String> params, FerryOrder order) {
        String sign = params.get("sign");
        params.remove("sign");
        boolean result = doDealNotify(params, sign, order);
        Map resMap = new HashMap();
        if (result) {
            resMap.put("return_code", "SUCCESS");
        } else {
            resMap.put("return_code", "FAIL");
        }
        PayBackObject payBackObject = new PayBackObject();
        payBackObject.setResult(resMap);
        return payBackObject;
    }

    @Override
    public PayBackObject doMakeBackObject() {
        return null;
    }

    @Override
    public void doPayRequest() {

    }

    @Override
    public PaySearchResult doPaySearch() {
        return null;
    }

    //
    public String prePay(Order order) {
        Map<String, Object> map = doAliOrder(order);
        if (map.get("status") != null && "ok".equals(map.get("status").toString())) {
            return map.get("qr_code").toString();
        } else {
            logger.error(map.get("return_msg").toString());
        }
        return null;
    }

    public String prePay(FerryOrder order) {
        Map<String, Object> map = doAliOrder(order);
        if (map.get("status") != null && "ok".equals(map.get("status").toString())) {
            return map.get("qr_code").toString();
        } else {
            logger.error(map.get("return_msg").toString());
        }
        return null;
    }

    public Map<String, Object> doAliOrder(Order order) {
        Float cost = order.getPrice() * 100;
        int totalFee = cost.intValue();
        // 若订单已经支付则抛出异常
        if (order.getStatus() != OrderStatus.WAIT) {
            logger.info("订单未处于待支付状态，不可支付");
            return null;
        }
        // 记录交易日志
        addPayLog(order.getId(), order.getOrderType(), order.getUser(), order.getPrice());

        String nonceStr = UUIDUtil.getUUID();

        Map<String, String> paramMap = Maps.newTreeMap();
        paramMap.put("mch_id", mchId);
        paramMap.put("nonce_str", nonceStr);
        try {
            paramMap.put("body", new String(order.getName().getBytes("UTF-8"), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("统一下单[商品描述]编码错误");
        }
        paramMap.put("out_trade_no", order.getOrderNo());
        paramMap.put("timeout_express", ((DateUtils.getDateDiffLong(order.getWaitTime(), new Date()) / 1000 / 60) + 1) + "m");
        paramMap.put("total_fee", totalFee + ""); //微信的支付金额的单位是“分”
        paramMap.put("spbill_create_ip", "110.84.38.253");
        paramMap.put("notify_url", propertiesManager.getString("ULINE_ALIPAY_NOTIFY_URL"));
        paramMap.put("trade_type", "NATIVE");
        paramMap.put("sign", getSign(paramMap));

        String xmlData = toXml(paramMap);

        Map<String, Object> map = postForXML(apiUrl + UlineCfg.ALIPAY_SAVE_ORDER, xmlData);

        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            map.put("status", "ok");
            return map;
        } else {
            return map;
        }
    }

    public Map<String, Object> doAliOrder(FerryOrder order) {
        Float cost = order.getAmount() * 100;
        int totalFee = cost.intValue();
        // 若订单已经支付则抛出异常
        if (order.getStatus() != OrderStatus.WAIT) {
            logger.info("订单未处于待支付状态，不可支付");
            return null;
        }
        // 记录交易日志
        addPayLog(order.getId(), OrderType.ferry, order.getUser(), order.getAmount());

        String nonceStr = UUIDUtil.getUUID();

        Map<String, String> paramMap = Maps.newTreeMap();
        paramMap.put("mch_id", mchId);
        paramMap.put("nonce_str", nonceStr);
        try {
            paramMap.put("body", new String(order.getFlightLineName().getBytes("UTF-8"), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("统一下单[商品描述]编码错误");
        }
        paramMap.put("out_trade_no", order.getOrderNumber());
        paramMap.put("timeout_express", ((DateUtils.getDateDiffLong(order.getWaitTime(), new Date()) / 1000 / 60) + 1) + "m");
        paramMap.put("total_fee", totalFee + ""); //微信的支付金额的单位是“分”
        paramMap.put("spbill_create_ip", "110.84.38.253");
        paramMap.put("notify_url", propertiesManager.getString("ULINE_ALIPAY_NOTIFY_URL"));
        paramMap.put("trade_type", "NATIVE");
        paramMap.put("sign", getSign(paramMap));

        String xmlData = toXml(paramMap);

        Map<String, Object> map = postForXML(apiUrl + UlineCfg.ALIPAY_SAVE_ORDER, xmlData);

        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            map.put("status", "ok");
            return map;
        } else {
            return map;
        }
    }

    // 记录请求日志
    public void addPayLog(Long orderId, OrderType orderType, User user, Float price) {
        logger.info("记录交易日志");
        PayLog payLog = new PayLog();
        payLog.setOrderId(orderId);
        payLog.setOrderType(orderType);
        payLog.setUser(user);
        payLog.setAction(PayAction.pay);
        payLog.setCost(price);
        payLog.setRequestTime(new Date());
        payLog.setTongdao(PayTongdao.alipay);

        payLogService.save(payLog);
    }

    /**
     * 生成签名
     *
     * @param paramMap
     * @return
     */
    public String getSign(Map<String, String> paramMap) {
        StringBuilder signSb = new StringBuilder();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            signSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        signSb.append("key=").append(propertiesManager.getString("ULINE_KEY"));

        return DigestUtils.md5Hex(getContentBytes(signSb.toString(), "ISO8859-1")).toUpperCase();
    }

    private byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (Exception e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    private String toXml(Map<String, String> paramMap) {
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

    public Map<String, Object> postForXML(String url, String xmlData) {
        try {
            HttpClient httpClient = HttpClientUtils.getHttpClient();

            X509TrustManager xtm = new X509TrustManager() {   //创建TrustManager

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
            SSLContext ctx = SSLContext.getInstance("TLS");

            //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            ctx.init(null, new TrustManager[]{xtm}, null);

            //创建SSLSocketFactory
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

            //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(xmlData, ContentType.TEXT_XML));
            HttpResponse response = httpClient.execute(httpPost);
            String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
            return HttpUtil.readStringXmlOutNull(resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addNotifyLog(Order order, Map<String, String> paramMap) {
        PayLog payLog = new PayLog();
        payLog.setOrderId(order.getId());
        payLog.setOrderType(order.getOrderType());
        payLog.setUser(order.getUser());
        payLog.setAction(PayAction.requestback);
        float cost = Float.parseFloat(paramMap.get("total_fee")) / 100;
        payLog.setCost(cost);
        payLog.setTongdao(PayTongdao.alipay);
        payLog.setRequestTime(new Date());
        //TODO 没有用户支付邮箱只有用户openID
        payLog.setPayAccount(paramMap.get("openid"));
//		payLog.setSubject(paramMap.get("subject").toString());
        payLog.setTradeNo(paramMap.get("transaction_id"));
//		payLog.setNotifyId(paramMap.get("notify_id").toString());
//		payLog.setNotifyType(paramMap.get("notify_type").toString());

        payLog.setNotifyTime(new Date());

        payLogService.save(payLog);
    }

    public boolean doDealNotify(Map<String, String> orderMap, String sign, Order order) {

        String validateStr = getSign(orderMap);
        if (!validateStr.equals(sign)) {
            logger.error("签名:" + sign);
            logger.error("参数:" + JSONObject.fromObject(orderMap));
            logger.error("签名验证失败");
            return false;
        }

        //交易状态
        String tradeStatus = orderMap.get("result_code");

        if (tradeStatus.equals("SUCCESS")) {
            if (order.getStatus() == OrderStatus.WAIT) {
                order.setPayType(OrderPayType.ULINEALIPAY);
                doBusiness(order);
                addNotifyLog(order, orderMap);
                return true;
            } else {//已处理，返回true
                return true;
            }
        }

        return false;
    }

    public boolean doDealNotify(Map<String, String> orderMap, String sign, FerryOrder order) {

        String validateStr = getSign(orderMap);
        if (!validateStr.equals(sign)) {
            logger.error("签名验证失败");
            return false;
        }

        //交易状态
        String tradeStatus = orderMap.get("result_code");

        if (tradeStatus.equals("SUCCESS")) {
            if (order.getStatus() == OrderStatus.WAIT) {
                order.setPayType(OrderPayType.ULINEALIPAY);
                doBusiness(order);
                return true;
            } else {//已处理，返回true
                return true;
            }
        }

        return false;
    }

    /**
     * 关闭订单
     * 以下情况需要调用关单接口：商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
     *
     * @param orderNo 订单号
     * @return
     */
    public Map<String, Object> closeOrder(String orderNo) {
        String nonceStr = com.taobao.util.UUIDUtil.getUUID();
        Map<String, String> paramMap = Maps.newTreeMap();
        paramMap.put("mch_id", mchId);
        paramMap.put("nonce_str", nonceStr);
        paramMap.put("out_trade_no", orderNo);
        paramMap.put("sign", getSign(paramMap));
        String xmlData = toXml(paramMap);
        Map<String, Object> map = postForXML(apiUrl + UlineCfg.ALIPAY_CLOSE_ORDER, xmlData);
        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            map.put("status", "ok");
            return map;
        } else {
            return map;
        }
    }

    /**
     * 订单查询
     *
     * @param orderNo 订单号
     * @return
     */
    public Map<String, Object> queryOrder(String orderNo) {
        String nonceStr = com.taobao.util.UUIDUtil.getUUID();
        Map<String, String> paramMap = Maps.newTreeMap();
        paramMap.put("mch_id", mchId);
        paramMap.put("nonce_str", nonceStr);
        paramMap.put("out_trade_no", orderNo);
        paramMap.put("sign", getSign(paramMap));
        String xmlData = toXml(paramMap);
        Map<String, Object> map = postForXML(apiUrl + UlineCfg.ALIPAY_QUERY_ORDER, xmlData);
        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            map.put("status", "ok");
            return map;
        } else {
            return map;
        }
    }

    /**
     * 订单退款
     * 同一笔单的部分退款需要设置相同的订单号和不同的退款号。一笔退款失败后重新提交，要采用原来的退款号。总退款金额不能超过用户实际支付金额(现金券金额不能退款)。
     *
     * @param orderNo     订单号
     * @param refundNo    退款号
     * @param refundPrice 退款金额
     * @return
     */
    public RefundResult refundOrder(String orderNo, String refundNo, Float totalPrice, Float refundPrice) {
        String nonceStr = com.taobao.util.UUIDUtil.getUUID();
        Map<String, String> paramMap = Maps.newTreeMap();
        paramMap.put("mch_id", mchId);
        paramMap.put("nonce_str", nonceStr);
        paramMap.put("out_trade_no", orderNo);
        paramMap.put("out_refund_no", refundNo);
        paramMap.put("refund_fee", Float.valueOf(refundPrice * 100).intValue() + "");
        paramMap.put("op_user_id", mchId);
        paramMap.put("sign", getSign(paramMap));
        String xmlData = toXml(paramMap);
        Map<String, Object> map = postForXML(apiUrl + UlineCfg.ALIPAY_REFUND_ORDER, xmlData);
        RefundResult refundResult = new RefundResult();
        if (map.get("return_msg") != null) {
            refundResult.setMsg(map.get("return_msg").toString());
        }
        if (!"SUCCESS".equals(map.get("return_code"))) {
            refundResult.setSuccess(false);
            return refundResult;
        }
        if (!"SUCCESS".equals(map.get("result_code"))) {
            refundResult.setSuccess(false);
            refundResult.setErrCode(map.get("err_code").toString());
            refundResult.setErrMsg(map.get("err_code_des").toString());
            return refundResult;
        }
        refundResult.setSuccess(true);
        refundResult.setOutTradeNo(map.get("out_trade_no").toString());
        refundResult.setOutRefundNo(map.get("out_refund_no").toString());
        refundResult.setRefundFee(map.get("refund_fee").toString());
        refundResult.setRefundId(map.get("refund_id").toString());
        return refundResult;
    }

    /**
     * 退款订单查询
     *
     * @param refundNo 退款号
     * @return
     */
    public Map<String, Object> queryRefundOrder(String refundNo) {
        String nonceStr = com.taobao.util.UUIDUtil.getUUID();
        Map<String, String> paramMap = Maps.newTreeMap();
        paramMap.put("mch_id", mchId);
        paramMap.put("nonce_str", nonceStr);
        paramMap.put("out_refund_no", refundNo);
        paramMap.put("sign", getSign(paramMap));
        String xmlData = toXml(paramMap);
        Map<String, Object> map = postForXML(apiUrl + UlineCfg.ALIPAY_QUERY_REFUND_ORDER, xmlData);
        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            map.put("status", "ok");
            return map;
        } else {
            return map;
        }
    }
}
