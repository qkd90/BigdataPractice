<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <title>轮渡船票</title>
    <link rel="stylesheet" href="/css/ferry/ferry-fill-order.css">
</head>
<body>
<div class="hotelIndex">
<%@include file="../../yhypc/public/order_header.jsp" %>
<div class="nav">
    <ul class="clearfix">
        <li>
            <a href="#">
                填写订单
                <span class="triangle1"></span>
            </a>
        </li>
        <li>
            <a href="#">
                <span class="triangle2"></span>
                在线支付
                <span class="triangle3"></span>
            </a>
        </li>
        <li>
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
        <div class="fill-order-body">
            <h4>航班信息
                <span class="head-bottom-line"></span>
            </h4>
            <div class="fill-order-content">
                <div class="fill-order-content-part1">
                    <input type="hidden" id="ferryDailyFlightGid" value="${ferryInfo.dailyFlightGid}">
                    <input type="hidden" id="ferryNumber" value="${ferryInfo.number}">
                    <input type="hidden" id="ferryFlightLineName" value="${ferryInfo.flightLineName}">
                    <input type="hidden" id="ferryDepartTime" value="${ferryInfo.departTime}">
                    <div class="input-group clearfix">
                        <label><i>航</i><i>线</i>：</label>
                        <span>${ferryInfo.departProt} → ${ferryInfo.arrivePort}</span>
                    </div>
                    <div class="input-group clearfix">
                        <label><i>航</i><i>班</i><i>号</i>：</label>
                        <span>${ferryInfo.number}</span>
                    </div>
                    <div class="input-group clearfix">
                        <label>开航时间：</label>
                        <span>${ferryInfo.departTime}</span>
                    </div>
                    <input type="hidden" id="ticket-type-count" value="${fn:length(ferryInfo.ticketVoList)}">
                    <s:forEach items="${ferryInfo.ticketVoList}" var="ticket" varStatus="status">
                        <input type="hidden" name="ticket_${status.index}" data-name="id" value="${ticket.id}">
                        <input type="hidden" name="ticket_${status.index}" data-name="price" value="${ticket.price}">
                        <input type="hidden" name="ticket_${status.index}" data-name="name" value="${ticket.name}">
                        <input type="hidden" name="ticket_${status.index}" data-name="number" value="${ticket.number}">
                    </s:forEach>

                </div>
                <div class="customer-name">
                    <h5>选择乘客<a onclick="ticketFillOrder.addTourist()">添加游客</a></h5>
                    <div class="customer-name-check clearfix">
                    </div>
                </div>
                <div class="customer-ticket">
                    <h5>选择票型</h5>
                    <ul class="ticket-header clearfix">
                        <li>票型</li>
                        <li>姓名</li>
                        <li>证件类型</li>
                        <li>证件号码</li>
                        <li>手机号</li>
                    </ul>
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
            </div>
            <div class="fill-order-footer-right clearfix">
                <div class="fill-order-cost clearfix">
                    <label>订单总额:</label>
                    <span id="total-span"><sup>￥</sup>0</span>
                </div>
                <a onclick="ticketFillOrder.submitOrder(this)">提交订单</a>
            </div>
        </div>
    </form>
</div>
<%@include file="../../yhypc/public/nav_footer.jsp"%>
</div>
</body>
<script type="text/html" id="tpl-order-ferry-ticket-info">
    <ul class="ticket-body clearfix" id="ticket-customer-{{id}}" data-id="{{id}}">
        <li class="ticket-datalist-wrap">
            <input type="text" name="" value="{{selectedTicket.name}}" readonly class="ticketDatalistBtn-{{id}}">
            <input type="hidden" name="perPrice-{{id}}" value="{{selectedTicket.price}}">
            <dl class="ticket-datalist">
                {{each ticketList as ticket}}
                <dt data-price="{{ticket.price}}" data-id="{{ticket.id}}" data-number="{{ticket.number}}">{{ticket.name}}</dt>
                {{/each}}
            </dl>
        </li>
        <li class="ticket-name">
            <input type="text" value="{{name}}" name="" readonly>
        </li>
        <li class="ticket-card">
            {{if idType=="IDCARD"}}
            <input type="text" value="身份证" name="" readonly>
            {{/if}}
            {{if idType=="STUDENTCARD"}}
            <input type="text" value="学生证" name="" readonly>
            {{/if}}
            {{if idType=="PASSPORT"}}
            <input type="text" value="护照" name="" readonly>
            {{/if}}
            {{if idType=="REMNANTSOLDIER"}}
            <input type="text" value="残军证" name="" readonly>
            {{/if}}
        </li>
        <li class="ticket-card-number">
            <input type="text" value="{{idNumber}}" name="" readonly>
        </li>
        <li class="ticket-tel-number">
            <input type="text" value="{{tel}}" name="" readonly>
        </li>
    </ul>
</script>

<script type="text/html" id="tpl-order-ferry-wirte-customer">
    <div class="checkbox-group clearfix">
        <input type="checkbox" name="customer" id="customer-{{id}}" data-id="{{id}}" onchange="ticketFillOrder.checkCustomer(this)">
        <span class="customer-name-radio"></span>
        <label for="customer-{{id}}">{{name}}</label>
    </div>
</script>
<%@include file="../../yhypc/public/footer.jsp"%>
<script src="/js/public/tourist.js"></script>
<script src="/js/ferry/ferry_fill_order.js"></script>