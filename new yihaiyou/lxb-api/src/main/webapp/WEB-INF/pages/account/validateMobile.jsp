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
			<li class="fl step-two active"><i>2</i>验证身份<label class="arrow"></label></li>
			<li class="fl step-three"><i>3</i>修改密码<label class="arrow"></label></li>
			<li class="fl step-four"><i>4</i>修改成功</li>
		</ul>
		<input type="hidden" id="J_mobile" value=""/>
		<form id="J_forget-form">
		<table class="forget-table">
			<tr>
				<td><label for="username">手机号：</label></td>
				<td class="txl">${user.username}</td>
			</tr>
			<tr>
				<td><label for="smsCode">短信验证码：</label></td>
				<td class="vcode txl cf">
					<input id="J_flowKey" name="flowKey" type="hidden" value="${flowKey}"/>
			    	<input id="J_sms-code" name="vcode" type="text" class="vcode-input sms-code fl">
	                <a id="J_send-sms" class="sms-btn fl">获取验证短信</a>
			   	</td>
			   	<td id="J_msg-one" class="error-msg hide"></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<a id="J_submit-btn" href="javascript:;" class="submit-btn resend-btn">下一步</a>
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	<jsp:include page="../account/footerV2.jsp"></jsp:include>
</body>

<script type="text/javascript" src="/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/static/js/common_util.js"></script>
<script type="text/javascript" src="/static/js/jquery.ajaxsubmit.js"></script>

<script type="text/javascript">
$(function()
{
	// 边框发光
	$("input").focus(function() 
	{
		$(this).addClass("selected");
	});
	
    $("#J_send-sms").click(sendSMS);
 	// 按钮绑定事件
    $("#J_submit-btn").ajaxsubmit(
    {
    	getdata : function()
    	{ 
    		return $("#J_forget-form").serialize(); 
    	}, 
    	url : "/findPwd/validateMobile", 
    	disableClass : "on",
    	beforesave : function()
    	{ 
    		return isNullVcode();
		}, 
		onsuccess : function(result)
		{
			success(result);
		}
	});
});

// 发送验证短信
var reSendTimeout;
var reSendInterval;
function sendSMS()
{
	$.get("/findPwd/ajax/sendValidateSMS?flowKey=" + $("#J_flowKey").val(), function(result)
	{
	    if (result.errorCode != 0)
	    {
	    	$("#J_msg-one").hide();
	        clearInterval(reSendInterval);
	        $("#J_msg-one").html("短信发送失败").show();
	    }
	});
	
	reSendTimeout = 60;
	$("#J_send-sms").addClass("btn-inactive").text(reSendTimeout+"秒后重新发送短信").unbind("click");
	reSendInterval = setInterval(function ()
	{
	    reSendTimeout--;
	    if(reSendTimeout<=0)
	    {
	        clearInterval(reSendInterval);
	        $("#J_send-sms").removeClass("btn-inactive").text("获取验证短信").click(function()
	        {
	            sendSMS();
	        });
	        return;
	    }
	    var content = reSendTimeout + "秒后重新发送短信";
	    $("#J_send-sms").text(content);
	}, 1000);
}

function isNullVcode()
{
	$("#J_msg-one").hide();
	
	var codevalue = $("#J_sms-code").val();
	if (!codevalue)
	{
		 $("#J_msg-one").html("短信验证码不能为空").show();
		 return false;
	}
	
	if (!isNumber(codevalue))
	{
		$("#J_msg-one").html("短信验证码必须是数字").show();
		return false;
	}	
	
	return true;
}

function success(result)
{
	if (result.errorCode != 0)
    {
        $("#J_msg-one").html(result.errorMsg).show();
    }
    else
    {
    	var url = result.resultList.data[0].url;
		window.location.href = url;
    }
}
</script>
</html>