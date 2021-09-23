<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../../common/yhyheader.jsp" %>
	<link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay_order_detailConsole.css">
	<title>民宿/订单处理</title>
</head>
<body class="homestayOrder includeTable">
	<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<input type="hidden" id="orderId" value="${orderId}"><input type="hidden" id="detailId" value="${detailId}">
	<div class="roomset">
		<div class="orderDetail">
			<div class="detail_header clearfix">
				<span class="detail_title">订单详情</span>
				<span class="Return" onclick="HotelOrderDetailCancel.goback()">返回</span>
			</div>
			<ul class="order_detailUl">
				<li>订单号：<span model="orderNo">${orderDetail.order.orderNo}</span></li>
				<li>订单状态：<span class="orderState" data-status="${orderDetail.status}">${orderDetail.status.description}</span></li>
				<%--<li>预订人：<span model="recName">${orderDetail.order.recName}</span></li>--%>
				<li>下单时间：<span model="createTime"><s:date name="orderDetail.order.createTime" format="yyyy-MM-dd HH:mm:ss"/></span></li>
				<li>入住日期：<span model="playTimeStr"><s:date name="orderDetail.playDate" format="yyyy-MM-dd"/></span></li>
				<li>离店日期：<span model="leaveTimeStr"><s:date name="orderDetail.leaveDate" format="yyyy-MM-dd"/></span></li>
				<s:if test="orderDetail.orderTouristList != null && !orderDetail.orderTouristList.isEmpty() > 0">
					<li><span >入住人信息</span>：
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
				<li>联系电话：<span model="mobile">${orderDetail.order.mobile}</span></li>
				<li>间数：<span model="count">${orderDetail.num}</span></li>
				<li>单价：¥<span class="orderPrice" model="perPrice">${orderDetail.unitPrice}</span></li>
				<li>订单总价：¥<span class="orderPrice" model="price">${orderDetail.order.price}</span></li>

			</ul>
		</div>
		<div class="orderDetail orderDetailTop">
			<table class="table table-striped">
				<tr class="headTr headTr_fir">
					<th class="productMess productMessTi">房型</th>
					<th class="reason reasonTi">拒绝理由</th>
					<th class="finalPrice">结算价（元）</th>
					<th class="sailPrice">销售价（元）</th>
					<th class="operator">操作人</th>
				</tr>
				<%--<tr class="headTr headTr_sec">
					<td class="productMess">豪华海景房</td>
					<td class="reason">地方结果房价都看过韩国反对韩国进口的换个角度看回家看过很多附加快递费</td>
					<td class="finalPrice">160</td>
					<td class="sailPrice">160</td>
					<td class="operator">张小虎</td>
				</tr>--%>
			</table>
		</div>
	</div>
	<%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
	<%@include file="../../common/yhyfooter.jsp" %>
	<script src="/js/yhy/yhyHotel/homestay.js"></script>
	<script type="text/javascript" src="/js/yhy/yhyHotel/homestay_order_detail_cancel.js"></script>
</body>
</html>