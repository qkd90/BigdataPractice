<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay_finance.css">
    <title>民宿/财务结算</title>
</head>
<body class="homestayFinance includeTable">
    <%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="secnav">
		<div class="secnav_list">
			<ul class="clearfix">
				<li data-href="/yhy/yhyMain/toHomestayFinance.jhtml" class="HSsec_active">财务账单</li>
				<li data-href="/yhy/yhyMain/toHomeFundsFlow.jhtml">资金流水</li>
			</ul>
		</div>
	</div>
	<div class="financeList roomset">
		<div class="selectBar">
			<div class="selB_1 businesOrderNum">
				<span class="orderTime_1" style="text-align:left">对账单号</span>
				<div class="input-group">
				  	<input type="text" id="billOrderSummaryId" style="width: 110px;" class="form-control">
				</div>
			</div>
			<div class="selB_1 orderTime">
				<span class="orderTime_1">出账日期</span>
				<div class="input-group">
				  	<input type="text" id="start_billSummaryDate" class="form-control">
				</div>
				<span class="orderTime_2">－</span>
				<div class="input-group">
				  	<input type="text" id="end_billSummaryDate" class="form-control">
				</div>
			</div>
			<div class="selB_1 week_moon">
				<button type="button" class="btn btn-default" onclick="HotelFinance.selectDateRange('week')">本周</button>
				<button type="button" class="btn btn-default" onclick="HotelFinance.selectDateRange('month')" style="float:right;">本月</button>
			</div>
			<div class="selB_1 orderState clearfix">

				<div class="form-group">
					<span class="orderState_1">账单状态</span>
					<select class="combobox input-large form-control" id="confirmStatus" name="normal">
						<option value="" selected="selected">全部</option>
						<option  value="1">已确认</option>
						<option  value="0">未确认</option>
					</select>
				</div>
			</div>
			<div class="selB_1 search">
				<div class="btn-group">
  					<button type="button" onclick="HotelFinance.search()" class="btn btn-default">查询</button>
				</div>
			</div>
		</div>
		<div class="financeTable messageList_header">
			<table class="table table-striped" id="orderFinanceId">
			</table>
		</div>
	</div>
    <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhyHotel/homestay_finance.js"></script>
</html>