<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/6/23
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>自助游_自驾游_自由行-旅行帮</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="自助游,自驾游,自由行,周边自助,国内自助,出境自助,周边自驾,国内自驾,出境自驾"/>
    <meta name="description" content="旅行帮提供丰富、优质的自驾、自助旅游线路及报价，并搭配多种玩法推荐。自驾游、自助游来旅行帮！"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
    <link href="/css/line/line.reset.css" rel="stylesheet" type="text/css"/>
    <link href="/css/line/line.mask.css" rel="stylesheet" type="text/css"/>
    <link href="/css/line/line.index.css" rel="stylesheet" type="text/css"/>
    <link href="/css/line/line_index_self.css" rel="stylesheet" type="text/css"/>
</head>
<body class="index1000">
<%@include file="../common/header.jsp" %>
<link href="/css/line/super.css" rel="stylesheet" type="text/css"/>
<jx:include fileAttr="${LVXBANG_LINE_SELF_TOUR_INDEX}" targetObject="lvXBangBuildService" targetMethod="buildLineSelfTourIndex" validDay="1"></jx:include>
<%@include file="../common/footer.jsp" %>
<script type="text/javascript" src="/js/lvxbang/line/index.js"></script>
<script type="text/javascript" src="/js/lvxbang/line/line_index_self.js"></script>
</body>
</html>
