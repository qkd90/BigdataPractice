<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/yhy/yhySailboat/sailling_mine.css">
    <link rel="stylesheet" type="text/css" href="/lib/kindeditor-4.1.11-zh-CN/themes/default/default.css">
    <title>海上休闲信息-一海游商户平台</title>
</head>
<body class="sailIndex includeTable">
    <%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="roomset sailMine">
		<form role="form" id="sailboatInfoForm" action="" method="post" enctype="multipart/form-data">
			<input type="hidden" id="ticketId" name="ticket.id" value="${editTicketId}">
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
					<input type="text" id="ticketName" class="form-control yhy-form-ctrl homestayName" name="ticket.name" placeholder="产品名称">
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="starColor">*</span>码头：</label>
					<input type="hidden" name="ticket.scenicInfo.id" value="${sailboatList[0].id}">
					<div class="selType selPlace" id="scenicSel">
						<c:forEach items="${sailboatList}" var="sailboat" varStatus="status">
							<span class="selSec <c:if test="${status.index == 0}">selected</c:if>" data-scenic-id="${sailboat.id}">${sailboat.name}</span>
						</c:forEach>
						<%--<span class="selFir selected" data-scenic-id="2700265">五缘湾游艇码头</span>--%>
						<%--<span class="selSec" data-scenic-id="2700266">香山国际游艇码头</span>--%>
						<%--<span class="selSec" data-scenic-id="2700267">邮轮中心厦鼓码头</span>--%>
					</div>
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="starColor">*</span>地址：</label>
					<input type="text" id="ticketAddress" name="ticket.address" class="form-control yhy-form-ctrl homestayName" placeholder="地址">
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="starColor marginLeft4">*</span>联系电话：</label>
					<input type="text" id="ticketPhone" name="ticket.telephone" class="form-control yhy-form-ctrl homestayName" placeholder="联系电话">
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="starColor marginLeft4">*</span>营业时间：</label>
					<input type="text" id="businessHours" class="form-control yhy-form-ctrl homestayName" name="ticketExplain.openTime" placeholder="营业时间">
				</div>
			</div>
			<div class="outDiv clearfix" id="confirm_area">
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
					<label id="ticketTipsLab" class="leftTitle">小贴士：</label>
					<textarea class="form-control yhy-form-ctrl Tips honey_atention" id="ticketTips" name="ticketExplain.tips"></textarea>
					<span class="writLimit">0/300</span>
				</div>
				<div class="remind_Tips">小贴士</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group" style="position: relative;">
					<label id="scenicIntroLab" class="leftTitle">产品描述：</label>
					<textarea class="form-control yhy-form-ctrl scenicIntro honey_atention" id="scenicIntro" name="ticketExplain.proInfo"></textarea>
					<span class="writLimit">0/300</span>
				</div>
				<div class="remind_scenicIntro">产品描述</div>
			</div>
			<div class="mineBtn">
				<a href="/yhy/yhyMain/toSailboatList.jhtml" class="btn btn-default btn1">取消</a>
				<button type="submit" class="btn btn-default btn2" id="saveTicketInfoBtn" data-loading-text="保存中...">提交</button>
			</div>
		</form>
	</div>
    <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script type="text/javascript" src="/lib/kindeditor-4.1.11-zh-CN/kindeditor-all-min.js"></script>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailboat_info.js"></script>
</html>