<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>厦门旅游集散服务中心</title>
    <link href="${mallConfig.resourcePath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/reg.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-4">
            <div class="logo pull-left"><a href="/"><img src="${mallConfig.resourcePath}/images/logo.jpg"></a></div>
            <div class="top-title pull-left">注册</div>
        </div>
        <div class="col-xs-8 text-right toplinks">
            <a href="#">供应商入驻</a><a href="#">帮助中心</a><a href="#">问题反馈</a><a href="#" class="btn btn-primary">登录</a>
        </div>
    </div>
</div>

<div class="clearfix reg-bg">
    <div class="container">
        <div class="row">
            <div class="col-xs-9" id="reg-form">
                <h4 class="reg-tab"><span>供销商注册</span><span class="curr">个人注册</span></h4>
                <div class="reg-content reg-1">
                    <form id="register-form" class="form-horizontal" action="/user/register/save.jhtml" method="post">
                        <s:if test="validateResult!=''">
                            <div class="form-group">
                                <div class="alert alert-danger alert-dismissible fade in" role="alert">${validateResult}</div>
                            </div>
                        </s:if>

                        <div class="form-group">
                            <label for="tel" class="col-xs-2 text-right">手机号码</label>
                            <div class="col-xs-6"><input type="text" class="form-control" id="tel" name="account"></div>
                            <div id="tel-message" class="col-xs-3 validate-message">请填写11位手机号码</div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-xs-2 text-right">密码</label>
                            <div class="col-xs-6"><input type="password" class="form-control" id="password" name="password"></div>
                            <div class="col-xs-3 validate-message">6-16位，区分大小写</div>
                        </div>
                        <div class="form-group">
                            <label for="rePassword" class="col-xs-2 text-right">确认密码</label>
                            <div class="col-xs-6"><input type="password" class="form-control" id="rePassword" name="rePassword"></div>
                            <div class="col-xs-3 validate-message">6-16位，区分大小写</div>
                        </div>
                        <div class="form-group">
                            <label for="captcha" class="col-xs-2 text-right">验证码</label>
                            <div class="col-xs-3"><input type="text" class="form-control" id="captcha" name="captcha"></div>
                            <div class="col-xs-3"><img src="/images/checkNum.jsp"></div>
                            <div id="captcha-message" class="col-xs-3 validate-message"></div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-5 col-xs-offset-2"><a id="submit-button" class="btn btn-warning next">下一页</a></div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-xs-3 reg-pic"><img src="${mallConfig.resourcePath}/images/reg-pic.jpg" class="img-responsive center-block"></div>
        </div>
    </div>
</div>

<!--底部-->
<div class="container-fluid text-center" id="footer">
    <div class="footer-links"><a href="#">关于我们</a>     |     <a href="#">联系我们</a>     |     <a href="#">免责声明</a>     |     <a href="#">诚聘英才</a>     |     <a href="#">问题反馈</a>     |     <a href="#">帮助中心</a></div>
    <div class="copyright">Copyright © 2015闽ICP备11015723号</div>
</div>
<!--底部-->
<script src="${mallConfig.resourcePath}/js/jquery.min.js"></script>
<script src="${mallConfig.resourcePath}/js/jquery.validation.min.js"></script>
<script src="${mallConfig.resourcePath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/user/register/register.js?v=${mallConfig.resourceVersion}"></script>
</body>
</html>
