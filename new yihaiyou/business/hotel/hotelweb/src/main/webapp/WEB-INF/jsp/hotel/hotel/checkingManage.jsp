<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script> 
<script type="text/javascript" src="/js/hotel/checkingManage.js"></script>

	<style type="text/css">
		.datagrid-view .datagrid-editable-input {
			margin: 0;
			padding: 2px 4px;
			border: 1px solid #95B8E7;
			font-size: 12px;
			outline-style: none;
			-moz-border-radius: 0 0 0 0;
			-webkit-border-radius: 0 0 0 0;
			border-radius: 0 0 0 0;
		}
		.datagrid-showorder-btn {
			background: rgba(255, 255, 255, 0);
			margin-left: 30px;
		}
		.textbox-addon {
			padding-top: 6px;
		}
	</style>

</head>
<body style="margin:0;padding:0px 3px 0px 0px;">
	<div id="tt" class="easyui-tabs" fit="true" style="width:100%;height:100%;">
		<div title="民宿审核列表" data-options="closable:false, fit:true" style="width:100%;height:100%;">
			<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">

				<div id="storage_tb" style="padding:2px 5px;">
					<form action="" id="storage_searchform">
						<input id="storage_qry_name" class="easyui-textbox" data-options="prompt:'请输入民宿名称'" style="width:200px;">
						<input id="storage_qry_status" type="text" class="easyui-combobox" style="width:120px;" data-options="
							valueField:'id',
							textField:'text',
							prompt:'请选择状态',
							panelHeight: 'auto',
							data:CheckHotelContants.productStatus
						">
						<a href="javascript:void(0)" id="storage_search" class="easyui-linkbutton"  onclick="HotelManage.doSearchStorage()">查询</a>
						<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="HotelManage.doClearStorage()">重置</a>
					</form>
				</div>
				<div data-options="region:'center',border:false">
					<table id="storage_dg"></table>
				</div>
			</div>
		</div>
		<div title="房型审核列表" data-options="closable:false, fit:true" style="width:100%;height:100%;">
			<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
				<div id="storage_type_tb" style="padding:2px 5px;">
					<form action="" id="storage_type_searchform">
						<input id="storage_type_qry_name" class="easyui-textbox" data-options="prompt:'请输入民宿名称'" style="width:200px;">
						<input type="text" id="storage_type_qry_status" class="easyui-combobox" style="width:120px;" data-options="
							valueField:'id',
							textField:'text',
							prompt:'请选择状态',
							panelHeight: 'auto',
							data:CheckHotelContants.priceStatus
						">
						<a href="javascript:void(0)" id="storage_type_search" class="easyui-linkbutton"  onclick="HotelManage.doSearchStorageType()">查询</a>
						<a href="javascript:void(0)"  class="easyui-linkbutton"  onclick="HotelManage.doClearStorageType()">重置</a>
					</form>
				</div>
				<div data-options="region:'center',border:false">
					<table id="storage_type_dg"></table>
				</div>
			</div>

		</div>
	</div>
	<!-- 编辑窗口 -->
	<div id="editPanel" class="easyui-dialog" title="编辑民宿基本信息"
        data-options="fit:true, resizable:false, modal:true, closed:true, closable:true, collapsible:false, shadow:false">
        <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>  
	</div>
	<div id="editPanel_1" class="easyui-dialog" title="民宿房型价格日历"
		 data-options="resizable:false, modal:true, closed:true, closable:true, collapsible:false, shadow:false" style="width:700px; height: 490px;">
		<iframe name="editIframe" id="editIframe_1" scrolling="no" frameborder="0"  style="width:100%;height:650px;border-width:0;"></iframe>
	</div>

	<div id="editRefusePanel" class="easyui-dialog" title="填写理由"
		 data-options="resizable:false,modal:true,closed:true,collapsible:false,shadow:false" style="width: 449px; height: 250px;">
		<div style="width: 100%; height: 100%;">
			<div style="height: 100%;">
				<input type="text" id="refuseContentId" class="easyui-textbox" data-options="multiline: true, prompt:'请输入理由', required: true" style="width: 425px; height: 150px;">
			</div>
		</div>
	</div>


</body>
</html>