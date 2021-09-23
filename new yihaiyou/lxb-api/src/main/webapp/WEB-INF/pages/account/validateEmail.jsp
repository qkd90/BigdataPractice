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
		<form id="J_forget-form">
			<input type="hidden" name="flowKey" value="${flowKey}"/>
		</form>
		<ul class="wrap-title cf">
			<li class="fl step-one"><i>1</i>输入账号<label class="arrow"></label></li>
			<li class="fl step-two active"><i>2</i>验证身份<label class="arrow"></label></li>
			<li class="fl step-three"><i>3</i>修改密码<label class="arrow"></label></li>
			<li class="fl step-four"><i>4</i>修改成功</li>
		</ul>
		<div class="error-msg" id="register_errinfo"></div>
		<input type="hidden" id="J_email" value="${user.username}"/>
		<p class="tips">若长时间未反应，请返回上一个页面重试</p>
		<table class="forget-table">
			<tr>
				<td><label for="username">注册邮箱：</label></td>
				<td class="txc">${user.username}</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<a id="J_submit-btn" href="javascript:;" class="submit-btn resend-btn">
						<span class="send-span">发送密码重置邮件</span>
						<span class="wait-span">发送中，请稍后！</span>
					</a>
				</td>
			</tr>
		</table>
		<div class="success-msg hide">
			<p class="success-tips cf"><i class="fl">成功标志</i>已向您的邮箱发送验证邮件，请登录邮箱<a class="email-a" target="_blank"></a>查看</p>
		</div>
	</div>
	
	<jsp:include page="../account/footerV2.jsp"></jsp:include>
	
</body>
<script type="text/javascript" src="/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/static/js/jquery.ajaxsubmit.js"></script>
<script type="text/javascript" src="/static/js/common_util.js"></script>

<script type="text/javascript">
$(function()
{
	// 按钮绑定事件
    $("#J_submit-btn").ajaxsubmit(
    {
    	getdata : function()
    	{ 
    		return $("#J_forget-form").serialize(); 
    	}, 
    	url : "/findPwd/ajax/sendValidateEmail", 
    	disableClass : "on",
		onsuccess : function(result)
		{
			success(result);
		}
	});
});

function success(result)
{
	if (result.errorCode == 0)
	{
		var url = $("#J_email").val();
		$(".email-a").html(url);
        mail_url = gotoEmail(url);
        
        if (mail_url != "") 
        {
            $(".email-a").attr("href", "http://" + mail_url);
        } 
        else 
        {
            return;
        }
        
		$(".success-msg").removeClass("hide");
		$("#J_submit-btn").removeClass("on");
	} 
	else
	{
		$("#J_submit-btn").removeClass("on");
	}	
}

// 功能：根据用户输入的Email跳转到相应的电子邮箱首页
function gotoEmail($mail) 
{
    $t = $mail.split("@")[1];
    $t = $t.toLowerCase();
    if ($t == "163.com") 
    {
        return "mail.163.com";
    } 
    else if ($t == "vip.163.com") 
    {
        return "vip.163.com";
    } 
    else if ($t == "126.com") 
    {
        return "mail.126.com";
    } 
    else if ($t == "qq.com" || $t == "vip.qq.com" || $t == "foxmail.com") 
    {
        return "mail.qq.com";
    } 
    else if ($t == "gmail.com") 
    {
        return "mail.google.com";
    }
    else if ($t == "sohu.com")
    {
        return "mail.sohu.com";
    } 
    else if ($t == "tom.com")
    {
        return "mail.tom.com";
    } 
    else if ($t == "vip.sina.com") 
    {
        return "vip.sina.com";
    } 
    else if ($t == "sina.com.cn" || $t == "sina.com") 
    {
        return "mail.sina.com.cn";
    } 
    else if ($t == "tom.com") 
    {
        return "mail.tom.com";
    } 
    else if ($t == "yahoo.com.cn" || $t == "yahoo.cn")
    {
        return "mail.cn.yahoo.com";
    } 
    else if ($t == "tom.com")
    {
        return "mail.tom.com";
    }
    else if ($t == "yeah.net") 
    {
        return "www.yeah.net";
    } 
    else if ($t == "21cn.com") 
    {
        return "mail.21cn.com";
    } 
    else if ($t == "hotmail.com") 
    {
        return "www.hotmail.com";
    } 
    else if ($t == "sogou.com") {
        return "mail.sogou.com";
    }
    else if ($t == "188.com") {
        return "www.188.com";
    } 
    else if ($t == "139.com") 
    {
        return "mail.10086.cn";
    } 
    else if ($t == "189.cn")
    {
        return "webmail15.189.cn/webmail";
    }
    else if ($t == "wo.com.cn")
    {
        return "mail.wo.com.cn/smsmail";
    } 
    else if ($t == "139.com") 
    {
        return "mail.10086.cn";
    } 
    else 
    {
        return "";
    }
};
</script>
</html>