<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" href="/css/personal/personal_Hotelorder.css">
	<title>民宿订单详情-一海游</title>
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
                        <c:when test="${orderAllVo.status == '已入住'}">
                            <div class="progress_pic progress_pic2">
                                <span class="submited">提交订单</span>
                                <span class="paying">处理中</span>
                                <span class="over">已入住</span>
                            </div>
                        </c:when>
                        <c:when test="${orderAllVo.status == '已退房'}">
                            <div class="progress_pic progress_pic2">
                                <span class="submited">提交订单</span>
                                <span class="paying">处理中</span>
                                <span class="over">已退房</span>
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
						<span>酒店名称</span>
						<span>下单时间</span>
						<span class="waitpay">订单状态</span>
						<span class="mess_pri">订单总价</span>
					</div>
					<div class="order_mess messcontain clearfix">
						<span class="">${orderAllVo.orderNo}</span>
						<span class="" title="${orderAllVo.fullName}">${orderAllVo.name}</span>
						<span class="">${orderAllVo.createTime}</span>
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
										<div class="paybtn" id="diypay" data-pay-url="/yhypc/order/orderHotelPay.jhtml?orderId=${orderAllVo.id}">立即支付</div>
									</div>
									<div class="pay_over" style="display:none">
										<div class="overlogo"></div>
										<p>
											<span class="paytit">已支付：</span>
											<span class="paycount">¥<label>${orderAllVo.price}</label></span>
										</p>
									</div>
								</c:when>
								<c:when test="${orderAllVo.status == '预订成功' || orderAllVo.status == '已入住' || orderAllVo.status == '已退房'}">
									<div class="pay_commit" style="">
										<div class="overlogo commitlogo"></div>
										<div class="commitBtn commitComment" <c:if test="${orderAllVo.hasComment}">style="display: none;"</c:if> data-name="${orderAllVo.details[0].name}" data-cover="${orderAllVo.details[0].cover}" data-pro-id="${orderAllVo.details[0].proId}" data-price-id="${orderAllVo.details[0].priceId}" data-pro-type="hotel" data-detail-id="${orderAllVo.details[0].id}">我要点评</div>
										<div class="commitBtn lookComment" <c:if test="${!orderAllVo.hasComment}">style="display: none;"</c:if> data-detail-id="${orderAllVo.details[0].id}">查看点评</div>
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
							<p class="listtitle"><i></i>酒店民宿订单信息</p>
							<p class="product"></p>
							<div class="hotelmes clearfix">
								<div class="le">
									<p><span class="l_title">房型名称：</span>${orderAllVo.seatType}</p>
									<p><span class="l_title">入住日期：</span>${orderAllVo.playDate}至${orderAllVo.leaveDate}（${orderAllVo.day}晚）</p>
									<p class="price"><span class="l_title">单价：</span>¥${orderAllVo.unitPrice}</p>
                                    <div class="l_title">入住人:</div>
                                    <div class="tourist_list">
                                        <c:if test="${orderAllVo.touristVos.size() <= 0}"><p>-</p></c:if>
                                        <c:forEach items="${orderAllVo.touristVos}" var="tourist"><p>${tourist.name}<i class="i"></i>${tourist.type}<i class="i"></i>${tourist.tel}</p></c:forEach>
                                    </div>
								</div>
								<div class="ri">
									<p><span class="l_title">房间数量：</span>${orderAllVo.num}间</p>
									<c:if test="${orderAllVo.status == '预订成功' || orderAllVo.status == '已入住' || orderAllVo.status == '已退房'}">
                                        <div class="l_title">验证码:</div>
                                        <div class="pvc_list">
                                            <c:if test="${orderAllVo.pvCodeVos.size() <= 0}"><p>-</p></c:if>
                                            <c:forEach items="${orderAllVo.pvCodeVos}" var="pvc"><p>${pvc.code}&nbsp;(${pvc.status})</p></c:forEach>
                                        </div>
                                    </c:if>
									<c:if test="${orderAllVo.status == '已退款'}">
                                        <div class="l_title">验证码:</div>
                                        <div class="pvc_list">
                                            <c:if test="${orderAllVo.pvCodeVos.size() <= 0}"><p>-</p></c:if>
                                            <c:forEach items="${orderAllVo.pvCodeVos}" var="pvc"><p>${pvc.code}&nbsp;(无效)</p></c:forEach>
                                        </div>
									</c:if>
								</div>
							</div>
						</div>
						<%--<div class="order_part">--%>
							<%--<p class="listtitle"><i></i>预订人信息</p>--%>
							<%--<p class="product"></p>--%>
							<%--<div class="hotelmes clearfix">--%>
								<%--<div class="le">--%>
									<%--<p><span class="l_title">姓名：</span>${orderAllVo.userName}</p>--%>
									<%--<p><span class="l_title">手机：</span>${orderAllVo.mobile}</p>--%>
								<%--</div>--%>
							<%--</div>--%>
						<%--</div>--%>
					</div>
				</div>
			</div>
			<div class="shadow"></div>
			<div class="commentContain" id="commitComment">
				<div class="com_header">
					<span class="commentType"></span>评价
					<span class="closebtn com_close"></span>
				</div>
				<div class="com_body">
					<div class="maincontain">
						<div class="contain_top clearfix">
							<div class="pic"><img src="" class="commentCover"></div>
							<div class="pic_mess">
								<p class="mess_name"></p>
								<div class="mess_score">
									<span class="score_le">评分：</span>
									<span class="starnum clearfix" data-star="0">
										<span></span><span></span><span></span><span></span><span></span>
									</span>
								</div>
							</div>
						</div>
						<div class="contain_mid">
							<textarea class="commentContent"></textarea>
						</div>
						<div class="com_submit">提交评价</div>
					</div>
				</div>
			</div>
			<div class="commentContain" id="lookComment" style="height: 370px;">
				<div class="com_header">
					<span class="commentType"></span>评价
					<span class="closebtn com_close"></span>
				</div>
				<div class="com_body">
					<div class="maincontain">
						<div class="contain_top clearfix">
							<div class="pic"><img src="" class="commentCover"></div>
							<div class="pic_mess">
								<p class="mess_name"></p>
								<div class="mess_score">
									<span class="score_le">评分：</span>
									<span class="starnum clearfix" data-star="0">
										<span></span><span></span><span></span><span></span><span></span>
									</span>
								</div>
							</div>
						</div>
						<div class="contain_mid">
							<textarea class="commentContent" disabled></textarea>
						</div>
					</div>
				</div>
			</div>
			<div class="comsuccess">
				<div class="com_header suss_header">
					<span class="closebtn" id="suss_close"></span>
				</div>
				<div class="suss_body">点评成功！</div>
				<%--<div class="suss_footer">--%>
				<%--<span class="turnIndex">返回首页</span>--%>
				<%--<span class="checkOrder">查看点评</span>--%>
				<%--</div>--%>
			</div>
		</div>
        <%@include file="../../yhypc/public/nav_footer.jsp"%>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/personal/personal_Hotelorder.js"></script>
<script type="text/javascript" src="/js/public/comment.js"></script>
</html>