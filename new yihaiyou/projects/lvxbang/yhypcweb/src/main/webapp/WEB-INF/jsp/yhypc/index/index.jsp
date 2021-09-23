<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="/css/index/index.css">
	<title>一海游首页</title>
</head>
<body class="Index chrome">
	<div class="hotelIndex">
		<%@include file="../../yhypc/public/nav_header.jsp"%>
		<jx:include fileAttr="${YHY_INDEX}" targetObject="yhyBuildService" targetMethod="buildIndex" validDay="1"></jx:include>
		<%@include file="../../yhypc/public/nav_footer.jsp"%>
	</div>
</body>
</html>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/index/index.js"></script>
<script>
var mySwiper = new Swiper ('.swiper-container', {
direction: 'horizontal',
loop: false,
autoplay:5000,
pagination: '.swiper-pagination'
})
</script>