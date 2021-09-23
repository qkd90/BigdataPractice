package com.data.data.hmly.action.user;

import com.data.data.hmly.service.SysSiteService;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.user.util.SmsUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.MD5;
import com.zuipin.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by guoshijie on 2015/10/22.
 */
public class RegisterWebAction extends JxmallAction implements ModelDriven<Member> {

    @Resource
    private MemberService memberService;
    @Resource
    private SysSiteService sysSiteService;

    private Integer smsCode;
    private String rePassword;
    private Member member = new Member();
    private String captcha;
    private String validateResult = "";


    public Result personal() {

        return dispatch();
    }

    public Result validatePhone() {
        String account = (String) getParameter("account");
        SysSite sysSite = (SysSite) getSession().getAttribute(UserConstans.SYSTEM_SITE_INFORMATION);
        Member oldUser = memberService.findByAccount(account, sysSite);
        if (oldUser != null) {
            return text("该手机号已注册，请重新输入");
        }
        return text("valid");
    }

    public Result validateCaptcha() {
        String sysCheckCode = (String) getSession().getAttribute("checkNum");
        String captcha = (String) getParameter("captcha");
        if (sysCheckCode != null && sysCheckCode.equals(captcha)) {
            return text("valid");
        }
        return text("验证码错误");
    }

    public Result validateSmsCode() {
        if (!isSmsCodeValid(smsCode)) {
            return text("手机验证码错误");
        }

        return text("valid");
    }

    public boolean validatePersonalInfo() {
        if (StringUtils.isBlank(member.getAccount())) {
            validateResult = "请填写手机号";
            return false;
        }
        SysSite sysSite = (SysSite) getSession().getAttribute(UserConstans.SYSTEM_SITE_INFORMATION);
        Member oldUser = memberService.findByAccount(member.getAccount(), sysSite);
        if (oldUser != null) {
            validateResult = "该手机号已注册，请重新输入";
            return false;
        }

        //todo: other password rules
        if (!member.getPassword().equals(rePassword)) {
            validateResult = "该手机号已注册，请重新输入";
            return false;
        }

//		if (!isSmsCodeValid(smsCode)) {
//			validateResult = "该手机号已注册，请重新输入";
//			return text("手机验证码错误");
//		}
        validateResult = "";
        return true;
    }

    public Result step2() {

        return dispatch();
    }

    public Result save() {
        boolean validate = validatePersonalInfo();
        if (!validate) {
            return redirect("/user/register/personal.jhtml?validateResult=" + validateResult);
        }
        String pwd = MD5.caiBeiMD5(member.getPassword());
        member.setPassword(pwd);
        SysSite sysSite = (SysSite) getSession().getAttribute(UserConstans.SYSTEM_SITE_INFORMATION);
        if (sysSite == null) {
            sysSite = sysSiteService.getDefaultSite();
        }
        member.setSysSite(sysSite);
        member.setStatus(UserStatus.activity);
        boolean result = memberService.save(member);
        if (result) {
            return dispatch();
        }
        return redirect("/user/register/personal.jhtml");
    }

    public Result validateAccount() {
        return dispatch();
    }

    public Result finish() {

        return dispatch();
    }

    public Result sendVerificationSms() {
        SmsUtil.sendRegisterMessage(member.getAccount());
        return text("success");
    }

    private boolean isSmsCodeValid(Integer code) {
        if (code == null) {
            return false;
        }
        Integer sentCode = (Integer) getRequest().getSession().getAttribute(SmsUtil.SMS_CODE_KEY);
        if (code.equals(sentCode)) {
            getRequest().getSession().setAttribute(SmsUtil.SMS_CODE_KEY, "");
            return true;
        }
        return false;
    }

    public Integer getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(Integer smsCode) {
        this.smsCode = smsCode;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getValidateResult() {
        return validateResult;
    }

    public void setValidateResult(String validateResult) {
        this.validateResult = validateResult;
    }

    @Override
    public Member getModel() {
        return member;
    }
}
