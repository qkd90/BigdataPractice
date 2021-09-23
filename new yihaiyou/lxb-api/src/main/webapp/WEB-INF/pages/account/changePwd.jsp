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
			<li class="fl step-three active"><i>3</i>修改密码<label class="arrow"></label></li>
			<li class="fl step-four"><i>4</i>修改成功</li>
		</ul>
		<form id="J_forget-form" action="/findPwd/changePwd" class="forget-form" method="post">
			<input type="hidden" name="flowKey" value="${flowKey}"/>
			<div class="error-msg" id="register_errinfo"></div>
			<table class="forget-table">
				<tr>
					<td><label for="password">账号</label></td>
					<td class="center-td">:</td>
					<td class="user-name">${user.username}</td>
				</tr>
				<tr>
					<td><label for="password">新密码</label></td>
					<td class="center-td">*</td>
					<td><input id="J_password" type="password"/></td>
					<td id="J_msg-one" class="error-msg hide"></td>
				</tr>
				<tr>
					<td><label for="passwordTwo">确认密码</label></td>
					<td class="center-td">*</td>
					<td><input id="J_password-two" type="password" name="password"/></td>
					<td id="J_msg-two" class="error-msg hide"></td>
				</tr>
				<tr>
					<td></td>
					<td class="center-td"></td>
					<td>
						<a id="J_submit-form" href="javascript:;" class="submit-btn">确认</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<jsp:include page="../account/footerV2.jsp"></jsp:include>
</body>

<script type="text/javascript" src="/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/static/js/common_util.js"></script>

<script type="text/javascript">
$(function()
{
	// 边框发光
	$("input").focus(function() 
	{
		$(this).addClass("selected");
		 
		document.onkeydown = function() 
        {
            if (event.keyCode == 13)
            {
                $("#J_submit-form").click();
                return false;
            }
        };
	});
	
	$("input").blur(function()
	{
		$(this).removeClass("selected");
	});
	
	$("#J_password").blur(function()
	{
		$("#J_msg-one").hide();
		
		if (!$("#J_password").val())
    	{
    		$("#J_msg-one").html("请填写新密码").show();
    		return;
    	}
		
		if($("#J_password").val().length < 6 || $("#J_password").val().length > 20)
		{
			$("#J_msg-one").html("密码长度必须是6-20位字符").show();
			return;
		}
	});
	
	$("#J_password-two").blur(function()
	{
		$("#J_msg-two").hide();
		
		if (!$("#J_password-two").val())
    	{
    		$("#J_msg-two").html("确认密码不能为空").show();
    		return;
    	}
	});
			
	$("#J_submit-form").click(function()
	{
		var pwd = $("#J_password").val();
		var pwd2 = $("#J_password-two").val();
		
		if(!pwd)
		{
			$("#J_msg-one").html("请填写新密码").show();
			return;
		}
		
		if(!pwd2)
		{
			$("#J_msg-two").html("确认密码不能为空").show();
			return;
		}
		
		if(pwd != pwd2)
		{
			$("#J_msg-two").html("两次密码不同，请重新填写！").show();
			return;
		}
		if( pwd.length < 6 || pwd.length > 20)
		{
			$("#J_msg-one").html("密码长度必须是6-20位字符").show();
			return;
		}
		
		$("#J_submit-form").html("正在保存，请稍等！");
		$("#J_forget-form").submit();
	});
});
</script>
</html>