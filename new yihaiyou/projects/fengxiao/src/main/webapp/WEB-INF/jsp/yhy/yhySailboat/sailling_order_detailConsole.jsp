<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" href="/css/yhy/yhySailboat/sailling_order_detailConsole.css">
    <title>海上休闲/订单详情</title>
</head>
<body class="sailOrder includeTable">
    <%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="roomset">
		<div class="orderDetail">
			<div class="detail_header clearfix">
				<span class="detail_title">订单详情</span>
				<span class="reTurn" onclick="SailboatOrderDetailCancel.goback()">返回</span>
				<input type="hidden" id="orderId" value="${orderId}"><input type="hidden" id="orderDetailId" value="${orderDetailId}">
			</div>
			<ul class="order_detailUl">
				<li>订单号：<span model="orderNo">${orderDetail.order.orderNo}</span></li>
				<li>订单状态：<span class="orderState" model="statusStr">${orderDetail.status.description}</span></li>
				<li>取票人：<span model="recName">${orderDetail.order.recName}</span></li>
				<li>取票人手机号：<span model="mobile">${orderDetail.order.mobile}</span></li>
				<s:if test="orderDetail.orderTouristList != null && !orderDetail.orderTouristList.isEmpty() > 0">
					<li><span >游客信息</span>：
						<table style="margin-top: -23px; margin-left: 75px;">
							<c:forEach items="${orderDetail.orderTouristList}" var="orderTourist">
								<tr>
									<td style="padding-right: 15px;">${orderTourist.name}</td>
									<td class="idNumber">${orderTourist.idNumber}</td>
								</tr>
							</c:forEach>
						</table>
					</li>
				</s:if>
				<li>下单时间：<span model="createTime"><s:date name="orderDetail.order.createTime" format="yyyy-MM-dd HH:mm:ss"/></span></li>
				<li>单价：¥<span class="orderPrice" model="perPrice">${orderDetail.unitPrice}</span></li>
				<li>张数：<span model="count">${orderDetail.num}</span></li>
				<li>订单总价：¥<span class="orderPrice" model="price">${orderDetail.order.price}</span></li>
			</ul>
		</div>
		<div class="orderDetail orderDetailTop">
			<table class="table table-striped">
				<tr class="headTr">
					<th class="productMess productMessTi">产品信息</th>
					<th class="reason">拒绝理由</th>
					<th class="finalPrice">结算价（元）</th>
					<th class="sailPrice">销售价（元）</th>
					<th class="operator">操作人</th>
				</tr>
				<%--<tr class="headTr headTr_sec">
					<td class="productMess">房型：豪华海景房</td>
					<td class="reason">巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉</td>
					<td class="finalPrice">160</td>
					<td class="sailPrice">160</td>
					<td class="operator">张小虎</td>
				</tr>--%>
			</table>
		</div>
	</div>
    <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailboat_order_detail_cancel.js"></script>
</html>