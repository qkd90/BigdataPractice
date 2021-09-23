<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>特色美食_小吃大全_特色小吃_旅行帮</title>
    <meta name="keywords" content="特色美食，小吃大全，特色小吃，美食之旅，旅行帮特色美食"/>
    <meta name="description" content="旅行帮美食频道为您提供全国各地上万种特色美食，美食旅游线路,美食旅游攻略，满足吃货的味蕾。"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
</head>
<body myname="strategy" class="Food"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--head-->
   <jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->
    <jx:include fileAttr="${LVXBANG_DELICACY_INDEX}" targetObject="lvXBangBuildService" targetMethod="buildDelicacyIndex" validDay="7"></jx:include>
        <!--banner-->
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>
<script src="/js/lvxbang/delicacy/index.js" type="text/javascript"></script>