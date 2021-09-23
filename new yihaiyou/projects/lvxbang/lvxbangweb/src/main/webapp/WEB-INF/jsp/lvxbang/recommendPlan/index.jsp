<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2015/12/24
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html >
<html>
<head>
    <title>毕业旅游_穷游_旅游攻略-旅行帮</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业旅游，穷游, 旅游攻略，北京游记，厦门游记，上海游记"/>
    <meta name="description"
          content="旅行帮，分享旅行达人的精彩旅程，汇集青春游、穷游、毕业游等海量游记，一键引用游记并购买相关线路，让您的旅行更轻松！"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
</head>
<body myname="strategy" class="Travel">
<%@include file="../common/header.jsp" %>
<jx:include fileAttr="${LVXBANG_RECOMMENDPLAN_INDEX}" targetObject="lvXBangBuildService" targetMethod="buildRecplanIndex" validDay="1"></jx:include>
<%@include file="../common/footer.jsp" %>
<script src="/js/lvxbang/index.js" type="text/javascript"></script>
</body>
</html>
