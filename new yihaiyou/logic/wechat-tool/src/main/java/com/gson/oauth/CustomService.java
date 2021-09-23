package com.gson.oauth;

import com.alibaba.fastjson.JSON;
import com.gson.util.HttpKit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by caiys on 2016/11/7.
 */
public class CustomService {
    private final Log log = LogFactory.getLog(CustomService.class);

    /**
     * 客服接口-发消息（给普通用户）
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public boolean customSend(String accessToken, String params) throws InterruptedException, ExecutionException, IOException {
        String jsonStr = HttpKit.post("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken, params);
        log.info(jsonStr);
        Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
        return "0".equals(map.get("errcode").toString());
    }

    /**
     * 获取在线状态客服，返回一个完整客服帐号，格式为：帐号前缀@公众号微信号
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String getOnlineKf(String accessToken) throws InterruptedException, ExecutionException, NoSuchAlgorithmException, KeyManagementException, IOException, NoSuchProviderException {
        String jsonStr = HttpKit.get("https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=" + accessToken);
        log.info("onlinekflist = " + jsonStr);
        JSONObject resultJson = JSONObject.fromObject(jsonStr);
        JSONArray onlineKfList = resultJson.getJSONArray("kf_online_list");
        if (onlineKfList != null && onlineKfList.size() > 0) {
            Iterator<Object> its = onlineKfList.iterator();
            while (its.hasNext()) {
                JSONObject onlineKf = (JSONObject) its.next();
                // 目前“将消息转发到客服”接口有问题，取的不是帐号前缀@公众号微信号，而是kf_id+@公众号微信号
                Integer kfId = (Integer) onlineKf.get("kf_id");
                String kfAccount = (String) onlineKf.get("kf_account");
                return kfId + kfAccount.substring(kfAccount.indexOf("@"));
            }
        }
        return null;
    }

}
