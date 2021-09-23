<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>旅游目的地_旅行目的地-旅行帮</title>
	<meta name="keywords" content="当季热门游记，热门目的地, 最新旅游攻略, 自由行目的地"/>
	<meta name="description"
		  content="旅行帮为旅游爱好者推荐当季热门目的地，并提供各目的地热门游记、目的地介绍、交通、美食、景点、酒店等自由行信息，帮助旅游爱好者寻找到最适合的旅游目的地，让旅行更精彩！"/>
<link href="/css/tBase.css" rel="stylesheet" type="text/css">
<link href="/css/announcement.css" rel="stylesheet" type="text/css">
<link href="/css/index.css" rel="stylesheet" type="text/css">
</head>
<body myname="strategy" class="Destination">
<!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end-->
<!-- #EndLibraryItem -->
 	<jx:include fileAttr="${LVXBANG_DESTINATION_INDEX}" targetObject="lvXBangBuildService" targetMethod="buildDestinationIndex"></jx:include>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<%--目的地搜索框跳到景点使用--%>
<form id="destinationScenicSearch" method="post" action="${SCENIC_PATH}/scenic_list.html">
	<input id="destinationScenicName" name="scenicName" type="hidden" placeholder="输入景点" value="" class="input" autocomplete="off">
</form>

<script src="/js/lvxbang/index.js" type="text/javascript"></script>
<script src="/js/lvxbang/destination/index.js" type="text/javascript"></script>
<!-- foot end -->
</body>
</html>