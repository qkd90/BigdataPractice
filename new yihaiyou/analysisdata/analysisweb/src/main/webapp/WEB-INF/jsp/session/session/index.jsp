<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>会话管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${path}/js/session/session/index.js"></script>
</head>
<body>
	<!-- begin 会话查询 -->
	<form id="fm" style="padding: 0">
		<div class="easyui-panel easyui-layout" title="订单查询">
			<table cellpadding="5" width="100%">
				<tr>
					<td>用户ID:</td>
					<td><input class="easyui-numberbox" id="uid" width="20"></input></td>
					<td>访问数:</td>
					<td><input class="easyui-numberbox" id="startPv" style="width:100px;"/> - <input width="10" class="easyui-numberbox" id="endPv"  style="width:100px;"/></td>
					<td>开始时间:</td>
					<td><input class="easyui-datetimebox" id="startTime"></input></td>
					<td>结束时间:</td>
					<td><input class="easyui-datetimebox" id="endTime"></input></td>
				</tr>
				<tr>
					<td>耗时:</td>
					<td><input class="easyui-numberbox" id="startCost"  style="width:100px;"/> - <input  style="width:100px;" class="easyui-numberbox" id="endCost" /></td>
					<td>来源:</td>
					<td><input width="20" class="easyui-textbox" id="referer" /></td>
					<td>&nbsp; </td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="center" colspan="8"><a href="javascript:void(0)" id="search" class="easyui-linkbutton" >查询</a> <a
						href="javascript:void(0)" class="easyui-linkbutton" >重置</a></td>
				</tr>
			</table>
		</div>
	</form>
	<!-- end 订单查询 -->
	<!-- 会话表 start-->
	<div id="Session"></div>
	<!-- 会话表 end-->
	
	<!-- 打开会话的动画窗口 -->
	<div id="sessionWindow"></div>
</body>
</html>