$(function()
{
	$("#J_username").placeholder({tip_class: "input-label"});
	$("#J_password").placeholder({tip_class: "input-label"});
	
	$("input").focus(function()
	{
		$(this).addClass("selected");
        document.onkeydown = function() 
        {
            if (event.keyCode == 13) {
                //js 监听对应的id
                $("#J_submit-btn").click();
                return false;
            }
        };
	});
	
	$("input").focusout(function()
	{
		  $(this).removeClass("selected");
	});
	
	$("#J_submit-btn").click(function()
	{
		$(".log-submit").addClass("log-submit-on");
		try
		{
			$("#J_login-form input").each(function()
			{ 
				$(this).validate({callback : function(msg)
				{ 
					$("#login_errinfo").html(msg).show();
					$(".log-submit").removeClass("log-submit-on");
				}}); 
			});
			$.post("loginAct", $("#J_login-form").serialize(), function(result)
			{
				if (result.errorCode == 0)
				{
					if($("#J_reb-login").attr("checked") == "checked")
			    	{
						location.href = result.resultList.data[0].uri + "&rememberme=1";
			    	}
			    	else
			    	{
			    		location.href = result.resultList.data[0].uri;
			    	}
				} 
				else if (result.errorCode == 58001)
				{
					$("#actfail-login").show();
					$(".log-submit").removeClass("log-submit-on");
				}
				else
				{
					$("#login_errinfo").html(result.errorMsg).show();
					$(".log-submit").removeClass("log-submit-on");
				}
				
			}, "json");
			$("#login_errinfo").hide();
			
		} 
		catch (e) {}
		
	});
	
	$(".goto-email").click(function () 
    {
        $("#J_username").validate({callback : function(msg)
		{ 
        	$("#actfail-login").hide();
			$("#login_errinfo").html(msg).show();
			return;
		}});
        
        var url = $("#J_username").val();
        mail_url = gotoEmail(url);
        if (mail_url != "") 
        {
        	$(".goto-email").attr("href", "http://" + mail_url);
        } 
        else 
        {
            return;
        }
    });
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