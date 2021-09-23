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
    <!-- jquery组件 -->
    <script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/js/sys/login.js"></script>
</head>
<body>
<div class="qn_s_header">
	<div class="q_s_header_tnav">
		<ul>
			<li><a href="<s:property value="fgDomain"/>" class="q_s_header_a" style="color: #ff7c00;">一海游首页</a>|</li>
			<li><a href="javascript:void(0);" class="q_s_header_a">客服 400-0131-770</a></li>
		</ul>
	</div>
</div>
<div class="qn_page">
	<div class="qn_page_tit">
		<a class="qn_page_logo" href="<s:property value="fgDomain"/>" target="_blank" style="background: url('${loginLogoImg}') 0 0 no-repeat;"></a>
		<div class="qn_tit">帐号登录</div>
	</div>
	<div class="qn_wrap clearfix">
		<div class="qn_left">
			<img src="/images/login_left.png" style="width:620px;height:360px;"/>
		</div>
		<div class="qn_right" style="height:350px">
			<div onkeydown ="tokeydown()">
				<form id="WfStaffAction" name="WfStaffAction" class="news-lgoin-box" method="post" autocomplete="off">
                    <s:if test='json!=null&&json!=""'>
                        <div id="validatetd_ADMIN" class="clearfix">
                            <span style="color: red;"><s:property value="json"/></span>
                        </div>
                    </s:if>
                    <s:else>
                        <div id="validatetd_ADMIN" class="clearfix" style="display: none;">
                            <span style="color: red;"></span>
                        </div>
                    </s:else>

					<div class="field-login" data-type="field_normal">
						<div class="field-news usermame-field clearfix">
							<i></i>
							<input name="account" id="account_ADMIN" class="textbox textbox_focus" placeholder="请输入登录账号" type="text" value="${user.account}"
                                   autocomplete="off" tabindex="1" style="font-weight: normal; background-color: #fff;">
						</div>
						<div class="field-news password-field clearfix">
							<i></i>
							<input name="password" id="password_ADMIN" class="textbox" type="password" placeholder="请输入密码" value="${user.password}"
                                   autocomplete="off" tabindex="2" style="font-weight: normal; background-color: #fff;">
						</div>
						<div class="field-vercode clearfix" id="captcha" name="captcha" style="">
							<div class="field-news vercode-field clearfix">
								<input name="validateVlue" id="validateVlue_ADMIN" class="v_code" type="text" autocomplete="off" value="" placeholder="请输入验证码" style="font-weight: normal;" tabindex="3">
							</div>
							<p>
                                <img title="看不清？换一张" width="80" height="26" src="/images/checkNum.jsp" id="imgyz_ADMIN" onclick="imgClick('imgyz_ADMIN');"/>
                                <a class="nogo" onclick="javascript:imgClick('imgyz_ADMIN');">换一张</a>
							</p>
						</div>
					</div>
					<div class="new-remember_paw clearfix" data-type="field_normal">
						<i><input name="chkrem" type="checkbox" ${chkrem} id="remember_paw" tabindex="3"></i>
						<span><label for="remember_paw">记住我</label></span>
					</div>

                    <input name="userType" id="userType" type="hidden" />
					<input class="new-login-btn" onclick="vsubform('ADMIN')" tabindex="4" type="button" style="cursor: pointer;" value="登      录">
				</form>
			</div>
		</div>
	</div>
</div>
<div class="qn_footer">
	<div class="inner">
		<div class="cr">
			<div class="links" style="text-align:center">
				Copyright©2016　 <a href="<s:property value="fgDomain"/>">一海游, 游四海  </a>　版权所有　闽ICP备 14006003号
			</div>
		</div>
	</div>
</div>
</body>
</html>
