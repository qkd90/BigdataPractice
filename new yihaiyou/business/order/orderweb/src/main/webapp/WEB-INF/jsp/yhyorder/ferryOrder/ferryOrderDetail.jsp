<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/2/23
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp"%>
    <title>轮渡船票订单</title>
    <script type="text/javascript" src="/js/yhyorder/ferry/ferryOrderDetail.js"></script>
    <link href="/css/yhyorder/order.detail.css" rel="stylesheet">
</head>
<style type="text/css">
    body {
        font-size: 14px;
        padding: 0px;
        background-color: white;
    }
    .labelName {
        font-size: 14px;
    }
    .labelValue {
        font-size: 14px;
    }
</style>
<body>
<div id="detail-panel" style="width: 99%; height: 100%">
    <div class="detail-panel">
        <strong class="title">订单信息</strong>
        <table>
            <tr>
                <td width="80">订单编号:</td>
                <td class="order-time">${ferryOrder.orderNumber}</td>
            </tr>
            <tr>
                <td>轮渡单号:</td>
                <td class="order-price">${ferryOrder.ferryNumber}</td>
            </tr>
            <tr>
                <td>航班号:</td>
                <td class="order-price">${ferryOrder.flightNumber}</td>
            </tr>
            <tr>
                <td>航线名称:</td>
                <td class="order-price">${ferryOrder.flightLineName}</td>
            </tr>
            <tr>
                <td>发班时间:</td>
                <td class="order-price">${ferryOrder.departTime}</td>
            </tr>
            <tr>
                <td>订单金额:</td>
                <td class="order-price">${ferryOrder.amount}</td>
            </tr>
            <tr>
                <td>状态:</td>
                <td class="order-price">${ferryOrder.status.description}</td>
            </tr>
            <s:if test="ferryOrder.status == @com.data.data.hmly.service.order.entity.enums.OrderStatus@SUCCESS">
            <tr>
                <td>操作:</td>
                <td class="order-price">
                    <a javascript:void(0); style="color:#0000ff" onclick="ferryOrderDetail.doDefundFerry(${ferryOrder.id})">申请退款</a>
                </td>
            </tr>
            </s:if>
            <s:elseif test="ferryOrder.status == @com.data.data.hmly.service.order.entity.enums.OrderStatus@FAILED || ferryOrder.status == @com.data.data.hmly.service.order.entity.enums.OrderStatus@PAYED">
            <tr>
                <td>操作:</td>
                <td class="order-price">
                    <a javascript:void(0); style="color:#0000ff" onclick="ferryOrderDetail.doFailOrderRefundFerry(${ferryOrder.id})">退款</a>
                </td>
            </tr>
            </s:elseif>
            <s:elseif test="ferryOrder.status == @com.data.data.hmly.service.order.entity.enums.OrderStatus@CANCELING || ferryOrder.status == @com.data.data.hmly.service.order.entity.enums.OrderStatus@REFUND">
            <tr>
                <td>退款金额:</td>
                <td class="order-price">${ferryOrder.returnAmount}</td>
            </tr>
            <tr>
                <td>退款手续费:</td>
                <td class="order-price">${ferryOrder.poundageAmount}</td>
            </tr>
            <tr>
                <td>退款说明:</td>
                <td class="order-price">${ferryOrder.poundageDescribe}</td>
            </tr>
            </s:elseif>
        </table>
    </div>

    <div class="detail-panel">
        <strong class="title">船票明细</strong>
        <table class="order-detail-table mt15">
            <thead>
            <tr>
                <td>票类名称</td>
                <td>价格</td>
                <td>门票编号</td>
                <td>证件类型</td>
                <td>姓名</td>
                <td>身份证号码</td>
                <td>手机号码</td>
            </tr>
            </thead>
            <tbody>
            <s:if test="ferryOrder.ferryOrderItemList.size > 0">
                <s:iterator value="ferryOrder.ferryOrderItemList" var="ferryOrderItem" status="stat">
                    <td><s:property value="#ferryOrderItem.ticketName"/> </td>
                    <td><s:property value="#ferryOrderItem.price"/> </td>
                    <td><s:property value="#ferryOrderItem.number"/> </td>
                    <td><s:property value="#ferryOrderItem.idTypeName.description"/></td>
                    <td><s:property value="#ferryOrderItem.name"/> </td>
                    <td><s:property value="#ferryOrderItem.idnumber"/> </td>
                    <td><s:property value="#ferryOrderItem.mobile"/> </td>
                    </tr>
                </s:iterator>
            </s:if>
            <s:else>
                <tr>
                    <td colspan="7">暂无信息</td>
                </tr>
            </s:else>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
