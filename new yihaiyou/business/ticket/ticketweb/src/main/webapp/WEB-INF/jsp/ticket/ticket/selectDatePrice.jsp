<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/2/23
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
    <title>录入外接订单</title>
    <link rel='stylesheet' href='/fullcalendar-2.4.0/lib/cupertino/jquery-ui.min.css'/>
    <link href='/fullcalendar-2.4.0/fullcalendar.css' rel='stylesheet'/>
    <link href='/fullcalendar-2.4.0/fullcalendar.print.css' rel='stylesheet' media='print'/>
    <script src='/fullcalendar-2.4.0/lib/moment.min.js'></script>
    <script src='/fullcalendar-2.4.0/fullcalendar.min.js'></script>

    <%--<script src="/js/outOrder/Time/WdatePicker.js" type="text/javascript"></script>--%>
    <%--<script language="javascript" type="text/javascript" src="/js/outOrder/Time/WdatePicker.js"></script>--%>
    <script type="text/javascript" src="/js/ticket/selectDatePrice.js"></script>
</head>
<style type="text/css">
  body {
    font-size: 14px;
    padding: 0px;
    background-color: white;
  }
</style>
<body>
    <input type="hidden" id="ticketPriceId" value="${ticketPriceId}">
    <input type="hidden" id="ticketId" value="${ticketId}">
    <div id="priceCalendar" style="width: 93%; height: 100%; padding: 10px"></div>
</body>
</html>
