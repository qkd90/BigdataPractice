package com.hmlyinfo.app.soutu.scenicTicket.service;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.account.domain.ThridPartyUser;
import com.hmlyinfo.app.soutu.account.mapper.ThridPartyUserMapper;
import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanOrder;
import com.hmlyinfo.app.soutu.bargain.service.BargainPlanOrderService;
import com.hmlyinfo.app.soutu.base.CacheKey;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.scenicTicket.domain.PayOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.RefundOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.WxToken;
import com.hmlyinfo.base.cache.CacheProvider;
import com.hmlyinfo.base.cache.XMemcachedImpl;
import com.hmlyinfo.base.util.HttpClientUtils;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.StringUtil;
import com.hmlyinfo.base.util.UUIDUtil;
import com.hmlyinfo.base.util.Validate;

/**
 * Created by Fox on 2015-04-03,0003.
 */
@Service
public class WeiXinService {
    private static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //统一下单的url
    private static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund"; //统一下单的url
    private static final String APP_ID = Config.get("H5_WEIXIN_APPID"); //公共账号id
    private static final String App_Secret = Config.get("H5_WEIXIN_AppSecret"); //公共账号id
    private static final String MCH_ID = Config.get("MCH_ID"); //商户号
    private static final String KEY = Config.get("MCH_KEY"); //key
    
    private CacheProvider provider = XMemcachedImpl.getInstance();
    private static ObjectMapper om = new ObjectMapper();
    private static final Log logs = LogFactory.getLog(WeiXinService.class);
    
    @Autowired
	private ThridPartyUserMapper<ThridPartyUser> thridPartyUserMapper;
    @Autowired
    private ScenicTicketOrderService orderService;
    @Autowired
    private BargainPlanOrderService bargainPlanOrderService;
    
    /**
     * 统一下订单
     */
    public Map<String, Object> weixinOrder(PayOrder payOrder, String notifyUrl) {
        String nonceStr = UUIDUtil.getUUID();

        Map<String, Object> paramMap = Maps.newTreeMap();
        paramMap.put("appid", APP_ID);
        paramMap.put("mch_id", MCH_ID);
        paramMap.put("nonce_str", nonceStr);
        try {
            paramMap.put("body", new String(payOrder.getBody().getBytes("utf8"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("编码错误");
        }
        paramMap.put("out_trade_no", payOrder.getId());
        paramMap.put("total_fee", (int) (payOrder.getTotalFee() * 100)); //微信的支付金额的单位是“分”
        paramMap.put("spbill_create_ip", payOrder.getIp());
        paramMap.put("notify_url", notifyUrl);
        paramMap.put("trade_type", "JSAPI");
        paramMap.put("openid", payOrder.getOpenId());
        paramMap.put("sign", getSign(paramMap));

        String xmlData = toXml(paramMap);

        Map<String, Object> map = postForXML(UNIFIED_ORDER_URL, xmlData);

        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            map.put("status", "ok");
            return map;
        } else {
            return map;
        }
    }

    //todo: 未经过测试，微信退款接口有问题
    public Map<String, Object> refund(RefundOrder refundOrder) {
        String nonceStr = UUIDUtil.getUUID();

        Map<String, Object> paramMap = Maps.newTreeMap();
        paramMap.put("appid", APP_ID);
        paramMap.put("mch_id", MCH_ID);
        paramMap.put("nonce_str", nonceStr);
        paramMap.put("op_user_id", MCH_ID);
        paramMap.put("out_refund_no", refundOrder.getId());
        paramMap.put("out_trade_no", refundOrder.getId());
        paramMap.put("refund_fee", refundOrder.getRefundFee() * 100); //微信的支付金额的单位是“分”
        paramMap.put("total_fee", refundOrder.getRefundFee());
        paramMap.put("sign", getSign(paramMap));

        String xmlData = toXml(paramMap);

        Map<String, Object> map = postForXML(REFUND_URL, xmlData);

        if ("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
            map.put("status", "ok");
            return map;
        } else {
            return map;
        }
    }

    public static String getSign(Map<String, Object> paramMap) {
        StringBuilder signSb = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            signSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        signSb.append("key=").append(KEY);

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

    private String toXml(Map<String, Object> paramMap) {
        StringBuilder xmlSb = new StringBuilder();
        xmlSb.append("<xml>");
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
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
        return PayOrder.VALIDATE_RESULT_SUCCESS.equals(result.toLowerCase());
    }
    
    public WxToken getToken()
    {
        long curTs = new Date().getTime() / 1000;
        WxToken token = provider.get(CacheKey.WX_TOKEN_KEY);
        if (token == null || curTs - token.getTimestamp() > 600)
        {
            try
            {
                token = new WxToken();

                String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID + "&secret=" + App_Secret;
                String tokenStr = HttpClientUtils.getHttps(tokenUrl);
                Map<String, String> tokenMap = om.readValue(tokenStr, Map.class);
                token.setAccessToken(tokenMap.get("access_token"));

                // 请求jsapi_ticket
                String responseStr = HttpClientUtils.getHttps("https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + token.getAccessToken());
                Map<String, String> responseMap = om.readValue(responseStr, Map.class);

                token.setJsapiTicket(responseMap.get("ticket"));
                token.setTimestamp(curTs);
                token.setNoncestr(UUIDUtil.getUUID());

                provider.set(CacheKey.WX_TOKEN_KEY, token);
            }
            catch (Exception e)
            {
                logs.error(e);
                token = null;
            }
        }

        return token;
    }
    
    // 订票成功后通过微信发送通知
    public void pushTicketMessage(Long orderId) {
    	ScenicTicketOrder order = orderService.info(orderId);
    	// 判断是否已经支付
    	if(order.getPushFlag() == ScenicTicketOrder.PUSH_YES || order.getStatus() == ScenicTicketOrder.STATUS_NOT_PAID){
    		return;
    	}
    	
    	String temId = Config.get("TICKET_TEMP_ID");
    	String url = Config.get("H5_SRV_ADDR_WWW") + "/order/detail/" + order.getId();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String orderDate = sdf.format(order.getOrderDate());
		String[][] infoStrings = { { "first", "恭喜您，您的门票购买成功！" },
				{ "OrderID", order.getOrderNum() }, 
				{ "PkgName", order.getOrderName() },
				{ "TakeOffDate", orderDate },
				{ "remark", "如有疑问，请致电0592-5530252。" } };

		Map<String, Map<String, Object>> paramMap = Maps.newHashMap();
		for (String[] info : infoStrings) {
			Map<String, Object> infoMap = Maps.newHashMap();
			infoMap.put("value", info[1]);
			paramMap.put(info[0], infoMap);
		}
		
		Long userId = order.getUserId();
 		Map<String, Object> userMap = Maps.newHashMap();
 		userMap.put("userId", userId);
 		userMap.put("type", "3");
 		List<ThridPartyUser> userList = thridPartyUserMapper.list(userMap);
 		String openId = userList.get(0).getOpenId();
		
		try {
			wxPushTemplate(temId, openId, url, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			Validate.isTrue(false, ErrorCode.ERROR_50001, "推送信息发送失败");
		} 
		order.setPushFlag(ScenicTicketOrder.PUSH_YES);
		orderService.update(order);
	}
    
    // 特价路线购买成功后通过微信发送通知
    public void pushBargainMessage(Long bargainOrderId) {
    	BargainPlanOrder order = bargainPlanOrderService.info(bargainOrderId);
    	// 判断是否已经支付
    	if(order.getPushFlag() == BargainPlanOrder.PUSH_YES || order.getStatus() != BargainPlanOrder.STATUS_PAY_SUCCESS){
    		return;
    	}
    	
    	String temId = Config.get("BARGAIN_TEMP_ID");
    	String url = Config.get("H5_SRV_ADDR_WWW") + "/order/onsale/order/detail/" + order.getId();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String orderDate = sdf.format(order.getPlayDate());
		String[][] infoStrings = { { "first", "恭喜您，您的特价行程购买成功！" },
				{ "OrderID", " " + order.getId().toString() }, 
				{ "PkgName", " " + order.getOrderName() },
				{ "TakeOffDate", " " + orderDate },
				{ "Remark", "如有疑问，请致电0592-5530252。" } };

		Map<String, Map<String, Object>> paramMap = Maps.newHashMap();
		for (String[] info : infoStrings) {
			Map<String, Object> infoMap = Maps.newHashMap();
			infoMap.put("value", info[1]);
			paramMap.put(info[0], infoMap);
		}
		
		Long userId = order.getUserId();
 		Map<String, Object> userMap = Maps.newHashMap();
 		userMap.put("userId", userId);
 		userMap.put("type", "3");
 		List<ThridPartyUser> userList = thridPartyUserMapper.list(userMap);
 		String openId = userList.get(0).getOpenId();
		
		try {
			wxPushTemplate(temId, openId, url, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			Validate.isTrue(false, ErrorCode.ERROR_50001, "推送信息发送失败");
		} 
		
		order.setPushFlag(BargainPlanOrder.PUSH_YES);
		bargainPlanOrderService.update(order);
	}
    
    public String wxPushTemplate(String temId, String openId, String url, Map<String, Map<String, Object>> paramMap) throws KeyManagementException, NoSuchAlgorithmException 
    {
    	WxToken token = getToken();
 		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token.getAccessToken();
 		
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\"touser\":\"").append(openId).append("\",\"template_id\":\"")
        	.append(temId).append("\",\"url\":\"").append(url).append("\",\"topcolor\":\"#FF0000\", \"data\":{");
        
        for (Map.Entry<String, Map<String, Object>> entry : paramMap.entrySet()) 
        {
        	buffer.append("\"").append(entry.getKey()).append("\":{");
        	buffer.append("\"value\":\"").append(entry.getValue().get("value")).append("\"},");
		}
        buffer.setLength(buffer.length()-1);
        buffer.append("}}");
 		
        String resultStr = HttpClientUtils.postHttpsJSON(tokenUrl, buffer.toString());
        logs.info(resultStr);
        
		return resultStr;
	}
    
    public String wxPushKfMsg(String openId, String content) throws KeyManagementException, NoSuchAlgorithmException 
    {
    	WxToken token = getToken();
 		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token.getAccessToken();
 		
 		String msgTpl = StringUtil.getStrFromFile(this.getClass().getResourceAsStream("/tpl/wxKfMsg.tpl"));
 		String msgJson = msgTpl.replaceAll("#OPENID", openId).replaceAll("#content", content);
 		
        String resultStr = HttpClientUtils.postHttpsJSON(tokenUrl, msgJson);
        logs.info(resultStr);
        
		return resultStr;
	}
    
}






