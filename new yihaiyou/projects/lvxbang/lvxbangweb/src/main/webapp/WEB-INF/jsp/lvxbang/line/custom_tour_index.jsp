<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/7/5
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>高端定制旅游_包团定制旅游_精品定制旅游-旅行帮</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="高端定制,高端旅游,包团旅游,小众包团,精品旅游线路"/>
    <meta name="description" content="旅行帮提供高端定制,高端旅游,包团旅游,小众包团等定制旅游线路，高端旅游定制就找旅行帮"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
    <link href="/css/line/line.reset.css" rel="stylesheet" type="text/css"/>
    <link href="/css/line/line.mask.css" rel="stylesheet" type="text/css"/>
    <link href="/css/line/line.index.css" rel="stylesheet" type="text/css"/>
    <link href="/css/line/line_index_custom.css" rel="stylesheet" type="text/css"/>
</head>
<body class="index1000">
<%@include file="../common/header.jsp" %>
<link href="/css/line/super.css" rel="stylesheet" type="text/css"/>
<jx:include fileAttr="${LVXBANG_LINE_CUSTOM_TOUR_INDEX}" targetObject="lvXBangBuildService" targetMethod="buildLineCustomTourIndex" validDay="1"></jx:include>
<%@include file="../common/footer.jsp" %>
<script type="text/javascript" src="/js/lvxbang/line/index.js"></script>
<script type="text/javascript" src="/js/lvxbang/line/line_index_custom.js"></script>
</body>
</html>
