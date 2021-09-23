package com.data.data.hmly.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.data.hmly.service.dao.SysUserRoleDao;
import com.data.data.hmly.service.entity.SysUserRole;

/**
 *　　　　　　　　┏┓　　　┏┓+ +
 *　　　　　　　┏┛┻━━━┛┻┓ + +
 *　　　　　　　┃　　　　　　　┃ 　
 *　　　　　　　┃　　　━　　　┃ ++ + + +
 *　　　　　　 ████━████ ┃+
 *　　　　　　　┃　　　　　　　┃ +
 *　　　　　　　┃　　　┻　　　┃
 *　　　　　　　┃　　　　　　　┃ + +
 *　　　　　　　┗━┓　　　┏━┛
 *　　　　　　　　　┃　　　┃　　　　　　　　　　　
 *　　　　　　　　　┃　　　┃ + + + +
 *　　　　　　　　　┃　　　┃    Code is far away from bug with the animal protecting　　　　　　　
 *　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug,永不修改！　　
 *　　　　　　　　　┃　　　┃
 *　　　　　　　　　┃　　　┃　　+　　　　　　　　　
 *　　　　　　　　　┃　 　　┗━━━┓ + +
 *　　　　　　　　　┃ 　　　　　　　┣┓
 *　　　　　　　　　┃ 　　　　　　　┏┛
 *　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 *　　　　　　　　　　┃┫┫　┃┫┫
 *　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */
/**
 * 功能描述：
 * 
 * @author : Teny_lu 刘鹰
 * @ProjectName : b2b_new
 * @FileName : SysUserRoleService.java
 * @E_Mail : liuying5590@163.com
 * @CreatedTime : 2015年4月21日-下午12:41:48
 */
@Service
public class SysUserRoleService {
	
	@Resource
	private SysUserRoleDao	dao;
	
	/**
	 * 功能描述：新增或更新
	 * 
	 * @author : Teny_lu 刘鹰
	 * @E_Mail : liuying5590@163.com
	 * @CreatedTime : 2015年4月21日-下午12:43:53
	 * @return
	 */
	public void saveOrUpdateRoles(SysUserRole role) {
		if (role != null) {
			if (role.getId() == null) {
				dao.save(role);
			} else {
				dao.update(role);
			}
		}
	}
	
}
