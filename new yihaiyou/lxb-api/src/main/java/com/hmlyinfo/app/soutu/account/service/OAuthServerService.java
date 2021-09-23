package com.hmlyinfo.app.soutu.account.service;

import com.hmlyinfo.app.soutu.account.domain.AccessToken;
import com.hmlyinfo.app.soutu.account.domain.OAuthClient;
import com.hmlyinfo.app.soutu.account.domain.ThridPartyUser;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.mapper.AccessTokenMapper;
import com.hmlyinfo.app.soutu.account.mapper.OAuthClientMapper;
import com.hmlyinfo.app.soutu.account.mapper.ThridPartyUserMapper;
import com.hmlyinfo.app.soutu.account.mapper.UserMapper;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.cache.CacheProvider;
import com.hmlyinfo.base.cache.XMemcachedImpl;
import com.hmlyinfo.base.util.MD5;
import com.hmlyinfo.base.util.UUIDUtil;
import com.hmlyinfo.base.util.Validate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OAuthServerService{
	
	private static final Logger logger = Logger.getLogger(OAuthServerService.class);

	private final static String TMP_CODE_PR = "_OAUTH_CODE_";
	public final static String TOKEN_STORE_PR = "_TOKEN_";
	/**
	 * 缓存默认时效为1天
	 */
	private final static long DEFALUT_EXPIRY = 24 * 60 * 60;
	
	@Autowired
	private UserService userService;
	private CacheProvider saeMemcacheImpl = XMemcachedImpl.getInstance();
	@Autowired
	private OAuthClientMapper<OAuthClient> mapper;
	@Autowired
	private AccessTokenMapper<AccessToken> tokenMapper;
	@Autowired
	private UserMapper<User> userMapper;
	@Autowired
	private ThridPartyUserMapper<ThridPartyUser> thridPartyUserMapper;
	
	public OAuthClient getClientById(String clientId)
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("clientId", clientId);
		List<OAuthClient> clientList = mapper.list(paramMap);
		
		return clientList.size() > 0 ? clientList.get(0) : null;
	}
	
	public String createTmpCode(String clientId, long userId)
	{
		String code = UUIDUtil.getUUID();
		Map<String, Object> cachedMap = new HashMap<String, Object>();
		cachedMap.put("clientId", clientId);
		cachedMap.put("userId", userId);
		
		// 10分钟内有效
		saeMemcacheImpl.set(TMP_CODE_PR + code, cachedMap, 600);
		
		return code;
	}
	
	@Transactional
	public AccessToken getAccessToken(String clientId, String clientSecret, String code, int days)
	{
		OAuthClient client = getClientById(clientId);
		Validate.notNull(client, ErrorCode.ERROR_51006);
		Validate.isTrue(client.getClientSecret().equals(clientSecret), ErrorCode.ERROR_51007);

		Map<String, Object> cachedMap = saeMemcacheImpl.get(TMP_CODE_PR + code);

		Validate.notNull(cachedMap, ErrorCode.ERROR_51008);
		Validate.isTrue(clientId.equals(cachedMap.get("clientId")), ErrorCode.ERROR_51008);

		String accessToken = UUIDUtil.getUUID();
		
		AccessToken token = new AccessToken();
		token.setClientId(clientId);
		token.setUserId((Long)cachedMap.get("userId"));
		token.setToken(accessToken);
		token.setCode(UUIDUtil.getUUID());
		
		// 使用新的令牌覆盖原有的访问令牌
		String cacheKey = getCacheKey(clientId, cachedMap.get("userId").toString(), token.getCode());
		saeMemcacheImpl.set(cacheKey, token, DEFALUT_EXPIRY * days);
		
		// 使用后就删除临时令牌
		saeMemcacheImpl.delete(TMP_CODE_PR + code);
		
		return token;
		
	}
	
	public static String getCacheKey(String clientId, String userId, String tmpCode)
	{
		return OAuthServerService.TOKEN_STORE_PR + clientId + "_" + userId + tmpCode;
	}
	
	/**
	 * 注销
	 * @param clientId
	 * @param clientSecret
	 * @param userId
	 */
	public void logout(String clientId, String clientSecret, String userId, String code)
	{
		// 验证用户是否已经登录
		String cacheKey = getCacheKey(clientId, userId, code);
		Validate.notNull(cacheKey, ErrorCode.ERROR_51003);
		AccessToken token = saeMemcacheImpl.get(cacheKey);
		Validate.notNull(token, ErrorCode.ERROR_51003);
		
		// 验证clientSecret是否匹配
		OAuthClient client = getClientById(clientId);
		Validate.notNull(client, ErrorCode.ERROR_51006);
		Validate.isTrue(clientSecret.equals(client.getClientSecret()), ErrorCode.ERROR_51007);
		
		// 注销用户在服务端的授权信息
		saeMemcacheImpl.delete(cacheKey);
	}
	
	/**
	 * 插入用户信息
	 * @param username
	 * @param password
	 */
	public String register(String username, String password, boolean actived)
	{
		// 查看数据库中是否存在该用户（邮箱），存在，返回错误信息，并且不往下执行
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("email", username);
		int userCount = userService.count(paramMap);
		Validate.isTrue(userCount == 0, ErrorCode.ERROR_51009);
		
		// 为用户设置默认昵称为注册邮箱前半部分
		String nickname = username.split("@")[0];
		// 为用户设置默认头像
		String userface = "userface/head.gif";
		
		// 向数据库插入 用户名（邮箱地址），nickname，用户默认，头像激活标志和注册时间
		User user = new User();
		user.setEmail(username);
		user.setUsername(username);
		user.setNickname(nickname);
		user.setUserface(userface);
		user.setPassword(MD5.GetMD5Code(password));
        if (actived) {
            user.setStatus(User.STATUS_ACTIVE);
        }
        userService.insert(user);
				
		return user.getId().toString();
	}
	
	/**
	 * 通过第三方返回的参数创建第三方用户
	 * <ul>
	 * 	<li>必选：头像:{figureurl_qq_1}</li>
	 * 	<li>必选：昵称:{nikename}</li>
	 * 	<li>必选：第三方Id:{openId}</li>
	 * 	<li>必选：第三方类型:{type}</li>
	 * </ul>
	 *
	 * @return
	 */
	public User createThridUser(Map<String, Object> paramMap) {
		
		
		logger.info("we_openid333 = " + paramMap.get("openId"));
		logger.info("we_bindType333 = " + paramMap.get("bindType"));
		logger.info("we_nickname333 = " + paramMap.get("userNickName"));
		logger.info("we_headimgurl333 = " + paramMap.get("userFacePath"));
		
		User user = new User();
		if(paramMap.get("userNickName") != null && "null" != paramMap.get("userNickName"))
			user.setNickname(paramMap.get("userNickName").toString());
		if(paramMap.get("userFacePath") != null && "null" != paramMap.get("userFacePath"))
			user.setUserface(paramMap.get("userFacePath").toString());
		if (paramMap.get("sexstr") != null && "null" != paramMap.get("sexstr"))
			user.setSex(paramMap.get("sexstr").toString());
		user.setUsername(UUIDUtil.getUUID());
		user.setPassword(null);
		user.setStatus(1);
		userMapper.insert(user);
		
		ThridPartyUser thridUser = new ThridPartyUser();
		thridUser.setUserId(user.getId());
		thridUser.setOpenId(paramMap.get("openId").toString());
		thridUser.setType(Integer.valueOf(paramMap.get("bindType").toString()));
		thridPartyUserMapper.insert(thridUser);
		
		return user;
	}
	
	/**
	 * 更新用户
	 * <ul>
	 * 	<li>必选：用户编号:{userId}</li>
	 * 	<li>必选：邮箱:{email}</li>
	 *  <li>必选：密码:{password}</li>
	 * </ul>
	 *
	 * @return
	 */
	public User updateUser(Map<String, Object> paramMap) {
		User user = userMapper.selById(paramMap.get("userId").toString());
		user.setEmail(paramMap.get("email").toString());;
		user.setEmail(paramMap.get("password").toString());;
		userMapper.update(user);
		return user;
	}
	
	/**
	 * 通过openId获取用户信息
	 * <ul>
	 * 	<li>必选：第三方openId:{openId}</li>
	 * </ul>
	 *
	 * @return
	 */
	public User getUserByOpenId(Map<String, Object> bindMap) {
		
		User user = null;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("openId", bindMap.get("openId"));
		paramMap.put("type", bindMap.get("type"));
		
		
		logger.info("we_openid22 = " + bindMap.get("openId"));
		logger.info("we_bindType2 = " + bindMap.get("bindType"));
		logger.info("we_nickname22 = " + bindMap.get("userNickName"));
		logger.info("we_headimgurl22 = " + bindMap.get("userFacePath"));
		
		List<ThridPartyUser> userList = thridPartyUserMapper.list(paramMap);
		// 第三方登陆用户不存在，默认创建一个
		if (userList.size() == 0)
		{
			user = createThridUser(bindMap);
		}
		else
		{
			ThridPartyUser thirdUser = userList.get(0);
			//更新用户信息
			User userUpdate = new User();
			userUpdate.setId(thirdUser.getUserId());
//			if(bindMap.get("userNickName") != null)
//				userUpdate.setNickname(bindMap.get("userNickName").toString());
			if(bindMap.get("userFacePath") != null)
				userUpdate.setUserface(bindMap.get("userFacePath").toString());
//			if (paramMap.get("sexstr") != null)
//				user.setSex(paramMap.get("sexstr").toString());
			userUpdate.setStatus(1);
			userService.update(userUpdate);
			user = userMapper.selById(thirdUser.getUserId() + "");
		}
		
		return user;
	}
	
	
	//用户关注之后的更新，会更新用户昵称和头像性别等信息
	public User updateFollower(Map<String, Object> bindMap) {
		
		User user = null;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("openId", bindMap.get("openId"));
		paramMap.put("type", bindMap.get("type"));
		
		logger.info("客户端单独发起更新微信用户信息请求");
		logger.info("openId = " + bindMap.get("openId"));
		logger.info("bindType = " + bindMap.get("bindType"));
		logger.info("userNickName = " + bindMap.get("userNickName"));
		logger.info("userFacePath = " + bindMap.get("userFacePath"));
		
		List<ThridPartyUser> userList = thridPartyUserMapper.list(paramMap);
		// 第三方登陆用户不存在，默认创建一个
		if (userList.size() == 0)
		{
			user = createThridUser(bindMap);
		}
		else
		{
			ThridPartyUser thirdUser = userList.get(0);
			//更新用户信息
			User userUpdate = new User();
			userUpdate.setId(thirdUser.getUserId());
			if(bindMap.get("userNickName") != null)
				userUpdate.setNickname(bindMap.get("userNickName").toString());
			if(bindMap.get("userFacePath") != null)
				userUpdate.setUserface(bindMap.get("userFacePath").toString());
			if (paramMap.get("sexstr") != null)
				user.setSex(paramMap.get("sexstr").toString());
			userUpdate.setStatus(1);
			userService.update(userUpdate);
			user = userMapper.selById(thirdUser.getUserId() + "");
		}
		
		return user;
	}

    public boolean isUserExist(String userName) {
        List<User> list = userMapper.list(Collections.<String, Object>singletonMap("username", userName));
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }
}
