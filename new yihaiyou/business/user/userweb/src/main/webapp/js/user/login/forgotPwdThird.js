/**
 * Created by vacuity on 15/10/31.
 */

var forgotPwdThird={

    initForm: function() {
        $("#forgot-pwd-third").validate({
            rules: {
                password: {
                    required: true,
                    rangelength: [6, 16]
                },
                rePassword: {
                    required: true,
                    rangelength: [6, 16],
                    equalTo: "#password"
                },
            },
            errorPlacement: function (error, element) {
                error.appendTo($("#forgot-pwd-third").find(".validate-message").text(""));
            },
            errorClass: "text-danger"
        });

        $("#submit-button").click(function () {
            var valid = $("#forgot-pwd-third").valid();
            if (valid){
                $("#forgot-pwd-third").attr("action","/user/user/forgotPwdThirdDeal.jhtml");
                $("#forgot-pwd-third").submit();
            }
        });

    },


    validateAccount: function() {
        var errMsg = $("#forgot-third-msg").val();
        if (errMsg != "null" && errMsg != ""){
            $("#forgot-pwd-third").find(".validate-message").text(errMsg);
        }
    },

}

$(function () {
    forgotPwdThird.initForm();
    forgotPwdThird.validateAccount();
});

jQuery.extend(jQuery.validator.messages, {
    required: "必选字段不能为空",
    rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串")
});