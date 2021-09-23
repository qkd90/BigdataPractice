<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/10/27
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>厦门旅游集散服务中心</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/base.css" rel="stylesheet">
    <link href="/css/reg.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-4">
            <div class="logo pull-left"><a href="#"><img src="/images/logo.jpg"></a></div>
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
                <div class="reg-content reg-3">
                    <div class="reg-ok"><h4>恭喜，已注册成功！</h4><span><a href="/mall/index/index.jhtml">返回首页</a><a href="/mall/index/index.jhtml">登录</a></span></div>
                </div>
            </div>
            <div class="col-xs-3 reg-pic"><img src="/images/reg-pic.jpg" class="img-responsive center-block"></div>
        </div>
    </div>
</div>

<!--底部-->
<div class="container-fluid text-center" id="footer">
    <div class="footer-links"><a href="#">关于我们</a>     |     <a href="#">联系我们</a>     |     <a href="#">免责声明</a>     |     <a href="#">诚聘英才</a>     |     <a href="#">问题反馈</a>     |     <a href="#">帮助中心</a></div>
    <div class="copyright">Copyright © 2015闽ICP备11015723号</div>
</div>
<!--底部-->
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/custom.js"></script>
</body>
</html>

