package com.data.data.hmly.action.test;

import javax.annotation.Resource;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.entity.SysUser;
import com.opensymphony.xwork2.Result;

public class TestAction extends FrameBaseAction {
	@Resource
	private SysUserService	sysUserService;

	public Result test() {
		SysUser user = sysUserService.findUserByAccount("admin");
		return text(user == null ? "NULL" : user.getAddress());
	}

	public Result testPage() {
		SysUser user = sysUserService.findUserByAccount("admin");
		setAttribute("user", user);
		return dispatch();
	}

}
