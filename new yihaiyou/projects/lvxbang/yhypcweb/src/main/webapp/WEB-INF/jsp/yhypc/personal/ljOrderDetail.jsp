<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" href="/css/personal/personal_ljOrder.css">
	<title>DIY行程订单详情-一海游</title>
</head>
<body class="judgebottom">
	<div class="hotelOrder">
		<%@include file="../../yhypc/public/nav_header.jsp"%>
		<div class="total">
			<div class="order_progress">
				<div class="po_both progress_left">
                    <c:choose>
                        <c:when test="${response.status == '预订成功'}">
                            <div class="progress_pic progress_pic2">
                                <span class="submited">提交订单</span>
                                <span class="paying">处理中</span>
                                <span class="over">预订成功</span>
                            </div>
                        </c:when>
                        <c:when test="${response.status == '已退款' || response.status == '已取消'}">
                            <div class="progress_text">您的订单${response.status}<c:if test="${response.status == '已退款'}"><label class="p_tip">(订单非余额支付退款到账时间以第三方处理为准)</label></c:if></div>
                        </c:when>
                        <c:otherwise>
                            <div class="progress_pic">
                                <span class="submited">提交订单</span>
                                <span class="paying pro_active">${response.status}</span>
                                <span class="over"></span>
                            </div>
                        </c:otherwise>
                    </c:choose>
					<div class="order_mess clearfix">
						<span>订单号</span>
						<span>下单时间</span>
						<span class="waitpay">订单状态</span>
						<span class="mess_pri">订单总价</span>
					</div>
					<div class="order_mess messcontain clearfix">
						<span class="arial">${response.orderNo}</span>
						<span class="arial">${response.startDate}</span>
						<span class="waitpay">${response.status}</span>
						<span class="mess_pri">¥<label>${response.price}</label></span>
					</div>
				</div>
				<div class="po_both progress_right">
					<c:choose>
                        <c:when test="${response.status == '已支付'}">
                            <div class="pay_over" style="display: block">
                                <div class="overlogo"></div>
                                <p>
                                    <span class="paytit">已支付：</span>
                                    <span class="paycount">¥<label>${response.price}</label></span>
                                </p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${response.status == '待支付'}">
                                    <div class="pay_wait">
                                        <p>
                                            <span class="payinline">在线支付：</span>
                                            <span class="paycount">¥<label>${response.price}</label></span>
                                        </p>
                                        <div class="paybtn" id="diypay" data-pay-url="/yhypc/order/orderPlanPay.jhtml?orderId=${response.id}">立即支付</div>
                                    </div>
                                    <div class="pay_over" style="display:none">
                                        <div class="overlogo"></div>
                                        <p>
                                            <span class="paytit">已支付：</span>
                                            <span class="paycount">¥<label>${response.price}</label></span>
                                        </p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="pay_over" style="display:block">
                                        <div class="overlogo"></div>
                                        <p><span class="paytit">${response.status}</span></p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
				</div>
			</div>
			<div class="orderMessage">
				<div class="Messagelist">
                    <div class="lj-order-title">
                        <i></i>驴记订单信息
                    </div>
					<div class="Messagecontain">
                        <div class="order-group-wrap clearfix">
                            <div class="order-group clearfix">
                                <label>名称：</label>
                                <span>鼓浪屿景区-手机语音导航</span>
                            </div>
                        </div>
                        <div class="order-group-wrap clearfix">
                            <div class="order-group clearfix">
                                <label>票型：</label>
                                <span>${response.recName}景区-手机语音导航</span>
                            </div>
                            <div class="order-group clearfix">
                                <label>单价：</label>
                                <span>￥${response.price}</span>
                            </div>
                        </div>
                        <div class="order-group-wrap clearfix">
                            <div class="order-group clearfix">
                                <label>时间：</label>
                                <span>${response.startDate}</span>
                            </div>
                            <div class="order-group clearfix">
                                <label>总价：</label>
                                <span class="orange">￥${response.price}</span>
                            </div>
                        </div>


						<%--<div class="order_part">--%>
							<%--<p class="listtitle"><i></i>预订人信息</p>--%>
							<%--<div class="hotelmes clearfix">--%>
								<%--<div class="le">--%>
									<%--<p><span class="l_title">姓名：</span>${response.userName}</p>--%>
									<%--<p><span class="l_title">手机：</span>${response.mobile}</p>--%>
								<%--</div>--%>
							<%--</div>--%>
						<%--</div>--%>
					</div>
				</div>
			</div>
		</div>
        <%@include file="../../yhypc/public/nav_footer.jsp"%>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/personal/personal_DIYorder.js"></script>
<script type="text/javascript" src="/js/public/comment.js"></script>
</html>