package com.data.data.hmly.service.zmyproduct.entity;

import com.alibaba.fastjson.JSONObject;
import com.data.data.hmly.service.zmyproduct.utils.ZmyouApiUtils;
import com.zuipin.util.MD5;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2016/5/9.
 */
public class OrderResult {

    private String version = "1.0";
    private String timestamp = new Date().getTime() + "";
    private String key = "1462775788972";
    private String token;
    private String secret = "fe89bbd72d89978e0e43da0535d452ea";

    public Map<String, Object> fmtHead(String encodeStr) {

        Map<String, Object> headMap = new HashMap<String, Object>();
        headMap.put("version", version);
        headMap.put("timestamp", timestamp);
        headMap.put("key", key);
        headMap.put("token", fmtToken(encodeStr));

        return headMap;
    }

    public Map<String, Object> fmtResult(String encodeStr) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
//        resultMap.put("version", version);
//        resultMap.put("timestamp", timestamp);
//        resultMap.put("key", key);
//        resultMap.put("token", fmtToken(encodeStr));
        resultMap.put("head", fmtHead(encodeStr));
        resultMap.put("data", encodeStr);

        data.put("data", ZmyouApiUtils.encode(JSONObject.toJSONString(resultMap)));

        return data;
    }

    public String fmtToken(String encodeStr) {
//    其中token=版本号【version】+时间戳【timestamp】+应用密钥【secret】+消息体【data】，取MD5值，
        token = MD5.Encode(version + timestamp + secret + encodeStr);
        return token;
    }

}
