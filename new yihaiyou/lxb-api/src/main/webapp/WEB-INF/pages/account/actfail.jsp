<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>激活失败</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	
	<link rel="stylesheet" type="text/css" href="/static/css/common.css" media="screen, projection">
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
					<h1 id="J_plan-title">激活失败</h1>
				</div>
			</div>
		</div>
	</div>
	<div class="log-wrapper">
		<div class="log-content reg-content cf">
			<div class="active-cont">
				<p class="ture resend">激活链接已经失效，请重新激活！</p>
				<div class="a-div">
					<a class="resend-email" id="J_resend_btn" href="javascript:;" email="${email}" uid="${uid}">发送验证邮件</a>
					<span class="resend-msg hide">发送成功</span>
				</div>
			</div>			
		</div>
	</div>
	<jsp:include page="../account/footerV2.jsp"></jsp:include>
	
	<script src="/static/js/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="/static/js/common_util.js" type="text/javascript"></script>
	<script src="/static/js-biz/actemail.js" type="text/javascript"></script>
</body>
</html>