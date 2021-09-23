$(function()
{
	$("#J_username").placeholder({tip_class: "input-label"});
	$("#J_password").placeholder({tip_class: "input-label"});
	$("#J_password2").placeholder({tip_class: "input-label"});
	$("#J_vcode").placeholder({tip_class: "input-label"});
	$("#J_sms_code").placeholder({tip_class: "input-label"});
	
    $("#sendSMSBtn").click(function()
    {
        sendSMS();
    });

    var form = $("#J_register-form");
    var userNameBox = $("#J_username");
    userNameBox.change(function ()
    {
        if ($(this).val().indexOf("@")>=0)
        {
            form.find(".vcode").show();
            $("#J_vcode").attr("need", "need");
            form.find(".sms_code").hide();
            $("#J_sms_code").removeAttr("need").val("");
        }
        else
        {
            form.find(".sms_code").show();
            $("#J_sms_code").attr("need", "need");
            form.find(".vcode").hide();
            $("#J_vcode").removeAttr("need").val("");
        }
    });
    $("input").focus(function ()
    {

        document.onkeydown = function ()
        {
            if (event.keyCode == 13)
            {
                $("#J_submit-btn").click();
                return false;
            }
        };
    }).blur(function()
    {
        document.onkeydown = "";
    }).change(function()
    {
            $("#register_errinfo").hide();
    });

    $("#J_password").blur(function ()
    {
        var password = $(this).val();
        if (!validatePassword(password))
        {
            $("#register_errinfo").html("密码必须是6-20位字符").show();
        }

    });

	// 改变验证码图片
	$("#J_vcode-img").click(function()
	{
		$("#J_vcode-img").attr("src","checkCodeAction/captcha?"+Math.random());
	});
	
	$(".vcode-inf").click(function()
	{
		$("#J_vcode-img").attr("src","checkCodeAction/captcha?"+Math.random());
	});

    // 判断用户名是否被占用
    userNameBox.blur(function() {
    	
    	if (!userNameBox.val())
    	{
    		return;
    	}
        $.get("/passport/validateUsername",{"username": userNameBox.val()},function(data) {
            if (data.errorCode == 0) {
                $("#register_errinfo").hide();
                return;
            }
            $("#register_errinfo").html(data.errorMsg).show();
        });
    });

	// 注册动作
	$("#J_submit-btn").click(function()
	{
        var regSubmit = $(".reg-submit");
		regSubmit.addClass("log-submit-on");
		try
		{
			$("#J_register-form").find("input").each(function()
			{ 
				$(this).validate({callback : function(msg)
				{ 
					$("#register_errinfo").html(msg).show();
					regSubmit.removeClass("log-submit-on");
				}}); 
			});

            var password = $("#J_password").val();

            if (!validatePassword(password)) {
                $("#register_errinfo").html("密码必须是6-20位字符").show();
                regSubmit.removeClass("log-submit-on");
                return false;
            }
			
			//确认密码判断是否一致 姜姝2014-6-18
			if (password != $("#J_password2").val())
			{
				$("#register_errinfo").html("两次密码输入不一致").show();
				$("#J_password2").focus();
				regSubmit.removeClass("log-submit-on");
				return false;
			}
			
			if ($("#agree-reg").attr("checked") != "checked")
			{
				$("#register_errinfo").html("您还未同意旅行帮用户协议").show();
				return false;
			}
			
			$.post("registerAct", $("#J_register-form").serialize(), function(result)
			{
				if (result.errorCode == 0)
				{
                    if (result.errorMsg=="mobile")
                    {
                        $.post("loginAct",
                            {
                                username: $("#J_username").val(),
                                password: $("#J_password").val(),
                                clientId: result.resultList.data[0].clientId,
                                redirectUri: result.resultList.data[0].redirectUri
                            }, function(result_data)
                        {
                            if (result_data.errorCode == 0)
                            {
                                location.href = result_data.resultList.data[0].uri;
                            }
                            else if (result_data.errorCode == 58001)
                            {
                                $("#actfail-login").show();
                                $(".log-submit").removeClass("log-submit-on");
                            }
                            else
                            {
                                $("#login_errinfo").html(result_data.errorMsg).show();
                                $(".log-submit").removeClass("log-submit-on");
                            }

                        }, "json");
                    }
                    else
                    {
                        location.href = "actemail/" +  result.resultList.data[0];
                    }
				}
				else
				{
					$("#register_errinfo").html(result.errorMsg).show();
                    $("#J_username_warn").css("display", "none");
					regSubmit.removeClass("log-submit-on");
				}
			}, "json");
			
			$("#register_errinfo").hide();
		} 
		catch (e) {}
	});
});

var reSendTimeout;
var reSendInterval;

function sendSMS() {
    var mobile = $("#J_username").val();
    if(mobile.length==0)
    {
        $("#register_errinfo").html("请输入手机号码！").show();
        $("#J_username").focus();
        return false;
    }
    if(mobile.length!=11)
    {
        $("#register_errinfo").html("请输入有效的手机号码！").show();
        $("#J_username").focus();
        return false;
    }

    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if(!myreg.test(mobile))
    {
        $("#register_errinfo").html("请输入有效的手机号码！").show();
        $("#J_username").focus();
        return false;
    }

    $.get("/passport/validateUsername",{"username": mobile},function(data) {
        if (data.errorCode != 0)
        {
            $("#register_errinfo").html(data.errorMsg).show();
            return;
        }
        $("#register_errinfo").hide();

        $.get("sendRegisterSms?mobile=" + mobile, function (result)
        {
            if (result.errorCode != 0)
            {
                clearInterval(reSendInterval);
                $("#register_errinfo").html("短信发送失败").show();
            }
        });
        reSendTimeout = 60;
        $("#sendSMSBtn").addClass("btn-inactive").text(reSendTimeout+"秒后重新发送短信").unbind("click");
        reSendInterval = setInterval(function ()
        {
            reSendTimeout--;
            if(reSendTimeout<=0)
            {
                clearInterval(reSendInterval);
                $("#sendSMSBtn").removeClass("btn-inactive").text("免费获取验证短信").click(function()
                {
                    sendSMS();
                });
                return;
            }
            var content = reSendTimeout + "秒后重新发送短信";
            $("#sendSMSBtn").text(content);
        }, 1000);

    });
}

function validatePassword(password)
{
    return !(password != "" && password.length < 6 || password.length > 20);
}