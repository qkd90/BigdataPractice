<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<title>一海游后台登录</title>
	<link href="/css/sys/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="mainBox">
	<div class="headerBox">
		<div class="header">一海游运维平台</div>
	</div>
	<div class="bodyBox clearfix">
		<div class="bodyLbox">
            <img class="logo-img" src="/css/images/loginpicture.png"/>
        </div>
		<div class="bodyRbox">
			<div class="loginBox" onkeydown ="tokeydown()">
				<form id="WfStaffAction" name="WfStaffAction" class="news-lgoin-box" method="post" autocomplete="off">
                    <div class="loginhead">一海游运维后台</div>
                    <s:if test='json!=null&&json!=""'>
                        <div id="validatetd_ADMIN" class="clearfix validatetd-msg">
                            <span style="color: red;"><s:property value="json"/></span>
                        </div>
                    </s:if>
                    <s:else>
                        <div id="validatetd_ADMIN" class="clearfix validatetd-msg">
                            <span style="color: red;"></span>
                        </div>
                    </s:else>
						<div class="account" style="margin-top:0">
                            <span class="border">|</span>
							<input name="account" id="account_ADMIN" class="textbox textbox_focus" placeholder="请输入登录账号" type="text" value=""
                                   autocomplete="off" tabindex="1" style="font-weight: normal; background-color: #fff;">
						</div>
						<div class="account loginpswrd">
                            <span class="border">|</span>
							<input id="password_ADMIN" class="textbox" type="password" placeholder="请输入密码" value=""
                                   autocomplete="off" tabindex="2" style="font-weight: normal; background-color: #fff;">
							<input type="hidden" name="password"/>
						</div>
						<div class="account logincode" id="captcha" name="captcha" style="">
							<div class="vercode-field">
                                <span class="border">|</span>
								<input name="validateVlue" id="validateVlue_ADMIN" class="v_code" type="text" autocomplete="off" value="" placeholder="验证码" style="font-weight: normal;" tabindex="3">
							</div>
                            <div class="vercode-img">
                                <img class="v-img" title="看不清？换一张" width="80" height="26" src="/images/checkNum.jsp" id="imgyz_ADMIN" onclick="imgClick('imgyz_ADMIN');"/>
                                <%--<a class="validateCode-a" onclick="javascript:imgClick('imgyz_ADMIN');">换一张</a>--%>
                            </div>
						</div>
					<div class="pswrdoperat clearfix" data-type="field_normal">
						<i><input name="chkrem" type="checkbox" id="remember_paw" tabindex="3"></i>
						<span class="getpswrd"><label for="remember_paw">记住我</label></span>
					</div>
                    <input name="userType" id="userType" type="hidden" />
					<button class="loginBtn" onclick="vsubform('ADMIN')" tabindex="4" type="button">登录</button>
				</form>
			</div>
		</div>
	</div>
    <div class="footerBox">版权所有 © Copyright 2016 一海游公司官网</div>
</div>
</body>
<!-- jquery组件 -->
<script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/js/md5.js"></script>
<script type="text/javascript" src="/js/sys/login.js"></script>
</html>
