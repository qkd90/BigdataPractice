<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2015/12/24
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html >
<html>

<head>

  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <jx:include fileAttr="${LVXBANG_RECOMMENDPLAN_HEAD}" targetObject="lvXBangBuildService" targetMethod="buildOneRecplan" objs="${param.recplanId}" validDay="60"></jx:include>
  <link href="/css/tBase.css" rel="stylesheet" type="text/css">
  <link href="/css/announcement.css" rel="stylesheet" type="text/css">
  <link href="/css/detail.css" rel="stylesheet" type="text/css">
</head>
<body myname="strategy" class="Travel_Detail">
<%@include file="../common/header.jsp" %>
<jx:include fileAttr="${LVXBANG_RECOMMENDPLAN_DETAIL}" targetObject="lvXBangBuildService" targetMethod="buildOneRecplan" objs="${param.recplanId}" validDay="60"></jx:include>
<%@include file="../common/footer.jsp" %>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/recommendPlan/recommendPlan.js" type="text/javascript"></script>
<!--comment-->
<jsp:include page="/WEB-INF/jsp/lvxbang/comment/comment.jsp"></jsp:include>
</body>
</html>
<script src="/js/lvxbang/recommendPlan/recomendPlanDetail.js" type="text/javascript"></script>
<script src="/js/lvxbang/collect.js" type="text/javascript"></script>
<script src="/js/lvxbang/public.js" type="text/javascript"></script>
<script type="text/javascript">
  $(document).ready(function() {
    //复制路线窗口
    $(".d_stroke").hover(function () {
      $('div', this).show();
    }, function () {
      $('div', this).hide();

    });

    $(".d_stroke").css('color','#4cb7f5');
    $(".d_stroke").find('i').css('background-position-y','-295px');

  });
</script>