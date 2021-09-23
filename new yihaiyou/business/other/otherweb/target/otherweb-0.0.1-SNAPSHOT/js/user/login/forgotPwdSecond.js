/**
 * Created by vacuity on 15/10/30.
 */


/**
 * Created by vacuity on 15/10/30.
 */

var ValidateAccount = {
    sendSms: false,
    errMsg:"",

    initConfirm: function () {
        $("#sendSms").click(function () {
            ValidateAccount.sendSmsCode();
        });
    },
    sendSmsCode: function () {
        if($("#tel").val() == null || $("#tel").val() == ""){
            ValidateAccount.errMsg="手机号不能为空";
            $("#forgot-pwd-second").find(".validate-message").text("手机号不能为空");
            return;
        }
        $.get("/user/user/sendVerificationSms.jhtml", {account: $("#tel").val()}, function (result) {
            if (result == 'success') {
                ValidateAccount.sendSms=true;
                alert("发送成功");
            } else {
                alert("发送失败");
                $("#forgot-pwd-second").find(".validate-message").text("验证码发送失败");
                ValidateAccount.errMsg="验证码发送失败";
            }
        });
    }
};


var forgotPwdSecond={

    initForm: function() {
        $("#forgot-pwd-second").validate({
            rules: {
                account: {
                    required: true,
                },
                smsCode: {
                    required: true
                }
            },
            errorPlacement: function (error, element) {
                error.appendTo($("#forgot-pwd-second").find(".validate-message").text(""));
            },
            errorClass: "text-danger"
        });

        $("#submit-button").click(function () {
            var valid = $("#forgot-pwd-second").valid();
            if (valid){
                $("#forgot-pwd-second").attr("action","/user/user/forgotPwdSecondDeal.jhtml");
                $("#forgot-pwd-second").submit();
            }
        });

    },


    validateAccount: function() {
        var errMsg = $("#forgot-second-msg").val();
        if (errMsg != "null" && errMsg != ""){
            $("#forgot-pwd-second").find(".validate-message").text(errMsg);
        }
    },

}

$(function () {
    ValidateAccount.initConfirm();
    forgotPwdSecond.initForm();
    forgotPwdSecond.validateAccount();
});

jQuery.extend(jQuery.validator.messages, {
    required: "必选字段不能为空"
});