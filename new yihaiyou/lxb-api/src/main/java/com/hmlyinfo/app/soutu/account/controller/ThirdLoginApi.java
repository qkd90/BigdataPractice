package com.hmlyinfo.app.soutu.account.controller;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.AccountService;
import com.hmlyinfo.app.soutu.account.service.OAuthServerService;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.base.properties.WeixinSettings;
import com.hmlyinfo.base.exception.BizLogicException;
import com.hmlyinfo.base.util.HttpClientUtils;
import com.qq.connect.QQConnectException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import weibo4j.model.WeiboException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequestMapping("/passport/third")
@Controller
public class ThirdLoginApi {

    private Logger logger = Logger.getLogger(ThirdLoginApi.class);

	@Autowired
	private OAuthServerService oauthService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private OAuthServerService service;
    @Autowired
    WeixinSettings weixinSettings;
    private static ObjectMapper om = new ObjectMapper();
	// 微信登录所需参数
	private static final String APPID = "wxeccc48d39a09696f";
	private static final String AppSecret = "90710982acbd6977d6d537cc4201b07d";
	// 微信授权登录所需参数
	private static final String H5_APPID = Config.get("H5_WEIXIN_APPID");
	private static final String H5_AppSecret = Config.get("H5_WEIXIN_AppSecret");

    private static final String QQ_REDIRECT_URL = "/oauth/qqcallback";

    @RequestMapping("/qq")
	public String qqLogin(HttpServletRequest request) throws QQConnectException
	{
		String client_id = request.getParameter("client_id");
		if (StringUtils.isNotBlank(client_id))
		{
			request.getSession().setAttribute("client_id", client_id);
		}

		return "redirect:" + new com.qq.connect.oauth.Oauth().getAuthorizeURL(request);
	}

	@RequestMapping("/weibo")
	public String weiboLogin(HttpServletRequest request) throws QQConnectException, WeiboException
	{
		String client_id = request.getParameter("client_id");
		if (StringUtils.isNotBlank(client_id))
		{
			request.getSession().setAttribute("client_id", client_id);
		}

		weibo4j.Oauth oauth = new weibo4j.Oauth();
		return "redirect:" + oauth.authorize("code",null,"all");
	}

    // 微信登录
    @RequestMapping("/weixin")
    public String weixinLogin(HttpServletRequest request) {
        String clientId = request.getParameter("client_id");
        if (StringUtils.isNotBlank(clientId)) {
            request.getSession().setAttribute("client_id", clientId);
        }
        String state = getState();

        return "redirect:" + weixinSettings.getLoginUrl() + "appid=" + weixinSettings.getAppId() + "&redirect_uri=" + weixinSettings.getLoginRedirectUrl() + "&state=" + state + "&response_type=code&scope=snsapi_login";
    }

    @RequestMapping("/weixincallback")
    public String weixinCallback(HttpServletRequest request) throws Exception {
        String clientId = getClientId(request);
        String redirectUri = oauthService.getClientById(clientId).getRedirectUri();

        Map<String, Object> bindMap = getWeiXinBindInfo(request);
        if (bindMap == null) {
            return "redirect:" + redirectUri;
        }
        User user = oauthService.getUserByOpenId(bindMap);

        String code = oauthService.createTmpCode(clientId, user.getId());
        redirectUri = redirectUri + "?code=" + code;

        return "redirect:" + redirectUri;
    }

    //获取通过微信登陆后需要绑定的信息
    private Map<String, Object> getWeiXinBindInfo(HttpServletRequest request)  {
        Map<String, Object> bindInfoMap = new HashMap<String, Object>();
        // 使用微信返回的code获取access_token和openid
        String code = request.getParameter("code");
        String tokenUrl = weixinSettings.getAccessTokenUrl() + "appid=" + weixinSettings.getAppId() + "&secret=" + weixinSettings.getAppSecret() + "&code=" + code + "&grant_type=authorization_code";
        String accessToken;
        String openID;

        try {
            String tokenStr = HttpClientUtils.getHttps(tokenUrl);
            Map<String, String> tokenMap = om.readValue(tokenStr, Map.class);
            accessToken = tokenMap.get("access_token");
            openID = tokenMap.get("openid");
        } catch (Exception e) {
            logger.error("获取access token失败", e);
            return null;
        }

        // 使用access_token和openid获取用户信息
        String userUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openID + "&lang=zh_CN";
        try {
            String userStr = HttpClientUtils.getHttps(userUrl);
            Map<String, Object> userMap = om.readValue(userStr, Map.class);
            bindInfoMap.put("openId", userMap.get("openid"));
            bindInfoMap.put("bindType", "3");
            bindInfoMap.put("userNickName", userMap.get("nickname"));
            bindInfoMap.put("userFacePath", userMap.get("headimgurl"));
        } catch (Exception e) {
            logger.error("获取user info失败", e);
            return null;
        }
        return bindInfoMap;
    }

	// H5微信授权
	@RequestMapping("/H5/login")
	public String H5Login(HttpServletRequest request, HttpServletResponse response)
	{
		String state = getState();
		request.getSession().setAttribute("h5state", state);
		String client_id = request.getParameter("client_id");
		String codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + H5_APPID + "&redirect_uri=http://passport.lvxbang.com/third/h5weixincallback?client_id=" + client_id + "&response_type=code&scope=snsapi_userinfo&state=" + state + "#wechat_redirect";

		return "redirect:" + codeUrl;
	}

    @RequestMapping("/qqcallback")
    public String qqCallback(HttpServletRequest request) throws Exception {

        String clientId = getClientId(request);
        String redirectUri = oauthService.getClientById(clientId).getRedirectUri();

        Map<String, Object> bindMap = getQQBindInfo(request);
        User user = oauthService.getUserByOpenId(bindMap);
        String code = oauthService.createTmpCode(clientId, user.getId());
        redirectUri = redirectUri + "?code=" + code + "&redirect_uri=" + QQ_REDIRECT_URL;

        return "redirect:" + redirectUri;
    }

    @RequestMapping("/weibocallback")
	public String weiboCallback(HttpServletRequest request) throws Exception
	{
		String clientId = getClientId(request);
		String redirectUri = oauthService.getClientById(clientId).getRedirectUri();

		Map<String, Object> bindMap = getSinaWeiboBindInfo(request);
		User user = oauthService.getUserByOpenId(bindMap);

		String code = oauthService.createTmpCode(clientId, user.getId());
		redirectUri = redirectUri + "?code=" + code;

		return "redirect:" + redirectUri;
	}

	@RequestMapping("/h5weixincallback")
	public String h5weixinCallback(HttpServletRequest request, String client_id) throws Exception
	{
		if (client_id == null)
		{
			client_id = Config.get("DEFAULT_H5_CLIENT_ID");
		}
		String redirectUri = oauthService.getClientById(client_id).getRedirectUri();

		Map<String, Object> bindMap = getH5WeiXinBindInfo(request);
		User user = oauthService.getUserByOpenId(bindMap);

		String code = oauthService.createTmpCode(client_id, user.getId());
		redirectUri = redirectUri + "?code=" + code;

		return "redirect:" + redirectUri;
	}


	private String getClientId(HttpServletRequest request)
	{
		String client_id = (String)request.getSession().getAttribute("client_id");
		if (StringUtils.isBlank(client_id))
		{
			client_id = Config.get("DEFAULT_CLIENT_ID");
		}

		return client_id;
	}

	private String getState()
	{
		Random random = new Random();
		String state = Integer.toHexString(random.nextInt()) + Integer.toHexString(random.nextInt());

		return state;
	}

	//获取通过qq登陆后需要绑定的信息
	private Map<String, Object> getQQBindInfo(HttpServletRequest request) throws Exception
	{
		Map<String, Object> bindInfoMap = new HashMap<String, Object>();

		String accessToken = null;
		String openID = null;

		com.qq.connect.javabeans.AccessToken accessTokenObj = (new com.qq.connect.oauth.Oauth()).getAccessTokenByRequest(request);
		if (accessTokenObj.getAccessToken().equals("")) {
			// 我们的网站被CSRF攻击了或者用户取消了授权
			// 做一些数据统计工作
			throw new BizLogicException("没有获取到响应参数");
		}
		else {
			accessToken = accessTokenObj.getAccessToken();

			// 利用获取到的accessToken 去获取当前用的openid
			com.qq.connect.api.OpenID openIDObj = new com.qq.connect.api.OpenID(accessToken);
			openID = openIDObj.getUserOpenID();

			com.qq.connect.api.qzone.UserInfo qzoneUserInfo = new com.qq.connect.api.qzone.UserInfo(accessToken, openID);
			com.qq.connect.javabeans.qzone.UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();

			if (userInfoBean.getRet() == 0) {
				bindInfoMap.put("openId", openID);
				bindInfoMap.put("bindType", "1");
				bindInfoMap.put("userNickName", userInfoBean.getNickname());
				bindInfoMap.put("userFacePath", userInfoBean.getAvatar().getAvatarURL100());
			} else {
				throw new BizLogicException("没有获取到用户信息，原因是:" + userInfoBean.getMsg());
			}
		}

		return bindInfoMap;
	}

	//获取通过新浪微博登陆后需要绑定的信息
	private Map<String, Object> getSinaWeiboBindInfo(HttpServletRequest request) throws Exception
	{
		Map<String, Object> bindInfoMap = new HashMap<String, Object>();

		String accessToken = null;
		String openID = null;

		String code = request.getParameter("code");
		weibo4j.http.AccessToken accessTokenObj = (new weibo4j.Oauth()).getAccessTokenByCode(code);
		if (accessTokenObj.getAccessToken().equals(""))
		{
			// 我们的网站被CSRF攻击了或者用户取消了授权
			// 做一些数据统计工作
			throw new BizLogicException("没有获取到响应参数");
		}
		else
		{
			accessToken = accessTokenObj.getAccessToken();

			//授权后获取uid
			weibo4j.Account account = new  weibo4j.Account();
            account.client.setToken(accessToken);
			weibo4j.org.json.JSONObject uidJson = account.getUid();
    		String uid = uidJson.getString("uid");

    		//根据uid获取用户信息
			weibo4j.Users users = new weibo4j.Users();
			users.client.setToken(accessToken);
            weibo4j.model.User user = users.showUserById(uid);

            if (user != null)
            {
            	bindInfoMap.put("openId", user.getId());
				bindInfoMap.put("bindType", "2");
				bindInfoMap.put("userNickName", user.getScreenName());
				bindInfoMap.put("userFacePath", user.getProfileImageUrl());
			}
		}

		return bindInfoMap;
	}

	//获取通过H5微信登陆后需要绑定的信息
	private Map<String, Object> getH5WeiXinBindInfo(HttpServletRequest request) throws Exception
	{
		Map<String, Object> bindInfoMap = new HashMap<String, Object>();

		String accessToken = null;
		String openID = null;
		String state = request.getParameter("h5state");
		request.getSession().setAttribute("h5state", "");
		// 使用微信返回的code获取access_token和openid
		String code = request.getParameter("code");
		String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + H5_APPID + "&secret=" + H5_AppSecret +"&code=" + code +"&grant_type=authorization_code";
		String tokenStr = HttpClientUtils.getHttps(tokenUrl);
		Map<String, String> tokenMap = om.readValue(tokenStr, Map.class);
		accessToken = tokenMap.get("access_token");
		openID = tokenMap.get("openid");

		// 使用access_token和openid获取用户信息
		String userUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openID + "&lang=zh_CN";
		String userStr = HttpClientUtils.getHttps(userUrl);
		Map<String, Object> userMap = om.readValue(userStr, Map.class);
		bindInfoMap.put("openId", userMap.get("openid"));
		bindInfoMap.put("bindType", "3");
		bindInfoMap.put("userNickName", userMap.get("nickname"));
		bindInfoMap.put("userFacePath", userMap.get("headimgurl"));

		return bindInfoMap;
	}

}
