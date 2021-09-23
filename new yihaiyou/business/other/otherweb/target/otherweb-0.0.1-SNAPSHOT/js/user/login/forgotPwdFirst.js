/**
 * Created by vacuity on 15/10/30.
 */


var forgotPwdFirst = {

    captchaValid: false,

    initForm: function () {
        $("#forgot-pwd-first").validate({
            rules: {
                account: {
                    required: true,
                },
                captchaCode: {
                    required: true
                }
            },
            errorPlacement: function (error, element) {
                error.appendTo($("#forgot-pwd-first").find(".validate-message").text(""));
            },
            errorClass: "text-danger"
        });

        $("#captchaCode").keyup(function () {
            var captchaCode = $(this).val();
            if (captchaCode.trim().length < 4) {
                return;
            }
            if (!$(this).valid()) {
                return;
            }
            $.get("/user/register/validateCaptcha.jhtml", {captcha: captchaCode}, function (result) {
                if (result == "valid") {
                    $("#captcha-message").text("验证成功").removeClass("text-danger").addClass("text-success");
                    $("#forgot-pwd-first").find(".validate-message").text("");
                    forgotPwdFirst.captchaValid = true;
                    return;
                }
                $("#captcha-message").text(result).addClass("text-danger").removeClass("text-success");
                forgotPwdFirst.captchaValid = false;
                $("#forgot-pwd-first").find(".validate-message").text("验证码错误");
            })
        });


        $("#submit-button").click(function () {
            var valid = $("#forgot-pwd-first").valid();
            if (valid && forgotPwdFirst.captchaValid) {
                $("#forgot-pwd-first").attr("action", "/user/user/forgotPwdFirstDeal.jhtml");
                $("#forgot-pwd-first").submit();
            }
        });

    },

    validateAccount: function () {
        var errMsg = $("#forgot-first-msg").val();
        if (errMsg != "null" && errMsg != "") {
            $("#forgot-pwd-first").find(".validate-message").text(errMsg);
        }
    },

    imgClick: function () {
        $("#captchaPic").attr("src", "/images/checkNum.jsp?" + new Date().getTime());
    }

};

$(function () {
    forgotPwdFirst.initForm();
    forgotPwdFirst.validateAccount();
});

jQuery.extend(jQuery.validator.messages, {
    required: "必选字段不能为空"
});