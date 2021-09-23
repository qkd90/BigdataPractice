<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>海上休闲船票管理</title>
	<script type="text/javascript">
		var FG_DOMAIN = '<s:property value="fgDomain"/>';
	</script>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="/js/sailboat/ticketCheckingList.js"></script>
<script type="text/javascript" src="/js/sailboat/ticketUtil.js"></script>
<%-- <script type="text/javascript" src="/js/user/user.js"></script> --%>
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
<body style="background-color: white;">

<div id="tt" class="easyui-tabs" fit="true" style="width:100%;height:100%;">
	<div title="海上休闲审核列表" data-options="closable:false, fit:true" style="width:100%;height:100%;">
		<div id="user_Tool" style="padding:2px 5px;">
			<input  class="easyui-combobox" id="search_ticketType"  name="ticket" style="width: 120px;"
					data-options="
							 	prompt:'请选择类型',
							 	editable:false,
							 	<%--'CTRIP','JUHE','ELONG','QUNAR'--%>
								valueField: 'id',
								textField: 'value',
								panelHeight: 'auto',
								data: [{
									id: 'sailboat',
									value: '帆船'
								},{
									id: 'yacht',
									value: '游艇'
								},{
									id: 'huanguyou',
									value: '鹭岛游'
								}]"  />

			<input class="easyui-textbox" id="search_name" data-options="prompt:'请输入产品名称'" style="width:200px;">
			<input  class="easyui-combobox" id="search_ticketStatus"  name="ticket" style="width: 120px;"
					data-options="
							 	prompt:'请选择状态',
							 	editable:false,
								valueField: 'id',
								textField: 'text',
								panelHeight: 'auto',
								data: TicketConstants.productStatus"  />

			<a href="javascript:void(0)" class="easyui-linkbutton"
			   onclick="TicketList.doShowSearch();">查询</a>

			<a href="javascript:void(0)" class="easyui-linkbutton"
			   onclick="TicketList.reload();">重置</a>
		</div>

		<%--<div data-options="region:'center',border:false">--%>
			<table id="show_dg"></table>
		<%--</div>--%>
	</div>
	<div title="票型审核列表" data-options="closable:false, fit:true" style="width:100%;height:100%;">
		<div id="user_type_Tool" style="padding:2px 5px;">
			<input  class="easyui-combobox" id="search_type_ticketType"  name="ticket" style="width: 120px;"
					data-options="
							 	prompt:'请选择类型',
							 	editable:false,
								valueField: 'id',
								textField: 'value',
								panelHeight: 'auto',
								data: [{
									id: 'sailboat',
									value: '帆船'
								},{
									id: 'yacht',
									value: '游艇'
								},{
									id: 'huanguyou',
									value: '鹭岛游'
								}]"  />
			<input class="easyui-textbox" id="search_type_name" data-options="prompt:'请输入票型名称'" style="width:200px;">
			<input class="easyui-textbox" id="search_ticket_name" data-options="prompt:'请输入产品名称'" style="width:200px;">
			<input  class="easyui-combobox" id="search_type_ticketStatus"  name="ticket" style="width: 120px;"
					data-options="
							 	prompt:'请选择状态',
							 	editable:false,
								valueField: 'id',
								textField: 'text',
								panelHeight: 'auto',
								data: TicketConstants.ticketPriceStatus"  />

			<a href="javascript:void(0)" class="easyui-linkbutton"
			   onclick="TicketList.doShowTypeSearch();">查询</a>

			<a href="javascript:void(0)" class="easyui-linkbutton"
			   onclick="TicketList.reloadType();">重置</a>
		</div>

		<%--<div data-options="region:'center',border:false">--%>
			<table id="show_type_dg"></table>
		<%--</div>--%>
	</div>
</div>

<div id="editPanel" class="easyui-dialog" title="编辑海上休闲基本信息"
	 data-options="fit:true, resizable:false, modal:true, closed:true, closable:true, collapsible:false, shadow:false">
	<iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>
</div>
<div id="editPanel_0" class="easyui-dialog" title="海上休闲票型基本信息"
	 data-options="fit:true, resizable:false, modal:true, closed:true, closable:true, collapsible:false, shadow:false" >
	<iframe name="editIframe" id="editIframe_0" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>
</div>
<div id="editPanel_1" class="easyui-dialog" title="海上休闲票型价格日历"
	 data-options="resizable:false, modal:true, closed:true, closable:true, collapsible:false, shadow:false" style="width:700px; height: 490px;">
	<iframe name="editIframe" id="editIframe_1" scrolling="no" frameborder="0"  style="width:100%;height:640px;border-width:0;"></iframe>
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