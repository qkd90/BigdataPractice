/**
 * Created by vacuity on 15/10/30.
 */

var login = {
    init: function() {
        $(".login-form").each(function () {
            var table = $(this);
            table.find("#loginForm").validate({
                rules: {
                    account: {
                        required: true
                    },
                    password: {
                        required: true
                    }
                },
                errorPlacement: function (error, element) {
                    error.appendTo($(element).parents(".login-form").find(".validate-message").text(""));
                },
                errorClass: "text-danger"
            });
            table.find(".login-bt").click(function () {
                var validateResult = table.find("#loginForm").validate();
                if (true) {
                    $.post("/user/user/doLogin.jhtml", {
                        account: table.find("#account").val(),
                        password: table.find("#password").val()
                    }, function (result) {
                        window.location.reload();
                    });
                }

            });
        });
    },
    initLogin: function () {

        $("#loginForm").validate({
            rules: {
                account: {
                    required: true
                },
                password: {
                    required: true
                }
            },
            errorPlacement: function (error, element) {
                error.appendTo($(element).parents(".login-form").find(".validate-message").text(""));
            },
            errorClass: "text-danger"
        });

        //$("#loginForm").attr("action", "/user/user/doLogin.jhtml");
        //$("#loginForm").submit();
    },

    validateAccount: function () {
        var errMsg = $("#login-fail-msg").val();
        if (errMsg != "null" && errMsg != "") {
            $(".login-form").find(".validate-message").text(errMsg);
        }

    }
};

$(function () {
    login.init();
    login.validateAccount();
});

jQuery.extend(jQuery.validator.messages, {
    required: "必选字段不能为空"
});


