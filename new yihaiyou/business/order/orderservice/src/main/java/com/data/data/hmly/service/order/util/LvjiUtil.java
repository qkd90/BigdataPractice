package com.data.data.hmly.service.order.util;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.common.util.HttpClientUtils;
import com.data.data.hmly.service.ctripcommon.entity.CtripApiLog;
import com.data.data.hmly.service.order.entity.LvjiOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.zuipin.util.HttpUtils;
import com.zuipin.util.MD5;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kok on 2017/8/18.
 */
public class LvjiUtil {

    private static PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    public static final String TAKE_ORDER_URL = "http://api.365daoyou.cn";

    /**
     * 下单接口
     * @param order
     * @return
     */
    public static Map<String, Object> saveOrder(LvjiOrder order) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newTreeMap();
        ObjectMapper mapper = new ObjectMapper();
        params.put("tplId", order.getTplId());
        params.put("price", order.getPrice());
        params.put("thirdOrderNo", order.getOrderNo());
        params.put("phone", order.getMobile());
        params.put("userName", order.getUserName());
        params.put("visitDate", DateUtils.formatShortDate(order.getStartDate()));
        params.put("macAddr", order.getMacAddr());
        params.put("isEmbedded", "1");
        params.put("reqTime", DateUtils.format(new Date(), "yyyyMMddHHmmss"));
        String key = "";
        for (Map.Entry<String, Object> param : params.entrySet()) {
            key += param.getKey() + "=" + param.getValue() + "&";
        }
        key += "k=lvji456ht";
        String sign = MD5Util.MD5Encode(key, "utf-8");
        params.put("sign", sign);

        String resultStr = HttpUtils.post(TAKE_ORDER_URL + "/code", params);
        try {
            result = mapper.readValue(resultStr, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 退款接口
     * @param order
     * @return
     */
    public static Map<String, Object> refund(LvjiOrder order) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> params = Maps.newTreeMap();
        ObjectMapper mapper = new ObjectMapper();
        params.put("orderNo", order.getLvjiOrderNo());
        params.put("reqTime", DateUtils.format(new Date(), "yyyyMMddHHmmss"));
        String key = "";
        for (Map.Entry<String, Object> param : params.entrySet()) {
            key += param.getKey() + "=" + param.getValue() + "&";
        }
        key += "k=lvji456ht";
        String sign = MD5Util.MD5Encode(key, "utf-8");
        params.put("sign", sign);

        String resultStr = HttpUtils.post(TAKE_ORDER_URL + "/refund", params);
        try {
            result = mapper.readValue(resultStr, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
