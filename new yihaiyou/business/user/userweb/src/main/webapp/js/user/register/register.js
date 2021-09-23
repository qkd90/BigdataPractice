var Register = {
    accountValid: false,
    captchaValid: false,
    form: $("#register-form"),
    initRegister: function() {
        var formValid = false;
        this.form.validate({
            rules: {
                account: {
                    required: true,
                    mobile: true
                },
                password: {
                    required: true,
                    rangelength: [6, 16]
                },
                rePassword: {
                    required: true,
                    rangelength: [6, 16],
                    equalTo: "#password"
                },
                captcha: {
                    required: true
                }
            },
            errorPlacement: function (error, element) {
                error.appendTo($(element).parents(".form-group").find(".validate-message").text(""));
            },
            errorClass: "text-danger"
        });
        $("#tel").focusout(function () {
            var account = $(this).val();
            if (account.trim().length < 1) {
                return;
            }
            if (!$(this).valid()) {
             return;
            }
            $.get("/user/register/validatePhone.jhtml", {account: account}, function (result) {
                if (result == "valid") {
                    $("#tel-message").text("该手机号没有被使用过").removeClass("text-danger").addClass("text-success");
                    Register.accountValid = true;
                    return;
                }
                if (result.length>50) {
                    console.log(result);
                    result = "服务器异常";
                }
                $("#tel-message").text(result).addClass("text-danger").removeClass("text-success");
                Register.accountValid = false;
            })
        });
        $("#captcha").focusout(function () {
            var captcha = $(this).val();
            if (captcha.trim().length < 1) {
                return;
            }
            if (!$(this).valid()) {
                return;
            }
            $.get("/user/register/validateCaptcha.jhtml", {captcha: captcha}, function (result) {
                if (result == "valid") {
                    $("#captcha-message").text("验证成功").removeClass("text-danger").addClass("text-success");
                    Register.captchaValid = true;
                    return;
                }
                if (result.length>50) {
                    console.log(result);
                    result = "服务器异常";
                }
                $("#captcha-message").text(result).addClass("text-danger").removeClass("text-success");
                Register.captchaValid = false;
            })
        });

        $("#submit-button").click(function () {
            var valid = $("#register-form").valid();
            if (valid && Register.accountValid && Register.captchaValid) {
                Register.form.submit();
            }
        });

    }

};
$(function () {
    Register.initRegister();
});
jQuery.validator.addMethod("phone", function (value, element) {
    var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
    return this.optional(element) || (tel.test(value));
}, "电话号码格式错误");
jQuery.validator.addMethod("mobile", function (value, element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号码格式错误");
jQuery.extend(jQuery.validator.messages, {
    required: "必选字段",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
    minlength: jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
    rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
    range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: jQuery.validator.format("请输入一个最大为{0} 的值"),
    min: jQuery.validator.format("请输入一个最小为{0} 的值")
});