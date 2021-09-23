<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2015/12/28
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>景点大全_景点门票_门票预订-旅行帮</title>
    <meta name="keywords" content="景点门票,全国旅游景点门票,门票预订,门票价格,景点点评"/>
    <meta name="description"
          content="旅行帮景点频道提供全国各地的景点门票查询预订服务,推荐当地热门景点，大量特价景点门票供大家选择。预订旅游景点门票就上旅行帮景点频道"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
</head>
<body myname="mall" class="Attractions">
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<jx:include fileAttr="${LVXBANG_SCENIC_INDEX}" targetObject="lvXBangBuildService" targetMethod="buildScenicIndex" validDay="7"></jx:include>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
</body>
</html>
<script type="text/html" id="tpl-selectName-list-item">
    <li scenic="{{s}}">
        <label class="fl">{{s1}}<strong>{{name}}</strong>{{s2}}</label>
    </li>
</script>
<script src="/js/lvxbang/scenic/index.js" type="text/javascript"></script>
<script src="/js/lvxbang/index.js" type="text/javascript"></script>