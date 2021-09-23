// 校验两次输入密码是否一致
$.extend($.fn.validatebox.defaults.rules, {
    equals : {
        validator: function(value, param){
            return value == $(param[0]).val();
        },
        message: '两次密码输入不一致'
    }
});
var merchantHome = {
    init : function() {
        // 设置内容区域高度，简单计算，不一定适应各浏览器
        var offsetHeight = document.body.offsetHeight;
        $('#frmContent').css('height', offsetHeight-180);

        // 头部导航栏点击事件
        $('.mpm-head #mainmenus a').click(function() {
            $(this).siblings().removeClass('nav-selected');
            $(this).addClass('nav-selected');
            var mainMenuId = $(this).attr('data-menuId');
            $('#subMenuFor' + mainMenuId).siblings().hide();
            $('#subMenuFor' + mainMenuId).show();
            // 默认第一个选中
            var firstSubMenu = $('#subMenuFor' + mainMenuId).find('.submenu li a').first();
            if (firstSubMenu) {
                firstSubMenu.click();
                // 此方式不能触发链接，手动赋值
                var firstSubMenuUrl = firstSubMenu.attr('href');
                $('#frame-container iframe').attr('src', firstSubMenuUrl);
            }
        });
        //  左侧导航栏点击事件
        $('.left_bg .submenu li a').click(function() {
            $('.left_bg .submenu li a').removeClass('on');
            $(this).addClass('on');
        });
        // 默认选中
        var firstMainMenu = $('.mpm-head #mainmenus a').first();
        if (firstMainMenu) {
            firstMainMenu.click();
        }
    },
    // 打开修改密码窗口
    openEditPwdDg : function() {
        $('#editPwdDg').window('open');
    },
    // 确认修改密码
    cfmSavePwd : function() {
        $('#pwdForm').form('submit', {
            url:'/sys/login/changeFenxiaoPWD.jhtml',
            onSubmit: function(){
                var isValid = $(this).form('validate');
                if(isValid){
                    $.messager.progress({
                        title:'温馨提示',
                        text:'数据处理中,请耐心等待...'
                    });
                }
                return isValid;
            },
            success : function(data) {
                $.messager.progress("close");
                var obj = eval('(' + data + ')');
                if (obj) {
                    show_msg(obj.errorMsg);
                    $('#editPwdDg').dialog('close');
                } else {
                    show_msg(obj.errorMsg);
                }
            }
        });
    },
    // 退出
    loginOut : function() {
        $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
            if (r) {
                var url = "/sys/login/loginOut.jhtml";
                var param = {};
                $.post(url, param, function(r) {
                    if (r.success) {
                        location.href="/sys/login/login.jhtml";
                    }
                });
            }
        });
    }
};
// 添加tab标签，为了适应旧首页打开tab方式
function addTab(subtitle, url, icon) {
    window.open(url, '_blank');
}
$(function(){
    merchantHome.init();
});