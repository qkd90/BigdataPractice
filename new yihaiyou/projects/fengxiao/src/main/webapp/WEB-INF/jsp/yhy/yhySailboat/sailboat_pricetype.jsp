<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../common/yhyheader.jsp" %>
    <link rel="stylesheet" href="/css/yhy/yhySailboat/sailling_Type.css">
	<link href="/lib/bootstrap-fileinput4.3.6/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/lib/kindeditor-4.1.11-zh-CN/themes/default/default.css">
	<title>海上休闲票型编辑-一海游商户平台</title>
</head>
<body class="sailIndex">
    <%@include file="../../yhy/yhyCommon/common_header.jsp" %>
	<div class="roomset sailBaseMess">
		<form role="form" id="ticketPriceForm" action="" method="post" enctype="multipart/form-data">
			<input type="hidden" id="ticketPriceId" name="ticketPrice.id" value="${editPriceId}">
			<input type="hidden" id="originId" name="ticketPrice.originId">
			<input type="hidden" id="ticketId" name="ticket.id">
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="starColor">*</span>票型名称：</label>
					<input type="text" id="ticketPriceName" name="ticketPrice.name" class="form-control yhy-form-ctrl homestayName">
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="whiteColor">*</span>条件退款：</label>
					<input type="hidden" name="ticketPrice.isConditionRefund" value="true">
					<div class="selType selType2" id="conditionRefundSel">
						<span class="selFir selected" data-condition-refund="true">是</span>
						<span class="selSec" data-condition-refund="false">否</span>
					</div>
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="whiteColor">*</span>今日可订：</label>
					<input type="hidden" name="ticketPrice.isTodayValid" value="true">
					<div class="selType selType3" id="todayValidSel">
						<span class="selFir selected" data-today-valid="true">是</span>
						<span class="selSec" data-today-valid="false">否</span>
					</div>
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="starColor">*</span>预订须知：</label>
					<div class="maxBox">
						<div id="bookingInfoGroup">
							<div class="secContain clearfix booking-info-item">
								<label class="secTitleName">子标题</label>
								<input type="hidden" data-name="firstTitle" name="firstTitle" value="预订须知">
								<input type="text" data-name="secondTitle" name="secondTitle" placeholder="非必填项" class="form-control yhy-form-ctrl homestayName">
								<a class="btn btn-default del-btn del-binfo-btn">删除</a>
								<textarea data-name="content" name="content" class="form-control yhy-form-ctrl"></textarea>
                                <span class="writLimit">0/300</span>
							</div>
						</div>
					</div>
					<a class="btn btn-default add-btn addBtn" id="addBookinInfoBtn">添加子标题</a>
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="starColor">*</span>费用说明：</label>
					<div class="maxBox">
						<div id="feeInfoGroup">
							<div class="secContain clearfix fee-info-item">
								<label class="secTitleName">子标题</label>
								<input type="hidden" data-name="firstTitle" name="firstTitle" value="费用说明">
								<input type="text" data-name="secondTitle" name="secondTitle" placeholder="非必填项" class="form-control yhy-form-ctrl homestayName">
								<a class="btn btn-default del-btn del-finfo-btn">删除</a>
								<textarea data-name="content" name="content" class="form-control yhy-form-ctrl"></textarea>
                                <span class="writLimit">0/300</span>
							</div>
						</div>
					</div>
					<a class="btn btn-default add-btn addBtn" id="addFeeInfoBtn">添加子标题</a>
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-form-group">
					<label class="leftTitle"><span class="starColor">*</span>退改说明：</label>
					<div class="maxBox">
						<div id="refundInfoGroup">
							<div class="secContain clearfix refund-info-item">
								<label class="secTitleName">子标题</label>
								<input type="hidden" data-name="firstTitle" name="firstTitle" value="退款说明">
								<input type="text" data-name="secondTitle" name="secondTitle" placeholder="非必填项" class="form-control yhy-form-ctrl homestayName">
								<a class="btn btn-default del-btn del-rinfo-btn">删除</a>
								<textarea data-name="content" name="content" class="form-control yhy-form-ctrl"></textarea>
                                <span class="writLimit">0/300</span>
							</div>
						</div>
					</div>
					<a class="btn btn-default add-btn addBtn" id="addRefundInfoBtn">添加子标题</a>
				</div>
			</div>
			<div class="outDiv clearfix">
				<label class="leftTitle" style="margin-left:29px;"><span class="starColor">*</span>相册：</label>
				<div class="row photo" style="padding: 0;border: none;">
					<div class="col-md-12" style="padding-left: 0;padding-right: 0;">
						<input id="ticketPriceImgs" name="resource" type="file" multiple class="file-loading">
					</div>
					<div class="advice">为了展示效果，建议上传图片的规格为450×200。</div>
				</div>
			</div>
			<div class="mineBtn">
				<a href="/yhy/yhyMain/toSailboatPriceList.jhtml" class="btn btn-default btn1">取消</a>
				<button class="btn btn-default btn2" id="savePriceInfoBtn" data-loading-text="保存中...">提交</button>
			</div>
		</form>
	</div>
    <%@include file="../../yhy/yhyCommon/common_footer.jsp" %>
</body>
<%@include file="../../common/yhyfooter.jsp" %>
<script src="/lib/bootstrap-fileinput4.3.6/js/fileinput.js" type="text/javascript"></script>
<script src="/lib/bootstrap-fileinput4.3.6/js/locales/zh.js" type="text/javascript"></script>
<script src="/lib/bootstrap-fileinput4.3.6/js/fileinput_util.js" type="text/javascript"></script>
<script type="text/javascript" src="/lib/kindeditor-4.1.11-zh-CN/kindeditor-all-min.js"></script>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/yhy/yhySailboat/sailboat_pricetype.js"></script>
</html>
