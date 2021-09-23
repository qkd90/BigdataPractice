<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
	<%@include file="../../common/yhyheader.jsp" %>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhySailboat/sailling_check.css">
    <title>海上休闲/验证核销</title>
</head>
<body class="sailCheck includeTable">
<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="secnav">
			<div class="secnav_list">
				<ul class="clearfix">
					<li data-href="/yhy/yhyMain/toSailboatCheck.jhtml" class="HSsec_active">验票</li>
					<li data-href="/yhy/yhyMain/toSailboatCheckRecorde.jhtml">验票记录</li>
				</ul>
			</div>
		</div>
	<div class="checkList roomset">
		<div class="checkBox">
			<div class="centerBox">
				<div class="outInput"><input type="text" placeholder="请输入验证码"></div>
				<div class="checkBtn">验证</div>
			</div>
		</div>
		<div class="checkResult">
			<div class="check_resultTitle"><span>验票结果</span></div>
			<div class="check_resultContain">
				<div class="check_return"></div>
			</div>
			<div class="check_resultTitle"><span>验票情况</span></div>
			<div class="check_state">
				<div class="ticketNum">总票数：<span class="ticket_num true"><span id="totalNum"></span>张</span></div>
				<div class="ticketNum">
					已验：<span class="ticket_num true"><span id="usedNum"></span>张</span>
					<span class="used_div">
					</span>
				</div>
				<div class="ticketNum">未验：<span class="ticket_num true"><span id="unUsedNum"></span>张</span></div>
			</div>
			<div class="check_resultTitle"><span>订单信息</span></div>
			<div class="check_message">
				<ul class="clearfix">
					<li>
						<span class="message_l">票型名称：</span>
						<span class="true"><span model="productTypeName"></span></span>
					</li>
					<li>
						<span class="message_l">门票数量：</span>
						<span class="true"><span model="totalNum"></span></span>
					</li>
					<li>
						<span class="message_l">订单号：</span>
						<span class="true"><span model="orderNo"></span></span>
					</li>
					<li>
						<span class="message_l">订单状态：</span>
						<span class="true"><span model="orderDetailStatus"></span></span>
					</li>
					<li>
						<span class="message_l">取票人：</span>
						<span class="true"><span model="buyerName"></span></span>
					</li>
					<li>
						<span class="message_l">取票人手机号：</span>
						<span class="true"><span model="buyerMobile"></span></span>
					</li>
					<li>
						<span class="message_l">下单时间：</span>
						<span class="true"><span model="orderCreateTime"></span></span>
					</li>
					<li>
						<span class="message_l">订单总价：</span>
						<span class="true">¥<span model="totalPrice"></span></span>
					</li>
				</ul>
			</div>
		</div>
	</div>
<%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailling_check.js"></script>
</html>