package com.hmlyinfo.app.soutu.weixin.service;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.CacheKey;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.scenicTicket.domain.PayOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.WxToken;
import com.hmlyinfo.base.cache.CacheProvider;
import com.hmlyinfo.base.cache.XMemcachedImpl;
import com.hmlyinfo.base.util.HttpClientUtils;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.UUIDUtil;

@Service
public class WeiXinService{

	private static final String APP_ID = Config.get("WX_APPID"); //公共账号id
    private static final String App_Secret = Config.get("WX_AppSecret"); //公共账号id
    private static final String MCH_ID = Config.get("MCH_ID"); //商户号
    private static final String KEY = Config.get("MCH_KEY"); //key
    private static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //统一下单的url
    private static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund"; //统一下单的url
    
	private static ObjectMapper om = new ObjectMapper();
	// TODO:token存放于缓存服务中，是否考虑其他方式
	private CacheProvider provider = XMemcachedImpl.getInstance();
	private static final Log logs = LogFactory.getLog(WeiXinService.class);
	
	/**
	 * 接口验证
	 * @param request
	 * @return
	 */
	public String verification(HttpServletRequest request)
	{
		// 加密签名
		String signature = request.getParameter("signature");
		// 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
        String echostr = request.getParameter("echostr");

        String tokenstr = Config.get("WX_TOKEN");
        String[] variables = {tokenstr, timestamp, nonce};
        Arrays.sort(variables);

        StringBuilder b = new StringBuilder();
        b.append(variables[0]).append(variables[1]).append(variables[2]);
        String result = encode(b.toString());
        if (result.equals(signature)){
            return echostr;
        }else{
            return "failed";
        }
	}
	
	/**
	 * 统一下门票订单接口
	 * @param payOrder
	 * @param notifyUrl
	 * @return
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
    
    /**
     * 微信下单地址回调，TODO:更新订单状态需调用者实现，是否需要发送推送消息，调用者自己选择调用
     * @param request
     * @return
     * @throws Exception
     */
	public long payOrderByPayOrder(HttpServletRequest request) throws Exception
	{
		Map<String, String> map = HttpUtil.parseXml(request);
        Map<String, Object> orderMap = new TreeMap<String, Object>();
        String sign = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if ("sign".equals(entry.getKey())) {
                sign = entry.getValue();
                continue;
            }
            orderMap.put(entry.getKey(), entry.getValue());
        }
        String validateStr = getSign(orderMap);
        if (!validateStr.equals(sign)) {
            logs.error("签名验证失败");
            return -1;
        }
        
        Long payOrderId = Long.valueOf(orderMap.get("out_trade_no").toString());
        return payOrderId;
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
    
	/**
	 * SHA-1加密
	 * @param source
	 * @return
	 */
	private String encode(String source) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(source.getBytes());
            byte[] result = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte word: result) {
                String shaHex = Integer.toHexString(word & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
	
	/**
	 * 获取token
	 * @return
	 */
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
	
	/**
	 * 微信模板消息
	 * @param temId
	 * @param openId
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
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
}
