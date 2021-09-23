<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>机票查询_火车时刻表_高铁时刻表查询-旅行帮</title>
    <meta name="keywords" content="机票查询，航班查询，高铁时刻表查询，火车时刻表，机票预订，火车票查询"/>
    <meta name="description" content="旅行帮交通频道提供特价飞机票查询预订，火车时刻表查询，高铁票预订，是您网上出行订票的首选。"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
</head>
<body class="Traffic" myname="mall">
<!--head  end--><!-- #EndLibraryItem --><!--banner-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--banner end-->

<jx:include fileAttr="${LVXBANG_TRAFFIC_INDEX}" targetObject="lvXBangBuildService" targetMethod="buildTrafficIndex" validDay="7"></jx:include>

<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<script src="/js/lvxbang/index.js" type="text/javascript"></script>
<script src="/js/lvxbang/traffic/index.js" type="text/javascript"></script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
</body>
</html>
