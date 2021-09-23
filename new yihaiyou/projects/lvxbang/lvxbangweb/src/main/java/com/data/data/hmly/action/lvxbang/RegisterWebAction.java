package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.user.util.SmsUtil;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.MD5;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guoshijie on 2015/10/22.
 */
public class RegisterWebAction extends JxmallAction {

    @Resource
    private MemberService memberService;
//    @Resource
//    private UserExinfoService userExinfoService;

    private String account;
    private Integer smsCode;
    private String password;
    public String validateVlue;


    public Result registerr() {
        String url = getRequest().getHeader("referer");
        getSession().setAttribute(UserConstans.FORWARD_URL, url);
        return dispatch();
    }

    public Result validatePhone() {
//		SysSite sysSite = (SysSite) getSession().getAttribute(UserConstans.SYSTEM_SITE_INFORMATION);
        String sysCheckCode = (String) getSession().getAttribute("checkNum");
        if (sysCheckCode == null || !sysCheckCode.equals(validateVlue)) {
            result.put("success", false);
            result.put("errMsg", "验证码错误");
            return json(JSONObject.fromObject(result));
        }
        SysSite sysSite = new SysSite();
        sysSite.setId(-1L);
        Member oldUser = memberService.findByAccount(account, sysSite);
        Map<String, Object> map = new HashMap<String, Object>();
        if (oldUser != null) {
            map.put("success", false);
            map.put("errMsg", "该手机号已注册，请更换或马上登录");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }
        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    public Result validateCaptcha() {
        String sysCheckCode = (String) getSession().getAttribute("checkNum");
        String captcha = (String) getParameter("captcha");
        if (sysCheckCode != null && sysCheckCode.equals(captcha)) {
            return text("valid");
        }
        return text("验证码错误");
    }


    public Result sendVerificationSms() {
        String sysCheckCode = (String) getSession().getAttribute("checkNum");
        if (sysCheckCode == null || !sysCheckCode.equals(validateVlue)) {
            result.put("success", false);
            result.put("errMsg", "验证码错误");
            return json(JSONObject.fromObject(result));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            SmsUtil.sendRegisterMessage(account);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", "该手机号已注册，请更换或马上登录");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }
        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    private boolean isSmsCodeValid(Integer code) {
        if (code == null) {
            return false;
        }
        Object codeObj = getRequest().getSession().getAttribute(SmsUtil.SMS_CODE_KEY);
        if (codeObj == null) {
            return false;
        }
        Integer sentCode = Integer.parseInt(codeObj.toString());
        if (code.equals(sentCode)) {
            getRequest().getSession().setAttribute(SmsUtil.SMS_CODE_KEY, "");
            return true;
        }
        return false;
    }

    public Result doRegisterr() {
        //
        Map<String, Object> map = new HashMap<String, Object>();


        if (!isSmsCodeValid(smsCode)) {
            map.put("success", false);
            map.put("errMsg", "验证码错误");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }

        Member newMember = new Member();
        newMember.setAccount(account);
        newMember.setMobile(account);
//        newMember.setUserName(account);
        String pwd = MD5.caiBeiMD5(password);
        newMember.setPassword(pwd);
        newMember.setStatus(UserStatus.activity);
//        newMember.setUserName("新用户");
        SysSite sysSite = new SysSite();
        sysSite.setId(-1L);
        newMember.setSysSite(sysSite);
        newMember.setMobile(account);
        newMember.setTelephone(account);
        newMember.setBalance(0D);
        newMember.setIsInBlackList(false);
        boolean result = memberService.save(newMember);
        if (!result) {
            //
            map.put("success", false);
            map.put("errMsg", "注册失败");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        } else {
            //注册成功后才有ID
            newMember.setNickName("旅行帮" + newMember.getId());
            newMember.setUserName("旅行帮" + newMember.getId());
            memberService.update(newMember);
        }
//        UserExinfo userExinfo = new UserExinfo();
//        userExinfo.setUser(newMember);
//        userExinfo.setNickName();
//        userExinfo.setTelephone(account);
//        userExinfoService.save(userExinfo);
        //
        getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, newMember);
        getSession().setAttribute("account", newMember.getAccount());
        getSession().setAttribute("staffName", newMember.getUserName());
        getSession().setAttribute("userName", newMember.getUserName());

        String url = "";
        if (getSession().getAttribute(UserConstants.FORWARD_URL) == null) {
            url = "/lvxbang/index/index.jhtml";
        } else {
            url = getSession().getAttribute(UserConstants.FORWARD_URL).toString();
            if (url.indexOf("/lvxbang/login/login.jhtml") != -1 || url.indexOf("/lvxbang/register/registerr.jhtml") != -1 || url.indexOf("/lvxbang/login/forgotPassword.jhtml") != -1) {
                url = "/lvxbang/index/index.jhtml";
            }
        }
        map.put("url", url);
        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    public Result updatePassword() {

        Map<String, Object> map = new HashMap<String, Object>();

        if (!isSmsCodeValid(smsCode)) {
            map.put("success", false);
            map.put("errMsg", "验证码错误");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }
        boolean change = false;

        getSession().setAttribute(UserConstans.FORGOT_PWD_MESSAGE, "");
        String errMsg = "";
//        SysSite sysSite = (SysSite) getSession().getAttribute(UserConstans.SYSTEM_SITE_INFORMATION);
        SysSite sysSite = new SysSite();
        sysSite.setId(-1L);
        Member member = memberService.findByAccount(account, sysSite);
        if (member == null) {
            errMsg = "用户不存在";
        } else {
            if (member.getStatus() != UserStatus.activity) {
                errMsg = "用户被锁定或者被删除";
            } else {
                String pwd = MD5.caiBeiMD5(password);
                member.setPassword(pwd);
                memberService.update(member);
                change = true;
            }
        }

        if (change) {
            getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, member);
            String url = "/lvxbang/index/index.jhtml";
            if (getSession().getAttribute(UserConstants.FORWARD_URL) != null) {
                url = getSession().getAttribute(UserConstants.FORWARD_URL).toString();

            }
            if (url.indexOf("/lvxbang/login/login.jhtml") != -1 || url.indexOf("/lvxbang/register/registerr.jhtml") != -1 || url.indexOf("/lvxbang/login/forgotPassword.jhtml") != -1) {
                url = "/lvxbang/index/index.jhtml";
            }
            map.put("url", url);
            map.put("success", true);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        } else {
            getSession().setAttribute(UserConstans.FORGOT_PWD_MESSAGE, errMsg);
            map.put("success", false);
            map.put("errMsg", errMsg);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }
    }


    public Integer getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(Integer smsCode) {
        this.smsCode = smsCode;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


}
