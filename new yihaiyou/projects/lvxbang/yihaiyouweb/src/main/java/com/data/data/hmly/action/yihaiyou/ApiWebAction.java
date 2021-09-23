package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.util.ShenzhouUtil;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.HttpUtils;
import com.zuipin.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by huangpeijie on 2016-09-02,0002.
 */
public class ApiWebAction extends BaseAction {
    public Member user;

    @AjaxCheck
    @NeedLogin
    public Result oauth() {
        user = getLoginUser();
        HttpServletRequest request = getRequest();
        String code = request.getParameter("code");
        String accessToken = (String) getServletContext().getAttribute(UserConstants.CLIENT_ACCESS_TOKEN);
        Date date = (Date) getServletContext().getAttribute(UserConstants.CLIENT_ACCESS_TOKEN_DATE);
        if (StringUtils.isBlank(accessToken) || date == null || date.before(new Date())) {
            Map<String, Object> clientResult = ShenzhouUtil.clientToken();
            if (!(Boolean) clientResult.get("success")) {
                return text("");
            }
            accessToken = (String) clientResult.get("accessToken");
            date = (Date) clientResult.get("tokenDate");
            getServletContext().setAttribute(UserConstants.CLIENT_ACCESS_TOKEN, accessToken);
            getServletContext().setAttribute(UserConstants.CLIENT_ACCESS_TOKEN_DATE, date);
        }
        Map<String, Object> addResult = ShenzhouUtil.addEmployee(accessToken, user.getTelephone());
        if (!(Boolean) addResult.get("success")) {
            return text("");
        }
        Map<String, Object> tokenResult = ShenzhouUtil.userToken(code);
        if (!(Boolean) tokenResult.get("success")) {
            return text("");
        }
        user.setShenzhouAccessToken((String) tokenResult.get("accessToken"));
        user.setShenzhouRefreshToken((String) tokenResult.get("refreshToken"));
        user.setShenzhouTokenDate((Date) tokenResult.get("tokenDate"));
        memberService.update(user);
        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, user);
        return redirect("/#/bookingCar");
    }

    @AjaxCheck
    @NeedLogin
    public Result api() {
        user = getLoginUser();
        if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
            return redirectUrlJson(user);
        } else if (user.getShenzhouTokenDate().before(new Date())) {
            refreshToken(user);
            if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
                return redirectUrlJson(user);
            }
        }
        HttpServletRequest request = getRequest();
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> orderMap = new TreeMap<String, Object>();
        String url = "";
        for (String s : map.keySet()) {
            if ("url".equals(s)) {
                url = map.get(s)[0];
                continue;
            }
            orderMap.put(s, map.get(s)[0]);
        }
        orderMap.put("access_token", user.getShenzhouAccessToken());
        try {
            result = mapper.readValue(HttpUtils.post(ShenzhouUtil.shenzhouApi + url, orderMap), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult(result);
    }
}
