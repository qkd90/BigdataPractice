package com.hmlyinfo.app.soutu.account.service;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.mapper.UserMapper;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.base.exception.BizLogicException;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.MD5;
import com.hmlyinfo.base.util.UserEmailUtil;
import com.hmlyinfo.base.util.Validate;
import com.hmlyinfo.base.util.mail.MailInfo;
import com.hmlyinfo.base.util.mail.MailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService extends BaseService<User, Long>{
	
	@Autowired
	private UserMapper<User> mapper;
	@Autowired
	private UserService userService;
	
	@Override
	public BaseMapper<User> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

	/**
	 * 根据用户名和密码判断是否登录成功
	 * <ul>
	 *  <li>必选：用户邮箱{email}</li>
	 *  <li>必选：密码{password}</li>
	 * </ul>
	 * 
	 * @return 
	 */
	public User login(String clientId, String username, String password)
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		
		int usercounts = mapper.count(paramMap);
		Validate.isTrue(usercounts > 0, ErrorCode.ERROR_51004);
		
		paramMap.put("password", MD5.GetMD5Code(password));
		List<User> userList = list(paramMap);
		
		Validate.isTrue(userList.size() > 0, ErrorCode.ERROR_51005);
		return userList.get(0);
	}
	
	/**
	 * 发送激活邮件
	 * @param userId
	 * @param email
	 * @return
	 */
	public boolean sendActiveMail(String userId, String email)
	{
		Validate.notNull(userId, ErrorCode.ERROR_51001, "userId不能为空");
		Validate.notNull(email, ErrorCode.ERROR_51001, "邮箱不能为空");
		
		String curMailSubject = "旅行帮用户激活";
		String curSendSid = null;
		try 
		{
			curSendSid = UserEmailUtil.encryptSendSid(userId);
			
		} 
		catch (Exception e) 
		{
			throw new BizLogicException("系统生成验证时发生错误");
		}

		String activeUrl = Config.get("LOCALLHOST_ADDR") + "active?sendSid=" + curSendSid;
		StringBuffer curMailContentSb = new StringBuffer();
		curMailContentSb.append("尊敬的旅行帮用户：<br/>您注册的用户【").append(email).append("】还未激活，您只需要点击下面链接，即可激活您的帐户。<br/>激活链接地址为:<br/>");
		curMailContentSb.append("<a href=\"").append(activeUrl).append("\">").append(activeUrl).append("</a>").append("<br/>");
		curMailContentSb.append("<br/><br/>如果无法点击该URL链接地址，请将它复制并粘帖到浏览器的地址输入框，然后单击回车即可");
		curMailContentSb.append("<br/><br/><br/><br/>").append("来自旅行帮");
		curMailContentSb.append("<br/><br/><br/><br/>").append("注：该邮件由系统自动发出，无需回复。");
		MailInfo sendInfo = new MailInfo();
		sendInfo.setMailTo(email);
		sendInfo.setMailSubject(curMailSubject);
		sendInfo.setMailContent(curMailContentSb.toString());
		
		return MailSender.getInstance().sendHtmlMail(sendInfo);
	}
	
	/**
	 * 验证用户是否激活，为激活设置激活状态为1
	 * @param sid
	 * @return
	 */
	public boolean activeUser(String sid) 
	{
		Validate.notNull(sid, ErrorCode.ERROR_51001, "sid不能为空");
		
		String curDecodeSid = null;
		try 
		{
			curDecodeSid = UserEmailUtil.decryptSendSid(sid);
		} 
		catch (Exception e) 
		{
			throw new BizLogicException("无效的激活验证码");
		}
		String curUserId = UserEmailUtil.parseUserId(curDecodeSid);
		User user = userService.info(Long.valueOf(curUserId));
		
		if (user != null && user.getStatus() == 0)
		{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", curUserId);
			paramMap.put("status", "1");
			userService.updateUserStatus(paramMap);

			return true;
		}
		else if (user != null && user.getStatus() == 1)
		{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", curUserId);
			paramMap.put("status", "1");
			userService.updateUserStatus(paramMap);

			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 忘记密码发送邮件
	 * @param userId
	 * @param email
	 * @return
	 */
	public boolean sendForgetMail(String userId, String email)
	{
		Validate.notNull(userId, ErrorCode.ERROR_51001, "userId不能为空");
		Validate.notNull(email, ErrorCode.ERROR_51001, "邮箱不能为空");
		
		String curMailSubject = "旅行帮找回密码";
		String curSendSid = null;
		try 
		{
			curSendSid = UserEmailUtil.encryptSendSid(userId);
			
		} 
		catch (Exception e) 
		{
			throw new BizLogicException("系统生成验证时发生错误");
		}

		String activeUrl = Config.get("LOCALLHOST_ADDR")+ "fgStepTwo?sendSid=" + curSendSid;
		StringBuffer curMailContentSb = new StringBuffer();
		curMailContentSb.append("尊敬的旅行帮用户：<br/>您的账号【").append(email).append("】正在申请找回密码服务，请点击下面链接进入旅行帮网站继续操作：<br/>");
		curMailContentSb.append("<a href=\"").append(activeUrl).append("\">").append(activeUrl).append("</a>").append("<br/>");
		curMailContentSb.append("<br/><br/>如果无法点击该URL链接地址，请将它复制并粘帖到浏览器的地址输入框，然后单击回车即可");
		curMailContentSb.append("<br/><br/><br/><br/>").append("来自旅行帮");
		curMailContentSb.append("<br/><br/><br/><br/>").append("注：该邮件由系统自动发出，无需回复。如果不是您的操作，请忽略此邮件");
		MailInfo sendInfo = new MailInfo();
		sendInfo.setMailTo(email);
		sendInfo.setMailSubject(curMailSubject);
		sendInfo.setMailContent(curMailContentSb.toString());
		
		return MailSender.getInstance().sendHtmlMail(sendInfo);
	}
}
