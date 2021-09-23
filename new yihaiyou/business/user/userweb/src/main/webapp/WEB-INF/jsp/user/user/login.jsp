<%@ page import="com.data.data.hmly.action.user.UserConstans" %>
<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/30
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="${mallConfig.resourcePath}/css/loginErrMsg.css?v=${mallConfig.resourceVersion}" rel="stylesheet">

<!--首页登录框-->

<div id="homelogin">
    <div class="login-title">
        <a class="personal" href="/user/register/personal.jhtml"></a>
        <a class="distributor" href="/mall/supplier/inivited.jhtml"></a>
    </div>
    <div id="login-form" class="login-form">
        <form name="loginForm" id="loginForm" method="post" >
            <span><input id="account" name="account" type="text" placeholder="手机/ID/昵称"></span>
            <span><input id="password" name="password" type="password" placeholder="密码"></span>
            <input id="login-fail-msg" type="hidden" value="<%=(String)session.getAttribute(UserConstans.LOGIN_FAIL_MESSAGE)%>">
            <%session.setAttribute(UserConstans.LOGIN_FAIL_MESSAGE, ""); %>
            <div id="login-errmsg" class="col-xs-3 validate-message"></div>
            <span class="text-center no-margin-left"><input name="" type="button" class="login-bt"></span>
            <span class="text-center no-margin-left"><a href="/user/user/forgotPwdFirst.jhtml">忘记密码？</a></span>
        </form>
    </div>
</div>

