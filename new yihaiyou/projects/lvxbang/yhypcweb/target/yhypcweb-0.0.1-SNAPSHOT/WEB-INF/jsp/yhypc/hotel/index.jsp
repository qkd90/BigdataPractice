<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="/css/hotel/hotelIndex.css">
	<title>酒店民宿首页</title>
</head>
<body class="hotel">
	<div class="hotelIndex">
        <%@include file="../../yhypc/public/nav_header.jsp"%>
        <jx:include fileAttr="${YHY_HOTEL_INDEX}" targetObject="yhyBuildService" targetMethod="buildHotelIndex" validDay="1"></jx:include>
        <%@include file="../../yhypc/public/nav_footer.jsp"%>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/hotel/hotel.js"></script>
<script type="text/javascript" src="/js/hotel/hotel_index.js"></script>
</html>