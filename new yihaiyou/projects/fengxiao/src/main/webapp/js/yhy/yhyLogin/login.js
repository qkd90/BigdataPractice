/**
 * Created by zzl on 2016/11/8.
 */
$(function () {
    YhyLogin.initLoginForm();
    YhyLogin.initEvt();
    YhyLogin.windowView();
});
var YhyLogin = {
    initEvt: function() {
        $('#yhy_validateImg').bind('click', function() {
            YhyLogin.getNewValidateCode();
        });
    },
    initLoginForm: function () {
        $("#yhyLoginForm").bootstrapValidator({
            live: "enabled",
            message: "请正确输入!",
            submitButtons: '.yhy-login-btn',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                yhyAccount: {
                    message: "账号不正确",
                    validators: {
                        notEmpty: {
                            message: "请输入账号!"
                        }
                    }
                },
                password: {
                    message: "请输入密码",
                    validators: {
                        notEmpty: {
                            message: "请输入密码!"
                        }
                    }
                },
                yhyValidateCode: {
                    message: "请输入验证码",
                    threshold: 4,
                    trigger: 'focus blur keydown keyup',
                    validators: {
                        notEmpty: {
                            message: "请输入验证码!"
                        },
                        remote: {
                            message: "验证码不正确!",
                            url: '/yhy/yhyLogin/checkValidateCode.jhtml'
                        }
                    }
                }
            },
            submitHandler: YhyLogin.doLoginForm
        });
    },
    doLoginForm: function (validator, form, submitButton) {
        submitButton.button('loading');
        if (validator.isValid()) {
            $("#yhy_md5_Pwd").val(hex_md5($("#yhy_Pwd").val()));
            $.form.commit({
                url: '/yhy/yhyLogin/doLogin.jhtml',
                formId: '#yhyLoginForm',
                success: function(result) {
                    if (result.success) {
                        window.location.href = result.url;
                    } else {
                        YhyLogin.getNewValidateCode();
                        $.messager.show({
                            msg: result.msg,
                            type: "error"
                        });
                    }
                    $(submitButton).button('reset');
                },
                error: function() {
                    $.messager.show({
                        msg: "登录错误! 稍候重试!",
                        type: "error"
                    });
                    YhyLogin.getNewValidateCode();
                    submitButton.button('reset');
                }
            });
        }
    },
    getNewValidateCode: function() {
        var stamp = new Date().getTime();
        $('#yhy_validateImg').attr('src', '/images/yhy/checkNum.jsp?' + stamp);
        // 重新校验验证码
        $('#yhy_validateCode').val(null);
        $('#yhyLoginForm')
            .data('bootstrapValidator')
            .updateStatus('yhyValidateCode', 'NOT_VALIDATED')
            .validateField('yhyValidateCode');
    },
    windowView: function() {
        var Vwindow = $(window).height();
        var topR = (Vwindow - $('.header').height()  - $('.loginBox').height() - $('.footerBox').height())/2 - 15;
        var topL = (Vwindow - $('.header').height()  - $('.bodyLbox img').height() - $('.footerBox').height())/2;
        $('.mainBox').css({'height':Vwindow});
        $('.bodyBox').css({'height':Vwindow - $('.header').height() - $('.footerBox').height()});
        $('.loginBox').css({'marginTop':topR});
        $('.bodyLbox img').css({'marginTop':topL});
    }
};