<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/30
  Time: 13:33
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
      <div class="col-xs-12 zhaohui-bz"><img src="${mallConfig.resourcePath}/images/zh-4.jpg"></div>
      <div class="col-xs-12"><div class="clearfix box">
        <div class="col-xs-9 col-xs-offset-2 zhaohui-form">
          <div class="ok"><h4>您的密码已重新设置，请使用新密码登录。</h4><span>请妥善保密账户与密码相关信息。<a href="/">登录我的账户</a></span></div>
        </div>
      </div></div>
    </div>
  </div>
</div>

<!--底部-->
<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>

<!--底部-->
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/custom.js"></script>
</body>
</html>

