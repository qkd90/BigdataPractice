<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<jx:include fileAttr="${LVXBANG_DESTINATION_HEAD}" targetObject="lvXBangBuildService" targetMethod="buildOneDetination" objs="${param.cityCode}" validDay="60"></jx:include>
	<link href="/css/tBase.css" rel="stylesheet" type="text/css">
	<link href="/css/announcement.css" rel="stylesheet" type="text/css">
	<link href="/css/float.css" rel="stylesheet" type="text/css">
	<link href="/css/index.css" rel="stylesheet" type="text/css">
</head>
<body myname="strategy" class="Destination_cs"><!-- #BeginLibraryItem "/lbi/head.lbi" -->

	<!-- header begin -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!-- header end -->

	<jx:include fileAttr="${LVXBANG_DESTINATION_DETAIL}" targetObject="lvXBangBuildService" targetMethod="buildOneDetination" objs="${param.cityCode}" validDay="60"></jx:include>

<!-- main begin -->
<form method="post" id="more-form">
    <input type="hidden" id="more-cityName" name="cityName">
    <input type="hidden" id="more-cityCode" name="cityCode">
    <input type="hidden" id="more-cityId" name="cityId">
</form>
</body>
</html>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<script src="/js/lvxbang/index.js" type="text/javascript"></script>
<script src="/js/lvxbang/destination/detail.js" type="text/javascript"></script>