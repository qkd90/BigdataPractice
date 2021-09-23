<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>私人定制_线路安排_定制旅游-旅行帮</title>
    <meta name="keywords" content="私人定制，行程安排，定制旅游，旅游线路，行程单，自由行"/>
    <meta name="description"
          content="你只需决定出发，剩下的交给旅行帮。旅行帮为旅游爱好者免费提供丰富的旅游线路，一键定制专属您的自由行行程。帮助旅游爱好者寻找到最适合的旅游目的地，让旅行更精彩！"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/index.css" rel="stylesheet" type="text/css">
</head>
<body myname="TripPlanning">
<%--提示下来状态判断--%>
<input type="hidden" id="keyStatus" value="13"/>
<div class="nextpage ff_yh fs36 textC">
    <a href="javaScript:;" class="close"></a>
    <img src="/images/g_ico.jpg" alt="快速规划旅行的秘诀"/>
</div><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!-- #EndLibraryItem --><!--banner-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>

    <jx:include fileAttr="${LVXBANG_PLAN_INDEX}" targetObject="lvXBangBuildService" targetMethod="buildPlanIndex" validDay="7"></jx:include>

<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<script src="/js/lvxbang/index.js" type="text/javascript"></script>
<script src="/js/lvxbang/plan/index.js" type="text/javascript"></script>
</body>
</html>