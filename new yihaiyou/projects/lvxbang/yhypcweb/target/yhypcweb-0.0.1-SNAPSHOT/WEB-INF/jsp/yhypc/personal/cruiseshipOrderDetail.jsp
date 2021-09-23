<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="/css/personal/personal_Cruiseorder.css">
	<title>邮轮订单详情-一海游</title>
</head>
<body class="judgebottom">
	<div class="hotelOrder">
        <%@include file="../../yhypc/public/nav_header.jsp"%>
		<div class="total">
			<div class="order_progress">
				<div class="po_both progress_left">
					<c:choose>
						<c:when test="${orderAllVo.status == '预订成功'}">
							<div class="progress_pic progress_pic2">
								<span class="submited">提交订单</span>
								<span class="paying">处理中</span>
								<span class="over">预订成功</span>
							</div>
						</c:when>
						<c:when test="${orderAllVo.status == '已退款' || orderAllVo.status == '已取消'}">
							<div class="progress_text">您的订单${orderAllVo.status}<c:if test="${orderAllVo.status == '已退款'}"><label class="p_tip">(订单非余额支付退款到账时间以第三方处理为准)</label></c:if></div>
						</c:when>
						<c:otherwise>
							<div class="progress_pic">
								<span class="submited">提交订单</span>
								<span class="paying pro_active">${orderAllVo.status}</span>
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
						<span class="arial">${orderAllVo.orderNo}</span>
						<span class="arial">${orderAllVo.createTime}</span>
						<span class="waitpay">${orderAllVo.status}</span>
						<span class="mess_pri">¥<label>${orderAllVo.price}</label></span>
					</div>
				</div>
				<div class="po_both progress_right">
                    <c:choose>
                        <c:when test="${orderAllVo.status == '已支付'}">
							<div class="pay_over" style="display: block">
								<div class="overlogo"></div>
								<p>
									<span class="paytit">已支付：</span>
									<span class="paycount">¥<label>${orderAllVo.price}</label></span>
								</p>
							</div>
                        </c:when>
                        <c:otherwise>
							<c:choose>
								<c:when test="${orderAllVo.status == '待支付'}">
									<div class="pay_wait">
										<p>
											<span class="payinline">在线支付：</span>
											<span class="paycount">¥<label>${orderAllVo.price}</label></span>
										</p>
										<div class="paybtn" id="diypay" data-pay-url="/yhypc/cruiseShip/index.jhtml">立即支付</div>
									</div>
									<div class="pay_over" style="display:none">
										<div class="overlogo"></div>
										<p>
											<span class="paytit">已支付：</span>
											<span class="paycount">¥<label>${orderAllVo.price}</label></span>
										</p>
									</div>
								</c:when>
								<c:otherwise>
									<div class="pay_over" style="display:block">
										<div class="overlogo"></div>
										<p><span class="paytit">${orderAllVo.status}</span></p>
									</div>
								</c:otherwise>
							</c:choose>
                        </c:otherwise>
                    </c:choose>
				</div>
			</div>
			<div class="orderMessage">
				<div class="Messagelist">
					<div class="Messagecontain">
						<div class="order_part">
							<p class="listtitle"><i></i>邮轮订单信息</p>
							<p class="product"></p>
							<div class="hotelmes clearfix">
								<div class="le">
									<p><span class="l_title">产品名称：</span>${orderAllVo.name}</p>
									<p><span class="l_title">舱房：</span>${orderAllVo.seatType}</p>
									<p><span class="l_title">出发日期：</span>${orderAllVo.playDate}</p>
								</div>
								<div class="ri">
									<p><span class="l_title">数量：</span>${orderAllVo.num}张</p>
									<p class="price"><span class="l_title">单价：</span>¥${orderAllVo.price}</p>
								</div>
							</div>
						</div>
						<div class="order_part">
							<p class="listtitle"><i></i>预订人信息</p>
							<p class="product"></p>
							<div class="hotelmes clearfix">
								<div class="le">
									<p><span class="l_title">姓名：</span>${orderAllVo.userName}</p>
									<p><span class="l_title">手机：</span>${orderAllVo.mobile}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
        <%@include file="../../yhypc/public/nav_footer.jsp"%>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/personal/personal_Cruiseorder.js"></script>
</html>