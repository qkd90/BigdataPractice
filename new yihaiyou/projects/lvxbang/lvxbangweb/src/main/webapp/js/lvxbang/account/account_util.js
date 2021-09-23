/**
 * Created by vacuity on 16/1/3.
 */

var Account = {
    mobile : /^((1[3,5,8,7,4]{1})\d{9})$/,
    hasSend: false,

    sendSms: function() {
        if (Account.hasSend) {
            return;
        }
        if($("#validateVlue_ADMIN").val() == ""){
            promptWarn("请输入验证码!",1500);
            $("#validateVlue_ADMIN").show();
            $("#validateVlue_ADMIN").focus();
            return false;
        }
        var account = $("#account").val();
        if (account == null || account == "") {
            $(".error").text("请输入手机号").animate({"height":"60px","opacity":1},400);
            return;
        }
        if (!account.match(Account.mobile) && !isEmail(account)) {
            //
            $(".error").text("账号格式不正确，请重新输入").animate({"height":"60px","opacity":1},400);
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
                                $(".error").text("验证码发送成功").animate({"height":"60px","opacity":1},400);
                                return true;
                            } else {
                                $(".error").text(result.errMsg).animate({"height":"60px","opacity":1},400);
                                return false;
                            }
                        }
                        ,"json");
                } else {
                    $(".error").text(result.errMsg).animate({"height":"60px","opacity":1},400);
                    return false;
                }
            }
            ,"json");
        var second = 60;
        Account.hasSend = true;
        var timer = setInterval(function () {
            if (second == 0) {
                $("#sendSms").text("免费获取验证短信");
                Account.hasSend = false;
                clearInterval(timer);
            } else {
                $("#sendSms").text(second-- + "秒");
            }
        }, 1000);

    },

    forgotPasswordSms: function() {
        if($("#validateVlue_ADMIN").val() == ""){
            promptWarn("请输入验证码!",1500);
            $("#validateVlue_ADMIN").show();
            $("#validateVlue_ADMIN").focus();
            return false;
        }
        var account = $("#account").val();
        if (account == null || account == "") {
            $(".error").text("请输入手机号").animate({"height":"60px","opacity":1},400);
            return;
        }
        if (!account.match(Account.mobile)) {
            //
            $(".error").text("手机号格式不正确，请重新输入").animate({"height":"60px","opacity":1},400);
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
                                $(".error").text("验证码发送成功").animate({"height":"60px","opacity":1},400);
                                return true;
                            } else {
                                $(".error").text(result.errMsg).animate({"height":"60px","opacity":1},400);
                                return false;
                            }
                        }
                    );
                } else {
                    $(".error").text(result.errMsg).animate({"height":"60px","opacity":1},400);
                    return false;
                }
            }
            ,"json");

    },

    register: function() {
        var account = $("#account").val();
        var smsCode = $("#smsCode").val();
        var password = $("#password").val();

        if (account == null || account == "") {
            $(".error").text("请输入手机号").animate({"height":"60px","opacity":1},400);
            return;
        }
        if (smsCode == null || smsCode == "") {
            $(".error").text("请输入验证码").animate({"height":"60px","opacity":1},400);
            return;
        }
        if (!account.match(Account.mobile)) {
            //
            $(".error").text("手机号格式不正确，请重新输入").animate({"height":"60px","opacity":1},400);
            return;
        }
        if (password.length < 6) {
            $(".error").text("密码长度不能少于6位").animate({"height":"60px","opacity":1},400);
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
                    var str = result.url;
                    str = str.substring(str.indexOf("login"), str.length);
                    var random = parseInt(Math.random() * 10000);
                    if (str == "login/login.jhtml") {
                        window.location.href = '/lvxbang/index/index.jhtml?random=' + random;
                    } else {
                        if (result.url.indexOf("?")>=0) {
                            window.location.href = result.url + "&random=" + random;
                        } else {
                            window.location.href = result.url + "?random=" + random;
                        }
                        //$(".error").text("恭喜您，注册成功").animate({"height":"60px","opacity":1},400);
                    }
                } else {
                    $(".error").text(result.errMsg).animate({"height":"60px","opacity":1},400);
                }
            }
            ,"json");
    },

    forgotPassword: function() {
        var account = $("#account").val();
        var smsCode = $("#smsCode").val();
        var password = $("#password").val();

        if (account == null || account == "") {
            $(".error").text("请输入手机号").animate({"height":"60px","opacity":1},400);
            return;
        }
        if (smsCode == null || smsCode == "") {
            $(".error").text("请输入验证码").animate({"height":"60px","opacity":1},400);
            return;
        }
        if (!account.match(Account.mobile)) {
            //
            $(".error").text("手机号格式不正确，请重新输入").animate({"height":"60px","opacity":1},400);
            return;
        }
        if (password.length < 6) {
            $(".error").text("密码长度不能少于6位").animate({"height":"60px","opacity":1},400);
            return;
        }

        $.post("/lvxbang/register/updatePassword.jhtml",
            {
                account: account,
                smsCode: smsCode,
                password: password

            }, function (result) {
                var random = parseInt(Math.random() * 10000);
                if (result.success) {
                    // todo 更改密码成功
                    if (result.url.indexOf("?")>=0) {
                        window.location.href = result.url + "&random=" + random;
                    } else {
                        window.location.href = result.url + "?random=" + random;
                    }
                    //$(".error").text("恭喜您，密码更改成功").animate({"height":"60px","opacity":1},400);
                } else {
                    $(".error").text(result.errMsg).animate({"height":"60px","opacity":1},400);
                }
            }
            ,"json");
    },

    login: function () {
        var account = $("#account").val();
        var password = $("#password").val();

        if (account == null || account == "") {
            $(".error").text("请输入账号").animate({"height": "60px", "opacity": 1}, 400);
            return;
        }
        if (password == null || password == "") {
            $(".error").text("请输入密码").animate({"height": "60px", "opacity": 1}, 400);
            return;
        }
        if (!account.match(Account.mobile) && !isEmail(account)) {
            //
            $(".error").text("账号格式不正确，请重新输入").animate({"height": "60px", "opacity": 1}, 400);
            return;
        }

        //提交动作写在这里
        $.post("/lvxbang/login/doLogin.jhtml",
            {
                account: account,
                password: password

            }, function (result) {
                var random = parseInt(Math.random() * 10000);
                if (result.success == true) {
                    var logintime = {};
                    logintime.userName = result.userName;
                    setCookie("logintime", JSON.stringify(logintime));
                    if($(".security").find(".checkbox").hasClass("checked")) {
                        //var json = {};
                        //json.account = account;
                        //json.password = password;
                        setCookie("JON", account);
                    }
                    if (result.url.indexOf("?")>=0) {
                        window.location.href = result.url + "&random=" + random;
                    } else {
                        window.location.href = result.url + "?random=" + random;
                    }
                } else {
                    $(".error").text(result.errMsg).animate({"height": "60px", "opacity": 1}, 400);
                }
            }
            ,"json");
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
        $.getJSON("/lvxbang/third/lastUrl.jhtml", function (result) {
            var logintime = {};
            logintime.userName = result.userName;
            setCookie("logintime", JSON.stringify(logintime));
            var random = parseInt(Math.random() * 10000);
            if (result && result.success) {
                if (result.url.indexOf("?")>=0) {
                    window.location.href = result.url + "&random=" + random;
                } else {
                    window.location.href = result.url + "?random=" + random;
                }
            } else {
                window.location.href = "/lvxbang/index/index.jhtml?random=" + random;
            }

        });

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
        var account = getCookie("JON");
        if (account) {
            //var user = JSON.parse(atob(json));
            //var user = json;
            $("#account").val(account);
        }
    }

};

$(function () {
    Account.init();
});
