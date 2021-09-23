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
	<script type="text/javascript" src="/js/contract/editContract.js"></script>
	<style type="text/css">
		.input_radio{  display: inline-block; line-height: 45px; vertical-align: top; position: relative; padding-left: 8px; padding-right: 5px;}
		.add_tr_class {display: none}
		.ke-inline-block {display: inline;}
		.ke-button {width: 100px;}
		.ke-upload-area .ke-upload-file {width: 100px;}
		.ke-button-common {width: 100px;}
		.ke-upload-area {width: 100px;}
	</style>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="合同编辑" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
	<div data-options="region:'center',border:false" style="padding:10px 10px 10px 10px" style="width:100%;">
		<div id="div-content">
			<form id="editForm" method="post">

				<div>
					<input id="contractId" name="contract.id" value="${contract.id}" type="hidden"/>
					<table style="">
						<tr>
							<td class="label"><font color="red">*&nbsp;</font>合同名称:</td>
							<td>
								<input class="easyui-textbox" name="contract.name" value="${contract.name}" style="width:200px;" data-options="required:'true', validType:['maxLength[40]']">
							</td>
						</tr>
						<%--<tr>
                            <td class="label"><font color="red">*&nbsp;</font>甲方单位:</td>
                            <td>
                                <input id="hid_unitAid" type="hidden" name="contract.partyAunit.id" value="${contract.partyAunit.id}">
                                <input id="unitAname" class="easyui-textbox" value="${contract.partyAunit.name}" style="width:200px;" data-options="required:'true', editable: false, validType:['maxLength[40]']">
                            </td>
                        </tr>--%>
						<tr>
							<td class="label"><font color="red">*&nbsp;</font>合同编号:</td>
							<td>
								<input class="easyui-textbox" name="contract.partyAnum" value="${contract.partyAnum}"
									   style="width:200px;" data-options="required:'true', readonly:'true'">
							</td>
						</tr>
						<tr>
							<td class="label"><font color="red">*&nbsp;</font>签约商户:</td>
							<td>
								<input id="hid_unitAid" type="hidden" name="contract.partyAunit.id" value="${contract.partyAunit.id}">
								<input class="easyui-combobox" name="contract.partyBunit.id" value="${contract.partyBunit.id}"
									   style="width:200px;" data-options="loader: ContractEdit.unitLoader,
										mode: 'remote',
										required:'true',
										valueField: 'id',
										textField: 'name',
										data:[{
												id:'${contract.partyBunit.id}',
												name:'${contract.partyBunit.name}',
												selected:true
											}]">
							</td>
						</tr>
						<%--<tr>
                            <td class="label"><font color="red">*&nbsp;</font>对方合同编号:</td>
                            <td>
                                <input class="easyui-textbox" name="contract.partyBnum" value="${contract.partyBnum}"
                                       style="width:200px;" data-options="required:'true', readonly:'true'">
                            </td>
                        </tr>--%>
						<tr>
							<td class="label"><font color="red">*&nbsp;</font>签约时间:</td>
							<td>
								<input id="ipt_signtime" class="easyui-datebox" name="contract.signTimeStr" value="${contract.signTime}"
									   style="width:200px;" data-options="required:'true',">
							</td>
						</tr>
						<tr>
							<td class="label"><font color="red">*&nbsp;</font>生效时间:</td>
							<td>
								<input id="ipt_efftime" class="easyui-datebox" name="contract.effectiveTimeStr" value="${contract.effectiveTime}"
									   style="width:200px;" data-options="required:'true',">
							</td>
						</tr>
						<tr>
							<td class="label"><font color="red">*&nbsp;</font>失效时间:</td>
							<td>
								<input id="ipt_exptime" class="easyui-datebox" name="contract.expirationTimeStr" value="${contract.expirationTime}"
									   style="width:200px;" data-options="required:'true',">
							</td>
						</tr>
						<tr>
							<td class="label"><font color="red">*&nbsp;</font>合同状态:</td>
							<td>
								<input id="ipt_status" class="easyui-combobox" name="contract.status" value="${contract.status}"
									   style="width:200px;" data-options="
									valueField:'id',
									textField:'text',
									required:'true',
									panelHeight:'70',
									data:ContractConstants.status
								   ">
							</td>
						</tr>
						<tr>
							<td class="label" valign="top"><font color="red">*&nbsp;</font>结算周期:</td>
							<td>
								<input type="hidden" name="contract.settlementValue" id="settlementValue" value="${contract.settlementValue}">
								<div>
									<label class="input_radio">
										<input type="radio" name="contract.settlementType" value="tday" onclick="ContractEdit.selectOn('settlementType', 'tday')" <s:if test="contract.settlementType == @com.data.data.hmly.service.contract.entity.nums.SettlementType@tday || contract.settlementType == null">checked</s:if> style="vertical-align: middle;margin-top: -2px;margin-bottom: 1px;"> T+
										<input class="easyui-numberbox" id="settlementValue_tday" style="width: 60px;" data-options="prompt:'>=0', min:0">
									</label>

								</div>
								<div>
									<label class="input_radio">
										<input type="radio" name="contract.settlementType" value="week" onclick="ContractEdit.selectOn('settlementType', 'week')" <s:if test="contract.settlementType == @com.data.data.hmly.service.contract.entity.nums.SettlementType@week">checked</s:if> style="vertical-align: middle;margin-top: -2px;margin-bottom: 1px;"> 每周
										<input class="easyui-combobox" id="settlementValue_week" style="width: 60px;" data-options="
									valueField:'id',
									textField:'text',
									panelHeight:'175',
									data:[
										{
											id:0,
											text:''
										},
										{
											id:2,
											text:'一'
										},
										{
											id:3,
											text:'二'
										},
										{
											id:4,
											text:'三'
										},
										{
											id:5,
											text:'四'
										},
										{
											id:6,
											text:'五'
										},
										{
											id:7,
											text:'六'
										},
										{
											id:1,
											text:'日'
										},

									]">
									</label>

								</div>
								<div>
									<label class="input_radio">
										<input type="radio" name="contract.settlementType" value="month" onclick="ContractEdit.selectOn('settlementType', 'month')" <s:if test="contract.settlementType == @com.data.data.hmly.service.contract.entity.nums.SettlementType@month">checked</s:if> style="vertical-align: middle;margin-top: -2px;margin-bottom: 1px;"> 每月
										<input class="easyui-numberbox" id="settlementValue_month" style="width: 60px;" data-options="prompt:'<=28', min:1, max:28">日
									</label>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label"><font color="red">*&nbsp;</font>计价模式:</td>
							<td>
								<input type="hidden" id="valuationValue" name="contract.valuationValue" value="${contract.valuationValue}">
								<label class="input_radio">
									<input type="radio" name="contract.valuationModels" value="commissionModel" onclick="ContractEdit.selectOn('valuationModels', 'commissionModel')" <s:if test="contract.valuationModels == @com.data.data.hmly.service.contract.entity.nums.ValuationModels@commissionModel">checked</s:if> style="vertical-align: middle;margin-top: -2px;margin-bottom: 1px;"> 佣金
									<input class="easyui-numberbox" id="valuationValue_commissionModel" style="width: 60px;" data-options="min:0">%
								</label>
								<label class="input_radio">
									<input type="radio" name="contract.valuationModels" value="fixedModel" onclick="ContractEdit.selectOn('valuationModels', 'fixedModel')" <s:if test="contract.valuationModels == @com.data.data.hmly.service.contract.entity.nums.ValuationModels@fixedModel">checked</s:if> style="vertical-align: middle;margin-top: -2px;margin-bottom: 1px;"> 固定价格
									<input class="easyui-numberbox" id="valuationValue_fixedModel" style="width: 60px;" data-options="min:0">元
								</label>
								<label class="input_radio">
									<input type="radio" name="contract.valuationModels" value="lowPriceModel" onclick="ContractEdit.selectOn('valuationModels', 'lowPriceModel')" <s:if test="contract.valuationModels == @com.data.data.hmly.service.contract.entity.nums.ValuationModels@lowPriceModel || contract.valuationModels == null">checked</s:if> style="vertical-align: middle;margin-top: -2px;margin-bottom: 1px;"> 底价
								</label>
							</td>
						</tr>
						<tr valign="top">
							<td class="label"><font color="red">*&nbsp;</font>合同内容:
								<p style="font-size: 10px;">（支持word、表格、pdf、jpeg、jpg、png）</p>
							</td>
							<td width="605px">
								<table width="450px" style="float: left; margin-right: 10px;">
									<thead>
									<tr>
										<td width="320px">文件名称</td>
										<td>操作</td>
									</tr>
									</thead>
									<tbody id="appendicesTbody">
									</tbody>
								</table>
								<input type="hidden" id="upload" value=""/>
								<input type="button" style="width:150px; float: right" id="uploadButton" value="添加附件"/>
								<%--<textarea id="kind_content" name="contract.content"
                                          style="width: 700px; height: 300px; visibility: hidden;">${contract.content}</textarea>
                                <span class="tip">还可以输入<span class="green-bold">5000</span>个字符</span>--%>
								<%--<input class="easyui-textbox" name="contract.content" value="${contract.content}" style="width:200px;" data-options="required:true, validType:['maxLength[20]']">--%>
							</td>
						</tr>
					</table>
				</div>
				<div>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="" onClick="ContractEdit.save()">保存</a>
				</div>
			</form>
		</div>

	</div>
</div>
<div id="downDialog" class="easyui-dialog" title="图片浏览" style="width:600px;height:500px;"
	 data-options="resizable:true, closed: true, modal:true">
	<div style="padding: 10px;">
		<img id="viewImg" src="" style="width: 98%; height: 95%;">
	</div>
</div>
</body>
</html>
