/**
 * Created by HMLY on 2017-01-18.
 */
var Popup = {
    init:function(){
        /*初始化*/
        /*微信登录与电脑登录之间的切换*/
        ///*电脑登录  切换到   微信登录*/
        //Popup.modelJump($("#weichartJump"),$("#loginWeichart"));
        ///*微信登录   切换到   电脑登录*/
        //Popup.modelJump($("#pcJump"),$("#loginComputer"));
        ///*电脑登录中的忘记密码  点击后  跳转到对应页面*/
        //Popup.modelJump($("#passwordforgetJump"),$("#passwordForget"));
        ///*电脑登录中的注册用户   点击跳转 到对应页面*/
        //Popup.modelJump($("#signinJump"),$("#realnameCheck"));
        ///*next*/
        //Popup.modelJump($("#accountSigninJump"),$("#accountSignin"));
        ///*next*/
        //Popup.modelJump($("#signinnextJump"),$("#accountCheck"));
        // close
        Popup.modalClose();
        /*密码框 输入的密码 切换显示/隐藏*/
        Popup.codeHideShow($("#eyeicon"),$("#pwd"));
        // 支付跳转事件
        Popup.goPay();
    },
    /*弹窗显示*/
    modelShow:function(obj){
        obj.click(function(){
            $("#popup_modal").show();
            $('html body').attr('style','overflow:hidden');
        });
    },
    /*弹窗隐藏*/
    modelHidden:function(obj){
        obj.click(function(){
            $("#popup_modal").hide();
            $('html body').attr('style','overflow:auto');
        });
    },
    /*阻止冒泡*/
    stopBubbling:function(obj){
        obj.click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
        });
    },
    /*弹窗之间的跳转*/
    modelJump:function(obj,targetObj){
        obj.click(function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            targetObj.siblings().hide();
            targetObj.slideDown().siblings().slideUp();
        })
    },
    // 弹窗关闭
    modalClose: function() {
        $('#popup_modal, #popup_modal, .close-btn, #popup_modal, .closeDgBtn').on('click', function (event) {
            event.stopPropagation();
            Popup.closeDialog();
        });
        $('#popup_modelBody').on('click', function(event) {
            event.stopPropagation();
        })
    },
    /*密码输入框   显示/隐藏密码*/
    codeHideShow:function(obj,target){
        var passwordval = target.val();
        var explorer = window.navigator.userAgent ;
        if (explorer.indexOf("MSIE 8.0") >= 0) {
            obj.hide();
        }else {
            obj.click(function () {
                if (target[0].type == 'password') {
                    target.attr('type', 'text');
                    target.attr('value', passwordval);
                } else {
                    target.attr('type', 'password');
                    target.attr('value', passwordval);
                }
            });
        }
    },
    openLogin: function() {
        Popup.openDialog("loginComputer");
    },
    openRegister: function() {
        Popup.openDialog("accountSignin");
    },
    openRealname: function (fn) {
        FerryUser.realname(fn);
    },
    openFerryLogin: function (fn) {
        FerryUser.ferryLogin(fn);
        Popup.openDialog("ferryLogin");
    },
    openFerryRegister: function (fn) {
        FerryUser.ferryRegister(fn);
        Popup.openDialog("ferryRegister");
    },
    openAddTourist: function (fn) {
        Popup.openDialog("addTourist");
        $("#popup_modelBody").css({ left: "35%", top: "45%" });
    },
    openDoRealname: function (fn) {
        FerryUser.doRealname(fn);
    },
    openDialog: function(dialogId) {
        if (dialogId && dialogId != "") {
            $("#popup_modal").show();
            $('html body').attr('style','overflow:hidden');
            $("#" + dialogId + " .img.vcode_img").click();
            $("#" + dialogId).show().siblings().hide();
        }
    },
    jumpToDialog: function(dialogId) {
        if (dialogId && dialogId != "") {
            var $dia = $('#' + dialogId);
            $dia.siblings().hide();
            $dia.slideDown().siblings().slideUp();
        }
    },
    closeDialog: function(dialogId) {
        if (dialogId && dialogId != "") {
            $('#' + dialogId).hide();
        } else {
            $('#popup_modal').children('div').children('div').hide();
        }
        $("#popup_modal").hide();
        $('html body').attr('style','overflow:auto');
    },
    // 支付跳转事件
    goPay: function(){
        $('#diypay').on('click', function(event) {
            event.stopPropagation();
            window.location.href = $(this).attr('data-pay-url');
        });
    }
};
$(function(){
    Popup.init();
});
