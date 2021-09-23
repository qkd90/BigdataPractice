<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>注册页面</title>
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
						<a id="logo" href="${CFG.SRV_ADDR_WWW }" title="旅行帮"></a>
					</div>
				</div>
				<div class="edit-plan-title">
					<h1 id="J_plan-title">注册</h1>
				</div>
				<div class="user-bar">
					<div class="userbtns">
						已经有账号？
						<a href="login" class="logbtn">马上登录</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="log-wrapper">
		<div class="log-content reg-content cf">
			<div class="welcomimg fl">
				<img alt="旅行帮" src="/static/img/reg-bg.png">
			</div>
			<div class="signin-box fr">
		        <div class="login-hd">注册会员</div>
		        <div class="error-info hide" id="register_errinfo"></div>
		        <div class="signin-forms register-form">
		            <form id="J_register-form" action="" method="post" autocomplete="off" >
		                <ul>
		                    <li>
		                        <input type="text" placeholder="邮箱/手机号码" id="J_username" class="username" name="username" need="need" cn="邮箱" autocomplete="off">
		                    </li>
		                    <li>
		                        <input type="password" placeholder="6-20位字母、数字和符号" id="J_password" class="password" name="password" need="need" cn="密码" autocomplete="off">
		                    </li>
		                    <li>
		                        <input type="password" placeholder="确认密码" id="J_password2" class="password" need="need" cn="确认密码" autocomplete="off">
		                    </li>
		                    <li class="vcode hide">
		                        <input id="J_vcode" placeholder="验证码" name="vcode" type="text" class="vcode-input" need="need" cn="验证码" maxlength="4">
		                        <div class="vcode-view">
			                        <img id="J_vcode-img" class="vcode-img" src="checkCodeAction/captcha">
			                        <a class="vcode-inf">换一张</a>
		                        </div>	
		                    </li>
		                    <li class="sms_code">
		                        <input id="J_sms_code" placeholder="短信验证码" name="smsCode" type="text" class="sms-code-input" need="need" cn="短信验证码" >
                                    <a id="sendSMSBtn" class="btn btn-m vcode-view">免费获取验证短信</a>
		                    </li>
		                </ul>
		                <div class="reb-register">
	                        <input type="checkbox" checked="checked" id="agree-reg"><label for="agree-reg">我已阅读并接受<a href="${CFG.SRV_ADDR_WWW}/footer/legal" target="_blank">《旅行帮用户协议》</a></label>
	                    </div>
		                <div class="submit-btn">
		                	<a href="javascript:;" id="J_submit-btn" class="reg-submit">立即注册</a>
		                </div>
		            </form>
		        </div>
		    </div>
		</div>
	</div>
	
	<jsp:include page="../account/footerV2.jsp"></jsp:include>
	<script src="/static/js/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="/static/js/myleyi.validate.js" type="text/javascript"></script>
	<script src="/static/js/common_util.js" type="text/javascript"></script>
	<script src="/static/js/soutuu.placeholder.js" type="text/javascript"></script>
	<script src="/static/js-biz/register.js" type="text/javascript"></script>
	
</body>
</html>