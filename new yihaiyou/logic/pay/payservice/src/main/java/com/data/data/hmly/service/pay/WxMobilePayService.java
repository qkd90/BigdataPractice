package com.data.data.hmly.service.pay;

import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.pay.entity.PayLog;
import com.data.data.hmly.service.pay.entity.enums.PayAction;
import com.data.data.hmly.service.pay.entity.enums.PayTongdao;
import com.data.data.hmly.service.pay.util.UUIDUtil;
import com.data.data.hmly.service.pay.util.httpclient.HttpClientUtils;
import com.data.data.hmly.service.pay.util.httpclient.HttpUtil;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.google.common.collect.Maps;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;

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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by vacuity on 15/12/1.
 */

@Service
public class WxMobilePayService {

    private static final Logger logger = Logger.getLogger(WeixinPayService.class);

    @Resource
    private PayLogService payLogService;
    @Resource
    private PropertiesManager propertiesManager;
    private static final Random random = new Random();

    /**
     * 统一下订单
     */
    public Map<String, Object> doWeixinOrder(Order order, WechatAccount wechatAccount, String openId, String ip) {


        // 查询订单信息
        Float cost = 0f;
        List<OrderDetail> orderDetailList = order.getOrderDetails();
        String subject = orderDetailList.get(0).getProduct().getName();
        for (OrderDetail orderDetail : orderDetailList) {
            cost += orderDetail.getFinalPrice();
        }
        cost *= 100;
        int totalFee = cost.intValue();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 若订单已经支付则抛出异常
        if (order.getStatus() != OrderStatus.WAIT) {
            logger.info("订单未处于待支付状态，不可支付");
            resultMap.put("success", false);
            resultMap.put("errMsg", "订单未处于待支付状态，不可支付");
            return resultMap;
        }
        // 记录交易日志
        addPayLog(order);


        String nonceStr = UUIDUtil.getUUID();

        Map<String, String> paramMap = Maps.newTreeMap();
        paramMap.put("appid", wechatAccount.getAppId());
        paramMap.put("mch_id", wechatAccount.getMchId());
//        paramMap.put("mch_id", "1234162902");
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
        String outTradeNo = String.valueOf(new Date().getTime()) + String.valueOf(random.nextInt(99999));

        paramMap.put("out_trade_no", outTradeNo);
        paramMap.put("attach", order.getId() + "");
        paramMap.put("total_fee", totalFee + ""); //微信的支付金额的单位是“分”
//		paramMap.put("spbill_create_ip", payOrder.getIp());
        //TODO 此IP一定是将要支付的移动终端的IP
        paramMap.put("spbill_create_ip", ip);
//        paramMap.put("notify_url", WeixinCfg.NOTIFY_URL);
        paramMap.put("notify_url", propertiesManager.getString("WEBCHAT_NOTIFY_URL"));

        paramMap.put("trade_type", "JSAPI");
        //TODO 微信支付的用户openID,JSAPI时必传
        paramMap.put("openid", openId);
        paramMap.put("sign", getSign(paramMap, wechatAccount));

        String xmlData = toXml(paramMap);

//        Map<String, Object> map = postForXML(WeixinCfg.UNIFIED_ORDER_URL, xmlData);
        Map<String, Object> map = postForXML(propertiesManager.getString("WEBCHAT_UNIFIED_ORDER_URL"), xmlData);


        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            map.put("status", "ok");
            if (map.get("prepay_id") != null) {
                resultMap.put("success", true);
                resultMap.put("prepayId", map.get("prepay_id"));
            }
        } else {
            logger.error(map.get("return_msg").toString());
            resultMap.put("success", false);
            resultMap.put("errMsg", map.get("return_msg"));
        }
        return resultMap;
    }

    // 记录请求日志
    public void addPayLog(Order order) {
//        float cost = 0;
//        for (OrderDetail orderDetail : order.getOrderDetails()) {
//            cost += orderDetail.getFinalPrice();
//        }
        logger.info("记录交易日志");
        PayLog payLog = new PayLog();
        payLog.setOrderId(order.getId());
        payLog.setOrderType(order.getOrderType());
        payLog.setUser(order.getUser());
        payLog.setAction(PayAction.pay);
        payLog.setCost(order.getPrice());
        payLog.setRequestTime(new Date());
        payLog.setTongdao(PayTongdao.weixin);

        payLogService.save(payLog);
    }


    public static String getSign(Map<String, String> paramMap, WechatAccount wechatAccount) {
        StringBuilder signSb = new StringBuilder();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            signSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        signSb.append("key=").append(wechatAccount.getMchKey());

        return DigestUtils.md5Hex(getContentBytes(signSb.toString(), "ISO8859-1")).toUpperCase();
    }


    private static byte[] getContentBytes(String content, String charset) {
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

}
