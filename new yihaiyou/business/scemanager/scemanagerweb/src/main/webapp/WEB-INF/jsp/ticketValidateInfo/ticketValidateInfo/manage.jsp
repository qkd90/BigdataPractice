<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<title>站点管理</title>
<%@ include file="../../common/common141.jsp"%>
<link href="/js/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<!-- <link rel="stylesheet" type="text/css" href="/css/scemanage/scemanage.css"/> -->
<script type="text/javascript" src="/js/ticketValidateInfo/ticketValidateInfo.js"></script>
<%-- <script type="text/javascript" src="/js/scemanager/scemanage.js"></script> --%>
<style type="text/css">
 	.inform_table {margin:15px;} 
 	.inform_table td {padding-bottom: 10px;} 
</style>
</head>
<body style="margin:0;padding:0px 3px 0px 0px;">

    <div id="content" class="easyui-layout" data-options="fit:true"
         style="width:100%;height:100%;">
        
        <div data-options="region:'north',border:false" style="height:70px;padding:20px 25px;">
       		<table>
       			<tr>
       				<td><label>手机号：</label></td>
       				<td style="padding-right: 20px;"><input id="ipt_sech_customerPhone" class="easyui-textbox" name="customerPhone" data-options="prompt:'请手机号',validType:['mobile']" name="customerPhone" style="width:300px"></td>
       				<td><label>日期：</label></td>
       				<td style="padding-right: 50px;"><input id="ipt_sech_createTime" type="text" name="createTime" class="easyui-datebox"></input></td>
       				<td><a id="btn" href="#" class="easyui-linkbutton" onclick="TicketValidateInfo.doSearch()" data-options="prompt:'请选择查询日期'">查询</a> </td>
       			</tr>
       		</table>
		</div>
		<!-- 数据表格 始 -->
		<div data-options="region:'center',border:false">
			<div id="addTool">
				<a href="javascript:void(0)" class="easyui-linkbutton"   onclick="TicketValidateInfo.addValidateInfo()">录入信息</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"   onclick="TicketValidateInfo.delValidateInof()">删除信息</a>
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="FollowerList.openMsgDialog()">编辑信息</a> -->
			</div>
			<table id="dg"></table>
		</div>
    </div>

	<div id="inform_dialog" class="easyui-dialog" title="录入信息"
        data-options="resizable:true,modal:true,closed:true"> 
		<form id="inform_form" action="" method="post">
			<table class="inform_table">
				<tr>
					<td><label>产品名称：</label></td>
					<td>
						<input id="ipt_productName" type="hidden" name="productName" value="">
						<input id="select_product" class="easyui-combobox input" name="productId" style="width:200px;"data-options="loader: TicketValidateInfo.productList,
									mode: 'remote',
									valueField: 'id',
									textField: 'name',
									prompt:'请选择产品'"/>
					</td>
				</tr>
				<tr>
					<td><label>姓名：</label></td>
					<td>
						<input id="ipt_customerName" name="customerName" class="easyui-textbox input"  data-options="prompt:'请输入请输入客户名称',validType:'maxLength[10]'"  style="width:200px;">
					</td>
				</tr>
				<tr>
					<td><label>手机号：</label></td>
					<td>
						<input id="ipt_customerPhone" name="customerPhone" class="easyui-textbox input" data-options="prompt:'请输入手机号',validType:['mobile']" style="width:200px"> 
					</td>
				</tr>
				<tr>
					<td><label>数量：</label></td>
					<td>
						<input id="ipt_amount" name="amount" class="easyui-textbox input" data-options="prompt:'请输入数字',validType:['number']" style="width:200px"> 
					</td>
				</tr>
				<tr>
					<td><label>来源：</label></td>
					<td>
						<input id="slt_origin" class="easyui-textbox input" data-options="prompt:'请输入门票来源',validType:'maxLength[10]'" name="origin" style="width:200px;">   
						    
					</td>
				</tr>
				
<!-- 				<tr> -->
<!-- 					<td></td> -->
<!-- 					<td colspan="1" align="center"> -->
						
<!-- 					</td> -->
<!-- 				</tr> -->
			</table>
			<div style="margin-left: 110px;">
				<a href="javascript:void(0)" style="width : 100px;" class="easyui-linkbutton"  onclick="TicketValidateInfo.sendInform()">提交</a>
			</div>
			
		</form>
		
	</div>

</body>
</html>