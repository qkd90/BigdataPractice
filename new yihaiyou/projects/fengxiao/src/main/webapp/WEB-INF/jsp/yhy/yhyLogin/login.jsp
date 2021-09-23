<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/11/8
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <title>商户登录</title>
    <link rel="stylesheet" href="/css/yhy/yhyLogin/loginMin.css">
    <%--<script>--%>
            <%--var Vwindow = window.screen.height;--%>
            <%--if(Vwindow < 800){--%>
                <%--document.write('<link rel="stylesheet" href="/css/yhy/yhyLogin/loginMin.css">')--%>
            <%--}else{--%>
                <%--document.write('<link rel="stylesheet" href="/css/yhy/yhyLogin/login.css">')--%>
            <%--}--%>
    <%--</script>--%>
</head>
<body>
<div class="container">
    <div class="mainBox">
        <div class="headerBox">
            <div class="header">一海游</div>
        </div>
        <div class="bodyBox clearfix">
            <div class="bodyLbox">
                <img src="/images/yhy/loginpicture.png">
            </div>
            <div class="bodyRbox">
                <div class="loginBox">
                <form role="form" class="form-signin" id="yhyLoginForm" action="" method="post" enctype="multipart/form-data">
                    <%--<h2 class="form-signin-heading">商户登录</h2>--%>
                    <div class="loginhead">一海游系统后台</div>
                    <div class="form-group yhy-from-group account">
                        <%--<!--[if lte IE 8]><label for="yhy_ac" class="control-label">账号</label><![endif]-->--%>
                        <span class="border">|</span>
                        <input type="text" id="yhy_ac" name="yhyAccount" class="form-control" placeholder="账号">
                    </div>
                    <div class="form-group yhy-from-group account loginpswrd">
                        <%--<!--[if lte IE 8]><label for="yhy_Pwd" class="control-label">密码</label><![endif]-->--%>
                        <span class="border">|</span>
                        <input type="password" id="yhy_Pwd" name="password" class="form-control" placeholder="密码">
                        <input type="hidden" id="yhy_md5_Pwd" name="yhyPassword" class="form-control" placeholder="密码">
                    </div>
                    <div class="form-group yhy-from-group account logincode">
                        <%--<!--[if lte IE 8]><label for="yhy_Pwd" class="control-label">验证码</label><![endif]-->--%>
                        <span class="border">|</span>
                        <input type="text" id="yhy_validateCode" name="yhyValidateCode" autocomplete="off" placeholder="验证码"
                               class="form-control">
                        <img id="yhy_validateImg" src="/images/yhy/checkNum.jsp" class="yhy-validateCode-img">
                    </div>
                    <button class="btn btn-lg btn-primary btn-block yhy-login-btn loginBtn" autocomplete="off" data-loading-text="登录中...">登录</button>
                </form>
                </div>
            </div>
        </div>
        <div class="footerBox">版权所有 © Copyright 2016 一海游公司官网</div>
    </div>
</div>
</body>
<script type="text/javascript" src="/lib/jquery-1.11.1/js/jquery.min.js"></script>
<script type="text/javascript" src="/lib/jquery-1.11.1/js/jquery.jplaceholder.js"></script>
<!--[if lte IE 8]>
<script type="text/javascript" src="/lib/respond/js/respond.js"></script>
<![endif] -->
<script type="text/javascript" src="/lib/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/lib/bootstrap-3.3.7-dist/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="/lib/md5.js"></script>
<script type="text/javascript" src="/js/yhy/yhyCommon/yhyPlugins.js"></script>
<script type="text/javascript" src="/js/yhy/yhyLogin/login.js"></script>
</html>
