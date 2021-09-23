<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../../yhypc/public/header.jsp" %>
    <link rel='stylesheet' href='/lib/fullcalendar-2.4.0/lib/cupertino/jquery-ui.min.css' />
    <link rel="stylesheet" href="/lib/fullcalendar-2.4.0/fullcalendar.css">
    <link href='/lib/fullcalendar-2.4.0/fullcalendar.print.css' rel='stylesheet' media='print' />
    <link rel="stylesheet" href="/css/order/sailboatOrder.css">
    <title>景点门票填写订单</title>
</head>
<body class="sailBoat">
<div class="hotelIndex">
<%@include file="../../yhypc/public/order_header.jsp" %>
<div class="nav">
    <ul class="clearfix">
        <li class="triangle-li1">
            <a href="#">
                填写订单
                <span class="triangle1"></span>
            </a>
        </li>
        <li class="triangle-li2">
            <a href="#">
                <span class="triangle2"></span>
                在线支付
                <span class="triangle3"></span>
            </a>
        </li>
        <li class="triangle-li3">
            <a href="#">
                <span class="triangle4"></span>
                订单完成
            </a>
        </li>
    </ul>
</div>
<div class="fill-order-wrap">
    <h3 class="fill-order-head">预定信息</h3>
    <form>
        <input type="hidden" id="ticketPriceId" name="priceId" value="${ticketPrice.id}">
        <input type="hidden" id="ticketId" value="${ticketPrice.ticket.id}">
        <div class="fill-order-body">
            <h4>${ticketPrice.ticket.name}
                <span class="head-bottom-line"></span>
            </h4>
            <div class="fill-order-content">
                <div class="fill-order-content-part1">
                    <div class="input-group clearfix">
                        <label class="pull-left">票型名称：</label>
                        <input type="hidden" name="seatType" value="${ticketPrice.name}" id="priceName">
                        <input type="hidden" name="seatType" value="${ticketPrice.ticket.ticketType}" id="ticketType">
                        <span class="pull-left">${ticketPrice.name}</span>
                    </div>
                    <div class="input-group input-group2 clearfix">
                        <label class="pull-left">游玩日期：</label>
                        <input type="text" id="play-date" name="startTime"  class="datepicker pull-left" readonly value="<s:date name="ticketPrice.name" format="yyyy-MM-dd"/>">
                        <div class="clearfix"></div>
                        <div class="calendar-shadom"></div>
                        <div id='price-calendar' style="width: 500px; margin-left: 85px; position: absolute; z-index: 200;display: none;"></div>
                        <%--<div id="calendar-div" style="width: 500px; margin-left: 85px;  display:none" onblur="this.style.display='none';">--%>
                            <%--<div id='price-calendar'></div>--%>
                        <%--</div>--%>
                    </div>
                    <div class="input-group input-group3 clearfix">
                        <label class="pull-left">门票数量：</label>
                        <span class="pull-left" id="ticket-count-span">0</span>
                    </div>
                    <div class="input-group input-group4 clearfix">
                        <label class="pull-left">费用小计：</label>
                        <input type="hidden" id="ticket-per-price" name="price" value="${ticketDateprice.priPrice}">
                        <input type="hidden" id="ticketDatePriceId" name="price" value="${ticketDateprice.id}">
                        <input type="hidden" id="datepriceName" name="price" value="${ticketDateprice.name}">
                        <span class="pull-left" id="ticket-price-span"><sub>￥</sub><span>0</span></span>
                    </div>
                </div>
                <div class="customer-name">
                    <h5>选择出行游客<a onclick="TicketOrderWrite.addTourist()">添加游客></a></h5>
                    <div class="customer-name-check clearfix">
                    </div>
                </div>
                <div class="customer-info">
                    <h5>取票人信息<a href="#">（接受确认短信）</a></h5>
                    <div class="input-group clearfix">
                        <label>中文姓名：</label>
                        <input type="text" name="main-customer" placeholder="中文姓名" class="customer-name-input" id="contactName">
                        <div class="fill-order-name-tooltips">
                            <div class="tooltips-arrow"></div>
                            <div class="tooltips-inner">
                                请输入姓名
                            </div>
                        </div>
                    </div>
                    <div class="input-group clearfix">
                        <label class="pull-left">手机号码：</label>
                        <input type="text" name="main-customer" placeholder="接收确认短息" class="customer-tel-input pull-left" id="contactTel">
                        <div class="fill-order-tel-tooltips">
                            <div class="tooltips-arrow"></div>
                            <div class="tooltips-inner">
                                请输入手机号码
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="fill-order-footer clearfix">
            <div class="fill-order-footer-left">
                <div class="input-group clearfix">
                    <input type="checkbox" name="agree" class="agree" id="agree" checked>
                    <span class="fill-order-footer-radio"></span>
                    <label for="agree">我已阅读并接受合同条款、补充条款及其他所有内容</label>
                </div>
                <div class="fill-order-cost clearfix">
                    <label>订单总额:</label>
                    <span id="ticket-total-price-span"><sup>￥</sup>0</span>
                </div>
            </div>
            <div class="fill-order-footer-right">
                <a id="submitOrder">提交订单</a>
            </div>
        </div>
    </form>
</div>
<%@include file="../../yhypc/public/nav_footer.jsp" %>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>

<script type="text/html" id="tpl-order-sailboat-wirte-customer">
    <div class="checkbox-group clearfix">
        <label onclick="TicketOrderWrite.statisticOrder()">
            <input type="checkbox" name="customer" id="customer-{{id}}" value="{{id}}" >
            <span class="customer-name-radio"></span>
            <span>{{name}}</span>
        </label>
    </div>
</script>

<script src='/lib/util/moment.min.js'></script>
<script src="/lib/fullcalendar-2.4.0/fullcalendar.min.js"></script>
<script src='/lib/fullcalendar-2.4.0/lang-all.js'></script>
<script src="/js/public/tourist.js"></script>
<script src="/js/order/ticketOrder_write.js"></script>
</html>