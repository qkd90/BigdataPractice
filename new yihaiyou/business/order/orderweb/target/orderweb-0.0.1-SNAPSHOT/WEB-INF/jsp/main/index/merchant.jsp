<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>${systemTitle}</title>
    <%@ include file="../../common/common141.jsp" %>
  <link rel="stylesheet" href="/css/index/merchant.css"/>
  <script type="text/javascript" src="/js/index/merchant.js"></script>
    <style type="text/css">
        .pwdTb td {
            padding:4px;
        }

    </style>
</head>

<body class="">
<!-- 访问“首页”时，给body添加样式名 mp-home-noside 隐藏侧栏 -->
<div class="mpm-topbar">
  <div class="mpg-section">
    <div class="mpm-topbar-bd">
      <strong class="welcome">
        <span class="mpg-gray">欢迎您&nbsp;</span><s:property value="#session.loginuser.userName"/>
      </strong>
      <span class="mpg-gray">|</span>
      <a href="javascript:merchantHome.openEditPwdDg();" hidefocus="true" class="mpg-gray">修改密码</a>
      <span class="mpg-gray">|</span>
      <a href="javascript:merchantHome.loginOut();" hidefocus="true" class="mpg-gray">退出</a>
        <span class="more">
            <a href="/main/index/index.jhtml" hidefocus="true">旧版后台</a>
            <span class="mpg-gray">|</span>
            <a href="<s:property value="fgDomain"/>" target="_blank" hidefocus="true">旅行帮首页</a>
            <span class="mpg-gray">|</span>
            <a href="javascript:void(0);" hidefocus="true" class="tuoshang">商家答疑平台</a>
        </span>
    </div>

  </div>
</div>

<div class="mpm-head">
  <div class="mpg-section clrfix">
    <div class="mpm-head-hd">
        <span class="logo" style="background: url('${logoImg}') 0 0 no-repeat;"></span>
      <strong class="role"></strong>
      <em class="name">${systemTitle}</em>
    </div>
    <div class="mpm-head-bd">
      <div class="nav clrfix" id="mainmenus">
      <c:forEach items="${menuList}" var="mainmenu">
          <a href="javascript:void(0);" hidefocus="true" class="nav-item" id="mainmenu${mainmenu.menuid}" data-menuId="${mainmenu.menuid}">${mainmenu.menuname}</a>
      </c:forEach>
      </div>
    </div>
  </div>
</div>

<div class="wrap">
  <div class="content">
    <table class="tb_layout" cellpadding="0" cellspacing="0" width="100%">
      <tbody><tr>
        <td class="left_bg">
          <div class="left" id="submenus">
              <c:forEach items="${menuList}" var="mainmenu">
              <div id="subMenuFor${mainmenu.menuid}" style="display: none;">
                  <ul class="submenu">
                  <c:forEach items="${mainmenu.children}" var="submenu">
                      <li><a target="frame" data-leaf="true" class="menu-item on" href="${submenu.url}" data-menuId="${submenu.menuid}">${submenu.menuname}</a></li>
                  </c:forEach>
                  </ul>
              </div>
              </c:forEach>
          </div>
        </td>
        <td class="rig_bg">
          <div class="right" id="frame-container">
              <div class="info" style="">
                <iframe id="frmContent" name="frame" frameborder="0" allowtransparency="true" width="100%" scrolling="no" src=""></iframe>
              </div>
          </div>
        </td>
      </tr>
      </tbody></table>
  </div>
</div>

<div class="mpm-foot">
  <p class="copyright"> Copyright©2016　
      <a href="http://www.lvxbang.com/">旅行帮, 帮旅行 </a>
      　版权所有　
      闽ICP备 14006003号</p>
</div>

<!--修改密码窗口-->
<div id="editPwdDg" title="修改密码" class="easyui-dialog" data-options="closed:true,modal:true" style="width:280px;height:180px;padding:10px">
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
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="merchantHome.cfmSavePwd()">确认修改</a>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>
