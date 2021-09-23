<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/11/27
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>供应商城市编辑</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" href="/css/area.area.supplier.css"/>
</head>
<body>
<div class="main-wrap">
    <form >
        <div class="area-section">
            <c:forEach items="${areaList}" var="area">
                <div class="province-section">
                    <div class="province-title">
                        <label>${area.name}</label>
                    </div>
                    <div class="city-list-wrap cf hide">
                        <c:forEach items="${area.childs}" var="child">
                            <div class="city ">
                                <c:if test="${child.recommended}">
                                    <span class="recommend">推荐</span>
                                </c:if>
                                <label>
                                    <input type="hidden" class="id" value="${child.extension.supplierCity!=null?child.extension.supplierCity.id:null}"/>

                                    <input type="hidden" class="cityId" value="${child.id}"/>
                                    ${child.name}
                                </label>
                                <div class="operation cf">
                                    <label class="operator">启用:<input class="selected" type="checkbox" <c:if test="${child.extension.supplierCity.selected}">checked="true" </c:if>/></label>
                                    <label class="operator  <c:if test="${!child.extension.supplierCity.selected}">disabled </c:if>">标红:<input class="recommended" <c:if test="${!child.extension.supplierCity.selected}">disabled="disabled" </c:if> type="checkbox" <c:if test="${child.extension.supplierCity.recommended}">checked="true" </c:if>/></label>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="button-section">
            <input class="submit" type="button" value="提交">
        </div>
    </form>
</div>
<script src="/js/area/area/supplier.js"></script>
</body>
</html>
