package com.data.data.hmly.service.lvxbang.util;

import com.data.data.hmly.enums.ThirdPartyUserType;
import com.data.data.hmly.service.common.util.HttpClientUtils;
import com.data.data.hmly.service.user.exception.ThirdPartyUserException;
import com.data.data.hmly.service.user.vo.OpenLoginInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qq.connect.javabeans.AccessToken;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Jonathan.Guo
 */
public class ThirdPartyLoginUtil {
    private static final Logger LOGGER = Logger.getLogger(ThirdPartyLoginUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    //获取通过微信登陆后需要绑定的信息
    public static OpenLoginInfo getWeiXinBindInfo(String code) {
        // 使用微信返回的code获取access_token和openid
        String tokenUrl = WeixinSettings.getTokenUrl() + "&code=" + code;
        String accessToken;
        String openId;

        try {
            String tokenStr = HttpClientUtils.getHttps(tokenUrl);
            Map<String, String> tokenMap = mapper.readValue(tokenStr, Map.class);
            accessToken = tokenMap.get("access_token");
            openId = tokenMap.get("openid");
        } catch (Exception e) {
            LOGGER.error("获取access token失败", e);
            return null;
        }

        // 使用access_token和openid获取用户信息
        String userUrl = WeixinSettings.USER_INFO_URL + "access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        try {
            String userStr = HttpClientUtils.getHttps(userUrl);
            Map<String, Object> userMap = mapper.readValue(userStr, Map.class);
            OpenLoginInfo info = new OpenLoginInfo();
            info.openId = userMap.get("openid").toString();
            info.type = ThirdPartyUserType.weixin;
            info.nickName = userMap.get("nickname").toString();
            info.headPath = userMap.get("headimgurl").toString();
            return info;
        } catch (Exception e) {
            LOGGER.error("获取user info失败", e);
            return null;
        }
    }

    //获取通过qq登陆后需要绑定的信息
    public static OpenLoginInfo getQQBindInfo(HttpServletRequest request) throws Exception {

        AccessToken accessTokenObj = new QQOauthUtil().getAccessTokenByRequest(request);
        if ("".equals(accessTokenObj.getAccessToken())) {
            // 我们的网站被CSRF攻击了或者用户取消了授权
            // 做一些数据统计工作
            throw new ThirdPartyUserException(ThirdPartyUserException.ERROR_NO_INFO, "没有获取到响应参数");
        } else {
            String accessToken = accessTokenObj.getAccessToken();

            // 利用获取到的accessToken 去获取当前用的openid
            com.qq.connect.api.OpenID openIDObj = new com.qq.connect.api.OpenID(accessToken);
            String openID = openIDObj.getUserOpenID();

            com.qq.connect.api.qzone.UserInfo qzoneUserInfo = new com.qq.connect.api.qzone.UserInfo(accessToken, openID);
            com.qq.connect.javabeans.qzone.UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();

            if (userInfoBean.getRet() == 0) {
                OpenLoginInfo info = new OpenLoginInfo();
                info.openId = openID;
                info.type = ThirdPartyUserType.qq;
                info.nickName = userInfoBean.getNickname();
                info.headPath = userInfoBean.getAvatar().getAvatarURL100();
                return info;
            } else {
                throw new ThirdPartyUserException(ThirdPartyUserException.ERROR_NO_INFO, "没有获取到用户信息，原因是:" + userInfoBean.getMsg());
            }

        }
    }


    //获取通过新浪微博登陆后需要绑定的信息
    public static OpenLoginInfo getSinaWeiboBindInfo(HttpServletRequest request) throws Exception {

        String code = request.getParameter("code");
        weibo4j.http.AccessToken accessTokenObj = new WeiboAccessTokenUtil().getAccessTokenByCode(code, request);
        if ("".equals(accessTokenObj.getAccessToken())) {
            // 我们的网站被CSRF攻击了或者用户取消了授权
            // 做一些数据统计工作
            throw new ThirdPartyUserException(ThirdPartyUserException.ERROR_NO_INFO, "没有获取到响应参数");
        } else {
            String accessToken = accessTokenObj.getAccessToken();

            //授权后获取uid
            weibo4j.Account account = new weibo4j.Account();
            account.client.setToken(accessToken);
            weibo4j.org.json.JSONObject uidJson = account.getUid();
            String uid = uidJson.getString("uid");

            //根据uid获取用户信息
            weibo4j.Users users = new weibo4j.Users();
            users.client.setToken(accessToken);
            weibo4j.model.User user = users.showUserById(uid);

            if (user != null) {
                OpenLoginInfo info = new OpenLoginInfo();
                info.openId = user.getId();
                info.type = ThirdPartyUserType.weibo;
                info.nickName = user.getScreenName();
                info.headPath = user.getProfileImageUrl();
                return info;
            } else {
                throw new ThirdPartyUserException(ThirdPartyUserException.ERROR_NO_INFO, "没有获取到用户信息");
            }
        }
    }


}
