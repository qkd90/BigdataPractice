package com.hmlyinfo.app.soutu.account.service;

import com.hmlyinfo.app.soutu.account.domain.User;

/**
 * 找回密码服务
 * @ClassName: IFindPwdService
 * @Description: TODO
 * @author shiqingju
 * @email shiqingju@hmlyinfo.com
 * @date 2015年11月17日 下午5:39:35
 *
 */
public interface IFindPwdService {
	
	/**
	 * 创建一个手机密码找回流程
	 * @Title: createMbFlow
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午5:39:25
	 * @version 
	 *
	 * @param username
	 * @return
	 *
	 * @return String 流程KEY
	 * @throws
	 */
	String createFlow(String username);
	
	/**
	 * 检查流程是否有效
	 * @Title: isFlowValid
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午9:20:14
	 * @version 
	 *
	 * @return
	 *
	 * @return boolean
	 * @throws
	 */
	boolean isFlowValid(String flowKey);
	
	
	/**
	 * 获取当前流程中的用户信息
	 * @Title: getFlowUser
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午9:30:31
	 * @version 
	 *
	 * @param flowKey
	 * @return
	 *
	 * @return User
	 * @throws
	 */
	User getFlowUser(String flowKey);
	
	/**
	 * 发送验证短信
	 * @Title: sendValidateMsg
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午10:14:19
	 * @version 
	 *
	 * @param flowKey
	 *
	 * @return void
	 * @throws
	 */
	boolean sendValidateMsg(String flowKey);
	
	
	/**
	 * 发送密码找回激活邮件
	 * @Title: sendValidateEmail
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 上午9:16:34
	 * @version 
	 *
	 * @param email
	 *
	 * @return void
	 * @throws
	 */
	boolean sendValidateEmail(String flowKey);
	
	/**
	 * 验证邮箱
	 * @Title: validateEmailKey
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 下午2:14:20
	 * @version 
	 *
	 * @param encryptFlowKey
	 * @return
	 *
	 * @return boolean
	 * @throws
	 */
	boolean validateEmailKey(String encryptFlowKey);
	
	/**
	 * 验证手机验证码
	 * @Title: validateMbVcode
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 下午2:15:05
	 * @version 
	 *
	 * @param flowKey
	 * @param vcode
	 * @return
	 *
	 * @return boolean
	 * @throws
	 */
	boolean validateMbVcode(String flowKey, String vcode);
	
	/**
	 * 解密流程key
	 * @Title: decryptFlowKey
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 下午2:19:10
	 * @version 
	 *
	 * @param flowKey
	 * @return
	 *
	 * @return String
	 * @throws
	 */
	String decryptFlowKey(String flowKey);
	
	/**
	 * 修改密码并完成修改密码流程
	 * @Title: changePwd
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 上午9:52:14
	 * @version 
	 *
	 * @param flowKey
	 * @param password
	 *
	 * @return void
	 * @throws
	 */
	void changePwd(String flowKey, String password);

}
