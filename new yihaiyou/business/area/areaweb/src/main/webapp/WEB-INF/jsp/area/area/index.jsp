<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/11/26
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>热门城市编辑</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" href="/css/area.area.index.css"/>
</head>
<body>
<div class="main-wrap">
    <form action="/area/area/selectRecommendCity.jhtml" >
        <div class="area-section">
            <c:forEach items="${areaList}" var="area">
                <div class="province-section">
                    <div class="province-title">
                        <input type="checkbox"/>
                        <label>
                           ${area.name}
                        </label>
                    </div>
                    <div class="city-list-wrap cf hide">
                        <c:forEach items="${area.childs}" var="child">
                            <div class="city <c:if test="${child.recommended}">selected </c:if>">
                                <label>
                                    <input type="checkbox" name="selectedCities" value="${child.id}" <c:if test="${child.recommended}">checked="true" </c:if> />${child.name}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="button-section">
            <input class="submit" type="submit">
        </div>
    </form>
</div>
<script src="/js/area/area/index.js"></script>
</body>
</html>
