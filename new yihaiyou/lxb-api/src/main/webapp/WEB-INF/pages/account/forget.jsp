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
			<li class="fl step-one active"><i>1</i>输入账号<label class="arrow"></label></li>
			<li class="fl step-two"><i>2</i>验证身份<label class="arrow"></label></li>
			<li class="fl step-three"><i>3</i>修改密码<label class="arrow"></label></li>
			<li class="fl step-four"><i>4</i>修改成功</li>
		</ul>
		<form id="J_forget-form" class="forget-form">
			<p class="tips">请输入您注册时使用的账号，我们将根据您使用的账号帮助您找回密码</p>
			<div class="error-msg" id="register_errinfo"></div>
			<table class="forget-table">
				<tr>
					<td><label for="username">注册邮箱/手机号</label></td>
					<td class="center-td">*</td>
					<td><input id="J_secureemail" name="username" type="email"/></td>
					<td id="J_msg-one" class="error-msg hide"></td>
				</tr>
				<tr>
					<td><label for="vcode">验证码</label></td>
					<td class="center-td">*</td>
					<td class="vcode">
						<input id="J_vcode" name="vcode" type="text" class="vcode-input" maxlength="4">
				    	<div class="vcode-view">
				    		 <img id="J_vcode-img" class="vcode-img" src="/passport/checkCodeAction/captcha">
				    		 <a class="vcode-inf">换一张</a>
				   		</div>
				   	</td>
				   	<td id="J_msg-two" class="error-msg hide"></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td>
						<a id="J_submit-btn" href="javascript:;" class="submit-btn">下一步</a>
					</td>
					<td id="J_msg-three" class="error-msg hide"></td>
				</tr>
			</table>
		</form>
	</div>
	
	<jsp:include page="../account/footerV2.jsp"></jsp:include>
	
</body>

<script type="text/javascript" src="/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/static/js/jquery.ajaxsubmit.js"></script>
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
                $("#J_submit-btn").click();
                return false;
            }
        };
	});
	
	$("input").blur(function()
	{
		$(this).removeClass("selected");
	});
	
	// 改变验证码图片
	$("#J_vcode-img").click(function()
	{
		$("#J_vcode-img").attr("src","/passport/checkCodeAction/captcha?" + Math.random());
	});
	
	$(".vcode-inf").click(function()
	{
		$("#J_vcode-img").attr("src","/passport/checkCodeAction/captcha?" + Math.random());
	});

	// 验证注册账号填写格式是否正确
	$("#J_secureemail").blur(function()
	{
		isTureUserName();
	});
	
	// 验证码不为空判断
	$("#J_vcode").blur(function()
	{
		idNullCode();
	});
    
	// 按钮绑定事件
    $("#J_submit-btn").ajaxsubmit(
    {
    	getdata : function()
    	{ 
    		return $("#J_forget-form").serialize(); 
    	}, 
    	url : "/findPwd/findPwd", 
    	disableClass : "on",
    	beforesave : function()
    	{ 
    		return isTureUserName() && idNullCode();
		}, 
		onsuccess : function(result)
		{
			success(result);
		}
	});
});

// 成功提交后
function success(result)
{
	if (result.errorCode != 0)
    {
        $("#J_msg-three").html(result.errorMsg).show();
     	$("#J_submit-btn").removeClass("on").click(postFindPwd);
    }
	else
	{
		var url = result.resultList.data[0].url;
		window.location.href = url;
	}
}

// 判断验证码是否为空
function idNullCode()
{
	$("#J_msg-two").hide();
	
	if (!$("#J_vcode").val())
	{
		$("#J_msg-two").html("验证码不能为空").show();
		return false;
	}
	
	return true;
}

// 判断注册账号是否为空，属于邮箱还是手机号，验证其格式正确性
function isTureUserName()
{
	$("#J_msg-one").hide();
	
	var iptvalue = $("#J_secureemail").val();
	if (!iptvalue)
	{
		$("#J_msg-one").html("请填写注册邮箱/手机号").show();
		return false;
	}
	
	// 如果是邮箱，验证邮箱格式
	if (iptvalue.indexOf("@") >= 0)
    {
        if (!isEmail(iptvalue))
        {
        	$("#J_msg-one").html("请填写正确的邮箱").show();
        	return false;
        }
    }
	// 如果是手机号，验证手机号格式
    else
    {
    	 if (!isMobile(iptvalue))
         {
         	$("#J_msg-one").html("请填写正确的手机号").show();
         	return false;
         }
    }
	
	return true;
}
</script>
</html>