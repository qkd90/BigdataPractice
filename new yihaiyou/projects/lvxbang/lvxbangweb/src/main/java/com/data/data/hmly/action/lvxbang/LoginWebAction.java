package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.SendingMsgService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SendingMsg;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.user.util.SmsUtil;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.MD5;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vacuity on 15/12/22.
 */
public class LoginWebAction extends LxbAction {

//    @Resource
//    private UserService userService;
    @Resource
    private MemberService memberService;
    @Resource
    private SendingMsgService sendingMsgService;


    private String account;
    private String password;
    private String newPassword;

    private Integer smsCode;

    private String captchaCode;

    private String rePassword;

    private String errMsg;

    public String validateVlue;

    private Map<String, Object> map = new HashMap<String, Object>();

    public Result login() {
        String url = getRequest().getHeader("referer");
        getSession().setAttribute(UserConstans.FORWARD_URL, url);
        getSession().setAttribute(UserConstans.LOGIN_FAIL_MESSAGE, "");
        return dispatch();
    }

    public Result forgotPassword() {
        String url = getRequest().getHeader("referer");
        getSession().setAttribute(UserConstans.FORWARD_URL, url);
        return dispatch();
    }


    public Result doLogin() {

        getSession().setAttribute(UserConstans.LOGIN_FAIL_MESSAGE, "");

        SysSite sysSite = new SysSite();
        sysSite.setId(-1L);
//        User user = userService.findByAccount(account, sysSite);
        Member user = memberService.findByAccount(account, sysSite);
        if (user == null) {
            errMsg = "用户不存在";
            return json(getFailMessage());
        }
        if (user.getStatus() != UserStatus.activity) {
            errMsg = "用户被锁定或者被删除";
            return json(getFailMessage());
        }

//        String pwd = Encryption.encryByAES(Constants.internalCode, password);
        String pwd = MD5.caiBeiMD5(password);

        if (!pwd.equals(user.getPassword())) {
            errMsg = "用户名或者密码错误";
            return json(getFailMessage());
        }

//        if (user instanceof SysUser) {
//            SysUser loginuser = (SysUser) user;
//            SysUnit unit = new SysUnit();
//            unit.setId(loginuser.getSysUnit().getId());
//            unit.setName(loginuser.getSysUnit().getName());
//            unit.setUnitType(loginuser.getSysUnit().getUnitType());
//            unit.setUnitNo(loginuser.getSysUnit().getUnitNo());
//            SysUnit companyUnit = new SysUnit();
//            if (loginuser.getSysUnit().getCompanyUnit() != null) {
//                companyUnit.setId(loginuser.getSysUnit().getCompanyUnit().getId());
//                companyUnit.setName(loginuser.getSysUnit().getCompanyUnit().getName());
//                companyUnit.setUnitType(loginuser.getSysUnit().getCompanyUnit().getUnitType());
//                companyUnit.setUnitNo(loginuser.getSysUnit().getCompanyUnit().getUnitNo());
//            }
//            unit.setCompanyUnit(companyUnit);
//            loginuser.setSysUnit(unit);
//        }
        getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, user);
        getSession().setAttribute("account", user.getAccount());
        getSession().setAttribute("staffName", user.getUserName());
//        login = true;

        String url = "/";
        if (getSession().getAttribute(UserConstants.FORWARD_URL) != null) {
            url = getSession().getAttribute(UserConstants.FORWARD_URL).toString();
            if (url.indexOf("/lvxbang/login/login.jhtml") != -1 || url.indexOf("/lvxbang/register/registerr.jhtml") != -1 || url.indexOf("/lvxbang/login/forgotPassword.jhtml") != -1) {
                url = "/";
            }
        }
        map.put("success", true);
        map.put("url", url);
//        if (user.getUserExinfo() != null) {
//            map.put("userName", StringUtils.isBlank(user.getUserExinfo().getNickName()) ? user.getAccount() : user.getUserExinfo().getNickName());
//            getSession().setAttribute("userName", StringUtils.isBlank(user.getUserExinfo().getNickName()) ? user.getAccount() : user.getUserExinfo().getNickName());
//        } else {
//            map.put("userName", user.getAccount());
//            getSession().setAttribute("userName", user.getAccount());
//        }
        if (StringUtils.isNotBlank(user.getNickName())) {
            map.put("userName", user.getNickName());
            getSession().setAttribute("userName", user.getNickName());
        } else if (StringUtils.isNotBlank(user.getUserName())) {
            map.put("userName", user.getUserName());
            getSession().setAttribute("userName", user.getUserName());
        } else {
            map.put("userName", user.getMobile());
            getSession().setAttribute("userName", user.getMobile());
        }

        map.put("userId", user.getId());
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);


    }

    private JSONObject getFailMessage() {
        getSession().setAttribute(UserConstans.LOGIN_FAIL_MESSAGE, errMsg);
        map.put("success", false);
        map.put("errMsg", errMsg);
        return JSONObject.fromObject(map);
    }

    /**
     * 发送短信验证码
     *
     * @return
     */
    public Result sendVerificationSms1() {
        String sysCheckCode = (String) getSession().getAttribute("checkNum");
        if (sysCheckCode == null || !sysCheckCode.equals(validateVlue)) {
            result.put("success", false);
            result.put("errMsg", "验证码错误");
            return json(JSONObject.fromObject(result));
        }
        SendingMsg search = new SendingMsg();
        search.setReceivernum(account);
        Page page = new Page(1, 1);
        List<SendingMsg> sendingMsgList = sendingMsgService.findMsgList(search, page);
        if (!sendingMsgList.isEmpty()) {
            SendingMsg sendingMsg = sendingMsgList.get(0);
            Long pastTime = new Date().getTime() - sendingMsg.getSendtime().getTime();
            if (pastTime < 3 * 60 * 1000) {
                //
                map.put("success", false);
                map.put("errMsg", "您在三分钟之内发送过短信，请稍后再试");
                JSONObject jsonObject = JSONObject.fromObject(map);
                return json(jsonObject);
            }
        }
//        SendingMsg sendingMsg = SmsUtil.
        SmsUtil.sendRegisterMessage(account);
        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }


    public Result forgotPwd() {

        getSession().setAttribute(UserConstans.FORGOT_PWD_MESSAGE, "");

        Map<String, Object> map = new HashMap<String, Object>();
        boolean validPhone = false;
        String errMsg = "";
        SysSite sysSite = new SysSite();
        sysSite.setId(-1L);
//        User user = userService.findByAccount(account, sysSite);
        Member user = memberService.findByAccount(account, sysSite);
        if (user == null) {
            errMsg = "用户不存在";

        } else {
            if (user.getStatus() != UserStatus.activity) {
                errMsg = "用户被锁定或者被删除";
            } else {
                validPhone = true;
            }
        }


        if (validPhone) {
            String url = "";
            if (getSession().getAttribute(UserConstants.FORWARD_URL) == null) {
                url = "/";
            } else {
                url = getSession().getAttribute(UserConstants.FORWARD_URL).toString();
            }
            map.put("url", url);
            map.put("success", true);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        } else {
            map.put("success", false);
            map.put("errMsg", errMsg);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }
    }


    public Result exitLogin() {
        getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, null);
        return redirect("/");
    }

    public Result handleLoginException() {
        if ("XMLHttpRequest".equals(getRequest().getHeader("X-Requested-With"))) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("result", "nologin");
            return json(JSONObject.fromObject(result));
        }
        return redirect("/lvxbang/login/login.jhtml");
    }
    public Result changePassword() {
        Member member = getLoginUser(false);
        Map<String, Object> result = new HashMap<String, Object>();
        String pwd = MD5.caiBeiMD5(password);
        if (!pwd.equals(member.getPassword())) {
            result.put("msg", "当前密码错误");
            result.put("success", false);
        } else {
            String newPwd = MD5.caiBeiMD5(newPassword);
            member.setPassword(newPwd);
            memberService.update(member);
            result.put("msg", "保存成功");
            result.put("success", true);
        }

        return json(JSONObject.fromObject(result));
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

    public Integer getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(Integer smsCode) {
        this.smsCode = smsCode;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getValidateVlue() {
        return validateVlue;
    }

    public void setValidateVlue(String validateVlue) {
        this.validateVlue = validateVlue;
    }
}
