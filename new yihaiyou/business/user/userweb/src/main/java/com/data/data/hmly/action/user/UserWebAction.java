package com.data.data.hmly.action.user;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.user.util.SmsUtil;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.MD5;

import javax.annotation.Resource;

/**
 * Created by vacuity on 15/10/30.
 */
public class UserWebAction extends FrameBaseAction {


	@Resource
	private UserService userService;

	private String account;
	private String password;

	private Integer smsCode;

	private String captchaCode;

	private String rePassword;

	public Result login() {
		getSession().setAttribute(UserConstans.LOGIN_FAIL_MESSAGE, "");
		return dispatch();
	}


	public Result doLogin() {

		getSession().setAttribute(UserConstans.LOGIN_FAIL_MESSAGE, "");

		String fail = "/mall/index/index.jhtml";
		String success = "/mall/index/index.jhtml";

		boolean login = false;
		String errMsg = "";

        SysSite sysSite = (SysSite) getSession().getAttribute(UserConstans.SYSTEM_SITE_INFORMATION);
        User user = userService.findByAccount(account, sysSite);
		if (user == null) {
			errMsg = "用户不存在";
		} else {
			if (user.getStatus() != UserStatus.activity) {
				errMsg = "用户被锁定或者被删除";
            }else {
                String pwd = MD5.caiBeiMD5(password);

                if (!pwd.equals(user.getPassword())){
                    errMsg = "用户名或者密码错误";
                }else {
        			if (user instanceof SysUser) {
        				SysUser loginuser = (SysUser) user;
    					SysUnit unit = new SysUnit();
    					unit.setId(loginuser.getSysUnit().getId());
    					unit.setName(loginuser.getSysUnit().getName());
    					unit.setUnitType(loginuser.getSysUnit().getUnitType());
    					unit.setUnitNo(loginuser.getSysUnit().getUnitNo());
    					SysUnit companyUnit = new SysUnit();
    					if (loginuser.getSysUnit().getCompanyUnit() != null) {
    						companyUnit.setId(loginuser.getSysUnit().getCompanyUnit().getId());
    						companyUnit.setName(loginuser.getSysUnit().getCompanyUnit().getName());
    						companyUnit.setUnitType(loginuser.getSysUnit().getCompanyUnit().getUnitType());
    						companyUnit.setUnitNo(loginuser.getSysUnit().getCompanyUnit().getUnitNo());
    					}
    					unit.setCompanyUnit(companyUnit);
    					loginuser.setSysUnit(unit);
        			}
                    getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, user);
                    getSession().setAttribute("account", user.getAccount());
                    getSession().setAttribute("staffName", user.getUserName());
                    login = true;
                }
            }
        }

        if (login == true) {
	        return text("success");
//	        return redirect(success);
        }else {
            getSession().setAttribute(UserConstans.LOGIN_FAIL_MESSAGE, errMsg);
	        return text ("fail");
//            return redirect(fail);
        }

    }

    public Result sendVerificationSms() {
        SmsUtil.sendRegisterMessage(account);
        return text("success");
    }

    private boolean isSmsCodeValid() {
        if (smsCode == null) {
            return false;
        }
        Integer sentCode = (Integer) getRequest().getSession().getAttribute(SmsUtil.SMS_CODE_KEY);
        if (smsCode.equals(sentCode)) {
            getRequest().getSession().setAttribute(SmsUtil.SMS_CODE_KEY, "");
            return true;
        }
        return false;
    }

    public Result forgotPwdFirst(){
        return dispatch();
    }

    public Result forgotPwdFirstDeal(){

        String success = "/user/user/forgotPwdSecond.jhtml";
        String fail = "/user/user/forgotPwdFirst.jhtml";

        getSession().setAttribute(UserConstans.FORGOT_PWD_MESSAGE, "");

        boolean validPhone = false;
        String errMsg = "";
        SysSite sysSite = (SysSite) getSession().getAttribute(UserConstans.SYSTEM_SITE_INFORMATION);
        User user = userService.findByAccount(account, sysSite);
        if (user == null){
            errMsg = "用户不存在";
        }else {
            if (user.getStatus() != UserStatus.activity){
                errMsg = "用户被锁定或者被删除";
            }else {
                getSession().setAttribute(UserConstans.FORGOT_PWD_PHONE, account);
                validPhone = true;
            }
        }


        if (validPhone == true){
            return redirect(success);
        }else {
            getSession().setAttribute(UserConstans.FORGOT_PWD_MESSAGE, errMsg);
            return redirect(fail);
        }
    }

    public Result forgotPwdSecond(){
        return dispatch();
    }

    public Result forgotPwdSecondDeal(){
        String success = "/user/user/forgotPwdThird.jhtml";
        String fail = "/user/user/forgotPwdSecond.jhtml";
        getSession().setAttribute(UserConstans.FORGOT_PWD_MESSAGE, "");
        getSession().setAttribute(UserConstans.FORGOT_PWD_PHONE, account);

        String errMsg = "";
        if (isSmsCodeValid() == false){
            getSession().setAttribute(UserConstans.FORGOT_PWD_MESSAGE, "验证码错误");
            return redirect(fail);
        }

        return redirect(success);
    }

    public Result forgotPwdThird(){
        return dispatch();
    }

    public Result forgotPwdThirdDeal(){
        String success = "/user/user/forgotPwdFourth.jhtml";
        String fail = "/user/user/forgotPwdThird.jhtml";
        boolean change = false;

        getSession().setAttribute(UserConstans.FORGOT_PWD_MESSAGE, "");
        String errMsg = "";
        SysSite sysSite = (SysSite) getSession().getAttribute(UserConstans.SYSTEM_SITE_INFORMATION);
        User user = userService.findByAccount(account, sysSite);
        if (user == null){
            errMsg = "用户不存在";
        }else {
            if (user.getStatus() != UserStatus.activity){
                errMsg = "用户被锁定或者被删除";
            }else {
                String pwd = MD5.caiBeiMD5(password);
                user.setPassword(pwd);
                userService.update(user);
                change = true;
            }
        }

        if (change){
            return redirect(success);
        } else {
            getSession().setAttribute(UserConstans.FORGOT_PWD_MESSAGE, errMsg);
            return redirect(fail);
        }

    }

    public Result forgotPwdFourth(){
        String success = "/user/user/forgotPwdFourth.jhtml";
        return dispatch();
    }

	public Result exitLogin() {
		getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, null);
		return redirect("/");
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
}
