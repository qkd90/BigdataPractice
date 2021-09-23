$(function()
{
	// 跳转到指定的邮箱登录页面
    $(".email-a").click(function () 
    {
        var url = $(".email-a").text();
        mail_url = gotoEmail(url);
        
        if (mail_url != "") 
        {
            $(".email-a").attr("href", "http://" + mail_url);
        } 
        else 
        {
            return;
        }
    });
    
    // 再次发送邮件
	$("#J_resend_btn").click(reSendMail);
});

//功能：根据用户输入的Email跳转到相应的电子邮箱首页
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

function reSendMail()
{
	var email = getEvtTgt().attr("email");
	var uid = getEvtTgt().attr("uid");
	
	$.getJSON("/passport/resend/email", {email : email, uid : uid}, function (result)
	{
		if (result.errorCode == 0)
		{
			$(".resend-msg").show();
		}
	});
}