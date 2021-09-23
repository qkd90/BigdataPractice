<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" href="/css/personal/personal_DIYorder.css">
	<title>DIY行程订单详情-一海游</title>
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
                                        <div class="paybtn" id="diypay" data-pay-url="/yhypc/order/orderPlanPay.jhtml?orderId=${orderAllVo.id}">立即支付</div>
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
					<h3>订单信息<span class="bottom"></span></h3>
					<div class="Messagecontain">
                        <c:if test="${ferryOrderAllVos.size() > 0}">
						<div class="order_part">
							<p class="listtitle"><i></i>船票</p>
                            <c:forEach items="${ferryOrderAllVos}" var="fvo">
                                <p class="product">${fvo.name}</p>
                                <div class="boatticket">
                                    <ul class="clearfix">
                                        <li><span>航班：</span>${fvo.seatType}</li>
                                        <li><span style="float:left;">单价：</span>
                                            <div style="float:left;"><c:forEach items="${fvo.details}" var="detail"><p>${detail.seatType} ${detail.num}张</p></c:forEach></div>
                                        </li>
                                        <li><span>时间：</span>${fvo.startDate}</li>
                                        <li class="price"><span>总价：</span>¥<label>${fvo.price}</label></li>
                                    </ul>
                                </div>
                            </c:forEach>
						</div>
                        </c:if>
                        <c:if test="${hotelOrderAllVoMap.size() > 0}">
						<div class="order_part">
							<p class="listtitle"><i></i>酒店</p>
                            <c:forEach items="${hotelOrderAllVoMap}" var="hvo">
                                <p class="product">${hvo.value.name}</p>
                                <div class="hotelmes clearfix">
                                    <div class="le">
                                        <p><span class="l_title">时间：</span>${hvo.value.playDate}</p>
                                        <div class="l_title">游客：</div>
                                        <div class="tourist_list">
                                            <c:forEach items="${hvo.value.touristVos}" var="tourist"><p>${tourist.name} / ${tourist.type} / ${tourist.tel}</p></c:forEach>
                                        </div>
                                        <p class="price"><span class="l_title">总价：</span>¥<label>${hvo.value.price}</label></p>
                                    </div>
                                    <div class="ri">
                                        <p>
                                            <span class="l_title">房型：</span>
                                            <c:forEach items="${hvo.value.details}" var="detail"> <span class="roommess">${detail.seatType}<label>/</label>${detail.day}晚${detail.num}间<label>/</label><label class="rmb">¥${detail.price}</label>
                                                <c:if test="${detail.status == '预订成功' || detail.status == '已入住' || detail.status == '已退房'}">
                                            <label class="commitBtn commitComment" <c:if test="${detail.hasComment}">style="display: none;"</c:if> data-name="${detail.name}" data-cover="${detail.cover}" data-pro-id="${detail.proId}" data-price-id="${detail.priceId}" data-pro-type="${detail.proType}" data-detail-id="${detail.id}">我要点评</label>
								            <label class="commitBtn lookComment" <c:if test="${!detail.hasComment}">style="display: none;"</c:if> data-detail-id="${detail.id}">查看点评</label>
                                                </c:if>
                                            </span></c:forEach>
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>
						</div>
                        </c:if>
                        <c:if test="${ticketOrderAllVoMap.size() > 0}">
						<div class="order_part">
							<p class="listtitle"><i></i>门票</p>
                            <c:forEach items="${ticketOrderAllVoMap}" var="tvo">
                                <p class="product">${tvo.value.name}</p>
                                <div class="hotelmes clearfix">
                                    <div class="le">
                                        <p><span class="l_title">出发日期：</span>${tvo.value.playDate}</p>
                                        <div class="l_title">游客：</div>
                                        <div class="tourist_list">
                                            <c:forEach items="${tvo.value.touristVos}" var="tourist"><p>${tourist.name} / ${tourist.type} / ${tourist.tel}</p></c:forEach>
                                        </div>
                                        <p class="price"><span class="l_title">总价：</span>¥<label>${tvo.value.price}</label></p>
                                    </div>
                                    <div class="ri">
                                        <p>
                                            <span class="l_title">票型：</span>
                                            <c:forEach items="${tvo.value.details}" var="detail">
                                                <span class="roommess">${detail.seatType}<label>/</label>${detail.num}张<label>/</label><label class="rmb">¥${detail.price}</label>
                                                    <c:if test="${detail.status == '预订成功'}">
                                                <label class="commitBtn commitComment" <c:if test="${detail.hasComment}">style="display: none;"</c:if> data-name="${detail.name}" data-cover="${detail.cover}" data-pro-id="${detail.proId}" data-price-id="${detail.priceId}" data-pro-type="${detail.proType}" data-detail-id="${detail.id}">我要点评</label>
								                <label class="commitBtn lookComment" <c:if test="${!detail.hasComment}">style="display: none;"</c:if> data-detail-id="${detail.id}">查看点评</label>
                                                    </c:if>
                                                </span>
                                            </c:forEach>
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>
						</div>
                        </c:if>
						<%--<div class="order_part">--%>
							<%--<p class="listtitle"><i></i>预订人信息</p>--%>
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
<script type="text/javascript" src="/js/personal/personal_DIYorder.js"></script>
<script type="text/javascript" src="/js/public/comment.js"></script>
</html>