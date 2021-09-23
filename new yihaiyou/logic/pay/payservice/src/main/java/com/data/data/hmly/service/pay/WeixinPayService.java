package com.data.data.hmly.service.pay;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.pay.entity.PayLog;
import com.data.data.hmly.service.pay.entity.enums.PayAction;
import com.data.data.hmly.service.pay.entity.enums.PayTongdao;
import com.data.data.hmly.service.pay.util.UUIDUtil;
import com.data.data.hmly.service.pay.util.httpclient.HttpClientUtils;
import com.data.data.hmly.service.pay.util.httpclient.HttpUtil;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.google.common.collect.Maps;
import com.gson.WeChatPay;
import com.gson.bean.RefundResult;
import com.gson.oauth.Pay;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WeixinPayService extends PayService {


    private static final Logger logger = Logger.getLogger(WeixinPayService.class);


    private PayLogService payLogService = SpringContextHolder.getBean("payLogService");
    private PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
    private WechatService wechatService = SpringContextHolder.getBean("wechatService");
    private static final Random random = new Random();

    @Override
    public PayRequest doMakePayRequest(Order order) {
        // TODO Auto-generated method stub
        String prepayId = prePay(order);
        PayRequest payRequest = new PayRequest();
        payRequest.setPrePay(prepayId);
        return payRequest;
    }

    public Map doMakePayRequestMap(Order order){
        List<OrderDetail> orderDetailList = order.getOrderDetails();
        String subject = "";
        if (StringUtils.isNotBlank(order.getName())) {
            subject = order.getName();
        } else {
            subject = orderDetailList.get(0).getProduct().getName();
        }
//        for (OrderDetail orderDetail : orderDetailList) {
//            if (orderDetail.getProductType() == ProductType.hotel && "ELONG".equals(String.valueOf(orderDetail.getProduct().getSource()))) {
//                cost += 0;
//            } else {
//                cost += orderDetail.getFinalPrice();
//            }
//        }
//        cost *= 100;
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
        String webchat_native_app_id = propertiesManager.getString("WEBCHAT_NATIVE_APP_ID");
        paramMap.put("appid", webchat_native_app_id);
        String mch_id = propertiesManager.getString("WEBCHAT_NATIVE_MCH_ID");
        paramMap.put("mch_id", mch_id);
        paramMap.put("nonce_str", nonceStr);
        if (StringUtils.isNotBlank(subject)) {
            try {
                paramMap.put("body", new String(subject.getBytes("UTF-8"), "ISO8859-1"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("统一下单[商品描述]编码错误");
            }
        } else {
            paramMap.put("body", subject);
        }
//		paramMap.put("out_trade_no", order.getId() + "");
        String outTradeNo = String.valueOf(new Date().getTime()) + String.valueOf(random.nextInt(99999));

        paramMap.put("out_trade_no", outTradeNo);
        paramMap.put("attach", order.getId() + "");
        paramMap.put("total_fee", totalFee + ""); //微信的支付金额的单位是“分”
//		paramMap.put("spbill_create_ip", payOrder.getIp());
        paramMap.put("spbill_create_ip", StringUtils.getIpAddr(ServletActionContext.getRequest()));
//        paramMap.put("notify_url", WeixinCfg.NOTIFY_URL);
        paramMap.put("notify_url", propertiesManager.getString("WEBCHAT_NOTIFY_URL"));
        paramMap.put("trade_type", "APP");
//		paramMap.put("openid", payOrder.getOpenId());
        //TODO 微信支付的用户openID,JSAPI时必传
//		paramMap.put("openid", "oSSZat5oEnuyKu9RM6GhRnXp_hdo");
        String sign = getSign(paramMap,propertiesManager.getString("WEBCHAT_NATIVE_KEY"));
        paramMap.put("sign", sign);

        String xmlData = toXml(paramMap);

        Map<String, Object> map = postForXML(propertiesManager.getString("WEBCHAT_UNIFIED_ORDER_URL"), xmlData);

        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            order.setTradeNo(outTradeNo);
            Map<String, Object> resultMap = Maps.newTreeMap();
            String prepareId = map.get("prepay_id").toString();
            resultMap.put("appid", webchat_native_app_id);
            resultMap.put("partnerid", mch_id);
            resultMap.put("prepayid",prepareId);
            resultMap.put("package","Sign=WXPay");
            resultMap.put("noncestr",UUIDUtil.getUUID());
            long timestamp = new Date().getTime() / 1000;
            resultMap.put("timestamp",timestamp);
            resultMap.put("sign",getSign(propertiesManager.getString("WEBCHAT_NATIVE_KEY"),resultMap));
//            String nonce_str = MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
//            String s="appid="+webchat_native_app_id+"&noncestr="+nonce_str+"&package=Sign=WXPay"+"&partnerid="+mch_id+"&prepayid="+prepareId+"&timestamp="+timestamp;
//            String newSign=MD5Util.MD5Encode(s+"&key="+propertiesManager.getString("WEBCHAT_NATIVE_KEY"),"UTF-8").toUpperCase();
//            resultMap.put("sign",newSign);

//            Map<String, String> resultMap = new HashMap<String, String>();
//            String sign = genAppSign(signParams);
//            resultMap.put("appid", ConstantUtil.APP_ID);
//            resultMap.put("noncestr", nonceStr);
//            resultMap.put("packageValue", "Sign=WXPay");
//            resultMap.put("partnerid", ConstantUtil.MCH_ID);
//            resultMap.put("prepayid", prepayId);
//            resultMap.put("timestamp", timeStamp);
//            resultMap.put("sign", sign);
//            return resultMap;

            return resultMap;
        }
        return null;
    }

    /**
     * 生成app支付签名
     *
     * @param params
     * @return
     */
    public String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(propertiesManager.getString("WEBCHAT_NATIVE_KEY"));
        String appSign = MD5(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }

    public final static String MD5(byte[] buffer) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public Map doMakePayRequest(Order order, WechatAccount wechatAccount, String openId, String ip, Boolean noCredit) {
        Float cost = Float.valueOf(order.getPrice().floatValue());
        String subject = order.getName();
        cost = Float.valueOf(cost.floatValue() * 100F);
        int totalFee = cost.intValue();
        Map resultMap = new HashMap();
        if (order.getStatus() != OrderStatus.WAIT) {
            logger.info("订单未处于待支付状态，不可支付");
            resultMap.put("success", Boolean.valueOf(false));
            resultMap.put("errMsg", "订单未处于待支付状态，不可支付");
            return resultMap;
        }
        String nonceStr = UUIDUtil.getUUID();
        Map<String, String> paramMap = Maps.newTreeMap();
        paramMap.put("appid", wechatAccount.getAppId());
        paramMap.put("mch_id", wechatAccount.getMchId());
        paramMap.put("nonce_str", nonceStr);
        if (StringUtils.isNotBlank(subject)) {
            try {
                paramMap.put("body", new String(subject.getBytes("UTF-8"), "ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("统一下单[商品描述]编码错误");
            }
        } else {
            paramMap.put("body", subject);
        }
        paramMap.put("out_trade_no", String.valueOf(order.getOrderNo()));
        paramMap.put("total_fee", (new StringBuilder()).append(totalFee).append("").toString());
        paramMap.put("spbill_create_ip", ip);
        paramMap.put("notify_url", wechatAccount.getNotifyUrl());
        paramMap.put("trade_type", "JSAPI");
        paramMap.put("openid", openId);
        paramMap.put("attach", order.getId().toString() + "-" + order.getOrderType().name());
        if (noCredit) {
            paramMap.put("limit_pay", "no_credit");
        }
        paramMap.put("sign", getSign(paramMap, wechatAccount));
        String xmlData = toXml(paramMap);
        addPayLog(order.getId(), order.getOrderType(), order.getUser(), order.getPrice());
        Map map = postForXML("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlData);
        logger.error(map);
        if ("SUCCESS".equals(map.get("return_code"))) {
            if ("SUCCESS".equals(map.get("result_code"))) {
                order.setOrderNo(String.valueOf(order.getOrderNo()));
                map.put("status", "ok");
                resultMap.put("success", Boolean.valueOf(true));
                resultMap.put("prepayId", map.get("prepay_id"));
            } else {
                logger.error(map.get("err_code_des").toString());
                resultMap.put("success", Boolean.valueOf(false));
                resultMap.put("errMsg", map.get("err_code_des"));
            }
        } else {
            logger.error(map.get("return_msg").toString());
            resultMap.put("success", Boolean.valueOf(false));
            resultMap.put("errMsg", map.get("return_msg"));
        }
        return resultMap;
    }

    public Map doMakePayRequest(FerryOrder order, WechatAccount wechatAccount, String openId, String ip) {
        Float cost = Float.valueOf(order.getAmount().floatValue());
        String subject = order.getFlightLineName();
        cost = Float.valueOf(cost.floatValue() * 100F);
        int totalFee = cost.intValue();
        Map resultMap = new HashMap();
        if (order.getStatus() != OrderStatus.WAIT) {
            logger.info("订单未处于待支付状态，不可支付");
            resultMap.put("success", Boolean.valueOf(false));
            resultMap.put("errMsg", "订单未处于待支付状态，不可支付");
            return resultMap;
        }
        String nonceStr = UUIDUtil.getUUID();
        Map<String, String> paramMap = Maps.newTreeMap();
        paramMap.put("appid", wechatAccount.getAppId());
        paramMap.put("mch_id", wechatAccount.getMchId());
        paramMap.put("nonce_str", nonceStr);
        if (StringUtils.isNotBlank(subject)) {
            try {
                paramMap.put("body", new String(subject.getBytes("UTF-8"), "ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("统一下单[商品描述]编码错误");
            }
        } else {
            paramMap.put("body", subject);
        }
        paramMap.put("out_trade_no", String.valueOf(order.getOrderNumber()));
        paramMap.put("total_fee", (new StringBuilder()).append(totalFee).append("").toString());
        paramMap.put("spbill_create_ip", ip);
        paramMap.put("notify_url", wechatAccount.getNotifyUrl());
        paramMap.put("trade_type", "JSAPI");
        paramMap.put("openid", openId);
        paramMap.put("attach", order.getId().toString() + "-ferry");
        paramMap.put("sign", getSign(paramMap, wechatAccount));
        String xmlData = toXml(paramMap);
        addPayLog(order.getId(), OrderType.ferry, order.getUser(), order.getAmount());
        Map map = postForXML("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlData);
        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            map.put("status", "ok");
            if (map.get("prepay_id") != null) {
                resultMap.put("success", Boolean.valueOf(true));
                resultMap.put("prepayId", map.get("prepay_id"));
            }
        } else {
            logger.error(map.get("return_msg").toString());
            resultMap.put("success", Boolean.valueOf(false));
            resultMap.put("errMsg", map.get("err_code_des"));
        }
        return resultMap;
    }

    public Map doMakeAppPayRequest(Order order, WechatAccount wechatAccount, String openId, String ip) {
        Float cost = Float.valueOf(order.getPrice().floatValue());
        String subject = wechatAccount.getAccount();
        cost = Float.valueOf(cost.floatValue() * 100F);
        int totalFee = cost.intValue();
        Map resultMap = new HashMap();
        if (order.getStatus() != OrderStatus.WAIT) {
            logger.info("订单未处于待支付状态，不可支付");
            resultMap.put("success", Boolean.valueOf(false));
            resultMap.put("errMsg", "订单未处于待支付状态，不可支付");
            return resultMap;
        }
        String nonceStr = UUIDUtil.getUUID();
        Map<String, String> paramMap = Maps.newTreeMap();
        paramMap.put("appid", wechatAccount.getAppAppId());
        paramMap.put("mch_id", wechatAccount.getMchId());
        paramMap.put("nonce_str", nonceStr);
        if (StringUtils.isNotBlank(subject)) {
            try {
                paramMap.put("body", new String(subject.getBytes("UTF-8"), "ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("统一下单[商品描述]编码错误");
            }
        } else {
            paramMap.put("body", subject);
        }
        paramMap.put("out_trade_no", String.valueOf(order.getOrderNo()));
        paramMap.put("total_fee", (new StringBuilder()).append(totalFee).append("").toString());
        paramMap.put("spbill_create_ip", ip);
        paramMap.put("notify_url", wechatAccount.getNotifyUrl());
        paramMap.put("trade_type", "JSAPI");
        paramMap.put("openid", openId);
        paramMap.put("attach", order.getId().toString() + "-" + order.getOrderType().name());
        paramMap.put("sign", getSign(paramMap, wechatAccount));
        String xmlData = toXml(paramMap);
        addPayLog(order.getId(), order.getOrderType(), order.getUser(), order.getPrice());
        Map map = postForXML("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlData);
        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            order.setOrderNo(String.valueOf(order.getOrderNo()));
            map.put("status", "ok");
            if (map.get("prepay_id") != null) {
                resultMap.put("success", Boolean.valueOf(true));
                resultMap.put("prepayId", map.get("prepay_id"));
            }
        } else {
            logger.error(map.get("return_msg").toString());
            resultMap.put("success", Boolean.valueOf(false));
            resultMap.put("errMsg", map.get("err_code_des"));
        }
        return resultMap;
    }

    public Map doMakeAppPayRequest(FerryOrder order, WechatAccount wechatAccount, String openId, String ip) {
        Float cost = Float.valueOf(order.getAmount().floatValue());
        String subject = wechatAccount.getAccount();
        cost = Float.valueOf(cost.floatValue() * 100F);
        int totalFee = cost.intValue();
        Map resultMap = new HashMap();
        if (order.getStatus() != OrderStatus.WAIT) {
            logger.info("订单未处于待支付状态，不可支付");
            resultMap.put("success", Boolean.valueOf(false));
            resultMap.put("errMsg", "订单未处于待支付状态，不可支付");
            return resultMap;
        }
        String nonceStr = UUIDUtil.getUUID();
        Map<String, String> paramMap = Maps.newTreeMap();
        paramMap.put("appid", wechatAccount.getAppAppId());
        paramMap.put("mch_id", wechatAccount.getMchId());
        paramMap.put("nonce_str", nonceStr);
        if (StringUtils.isNotBlank(subject)) {
            try {
                paramMap.put("body", new String(subject.getBytes("UTF-8"), "ISO-8859-1"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("统一下单[商品描述]编码错误");
            }
        } else {
            paramMap.put("body", subject);
        }
        paramMap.put("out_trade_no", String.valueOf(order.getOrderNumber()));
        paramMap.put("total_fee", (new StringBuilder()).append(totalFee).append("").toString());
        paramMap.put("spbill_create_ip", ip);
        paramMap.put("notify_url", wechatAccount.getNotifyUrl());
        paramMap.put("trade_type", "JSAPI");
        paramMap.put("openid", openId);
        paramMap.put("attach", order.getId().toString() + "-ferry");
        paramMap.put("sign", getSign(paramMap, wechatAccount));
        String xmlData = toXml(paramMap);
        addPayLog(order.getId(), OrderType.ferry, order.getUser(), order.getAmount());
        Map map = postForXML("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlData);
        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            map.put("status", "ok");
            if (map.get("prepay_id") != null) {
                resultMap.put("success", Boolean.valueOf(true));
                resultMap.put("prepayId", map.get("prepay_id"));
            }
        } else {
            logger.error(map.get("return_msg").toString());
            resultMap.put("success", Boolean.valueOf(false));
            resultMap.put("errMsg", map.get("err_code_des"));
        }
        return resultMap;
    }

    public String getSign(Map<String, String> paramMap, WechatAccount wechatAccount) {
        StringBuilder signSb = new StringBuilder();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            signSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        signSb.append("key=").append(wechatAccount.getMchKey());
        return DigestUtils.md5Hex(getContentBytes(signSb.toString(), "ISO8859-1")).toUpperCase();
    }

    @Override
    public PayBackObject doMakeBackObject(Map<String, String> params, Order order) {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub

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

    // 已经成功回调后再次回调
    public PayBackObject doMakeBackObject() {
        PayBackObject payBackObject = new PayBackObject();
        Map<String, Object> result = Maps.newHashMap();
        result.put("return_code", "SUCCESS");
        payBackObject.setResult(result);
        return payBackObject;
    }

    @Override
    public void doPayRequest() {
        // TODO Auto-generated method stub

    }

    @Override
    public PaySearchResult doPaySearch() {
        // TODO Auto-generated method stub
        return null;
    }

    public String prePay(Order order) {
        Map<String, Object> map = doWeixinOrder(order);
        if (map.get("status") != null && isValidate(map.get("status").toString())) {
            return map.get("code_url").toString();
        } else {
            logger.error(map.get("return_msg").toString());
        }
        return null;
    }


    /**
     * 统一下订单
     */
    public Map<String, Object> doWeixinOrder(Order order) {


        // 查询订单信息
//        Float cost = 0f;
        List<OrderDetail> orderDetailList = order.getOrderDetails();
        String subject = "";
        if (StringUtils.isNotBlank(order.getName())) {
            subject = order.getName();
        } else {
            subject = orderDetailList.get(0).getProduct().getName();
        }
//        for (OrderDetail orderDetail : orderDetailList) {
//            if (orderDetail.getProductType() == ProductType.hotel && "ELONG".equals(String.valueOf(orderDetail.getProduct().getSource()))) {
//                cost += 0;
//            } else {
//                cost += orderDetail.getFinalPrice();
//            }
//        }
//        cost *= 100;
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
        paramMap.put("appid", propertiesManager.getString("WEBCHAT_APP_ID"));
        paramMap.put("mch_id", propertiesManager.getString("WEBCHAT_MCH_ID"));
        paramMap.put("nonce_str", nonceStr);
        if (StringUtils.isNotBlank(subject)) {
            try {
                paramMap.put("body", new String(subject.getBytes("UTF-8"), "ISO8859-1"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("统一下单[商品描述]编码错误");
            }
        } else {
            paramMap.put("body", subject);
        }
//		paramMap.put("out_trade_no", order.getId() + "");
        String outTradeNo = String.valueOf(new Date().getTime()) + String.valueOf(random.nextInt(99999));

        paramMap.put("out_trade_no", outTradeNo);
        paramMap.put("attach", order.getId() + "");
        paramMap.put("total_fee", totalFee + ""); //微信的支付金额的单位是“分”
//		paramMap.put("spbill_create_ip", payOrder.getIp());
        paramMap.put("spbill_create_ip", "110.84.38.253");
//        paramMap.put("notify_url", WeixinCfg.NOTIFY_URL);
        paramMap.put("notify_url", propertiesManager.getString("WEBCHAT_NOTIFY_URL"));
        paramMap.put("trade_type", "NATIVE");
//		paramMap.put("openid", payOrder.getOpenId());
        //TODO 微信支付的用户openID,JSAPI时必传
//		paramMap.put("openid", "oSSZat5oEnuyKu9RM6GhRnXp_hdo");
        paramMap.put("sign", getSign(paramMap));

        String xmlData = toXml(paramMap);

        Map<String, Object> map = postForXML(propertiesManager.getString("WEBCHAT_UNIFIED_ORDER_URL"), xmlData);

        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            order.setTradeNo(outTradeNo);
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
        payLog.setTongdao(PayTongdao.weixin);

        payLogService.save(payLog);
    }

    // 记录回调日志
    public void addNotifyLog(Order order, Map<String, String> paramMap) {
        PayLog payLog = new PayLog();
        payLog.setOrderId(order.getId());
        payLog.setOrderType(order.getOrderType());
        payLog.setUser(order.getUser());
        payLog.setAction(PayAction.requestback);
        float cost = Float.parseFloat(paramMap.get("total_fee")) / 100;
        payLog.setCost(cost);
        payLog.setTongdao(PayTongdao.weixin);
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
            logger.error("签名验证失败");
            return false;
        }

        //交易状态
        String tradeStatus = orderMap.get("result_code");

        if (tradeStatus.equals("SUCCESS")) {
            if (order.getStatus() == OrderStatus.WAIT) {
                order.setPayType(OrderPayType.WECHAT);
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
                order.setPayType(OrderPayType.WECHAT);
                doBusiness(order);
                return true;
            } else {//已处理，返回true
                return true;
            }
        }

        return false;
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

        signSb.append("key=").append(propertiesManager.getString("WEBCHAT_KEY"));

        return DigestUtils.md5Hex(getContentBytes(signSb.toString(), "ISO8859-1")).toUpperCase();
    }
    /**
     * 生成签名
     *
     * @param paramMap
     * @return
     */
    public String getSign(String key,Map<String, Object> paramMap) {
        StringBuilder signSb = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            signSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        signSb.append("key=").append(key);
//        return DigestUtils.md5Hex(getContentBytes(signSb.toString(), "ISO8859-1")).toUpperCase();
        return MD5(signSb.toString().getBytes()).toUpperCase();
    }
    /**
     * 生成签名
     *
     * @param paramMap
     * @return
     */
    public String getSign(Map<String, String> paramMap,String key) {
        StringBuilder signSb = new StringBuilder();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            signSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        signSb.append("key=").append(key);

        return DigestUtils.md5Hex(getContentBytes(signSb.toString(), "ISO8859-1")).toUpperCase();
//        return MD5(signSb.toString().getBytes()).toUpperCase();
    }

    /**
     * 生成签名
     *
     * @param paramMap
     * @return
     */
    public String getNativeSign(Map<String, Object> paramMap) {
        StringBuilder signSb = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            signSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        signSb.append("key=").append(propertiesManager.getString("WEBCHAT_KEY"));

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


    /**
     * 参数值用XML转义
     *
     * @param paramMap
     * @return
     */
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

    public static boolean isValidate(String result) {
        return "ok".equals(result.toLowerCase());
    }

    @Override
    public Map doTransfersRequest(Order order, WechatAccount wechatAccount, String openId, String ip, String filePath) {
        Map<String, Object> resultMap = Maps.newHashMap();
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("mch_appid", wechatAccount.getAppId());      // 公众账号ID
        paramMap.put("mchid", wechatAccount.getMchId());     // 商户号
        String nonceStr = com.zuipin.util.UUIDUtil.getUUID();
        paramMap.put("nonce_str", nonceStr);                //随机字符串
        paramMap.put("partner_trade_no", order.getOrderNo());                //商户订单号
        paramMap.put("openid", openId);                //用户openid
        paramMap.put("check_name", "NO_CHECK");                //校验用户姓名选项
        paramMap.put("amount", String.valueOf(Float.valueOf(order.getPrice() * 100).intValue()));                //金额
        paramMap.put("desc", order.getName());                //企业付款描述信息
        paramMap.put("spbill_create_ip", ip);                //Ip地址
        String paternerKey = wechatAccount.getMchKey();
        String sign = null;
        try {
            sign = Pay.sign(paramMap, paternerKey);
        } catch (UnsupportedEncodingException e) {
            resultMap.put("success", false);
            resultMap.put("errCode", "");
            e.printStackTrace();
        }
        paramMap.put("sign", sign);                         // 签名
        String xmlData = WeChatPay.toNoCDATAXml(paramMap);
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(filePath));
            keyStore.load(instream, wechatAccount.getMchId().toCharArray());
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, wechatAccount.getMchId().toCharArray()).build();//指定TLS版本 
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);//设置httpclient的SSLSocketFactory 
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            StringEntity se = new StringEntity(xmlData, "UTF-8");

            HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");

            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
            Map map = HttpUtil.readStringXmlOutNull(resultStr);
            if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
                resultMap.put("success", true);
                resultMap.put("orderNo", map.get("partner_trade_no"));
            } else {
                logger.error(map.get("return_msg").toString());
                resultMap.put("success", false);
                resultMap.put("errCode", map.get("err_code"));
                resultMap.put("errMsg", map.get("err_code_des"));
            }
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("errCode", "");
            e.printStackTrace();
        }
        return resultMap;
    }

    @Override
    public RefundResult refundOrder(String orderNo, String refundNo, Float totalPrice, Float refundPrice) {
        WechatAccount wechatAccount = wechatService.getWechatAccount(propertiesManager.getLong("WEBCHAT_ACCOUNT_ID"));
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appid", wechatAccount.getAppId());      // 公众账号ID
        paramMap.put("mch_id", wechatAccount.getMchId());     // 商户号
        paramMap.put("out_trade_no", orderNo);   // 商户订单号：32个字符内，唯一，重新支付用原订单号
        paramMap.put("out_refund_no", refundNo);   // 商户退款编号：32个字符内，唯一，重新退款用退款编号
        paramMap.put("total_fee", Float.valueOf(totalPrice * 100).intValue() + ""); // 订单总金额，单位为分
        paramMap.put("refund_fee", Float.valueOf(refundPrice * 100).intValue() + ""); // 订单退款金额，单位为分
        paramMap.put("op_user_id", wechatAccount.getOriginalId());
        String paternerKey = wechatAccount.getMchKey();
//        String refundUrl = propertiesManager.getString("WEBCHAT_REFUND_URL");
        String filePath = propertiesManager.getString("CERT_DIR");
        String wId = wechatAccount.getMchId();

        return WeChatPay.refundOrder(paramMap, paternerKey, filePath, wId);
    }
}
