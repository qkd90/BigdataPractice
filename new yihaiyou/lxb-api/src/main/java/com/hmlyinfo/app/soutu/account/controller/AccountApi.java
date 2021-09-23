package com.hmlyinfo.app.soutu.account.controller;

import com.hmlyinfo.app.soutu.account.domain.AccessToken;
import com.hmlyinfo.app.soutu.account.domain.OAuthClient;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.AccountService;
import com.hmlyinfo.app.soutu.account.service.OAuthServerService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpClientUtils;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/trust")
public class AccountApi 
{
	private static final Logger logger = Logger.getLogger(AccountApi.class);
	@Autowired
	private AccountService accountService;
	@Autowired
	private OAuthServerService service;
	@Autowired
	private UserService userService;
	@Autowired
	private OAuthServerService oauthService;
	
	private ObjectMapper om = new ObjectMapper();
	
	private static final Log logs = LogFactory.getLog(AccountApi.class);
	
	// 微信授权登录所需参数
	private static final String H5_APPID = Config.get("H5_WEIXIN_APPID");
	private static final String H5_AppSecret = Config.get("H5_WEIXIN_AppSecret");
	
	@RequestMapping("/login")
	public @ResponseBody ActionResult login(String username, String password, String code, String client_id, String client_secret, int days)
	{
		Validate.notNull(username, ErrorCode.ERROR_50001, "用户名不能为空");
		Validate.notNull(password, ErrorCode.ERROR_50001, "密码不能为空");
		Validate.notNull(client_id, ErrorCode.ERROR_50001, "非法的客户端");
		Validate.notNull(client_secret, ErrorCode.ERROR_50001, "非法的客户端");
		
		// 当登陆失败次数超过5次以后
		// Validate.notNull(code, ErrorCode.ERROR_50001);
		User user = accountService.login(client_id, username, password);
		
		return simulateOAuthLogin(user, client_id, client_secret, days);
	}
	
	@RequestMapping("/register")
	public @ResponseBody ActionResult register(String username, String password, String validatePassword, String client_id, String client_secret)
	{
		Validate.notNull(username, ErrorCode.ERROR_50001, "用户名不能为空");
		Validate.notNull(password, ErrorCode.ERROR_50001, "密码不能为空");
		Validate.notNull(validatePassword, ErrorCode.ERROR_50001, "验证密码不能为空");
		Validate.isTrue(password.equals(validatePassword), ErrorCode.ERROR_50001, "两次输入的密码必须相同");
		Validate.notNull(client_id, ErrorCode.ERROR_50001, "非法的客户端");
		Validate.notNull(client_secret, ErrorCode.ERROR_50001, "非法的客户端");
		
		//验证邮箱格式
		String patternE = "^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
		Pattern pe = Pattern.compile(patternE);
		Validate.isTrue(pe.matcher(username).matches(), ErrorCode.ERROR_51001, "请输入正确的邮箱");
		
		// 注册
		String userId = service.register(username, password, false);
		
		// 用户激活
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("status", "1");
		userService.updateUserStatus(paramMap);
		
		// 登陆
		return login(username, password, "", client_id, client_secret, 1);
	}
	
	@RequestMapping("/thridLogin")
	public @ResponseBody ActionResult thridLogin(String openId, String bindType, String userNickName, String userFacePath, String client_id, String client_secret)
	{
		Validate.notNull(client_id, ErrorCode.ERROR_50001, "非法的客户端");
		Validate.notNull(client_secret, ErrorCode.ERROR_50001, "非法的客户端");
		
		Validate.notNull(openId, ErrorCode.ERROR_50001);
		Validate.notNull(bindType, ErrorCode.ERROR_50001);
		Validate.notNull(userNickName, ErrorCode.ERROR_50001);
		Validate.notNull(userFacePath, ErrorCode.ERROR_50001);
		
		Map<String, Object> bindInfoMap = new HashMap<String, Object>();
		bindInfoMap.put("openId", openId);
		bindInfoMap.put("bindType", "1");
		bindInfoMap.put("userNickName", userNickName);
		bindInfoMap.put("userFacePath", userFacePath);
		
		User user = oauthService.getUserByOpenId(bindInfoMap);
		
		// 用户激活
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", user.getId());
		paramMap.put("status", "1");
		userService.updateUserStatus(paramMap);
		
		return simulateOAuthLogin(user, client_id, client_secret, 30);
	}
	
	@RequestMapping("/getClientById")
	public @ResponseBody ActionResult getClientById(String clientId)
	{
		OAuthClient client = service.getClientById(clientId);
		
		return ActionResult.createSuccess(client);
	}
	
	@RequestMapping("/loginByWeixin")
	public @ResponseBody ActionResult loginByWeixin(HttpServletRequest request, String client_id, String client_secret, int days) throws Exception
	{
		Map<String, Object> bindMap = getH5WeiXinBindInfo(request);
		User user = oauthService.getUserByOpenId(bindMap);
		
		return simulateOAuthLogin(user, client_id, client_secret, days, bindMap);
	}
	
	//关注公众号之后的创建or更新用户
	@RequestMapping("/followWeixin")
	public @ResponseBody ActionResult followWeixin(HttpServletRequest request)
	{
		Map<String, Object> bindMap = HttpUtil.parsePageMap(request);
		User user = oauthService.updateFollower(bindMap);
		return ActionResult.createSuccess(user);
	}
	
	private ActionResult simulateOAuthLogin(User user, String client_id, String client_secret, int days)
	{
		return simulateOAuthLogin(user, client_id, client_secret, days, null);
	}
	
	private ActionResult simulateOAuthLogin(User user, String client_id, String client_secret, int days, Map<String, Object> datamap)
	{
		OAuthClient client = service.getClientById(client_id);
		
		Validate.notNull(client, ErrorCode.ERROR_51006);
		Validate.isTrue(client.getClientSecret().equals(client_secret), ErrorCode.ERROR_51007);
		// 客户端信任度级别是否足够
		Validate.isTrue(client.getScope() == OAuthClient.Scope.trust, ErrorCode.ERROR_51007);
		
		Validate.isTrue(user.getStatus()==1, ErrorCode.ERROR_58001, "用户未激活");
		// 创建临时授权码
		String tmpcode = service.createTmpCode(client_id, user.getId());
		// 使用临时授权码换取访问令牌
		AccessToken token = service.getAccessToken(client_id, client_secret, tmpcode, days);
		
		Map<String, Object> resultMap;
		if (datamap != null)
		{
			resultMap = datamap;
		}
		else
		{
			resultMap = new HashMap<String, Object>();
		}
		
		resultMap.put("accessToken", token.getToken());
		resultMap.put("code", token.getCode());
		resultMap.put("expiryDate", token.getExpiryDate());
		resultMap.put("nickname", user.getNickname());
		resultMap.put("userface", user.getUserface());
		resultMap.put("email", user.getEmail());
		resultMap.put("userId", user.getId());
		if(datamap != null){
			resultMap.put("openId", datamap.get("openId"));
		}
		

		return ActionResult.createSuccess(resultMap);
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
		
		logger.info("we_openid = " + userMap.get("openid"));
		logger.info("we_nickname = " + userMap.get("nickname"));
		logger.info("we_headimgurl = " + userMap.get("headimgurl"));
		
		bindInfoMap.put("openId", userMap.get("openid"));
		bindInfoMap.put("bindType", "3");
		bindInfoMap.put("userNickName", userMap.get("nickname"));
		bindInfoMap.put("userFacePath", userMap.get("headimgurl"));
		bindInfoMap.put("subscribe", userMap.get("subscribe"));
		String sexstr = "";
		try
		{
			int sex = (Integer)userMap.get("sex");
			if (sex == 1)
			{
				sexstr = "man";
			}
			else if (sex == 2)
			{
				sexstr = "woman";
			}
		}
		catch (Exception e)
		{
			logs.warn("获取用户性别失败，原因是:" + e.getLocalizedMessage());
		}
		bindInfoMap.put("sexstr", sexstr);
		
		return bindInfoMap;
	}
}
