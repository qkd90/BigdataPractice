<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" href="/css/yhy/yhySailboat/sailling_order.css">
    <title>海上休闲/订单处理</title>
</head>
<body class="sailOrder includeTable">
    <%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="roomset orderList">
		<div class="selectBar">
			<div class="selB_1 businesOrderNum">
				<div class="form-group">
				  	<input type="text" id="keyword" class="form-control" placeholder="客人/手机/订单号">
				</div>
			</div>
			<div class="selB_1 orderTime">
				<span class="orderTime_1">下单时间</span>
				<div class="form-group">
				  	<input type="text" id="createTime_start" class="form-control">
				</div>
				<span class="orderTime_2">到</span>
				<div class="form-group">
				  	<input type="text" id="createTime_end" class="form-control">
				</div>
			</div>
			<div class="selB_1 orderState clearfix">
				<div class="form-group" style="margin-left: 10px;">
					<span for="orderStatus" class="leftTitle">订单状态：</span>
					<select id="orderStatus" name="hotel.star" data-btn-class="form-control yhy-hotelinfo-form-ctrl homestayLevel btcombo btn-default">
						<option value="" selected="selected">全部</option>

						<option  value="WAITING">待支付</option>
						<option  value="SUCCESS">预订成功</option>
						<option  value="CANCELED">已取消</option>
						<option  value="FAILED">预订失败</option>
						<option  value="UNCONFIRMED">待确认</option>
						<option  value="REFUNDED">已退款</option>
						<option  value="INVALID">无效订单</option>
					</select>
				</div>
			</div>
			<div class="selB_1 search">
				<div class="btn-group">
  					<button type="button" class="btn btn-default" onclick="SailboatOrder.search()">查询</button>
				</div>
			</div>
		</div>
		<div class="orderTable messageList_header">
			<table class="table table-striped" id="orderList">
			</table>
		</div>
		<%--<div class="pageChange">
			<nav>
			    <ul class="pagination clearfix">
			    	<li><a href="#">首页</a></li>
			        <li><a href="#">上一页</a></li>			        
			        <li class="active"><a href="#">1</a></li>
			        <li><a href="#">2</a></li>
			        <li><a href="#">3</a></li>
			        <li><a href="#">4</a></li>
			        <li><a href="#">5</a></li>		        
			        <li><a href="#">下一页</a></li>
			        <li><a href="#">尾页</a></li>
			    </ul>
			</nav>
		</div>	--%>
	</div>
    <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailboat_order.js"></script>
</html>