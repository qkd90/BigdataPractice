<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" href="/css/yhy/yhySailboat/sailling.css">
	<link rel="stylesheet" href="/css/yhy/yhyHotel/homestay_index.css">
	<title>海上休闲产品管理-一海游商户平台</title>
</head>
<body class="sailIndex includeTable">
    <%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="secnav">
			<div class="secnav_list">
				<ul class="clearfix">
					<li data-href="/yhy/yhyMain/toSailing.jhtml" class="HSsec_active">我的产品</li>
					<li data-href="/yhy/yhyMain/toSailboatPriceList.jhtml">票型设置</li>
				</ul>
			</div>
		</div>
	<div class="roomset sailMine sailPrice" style="display:block">
		 <div class="selectBar">
		 	<div class="selectBar_1">
		 		<span class="roomemess">状态</span>
			 	<div class="dropdown status-dropdown">
					<select id="sailboatStatusSel" name="ticket.status" data-btn-class="btcombo btn-default status-sel">
						<option value="">全部</option>
						<option value="UP">已上架</option>
						<option value="DOWN">已下架</option>
						<option value="UP_CHECKING">上架中</option>
						<option value="DOWN_CHECKING">下架中</option>
						<option value="REFUSE">被拒绝</option>
					</select>
				</div>
				<span class="roomemess">产品名称</span>
				<div class="form-group">
	  				<input type="text" class="form-control" id="searchProductName">
				</div>
				<div class="btn-group">
  					<button type="button" class="btn btn-default search-btn" id="sailboatSearchBtn">查询</button>
				</div>
			</div>
			<div class="btn-group addmore">
  				<a href="/yhy/yhySailboatInfo/toSailboatInfo.jhtml" class="btn btn-default">新增</a>
			</div>
		</div>
		<div class="messageList_header">
			<table class="table table-striped yhy-common-table" id="yhySailboatList">
				<thead>
					<tr>
						<th class="proName">产品名称</th>
						<th class="proType">类型</th>
						<th class="proAddress">地址</th>
						<th class="proContact">联系人</th>
						<th class="proPhone">联系电话</th>
						<th class="proState">状态</th>
						<th class="proOperate">操作</th>
					</tr>
				</thead>
			</table>
		</div> 
	</div>
    <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailboat_list.js"></script>
</html>
