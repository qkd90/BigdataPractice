<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay_fundsFlow.css">
    <title>海上休闲/资金流水</title>
</head>
<body class="sailFinance includeTable">
	<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="secnav">
		<div class="secnav_list">
			<ul class="clearfix">
				<li data-href="/yhy/yhyMain/toSailboatFinance.jhtml">财务账单</li>
				<li data-href="/yhy/yhyMain/toSailboatFundsFlow.jhtml" class="HSsec_active">资金流水</li>
			</ul>
		</div>
	</div>
	<div class="financeList roomset">
		<div class="permess">
			<div class="cashmax">
				<div class="cashmin">
					<ul>
						<li class="cashpic"></li>
						<li class="cashcount" model="unBill">0元</li>
						<li class="cashstate">待结算</li>
					</ul>
				</div>
			</div>
			<div class="cashmax">
				<div class="cashmin">
					<ul>
						<li class="cashpic cashpic2"></li>
						<li class="cashcount" model="hasedBill">0元</li>
						<li class="cashstate">已结算</li>
					</ul>
				</div>
			</div>
			<div class="cashmax">
				<div class="cashmin">
					<ul>
						<li class="cashpic cashpic3"></li>
						<input type="hidden" id="hid_totalBalance" model="totalBalance" value="0">
						<li class="cashcount" model="totalBalance">0元</li>
						<li class="cashstate">账户余额</li>
					</ul>
				</div>
			</div>
			<div class="withdraw" id="withdraw">提现</div>
			<div class="countbox">
				<form id="tixian_form">
					<p class="closeacount"><span id="closeacount"></span></p>
					<p class="pic"></p>
					<div class="form-group inputbox" style="margin-bottom: 0px;">
						<p>银行帐号：<span>${unitDetail.crtacc}</span></p>
						<p>银行名称：<span>${unitDetail.crtbnk}</span></p>
						<p>开户名：<span>${unitDetail.crtnam}</span></p>
						<input type="num" id="money" name="money" placeholder="提现金额" />
					</div>
					<button type="submit" class="btn btn-primary sureBtn">确认</button>
				</form>
			</div>
		</div>
		<div class="selectBar">
			<div class="selB_1 search">
				<div class="btn-group">
					<button type="button" class="btn btn-default" onclick="SailboatFundsFlow.search()">查询</button>
				</div>
			</div>
			<div class="selB_1 orderState">
				<div class="orderStatelist">
					进度
					<select class="form-control" id="sel_status">
						<option value="">全部</option>
						<option value="submit">提交</option>
						<option value="reject">拒绝</option>
						<option value="normal">正常</option>
						<option value="processing">处理中</option>
						<option value="fail">失败</option>
					</select>
				</div>
			</div>
			<div class="selB_1 orderState">
				<div class="orderStatelist">
					类型
					<select class="form-control" id="sel_type">
						<option value="">全部</option>
						<option value="out">出账</option>
						<option value="outlinewd">提现</option>
						<option value="refund">退款</option>
						<option value="in">入账</option>
					</select>
				</div>
			</div>
			<div class="selB_1 orderTime">
				<span class="orderTime_1">时间</span>
				<div class="input-group getinTime">
					<input type="text" id="orderTime_1" class="form-control">
				</div>
				<span class="orderTime_2">到</span>
				<div class="input-group getinTime">
					<input type="text" id="orderTime_2" class="form-control">
				</div>
			</div>
		</div>
		<div class="financeTable messageList_header">
			<table class="table table-striped">
			</table>
		</div>
	</div>
	<%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailboat_fundsflow.js"></script>
</html>