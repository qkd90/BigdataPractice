/**
 * Created by huangpeijie on 2017-02-10,0010.
 */
var FerryUser = {
    user: {},

    realname: function (fn) {
        var realname = $("#realnameCheck");
        var ferryRegister = $("#ferryRegister");

        $.post("/yhypc/user/userInfo.jhtml", function (data) {
            if (data.success) {
                if (data.user.isReal) {
                    fn();
                } else {
                    realname.children(".realName").val(data.user.userName);
                    realname.children(".realIdNumber").val(data.user.idNumber);
                    realname.children(".realTelephone").val(data.user.telephone);
                    realname.children(".realEmail").val(data.user.email);
                    realname.find(".submitButton").off("click").on("click", function () {
                        realnameFun(true, fn);
                    });
                    //realnameFun(false, fn);
                    Popup.openDialog("realnameCheck");
                }
            }
        });

        function realnameFun(isAlert, callback) {
            var trueName = realname.children(".realName").val();
            var idnumber = realname.children(".realIdNumber").val();
            var mobile = realname.children(".realTelephone").val();
            var email = realname.children(".realEmail").val();
            if (trueName == null || trueName == "") {
                if (isAlert) {
                    alert("请输入姓名");
                }
                return;
            }
            if (!trueName.match(Reg.nameReg) || trueName.length > 10) {
                if (isAlert) {
                    alert("姓名格式错误");
                }
                return;
            }
            if (idnumber == null || idnumber == "") {
                if (isAlert) {
                    alert("请输入身份证号");
                }
                return;
            }
            var msg = checkIdcard(idnumber);
            if (msg != "验证通过!") {
                if (isAlert) {
                    alert("身份证号格式错误");
                }
                return;
            }
            if (mobile == null || mobile == "") {
                if (isAlert) {
                    alert("请输入手机号");
                }
                return;
            }
            if (!mobile.match(Reg.telephoneReg)) {
                if (isAlert) {
                    alert("手机号格式错误");
                }
                return;
            }
            if (email == null || email == "") {
                if (isAlert) {
                    alert("请输入邮箱");
                }
                return;
            }
            if (!email.match(Reg.emailReg)) {
                if (isAlert) {
                    alert("邮箱格式错误");
                }
                return;
            }
            FerryUser.user = {
                trueName: trueName,
                idnumber: idnumber,
                mobile: mobile,
                idTypeName: "ID_CARD",
                email: email
            };
            $.post("/yhypc/ferry/queryReal.jhtml", {
                json: JSON.stringify(FerryUser.user)
            }, function (data) {
                if (data.success) {
                    Popup.closeDialog("realnameCheck");
                    FerryUser.user.name = data.name;
                    FerryUser.setFerryName(FerryUser.user.name);
                    if (data.isReal) {
                        Popup.openFerryLogin(callback);
                    } else {

                    }
                } else {
                    alert(data.errMsg);
                }
            });
        }
    },

    ferryLogin: function (fn) {
        var ferryLogin = $("#ferryLogin");
        var doRealname = $("#doRealname");
        ferryLogin.children(".ferryName").val(FerryUser.getFerryName());
        ferryLogin.find(".submitButton").off("click").on("click", function () {
            ferryLoginFun(fn);
        });

        function ferryLoginFun(callback) {
            var password = ferryLogin.children(".ferryPassword").val();
            if (password == null) {
                alert("请输入密码");
                return;
            }
            $.post("/yhypc/ferry/ferryLogin.jhtml", {
                password: password
            }, function (data) {
                if (data.success) {
                    FerryUser.delFerryName();
                    Popup.closeDialog("ferryLogin");
                    callback();
                } else {
                    alert(data.errMsg);
                }
            });
        }
    },

    ferryRegister: function (fn) {
        var ferryRegister = $("#ferryRegister");
        var doRealname = $("#doRealname");

        ferryRegister.find(".submitButton").off("click").on("click", function () {
            ferryRegisterFun(fn);
        });

        function ferryRegisterFun(callback) {
            var password = ferryRegister.children(".ferryPassword").val();
            var checkPassword = ferryRegister.children(".ferryCheckPassword").val();
            var bankNo = ferryRegister.children(".ferryBankNo").val();
            if (password == null) {
                alert("请输入密码");
                return;
            }
            if (password != checkPassword) {
                alert("两次输入的密码不一样");
                return;
            }
            if (bankNo == null || bankNo == "") {
                alert("请输入银行卡号");
                return;
            }
            if (!bankNo.match(Reg.bankNoReg)) {
                alert("银行卡号格式错误");
                return;
            }
            FerryUser.user.password = password;
            FerryUser.user.bankNo = bankNo;
            $.post("/yhypc/ferry/ferryRegister.jhtml", {
                json: JSON.stringify(FerryUser.user)
            }, function (data) {
                if (data.success) {
                    Popup.closeDialog("ferryRegister");
                    if (data.isReal) {
                        callback();
                    } else {
                        doRealname.children(".realName").val(FerryUser.user.trueName);
                        doRealname.children(".realIdNumber").val(FerryUser.user.idnumber);
                        doRealname.children(".realTelephone").val(FerryUser.user.mobile);
                        doRealname.children(".realBankNo").val(FerryUser.user.bankNo);
                        Popup.openDoRealname();
                    }
                } else {
                    alert(data.errMsg);
                }
            });
        }
    },

    doRealname: function (fn) {
        var doRealname = $("#doRealname");

        $.post("/yhypc/user/userInfo.jhtml", function (data) {
            if (data.success) {
                if (data.user.isReal) {
                    fn();
                } else {
                    doRealname.children(".realName").val(data.user.userName);
                    doRealname.children(".realIdNumber").val(data.user.idNumber);
                    doRealname.children(".realTelephone").val(data.user.telephone);
                    doRealname.children(".realBankNo").val(data.user.bankNo);
                    doRealname.find(".submitButton").off("click").on("click", function () {
                        doRealnameFun(fn);
                    });
                    Popup.openDialog("doRealname");
                }
            }
        });

        function doRealnameFun(callback) {
            var trueName = doRealname.children(".realName").val();
            var idnumber = doRealname.children(".realIdNumber").val();
            var mobile = doRealname.children(".realTelephone").val();
            var bankNo = doRealname.children(".realBankNo").val();
            if (trueName == null || trueName == "") {
                alert("请输入姓名");
                return;
            }
            if (!trueName.match(Reg.nameReg) || trueName.length > 10) {
                alert("姓名格式错误");
                return;
            }
            if (idnumber == null || idnumber == "") {
                alert("请输入身份证号");
                return;
            }
            if (!idnumber.match(Reg.idCardReg)) {
                alert("身份证号格式错误");
                return;
            }
            if (mobile == null || mobile == "") {
                alert("请输入手机号");
                return;
            }
            if (!mobile.match(Reg.telephoneReg)) {
                alert("手机号格式错误");
                return;
            }
            if (bankNo == null || bankNo == "") {
                alert("请输入银行卡号");
                return;
            }
            if (!bankNo.match(Reg.bankNoReg)) {
                alert("银行卡号格式错误");
                return;
            }
            FerryUser.user = {
                trueName: trueName,
                idnumber: idnumber,
                mobile: mobile,
                idTypeName: "ID_CARD",
                bankNo: bankNo
            };
            $.post("/yhypc/ferry/doRealname.jhtml", {
                json: JSON.stringify(FerryUser.user)
            }, function (data) {
                if (data.success) {
                    Popup.closeDialog("doRealname");
                    if (data.success) {
                        callback();
                    } else {
                        alert(data.errMsg);
                    }
                } else {
                    alert(data.errMsg);
                }
            });
        }
    },

    setFerryName: function (name) {
        setUnCodedCookie("ferryName", name);
    },

    getFerryName: function () {
        return getUnCodedCookie("ferryName");
    },

    delFerryName: function () {
        delCookie("ferryName");
    }
};