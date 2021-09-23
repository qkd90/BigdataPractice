package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.SysUserDao;
import com.data.data.hmly.service.entity.SysUser;
import com.zuipin.util.MD5;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLoginService {
	@Resource
	private SysUserDao	sysUserDao;

	/**
	 * 功能描述：获取登录用户是否存在
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2014年12月12日下午3:35:22
	 * @param user
	 * @return
	 */
	public SysUser findLoginUser(SysUser user) {
		String pwd = MD5.caiBeiMD5(user.getPassword());
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysUser where");
		sb.append(" account = '" + user.getAccount() + "'");
		sb.append(" and password ='" + pwd + "'");
		// Criteria<SysUser> criteria = new Criteria<SysUser>(SysUser.class);
		// criteria.eq("account", user.getAccount());
		// criteria.eq("password", pwd);
		// criteria.eq("role.name", "管理员");
		// sysUserDao.findByCriteria(criteria);
		SysUser loginuser = sysUserDao.findOneByHQL(sb.toString());
		return loginuser;
	}

	/**
	 * 功能描述：获取登录用户是否存在
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2014年12月12日下午3:35:22
	 * @param user
	 * @return
	 */
	public SysUser findLoginUser(SysUser user, String userAccount) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysUser ");
		sb.append(" where ");
		sb.append(" account = ? ");
		sb.append(" and isUse='已激活' and roleid=4 ");
		// 验证店铺管理员帐号
		SysUser shopuser = sysUserDao.findOneByHQL(sb.toString(), user.getAccount());
		if (shopuser != null) {
			sb = new StringBuffer();
			sb.append(" from SysUser ");
			sb.append(" where ");
			sb.append(" account = ? ");
			sb.append(" and password =? and roleid=3 ");
			String pwd = MD5.caiBeiMD5(user.getPassword());
			// 验证员工帐号
			SysUser loginuser = sysUserDao.findOneByHQL(sb.toString(), userAccount, pwd);
			return loginuser;
		}
		return null;
	}

	/**
	 * 功能描述：更新登陆用户的登陆次数
	 * 
	 * @author : Teny_lu 刘鹰
	 * @E_Mail : liuying5590@163.com
	 * @CreatedTime : 2014年12月16日-上午9:00:12
	 * @return
	 */
	public void updateUser(SysUser loginuser) {
		if (loginuser != null && loginuser.getId() != null) {
			sysUserDao.update(loginuser);
		}
	}
}
