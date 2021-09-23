package com.data.data.hmly.service.pay;

import com.data.data.hmly.service.pay.util.MD5;
import com.data.data.hmly.service.pay.util.UUIDUtil;
import com.data.data.hmly.service.pay.util.httpclient.HttpClientUtils;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gson.util.SHA1;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WxService {

    @Resource
    private WechatAccountService wechatAccountService;
    @Resource
    private WechatService wechatService;



    private static ObjectMapper om = new ObjectMapper();

    private static final Log logs = LogFactory.getLog(WxService.class);

    // 创建支付的时候需要的config
    public Map<String, Object> doMakeConfig(Long accountId, String prepayId) throws Exception {

        WechatAccount wechatAccount = wechatAccountService.get(accountId);

        long timestamp = new Date().getTime() / 1000;
        String noncestr = UUIDUtil.getUUID();
        String paySign = getSign(prepayId, noncestr, timestamp, wechatAccount);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("timeStamp", timestamp);
        resultMap.put("nonceStr", noncestr);
        resultMap.put("paySign", paySign);
        resultMap.put("appId", wechatAccount.getAppId());

        return resultMap;
    }
    // 创建支付的时候需要的config
    public Map<String, Object> doMakeAppConfig(Long accountId, String prepayId) throws Exception {

        WechatAccount wechatAccount = wechatAccountService.get(accountId);

        long timestamp = new Date().getTime() / 1000;
        String noncestr = UUIDUtil.getUUID();
        String paySign = getAppSign(prepayId, noncestr, timestamp, wechatAccount);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("timeStamp", timestamp);
        resultMap.put("nonceStr", noncestr);
        resultMap.put("paySign", paySign);
        resultMap.put("appId", wechatAccount.getAppId());
        resultMap.put("partnerid", wechatAccount.getMchId());

        return resultMap;
    }

    public String getAppSign(String prepayId, String nonceStr, long timeStamp, WechatAccount wechatAccount)
    {
        String param = "appId=" + wechatAccount.getAppId() +"&partnerid=" + wechatAccount.getMchId() +  "&nonceStr=" + nonceStr + "&package=Sign=WXPay" + "&timeStamp=" + timeStamp;
        String signTemp = param + "&key=" + wechatAccount.getMchKey();
        String sign = MD5.GetMD5Code(signTemp).toUpperCase();

        return sign;
    }

    // 生成微信分享时需要的配置
    public Map<String, Object> doShareConfig(Long accountId, String url) throws Exception {

        WechatAccount wechatAccount = wechatAccountService.get(accountId);

        Long timestamp = new Date().getTime() / 1000;
        String noncestr = UUIDUtil.getUUID();


        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("timeStamp", timestamp);
        resultMap.put("nonceStr", noncestr);
        resultMap.put("appId", wechatAccount.getAppId());

        String ticket = wechatService.doGetTicketBy(accountId);

        String signString = getShareSign(noncestr, timestamp, url, ticket);

        resultMap.put("signature", signString);

        return resultMap;
    }

    public String getSign(String prepayId, String nonceStr, long timeStamp, WechatAccount wechatAccount)
    {
        String param = "appId=" + wechatAccount.getAppId() + "&nonceStr=" + nonceStr + "&package=prepay_id=" + prepayId + "&signType=MD5" + "&timeStamp=" + timeStamp;
        String signTemp = param + "&key=" + wechatAccount.getMchKey();
        String sign = MD5.GetMD5Code(signTemp).toUpperCase();

        return sign;
    }

    public String getShareSign(String nonceStr, long timeStamp, String url, String ticket)
    {
        String param = "jsapi_ticket=" + ticket
                + "&noncestr=" + nonceStr
                + "&timestamp=" + timeStamp
                + "&url=" + url;
        SHA1 sha1 = new SHA1();
        String sign = SHA1.encode(param);
        return sign;
    }



    private String getSignstr(Map<String, Object> paramMap, WechatAccount wechatAccount)
    {
        StringBuffer signSb = new StringBuffer();
        for (Map.Entry<String, Object> entry : paramMap.entrySet())
        {
            signSb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        signSb.append("key=").append(wechatAccount.getMchKey());

        return DigestUtils.md5Hex(signSb.toString()).toUpperCase();
    }

    public String toXml(Map<String, String> paramMap)
    {
        StringBuffer xmlSb = new StringBuffer();
        xmlSb.append("<xml>");
        for (Map.Entry<String, String> entry : paramMap.entrySet())
        {
            xmlSb.append("<").append(entry.getKey()).append("><![CDATA[")
                    .append(entry.getValue()).append("]]>")
                    .append("</").append(entry.getKey()).append(">");
        }
        xmlSb.append("</xml>");

        return xmlSb.toString();
    }

    public String postForXML(String url, String xmlData) {
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
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}


















