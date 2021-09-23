package com.hmlyinfo.app.soutu.account.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.account.domain.FindPwdFlowItem;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.base.CacheKey;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.base.cache.CacheProvider;
import com.hmlyinfo.base.cache.XMemcachedImpl;
import com.hmlyinfo.base.util.SMSUtil;
import com.hmlyinfo.base.util.StringUtil;
import com.hmlyinfo.base.util.UUIDUtil;
import com.hmlyinfo.base.util.UserEmailUtil;
import com.hmlyinfo.base.util.Validate;
import com.hmlyinfo.base.util.mail.MailInfo;
import com.hmlyinfo.base.util.mail.MailSender;

@Service
public class FindPwdServiceImpl implements IFindPwdService{

	private CacheProvider cachePrivider = XMemcachedImpl.getInstance();;
	@Autowired
	private IUserService userService;
	
	/**
	 * 流程时效为10分钟
	 */
	private static final int flowAging = 600;
	
	private User getUser(String username)
	{
		User user = new User();
		user.setUsername(username);
		
		return user;
	}
	
	@Override
	public String createFlow(String username) {
		
		User user = getUser(username);
		FindPwdFlowItem flowItem = new FindPwdFlowItem();
		flowItem.setUser(user);
		
		String flowKey = CacheKey.FIND_PWD_FLOW_PR + UUIDUtil.getUUID();
		
		cachePrivider.set(flowKey, flowItem, flowAging);
		
		return flowKey;
	}

	@Override
	public boolean isFlowValid(String flowKey) {
		
		FindPwdFlowItem flowItem = cachePrivider.get(flowKey);
		
		return flowItem != null;
	}

	@Override
	public User getFlowUser(String flowKey) {
		
		User user = null;
		FindPwdFlowItem flowItem = cachePrivider.get(flowKey);
		if (flowItem != null)
		{
			user = flowItem.getUser();
		}
		
		return user;
	}

	@Override
	public boolean sendValidateMsg(String flowKey) {
		
		User user = getFlowUser(flowKey);
		Validate.notNull(user, ErrorCode.ERROR_51012);
		
		String vcode = StringUtil.getRadomNum(4);
		String smsTpl = StringUtil.getStrFromFile(this.getClass().getResourceAsStream("/tpl/sms/findPwd.tpl"));
		String smsText = smsTpl.replaceAll("#vcode", vcode);
		
		SMSUtil.send(user.getUsername(), smsText);
		
		// 存储验证码信息
		FindPwdFlowItem flowItem = cachePrivider.get(flowKey);
		flowItem.setVcode(vcode);
		cachePrivider.set(flowKey, flowItem, flowAging);
		
		return true;
	}

	@Override
	public boolean sendValidateEmail(String flowKey) {
		
		User user = this.getFlowUser(flowKey);
		Validate.notNull(user, ErrorCode.ERROR_51012);
		
		String mailTpl = StringUtil.getStrFromFile(this.getClass().getResourceAsStream("/tpl/mail/findPwd.tpl"));
		String encryptFlowKey = "";
		try
		{
			encryptFlowKey = UserEmailUtil.encryptSendSid(flowKey);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		String url = Config.get("API_ADDR") + "findPwd/doValidateEmail?encryptFlowKey=" + encryptFlowKey;
		
		String mailHtml = mailTpl.replaceAll("#mail", user.getUsername()).replaceAll("#activeUrl", url);
		
		updateFlowTime(flowKey);
		
		MailInfo sendInfo = new MailInfo();
		sendInfo.setMailTo(user.getUsername());
		sendInfo.setMailSubject("旅行帮找回密码");
		sendInfo.setMailContent(mailHtml);
		
		return MailSender.getInstance().sendHtmlMail(sendInfo);
	}

	@Override
	public String decryptFlowKey(String encryptFlowKey) {
		
		String flowKey = "";
		try
		{
			flowKey = UserEmailUtil.decryptSendSid(encryptFlowKey);
		}
		catch (Exception e){}
		
		return flowKey.split("#")[0];
	}

	@Override
	public void changePwd(String flowKey, String password) {
		
		FindPwdFlowItem item = cachePrivider.get(flowKey);
		
		// 流程必须存在
		Validate.notNull(item, ErrorCode.ERROR_51012);
		// 流程必须通过验证
		Validate.isTrue(item.getFlowStatu() == FindPwdFlowItem.FLOW_AUTHENTICATED, ErrorCode.ERROR_51012);
		
		userService.updatePassword(item.getUser().getUsername(), password);
		
		// 删除流程
		cachePrivider.delete(flowKey);
	}
	
	/**
	 * 更新流程时效，重新设置为默认的时效
	 * @Title: updateFlowTime
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 下午1:57:30
	 * @version 
	 *
	 * @param flowKey
	 *
	 * @return void
	 * @throws
	 */
	private void updateFlowTime(String flowKey)
	{
		cachePrivider.set(flowKey, cachePrivider.get(flowKey), flowAging);
	}
	
	@Override
	public boolean validateEmailKey(String encryptFlowKey) 
	{
		String flowKey = decryptFlowKey(encryptFlowKey);
		if (StringUtils.isBlank(flowKey))
		{
			return false;
		}
		
		FindPwdFlowItem item = cachePrivider.get(flowKey);
		if (item == null)
		{
			return false; 
		}
		
		updateFlowStatu(flowKey, FindPwdFlowItem.FLOW_AUTHENTICATED);
		
		return true;
	}

	@Override
	public boolean validateMbVcode(String flowKey, String vcode) {
		
		FindPwdFlowItem item = cachePrivider.get(flowKey);
		if (item == null)
		{
			return false; 
		}
		if (!vcode.equals(item.getVcode()))
		{
			return false; 
		}
		
		updateFlowStatu(flowKey, FindPwdFlowItem.FLOW_AUTHENTICATED);
		
		return true;
	}
	
	private void updateFlowStatu(String flowKey, int flowStatu)
	{
		FindPwdFlowItem item = cachePrivider.get(flowKey);
		item.setFlowStatu(flowStatu);
		cachePrivider.set(flowKey, item, flowAging);
	}

}
