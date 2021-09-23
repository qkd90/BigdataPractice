<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>用户管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="/js/ticket/ticketList.js"></script>
<script type="text/javascript" src="/js/ticket/ticketUtil.js"></script>
<%-- <script type="text/javascript" src="/js/user/user.js"></script> --%>

</head>
<body style="background-color: white;">

	<div title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
		<div id="store_userTool" style="height: auto">
			<div>
				筛选条件：
				<input  class="easyui-combobox" id="store_ticketType"  name="ticket" style="width: 100px;line-height:22px;border:1px solid #95B8E7"
						data-options="
									prompt:'请选择类型',
									editable:false,
									valueField: 'id',
									textField: 'value',
									panelHeight: 'auto',
									data: [
									{
										id: 'ALL',
										value: '选择全部类型'
									},{
										id: 'scenic',
										value: '景点门票'
									},{
										id: 'shows',
										value: '演出票'
									}]"  />
				<input class="easyui-combobox" style="width: 120px" id="store_category" data-options="prompt:'请选择门票分类', editable:false" name="category"/>
				<input  class="easyui-combobox" id="store_status"  name="ticket" style="width: 100px;line-height:22px;border:1px solid #95B8E7"
						data-options="
									prompt:'请选择状态',
									editable:false,
									valueField: 'id',
									textField: 'value',
									panelHeight: 'auto',
									data: [
									{
										id: 'ALL',
										value: '选择全部状态'
									},{
										id: 'UP',
										value: '上架'
									},{
										id: 'DOWN',
										value: '下架'
									}]"  />

				<input  class="easyui-combobox" id="store_ticketSource"  name="ticket" style="width: 120px;line-height:22px;border:1px solid #95B8E7"
						data-options="
									prompt:'请选择门票来源',
									editable:false,
									<%--'CTRIP','JUHE','ELONG','QUNAR'--%>
									valueField: 'id',
									textField: 'value',
									panelHeight: 'auto',
									data: [
									{
										id: 'ALL',
										value: '选择全部'
									},{
										id: 'LXB',
										value: '本平台',
										selected:true

									},{
										id: 'JUHE',
										value: '聚合'
									},{
										id: 'CTRIP',
										value: '携程'
									},{
										id: 'ELONG',
										value: '艺龙'
									},{
										id: 'QUNAR',
										value: '去哪儿'
									}]"  />

				<input class="easyui-textbox" id="store_name" data-options="prompt:'请输入门票标题或编号'" style="width:200px;line-height:20px;border:1px solid #ccc">
				<a href="javascript:void(0)" class="easyui-linkbutton"
				   onclick="TicketList.doStorehouseSearch();">查询</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
				   onclick="TicketList.reloadStore();">重置</a>
			</div>

			<div id="addTool">
				<a href="javascript:void(0)"   class="easyui-linkbutton"  onclick="TicketList.doChangeTicketStatus('CHECKING')">提交审核</a>
				<a href="javascript:void(0)"   class="easyui-linkbutton"  onclick="TicketList.doChangeTicketStatus('DEL')">删除</a>
				<a href="javascript:void(0)"   class="easyui-linkbutton"  onclick="TicketList.doChangeTicketStatus('DOWN')">下架</a>
				<a href="javascript:void(0)"   class="easyui-linkbutton"  onclick="TicketList.addTicket()">添加新门票</a>
			</div>
		</div>

		<div data-options="region:'center',border:false">
			<table id="storehouse_dg"></table>
		</div>
	</div>

</body>
</html>