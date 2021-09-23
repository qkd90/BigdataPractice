<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/11/20
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>选择日期</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/common.css?${mallConfig.resourceVersion}">
    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/line/date.css?${mallConfig.resourceVersion}">
    <link href="${mallConfig.resourcePath}/js/fullcalendar/fullcalendar.min.css?${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/js/fullcalendar/fullcalendar.print.css?${mallConfig.resourceVersion}" rel="stylesheet">

</head>
<body>
<div class="header-wrap">
    <a class="left-btn" href="javascript:history.go(-1)"></a>
    <h1 class="pageTitle">选择日期</h1>
</div>
<div class="main-wrap">
    <input type="hidden" value="${priceTypeId}" id="priceTypeId"/>
    <input type="hidden" value="${lineId}" id="lineId"/>
    <div class="price-type-wrap cf">
        <c:forEach items="${line.linetypeprices}" var="priceType">
            <div class="price-type fl">
                <div class="price-type-content" data-id="${priceType.id}">
                    <h3 class="name">${priceType.quoteName}</h3>
                    <%--<div class="description">${priceType.quoteDesc}</div>--%>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="calendar-wrap">
        <div id="calendar"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/mobile/common/common-script.jsp"></jsp:include>
<script src="${mallConfig.resourcePath}/js/fullcalendar/lib/moment.min.js?${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/fullcalendar/fullcalendar.min.js?${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/fullcalendar/lang-all.js?${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/mobile/plan/date.js?${mallConfig.resourceVersion}"></script>
</body>
</html>
