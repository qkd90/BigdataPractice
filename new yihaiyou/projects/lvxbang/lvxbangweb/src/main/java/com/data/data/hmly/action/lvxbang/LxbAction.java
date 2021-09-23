package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.lvxbang.exception.LoginException;
import com.data.data.hmly.service.entity.Member;
import com.zuipin.action.JxmallAction;

import javax.servlet.http.Cookie;

/**
 * @author vacuity
 */
public class LxbAction extends JxmallAction {

    public Member getLoginUser() {
        return getLoginUser(true);
    }

    public Member getLoginUser(boolean strict) {
        Member user = (Member) getSession().getAttribute(UserConstants.CURRENT_LOGIN_USER);
        if (user != null) {
            return user;
        }
        if (!strict) {
            return null;
        }
        throw new LoginException();
    }

    public void addCookie(String name, String value) {
        Cookie cookie = getCookie(name);
        if (cookie != null) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
            cookie.setPath("/");
        }
        getResponse().addCookie(cookie);
    }
}
