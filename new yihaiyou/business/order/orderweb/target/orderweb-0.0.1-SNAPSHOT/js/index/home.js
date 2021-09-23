var merchantHome = {
    init : function() {
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
    }
};
$(function(){
    merchantHome.init();
});