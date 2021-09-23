<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第一步</title>
	<%@ include file="../../common/common141.jsp"%>
<%--<script type="text/javascript" src="/js/line/line/lineUtil.js"></script>--%>
	<link rel="stylesheet" type="text/css" href="/css/addWizard.css"/>
	<link rel="stylesheet" type="text/css" href="/js/kindeditor/themes/default/default.css"/>
	<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/hotel/iconfont/iconfont.css"/>
	<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
	<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
	<script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
	<script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
	<script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=R9424rkP6oyCzex5FuLa7XIw"></script>
	<script type="text/javascript" src="/js/extends/jquery.radio.js"></script>
	<script type="text/javascript" src="/js/contract/contractUtil.js"></script>
	<script type="text/javascript" src="/js/contract/viewContract.js"></script>
	<style type="text/css">
		.input_radio{  display: inline-block; line-height: 45px; vertical-align: top; position: relative; padding-left: 8px; padding-right: 5px;}

	</style>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="合同编辑" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
	<div data-options="region:'center',border:false" style="padding:10px 10px 10px 10px" style="width:100%;">
		<form id="editForm" method="post">
			<input name="contract.id" value="${contract.id}" type="hidden"/>
			<table style="float:left;">
				<tr>
					<td class="label">合同名称:</td>
					<td>
						${contract.name}
					</td>
				</tr>
				<tr>
					<td class="label">甲方单位:</td>
					<td>
						${contract.partyAunit.name}
					</td>
				</tr>
				<tr>
					<td class="label">合同编号:</td>
					<td>
						${contract.partyAnum}
					</td>
				</tr>
				<tr>
					<td class="label">乙方单位:</td>
					<td>
						${contract.partyBunit.name}
					</td>
				</tr>
				<tr>
					<td class="label">对方合同编号:</td>
					<td>
						${contract.partyBnum}
					</td>
				</tr>
				<tr>
					<td class="label">签约时间:</td>
					<td>
						<s:date name="contract.signTime" format="yyyy-MM-dd"></s:date>
					</td>
				</tr>
				<tr>
					<td class="label">合同生效时间:</td>
					<td>
						<s:date name="contract.effectiveTime" format="yyyy-MM-dd"></s:date>
					</td>
				</tr>
				<tr>
					<td class="label">合同失效时间:</td>
					<td>
						<s:date name="contract.expirationTime" format="yyyy-MM-dd"></s:date>
					</td>
				</tr>
				<tr>
					<td class="label">合同初始状态:</td>
					<td>
						<s:if test="contract.status == @com.data.data.hmly.service.contract.entity.nums.ContractStatus@DEL">已删除</s:if>
						<s:if test="contract.status == @com.data.data.hmly.service.contract.entity.nums.ContractStatus@UP">生效中</s:if>
						<s:if test="contract.status == @com.data.data.hmly.service.contract.entity.nums.ContractStatus@FREEZE">已冻结</s:if>
						<s:if test="contract.status == @com.data.data.hmly.service.contract.entity.nums.ContractStatus@INVALID">已无效</s:if>

					</td>
				</tr>
				<tr>
					<td class="label" valign="top">结算方式:</td>
					<td>
						<s:if test="contract.settlementType == @com.data.data.hmly.service.contract.entity.nums.SettlementType@tday">T+${contract.settlementValue}</s:if>
						<s:if test="contract.settlementType == @com.data.data.hmly.service.contract.entity.nums.SettlementType@week">每周${contract.settlementValue}</s:if>
						<s:if test="contract.settlementType == @com.data.data.hmly.service.contract.entity.nums.SettlementType@month">每月${contract.settlementValue}</s:if>

					</td>
				</tr>
				<tr>
					<td class="label">计价模式:</td>
					<td>
						<s:if test="contract.valuationModels == @com.data.data.hmly.service.contract.entity.nums.ValuationModels@commissionModel">按${contract.valuationValue}%佣金模式</s:if>
						<s:if test="contract.valuationModels == @com.data.data.hmly.service.contract.entity.nums.ValuationModels@fixedModel">固定价格模式</s:if>
						<s:if test="contract.valuationModels == @com.data.data.hmly.service.contract.entity.nums.ValuationModels@lowPriceModel">低价模式</s:if>

					</td>
				</tr>
				<tr valign="top">
					<td class="label">合同内容:</td>
					<td>
						<textarea id="kind_content" name="contract.content"
								  style="width: 700px; height: 300px; visibility: hidden;">${contract.content}</textarea>
					</td>
				</tr>
			</table>
	    </form>
	</div>
	<div data-options="region:'south',border:false" style="padding:0px 20px 0px 20px">
		<div style="text-align:right;padding:5px;height:30px;margin-right: 15px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="ContractView.cancelEdit()">关闭</a>
		</div>  
	</div>
</div>
</body>
</html>
