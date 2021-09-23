<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/11/19
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul class="index-footer-wrap cf" id="J_foot_menu">
    <li class="menu-li plan" onclick="plan();">
        <a href="/mobile/index/index.jhtml?supplierId=${supplierId}" class="menu-a plan">首页</a>
    </li>
    <li class="menu-li" onclick="find();">
        <a href="/mobile/line/index.jhtml?supplierId=${supplierId}" class="menu-a find">线路</a>
    </li>
    <li class="menu-li friends" onclick="friends();">
        <a href="/mobile/user/home.jhtml?supplierId=${supplierId}" class="menu-a friends">个人中心</a>
    </li>
</ul>