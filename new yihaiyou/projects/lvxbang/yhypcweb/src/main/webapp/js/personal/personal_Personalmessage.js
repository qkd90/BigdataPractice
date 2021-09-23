var MyInfo= {
    avatar_uploader: null,
    init: function() {
        YhyUser.checkLogin(function(result) {
            if (result && result.success) {
                MyInfo.initComp();
                MyInfo.initEvt();
            } else {
                YhyUser.goLogin();
            }
        });
    },
    initComp: function() {
        MyInfo.initUploader();
        // center box
        var left = (window.screen.width - $('.setpswordBox').width())/2;
        var top = (window.screen.height - $('.setpswordBox').height())/2.5;
        $('.setpswordBox').css({'left':left,'top':top});
        // set gender
        var gender = $('#myinfo_form').find('input[name = "member.gender"]').val();
        if (gender && gender != null) {
            $('div.sex span[data-gender-value=' + gender + ']').addClass('checksex');
        }
        scroll(0, 480);
    },
    initEvt: function() {
        $('#savefaceBtn').on('click', function(event) {
            event.stopPropagation();
            if ($('div.setface div.facebox').find('img').length != 1) {
                $('#up_msg').html(null).html("请选择一张图片!");
                MyInfo.clearMsg("#up_msg");
                return;
            }
            $('#up_msg').html(null).html("保存中, 请稍候...");
            MyInfo.avatar_uploader.upload();
        });
        $('div.sex span').on('click', function(event) {
            event.stopPropagation();
            $(this).addClass('checksex').siblings().removeClass("checksex");
            $('#myinfo_form').find('input[name = "member.gender"]').val($(this).attr('data-gender-value'));
        });
        $('#saveMyInfoBtn').on('click', function(event) {
            event.stopPropagation();
            MyInfo.doSaveMyInfo();
        });
        $('#revise').on('click',function(){
            $('.shadow').show();
            $('.setpswordBox').show();
            $('body').css({'overflow':'hidden'});
        });
        $('#psword_close').on('click',function(){
            MyInfo.closeChangePwd();
        });
        // change pwd btn
        $('#changPwdBtn').on('click', function(event) {
            event.stopPropagation();
            MyInfo.doChangPwd();
        });
    },
    initUploader: function() {
        MyInfo.avatar_uploader = WebUploader.create({
            server: '/yhypc/upload/imageUpload.jhtml',
            swf: '/lib/webuploader/Uploader.swf',
            pick: {id: '#avatarUpBtn', innerHTML: '选择图片', multiple: false},
            auto: false,
            method: 'post',
            formData: {section: "avatar"},
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif,image/bmp'
            },
            thumb: {width: 200, height: 200, crop: true},
            compress: {width: 200, height: 200, crop: true},
            fileSingleSizeLimit: 2097152
        });
        MyInfo.avatar_uploader.on('beforeFileQueued', function(file) { MyInfo.avatar_uploader.reset();return true;});
        MyInfo.avatar_uploader.on('fileQueued', function(file) {
            var $div = $('div.setface div.facebox');
            MyInfo.avatar_uploader.makeThumb(file, function(error, ret) {
                $div.addClass("none-bg");
                if (error) {
                    $div.html("预览错误");
                    MyInfo.clearMsg("#up_msg");
                } else {
                    $div.empty().append("<img src='" + ret + "'>");
                }
            });
        });
        MyInfo.avatar_uploader.on('uploadSuccess', function(file, response) {
            $.ajax({
                url: '/yhypc/personal/saveAvatar.jhtml',
                data: {path: response.path},
                success: function(result) {
                    if (result.success) {
                        MyInfo.avatar_uploader.reset();
                        $('#up_msg').html(null).html("保存成功!");
                        MyInfo.clearMsg("#up_msg");
                        setTimeout(function () {
                            window.location.reload(true);
                        }, 3000);
                    } else {
                        file.setStatus('queued');
                        $('#up_msg').html(null).html("保存失败, " + result.msg + ", 请重试!");
                        MyInfo.clearMsg("#up_msg");
                    }
                },
                error: function() {
                    file.setStatus('queued');
                    $('#up_msg').html(null).html("保存出错! 请重试!");
                    MyInfo.clearMsg("#up_msg");
                }
            });
        });
        MyInfo.avatar_uploader.on('uploadError', function(code) {$('#up_msg').html(null).html("上传出错!(" + code + ")");});
        MyInfo.avatar_uploader.on('error', function (code) {$('#up_msg').html(null).html("图片格式错误或太大!");MyInfo.clearMsg("#up_msg");});
    },
    clearMsg: function(eId) {setTimeout(function() {$(eId).html(null);}, 3000);},
    doSaveMyInfo: function() {
        var nickName = $('#myinfo_form').find('input[name = "member.nickName"]').val();
        var email = $('#myinfo_form').find('input[name = "member.email"]').val();
        var telephone = $('#myinfo_form').find('input[name = "member.telephone"]').val();
        if (!nickName.match(Reg.nickNameReg)) {
            $('#save_msg').html(null).html("昵称格式不正确!");
            return;
        }
        //if (!email.match(Reg.emailReg)) {
        //    $('#save_msg').html(null).html("邮箱格式不正确!");
        //    return;
        //}
        if (!telephone.match(Reg.telephoneReg)) {
            $('#save_msg').html(null).html("手机号格式不正确!");
            return;
        }
        $('#save_msg').html(null);
        $.form.commit({
            formId: '#myinfo_form',
            url: '/yhypc/personal/saveMyInfo.jhtml',
            success: function(result) {
                if (result.success) {
                    $('#save_msg').html(null).html("个人信息更新成功!");
                } else {
                    $('#save_msg').html(null).html("保存错误, " + result.msg);
                }
                setTimeout(function() {
                    $('#save_msg').html(null)
                }, 2000);
            },
            error: function() {
                $('#save_msg').html(null).html("保存错误, 请重试!");
                setTimeout(function() {
                    $('#save_msg').html(null)
                }, 2000);
            }
        })
    },
    doChangPwd: function() {
        var $msgSpan = $('div.setpswordBox span.msg');
        var oldPwd = $('#changeOldPwd').val();
        var newPwd = $('#changeNewPwd').val();
        var cfmPwd = $('#changeCfmPwd').val();
        if (!oldPwd || oldPwd == "") {
            $msgSpan.html(null).html("旧密码不可为空!");
            return;
        }
        if (!newPwd || newPwd == "") {
            $msgSpan.html(null).html("新密码不可为空!");
            return;
        }
        if (!cfmPwd || cfmPwd == "") {
            $msgSpan.html(null).html("确认密码不可为空!");
            return;
        }
        if (cfmPwd != newPwd) {
            $msgSpan.html(null).html("确认密码与新密码不一致!");
            return;
        }
        if (newPwd == oldPwd) {
            $msgSpan.html(null).html("新密码不可与旧密码一致!");
            return;
        }
        $('#change_pwd_form').find('input[name = "oldPwd"]').val(hex_md5(oldPwd));
        $('#change_pwd_form').find('input[name = "newPwd"]').val(hex_md5(newPwd));
        $('#change_pwd_form').find('input[name = "cfmPwd"]').val(hex_md5(cfmPwd));
        $msgSpan.html(null);
        $.form.commit({
            formId: '#change_pwd_form',
            url: '/yhypc/user/doChangPwd.jhtml',
            success: function(result) {
                if (result.success) {
                    MyInfo.closeChangePwd();
                    Popup.openDialog("changePwdSuccess");
                } else {
                    $msgSpan.html(null).html("修改失败, " + result.msg);
                }
            },
            error: function() {
                $msgSpan.html(null).html("修改失败,请重试!");
            }
        });
    },
    closeChangePwd: function() {
        $('.shadow').hide();
        $('.setpswordBox').hide();
        $('body').css({'overflow':'auto'});
    }
};
$(function() {
    MyInfo.init();
});