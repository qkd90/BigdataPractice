package com.hmlyinfo.app.soutu.common.service;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpSession;

/**
 * 验证码服务
 * @ClassName: ICAPTCHAService
 * @Description: TODO
 * @author shiqingju
 * @email shiqingju@hmlyinfo.com
 * @date 2015年11月17日 下午5:20:15
 *
 */
public interface ICAPTCHAService {

	
	/**
	 * 创建一个验证码图片
	 * @Title: createCAPTCHAImg
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午5:22:21
	 * @version 
	 *
	 * @return
	 *
	 * @return BufferedImage
	 * @throws
	 */
	BufferedImage createCAPTCHAImg(HttpSession session, int width, int height);
	
	
	/**
	 * 获取当前会话验证码的值
	 * @Title: getCAPTCHAValue
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午5:23:41
	 * @version 
	 *
	 * @return
	 *
	 * @return String
	 * @throws
	 */
	String getCAPTCHAValue(HttpSession session);
	
	
	/**
	 * 验证验证码是否正确
	 * @Title: validateCAPTCHA
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午5:27:49
	 * @version 
	 *
	 * @param session
	 * @param vcode
	 * @return
	 *
	 * @return boolean
	 * @throws
	 */
	boolean validateCAPTCHA(HttpSession session, String vcode);
}
