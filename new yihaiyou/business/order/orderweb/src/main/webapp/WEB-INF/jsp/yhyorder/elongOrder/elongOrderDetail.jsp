<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>录入外接订单</title>
    <link href="/css/yhyorder/order.detail.css" rel="stylesheet">
    <script type="text/javascript" src="/js/order/elongOrder/elongOrderDetail.js"></script>
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

<%--<div id="p" class="easyui-panel" style="width:100%;height:100%;padding:10px;">--%>
    <input id="ipt_id" type="hidden" name="id" value="${order.id}">
    <input id="ipt_detail_length" type="hidden" value="${fn:length(order.orderDetails)}">
    <div id="detail-panel" style="width: 99%; height: 100%">
        <div class="detail-panel">
            <strong class="title">订单信息</strong>
            <table>
                <tr>
                    <td width="80">订单编号:</td>
                    <td class="order-time">${order.orderNo}</td>
                </tr>
                <tr>
                    <td>订单金额:</td>
                    <td class="order-price">${order.price}</td>
                </tr>
                <tr>
                    <td>下单时间:</td>
                    <td class="order-time">${order.createTime}</td>
                </tr>
                <%--<tr>
                    <td>联 系 人：</td>
                    <td class="name">
                        <c:choose>
                            <c:when test="${order.recName != null}">
                                ${order.recName}
                            </c:when>
                            <c:otherwise>无</c:otherwise>
                        </c:choose>
                    </td>
                </tr>--%>
                <tr>
                    <td>联系电话：</td>
                    <td class="mobile">
                        <c:choose>
                            <c:when test="${order.mobile != null}">
                                ${order.mobile}
                            </c:when>
                            <c:otherwise>无</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
        </div>
        <c:forEach items="${order.orderDetails}" var="detail">
            <div class="detail-panel">
                <strong class="title">
                    艺龙酒店预定信息
                </strong>
                <table id="order-detail-table-a" class="order-detail-table mt15">
                    <thead>
                    <tr>
                        <td>详情ID</td>
                        <td>预定产品</td>
                        <td>价格类型</td>
                        <td>使用日期</td>
                        <td>价格(元)</td>
                        <td>预订人数</td>
                            <%--<td>单房差(元)</td>--%>
                            <%--<td>优惠券优惠(元)</td>--%>
                            <%--<td>其他优惠(元)</td>--%>
                        <td>状态</td>
                        <td>接口订单ID</td>
                        <td>预定结果</td>
                        <td>操作</td>
                        <td> 订单日志</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="">${detail.id}</td>
                        <td class="">
                            <div class="product cf">
                                <div class="product-title"><span>${detail.product.name}</span></div>
                            </div>
                        </td>
                        <td class="price-type">${detail.seatType}</td>
                        <td class="play-date" id="playDate">${detail.playDate}</td>
                        <td class="price">${detail.unitPrice}</td>
                        <td class="order-count">${detail.num}</td>
                            <%--<td class="single_room_price">--%>
                            <%--<c:if test="${detail.singleRoomPrice != null}">--%>
                            <%--${detail.singleRoomPrice}--%>
                            <%--</c:if>--%>
                            <%--</td>--%>
                            <%--<td class="prom-discount">${detail.promDiscount}</td>--%>
                            <%--<td class="other-discount">0</td>--%>
                        <td class="detail-status">
                                ${detail.status.description}
                        </td>
                        <td class="detail-realOrderId">
                            <c:choose>
                                <c:when test="${detail.realOrderId != null}">
                                    ${detail.realOrderId}
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="detail-apiResult">
                            <c:choose>
                                <c:when test="${detail.apiResult != null}">
                                    ${detail.apiResult}
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="detail-opt" style="width: 80px;">
                            <c:choose>
                                <c:when test="${detail.status == 'SUCCESS'}">
                                    <a javascript:void(0); style="color:#0000ff" onclick="CliendOrderDetail.doRefundOtaElong(${detail.id})">退款</a>
                                </c:when>
                                <%--没涉及金额的就统一用CANCELED--%>
                                <%--有涉及金额就是REFUNDED--%>
                                <c:when test="${detail.status == 'FAILED' || detail.status == 'PAYED'}">
                                    <a javascript:void(0); style="color:#0000ff" onclick="CliendOrderDetail.doFailOrderRefund(${detail.id})">退款</a>
                                </c:when>
                                <c:when test="${detail.status == 'WAITING'}">
                                    <a javascript:void(0); style="color:#0000ff" onclick="CliendOrderDetail.doCancel(${detail.id})">交易关闭</a>
                                </c:when>
                                <c:otherwise>无</c:otherwise>
                            </c:choose>
                        </td>
                        <td class="log">
                            <a href='javascript:;' onclick='CliendOrderDetail.getLog(${order.id}, ${detail.id})'>查看日志</a>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <div style="margin-top: 10px;">
                    <strong class="title">入住人信息</strong>
                    <table class="order-detail-table mt15">
                        <thead>
                        <tr>
                            <td>姓名</td>
                            <td>性别</td>
                            <td>电话</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${detail.orderTouristList != null && fn:length(detail.orderTouristList) > 0}">
                            <c:forEach items="${detail.orderTouristList}" var="orderTourist">
                                <tr>
                                    <td>${orderTourist.name}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${orderTourist.gender == 'male'}">男</c:when>
                                            <c:when test="${orderTourist.gender == 'female'}">女</c:when>
                                            <c:otherwise>保密</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${orderTourist.tel != null}">
                                                ${orderTourist.tel}
                                            </c:when>
                                            <c:otherwise>无</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${detail.orderTouristList == null || fn:length(detail.orderTouristList) <= 0}">
                            <tr>
                                <td colspan="3">暂无游客信息</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:forEach>
    </div>
<%--</div>--%>

<%--订单日志框--%>
<div class="easyui-dialog easyui-layout panel-scroll" id="log-panel" closed="true"
     style="width:650px;height: 500px;overflow-y: auto;">
    <div data-options="region:'center',border:false">
        <table id="logDg"></table>
    </div>
</div>

</body>
</html>
