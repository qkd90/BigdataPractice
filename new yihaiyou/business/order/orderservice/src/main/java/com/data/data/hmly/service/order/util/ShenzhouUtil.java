package com.data.data.hmly.service.order.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.zuipin.util.DateUtils;
import com.zuipin.util.HttpUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-12-30,0030.
 */
public class ShenzhouUtil {
    private static PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String shenzhouOauth;
    public static String shenzhouApi;
    public static String shenzhouAuthorize;
    private static String clientId;
    private static String clientSecret;
    private static String redirectUrl;
    public static String companyId;
    private static Map<String, Object> params = Maps.newHashMap();
    private static Map<String, Object> clientParams = Maps.newHashMap();

    static {
        shenzhouOauth = propertiesManager.getString("shenzhouOauth");
        shenzhouApi = propertiesManager.getString("shenzhouApi");
        clientId = propertiesManager.getString("clientId");
        clientSecret = propertiesManager.getString("clientSecret");
        redirectUrl = propertiesManager.getString("redirectUrl");
        companyId = propertiesManager.getString("companyId");
        shenzhouAuthorize = shenzhouOauth + ShenzhouUrl.AUTHORIZE + "?client_id=" + clientId + "&redirect_uri=" + redirectUrl + "&response_type=code&scope=read";
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        clientParams.put("client_id", clientId);
        clientParams.put("client_secret", clientSecret);
        clientParams.put("grant_type", "client_credentials");
    }

    /**
     * 客户端授权
     *
     * @return
     */
    public static Map<String, Object> clientToken() {
        Map<String, Object> result = Maps.newHashMap();
        try {
            Map<String, Object> resultMap = mapper.readValue(HttpUtils.post(shenzhouOauth + ShenzhouUrl.TOKEN, clientParams), Map.class);
            result.put("accessToken", resultMap.get("access_token").toString());
            result.put("tokenDate", DateUtils.add(new Date(), Calendar.SECOND, Integer.valueOf(resultMap.get("expires_in").toString())));
            result.put("success", true);
        } catch (Exception e) {
            result.clear();
            result.put("success", false);
        }
        return result;
    }

    /**
     * 添加员工
     *
     * @param accessToken
     * @param telephone
     * @return
     */
    public static Map<String, Object> addEmployee(String accessToken, String telephone) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> addParams = Maps.newHashMap();
        addParams.put("access_token", accessToken);
        addParams.put("companyId", companyId);
        addParams.put("mobile", telephone);
        try {
            Map<String, Object> addResult = mapper.readValue(HttpUtils.post(shenzhouApi + ShenzhouUrl.ADD_EMPLOYEE, addParams), Map.class);
            if (addResult.get("status").toString().equals("SUCCESS")) {
                result.put("success", true);
            } else {
                result.put("success", false);
            }
        } catch (Exception e) {
            result.clear();
            result.put("success", false);
        }
        return result;
    }

    /**
     * 用户授权
     *
     * @param code
     * @return
     */
    public static Map<String, Object> userToken(String code) {
        Map<String, Object> result = Maps.newHashMap();
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        params.put("redirect_uri", redirectUrl);
        try {
            Map<String, Object> resultMap = mapper.readValue(HttpUtils.post(shenzhouOauth + ShenzhouUrl.TOKEN, params), Map.class);
            result.put("accessToken", resultMap.get("access_token").toString());
            result.put("refreshToken", resultMap.get("refresh_token").toString());
            result.put("tokenDate", DateUtils.add(new Date(), Calendar.SECOND, Integer.valueOf(resultMap.get("expires_in").toString())));
            result.put("success", true);
        } catch (Exception e) {
            result.clear();
            result.put("success", false);
        }
        return result;
    }

    /**
     * 更新授权码
     *
     * @param refreshToken
     * @return
     */
    public static Map<String, Object> refreshToken(String refreshToken) {
        Map<String, Object> result = Maps.newHashMap();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        try {
            Map<String, Object> resultMap = mapper.readValue(HttpUtils.post(shenzhouOauth + ShenzhouUrl.TOKEN, params), Map.class);
            result.put("accessToken", resultMap.get("access_token").toString());
            result.put("refreshToken", resultMap.get("refresh_token").toString());
            result.put("tokenDate", DateUtils.add(new Date(), Calendar.SECOND, Integer.valueOf(resultMap.get("expires_in").toString())));
            result.put("success", true);
        } catch (Exception e) {
            result.clear();
            result.put("success", false);
        }
        return result;
    }
}
