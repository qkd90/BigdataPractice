<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2017-02-09,0009
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../../yhypc/public/header.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/order/hotelOrder.css">
    <title>行程支付</title>
</head>
<body>
<div class="hotelOrder hotelIndex">
    <%@include file="../../yhypc/public/order_header.jsp" %>
    <input type="hidden" id="waitSeconds" value=1800>
    <div class="body_order">
        <div class="progress progress2">
            <span>填写订单</span><span class="active">在线支付</span><span>订单完成</span>
        </div>
        <div class="orderPay clearfix ">
            <div class="payTop">
                <div class="picture"></div>
                <div class="order_mess">
                    <h3>${order.scenicName}</h3>
                    <p>
                        <span class="l_title">景点名称：</span>
                        <span class="s_fir"></span>
                        <span class="date">${lvjiOrder.scenicName}</span>
                        <%--<span class="week">星期六（Sat）</span>--%>

                        <span class="roomNum"><label>购买数量：</label>1</span>
							<span class="total ferrypay">订单总额：
								<span class="rmb">¥</span><span class="num">${lvjiOrder.price}</span>
							</span>
                    </p>
                    <%--<p>--%>
                        <%--<span class="l_title">航班号：</span>--%>
                        <%--<span class="date">${order.flightNumber}</span>--%>
                        <%--<span class="host ferryhost"><label>乘客：</label><c:forEach items="${order.ferryOrderItemList}" var="orderItem">${orderItem.name}</c:forEach></span>--%>
                    <%--</p>--%>
                    <p class="order_timer">： 建议您在<span id="waitTimeStr">${lvjiOrder.waitTime}</span>内完成付款，过期订单会自动取消哦</p>
                </div>
            </div>
        </div>
        <input type="hidden" id="orderId" value="${lvjiOrder.id}">
        <div class="payBox">
            <div class="recharge">
	<span class="total">用户余额：
		<span class="rmb">¥</span><span class="num" id="balance" data-balance="<fmt:formatNumber value="${user.balance}" pattern="###.##"/>"><fmt:formatNumber value="${user.balance}" pattern="###.##"/></span>
	</span>
                <%--<span class="recha">充值</span>--%>
                <span class="balance_disable">去付款</span>
            </div>
            <div class="payList">
                <ul class="clearfix">
                    <li class="wechat pay_active wechatActive">微信支付</li>
                    <li class="zhifubao">支付宝支付</li>
                    <li class="bank t_indent">银行卡支付</li>
                    <li class="blank"></li>
                </ul>
            </div>
            <div class="payContain wechatPay active payferry">
                <p class="img" id="wechat_qrcode"></p>
                <p class="paystyle">微信支付</p>
                <p class="at_word">提示：点击“确认支付”后，请打开手机微信的“扫一扫”，扫描二维码</p>
                <div class="bypicture"></div>
                <div class="pre_nex">
                    <span class="prev"><a>取消订单</a></span>
                    <span class="next"><a>确认支付</a></span>
                </div>
            </div>
            <div class="payContain zhifubaopay payferry">
                <p class="img" id="zhifubao_qrcode"></p>
                <p class="paystyle">支付宝支付</p>
                <p class="at_word">提示：点击“确认支付”后，请打开手机支付宝的“扫一扫”，扫描二维码</p>
                <div class="bypicture"></div>
                <div class="pre_nex">
                    <span class="prev"><a>取消订单</a></span>
                    <span class="next"><a>确认支付</a></span>
                </div>
            </div>
            <div class="payContain bankpay payferry">
                <p class="img"></p>
                <p class="paystyle">银行卡支付</p>
                <p class="at_word">提示：点击“确认支付”后，填写银行卡号等信息</p>
                <div class="bypicture"></div>
                <div class="pre_nex">
                    <span class="prev"><a>取消订单</a></span>
                    <span class="next"><a>确认支付</a></span>
                </div>
            </div>
        </div>
    </div>
    <div class="windowPayShadow"></div>
    <div class="payPasswordBox">
        <span class="closebtn"></span>
        <h3>支付密码</h3>
        <div class="attenContain">
            <input class="paykey" type="password">
        </div>
        <div class="btn">确认</div>
    </div>
    <div class="payPassConfirm">
        <span class="closebtn"></span>
        <h3>提示</h3>
        <div class="attenContain"><p>还未设置支付密码，是否前往设置？</p></div>
        <div class="btn_double"><a href="/yhypc/personal/myWallet.jhtml?fn=openSetPayPwdBox" target="_blank" class="yes">确认</a><span class="no">取消</span></div>
    </div>
    <%@include file="../../yhypc/public/nav_footer.jsp" %>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp" %>
<script type="text/javascript" src="/js/ferry/lvji_order_pay.js"></script>
<script type="text/javascript" src="/lib/jquery/jquery.qrcode.min.js"></script>
</html>