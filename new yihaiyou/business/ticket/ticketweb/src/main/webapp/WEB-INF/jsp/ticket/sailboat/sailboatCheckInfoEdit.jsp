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
	<title>海上休闲信息</title>
	<%@ include file="../../common/yhyheader.jsp"%>
	<script type="text/javascript">
		var FG_DOMAIN = '<s:property value="fgDomain"/>';
	</script>
	<%--<%@ include file="../../common/yhyheader.jsp"%>--%>

	<link rel="stylesheet" type="text/css" href="/css/yhy/yhySailboat/sailling_mine.css">
	<link rel="stylesheet" type="text/css" href="/lib/kindeditor-4.1.11-zh-CN/themes/default/default.css">
	<title>海上休闲信息-一海游商户平台</title>
</head>
<body class="sailIndex">
<div class="sailMine">
	<form role="form" id="sailboatInfoForm" action="" method="post" enctype="multipart/form-data">
		<input type="hidden" id="ticketId" name="ticket.id" value="${ticket.id}">
		<input type="hidden" id="ticketExplainId" name="ticketExplain.id" value="${ticketExplain.id}">
		<div class="outDiv clearfix">
			<div class="form-group yhy-form-group">
				<label class="leftTitle"><span class="starColor">*</span>类型：</label>
				<input type="hidden" name="ticket.ticketType" value="${ticket.ticketType}">
				<div class="selType" id="ticketTypeSel">
					<span class="selFir selected" data-ticket-type="yacht">游艇</span>
					<span class="selSec" data-ticket-type="sailboat">帆船</span>
					<span class="selSec" data-ticket-type="huanguyou">鹭岛游</span>
				</div>
			</div>
		</div>
		<div class="outDiv clearfix">
			<div class="form-group yhy-form-group">
				<label class="leftTitle"><span class="starColor marginLeft4">*</span>产品名称：</label>
				<input type="hidden" name="ticket.ticketName">
				<input type="text" id="ticketName" class="form-control yhy-form-ctrl homestayName" name="ticket.name" placeholder="产品名称" value="${ticket.name}">
			</div>
		</div>
		<div class="outDiv clearfix">
			<div class="form-group yhy-form-group">
				<label class="leftTitle"><span class="starColor">*</span>码头：</label>
				<input type="hidden" name="ticket.scenicInfo.id" value="${ticket.scenicInfo.id}">
				<div class="selType selPlace" id="scenicSel">
					<span class="selFir selected" data-scenic-id="2700265">五缘湾游艇码头</span>
					<span class="selSec" data-scenic-id="2700266">香山国际游艇码头</span>
					<span class="selSec" data-scenic-id="2700267">邮轮中心厦鼓码头</span>
				</div>
			</div>
		</div>
		<div class="outDiv clearfix">
			<div class="form-group yhy-form-group">
				<label class="leftTitle"><span class="starColor">*</span>地址：</label>
				<input type="text" id="ticketAddress" name="ticket.address" class="form-control yhy-form-ctrl homestayName" placeholder="地址" value="${ticket.address}">
			</div>
		</div>
		<div class="outDiv clearfix">
			<div class="form-group yhy-form-group">
				<lebel class="leftTitle"><span class="starColor marginLeft4">*</span>联系电话：</lebel>
				<input type="text" id="ticketPhone" name="ticket.telephone" class="form-control yhy-form-ctrl homestayName" placeholder="联系电话" value="${ticket.telephone}">
			</div>
		</div>
		<div class="outDiv clearfix">
			<div class="form-group yhy-form-group">
				<label class="leftTitle">营业时间：</label>
				<input type="text" id="businessHours" class="form-control yhy-form-ctrl homestayName" name="ticketExplain.openTime" placeholder="营业时间" value="${ticketExplain.openTime}">
			</div>
		</div>
		<div class="outDiv clearfix" id="confirm_area">
			<div class="form-group yhy-form-group">
				<span class="leftTitle"><span class="starColor" style="margin-left:5px">*</span>是否需要确认：</span>
				<input type="hidden" name="ticket.needConfirm" value="${ticket.needConfirm}">
				<div class="selType" id="confirmSel">
						<span class="selFir" data-is-needconfirm="true">是</span>
					<span class="selSec selected" data-is-needconfirm="false">否</span>
				</div>
			</div>
		</div>
		<div class="outDiv clearfix">
			<div class="form-group yhy-form-group" style="position: relative;">
				<label id="ticketTipsLab" class="leftTitle">小贴士：</label>
				<textarea class="form-control yhy-form-ctrl Tips honey_atention" id="ticketTips" name="ticketExplain.tips">${ticketExplain.tips}</textarea>
				<span class="writLimit"><span id="ticketTipLength">0</span>/300</span>
			</div>
			<div class="remind_Tips">小贴士</div>
		</div>
		<div class="outDiv clearfix">
			<div class="form-group yhy-form-group" style="position: relative;">
				<label id="scenicIntroLab" class="leftTitle">产品描述：</label>
				<textarea class="form-control yhy-form-ctrl scenicIntro honey_atention" id="scenicIntro" name="ticketExplain.proInfo">${ticketExplain.proInfo}</textarea>
				<span class="writLimit"><span id="scenicIntroLength">0</span>/300</span>
			</div>
			<div class="remind_scenicIntro">产品描述</div>
		</div>
		<div class="mineBtn">
			<a class="btn btn-default btn1">取消</a>
			<button type="submit" class="btn btn-default btn2" id="saveTicketInfoBtn" data-loading-text="保存中...">提交</button>
		</div>
	</form>
</div>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script type="text/javascript" src="/lib/kindeditor-4.1.11-zh-CN/kindeditor-all-min.js"></script>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/sailboat/ticketUtil.js"></script>
<script src="/js/sailboat/sailboatCheckInfoEdit.js"></script>
</html>