<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>找回密码</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<link rel="stylesheet" type="text/css" href="/static/css/common.css" media="screen, projection">
	<jsp:include page="baidutjNew.jsp"></jsp:include>
</head>
<body>
	<div class="header">
		<div class="header-top-wrapper">
			<div class="top-bar cf">
				<div class="logo-bar fl">
					<div class="logo">
						<a id="logo" href="${CFG.SRV_ADDR_WWW}" title="旅行帮"></a>
					</div>
				</div>
				<div class="edit-plan-title">
					<h1 id="J_plan-title">找回密码</h1>
				</div>
				<div class="user-bar forget-user-bar cf">
					<a href="/passport/login" class="logbtn">登录</a>
					<a href="/passport/register" class="registerbtn">注册</a>
				</div>
			</div>
		</div>
	</div>
	<div class="log-wrapper forget-wrapper">
		<ul class="wrap-title cf">
			<li class="fl step-one"><i>1</i>输入账号<label class="arrow"></label></li>
			<li class="fl step-two"><i>2</i>验证身份<label class="arrow"></label></li>
			<li class="fl step-three"><i>3</i>修改密码<label class="arrow"></label></li>
			<li class="fl step-four active"><i>4</i>修改成功</li>
		</ul>
		<p class="success-tips cf"><i class="fl">成功标志</i>恭喜您密码修改成功!</p>
		<a class="resend-a" href="${CFG.SRV_ADDR_WWW}">返回首页</a>
	</div>
	
	<jsp:include page="../account/footerV2.jsp"></jsp:include>
</body>
</html>