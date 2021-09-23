<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="/css/sailboat/sailboat_index.css">
	<title>海上休闲首页</title>
</head>
<body class="sailBoat">
	<div class="hotelIndex">
        <%@include file="../../yhypc/public/nav_header.jsp"%>
        <jx:include fileAttr="${YHY_SAILBOAT_INDEX}" targetObject="yhyBuildService" targetMethod="buildSailboatIndex" validDay="1"></jx:include>
        <%@include file="../../yhypc/public/nav_footer.jsp"%>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/sailboat/sailboat_index.js"></script>
</html>