<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/11/16
  Time: 15:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header">
    <div class="header_nav">
        <div class="logoName">海游通</div>
        <div class="hostArea">
            <div class="dropdown hotelList">
                <input type="hidden" id="productId" value="${product.id}">
                <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
                    <span class="downcar"><span class="downcar_inner"></span></span>
                    <span id="productName">${product.name}</span>
                </button>
                <ul id="product-drop-menu" class="dropdown-menu top-dropdown-menu" role="menu">
                </ul>
            </div>
            <span class="hostName">你好，${loginUser.userName}&nbsp;|&nbsp;<a href="/yhy/yhyLogin/doLogout.jhtml" hidefocus="true" class="tuoshang">退出</a></span>
        </div>
        <div class="header_list">
            <ul class="clearfix">
                <c:forEach items="${sessionScope.hasSubMenus}" var="menu">
                    <li><a href="${menu.url}">${menu.menuname}</a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
