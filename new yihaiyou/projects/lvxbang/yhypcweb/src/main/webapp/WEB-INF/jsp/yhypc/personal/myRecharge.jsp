<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
	<link rel="stylesheet" href="/css/personal/personal_Recharge.css">
	<title>充值-一海游</title>
</head>
<body data-page-obj="MyRecharge" data-page-class="pc-mywallet" class="judgebottom">
	<div class="hotelIndex">
		<%@include file="../../yhypc/public/nav_header.jsp"%>
		<div style="background:#f3f8fe">
			<%@include file="../../yhypc/public/user_head.jsp"%>
			<div class="mainBox">
				<div class="recharge_count">
					<label>充值金额</label>
					<input type="text" id="rechargeIpt" placeholder="请输入充值金额" onkeyup="value=value.replace(/[^\d.]/g,'')">
				</div>
				<div class="payList">
					<ul class="clearfix">
						<li class="wechat pay_active wechatActive">微信支付</li>
						<li class="zhifubao">支付宝支付</li>
						<li class="blank"></li>
					</ul>
				</div>
				<div class="payContain wechatPay active">
					<p class="img" id="wechat_qrcode"></p>
					<p class="paystyle">微信支付</p>
					<p class="at_word">提示：点击“下一步”后，请打开手机微信的“扫一扫”，扫描二维码</p>
					<div class="bypicture"></div>
					<span class="next"><a>下一步</a></span>
					<%--<p>返回重新修改</p>--%>
				</div>
				<div class="payContain zhifubaopay">
					<p class="img" id="zhifubao_qrcode"></p>
					<p class="paystyle">支付宝支付</p>
					<p class="at_word">提示：点击“下一步”后，请打开手机支付宝的“扫一扫”，扫描二维码</p>
					<div class="bypicture"></div>
					<span class="next"><a>下一步</a></span>
					<%--<p>返回重新修改</p>--%>
				</div>
			</div>
			<%@include file="../../yhypc/public/nav_footer.jsp"%>
		</div>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/personal/personal_Recharge.js"></script>
<script type="text/javascript" src="/lib/jquery/jquery.qrcode.min.js"></script>
</html>