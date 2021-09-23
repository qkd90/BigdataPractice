package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.response.SimpleUser;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.UserStatus;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.MD5;
import net.sf.json.JSONObject;

import javax.security.auth.login.LoginException;

/**
 * Created by huangpeijie on 2016-09-13,0013.
 */
public class LoginWebAction extends BaseAction {
    public String account;
    public String password;

    @AjaxCheck
    public Result login() throws LoginException {
        SysSite sysSite = new SysSite();
        sysSite.setId(-1L);
        Member user = memberService.findByAccount(account, sysSite);
        if (user == null) {
            result.put("errMsg", "用户不存在");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (user.getStatus() != UserStatus.activity) {
            result.put("errMsg", "用户被锁定或者被删除");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        String pwd = MD5.caiBeiMD5(password);
        if (!pwd.equals(user.getPassword())) {
            result.put("errMsg", "用户名或者密码错误");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
        SimpleUser response = new SimpleUser(user);
        result.put("user", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }
}
