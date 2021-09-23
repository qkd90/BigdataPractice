<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
	<%@include file="../../common/yhyheader.jsp" %>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhySailboat/sailling_checkRecorde.css">

    <title>海上休闲/验证记录</title>
</head>
<body class="sailCheck includeTable">
<%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="secnav">
			<div class="secnav_list">
				<ul class="clearfix">
					<li data-href="/yhy/yhyMain/toSailboatCheck.jhtml">验票</li>
					<li data-href="/yhy/yhyMain/toSailboatCheckRecorde.jhtml" class="HSsec_active">验票记录</li>
				</ul>
			</div>
		</div>
	<div class="checkList roomset">
		<div class="selectBar">
			<div class="selectBar_left">验证成功记录</div>
			<div class="selectBar_right">
				<div class="form-group orderNum_list">
					<input id="query_keyword" type="text" placeholder="订单编号/验证码">
				</div>
				<div class="btn-group">
	  				<button type="button" class="btn btn-default search-btn" id="sailboatSearchBtn">查询</button>
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
<script src="/js/yhy/yhySailboat/sailboat_check_recorde.js"></script>
</html>