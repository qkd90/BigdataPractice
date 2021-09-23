var ValidateAccount = {
    initConfirm: function () {
        $("#sendSms").click(function () {
            ValidateAccount.sendSms();
        });
        $("#submit-button").click(function () {
            ValidateAccount.validateSms();
        })
    },
    sendSms: function () {
        $.get("/user/register/sendVerificationSms.jhtml", {account: $("#tel").val()}, function (result) {
            if (result == 'success') {
                alert("发送成功");
            }
        });
    },
    validateSms: function () {
        $.get("/user/register/validateSmsCode.jhtml", {smsCode: $("#smsCode").val()}, function (result) {
            if (result == 'valid') {
                alert("验证成功");
                window.location = "/user/register/finish.jhtml";
            } else {
                alert(result);
            }
        });
    }
};
ValidateAccount.initConfirm();