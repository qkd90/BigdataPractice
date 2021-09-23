<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>民宿管理</title>
	<%@ include file="../../common/yhyheader.jsp"%>
	<script type="text/javascript">
		var FG_DOMAIN = '<s:property value="fgDomain"/>';
	</script>
	<%--<%@ include file="../../common/yhyheader.jsp"%>--%>

	<link rel="stylesheet" href="/css/yhy/yhySailboat/sailling.css">
	<link rel="stylesheet" href="/css/yhy/yhyHotel/homestay_index.css">
	<link rel='stylesheet' href='/fullcalendar-2.4.0/lib/cupertino/jquery-ui.min.css' />
	<link href='/fullcalendar-2.4.0/fullcalendar.css' rel='stylesheet'>
	<link href='/fullcalendar-2.4.0/fullcalendar.print.css' rel='stylesheet' media='print'>
	<style type="text/css">
		.view-calendar {
			padding: 15px;
		}
		.view-calendar .main-footer {
			position: fixed;
			bottom: 15px;
			right: 20px;
		}
		.view-calendar .main {

		}
		.yhy-modal-content.without-title {
			margin-top: 290px!important;
			left: 240px;
		}
	</style>
</head>
<body style="background:#ffffff;">
<div class="container view-calendar">
	<!-- 民宿信息/基础信息 -->
	<div class="main">
		<div class="main-body">
			<form role="form" id="priceCalendarForm" action="" method="post" enctype="multipart/form-data">
				<div class="price-calendar-info">
					<div class="outDiv clearfix">
						<div class="form-group yhy-form-group">
							<label for="startDate" class="leftTitle"><span class="starColor">*</span>指定时间段：</label>
							<input type="text" id="startDate" class="form-control yhy-form-ctrl" placeholder="开始日期">
							<label class="leftTitle" style="margin: 0 10px">至</label>
							<input type="text" id="endDate" class="form-control yhy-form-ctrl" placeholder="结束日期">
							<label for="startDate" class="leftTitle" style="margin-left: 5px"><span class="starColor">*</span>计价模式：</label>
							<input type="text" id="valuationModels" readonly="readonly" class="form-control yhy-form-ctrl readonly" style="width: 130px;" placeholder="-">
						</div>
					</div>
					<div class="outDiv clearfix">
						<div class="form-group yhy-form-group">
							<label class="leftTitle"><span class="starColor">*</span>每周：</label>
							<div class="week-area">
								<label class="checkbox-inline"><input id="allWeekCheck" class="form-control yhy-form-ctrl" type="checkbox" value="" name="weekday">整周</label>
								<label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="1" name="weekday">周一</label>
								<label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="2" name="weekday">周二</label>
								<label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="3" name="weekday">周三</label>
								<label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="4" name="weekday">周四</label>
								<label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="5" name="weekday">周五</label>
								<label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="6" name="weekday">周六</label>
								<label class="checkbox-inline"><input class="form-control yhy-form-ctrl" type="checkbox" value="0" name="weekday">周日</label>
							</div>
						</div>
					</div>
					<div class="outDiv clearfix">
						<div class="form-group yhy-form-group">
							<input type="hidden" id="ticketPriceId" value="${ticketPriceId}">
							<label for="startDate" class="leftTitle"><span class="starColor">*</span>价格设置：</label>
							<input type="text" id="priPrice" style="width: 85px; margin-right: 10px" class="form-control yhy-form-ctrl" placeholder="销售价">
							<input type="text" id="price" style="width: 85px" class="form-control yhy-form-ctrl" placeholder="结算价">
							<label for="startDate" class="leftTitle" style="margin-left: 5px"><span class="starColor">*</span>设置库存：</label>
							<input type="text" id="inventory" style="width: 85px" class="form-control yhy-form-ctrl" placeholder="库存数量">
							<button type="button" class="btn btn-default add-price-btn" id="addPriceInfoBtn">添加</button>
							<button type="button" class="btn btn-danger clear-price-btn" id="clearPriceInfoBtn">清除</button>
						</div>
					</div>
				</div>
			</form>
			<div id="price_calendar"></div>
		</div>
		<div class="main-footer">
			<button class="btn btn-default" id="cancelPrice">取消</button>
			<button class="btn btn-primary" id="savePriceBtn">保存</button>
		</div>
	</div>
</div>
</body>
<%@ include file="../../common/yhyfooter.jsp"%>
<script src='/fullcalendar-2.4.0/lib/moment.min.js'></script>
<script src='/fullcalendar-2.4.0/fullcalendar.min.js'></script>
<script src='/fullcalendar-2.4.0/lang-all.js'></script>
<script src='/lib/moment.min.js'></script>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/sailboat/viewPriceCalendar.js"></script>
</html>