<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2017/1/13
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="../../yhypc/public/header.jsp"%>
  <link rel="stylesheet" type="text/css" href="/css/recommendPlan/recommendPlan_index.css">
    <title>游记攻略首页</title>
</head>
<body  class="travel">
<div class="hotelIndex">
  <%@include file="../../yhypc/public/nav_header.jsp"%>
  <jx:include fileAttr="${YHY_RECOMMEND_PLAN_INDEX}" targetObject="yhyBuildService" targetMethod="buildRecommendPlanIndex" validDay="1"></jx:include>
  <%@include file="../../yhypc/public/nav_footer.jsp"%>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/recommendPlan/recommendPlan_index.js"></script>
</html>
