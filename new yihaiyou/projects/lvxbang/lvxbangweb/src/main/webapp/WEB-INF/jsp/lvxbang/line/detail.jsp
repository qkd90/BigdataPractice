<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2016-06-07,0007
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="jx" uri="/includeTag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <jx:include fileAttr="${LVXBANG_LINE_HEAD}" targetObject="lvXBangBuildService" targetMethod="buildOneLine" objs="${param.lineId}" validDay="30"></jx:include>
  <link href="/css/tBase.css" rel="stylesheet" type="text/css">
  <link href="/css/announcement.css" rel="stylesheet" type="text/css">
  <link href="/css/index.css" rel="stylesheet" type="text/css">
  <link href="/css/detail.css" rel="stylesheet" type="text/css">
  <link href="/css/line/dengji.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" href="/css/line/index_nav_menu.css" type="text/css">
  <link rel="stylesheet" href="/css/line/modules.css" type="text/css">
  <link rel="stylesheet" href="/css/line/head_nav_new.css" type="text/css">
  <link rel="stylesheet" href="/css/line/common_foot_v3.css" type="text/css">
</head>
<body class="line_detail" style="line-height: 1.5;">
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<jx:include fileAttr="${LVXBANG_LINE_DETAIL}" targetObject="lvXBangBuildService" targetMethod="buildOneLine" objs="${param.lineId}" validDay="30"></jx:include>
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
</body>
</html>
<script type="text/html" id="tpl-select-date-item">
  <li data-date="{{day}}" data-child-price="{{childPrice}}">
      {{showDate}}({{showWeek}}) {{discountPrice + rebate}}元/人  {{if childPrice > 0}}{{childPrice + childRebate}}元/儿童{{/if}}  {{if oasiaHotel > 0}}(单房差{{oasiaHotel}}元/人){{/if}}
  </li>
</script>
<script type="text/html" id="tpl-history-item">
  <li class="clearfix">
    <div class="des">
      <p class="name">
        <a href="/line_detail_{{resObjectId}}.html" target="_blank">
          &lt;{{title}}&gt;					</a>
      </p>
      <p class="price">
        <em>¥{{price}}起</em>
      </p>
    </div>
    <div class="hot_num{{if index > 3}} hot_num_grey{{/if}}">{{index}}</div>
  </li>
</script>
<link rel='stylesheet' href='/fullcalendar-2.4.0/lib/cupertino/jquery-ui.min.css'/>
<link href='/fullcalendar-2.4.0/fullcalendar.css' rel='stylesheet'/>
<link href='/fullcalendar-2.4.0/fullcalendar.print.css' rel='stylesheet' media='print'/>
<script src='/fullcalendar-2.4.0/lib/moment.min.js'></script>
<script src='/fullcalendar-2.4.0/fullcalendar.min.js'></script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript" ></script>
<script src="/js/lvxbang/line/detail.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/lvxbang/collect.js"></script>