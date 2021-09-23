package com.data.data.hmly.action.mobile;

import com.data.data.hmly.action.mobile.request.SimpleUser;
import com.data.data.hmly.action.mobile.response.AccountLogResponse;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.MsgHistoryService;
import com.data.data.hmly.service.balance.AccountLogService;
import com.data.data.hmly.service.balance.entity.AccountLog;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SendingMsgHis;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.lvxbang.ThirdPartyLonginService;
import com.data.data.hmly.service.lxbcommon.FeedbackService;
import com.data.data.hmly.service.lxbcommon.entity.Feedback;
import com.data.data.hmly.service.lxbcommon.entity.enums.FeedBackStatus;
import com.data.data.hmly.service.user.entity.enums.ThirdPartyUserType;
import com.data.data.hmly.service.user.util.SmsUtil;
import com.data.data.hmly.service.user.vo.OpenLoginInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.gson.bean.UserInfo;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.MD5;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-13,0013.
 */
public class UserWebAction extends MobileBaseAction {
    @Resource
    private MemberService memberService;
    @Resource
    private MsgHistoryService msgHistoryService;
    @Resource
    private FeedbackService feedbackService;
    @Resource
    private ThirdPartyLonginService thirdPartyLonginService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private AccountLogService accountLogService;

    private final ObjectMapper mapper = new ObjectMapper();

    public Member user;
    public String account;
    public String password;
    public String checkNum;
    public String smsCode;
    public String content;
    public String json;
    public String userInfo;

    private final Log log = LogFactory.getLog(UserWebAction.class);

    @AjaxCheck
    public Result appLogin() throws IOException, LoginException {
        Member user = getLoginUser();
        if (user == null) {
            UserInfo userInfo = mapper.readValue(json, UserInfo.class);
            Long accountId = propertiesManager.getLong("WEBCHAT_ACCOUNT_ID");
            OpenLoginInfo openLoginInfo = null;
            try {
                openLoginInfo = new OpenLoginInfo(userInfo.getOpenid(), ThirdPartyUserType.weixin, userInfo.getNickname(), userInfo.getHeadimgurl(), userInfo.getUnionid());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
//                String accessToken = json.getString("access_token");
//                JSONObject userInfo = JSONObject.fromObject(oauth.getUserInfo(accessToken, openid));
//                String nickname = userInfo.getString("nickname");
            Member member = memberService.findByUnionId(userInfo.getUnionid());
            if (member != null) { // 存在，更新
                if (openLoginInfo != null) {
                    member = thirdPartyLonginService.update(member, openLoginInfo, accountId);
                }
            } else {    // 不存在，新增
                member = thirdPartyLonginService.create(openLoginInfo, accountId);
            }
            getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, member);
            user = member;
        }
        SimpleUser response = new SimpleUser(user);
        result.put("user", response);
        result.put("success", true);
        return jsonResult(result);
    }

    /**
     * 登录
     *
     * @return
     */
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

    /**
     * 验证用户
     *
     * @return
     */
    @AjaxCheck
    public Result checkUser() {
        SysSite sysSite = new SysSite();
        sysSite.setId(-1L);
        Member user = memberService.findByAccount(account, sysSite);
        Boolean exists = user != null;
        result.put("exists", exists);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result noLogin() {
        result.put("success", false);
        result.put("nologin", true);
        result.put("errorMsg", "请先登录");
        return jsonResult(result);
    }

    /**
     * 注册
     *
     * @return
     */
    @AjaxCheck
    public Result register() {
        Integer smsCodeKey = (Integer) getRequest().getSession().getAttribute(SmsUtil.SMS_CODE_KEY);
        if (smsCodeKey == null) {
            result.put("errMsg", "未发送验证码");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!smsCodeKey.toString().equals(smsCode)) {
            result.put("errMsg", "验证码错误");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }

        SysSite sysSite = new SysSite();
        sysSite.setId(-1L);
        Member member = memberService.findByAccount(account, sysSite);
        if (member != null) {
            result.put("errMsg", "该手机号已存在！");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }

        member = new Member();
        member.setAccount(account);
        member.setTelephone(account);
        member.setPassword(MD5.caiBeiMD5(password));
        member.setStatus(UserStatus.activity);
        member.setSysSite(sysSite);
        if (!memberService.save(member)) {
            result.put("errMsg", "注册失败");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        member.setNickName("旅行帮" + member.getId());
        member.setUserName("旅行帮" + member.getId());
        memberService.update(member);
        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, member);
        result.put("id", member.getId());
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 忘记密码
     *
     * @return
     */
    @AjaxCheck
    public Result forgotPassword() {
        Integer smsCodeKey = (Integer) getRequest().getSession().getAttribute(SmsUtil.SMS_CODE_KEY);
        if (smsCodeKey == null) {
            result.put("errMsg", "未发送验证码");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!smsCodeKey.toString().equals(smsCode)) {
            result.put("errMsg", "验证码错误");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }

        SysSite sysSite = new SysSite();
        sysSite.setId(-1L);
        Member member = memberService.findByAccount(account, sysSite);
        if (member == null) {
            result.put("errMsg", "用户不存在");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (member.getStatus() != UserStatus.activity) {
            result.put("errMsg", "用户被锁定或者被删除");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        member.setPassword(MD5.caiBeiMD5(password));
        memberService.update(member);
        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, member);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 修改密码
     *
     * @return
     */
    @AjaxCheck
    public Result updatePassword() {
        SysSite sysSite = new SysSite();
        sysSite.setId(-1L);
        Member user = memberService.findByAccount(account, sysSite);
        if (user == null) {
//            errMsg = "用户不存在";
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (user.getStatus() != UserStatus.activity) {
//            errMsg = "用户被锁定或者被删除";
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }

        SendingMsgHis search = new SendingMsgHis();
        search.setReceivernum(account);
        search.setContext("您的验证码为：");
        Page page = new Page(1, 1);
        List<SendingMsgHis> sendingMsgList = msgHistoryService.findMsgHisList(search, page);
        if (sendingMsgList.isEmpty()) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        SendingMsgHis sendingMsg = sendingMsgList.get(0);
        Date sendTime = sendingMsg.getSendtime();
        if (DateUtils.getDateDiffMin(new Date(), sendTime) > 5) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        String code = sendingMsg.getContext().replace("您的验证码为：", "").replace(", 请在5分钟内使用", "");
        if (!code.equals(smsCode)) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }

        user.setPassword(MD5.caiBeiMD5(password));
        memberService.update(user);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 发送短信验证码
     *
     * @return
     */
    @AjaxCheck
    public Result sendSmsCode() {
        String sysCheckCode = (String) getSession().getAttribute("checkNum");
        if (sysCheckCode == null || !sysCheckCode.equals(checkNum)) {
            result.put("success", false);
            result.put("errMsg", "验证码错误");
            return json(JSONObject.fromObject(result));
        }
        SmsUtil.sendRegisterMessage(account);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 验证短信验证码
     *
     * @return
     */
    @AjaxCheck
    public Result checkSmsCode() {
        Integer smsCodeKey = (Integer) getRequest().getSession().getAttribute(SmsUtil.SMS_CODE_KEY);
        if (smsCodeKey == null) {
            result.put("errMsg", "未发送验证码");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        if (!smsCodeKey.toString().equals(smsCode)) {
            result.put("errMsg", "验证码错误");
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 查询个人信息
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result detail() throws LoginException {
        user = getLoginUser();
        user = memberService.get(user.getId());
        SimpleUser response = new SimpleUser(user);
        result.put("user", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 修改个人信息
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result update() throws IOException, LoginException {
        SimpleUser simpleUser = mapper.readValue(json, SimpleUser.class);
        user = getLoginUser();
        user = simpleUser.toMember(user);
        memberService.update(user);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 退出
     *
     * @return
     */
    @AjaxCheck
    public Result logout() {
        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, null);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 验证登录
     *
     * @return
     */
    @AjaxCheck
    public Result checkLogin() throws LoginException {
        user = getLoginUser();
        if (user == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 意见反馈
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result feedback() throws LoginException {
        user = getLoginUser();
        Feedback newFeedback = new Feedback();
        newFeedback.setContact(user.getTelephone());
        newFeedback.setContent(content);
        newFeedback.setCreator(user);
        newFeedback.setCreateTime(new Date());
        newFeedback.setStatus(FeedBackStatus.OPEN);
        newFeedback.setDelFlag(0);
        feedbackService.save(newFeedback);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result balanceLog() throws LoginException {
        user = getLoginUser();
        AccountLog accountLog = new AccountLog();
        accountLog.setUser(user);
        List<AccountLog> accountLogList = accountLogService.findListByAccountLog(accountLog, "createTime");
        List<AccountLogResponse> accountLogResponseList = Lists.transform(accountLogList, new Function<AccountLog, AccountLogResponse>() {
            @Override
            public AccountLogResponse apply(AccountLog input) {
                return new AccountLogResponse(input);
            }
        });
        result.put("success", true);
        result.put("balanceLogList", accountLogResponseList);
        return json(JSONObject.fromObject(result));
    }
}
