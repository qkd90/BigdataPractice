<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhySailboat/sailling_mine.css">
    <link rel="stylesheet" type="text/css" href="/lib/kindeditor-4.1.11-zh-CN/themes/default/default.css">
    <title>海上休闲详情-一海游商户平台</title>
</head>
<body class="includeTable">
    <%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="roomset sailMine">
		<div class="detail_header clearfix" style="z-index: 2">
			<span class="return" onclick="history.back()">返回</span>
		</div>
		<form role="form" id="sailboatInfoForm" action="" method="post" enctype="multipart/form-data">
			<input type="hidden" id="ticketId" value="${ticketId}">
			<input type="hidden" id="originId" name="ticket.originId">
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="starColor">*</span>类型：</label>
					<input type="hidden" name="ticket.ticketType" value="yacht">
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
					<input type="text" readonly="readonly" id="ticketName" class="form-control yhy-form-ctrl homestayName" name="ticket.name" placeholder="产品名称">
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="starColor">*</span>码头：</label>
					<input type="hidden" name="ticket.scenicInfo.id" value="2700265">
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
					<input type="text" readonly="readonly" name="ticket.address" class="form-control yhy-form-ctrl homestayName" placeholder="地址">
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<lebel class="leftTitle"><span class="starColor marginLeft4">*</span>联系电话：</lebel>
					<input type="text" readonly="readonly" name="ticket.telephone" class="form-control yhy-form-ctrl homestayName" placeholder="联系电话">
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle">营业时间：</label>
					<input type="text" readonly="readonly" class="form-control yhy-form-ctrl homestayName" name="ticketExplain.openTime" placeholder="营业时间">
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<span class="leftTitle"><span class="starColor" style="margin-left:5px">*</span>是否需要确认：</span>
					<input type="hidden" name="ticket.needConfirm">
					<div class="selType" id="confirmSel">
						<span class="selFir" data-is-needconfirm="true">是</span>
						<span class="selSec selected" data-is-needconfirm="false">否</span>
					</div>
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group" style="position: relative;">
					<label class="leftTitle">小贴士：</label>
					<textarea id="ticketTips" class="form-control yhy-form-ctrl Tips honey_atention" name="ticketExplain.tips" readonly="readonly"></textarea>
				</div>
			</div>
			<div class="outDiv clearfix" style="margin-bottom: 20px">
				<div class="form-group yhy-form-group" style="position: relative;">
					<label class="leftTitle">产品描述：</label>
					<textarea id="scenicIntro" class="form-control yhy-form-ctrl scenicIntro honey_atention" name="ticketExplain.proInfo" readonly="readonly"></textarea>
				</div>
			</div>
		</form>
	</div>
    <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script type="text/javascript" src="/lib/kindeditor-4.1.11-zh-CN/kindeditor-all-min.js"></script>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailboat_info_detail.js"></script>
</html>