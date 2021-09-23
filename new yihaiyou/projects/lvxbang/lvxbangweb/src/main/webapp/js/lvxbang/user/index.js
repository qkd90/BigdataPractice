/**
 * Created by vacuity on 15/12/16.
 */

var pager;
var User = {

    previewHead: function () {
        if ($("#path").val() == null || $("#path").val() == "") {
            return;
        }
        preImg("path", "head-pic");
        preImg("path", "change_avatar");
        User.uploadHead();
    },

    uploadHead: function () {
        if($('#head-pic').attr("src") == $('#head-pic2').attr("src")){
            promptWarn("请上传新头像");
            return;
        }
        $("#avatar_upload_tip").show();
        $('#headForm').ajaxSubmit({
            success: function (result) {
                if (result.success) {
                    promptMessage("更换头像成功");
                    $("#head-pic").attr("src", "http://7u2inn.com2.z0.glb.qiniucdn.com/" + result.path);
                    $('#head-pic2').attr("src", "http://7u2inn.com2.z0.glb.qiniucdn.com/" + result.path);
                    $("#avatar_upload_tip").hide();
                } else {
                    promptMessage("上传图片失败");
                }
            }
        });
    },

    updateInfo: function () {
        var userName = $("#name").val();
        if (userName == "") {
            promptMessage("姓名不能为空");
            return;
        }
        var nickName = $("#nickName").val();
        if (nickName == "") {
            promptMessage("昵称不能为空");
            return;
        }
        var gender = "";
        if ($("#gender-male").hasClass("checked")) {
            gender = "male";
        } else if ($("#gender-female").hasClass("checked")) {
            gender = "female";
        } else {
            gender = "secret";
        }
        var birthday = $("#birthday").val();
        var telephone = $("#tel").val();

        var mobile = /^((1[3,5,8,7,4]{1})\d{9})$/;
        if (!telephone.match(mobile)) {
            //
            promptMessage("手机号格式不正确，请重新输入");
            return;
        }
        var email = $("#email").val();

        $.post("/lvxbang/user/updateUserInfo.jhtml",
            {
                userName: userName,
                nickName: nickName,
                gender: gender,
                telephone: telephone,
                email: email,
                birthday: birthday
            }, function (result) {
                if (result.success) {
                    $('#userMessage').attr("value", nickName);
                    $('#userMessage').html(nickName);
                    promptMessage("保存成功");
                    location.reload();
                } else {
                    promptMessage(result.errMsg);
                }
            },"json");
    },

    initInfo: function () {
        var gender = $("#gender").val();
        switch (gender) {
            case "male":
                $("#gender-male").addClass("checked");
                break;
            case "female":
                $("#gender-female").addClass("checked");
                break;
            default:
                $("#gender-secret").addClass("checked");
                break;
        }
    },

    initPanel: function () {
        $(".personal").removeClass("checked");
        $("#info-panel").addClass("checked");
    },
    initClick: function() {
        $('.m_left a').click(function(){
            $(this).siblings('a').removeClass('checked');
            $(this).addClass('checked');
           var cla = $(this).attr("div_class");
            $("."+cla).siblings('.m_right').hide();
            $("."+cla).show();
        });
    },
    changePassword: function(){
        var nowPassword = $('#now_password').val();
        var newPassword = $('#new_password').val();
        var againPassword = $('#again_password').val();
        if(isNull(nowPassword)){
            promptWarn("当前密码不能为空", 1500);
            return;
        }
        if(isNull(newPassword)){
            promptWarn("新设密码不能为空", 1500);
            return;
        }
        if(isNull(againPassword)){
            promptWarn("重复密码不能为空", 1500);
            return;
        }
        if($.trim(newPassword).length < 6){
            promptWarn("密码至少包含6个字符", 1500);
            return;
        }
        if($.trim(newPassword) != $.trim(againPassword)){
            promptWarn("重复密码与新密码不一致", 1500);
            return;
        }

        $.ajax({
            url:"/lvxbang/login/changePassword.jhtml",
            type:"post",
            dataType:"json",
            data:{
                'password':nowPassword,
                'newPassword':newPassword
            },
            success:function(data){
                promptMessage(data.msg, 1000);
                if(data.success == "false") {
                    $('#nowPassword').val('').focus();
                }
            },
            error:function(data){
                //console.log(data);
            }
        });
    }
};

$(function () {
    User.initPanel();
    User.initInfo();
    User.initClick();
});

function preImg(sourceId, targetId) {
    var url = getFileUrl(sourceId);
    var imgPre = document.getElementById(targetId);
    imgPre.src = url;
}

function getFileUrl(sourceId) {
    var url;
    if (navigator.userAgent.indexOf("MSIE") >= 1) { // IE
        url = document.getElementById(sourceId).value;
    } else if (navigator.userAgent.indexOf("Firefox") > 0) { // Firefox
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    } else if (navigator.userAgent.indexOf("Chrome") > 0) { // Chrome
        url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
    }
    return url;
}
