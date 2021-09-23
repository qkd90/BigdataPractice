package com.data.data.hmly.service.lvxbang.util;

import com.data.data.hmly.service.common.util.PropertiesUtils;

import javax.servlet.ServletRequest;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Created by guoshijie on 2015/6/24.
 */
public class WeixinSettings {

    private static final Properties PROPERTIES = PropertiesUtils.getProperties("weixin.properties");

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static final String APP_ID = PROPERTIES.getProperty("app.id");
    public static final String APP_SECRET = PROPERTIES.getProperty("app.secret");
    public static final String LOGIN_URL = PROPERTIES.getProperty("weixin.login");
    public static final String LOGIN_REDIRECT_URL = PROPERTIES.getProperty("weixin.login.redirect");
    public static final String ACCESS_TOKEN_URL = PROPERTIES.getProperty("weixin.access.token.url");
    public static final String USER_INFO_URL = PROPERTIES.getProperty("weixin.info.url");

    public static final Long LOGIN_ACCOUNT_ID = Long.valueOf(PROPERTIES.getProperty("weixin.login.account.id"));

    public static String getTokenUrl() {
        return ACCESS_TOKEN_URL + "appid=" + APP_ID + "&secret=" + APP_SECRET + "&grant_type=authorization_code";
    }

    public static String getLoginUrl(ServletRequest request) {
        String redirectUri = RedirectUriUtil.getRedirectUri(request, LOGIN_REDIRECT_URL);
        // 为了支持跨域,增加realDomain来存放currentDomain
        // 一旦授权域的域名和传过来的realDomain不一致,就会将code传给realDomain域
        String currentDomain = request.getScheme() + "://" + request.getServerName();
        if (!redirectUri.startsWith(currentDomain)) {
            redirectUri += "?realDomain=" + currentDomain;
        }
        return LOGIN_URL + "appid=" + APP_ID + "&redirect_uri=" + URLEncoder.encode(redirectUri) + "&response_type=code&scope=snsapi_login";
    }

}
