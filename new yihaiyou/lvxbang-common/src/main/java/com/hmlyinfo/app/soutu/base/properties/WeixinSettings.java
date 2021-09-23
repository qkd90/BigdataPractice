package com.hmlyinfo.app.soutu.base.properties;

import com.hmlyinfo.app.soutu.base.PropertiesUtils;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by guoshijie on 2015/6/24.
 */
@Service
public class WeixinSettings {

	private static final Properties PROPERTIES = PropertiesUtils.getProperties("weixin.properties");

	public static String get(String key) {
		return PROPERTIES.getProperty(key);
	}

	private String appId = PROPERTIES.getProperty("app.id");
	private String appSecret = PROPERTIES.getProperty("app.secret");
	private String loginUrl = PROPERTIES.getProperty("weixin.login");
	private String loginRedirectUrl = PROPERTIES.getProperty("weixin.login.redirect");
	private String accessTokenUrl = PROPERTIES.getProperty("weixin.access.token.url");
	private String userInfoUrl = PROPERTIES.getProperty("weixin.info.url");

	public String getAppId() {
		return appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public String getLoginRedirectUrl() {
		return loginRedirectUrl;
	}

	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}

	public String getUserInfoUrl() {
		return userInfoUrl;
	}
}
