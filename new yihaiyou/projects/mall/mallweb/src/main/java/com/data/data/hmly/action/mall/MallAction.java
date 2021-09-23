package com.data.data.hmly.action.mall;

import com.data.data.hmly.action.mall.vo.MallConfig;
import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.entity.User;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.FileUtil;

/**
 * Created by guoshijie on 2015/10/20.
 */
public class MallAction extends JxmallAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1641769069475529889L;

	@Override
	protected Result dispatch() {
		getGlobalData();
		return super.dispatch();
	}

	private void getGlobalData() {
		setAttribute(MallConstants.GLOBAL_HEADER_KEY, FileUtil.loadHTML(MallConstants.GLOBAL_HEADER));
		setAttribute(MallConstants.GLOBAL_FOOTER_KEY, FileUtil.loadHTML(MallConstants.GLOBAL_FOOTER));
	}

	public void getHeader() {
		setAttribute(MallConstants.GLOBAL_HEADER_KEY, FileUtil.loadHTML(MallConstants.GLOBAL_HEADER));
	}

	public User getLoginUser() {
		User user = (User) getSession().getAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			return user;
		} else {
			return null;
		}
	}

	public MallConfig getMallConfig() {
		MallConfig mallConfig = (MallConfig) getSession().getAttribute("mallConfig");
		return mallConfig;
	}

}
