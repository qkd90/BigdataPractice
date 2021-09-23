<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>登录旅行帮</title>
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
					<h1 id="J_plan-title">登录</h1>
				</div>
				<div class="user-bar">
					<div class="userbtns">
						还没有账号？
						<a href="register" class="registerbtn">立即注册</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="log-wrapper">
		<div class="log-content cf">
			<div class="welcomimg fl">
				<img alt="旅行帮" src="/static/img/login-bg.png">
			</div>
			<div class="signin-box fr">
		        <div class="login-hd">用户登录</div>
		        <div class="error-info hide" id="login_errinfo"></div>
		        <div class="error-info actfail-info hide" id="actfail-login">
	        		<span>账号还未激活！</span>
	        		<a class="goto-email" target="_blank">立即激活 >></a>
		        </div>
		        <div class="signin-forms">
		            <form id="J_login-form" action="" method="post">
			            <input type="hidden" name="clientId" value="${clientId }"/>
						<input type="hidden" name="redirectUri" value="${redirectUri }"/>
		                <ul>
		                    <li>
		                        <input type="text" placeholder="用户名" id="J_username" class="username" name="username" need="need" cn="用户名" >
		                    </li>
		                    <li>
		                        <input type="password" placeholder="密码" id="J_password" class="password" name="password" need="need" cn="密码" autocomplete="off">
		                    </li>
		                </ul>
		                <div class="reb-login">
		                	<input type="checkbox" checked="checked" id="J_reb-login"><label for="J_reb-login">记住密码</label>
		            	</div>
		                <div class="submit-btn cf">
		                	<a href="javascript:;" id="J_submit-btn" class="log-submit">登录</a>
		                	<a href="/findPwd/index" class="forget-pwd fr">忘记密码？</a>
		                </div>
		                <div class="connect">
		                    <p class="hd">用社交网站帐号登录</p>
		                    <div class="bd-wrap">
			                    <div class="bd cf">
			                        <a href="third/weibo" title="微博登录" class="weibo"><i></i>新浪微博</a>
			                        <a href="third/qq" title="QQ登录" class="qq"><i></i>QQ</a>
			                        <a title="微信登录" class="weixin" href="third/weixin"><i></i>微信</a>
			                    </div>
		                    </div>
		                </div>
		            </form>
		        </div>
		    </div>
		</div>
	</div>
	
	<jsp:include page="../account/footerV2.jsp"></jsp:include>

	<script type="text/javascript" src="/static/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="/static/js/myleyi.validate.js"></script>
	<script type="text/javascript" src="/static/js/common_util.js"></script>
	<script type="text/javascript" src="/static/js/soutuu.placeholder.js"></script>
	<script type="text/javascript" src="/static/js-biz/login.js"></script>
	
</body>
</html>