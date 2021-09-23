<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../../yhypc/public/header.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/order/hotelOrder.css">
    <title>邮轮舱房支付</title>
</head>
<body>
<div class="hotelOrder hotelIndex">
    <%@include file="../../yhypc/public/order_header.jsp" %>
    <input type="hidden" id="startDate" value="${order.playDate}">
    <div class="body_order">
        <div class="progress progress2">
            <span>填写订单</span><span class="active">在线支付</span><span>订单完成</span>
        </div>
        <div class="orderPay clearfix ">
            <div class="payTop">
                <div class="picture"><img src="${orderDetail.product.imgUrl}"></div>
                <div class="order_mess">
                    <h3>${order.name}</h3>
                    <p>
                        <span class="l_title">游玩时间：</span>
                        <span class="s_fir"></span>
                        <span class="date" id="startDateStr"></span>
                        <span class="week" id="startWeekday"></span>
                        <c:forEach items="${order.orderDetails}" var="orderDetails">
                        <input type="hidden" class="roomNum" num="${orderDetails.num}">

                        </c:forEach>
                        <span class="numOfRoom"><label>购买数量：</label></span>
							<span class="total">订单总额：
								<span class="rmb">¥</span><span class="num">${order.price}</span>
							</span>
                    </p>
                    <p>
                        <span class="l_title">取票人：</span>
                        <span class="date">${order.recName}</span>
                        <span class="host"><label>联系方式：</label>${order.mobile}</span>
                    </p>
                    <p class="order_timer">： 建议您在<span id="waitTimeStr"></span>内完成付款，过期订单会自动取消哦</p>
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
<script type="text/javascript" src="/js/order/cruiseshipOrder_pay.js"></script>
<script type="text/javascript" src="/lib/jquery/jquery.qrcode.min.js"></script>
</html>