<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2017-01-04,0004
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="../../yhypc/public/header.jsp" %>
  <link rel="stylesheet" type="text/css" href="/css/order/hotelOrder.css">
  <title>酒店民宿支付</title>
</head>
<body>
<div class="hotelOrder hotelIndex">
  <%@include file="../../yhypc/public/order_header.jsp" %>
  <input type="hidden" id="startDate" value="${orderDetail.playDate}">
  <input type="hidden" id="endDate" value="${orderDetail.leaveDate}">
  <div class="body_order">
    <div class="progress progress2">
      <span>填写订单</span><span class="active">在线支付</span><span>订单完成</span>
    </div>
    <div class="orderPay clearfix ">
      <div class="payTop">
        <div class="picture"><img src="${orderDetail.product.imgUrl}"></div>
        <div class="order_mess">
          <h3>${order.name}</h3>
          <p>入住日期：
            <span class="date" id="startDateStr"></span>
            <span class="week" id="startWeekday"></span>
            <span class="roomNum">房间数量：${orderDetail.num} </span>
							<span class="total">订单总额：
								<span class="rmb">¥</span><span class="num">${order.price}</span>
							</span>
          </p>
          <p>离店日期：
            <span class="date" id="endDateStr"></span>
            <span class="week" id="endWeekday"></span>
            <span class="roomNum">联系方式：${order.mobile}</span>
          </p>
          <p class="order_timer">：建议您在<span id="waitTimeStr"></span>内完成付款，过期订单会自动取消哦</p>
        </div>
      </div>
    </div>
    <%@include file="../../yhypc/order/orderPay.jsp"%>
  </div>
  <%@include file="../../yhypc/public/nav_footer.jsp" %>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>
<script type="text/javascript" src="/js/order/orderPay.js"></script>
<script type="text/javascript" src="/js/order/hotelOrder_pay.js"></script>
<script type="text/javascript" src="/lib/jquery/jquery.qrcode.min.js"></script>
</html>