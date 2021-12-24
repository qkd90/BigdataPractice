<%@ page import="com.data.data.hmly.action.user.UserConstans" %>
<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/30
  Time: 13:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>找回密码</title>
  <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <link href="${mallConfig.resourcePath}/css/zhaohui.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
  <!--[if lt IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
<div class="container">
  <div class="row">
    <div class="col-xs-4">
      <div class="logo pull-left"><a href="#"><img src="${mallConfig.resourcePath}/images/logo.jpg"></a></div>
      <div class="top-title pull-left">找回密码</div>
    </div>
  </div>
</div>

<div class="clearfix zhaohui-bg">
  <div class="container">
    <div class="row">
      <div class="col-xs-12 zhaohui-bz"><img src="${mallConfig.resourcePath}/images/zh-2.jpg"></div>
      <div class="col-xs-12"><div class="clearfix box">
        <div class="col-xs-7 col-xs-offset-3 zhaohui-form">
          <form id="forgot-pwd-second" class="form-horizontal">
            <div class="form-group">
              <label for="tel" class="col-xs-2 text-right">手机号</label>
              <div class="col-xs-5"><input type="text" class="form-control" id="tel" name="account" value="<%=(String)session.getAttribute(UserConstans.FORGOT_PWD_PHONE)%>" readonly="readonly"></div>
              <div class="col-xs-4"><input id="sendSms" name="" type="button" class="btn btn-info" value="获取验证码"></div>
            </div>
            <div class="form-group">
              <label for="tel" class="col-xs-2 text-right">验证码</label>
              <div class="col-xs-2"><input type="text" class="form-control" id="smsCode" name="smsCode"></div>
              <div class="col-xs-4">请输入6位验证码</div>
            </div>
            <input id="forgot-second-msg" type="hidden" value="<%=(String)session.getAttribute(UserConstans.FORGOT_PWD_MESSAGE)%>">
            <%session.setAttribute(UserConstans.FORGOT_PWD_MESSAGE, ""); %>
            <div id="forgot-errmsg" class="col-xs-12 col-xs-offset-2 validate-message"></div>
            <div class="form-group">
              <div class="col-xs-3 col-xs-offset-2"><input id="submit-button" name="" type="button" class="btn btn-warning zh-btn" value="下一步"></div>
            </div>
          </form>
        </div>
      </div></div>
    </div>
  </div>
</div>

<!--底部-->
<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
<!--底部-->
<script src="${mallConfig.resourcePath}/js/jquery.min.js"></script>
<script src="${mallConfig.resourcePath}/js/jquery.validation.min.js"></script>
<script src="${mallConfig.resourcePath}/js/bootstrap.min.js"></script>
<script src="${mallConfig.resourcePath}/js/user/login/forgotPwdSecond.js?v=${mallConfig.resourceVersion}"></script>
</body>
</html>
