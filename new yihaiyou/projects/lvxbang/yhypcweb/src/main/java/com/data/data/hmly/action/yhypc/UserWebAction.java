package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.yhypc.response.SimpleUser;
import com.data.data.hmly.enums.Gender;
import com.data.data.hmly.enums.ThirdPartyUserType;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.common.YhyMsgService;
import com.data.data.hmly.service.entity.*;
import com.data.data.hmly.service.lvxbang.ThirdPartyLonginService;
import com.data.data.hmly.service.order.util.MsgTemplateKey;
import com.data.data.hmly.service.user.TouristService;
import com.data.data.hmly.service.user.entity.Tourist;
import com.data.data.hmly.service.user.entity.enums.TouristPeopleType;
import com.data.data.hmly.service.user.entity.enums.TouristStatus;
import com.data.data.hmly.service.user.vo.OpenLoginInfo;
import com.data.data.hmly.util.GenValidateCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gson.oauth.Oauth;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.CouponCodeUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.util.Strings;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2017-01-13,0013.
 */
public class UserWebAction extends YhyAction {

    private static Log log = LogFactory.getLog(UserWebAction.class);

    private Map<String, Object> result = new HashMap<String, Object>();
    private String account;
    private String password;
    private String mobile;
    private Boolean autoLogin = false;

    @Resource
    private TouristService touristService;
    @Resource
    private MemberService memberService;
    @Resource
    private YhyMsgService yhyMsgService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private ThirdPartyLonginService thirdPartyLonginService;

    private final ObjectMapper mapper = new ObjectMapper();

    public String json;

    public Member user;

    public User accountUser;

    public Result checkLogin() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }

        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    // 登陆(vaild)
    public Result dologin() {
        final HttpServletRequest request = getRequest();
        String vcode = (String) getParameter("vcode");
        String validateCode = (String) getSession().getAttribute("loginCheckNum");
        if (!StringUtils.hasText(vcode) || !vcode.equals(validateCode)) {
            result.put("success", false);
            result.put("msg", "验证码不正确");
            return json(JSONObject.fromObject(result));
        }
        if (!StringUtils.hasText(account)) {
            result.put("success", false);
            result.put("msg", "用户名不可为空!");
            return json(JSONObject.fromObject(result));
        }
        if (!StringUtils.hasText(password)) {
            result.put("success", false);
            result.put("msg", "密码不可为空!");
            return json(JSONObject.fromObject(result));
        }
        Member member = memberService.findByAccount(account);
        if (member == null) {
            result.put("msg", "用户不存在");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!UserStatus.activity.equals(member.getStatus())) {
            result.put("msg", "用户未激活(锁定或删除)");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        password = Strings.toUpperCase(password);
        if (!password.equals(member.getPassword())) {
            result.put("msg", "用户名或者密码错误");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        // login success
        member.setLastLoginTime(new Date());
        // auto login
        if (autoLogin) {
            String autoLoginCode = CouponCodeUtils.getCodeWithoutSplit();
            member.setAutoLoginCode(autoLoginCode);
            member.setIsAutoLogin(true);
            result.put("alc", autoLoginCode);
            memberService.update(member);
        }
        getSession().setAttribute("LOGIN_USER", member);
        result.put("success", true);
        result.put("LOGIN_USER", member);
        JsonConfig jsonConfig = JsonFilter.getExcludeConfig("password", "payPassword", "autoLoginCode", "isAutoLogin", "shenzhouAccessToken", "shenzhouRefreshToken", "shenzhouTokenDate", "idNumber", "unionId", "bankNo");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result validateUser() {
        String neUserId = (String) getParameter("neUserId");
        boolean isExists = sysUserService.isExistsAccount(accountUser, neUserId);
        if (!isExists) {
            result.put("notExisted", true);
        } else {
            result.put("notExisted", false);
        }
        simpleResult(result, true, null);
        return jsonResult(result);

    }

    public Result noLogin() {
        result.put("success", false);
        result.put("nologin", true);
        result.put("errorMsg", "请先登录");
        return jsonResult(result);
    }

    public Result doLogin() {
        final HttpServletRequest request = getRequest();
        String vcode = (String) getParameter("vcode");
        String validateCode = (String) getSession().getAttribute("loginCheckNum");
        if (!StringUtils.hasText(vcode) || !vcode.equals(validateCode)) {
            result.put("success", false);
            result.put("msg", "验证码不正确");
            return json(JSONObject.fromObject(result));
        }
        if (!StringUtils.hasText(account)) {
            result.put("success", false);
            result.put("msg", "用户名不可为空!");
            return json(JSONObject.fromObject(result));
        }
        if (!StringUtils.hasText(password)) {
            result.put("success", false);
            result.put("msg", "密码不可为空!");
            return json(JSONObject.fromObject(result));
        }
        Member member = memberService.findByAccount(account);
        if (member == null) {
            result.put("msg", "用户不存在");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!UserStatus.activity.equals(member.getStatus())) {
            result.put("msg", "用户未激活(锁定或删除)");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        password = Strings.toUpperCase(password);
        if (!password.equals(member.getPassword())) {
            result.put("msg", "用户名或者密码错误");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        // login success
        member.setLastLoginTime(new Date());
        // auto login
        if (autoLogin) {
            String autoLoginCode = CouponCodeUtils.getCodeWithoutSplit();
            member.setAutoLoginCode(autoLoginCode);
            member.setIsAutoLogin(true);
            result.put("alc", autoLoginCode);
            memberService.update(member);
        }
        getSession().setAttribute("LOGIN_USER", member);
        result.put("success", true);
        result.put("LOGIN_USER", member);
        JsonConfig jsonConfig = JsonFilter.getExcludeConfig("password", "payPassword", "autoLoginCode", "isAutoLogin", "shenzhouAccessToken", "shenzhouRefreshToken", "shenzhouTokenDate", "idNumber", "unionId", "bankNo");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result autoLogin() {
        String id = (String) getParameter("id");
        String alc = (String) getParameter("alc");
        if (!StringUtils.hasText(id)) {
            result.put("success", false);
            log.error("自动登录失败, 用户id错误或为空");
            return json(JSONObject.fromObject(result));
        }
        if (!StringUtils.hasText(alc)) {
            result.put("success", false);
            log.error("自动登录失败, 安全校验码为空");
            return json(JSONObject.fromObject(result));
        }
        Member member = memberService.get(Long.parseLong(id));
        if (member != null) {
            String autoLoginCode = member.getAutoLoginCode();
            log.warn("WEB自动登录安全校验码: " + alc);
            log.warn("用户自动登录安全校验码: " + autoLoginCode);
            log.warn("用户自动登录标记: " + member.getIsAutoLogin().toString());
            if (member.getIsAutoLogin() && StringUtils.hasText(autoLoginCode) && alc.equals(autoLoginCode)) {
                getSession().setAttribute("LOGIN_USER", member);
                result.put("success", true);
                result.put("LOGIN_USER", member);
                JsonConfig jsonConfig = JsonFilter.getExcludeConfig("password", "payPassword", "autoLoginCode", "isAutoLogin", "shenzhouAccessToken", "shenzhouRefreshToken", "shenzhouTokenDate", "idNumber", "unionId", "bankNo");
                log.warn("用户自动登录成功");
                return json(JSONObject.fromObject(result, jsonConfig));
            } else {
                log.error("自动登录失败, 用户自动登录安全校验码错误或为空");
                // clear auto login info
                member.setIsAutoLogin(false);
                member.setAutoLoginCode(null);
                memberService.update(member);
                result.put("success", false);
                return json(JSONObject.fromObject(result));
            }
        } else {
            result.put("success", false);
            log.error("自动登录失败, 用户不存在或用户状态不为自动登录");
            return json(JSONObject.fromObject(result));
        }
    }

    public Result wechatCallback() {
        final HttpServletRequest request = getRequest();
        // 登录成功回调地址, 最后一次回调
        String redirectUrl = request.getParameter("returnUrl");
        if (redirectUrl.contains(".xml") || redirectUrl.contains(".properties")) {
            return redirect("/");
        }
        try {
            String wxAppId = propertiesManager.getString("WEB_WX_APP_ID");
            String wxAppSecret = propertiesManager.getString("WEB_WX_APP_SECRET");
            Long wxAccountId = Long.parseLong(propertiesManager.getString("WEBCHAT_ACCOUNT_ID"));
            if (!StringUtils.hasText(wxAppId) || !StringUtils.hasText(wxAppSecret)) {
                log.error("请配置微信开放平台相关信息!");
                return redirect("/");
            }
            Oauth oauth = new Oauth(wxAppId, wxAppSecret);
            // 使用code换取access_token
            JSONObject json = JSONObject.fromObject(oauth.getToken(getRequest().getParameter("code")));
            String openId = json.getString("openid");
            String acceccTokern = json.getString("access_token");
            // 通过access token换取用户信息
            JSONObject wxUserInfo = JSONObject.fromObject(oauth.getUserInfo(acceccTokern, openId));
            String nickName = wxUserInfo.getString("nickname");
            String unionId = wxUserInfo.getString("unionid");
            String avatar = wxUserInfo.getString("headimgurl");
            // 组装用户信息
            OpenLoginInfo openLoginInfo = new OpenLoginInfo(openId, ThirdPartyUserType.weixin, nickName, avatar, unionId);
            // 查询微信用户是否已授权登录过
            Member member = memberService.findByUnionId(unionId);
            if (member != null) {
                // 更新
                member = thirdPartyLonginService.update(member, openLoginInfo, wxAccountId);
            } else {
                // 新增
                member = thirdPartyLonginService.create(openLoginInfo, wxAccountId);
            }
            // 保存会话
            getSession().setAttribute("LOGIN_USER", member);
            // 返回地址
            return redirect(redirectUrl);
        } catch (Exception e) {
            log.error("微信登录获取用户信息异常", e);
            return redirect("/");
        }
    }

    public Result doLogout() {
        Member member = memberService.get(((Member) getSession().getAttribute("LOGIN_USER")).getId());
        if (member != null) {
            member.setAutoLoginCode(null);
            member.setIsAutoLogin(false);
            memberService.update(member);
        }
        getSession().removeAttribute("LOGIN_USER");
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    //注册
    public Result doPreRegister() {
        final HttpServletRequest request = getRequest();
        if (!StringUtils.hasText(account)) {
            result.put("success", false);
            result.put("msg", "用户名不可为空!");
            return json(JSONObject.fromObject(result));
        }
        if (!StringUtils.isMobile(mobile)) {
            result.put("success", false);
            result.put("msg", "手机号码不正确!");
            return json(JSONObject.fromObject(result));
        }
        if (!StringUtils.hasText(password)) {
            result.put("success", false);
            result.put("msg", "密码不可为空!");
            return json(JSONObject.fromObject(result));
        }
        String vcode = (String) getParameter("vcode");
        String validateCode = (String) getSession().getAttribute("checkNum");
        if (!StringUtils.hasText(vcode) || !vcode.equals(validateCode)) {
            result.put("success", false);
            result.put("msg", "验证码不正确");
            return json(JSONObject.fromObject(result));
        }
        // 用户名唯一校验
        Member existMember = memberService.findByAccount(StringUtils.htmlEncode(account));
        if (existMember != null) {
            result.put("success", false);
            result.put("msg", "用户名已被注册!");
            return json(JSONObject.fromObject(result));
        }
        existMember = memberService.findByMobile(mobile);
        if (existMember != null) {
            result.put("success", false);
            result.put("msg", "手机号已被注册!");
            return json(JSONObject.fromObject(result));
        }
        // send sms code
        String timeout = propertiesManager.getString("REG_SMS_CODE_TIMEOUT");
        this.sendSMSCode(mobile, MsgTemplateKey.WEB_REGISTER_SMS_CODE, timeout);
        result.put("success", true);
        result.put("mobile", mobile);
        //result.put("timeout", timeout);
        getSession().setAttribute("registerMobile", mobile);
        return json(JSONObject.fromObject(result));
    }

    public Result doRegister() {
        long timeout = Long.parseLong(propertiesManager.getString("REG_SMS_CODE_TIMEOUT"));
        String webSmsCode = (String) getParameter("smsCode");
        String smsCode = (String) getSession().getAttribute("smsCode");
        long smsCodeStamp = (Long) getSession().getAttribute("smsCodeStamp");
        String registerMobile = (String) getSession().getAttribute("registerMobile");
        long nowStamp = System.currentTimeMillis();
        if ((nowStamp - smsCodeStamp) > (timeout * 1000)) {
            result.put("success", false);
            result.put("msg", "手机验证码失效,请重新获取");
            return json(JSONObject.fromObject(result));
        }
        if (!webSmsCode.equals(smsCode)) {
            result.put("success", false);
            result.put("msg", "手机验证码错误!");
            return json(JSONObject.fromObject(result));
        }
        if (!registerMobile.equals(mobile)) {
            result.put("success", false);
            result.put("msg", "注册手机号错误!");
            return json(JSONObject.fromObject(result));
        }
        Member member = new Member();
        member.setAccount(StringUtils.htmlEncode(account));
        member.setUserName(StringUtils.htmlEncode(account));
        member.setNickName(StringUtils.htmlEncode(account));
        member.setPassword(Strings.toUpperCase(password));
        member.setMobile(StringUtils.htmlEncode(mobile));
        member.setTelephone(StringUtils.htmlEncode(mobile));
        member.setGender(Gender.secret);
        member.setJifen(0F);
        member.setStatus(UserStatus.activity);
        member.setIsInBlackList(false);
        member.setIsAutoLogin(false);
        member.setUserType(UserType.USER);
        member.setStatus(UserStatus.activity);
        SysSite sysSite = new SysSite();
        sysSite.setId(1L);
        member.setSysSite(sysSite);
        member.setBalance(0D);
        memberService.save(member);
        result.put("success", true);
        getSession().removeAttribute("checkNum");
        getSession().removeAttribute("smsCode");
        getSession().removeAttribute("smsCodeStamp");
        return json(JSONObject.fromObject(result));
    }

    public Result resendRegCode() {
        final HttpServletRequest request = getRequest();
        String vcode = request.getParameter("vcode");
        String validateCode = (String) getSession().getAttribute("checkNum");
        if (!StringUtils.hasText(vcode) || !vcode.equals(validateCode)) {
            result.put("success", false);
            result.put("msg", "图形验证码不正确, 请返回重新注册!");
            return json(JSONObject.fromObject(result));
        }
        String mobile = (String) getSession().getAttribute("registerMobile");
        if (!StringUtils.hasText(mobile)) {
            result.put("success", false);
            result.put("msg", "操作超时, 请返回重新注册!");
            return json(JSONObject.fromObject(result));
        }
        String timeout = propertiesManager.getString("REG_SMS_CODE_TIMEOUT");
        this.sendSMSCode(mobile, MsgTemplateKey.WEB_REGISTER_SMS_CODE, timeout);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result resendForgetPwdCode() {
        final HttpServletRequest request = getRequest();
        String vcode = request.getParameter("vcode");
        String validateCode = (String) getSession().getAttribute("checkNum");
        if (!StringUtils.hasText(vcode) || !vcode.equals(validateCode)) {
            result.put("success", false);
            result.put("msg", "图形验证码不正确, 请返回重新找回密码!");
            return json(JSONObject.fromObject(result));
        }
        String timeout = propertiesManager.getString("REG_SMS_CODE_TIMEOUT");
        String mobile = (String) getSession().getAttribute("forgetPwdMobile");
        this.sendSMSCode(mobile, MsgTemplateKey.WEB_FORGET_PWD_SMS_CODE, timeout);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result doStartForgetPwd() {
        final HttpServletRequest request = getRequest();
        if (!StringUtils.hasText(account)) {
            result.put("success", false);
            result.put("msg", "用户名不可为空");
            return json(JSONObject.fromObject(result));
        }
        String vcode = request.getParameter("vcode");
        String checkNum = (String) getSession().getAttribute("checkNum");
        if (!StringUtils.hasText(vcode) || !vcode.equals(checkNum)) {
            result.put("success", false);
            result.put("msg", "验证码不正确");
            return json(JSONObject.fromObject(result));
        }
        Member member = memberService.findByAccount(account);
        if (member == null) {
            result.put("success", false);
            result.put("msg", "用户不存在");
            return json(JSONObject.fromObject(result));
        }
        String mobile = member.getTelephone();
        if (!StringUtils.hasText(mobile)) {
            result.put("success", false);
            result.put("msg", "您没有绑定手机号, 无法找回密码");
            return json(JSONObject.fromObject(result));
        }
        // sent sms validate code
        String timeout = propertiesManager.getString("REG_SMS_CODE_TIMEOUT");
        String forgetPwdCheckCode = CouponCodeUtils.getCodeWithoutSplit();
//        this.sendSMSCode(mobile, MsgTemplateKey.WEB_FORGET_PWD_SMS_CODE, timeout);
        getSession().setAttribute("forgetPwdAccount", account);
        getSession().setAttribute("forgetPwdMobile", mobile);
        getSession().setAttribute("forgetPwdCheckCode", forgetPwdCheckCode);
        result.put("timeout", timeout);
        result.put("success", true);
        result.put("mobile", mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        result.put("fpcCode", forgetPwdCheckCode);
        return json(JSONObject.fromObject(result));
    }

    public Result doCheckForgetPassword() {
        final HttpServletRequest request = getRequest();
        long timeout = Long.parseLong(propertiesManager.getString("REG_SMS_CODE_TIMEOUT"));
        String webSmsCode = request.getParameter("smsCode");
        String smsCode = (String) getSession().getAttribute("smsCode");
        String fpcCode = request.getParameter("fpcCode");
        String forgetPwdCheckCode = (String) getSession().getAttribute("forgetPwdCheckCode");
        long smsCodeStamp = (Long) getSession().getAttribute("smsCodeStamp");
        long nowStamp = System.currentTimeMillis();
        if ((nowStamp - smsCodeStamp) > (timeout * 1000)) {
            result.put("success", false);
            result.put("msg", "手机验证码失效,请重新获取");
            return json(JSONObject.fromObject(result));
        }
        if (!fpcCode.equals(forgetPwdCheckCode)) {
            result.put("success", false);
            result.put("msg", "安全校验失败, 请返回重新操作");
            return json(JSONObject.fromObject(result));
        }
        if (!webSmsCode.equals(smsCode)) {
            result.put("success", false);
            result.put("msg", "手机验证码错误");
            return json(JSONObject.fromObject(result));
        }
        result.put("fpcCode", forgetPwdCheckCode);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result doResetPwd() {
        final HttpServletRequest request = getRequest();
        String forgetPwdAccount = (String) getSession().getAttribute("forgetPwdAccount");
        String webSmsCode = request.getParameter("smsCode");
        String smsCode = (String) getSession().getAttribute("smsCode");
        String fpcCode = request.getParameter("fpcCode");
        String forgetPwdCheckCode = (String) getSession().getAttribute("forgetPwdCheckCode");
        if (!StringUtils.hasText(forgetPwdAccount) || !StringUtils.hasText(forgetPwdCheckCode)) {
            result.put("success", false);
            result.put("msg", "操作超时, 请返回重新操作");
            return json(JSONObject.fromObject(result));
        }
        if (!StringUtils.hasText(fpcCode) || !fpcCode.equals(forgetPwdCheckCode)) {
            result.put("success", false);
            result.put("msg", "安全校验失败, 请返回重新操作");
            return json(JSONObject.fromObject(result));
        }
        Member member = memberService.findByAccount(forgetPwdAccount);
        if (member == null) {
            result.put("success", false);
            result.put("msg", "用户不存在");
            return json(JSONObject.fromObject(result));
        }
        if (!webSmsCode.equals(smsCode)) {
            result.put("success", false);
            result.put("msg", "手机验证码错误");
            return json(JSONObject.fromObject(result));
        }
        member.setPassword(Strings.toUpperCase(password));
        memberService.update(member);
        result.put("success", true);
        // clear session info
        getSession().removeAttribute("forgetPwdAccount");
        getSession().removeAttribute("forgetPwdMobile");
        getSession().removeAttribute("forgetPwdCheckCode");
        return json(JSONObject.fromObject(result));
    }

    public Result doChangPwd() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        final HttpServletRequest request = getRequest();
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        oldPwd = Strings.toUpperCase(oldPwd);
        newPwd = Strings.toUpperCase(newPwd);
        String nowPwd = memberService.findPwdById(loginUser.getId());
        if (!oldPwd.equals(nowPwd)) {
            result.put("success", false);
            result.put("msg", "旧密码不正确");
            return json(JSONObject.fromObject(result));
        }
        if (newPwd.equals(nowPwd)) {
            result.put("success", false);
            result.put("msg", "新密码不可与旧密码一致");
            return json(JSONObject.fromObject(result));
        }
        loginUser.setPassword(newPwd);
        memberService.update(loginUser);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result doSetPayPwd() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        final HttpServletRequest request = getRequest();
        Member member = memberService.get(loginUser.getId());
        String oldPayPwd = request.getParameter("oldPayPwd");
        String payPwd = request.getParameter("payPwd");
        oldPayPwd = Strings.toUpperCase(oldPayPwd);
        payPwd = Strings.toUpperCase(payPwd);
        if (StringUtils.hasText(member.getPayPassword()) && !oldPayPwd.equals(member.getPayPassword())) {
            result.put("success", false);
            result.put("msg", "当前支付密码不正确!");
            return json(JSONObject.fromObject(result));
        }
        if (StringUtils.hasText(member.getPayPassword()) && payPwd.equals(member.getPayPassword())) {
            result.put("success", false);
            result.put("msg", "新旧支付密码不能相同!");
            return json(JSONObject.fromObject(result));
        }
        member.setPayPassword(payPwd);
        memberService.update(member);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result addTourist() throws IOException {
        Tourist tourist = mapper.readValue(json, Tourist.class);
        user = getLoginUser();
        tourist.setUser(user);
        tourist.setStatus(TouristStatus.normal);
        tourist.setPeopleType(TouristPeopleType.ADULT);
        tourist.setGender(Gender.male);
        touristService.saveTourist(tourist);
        result.put("success", true);
        return jsonResult(result);
    }

    public Result touristList() {
        user = getLoginUser();
        List<Tourist> touristList = touristService.getMyTourist(user, null, null);
        result.put("success", true);
        result.put("touristList", touristList);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    public Result userInfo() {
        user = getLoginUser();
        user = memberService.get(user.getId());
        SimpleUser response = new SimpleUser(user);
        result.put("user", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    private void sendSMSCode(String mobile, String templateKey, String timeout) {
        // send sms code
        Map<String, Object> data = new HashMap<String, Object>();
        String smsCode = GenValidateCode.createSMSCode(true, 6);
        data.put("smsCode", smsCode);
        data.put("timeout", timeout);
        // @WEB_SMS
        yhyMsgService.doSendSMS(data, mobile,  templateKey);
        getSession().setAttribute("smsCode", smsCode);
        getSession().setAttribute("smsCodeStamp", System.currentTimeMillis());
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(Boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public User getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(User accountUser) {
        this.accountUser = accountUser;
    }
}
