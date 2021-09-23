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
    <script type="text/javascript" src="/js/outOrder/outOrder/confimOrder.js"></script>
</head>
<style type="text/css">
  body {
    font-size: 14px;
    padding: 0px;
    background-color: white;
  }
    .p_div {
        padding: 5px 20px;
        font-size: 14px;
        font-weight: 700;
    }
</style>
<body>
<%--<input type="text" onkeyup="">--%>

    <div class="p_div">
        <label class="p_label1">产品名称：</label>
        <label class="p_label2">${jszxOrder.product.name}</label>
    </div>
    <div class="p_div">
        <label class="p_label1">联系人：</label>
        <label class="p_label2">${jszxOrder.contact}</label>
    </div>
    <div class="p_div">
        <label class="p_label1">电话：</label>
        <label class="p_label2">${jszxOrder.phone}</label>
    </div>
    <div class="p_div">
        <label class="p_label1">总计：</label>
        <label class="p_label2">${jszxOrder.actualPayPrice}</label>
    </div>
    <input type="hidden" id="confim_orderId" value="${jszxOrder.id}">
    <div style="padding: 5px 20px;width:530px ;height: 225px;">
        <div id="dg_orderDetails"></div>
    </div>


</body>
</html>
