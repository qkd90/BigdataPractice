package com.hmlyinfo.app.soutu.account.service;

/**
 * 用户服务的基本接口
 * @ClassName: IUserService
 * @Description: TODO
 * @author shiqingju
 * @email shiqingju@hmlyinfo.com
 * @date 2015年11月17日 下午5:12:05
 *
 */
public interface IUserService {
	
	/**
	 * 用户是否存在
	 * @Title: isUserExists
	 * @email shiqingju@hmlyinfo.com
	 * @date 2015年11月17日 下午5:29:37
	 * @version 
	 *
	 * @param username
	 * @return
	 *
	 * @return boolean
	 * @throws
	 */
	boolean isUserExists(String username);
	
	/**
	 * 更新密码
	 * @Title: updatePassword
	 * @email jlr_6@foxmail.com
	 * @date 2015年11月18日 上午9:47:56
	 * @version 
	 *
	 * @param username
	 * @param password
	 *
	 * @return void
	 * @throws
	 */
	void updatePassword(String username, String password);

}
