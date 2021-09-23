package com.data.spider.service.mfw;

import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Sane on 15/10/21.
 */
public class MfwOAuthService {

    private static String encoding = "UTF-8";
    private static String OAUTH_CONSUMER_KEY = "5";
    private static String CONSUMER_SECRET = "3cd30ff35d0b1a5b0b303407de599ca0";
    private static String OAUTH_TOKEN = "0_0969044fd4edf59957f4a39bce9200c6";
    private static String OAUTH_TOKEN_SECRET = "b51c30e9ec2b3beb549cbb2f6e766abd";


    public static String getRequestUrl(String baseUrl, String method,
                                       Map<String, String> params) {
        try {
            fillUpParams(params);
            String queryParse = getQueryParse(params);
            String oauthSignature = getOauthSignature(baseUrl, method, queryParse);
            return baseUrl + "?" + queryParse + "&oauth_signature="
                    + URLEncoder.encode(oauthSignature, encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void fillUpParams(Map<String, String> params) {
        if (!params.containsKey("device_type"))
            params.put("device_type", "android");
        if (!params.containsKey("mfwsdk_ver"))
            params.put("mfwsdk_ver", "20140507");
        if (!params.containsKey("screen_width"))
            params.put("screen_width", "1080");
        if (!params.containsKey("device_id"))
            params.put("device_id", "08:00:27:af:32:f2");
        if (!params.containsKey("sys_ver"))
            params.put("sys_ver", "4.2.2");
        if (!params.containsKey("channel_id"))
            params.put("channel_id", "JiFeng");
        if (!params.containsKey("x_auth_mode"))
            params.put("x_auth_mode", "client_auth");
        if (!params.containsKey("oauth_signature_method"))
            params.put("oauth_signature_method", "HMAC-SHA1");
        if (!params.containsKey("oauth_token"))
            params.put("oauth_token", OAUTH_TOKEN);
        if (!params.containsKey("open_udid"))
            params.put("open_udid", "08:00:27:af:32:f2");
        if (!params.containsKey("app_ver"))
            params.put("app_ver", "5.3.3");
        if (!params.containsKey("app_code"))
            params.put("app_code", "com.mfw.roadbook");
        if (!params.containsKey("oauth_timestamp"))
            params.put("oauth_timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        if (!params.containsKey("screen_height"))
            params.put("screen_height", "1776");
        if (!params.containsKey("oauth_version"))
            params.put("oauth_version", "1.0");
        if (!params.containsKey("oauth_nonce"))
            params.put("oauth_nonce", "61321d67-3c03-4ee8-acb4-96f7fdd162b0");
        if (!params.containsKey("oauth_consumer_key"))
            params.put("oauth_consumer_key", OAUTH_CONSUMER_KEY);
    }

    public static String getOauthSignature(String baseUrl, String method,
                                           Map<String, String> parse) {
        try {
            String queryParse = getQueryParse(parse);
            return getOauthSignature(baseUrl, method, queryParse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getOauthSignature(String baseUrl, String method,
                                           String quaryParse) throws Exception {
        String bss = method + "&" + URLEncoder.encode(baseUrl, encoding) + "&";
        String bsss = URLEncoder.encode(quaryParse, encoding);
        String urlString = bss + bsss;
        String oauthKey = URLEncoder.encode(CONSUMER_SECRET, encoding) + "&"
                + URLEncoder.encode(OAUTH_TOKEN_SECRET, encoding);
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec spec = new SecretKeySpec(oauthKey.getBytes(),
                "HmacSHA1");
        mac.init(spec);
        byte[] byteHMAC = mac.doFinal((urlString).getBytes());
        String oauthSignature = new BASE64Encoder().encode(byteHMAC);
        return oauthSignature;
    }

    private static String getQueryParse(Map<String, String> parse) throws Exception {
        Object[] parseKeys = parse.keySet().toArray();
        Arrays.sort(parseKeys);
        StringBuffer urlBuffer = new StringBuffer();
        for (int i = 0; i < parseKeys.length; i++) {
            if (i > 0) {
                urlBuffer.append("&");
            }
            urlBuffer.append(parseKeys[i] + "="
                    + URLEncoder.encode(parse.get(parseKeys[i]), encoding));
        }
        return urlBuffer.toString();
    }
}
