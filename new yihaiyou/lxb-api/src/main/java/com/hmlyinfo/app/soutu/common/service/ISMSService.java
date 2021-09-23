package com.hmlyinfo.app.soutu.common.service;

/**
 * 短信服务
 * @ClassName: ISMSService
 * @Description: TODO
 * @author shiqingju
 * @email shiqingju@hmlyinfo.com
 * @date 2015年11月17日 下午9:51:08
 *
 */
public interface ISMSService {

	
	/**
	 * 给手机发送信息
	 * @Title: sendMsg
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午9:54:23
	 * @version 
	 *
	 * @param mobile
	 * @param content
	 *
	 * @return void
	 * @throws
	 */
	void sendMsg(String mobile, String content);
	
	/**
	 * 给指定手机发送验证短信
	 * @Title: sendValidateMsg
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午9:54:35
	 * @version 
	 *
	 * @param mobile 手机号
	 * @param vcode 验证码
	 * @param serviceId 服务标识
	 * @param tplId 短信模板
	 *
	 * @return void
	 * @throws
	 */
	void sendValidateMsg(String mobile, String vcode, String serviceId, String tplId);
	
	/**
	 * 获取发送过的验证码
	 * @Title: getVcode
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午10:08:27
	 * @version 
	 *
	 * @param mobile
	 * @param serviceId
	 *
	 * @return void
	 * @throws
	 */
	void getVcode(String mobile, String serviceId);
}
