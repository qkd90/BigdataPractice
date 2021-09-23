package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.enums.ThirdPartyUserType;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.lvxbang.ThirdPartyLonginService;
import com.data.data.hmly.service.lvxbang.util.RedirectUriUtil;
import com.data.data.hmly.service.lvxbang.util.ThirdPartyLoginUtil;
import com.data.data.hmly.service.lvxbang.util.WeixinSettings;
import com.data.data.hmly.service.user.vo.OpenLoginInfo;
import com.google.common.collect.Maps;
import com.gson.oauth.Oauth;
import com.opensymphony.xwork2.Result;
import com.qq.connect.QQConnectException;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.RandomStatusGenerator;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Jonathan.Guo
 */
public class ThirdWebAction extends LxbAction {

    @Resource
    private ThirdPartyLonginService thirdPartyLonginService;
    @Resource
    private MemberService memberService;

    private final Map<String, Object> map = new HashMap<String, Object>();


    public Result weixin() {
        return redirect(WeixinSettings.getLoginUrl(getRequest()) + "&state=" + getState());
    }

    public Result weixinCallback() throws Exception {
        String realDomain = getRequest().getParameter("realDomain");
        if (realDomain != null) {
            return redirect(realDomain + "/lvxbang/third/weixinCallback.jhtml?code=" + getRequest().getParameter("code"));
        }

        // 微信用户同意授权后回调处理：进一步获取用户详细信息
        Oauth oauth = new Oauth(WeixinSettings.APP_ID, WeixinSettings.APP_SECRET);
        JSONObject json = JSONObject.fromObject(oauth.getToken(getRequest().getParameter("code")));
        String openid = json.getString("openid");
        String accessToken = json.getString("access_token");
        JSONObject userInfo = JSONObject.fromObject(oauth.getUserInfo(accessToken, openid));
        String nickname = userInfo.getString("nickname");
        String unionid = userInfo.getString("unionid");
        String headimgurl = userInfo.getString("headimgurl");
        OpenLoginInfo openLoginInfo = new OpenLoginInfo(openid, ThirdPartyUserType.weixin, nickname, headimgurl, unionid);
//        Member member = thirdPartyLonginService.login(openLoginInfo.openId, ThirdPartyUserType.weixin, WeixinSettings.LOGIN_ACCOUNT_ID);
        Member member = memberService.findByUnionId(unionid);
        if (member != null) { // 存在，更新
            member = thirdPartyLonginService.update(member, openLoginInfo, WeixinSettings.LOGIN_ACCOUNT_ID);
        } else {    // 不存在，新增
            member = thirdPartyLonginService.create(openLoginInfo, WeixinSettings.LOGIN_ACCOUNT_ID);
        }

        getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, member);
        getSession().setAttribute("account", member.getAccount());
        getSession().setAttribute("staffName", member.getUserName());

        if (StringUtils.hasText(member.getNickName())) {
            getSession().setAttribute("userName", member.getNickName());
        } else {
            getSession().setAttribute("userName", member.getUserName());
        }
        String url = "/lvxbang/third/success.jhtml";
        return redirect(url);
    }

    public Result qq() throws QQConnectException {
        return redirect(getQQAuthorizeURL(getRequest()));
    }

    public String getQQAuthorizeURL(ServletRequest request) throws QQConnectException {
        String state = RandomStatusGenerator.getUniqueState();
        ((HttpServletRequest) request).getSession().setAttribute("qq_connect_state", state);
        String scope = QQConnectConfig.getValue("scope");
        String redirectUri = RedirectUriUtil.getRedirectUri(getRequest(), QQConnectConfig.getValue("redirect_URI").trim());
        // 为了支持跨域,增加realDomain来存放currentDomain
        // 一旦授权域的域名和传过来的realDomain不一致,就会将code传给realDomain域
        String currentDomain = request.getScheme() + "://" + request.getServerName();
        if (!redirectUri.startsWith(currentDomain)) {
            redirectUri += "?realDomain=" + currentDomain;
        }
        try {
            redirectUri = URLEncoder.encode(redirectUri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return QQConnectConfig.getValue("authorizeURL").trim() + "?client_id=" + QQConnectConfig.getValue("app_ID").trim() + "&redirect_uri=" + redirectUri + "&response_type=" + "code" + "&state=" + state + (scope != null && !"".equals(scope) ? ("&scope=" + scope) : "");
    }


    public Result qqCallback() throws Exception {

        String realDomain = getRequest().getParameter("realDomain");
        if (realDomain != null) {
            return redirect(realDomain + "/lvxbang/third/qqCallback.jhtml?code=" + getRequest().getParameter("code") + "&state=" + getRequest().getParameter("state") + "&");
        }
        OpenLoginInfo openLoginInfo = ThirdPartyLoginUtil.getQQBindInfo(getRequest());
        Member user = thirdPartyLonginService.login(openLoginInfo.openId, ThirdPartyUserType.qq, null);

        if (user == null) {
            user = thirdPartyLonginService.create(openLoginInfo);
        }

        getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, user);
        getSession().setAttribute("account", user.getAccount());
        getSession().setAttribute("staffName", user.getUserName());

        if (StringUtils.hasText(user.getNickName())) {
            getSession().setAttribute("userName", user.getNickName());
        } else {
            getSession().setAttribute("userName", user.getUserName());
        }

        return redirect("/lvxbang/third/success.jhtml");
    }

    public Result weibo() throws WeiboException {
        return redirect(weiboAuthorize("code", null, "all", getRequest()));
    }

    public String weiboAuthorize(String responseType, String state, String scope, ServletRequest request) throws WeiboException {
        String redirectUri = RedirectUriUtil.getRedirectUri(request, WeiboConfig.getValue("redirect_URI").trim());
        return WeiboConfig.getValue("authorizeURL").trim() + "?client_id=" + WeiboConfig.getValue("client_ID").trim() + "&redirect_uri=" + redirectUri + "&response_type=" + responseType + "&state=" + state + "&scope=" + scope;
    }

    public Result weiboCallback() throws Exception {

        OpenLoginInfo openLoginInfo = ThirdPartyLoginUtil.getSinaWeiboBindInfo(getRequest());
        Member user = thirdPartyLonginService.login(openLoginInfo.openId, ThirdPartyUserType.weibo, null);

        if (user == null) {
            user = thirdPartyLonginService.create(openLoginInfo);
        }

        getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, user);
        getSession().setAttribute("account", user.getAccount());
        getSession().setAttribute("staffName", user.getUserName());

        if (StringUtils.hasText(user.getNickName())) {
            getSession().setAttribute("userName", user.getNickName());
        } else {
            getSession().setAttribute("userName", user.getUserName());
        }

        return redirect("/lvxbang/third/success.jhtml");
    }

    public Result lastUrl() {
        String url = "/lvxbang/index/index.jhtml";
        if (getSession().getAttribute(UserConstants.FORWARD_URL) != null) {
            url = getSession().getAttribute(UserConstants.FORWARD_URL).toString();
            if (url.contains("/lvxbang/login/login.jhtml") || url.contains("/lvxbang/register/registerr.jhtml") || url.contains("/lvxbang/login/forgotPassword.jhtml")) {
                url = "/lvxbang/index/index.jhtml";
            }
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("userName", getSession().getAttribute("userName"));
        result.put("url", url);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result loginData() {
        map.put("success", true);
        map.put("userName", getSession().getAttribute("userName"));

        map.put("userId", ((User) getSession().getAttribute(UserConstans.CURRENT_LOGIN_USER)).getId());
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    public Result success() {
        return dispatch();
    }

    private String getState() {
        Random random = new Random();
        return Integer.toHexString(random.nextInt()) + Integer.toHexString(random.nextInt());
    }

}
