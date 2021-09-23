package com.data.data.hmly.action.mobile;

import com.alibaba.fastjson.JSONObject;
import com.data.data.hmly.action.mobile.interceptor.LoginInterceptor;
import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.mobile.MobileService;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.gson.oauth.Oauth;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.CookieUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginMobileAction extends JxmallAction {
	private static final long serialVersionUID = -3678072502769281037L;
    @Resource
    private WechatAccountService wechatAccountService;
    @Resource
    private MobileService mobileService;

    /**
     * 微信登录
     * @author caiys
     * @date 2015年11月26日 下午2:32:45
     * @return
     * @throws Exception
     */
	public Result login() throws Exception {
		// 参数
		String accountId = (String) getParameter("accountId");
		String code = (String) getParameter("code");

		// 获取openid
		WechatAccount wechatAccount = wechatAccountService.get(Long.valueOf(accountId));
		Oauth oauth = new Oauth(wechatAccount.getAppId(), wechatAccount.getAppSecret());
		String result = oauth.getToken(code);
		JSONObject jsonObject = JSONObject.parseObject(result);
		String openId = jsonObject.getString("openid");

		// 判断openid是否已经存在，存在直接登录，不存在创建用户
		User user = mobileService.doLogin(accountId, openId);
		ActionContext context = ActionContext.getContext();
		WechatAccount account = wechatAccountService.get(Long.valueOf(accountId));
        context.getSession().put(UserConstans.CURRENT_LOGIN_USER, user);
		context.getSession().put(UserConstans.CURRENT_VIEW_ACCOUNTID, accountId);
		context.getSession().put(UserConstans.CURRENT_SUPPLIER_ID, account.getCompanyUnit().getId());
        // 写入cookie
        String value = accountId + "_" + openId;
        try {
            value = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       CookieUtils.addCookie(getRequest(), getResponse(), LoginInterceptor.COOKIENAME, value, 180 * 24 * 60 * 60);

		// 用户登录前跳转链接
		String forwardUrl = (String) context.getSession().get(UserConstans.FORWARD_URL);
		
		return redirect(forwardUrl);
	}
	
}
