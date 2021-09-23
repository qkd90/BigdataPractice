<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay_order.css">
    <title>民宿/订单处理</title>
</head>
<body class="homestayOrder includeTable">
    <%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="roomset orderList">
		<div class="selectBar" id="search_div">
			<div class="selB_1 businesOrderNum">
				<div class="input-group">
				  	<input type="text" id="keyword" class="form-control" placeholder="客人/手机/订单号">
				</div>
			</div>
			<div class="selB_1 orderTime">
				<span class="orderTime_1">下单时间</span>
				<div class="input-group">
				  	<input type="text" id="createTime_start" class="form-control">
				</div>
				<span class="orderTime_2">到</span>
				<div class="input-group">
				  	<input type="text" id="createTime_end" class="form-control">
				</div>
			</div>
			<div class="selB_1 orderTime">
				<span class="orderTime_1">入住时间</span>
				<div class="input-group">
				  	<input type="text" id="playDate" class="form-control">
				</div>
				<span class="orderTime_2">－</span>
				<div class="input-group">
				  	<input type="text" id="leaveDate" class="form-control">
				</div>
			</div>
			<div class="selB_1 orderState clearfix">
				<%--<span class="orderState_1">订单状态</span>--%>
				<div class="form-group">
					<span class="orderState_1">订单状态</span>
					<select class="combobox input-large form-control" id="orderStatus" name="normal">
						<option value="" selected="selected">全部</option>
						<option  value="WAITING">待支付</option>
						<option  value="SUCCESS">预订成功</option>
						<option  value="CANCELED">已取消</option>
						<option  value="FAILED">预订失败</option>
						<option  value="CHECKIN">已入住</option>
						<option  value="CHECKOUT">已退房</option>
						<option  value="REFUNDED">已退款</option>
						<option  value="INVALID">无效订单</option>
					</select>
				</div>
			</div>
			<div class="selB_1 search">
				<div class="btn-group">
  					<button type="button" onclick="HotelOrder.search()" class="btn btn-default">查询</button>
				</div>
			</div>
		</div>
		<div class="orderTable messageList_header">
			<table id="hotelOrderListId" class="display table table-striped" cellspacing="0" width="100%">
			</table>
		</div>
	</div>
    <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhyHotel/homestay_order.js"></script>
</html>