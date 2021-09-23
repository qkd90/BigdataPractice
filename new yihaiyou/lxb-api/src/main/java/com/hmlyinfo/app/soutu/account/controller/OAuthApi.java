package com.hmlyinfo.app.soutu.account.controller;

import com.hmlyinfo.app.soutu.account.domain.AccessToken;
import com.hmlyinfo.app.soutu.account.domain.OAuthClient;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.mapper.UserMapper;
import com.hmlyinfo.app.soutu.account.service.AccountService;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.OAuthServerService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.MD5;
import com.hmlyinfo.base.util.SMSUtil;
import com.hmlyinfo.base.util.UserEmailUtil;
import com.hmlyinfo.base.util.Validate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/passport")
public class OAuthApi
{
	Logger logger = Logger.getLogger(OAuthApi.class);
	@Autowired
	private OAuthServerService service;
	@Autowired
	private AccountService accountService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private UserService userService;
	@Autowired
    private UserMapper<User> mapper;

    private static final String REGISTER_SMS_CODE = "REGISTER_SMS_CODE";
    private static final String RANDOM_CHECK_CODE = "randCheckCode";

    @RequestMapping("/login")
	public String loginRequest(Model model, String client_id, String redirect_uri){
		
		if (StringUtils.isBlank(client_id))
		{
			client_id = Config.get("DEFAULT_CLIENT_ID");
			redirect_uri = service.getClientById(client_id).getRedirectUri();
		}
		
		Validate.notNull(client_id, ErrorCode.ERROR_50001);
		Validate.notNull(redirect_uri, ErrorCode.ERROR_50001);
		
		Map<String, Object> checkMap = new HashMap<String, Object>();
		checkMap.put("clientId", client_id);
		
		OAuthClient client = service.getClientById(client_id);
		Validate.notNull(client, ErrorCode.ERROR_51006);
		
		Validate.isTrue(redirect_uri.startsWith(client.getRedirectUri()), ErrorCode.ERROR_51007);
		
		model.addAttribute("clientId", client_id);
		model.addAttribute("redirectUri", redirect_uri);
		
		return "account/login";
		
	}
	
	/**
	 * 登录
	 * <ul>
	 * 	<li>必选：用户邮箱{email}</li>
	 *  <li>必选：密码{password}</li>
	 * 	<li>url:/passport/loginAct</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/loginAct")
	public @ResponseBody ActionResult loginAct(final HttpServletRequest request, Model model, String clientId, 
			String username, String password, String redirectUri)
	{
		Validate.notNull(clientId, ErrorCode.ERROR_51001);
		Validate.notNull(username, ErrorCode.ERROR_51001);
		Validate.notNull(password, ErrorCode.ERROR_51001);
		
		// TODO 检查重定向URI和CLIENT_ID是否匹配
		OAuthClient client = service.getClientById(clientId);
		Validate.notNull(client, ErrorCode.ERROR_51006);
		Validate.isTrue(redirectUri.startsWith(client.getRedirectUri()), ErrorCode.ERROR_51007);
		
		// 用户登陆
		User user = accountService.login(clientId, username, password);
		
		Validate.isTrue(user.getStatus()==1, ErrorCode.ERROR_58001);
		// 创建临时授权码
		String code = service.createTmpCode(clientId, user.getId());
		redirectUri = redirectUri + "?code=" + code;
		
		Map<String, Object> uri = new HashMap<String, Object>();
		uri.put("uri", redirectUri);
		
		return ActionResult.createSuccess(uri);
	}
	
	/**
	 * 获取访问密钥
	 * <ul>
	 * 	<li>必选：客户端ID{client_id}</li>
	 *  <li>必选：客户端密钥{client_secret}</li>
	 *  <li>必选：临时令牌{code}</li>
	 * 	<li>url:/passport/getaccesstoken</li>
	 * </ul>
	 */
	@RequestMapping("/getaccesstoken")
	public @ResponseBody ActionResult getAccessToken(String client_id, String client_secret, String code, int days)
	{
		Validate.notNull(client_id, ErrorCode.ERROR_51001);
		Validate.notNull(client_secret, ErrorCode.ERROR_51001);
		Validate.notNull(code, ErrorCode.ERROR_51001);
		
		OAuthClient client = service.getClientById(client_id);
		Validate.notNull(client, ErrorCode.ERROR_51001);
		
		AccessToken token = service.getAccessToken(client_id, client_secret, code, days);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("accessToken", token.getToken());
		resultMap.put("code", token.getCode());
		resultMap.put("expiryDate", token.getExpiryDate());
		
		User user = userService.info(token.getUserId());
		resultMap.put("nickname", user.getNickname());
		resultMap.put("userface", user.getUserface());
		resultMap.put("email", user.getEmail());
		resultMap.put("userId", user.getId());
		
		return ActionResult.createSuccess(resultMap);
	}
	
	
	/**
	 * 登出
	 * <ul>
	 * 	<li>必选：client_id</li>
	 * 	<li>必选：client_secret</li>
	 * 	<li>必选：userId</li>
	 *  <li>url:/passport/logout</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/logout")
	public @ResponseBody ActionResult logout(String client_id, String client_secret, String userId, String code)
	{
		Validate.notNull(client_id, ErrorCode.ERROR_51001);
		Validate.notNull(client_secret, ErrorCode.ERROR_51001);
		Validate.notNull(userId, ErrorCode.ERROR_51001);
		Validate.notNull(code, ErrorCode.ERROR_51001);
		
		service.logout(client_id, client_secret, userId, code);
		
		return ActionResult.createSuccess();
		
	}
	
	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping("/register")
	public String register()
	{
		return "account/register";
	}

    /**
     * 注册动作
     *
     * @param request
     * @param username
     * @param password
     * @return
     */
    @Transactional
    @RequestMapping("/registerAct")
    @ResponseBody
    public ActionResult registerAct(final HttpServletRequest request, String username, String password, String vcode, String smsCode) {
        Validate.notNull(username, ErrorCode.ERROR_51001);
        Validate.notNull(password, ErrorCode.ERROR_51001);

        //验证邮箱格式
        String patternE = "^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
        Pattern emailPatter = Pattern.compile(patternE);
        //手机号注册暂时不做
        String patternM = "^1[3,5,8][0-9]{9}$";
        Pattern mobilePatter = Pattern.compile(patternM);
        //邮箱格式正确的情况下，往下走，不正确，返回错误信息
        //Validate.isTrue(pe.matcher(username).matches() || pm.matcher(username).matches(), ErrorCode.ERROR_51001, "请输入正确的邮箱/手机号");

        //将验证码与session里的验证码做匹配，正确，往下走，不正确，返回错误信息
        HttpSession session = request.getSession(true);
        if (mobilePatter.matcher(username).matches()) {
            Object code = session.getAttribute(REGISTER_SMS_CODE + username);
            if (code == null) {
                return ActionResult.createFail(ErrorCode.ERROR_50001, "验证码错误");
            }
            String checkCode = code.toString();
            Validate.isTrue(smsCode.equalsIgnoreCase(checkCode), ErrorCode.ERROR_50001, "短信验证码错误");
            session.removeAttribute(REGISTER_SMS_CODE + username);
            service.register(username, password, true);
            Map<String, Object> loginData = new HashMap<String, Object>();
            loginData.put("clientId", Config.get("DEFAULT_CLIENT_ID"));
            loginData.put("redirectUri", service.getClientById(Config.get("DEFAULT_CLIENT_ID")).getRedirectUri());
            ActionResult actionResult = ActionResult.createSuccess(loginData);
            actionResult.setErrorMsg("mobile");
            return actionResult;
        } else if ( emailPatter.matcher(username).matches()) {
            String checkCode = (String) session.getAttribute(RANDOM_CHECK_CODE);
            Validate.isTrue(vcode.equalsIgnoreCase(checkCode), ErrorCode.ERROR_50001, "验证码错误");
            String userId = service.register(username, password, false);

            Validate.isTrue(accountService.sendActiveMail(userId, username), ErrorCode.ERROR_50001, "发送验证邮箱失败，请稍后再试");
            // 将用户id加密后传到下一个页面
            String sendUid = null;

            try {
                sendUid = UserEmailUtil.encryptSendUid(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ActionResult.createSuccess(sendUid);
        } else {
            return ActionResult.createFail(ErrorCode.ERROR_50001, "请输入正确的手机号码或邮箱");
        }
    }

    /**
	 * 发送邮件激活页面
	 * @param model
	 * @param email
	 * @return
	 */
	@RequestMapping("/actemail/{uid}")
	public String regSuccess(Model model, @PathVariable String uid)
	{
		String userId;
		try 
		{
			userId = UserEmailUtil.decryptSendSid(uid);
			User user = userService.info(Long.valueOf(userId));
			model.addAttribute("email", user.getEmail());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return "account/actemail";
	}
	
	/**
	 * 邮箱验证激活
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/active")
	public String active(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		String curDecodeSid = null;
		try 
		{
			curDecodeSid = UserEmailUtil.decryptSendSid(request.getParameter("sendSid"));
		} 
		catch (Exception e) 
		{
			logger.error("用户不存在", e);
		}
		
		String curUserId = UserEmailUtil.parseUserId(curDecodeSid);
		User user = userService.info(Long.valueOf(curUserId));
		
		// 激活成功并登录
		if (accountService.activeUser(request.getParameter("sendSid")))
		{
			// 处理默认登陆动作
			String clientId = Config.get("DEFAULT_CLIENT_ID");
			// 创建临时授权码
			String tmpcode = service.createTmpCode(clientId, user.getId());
			OAuthClient client = service.getClientById(clientId);
			model.addAttribute("code", tmpcode);
			model.addAttribute("redirect_uri", Config.get("LOCALLHOST_ADDR") + "actsus");
			
			return "redirect:" + client.getRedirectUri();
		}
		else
		{
			model.addAttribute("email", user.getEmail());
			String uid = null;
			try {
				uid = UserEmailUtil.encryptSendSid(curUserId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.addAttribute("uid", uid);
			// 激活失败，再次发送邮件按钮
			return "account/actfail";
		}
	}
	
	/**
	 * 激活成功页面
	 * @return
	 */
	@RequestMapping("/actsus")
	public String actsus()
	{
		return "account/actsus";
	}
	
	/**
	 * 再次发送激活邮件
	 * @param request
	 * @return
	 */
	@RequestMapping("/resend/email")
	public @ResponseBody ActionResult resendMail(HttpServletRequest request)
	{
		Validate.notNull(request);
		
		String curUserId = null;
		try {
			curUserId = UserEmailUtil.decryptSendSid(request.getParameter("uid"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Validate.isTrue(accountService.sendActiveMail(curUserId, request.getParameter("email")), ErrorCode.ERROR_50001, "发送验证邮箱失败");
		
		return ActionResult.createSuccess(null);
	}

    /**
     * 发送验证码短信
     */
    @RequestMapping("/sendRegisterSms")
    @ResponseBody
    public ActionResult sendRegisterSms(String mobile, HttpServletRequest request) {
        Pattern pattern = Pattern.compile("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$");
        Matcher matcher = pattern.matcher(mobile);
        if (!matcher.find()) {
            return ActionResult.createFail(ErrorCode.ERROR_51011, "手机号码错误");
        }

        boolean isUserExist = service.isUserExist(mobile);
        if (isUserExist) {
            return ActionResult.createFail(ErrorCode.ERROR_51010, "手机号码已注册");
        }

        String smsCode = (new Random().nextDouble()+"").substring(2, 8);
        request.getSession().setAttribute(REGISTER_SMS_CODE + mobile, smsCode);
        String content = Config.get("sms.register.content").replace("#registerCode", smsCode);
        SMSUtil.send(mobile, content);
        return ActionResult.createSuccess();
    }
	
	/**
	 * 通过第三方返回的参数创建第三方用户
	 * <ul>
	 * 	<li>必选：头像:{figureurl_qq_1}</li>
	 * 	<li>必选：昵称:{nikename}</li>
	 *  <li>必选：第三方Id:{openId}</li>
	 *  <li>必选：第三方类型:{type}</li>
	 *  <li>url:/passport/thridPartyUser/create</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/thridPartyUser/create")
	public @ResponseBody ActionResult crearteThridUser(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("figureurl_qq_1"), ErrorCode.ERROR_51001);
		Validate.notNull(request.getParameter("nikename"), ErrorCode.ERROR_51001);
		Validate.notNull(request.getParameter("openId"), ErrorCode.ERROR_51001);
		Validate.notNull(request.getParameter("type"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.createThridUser(paramMap));
	}

	/**
	 * 更新用户
	 * <ul>
	 * 	<li>必选：用户编号:{userId}</li>
	 * 	<li>可选：邮箱:{email}</li>
	 *  <li>可选：密码:{password}</li>
	 *  <li>url:/passport/updateUser</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/updateUser")
	public @ResponseBody ActionResult updateUser(final HttpServletRequest request) {
		String userId = String.valueOf(MemberService.getCurrentUserId());
		Validate.isTrue(userId == request.getParameter("userId"), ErrorCode.ERROR_50001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.updateUser(paramMap));
	}
	
	/**
	 * 通过openId获取用户信息
	 * <ul>
	 * 	<li>必选：第三方openId:{openId}</li>
	 *  <li>url:/passport/getUserByOpenId</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/getUserByOpenId")
	public @ResponseBody ActionResult getUserByOpenId(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("openId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.getUserByOpenId(paramMap));
	}

    /**
     * 验证用户名是否存在
     * @param username
     * @return ActionResult
     */
    @RequestMapping("/validateUsername")
    @ResponseBody
    public ActionResult validateUsername(String username) {
        Validate.notNull(username, ErrorCode.ERROR_51001, "请输入用户名");
        boolean isUserExist = service.isUserExist(username);
        if (isUserExist) {
            return ActionResult.createFail(ErrorCode.ERROR_51010, "用户名已存在");
        }
        return ActionResult.createSuccess();
    }
    
    /**
	 * 忘记密码页面
	 * @return
	 */
	@RequestMapping("/forgetPwd")
	public String forget()
	{
		return "account/forget";
	}
	
	/**
	 * 忘记密码动作
	 * @param request
	 * @param model
	 * @param username
	 * @param password
	 * @return
	 */
	@Transactional
	@RequestMapping("/forgetAct")
	public @ResponseBody ActionResult forgetAct(final HttpServletRequest request,
			Model model, String username, String vcode)
	{
		Validate.notNull(username, ErrorCode.ERROR_51001, "请输入用户名");
		Validate.notNull(vcode, ErrorCode.ERROR_51001);
		
		//验证邮箱格式
		String patternE = "^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
		Pattern pe = Pattern.compile(patternE);
		//邮箱格式正确的情况下，往下走，不正确，返回错误信息
		Validate.isTrue(pe.matcher(username).matches(), ErrorCode.ERROR_51001, "请输入正确的邮箱");
	    
		//将验证码与session里的验证码做匹配，正确，往下走，不正确，返回错误信息
		HttpSession session = request.getSession(true);
	    String checkCode = (String) session.getAttribute(RANDOM_CHECK_CODE);
		Validate.isTrue(vcode.equalsIgnoreCase(checkCode), ErrorCode.ERROR_50001,"验证码错误");
		
		//验证邮箱是否存在
		List<User> userMap = userService.list( Collections.<String, Object>singletonMap("username", username));
		Validate.isTrue(!userMap.isEmpty(), ErrorCode.ERROR_51001,"该邮箱未注册");
		
		// 一切都匹配的情况下,发送邮箱验证
		Validate.isTrue(accountService.sendForgetMail(userMap.get(0).getId().toString(), username), ErrorCode.ERROR_50001,"发送验证邮箱失败，请稍后再试");
		
		return ActionResult.createSuccess();
	}
	
	/**
	 * 忘记密码第二步页面
	 * @return
	 */
	@RequestMapping("/fgStepTwo")
	public String forgetStepTwo(Model model,HttpServletRequest request)
	{
		String curDecodeSid = null;
		try 
		{
			curDecodeSid = UserEmailUtil.decryptSendSid(request.getParameter("sendSid"));
		} 
		catch (Exception e) 
		{
			logger.error("用户不存在", e);
		}
		Validate.notNull(curDecodeSid);
		
		String curUserId = UserEmailUtil.parseUserId(curDecodeSid);
		User user = userService.info(Long.valueOf(curUserId));

		model.addAttribute("nickname", user.getNickname());
		model.addAttribute("curDecodeSid", curDecodeSid);
		
		return "account/fsteptwo";
	}
	
	/**
     * 修改密码
     * @param request
     * @param view
     * @return
     */
    @RequestMapping("/savePwd")
    public @ResponseBody ActionResult updatePassword(HttpServletRequest request, Map<String, Object> view) 
    {
        String newPassword = request.getParameter("newpassword");
        String repeatPassword = request.getParameter("repeatpassword");
        
        //判断两个密码是否为空，是否相同
        Validate.notNull(newPassword, ErrorCode.ERROR_51001, "请输入密码");
        Validate.notNull(repeatPassword, ErrorCode.ERROR_51001, "请输入确认密码");
        Validate.isTrue(newPassword.length()>=6, ErrorCode.ERROR_51001, "密码长度必须是6-20位字符");
        Validate.isTrue(newPassword.length()<=20, ErrorCode.ERROR_51001, "密码长度必须是6-20位字符");
        Validate.isTrue(newPassword.equals(repeatPassword), ErrorCode.ERROR_51001, "两次输入的密码不一致，请重新输入");
        
        //拿到用户ID
        String curDecodeSid = null;
		try 
		{
			curDecodeSid = UserEmailUtil.decryptSendSid(request.getParameter("sendSid"));
		} 
		catch (Exception e) 
		{
			logger.error("修改失败", e);
			return ActionResult.createSuccess(e);
		}
		String userId = UserEmailUtil.parseUserId(curDecodeSid);
		//根据用户ID取用户信息，判断用户是否存在
		List<User> users = userService.list( Collections.<String, Object>singletonMap("id", userId) );
		Validate.isTrue(!users.isEmpty(), ErrorCode.ERROR_51001,"用户不存在");
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", userId);
        params.put("password", MD5.GetMD5Code(newPassword.toString()));
        mapper.updatePassword(params);
        
        //将新密码保存到Session中，用于下个页面的显示
        HttpSession session = request.getSession(true);
        session.setAttribute("newPassword", newPassword);
        
        return ActionResult.createSuccess();
    }
    
    /**
	 * 忘记密码第三步页面
	 * @return
	 */
	@RequestMapping("/fgStepThree")
	public String forgetStepThree(Model model, HttpServletRequest request)
	{
		
		HttpSession session = request.getSession(true);
        String newPassword = (String) session.getAttribute("newPassword");
        model.addAttribute("password",newPassword);
        
		return "account/fstepthree";
	}
}
