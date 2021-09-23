/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2014 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson.oauth;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gson.bean.UserInfo;
import com.gson.util.EmojiFilterUtil;
import com.gson.util.HttpKit;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户操作接口
 *
 * @author ____′↘夏悸
 */
public class User {

    private static final String USER_INFO_URI = "https://api.weixin.qq.com/cgi-bin/user/info";
    private static final String USER_POST_INFO_URI = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=";
    private static final String USER_GET_URI = "https://api.weixin.qq.com/cgi-bin/user/get";

    /**
     * 拉取用户信息
     *
     * @param accessToken
     * @param openid
     * @return
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static UserInfo getUserInfo(String accessToken, String openid) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);
        params.put("openid", openid);
        int i = 0;
        do {
            String jsonStr = HttpKit.get(USER_INFO_URI, params);
            if (StringUtils.isNotEmpty(jsonStr)) {
                JSONObject obj = JSONObject.parseObject(jsonStr);
                if (obj.get("errcode") != null) {
                    throw new Exception(obj.getString("errmsg"));
                }
                UserInfo user = JSONObject.toJavaObject(obj, UserInfo.class);
                return user;
            }
        } while (i < 3);
        return null;
    }

    /**
     * post批量获取用户信息
     *
     * @param accessToken 公众号token
     * @param openid      批量查询的用户openid
     * @return 查询结果的用户信息类列表
     * @throws Exception
     * @author huangpeijie
     * @date 2015年12月8日11:32:51
     */
    public List<UserInfo> getUserInfoList(String accessToken, List<String> openid) throws Exception {
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject param = new JSONObject();
        for (String id : openid) {
            JSONObject json = new JSONObject();
            json.put("openid", id);
            jsonList.add(json);
        }
        param.put("user_list", jsonList);
        String result = HttpKit.post(USER_POST_INFO_URI + accessToken, param.toJSONString());
        if (StringUtils.isNotEmpty(result)) {
            JSONObject resultJson = JSONObject.parseObject(result);
            if (resultJson.get("errcode") != null) {
                throw new Exception(resultJson.getString("errmsg"));
            }
            List<UserInfo> list = JSONArray.parseArray(resultJson.getJSONArray("user_info_list").toJSONString(), UserInfo.class);
            for (UserInfo info : list) {
                if (info.getSubscribe() != 0) {
                    //\xF0\x9F\x99\x88
                    info.setNickname(EmojiFilterUtil.filterEmoji(info.getNickname()));
                    info.setCity(EmojiFilterUtil.filterEmoji(info.getCity()));
                    info.setProvince(EmojiFilterUtil.filterEmoji(info.getProvince()));
                    info.setCountry(EmojiFilterUtil.filterEmoji(info.getCountry()));
                    userInfoList.add(info);
                }
            }
            return userInfoList;
        }
        return null;
    }

    /**
     * 获取帐号的关注者列表
     *
     * @param accessToken
     * @param next_openid
     * @return
     */
    public JSONObject getFollwersList(String accessToken, String next_openid) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);
        params.put("next_openid", next_openid);
        String jsonStr = HttpKit.get(USER_GET_URI, params);
        if (StringUtils.isNotEmpty(jsonStr)) {
            JSONObject obj = JSONObject.parseObject(jsonStr);
            if (obj.get("errcode") != null) {
                throw new Exception(obj.getString("errmsg"));
            }
            return obj;
        }
        return null;
    }

}