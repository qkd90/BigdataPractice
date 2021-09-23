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

	<link rel="stylesheet" type="text/css" href="/css/yhy/yhyHotel/homestay.css">
	<link href="/lib/bootstrap-fileinput4.3.6/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="/lib/kindeditor-4.1.11-zh-CN/themes/default/default.css">
	<style type="text/css">
		.roomBaseMess .homestayLevel .label{
			top: 7px;
		}
		#hotelInfoForm .has-feedback .form-control-feedback {
			top: 0px;
			right: 627px;
		}
		#hotelInfoForm .jianjie .has-feedback .form-control-feedback {
			top: 0px;
			right: 325px;
		}
		#hotelInfoForm .zc  .has-feedback .form-control-feedback {
			top: 0px;
			right: 277px;
		}
		.has-feedback span.writLimit{
			left: 735px;
			top: -20px;
			position: absolute;
		}
	</style>
</head>
<body class="homestayIndex">
<div class="container">
	<!-- 民宿信息/基础信息 -->
	<div class="roomset roomBaseMess" style="display:block">
		<form role="form" id="hotelInfoForm" action="" method="post" enctype="multipart/form-data">
			<input type="hidden" id="hotelId" name="hotel.id" value="${hotel.id}">
			<input type="hidden" id="originId" name="hotel.originId">
			<div class="outDiv clearfix">
				<div class="form-group yhy-hotelinfo-form-group">
					<label for="hotelName" class="leftTitle"><span class="starColor">*</span>名称：</label>
					<input type="text" id="hotelName" name="hotel.name" class="form-control yhy-form-ctrl homestayName" placeholder="民宿名称" value="${hotel.name}">
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-hotelinfo-form-group">
					<input type="hidden" id="hid_hotelStar" value="${hotel.star}">
					<label for="hotelStar" class="leftTitle"><span class="starColor_white">*</span>星级：</label>
					<select id="hotelStar" name="hotel.star" data-btn-class="form-control yhy-form-ctrl homestayLevel btcombo btn-default">
						<option value="0">客栈/经济</option>
						<option value="1">一星级</option>
						<option value="2">二星级</option>
						<option value="3">三星级</option>
						<option value="4">四星级</option>
						<option value="5">五星级</option>
					</select>
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-hotelinfo-form-group">
					<input type="hidden" id="hid_hotelCityId" value="${hotel.cityId}">
					<label for="hotelArea" class="leftTitle"><span class="starColor">*</span>区域：</label>
					<%--<div class="homestayAddress">请选择省市区</div>--%>
					<input type="text" readonly id="hotelArea" class="form-control yhy-form-ctrl homestayAddress" placeholder="请选择省市区">
					<input type="hidden" name="hotel.cityId" id="cityId" value="350200">
				</div>
				<div class="addressSel" id="addressSelBox">
					<div class="title">
						<ul class="titleUl clearfix" id="addressTitleTab">
							<li class="titleLi" id="provinceTitleTab" data-show-box="provinceSel">省份</li>
							<li class="titleLi" id="cityTitleTab" data-show-box="citySel">城市</li>
							<li class="titleLi active" id="regionTitleTab" data-show-box="regionSel">区/县</li>
						</ul>
					</div>
					<div class="selbox province clearfix" id="provinceSel">
						<ul class="clearfix provinceUl">
							<li class="selected">福建</li>
						</ul>
					</div>
					<div class="selbox city" id="citySel">
						<ul class="clearfix cityUl">
							<li class="selected">厦门</li>
						</ul>
					</div>
					<div class="selbox area disb" id="regionSel">
						<ul class="clearfix regionUl">
							<li data-city-id="350203">思明</li>
							<li data-city-id="350205">海沧</li>
							<li data-city-id="350206">湖里</li>
							<li data-city-id="350211">集美</li>
							<li data-city-id="350212">同安</li>
							<li data-city-id="350213">翔安</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-hotelinfo-form-group">
					<label for="hotelAddress" class="leftTitle" style="margin-left:32px!important;"><span class="starColor">*</span>详细地址：</label>
					<textarea id="hotelAddress" class="form-control yhy-form-ctrl addressDe"
							  placeholder="请输入详细地址" name="hotel.extend.address">${hotel.extend.address}</textarea>
				</div>
			</div>
			<%--<div class="outDiv clearfix">
				<div class="form-group yhy-hotelinfo-form-group">
					<label for="hotelPhone" class="leftTitle"><span class="starColor">*</span>电话：</label>
					<input id="hotelPhone" type="text" class="form-control yhy-form-ctrl homestayName"
						   placeholder="请输入电话" name="hotel.extend.tel" value="${hotel.extend.tel}">
				</div>
			</div>--%>
			<div class="outDiv jianjie clearfix">
				<div class="form-group yhy-hotelinfo-form-group" style="position:relative">
					<label for="hotelIntro" id="hotelIntroLab" class="leftTitle"><span class="starColor">*</span>简介：</label>
					<textarea id="hotelIntro" class="form-control yhy-form-ctrl hotel-intro"
							  placeholder="请输入简介" name="hotel.shortDesc">${hotel.shortDesc}</textarea>
					<span class="writLimit"><span id="introLenth">0</span>/500</span>
				</div>
				<div class="remind_service">我是90后隔壁老王，最近风声有点紧我出国躲两个月，房子空闲出来低价出租</div>
			</div>
			<div class="outDiv clearfix">
				<span class="leftTitle" style="margin-left:58px!important"><span class="starColor">*</span>相册：</span>
				<div class="row photo" style="padding: 0;border: none;">
					<div class="col-md-12" style="padding-left: 0;padding-right: 0;">
						<input id="hotelImgs" name="resource" type="file" multiple class="file-loading">
					</div>
					<div class="advice">为了展示效果，建议上传图片的规格为380×240。</div>
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="form-group yhy-hotelinfo-form-group">
					<input type="hidden" id="hid_hotelServiceAmenities" value="${hotel.serviceAmenities}"/>
					<label class="leftTitle" style="margin-left:32px!important"><span class="starColor_white">*</span>设施服务：</label>
					<div class="service" id="service_group">
						<label class="checkbox-inline service-item">
							<input class="form-control yhy-form-ctrl" type="checkbox" value="" name="hotel.serviceAmenities">
						</label>
					</div>
				</div>
			</div>
			<div class="outDiv zc clearfix" style="margin-top: 25px;">
				<div class="form-group yhy-hotelinfo-form-group">
					<label for="hotelPolicy" id="hotelPolicyLab" class="leftTitle"><span class="starColor_white">*</span>政策：</label>
					<textarea id="hotelPolicy" class="form-control yhy-form-ctrl hotel-policy" placeholder="请填写政策" name="hotel.policy">${hotel.policy}</textarea>
					<span class="writLimit"><span id="policyLenth">0</span>/500</span>
				</div>
				<div class="remind_policy" style="left:770px">
					<p>1.爱国</p>
					<p>2.爱家</p>
					<p>3.爱自己</p>
				</div>
			</div>
			<div class="outDiv clearfix">
				<div class="mineBtn">
					<button class="btn btn-default btn1" onclick="CheckHotelInfo.doCancel()" data-loading-text="保存中...">取消</button>
					<button class="btn btn-default btn2" id="saveCheckHotelInfoBtn" onclick="CheckHotelInfo.doSaveCheckHotelInfo()" data-loading-text="保存中...">保存</button>
				</div>
			</div>
		</form>
	</div>
</div>
</body>
<%@ include file="../../common/yhyfooter.jsp"%>
<script src="/lib/bootstrap-fileinput4.3.6/js/fileinput.js" type="text/javascript"></script>
<script src="/lib/bootstrap-fileinput4.3.6/js/locales/zh.js" type="text/javascript"></script>
<script src="/lib/bootstrap-fileinput4.3.6/js/fileinput_util.js" type="text/javascript"></script>
<script type="text/javascript" src="/lib/kindeditor-4.1.11-zh-CN/kindeditor-all-min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=04l9I8LnhQWXsj2ezduHh8KFz4ndNaY4"></script>
<script src="/js/yhy/yhyHotel/homestay.js"></script>
<script src="/js/hotel/checkHotelInfo.js"></script>
</html>