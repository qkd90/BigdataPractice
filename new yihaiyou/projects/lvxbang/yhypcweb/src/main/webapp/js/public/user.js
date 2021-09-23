/**
 * Created by zzl on 2017/1/18.
 */
var YhyUser = {
    TipForAccount: "请输入用户名",
    TipForPassword: "请输入密码",
    TipForMobile: "手机号不正确",
    TipForCfmPassword: "确认密码不正确",
    TipForVcode: "验证码不正确",
    TipForSMSCode: "手机验证码不正确",
    ResendTimer: null,
    ResendTimeout: 60,
    LoginCallback: null,
    RegisterCallback: null,
    init: function() {
        YhyUser.initComp();
        YhyUser.initEvt();
        YhyUser.autoLogin();
    },
    initComp: function() {
        //var wxLogin = new WxLogin({
        ({
            id: "wx_code_ctn",
            appid: WEB_WX_APP_ID,
            scope: "snsapi_login",
            redirect_uri: WEB_WX_RETURN_URI,
            state: "",
            style: "",
            href: ""
        });
    },
    initEvt: function() {
        // refresh validate code btn
        $('#popup_modal img.vcode_img, #popup_modal a.vcode_a').on('click', function(event) {
            event.stopPropagation();
            var $img;
            if ($(this).hasClass("vcode_img")) {
                $img = $(this);
            } else if ($(this).hasClass("vcode_a")) {
                $img = $(this).siblings("span.vcode_span").find('img.vcode_img');
            }
            var src;
            if ($(this).hasClass("vcode_login_a")) {
                src = "/image/dologin_vcode.jsp?c=" + Math.random().toString(36).substring(2);
            } else {
                src = "/image/login_vcode.jsp?c=" + Math.random().toString(36).substring(2);
            }
            $img.prop("src", src);
        });
        // all modal close event
        $('#popup_modelBody a.close-btn').on('click', function(event) {
            event.stopPropagation();
            $('#popup_modelBody').find('img.vcode_img').click();
        })
        // login modal close event
        $('#loginComputer a.close-btn').on('click', function(event) {
            event.stopPropagation();
            $('#loginComputer p.tips').html(null);
            $.form.reset({formId: '#login_form'});
        });
        // wx login modal close event
        $('#loginWeichart a.close-btn').on('click', function(event) {
            event.stopPropagation();
            var wxSrc = $('#wx_code_ctn iframe').attr('src');
            var r = Math.random().toString(16).substring(2);
            var src = wxSrc + "&r=" + r;
            $('#wx_code_ctn iframe').attr('src', src);
        });
        // register check modal close event
        $('#accountSignin a.close-btn').on('click', function(event) {
            event.stopPropagation();
            $('#accountSignin p.tips').html(null);
            $.form.reset({formId: '#register_form'});
        });
        // register modal close event
        $('#accountCheck a.close-btn').on('click', function(event) {
            event.stopPropagation();
            $('#accountSignin p.tips').html(null);
            $('#accountCheck p.tips').html(null);
            $.form.reset({formId: '#register_form'});
            $.form.reset({formId: '#accountRegCheckForm'});
            $('#accountCheck span.send-mobile').html(null);
            $('#accountCheck span.code-timeout').html(null);
        });
        // start forget password modal close event
        $('#passwordForget a.close-btn').on('click', function(event) {
            event.stopPropagation();
            $('#passwordForget p.username-tips').html(null);
            $.form.reset({formId: '#startForgetPwdForm'});
        });
        // check forget password modal close event
        $('#forgetPwdCheck a.close-btn').on('click', function(event) {
            event.stopPropagation();
            $('#forgetPwdCheck p.tips').html(null);
            $('#forgetPwdCheck p.tips').html(null);
            $.form.reset({formId: '#startForgetPwdForm'});
            $('#forgetPwdCheck input[name = "smsCode"]').val(null);
        });
        // reset password modal close event
        $('#resetPwd a.close-btn').on('click', function(event) {
            event.stopPropagation();
            $('#passwordForget p.username-tips').html(null);
            $.form.reset({formId: '#startForgetPwdForm'});
            $('#forgetPwdCheck p.tips').html(null);
            $('#forgetPwdCheck p.tips').html(null);
            $('#forgetPwdCheck input[name = "smsCode"]').val(null);
            $.form.reset({formId: '#resetPwd_form'});
        });
        // jump to wechat login
        $('#jumpToWechat').on('click', function(event) {
            event.stopPropagation();
            Popup.jumpToDialog("loginWeichart");
            // clear login form info
            $('#loginComputer p.tips').html(null);
            $.form.reset({formId: '#login_form'});
        });
        // jump PC login
        $('#jumpToPC').on('click', function(event) {
            event.stopPropagation();
            Popup.jumpToDialog("loginComputer");
            var wxSrc = $('#wx_code_ctn iframe').attr('src');
            var r = Math.random().toString(16).substring(2);
            var src = wxSrc + "&r=" + r;
            $('#wx_code_ctn iframe').attr('src', src);
        });
        // jump to register
        $('#jumpToRegister').on('click', function (event) {
            event.stopPropagation();
            Popup.jumpToDialog("accountSignin");
            // clear login form info
            $('#loginComputer p.tips').html(null);
            $.form.reset({formId: '#login_form'});
        });
        // jump to password forgot
        $('#jumpToPwdforgot').on('click', function(event) {
            event.stopPropagation();
            Popup.jumpToDialog("passwordForget");
            // clear login form info
            $('#loginComputer p.tips').html(null);
            $.form.reset({formId: '#login_form'});
        });
        // go login btn
        $(document).on('click', '#login_info_ul li.login span.loginbt', function(event) {
            event.stopPropagation();
            YhyUser.goLogin();
        });
        // go register btn
        $(document).on('click', '#login_info_ul li.login span.register', function(event) {
            event.stopPropagation();
            YhyUser.goRegister();
        });
        // input event
        $('#account').on('keyup', function (event) {
            event.stopPropagation();
            if ($(this).val() && $(this).val() != "") {
                $(this).next('p.login-tips').html(null);
            }
        });
        $('#pwd').on({'keyup': function (event) {
            event.stopPropagation();
            if ($(this).val() && $(this).val() != "") {$(this).next('p.login-tips').html(null);}
        }, 'keydown': function(event) {
            event.stopPropagation();
            if (event.keyCode === 13) {YhyUser.doLogin();}
        }});
        $('#register_form input[name = "account"]').on('keyup', function(event) {
            event.stopPropagation();
            if ($(this).val() && $(this).val() != "") {
                $(this).next('p.input-tips').html(null);
            }
        });
        $('#register_form input[name = "mobile"]').on('keyup', function(event) {
            event.stopPropagation();
            if ($(this).val() && $(this).val() != "") {
                $(this).next('p.input-tips').html(null);
            }
        });
        $('#register_form input[name = "pwd"]').on('keyup', function(event) {
            event.stopPropagation();
            if ($(this).val() && $(this).val() != "") {
                $(this).next('p.input-tips').html(null);
            }
        });
        $('#register_form input[name = "vcode"]').on('keyup', function(event) {
            event.stopPropagation();
            if ($(this).val() && $(this).val() != "") {
                $('.account-signin-group').next('p.input-tips').html(null);
            }
        });
        $('#accountCheck input[name = "smsCode"]').on('keyup', function(event) {
            if ($(this).val() && $(this).val() != "") {
                $('#accountCheck p.smscode-msg').html(null)
            }
        });
        $('#passwordForget input[name = "account"]').on('keyup', function(event) {
            if ($(this).val() && $(this).val() != "") {
                $('#passwordForget p.username-tips').html(null)
            }
        });
        $('#passwordForget input[name = "vcode"]').on('keyup', function(event) {
            if ($(this).val() && $(this).val() != "") {
                $('#passwordForget .passwordforget-group').next('p.username-tips').html(null);
            }
        });
        $('#forgetPwdCheck input[name = "smsCode"]').on('click', function() {
            if ($(this).val() && $(this).val() != "") {
                $('#forgetPwdCheck p.smscode-msg').html(null)
            }
        });
        // do login btn
        $('#doLoginBtn').on('click', function(event) {
            event.stopPropagation();
            YhyUser.doLogin();
        });
        // do logout btn
        $(document).on('click', '#login_info_ul li.login_scs span.logout', function(event) {
            event.stopPropagation();
            YhyUser.doLogout();
        });
        // do check register btn
        $('#preRegister').on('click', function (event) {
            event.stopPropagation();
            YhyUser.doPreRegister();
        });
        // do register btn
        $('#doRegisterBtn').on('click', function(event) {
            event.stopPropagation();
            YhyUser.doRegister();
        });
        // resend sms code btn
        $('#accountCheck p.resend-timeout').on('click', function (event) {
            event.stopPropagation();
            if (YhyUser.ResendTimeout == 60) {
                YhyUser.resendRegCode();
            }
        });
        $('#forgetPwdCheck p.resend-timeout').on('click', function(event) {
            event.stopPropagation();
            if (YhyUser.ResendTimeout == 60) {
                YhyUser.resendForgetPwdCode();
            }
        });
        // back to login btn
        $('#popup_modal .backToLogin').on('click', function(event) {
            event.stopPropagation();
            Popup.jumpToDialog('loginComputer');
        });
        // back to login from reg
        $('#accountSignin span.login_r').on('click', function(event) {
            event.stopPropagation();
            $('#accountSignin p.tips').html(null);
            $('#accountCheck p.tips').html(null);
            $.form.reset({formId: '#register_form'});
            $.form.reset({formId: '#accountRegCheckForm'});
            $('#accountCheck span.send-mobile').html(null);
            $('#accountCheck span.code-timeout').html(null);
            Popup.jumpToDialog('loginComputer');
        });
        // start forget password
        $('#startForgetPwd').on('click', function(event) {
            event.stopPropagation();
            YhyUser.doStartForgetPwd();
        });
        // do check forget password
        $('#doCheckForgetPwd').on('click', function (event) {
            event.stopPropagation();
            YhyUser.doCheckForgetPassword();
        });
        // do reset password btn
        $('#doResetPwd').on('click', function(event) {
            event.stopPropagation();
            YhyUser.doResetPassword();
        });
    },
    doLogin: function() {
        var ac = $('#account').val();
        var pwd = $('#pwd').val();
        var code = $('#loginComputer input[name = "vcode"]').val();
        if (!ac || ac == "") {
            $('#account').next('p.login-tips').html(YhyUser.TipForAccount);
            return;
        }
        if (!pwd || pwd == "") {
            $('#pwd').next('p.login-tips').html(YhyUser.TipForPassword);
            return;
        }
        if (!code || code == "") {
            $('.account-signin-group').next('p.login-tips').html(YhyUser.TipForVcode);
            return;
        }
        $('#loginComputer input[name=password]').val(hex_md5(pwd));
        $.form.commit({
            formId: '#login_form',
            url: '/yhypc/user/doLogin.jhtml',
            success: function(result) {
                if (result.success) {
                    // set cookie
                    setCookie("LOGIN_USER", JSON.stringify(result.LOGIN_USER));
                    if ($('input[name="autoLogin"]').prop('checked') === true) {
                        setCookie("autoLogin", true);
                        setCookie("alc", result.alc);
                    }
                    if (YhyUser.LoginCallback && typeof YhyUser.LoginCallback === "function") {
                        $('#login_info_ul li.login').remove();
                        $('#login_info_ul').append(template('login_info_item', result.LOGIN_USER));
                        Popup.closeDialog();
                        // clear input
                        $.form.reset({formId: '#login_form'});
                        YhyUser.LoginCallback();
                    } else {
                        var t = 3;
                        var autoRd = setInterval(function(){
                            $('#pwd').next('p.login-tips').html(null).html('登录成功, ' + t + 's后自动返回');
                            if (t == 0) {
                                clearInterval(autoRd);
                                window.location.reload(true);
                            }
                            t--;
                        }, 1000);
                    }
                } else {
                    $('#pwd').next('p.login-tips').html(null).html(result.msg);
                }
            },
            error: function() {
                $('#pwd').next('p.login-tips').html(null).html('登录错误, 请重试!');
            }
        });
    },
    doPreRegister: function() {
        var $ac = $('#register_form').find('input[name = "account"]');
        var $mobile = $('#register_form').find('input[name = "mobile"]');
        var $pwd = $('#reg_pwd');
        var $cfm_pwd = $('#reg_cfm_pwd');
        var $vcode = $('#register_form').find('input[name = "vcode"]');
        var ac = $ac.val();
        var mobile = $mobile.val();
        var pwd = $pwd.val();
        var cfm_pwd = $cfm_pwd.val();
        var vcode = $vcode.val();
        if (!ac || ac == "") {
            $ac.next('p.input-tips').html(null).html(YhyUser.TipForAccount);
            return;
        }
        if (!mobile.match(Reg.telephoneReg)) {
            $mobile.next('p.input-tips').html(null).html(YhyUser.TipForMobile);
            return;
        }
        if (!pwd || pwd == "") {
            $pwd.next('p.input-tips').html(null).html(YhyUser.TipForPassword);
            return;
        }
        if (!cfm_pwd || cfm_pwd == "" || cfm_pwd != pwd) {
            $cfm_pwd.next('p.input-tips').html(null).html(YhyUser.TipForCfmPassword);
            return;
        }
        if (!vcode || vcode == "") {
            $('.account-signin-group').next('p.input-tips').html(null).html(YhyUser.TipForVcode);
            return;
        }
        $('#register_form').find('input[name = "password"]').val(hex_md5(pwd));
        $.form.commit({
            formId: '#register_form',
            url: '/yhypc/user/doPreRegister.jhtml',
            success: function(result) {
                if (result.success) {
                    $('#accountCheck span.send-mobile').html(null).html(result.mobile);
                    $('#accountCheck span.code-timeout').html(null).html(result.timeout);
                    Popup.jumpToDialog("accountCheck");
                    YhyUser.startCountLeftTime("accountCheck");
                } else {
                    $('.account-signin-group').next('p.input-tips').html(null).html(result.msg);
                }
            },
            error: function() {
                $('.account-signin-group').next('p.input-tips').html(null).html("出错啦! 稍后重试!");
            }
        });

    },
    doRegister: function() {
        var $smsCode = $('#accountCheck').find('input[name = "smsCode"]');
        var smsCode = $smsCode.val();
        if (!smsCode || smsCode == "") {
            $smsCode.siblings('p.smscode-msg').html(null).html(YhyUser.TipForSMSCode);
            return;
        } else {
            $smsCode.siblings('p.smscode-msg').html(null)
        }
        $.form.commit({
            formId: '#register_form',
            url: '/yhypc/user/doRegister.jhtml',
            extraData: {smsCode: smsCode},
            success: function(result) {
                if (result.success) {
                    Popup.jumpToDialog("accountRegSuccess");
                    YhyUser.resetLeftTimer("accountCheck");
                    // clear register info
                    $.form.reset({formId: '#register_form'});
                    $('#accountSignin p.tips').html(null);
                    $.form.reset({formId: "#accountRegCheckForm"});
                    $('#accountCheck span.send-mobile').html(null);
                    $('#accountCheck span.code-timeout').html(null);
                    $('#accountCheck p.tips').html(null);
                } else {
                    $('#accountCheck p.smscode-msg').html(null).html(result.msg);
                }
            },
            error: function() {
                $('#accountCheck p.smscode-msg').html(null).html("出错啦, 稍候重试");
            }

        });
    },
    resendRegCode: function() {
        if (YhyUser.ResendTimeout == 60) {
            $.ajax({
                url: '/yhypc/user/resendRegCode.jhtml',
                data: {vcode: $('#register_form').find('input[name = "vcode"]').val()},
                success: function(result) {
                    if (result.success) {
                        YhyUser.startCountLeftTime("accountCheck");
                    } else {
                        $('#register_form p.resend-timeout').html(null).html(result.msg);
                    }
                },
                error: function() {
                    $('#register_form p.resend-timeout').html(null).html("发送失败, 重新获取");
                }
            })
        }
    },
    resendForgetPwdCode: function() {
        if (YhyUser.ResendTimeout == 60) {
            $.ajax({
                url: '/yhypc/user/resendForgetPwdCode.jhtml',
                data: {vcode: $('#passwordForget input[name = "vcode"]').val()},
                success: function(result) {
                    if (result.success) {
                        YhyUser.startCountLeftTime("forgetPwdCheck");
                    } else {
                        $('#forgetPwdCheck p.resend-timeout').html(null).html(result.msg);
                    }
                },
                error: function() {
                    $('#forgetPwdCheck p.resend-timeout').html(null).html("发送失败, 重新获取");
                }
            });
        }
    },
    doLogout: function() {
        $.ajax({
            url: '/yhypc/user/doLogout.jhtml',
            success: function(result) {
                if (result.success) {
                    YhyUser.deleteLoginCookie();
                    $('#login_info_ul li.login').remove();
                    $('#login_info_ul').append(template('login_out_item'));
                    if (YhyPersonalCenter.isUserPage()) {
                        window.location.reload(true);
                    }
                } else {
                    alert("出错啦! 稍后重试!");
                }
            },
            error: function() {
                alert("出错啦! 稍后重试!");
            }
        });
    },
    doStartForgetPwd: function() {
        var $ac = $('#passwordForget input[name = "account"]');
        var ac = $ac.val();
        if (!ac || ac == "") {
            $ac.next('p.username-tips').html(null).html(YhyUser.TipForAccount);
            return;
        }
        $.form.commit({
            formId: "#startForgetPwdForm",
            url: '/yhypc/user/doStartForgetPwd.jhtml',
            success: function(result) {
                if (result.success) {
                    $('#forgetPwdCheck span.send-mobile').html(null).html(result.mobile);
                    $('#forgetPwdCheck span.code-timeout').html(null).html(result.timeout);
                    $('#forgetPwdCheck input[name = "fpcCode"]').val(result.fpcCode);
                    Popup.jumpToDialog("forgetPwdCheck");
                    YhyUser.startCountLeftTime("forgetPwdCheck");
                } else {
                    $('#passwordForget .passwordforget-group').next('p.username-tips').html(null).html(result.msg);
                }
            },
            error: function() {
                $('#passwordForget .passwordforget-group').next('p.username-tips').html(null).html("出错啦! 稍后重试!");
            }
        });
    },
    doCheckForgetPassword: function() {
        var $smsCode = $('#forgetPwdCheck input[name = "smsCode"]');
        var $fpcCode = $('#forgetPwdCheck input[name = "fpcCode"]');
        var smsCode = $smsCode.val();
        var fpcCode = $fpcCode.val();
        if (!smsCode || smsCode == "") {
            $smsCode.siblings('p.smscode-msg').html(null).html(YhyUser.TipForSMSCode);
            return;
        } else {
            $smsCode.siblings('p.smscode-msg').html(null)
        }
        $.ajax({
            url: '/yhypc/user/doCheckForgetPassword.jhtml',
            data: {smsCode: smsCode, fpcCode: fpcCode},
            success: function(result) {
                if (result.success) {
                    $('#resetPwd input[name = "fpcCode"]').val(result.fpcCode);
                    $('#resetPwd input[name = "smsCode"]').val($smsCode);
                    YhyUser.resetLeftTimer("forgetPwdCheck");
                    Popup.jumpToDialog("resetPwd");
                } else {
                    $('#forgetPwdCheck p.smscode-msg').html(null).html(result.msg);
                }
            },
            error: function() {
                $('#forgetPwdCheck p.smscode-msg').html(null).html("出错啦, 稍候重试");
            }
        });
    },
    doResetPassword: function() {
        var $pwd = $('#resetPwd input[name = "password"]');
        var $cfm_pwd = $('#resetPwd input[name = "cfmPwd"]');
        var pwd = $pwd.val();
        var cfm_pwd = $cfm_pwd.val();
        if (!pwd || pwd == "") {
            $pwd.next('p.input-tips').html(null).html(YhyUser.TipForPassword);
            return;
        }
        if (!cfm_pwd || cfm_pwd == "") {
            $cfm_pwd.next('p.input-tips').html(null).html(YhyUser.TipForCfmPassword);
            return;
        }
        $.form.commit({
            formId: '#resetPwd_form',
            url: '/yhypc/user/doResetPwd.jhtml',
            success: function(result) {
                if (result.success) {
                    Popup.jumpToDialog("resetPwdSuccess");
                    $.form.reset({formId: "#startForgetPwdForm"});
                    $.form.reset({formId: "#forgetPwdCheckForm"});
                    $.form.reset({formId: "#resetPwd_form"});
                    $('#passwordForget p.tips').html(null);
                    $('#forgetPwdCheck p.tips').html(null);
                    $('#resetPwd p.tips').html(null);
                } else {
                    $cfm_pwd.next('p.input-tips').html(null).html(result.msg);
                }
            },
            error: function() {
                $cfm_pwd.next('p.input-tips').html(null).html("出错啦, 稍后重试");
            }
        });
    },
    startCountLeftTime: function(dialogId) {
        // init timeout
        YhyUser.ResendTimeout = 60;
        $('#' + dialogId + ' p.resend-timeout').css('cursor', 'auto');
        YhyUser.ResendTimer = setInterval(function() {
            $('#' + dialogId + ' p.resend-timeout').html(null).html(YhyUser.ResendTimeout + "s后重新获取");
            YhyUser.ResendTimeout--;
            if (YhyUser.ResendTimeout == 0) {
                YhyUser.resetLeftTimer(dialogId);
            }
        }, 1000);
    },
    resetLeftTimer: function(dialogId) {
        clearInterval(YhyUser.ResendTimer);
        YhyUser.ResendTimeout = 60;
        $('#' + dialogId + ' p.resend-timeout').html(null).html("重新获取");
        $('#' + dialogId + ' p.resend-timeout').css('cursor', 'pointer');
    },
    goLogin: function(cb) {
        if (cb && typeof cb === "function") {
            YhyUser.LoginCallback = cb;
        }
        Popup.openLogin();
    },
    goRegister: function(cb) {
        if (cb && typeof cb === "function") {
            YhyUser.RegisterCallback = cb;
        }
        Popup.openRegister();
    },
    checkLogin: function(cb) {
        $.ajax({
            url: '/yhypc/user/checkLogin.jhtml',
            success: function(result) {cb(result);},
            error: function () {}
        });
    },
    autoLogin: function() {
        YhyUser.checkLogin(function (result) {
            if (result && !result.success) {
                var LOGIN_USER_STR = getCookie("LOGIN_USER");
                var LOGIN_USER = JSON.parse(LOGIN_USER_STR);
                var alc = getCookie("alc");
                var isAutoLogin = Boolean(getCookie("autoLogin"));
                if (isAutoLogin && isAutoLogin === true) {
                    $('#login_info_ul li.login').remove();
                    $('#login_info_ul').append("<li class='login' style='margin-left: 15px;'>自动登录中...</li>");
                    if (YhyPersonalCenter.isUserPage()) {
                        $('#user_info').empty().append(template('login_loading_tpl'));
                    }
                    $.ajax({
                        url: '/yhypc/user/autoLogin.jhtml',
                        data: {id: LOGIN_USER.id, alc: alc},
                        success: function (result) {
                            if (result.success) {
                                // refresh cookie
                                LOGIN_USER = result.LOGIN_USER;
                                setCookie("LOGIN_USER", JSON.stringify(LOGIN_USER));
                                $('#login_info_ul li.login').remove();
                                $('#login_info_ul').append(template('login_info_item', LOGIN_USER));
                                if (YhyPersonalCenter.isUserPage()) {
                                    $('#user_info').empty().append(template('logged_tpl', LOGIN_USER));
                                    // call personal center init function
                                    var pageObj = window[$('body').attr('data-page-obj')];
                                    if (pageObj && typeof pageObj === "object") {
                                        pageObj.init();
                                    }
                                }
                            } else {
                                YhyUser.deleteLoginCookie();
                                $('#login_info_ul li.login').remove();
                                $('#login_info_ul').append(template('login_out_item'));
                                if (YhyPersonalCenter.isUserPage()) {
                                    $('#user_info').empty().append(template('logout_tpl'));
                                }
                            }
                        },
                        error: function () {
                            $('#login_info_ul li.login').remove();
                            $('#login_info_ul').append(template('login_out_item'));
                        }
                    })
                }
            }
        });
    },
    deleteLoginCookie: function() {
        // clear cookie
        delCookie("LOGIN_USER");
        delCookie("autoLogin");
        delCookie("alc");
    }
};
// personal center
var YhyPersonalCenter = {
    PersonalInitFns: {"order": "YhyUserOrder.init"},
    init: function() {
        if (!this.isUserPage()) {return;}
        YhyPersonalCenter.initComp();
        YhyPersonalCenter.initEvt();
    },
    initComp: function() {},
    initEvt: function () {
        // require login
        $(document).on('click', '#user_info img.req-login, #user_info span.req-login', function(event) {
            event.stopPropagation();
            YhyUser.goLogin();
        });
    },
    isUserPage: function() {
        var $personalCenterFlag = $('input[name = "personalCenterFlag"]');
        if (!$personalCenterFlag.length > 0 || !Boolean($personalCenterFlag.val()) === true) {
            return false;
        }
        return true;
    }
};
$(function() {
    YhyUser.init();
    YhyPersonalCenter.init();
});