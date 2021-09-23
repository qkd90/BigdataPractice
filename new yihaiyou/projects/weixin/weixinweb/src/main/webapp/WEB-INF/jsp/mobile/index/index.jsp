<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/11/19
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<html>
<head>
    <title>旅行帮</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/common.css?${mallConfig.resourceVersion}">
    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/index/index.css?${mallConfig.resourceVersion}">
    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/carousel.min.css?${mallConfig.resourceVersion}">
    <jsp:include page="/WEB-INF/jsp/mobile/common/baiduCount.jsp"></jsp:include>
</head>
<body>
<jx:include fileAttr="${MOBILE_INDEX}"></jx:include>
<jsp:include page="/WEB-INF/jsp/mobile/common/footer-nav.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/mobile/common/common-script.jsp"></jsp:include>
<script src="${mallConfig.resourcePath}/js/carousel.min.js?${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/mobile/index/index.js?${mallConfig.resourceVersion}"></script>

</body>
</html>
