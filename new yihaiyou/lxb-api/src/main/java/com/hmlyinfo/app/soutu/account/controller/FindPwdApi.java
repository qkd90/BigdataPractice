package com.hmlyinfo.app.soutu.account.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;
import com.hmlyinfo.app.soutu.account.service.IFindPwdService;
import com.hmlyinfo.app.soutu.account.service.IUserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.common.service.ICAPTCHAService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.RegexUtil;
import com.hmlyinfo.base.util.Validate;

/**
 * 找回密码
 * @ClassName: ForgetPwdApi
 * @Description: 处理忘记密码页面，支持手机找回密码和邮箱找回密码
 * @author shiqingju
 * @email shiqingju@hmlyinfo.com
 * @date 2015年11月17日 下午4:55:02
 *
 */
@Controller
@RequestMapping("/findPwd")
public class FindPwdApi {

	@Autowired
	private IUserService userService;
	@Autowired
	private ICAPTCHAService captchaService;
	@Autowired
	private IFindPwdService findPwdService;
	
	
	/**
	 * 找回密码的首页
	 * @Title: index
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午5:02:59
	 * @version 
	 *
	 * @return
	 *
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String showIndex()
	{
		return "account/forget";
	}
	
	/**
	 * 确认查找密码动作
	 * @Title: dofindPwd
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 上午10:49:13
	 * @version 
	 *
	 * @param session
	 * @param model
	 * @param username
	 * @param vcode
	 * @return
	 *
	 * @return ActionResult
	 * @throws
	 */
	@RequestMapping(value = "/findPwd", method = RequestMethod.POST)
	public @ResponseBody ActionResult doFindPwd(HttpSession session, Model model, String username, String vcode)
	{
		// 确认用户名是否存在
		Validate.isTrue(userService.isUserExists(username), ErrorCode.ERROR_50001, "用户不存在");
		
		// 确认验证码是否正确
		Validate.isTrue(captchaService.validateCAPTCHA(session, vcode), ErrorCode.ERROR_50001, "验证码错误");
		
		// 创建找回密码流程
		String flowKey = findPwdService.createFlow(username);
		model.addAttribute("flowKey", flowKey);
		
		ActionResult actionResult;
		
		// 邮箱找回流程
		if (RegexUtil.isEmail(username))
		{
			actionResult = ActionResult.createSuccess(ImmutableMap.of("url", "validateEmail?flowKey=" + flowKey));
		}
		// 手机找回流程
		else if (RegexUtil.isMobile(username))
		{
			actionResult = ActionResult.createSuccess(ImmutableMap.of("url", "validateMobile?flowKey=" + flowKey));
		}
		// 非邮箱和手机，需要暂时不支持密码找回
		else
		{
			actionResult = null;
		}
		
		return actionResult;
		
	}
	
	/**
	 * 验证电子邮箱页面
	 * @Title: validateEmail
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午9:18:54
	 * @version 
	 *
	 * @param flowKey
	 * @return
	 *
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/validateEmail", method=RequestMethod.GET)
	public String showValidateEmail(Model model, String flowKey)
	{
		Validate.notNull(flowKey, ErrorCode.ERROR_50001, "非法请求");
		Validate.isTrue(findPwdService.isFlowValid(flowKey), ErrorCode.ERROR_51012);
		model.addAttribute("user", findPwdService.getFlowUser(flowKey));
		model.addAttribute("flowKey", flowKey);
		
		return "account/validateEmail";
	}
	
	/**
	 * 发送验证邮件
	 * @Title: sendValidateEmail
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月17日 下午10:29:35
	 * @version 
	 *
	 * @param flowKey
	 * @return
	 *
	 * @return AjaxList
	 * @throws
	 */
	@RequestMapping(value="/ajax/sendValidateEmail")
	public @ResponseBody ActionResult doSendValidateEmail(String flowKey)
	{
		Validate.notNull(flowKey, ErrorCode.ERROR_50001, "非法请求");
		
		// 发送电子邮件
		findPwdService.sendValidateEmail(flowKey);
		
		return ActionResult.createSuccess();
	}
	
	/**
	 * 验证邮件
	 * @Title: doValidateEmail
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月17日 下午10:31:54
	 * @version 
	 *
	 * @param model
	 * @param encryptFlowKey
	 * @return
	 *
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/doValidateEmail", method=RequestMethod.GET)
	public String doValidateEmail(Model model, String encryptFlowKey)
	{
		Validate.notNull(encryptFlowKey, ErrorCode.ERROR_50001, "非法请求");
		Validate.isTrue(findPwdService.validateEmailKey(encryptFlowKey), ErrorCode.ERROR_50001, "非法请求");
		
		String flowKey = findPwdService.decryptFlowKey(encryptFlowKey);
		
		// 显示修改密码页面
		return "redirect:changePwd?flowKey=" + flowKey;
	}
	
	/**
	 * 验证手机页面
	 * @Title: validateMobile
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午9:34:28
	 * @version 
	 *
	 * @return
	 *
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/validateMobile", method=RequestMethod.GET)
	public String showValidateMobile(Model model, String flowKey)
	{
		Validate.notNull(flowKey, ErrorCode.ERROR_50001, "非法请求");
		Validate.isTrue(findPwdService.isFlowValid(flowKey), ErrorCode.ERROR_51012);
		model.addAttribute("user", findPwdService.getFlowUser(flowKey));
		model.addAttribute("flowKey", flowKey);
		
		return "account/validateMobile";
	}
	
	/**
	 * 发送验证短信
	 * @Title: sendValidateSMS
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 上午10:31:37
	 * @version 
	 *
	 * @return
	 *
	 * @return ActionResult
	 * @throws
	 */
	@RequestMapping(value="/ajax/sendValidateSMS")
	public @ResponseBody ActionResult doSendValidateSMS(String flowKey)
	{
		Validate.notNull(flowKey, ErrorCode.ERROR_50001, "非法请求");
		Validate.isTrue(findPwdService.isFlowValid(flowKey), ErrorCode.ERROR_51012);
		
		findPwdService.sendValidateMsg(flowKey);
		
		return ActionResult.createSuccess();
	}
	
	
	/**
	 * 验证手机验证码
	 * @Title: doValidateMobile
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午9:45:35
	 * @version 
	 *
	 * @return
	 *
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/validateMobile", method=RequestMethod.POST)
	public @ResponseBody ActionResult doValidateMobile(Model model, String flowKey, String vcode)
	{
		Validate.notNull(flowKey, ErrorCode.ERROR_50001, "非法请求");
		Validate.notNull(vcode, ErrorCode.ERROR_50001, "验证码不能为空");
		
		Validate.isTrue(findPwdService.validateMbVcode(flowKey, vcode), ErrorCode.ERROR_50001, "验证码错误");
				
		// 显示修改密码页面
		return ActionResult.createSuccess(ImmutableMap.of("url", "changePwd?flowKey=" + flowKey));
	}
	
	/**
	 * 修改密码页面
	 * @Title: changePwd
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 上午9:26:31
	 * @version 
	 *
	 * @param flowKey
	 * @return
	 *
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/changePwd", method=RequestMethod.GET)
	public String showChangePwd(Model model, String flowKey)
	{
		Validate.notNull(flowKey, ErrorCode.ERROR_50001, "非法请求");
		Validate.isTrue(findPwdService.isFlowValid(flowKey), ErrorCode.ERROR_51012);
		
		model.addAttribute("user", findPwdService.getFlowUser(flowKey));
		model.addAttribute("flowKey", flowKey);
		
		return "account/changePwd";
	}
	
	
	/**
	 * 确认修改密码
	 * @Title: doChangePwd
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 上午9:43:49
	 * @version 
	 *
	 * @param model
	 * @param flowKey
	 * @return
	 *
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/changePwd", method=RequestMethod.POST)
	public String doChangePwd(Model model, String flowKey, String password)
	{
		Validate.notNull(flowKey, ErrorCode.ERROR_50001, "非法请求");
		Validate.isTrue(findPwdService.isFlowValid(flowKey), ErrorCode.ERROR_51012);
		
		findPwdService.changePwd(flowKey, password);
		
		return "redirect:success";
	}
	
	
	/**
	 * 找回密码成功页面
	 * @Title: success
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 上午9:54:01
	 * @version 
	 *
	 * @return
	 *
	 * @return String
	 * @throws
	 */
	@RequestMapping(value="/success", method=RequestMethod.GET)
	public String showSuccess()
	{
		return "account/findSuccess";
	}
}
