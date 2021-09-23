/**
 * Created by dy on 2016/11/16.
 */
$(function () {
    SailboatTenant.init();
});

var SailboatTenant = {
    hasSend: false,
    init: function() {
        //SailboatTenant.initPage();
        SailboatTenant.initCom();
        SailboatTenant.initAppendice();
        SailboatTenant.initValidate();
        $('#download').modal('hide');
    },


    initAppendice: function() {
        if ($("#contractId").val()) {
            var url = "/contract/contract/getContractAppendiceList.jhtml";
            $.post(url,
                {'contract.id': $("#contractId").val()},
                function(data) {
                    if (data.success) {
                        $.each(data.appendiceList, function(i, perValue) {
                            SailboatTenant.addTr(perValue);
                        });

                        $("#contract_appendices").show();

                    } else {
                    }
                }
            );
        }
    },

    goCompanyEditInfo: function() {
        window.location.href = "/yhy/yhyMain/companyInfoEdit.jhtml";
    },

    appendTr: function(index, result) {
        var html_tr = "";
        html_tr += '<tr id="tr_'+ index +'" class="appendice_class" rows="'+ index +'">';
        html_tr += '<td width="320px">'+ result.name +'</td>';
        html_tr += '<input type="hidden" value="'+ result.name +'">' ;
        html_tr += '<input type="hidden" value="'+ result.path +'">' ;
        html_tr += '<input type="hidden" value="'+ result.type +'">' ;
        html_tr += '<td align="center">' ;
        html_tr += '<a href="'+result.path+'"  class="easyui-linkbutton">下载</a>&nbsp;&nbsp;' ;
        //html_tr += '<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="SailboatTenant.delTr('+ index +')">删除</a>' ;
        html_tr += '</td>';
        html_tr += '</tr>';
        $("#appendicesTbody").append(html_tr);

    },
    addTr: function(result) {
        var appdeniceClassList = $(".appendice_class");
        var index = appdeniceClassList.length + 1;
        SailboatTenant.appendTr(index, result);
    },
    delTr: function(index) {
        $("#tr_" + index +"").remove();
    },
    download: function() {
        var url = "/sys/sysUnit/downloadFile.jhtml";
        var imgPathURL = $("#url").val();
        var fileName = $("#fileName").val();
        var filePath = $("#filePath").val();
        var data = {'imgPathURL': imgPathURL, fileName: fileName, filePath: filePath};
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            progress: true,
            loadingText: '正在下载中....',
            success: function(data){
                if (data.success) {
                    $.messager.show({
                        msg: "下载成功",
                        type: 'success'
                    });
                    $("#downloadFormId").bootstrapValidator('resetForm', true);
                    $('#download').modal('hide');
                } else {
                    $.messager.show({
                        msg: "下载失败",
                        type: 'error'
                    });
                }
            },
            error: function(error) {
                $.messager.show({
                    msg: "下载失败，请重试",
                    type: 'error'
                });
            }
        });
    },

    openDownLoad: function(url, fileName) {
        $("#viewImg").attr("src", url);
        $('#download').modal('show');
    },


    initPage: function() {
        var url = '/yhy/yhyUser/getCompanyInfo.jhtml'
        $.post(url,
            //{'user.id': $("#userId").val()},
            function(data) {
                if (data.success) {
                    $.loadData({
                        scopeId: '.accUl',
                        data: data.data
                    });
                    $.loadData({
                        scopeId: '.company_info',
                        data: data.data
                    });

                } else {
                    $.messager.show(data.errorMsg);
                }
            }
        );

    },

    initCom: function() {
        $("#contract_appendices").hide();
        $('#changeName').click(function(){
            if ($("#userNameId").val()) {
                $("#userNameFormId").bootstrapValidator('resetForm', true);
                $("#tempUserName").val($("#userNameId").val());
                $('#reviseName').modal('show');
            }
        });

        $('#changePhoneNum').click(function(){
            if ($("#mobileId").val()) {
                $("#oldMobile").html($("#mobileId").val());
                $("#validate_mobile").bootstrapValidator('resetForm', true);
                $('#revisePhoneNum').modal('show');
            }
        });

        $('#changePassword').click(function(){
            $('#revisePassword').modal('show')
        })



    },

    getValidateMsgCode: function() {
        if (SailboatTenant.hasSend) {
            return ;
        }
        if ($("#newMobileId").val()) {
            var url = "/yhy/yhyUser/sendUpdateMobileMsg.jhtml";
            var data = {
                telephone: $("#newMobileId").val()
            };
            $.post(url, data, function(result) {
                if (result.success) {
                    var second = 60;
                    SailboatTenant.hasSend = true;
                    var timer = setInterval(function () {
                        if (second == 0) {
                            $("#sendSms").css("background-color", "#478ff1");
                            $("#sendSms").css("color", "#fff");
                            $("#sendSms").attr("onclick", "SailboatTenant.getValidateMsgCode()");
                            $("#sendSms").text("免费获取验证短信");
                            SailboatTenant.hasSend = false;
                            clearInterval(timer);
                        } else {
                            $("#sendSms").text(second-- + "秒");
                            $("#sendSms").css("background-color", "#B4B5B6");
                            $("#sendSms").css("color", "#3DF73A");
                            $("#sendSms").attr("onclick", "");
                        }
                    }, 1000);
                } else {

                }
            });


        } else {
            $('#validate_mobile')
                .data('bootstrapValidator')
                .updateStatus('mobile', 'notEmpty')
                .validateField('mobile');
        }

    },

    initValidate: function() {
        $("#editPasswordFormId").bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                oldPassword: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        //remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        //    url: "/sys/sysUser/validateYhyUserPassword.jhtml?neUserId="+ $('#userId').val(),//验证地址
                        //    message: '密码输入错误，请重新输入',//提示消息
                        //    delay :  2000, //每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        //    type: 'POST', //请求方式
                        //    //自定义提交数据，默认值提交当前input value
                        //    data: function(validator) {
                        //        return {'accountUser.password':$(':input[name="oldPassword"]').val()};
                        //    }
                        //},
                        regexp: {
                            regexp: /^[a-zA-Z0-9]{6,16}$/,
                            message: '只能有数字、字母和常用特殊字符。'
                        }
                    }
                },
                newPassword: {
                    message: '密码验证失败',
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 16,
                            message: '密码长度必须为6位数'
                        },
                        identical: {
                            field: 'reNewPassword',
                            message: '两次输入密码不一致'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9]{6,16}$/,
                            message: '只能有数字、字母和常用特殊字符。'
                        }
                    }
                },
                reNewPassword: {
                    message: '确认密码验证失败',
                    validators: {
                        notEmpty: {
                            message: '确认密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 16,
                            message: '确认密码长度必须为6位数'
                        },
                        identical: {
                            field: 'newPassword',
                            message: '两次输入密码不一致'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9]{6,16}$/,
                            message: '只能有数字、字母和常用特殊字符。'
                        }
                    }
                }
            },
            submitHandler: function (validator, form, submitButton) {
                SailboatTenant.editUserpassword();
            }

        });


        $("#validate_mobile").bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                mobile: {
                    validators: {
                        notEmpty: {
                            message: '手机号码不能为空'
                        },
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            url: "/sys/sysUser/validateYhyUser.jhtml?neUserId="+ $('#userId').val(),//验证地址
                            message: '该手机号已被使用',//提示消息
                            delay :  2000, //每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                            type: 'POST', //请求方式
                            //自定义提交数据，默认值提交当前input value
                            data: function(validator) {
                               return {'accountUser.mobile':$(':input[name="mobile"]').val()};
                            }
                        },
                        regexp: {
                            regexp: /^1[3|4|5|6|7|8|9]\d{9,9}$/,
                            message: '请输入正确的手机号码'
                        }
                    }
                },
                validCode: {
                    message: '验证码验证失败',
                    validators: {
                        notEmpty: {
                            message: '验证码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 6,
                            message: '验证码长度必须为6位数'
                        },
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            url: "/yhy/yhyUser/checkMsgCode.jhtml",//验证地址
                            message: '该验证码不正确',//提示消息
                            delay :  2000, //每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                            type: 'POST', //请求方式
                            //自定义提交数据，默认值提交当前input value
                            data: function(validator) {
                                return {'msgCode':$(':input[name="validCode"]').val()};
                            }
                        },
                        regexp: {
                            regexp: /^\d+$/,
                            message: '验证码只能是为数字'
                        }
                    }
                }
            },
            submitHandler: function (validator, form, submitButton) {
                SailboatTenant.editMobile();
            }

        });


        $("#userNameFormId").bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                userName: {
                    validators: {
                        notEmpty: {
                            message: '姓名不能为空'
                        },
                        stringLength: {
                            min: 1,
                            max: 12,
                            message: '字符长度必须大于0小于12位'
                        }
                    }
                }
            },
            submitHandler: function (validator, form, submitButton) {
                SailboatTenant.editUserName();
            }

        });

        $("#downloadFormId").bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                filePath: {
                    validators: {
                        notEmpty: {
                            message: '路径不能为空！'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z]:\\[a-zA-Z_0-9\\]*/,
                            message: '路径格式不正确'
                        }
                    }
                }
            },
            submitHandler: function (validator, form, submitButton) {
                SailboatTenant.download();
            }

        });
    },
    editUserName: function() {
        var url = "/sys/sysUser/saveUser.jhtml";
        var data = {
            'user.id': $('#userId').val(),
            'user.userName': $("#tempUserName").val()
        }
        $.post(url, data, function(data) {
            if (data.success) {
                //$.form.message(data);
                $("#userNameFormId").bootstrapValidator('resetForm', true);
                $('#reviseName').modal('hide');
                SailboatTenant.initPage();
            }
        });
    },

    editMobile: function() {
        var url = "/sys/sysUser/saveUser.jhtml";
        var data = {
            'user.id': $('#userId').val(),
            'user.mobile': $("#newMobileId").val(),
            validCode: $(":input[name='validCode']").val()
        }
        $.post(url, data, function(data) {
            if (data.success) {
                //$.form.message(data);
                $("#validate_mobile").bootstrapValidator('resetForm', true);
                $('#revisePhoneNum').modal('hide');
                SailboatTenant.initPage();
            }
        });
    },

    editUserpassword: function() {
        var url = "/sys/sysUser/edtiUserPassword.jhtml";
        var data = {
            'user.id': $('#userId').val(),
            'user.password': $(":input[name='newPassword']").val(),
            oldPassword: $(":input[name='oldPassword']").val(),
            validCode: $(":input[name='validCode']").val()
        }
        $.post(url, data, function(data) {
            if (data.success) {
                //$.form.message(data);
                $("#editPasswordFormId").bootstrapValidator('resetForm', true);
                $('#revisePassword').modal('hide');
                SailboatTenant.initPage();
            } else {
                //errorMsg
                $.messager.show({
                    msg: data.errorMsg,
                    type: 'error'
                });
                $("#editPasswordFormId").bootstrapValidator('resetForm', true);
            }
        });
    }
};
