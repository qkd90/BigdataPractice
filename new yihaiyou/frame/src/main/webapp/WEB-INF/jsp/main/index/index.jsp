<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>后台管理</title>
    <%@ include file="../../common/common141.jsp" %>
    <script type="text/javascript" src="/js/outlook2.js"></script>
    <script type="text/javascript" src="/js/index.js"></script>
    <link rel="stylesheet" href="/css/index/merchant.css">
    <script type="text/javascript">


        var result = <s:property value='json' escape='false' />
        var _menus = result.menuList;
        //        var logo_img = result.logo;

        //        $("#logoId").css("background-image", "url('"+ logo_img +"') no-repeat left 10%");
        function openmainnoticeWindow(title, url) {
            $('#noticeinfoWindow').window({
                title: title
            });
            $('#noticeinfoWindow').window('open');
            $('#noticeinfoWindow').window('refresh', url);
        }

    </script>
    <style type="text/css">
        .legend {
            color: blue;
            font-size: 13px;
            font-family: "Comic Sans MS";
            font-weight: bold;
            text-shadow: 1px 1px 1px #999;
            text-transform: uppercase;
        }

        #editpass:hover, #loginOut:hover {
            color: #f00;
            font-size: 13px;
            font-family: "Comic Sans MS";
            font-weight: bold;
            text-shadow: 2px 2px 1px #999;
            text-transform: uppercase;
            text-decoration: none;
        }
    </style>
</head>
<div id="over" class="over"></div>
<div id="loadlayout" class="loadlayout"><img src="/css/o_31.gif"/></div>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<noscript>
    <div style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
        <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！'/>
    </div>
</noscript>
<div class="mpm-head" region="north" style="overflow:hidden;">
    <div class="mpm-topbar">
        <div class="mpg-section" style="width:100%">
            <div class="mpm-topbar-bd">
                <strong class="welcome">
                    <span class="mpg-gray">欢迎您&nbsp;</span><s:property value="#session.loginuser.userName"/>
                </strong>
                <span class="mpg-gray">|</span>
                <a href="javascript:openEditPwdDg();" hidefocus="true" class="mpg-gray">修改密码</a>
                <span class="mpg-gray">|</span>
                <a href="javascript:loginOut();" hidefocus="true" class="mpg-gray">退出</a>
        <span class="more">
            <a href="<s:property value="fgDomain"/>" target="_blank" hidefocus="true">一海游首页</a>
            <span class="mpg-gray">|</span>
            <a href="javascript:void(0);" hidefocus="true" class="tuoshang">商家答疑平台</a>
        </span>
            </div>

        </div>
    </div>
    <div class="mpg-section clrfix" style="overflow:hidden;width:100%">
        <div class="mpm-head-hd">
            <%--<span class="logo" style="background: url('${logoImg}') 80% 0 no-repeat;margin:10px 0 0 10px;background-size:50px 60px"></span>--%>
            <%--<span class="logo" style="background: url(/images/yihaiyou_logo.png) 0 0 no-repeat;margin:10px 0 0 10px;"></span>--%>
                <span class="logo"></span>
            <strong class="role" style="margin-top:6px;"></strong>
            <em class="name">${systemTitle}</em>
        </div>
        <div class="mpm-head-bd">
            <div class="nav clrfix" id="mainmenus" style="height:auto;">
                <c:forEach items="${firstMenus}" var="mainmenu">
                    <a href="javascript:void(0);" hidefocus="true" class="nav-item j-smenu" menuid="${mainmenu.menuid}" id="mainmenu${mainmenu.menuid}" data-menuId="${mainmenu.menuid}">${mainmenu.menuname}</a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<script>
    var a=$('.mpg-section .mpm-head-bd .nav a');
    a.first().addClass('white_border_a');
    a.click(function(){
        a.removeClass('white_border_a');
        $(this).addClass('white_border_a');
    });
</script>
<!--<div region="north" title="<s:property value='#session.loginuser.sysUnit.sysSite.sitename' />" id="north" split="false" border="true"
     hide="true"
     style=" height: 100px;overflow:hidden; background: url(/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%; line-height: 20px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">
    <div class="header home-win" id="j-header">
        <div id="logoId" style="background: url('${logoImg}') no-repeat 5% 30%">
		<span style="float: right; padding-left: 20px;" class="head">
		欢迎   【<s:property value='#session.account'/>】
			<span class="legend">
				<s:property value="#session.loginuser.userName"/>
			</span>&nbsp;登录本系统
		</span>
		<span style="float: right; padding-left:88%;padding-top:30px;position: absolute;width:100%" class="head">
			 【<a href="#" id="editpass">修改密码</a>】 【<a href="#" id="loginOut">安全退出</a>】
		</span>
		<span style="padding-left: 10px; font-size: 16px;">
		        <div class="smenu" id="j-smenu">
                    <s:if test="firstMenus.size <= 1">
                        <span href="<s:property value='url' />" menuid="<s:property value='menuid' />" class="j-smenu"></span>
                    </s:if>
                    <s:else>
                        <s:iterator value="firstMenus" var="item">
                            <span href="<s:property value='url' />" menuid="<s:property value='menuid' />" class="j-smenu">
                                <i class="<s:property value='icon' />"></i>
                                <s:property value='menuname'/>
                            </span>
                        </s:iterator>
                    </s:else>
                </div>
		</span>
        </div>
    </div>
</div>-->
<!-- 	<div region="south" split="false"
		style="height: 30px; background: #D2E0F2;">
		<div class="footer">版权所有，翻版必究</div>
	</div>
 -->
<div region="west" hide="true" split="true" title="导航菜单"
     style="width: 180px;" id="west">
    <div id="nav" class="easyui-accordion" border="false">
        <!--  导航内容 -->

    </div>
</div>


<div id="mainPanle" region="center"
     style="background: #eee; overflow-y: hidden">
    <%--<div id="tabs" class="easyui-tabs" fit="true" border="false">
        <div title="欢迎使用本系统"
             style="padding: 20px; overflow: hidden; color: red;">
            <div id="p" class="easyui-panel" title="最新公告,功能有待完善"
                 style="width: 500px; height: 400px; padding: 100px;">
                <ul>
                    <li>您好，欢迎回来
						<span class="legend">
							<s:property value="#session.loginuser.userName"/>
						</span>
                    </li>
                </ul>
            </div>
        </div>
    </div>--%>
    <iframe scrolling="auto" frameborder="0"  src="#blank" id="frame" style="width:100%;height:100%;"></iframe>
</div>

<!--修改密码窗口-->
<div id="editPwdDg" title="修改密码" class="easyui-dialog" data-options="closed:true,modal:true" style="width:280px;height:220px;padding:10px">
    <form id="pwdForm" method="post">
        <table class="pwdTb">
            <tr>
                <td align="right">原密码：</td>
                <td>
                    <input class="easyui-textbox" type="password" name="oldpass" data-options="required:true,validType:'length[6,20]'" style="width:140px;">
                </td>
            </tr>
            <tr>
                <td align="right">新密码：</td>
                <td>
                    <input id="newpass" class="easyui-textbox" type="password" name="newpass" data-options="required:true,validType:'length[6,20]'" style="width:140px;">
                </td>
            </tr>
            <tr>
                <td align="right">确认密码：</td>
                <td>
                    <input class="easyui-textbox" type="password" name="repass" data-options="required:true,validType:['length[6,20]','equals[\'#newpass\']']" style="width:140px;">
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="cfmSavePwd()">确认修改</a>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>