<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <%@include file="../../yhypc/public/header.jsp"%>
  <link rel="stylesheet" type="text/css" href="/css/cruiseship/cruiseship_index.css">
  <title>游轮旅游首页</title>
<body class="cruise">
<div class="hotelIndex">
  <%@include file="../../yhypc/public/nav_header.jsp"%>
  <jx:include fileAttr="${YHY_CRUISESHIP_INDEX}" targetObject="yhyBuildService" targetMethod="buildCruiseshipIndex" validDay="1"></jx:include>
  <%@include file="../../yhypc/public/nav_footer.jsp"%>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/cruiseship/cruiseship_index.js"></script>
</html>