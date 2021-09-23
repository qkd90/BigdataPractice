<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" href="/css/scenic/scenicIndex.css">
	<title>景点首页</title>
</head>
<body class="scenic">
	<div class="hotelIndex">
        <%@include file="../../yhypc/public/nav_header.jsp"%>
        <jx:include fileAttr="${YHY_SCENIC_INDEX}" targetObject="yhyBuildService" targetMethod="buildScenicIndex" validDay="1"></jx:include>
        <%@include file="../../yhypc/public/nav_footer.jsp"%>
    </div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/scenic/scenic_index.js"></script>
</html>