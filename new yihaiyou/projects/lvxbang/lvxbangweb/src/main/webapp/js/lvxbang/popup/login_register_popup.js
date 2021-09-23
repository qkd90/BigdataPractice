var login_success_fn = null;
//登录状态（判断是否跳转）
//var logStatus = false;
function login_popup(fn) {
    login_success_fn = fn;
    $(".mask").show();
    $(".denglux").fadeIn();
}

function register_popup() {
    $(".mask").show();
    $(".zhuchex").fadeIn()
}

function to_login_popup() {
    $(".mask,.cq_login").hide();
    login_popup();
}

function to_register_popup() {
    $(".mask,.cq_login").hide();
    register_popup();
}



$(function () {
    //输入时横线加深
    $('.border_bottom').focus(function(){
        $(this).parent().css('border-bottom','1px solid #bbbbbb');
    }).blur(function(){
        $(this).parent().css('border-bottom','1px solid #eef0f5');
    });
    //记住账号样式
    $(".login .checkbox").click(function(){
        if($(this).hasClass('checked')){
            $(this).removeClass("checked")
        }else{
            $(this).addClass("checked")
        }
    });
    //关闭
    $(".cq_login .close").click(function () {
        $(".mask,.cq_login").hide();
    });
    //退出登录时移除cookie，不做自动登录不需要移除
    //$("#loginStatus").find(".exit").click(function () {
    //    delCookie("JON");
    //});

    //登陆验证
    //焦点移开时判断是否为空
    $(document.body).delegate('.login_li input', 'blur', function () {
        var val = $(this).val();
        var placeholder = $(this).attr("placeholder");
        if (val != "" && val != "null") {
            $(this).next("em").hide();
            $(".error").animate({"height": "0px", "opacity": 1}, 0);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
        } else {
            $(this).next("em").show();
            $(".error").text(placeholder).animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
        }

    });
    var account = getCookie("JON");
    if (account) {
        //var user = json;
        //autoLogin(user); 暂时不要自动登录的功能
        $("#login_account").val(account)
    }
    Account.init();
});

function autoLogin(user) {
    if (isNull($('#userMessage').html())) {
        $.post("/lvxbang/login/doLogin.jhtml",
            user, function (result) {
                if (result.success == true) {
                    $(".mask,.cq_login").hide();
                    $('#logoutStatus').remove();
                    if (isNull($('#userMessage').html())) {
                        var res = template("tpl-has-user-item", result);
                        $('#head_p').after(res);
                    }
                    messageCount();
                }
            }
            ,"json");
    }
}

//登陆提交判断
function toLogin() {
    var emptyCount = 0;
    var emptyIndex = -1;
    var account;
    var password;
    var index = 0;

    $('.login_lil input').each(function (i) {
        var value = $(this).val();

        if (index == 0) {
            account = value;
            index++;
        } else {
            password = value;
        }
        if (value == '') {
            emptyCount++;
            $(this).next("em").show();
            emptyIndex = i;
        } else {
            $(this).next("em").hide();
        }
    });

    if (emptyCount > 1) {
        $(".error").text("请输入手机号和密码").animate({"height": "60px", "opacity": 1}, 400);
        setTimeout(function () {
            $(".error").animate({"height": "0px", "opacity": 0}, 200);
        }, 2000);
    } else if (emptyCount > 0) {
        if (emptyIndex == 0) {
            $(".error").text("请输入手机号").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
        } else {
            $(".error").text("请输入密码").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
        }
    } else {
        $(".error").animate({"height": "0px", "opacity": 0}, 400);
        setTimeout(function () {
            $(".error").animate({"height": "0px", "opacity": 0}, 200);
        }, 2000);
        var mobile = /^((1[3,5,8,7,4]{1})\d{9})$/;
        if (!account.match(mobile) && !isEmail(account)) {
            //
            $(".error").text("账号格式不正确，请重新输入").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }

        //提交动作写在这里
        Account.login();
    }

}

function toRegister() {


    var emptyCount = 0;
    var emptyIndex = -1;
    $('.login_lir input').each(function (i) {
        var value = $(this).val();
        if (value == '') {
            emptyCount++;
            $(this).next("em").show();
            emptyIndex = i;
        } else {
            $(this).next("em").hide();
        }
    });

    if ($(".checkbox").hasClass('checked')) {
        $(".security em").hide();
    } else {
        $(".security em").show();
    }

    if (emptyCount > 2) {
        $(".error").text("请输入手机号和密码").animate({"height": "60px", "opacity": 1}, 400);
        setTimeout(function () {
            $(".error").animate({"height": "0px", "opacity": 0}, 200);
        }, 2000);
    } else if (emptyCount > 0) {


        if (emptyIndex == 0) {
            $(".error").text("请输入手机号").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
        } else {
            $(".error").text("请输入验证码和密码").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
        }
    } else {
        if ($(".checkbox_r").hasClass('checked')) {
            $(".error").animate({"height": "0px", "opacity": 0}, 400);
            Account.register();
        } else {
            $(".error").text("请接受《旅行帮用户协议》！").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
        }
        //提交动作写在这里
        //Account.register();

    }
}

function KeyDown() {
    if (event.keyCode == 13) {
        event.returnValue = false;
        event.cancel = true;
        Account.login();
    }
}


/**
 * Created by vacuity on 16/1/3.
 */

var Account = {
    mobile: /^((1[3,5,8,7,4]{1})\d{9})$/,

    sendSms: function () {
        if($("#validateVlue_ADMIN").val() == ""){
            $(".error").text("请输入验证码!").animate({"height": "60px", "opacity": 1}, 400);
            $("#validateVlue_ADMIN").show();
            $("#validateVlue_ADMIN").focus();
            return false;
        }
        var account = $("#register_account").val();
        if (account == null || account == "") {
            $(".error").text("请输入手机号").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        if (!account.match(Account.mobile)) {
            //
            $(".error").text("手机号格式不正确，请重新输入").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        $.post("/lvxbang/register/validatePhone.jhtml",
            {
                account: account,
                validateVlue: $("#validateVlue_ADMIN").val()
            }, function (result) {
                if (result.success) {
                    $.post("/lvxbang/login/sendVerificationSms1.jhtml",
                        {
                            account: account,
                            validateVlue: $("#validateVlue_ADMIN").val()
                        }, function (result) {
                            if (result.success) {
                                $(".error").text("验证码发送成功").animate({"height": "60px", "opacity": 1}, 400);
                                setTimeout(function () {
                                    $(".error").animate({"height": "0px", "opacity": 0}, 200);
                                }, 2000);
                                return true;
                            } else {
                                $(".error").text(result.errMsg).animate({"height": "60px", "opacity": 1}, 400);
                                setTimeout(function () {
                                    $(".error").animate({"height": "0px", "opacity": 0}, 200);
                                }, 2000);
                                return false;
                            }
                        }
                        ,"json");
                } else {
                    $(".error").text(result.errMsg).animate({"height": "60px", "opacity": 1}, 400);
                    setTimeout(function () {
                        $(".error").animate({"height": "0px", "opacity": 0}, 200);
                    }, 2000);
                    return false;
                }
            }
            ,"json");

    },

    forgotPasswordSms: function () {
        if($("#validateVlue_ADMIN").val() == ""){
            $(".error").text("请输入验证码！").animate({"height": "60px", "opacity": 1}, 400);
            $("#validateVlue_ADMIN").show();
            $("#validateVlue_ADMIN").focus();
            return false;
        }
        var account = $("#login_account").val();
        if (account == null || account == "") {
            $(".error").text("请输入手机号").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        if (!account.match(Account.mobile)) {
            //
            $(".error").text("手机号格式不正确，请重新输入").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        $.post("/lvxbang/login/forgotPwd.jhtml",
            {
                account: account
            }, function (result) {
                if (result.success) {
                    $.post("/lvxbang/login/sendVerificationSms1.jhtml",
                        {
                            account: account,
                            validateVlue: $("#validateVlue_ADMIN").val()
                        }, function (result) {
                            if (result.success) {
                                $(".error").text("验证码发送成功").animate({"height": "60px", "opacity": 1}, 400);
                                setTimeout(function () {
                                    $(".error").animate({"height": "0px", "opacity": 0}, 200);
                                }, 2000);
                                return true;
                            } else {
                                $(".error").text(result.errMsg).animate({"height": "60px", "opacity": 1}, 400);
                                setTimeout(function () {
                                    $(".error").animate({"height": "0px", "opacity": 0}, 200);
                                }, 2000);
                                return false;
                            }
                        }
                        ,"json" );
                } else {
                    $(".error").text(result.errMsg).animate({"height": "60px", "opacity": 1}, 400);
                    setTimeout(function () {
                        $(".error").animate({"height": "0px", "opacity": 0}, 200);
                    }, 2000);
                    return false;
                }
            }
            ,"json");

    },

    register: function () {
        var account = $("#register_account").val();
        var smsCode = $("#smsCode").val();
        var password = $("#register_password").val();

        if (account == null || account == "") {
            $(".error").text("请输入手机号").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        if (smsCode == null || smsCode == "") {
            $(".error").text("请输入验证码").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        if (!account.match(Account.mobile)) {
            //
            $(".error").text("手机号格式不正确，请重新输入").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        if (password.length < 6) {
            $(".error").text("密码长度不能少于6位").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }

        $.post("/lvxbang/register/doRegisterr.jhtml",
            {
                account: account,
                smsCode: smsCode,
                password: password

            }, function (result) {
                if (result.success) {
                    // todo 注册成功

                    $(".error").text("注册成功,自动登录").animate({"height": "60px", "opacity": 1}, 400);
                    setTimeout(function () {
                        window.location.href = result.url;
                    }, 2000);//延迟1秒
                    //$(".error").text("恭喜您，注册成功").animate({"height":"60px","opacity":1},400);
                    //window.location.href = result.url;
                    //$(".error").text("恭喜您，注册成功").animate({"height":"60px","opacity":1},400);
                } else {
                    $(".error").text(result.errMsg).animate({"height": "60px", "opacity": 1}, 400);
                    setTimeout(function () {
                        $(".error").animate({"height": "0px", "opacity": 0}, 200);
                    }, 2000);
                }
            }
            ,"json");

    },

    forgotPassword: function () {
        var account = $("#account").val();
        var smsCode = $("#smsCode").val();
        var password = $("#password").val();

        if (account == null || account == "") {
            $(".error").text("请输入手机号").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        if (smsCode == null || smsCode == "") {
            $(".error").text("请输入验证码").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        if (!account.match(Account.mobile)) {
            //
            $(".error").text("手机号格式不正确，请重新输入").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        if (password.length < 6) {
            $(".error").text("密码长度不能少于6位").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }

        $.post("/lvxbang/register/updatePassword.jhtml",
            {
                account: account,
                smsCode: smsCode,
                password: password

            }, function (result) {
                if (result.success) {
                    // todo 更改密码成功
                    window.location.href = result.url;
                    //$(".error").text("恭喜您，密码更改成功").animate({"height":"60px","opacity":1},400);
                } else {
                    $(".error").text(result.errMsg).animate({"height": "60px", "opacity": 1}, 400);
                    setTimeout(function () {
                        $(".error").animate({"height": "0px", "opacity": 0}, 200);
                    }, 2000);
                }
            },"json");

    },

    login: function () {
        var account = $("#login_account").val();
        var password = $("#login_password").val();

        if (account == null || account == "") {
            $(".error").text("请输入手机号").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        if (password == null || password == "") {
            $(".error").text("请输入密码").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }
        if (!account.match(Account.mobile) && !isEmailA(account)) {
            //
            $(".error").text("账号格式不正确，请重新输入").animate({"height": "60px", "opacity": 1}, 400);
            setTimeout(function () {
                $(".error").animate({"height": "0px", "opacity": 0}, 200);
            }, 2000);
            return;
        }


        //提交动作写在这里
        $.post("/lvxbang/login/doLogin.jhtml",
            {
                account: account,
                password: password

            }, function (result) {
                if (result.success == true) {
                    var logintime = {};
                    logintime.userName = result.userName;
                    setCookie("logintime", JSON.stringify(logintime));
                    if($(".security").find(".checkbox").hasClass("checked")) {
                        //var json = {};
                        //json.account = account;
                        //json.password = password;
                        //setCookie("JON", btoa(JSON.stringify(json)));
                        setCookie("JON", account);

                    }
                    $(".cq_login .error").text("登录成功").animate({"height": "60px", "opacity": 1}, 400);
                    if ($('#to_order_url').val() != "") {
                        var url = $('#to_order_url').val();
                        $('#to_order_url').val('');
                        setTimeout(function () {
                            window.location.href = url;
                        }, 1100);
                    } else {

                    setTimeout(function () {
                        $(".mask,.cq_login").hide();
                        $('#logoutStatus').remove();
                        if (isNull($('#userMessage').html())) {
                            var res = template("tpl-has-user-item", result);
                            $('#head_p').after(res);
                        }
                        messageCount();
                        if (login_success_fn) {
                            login_success_fn();
                            login_success_fn = null;
                        }
                    }, 1100);
                    }
                    //window.location.href = result.url;
                } else {
                    $(".error").text(result.errMsg).animate({"height": "60px", "opacity": 1}, 400);
                    setTimeout(function () {
                        $(".error").animate({"height": "0px", "opacity": 0}, 200);
                    }, 2000);
                }


            },"json"

        );
    },

    loginQQ: function () {
        // QQ登陆
        window.logincallback = function () {
            Account.loginSuccess();
        };
        setCookie("qq_login_forward", document.location.href+";path=/");
        window.open("/lvxbang/third/qq.jhtml", "newwindow", "height=575,width=750,toolbar=no,menubar=no");
    },

    loginWeibo: function() {
        window.logincallback = function () {
            Account.loginSuccess();
        };
        setCookie("qq_login_forward", document.location.href+";path=/");
        window.open("/lvxbang/third/weibo.jhtml", "newwindow", "height=575,width=750,toolbar=no,menubar=no");
    },

    loginWeixin: function () {
        window.logincallback = function () {
            Account.loginSuccess();
        };
        setCookie("qq_login_forward", document.location.href+";path=/");
        window.open("/lvxbang/third/weixin.jhtml", "newwindow", "height=575,width=750,toolbar=no,menubar=no");
    },

    loginSuccess: function () {
        $.getJSON("/lvxbang/third/loginData.jhtml", function (result) {
            var logintime = {};
            logintime.userName = result.userName;
            setCookie("logintime", JSON.stringify(logintime));
        });
        $(".error").text("登录成功").animate({"height": "60px", "opacity": 1}, 400);
        if ($('#to_order_url').val() != "") {
            var url = $('#to_order_url').val();
            $('#to_order_url').val('');
            setTimeout(function () {
                window.location.href = url;
            }, 1100);
        } else {
            setTimeout(function () {
                $(".mask,.cq_login").hide();
                $('#logoutStatus').remove();
                if (isNull($('#userMessage').html())) {
                    $.getJSON("/lvxbang/third/loginData.jhtml", function (result) {
                        var res = template("tpl-has-user-item", result);
                        $('#head_p').after(res);
                    });

                }
                messageCount();
                if (login_success_fn) {
                    login_success_fn();
                    login_success_fn = null;
                }
            }, 1100);
        }



    },

    init: function () {
        $(".qq").click(function () {
            Account.loginQQ();
        });
        $(".wx").click(function () {
            Account.loginWeixin();
        });
        $(".xl").click(function () {
            Account.loginWeibo();
        });
    }
};

//登录拦截：a标签to订单页面
function toOrderPage(order) {
    var url = $(order).attr("url");
    $('#to_order_url').val(url);
    if (has_no_User(null)) {
        return;
    }
    $('#to_order_url').val("");
    window.location.href = url;
}
//登录拦截：表单提交时判断是否已经登录，登录后跳到相应的页面
function formToOrderPage(order) {
    if (has_no_User(function () {
            $(order).parents(".traffic_flight_train").submit();
        })) {
        return;
    }
    $(order).parents(".traffic_flight_train").submit();
}

//退出时删除cookie中的logintime
function exitDelete() {
    delCookie("logintime");
    location.href = $(".header_nav .menu_panel .menu_list li").find("a").eq(0).attr("href") + "/lvxbang/login/exitLogin.jhtml";
}


