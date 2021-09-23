<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>发送短信</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/sys/sysMsg/sysMsg.js"></script>
<link rel="stylesheet" type="text/css"
	href="/css/sys/sysRole/manage.css">
<script type="text/javascript" src="/js/sys/sysRight/sysRight.js"></script>
</head>

<body>
	<!--查询区域 始 -->
	<div id="tb" class="easyui-panel" title="查询条件" style="padding: 10px;">
		<form action="" id="searchform">
			<table cellpadding="5" style="width: 100%">
				<tr>
					<td>号码：</td>
					<td><input type="text" id="receivenum"></input></td>
					<td>文本内容：</td>
					<td><input type="text" id="context"></input></td>
<!-- 					<td>发送日期:</td> -->

					<%-- <td><s:select list="#{0:'激活',1:'冻结' }" id="msg_date" --%>
					<%-- headerKey="" headerValue="选择角色状态"></s:select></td> --%>
				</tr>
			</table>
		</form>
		<div style="text-align: center; padding: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysMsg.doSearch()">查询</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" onclick="$('#searchform').form('clear')">重置</a>
		</div>
	</div>
	<!--查询区域 终  -->

	<!-- 数据表格 始 -->
	<table id="tg">
	
	
	</table>
	<!-- 数据表格 终-->

	<!-- 数据表格 按纽组 始 --> 
<!-- 	<div id="toolbar"> -->
<!-- 		<a id="editbtn" href="#" onclick="SysMsg.openEditForm();" -->
<!-- 			class="easyui-linkbutton" >编辑短信</a> -->
<!-- 		<a id="delbtn" href="#" onclick="SysMsg.deleteMsg();" -->
<!-- 			class="easyui-linkbutton" >删除短信</a> -->
<!-- 		<a id="sendbtn" href="#" onclick="SysMsg.senddinMsg();" -->
<!-- 			class="easyui-linkbutton" >发送短信</a> -->
<!-- 	</div> -->
	<!-- 数据表格 按纽组 终 -->

	<!-- 编辑框  始--> 
<!-- 	<div class="easyui-dialog" id="edit_panel" closed="true" -->
<!-- 		onClose="SysMsg.clearForm()" style="width: 300px; top: 80px;"> -->
<!-- 		<form id="tt" method="post"> -->
<!-- 			<table cellpadding="5"> -->
<!-- 				<tr> -->
<!-- 					<td>电话号码</td> -->
<!-- 					<td><input type="button" id="select_from_db" value="从数据库选择号码" -->
<!-- 						onclick="sysMsg.upForm()" /></td> -->
<!-- 				<tr> -->
<!-- 					<td>文本内容</td> -->
<!-- 					<td><input class="easyui-textbox" name="remark" -->
<!-- 						data-options="multiline:true" style="height: 200px"></input></td> -->
<!-- 				</tr> -->

<!-- 			</table> -->
<!-- 		</form> -->
<!-- 		<div style="text-align: center; padding: 5px"> -->
<!-- 			<a href="javascript:void(0)" class="easyui-linkbutton" -->
<!-- 				onclick="SysMsg.submitForm()">确定</a> <a href="javascript:void(0)" -->
<!-- 				class="easyui-linkbutton" onclick="SysMsg.clearForm()">重置</a> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<!-- 编辑框 终 --> 

	<!--号码选择框 始 --> 
<!-- 	<div class="easyui-dialog" id="up_panel" closed="true" -->
<!-- 		onClose="SysMsg.clearForm()" style="width: 300px; top: 100px;"> -->
<!-- 		<form id="gg" method="post"> -->
		<!-- 数据库中号码 始 --> 
<!-- 			<table id="db"></table> -->
		<!-- 数据库中号码  终 --> 
<!-- 			<div style="text-align: center; padding: 5px"> -->
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysMsg.submitForm()">确定</a>  -->
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="SysMsg.clearForm()">重置</a> -->
<!-- 			</div> -->
<!-- 		</form> -->
<!-- 	</div> -->
	<!--号码选择框 终 --> 

</body>
</html>