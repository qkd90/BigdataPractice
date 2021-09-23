////////////////////////首页////////////////////////
$(function () {
    InitLeftMenu(_menus, false);
    tabClose();
    tabCloseEven();
});

//初始化左侧
function InitLeftMenu(_menus, isClick) {
    if (isClick) {
        var _allPanels = $("#nav").accordion('panels');
        //移除之前面板
        for (var i = 0; i < _allPanels.length;) {
            $('#nav').accordion('remove', _allPanels[i].panel('options').title);

        }
    }
    $("#nav").accordion({animate: false});//为id为nav的div增加手风琴效果，并去除动态滑动效果
    $.each(_menus, function (i, n) {//$.each 遍历_menu中的元素
        if (n.children != undefined && n.children.length > 0) {
            var menulist = '';
            menulist += '<ul>';
            $.each(n.children, function (j, o) {
                menulist += '<li><div><a id="'+ o.menuid +'" ref="' + o.menuid + '" href="#" rel="' + o.url + '" ><span class="nav">' + o.menuname + '</span></a></div></li> ';
            });
            menulist += '</ul>';
            $('#nav').accordion('add', {
                title: n.menuname,
                content: menulist
            });
        }
    });
    var len = $('.easyui-accordion li div.selected').length;
    if (len == 0) {
        if ($('.easyui-accordion li a').length > 0) {
            childMenuClick($('.easyui-accordion li a').eq(0));
        }
    }

    $('.easyui-accordion li a').click(function () {//当单击菜单某个选项时，在右边出现对用的内容
        childMenuClick($(this));
    }).hover(function () {
        $(this).parent().addClass("hover");
    }, function () {
        $(this).parent().removeClass("hover");
    });

    //选中第一个
    var panels = $('#nav').accordion('panels');
    if (panels != null && panels.length > 0) {
        var t = panels[0].panel('options').title;
        $('#nav').accordion('select', t);
    }
}

function childMenuClick(m) {
    var tabTitle = m.children('.nav').text();//获取超链里span中的内容作为新打开tab的标题

    var url = m.attr("rel");
    var menuid = m.attr("ref");//获取超链接属性中ref中的内容
    var icon = getIcon(menuid, icon);

    addTab(tabTitle, url, '');//增加tab
    $('.easyui-accordion li div').removeClass("selected");
    m.parent().addClass("selected");
}

function outclick(url) {
    $.each($('.easyui-accordion li a'), function(i, perA) {
        var rel = $(perA).attr("rel");
        if (rel === url) {
            childMenuClick($(perA));
            return false;
        }
    });
}

//获取左侧导航的图标
function getIcon(menuid) {
    var icon = 'icon ';
    $.each(_menus, function (i, n) {
        $.each(n.children, function (j, o) {
            if (o.menuid == menuid) {
                icon += o.icon;
            }
        });
    });

    return icon;
}

//添加tab标签
function addTab(subtitle, url, icon) {
    //if(!$('#tabs').tabs('exists',subtitle)){
    //	$('#tabs').tabs('add',{
    //		title:subtitle,
    //		content:createFrame(url),
    //		closable:true,
    //		icon:icon
    //	});
    //}else{
    //	$('#tabs').tabs('select',subtitle);
    //	$('#mm-tabupdate').click();
    //}
    //tabClose();
    $("#frame").attr('src', url);
}

//创建frame
function createFrame(url) {
    var s = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
    return s;
}

//关闭tab标签
function tabClose() {
    /*双击关闭TAB选项卡*/
    $(".tabs-inner").dblclick(function () {
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tabs').tabs('close', subtitle);
    });
    /*为选项卡绑定右键*/
    $(".tabs-inner").bind('contextmenu', function (e) {
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });

        var subtitle = $(this).children(".tabs-closable").text();

        $('#mm').data("currtab", subtitle);
        $('#tabs').tabs('select', subtitle);
        return false;
    });
}
//绑定右键菜单事件
function tabCloseEven() {
    //刷新
    $('#mm-tabupdate').click(function () {
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        $('#tabs').tabs('update', {
            tab: currTab,
            options: {
                content: createFrame(url)
            }
        });
    });
    //关闭当前
    $('#mm-tabclose').click(function () {
        var currtab_title = $('#mm').data("currtab");
        $('#tabs').tabs('close', currtab_title);
    });
    //全部关闭
    $('#mm-tabcloseall').click(function () {
        $('.tabs-inner span').each(function (i, n) {
            var t = $(n).text();
            $('#tabs').tabs('close', t);
        });
    });
    //关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function () {
        $('#mm-tabcloseright').click();
        $('#mm-tabcloseleft').click();
    });
    //关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function () {
        var nextall = $('.tabs-selected').nextAll();
        if (nextall.length == 0) {
            //msgShow('系统提示','后边没有啦~~','error');
            alert('后边没有啦~~');
            return false;
        }
        nextall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });
    //关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function () {
        var prevall = $('.tabs-selected').prevAll();
        if (prevall.length == 0) {
            alert('到头了，前边没有啦~~');
            return false;
        }
        prevall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });

    //退出
    $("#mm-exit").click(function () {
        $('#mm').menu('hide');
    });
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
    $.messager.alert(title, msgString, msgType);
}
